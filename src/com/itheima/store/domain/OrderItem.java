package com.itheima.store.domain;

public class OrderItem {
	//������id
	private String itemid;
	//����������
	private Integer count;
	//������С��
	private Double subtotal;
	//��ʾ�����Ǹ���Ʒ
	private  Product product;
	//��ʾ�����Ǹ�����
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
