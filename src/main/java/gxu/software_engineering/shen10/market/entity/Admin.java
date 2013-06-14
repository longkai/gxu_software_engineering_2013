/*
 * Copyright Copyright 2013 Department of Computer Science and Technology, Guangxi University
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

import java.lang.Long;
import java.lang.String;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Admin实体类
 * 
 * @author longkai
 * @email  im.longkai@gmail.com
 */
@Entity
@Table(name = "admins")
public class Admin {

	@Id
	@GeneratedValue
	private Long	id;

	@NotBlank(message = "管理员名称不能为空！")
	@Size(min = 2, max = 7, message = "管理员名称必须在2-7个字符之间！")
	private String	name;

	@NotBlank(message = "管理员账号不能为空！")
	@Size(min = 6, max = 18, message = "管理员账号必须在6-18个字符之间！")
	private String	account;

	@NotBlank(message = "管理员密码不能为空！")
	@Size(min = 6, max = 32, message = "管理员密码必须在6-32个字符之间！")
	@JsonIgnore
	private String	password;

	@Column(name = "added_time")
	@JsonProperty("added_time")
	private Date	addedTime;

	@Column(name = "last_login_time")
	@JsonIgnore
	private Date	lastLoginTime;

	@Column(name = "last_login_ip")
	@JsonIgnore
	@Pattern(regexp = "(((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d|\\d))(\\.))"
				+ "{3}((25[0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|([1-9]))")
	private String	lastLoginIP;

	/** 额外说明信息 */
	@Size(min = 2, max = 255, message = "额外说明信息必须在2-255个字符之间！")
	private String	extra;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getAddedTime() {
		return addedTime;
	}

	public void setAddedTime(Date addedTime) {
		this.addedTime = addedTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
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
		Admin other = (Admin) obj;
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
