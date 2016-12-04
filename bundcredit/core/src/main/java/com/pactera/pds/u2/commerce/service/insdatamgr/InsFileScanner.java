package com.pactera.pds.u2.commerce.service.insdatamgr;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.util.Asserts;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pactera.pds.u2.commerce.entity.InstitutionUploadedFile;
import com.pactera.pds.u2.commerce.repository.mybatis.BCInsDataUploadMybatisDao;
import com.pactera.pds.u2.commerce.repository.mybatis.InstitutionUploadedFileMybatisDao;
import com.pactera.pds.u2.commerce.utils.AntZipUtil;
import com.pactera.pds.u2.commerce.utils.DESUtil;
import com.pactera.pds.u2.commerce.utils.IdcardUtils;


public class InsFileScanner {
	
	@Autowired
    private InstitutionUploadedFileService insFileService;
	
    @Autowired
    private DESUtil des;
    private Map<Integer, String> initMap;
    
    private Map<Integer, String> initMap2;
    
    private Map<Integer, List<String>> templateMap = new HashMap<Integer, List<String>>();
    
    public static String DELIMITER = "\\|";
    
    public Map<Integer, String> getInitMap() {
        return initMap;
    }
    
    public void setInitMap(Map<Integer, String> initMap) {
        this.initMap = initMap;
        for (Integer key : initMap.keySet()) {
            List<String> sortedProp = new ArrayList<String>();
            String[] tmp = initMap.get(key).split(DELIMITER);
            for (String s : tmp) {
                sortedProp.add(s);
            }
            templateMap.put(key, sortedProp);
        }
    }
    
    public ArrayList<ArrayList<String>> readExcel(InstitutionUploadedFile f) {
    	
    	ArrayList<ArrayList<String>> Row =new ArrayList<ArrayList<String>>();
		
		try {
			Workbook workBook = null;
			try {
				workBook = new XSSFWorkbook(f.me());
			} catch (Exception ex) {
				workBook = new HSSFWorkbook(new FileInputStream(f.me()));
			}
			
			
			for (int numSheet = 0; numSheet < workBook.getNumberOfSheets(); numSheet++) {
				Sheet sheet = workBook.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
				// Loop row
				for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
					Row row = sheet.getRow(rowNum);
					if (row == null) {
						continue;
					}
					
					if (row.getCell(0) == null) {
						continue;
					}

					String cellvalue = getValue(row.getCell(0)).trim();
					if ("".equals(cellvalue)) {
						continue;
					}
					
					// Loop cell
					ArrayList<String> arrCell =new ArrayList<String>();
					for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
						Cell cell = row.getCell(cellNum);
						if (cell == null) {
							arrCell.add("");
							continue;
						}
						arrCell.add(getValue(cell));
					}
					Row.add(arrCell);
				}
			}
		} catch (IOException e) {
			System.out.println("e:"+e);
		}
		
		return Row;
    }
    
    private String getValue(Cell cell) {
		if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue());
		} else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
			return String.valueOf(cell.getNumericCellValue());
		} else {
			return String.valueOf(cell.getStringCellValue());
		}
	}
    
	public List<Map<String, String>> processImportExcelData(InstitutionUploadedFile f) {
		Integer type = f.getFileType();
		String insCode = f.getInsCode();
		String uploadFileId = "" + f.getId();
		List<String> fileds = templateMap.get(type);

		List<Map<String, String>> batch = new ArrayList<Map<String, String>>();
		Integer count = Integer.parseInt(initMap2.get(type));

		ArrayList<ArrayList<String>> excelRows = readExcel(f);

		for (ArrayList<String> row : excelRows) {

			String[] cellArray = new String[row.size()];
			row.toArray(cellArray);
			Map<String, String> m = makeMap(cellArray, fileds, count);
			m.put("insCode", insCode);
			m.put("uploadFileId", uploadFileId);
			try {
				if (m.get("idCardNum") != null) {
					m.put("idCardNum", des.encrypt(m.get("idCardNum").trim().toUpperCase()));
				} else {
					m.put("id_card_num", des.encrypt(m.get("id_card_num").trim().toUpperCase()));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			batch.add(m);
		}
		return batch;
	}
	
	/**
	 * Extract Zip file, save files to local and and record to DB table.
	 * @param InstitutionUploadedFile File record
	 * @return
	 */
	public boolean uncompressZipData(InstitutionUploadedFile f) {
		try {
//			List<String> fileList = ectract(f.me().toString());
			
			List<String> fileList = AntZipUtil.unZip(f.me().toString(), f.me().getParentFile().getPath());

			if(fileList == null || fileList.size() == 0){
				return false;
			}
			List<MultipartFile> mFileList = insFileService
					.convertFileToMultipartFile(fileList);

			insFileService.saveZipFiles(f, mFileList);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

    
	public Boolean validateImportExcelData(InstitutionUploadedFile f,
			InstitutionUploadedFileMybatisDao uploadDao,
			BCInsDataUploadMybatisDao uploadTmpDao) {
		boolean result = true;
		Integer type = f.getFileType();
		String insCode = f.getInsCode();
		String uploadFileId = "" + f.getId();
		List<String> fileds = templateMap.get(type);
		
		ArrayList<ArrayList<String>> excelRows = readExcel(f);
		
		int index = 1;
		StringBuffer sb = new StringBuffer();
		Integer count = Integer.parseInt(initMap2.get(type));
		
		for (ArrayList<String> row : excelRows) {
			index++;
			
			String[] cellArray = new String[row.size()];
			row.toArray(cellArray);
			
//			if (cellArray == null || count > cellArray.length) {
//				sb.append("【第" + index + "行数据不够，至少要有" + count + "个】<br>");
//				result = false;
//				continue;
//			}
//			if (!"end".equalsIgnoreCase(cellArray[cellArray.length - 1].trim())) {
//				sb.append("【第" + index + "行没有以end结尾】<br>");
//				result = false;
//				continue;
//			}

			Map<String, String> m = makeMap(cellArray, fileds, count);
			m.put("insCode", insCode);
			m.put("uploadFileId", uploadFileId);

			// Validate ID Card Number
			if (("0".equals(m.get("idCardType")))
					&& !IdcardUtils.validateCard(m.get("idCardNum"))) {
				sb.append("【第" + index + "行身份证号码有误】<br>");
				result = false;
				continue;
			}

			// Validate Passport
			if (("2".equals(m.get("idCardType")))
					&& (!IdcardUtils.isNum(m.get("idCardNum")) || m.get(
							"idCardNum").length() != 9)) {
				sb.append("【第" + index + "行护照号码有误】<br>");
				result = false;
				continue;
			}

			if (type == 1 // 验证重复
					&& (!validateNull4Apply(m, sb, index)
							|| !validateOthers4Apply(m, uploadDao, index, sb) || isRepeat4Apply(
								m, uploadDao, uploadTmpDao, index, sb))) {
				result = false;
				continue;
			}
			if (type == 2 // 验证重复
					&& (!validateNull4Acc(m, sb, index)
							|| !validateOthers4Acc(m, uploadDao, index, sb) || isRepeat4Acc(
								m, uploadDao, uploadTmpDao, index, sb))) {
				result = false;
				continue;
			}
			
			if (type == 4 // 验证重复
					&& (!validateNull4comment(m, sb, index) ||
						 isRepeat4comment(m, uploadDao, uploadTmpDao, index, sb))) {
				result = false;
				continue;
			}

		}

		if (index == 0) {
			result = false;
			sb.append("【文件为空】");
		}
		if (sb.length() > 4000) {
			f.setValidationLog(sb.substring(0, 4000));
		} else {
			f.setValidationLog(sb.toString());
		}
		return result;
	}
    
    
    public List  processImportData(InputStream in, Integer type,String insCode,Long uploadFileId) {
        //TODO 以后遇到乱码需考虑根据文件编码采用相应的编码读入
        Scanner sn = new Scanner(new BufferedInputStream(in),"UTF-8");
        List<String> fileds = templateMap.get(type);
        Asserts.check(templateMap.containsKey(type), "尚不支持指定文件类型解析。type=" + type);
        List batch = new ArrayList();
        Integer count = Integer.parseInt(initMap2.get(type));
        while (sn.hasNext()) {
            String[] d = sn.nextLine().split(DELIMITER);
//            if (d != null && d.length >= fileds.size()) {
//                Map m = new HashMap(d.length);
                Map<String, String> m = makeMap(d, fileds, count);
                m.put("insCode", insCode);
                m.put("uploadFileId", uploadFileId+"");
                try {
                    
                    if(m.get("idCardNum")!=null){
                        m.put("idCardNum", des.encrypt(m.get("idCardNum").trim().toUpperCase()));
                    }
                    else
                    {
                        m.put("id_card_num", des.encrypt(m.get("id_card_num").trim().toUpperCase()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//                for (int i = 0; i < fileds.size() && i < d.length; i++) {
//                	if(".".equals( d[i])){
//                		d[i] = "";
//                	}
//                    m.put(fileds.get(i), d[i]);
//                }
                batch.add(m);
//            }
        }
        sn.close();
        return batch;
    }
    
	public Boolean validateImportData(InstitutionUploadedFile f, 
									  InstitutionUploadedFileMybatisDao uploadDao,
			                          BCInsDataUploadMybatisDao uploadTmpDao) {
    	boolean result = true; 
    	InputStream in = null;
		try {
			in = new FileInputStream(f.me());
		} catch (FileNotFoundException e) {
		}
    	Integer type = f.getFileType();
    	String insCode = f.getInsCode();
    	String uploadFileId= ""+f.getId();
    	Scanner sn = new Scanner(new BufferedInputStream(in),"UTF-8");
        List<String> fileds = templateMap.get(type);
//        Asserts.check(templateMap.containsKey(type), "尚不支持指定文件类型解析。type=" + type);
        int index = 0;
        StringBuffer sb = new StringBuffer();
        Integer count = Integer.parseInt(initMap2.get(type));
//        if(!hasContent){
//        	result = false; 
//        	sb.append("【文件为空】");
//        }
        while (sn.hasNext()) {
        	index++;
            String[] d = sn.nextLine().split(DELIMITER);
            if(d==null || count >= d.length){
            	sb.append("【第" + index + "行数据不够，至少要有"+count + "个】<br>");
            	result = false;
            	continue;
            }
            if(!"end".equalsIgnoreCase(d[d.length-1].trim())){
            	sb.append("【第"+index+"行没有以end结尾】<br>");
            	result = false;
            	continue;
            }
            
            Map<String, String> m = makeMap(d, fileds, count);
            m.put("insCode", insCode);
            m.put("uploadFileId", uploadFileId);
//            Map<String, String> m = new HashMap<String, String>(d.length);
//            for (int i = 0; i < fileds.size() && i < d.length; i++) {
//            	String val;
//            	if(count <= i || ".".equals( d[i])){
//            		val = "";
//            	}else{
//            		val = d[i];
//            	}
//            	m.put(fileds.get(i), val);
////                m.put(fileds.get(i), d[i]);
//            }
            
            
            // Validate ID Card Number
            if(("0".equals(m.get("idCardType"))) && !IdcardUtils.validateCard(m.get("idCardNum"))){
            	sb.append("【第"+index+"行身份证号码有误】<br>");
            	result = false;
            	continue;
        	}
            
            // Validate Passport
            if(("2".equals(m.get("idCardType"))) && (!IdcardUtils.isNum(m.get("idCardNum")) || m.get("idCardNum").length() != 9)){
            	sb.append("【第"+index+"行护照号码有误】<br>");
            	result = false;
            	continue;
        	}
            
            if(type == 1  //验证重复
            		&& (!validateNull4Apply(m, sb, index)  
            		|| !validateOthers4Apply(m, uploadDao, index, sb)
            		|| isRepeat4Apply(m, uploadDao, uploadTmpDao, index, sb))){
            	result = false;
            	continue;
            }
            if(type == 2  //验证重复
            		&& (!validateNull4Acc(m, sb, index)  
            		|| !validateOthers4Acc(m, uploadDao, index, sb)
            		|| isRepeat4Acc(m, uploadDao, uploadTmpDao, index, sb))){
            	result = false;
            	continue;
            }
            
			if (type == 4 // 验证重复
					&& (!validateNull4comment(m, sb, index) ||
						 isRepeat4comment(m, uploadDao, uploadTmpDao, index, sb))) {
				result = false;
				continue;
			}
        }
        if(index == 0){
	    	result = false; 
	    	sb.append("【文件为空】");
        }
        if(sb.length() > 4000){
        	f.setValidationLog(sb.substring(0, 4000));
        }else{
        	f.setValidationLog(sb.toString());
        }
        sn.close();
        return result;
    }
	
	private boolean isRepeat4comment(Map<String, String> m,
			InstitutionUploadedFileMybatisDao uploadDao, BCInsDataUploadMybatisDao uploadTmpDao, int index,
			StringBuffer sb) {
		String idCardNum = m.get("idCardNum");
		String idCardType = m.get("idCardType");
		String commentType = m.get("commentType");
		String commentContent = m.get("commentContent");
		String commentTime  = m.get("commentTime");
		String insCode = m.get("insCode");
		int count = 1;
		Date commentTimeObj = format(commentTime);
		try {
			if (uploadDao.hasItem4comment(idCardType, des.encrypt(idCardNum), insCode, commentTimeObj) == 0) {
				List<InstitutionUploadedFile> files = uploadDao.findAvaliableFiles();
				if (files.size() > 0) {
					StringBuffer fileIDs = new StringBuffer();
					for (InstitutionUploadedFile file : files) {
						fileIDs.append(file.getId());
						fileIDs.append(",");
					}
					fileIDs.deleteCharAt(fileIDs.length()-1);
					String avaFiles = fileIDs.toString();
					count = uploadTmpDao.hasItem4CommentTmp(idCardType, des.encrypt(idCardNum), insCode, commentTime, avaFiles);
				} else {
					count = 0;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(count == 0){
			return false;
		}
		sb.append("【第"+index+"行是重复数据】<br>");
		return true;
	}
	
	private boolean validateNull4comment(Map<String, String> m, StringBuffer sb,
			int index) {
		String idCardNum = m.get("idCardNum");
		String idCardType = m.get("idCardType");
		String commentType = m.get("commentType");
		String commentContent = m.get("commentContent");
		String commentTime  = m.get("commentTime");
		String insCode = m.get("insCode");
		if(StringUtils.isEmpty(idCardNum) || 
				StringUtils.isEmpty(idCardType) || 
				StringUtils.isEmpty(commentType) || 
				StringUtils.isEmpty(commentContent) || 
				StringUtils.isEmpty(commentTime) ||
				StringUtils.isEmpty(insCode)){
			sb.append("【第"+index+"行验证重复数据失败，缺少关键数据】<br>");
			return false;
		}
		return true;
	}
	
//    private String loanstatusSet = "_0_1_2_3_4_5_6_7_E_e_";
    private String loanstatusSet = "_0_1_2_3_4_5_6_7_C_c_D_d_G_g_O_o_/_*_N_n";
    private String applyTypeSet = "_N_A_H_O_P_E_X_n_a_h_o_p_e_x_";
    private boolean validateOthers4Apply(Map<String, String> m,
			InstitutionUploadedFileMybatisDao uploadDao, int index,
			StringBuffer sb) {
    	String applyType = m.get("applyType");
    	if(applyTypeSet.indexOf(applyType) == -1){
    		sb.append("【第"+index+"行验证失败，无法识别“申请类型”】<br>");
    		return false;
    	}
		return true;
	}
    private boolean validateOthers4Acc(Map<String, String> m,
			InstitutionUploadedFileMybatisDao uploadDao, int index,
			StringBuffer sb) {
    	String loanstatus = m.get("loan_status_1");
    	if(loanstatusSet.indexOf(loanstatus) == -1){
    		sb.append("【第"+index+"行验证失败，无法识别“当前状态”】<br>");
    		return false;
    	}
		return true;
	}
    

	private Map<String, String> makeMap(String[] d, List<String> fileds,
			Integer count) {
		Map<String, String> m = new HashMap<String, String>(d.length);
        for (int i = 0; i < fileds.size(); i++) {
        	String val;
        	if(count <= i || d.length <= i || ".".equals( d[i])){
        		val = "";
        	}else{
        		val = d[i];
        	}
        	if(val.equals("")){
        		m.put(fileds.get(i), null);
        	}else
        		m.put(fileds.get(i), val);
        }
		return m;
	}

	private boolean isRepeat4Acc(Map<String, String> m,
			InstitutionUploadedFileMybatisDao uploadDao, BCInsDataUploadMybatisDao uploadTmpDao, int index,
			StringBuffer sb) {
		String loanAccount = m.get("loan_account");
		String idCardType = m.get("id_card_type");
		String idCardNum = m.get("id_card_num");
		String insCode = m.get("insCode");
		String lastestRepayDate  = m.get("next_loan_reply_date");
		int count = 1;
		Date lastestRepayDateObj = format(lastestRepayDate);
		try {
			if (uploadDao.hasItem4AccountDetail(idCardType, des.encrypt(idCardNum), insCode, lastestRepayDateObj, loanAccount) == 0) {
				List<InstitutionUploadedFile> files = uploadDao.findAvaliableFiles();
				if (files.size() > 0) {
					StringBuffer fileIDs = new StringBuffer();
					for (InstitutionUploadedFile file : files) {
						fileIDs.append(file.getId());
						fileIDs.append(",");
					}
					fileIDs.deleteCharAt(fileIDs.length()-1);
					String avaFiles = fileIDs.toString();
					count = uploadTmpDao.hasItem4AccountDetailTmp(idCardType, des.encrypt(idCardNum), insCode, lastestRepayDate, loanAccount, avaFiles);
				} else {
					count = 0;
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(count == 0)return false;
		sb.append("【第"+index+"行是重复数据】<br>");
		return true;
	}
	
	

	private boolean validateNull4Acc(Map<String, String> m, StringBuffer sb,
			int index) {
		String loanAccount = m.get("loan_account");
		String idCardType = m.get("id_card_type");
		String idCardNum = m.get("id_card_num");
		String insCode = m.get("insCode");
		String lastestRepayDate  = m.get("next_loan_reply_date");
		String loanstatus = m.get("loan_status_1");
		if(StringUtils.isEmpty(loanAccount) || 
				StringUtils.isEmpty(idCardType) || 
				StringUtils.isEmpty(idCardNum) || 
				StringUtils.isEmpty(insCode) || 
				StringUtils.isEmpty(lastestRepayDate) || 
				StringUtils.isEmpty(loanstatus)){
			sb.append("【第"+index+"行验证重复数据失败，缺少关键数据】<br>");
			return false;
		}
		return true;
	}

	private Date format(String str){
    	if(str == null || "".equals(str)){
    		return null;
    	}
    	DateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date applyDatetimeObj = null;
		try {
			applyDatetimeObj = format.parse(str);
		} catch (ParseException e) {
		}
		return applyDatetimeObj;
    }
    
    private boolean isRepeat4Apply(Map<String, String> m
    		, InstitutionUploadedFileMybatisDao uploadDao, BCInsDataUploadMybatisDao uploadTmpDao, int index, StringBuffer sb) {
		String loanAccount = m.get("loanAccount");
		String idCardType = m.get("idCardType");
		String idCardNum = m.get("idCardNum");
		String insCode = m.get("insCode");
		String applyDatetime = m.get("applyDatetime");
		int count = 1;
		if(StringUtils.isEmpty(loanAccount)){//未审批
			Date applyDatetimeObj = format(applyDatetime);
			try {
				if (uploadDao.hasItem4FailedApply(idCardType, des.encrypt(idCardNum), insCode, applyDatetimeObj) == 0) {
					List<InstitutionUploadedFile> files = uploadDao.findAvaliableFiles();
					if (files.size() > 0) {
						StringBuffer fileIDs = new StringBuffer();
						for (InstitutionUploadedFile file : files) {
							fileIDs.append(file.getId());
							fileIDs.append(",");
						}
						fileIDs.deleteCharAt(fileIDs.length()-1);
						String avaFiles = fileIDs.toString();
						count = uploadTmpDao.hasItem4FailedApplyTmp(idCardType, des.encrypt(idCardNum), insCode, applyDatetime, avaFiles);
					} else {
						count = 0;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				if (uploadDao.hasItem4SuccessfulApply(idCardType, des.encrypt(idCardNum), insCode, loanAccount) == 0) {
					List<InstitutionUploadedFile> files = uploadDao.findAvaliableFiles();
					
					if (files.size() > 0) {
						StringBuffer fileIDs = new StringBuffer();
						for (InstitutionUploadedFile file : files) {
							fileIDs.append(file.getId());
							fileIDs.append(",");
						}
						
						fileIDs.deleteCharAt(fileIDs.length()-1);
						String avaFiles = fileIDs.toString();
						count = uploadTmpDao.hasItem4SuccessfulApplyTmp(idCardType, des.encrypt(idCardNum), insCode, loanAccount, avaFiles);
					} else {
						count = 0;
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(count == 0)return false;
		sb.append("【第"+index+"行是重复数据】<br>");
		return true;
	}

	private boolean validateNull4Apply(Map<String, String> m, StringBuffer sb, int index) {
		String loanAccount = m.get("loanAccount");
		String idCardType = m.get("idCardType");
		String idCardNum = m.get("idCardNum");
		String insCode = m.get("insCode");
		String applyDatetime = m.get("applyDatetime");
		String applyType = m.get("applyType");
		if(StringUtils.isEmpty(loanAccount)){
			if(StringUtils.isEmpty(idCardType) || 
					StringUtils.isEmpty(idCardNum) || 
					StringUtils.isEmpty(insCode) || 
					StringUtils.isEmpty(applyDatetime) || 
					StringUtils.isEmpty(applyType) ){
				sb.append("【第"+index+"行验证重复数据失败，缺少关键数据】<br>");
				return false;
			}
		}else{
			if(StringUtils.isEmpty(loanAccount) || 
					StringUtils.isEmpty(idCardType) || 
					StringUtils.isEmpty(idCardNum) || 
					StringUtils.isEmpty(insCode) || 
					StringUtils.isEmpty(applyType) ){
				sb.append("【第"+index+"行验证重复数据失败，缺少关键数据】");
				return false;
			}
		}
		
		return true;
	}

	public Map<Integer, String> getInitMap2() {
		return initMap2;
	}

	public void setInitMap2(Map<Integer, String> initMap2) {
		this.initMap2 = initMap2;
	}

}
