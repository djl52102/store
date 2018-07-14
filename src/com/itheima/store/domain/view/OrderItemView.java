package com.itheima.store.domain.view;

import com.itheima.store.domain.Order;
import com.itheima.store.domain.Product;

public class OrderItemView {
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
		
		private String pname;
		
		private double shop_price;
		
		private String pimage;

		public OrderItemView() {
			super();
			// TODO Auto-generated constructor stub
		}

		public OrderItemView(String itemid, Integer count, Double subtotal, Product product, Order order, String oid,
				String pid, String pname, double shop_price, String pimage) {
			super();
			this.itemid = itemid;
			this.count = count;
			this.subtotal = subtotal;
			this.product = product;
			this.order = order;
			this.oid = oid;
			this.pid = pid;
			this.pname = pname;
			this.shop_price = shop_price;
			this.pimage = pimage;
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

		public String getPname() {
			return pname;
		}

		public void setPname(String pname) {
			this.pname = pname;
		}

		public double getShop_price() {
			return shop_price;
		}

		public void setShop_price(double shop_price) {
			this.shop_price = shop_price;
		}

		public String getPimage() {
			return pimage;
		}

		public void setPimage(String pimage) {
			this.pimage = pimage;
		}
		
		
}
