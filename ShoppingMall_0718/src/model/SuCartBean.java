package model;

// 카드에 담을 수작업공구 정보를 저장하는 자바빈클래스 DTO
public class SuCartBean {

	private int suno;
	private String suname;
	private String suimg;
	private int suprice;
	private int suqty;

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

	public int getSuqty() {
		return suqty;
	}

	public void setSuqty(int suqty) {
		this.suqty = suqty;
	}

}
