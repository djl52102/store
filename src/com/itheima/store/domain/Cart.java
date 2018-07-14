package com.itheima.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
	//多个购物项
	private Map<String,CartItem> map=new HashMap<>();
	//总金额
	private double total=0.00;
	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cart(Map<String, CartItem> map, double total) {
		super();
		this.map = map;
		this.total = total;
	}
	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	public Collection<CartItem> getMap() {
		return map.values();
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	//添加购物车方法
	public void addItem(CartItem item){
		//获取商品的pid
		String pid=item.getP().getPid();
		
		//判断是否存在
		if(map.containsKey(pid)){
			//获取数量进行添加
			int count=item.getCount()+map.get(pid).getCount();
			map.get(pid).setCount(count);
			
		}else{
			map.put(pid, item);	
		}
		
		total += item.getSubTotal();
	}
	
	//移除购物项方法
	public void removeItem(String pid){
		CartItem item = map.remove(pid);
		
		//减去该购物项金额
		total -= item.getSubTotal();
	}
	
	//清除购物项方法
	public void clear() {
		map.clear();

		//金额重置为0
		total = 0.0;
	}
	@Override
	public String toString() {
		return "Cart [map=" + map + ", total=" + total + "]";
	}
	
	
}
