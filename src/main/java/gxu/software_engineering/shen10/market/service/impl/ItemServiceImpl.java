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

import gxu.software_engineering.shen10.market.entity.Item;
import gxu.software_engineering.shen10.market.entity.User;
import gxu.software_engineering.shen10.market.repository.CategoryDao;
import gxu.software_engineering.shen10.market.repository.ItemDao;
import gxu.software_engineering.shen10.market.repository.UserDao;
import gxu.software_engineering.shen10.market.service.ItemService;
import gxu.software_engineering.shen10.market.util.Assert;
import gxu.software_engineering.shen10.market.util.Consts;
import gxu.software_engineering.shen10.market.util.CoreUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-19
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Throwable.class)
@Validated
public class ItemServiceImpl implements ItemService {
	
	private static final Logger L = LoggerFactory.getLogger(ItemServiceImpl.class);
	
	@Inject
	private ItemDao itemDao;
	
	@Inject
	private UserDao userDao;
	
	@Inject
	private CategoryDao categoryDao;

	@Override
	@Transactional(readOnly = false)
	public Item create(Item item, long categoryId, long sellerId) {
		Date[] boundry = CoreUtils.boundry();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("begin", boundry[0]);
		params.put("end", boundry[1]);
		long count = itemDao.size(false, true, "Item.daily_limit", params);
		if (count == Consts.DAILY_MAX_ITEM_COUNT) {
			throw new RuntimeException("对不起，您今天已经达到了最大发布物品数量，请休息一下吧:-)");
		}
		item.setAddedTime(new Date());
		item.setLastModifiedTime(item.getAddedTime());
		item.setBlocked(false);
		item.setClickTimes(0L);
		item.setClosed(false);
		item.setDeal(false);
		
		item.setSeller(userDao.find(sellerId));
		item.setCategory(categoryDao.find(categoryId));
		itemDao.persist(item);
		return item;
	}

	@Override
	public Item modify(Item item) {
		Item i = itemDao.find(item.getId());
//		验证是否具有修改权限
		this.modifiable(i, item.getSeller());
		
		i.setLastModifiedTime(new Date());
		i.setName(item.getName());
		i.setDescription(item.getDescription());
//		这个有单独的方法
//		i.setClosed(item.getClosed());
		i.setCategory(item.getCategory());
		i.setExtra(item.getExtra());
		i.setPrice(item.getPrice());
		itemDao.merge(i);
		return i;
	}

	@Override
	public Item close(boolean close, User user, long itemId) {
		Item item = itemDao.find(itemId);
		this.modifiable(item, user);
		item.setLastModifiedTime(new Date());
		item.setClosed(close);
		itemDao.merge(item);
		return item;
	}

	@Override
	public Item detail(long itemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> latest(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> list(long lastItemId, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long size(boolean deal) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long size(long userId, boolean deal) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Item> list(long userId, int count, boolean deal, long lastItemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Item> list(long categoryId, int count, long lastItemId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long size(long categoryId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Item> hot(int count) {
		// TODO Auto-generated method stub
		return null;
	}

	private void modifiable(Item i, User seller) {
		Assert.notNull(i, "对不起，您所修改的物品不存在！");
		if (!i.getSeller().equals(seller)) {
			throw new SecurityException("对不起，不是你的物品，您无权修改之！");
		}
		if (i.getBlocked().booleanValue()) {
			throw new RuntimeException("对不起，这个物品已经被管理员锁住，请联系管理员！");
		}
		if (i.getDeal().booleanValue()) {
			throw new RuntimeException("对不起，此物品已经成功交易，不能再修改啦！");
		}
	}
	
}
