/**
 * 
 */
package com.prax.sample.user.service;

import org.springframework.transaction.annotation.Transactional;

import com.prax.framework.base.service.ServiceImpl;
import com.prax.sample.user.entity.User;

/**
 * @author Huanan
 * 
 */
@Transactional
public class UserServiceImpl extends ServiceImpl<User> implements UserService {

}
