
package com.pactera.pds.u2.commerce.web.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pactera.pds.u2.commerce.service.insdatamgr.InsUpFiles2TmpDBService;

@Controller
@RequestMapping(value = "/api")
public class ApiListController {
    @Autowired
    InsUpFiles2TmpDBService ins;
    
	@RequestMapping(method = RequestMethod.GET)
	public String list() {
		return "api/list";
	}
	
	@RequestMapping(value="/import/tmpdb",method = RequestMethod.GET)
	public String test(){
	    ins.executeFileScanAndImport();
	    return "api/list";
	}
    
    public InsUpFiles2TmpDBService getIns() {
        return ins;
    }
    
    public void setIns(InsUpFiles2TmpDBService ins) {
        this.ins = ins;
    }
}
