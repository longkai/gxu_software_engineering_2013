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

import gxu.software_engineering.shen10.market.entity.Category;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 类别服务接口
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 */
public interface CategoryService {

	/**
	 * 添加新的类别，类别不允许重名。
	 * @return 成功添加的类别。
	 */
	Category add(Category category);

	/**
	 * 修改类别。
	 * @return 若成功，返回修改后的类别，否则抛出异常。
	 */
	Category modify(@Min(1) long categoryId, @NotBlank String name, @NotBlank String description);

	/**
	 * 根据类别的id，查看该类别的详细信息。
	 * @param 类别的id。
	 * @return 若存在，则返回该类别，否则抛出异常。
	 */
	@NotNull(message = "您所查找的类别不存在！")
	Category view(@Min(1) long categoryId);

	/**
	 * 查看所有的类别。
	 * @return 返回类别的列表。
	 */
	List<Category> list();

	/**
	 * 抓取所有的类别数量。
	 */
	long size();
	
	/**
	 * 按照类别名字搜索.
	 * @param name
	 * @param count
	 */
	Map<String, Object> search(String name, int count);

}
