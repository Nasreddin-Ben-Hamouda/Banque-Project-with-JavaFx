package entities;

import java.util.Date;
public class Card {
   private long confCode;
   private long internetCode;
   private long dabCode;
   private Date expringDate;

public Card() {}
public Card(long confCode, long internetCode,long  dabCode,Date expringDate) {
	this.confCode=confCode;
	this.internetCode=internetCode;
	this.dabCode=dabCode;
	this.expringDate=expringDate;
	
}
	public Date getExpirigDate() {
		return this.expringDate;
	}
	public void setExpiringDate(Date d) {
		this.expringDate=d;
	}
	public long getConfCode() {
		return confCode;
	}

	public void setConfCode(long confCode) {
		this.confCode = confCode;
	}
	
	public long getInternetCode() {
		return internetCode;
	}
	
	public void setInternetCode(long internetCode) {
		this.internetCode = internetCode;
	}
	
	public long getDabCode() {
		return dabCode;
	}
	
	public void setDabCode(long dabCode) {
		this.dabCode = dabCode;
	}
   
   
}
