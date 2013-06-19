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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import gxu.software_engineering.shen10.market.entity.Category;
import gxu.software_engineering.shen10.market.repository.CategoryDao;
import gxu.software_engineering.shen10.market.service.CategoryService;
import gxu.software_engineering.shen10.market.util.Assert;

/**
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-19
 */
@Service
@Validated
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Throwable.class)
public class CategoryServiceImpl implements CategoryService {

	@Inject
	private CategoryDao categoryDao;
	
	@Override
	@Transactional(readOnly = false)
	public Category add(Category category) {
//		验证类别名是否已经存在
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", category.getName());
		Category c = categoryDao.find("Category.name", params);
		if (c != null) {
			throw new RuntimeException("对不起，该类别已经存在，无法重复添加！");
		}
		category.setAddedTime(new Date());
		categoryDao.persist(category);
		return category;
	}

	@Override
	@Transactional(readOnly = false)
	public Category modify(long categoryId, String name, String description) {
//		验证类别是否存在
		Category c = categoryDao.find(categoryId);
		Assert.notNull(c, "对不起，你所查找的类别不存在！");
//		验证类别名称是否存在
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", name);
		Category tmp = categoryDao.find("Category.name", params);
		if (tmp != null && !tmp.equals(c)) {
			throw new RuntimeException("对不起，该类别已经存在，无法重复添加！");
		}
//		更新数据库
		c.setName(name);
		c.setDescription(description);
		categoryDao.merge(c);
		return c;
	}

	@Override
	public Category view(long categoryId) {
		return categoryDao.find(categoryId);
	}

	@Override
	public List<Category> list() {
		return categoryDao.list("Category.list", null, 0, Integer.MAX_VALUE);
	}

	@Override
	public long size() {
		return categoryDao.size();
	}

}
