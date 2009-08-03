package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Chtj;
import com.sw.cms.model.ChtjDesc;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class ChtjDAO extends JdbcBaseDAO {
	
	
	/**
	 * 根据查询条件取调价列表
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
	 * 保存存货调价相关信息
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
	 * 更新存货调价相关信息
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
	 * 取存货调价信息
	 * @param id
	 * @return
	 */
	public Object getChtj(String id){
		String sql = "select * from chtj where id='" + id + "'";
		return this.getJdbcTemplate().queryForObject(sql, new ChtjRowMapper());
	}
	
	
	/**
	 * 取存货调价明细
	 * @param id
	 * @return
	 */
	public List getChtjDesc(String id){
		String sql = "select * from chtj_desc where chtj_id='" + id + "'";
		return this.getJdbcTemplate().query(sql, new ChtjDescProductRowMapper());
	}
	
	
	/**
	 * 删除存货调价
	 * @param id
	 */
	public void delChtj(String id){
		String sql = "delete from chtj where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from chtj_desc where chtj_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getChtjID() {
		String sql = "select chtjid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set chtjid=chtjid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "TJ" + day + "-" + curId;
	}
	
	
	/**
	 * 添加存货调价明细
	 * @param lsdProducts
	 * @param lsd_id
	 */
	private void addChtjDesc(List chtjDescs,String chtj_id){
		String sql = "";
		Object[] param = new Object[8];
		
		if(chtjDescs != null && chtjDescs.size()>0){
			for(int i=0;i<chtjDescs.size();i++){
				ChtjDesc chtjDesc = (ChtjDesc)chtjDescs.get(i);
				if(chtjDesc != null){
					if(!chtjDesc.getProduct_id().equals("") && !chtjDesc.getProduct_name().equals("")){
						sql = "insert into chtj_desc(chtj_id,product_id,product_xh,product_name,ysjg,tzjg,remark,nums) values(?,?,?,?,?,?,?,?)";
						
						param[0] = chtj_id;
						param[1] = chtjDesc.getProduct_id();
						param[2] = chtjDesc.getProduct_xh();
						param[3] = chtjDesc.getProduct_name();
						param[4] = new Double(chtjDesc.getYsjg());
						param[5] = new Double(chtjDesc.getTzjg());
						param[6] = chtjDesc.getRemark();
						param[7] = chtjDesc.getNums();
						
						this.getJdbcTemplate().update(sql,param);
					}
				}
			}
		}
	}
	
	
	/**
	 * 删除存货调价明细
	 * @param lsd_id
	 */
	private void delChtjDesc(String chtj_id){
		String sql = "delete from chtj_desc where chtj_id='" + chtj_id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 包装对象(存货调价)
	 * 
	 * @author liyt
	 * 
	 */
	class ChtjRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Chtj chtj = new Chtj();
			
			if (SqlUtil.columnIsExist(rs, "id")) chtj.setId(rs.getString("id"));
			if (SqlUtil.columnIsExist(rs, "tj_date")) chtj.setTj_date(rs.getString("tj_date"));
			if (SqlUtil.columnIsExist(rs, "jsr")) chtj.setJsr(rs.getString("jsr"));
			if (SqlUtil.columnIsExist(rs, "state")) chtj.setState(rs.getString("state"));
			if (SqlUtil.columnIsExist(rs, "czr")) chtj.setCzr(rs.getString("czr"));
			if (SqlUtil.columnIsExist(rs, "remark")) chtj.setRemark(rs.getString("remark"));
			
			return chtj;
		}
	}	
	
	
	/**
	 * 包装对象(存货调价明细)
	 * 
	 * @author liyt
	 * 
	 */
	class ChtjDescProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			ChtjDesc chtjDesc = new ChtjDesc();

			if (SqlUtil.columnIsExist(rs, "chtj_id")) chtjDesc.setChtj_id(rs.getString("chtj_id"));
			if (SqlUtil.columnIsExist(rs, "product_id")) chtjDesc.setProduct_id(rs.getString("product_id"));
			if (SqlUtil.columnIsExist(rs, "product_name")) chtjDesc.setProduct_name(rs.getString("product_name"));
			if (SqlUtil.columnIsExist(rs, "product_xh")) chtjDesc.setProduct_xh(rs.getString("product_xh"));
			if (SqlUtil.columnIsExist(rs, "tzjg")) chtjDesc.setTzjg(rs.getDouble("tzjg"));
			if (SqlUtil.columnIsExist(rs, "ysjg")) chtjDesc.setYsjg(rs.getDouble("ysjg"));
			if (SqlUtil.columnIsExist(rs, "remark")) chtjDesc.setRemark(rs.getString("remark"));
			if (SqlUtil.columnIsExist(rs, "nums")) chtjDesc.setNums(rs.getInt("nums"));
			
			return chtjDesc;
		}
	}	

}
