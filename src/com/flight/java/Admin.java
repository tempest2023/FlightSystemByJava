package com.flight.java;

public class Admin {
	private int id = 0;
	private String username = "";
	private String pwd = "";

	public Admin(int Id, String user, String pwd) {
		this.id = Id;
		this.username = user;
		this.pwd = pwd;
	}

	public int getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPwd() {
		return pwd;
	}

	/**
	 * @name 登录验证函数
	 * @param username
	 * @param pwd
	 * @return boolean
	 */
	public static boolean CheckAdmin(String username, String pwd) {
		DbSelect _s = new DbSelect();
		Admin _a = _s.AdminSelect(username);
		if (_a != null) {
			// System.out.println(_a.getPwd() + "   " + _a.getUsername());
			// System.out.println(Encode.MD5(pwd));
			if (Encode.MD5(pwd).equals(_a.getPwd())) {
				return true;
			}
		}
		return false;

	}

	/**
	 * @name 注册管理员用户
	 * @param username
	 * @param pwd
	 * @param cid
	 *            你绝对猜不到创建权限密码是什么
	 * @return boolean
	 */
	public static boolean CreateAdmin(String username, String pwd, String cid) {
		if (Encode.MD5(cid).equals("b5223cc5cda3647409cede336d76aed8")) {
			DbInsert _i = new DbInsert();
			if (_i.AdminInsert(username, pwd)) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		/*
		 * 登录验证 if(Admin.CheckAdmin("admin", "admin")) {
		 * System.out.println("登录成功"); }else{ System.out.println("用户名不存在或密码错误");
		 * }
		 */
		/*
		 * 注册管理员账户
		 * 
		 * if(Admin.CreateAdmin("任涛", "不告诉你","你绝对猜不到创建权限密码是什么")) {
		 * System.out.println("注册管理员成功"); } else { System.out.println("注册失败"); }
		 */
	}

}
