package com.itheima.store.web.views;

public class Result {
	public static final int STATE_SUCCESS=1;
	public static final int STATE_FAIL=0;
	private int state;
	private String msg;
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Result(int state, String msg) {
		super();
		this.state = state;
		this.msg = msg;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "Result [state=" + state + ", msg=" + msg + "]";
	}
	
}
