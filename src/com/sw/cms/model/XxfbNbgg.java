package com.sw.cms.model;

import java.sql.Timestamp;
import java.util.Date;


/**
 * 信息发布内部公告
 * @author liyt
 *
 */

public class XxfbNbgg {
	
	private String id;
	private String title;
	private String content;
	private String pub_date;
	private String czr;
	private Timestamp cz_date;
	private String type;
	private String parent_id;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCzr() {
		return czr;
	}
	public void setCzr(String czr) {
		this.czr = czr;
	}
	public String getPub_date() {
		return pub_date;
	}
	public void setPub_date(String pub_date) {
		this.pub_date = pub_date;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParent_id() {
		return parent_id;
	}
	public void setParent_id(String parentId) {
		parent_id = parentId;
	}
	public Timestamp getCz_date() {
		return cz_date;
	}
	public void setCz_date(Timestamp czDate) {
		cz_date = czDate;
	}
	
}
