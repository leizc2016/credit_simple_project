package com.pactera.pds.u2.commerce.web.account;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.service.account.AccountService;
import com.pactera.pds.u2.commerce.service.account.BCAccountService;
import com.pactera.pds.u2.commerce.utils.Sessions;

/** 机构用户管理
 * @author luogang
 *
 */
@Controller
@RequestMapping(value = "/user_Safeguard")
public class UserSafeguardContorller {
    @Autowired
    private AccountService accountService;
    @Autowired
    private BCAccountService bcAccountService;
    
    @RequestMapping(value="{insCode}",method = RequestMethod.GET)
    public String list(@PathVariable("insCode") String insCode,Model model) {
//        List<User> users = accountService.getAllUser();
//        model.addAttribute("users", users);
        Map<String, Object> map=new HashMap<>();
        System.out.println( insCode);
        
        map.put("insCode", insCode);
        List<BCUser> bcUsers = bcAccountService.getAllUser(map);
        model.addAttribute("insCode", insCode);
        model.addAttribute("bcusers", bcUsers);
        return "user/user_safeguard";
    }
    @RequestMapping(value="userpage/{insCode}",method = RequestMethod.GET)
    public String adduser(@PathVariable("insCode") String insCode,Model model) {
        model.addAttribute("insCode", insCode);
        return "user/add";
    }
    @RequestMapping(value="adduser",method = RequestMethod.POST)
    public String save(BCUser user,Model model,RedirectAttributes redirectAttributes) {
        if(bcAccountService.findUserByLoginName(user.getLoginName())!=null){
            redirectAttributes.addFlashAttribute("message", "登录名不能重复");
        }else{
        bcAccountService.save(user);
        redirectAttributes.addFlashAttribute("message", "创建用户" + user.getLoginName() + "成功");
        }
        return "redirect:/insadmin/institutions";
    }
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        BCUser user =  bcAccountService.getBcUser(id);
        model.addAttribute("user", user);
        return "user/update";
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public String update(BCUser user, RedirectAttributes redirectAttributes) {
        BCUser user1 =  bcAccountService.getBcUser(user.getId());
        bcAccountService.updateuser(user);
        redirectAttributes.addFlashAttribute("message", "更新用户" + user1.getLoginName() + "成功");
        return "redirect:/insadmin/institutions";
    }

    @RequestMapping(value = "delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        BCUser user = bcAccountService.getBcUser(id);
        bcAccountService.delete(id);
        redirectAttributes.addFlashAttribute("message", "删除用户" + user.getLoginName() + "成功");
        return "redirect:/insadmin/institutions";
    }

    @RequestMapping(value="forbidden/{id}",method = RequestMethod.GET)
    public String forbidden(@PathVariable("id") long id ,Model model,RedirectAttributes redirectAttributes) {
        BCUser bcUser = bcAccountService.getBcUser(id);
       /* if(bcUser.getAllow()==allow){
            
        }*/
        int allow=bcUser.getAllow();
        System.out.println(allow);
        Map<String, Object> param=new HashMap<String, Object>();
        param.put("id", id);
        if(allow==1){
            System.out.println("11");
            param.put("allow", 0);
        }
        else{
            param.put("allow", 1);
        }
        bcAccountService.forbiddenUser(param);
        if(allow==1)
        redirectAttributes.addFlashAttribute("message", "禁用"+bcUser.getLoginName()+"成功");
        else{
            redirectAttributes.addFlashAttribute("message", "启用"+bcUser.getLoginName()+"成功");
        }

        return "redirect:/insadmin/institutions";
    } 
}
