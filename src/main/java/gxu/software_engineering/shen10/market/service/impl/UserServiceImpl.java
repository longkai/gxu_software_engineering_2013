/*
 * Copyright 2013 Department of Computer Science and Technology, Guangxi University
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package gxu.software_engineering.shen10.market.service.impl;

import gxu.software_engineering.shen10.market.entity.Category;
import gxu.software_engineering.shen10.market.entity.User;
import gxu.software_engineering.shen10.market.repository.UserDao;
import gxu.software_engineering.shen10.market.service.UserService;
import gxu.software_engineering.shen10.market.util.Assert;
import gxu.software_engineering.shen10.market.util.Encryptor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.hibernate.validator.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-18
 */
@Service
@Validated
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {

	private static final Logger L = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Inject
	private UserDao userDao;
	
	@Override
	@Transactional(readOnly = false)
	public User register(User user, String confirmedPassword) {
//		验证密码是否相同
		Assert.equals(user.getPassword(), confirmedPassword, "两次密码不相同！");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", user.getAccount());
//		严重account是否存在
		User u = userDao.find("User.account", params);
		if (u != null) {
			throw new RuntimeException("对不起，account " + user.getAccount() + " 已经存在！");
		}
//		验证nick是否存在
		params.clear();
		params.put("nick", user.getNick());
		u = userDao.find("User.nick", params);
		if (u != null) {
			throw new RuntimeException("对不起，nick " + user.getNick() + " 已经存在！");
		}
//		md5加密密码
		user.setPassword(Encryptor.MD5(confirmedPassword));
		user.setRegisterTime(new Date());
//		验证字段并持久化到数据库
		userDao.persist(user);
		return user;
	}

	@Override
	public User login(String account, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("account", account);
		params.put("password", Encryptor.MD5(password));
		return userDao.find("User.login", params);
	}

	@Override
	public User profile(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User modify(Category user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> latest(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> list(long lastUserId, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return 0;
	}

}
