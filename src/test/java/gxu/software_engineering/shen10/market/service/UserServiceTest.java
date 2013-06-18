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

import static org.junit.Assert.*;

import javax.inject.Inject;

import gxu.software_engineering.shen10.market.entity.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.stereotype.Service;
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
	public void testLogin() {
		fail("Not yet implemented");
	}

	@Test
	public void testProfile() {
		fail("Not yet implemented");
	}

	@Test
	public void testModify() {
		fail("Not yet implemented");
	}

	@Test
	public void testLatest() {
		fail("Not yet implemented");
	}

	@Test
	public void testList() {
		fail("Not yet implemented");
	}

	@Test
	public void testSize() {
		fail("Not yet implemented");
	}

}
