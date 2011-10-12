package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.HpflxsHzService;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.StringUtils;

/**
 * ��Ʒ�������ۻ���
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
	
	String c_end_date = "";
	String c_start_date = "";

	/**
	 * ��ʾ����
	 * @return
	 * @throws Exception
	 */
	public String showCondition()throws Exception{
		deptList = deptService.getDepts();
		return SUCCESS;
	}
	
	
	/**
	 * ͳ�ƽ��
	 * @return
	 * @throws Exception
	 */
	public String getResults() throws Exception{
		try{
			resultList = hpflxsHzService.getHzResults(start_date,end_date,xsry,client_name,dept,xwType,dj);
			return SUCCESS;
		}catch(Exception e){
			log.error("��Ʒ���۷�����ܳ�������ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	
	/**
	 * ��Ʒ���۷�����ܣ���״ͼ����Ĭ��ʱ�䵱��
	 * @throws Exception
	 */
	public void getResultsForChart() throws Exception{
		try{
			resultList = hpflxsHzService.getHzResults(c_start_date,c_end_date,"","","", new String[]{"���۵�","���۵�","�˻���"},1);
			String strResult = "";
			double hjje = 0;
			if(resultList != null && resultList.size()>0){
				for(int i=0;i<resultList.size();i++){
					Map map = (Map)resultList.get(i);
					hjje += map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
				}
			}
			if(resultList != null && resultList.size()>0){
				for(int i=0;i<resultList.size();i++){
					Map map = (Map)resultList.get(i);
					
					String id = StringUtils.nullToStr(map.get("id"));
					String name = StringUtils.nullToStr(map.get("name"));
					double je = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
					strResult += "<set label='" + name + "' value='" + JMath.round(je) + "' toolText='" + name + ":" + JMath.round(je,2) + "Ԫ:ռ��" + JMath.percent(je, hjje) + "'/>";
				}
			}
			this.writeStringToResponse(strResult);
		}catch(Exception e){
			log.error("��Ʒ���۷�����ܳ�������ԭ��" + e.getMessage());
		}
	}
	
	
	/**
	 * ��ϸ��Ϣ
	 * @return
	 * @throws Exception
	 */
	public String getMxResults() throws Exception{
		try{
			resultList = hpflxsHzService.getMxResults(product_kind, start_date, end_date, client_name,dept, xsry);
			return SUCCESS;
		}catch(Exception e){
			log.error("��Ʒ���۷��������ϸ��Ϣ��������ԭ��" + e.getMessage());
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


	public String getC_start_date() {
		return c_start_date;
	}


	public void setC_start_date(String cStartDate) {
		c_start_date = cStartDate;
	}


	public String getC_end_date() {
		return c_end_date;
	}


	public void setC_end_date(String cEndDate) {
		c_end_date = cEndDate;
	}

}
