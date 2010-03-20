package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.HpflxsHzService;
import com.sw.cms.service.UserService;

/**
 * 货品分类销售汇总
 * @author liyt
 *
 */
public class HpflxsHzAction extends BaseAction {
	
	private HpflxsHzService hpflxsHzService;
	private DeptService deptService;
	
	private List resultList = new ArrayList();
	private List deptList = new ArrayList();
	 
	
	private String start_date = "";
	private String end_date = "";
	private int dj = 1;
	private String client_name = "";
	private String xsry = "";
	private String product_kind = "";
	private String dept = "";
	private String[] xwType;
	

	/**
	 * 显示条件
	 * @return
	 * @throws Exception
	 */
	public String showCondition()throws Exception{
		deptList = deptService.getDepts();
		return SUCCESS;
	}
	
	
	/**
	 * 统计结果
	 * @return
	 * @throws Exception
	 */
	public String getResults() throws Exception{
		try{
			resultList = hpflxsHzService.getHzResults(start_date,end_date,xsry,client_name,dept,xwType,dj);
			return SUCCESS;
		}catch(Exception e){
			log.error("货品销售分类汇总出错，错误原因：" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * 明细信息
	 * @return
	 * @throws Exception
	 */
	public String getMxResults() throws Exception{
		try{
			resultList = hpflxsHzService.getMxResults(product_kind, start_date, end_date, client_name,dept, xsry);
			return SUCCESS;
		}catch(Exception e){
			log.error("货品销售分类汇总明细信息出错，错误原因：" + e.getMessage());
			return ERROR;
		}
	}


	public HpflxsHzService getHpflxsHzService() {
		return hpflxsHzService;
	}


	public void setHpflxsHzService(HpflxsHzService hpflxsHzService) {
		this.hpflxsHzService = hpflxsHzService;
	}


	public DeptService getDeptService() {
		return deptService;
	}


	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}


	public List getResultList() {
		return resultList;
	}


	public void setResultList(List resultList) {
		this.resultList = resultList;
	}


	public List getDeptList() {
		return deptList;
	}


	public void setDeptList(List deptList) {
		this.deptList = deptList;
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


	public int getDj() {
		return dj;
	}


	public void setDj(int dj) {
		this.dj = dj;
	}


	public String getClient_name() {
		return client_name;
	}


	public void setClient_name(String clientName) {
		client_name = clientName;
	}


	public String getXsry() {
		return xsry;
	}


	public void setXsry(String xsry) {
		this.xsry = xsry;
	}


	public String getProduct_kind() {
		return product_kind;
	}


	public void setProduct_kind(String productKind) {
		product_kind = productKind;
	}


	public String getDept() {
		return dept;
	}


	public void setDept(String dept) {
		this.dept = dept;
	}


	public String[] getXwType() {
		return xwType;
	}


	public void setXwType(String[] xwType) {
		this.xwType = xwType;
	}

}
