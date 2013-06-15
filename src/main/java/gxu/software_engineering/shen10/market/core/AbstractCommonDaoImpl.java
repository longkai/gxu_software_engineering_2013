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


import gxu.software_engineering.shen10.market.util.Assert;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 抽象的通用数据访问接口的实现，同时提供了一些实用dao方法。
 * 
 * @author longkai
 * @email  im.longkai@gmail.com
 * @since  2013-3-28
 */
public abstract class AbstractCommonDaoImpl<T> implements CommonDao<T> {

	/** 共用的日志对象 */
	protected static final Logger	L	= LoggerFactory.getLogger(AbstractCommonDaoImpl.class);

	@PersistenceContext
	protected EntityManager			em;

	@Override
	public void persist(T entity) {
		Assert.notNull(entity, "实体类对象不能为空！");
		em.persist(entity);
	}

	@Override
	public void remove(T entity) {
		Assert.notNull(entity, "实体类对象不能为空！");
		em.remove(entity);
	}

	@Override
	public void merge(T entity) {
		Assert.notNull(entity, "实体类对象不能为空！");
		em.merge(entity);
	}
	
	/**
	 * 通过实体类类型返回该实体类的总记录数。
	 * @param clazz 实体类
	 * @return 该实体类的总记录数
	 */
	protected long size(Class<?> clazz) {
		return em.createQuery("SELECT count(*) FROM " + clazz.getSimpleName(), Long.class)
					.getSingleResult();
	}
	
	/**
	 * 查询，只返回一个对象，支持<b style="color: red;"> ? </b>格式来作为占位符。
	 * @param hql hql
	 * @param type 查询的实体类型
	 * @param keys 参数名， 为null表示无参数
	 * @param objects 参数值, 为null表示无参数
	 * @return 单个对象
	 */
	protected T executeQuery(String hql, Class<T> clazz, String[] keys, Object[] values) {
		TypedQuery<T> query = em.createNamedQuery(hql, clazz);
		if (keys != null && values != null) {
			Assert.equals(keys.length, values.length,
					String.format("查询参数的数目不一致！键->%d, 值->%d", keys.length, values.length));
			for (int i = 0; i < keys.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
		return query.getSingleResult();
	}
	
	/**
	 * 查询，只返回一个对象，支持<b style="color: red;"> :xxx </b>格式来作为参数占位符。
	 * @param hql 预处理hql
	 * @param type 查询的实体类型
	 * @param objects 参数map, 不能为空
	 * @return 单个对象
	 */
	protected T executeQuery(String hql, Class<T> clazz, Map<String, Object> params) {
		TypedQuery<T> query = em.createNamedQuery(hql, clazz);
		if (params != null) {
			for (String name : params.keySet()) {
				query.setParameter(name, params.get(name));
			}
		}
		return query.getSingleResult();
	}

	/**
	 * 查询，返回一个列表，支持<b style="color: red;"> ? </b>格式来作为参数占位符。
	 * @param hql hql
	 * @param clazz 查询的实体类型
	 * @param offset 偏移量，即，从第几条记录开始取
	 * @param number 抓取数，即，抓取多少条记录
	 * @param keys 参数名， 为null表示无参数
	 * @param objects 参数值, 为null表示无参数
	 * @return 对象列表
	 */
	protected List<T> executeQuery(String hql, Class<T> clazz, int offset, int number, String[] keys, Object[] values) {
		offset = rangeCheck(offset, clazz);
		TypedQuery<T> query = em.createNamedQuery(hql, clazz);
		if (keys != null && values != null) {
			Assert.equals(keys.length, values.length,
					String.format("查询参数的数目不一致！键->%d, 值->%d", keys.length, values.length));
			
			for (int i = 0; i < keys.length; i++) {
				query.setParameter(i + 1, values[i]);
			}
		}
		return query.setFirstResult(offset).setMaxResults(number).getResultList();
	}
	
	/**
	 * 查询，返回一个列表，支持<b style="color: red;"> :xxx </b>格式来作为参数占位符。
	 * @param hql hql
	 * @param clazz 查询的实体类型
	 * @param offset 偏移量，即，从第几条记录开始取
	 * @param number 抓取数，即，抓取多少条记录
	 * @param params 参数map，不能为空
	 * @return 对象列表
	 */
	protected List<T> executeQuery(String hql, Class<T> clazz, int offset, int number, Map<String, Object> params) {
		offset = rangeCheck(offset, clazz);
		TypedQuery<T> query = em.createNamedQuery(hql, clazz);
		if (params != null) {
			for (String name : params.keySet()) {
				query.setParameter(name, params.get(name));
			}
		}
		return query.setFirstResult(offset).setMaxResults(number).getResultList();
	}
	
	/**
	 * 检测偏移量是否越界。
	 * 
	 * @param offset 偏移量
	 * @param clazz 操作的实体类型
	 * @return 调整后（越界）的偏移量
	 */
	protected int rangeCheck(int offset, Class<?> clazz) {
		if (offset < 0) {
			offset = 0;
			return offset;
		}
		long size = size(clazz);
		if (offset > size) {
			offset = Long.valueOf(size).intValue();
		}
		return offset;
	}

}
