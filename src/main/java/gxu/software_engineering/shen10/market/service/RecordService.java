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
package gxu.software_engineering.shen10.market.service;

import gxu.software_engineering.shen10.market.entity.Record;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 交易记录服务接口
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 */
public interface RecordService {

	/**
	 * 交易成功，生成一条记录，否则抛出异常。
	 */
	Record create(@Min(1) long itemId, @Min(1) long sellerId, String extra);

	/**
	 * 返回系统共有多少条成功交易记录。
	 */
	long size();

	/**
	 * 返回某个卖家的成功交易记录数。
	 */
	long size(@Min(1) long userId);

	/**
	 * 某个卖家成功交易的列表。
	 * @param lastRecordId 客户端的最后一个记录，为0表示刷新。
	 */
	List<Record> list(@Min(1) long userId, @Min(1) @Max(50) int count, @Min(0) long lastRecordId);

}
