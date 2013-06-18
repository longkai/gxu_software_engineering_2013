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

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.*;

import java.util.List;

import gxu.software_engineering.shen10.market.entity.User;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring.xml")
@Transactional
public class UserServiceTest {
	
	private static final Logger L = LoggerFactory.getLogger(UserServiceTest.class);

	@Inject
	private UserService userService;
	
	private User u;
	
	@Before
	public void setUp() throws Exception {
		u = new User();
		u.setAccount("test321");
		u.setContact("14795633343");
		u.setNick("我是谁？");
		u.setPassword("123456");
		u.setRealName("拉登");
	}

	@Test(expected = RuntimeException.class)
	public void testRegisterDifferentPwd() {
		userService.register(u, "123");
	}
	
	@Test(expected = RuntimeException.class)
	public void testRegisterWithExistAccount() {
		u.setAccount("longkai");
		userService.register(u, u.getPassword());
	}
	
	@Test(expected = RuntimeException.class)
	public void testRegisterWithExistNick() {
		u.setNick("爱因斯坦的狗");
		userService.register(u, u.getPassword());
	}

	@Test(expected = RuntimeException.class)
	public void testRegisterWithInvalidField() {
//		字段由于使用了javax.validation，所以测一个也就够了
		u.setRealName("");
		userService.register(u, u.getPassword());
	}
	
	@Test
	public void testRegister() {
//		字段由于使用了javax.validation，所以测一个也就够了
		userService.register(u, u.getPassword());
	}
	
	@Test
	public void testLoginWithRightAccountAndPwd() {
		String rawPwd = u.getPassword();
		User user = userService.register(u, rawPwd);
		assertThat(user, notNullValue());
		assertEquals(user, u);
		L.info("u: {}", u);
		L.info("user:{}", user);
		
		User login = userService.login(u.getAccount(), rawPwd);
		assertThat(login, notNullValue());
	}
	
	@Test(expected = RuntimeException.class)
	public void testLoginWithWrongAccountAndPwd() {
		User login = userService.login("abc", "321");
		assertThat(login, nullValue());
	}

	@Test
	public void testProfile() {
		User u = userService.profile(1L);
		assertThat(u, notNullValue());
	}
	
	@Test(expected = RuntimeException.class)
	public void testProfileWithInvalidValue() {
		userService.profile(1000L);
	}

	@Test
	public void testModify() {
		userService.register(u, u.getPassword());
		String newContact = "14795633333";
		u.setContact(newContact);
		assertThat(userService.modify(u).getContact(), is(newContact));
	}

	@Test(expected = RuntimeException.class)
	public void testLatestWithRongCount() {
		userService.latest(100);
	}
	
	@Test
	public void testLatest() {
		List<User> users = userService.latest(10);
		assertThat(users.size() > 2, is(true));
	}

	@Test
	public void testListWithRefresh() {
		List<User> users = userService.list(0, 10);
//		这里的测试其实使用了小技巧 -:)
		assertThat(users.get(0).getId() >= users.size(), is(true));
	}
	
	@Test
	public void testListMore() {
		List<User> users = userService.list(4, 10);
		assertThat(users.get(0).getId() < 4, is(true));
	}

	@Test
	public void testSize() {
		fail("Not yet implemented");
	}

}
