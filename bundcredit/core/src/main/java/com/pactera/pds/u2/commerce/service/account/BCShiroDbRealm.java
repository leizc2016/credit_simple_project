
package com.pactera.pds.u2.commerce.service.account;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.pactera.pds.u2.commerce.entity.User;
import com.pactera.pds.u2.commerce.entity.mybatis.BCUser;
import com.pactera.pds.u2.commerce.service.insdatamgr.InstitutionService;

import org.springside.modules.utils.Encodes;

import com.google.common.base.Objects;

public class BCShiroDbRealm extends AuthorizingRealm {

	protected BCAccountService accountService;
	protected InstitutionService insService;
	
	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		BCUser user = accountService.findUserByLoginName(token.getUsername());
		if(user.getAllow() == 0){
		    throw new AccountException("账户已被禁用，请联系管理员！");
		}
		if (user != null) {
			byte[] salt = Encodes.decodeHex(user.getSalt());
			String insCode=user.getInsCode();
			return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getLoginName(), user.getName(),insCode,insService.getByInscode(insCode).getName()),
					user.getPassword(), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = accountService.findUserByLoginName(shiroUser.loginName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(user.getRoleList());
		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(AccountService.HASH_ALGORITHM);
		matcher.setHashIterations(AccountService.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	public void setAccountService(BCAccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Long id;
		public String loginName;
		public String name;
		public String insCode;
		public String insName;
		
		public ShiroUser(Long id, String loginName, String name,String code,String insName) {
			this.id = id;
			this.loginName = loginName;
			this.name = name;
			this.insCode=code;
			this.insName=insName;
		}

		public String getName() {
			return name;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null) {
					return false;
				}
			} else if (!loginName.equals(other.loginName)) {
				return false;
			}
			return true;
		}

        
        public Long getId() {
            return id;
        }

        
        public String getLoginName() {
            return loginName;
        }

        
        public String getInsCode() {
            return insCode;
        }

        
        public void setName(String name) {
            this.name = name;
        }

        
        public String getInsName() {
            return insName;
        }

        
        public void setInsName(String insName) {
            this.insName = insName;
        }
	}

    
    public InstitutionService getInsService() {
        return insService;
    }

    
    public void setInsService(InstitutionService insService) {
        this.insService = insService;
    }
}
