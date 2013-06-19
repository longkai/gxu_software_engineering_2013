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
import static org.hamcrest.CoreMatchers.*;

import java.util.List;

import gxu.software_engineering.shen10.market.entity.Category;
import gxu.software_engineering.shen10.market.entity.Item;
import gxu.software_engineering.shen10.market.entity.User;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring.xml")
@Transactional
public class ItemServiceTest {

	@Inject
	private ItemService itemService;
	
	@Inject
	private UserService userService;
	
	@Inject
	private CategoryService categoryService;
	
	private Item item;
	private User seller;
	private Category category;
	
	@Before
	public void setUp() throws Exception {
		item = new Item();
		seller = userService.profile(1L);
		category = categoryService.view(1L);
		
		item.setName("呵呵");
		item.setDescription("描述描述描述描述描述描述描述描述描述描述描述");
		item.setPrice(99.9F);
	}

//	@Test(expected = RuntimeException.class)
//	public void testAddWithLimit() {
////		注意，这个是通过手动修改数据库的时间来测试的，so。。。
//		itemService.create(item, category.getId(), seller.getId());
//	}
	
	@Test
	public void testModify() {
		itemService.create(item, category.getId(), seller.getId());
		assertThat(item.getId(), notNullValue());
		
		String newName = "newName";
		item.setName(newName);
		assertThat(itemService.modify(item).getName(), is(newName));
	}

	@Test
	public void testClose() {
		itemService.create(item, category.getId(), seller.getId());
		assertThat(item.getId(), notNullValue());
		
		item = itemService.close(true, seller, item.getId());
		assertThat(item.getClosed(), is(true));
	}

	@Test
	public void testOpen() {
		itemService.create(item, category.getId(), seller.getId());
		assertThat(item.getId(), notNullValue());
		
		item = itemService.close(false, seller, item.getId());
		assertThat(item.getClosed(), is(false));
	}

	@Test
	public void testDetail() {
		Item i = itemService.detail(1L);
		assertThat(i, notNullValue());
	}

	@Test
	public void testLatest() {
		List<Item> items = itemService.latest(49);
		assertThat(items.size() >= 2, is(true));
		assertThat(items.get(0).getId() > items.get(1).getId(), is(true));
	}

	@Test
	public void testListLongInt() {
		long target = 4L;
		List<Item> items = itemService.list(target, 50);
		assertThat(items.get(0).getId() < target, is(true));
	}

	@Test
	public void testSizeBoolean() {
		long size = itemService.size(true);
		System.err.println(size);
		assertThat(size, not(0L));
		size = itemService.size(false);
		System.err.println(size);
//		当前数据库是这样，后面就不知道了。。。
//		assertThat(itemService.size(true), is(0L));
	}

	@Test
	public void testSizeLongBoolean() {
//		这里测试的仅仅当前的数据库，so，测试完我就注视掉好了
//		long size = itemService.size(2, true);
//		assertThat(size, is(2L));
//		size = itemService.size(2, false);
//		assertThat(size, is(0L));
	}

	@Test
	public void testListLongIntBooleanLong() {
		long size = itemService.size(category.getId());
//		理由同上！
//		assertThat(size, is(1L));
	}
	
	@Test
	public void testListWithCategory() {
		List<Item> list = itemService.list(category.getId(), 5, 0);
		int size = list.size();
		System.err.println(size);
		assertThat(size >= 1, is(true));
	}

	@Test
	public void testListLongIntLong() {
//		理由同上！而且是否抽出已经测试过了！
		//		List<Item> list = itemService.list(1, 5, true, 0);
//		assertThat(list.size(), is(2));
//		
//		list = itemService.list(1, 5, true, 2);
//		assertThat(list.size(), is(1));
	}

	@Test
	public void testSizeLong() {
		
	}

	@Test
	public void testHot() {
//		故意修改数据库的-.-
//		List<Item> items = itemService.hot(50);
//		assertThat(items.get(0).getId(), is(2L));
	}

}
