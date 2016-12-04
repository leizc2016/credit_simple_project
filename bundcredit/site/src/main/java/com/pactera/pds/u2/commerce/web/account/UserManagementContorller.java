package com.pactera.pds.u2.commerce.web.account;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.service.account.BCAccountService;
import com.pactera.pds.u2.commerce.utils.Sessions;

@Controller
@RequestMapping(value = "/user/user_management")
public class UserManagementContorller {
 
    @Autowired
    private BCAccountService bcAccountService;

    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        Map<String, Object> map=new HashMap<>();
        map.put("insCode", Sessions.insCode());
        List<BCUser> bcUsers = null;
        
        BCUser currentUser = bcAccountService.findUserByLoginName(Sessions.loginName());
        
		if (("admin").equals(currentUser.getRoles())) {
			bcUsers = bcAccountService.getAllUser(map);
		} else if (("account").equals(currentUser.getRoles())) {
			bcUsers = bcAccountService.getAllNoneAdminUsers(map);
		}
        model.addAttribute("bcusers", bcUsers);

        return "user/user_management";
    }

    @RequestMapping(value="user_password/{id}",method = RequestMethod.GET)
    public String alterPassword(@PathVariable("id") long id,Model model) {
        BCUser bcUser = bcAccountService.getBcUser(id);
        model.addAttribute("bcuser", bcUser);
        return "user/user_password";
    }
    @RequestMapping(value="password_save",method = RequestMethod.POST)
    public String passwordSave(BCUser user,String oldPassword,Model model,RedirectAttributes redirectAttributes) {
            bcAccountService.updatePassword(user);
            redirectAttributes.addFlashAttribute("message", "修改"+user.getLoginName()+"密码成功");
              return "redirect:/user/user_management";
    
        
    }
    @RequestMapping(value="user_update/{id}",method = RequestMethod.GET)
    public String passwordreset(@PathVariable("id") long id,Model model) {
        BCUser bcUser = bcAccountService.getBcUser(id);
        model.addAttribute("bcuser", bcUser);
        return "user/user_update";
    }
    @RequestMapping(value="userpage",method = RequestMethod.GET)
    public String adduser(Model model) {
        model.addAttribute("inscode", Sessions.insCode());
        return "user/add";
    }
    @RequestMapping(value="adduser",method = RequestMethod.POST)
	public String save(BCUser user, Model model, RedirectAttributes redirectAttributes) {
		if (Sessions.insCode().equals(user.getInsCode())) {
			if (null == bcAccountService.findUserByLoginName(user.getLoginName())){
				bcAccountService.save(user);
				redirectAttributes.addFlashAttribute("message", "创建用户" + user.getLoginName() + "成功");
				return "redirect:/user/user_management";
			}
			else {
//				redirectAttributes.addFlashAttribute("message", "用户名" + user.getLoginName() + "已存在");
				model.addAttribute("message", "用户名" + user.getLoginName() + "已存在");
				model.addAttribute("inscode", Sessions.insCode());
				return "user/add";
			}
		} else {
			redirectAttributes.addFlashAttribute("error_message", "创建用户" + user.getLoginName() + "失败");
			return "redirect:/user/user_management";
		}
	}
/*    @RequestMapping(value="password_reset/{id}",method = RequestMethod.POST)
    public String passreset(BCUser user ,Model model) {
        bcAccountService.updatePassword(user);
        return "user/user_password";
    } */
    @RequestMapping(value="forbidden/{id}",method = RequestMethod.GET)
    public String forbidden(@PathVariable("id") long id ,Model model,RedirectAttributes redirectAttributes) {
        BCUser bcUser = bcAccountService.getBcUser(id);
       /* if(bcUser.getAllow()==allow){
            
        }*/
        int allow=bcUser.getAllow();
        Map<String, Object> param=new HashMap<>();
        param.put("id", id);
        if(allow==1){
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
        
        return "redirect:/user/user_management";
    }
    
    @RequestMapping(value="password_validate",method = RequestMethod.POST)
    public void passwordValidate(Long id,String oldPassword,HttpServletResponse response) {
        try {  
        if (bcAccountService.validatePassword(id, oldPassword)) {
            response.getWriter().write("1");
        }
        else{
            response.getWriter().write("0");
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "validatefirst")
    @ResponseBody
    public String validateFirst() {
        if (bcAccountService.getBcUser(Sessions.id()).getLastLoginTime()==null) {
            return "true";
        } else {
            return "false";
        }
    }
    @RequestMapping(value = "firstpass")
    public String firstpass(BCUser user) {
         user.setId(Sessions.id());
        bcAccountService.updatePassword(user);
        return "";
    }
   
}
