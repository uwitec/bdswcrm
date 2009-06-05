package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.DataDisParseBaseAction;
import com.sw.cms.model.LoginInfo;
import com.sw.cms.model.PosType;
import com.sw.cms.service.PosTypeService;

public class PosTypeAction extends DataDisParseBaseAction {
	
	private PosTypeService posTypeService;
	
	private PosType posType = new PosType();
	List posTypeList = new ArrayList();
	
	private String id = "";
	
	/**
	 * 列表页面
	 * @return
	 */
	public String list(){
		posTypeList = posTypeService.getPosTypeList();
		return "success";
	}
	
	
	/**
	 * 编辑页面
	 * @return
	 */
	public String edit(){
		if(!id.equals("")){
			posType = posTypeService.getPosType(id);
		}
		return "success";
	}
	
	
	/**
	 * 更新信息
	 * @return
	 */
	public String update(){
		LoginInfo info = (LoginInfo)getSession().getAttribute("LOGINUSER");
		String user_id = info.getUser_id();
		posType.setCzr(user_id);
		
		posTypeService.updatePosType(posType);
		
		return "success";
	}
	
	
	/**
	 * 删除
	 * @return
	 */
	public String del(){
		posTypeService.delPosType(id);
		return "success";
	}
	

	public PosTypeService getPosTypeService() {
		return posTypeService;
	}

	public void setPosTypeService(PosTypeService posTypeService) {
		this.posTypeService = posTypeService;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PosType getPosType() {
		return posType;
	}

	public void setPosType(PosType posType) {
		this.posType = posType;
	}

	public List getPosTypeList() {
		return posTypeList;
	}

	public void setPosTypeList(List posTypeList) {
		this.posTypeList = posTypeList;
	}

}
