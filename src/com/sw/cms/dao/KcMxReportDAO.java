package com.sw.cms.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.util.StringUtils;

/**
 * ���ͳ��
 * @author liyt
 *
 */

public class KcMxReportDAO extends JdbcBaseDAO {

	/**
	 * ����ѯ����ȡ����Ʒ�б�
	 * @param product_kind ��Ʒ���
	 * @param product_name ��Ʒ����
	 * @param store_id    �ⷿ���
	 * @return
	 */
	public List getProductKcList(String product_kind,String product_name,String store_id){
		String sql = "";
		
		if(!store_id.equals("")){
			sql = "select * from (select b.*,a.nums as kc_nums,a.store_id  from product_kc a left join product b on b.product_id=a.product_id where b.prop='�����Ʒ' and a.store_id='" + store_id + "') as kc_table where 1=1";
		}else{
			sql = "select * from (select a.*,(select sum(nums) from product_kc b where b.product_id=a.product_id) as kc_nums,'' as store_id from product a where a.prop='�����Ʒ') as kc_table where 1=1";
		}
		
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");
			
			if(arryItems != null && arryItems.length >0){
				sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						sql += " product_kind like '" + arryItems[i] + "%'";
					}else{
						sql += " or product_kind like '" + arryItems[i] + "%'";
					}
				}
				sql += ")";
			}
			
		}
		if(!product_name.equals("")){
			sql = sql + " and product_name like '%" + product_name + "%'";
		}
		
		return this.getResultList(sql);
	}
	
	
	
	/**
	 * ȡ����ڳ������Ϣ
	 * @param product_id
	 * @param cdate
	 * @param store_id
	 * @return map
	 */
	public Map getKcqcMxMap(String product_id,String cdate,String store_id){
		Map map = null;
		String sql = "";
		if(!store_id.equals("")){
			sql = "select product_id,sum(nums) as nums,price from product_kc_qc where product_id='" + product_id + "' and store_id='" + store_id + "' and cdate='" + cdate + "' group by product_id,store_id,price";
		}else{
			sql = "select product_id,sum(nums) as nums,price from product_kc_qc where product_id='" + product_id +"' and cdate='" + cdate + "' group by product_id,price";
		}
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			map = (Map)list.get(0);
		}
		return map;
	}
	
	
	/**
	 * ȡ���仯��ϸ
	 * �������ⵥ����ⵥ���ⷿ����
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @return
	 */
	public List getKcbhMxList(String product_id,String start_date,String end_date,String store_id){
		
		String sql = "";
		
		//��ⵥ
		String sql_rk = "select b.jhd_id as dj_id,b.rkd_id as cd_id,a.product_id,a.product_name," +
				"a.product_xh,a.nums,a.price," +
				"DATE_FORMAT(b.cz_date,'%Y-%m-%d') as fsrq,'���' as flag,b.cz_date,b.client_name " +
				"from rkd_product a left join rkd b on b.rkd_id=a.rkd_id where b.state='�����'";
		if(!product_id.equals("")){
			sql_rk = sql_rk + " and a.product_id='" + product_id + "'";
		}
		if(!store_id.equals("")){
			sql_rk = sql_rk + " and b.store_id='" + store_id + "'";
		}
		if(!start_date.equals("")){
			sql_rk = sql_rk + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql_rk = sql_rk + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		
		//���ⵥ
		String sql_ck = "select b.xsd_id as dj_id,b.ckd_id as cd_id, a.product_id,a.product_name,a.product_xh,a.nums,a.price," +
				"DATE_FORMAT(b.cz_date,'%Y-%m-%d') as fsrq,'����' as flag,b.cz_date,b.client_name " +
				"from ckd_product a left join ckd b on b.ckd_id=a.ckd_id where b.state='�ѳ���'";
		if(!product_id.equals("")){
			sql_ck = sql_ck + " and a.product_id='" + product_id + "'";
		}
		if(!store_id.equals("")){
			sql_ck = sql_ck + " and b.store_id='" + store_id + "'";
		}
		if(!start_date.equals("")){
			sql_ck = sql_ck + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql_ck = sql_ck + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		
		//�ⷿ����
		String sql_db = "select dj_id,'' as cd_id,product_id,product_name,product_xh,nums,price,ck_date as fsrq,flag,cz_date,'' as client_name from (" +
				"(select a.kfdb_id as dj_id,a.product_id,a.product_name,a.product_xh, a.nums,c.price,b.ck_store_id as store_id,DATE_FORMAT(b.cz_date,'%Y-%m-%d') as ck_date,'������' as type,'����' as flag,b.cz_date from kfdb_product a left join kfdb b on b.id=a.kfdb_id left join product c on c.product_id=a.product_id where b.state='�ѳ���') " +
				"union " +
				"(select a.kfdb_id as dj_id,a.product_id,a.product_name,a.product_xh,a.nums as nums,c.price,b.rk_store_id as store_id,DATE_FORMAT(b.cz_date,'%Y-%m-%d') as ck_date,'������' as type,'���' as flag,b.cz_date from kfdb_product a left join kfdb b on b.id=a.kfdb_id left join product c on c.product_id=a.product_id where b.state='�ѳ���')) " +
				"z where 1=1";
		if(!product_id.equals("")){
			sql_db = sql_db + " and product_id='" + product_id + "'";
		}
		if(!store_id.equals("")){
			sql_db = sql_db + " and store_id='" + store_id + "'";
		}
		if(!start_date.equals("")){
			sql_db = sql_db + " and ck_date>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql_db = sql_db + " and ck_date<='" + (end_date + " 23:59:59") + "'";
		}
		
		//����̵�
		String sql_pd = "select a.pd_id as dj_id,'' as cd_id,a.product_id,a.product_name,a.product_xh,a.yk as nums,0 as price,b.pdrq as fsrq,'����' as flag,b.cz_date,'' as client_name from kcpd_desc a left join kcpd b on b.id=a.pd_id where b.state='���ύ'";
		if(!product_id.equals("")){
			sql_pd = sql_pd + " and a.product_id='" + product_id + "'";
		}
		if(!store_id.equals("")){
			sql_pd = sql_pd + " and b.store_id='" + store_id + "'";
		}
		if(!start_date.equals("")){
			sql_pd = sql_pd + " and b.pdrq>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql_pd = sql_pd + " and b.pdrq<='" + (end_date + " 23:59:59") + "'";
		}
		
		sql = "select * from ((" + sql_rk + ") union all (" + sql_ck + ") union all (" + sql_db + ") union all (" + sql_pd + ")) x order by cz_date asc"; 
		
		return this.getResultList(sql);
		
	}
	
	
	
	/**
	 * ���ɱ��仯���
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	public List getKcCbBhMxList(String product_id,String start_date,String end_date){
		
		String sql = "";
		
		//��ⵥ
		String sql_rk = "select b.jhd_id as dj_id,b.rkd_id as cd_id,a.product_id,a.product_name,a.product_xh,a.nums,a.price,DATE_FORMAT(b.cz_date,'%Y-%m-%d') as fsrq,'���' as flag,b.cz_date,b.client_name from rkd_product a left join rkd b on b.rkd_id=a.rkd_id where b.state='�����'";
		if(!product_id.equals("")){
			sql_rk = sql_rk + " and a.product_id='" + product_id + "'";
		}
		if(!start_date.equals("")){
			sql_rk = sql_rk + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql_rk = sql_rk + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		
		//���ⵥ
		String sql_ck = "select b.xsd_id as dj_id,b.ckd_id as cd_id, a.product_id,a.product_name,a.product_xh,a.nums,a.price,DATE_FORMAT(b.cz_date,'%Y-%m-%d') as fsrq,'����' as flag,b.cz_date,b.client_name from ckd_product a left join ckd b on b.ckd_id=a.ckd_id where b.state='�ѳ���'";
		if(!product_id.equals("")){
			sql_ck = sql_ck + " and a.product_id='" + product_id + "'";
		}
		if(!start_date.equals("")){
			sql_ck = sql_ck + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql_ck = sql_ck + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		
		//����̵�
		String sql_pd = "select a.pd_id as dj_id,'' as cd_id,a.product_id,a.product_name,a.product_xh,a.yk as nums,0 as price,b.pdrq as fsrq,'����' as flag,b.cz_date,'' as client_name from kcpd_desc a left join kcpd b on b.id=a.pd_id where b.state='���ύ'";
		if(!product_id.equals("")){
			sql_pd = sql_pd + " and a.product_id='" + product_id + "'";
		}
		if(!start_date.equals("")){
			sql_pd = sql_pd + " and b.pdrq>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql_pd = sql_pd + " and b.pdrq<='" + (end_date + " 23:59:59") + "'";
		}
		
		sql = "select * from ((" + sql_rk + ") union all (" + sql_ck + ") union all(" + sql_pd + ")) x order by cz_date asc"; 
		
		return this.getResultList(sql);
		
	}	
	
	
	/**
	 * ȡ�������
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @return
	 */
	public int getRkNums(String product_id,String start_date,String end_date,String store_id){
		int rkNums = 0;
		
		//��ⵥ�������
		String sql_rk = "select sum(a.nums) as rk_nums from rkd_product a left join rkd b on b.rkd_id=a.rkd_id where b.state='�����'";
		if(!product_id.equals("")){
			sql_rk = sql_rk + " and a.product_id='" + product_id + "'";
		}
		if(!store_id.equals("")){
			sql_rk = sql_rk + " and b.store_id='" + store_id + "'";
		}
		if(!start_date.equals("")){
			sql_rk = sql_rk + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql_rk = sql_rk + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		Map rkMap = this.getResultMap(sql_rk);
		if(!StringUtils.nullToStr(rkMap.get("rk_nums")).equals("")){
			rkNums += new Integer(StringUtils.nullToStr(rkMap.get("rk_nums"))).intValue();
		}
		
		
		//�������������
		String sql_db = "select sum(a.nums) as rk_nums from kfdb_product a left join kfdb b on b.id=a.kfdb_id where b.state='�ѳ���'";
		
		if(!product_id.equals("")){
			sql_db = sql_db + " and a.product_id='" + product_id + "'";
		}
		if(!store_id.equals("")){
			sql_db = sql_db + " and b.rk_store_id='" + store_id + "'";
		}
		if(!start_date.equals("")){
			sql_db = sql_db + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql_db = sql_db + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		Map dbMap = this.getResultMap(sql_db);
		if(!StringUtils.nullToStr(dbMap.get("rk_nums")).equals("")){
			rkNums += new Integer(StringUtils.nullToStr(dbMap.get("rk_nums"))).intValue();
		}
		
		//����̵㱨������
		String sql_kcpd = "select sum(yk) as by_nums from kcpd_desc a left join kcpd b on b.id=a.pd_id where b.state='���ύ' and a.yk>0";
		if(!product_id.equals("")){
			sql_kcpd = sql_kcpd + " and a.product_id='" + product_id + "'";
		}
		if(!store_id.equals("")){
			sql_kcpd = sql_kcpd + " and b.store_id='" + store_id + "'";
		}
		if(!start_date.equals("")){
			sql_kcpd = sql_kcpd + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql_kcpd = sql_kcpd + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		Map pdMap = this.getResultMap(sql_kcpd);
		if(!StringUtils.nullToStr(pdMap.get("by_nums")).equals("")){
			rkNums += new Integer(StringUtils.nullToStr(pdMap.get("by_nums"))).intValue();
		}
		
		return rkNums;
	}
	
	
	/**
	 * ȡ��������
	 * @param product_id
	 * @param start_date
	 * @param end_date
	 * @param store_id
	 * @return
	 */
	public int getCkNums(String product_id,String start_date,String end_date,String store_id){
		int ckNums = 0;
		
		//���ⵥ��������
		String sql_ck = "select sum(a.nums) as ck_nums from ckd_product a left join ckd b on b.ckd_id=a.ckd_id where b.state='�ѳ���'";
		if(!product_id.equals("")){
			sql_ck = sql_ck + " and a.product_id='" + product_id + "'";
		}
		if(!store_id.equals("")){
			sql_ck = sql_ck + " and b.store_id='" + store_id + "'";
		}
		if(!start_date.equals("")){
			sql_ck = sql_ck + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql_ck = sql_ck + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		Map ckMap = this.getResultMap(sql_ck);
		if(!StringUtils.nullToStr(ckMap.get("ck_nums")).equals("")){
			ckNums += new Integer(StringUtils.nullToStr(ckMap.get("ck_nums"))).intValue();
		}
		
		
		//��������������
		String sql_db = "select sum(a.nums) as ck_nums from kfdb_product a left join kfdb b on b.id=a.kfdb_id where b.state='�ѳ���'";
		
		if(!product_id.equals("")){
			sql_db = sql_db + " and a.product_id='" + product_id + "'";
		}
		if(!store_id.equals("")){
			sql_db = sql_db + " and b.ck_store_id='" + store_id + "'";
		}
		if(!start_date.equals("")){
			sql_db = sql_db + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql_db = sql_db + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		Map dbMap = this.getResultMap(sql_db);
		if(!StringUtils.nullToStr(dbMap.get("ck_nums")).equals("")){
			ckNums += new Integer(StringUtils.nullToStr(dbMap.get("ck_nums"))).intValue();
		}
		
		//����̵㱨������
		String sql_kcpd = "select sum(0-yk) as bs_nums from kcpd_desc a left join kcpd b on b.id=a.pd_id where b.state='���ύ' and a.yk<0";
		if(!product_id.equals("")){
			sql_kcpd = sql_kcpd + " and a.product_id='" + product_id + "'";
		}
		if(!store_id.equals("")){
			sql_kcpd = sql_kcpd + " and b.store_id='" + store_id + "'";
		}
		if(!start_date.equals("")){
			sql_kcpd = sql_kcpd + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql_kcpd = sql_kcpd + " and DATE_FORMAT(b.cz_date,'%Y-%m-%d')<='" + (end_date + " 23:59:59") + "'";
		}
		Map pdMap = this.getResultMap(sql_kcpd);
		if(!StringUtils.nullToStr(pdMap.get("bs_nums")).equals("")){
			ckNums += new Integer(StringUtils.nullToStr(pdMap.get("bs_nums"))).intValue();
		}
		
		return ckNums;
	}
	
	
	/**
	 * �������(�����)����
	 * @param product_kind   ��Ʒ���
	 * @param product_name   �̿�����
	 * @param store_id       �ⷿ���
	 * @param state          �Ƿ���ʾͣ����Ʒ
	 * @param flag           �Ƿ���ʾ0�����Ʒ
	 * @return
	 */
	public List getKcNumsResults(String product_kind,String product_name,String store_id,String state,String flag){
		String sql = "select a.ms, a.product_id,a.product_name,a.product_xh,a.price,a.khcbj,a.lsbj,a.lsxj,a.fxbj,a.fxxj,a.gf,a.dss," +
				"(select sum(b.nums) as hj_nums from product_kc b where b.product_id=a.product_id";
		
		if(!store_id.equals("")){
			sql += " and b.store_id='" + store_id + "'";
		}
		
		sql += ") as kc_nums from product a where prop='�����Ʒ'";
		
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");
			
			if(arryItems != null && arryItems.length >0){
				sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						sql += " a.product_kind like '" + arryItems[i] + "%'";
					}else{
						sql += " or a.product_kind like '" + arryItems[i] + "%'";
					}
				}
				sql += ")";
			}
			
		}
		
		
		if(!product_name.equals("")){
			sql += " and a.product_name like '%" + product_name + "%'";
		}
		if(state.equals("��")){
			sql += " and state='����'";
		}
		
		if(flag.equals("��")){
			sql += " having kc_nums>0";
		}
		return this.getResultList(sql);
	}

	
	/**
	 * ��ʷ������ѯ
	 * @param product_kind  ��Ʒ���
	 * @param product_name  ��Ʒ����
	 * @param product_xh    ��Ʒ���
	 * @param store_id      �ⷿ���
	 * @param cdate         �������
	 * @return
	 */
	public List getHisKcjeResults(String product_kind,String product_name,String product_xh,String store_id,String cdate){
		String sql = "select b.product_id,b.product_xh,b.product_name,a.price as price,sum(a.nums) as nums,sum(a.price*a.nums) as hjje " +
				"from (SELECT * FROM product_kc_qc where cdate='" + cdate + "'";
		
		if(!store_id.equals("")){
			sql += " and store_id='" + store_id + "'";
		}
		
		sql += ") a join product b on b.product_id=a.product_id where b.prop='�����Ʒ'";
		
		
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");
			
			if(arryItems != null && arryItems.length >0){
				sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						sql += " b.product_kind like '" + arryItems[i] + "%'";
					}else{
						sql += " or b.product_kind like '" + arryItems[i] + "%'";
					}
				}
				sql += ")";
			}
			
		}
		
		
		if(!product_name.equals("")){
			sql += " and b.product_name like '%" + product_name + "%'";
		}
		
		if(!product_xh.equals("")){
			sql += " and b.product_xh like '%" + product_xh + "%'";
		}
		
		sql += " group by b.product_id,b.product_xh,b.product_name,a.price";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * �����������(��Ӧ��)
	 * @param product_kind   ��Ʒ���
	 * @param product_name   �̿�����
	 * @param store_id       �ⷿ���
	 * @param state          �Ƿ���ʾͣ����Ʒ
	 * @param flag           �Ƿ���ʾ0�����Ʒ
	 * @return
	 */
	public List getGysKcNumsResults(String product_kind){
		String sql = "select a.product_id,a.product_name,a.product_xh,a.price,a.khcbj,a.lsbj,a.lsxj,a.fxbj,a.fxxj,a.gf,a.dss," +
				"(select sum(b.nums) as hj_nums from product_kc b where b.product_id=a.product_id) as kc_nums from product a where prop='�����Ʒ'";
		
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");
			
			if(arryItems != null && arryItems.length >0){
				sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						sql += " a.product_kind like '" + arryItems[i] + "%'";
					}else{
						sql += " or a.product_kind like '" + arryItems[i] + "%'";
					}
				}
				sql += ")";
			}
			
		}

		sql += " and state='����' having kc_nums>0";

		return this.getResultList(sql);
	}
	
	
	
	/**
	 * �ֲֿ���������ܣ���Ʒ�б�
	 * @param product_kind
	 * @param product_name
	 * @param state
	 * @return
	 */
	public List getProductList(String product_kind,String product_name,String state){
		
		String sql = "select product_id,product_name,product_xh,dw from product where 1=1";
		
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");
			
			if(arryItems != null && arryItems.length >0){
				sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						sql += " product_kind like '" + arryItems[i] + "%'";
					}else{
						sql += " or product_kind like '" + arryItems[i] + "%'";
					}
				}
				sql += ")";
			}
			
		}
		
		if(!product_name.equals("")){
			sql = sql + " and (product_name like '%" + product_name + "%' or product_xh like '%" + product_name + "%')";
		}
		
		if(state.equals("��")){
			sql = sql + " and state='����'";
		}
		
		return this.getResultList(sql);
		
	}
	
	
	/**
	 * �ֲֿ���������ܣ�ȡ�ⷿ�б�
	 * @param store_id
	 * @return
	 */
	public List getStoreList(String store_id){
		String sql = "select * from storehouse where 1=1";
		
		if(!store_id.equals("")){
			sql += " and id='" + store_id + "'";
		}
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ͳ�ƽ��д��HashMap�У�ǰ̨��Map��ȡ
	 * @param product_kind
	 * @param product_name
	 * @param state
	 * @param store_id
	 * @return Map(key,value)  key=product_id+store_id,value=�������
	 */
	public Map getKcStatResult(String product_kind,String product_name,String state,String store_id){
		Map map = new HashMap();
		
		String sql = "select a.product_id,a.store_id,a.nums from product_kc a inner join product b on b.product_id=a.product_id where 1=1";
		
		//������Ʒ���
		if(!product_kind.equals("")){
			String[] arryItems = product_kind.split(",");
			
			if(arryItems != null && arryItems.length >0){
				sql += " and (";
				for(int i=0;i<arryItems.length;i++){
					if(i == 0){
						sql += " b.product_kind like '" + arryItems[i] + "%'";
					}else{
						sql += " or b.product_kind like '" + arryItems[i] + "%'";
					}
				}
				sql += ")";
			}
			
		}
		
		if(!product_name.equals("")){
			sql = sql + " and (b.product_name like '%" + product_name + "%' or b.product_xh like '%" + product_name + "%')";
		}
		
		if(state.equals("��")){
			sql = sql + " and b.state='����'";
		}
		
		if(!store_id.equals("")){
			sql += " and a.store_id ='" + store_id + "'";
		}
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size() > 0){
			Iterator it = list.iterator();
			while(it.hasNext()){
				Map tempMap = (Map)it.next();				
				map.put(StringUtils.nullToStr(tempMap.get("product_id")) + StringUtils.nullToStr(tempMap.get("store_id")), tempMap.get("nums"));
			}
		}
		
		return map;
	}
	
}
