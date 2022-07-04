package com.example.demo.Model;


public class Product {
	
	
	private int prodid;
	private String prodcode;
	private Double prodcost;
	
	private Double proddisc;
	private Double prodgst;
	public int getProdid() {
		return prodid;
	}
	public void setProdid(int prodid) {
		this.prodid = prodid;
	}
	public String getProdcode() {
		return prodcode;
	}
	public void setProdcode(String prodcode) {
		this.prodcode = prodcode;
	}
	public Double getProdcost() {
		return prodcost;
	}
	public void setProdcost(Double prodcost) {
		this.prodcost = prodcost;
	}
	public Double getProddisc() {
		return proddisc;
	}
	public void setProddisc(Double proddisc) {
		this.proddisc = proddisc;
	}
	public Double getProdgst() {
		return prodgst;
	}
	public void setProdgst(Double prodgst) {
		this.prodgst = prodgst;
	}
	public Product(int prodid, String prodcode, Double prodcost, Double proddisc, Double prodgst) {
		super();
		this.prodid = prodid;
		this.prodcode = prodcode;
		this.prodcost = prodcost;
		this.proddisc = proddisc;
		this.prodgst = prodgst;
	}
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Product [prodid=" + prodid + ", prodcode=" + prodcode + ", prodcost=" + prodcost + ", proddisc="
				+ proddisc + ", prodgst=" + prodgst + "]";
	}
	
	

}
