package model;

import java.sql.Date;

public class SuBean { // dto // db에서 검색한 수작업공구들을 레코드단위로 저장

	private int suno;
	private String suname;
	private String suinfo;
	private String suimg;
	private int suprice;
	private Date sudate;
	private String sutemp;
	private String sucate;
	
	public int getSuno() {
		return suno;
	}
	public void setSuno(int suno) {
		this.suno = suno;
	}
	public String getSuname() {
		return suname;
	}
	public void setSuname(String suname) {
		this.suname = suname;
	}
	public String getSuinfo() {
		return suinfo;
	}
	public void setSuinfo(String suinfo) {
		this.suinfo = suinfo;
	}
	public String getSuimg() {
		return suimg;
	}
	public void setSuimg(String suimg) {
		this.suimg = suimg;
	}
	public int getSuprice() {
		return suprice;
	}
	public void setSuprice(int suprice) {
		this.suprice = suprice;
	}
	public Date getSudate() {
		return sudate;
	}
	public void setSudate(Date sudate) {
		this.sudate = sudate;
	}
	public String getSutemp() {
		return sutemp;
	}
	public void setSutemp(String sutemp) {
		this.sutemp = sutemp;
	}
	public String getSucate() {
		return sucate;
	}
	public void setSucate(String sucate) {
		this.sucate = sucate;
	}
	
	
	
}
