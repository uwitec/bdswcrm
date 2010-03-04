package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.Page;
import com.sw.cms.model.Bwl;
import com.sw.cms.service.BwlService;
import com.sw.cms.util.Constant;

public class BwlAction extends BaseAction {
	
	private BwlService bwlService;
	
	private Page bwlPage;
	private Bwl bwl;
	
	private String id = "";
	private int curPage = 1;
	
	
	/**
	 * ȡ����¼��Ϣ�б�
	 * @return
	 */
	public String list(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		
		int rowsPerPage = Constant.PAGE_SIZE2;
		bwlPage = bwlService.getBwlList(curPage, rowsPerPage,user_id);
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
	 * ���汸��¼
	 * @return
	 */
	public String save(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		bwl.setCzr(user_id);
		
		bwlService.saveBwl(bwl);
		return "success";
	}
	
	
	/**
	 * ���±���¼
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		bwl.setCzr(user_id);
		
		bwlService.updateBwl(bwl);
		return "success";
	}
	
	
	/**
	 * �༭����¼
	 * @return
	 */
	public String edit(){
		bwl = bwlService.getBwl(id);
		return "success";
	}
	
	
	/**
	 * ɾ������¼
	 * @return
	 */
	public String del(){
		bwlService.delBwl(id);
		return "success";
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Page getBwlPage() {
		return bwlPage;
	}

	public void setBwlPage(Page bwlPage) {
		this.bwlPage = bwlPage;
	}

	public Bwl getBwl() {
		return bwl;
	}

	public void setBwl(Bwl bwl) {
		this.bwl = bwl;
	}

	public BwlService getBwlService() {
		return bwlService;
	}

	public void setBwlService(BwlService bwlService) {
		this.bwlService = bwlService;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

}
