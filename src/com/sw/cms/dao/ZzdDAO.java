package com.sw.cms.dao;

import java.util.List;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Zzd;
import com.sw.cms.model.ZzdProduct;
import com.sw.cms.util.DateComFunc;

/**
 * ��װ��
 * @author liyt
 *
 */
public class ZzdDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ��װ���б�
	 * @param start_date
	 * @param end_date
	 * @param state
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getZzdList(String con,int curPage, int rowsPerPage){
		String sql = "select * from zzd where 1=1";
		
		if(!con.equals("")){
			sql += con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(Zzd.class));
	}
	
	
	/**
	 * ���ݱ��ȡ��װ����Ϣ
	 * @param id
	 * @return
	 */
	public Zzd editZzd(String id){
		String sql = "select * from zzd where id='" + id + "'";
		return (Zzd)this.queryForObject(sql, new BeanRowMapper(Zzd.class));
	}
	
	
	/**
	 * ���ݱ��ȡ��װ����Ʒ��ϸ��Ϣ
	 * @param cxd_id
	 * @return
	 */
	public List editZzdProducts(String zzd_id){
		String sql = "select * from zzd_product where zzd_id='" + zzd_id + "'";
		return this.getResultList(sql, new BeanRowMapper(ZzdProduct.class));
	}
	
	
	/**
	 * ���ݱ����ж���װ���Ƿ����ύ
	 * @param cxd_id
	 * @return
	 */
	public boolean isZzdSubmit(String zzd_id){
		boolean is = false;
		String sql = "select count(*) as counts from zzd where id='" + zzd_id + "' and state='���ύ'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * ������װ����Ϣ
	 * @param cxd
	 * @param cxdProduct
	 */
	public void updateZzd(Zzd zzd,List zzdProducts){
		String sql = "select count(*) as counts from zzd where id='" + zzd.getId() + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			//��¼���ڸ���
			sql = "update zzd set cdate=?,store_id=?,product_id=?,product_name=?,product_xh=?,product_dw=?,nums=?," +
					"price=?,hjje=?,jsr=?,remark=?,czr=?,cz_date=now(),serial_nums=?,qz_flag=?,state=? where id=?";
		}else{
			//��¼�����ڲ���
			sql = "insert into zzd(cdate,store_id,product_id,product_name,product_xh,product_dw,nums,price,hjje,jsr,remark,czr,cz_date,serial_nums,qz_flag,state,id) " +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?)";
		}
		
		Object[] param = new Object[16];
		param[0] = zzd.getCdate();
		param[1] = zzd.getStore_id();
		param[2] = zzd.getProduct_id();
		param[3] = zzd.getProduct_name();
		param[4] = zzd.getProduct_xh();
		param[5] = zzd.getProduct_dw();
		param[6] = zzd.getNums();
		param[7] = zzd.getPrice();
		param[8] = zzd.getHjje();
		param[9] = zzd.getJsr();
		param[10] = zzd.getRemark();
		param[11] = zzd.getCzr();
		param[12] = zzd.getSerial_nums();
		param[13] = zzd.getQz_flag();
		param[14] = zzd.getState();
		param[15] = zzd.getId();
		
		this.getJdbcTemplate().update(sql, param);
		
		this.delZzdProducts(zzd.getId());
		
		this.addZzdProducts(zzd, zzdProducts);
	}
	
	
	/**
	 * ɾ����װ��
	 * @param id
	 */
	public void delZzd(String id){
		String sql = "delete from zzd where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from zzd_product where zzd_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String getZzdID() {
		String sql = "select zzdid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();

		// �����кż�1
		sql = "update cms_all_seq set zzdid=zzdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "ZZ" + day + "-" + curId;
	}

	
	/**
	 * ɾ����װ�������Ʒ
	 * @param cxd_id
	 */
	private void delZzdProducts(String zzd_id){
		String sql = "delete from zzd_product where zzd_id='" + zzd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * �����װ�������Ʒ
	 * @param cxd
	 * @param cxdProducts
	 */
	private void addZzdProducts(Zzd zzd,List zzdProducts){		
		String zzd_id = zzd.getId();
		String sql = "";
		Object[] param = new Object[11];
		
		if(zzdProducts != null && zzdProducts.size() > 0){
			for(int i=0;i<zzdProducts.size();i++){
				
				ZzdProduct zzdProduct = (ZzdProduct)zzdProducts.get(i);
				if(zzdProduct != null && zzdProduct.getProduct_id() != null){
					sql = "insert into zzd_product(zzd_id,product_id,product_name,product_xh,product_dw,price,nums,hj,remark,qz_serial_num,qz_flag) " +
							"values(?,?,?,?,?,?,?,?,?,?,?)";
					
					param[0] = zzd_id;
					param[1] = zzdProduct.getProduct_id();
					param[2] = zzdProduct.getProduct_name();
					param[3] = zzdProduct.getProduct_xh();
					param[4] = zzdProduct.getProduct_dw();
					param[5] = zzdProduct.getPrice();
					param[6] = zzdProduct.getNums();
					param[7] = zzdProduct.getHj();
					param[8] = zzdProduct.getRemark();
					param[9] = zzdProduct.getQz_serial_num();
					param[10] = zzdProduct.getQz_flag();
					
					this.getJdbcTemplate().update(sql, param);
				}
				
			}
		}		
	}

}
