package com.ispan.model1;

public class ExchangeRates {
	private String ymd;
	private String usd2Ntd;
	private String cny2Ntd;
	private String usd2Jpy;
	private String usd2Hkd;
	private String usd2Cny;
	
	public ExchangeRates() {
	}
	
	public ExchangeRates(String ymd, String usd2Ntd, String cny2Ntd, String usd2Jpy, String usd2Hkd, String usd2Cny) {
		super();
		this.ymd = ymd;
		this.usd2Ntd = usd2Ntd;
		this.cny2Ntd = cny2Ntd;
		this.usd2Jpy = usd2Jpy;
		this.usd2Hkd = usd2Hkd;
		this.usd2Cny = usd2Cny;
	}

	public String getYmd() {
		return ymd;
	}

	public void setYmd(String ymd) {
		this.ymd = ymd;
	}

	public String getUsd2Ntd() {
		return usd2Ntd;
	}

	public void setUsd2Ntd(String usd2Ntd) {
		this.usd2Ntd = usd2Ntd;
	}

	public String getCny2Ntd() {
		return cny2Ntd;
	}

	public void setCny2Ntd(String cny2Ntd) {
		this.cny2Ntd = cny2Ntd;
	}

	public String getUsd2Jpy() {
		return usd2Jpy;
	}

	public void setUsd2Jpy(String usd2Jpy) {
		this.usd2Jpy = usd2Jpy;
	}

	public String getUsd2Hkd() {
		return usd2Hkd;
	}

	public void setUsd2Hkd(String usd2Hkd) {
		this.usd2Hkd = usd2Hkd;
	}

	public String getUsd2Cny() {
		return usd2Cny;
	}

	public void setUsd2Cny(String usd2Cny) {
		this.usd2Cny = usd2Cny;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExchangeRates [ymd=");
		builder.append(ymd);
		builder.append(", usd2Ntd=");
		builder.append(usd2Ntd);
		builder.append(", cny2Ntd=");
		builder.append(cny2Ntd);
		builder.append(", usd2Jpy=");
		builder.append(usd2Jpy);
		builder.append(", usd2Hkd=");
		builder.append(usd2Hkd);
		builder.append(", usd2Cny=");
		builder.append(usd2Cny);
		builder.append("]");
		return builder.toString();
	}
	
}

