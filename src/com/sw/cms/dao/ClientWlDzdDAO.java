package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * �ͻ��������˵�
 * �б��ֶΰ�����(���ڡ�ҵ�����͡����ݺš���Ʒ���ơ���Ʒ���������Ӧ�տ�����տ��ĩӦ�ա�Ӧ����ɹ������ĩӦ��)
 * @author liyt
 * add ad 2008-09-15
 */
public class ClientWlDzdDAO extends JdbcBaseDAO {
	
	
	/**
	 * ���ݲ�ѯ�����ͻ��������˵��б�
	 * �������ۡ��˻����ɹ����ɹ��˻��������տ�ɹ�����
	 * �б��ֶΰ�����(���ڡ�ҵ�����͡����ݺš����(����ҵ���������ֽ��������λ��))
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getClientWlDzdList(String start_date,String end_date,String client_name){
		String sql = "";
		
		//���۵��б�
		String xsd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'����' as xwtype,id as dj_id,sjcjje as je,cz_date from xsd where state='�ѳ���'";
		if(!start_date.equals("")){
			xsd_sql = xsd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xsd_sql = xsd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			xsd_sql = xsd_sql + " and client_name = '" + client_name + "'";
		}
		
		//�����˻�
		String thd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'�����˻�' as xwtype,thd_id as dj_id,(0-thdje) as je,cz_date from thd where state='�����'";
		if(!start_date.equals("")){
			thd_sql = thd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			thd_sql = thd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			thd_sql = thd_sql + " and client_name = '" + client_name + "'";
		}
		
		//�����տ�
		String xssk_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'�տ�' as xwtype,id as dj_id,skje as je,cz_date from xssk where state='���ύ'";
		if(!start_date.equals("")){
			xssk_sql = xssk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			xssk_sql = xssk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			xssk_sql = xssk_sql + " and client_name = '" + client_name + "'";
		}
		
		//�ɹ�������
		String jhd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'�ɹ�' as xwtype,id as dj_id,total as je,cz_date from jhd where state='�����'";
		if(!start_date.equals("")){
			jhd_sql = jhd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			jhd_sql = jhd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			jhd_sql = jhd_sql + " and gysbh = '" + client_name + "'";
		}
		
		//�ɹ��˻���
		String cgthd_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'�ɹ��˻�' as xwtype,id as dj_id,(0-tkzje) as je,cz_date from cgthd where state='�ѳ���'";
		if(!start_date.equals("")){
			cgthd_sql = cgthd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			cgthd_sql = cgthd_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			cgthd_sql = cgthd_sql + " and provider_name = '" + client_name + "'";
		}
		
		//�ɹ�����
		String cgfk_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'����' as xwtype,id as dj_id,fkje as je,cz_date from cgfk where (state='���ύ' or state='��֧��')";
		if(!start_date.equals("")){
			cgfk_sql = cgfk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			cgfk_sql = cgfk_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			cgfk_sql = cgfk_sql + " and gysbh = '" + client_name + "'";
		}
		
		
		//��������(Ӧ��)
		String wltz_ys_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'��������(Ӧ��)' as xwtype,id as dj_id,pzje as je,cz_date from pz where state='���ύ' and type='Ӧ��'";
		if(!start_date.equals("")){
			wltz_ys_sql = wltz_ys_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			wltz_ys_sql = wltz_ys_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			wltz_ys_sql = wltz_ys_sql + " and client_name = '" + client_name + "'";
		}
		
		
		//�������ˣ�Ӧ����
		String wltz_yf_sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,'��������(Ӧ��)' as xwtype,id as dj_id,pzje as je,cz_date from pz where state='���ύ' and type='Ӧ��'";
		if(!start_date.equals("")){
			wltz_yf_sql = wltz_yf_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			wltz_yf_sql = wltz_yf_sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!client_name.equals("")){
			wltz_yf_sql = wltz_yf_sql + " and client_name = '" + client_name + "'";
		}
		
		
		
		sql = "select * from ((" + xsd_sql + ") union (" + thd_sql + ") union (" + xssk_sql + ") union(" + jhd_sql + ") union(" + cgthd_sql + ") union(" + cgfk_sql + ") union (" + wltz_ys_sql + ") union(" + wltz_yf_sql + ")) m order by cz_date asc";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ��ȡ������ϸ��Ϣ
	 * @param dj_id ���ݱ��
	 * @param cdid  �ӵ����
	 * @param xwtype ҵ������
	 * @return
	 */
	public List getDjMxList(String dj_id,String xwtype){
		String sql = "";
		
		if(xwtype.equals("����")){
			sql = "select product_id,product_name,product_xh,sjcj_nums,((price+jgtz) * sjcj_nums) as xj from xsd_product where xsd_id='" + dj_id + "'";
		}else if(xwtype.equals("�����˻�")){
			sql = "select product_id,product_name,product_xh,nums,(0-th_price * nums) as xj from thd_product where thd_id='" + dj_id + "'";
		}else if(xwtype.equals("�ɹ�")){
			sql = "select  product_id,product_name,product_xh,nums,(price*nums) as xj from jhd_product where jhd_id='" + dj_id + "'";
		}else if(xwtype.equals("�ɹ��˻�")){
			sql = "select product_id,product_name,product_xh,nums,(0-th_price*nums) as xj from cgthd_product where cgthd_id='" + dj_id + "'";
		}
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ�ͻ��ڳ���Ϣ
	 * @param client_name
	 * @param qc_date
	 * @return
	 */
	public Map getClientQcInfo(String client_name,String cdate){
		Map map = null;
		String sql = "select * from client_qc where client_name='" + client_name + "' and cdate='" + cdate + "'";		
		List list = this.getResultList(sql);
		if(list != null && list.size()>0){
			map = (Map)list.get(0);
		}
		return map;
	}
	

}
