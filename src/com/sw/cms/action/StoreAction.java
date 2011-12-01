package com.sw.cms.action;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Page;
import com.sw.cms.model.StoreHouse;
import com.sw.cms.service.StoreService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;

public class StoreAction extends BaseAction {
	
	private StoreService storeService;
	
	private StoreHouse storeHouse;
	private Page storePage;
	
	private String orderName ="";
	private String orderType ="";
	
	private int curPage = 1;
	
	
	/**
	 * ���زֿ��б�����ҳ��
	 * @return
	 */
	public String list(){
		
		String con = "";
		
		int rowsPerPage = Constant.PAGE_SIZE2;
		
		if(orderName.equals("")){
			orderName = "id";
		}
		if(orderType.equals("")){
			orderType = "desc";
		}
		
		con += " order by " + orderName + " " + orderType;
		
		storePage = storeService.getStores(con, curPage, rowsPerPage);
		
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
	 * ����ֿ���Ϣ
	 * @return
	 */
	public String save(){
		storeService.saveStore(storeHouse);
		return "success";
	}
	
	
	
	/**
	 * �鿴�ֿ���Ϣ
	 * @return
	 */
	public String edit(){
		String id = ParameterUtility.getStringParameter(getRequest(),"id", "");
		storeHouse = (StoreHouse)storeService.getStore(id);
		return "success";
	}
	
	
	
	/**
	 * ���²ֿ���Ϣ
	 * @return
	 */
	public String update(){
		storeService.updateStore(storeHouse);
		return "success";
	}
	
	
	
	/**
	 * ɾ���ֿ���Ϣ
	 * @return
	 */
	public String del(){
		String id = ParameterUtility.getStringParameter(getRequest(),"id", "");
		storeService.delStore(id);
		return "success";
	}
	
	public void checkStoreCanDel(){
		String id = ParameterUtility.getStringParameter(getRequest(),"id", "");
		if(!storeService.isCanDel(id)){
			this.writeStringToResponse("false");
		}else{
			this.writeStringToResponse("true");
		}
	}
	

	public void setStoreService(StoreService storeService) {
		this.storeService = storeService;
	}

	public StoreHouse getStoreHouse() {
		return storeHouse;
	}

	public void setStoreHouse(StoreHouse storeHouse) {
		this.storeHouse = storeHouse;
	}

	public Page getStorePage() {
		return storePage;
	}

	public void setStorePage(Page storePage) {
		this.storePage = storePage;
	}


	public int getCurPage() {
		return curPage;
	}


	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}


	public String getOrderName() {
		return orderName;
	}


	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}


	public String getOrderType() {
		return orderType;
	}


	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


	public StoreService getStoreService() {
		return storeService;
	}

}
