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
	
	
	/**
	 * ȡ�ڲ�������Ϣ�б�
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;
		nbggPage = xxfbNbggService.getNbggList(curPage, rowsPerPage);
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

}
