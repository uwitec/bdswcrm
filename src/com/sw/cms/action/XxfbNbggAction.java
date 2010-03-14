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
	 * 取内部公告信息列表
	 * @return
	 */
	public String list(){
		int rowsPerPage = Constant.PAGE_SIZE2;
		nbggPage = xxfbNbggService.getNbggList(curPage, rowsPerPage);
		return "success";
	}
	
	
	/**
	 * 打开添加页面
	 * @return
	 */
	public String add(){
		return "success";
	}
	
	
	/**
	 * 保存内部公告
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
	 * 保存回复信息
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
			log.error("保存回复信息失败，原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * 更新内部公告
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
	 * 编辑内部公告
	 * @return
	 */
	public String edit(){
		xxfbNbgg = xxfbNbggService.getNbgg(id);
		return "success";
	}
	
	
	/**
	 * 删除内部公告
	 * @return
	 */
	public String del(){
		xxfbNbggService.delNbgg(id);
		return "success";
	}
	
	
	/**
	 * 查看内部公告，包括回复内容
	 * @return
	 */
	public String view(){
		try{
			xxfbNbgg = xxfbNbggService.getNbgg(id);
			xxfbNbggList = xxfbNbggService.getNbggListByParentId(id);
			return SUCCESS;
		}catch(Exception e){
			log.error("打开查看内部公告查看错误，原因：" + e.getMessage());
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
