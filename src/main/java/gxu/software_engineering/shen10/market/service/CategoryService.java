package gxu.software_engineering.shen10.market.service;

import gxu.software_engineering.shen10.market.entity.Category;

import java.util.List;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;

public interface CategoryService {

	/**
	 * 添加新的类别，类别不允许重名。
	 * @return 成功添加的类别。
	 */
	public Category add(Category category);

	/**
	 * 修改类别。
	 * @return 若成功，返回修改后的类别，否则抛出异常。
	 */
	public Category modify(@Min(1) long categoryId, @NotBlank String name, @NotBlank String description);

	/**
	 * 根据类别的id，查看该类别的详细信息。
	 * @param 类别的id。
	 * @return 若存在，则返回该类别，否则抛出异常。
	 */
	public Category view(long categoryId);

	/**
	 * 查看所有的类别。
	 * @return 返回类别的列表。
	 */
	public List<Category> list();

	/**
	 * 抓取所有的类别数量。
	 */
	public long size();

}
