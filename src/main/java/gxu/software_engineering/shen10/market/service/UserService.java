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
package gxu.software_engineering.shen10.market.service;

import gxu.software_engineering.shen10.market.entity.User;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 用户服务接口
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 */
public interface UserService {

	/**
	 * 用户注册，必须提供必要的注册信息。
	 * @return 成功注册后的用户。
	 */
	User register(User user, String confirmedPassword);

	/**
	 * 提供正确的登录信息，返回授权用户。
	 * @return 已经注册但未被冻结的账户。
	 */
	@NotNull(message = "用户名或者密码错误！")
	User login(@NotBlank String account, @NotBlank String password);

	/**
	 * 查看用户的信息。
	 * @return 给定id的用户。
	 */
	@NotNull(message = "您所查找的用户不存在！")
	User profile(@Min(1) long id);

	/**
	 * 修改用户信息。
	 * @return 修改成功后的用户。
	 */
	User modify(@Min(1) long uid, boolean isPwd, @NotBlank String s);

	/**
	 * 最新注册的用户列表。
	 * @param count 需要多少条记录。
	 */
	List<User> latest(@Min(1) @Max(50) int count);

	/**
	 * 查看更多用户。
	 * @param lastUserId 上一个列表项目的用户标识，0表示刷新当前列表。
	 * @param count 需要多少条记录。
	 */
	List<User> list(@Min(0) long lastUserId, @Min(1) @Max(50) int count);

	/**
	 * 返回所有的卖家数。
	 */
	long size();

}
