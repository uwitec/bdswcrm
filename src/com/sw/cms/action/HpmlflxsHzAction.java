package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.ClientsService;
import com.sw.cms.service.DeptService;
import com.sw.cms.service.HpmlflxsHzService;
import com.sw.cms.service.UserService;
import com.sw.cms.util.JMath;
import com.sw.cms.util.StringUtils;

/**
 * ��Ʒë���������ۻ���
 * @author zuohj
 *
 */
public class HpmlflxsHzAction extends BaseAction {
	
	private HpmlflxsHzService hpmlflxsHzService;
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
			resultList = hpmlflxsHzService.getHzResults(start_date,end_date,xsry,client_name,dept,dj);
			return SUCCESS;
		}catch(Exception e){
			log.error("��Ʒ����ë��������ܳ�������ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	
	
	/**
	 * ��ϸ��Ϣ
	 * @return
	 * @throws Exception
	 */
	public String getMxResults() throws Exception{
		try{
			resultList = hpmlflxsHzService.getMxResults(product_kind, start_date, end_date, client_name,dept, xsry);
			return SUCCESS;
		}catch(Exception e){
			log.error("��Ʒ����ë�����������ϸ��Ϣ��������ԭ��" + e.getMessage());
			return ERROR;
		}
	}
	
	/**
	 * ajax��Ʒë���������
	 */
	public void queryHpmlflhz(){
		try{
			resultList = hpmlflxsHzService.getHzResults(start_date,end_date,"","","",1);
			
			String strResult = "";
			double hjml = 0;
			if(resultList != null && resultList.size()>0){
				for(int i=0;i<resultList.size();i++){
					Map map = (Map)resultList.get(i);
					double hjje = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
					double hjcb = map.get("hjcb")==null?0:((Double)map.get("hjcb")).doubleValue();
					hjml += hjje - hjcb;
				}
			}
			if(resultList != null && resultList.size()>0){
				for(int i=0;i<resultList.size();i++){
					Map map = (Map)resultList.get(i);
					
					String id = StringUtils.nullToStr(map.get("id"));
					String name = StringUtils.nullToStr(map.get("name"));
					double hjje = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue();
					double hjcb = map.get("hjcb")==null?0:((Double)map.get("hjcb")).doubleValue();
					double ml = hjje - hjcb;
					strResult += "<set label='" + name + "' value='" + JMath.round(ml) + "' toolText='" + name + ":" + JMath.round(ml,2) + "Ԫ:ռ��" + JMath.percent(ml, hjml) + "'/>";
				}
			}
			this.writeStringToResponse(strResult);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public HpmlflxsHzService getHpmlflxsHzService() {
		return hpmlflxsHzService;
	}


	public void setHpmlflxsHzService(HpmlflxsHzService hpmlflxsHzService) {
		this.hpmlflxsHzService = hpmlflxsHzService;
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

}
