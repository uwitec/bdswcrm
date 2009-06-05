package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Chtj;
import com.sw.cms.model.ChtjDesc;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class ChtjDAO extends JdbcBaseDAO {
	
	
	/**
	 * ���ݲ�ѯ����ȡ�����б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getChtjList(String con,int curPage, int rowsPerPage){
		String sql = "select * from chtj where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new ChtjRowMapper());
	}
	
	
	/**
	 * ���������������Ϣ
	 * @param chtj
	 * @param chtjDescs
	 */
	public void saveChtj(Chtj chtj,List chtjDescs){
		String sql = "insert into chtj(tj_date,jsr,state,remark,czr,cz_date,id) values(?,?,?,?,?,now(),?)";
		
		Object[] param = new Object[6];
		
		param[0] = chtj.getTj_date();
		param[1] = chtj.getJsr();
		param[2] = chtj.getState();
		param[3] = chtj.getRemark();
		param[4] = chtj.getCzr();
		param[5] = chtj.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.addChtjDesc(chtjDescs, chtj.getId());
	}
	
	
	/**
	 * ���´�����������Ϣ
	 * @param chtj
	 * @param chtjDescs
	 */
	public void updateChtj(Chtj chtj,List chtjDescs){
		String sql = "update chtj set tj_date=?,jsr=?,state=?,remark=?,czr=?,cz_date=now() where id=?";
		
		Object[] param = new Object[6];
		
		param[0] = chtj.getTj_date();
		param[1] = chtj.getJsr();
		param[2] = chtj.getState();
		param[3] = chtj.getRemark();
		param[4] = chtj.getCzr();
		param[5] = chtj.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.delChtjDesc(chtj.getId());
		
		this.addChtjDesc(chtjDescs, chtj.getId());
	}
	
	
	/**
	 * ȡ���������Ϣ
	 * @param id
	 * @return
	 */
	public Object getChtj(String id){
		String sql = "select * from chtj where id='" + id + "'";
		return this.getJdbcTemplate().queryForObject(sql, new ChtjRowMapper());
	}
	
	
	/**
	 * ȡ���������ϸ
	 * @param id
	 * @return
	 */
	public List getChtjDesc(String id){
		String sql = "select * from chtj_desc where chtj_id='" + id + "'";
		return this.getJdbcTemplate().query(sql, new ChtjDescProductRowMapper());
	}
	
	
	/**
	 * ɾ���������
	 * @param id
	 */
	public void delChtj(String id){
		String sql = "delete from chtj where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from chtj_desc where chtj_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String getChtjID() {
		String sql = "select chtjid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();

		// �����кż�1
		sql = "update cms_all_seq set chtjid=chtjid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "TJ" + day + "-" + curId;
	}
	
	
	/**
	 * ��Ӵ��������ϸ
	 * @param lsdProducts
	 * @param lsd_id
	 */
	private void addChtjDesc(List chtjDescs,String chtj_id){
		String sql = "";
		Object[] param = new Object[7];
		
		if(chtjDescs != null && chtjDescs.size()>0){
			for(int i=0;i<chtjDescs.size();i++){
				ChtjDesc chtjDesc = (ChtjDesc)chtjDescs.get(i);
				if(chtjDesc != null){
					if(!chtjDesc.getProduct_id().equals("") && !chtjDesc.getProduct_name().equals("")){
						sql = "insert into chtj_desc(chtj_id,product_id,product_xh,product_name,ysjg,tzjg,remark) values(?,?,?,?,?,?,?)";
						
						param[0] = chtj_id;
						param[1] = chtjDesc.getProduct_id();
						param[2] = chtjDesc.getProduct_xh();
						param[3] = chtjDesc.getProduct_name();
						param[4] = new Double(chtjDesc.getYsjg());
						param[5] = new Double(chtjDesc.getTzjg());
						param[6] = chtjDesc.getRemark();
						
						this.getJdbcTemplate().update(sql,param);
					}
				}
			}
		}
	}
	
	
	/**
	 * ɾ�����������ϸ
	 * @param lsd_id
	 */
	private void delChtjDesc(String chtj_id){
		String sql = "delete from chtj_desc where chtj_id='" + chtj_id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ��װ����(�������)
	 * 
	 * @author liyt
	 * 
	 */
	class ChtjRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Chtj chtj = new Chtj();

			chtj.setId(rs.getString("id"));
			chtj.setTj_date(rs.getString("tj_date"));
			chtj.setJsr(rs.getString("jsr"));
			chtj.setState(rs.getString("state"));
			chtj.setCzr(rs.getString("czr"));
			chtj.setRemark(rs.getString("remark"));
			
			return chtj;
		}
	}	
	
	
	/**
	 * ��װ����(���������ϸ)
	 * 
	 * @author liyt
	 * 
	 */
	class ChtjDescProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			ChtjDesc chtjDesc = new ChtjDesc();

			chtjDesc.setChtj_id(rs.getString("chtj_id"));
			chtjDesc.setProduct_id(rs.getString("product_id"));
			chtjDesc.setProduct_name(rs.getString("product_name"));
			chtjDesc.setProduct_xh(rs.getString("product_xh"));
			chtjDesc.setTzjg(rs.getDouble("tzjg"));
			chtjDesc.setYsjg(rs.getDouble("ysjg"));
			chtjDesc.setRemark(rs.getString("remark"));
			
			return chtjDesc;
		}
	}	

}
