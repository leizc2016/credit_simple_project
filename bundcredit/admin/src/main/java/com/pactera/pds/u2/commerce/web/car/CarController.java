
package com.pactera.pds.u2.commerce.web.car;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.google.common.collect.Maps;
import com.pactera.pds.u2.commerce.entity.Car;
import com.pactera.pds.u2.commerce.entity.User;
import com.pactera.pds.u2.commerce.service.account.ShiroDbRealm.ShiroUser;
import com.pactera.pds.u2.commerce.service.car.CarService;

/**
 * Task绠＄悊鐨凜ontroller, 浣跨敤Restful椋庢牸鐨刄rls:
 * 
 * List page : GET /task/
 * Create page : GET /task/create
 * Create action : POST /task/create
 * Update page : GET /task/update/{id}
 * Update action : POST /task/update
 * Delete action : GET /task/delete/{id}
 * 
 */
@Controller
@RequestMapping(value = "/car")
public class CarController {

	private static final String PAGE_SIZE = "3";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("title", "标题");
	}

	@Autowired
	private CarService carService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();

		Page<Car> cars = carService.getUserCar(userId, searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("cars", cars);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 灏嗘悳绱㈡潯浠剁紪鐮佹垚瀛楃涓诧紝鐢ㄤ簬鎺掑簭锛屽垎椤电殑URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "car/carList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("car", new Car());
		model.addAttribute("action", "create");
		return "car/carForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Car newCar, RedirectAttributes redirectAttributes) {
		User user = new User(getCurrentUserId());
		newCar.setUser(user);

		carService.saveCar(newCar);
		redirectAttributes.addFlashAttribute("message", "创建任务成功");
		return "redirect:/car/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("car",carService.getCar(id));
		model.addAttribute("action", "update");
		return "car/carForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("car") Car car, RedirectAttributes redirectAttributes) {
		carService.saveCar(car);
		redirectAttributes.addFlashAttribute("message", "更新任务成功");
		return "redirect:/car/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		carService.deleteCar(id);
		redirectAttributes.addFlashAttribute("message", "删除任务成功");
		return "redirect:/car/";
	}

	/**
	 * 鎵�湁RequestMapping鏂规硶璋冪敤鍓嶇殑Model鍑嗗鏂规硶, 瀹炵幇Struts2 Preparable浜屾閮ㄥ垎缁戝畾鐨勬晥鏋�鍏堟牴鎹甪orm鐨刬d浠庢暟鎹簱鏌ュ嚭Task瀵硅薄,鍐嶆妸Form鎻愪氦鐨勫唴瀹圭粦瀹氬埌璇ュ璞′笂銆�
	 * 鍥犱负浠卽pdate()鏂规硶鐨刦orm涓湁id灞炴�锛屽洜姝や粎鍦╱pdate鏃跺疄闄呮墽琛�
	 */
	@ModelAttribute
	public void getCar(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("car", carService.getCar(id));
		}
	}

	

	    @RequestMapping(value="/multipleUpload" , method=RequestMethod.GET)
	    public String multiUpload(){
	    	return "car/upload";
	    }
	    @RequestMapping(value="/multipleSave", method=RequestMethod.POST )
	    public @ResponseBody String multipleSave(@RequestParam("file") MultipartFile[] files){
	    	String fileName = null;
	    	String msg = "";
	    	if (files != null && files.length >0) {
	    		for(int i =0 ;i< files.length; i++){
		            try {
		                fileName = files[i].getOriginalFilename();
		                if("".equals(fileName))continue;
		                byte[] bytes = files[i].getBytes();
		                BufferedOutputStream buffStream = 
		                        new BufferedOutputStream(new FileOutputStream(new File("e:/cp/" + fileName)));
		                buffStream.write(bytes);
		                buffStream.close();
		                msg += "You have successfully uploaded " + fileName +"<br/>";
		            } catch (Exception e) {
		                return "You failed to upload " + fileName + ": " + e.getMessage() +"<br/>";
		            }
	    		}
	    		return msg;
	        } else {
	            return "Unable to upload. File is empty.";
	        }
	    }
	    
	    
	    @RequestMapping(value="/multipleSavebak", method=RequestMethod.POST )
	    public @ResponseBody String multipleSavebak(@RequestParam("file") MultipartFile[] files){
	    	String fileName = null;
	    	String msg = "";
	    	if (files != null && files.length >0) {
	    		for(int i =0 ;i< files.length; i++){
		            try {
		                fileName = files[i].getOriginalFilename();
		                if("".equals(fileName))continue;
		                byte[] bytes = files[i].getBytes();
		                BufferedOutputStream buffStream = 
		                        new BufferedOutputStream(new FileOutputStream(new File("e:/cp/" + fileName)));
		                buffStream.write(bytes);
		                buffStream.close();
		                msg += "You have successfully uploaded " + fileName +"<br/>";
		            } catch (Exception e) {
		                return "You failed to upload " + fileName + ": " + e.getMessage() +"<br/>";
		            }
	    		}
	    		return msg;
	        } else {
	            return "Unable to upload. File is empty.";
	        }
	    }

	
	/**
	 * 鍙栧嚭Shiro涓殑褰撳墠鐢ㄦ埛Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
