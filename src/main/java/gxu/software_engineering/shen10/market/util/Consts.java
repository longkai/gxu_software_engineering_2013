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
package gxu.software_engineering.shen10.market.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import gxu.software_engineering.shen10.market.entity.User;

/**
 * 终于感觉还是有必要弄一个常量池了。。。
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-19
 */
public final class Consts {

	/** 每日最大发布新物品数量 */
	public static final int DAILY_MAX_ITEM_COUNT = 3;
	
	/** 一次物品请求列表的最大长度 */
	public static final int MAX_ITEM_COUNT = 50;
	
	/** http 请求状态 */
	public static final String STATUS = "status";
	
	/** http 请求成功标志 */
	public static final int STATUS_OK = 1;
	
	/** http 请求失败标志 */
	public static final int STATUS_NO = 0;
	
	/** user 字符串，标志为单个卖家 */
	public static final String USER = "user";
	
	/** users 字符串，标志为卖家列表 */
	public static final String USERS = "users";
	
	/** 错误的请求，用户只提供ajax数据的情形 */
	public static final String BAD_REQUEST = "bad_request";
	
	/** http 请求返回信息 */
	public static final String MESSAGE = "msg";
	
	/** http 请求失败原因 */
	public static final String EXP_REASON = "reason";
	
	/** http 请求失败未知原因 */
	public static final String UNKNOWN_REASON = "我们没有能收集足够的错误信息，请您稍后再试！";
	
	/** http 请求错误html视图 */
	public static final String ERROR_PAGE = "error";
	
	/** admin 管理员 */
	public static final String ADMIN = "admin";
	
	/** category 类别 */
	public static final String CATEGORY = "category";
	
	/** categories 类别列表 */
	public static final String CATEGORIES = "categories";
	
	/** item 物品 */
	public static final String ITEM = "item";
	
	/** items 物品列表 */
	public static final String ITEMS = "items";
	
	/** records 成功交易 */
	public static final String RECORD = "records";
	
	/** records 成功交易列表 */
	public static final String RECORDS = "records";
	
	/** 最新信息 */
	public static final int	LATEST		= 1;

	/** 加载更多 */
	public static final int	LATEST_MORE	= 2;

	/** 刷新 */
	public static final int	REFRESH		= 3;
	
	/** 某个用户相关 */
	public static final int LIST_BY_USER = 4;
	
	/** 某个类别相关 */
	public static final int LIST_BY_CATEGORY = 5;
	
	/** 某个热门相关 */
	public static final int LIST_BY_HOT = 6;
	
	public static User resolveUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute(Consts.USER) == null) {
			throw new RuntimeException("对不起，您没有登陆或者登陆超时，请登陆后再操作！");
		}
		return (User) session.getAttribute(Consts.USER);
	}
	
}
