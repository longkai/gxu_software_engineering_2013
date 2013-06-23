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
package gxu.software_engineering.shen10.market.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import gxu.software_engineering.shen10.market.entity.Category;
import gxu.software_engineering.shen10.market.entity.User;

/**
 * item实体类（指的是一个可以买卖，交易的物品）
 * 
 * @author longkai
 * @email  im.longkai@gmail.com
 */
@Entity
@Table(name = "items")
@NamedQueries({
	@NamedQuery(name = "Item.daily_limit", query = "SELECT COUNT(*) FROM Item i WHERE"
			+ " i.addedTime >= :begin AND i.addedTime <= :end"),
	@NamedQuery(name = "Item.list_latest", query = "FROM Item i WHERE i.blocked IS FALSE AND i.closed IS FALSE AND i.deal IS FALSE ORDER BY i.id DESC"),
	@NamedQuery(name = "Item.list_more", query = "FROM Item i WHERE i.id < :id ORDER BY i.id DESC"),
	@NamedQuery(name = "Item.size_deal", query = "SELECT COUNT(*) FROM Item i WHERE i.deal IS FALSE"),
	@NamedQuery(name = "Item.size_seller_deal", query = "SELECT COUNT(*) FROM Item i WHERE i.seller.id = :seller_id AND i.deal IS FALSE"),
	@NamedQuery(name = "Item.size_seller", query = "SELECT COUNT(*) FROM Item i WHERE i.seller.id = :seller_id"),
	@NamedQuery(name = "Item.size_category", query = "SELECT COUNT(*) FROM Item i WHERE i.category.id = :category_id"),
	@NamedQuery(name = "Item.list_user_all", query = "FROM Item i WHERE i.seller.id = :seller_id AND i.id < :last_id ORDER BY i.id DESC"),
	@NamedQuery(name = "Item.list_user_deal", query = "FROM Item i WHERE i.seller.id = :seller_id AND i.id < :last_id AND i.deal IS FALSE ORDER BY i.id DESC"),
	@NamedQuery(name = "Item.list_category_deal", query = "FROM Item i WHERE i.category.id = :category_id AND i.id < :last_id AND i.deal IS FALSE ORDER BY i.id DESC"),
	@NamedQuery(name = "Item.list_hot", query = "FROM Item i WHERE i.blocked IS FALSE AND i.closed IS FALSE AND i.deal IS FALSE ORDER BY i.clickTimes DESC")
})
public class Item {

	@Id
	@GeneratedValue
	private Long		id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	@NotNull(message = "类别不能为空！")
	private Category	category;

	@NotBlank(message = "物品名称不能为空！")
	@Size(min = 1, max = 18, message = "物品名称必须在1-18个字符之间！")
	private String		name;

	@NotNull(message = "物品价格不能为空！")
	// well, validation api has no support float types :(
	private Float		price;

	@Column(name = "added_time")
	@JsonProperty("added_time")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date		addedTime;

	@Column(name = "last_modified_time")
	@JsonProperty("last_modified_time")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date		lastModifiedTime;

	@Column(name = "click_times")
	@JsonProperty("click_times")
	private Long		clickTimes;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "seller_id")
	@NotNull(message = "卖家不能为空！")
	private User		seller;

	/** 是否被用户自己关闭交易 */
	private boolean		closed;
	
	@NotBlank(message = "描述信息不能为空！")
	@Size(min = 10, max = 255, message = "描述信息必须在10-255个字段内！")
	private String		description;

	/** 由于不符合规定，由管理员将其锁住 */
	@JsonIgnore
	private boolean		blocked;

	/** 交易是否成功了 */
	private boolean deal;

	/** 额外说明，optional */
	@Size(min = 2, max = 255, message = "额外说明最大不能超过255个字符！")
	private String extra;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Date getAddedTime() {
		return addedTime;
	}

	public void setAddedTime(Date addedTime) {
		this.addedTime = addedTime;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Long getClickTimes() {
		return clickTimes;
	}

	public void setClickTimes(Long clickTimes) {
		this.clickTimes = clickTimes;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public boolean isDeal() {
		return deal;
	}

	public void setDeal(boolean deal) {
		this.deal = deal;
	}

	public String getExtra() {
		return extra;
	}

	public void setExtra(String extra) {
		this.extra = extra;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("{\"type\":%s,\"id\":%d,\"name\":%s}",
				getClass().getSimpleName(), id, name);
	}

}
