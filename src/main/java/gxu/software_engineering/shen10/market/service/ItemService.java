package gxu.software_engineering.shen10.market.service;

import gxu.software_engineering.shen10.market.entity.Item;
import gxu.software_engineering.shen10.market.entity.User;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public interface ItemService {

	/**
	 * 发布一个新物品。
	 * @return 若创建成功，返回成功创建的，否则抛出异常。
	 */
	public Item create(@NotNull Item item, @Min(1) long categoryId, @Min(1) long sellerId);

	/**
	 * 修改一个物品。
	 * @return 返回修改成功后的物品，否则抛出异常。
	 */
	public Item modify(@NotNull Item item);

	/**
	 * 物品所属人将物品标注为关闭状态，或者将已经关闭的物品置为打开状态（可以出售）。
	 * @param closed 是否关闭。
	 * @param user 提出申请的卖家。
	 * @param itemId 想要关闭或者打开的物品的标识。
	 */
	public Item close(boolean close, @NotNull User user, @Min(1) long itemId);

	/**
	 * 查看物品详细说明，若不存在，或者被管理员锁住，则会抛出异常。
	 * @param 物品id标识。
	 */
	@NotNull(message = "对不起，您所要查早的物品不存在！")
	public Item detail(@Min(1) long itemId);

	/**
	 * 查看最新的物品列表。
	 */
	public List<Item> latest(@Min(1) @Max(50) int count);

	/**
	 * 查看物品的列表。
	 * @param lastItemId 上一个物品的id，为0表示刷新。
	 */
	public List<Item> list(@Min(1) long lastItemId, @Min(1) @Max(50) int count);

	/**
	 * 返回系统所有的物品数。
	 * @param deal 是否卖出。
	 * @return 若deal为true，那么返回所有的，否则返回待售的。
	 */
	public long size(boolean deal);

	/**
	 * 某个卖家的物品数。
	 * @param deal 是否已经卖出
	 * @return 若deal为true，那么返回所有的，否则返回待售的。
	 */
	public long size(long userId, boolean deal);

	/**
	 * 返回某个卖家的物品。
	 * @param lastItemId 查看更多的物品的偏移量，为0表示刷新。
	 * @return 若deal为true，那么返回的是所有的，否则返回待售的。
	 */
	public List<Item> list(long userId, int count, boolean deal, long lastItemId);

	/**
	 * 返回某个类别下的代售物品。
	 * @param lastItemId 查看更多的物品的偏移量，为0表示刷新。
	 */
	public List<Item> list(long categoryId, int count, long lastItemId);

	/**
	 * 返回某个类别下的待售物品数。
	 */
	public long size(long categoryId);

	/**
	 * 返回最热门的物品列表。
	 */
	public List<Item> hot(int count);

}
