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
 * ̯������DAO��
 * @author liyt
 *
 */
public class TxfkDAO extends JdbcBaseDAO {
	
	
	/**
	 * ���ݲ�ѯ����ȡ̯�������б�
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
	 * ����̯��������Ϣ
	 * ��Ų�������ӣ���Ŵ��ڸ���
	 * @param txfk
	 * @param txfkDescs
	 */
	public void updateTxfk(Txfk txfk,List txfkDescs){
		String sql = "select * from txfk where id='" + txfk.getId() + "'";
		List list = this.getResultList(sql);
		
		if(list != null && list.size() >0){
			//��Ŵ��ڣ����¼�¼
			sql = "update txfk set client_name=?,fk_date=?,jsr=?,account_id=?,fklx=?,fkje=?,remark=?,czr=?,cz_date=now(),state=? where id=?";
		}else{
			//��Ų����ڣ�������¼
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
		
		//����̯��������Ϣ(��ӻ����)
		this.getJdbcTemplate().update(sql,param);
		
		//ɾ����ϸ��Ϣ
		this.delTxfkDescs(txfk.getId());
		
		//����µ���ϸ��Ϣ
		this.addTxfkDesc(txfkDescs, txfk.getId());
		
	}
	
	
	/**
	 * ���ݱ�Ż�ȡ̯��������Ϣ
	 * @param id
	 * @return
	 */
	public Txfk getTxfk(String id){
		String sql = "select * from txfk where id='" + id + "'";
		return (Txfk)this.queryForObject(sql, new TxfkRowMapper());
	}
	
	
	/**
	 * ����̯��������ȡ��ϸ��Ϣ�б�
	 * @param id
	 * @return
	 */
	public List getTxfkDescs(String id){
		String sql = "select * from txfk_desc where txfk_id='" + id + "'";
		return this.getResultList(sql, new TxfkDescRowMapper());
	}
	
	
	/**
	 * ɾ��̯�����������Ϣ
	 * @param id
	 */
	public void delTxfk(String id){
		String sql = "delete from txfk where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from txfk_desc where txfk_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ȡ��ǰ���õ�̯��������
	 * @return
	 */
	public String getTxfkID() {
		String sql = "select txfkid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();
		// �����кż�1
		sql = "update cms_all_seq set txfkid=txfkid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "TX" + day + "-" + curId;
	}
	
	
	/**
	 * ȡ��ǰ���õ�̯��������ϸ���
	 * @return
	 */
	private String getTxfkDescID() {
		String sql = "select txfkdescid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();
		// �����кż�1
		sql = "update cms_all_seq set txfkdescid=txfkdescid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "TXFK" + day + "-" + curId;
	}
	
	
	/**
	 * ����̯��������ϸ��Ϣ
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
	 * ɾ��̯��������ϸ��Ϣ
	 * @param txfk_id
	 */
	private void delTxfkDescs(String txfk_id){
		String sql = "delete from txfk_desc where txfk_id='" + txfk_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ��װ����(̯������)
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
	 * ��װ����(̯��������ϸ)
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
