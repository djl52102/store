package com.itheima.store.domain;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.itheima.store.domain.view.OrderItemView;

public class Order {
	// 订单id
	private String oid;
	// 订单时间
	private Date ordertime;
	// 订单金额
	private Double total;
	// 订单状态
	private Integer state;// 订单状态 0:未付款 1:已付款 2:已发货 3.已完成
	// 收获地址
	private String address;
	// 收货人姓名
	private String name;
	// 收获人电话
	private String telephone;
	// 表示当前订单属于那个用户
	private User user;
	
	private String uid;
	
	private List<OrderItem> items=new ArrayList<>();
	
	private List<OrderItemView> itemViews=new ArrayList<>();
	
	public List<OrderItemView> getItemViews() {
		return itemViews;
	}

	public void setItemViews(List<OrderItemView> itemViews) {
		this.itemViews = itemViews;
	}

	//设置订单项集合
	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> list) {
		this.items = list;
	}

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Order(String oid, Date ordertime, Double total, Integer state, String address, String name, String telephone,
			User user, String uid) {
		super();
		this.oid = oid;
		this.ordertime = ordertime;
		this.total = total;
		this.state = state;
		this.address = address;
		this.name = name;
		this.telephone = telephone;
		this.user = user;
		this.uid = uid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public Date getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "Order [oid=" + oid + ", ordertime=" + ordertime + ", total=" + total + ", state=" + state + ", address="
				+ address + ", name=" + name + ", telephone=" + telephone + ", user=" + user + ", uid=" + uid
				+ ", items=" + items + ", itemViews=" + itemViews + "]";
	}
	
	
	
	
}
