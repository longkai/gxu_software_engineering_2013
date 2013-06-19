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
import static org.junit.Assert.*;
import gxu.software_engineering.shen10.market.entity.Category;

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
 * @since  2013-6-19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring.xml")
@Transactional
public class CategoryServiceTest {

	private static final Logger L = LoggerFactory
			.getLogger(CategoryServiceTest.class);
	
	@Inject
	private CategoryService categoryService;
	
	private Category c;
	
	@Before
	public void setUp() throws Exception {
		c = new Category();
		c.setName("新类别");
		c.setDescription("描述");
	}
	
	@Test
	public void testAdd() {
		categoryService.add(c);
		assertThat(c, notNullValue());
	}

	@Test(expected = RuntimeException.class)
	public void testAddWithExistName() {
		categoryService.add(c);
		
		Category tmp = new Category();
		tmp.setName(c.getName());
		tmp.setDescription(c.getDescription());
		
		categoryService.add(tmp);
	}

	@Test(expected = RuntimeException.class)
	public void testModifyWithExsitName() {
		categoryService.add(c);
		Category tmp = categoryService.view(1L);
		
		categoryService.modify(c.getId(), tmp.getName(), tmp.getDescription());
	}
	
	@Test
	public void testModify() {
		categoryService.add(c);
		L.info("category before: {}", c);
		categoryService.modify(c.getId(), "hello", c.getDescription());
		L.info("category after: {}", c);
	}

	@Test(expected = RuntimeException.class)
	public void testViewWitnNonExistId() {
		categoryService.view(100000L);
	}
	
	@Test
	public void testView() {
		Category c = categoryService.view(1L);
		assertThat(c, notNullValue());
		L.info("category: {}", c);
	}

	@Test
	public void testList() {
		assertThat(categoryService.list().size() >= 3, is(true));
	}

	@Test
	public void testSize() {
		fail("Not yet implemented");
	}

}
