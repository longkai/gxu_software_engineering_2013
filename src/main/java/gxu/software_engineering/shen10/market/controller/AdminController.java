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

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import gxu.software_engineering.shen10.market.entity.Admin;
import gxu.software_engineering.shen10.market.service.AdminService;
import gxu.software_engineering.shen10.market.service.CategoryService;
import gxu.software_engineering.shen10.market.service.ItemService;
import gxu.software_engineering.shen10.market.service.RecordService;
import gxu.software_engineering.shen10.market.service.UserService;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.web.bind.annotation.RequestMethod.*;
import static gxu.software_engineering.shen10.market.util.Consts.*;

/**
 * 简单的管理员控制器。
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-7-21
 */
@Controller
@RequestMapping("/admin/")
@Scope("session")
public class AdminController {

	@Inject
	private ItemService itemService;
	
	@Inject
	private CategoryService categoryService;
	
	@Inject
	private RecordService recordService;
	
	@Inject
	private UserService userService;
	
	@Inject
	private AdminService adminService;
	
	@RequestMapping(value = "login", method = GET)
	public String login() {
		return "login";
	}
	
	@RequestMapping(value = "_login", method = GET)
	public String login(
			Model model,
			HttpServletRequest request,
			@RequestParam("account") String account,
			@RequestParam("password") String password) {
//		if (result.hasErrors()) {
//			List<ObjectError> errors = result.getAllErrors();
//			String msg = "您输入的信息有误！\n";
//			for (int i = 0; i < errors.size(); i++) {
//				msg += errors.get(i).getDefaultMessage() + "\n";
//			}
//			throw new IllegalArgumentException(msg);
//		}
		Admin admin = adminService.login(account, password);
		request.getSession().setAttribute("admin", admin);
		model.addAttribute(STATUS, STATUS_OK);
		return BAD_REQUEST;
	}
}
