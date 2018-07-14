package com.itheima.store.domain;

import java.util.List;

public class PageBean<T> {
	private int pageNumber;
	private int total;
	private int totalPage;
	private int pageSize;
	private List<T> list;
	public PageBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PageBean(int pageNumber, int total, int totalPage, List<T> list) {
		super();
		this.pageNumber = pageNumber;
		this.total = total;
		this.totalPage = totalPage;
		this.list = list;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getTotalPage() {
		return (int) Math.ceil(total*1.0/pageSize);
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	@Override
	public String toString() {
		return "PageBean [pageNumber=" + pageNumber + ", total=" + total + ", totalPage=" + totalPage + ", pageSize="
				+ pageSize + ", list=" + list + "]";
	}
	
	
}
