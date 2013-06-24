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
import static gxu.software_engineering.shen10.market.util.Consts.CLOSED_ITEMS;
import static gxu.software_engineering.shen10.market.util.Consts.ITEM;
import static gxu.software_engineering.shen10.market.util.Consts.ITEMS;
import static gxu.software_engineering.shen10.market.util.Consts.LATEST;
import static gxu.software_engineering.shen10.market.util.Consts.LATEST_MORE;
import static gxu.software_engineering.shen10.market.util.Consts.LIST_BY_CATEGORY;
import static gxu.software_engineering.shen10.market.util.Consts.LIST_BY_HOT;
import static gxu.software_engineering.shen10.market.util.Consts.LIST_BY_USER;
import static gxu.software_engineering.shen10.market.util.Consts.REFRESH;
import static gxu.software_engineering.shen10.market.util.Consts.STATUS;
import static gxu.software_engineering.shen10.market.util.Consts.STATUS_OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import gxu.software_engineering.shen10.market.entity.Item;
import gxu.software_engineering.shen10.market.service.ItemService;

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
 * 物品控制器。
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-21
 */
@Controller
@Scope("session")
public class ItemController {

	private static final Logger L = LoggerFactory.getLogger(ItemController.class);
	
	@Inject
	private ItemService itemService;
	
	@RequestMapping(value = "/items/add", method = POST)
	public String add(Model model, Item item,
			@RequestParam("cid") long cid, @RequestParam("uid") long uid) {
		L.info("新建物品: {}", item);
		Item i = itemService.create(item, cid, uid);
		model.addAttribute(STATUS, STATUS_OK);
		model.addAttribute(ITEM, i);
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/items/{id}/modify", method = PUT)
	public String modify(
			Model model,
			@PathVariable("id") long id,
			@RequestParam("uid") long uid,
			@RequestParam("cid") long cid,
			Item item) {
		L.info("修改物品信息: {}", item);
		item.setId(id);
		Item i = itemService.modify(item, cid, uid);
		model.addAttribute(STATUS, STATUS_OK);
		model.addAttribute(ITEM, i);
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/items/{id}", method = GET)
	public String detail(Model model, @PathVariable("id") long id) {
		model.addAttribute(ITEM, itemService.detail(id));
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/items/{id}/{open}", method = PUT)
	public String close(
			Model model,
			@PathVariable("id") long id,
			@RequestParam("uid") long uid,
			@PathVariable("open") String type) {
		L.info("{} 物品id：{}", type, id);
		Item i = null;
		if (type.equals("open")) {
			i = itemService.close(false, uid, id);
		} else if (type.equals("close")) {
			i = itemService.close(true, uid, id);
		} else {
			throw new IllegalArgumentException("对不起，没有这个选项！"); 
		}
		model.addAttribute(STATUS, STATUS_OK);
		model.addAttribute(ITEM, i);
		return BAD_REQUEST;
	}
	
	@RequestMapping(value = "/items", method = GET)
	public String list(
			Model model,
			@RequestParam("type") int type,
			@RequestParam("count") int count,
			@RequestParam(value = "last_id", defaultValue = "0") long lastId,
			@RequestParam(value = "uid", defaultValue = "0") long uid,
			@RequestParam(value = "cid", defaultValue = "0") long cid,
			@RequestParam(value = "deal", defaultValue = "0") boolean deal) {
		List<Item> items = null;
		switch (type) {
		case LATEST:
			items = itemService.latest(count);
			break;
		case LATEST_MORE:
			items = itemService.list(lastId, count);
			break;
		case REFRESH:
			items = itemService.list(0, count);
			break;
		case LIST_BY_USER:
			items = itemService.list(uid, count, deal, lastId);
			break;
		case LIST_BY_CATEGORY:
			items = itemService.list(cid, count, lastId);
			break;
		case LIST_BY_HOT:
			items = itemService.hot(count);
			break;
		case CLOSED_ITEMS:
			items = itemService.closed(uid, count, lastId);
			break;
		default:
			throw new RuntimeException("对不起，没有这个选项！");
		}
		model.addAttribute(ITEMS, items);
		return BAD_REQUEST;
	}
	
}
