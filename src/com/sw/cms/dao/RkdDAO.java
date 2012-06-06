package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Page;
import com.sw.cms.model.Rkd;
import com.sw.cms.model.RkdProduct;
import com.sw.cms.util.DateComFunc;

/**
 * <p>����ⵥ������ݿ������</p>
 * <p>��ⵥ��Ϊ����״̬</p>
 * <p>����⡢δ���</p>
 * <p>�������ⵥֻ�ɲ飬�����������޸�</p>
 * <p>��ⵥ���ͬʱ���¿�����ݼ��۸�</p>
 * <p>�����Ʒ�۸�Ŀǰ���ü�Ȩƽ����</p>
 * autor liyt
 * 2008-03-29
 */

public class RkdDAO extends JdbcBaseDAO {
	
	
	/**
	 * ȡ��ⵥ�б�(����ҳ��־)
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getRkdList(String con,int curPage, int rowsPerPage){
		String sql = "select * from rkd where 1=1";
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	
	/**
	 * ������ⵥ��Ϣ������������Ʒ��Ϣ��
	 * @param rkd
	 * @param rkdProducts
	 */
	public void saveRkd(Rkd rkd,List rkdProducts){
		String sql = "insert into rkd(rkd_id,store_id,fzr,jhd_id,creatdate,rk_date,state,ms,cgfzr,czr,cz_date,client_name) values(?,?,?,?,?,?,?,?,?,?,now(),?)";
		
		String rkd_id = rkd.getRkd_id();
		Object[] param = new Object[11];
		param[0] = rkd_id;
		param[1] = rkd.getStore_id();
		param[2] = rkd.getFzr();
		param[3] = rkd.getJhd_id();
		param[4] = rkd.getCreatdate();
		param[5] = rkd.getRk_date();
		param[6] = rkd.getState();
		param[7] = rkd.getMs();
		param[8] = rkd.getCgfzr();
		param[9] = rkd.getCzr();
		param[10] = rkd.getClient_name();
		
		this.getJdbcTemplate().update(sql,param);  //�����ⵥ��Ϣ
		
		addRkdProducts(rkdProducts, rkd_id);   //�����ⵥ������Ʒ��Ϣ
	}
	
	
	
	/**
	 * ������ⵥ��Ϣ������������Ʒ��Ϣ��
	 * @param rkd
	 * @param rkdProducts
	 */
	public void updateRkd(Rkd rkd,List rkdProducts){
		String sql = "update rkd set fzr=?,jhd_id=?,creatdate=?,rk_date=?,state=?,ms=?,store_id=?,cgfzr=?,czr=?,cz_date=now(),client_name=? where rkd_id=?";
		
		Object[] param = new Object[11];

		param[0] = rkd.getFzr();
		param[1] = rkd.getJhd_id();
		param[2] = rkd.getCreatdate();
		param[3] = rkd.getRk_date();
		param[4] = rkd.getState();
		param[5] = rkd.getMs();
		param[6] = rkd.getStore_id();
		param[7] = rkd.getCgfzr();		
		param[8] = rkd.getCzr();
		param[9] = rkd.getClient_name();
		param[10] = rkd.getRkd_id();
		
		this.getJdbcTemplate().update(sql,param);     //������ⵥ��Ϣ
		
		this.delRkdProducts(rkd.getRkd_id());    //ɾ����ⵥ������Ʒ
		this.addRkdProducts(rkdProducts, rkd.getRkd_id()); //����µ���ⵥ������Ʒ
	}
	
	
	
	/**
	 * ɾ����ⵥ��Ϣ������������Ʒ��
	 * @param rkd_id
	 */
	public void delRkd(String rkd_id){
		String sql = "delete from rkd where rkd_id='" + rkd_id + "'";
		
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from rkd_product where rkd_id='" + rkd_id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ������ⵥIDȡ��ⵥ��Ϣ
	 * @param rkd_id
	 * @return
	 */
	public Object getRkd(String rkd_id){
		String sql = "select * from rkd where rkd_id='" + rkd_id + "'";
		
		return this.queryForObject(sql, new RkdRowMapper());
	}
	
	
	/**
	 * ������ⵥIDȡ��ⵥ��Ʒ�б�
	 * @param rkd_id
	 * @return
	 */
	public List getRkdProducts(String rkd_id){
		String sql = "select a.*,b.qz_serial_num as qz_flag,b.dw from rkd_product a left join product b on b.product_id=a.product_id where a.rkd_id='" + rkd_id + "'";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ������ⵥ��Ų鿴��ⵥ�Ƿ��Ѿ����
	 * @param xsd_id
	 * @return
	 */
	public boolean isJhdSubmit(String rkd_id){
		boolean is = false;
		String sql = "select count(*) from rkd where rkd_id='" + rkd_id + "' and state='�����'";
		
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * �����ⵥ������Ʒ
	 * @param rkdProducts  ��ƷList
	 * @param rkd_id    ��ⵥID
	 */
	private void addRkdProducts(List rkdProducts,String rkd_id){
		String sql = "";
		Object[] param = new Object[12];
		
		if(rkdProducts != null && rkdProducts.size()>0){
			for(int i =0;i<rkdProducts.size();i++){
				
				RkdProduct rkdProduct = (RkdProduct)rkdProducts.get(i);
				if(rkdProduct != null){
					if(!rkdProduct.getProduct_name().equals("")){
						sql = "insert into rkd_product(rkd_id,product_id,product_xh,product_name,price,nums,remark,qz_serial_num,sd,hsje,sje,bhsje) values(?,?,?,?,?,?,?,?,?,?,?,?)";
						
						param[0] = rkd_id;
						param[1] = rkdProduct.getProduct_id();
						param[2] = rkdProduct.getProduct_xh();
						param[3] = rkdProduct.getProduct_name();
						param[4] = new Double(rkdProduct.getPrice());
						param[5] = new Integer(rkdProduct.getNums());
						param[6] = rkdProduct.getRemark();
						param[7] = rkdProduct.getQz_serial_num();
						param[8] = rkdProduct.getSd();
						param[9] = rkdProduct.getHsje();
						param[10] = rkdProduct.getSje();
						param[11] = rkdProduct.getBhsje();
						this.getJdbcTemplate().update(sql, param);
					}
				}
			}
		}
	}
	
	
	
	
	/**
	 * ɾ����ⵥ������������Ʒ
	 * @param rkd_id
	 */
	private void delRkdProducts(String rkd_id){
		String sql = "delete from rkd_product where rkd_id='" + rkd_id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String getRkdID() {
		String sql = "select rkdid from cms_all_seq";
		
		String day = DateComFunc.getCurDay();

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		// �����кż�1
		sql = "update cms_all_seq set rkdid=rkdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "RK" + day + "-" + curId;
	}
	
	
	/**
	 * ��װ����(��ⵥ)
	 * 
	 * @author liyt
	 * 
	 */
	class RkdRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Rkd rkd = new Rkd();
			
			rkd.setRkd_id(rs.getString("rkd_id"));
			rkd.setFzr(rs.getString("fzr"));
			rkd.setJhd_id(rs.getString("jhd_id"));
			rkd.setCreatdate(rs.getString("creatdate"));
			rkd.setRk_date(rs.getString("rk_date"));
			rkd.setState(rs.getString("state"));
			rkd.setMs(rs.getString("ms"));
			rkd.setStore_id(rs.getString("store_id"));
			rkd.setCgfzr(rs.getString("cgfzr"));
			rkd.setCzr(rs.getString("czr"));
			rkd.setClient_name(rs.getString("client_name"));
			
			return rkd;
		}
	}
	

	
	/**
	 * ��װ����(��ⵥ��Ʒ)
	 * 
	 * @author liyt
	 * 
	 */
	class RkdProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			RkdProduct rkdProduct = new RkdProduct();

			rkdProduct.setId(rs.getInt("id"));
			rkdProduct.setRkd_id(rs.getString("rkd_id"));
			rkdProduct.setProduct_id(rs.getString("product_id"));
			rkdProduct.setProduct_xh(rs.getString("product_xh"));
			rkdProduct.setProduct_name(rs.getString("product_name"));
			rkdProduct.setPrice(rs.getDouble("price"));
			rkdProduct.setNums(rs.getInt("nums"));
			rkdProduct.setRemark(rs.getString("remark"));
			rkdProduct.setQz_serial_num(rs.getString("qz_serial_num"));
			
			return rkdProduct;
		}
	}	

}
