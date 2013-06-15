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
package gxu.software_engineering.shen10.market.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import gxu.software_engineering.shen10.market.entity.User;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 对dao的测试，注意，虽然只是测试了一个接口，但是我们所有的dao实现均是继承了一个基类，故只测一个即可 :-)
 * 
 * 由于使用了javax.validation api, 所以存储时必须是符合验证的值！
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring.xml")
@Transactional
public class UserDaoTest {

	@Inject
	private UserDao dao;
	
	private User user;
	private User dummyUser;
	
	@Before
	public void setUp() throws Exception {
		user = new User();
		user.setAccount("longkai");
		user.setContact("14795633343");
		user.setNick("爱因斯坦的狗");
		user.setPassword("123456");
		user.setRealName("龙凯");
		user.setRegisterTime(new Date());
		
//		放置另一个实体，辅助测试
		dummyUser = new User();
		dummyUser.setAccount("dummy_test");
		dummyUser.setContact("14795633343");
		dummyUser.setNick("爱因斯坦的狗");
		dummyUser.setPassword("654321");
		dummyUser.setRealName("test");
		dummyUser.setRegisterTime(new Date());
		
//		保存一个实体，供接下来所有的方法共享一个已经持久化的实体。
		dao.persist(user);
	}

	@Test
	public void testPersist() {
		assertThat(dummyUser.getId(), nullValue());
		dao.persist(dummyUser);
		assertThat(dummyUser.getId(), notNullValue());
	}

	@Test
	public void testRemove() {
		assertThat(user.getId(), notNullValue());
		long id = user.getId();
		dao.remove(user);
		assertThat(dao.find(id), nullValue());
	}

	@Test
	public void testMerge() {
		String newName = "hello";
		user.setNick(newName);
		dao.merge(user);
		assertThat(dao.find(user.getId()).getNick(), is(newName));
	}

	@Test
	public void testFindSerializable() {
		Long id = user.getId();
		User tmp = dao.find(id);
		assertEquals(user, tmp);
	}

	@Test
	public void testSize() {
		dao.persist(dummyUser);
		assertThat(dao.size(), is(2L));
	}

	@Test
	public void testFindStringMapOfStringObject() {
//		not yet has the named query! but the test passed because i used it in last a few projects!
	}

	@Test
	public void testList() {
//		not yet has the named query! but the test passed because i used it in last a few projects!
	}

}
