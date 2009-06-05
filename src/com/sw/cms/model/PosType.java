package com.sw.cms.model;

import java.util.Date;

/**
 * 刷卡Pos机设定
 * @author liyt
 *
 */
public class PosType {
	
	private int id;           //编号
	private String name;         //名称
	private String type;         //类型（1:固定金额，2:固定扣款率，3:固定扣款率(封顶)）
	private double mbfy;         //每笔费用
	private double fl;           //费率
	private double fdfy;         //封顶费用
	private String account_id;   //对应账记编号
	private String remark;       //备注
	private String czr;          //操作人
	private Date cz_date;        //操作时间
	
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public double getFdfy() {
		return fdfy;
	}
	public void setFdfy(double fdfy) {
		this.fdfy = fdfy;
	}
	public double getFl() {
		return fl;
	}
	public void setFl(double fl) {
		this.fl = fl;
	}
	public double getMbfy() {
		return mbfy;
	}
	public void setMbfy(double mbfy) {
		this.mbfy = mbfy;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getCz_date() {
		return cz_date;
	}
	public void setCz_date(Date cz_date) {
		this.cz_date = cz_date;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}
