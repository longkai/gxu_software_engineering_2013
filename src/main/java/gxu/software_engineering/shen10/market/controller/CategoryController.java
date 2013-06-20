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
package gxu.software_engineering.shen10.market.controller;

import static gxu.software_engineering.shen10.market.util.Consts.BAD_REQUEST;
import static gxu.software_engineering.shen10.market.util.Consts.CATEGORIES;
import static gxu.software_engineering.shen10.market.util.Consts.CATEGORY;
import static gxu.software_engineering.shen10.market.util.Consts.STATUS;
import static gxu.software_engineering.shen10.market.util.Consts.STATUS_OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import gxu.software_engineering.shen10.market.entity.Category;
import gxu.software_engineering.shen10.market.service.CategoryService;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 类别控制器。
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-21
 */
@Controller
@Scope("session")
public class CategoryController {
	
	private static final Logger L = LoggerFactory.getLogger(CategoryController.class);
	
	@Inject
	private CategoryService categoryService;
	
	@RequestMapping(value = "/categories/add", method = POST) 
	public String add(Model model, @Valid Category category, BindingResult result) {
		if (result.hasFieldErrors()) {
			throw new IllegalArgumentException(result.getFieldError().getDefaultMessage());
		}
		model.addAttribute(STATUS, STATUS_OK);
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/categories/{cid}", method = GET)
	public String view(Model model, @PathVariable("cid") long cid) {
		Category c = categoryService.view(cid);
		model.addAttribute(CATEGORY, c);
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/categories/{cid}/modify", method = PUT)
	public String modify(Model model, @PathVariable("cid") long cid, Category category) {
		L.info("修改类别信息：{}", category);
		Category c = categoryService.modify(cid, category.getName(), category.getDescription());
		model.addAttribute(CATEGORY, c);
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/categories", method = GET)
	public String list(Model model) {
		List<Category> list = categoryService.list();
		model.addAttribute(CATEGORIES, list);
		return BAD_REQUEST;
	}

}
