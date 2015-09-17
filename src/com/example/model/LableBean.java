package com.example.model;

public class LableBean {
	private int id;
	private String name;
	private int imgID;
	private String utl;
	
	public LableBean(){}

	public LableBean(int id,int icLauncher) {
		this.id = id;
		this.imgID = icLauncher;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getImgID() {
		return imgID;
	}

	public void setImgID(int imgID) {
		this.imgID = imgID;
	}

	public String getUtl() {
		return utl;
	}

	public void setUtl(String utl) {
		this.utl = utl;
	}

}
