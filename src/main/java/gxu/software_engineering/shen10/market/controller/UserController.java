package gxu.software_engineering.shen10.market.controller;

import gxu.software_engineering.shen10.market.entity.User;
import gxu.software_engineering.shen10.market.service.UserService;
import static gxu.software_engineering.shen10.market.util.Consts.*;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 卖家操作控制器。
 * 
 * @author longkai
 * @email  im.longkai@gmail.com
 * @since  2013-6-20
 */
@Controller
@Scope("session")
public class UserController {
	
	private static final Logger L = LoggerFactory.getLogger(UserController.class);
	
	@Inject
	private UserService userService;
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(Model model, User user, @RequestParam("pwd") String pwd) {
		L.info("卖家注册：{}", user);
		userService.register(user, pwd);
		model.addAttribute(STATUS, STATUS_OK);
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(
			Model model,
			@RequestParam("account") String account,
			@RequestParam("password") String password) {
		L.info("卖家登陆：account: {}", account);
		User user = userService.login(account, password);
		model.addAttribute(STATUS, STATUS_OK);
		model.addAttribute(USER, user);
		return BAD_REQUEST;
	}
}
