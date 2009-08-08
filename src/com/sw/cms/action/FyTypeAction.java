package com.sw.cms.action;

import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.FyType;
import com.sw.cms.service.FyTypeService;

public class FyTypeAction extends BaseAction {
	
	private FyTypeService fyTypeService;
	
	private String strTree = "";  //���ص������ַ�
	private String id = "";
	private String parent_id = "";
	private FyType fyType = new FyType();
	
	
	/**
	 * ������״���
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
			log.error("ȡ���������б����" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * �༭���������Ϣ
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
			log.error("�༭���������Ϣ����" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * ���·��������Ϣ
	 * @return
	 */
	public String update(){
		try{
			if(fyType.getId().length() == 2){
				this.setMsg("���ô��಻���޸�");
				return INPUT;
			}

			fyTypeService.updateFyType(fyType);
			return SUCCESS;
		}catch(Exception e){
			log.error("���·��������Ϣ����" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * ɾ�����������Ϣ����
	 * @return
	 */
	public String del(){
		try{
			if(id.length() == 2){
				this.setMsg("���ô��಻��ɾ����");
				return SUCCESS;
			}
			if(fyTypeService.isChildren(id)){
				this.setMsg("�������������𣬲���ɾ����");
				return SUCCESS;
			}
			fyTypeService.delFyType(id);
			
			return SUCCESS;
		}catch(Exception e){
			log.error("ɾ�����������Ϣ����" + e.getMessage());
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
