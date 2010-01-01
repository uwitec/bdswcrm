package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Cxd;
import com.sw.cms.model.CxdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

/**
 * ��ж��
 * @author liyt
 *
 */
public class CxdDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ��ж���б�
	 * @param start_date
	 * @param end_date
	 * @param state
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCxdList(String start_date,String end_date,String state,int curPage, int rowsPerPage){
		String sql = "select * from cxd where 1=1";
		
		if(!start_date.equals("")){
			sql += " and cdate>='" + start_date + "'";
		}
		if(!end_date.equals("")){
			sql += " and cdate<='" + end_date + "'";
		}
		if(!state.equals("")){
			sql += " and state='" + state + "'";
		}
		sql += " order by id desc";
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(Cxd.class));
	}
	
	
	/**
	 * ���ݱ��ȡ��ж����Ϣ
	 * @param id
	 * @return
	 */
	public Cxd editCxd(String id){
		String sql = "select * from cxd where id='" + id + "'";
		return (Cxd)this.queryForObject(sql, new BeanRowMapper(Cxd.class));
	}
	
	
	/**
	 * �����ж����Ϣ
	 * @param cxd
	 * @param cxdProduct
	 */
	public void updateCxd(Cxd cxd,List cxdProducts){
		String sql = "select count(*) as counts from cxd where id='" + cxd.getId() + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			//��¼���ڸ���
			sql = "update cxd set cdate=?,store_id=?,product_id=?,product_name=?,product_xh=?,product_dw=?,nums=?," +
					"price=?,hjje=?,jsr=?,remark=?,czr=?,cz_date=now() where id=?";
		}else{
			//��¼�����ڲ���
			sql = "insert into cxd(cdate,store_id,product_id,product_name,product_xh,product_dw,nums,price,hjje,jsr,remark,czr,cz_date,id) " +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,now(),?)";
		}
		
		Object[] param = new Object[13];
		param[0] = cxd.getCdate();
		param[1] = cxd.getStore_id();
		param[2] = cxd.getProduct_id();
		param[3] = cxd.getProduct_name();
		param[4] = cxd.getProduct_xh();
		param[5] = cxd.getProduct_dw();
		param[6] = cxd.getNums();
		param[7] = cxd.getPrice();
		param[8] = cxd.getHjje();
		param[9] = cxd.getJsr();
		param[10] = cxd.getRemark();
		param[11] = cxd.getCzr();
		param[12] = cxd.getId();
		
		this.getJdbcTemplate().update(sql, param);
		
		this.delCxdProducts(cxd.getId());
		
		this.addCxdProducts(cxd, cxdProducts);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String getCxdID() {
		String sql = "select cxdid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();

		// �����кż�1
		sql = "update cms_all_seq set cxdid=cxdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "CX" + day + "-" + curId;
	}
	
	
	/**
	 * ɾ����ж��
	 * @param id
	 */
	public void delCxd(String id){
		String sql = "delete from cxd where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from cxd_product where cxd_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ɾ����ж�������Ʒ
	 * @param cxd_id
	 */
	private void delCxdProducts(String cxd_id){
		String sql = "delete from cxd_product where cxd_id='" + cxd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ��Ӳ�ж�������Ʒ
	 * @param cxd
	 * @param cxdProducts
	 */
	private void addCxdProducts(Cxd cxd,List cxdProducts){		
		String cxd_id = cxd.getId();
		String sql = "";
		Object[] param = new Object[9];
		
		if(cxdProducts != null && cxdProducts.size() > 0){
			for(int i=0;i<cxdProducts.size();i++){
				
				CxdProduct cxdProduct = (CxdProduct)cxdProducts.get(i);
				if(cxdProduct != null && cxdProduct.getProduct_id() != null){
					sql = "insert into cxd_product(cxd_id,product_id,product_name,product_xh,product_dw,price,nums,hj,remark) " +
							"values(?,?,?,?,?,?,?,?,?)";
					
					param[0] = cxd_id;
					param[1] = cxdProduct.getProduct_id();
					param[2] = cxdProduct.getProduct_name();
					param[3] = cxdProduct.getProduct_xh();
					param[4] = cxdProduct.getProduct_dw();
					param[5] = cxdProduct.getPrice();
					param[6] = cxdProduct.getNums();
					param[7] = cxdProduct.getHj();
					param[8] = cxdProduct.getRemark();
					
					this.getJdbcTemplate().update(sql, param);
				}
				
			}
		}		
	}

}
