package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.XxfbNbgg;
import com.sw.cms.service.XxfbNbggService;
import com.sw.cms.util.Constant;

public class XxfbNbggAction extends BaseAction {
	
	private XxfbNbggService xxfbNbggService;
	
	private Page nbggPage;
	private XxfbNbgg xxfbNbgg;
	
	private String id = "";
	private int curPage = 1;
	
	
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

}
