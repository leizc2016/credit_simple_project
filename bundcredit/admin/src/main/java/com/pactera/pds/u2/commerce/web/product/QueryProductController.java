
package com.pactera.pds.u2.commerce.web.product;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletRequest;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.pactera.pds.u2.commerce.entity.QueryProduct;
import com.pactera.pds.u2.commerce.interceptor.PageHelper;
import com.pactera.pds.u2.commerce.service.product.QueryProductService;

/**
 * 管理员管理用户的Controller.
 * 
 */
@Controller
@RequestMapping(value = "/admin/product")
public class QueryProductController {

	@Autowired
	private QueryProductService queryProductService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model,ServletRequest request,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		PageBounds pageBound = PageHelper.composeFromRequest(request, model);
		Page<QueryProduct> queryProducts = queryProductService.getQueryProduct(searchParams, pageBound.getPage(), pageBound.getLimit(), sortType);
		Map<String, Object> commonPaginator = new HashMap<String, Object>();
		commonPaginator.put("totalPages", queryProducts.getTotalPages());
		commonPaginator.put("page", pageBound.getPage());
		commonPaginator.put("prePage", pageBound.getPage() -1);
		commonPaginator.put("nextPage", pageBound.getPage() + 1);
		if(pageBound.getPage() == queryProducts.getTotalPages()){
			commonPaginator.put("hasNextPage", false);
		}
		if(pageBound.getPage() ==1){
			commonPaginator.put("hasPrePage", false);
		}else{
			commonPaginator.put("hasPrePage", true);
		}
		model.addAttribute("commonPaginator", commonPaginator);
		model.addAttribute("queryProducts", queryProducts);
		return "product/productList";
	}

	  @RequestMapping(value="addpage",method = RequestMethod.GET)
	    public String addProduct(Model model) {
	        return "product/add";
	    }
	    @RequestMapping(value="saveOrUpdate",method = RequestMethod.POST)
	    public String saveOrUpdate(@RequestParam(value = "name", defaultValue = "")String name, 
	    		@RequestParam(value = "productCode", defaultValue = "")String productCode, 
	    		@RequestParam(value = "queryPrice")float queryPrice, 
	    		@RequestParam(value = "cashFlag", defaultValue = "1")int cashFlag, 
	    		@RequestParam(value = "queryReturnPrice")float queryReturnPrice, 
	    		@RequestParam(value = "queryNoreturnPrice")float queryNoreturnPrice, 
	    		@RequestParam(value = "id", defaultValue = "0")long id,Model model,RedirectAttributes redirectAttributes) {
	    	QueryProduct product = queryProductService.findByProductCode(productCode);
	    	QueryProduct queryProduct = new QueryProduct();
	    	queryProduct.setId(id);
	    	queryProduct.setName(name);
	    	queryProduct.setProductCode(productCode);
	    	queryProduct.setQueryNoreturnPrice(queryNoreturnPrice);
	    	queryProduct.setQueryReturnPrice(queryReturnPrice);
	    	queryProduct.setQueryPrice(queryPrice);
	    	queryProduct.setCashFlag(cashFlag);
	    	if(null != product && (0 == id || product.getId() !=id)){
	    		redirectAttributes.addFlashAttribute("queryProduct", queryProduct);
	    		redirectAttributes.addFlashAttribute("message", "操作失败,产品编号重复,请重新输入!");
	    		return "redirect:/admin/product/addpage";
			}else{
				queryProductService.saveQueryProduct(queryProduct);
		    	if(null != queryProduct.getId()){
		    		redirectAttributes.addFlashAttribute("message", "更新产品" + queryProduct.getName() + "成功");
		    	}else{
		    		redirectAttributes.addFlashAttribute("message", "创建产品" + queryProduct.getName()+ "成功");
		    	}
		    	return "redirect:/admin/product";
			}
	    }
	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("queryProduct", queryProductService.getQueryProduct(id));
		return "product/add";
	}
	
	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		QueryProduct queryProduct = queryProductService.getQueryProduct(id);
		queryProductService.deleteQueryProduct(id);
		redirectAttributes.addFlashAttribute("message", "删除产品" + queryProduct.getName() + "成功");
		return "redirect:/admin/product";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("queryProduct", queryProductService.getQueryProduct(id));
		}
	}
}
