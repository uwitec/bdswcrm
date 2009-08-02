package com.sw.cms.action;

import java.util.HashMap;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.GainTblService;
import com.sw.cms.util.DateComFunc;

/**
 * �¶������
 * @author liyt
 *
 */
public class MonthlyGainAction extends BaseAction {

	private GainTblService gainTblService;
	
	private String year = "";
	private String month = "";
	
	private Map zyywsrMap = new HashMap();  //��Ӫҵ������
	private Map zyywcbMap = new HashMap();  //��Ӫҵ��ɱ�
	private Map qtywsrMap = new HashMap();  //����ҵ������
	private Map qtywcbMap = new HashMap();  //����ҵ��ɱ�
	private Map yywsrMap = new HashMap();   //Ӫҵ������
	private Map yywzcMap = new HashMap();   //Ӫҵ��֧��
	private Map spbysrMap = new HashMap();  //��Ʒ��������
	private Map spbszcMap = new HashMap();  //��Ʒ����֧��
	private Map wltzsrMap = new HashMap();  //������������
	private Map wltzzcMap = new HashMap();  //��������֧��
	private Map dtfyMap = new HashMap();    //��̯����
	
	
	
	/**
	 * ��ʾ��ѯ����
	 * @return
	 */
	public String showCondition(){
		//Ĭ�ϵ�ǰ��
		if(year.equals("") || month.equals("")){
			year = DateComFunc.getYear() + "";
			if(DateComFunc.getMonth()<10){
				month = "0" + DateComFunc.getMonth();
			}else{
				month =  "" + DateComFunc.getMonth();
			}
		}
		
		return "success";
	}
	
	/**
	 * ͳ�ƽ��
	 * @return
	 */
	public String getResults(){
		
		//Ĭ�ϵ�ǰ��
		if(year.equals("") || month.equals("")){
			year = DateComFunc.getYear() + "";
			if(DateComFunc.getMonth()<10){
				month = "0" + DateComFunc.getMonth();
			}else{
				month =  "" + DateComFunc.getMonth();
			}
		}
		
		String ny = year + "-" + month;
		
		zyywsrMap = gainTblService.statZyywSr(ny);  //��Ӫҵ������
		zyywcbMap = gainTblService.statZyywCb(ny);  //��Ӫҵ��ɱ�
		qtywsrMap = gainTblService.statQtywSr(ny);  //����ҵ������
		qtywcbMap = gainTblService.statQtywCb(ny);  //����ҵ��ɱ�
		yywsrMap = gainTblService.statYywSr(ny);   //Ӫҵ������
		yywzcMap = gainTblService.statYywZc(ny);   //Ӫҵ��֧��
		spbysrMap = gainTblService.statSpbySr(ny);  //��Ʒ��������
		spbszcMap = gainTblService.statSpbsZc(ny);  //��Ʒ����֧��
		wltzsrMap = gainTblService.statWltzSr(ny);  //������������
		wltzzcMap = gainTblService.statWltzZc(ny);  //��������֧��
		dtfyMap = gainTblService.statDtfy(ny);    //��̯����
		
		return "success";
	}

	public GainTblService getGainTblService() {
		return gainTblService;
	}

	public void setGainTblService(GainTblService gainTblService) {
		this.gainTblService = gainTblService;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Map getDtfyMap() {
		return dtfyMap;
	}

	public void setDtfyMap(Map dtfyMap) {
		this.dtfyMap = dtfyMap;
	}

	public Map getQtywcbMap() {
		return qtywcbMap;
	}

	public void setQtywcbMap(Map qtywcbMap) {
		this.qtywcbMap = qtywcbMap;
	}

	public Map getQtywsrMap() {
		return qtywsrMap;
	}

	public void setQtywsrMap(Map qtywsrMap) {
		this.qtywsrMap = qtywsrMap;
	}

	public Map getSpbszcMap() {
		return spbszcMap;
	}

	public void setSpbszcMap(Map spbszcMap) {
		this.spbszcMap = spbszcMap;
	}

	public Map getSpbysrMap() {
		return spbysrMap;
	}

	public void setSpbysrMap(Map spbysrMap) {
		this.spbysrMap = spbysrMap;
	}

	public Map getWltzsrMap() {
		return wltzsrMap;
	}

	public void setWltzsrMap(Map wltzsrMap) {
		this.wltzsrMap = wltzsrMap;
	}

	public Map getWltzzcMap() {
		return wltzzcMap;
	}

	public void setWltzzcMap(Map wltzzcMap) {
		this.wltzzcMap = wltzzcMap;
	}

	public Map getYywsrMap() {
		return yywsrMap;
	}

	public void setYywsrMap(Map yywsrMap) {
		this.yywsrMap = yywsrMap;
	}

	public Map getYywzcMap() {
		return yywzcMap;
	}

	public void setYywzcMap(Map yywzcMap) {
		this.yywzcMap = yywzcMap;
	}

	public Map getZyywcbMap() {
		return zyywcbMap;
	}

	public void setZyywcbMap(Map zyywcbMap) {
		this.zyywcbMap = zyywcbMap;
	}

	public Map getZyywsrMap() {
		return zyywsrMap;
	}

	public void setZyywsrMap(Map zyywsrMap) {
		this.zyywsrMap = zyywsrMap;
	}
	
}
