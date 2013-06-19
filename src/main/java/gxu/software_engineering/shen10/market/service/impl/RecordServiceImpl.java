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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import gxu.software_engineering.shen10.market.entity.Item;
import gxu.software_engineering.shen10.market.entity.Record;
import gxu.software_engineering.shen10.market.repository.ItemDao;
import gxu.software_engineering.shen10.market.repository.RecordDao;
import gxu.software_engineering.shen10.market.service.RecordService;
import gxu.software_engineering.shen10.market.util.Assert;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-20
 */
@Service
@Validated
@Transactional(propagation = Propagation.REQUIRED, readOnly = true, rollbackFor = Throwable.class)
public class RecordServiceImpl implements RecordService {

	@Inject
	private RecordDao recordDao;
	
	@Inject
	private ItemDao itemDao;
	
	@Override
	@Transactional(readOnly = false)
	public Record create(long itemId, long sellerId, String extra) {
		Item item = itemDao.find(itemId);
		Assert.notNull(item, "对不起，您所查找的物品不存在！");
		
		if (item.getBlocked() != null && item.getBlocked().booleanValue()) {
			throw new RuntimeException("对不起，该物品已经被管理员冻结，您不能出售它！请联系管理员！");
		}
		
		Boolean blocked = item.getSeller().getBlocked();
		if (blocked != null && blocked.booleanValue()) {
			throw new RuntimeException("对不起，您的账号已经被冻结，请联系管理员！");
		}
		
		if (!item.getSeller().getId().equals(sellerId)) {
			throw new SecurityException("对不起，不是您的商品不能出售！");
		}
		
		Record record = new Record();
		record.setOccurTime(new Date());
		record.setExtra(extra);
		record.setItem(item);
		
		recordDao.persist(record);
		return record;
	}

	@Override
	public long size() {
		return recordDao.size(true, true, null, null);
	}

	@Override
	public long size(long userId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seller_id", userId);
		return recordDao.size(false, true, "Record.size_seller", params);
	}

	@Override
	public List<Record> list(long userId, int count, long lastRecordId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("seller_id", userId);
		if (lastRecordId == 0) {
			params.put("last_id", Long.MAX_VALUE);
		} else {
			params.put("last_id", lastRecordId);
		}
		return recordDao.list(true, "Record.list_seller", params, 0, count);
	}
	
}
