package com.sw.cms.action;

import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.FyType;
import com.sw.cms.service.FyTypeService;

public class FyTypeAction extends BaseAction {
	
	private FyTypeService fyTypeService;
	
	private String strTree = "";  //返回的树形字符
	private String id = "";
	private String parent_id = "";
	private FyType fyType = new FyType();
	
	
	/**
	 * 生成树状类别
	 * @return
	 */
	public String list(){
		try{
			List list = fyTypeService.getFyTypeList();
			
			if(list != null && list.size() > 0){
				for(int i=0;i<list.size();i++){
					FyType fyType = (FyType)list.get(i);
					
					strTree += "add_item(\"" + fyType.getId() + "\",\"" + fyType.getParent_id() + "\",\"" + fyType.getName() + "\");\n";
				}
			}
			return SUCCESS;
		}catch(Exception e){
			log.error("取费用类型列表出错：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * 编辑费用类别信息
	 * @return
	 */
	public String edit(){
		try{
			if(!id.equals("")){
				fyType = fyTypeService.getFyType(id);
			}else{
				fyType.setParent_id(parent_id);
			}
			return SUCCESS;
		}catch(Exception e){
			log.error("编辑费用类别信息出错：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * 更新费用类别信息
	 * @return
	 */
	public String update(){
		try{
			if(fyType.getId().length() == 2){
				this.setMsg("费用大类不能修改");
				return INPUT;
			}

			fyTypeService.updateFyType(fyType);
			return SUCCESS;
		}catch(Exception e){
			log.error("更新费用类别信息出错：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * 删除费用类别信息出错
	 * @return
	 */
	public String del(){
		try{
			if(id.length() == 2){
				this.setMsg("费用大类不能删除！");
				return SUCCESS;
			}
			if(fyTypeService.isChildren(id)){
				this.setMsg("该类别下有子类别，不能删除！");
				return SUCCESS;
			}
			fyTypeService.delFyType(id);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("删除费用类别信息出错：" + e.getMessage());
			return ERROR;
		}
	}
	

	public FyTypeService getFyTypeService() {
		return fyTypeService;
	}

	public void setFyTypeService(FyTypeService fyTypeService) {
		this.fyTypeService = fyTypeService;
	}

	public String getStrTree() {
		return strTree;
	}

	public void setStrTree(String strTree) {
		this.strTree = strTree;
	}


	public FyType getFyType() {
		return fyType;
	}


	public void setFyType(FyType fyType) {
		this.fyType = fyType;
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


	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

}
