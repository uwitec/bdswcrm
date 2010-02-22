package com.sw.cms.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sw.cms.action.base.BaseAction;
import com.sw.cms.model.Page;
import com.sw.cms.model.Shkc;
import com.sw.cms.service.ShkcService;
import com.sw.cms.util.Constant;
import com.sw.cms.util.ParameterUtility;

public class ShkcAction extends BaseAction
{
	private ShkcService shkcService;
    private Page pageShkc;
    private int curPage=1;
    private String  qz_serial_num="";
    private String  product_name="";
    private String  state="";
    private String  id;
    private Shkc shkc =new Shkc();
    private List serialFlowList=new ArrayList();
    
  
	/**
     * 售后库存列表
     * @return
     * @throws Exception
     */
    public String list()throws Exception
    {
    	try
    	{
    		int rowsPerPage = Constant.PAGE_SIZE2;
    		String con="";
    		if(!qz_serial_num.trim().equals(""))
    		{
    			con+=" and a.qz_serial_num='"+qz_serial_num+"'";
    		}
    		if(!product_name.trim().equals(""))
    		{
    			con+=" and a.product_name like '%"+product_name+"%'";
    		}
    		if(!state.trim().equals(""))
    		{
    			con+=" and a.state='"+state+"'";
    		}
    		pageShkc=shkcService.getShkcProduct(con, curPage, rowsPerPage);
    		return "success";
    	}
    	catch(Exception e)
    	{
    		log.error("返还售后库存类表  错误原因："+e.getMessage());
    		return "error";
    	}
    }
    
    /**
     * 打开售后库存视图
     * @return
     * @throws Exception
     */
    public String edit()throws Exception
    {
    	try
    	{
    	   String id = ParameterUtility.getStringParameter(getRequest(), "id","");
    	   shkc=(Shkc)shkcService.getShkcById(id);
    	   return "success";
    	}
    	catch(Exception e)
    	{
    		log.error("打开售后库存视图  错误原因："+e.getMessage());
    		return "error";   		
    	}
    }
    
    public String getSerialFlow()
    {
		if(!qz_serial_num.equals(""))
		{			 
			serialFlowList = shkcService.getSerialFlow(qz_serial_num);
		}
		return "success";
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getQz_serial_num() {
		return qz_serial_num;
	}

	public void setQz_serial_num(String qz_serial_num) {
		this.qz_serial_num = qz_serial_num;
	}

 

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public ShkcService getShkcService() 
	{
		return shkcService;
	}

	public void setShkcService(ShkcService shkcService) 
	{
		this.shkcService = shkcService;
	}

	public Page getPageShkc() {
		return pageShkc;
	}

	public void setPageShkc(Page pageShkc) {
		this.pageShkc = pageShkc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public Shkc getShkc() {
		return shkc;
	}

	public void setShkc(Shkc shkc) {
		this.shkc = shkc;
	}

	public List getSerialFlowList() {
		return serialFlowList;
	}

	public void setSerialFlowList(List serialFlowList) {
		this.serialFlowList = serialFlowList;
	}
	 
}
