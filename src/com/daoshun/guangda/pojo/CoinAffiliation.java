package com.daoshun.guangda.pojo;

public class CoinAffiliation {
	public CoinAffiliation() {
		super();
	}
	public CoinAffiliation(int coin, String msg) {
		super();
		this.coin = coin;
		this.msg = msg;
	}
	
	public CoinAffiliation(int coin, String msg, int type) {
		super();
		this.coin = coin;
		this.msg = msg;
		this.type = type;
	}

	int coin;
	String msg;
	int type;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCoin() {
		return coin;
	}
	public void setCoin(int coin) {
		this.coin = coin;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
