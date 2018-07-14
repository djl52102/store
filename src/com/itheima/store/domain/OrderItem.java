package com.itheima.store.domain;

public class OrderItem {
	//订单项id
	private String itemid;
	//订单项数量
	private Integer count;
	//订单项小计
	private Double subtotal;
	//表示包含那个商品
	private  Product product;
	//表示属于那个订单
	private  Order order;
	
	private String oid;
	
	private String pid;

	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderItem(String itemid, Integer count, Double subtotal, Product product, Order order, String oid,
			String pid) {
		super();
		this.itemid = itemid;
		this.count = count;
		this.subtotal = subtotal;
		this.product = product;
		this.order = order;
		this.oid = oid;
		this.pid = pid;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	
	
	
}
