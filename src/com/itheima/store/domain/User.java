package com.itheima.store.domain;

import java.sql.Date;

public class User {
	private String uid;
	private String username;
	private String password;
	private String name;
	private String email;
	private String remark;
	private Date birthday;
	private String gender;
	private int state;
	private String code;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(String uid, String username, String password, String name, String email, String remark, Date birthday,
			String gender, int state, String code) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.remark = remark;
		this.birthday = birthday;
		this.gender = gender;
		this.state = state;
		this.code = code;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getgender() {
		return gender;
	}
	public void setgender(String gender) {
		this.gender = gender;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override
	public String toString() {
		return "User [uid=" + uid + ", username=" + username + ", password=" + password + ", name=" + name + ", email="
				+ email + ", remark=" + remark + ", birthday=" + birthday + ", gender=" + gender + ", state=" + state
				+ ", code=" + code + "]";
	}
	
	
}
