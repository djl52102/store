package com.itheima.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
	//���������
	private Map<String,CartItem> map=new HashMap<>();
	//�ܽ��
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
	
	//��ӹ��ﳵ����
	public void addItem(CartItem item){
		//��ȡ��Ʒ��pid
		String pid=item.getP().getPid();
		
		//�ж��Ƿ����
		if(map.containsKey(pid)){
			//��ȡ�����������
			int count=item.getCount()+map.get(pid).getCount();
			map.get(pid).setCount(count);
			
		}else{
			map.put(pid, item);	
		}
		
		total += item.getSubTotal();
	}
	
	//�Ƴ��������
	public void removeItem(String pid){
		CartItem item = map.remove(pid);
		
		//��ȥ�ù�������
		total -= item.getSubTotal();
	}
	
	//����������
	public void clear() {
		map.clear();

		//�������Ϊ0
		total = 0.0;
	}
	@Override
	public String toString() {
		return "Cart [map=" + map + ", total=" + total + "]";
	}
	
	
}
