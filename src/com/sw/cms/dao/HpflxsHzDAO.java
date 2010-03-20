package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.JdbcBaseDAO;

/**
 * ��Ʒ�������ۻ���
 * @author liyt
 *
 */
public class HpflxsHzDAO extends JdbcBaseDAO {

	
	/**
	 * ����Ʒ���ȼ�ͳ�������������
	 * @param start_date
	 * @param end_date
	 * @param dj
	 * @return
	 */
	public List getHzResults(String start_date,String end_date,String xsry,String client_name,String dept,String[] xwType,int dj){
		
		String con = "";
		if(!start_date.equals("")){
			con += " and b.cz_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			con += " and b.cz_date<='" + end_date + "'"; 
		}
		if(!xsry.equals("")){
			con += " and b.xsry='" + xsry + "'";
		}
		if(!client_name.equals("")){
			con += " and b.client_name='" + client_name + "'"; 
		}
		if(!dept.equals("")){
			con += " and b.dept like '" + dept + "%'";
		}
		if(xwType == null || xwType.length == 0){
			return null;
		}else{
			con += " and(";
			for(int i=0;i<xwType.length;i++){
				if(i==0){
					con += "yw_type='" + xwType[i] + "'";
				}else{
					con += " or yw_type='" + xwType[i] + "'";
				}
			}
			con += ")";
		}
		
		String sql = "select a.id,a.name," +
				"(select sum(nums) from view_hpxshz_tj b where b.product_kind like concat(a.id,'%') " + con + ") as nums," +
				"(select sum(hjje) from view_hpxshz_tj b where b.product_kind like concat(a.id,'%') " + con + ") as hjje" +
				" from product_kind a where LENGTH(id)<=" + (dj*2);
		
		List list = this.getResultList(sql);
		
		return list;
	}
	
	
	
	/**
	 * ���ݻ�Ʒ���۷��������ϸ��Ϣ
	 * @param product_kind   ��Ʒ���
	 * @param start_date     ��ʼʱ��
	 * @param end_date       ����ʱ��
	 * @param client_name    �ͻ�����
	 * @param xsry_id        ������Ա
	 * @return
	 */
	public List getMxResults(String product_kind,String start_date,String end_date,String client_name,String dept,String xsry){
		
		String sql = "select product_id,product_name,product_xh,sum(nums) as nums,sum(hjje) as hjje from view_hpxshz_tj where 1=1";
		
		//������Ʒ���
		if(!product_kind.equals("")){
			sql += " and product_kind like '" + product_kind + "%'";
		}
		
		//��ʼʱ��
		if(!start_date.equals("")){
			sql += " and cz_date>='" + start_date + "'";
		}
		
		//����ʱ��
		if(!end_date.equals("")){
			sql += " and cz_date<='" + end_date + "'";
		}
		
		//�ͻ����
		if(!client_name.equals("")){
			sql += " and client_name='" + client_name + "'";
		}
		
		//������Ա
		if(!xsry.equals("")){
			sql += " and xsry='" + xsry + "'";
		}
		
		if(!dept.equals("")){
			sql += " and dept like '" + dept + "%'";
		}
		
		sql += " group by product_id,product_name,product_xh";
		
		return this.getResultList(sql);
	}
	
}
