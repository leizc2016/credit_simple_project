package com.pactera.pds.u2.commerce.service.storage;

import java.io.File;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.pactera.pds.u2.commerce.utils.Base64Utils;


/**
 * 
 * @author foy
 *
 *         简单文件服务
 */
public class SimpleFileService implements FileService {
    
    private String rootFilePath;
    
    private String rootFolderName = "/txt/";
    
    public long maxSize = 50000000L;
    
    @Override
    public String genFileName(MultipartFile file) {
        return file.getOriginalFilename();
    }
    
    private String genFileFullPath(String rId, String fileName) {
        return rootFilePath + rootFolderName + rId + "/" + fileName;
    }
    
    private void ensureExist(String rId) {
        File folder = new File(genFileFullPath(rId, ""));
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }
    
    private long usedFileSize(String rId) {
        File[] files = getFiles(rId);
        long size = 0L;
        if (null == files) {
            return size;
        }
        for (File f : files) {
            if (f.isFile()) {
                size = size + f.length();
            }
        }
        return size;
    }
    
    @Override
    public boolean saveFile(String rId, String pFileName, MultipartFile file) throws IllegalStateException, IOException {
        if ((file.getSize() + usedFileSize(rId)) > maxSize) {
            return false;
        }
        ensureExist(rId);
        String fileName = encodeFileName(pFileName);
        File destination = new File(genFileFullPath(rId, fileName));
        file.transferTo(destination);
        return true;
    }
    
    @Override
    public File getFile(String rId, String pFileName) {
        String fileName = encodeFileName(pFileName);
        return new File(genFileFullPath(rId, fileName));
    }
    
    @Override
    public File[] getFiles(String rId) {
        File parent = new File(genFileFullPath(rId, ""));
        return parent.listFiles();
    }
    
    @Override
    public String[] getFilesName(String rId) {
        File parent = new File(genFileFullPath(rId, ""));
        if (parent.isDirectory()) {
            String[] tmpFileNames = parent.list();
            String[] fileNames = new String[tmpFileNames.length];
            for (int i = 0; i < tmpFileNames.length; i++) {
                fileNames[i] = decodeFileName(tmpFileNames[i]);
            }
            return fileNames;
        }
        return new String[0];
    }
    
    @Override
    public boolean deleteFile(String rId, String pFileName) throws IOException {
        ensureExist(rId);
        String fileName = encodeFileName(pFileName);
        File destination = new File(genFileFullPath(rId, fileName));
        try {
            FileUtils.forceDelete(destination);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    private String encodeFileName(String fileName) {
        return Base64Utils.encodeBase64(fileName);
    }
    
    private String decodeFileName(String fileName) {
        return Base64Utils.decode(fileName);
    }

    
    public String getRootFilePath() {
        return rootFilePath;
    }

    
    public void setRootFilePath(String rootFilePath) {
        this.rootFilePath = rootFilePath;
    }
}
