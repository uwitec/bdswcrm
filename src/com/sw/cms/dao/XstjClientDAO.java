package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;

public class XstjClientDAO extends JdbcBaseDAO {

	/**
	 * ȡ���۵��б�
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @return
	 */
	public List getXsdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		String sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,id,fzr,sjcjje as xsdje from xsd where state='�ѳ���'";
		if(!dj_id.equals("")){
			sql = sql + " and id='" + dj_id + "'";
		}			
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and fzr ='" + xsry_id + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and client_name = '" + client_name + "'";
		}
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * �������۵����ȡ������ϸ��Ϣ
	 * @param xsd_id
	 * @return
	 */
	public List getXsdMxList(String xsd_id){
		String sql = "select * from xsd_product where xsd_id='" + xsd_id + "'";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ���ݲ�ѯ����ȡ���۵��б�
	 * @param start_date ��ʼʱ��
	 * @param end_date  ��ֹʱ��
	 * @param client_id  �ͻ�����
	 * @return
	 */
	public List getLsdList(String start_date,String end_date,String xsry_id,String dj_id){
		String sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as creatdate,id,xsry,lsdje from lsd where state='���ύ'";
		if(!dj_id.equals("")){
			sql = sql + " and id='" + dj_id + "'";
		}		
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and xsry = '" + xsry_id + "'";
		}	
		return this.getResultList(sql);
	}
	
	
	/**
	 * �������۵����ȡ���۵�Ԫ������ϸ
	 * @param lsd_id
	 * @return
	 */
	public List getLsdMxList(String lsd_id){
		String sql = "select * from lsd_product where lsd_id='" + lsd_id + "'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ�˻����б�
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @return
	 */
	public List getThdList(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		String sql = "select DATE_FORMAT(cz_date,'%Y-%m-%d') as th_date,thd_id,th_fzr,thdje from thd where state='�����'";
		if(!dj_id.equals("")){
			sql = sql + " and thd_id='" + dj_id + "'";
		}
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and th_fzr = '" + xsry_id + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and client_name = '" + client_name + "'";
		}		
		return this.getResultList(sql);
	}
	
	
	/**
	 * �����˻������ȡ�˻�����ϸ
	 * @param thd_id
	 * @return
	 */
	public List getThdMxList(String thd_id){
		String sql = "select * from thd_product where thd_id='" + thd_id + "'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * ȡ�ͻ����ۻ����ܽ��
	 * �������۶������˻���
	 * �ܽ��=���۶����ϼƳɽ����-�˻������
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @param client_name
	 * @param dj_id
	 * @return
	 */
	public double getClientZje(String start_date,String end_date,String xsry_id,String client_name,String dj_id){
		double zje = 0;

		//���ۺϼƳɽ����
		double xsdje = 0;
		String sql = "select sum(sjcjje) as xsdje from xsd where state='�ѳ���'";
		if(!dj_id.equals("")){
			sql = sql + " and id='" + dj_id + "'";
		}			
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and fzr ='" + xsry_id + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and client_name = '" + client_name + "'";
		}
		List list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			xsdje = map.get("xsdje")==null?0:((Double)map.get("xsdje")).doubleValue();
		}
		
		//�˻����ϼƽ��
		double thdje = 0;
		sql = "select sum(thdje) as thdje from thd where state='�����'";
		if(!dj_id.equals("")){
			sql = sql + " and thd_id='" + dj_id + "'";
		}			
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and th_fzr ='" + xsry_id + "'";
		}
		if(!client_name.equals("")){
			sql = sql + " and client_name = '" + client_name + "'";
		}
		list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			thdje = map.get("thdje")==null?0:((Double)map.get("thdje")).doubleValue();
		}
		
		zje = xsdje - thdje;
		
		return zje;
	}
	
	
	/**
	 * �����������۵������Ϊһ����¼
	 * @param start_date
	 * @param end_date
	 * @param xsry_id
	 * @param dj_id
	 * @return
	 */
	public double getLsdZje(String start_date,String end_date,String xsry_id,String dj_id){
		double lsdje = 0;
		String sql = "select sum(lsdje) as lsdje from lsd where state='���ύ'";
		if(!dj_id.equals("")){
			sql = sql + " and id='" + dj_id + "'";
		}			
		if(!start_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql = sql + " and DATE_FORMAT(cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		if(!xsry_id.equals("")){
			sql = sql + " and xsry ='" + xsry_id + "'";
		}
		List list = this.getResultList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list .get(0);
			lsdje = map.get("lsdje")==null?0:((Double)map.get("lsdje")).doubleValue();
		}
		
		return lsdje;
	}
	
	
	/**
	 * �ͻ����ۻ���
	 * 2010-04-10
	 * @param start_date
	 * @param end_date
	 * @param client_name
	 * @param xsry_id
	 * @param dj_id
	 * @return
	 */
	public List getXstjClientResult(String start_date, String end_date,String client_name, String xsry_id, String dj_id){
		String sql = "select a.client_name as client_id,b.name as client_name,sum(hjje) as hjje from product_sale_flow a join clients b on b.id=a.client_name where (yw_type='���۵�' or yw_type='�˻���')";
		
		if(!start_date.equals("")){
			sql += " and a.cz_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and a.cz_date<='" + end_date + "'";
		}
		if(!client_name.equals("")){
			sql += " and a.client_name ='" + client_name + "'";
		}
		if(!xsry_id.equals("")){
			sql += " and a.xsry ='" + xsry_id + "'";
		}
		if(!dj_id.equals("")){
			sql += " and a.id ='" + dj_id + "'";
		}
		
		sql += " group by a.client_name,b.name";
		
		return this.getResultList(sql);
	}
	
}
