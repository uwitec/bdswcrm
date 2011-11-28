package com.sw.cms.action;

import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.XxfbNbgg;
import com.sw.cms.service.XxfbNbggService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.DateComFunc;

public class XxfbNbggAction extends BaseAction {
	
	private XxfbNbggService xxfbNbggService;
	
	private Page nbggPage;
	private XxfbNbgg xxfbNbgg;
	private List xxfbNbggList;
	
	private String id = "";
	private int curPage = 1;
	
	private String content;
	private String parent_id;
	
	private String q_con = "";
	private String start_date = "";
	private String end_date = "";
	private String czr = "";
	
	
	/**
	 * ȡ�ڲ�������Ϣ�б�
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;
		
		String con = "";
		if(!start_date.equals("")){
			con += " and a.pub_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			con += " and a.pub_date<='" + (end_date + " 23:59:59") + "'";
		}
		if(!q_con.equals("")){
			con += " and (a.title like'%" + q_con + "%' or a.content like '%" + q_con + "%')";
		}
		if(!czr.equals("")){
			con += " and b.real_name like'%" + czr + "%'";
		}
		nbggPage = xxfbNbggService.getNbggList(con, curPage, rowsPerPage);
		return "success";
	}
	
	
	/**
	 * �����ҳ��
	 * @return
	 */
	public String add(){
		return "success";
	}
	
	
	/**
	 * �����ڲ�����
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		xxfbNbgg.setCzr(user_id);
		
		xxfbNbggService.saveNbgg(xxfbNbgg);
		return "success";
	}
	
	
	/**
	 * ����ظ���Ϣ
	 * @return
	 */
	public String saveReplay(){
		try{
			LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
			String user_id = info.getUser_id();
			
			XxfbNbgg xxfbNbgg = new XxfbNbgg();
			xxfbNbgg.setContent(content);
			xxfbNbgg.setCzr(user_id);
			xxfbNbgg.setParent_id(parent_id);
			xxfbNbgg.setPub_date(DateComFunc.getToday());
			
			xxfbNbggService.saveNbgg(xxfbNbgg);
			content = "";
			return SUCCESS;
		}catch(Exception e){
			log.error("����ظ���Ϣʧ�ܣ�ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * �����ڲ�����
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		xxfbNbgg.setCzr(user_id);
		
		xxfbNbggService.updateNbgg(xxfbNbgg);
		return "success";
	}
	
	
	/**
	 * �༭�ڲ�����
	 * @return
	 */
	public String edit(){
		xxfbNbgg = xxfbNbggService.getNbgg(id);
		return "success";
	}
	
	
	/**
	 * ɾ���ڲ�����
	 * @return
	 */
	public String del(){
		xxfbNbggService.delNbgg(id);
		return "success";
	}
	
	
	/**
	 * �鿴�ڲ����棬�����ظ�����
	 * @return
	 */
	public String view(){
		try{
			xxfbNbgg = xxfbNbggService.getNbgg(id);
			xxfbNbggList = xxfbNbggService.getNbggListByParentId(id);
			return SUCCESS;
		}catch(Exception e){
			log.error("�򿪲鿴�ڲ�����鿴����ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Page getNbggPage() {
		return nbggPage;
	}

	public void setNbggPage(Page nbggPage) {
		this.nbggPage = nbggPage;
	}

	public XxfbNbgg getXxfbNbgg() {
		return xxfbNbgg;
	}

	public void setXxfbNbgg(XxfbNbgg xxfbNbgg) {
		this.xxfbNbgg = xxfbNbgg;
	}

	public XxfbNbggService getXxfbNbggService() {
		return xxfbNbggService;
	}

	public void setXxfbNbggService(XxfbNbggService xxfbNbggService) {
		this.xxfbNbggService = xxfbNbggService;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}


	public List getXxfbNbggList() {
		return xxfbNbggList;
	}


	public void setXxfbNbggList(List xxfbNbggList) {
		this.xxfbNbggList = xxfbNbggList;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getParent_id() {
		return parent_id;
	}


	public void setParent_id(String parentId) {
		parent_id = parentId;
	}


	public String getQ_con() {
		return q_con;
	}


	public void setQ_con(String qCon) {
		q_con = qCon;
	}


	public String getStart_date() {
		return start_date;
	}


	public void setStart_date(String startDate) {
		start_date = startDate;
	}


	public String getEnd_date() {
		return end_date;
	}


	public void setEnd_date(String endDate) {
		end_date = endDate;
	}


	public String getCzr() {
		return czr;
	}


	public void setCzr(String czr) {
		this.czr = czr;
	}

}
