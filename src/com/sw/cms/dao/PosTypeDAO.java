package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.PosType;

/**
 * 刷卡POS机设定
 * @author liyt
 *
 */
public class PosTypeDAO extends JdbcBaseDAO {
	
	
	/**
	 * 取刷卡Pos机设定列表信息
	 * @return
	 */
	public List getPosTypeList(){
		String sql = "select * from pos_type";
		return this.getResultList(sql, new PosTypeMapper());
	}
	
	
	/**
	 * 保存刷卡Pos机设定信息
	 * @param posType
	 */
	public void updatePosType(PosType posType){

		if(posType.getId() == 0){
			//新添加
			String sql = "insert into pos_type(name,type,mbfy,fl,fdfy,account_id,remark,czr,cz_date) values (?,?,?,?,?,?,?,?,?)";
			
			Object[] param = new Object[9];
			param[0] = posType.getName();
			param[1] = posType.getType();
			param[2] = posType.getMbfy();
			param[3] = posType.getFl();
			param[4] = posType.getFdfy();
			param[5] = posType.getAccount_id();
			param[6] = posType.getRemark();
			param[7] = posType.getCzr();
			param[8] = posType.getCz_date();
			this.getJdbcTemplate().update(sql,param);
		}else{
			//修改
			String sql = "update pos_type set name=?,type=?,mbfy=?,fl=?,fdfy=?,account_id=?,remark=?,czr=?,cz_date=? where id=?";
			
			Object[] param = new Object[10];
			param[0] = posType.getName();
			param[1] = posType.getType();
			param[2] = posType.getMbfy();
			param[3] = posType.getFl();
			param[4] = posType.getFdfy();
			param[5] = posType.getAccount_id();
			param[6] = posType.getRemark();
			param[7] = posType.getCzr();
			param[8] = posType.getCz_date();
			param[9] = posType.getId();
			this.getJdbcTemplate().update(sql,param);
		}
	}
	
	
	/**
	 * 根据编号取POS机设定信息
	 * @param id
	 * @return
	 */
	public PosType getPosType(String id){
		String sql = "select * from pos_type where id=" + id;
		return (PosType)this.queryForObject(sql, new PosTypeMapper());
	}
	
	
	/**
	 * 删除刷卡POS机信息
	 * @param id
	 */
	public void delPosType(String id){
		String sql = "delete from pos_type where id=" + id;
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 根据POS机编号及刷卡金额取刷卡费用
	 * @param pos_id
	 * @param je
	 * @return
	 */
	public double getBrushCardfy(String pos_id,double je){
		double fy = 0;		
		PosType posType = getPosType(pos_id);
		
		if(posType != null){
			//类型（1:固定金额，2:固定扣款率，3:固定扣款率(封顶)）
			if(posType.getType().equals("1")){
				//固定金额
				fy = posType.getMbfy();
			}else if(posType.getType().equals("2")){
				//固定扣款率
				fy = posType.getFl() * je;
			}else if(posType.getType().equals("3")){
				//固定扣款率(封顶)
				fy = posType.getFl() * je;
				if(fy > posType.getFdfy()){
					fy = posType.getFdfy();
				}
				if(fy < -posType.getFdfy()){
					fy = 0-posType.getFdfy();
				}
			}
		}		
		return fy;
	}
	
	
	/**
	 * 根据POS机编号取POS机名称
	 * @param id
	 * @return
	 */
	public String getPosNameById(String id){
		String name = "";
		
		if(getPosType(id) != null){
			name = getPosType(id).getName();
		}
		return name;
	}
	
	
	/**
	 * 包装对象(序列号)
	 * @author liyt
	 */
	class PosTypeMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			PosType posType = new PosType();

			if(SqlUtil.columnIsExist(rs,"id")) posType.setId(rs.getInt("id"));
			if(SqlUtil.columnIsExist(rs,"name")) posType.setName(rs.getString("name"));
			if(SqlUtil.columnIsExist(rs,"type")) posType.setType(rs.getString("type"));
			if(SqlUtil.columnIsExist(rs,"mbfy")) posType.setMbfy(rs.getDouble("mbfy"));
			if(SqlUtil.columnIsExist(rs,"fl")) posType.setFl(rs.getDouble("fl"));
			if(SqlUtil.columnIsExist(rs,"fdfy")) posType.setFdfy(rs.getDouble("fdfy"));
			if(SqlUtil.columnIsExist(rs,"account_id")) posType.setAccount_id(rs.getString("account_id"));
			if(SqlUtil.columnIsExist(rs,"remark")) posType.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"czr")) posType.setCzr(rs.getString("czr"));
			if(SqlUtil.columnIsExist(rs,"cz_date")) posType.setCz_date(rs.getDate("cz_date"));
			
			return posType;
		}
	}

}
