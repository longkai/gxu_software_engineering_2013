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
package gxu.software_engineering.shen10.market.repository.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import gxu.software_engineering.shen10.market.core.AbstractCommonDaoImpl;
import gxu.software_engineering.shen10.market.entity.Record;
import gxu.software_engineering.shen10.market.repository.RecordDao;

import org.springframework.stereotype.Repository;

/**
 * Record(交易记录)数据访问接口实现。
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-6-15
 */
@Repository
public class RecordDaoImpl extends AbstractCommonDaoImpl<Record> implements RecordDao {

	@Override
	public Record find(Serializable pk) {
		return em.find(Record.class, pk);
	}

	@Override
	public long size(boolean all, boolean namedQuery, String query, Map<String, Object> params) {
		if (all) {
			return super.size(Record.class);
		}
		return super.size(namedQuery, query, params);
	}

	@Override
	public Record find(String query, Map<String, Object> params) {
		return super.executeQuery(true, query, Record.class, params);
	}

	@Override
	public List<Record> list(boolean namedQuery, String query, Map<String, Object> params,
			int offset, int number) {
		return super.executeQuery(true, query, Record.class, offset, number, params);
	}

	@Override
	public List<Record> search(String hql, Map<String, Object> params,
			int number) {
		return super.executeQuery(false, hql, Record.class, 0, number, params);
	}

}
