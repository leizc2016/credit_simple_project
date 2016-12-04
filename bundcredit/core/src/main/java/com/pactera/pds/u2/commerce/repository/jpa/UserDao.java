
package com.pactera.pds.u2.commerce.repository.jpa;

import com.pactera.pds.u2.commerce.entity.User;
import com.pactera.pds.u2.commerce.repository.jpa.base.GlobalRepository;

public interface UserDao extends GlobalRepository<User, Long> {
	User findByLoginName(String loginName);
}
