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
	 * ȡ�������б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getJhdList(String con,int curPage, int rowsPerPage){
		return jhdDao.getJhdList(con, curPage, rowsPerPage);
	}
	
	
	/**
	 * ���ݽ�����IDȡ��������ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public Object getJhd(String id){
		return jhdDao.getJhd(id);
	}
	
	
	/**
	 * �����������Ϣ
	 * @param jhd
	 * @return
	 */
	public void saveJhd(Jhd jhd,List jhdProducts){

		jhd.setFklx("δ��");
		
		//����Ӧ������
		jhd.setYfrq(DateComFunc.formatDate(DateComFunc.addDay((DateComFunc.strToDate(jhd.getCg_date(), "yyyy-MM-dd")),jhd.getZq()),"yyyy-MM-dd"));
		
		//����ɹ������������Ϣ
		jhdDao.saveJhd(jhd, jhdProducts);
		
		if(!jhd.getState().equals("�ѱ���")){ //���������״̬Ϊ������⡱�����ύ��	
			//������ⵥ
			saveRkd(jhd,jhdProducts); 
		}
		
	}
	
	
	
	/**
	 * �޸Ľ�������Ϣ
	 * @param jhd
	 * @return
	 */
	public void updateJhd(Jhd jhd,List jhdProducts){

		jhd.setFklx("δ��");
		
		jhd.setYfrq(DateComFunc.formatDate(DateComFunc.addDay((DateComFunc.strToDate(jhd.getCg_date(), "yyyy-MM-dd")),jhd.getZq()),"yyyy-MM-dd"));

		//���½�������Ϣ
		jhdDao.updateJhd(jhd, jhdProducts);
		
		if(!jhd.getState().equals("�ѱ���")){ //���������״̬Ϊ������⡱�����ύ��
			//������ⵥ
			saveRkd(jhd,jhdProducts); 
		}
		
	}
	
	
	/**
	 * ���ݽ�������ȡ����������Ʒ
	 * @param jhd_id
	 * @return
	 */
	public List getJhdProducts(String jhd_id){
		return jhdDao.getJhdProducts(jhd_id);
	}	
	
	
	/**
	 * ɾ����������Ϣ
	 * @param id
	 * @return
	 */
	public void delJhd(String id){
		jhdDao.delJhd(id);
	}
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String updateJhdID() {
		return jhdDao.getJhdID();
	}
	
	
	
	/**
	 * ���ݽ�������Ų鿴�������Ƿ��Ѿ��ύ
	 * @param xsd_id
	 * @return
	 */
	public boolean isJhdSubmit(String jhd_id){
		return jhdDao.isJhdSubmit(jhd_id);
	}
	
	
	/**
	 * ���������ⵥ������
	 * @param jhd
	 * @param JhdProducts
	 */
	private void saveRkd(Jhd jhd,List jhdProducts){
		Rkd rkd = new Rkd();
		List rkdProducts = new ArrayList();
		
		String rkd_id = rkdDao.getRkdID();//��ǰ������ⵥ��
		
		rkd.setRkd_id(rkd_id); 
		rkd.setJhd_id(jhd.getId());
		rkd.setCreatdate(jhd.getCg_date());
		rkd.setState("�ѱ���");
		
		rkd.setMs(jhd.getCg_date() + "�������� [" + jhd.getId() + "] " + jhd.getMs());
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
		//������ⵥ
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
