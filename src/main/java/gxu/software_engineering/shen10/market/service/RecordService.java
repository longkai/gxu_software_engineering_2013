package gxu.software_engineering.shen10.market.service;

import gxu.software_engineering.shen10.market.entity.Record;
import java.util.List;

public interface RecordService {

	/**
	 * 交易成功，生成一条记录，否则抛出异常。
	 */
	public Record create(long itemId);

	/**
	 * 返回系统共有多少条成功交易记录。
	 */
	public long size();

	/**
	 * 返回某个卖家的成功交易记录数。
	 */
	public long size(long userId);

	/**
	 * 某个卖家成功交易的列表。
	 * @param lastRecordId 客户端的最后一个记录，为0表示刷新。
	 */
	public List<Record> list(long userId, int count, long lastRecordId);

}
