/*
 * Copyright 2013 longkai
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
package gxu.software_engineering.shen10.market.core;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 通用的数据访问接口，包含简单的增删改查列表和总记录数方法。
 * 
 * @author longkai(龙凯)
 * @email  im.longkai@gmail.com
 * @since  2013-3-28
 */
public interface CommonDao<T> {
	
	/**
	 * 将一个实体持久化。
	 * @param entity
	 */
	void persist(T entity);
	
	/**
	 * 将一个持久化的实体处于游离化。
	 * @param entity
	 */
	void remove(T entity);
	
	/**
	 * 将持久化的实体与运行时的实体合并，更新是也。
	 * @param entity
	 */
	void merge(T entity);
	
	/**
	 * 通过主键查询一个实体。
	 * @param pk 序列化类型的主键
	 * @return 查找到的实体对象
	 */
	T find(Serializable pk);
	
	/**
	 * 实体类的记录数。
	 * @param all 是否查询所有的记录数？
	 * @param namedQuery 是否是静态查询？
	 * @param query 自定义查询hql
	 * @return 记录总数
	 */
	long size(boolean all, boolean namedQuery, String query);
	
	/**
	 * 自定义查询实体对象。
	 * @param query
	 * @param params
	 * @return
	 */
	T find(String query, Map<String, Object> params);
	
	/**
	 * 获取实体类对象的列表。
	 * @param namedQuery 是否使用静态查询hql
	 * @param query 查询hql
	 * @param params 查询参数
	 * @param offset 偏移量，即，从第几条记录开始取
	 * @param number 抓取数，即，抓取多少条记录
	 * @return 实体类对象列表
	 */
	List<T> list(boolean namedQuery, String query, Map<String, Object> params, int offset, int number);
	
	/**
	 * 搜索。
	 * @param hql
	 * @param params
	 * @param number
	 * @return 搜索到的实体类对象列表
	 */
	List<T> search(String hql, Map<String, Object> params, int number);
	
}
