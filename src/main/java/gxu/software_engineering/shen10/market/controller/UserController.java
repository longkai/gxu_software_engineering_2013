package gxu.software_engineering.shen10.market.controller;

import static gxu.software_engineering.shen10.market.util.Consts.BAD_REQUEST;
import static gxu.software_engineering.shen10.market.util.Consts.LATEST;
import static gxu.software_engineering.shen10.market.util.Consts.LATEST_MORE;
import static gxu.software_engineering.shen10.market.util.Consts.REFRESH;
import static gxu.software_engineering.shen10.market.util.Consts.STATUS;
import static gxu.software_engineering.shen10.market.util.Consts.STATUS_OK;
import static gxu.software_engineering.shen10.market.util.Consts.USER;
import static gxu.software_engineering.shen10.market.util.Consts.USERS;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import gxu.software_engineering.shen10.market.entity.User;
import gxu.software_engineering.shen10.market.service.UserService;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@RequestMapping(value = "/register", method = POST)
	public String register(Model model, User user, @RequestParam("pwd") String pwd) {
		L.info("卖家注册：{}", user);
		userService.register(user, pwd);
		model.addAttribute(STATUS, STATUS_OK);
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/login", method = GET)
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
	
	@RequestMapping(value = "/users/{uid}", method = GET)
	public String profile(Model model, @PathVariable("uid") long uid) {
		User user = userService.profile(uid);
		model.addAttribute(STATUS, STATUS_OK);
		model.addAttribute(USER, user);
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/users/{uid}/modify", method = PUT)
	public String modify(
			Model model,
			@PathVariable("uid") long uid,
			@RequestParam("type") boolean type,
			@RequestParam("value") String value) {
		L.info("用户：{} 请求修改信息， type: {}, 值：{}", uid, type, value);
		User u = userService.modify(uid, type, value);
		model.addAttribute(STATUS, STATUS_OK);
		model.addAttribute(USER, u);
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/users", method = GET)
	public String list(
			Model model,
			@RequestParam("type") int type,
			@RequestParam("count") int count,
			@RequestParam(value = "last_id", defaultValue = "0") long lastId) {
		List<User> users = null;
		switch (type) {
		case LATEST:
			users = userService.latest(count);
			break;
		case LATEST_MORE:
			users = userService.list(lastId, count);
			break;
		case REFRESH:
			users = userService.list(lastId, count);
			break;
		default:
			throw new IllegalArgumentException("对不起，没有这个选项！");
		}
		model.addAttribute(USERS, users);
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/users/block", method = PUT)
	public String block(Model model, @RequestParam("uid") long uid, @RequestParam("block") boolean blocked) {
		L.debug("isblock: {}", blocked);
		User u = userService.block(uid, blocked);
		model.addAttribute(STATUS, STATUS_OK);
		model.addAttribute(USER, u);
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/users/{uid}/alter", method = PUT)
	public String alter(Model model,@PathVariable("uid") long uid,
			@RequestParam("nick") String nick,
			@RequestParam("account") String account,
			@RequestParam("contact") String contact) {
		L.info("修正用户信息：uid：{}", uid);
		User u = userService.alter(uid, account, nick, contact);
		model.addAttribute(STATUS, STATUS_OK);
		model.addAttribute(USER, u);
		return BAD_REQUEST;
	}
	
}
