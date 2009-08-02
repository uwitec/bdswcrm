package com.sw.cms.action;

import java.util.HashMap;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.service.GainTblService;
import com.sw.cms.util.DateComFunc;

/**
 * 月度利润表
 * @author liyt
 *
 */
public class MonthlyGainAction extends BaseAction {

	private GainTblService gainTblService;
	
	private String year = "";
	private String month = "";
	
	private Map zyywsrMap = new HashMap();  //主营业务收入
	private Map zyywcbMap = new HashMap();  //主营业务成本
	private Map qtywsrMap = new HashMap();  //其它业务收入
	private Map qtywcbMap = new HashMap();  //其它业务成本
	private Map yywsrMap = new HashMap();   //营业外收入
	private Map yywzcMap = new HashMap();   //营业外支出
	private Map spbysrMap = new HashMap();  //商品报溢收入
	private Map spbszcMap = new HashMap();  //商品报损支出
	private Map wltzsrMap = new HashMap();  //往来调帐收入
	private Map wltzzcMap = new HashMap();  //往来调账支出
	private Map dtfyMap = new HashMap();    //待摊费用
	
	
	
	/**
	 * 显示查询条件
	 * @return
	 */
	public String showCondition(){
		//默认当前月
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
	 * 统计结果
	 * @return
	 */
	public String getResults(){
		
		//默认当前月
		if(year.equals("") || month.equals("")){
			year = DateComFunc.getYear() + "";
			if(DateComFunc.getMonth()<10){
				month = "0" + DateComFunc.getMonth();
			}else{
				month =  "" + DateComFunc.getMonth();
			}
		}
		
		String ny = year + "-" + month;
		
		zyywsrMap = gainTblService.statZyywSr(ny);  //主营业务收入
		zyywcbMap = gainTblService.statZyywCb(ny);  //主营业务成本
		qtywsrMap = gainTblService.statQtywSr(ny);  //其它业务收入
		qtywcbMap = gainTblService.statQtywCb(ny);  //其它业务成本
		yywsrMap = gainTblService.statYywSr(ny);   //营业外收入
		yywzcMap = gainTblService.statYywZc(ny);   //营业外支出
		spbysrMap = gainTblService.statSpbySr(ny);  //商品报溢收入
		spbszcMap = gainTblService.statSpbsZc(ny);  //商品报损支出
		wltzsrMap = gainTblService.statWltzSr(ny);  //往来调帐收入
		wltzzcMap = gainTblService.statWltzZc(ny);  //往来调账支出
		dtfyMap = gainTblService.statDtfy(ny);    //待摊费用
		
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
