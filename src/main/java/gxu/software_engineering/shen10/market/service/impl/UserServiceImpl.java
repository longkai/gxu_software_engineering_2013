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

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 用户相关服务的实现。
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-18
 */
@Service
@Validated
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {

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
		user.setLastLoginTime(user.getRegisterTime());
		user.setLoginTimes(0);
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
		return userDao.find(id);
	}

	@Override
	@Transactional(readOnly = false)
	public User modify(long uid, boolean isPwd, String s) {
		User user = userDao.find(uid);
		if (isPwd) {
			user.setPassword(Encryptor.MD5(s));
		} else {
			user.setContact(s);
		}
		userDao.merge(user);
		return user;
	}

	@Override
	public List<User> latest(int count) {
		return userDao.list(true, "User.list_latest", null, 0, count);
	}

	@Override
	public List<User> list(long lastUserId, int count) {
		List<User> users = null;
		if (lastUserId == 0) {
			users = userDao.list(true, "User.list_latest", null, 0, count);
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("id", lastUserId);
			users = userDao.list(true, "User.list_latest_more", params, 0, count);
		}
		return users;
	}

	@Override
	public long size() {
		return userDao.size(true, false, null, null);
	}

}
