package com.pactera.pds.u2.commerce.web.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.service.account.BCAccountService;
import com.pactera.pds.u2.commerce.utils.Sessions;

@Controller
@RequestMapping(value = "/userperson")
public class UserPersonContorller {
 
    @Autowired
    private BCAccountService bcAccountService;

    //个人账户修改
    @RequestMapping(value="personmanagerpage",method = RequestMethod.GET)
    public String personManager(Model model) {
        BCUser bcUser=bcAccountService.getBcUser(Sessions.id());
        model.addAttribute("bcuser", bcUser);
        return "user/personManager";
    }
  
    @RequestMapping(value="personManagersave",method = RequestMethod.POST)
    public String personManagerSave( BCUser bcUser,Model model) {
        if(!bcAccountService.validatePassword(Sessions.id(), bcUser.getPlainPassword())){
            bcAccountService.updateuser(bcUser);
            model.addAttribute("bcuser", bcUser);
            model.addAttribute("message", "修改成功");
        }   
        else{
            model.addAttribute("bcuser", bcUser);
            model.addAttribute("message", "新密码不能和旧密码相同,请重新修改");
        }
        return "user/personManager";
    }
}
