
package com.pactera.pds.u2.commerce.service.account;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;

import com.pactera.pds.u2.commerce.entity.User;
import com.pactera.pds.u2.commerce.repository.jpa.TaskDao;
import com.pactera.pds.u2.commerce.repository.jpa.UserDao;
import com.pactera.pds.u2.commerce.service.ServiceException;
import com.pactera.pds.u2.commerce.service.account.ShiroDbRealm.ShiroUser;
import com.pactera.pds.u2.commerce.utils.UuidUtil;

/**
 * 用户管理类.
 * 
 */
// Spring Service Bean的标识.
@Component
@Transactional
public class AccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(AccountService.class);

	private UserDao userDao;
	private TaskDao taskDao;
	private Clock clock = Clock.DEFAULT;

	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}
	
	public Page<User> getAllUser(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		return userDao.findAll(pageRequest);
	}
	
	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("loginName".equals(sortType)) {
			sort = new Sort(Direction.ASC, "loginName");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}	
	
	public User getUser(Long id) {
		return userDao.findOne(id);
	}

	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}
	
	public boolean validatePassword(Long id,String oldPassword ){
		User user= getUser(id);
//	    byte[] salt = Digests.generateSalt(SALT_SIZE);
     byte[] hashPassword = Digests.sha1(oldPassword.getBytes(), Encodes.decodeHex(user.getSalt()), HASH_INTERATIONS);
     if (user.getPassword().equals(Encodes.encodeHex(hashPassword))) {
         return true;
     }
	    return false;
	}

	public void registerUser(User user) {
		entryptPassword(user);
		/*user.setRoles("user");*/
		user.setRegisterDate(clock.getCurrentDate());

		userDao.save(user);
	}

	public void updateUser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		if (StringUtils.isBlank(user.getUuid()) || user.getId()==null) {
			user.setUuid(UuidUtil.getUuid());
		}
		userDao.save(user);
	}

	public void deleteUser(Long id) {
		if (isSupervisor(id)) {
			logger.warn("操作员{}尝试删除超级管理员用户", getCurrentUserName());
			throw new ServiceException("不能删除超级管理员用户");
		}
		userDao.delete(id);
		taskDao.deleteByUserId(id);

	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		return id == 1;
	}

	/**
	 * 取出Shiro中的当前用户LoginName.
	 */
	private String getCurrentUserName() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.loginName;
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setTaskDao(TaskDao taskDao) {
		this.taskDao = taskDao;
	}

	public void setClock(Clock clock) {
		this.clock = clock;
	}
	public void save (User user){
	    userDao.save(user);
	}
}
