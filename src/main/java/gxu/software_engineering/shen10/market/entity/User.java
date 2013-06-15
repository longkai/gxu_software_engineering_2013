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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

/**
 * user实体类
 * 
 * @author longkai
 * @email  im.longkai@gmail.com
 */
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue
	private Long	id;

	@Column(name = "real_name")
//	@NotNull(message = "真实姓名不能为空！")
	@NotBlank(message = "真实姓名不能为空！")
	@Size(min = 2, max = 4, message = "用户名必须在1-4个字符之间！")
	@JsonIgnore
	private String	realName;

//	@NotNull(message = "昵称不能为空！")
	@NotBlank(message = "昵称不能为空！")
	@Size(min = 2, max = 7, message = "昵称必须在2-7个字符之间！")
	private String	nick;

//	@NotNull(message = "账号不能为空！")
	@NotBlank(message = "账号不能为空！")
	@Size(min = 6, max = 18, message = "账号必须在6-18个字符之间！")
	private String	account;

//	@NotNull(message = "密码不能为空！")
	@NotBlank(message = "密码不能为空！")
	@Size(min = 6, max = 18, message = "密码必须在6-18个字符之间！")
	@JsonIgnore
	private String	password;

	@Column(name = "register_time")
	@JsonProperty("register_time")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date	registerTime;

	@Column(name = "last_login_time")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonProperty("last_login_time")
	private Date	lastLoginTime;

	@Column(name = "login_times")
	@JsonProperty("login_times")
	private Integer	loginTimes;

	@JsonIgnore
	private Boolean	blocked;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
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

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	public Boolean getBlocked() {
		return blocked;
	}

	public void setBlocked(Boolean blocked) {
		this.blocked = blocked;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("{\"type\":%s,\"id\":%d,\"nick\":%s}",
				getClass().getSimpleName(), id, nick);
	}
	
}
