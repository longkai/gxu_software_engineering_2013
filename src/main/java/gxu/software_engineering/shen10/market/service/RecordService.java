package gxu.software_engineering.shen10.market.service;

import gxu.software_engineering.shen10.market.entity.Record;
import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public interface RecordService {

	/**
	 * 交易成功，生成一条记录，否则抛出异常。
	 */
	public Record create(@Min(1) long itemId, @Min(1) long sellerId, String extra);

	/**
	 * 返回系统共有多少条成功交易记录。
	 */
	public long size();

	/**
	 * 返回某个卖家的成功交易记录数。
	 */
	public long size(@Min(1) long userId);

	/**
	 * 某个卖家成功交易的列表。
	 * @param lastRecordId 客户端的最后一个记录，为0表示刷新。
	 */
	public List<Record> list(@Min(1) long userId, @Min(1) @Max(50) int count, @Min(0) long lastRecordId);

}
