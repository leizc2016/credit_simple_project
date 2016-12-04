package com.pactera.pds.u2.commerce.web.institution;

import java.util.List;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.interceptor.PageHelper;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionUploadedFileService;


@Controller
@RequestMapping(value = "/insdata")
public class InsDataFileUploadController {
    @Autowired
    private InstitutionUploadedFileService insFileService;
    
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model,
			ServletRequest request) {
    	PageBounds pageBound = PageHelper.composeFromRequest4site(request, model);
        model.addAttribute("insFiles", insFileService.findByInsCode(pageBound));
        return "insdata/list";
    }
    public static Long MAX_SIZE = 5*1024*1024L; 
    @RequestMapping(value = "{fileType}/upload", method = RequestMethod.POST)
    public String upload(@PathVariable("fileType") Integer fileType, @RequestParam("files") List<MultipartFile> attachments, Model model,
        RedirectAttributes redirectAttributes) {
//    	if(attachments.size() > 0){
//    		MultipartFile file = attachments.get(0);
//    		Long uploadSize = file.getSize();
//    		if(uploadSize == 0L){
//    			redirectAttributes.addFlashAttribute("message_info", "上传失败， 文件不能为空");
//        		return "redirect:/insdata";
//    		}
//    		if(uploadSize > MAX_SIZE){
//    			redirectAttributes.addFlashAttribute("message_info", "上传失败， 上传文件最大5M");
//        		return "redirect:/insdata";
//    		}
//    		if(!file.getOriginalFilename().endsWith(".txt") && !file.getOriginalFilename().endsWith(".TXT")){
//    			redirectAttributes.addFlashAttribute("message_info", "上传失败， 文件类型应为txt");
//        		return "redirect:/insdata";
//    		}
//    	}
        insFileService.saveUploadMetaFiles(fileType, attachments);
        redirectAttributes.addFlashAttribute("message_info", "上传文件成功");
        return "redirect:/insdata";
    }
    
}
