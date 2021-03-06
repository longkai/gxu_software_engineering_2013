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
import gxu.software_engineering.shen10.market.repository.CategoryDao;
import gxu.software_engineering.shen10.market.repository.ItemDao;
import gxu.software_engineering.shen10.market.repository.UserDao;
import gxu.software_engineering.shen10.market.service.ItemService;
import gxu.software_engineering.shen10.market.util.Assert;
import gxu.software_engineering.shen10.market.util.Consts;
import gxu.software_engineering.shen10.market.util.CoreUtils;
import gxu.software_engineering.shen10.market.util.TextUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 物品相关服务的实现。
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-19
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Throwable.class)
@Validated
public class ItemServiceImpl implements ItemService {
	
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
	@Transactional(readOnly = false)
	public Item modify(Item item, long cid, long uid) {
		Item i = itemDao.find(item.getId());
//		验证是否具有修改权限
		this.modifiable(i, uid);
		
		i.setLastModifiedTime(new Date());
		i.setName(item.getName());
		i.setDescription(item.getDescription());
//		这个有单独的方法
//		i.setClosed(item.getClosed());
		i.setCategory(categoryDao.find(cid));
		i.setExtra(item.getExtra());
		i.setPrice(item.getPrice());
		itemDao.merge(i);
		return i;
	}

	@Override
	@Transactional(readOnly = false)
	public Item close(boolean close, long uid, long itemId) {
		Item item = itemDao.find(itemId);
		this.modifiable(item, uid);
		item.setLastModifiedTime(new Date());
		item.setClosed(close);
		itemDao.merge(item);
		return item;
	}

	@Override
	public Item detail(long itemId) {
		return itemDao.find(itemId);
	}

	@Override
	public List<Item> latest(int count) {
		return itemDao.list(true, "Item.list_latest", null, 0, count);
	}

	@Override
	public List<Item> list(long lastItemId, int count) {
		Map<String, Object> params = new HashMap<String, Object>();
		if (lastItemId == 0) {
			params.put("id", Long.MAX_VALUE);
		} else {
			params.put("id", lastItemId);
		}
		return itemDao.list(true, "Item.list_more", params, 0, count);
	}

	@Override
	public long size(boolean deal) {
		if (deal) {
			return itemDao.size(true, true, null, null);
		}
		return itemDao.size(false, true, "Item.size_deal", null);
	}

	@Override
	public long size(long userId, boolean deal) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seller_id", userId);
		if (deal) {
			return itemDao.size(false, true, "Item.size_seller", params);
		}
		return itemDao.size(false, true, "Item.size_seller_deal", params);
	}

	@Override
	public List<Item> list(long userId, int count, boolean deal, long lastItemId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seller_id", userId);
		if (lastItemId == 0) {
//			比这个还小，足够大了吧:-)
			params.put("last_id", Long.MAX_VALUE);
		} else {
			params.put("last_id", lastItemId);
		}
//		返回已售
		if (deal) {
			params.put("deal", true);
			return itemDao.list(true, "Item.list_user_deal", params, 0, count);
		}
//		返回待售
		params.put("deal", false);
		return itemDao.list(true, "Item.list_user_deal", params, 0, count);
	}

	@Override
	public List<Item> list(long categoryId, int count, long lastItemId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("category_id", categoryId);
		if (lastItemId == 0) {
			params.put("last_id", Long.MAX_VALUE);
		} else {
			params.put("last_id", lastItemId);
		}
		return itemDao.list(true, "Item.list_category_deal", params, 0, count);
	}

	@Override
	public long size(long categoryId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("category_id", categoryId);
		return itemDao.size(false, true, "Item.size_category", params);
	}

	@Override
	public List<Item> hot(int count) {
		return itemDao.list(true, "Item.list_hot", null, 0, count);
	}

	private void modifiable(Item i, long uid) {
		Assert.notNull(i, "对不起，您所修改的物品不存在！");
		if (i.getSeller().getId() != uid) {
			throw new SecurityException("对不起，不是你的物品，您无权修改之！");
		}
		if (i.isBlocked()) {
			throw new RuntimeException("对不起，这个物品已经被管理员锁住，请联系管理员！");
		}
		if (i.isDeal()) {
			throw new RuntimeException("对不起，此物品已经成功交易，不能再修改啦！");
		}
	}

	@Override
	public List<Item> closed(long userId, int count, long lastItemId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seller_id", userId);
		if (lastItemId == 0) {
			params.put("last_id", Long.MAX_VALUE);
		} else {
			params.put("last_id", lastItemId);
		}
		return itemDao.list(true, "Item.list_user_closed", params, 0, count);
	}

	@Override
	public List<Item> sync(long lastSyncMills, int count) {
		Date d = new Date(lastSyncMills);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("last_modified_time", d);
		return itemDao.list(true, "Item.list_sync", params, 0, count);
	}

	@Override
	@Transactional(readOnly = false)
	public Item block(long id, boolean blocked) {
		Item i = itemDao.find(id);
		i.setBlocked(blocked);
		itemDao.merge(i);
		return i;
	}

	@Override
	@Transactional(readOnly = false)
	public Item alter(long id, float price, String name, String desc,
			String extra) {
		Item i = itemDao.find(id);
		i.setPrice(price);
		i.setName(name);
		i.setDescription(desc);
		i.setExtra(extra);
		itemDao.merge(i);
		return i;
	}

	@Override
	public Map<String, Object> search(String name, float minPrice,
			float maxPrice, long lastId, int count) {
		StringBuilder sb = new StringBuilder("FROM Item i WHERE 1=1 ");
		if (!TextUtils.isEmpty(name)) {
			sb.append("AND i.name like '%").append(name).append("%' ");
		}
		if (maxPrice > minPrice) {
			sb.append("AND i.price>").append(minPrice)
				.append(" AND i.price<").append(maxPrice).append(" ");
		}
		if (lastId > 0) {
			sb.append("AND i.id<").append(lastId).append(" ");
		}
		sb.append("ORDER BY i.id DESC");
		String query = sb.toString();
		List<Item> list = itemDao.search(query, null, count);
		query = "SELECT COUNT(i.id) " + query;
		long total = itemDao.size(false, false, query, null);
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("list", list);
		map.put("total", total);
		return map;
	}
	
}
