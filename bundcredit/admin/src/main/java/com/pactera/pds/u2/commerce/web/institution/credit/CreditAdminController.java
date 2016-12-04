package com.pactera.pds.u2.commerce.web.institution.credit;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.BundPersonComment;
import com.pactera.pds.u2.commerce.entity.PersonBasicInfo;
import com.pactera.pds.u2.commerce.interceptor.PageHelper;
import com.pactera.pds.u2.commerce.service.car.CreditSearchService;
import com.pactera.pds.u2.commerce.utils.DESUtil;


@Controller
@RequestMapping(value = "/creditadmin")
public class CreditAdminController {
    
    @Autowired
    private CreditSearchService creditSearchService;
    @Autowired
    private DESUtil des;
    @RequestMapping(value = "/persons", method = RequestMethod.GET)
    public String listAllPersonBasicInfo(@RequestParam(value = "q_fullName", defaultValue = "")String fullName, 
        @RequestParam(value = "q_idCardNum", defaultValue = "")String idCardNum,  @RequestParam(value = "q_location", defaultValue = "")String location,PersonBasicInfo personBasicInfo,Model model,ServletRequest request) {
        PageBounds pageBound=PageHelper.composeFromRequest(request, model);
        Map<String, Object> person=new HashMap<String, Object>();
        person.put("fullName",fullName);
        try {
            if(!"".equals(idCardNum)){
            person.put("idCardNum",des.encrypt(idCardNum));}
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        person.put("location",location);
        model.addAttribute("persons", creditSearchService.listAllPerson(person,pageBound));
        person.put("idCardNum",idCardNum);
       model.addAllAttributes(person);
        return "/credit/persons";
    }
    
    @RequestMapping(value = "/{idCardNum}/comments", method = RequestMethod.GET)
    public String comment(@PathVariable("idCardNum") String idCardNum,@RequestParam("idCardNumString") String idCardNumString,Model model) throws Exception {
    	List<BundPersonComment> bpcommentlist = creditSearchService.bundCommentByIdCardNum(idCardNum);
    	for(BundPersonComment bpcomment : bpcommentlist){
    		bpcomment.setIdCardNum(idCardNumString);
    	}
        model.addAttribute("comments", bpcommentlist);
        return "credit/comments";
    }
    
    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public String addForm(@RequestParam("idCardNum") String idCardNum, Model model) {
        BundPersonComment bp = new BundPersonComment();
        bp.setIdCardNum(idCardNum);
        model.addAttribute("comment", bp);
        return "credit/comment";
    }

    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public String addComment(@Valid BundPersonComment comment, Model model, RedirectAttributes redirectAttributes) throws Exception {
        comment.setLastUpdateDatetime(new Timestamp(System.currentTimeMillis()));
        Long id = creditSearchService.addBundComment(comment);
        comment.setIdCardNum(des.decrypt(comment.getIdCardNum()));
        redirectAttributes.addFlashAttribute("message", "添加备注成功。证件号：" + comment.getIdCardNum() + ",备注内容：" + comment.getContent());
        return "redirect:/creditadmin/persons";
    }
    
    public CreditSearchService getCreditSearchService() {
        return creditSearchService;
    }
    
    public void setCreditSearchService(CreditSearchService creditSearchService) {
        this.creditSearchService = creditSearchService;
    }
}
