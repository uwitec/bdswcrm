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
		
		String sql = "select c.product_kind,sum(nums) as nums,sum(hjje) as hjje from product_sale_flow a inner join sys_user b on b.user_id=a.xsry inner join product c on c.product_id=a.product_id where 1=1";
		
		if(!start_date.equals("")){
			sql += " and a.cz_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and a.cz_date<='" + end_date + "'"; 
		}
		if(!xsry.equals("")){
			sql += " and a.xsry='" + xsry + "'";
		}
		if(!client_name.equals("")){
			sql += " and a.client_name='" + client_name + "'"; 
		}
		if(!dept.equals("")){
			sql += " and b.dept like '" + dept + "%'";
		}
		if(xwType == null || xwType.length == 0){
			return null;
		}else{
			sql += " and(";
			for(int i=0;i<xwType.length;i++){
				if(i==0){
					sql += "a.yw_type='" + xwType[i] + "'";
				}else{
					sql += " or a.yw_type='" + xwType[i] + "'";
				}
			}
			sql += ")";
		}
		sql += " group by c.product_kind";
		
		sql = "select y.id,y.name," +
				"(select sum(nums) from (" + sql + ") x where x.product_kind like concat(y.id,'%')) as nums," +
				"(select sum(hjje) from (" + sql + ") x where x.product_kind like concat(y.id,'%')) as hjje " +
				"from product_kind y where LENGTH(y.id)<=" + (dj*2);
		
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
		
		String sql = "select a.product_id,c.product_name,c.product_xh,sum(a.nums) as nums,sum(a.hjje) as hjje from product_sale_flow a left join sys_user b on b.user_id=a.xsry left join product c on c.product_id=a.product_id where 1=1";
		
		//������Ʒ���
		if(!product_kind.equals("")){
			sql += " and c.product_kind like '" + product_kind + "%'";
		}
		
		//��ʼʱ��
		if(!start_date.equals("")){
			sql += " and a.cz_date>='" + start_date + "'";
		}
		
		//����ʱ��
		if(!end_date.equals("")){
			sql += " and a.cz_date<='" + end_date + "'";
		}
		
		//�ͻ����
		if(!client_name.equals("")){
			sql += " and a.client_name='" + client_name + "'";
		}
		
		//������Ա
		if(!xsry.equals("")){
			sql += " and a.xsry='" + xsry + "'";
		}
		
		if(!dept.equals("")){
			sql += " and b.dept like '" + dept + "%'";
		}
		
		sql += " group by a.product_id,c.product_name,c.product_xh";
		
		return this.getResultList(sql);
	}
	
}
