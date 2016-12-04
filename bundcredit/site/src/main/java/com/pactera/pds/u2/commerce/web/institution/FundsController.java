package com.pactera.pds.u2.commerce.web.institution;

import javax.servlet.ServletRequest;

import org.apache.http.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.github.miemiedev.mybatis.paginator.domain.Order;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.interceptor.PageHelper;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionService;
import com.pactera.pds.u2.commerce.utils.Sessions;

@Controller
@RequestMapping(value = "/funds")
public class FundsController {
    @Autowired
    private InstitutionService institutionService;
    @RequestMapping(method = RequestMethod.GET)
    public String fundsPage(Model model,
			ServletRequest request){
        String insCode=Sessions.insCode();
//        PageBounds pb=new PageBounds(Order.formString("trans_datetime.desc"));
        PageBounds pageBound = PageHelper.composeFromRequest4site(request, model);
        Asserts.notEmpty(insCode, "机构代码");
        model.addAttribute("institution", institutionService.getByInscode(insCode));
        model.addAttribute("transLogs",institutionService.findTransactionsByInscode(insCode, pageBound));
        return "/funds/detail";
    }
    
    public InstitutionService getInstitutionService() {
        return institutionService;
    }
    
    public void setInstitutionService(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }
}
