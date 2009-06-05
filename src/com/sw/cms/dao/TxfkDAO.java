package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Page;
import com.sw.cms.model.Txfk;
import com.sw.cms.model.TxfkDesc;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.StaticParamDo;

/**
 * 摊销付款DAO类
 * @author liyt
 *
 */
public class TxfkDAO extends JdbcBaseDAO {
	
	
	/**
	 * 根据查询条件取摊销付款列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getTxfkList(String con,int curPage, int rowsPerPage){
		String sql = "select * from txfk where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage,new TxfkRowMapper());
	}
	
	
	/**
	 * 更新摊销付款信息
	 * 编号不存在添加，编号存在更新
	 * @param txfk
	 * @param txfkDescs
	 */
	public void updateTxfk(Txfk txfk,List txfkDescs){
		String sql = "select * from txfk where id='" + txfk.getId() + "'";
		List list = this.getResultList(sql);
		
		if(list != null && list.size() >0){
			//编号存在，更新记录
			sql = "update txfk set client_name=?,fk_date=?,jsr=?,account_id=?,fklx=?,fkje=?,remark=?,czr=?,cz_date=now(),state=? where id=?";
		}else{
			//编号不存在，新增记录
			sql = "insert into txfk(client_name,fk_date,jsr,account_id,fklx,fkje,remark,czr,cz_date,state,id) values(?,?,?,?,?,?,?,?,now(),?,?)";
		}
		
		Object[] param = new Object[10];
		param[0] = txfk.getClient_name();
		param[1] = txfk.getFk_date();
		param[2] = txfk.getJsr();
		param[3] = txfk.getAccount_id();
		param[4] = txfk.getFklx();
		param[5] = txfk.getFkje();
		param[6] = txfk.getRemark();
		param[7] = txfk.getCzr();
		param[8] = txfk.getState();
		param[9] = txfk.getId();
		
		//保存摊销付款信息(添加或更新)
		this.getJdbcTemplate().update(sql,param);
		
		//删除明细信息
		this.delTxfkDescs(txfk.getId());
		
		//添加新的明细信息
		this.addTxfkDesc(txfkDescs, txfk.getId());
		
	}
	
	
	/**
	 * 根据编号获取摊销付款信息
	 * @param id
	 * @return
	 */
	public Txfk getTxfk(String id){
		String sql = "select * from txfk where id='" + id + "'";
		return (Txfk)this.queryForObject(sql, new TxfkRowMapper());
	}
	
	
	/**
	 * 根据摊销付款编号取明细信息列表
	 * @param id
	 * @return
	 */
	public List getTxfkDescs(String id){
		String sql = "select * from txfk_desc where txfk_id='" + id + "'";
		return this.getResultList(sql, new TxfkDescRowMapper());
	}
	
	
	/**
	 * 删除摊销付款相关信息
	 * @param id
	 */
	public void delTxfk(String id){
		String sql = "delete from txfk where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from txfk_desc where txfk_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取当前可用的摊销付款编号
	 * @return
	 */
	public String getTxfkID() {
		String sql = "select txfkid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();
		// 将序列号加1
		sql = "update cms_all_seq set txfkid=txfkid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "TX" + day + "-" + curId;
	}
	
	
	/**
	 * 取当前可用的摊销付款明细编号
	 * @return
	 */
	private String getTxfkDescID() {
		String sql = "select txfkdescid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();
		// 将序列号加1
		sql = "update cms_all_seq set txfkdescid=txfkdescid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "TXFK" + day + "-" + curId;
	}
	
	
	/**
	 * 保存摊销付款明细信息
	 * @param txfkDescs
	 */
	private void addTxfkDesc(List txfkDescs,String txfk_id){
		
		String sql = "";
		Object[] param = new Object[5];
		
		if(txfkDescs != null && txfkDescs.size() > 0){
			for(int i=0;i<txfkDescs.size();i++){
				TxfkDesc txfkDesc = (TxfkDesc)txfkDescs.get(i);
				if(txfkDesc != null && txfkDesc.getTxrq() != null){
					sql = "insert into txfk_desc(id,txfk_id,txrq,je,remark) values(?,?,?,?,?)";
					
					param[0] = this.getTxfkDescID();
					param[1] = txfk_id;
					param[2] = txfkDesc.getTxrq();
					param[3] = txfkDesc.getJe();
					param[4] = txfkDesc.getRemark();
					
					this.getJdbcTemplate().update(sql,param);
				}
			}
		}
	}
	
	
	/**
	 * 删除摊销付款明细信息
	 * @param txfk_id
	 */
	private void delTxfkDescs(String txfk_id){
		String sql = "delete from txfk_desc where txfk_id='" + txfk_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 包装对象(摊销付款)
	 * @author liyt
	 */
	class TxfkRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Txfk txfk = new Txfk();

			if(SqlUtil.columnIsExist(rs,"id")) txfk.setId(rs.getString("id"));			
			if(SqlUtil.columnIsExist(rs,"account_id")) txfk.setAccount_id(rs.getString("account_id"));
			if(SqlUtil.columnIsExist(rs,"client_name")) txfk.setClient_name(rs.getString("client_name"));			
			if(SqlUtil.columnIsExist(rs,"cz_date") && rs.getTimestamp("cz_date") != null) txfk.setCz_date(rs.getTimestamp("cz_date").toString());
			if(SqlUtil.columnIsExist(rs,"czr"))	txfk.setCzr(rs.getString("czr"));
			if(SqlUtil.columnIsExist(rs,"fk_date")) txfk.setFk_date(rs.getString("fk_date"));
			
			if(SqlUtil.columnIsExist(rs,"fkje")) txfk.setFkje(rs.getDouble("fkje"));
			if(SqlUtil.columnIsExist(rs,"fklx")) txfk.setFklx(rs.getString("fklx"));			
			if(SqlUtil.columnIsExist(rs,"jsr")) txfk.setJsr(rs.getString("jsr"));
			if(SqlUtil.columnIsExist(rs,"remark")) txfk.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"state")) txfk.setState(rs.getString("state"));
			
			return txfk;
		}
	}
	
	
	/**
	 * 包装对象(摊销付款明细)
	 * @author liyt
	 */
	class TxfkDescRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			TxfkDesc txfkDesc = new TxfkDesc();

			if(SqlUtil.columnIsExist(rs,"id")) txfkDesc.setId(rs.getString("id"));
			if(SqlUtil.columnIsExist(rs,"je")) txfkDesc.setJe(rs.getDouble("je"));
			if(SqlUtil.columnIsExist(rs,"remark")) txfkDesc.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"txfk_id")) txfkDesc.setTxfk_id(rs.getString("txfk_id"));
			if(SqlUtil.columnIsExist(rs,"txrq")) txfkDesc.setTxrq(rs.getString("txrq"));
			
			return txfkDesc;
		}
	}

}
