package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * ��Ʒ���к����ۻ���
 * @author liyt
 *
 */
public class ProductSerialNumXsHzDAO extends JdbcBaseDAO {
	
	/**
	 * ���ݲ�ѯ�������������������к�
	 * @param start_date  ��ʼʱ��
	 * @param end_date    ����ʱ��
	 * @param product_kind ��Ʒ���
	 * @param product_name ��Ʒ����
	 * @param dept_id      ���ű��
	 * @param xsry_id      ������Ա���
	 * @param clientId �ͻ�����
	 * @return
	 */
	public List getSerialNumXsList(String start_date,String end_date,String product_kind,
								   String product_name,String dept_id,String xsry_name,String clientId){
	
		String sql = "SELECT a.fs_date,a.serial_num,b.product_name,b.product_xh,a.client_name,a.tel,a.jsr,a.xsdj " +
					 "FROM  serial_num_flow a inner join serial_num_mng b on b.serial_num=a.serial_num " +
					 "left join product c on c.product_id=b.product_id where (a.ywtype='����' or a.ywtype='����')";
		
		if(!start_date.equals("")){
			sql += " and a.fs_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and a.fs_date<='" + (end_date + " 23:59:59") + "'";
		}
		if(!product_kind.equals("")){
			sql += " and c.product_kind like '" + product_kind + "%'";
		}
		if(!product_name.equals("")){
			sql += " and b.product_name like '%" + product_name + "%'";
		}
		if(!xsry_name.equals("")){
			sql += " and a.jsr='" + xsry_name + "'";
		}
		if(!clientId.equals("")){
			sql += " and a.client_name='" + clientId + "'";
		}
		
		sql += " order by a.fs_date";
		
		return this.getResultList(sql);
	}

}
