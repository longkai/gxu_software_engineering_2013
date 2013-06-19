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
import gxu.software_engineering.shen10.market.util.Consts;
import gxu.software_engineering.shen10.market.util.CoreUtils;

import java.util.Date;
import java.util.List;

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
//		long count = itemDao.size(false, false, "Item.daily_limit");
		String[] boundry = CoreUtils.boundry();
		long count = itemDao.size(false, false, "SELECT COUNT(*) FROM Item i WHERE"
			+ " i.addedTime >= " + boundry[0] + " AND i.addedTime <= " + boundry[1]);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close(User user, long itemId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void open(User user, long itemId) {
		// TODO Auto-generated method stub
		
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

}
