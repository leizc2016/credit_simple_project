
package com.pactera.pds.u2.commerce.web.account;


import java.util.Date;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.service.account.BCAccountService;
import com.pactera.pds.u2.commerce.utils.Sessions;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
    @Autowired
    private BCAccountService bcAccountService;
	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		//fix 已经登录的永辉到登录页永远无法登录问题
		if(Sessions.me().isAuthenticated()){
			return "redirect:/";
		}
		return "account/login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		//fix 已经登录的永辉到登录页永远无法登录问题
		if(Sessions.me().isAuthenticated()){
			return "redirect:/";
		}
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		return "account/login";
	}
	@RequestMapping(value="/roles",method = RequestMethod.GET)
	public String roleChoose(Model model,RedirectAttributes redirectAttributes){
	    
	  Subject subject=Sessions.me(); 
	  if(subject !=null){
	      if(bcAccountService.getBcUser(Sessions.id()).getLastLoginTime()!=null){
	          if(subject.hasRole("admin"))
	          {
	              return "redirect:/creditreport/history";}
	          if(subject.hasRole("creditreport"))
	          {
                  return "redirect:/creditreport/history";}
	          if(subject.hasRole("fund"))
	              return "redirect:/funds";
	          if(subject.hasRole("account"))
	              return "redirect:/user/user_management";
	           if(subject.hasRole("insdata"))
	              return "redirect:/insdata";
	      }
	     else{
	          return "redirect:/login/pass";
	      }
	  }
	     subject.logout();
	    return "redirect:/login";
	}
	@RequestMapping(value="/pass",method = RequestMethod.GET)
	public String pass(){
	    return "account/add";
	}
	@RequestMapping(value="/pass",method = RequestMethod.POST)
    public String passSave(String plainPassword){
	    BCUser bcUser=new BCUser();
	    bcUser.setPlainPassword(plainPassword);
	    bcUser.setId(Sessions.id());
	    bcUser.setLastLoginTime(new Date());
	    bcAccountService.updatePassword(bcUser);
        return "redirect:/";
    }
    
}
