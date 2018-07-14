package com.itheima.store.domain;

public class CartItem {
	private Product p;
	private int count;
	private double subTotal;
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CartItem(Product p, int count, double subTotal) {
		super();
		this.p = p;
		this.count = count;
		this.subTotal = subTotal;
	}
	public Product getP() {
		return p;
	}
	public void setP(Product p) {
		this.p = p;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubTotal() {
		return p.getShop_price()*count;
	}
	@Override
	public String toString() {
		return "CartItem [p=" + p + ", count=" + count + ", subTotal=" + subTotal + "]";
	}
	
	
}
