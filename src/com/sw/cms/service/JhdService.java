package com.sw.cms.service;

import java.util.ArrayList;
import java.util.List;

import com.sw.cms.dao.JhdDAO;
import com.sw.cms.dao.RkdDAO;
import com.sw.cms.model.Jhd;
import com.sw.cms.model.JhdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.model.Rkd;
import com.sw.cms.model.RkdProduct;
import com.sw.cms.util.DateComFunc;

public class JhdService {
	private JhdDAO jhdDao;
	private RkdDAO rkdDao;
	
	/**
	 * 取进货单列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getJhdList(String con,int curPage, int rowsPerPage){
		return jhdDao.getJhdList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * 根据进货单ID取进货单详细信息
	 * @param id
	 * @return
	 */
	public Object getJhd(String id){
		return jhdDao.getJhd(id);
	}
	
	
	/**
	 * 保存进货单信息
	 * @param jhd
	 * @return
	 */
	public void saveJhd(Jhd jhd,List jhdProducts){

		jhd.setFklx("未付");
		
		//处理应付日期
		jhd.setYfrq(DateComFunc.formatDate(DateComFunc.addDay((DateComFunc.strToDate(jhd.getCg_date(), "yyyy-MM-dd")),jhd.getZq()),"yyyy-MM-dd"));
		
		//保存采购进货单相关信息
		jhdDao.saveJhd(jhd, jhdProducts);
		
		if(!jhd.getState().equals("已保存")){ //如果进货单状态为“已入库”或“已提交”	
			//生成入库单
			saveRkd(jhd,jhdProducts); 
		}
		
	}
	
	
	
	/**
	 * 修改进货单信息
	 * @param jhd
	 * @return
	 */
	public void updateJhd(Jhd jhd,List jhdProducts){

		jhd.setFklx("未付");
		
		jhd.setYfrq(DateComFunc.formatDate(DateComFunc.addDay((DateComFunc.strToDate(jhd.getCg_date(), "yyyy-MM-dd")),jhd.getZq()),"yyyy-MM-dd"));

		//更新进货单信息
		jhdDao.updateJhd(jhd, jhdProducts);
		
		if(!jhd.getState().equals("已保存")){ //如果进货单状态为“已入库”或“已提交”
			//生成入库单
			saveRkd(jhd,jhdProducts); 
		}
		
	}
	
	
	/**
	 * 根据进货单号取进货关联产品
	 * @param jhd_id
	 * @return
	 */
	public List getJhdProducts(String jhd_id){
		return jhdDao.getJhdProducts(jhd_id);
	}	
	
	
	/**
	 * 删除进货单信息
	 * @param id
	 * @return
	 */
	public void delJhd(String id){
		jhdDao.delJhd(id);
	}
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String updateJhdID() {
		return jhdDao.getJhdID();
	}
	
	
	
	/**
	 * 根据进货单编号查看进货单是否已经提交
	 * @param xsd_id
	 * @return
	 */
	public boolean isJhdSubmit(String jhd_id){
		return jhdDao.isJhdSubmit(jhd_id);
	}
	
	
	/**
	 * 生成相关入库单并保存
	 * @param jhd
	 * @param JhdProducts
	 */
	private void saveRkd(Jhd jhd,List jhdProducts){
		Rkd rkd = new Rkd();
		List rkdProducts = new ArrayList();
		
		String rkd_id = rkdDao.getRkdID();//当前可用入库单号
		
		rkd.setRkd_id(rkd_id); 
		rkd.setJhd_id(jhd.getId());
		rkd.setCreatdate(jhd.getCg_date());
		rkd.setState("已保存");
		
		rkd.setMs(jhd.getCg_date() + "进货单号 [" + jhd.getId() + "] " + jhd.getMs());
		rkd.setCgfzr(jhd.getFzr());
		rkd.setCzr(jhd.getCzr());
		rkd.setClient_name(jhd.getGysbh());
		rkd.setStore_id(jhd.getStore_id());
		
		
		
		if(jhdProducts != null && jhdProducts.size()>0){
			for(int i=0;i<jhdProducts.size();i++){
				
				JhdProduct jhdProduct = (JhdProduct)jhdProducts.get(i);
				if(jhdProduct != null){
					if(jhdProduct.getProduct_name() != null && !jhdProduct.getProduct_name().equals("")){
						RkdProduct rkdProduct = new RkdProduct();
						
						rkdProduct.setProduct_id(jhdProduct.getProduct_id());
						rkdProduct.setProduct_name(jhdProduct.getProduct_name());
						rkdProduct.setProduct_xh(jhdProduct.getProduct_xh());
						rkdProduct.setPrice(jhdProduct.getPrice());
						rkdProduct.setNums(jhdProduct.getNums());
						rkdProduct.setRkd_id(rkd_id);
						rkdProduct.setRemark(jhdProduct.getRemark());
						rkdProduct.setQz_serial_num(jhdProduct.getQz_serial_num());
						
						rkdProducts.add(rkdProduct);
					}
				}
			}
		}
		//保存入库单
		rkdDao.saveRkd(rkd, rkdProducts);
		
	}


	public JhdDAO getJhdDao() {
		return jhdDao;
	}


	public void setJhdDao(JhdDAO jhdDao) {
		this.jhdDao = jhdDao;
	}


	public RkdDAO getRkdDao() {
		return rkdDao;
	}


	public void setRkdDao(RkdDAO rkdDao) {
		this.rkdDao = rkdDao;
	}

}
