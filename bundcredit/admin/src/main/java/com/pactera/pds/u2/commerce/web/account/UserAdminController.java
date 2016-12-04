
package com.pactera.pds.u2.commerce.web.account;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.User;
import com.pactera.pds.u2.commerce.interceptor.PageHelper;
import com.pactera.pds.u2.commerce.service.account.AccountService;

/**
 * 管理员管理用户的Controller.
 * 
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserAdminController {

	@Autowired
	private AccountService accountService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model,ServletRequest request,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		PageBounds pageBound = PageHelper.composeFromRequest(request, model);
		Page<User> users = accountService.getAllUser(searchParams, pageBound.getPage(), pageBound.getLimit(), sortType);
		Map<String, Object> commonPaginator = new HashMap<String, Object>();
		commonPaginator.put("totalPages", users.getTotalPages());
		commonPaginator.put("page", pageBound.getPage());
		commonPaginator.put("prePage", pageBound.getPage() -1);
		commonPaginator.put("nextPage", pageBound.getPage() + 1);
		if(pageBound.getPage() == users.getTotalPages()){
			commonPaginator.put("hasNextPage", false);
		}
		if(pageBound.getPage() ==1){
			commonPaginator.put("hasPrePage", false);
		}else{
			commonPaginator.put("hasPrePage", true);
		}
		model.addAttribute("commonPaginator", commonPaginator);
		model.addAttribute("users", users);
		return "account/adminUserList";
	}

	  @RequestMapping(value="addpage",method = RequestMethod.GET)
	    public String adduser(Model model) {
	        return "account/add";
	    }
	    @RequestMapping(value="save",method = RequestMethod.POST)
	    public String save(User user,Model model,RedirectAttributes redirectAttributes) {
	    	accountService.updateUser(user);
	    	redirectAttributes.addFlashAttribute("message", "创建用户" + user.getLoginName() + "成功");
	        return "redirect:/admin/user";
	    }
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		
		model.addAttribute("user", accountService.getUser(id));
		return "account/adminUserForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
		accountService.updateUser(user);
		redirectAttributes.addFlashAttribute("message", "更新用户" + user.getLoginName() + "成功");
		return "redirect:/admin/user";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		User user = accountService.getUser(id);
		accountService.deleteUser(id);
		redirectAttributes.addFlashAttribute("message", "删除用户" + user.getLoginName() + "成功");
		return "redirect:/admin/user";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("user", accountService.getUser(id));
		}
	}
	@RequestMapping(value="forbidden/{id}",method = RequestMethod.GET)
    public String forbidden(@PathVariable("id") long id ,Model model,RedirectAttributes redirectAttributes) {
        User bcUser = accountService.getUser(id);
       /* if(bcUser.getAllow()==allow){
            
        }*/
        int allow=bcUser.getAllow();
        System.out.println(allow);
        if(allow==1){
            bcUser.setAllow(0);
        }
        else{
            bcUser.setAllow(1);
        }
        accountService.save(bcUser);
        if(allow==1)
        redirectAttributes.addFlashAttribute("message", "禁用"+bcUser.getLoginName()+"成功");
        else{
            redirectAttributes.addFlashAttribute("message", "启用"+bcUser.getLoginName()+"成功");
        }
        
        return "redirect:/admin/user";
    } 
	
	@RequestMapping(value = "password_validate", method = RequestMethod.POST)
	public void passwordValidate(Long id, String oldPassword, HttpServletResponse response) {
		try {
			if (accountService.validatePassword(id, oldPassword)) {
				response.getWriter().write("1");
			} else {
				response.getWriter().write("0");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
