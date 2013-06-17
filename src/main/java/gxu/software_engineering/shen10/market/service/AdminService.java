package gxu.software_engineering.shen10.market.service;

import gxu.software_engineering.shen10.market.entity.Admin;

import java.lang.String;

public interface AdminService {

	/**
	 * 管理员登陆。
	 */
	public Admin login(String account, String password);

}
