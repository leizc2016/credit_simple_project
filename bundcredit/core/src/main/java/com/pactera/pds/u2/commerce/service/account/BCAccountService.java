
package com.pactera.pds.u2.commerce.service.account;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pactera.pds.u2.commerce.entity.User;
import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.repository.mybatis.BCUserMybatisDao;
import com.pactera.pds.u2.commerce.utils.UuidUtil;

import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Clock;
import org.springside.modules.utils.Encodes;


/**
 * 用户管理类.
 * 
 */
// Spring Service Bean的标识.
@Component("bcAccountService")
@Transactional
public class BCAccountService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(BCAccountService.class);

	private BCUserMybatisDao userDao;
	private Clock clock = Clock.DEFAULT;



	public User getUser(Long id) {
		return userDao.get(id);
	}

    public BCUser getBcUser(Long id) {
        return userDao.get(id);
    }
    //更新密码（loginname）
    public void updatePassword(BCUser bcUser){
        if (StringUtils.isNotBlank(bcUser.getPlainPassword())) {
            entryptPassword(bcUser);
        }
        userDao.update(bcUser);
    }
    public void forbiddenUser(Map param){
        userDao.forbiddenUser(param);
    }
    
    //更新所有字段（ID）
    public void updateuser(BCUser bcUser){
        if (StringUtils.isNotBlank(bcUser.getPlainPassword())) {
            entryptPassword(bcUser);
        }
        userDao.updateuser(bcUser);
    }
    
    private void entryptPassword(BCUser user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }
    
    public void save(BCUser bcUser) {
        if (StringUtils.isNotBlank(bcUser.getPlainPassword())) {
            entryptPassword(bcUser);
        }
        bcUser.setUuid(UuidUtil.getUuid());
         userDao.save(bcUser);
    }
    public void delete(long id) {
        
         userDao.delete(id);
    }
	public BCUser findUserByLoginName(String loginName) {
		return userDao.findUserByLoginName(loginName);
	}
	
	public BCUser findUserByUUID(String UUID) {
		return userDao.findUserByUUID(UUID);
	}
	public List<BCUser> getAllUser(Map<String, Object> map) {
//	       "ora_bundcredit"
	    return (List<BCUser>) userDao.findAllBcUser(map);
	}
	
	public List<BCUser> getAllNoneAdminUsers(Map<String, Object> map) {
//	       "ora_bundcredit"
	    return (List<BCUser>) userDao.findAllNoneAdminBcUser(map);
	}

	@Autowired
	public void setUserDao(BCUserMybatisDao userDao) {
		this.userDao = userDao;
	}
	public void setClock(Clock clock) {
		this.clock = clock;
	}
	public boolean validatePassword(Long id,String oldPassword ){
	       BCUser user= userDao.get(id);
//	    byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(oldPassword.getBytes(), Encodes.decodeHex(user.getSalt()), HASH_INTERATIONS);
        if (user.getPassword().equals(Encodes.encodeHex(hashPassword))) {
            return true;
        }
	    return false;
	}
/*	public static void main(String[] args) {
	    byte[] hashPassword = Digests.sha1("admin".getBytes(), Encodes.decodeHex("1051b6ec4401b1a3"), HASH_INTERATIONS);
        System.out.println(Encodes.encodeHex(hashPassword)+"111111111111111");
        
    }*/
}
