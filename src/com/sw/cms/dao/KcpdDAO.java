package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Kcpd;
import com.sw.cms.model.KcpdDesc;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;

public class KcpdDAO extends JdbcBaseDAO {
	
	
	/**
	 * ȡѪ���б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getKcpds(String con,int curPage, int rowsPerPage){
		String sql = "select * from kcpd where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	
	/**
	 * ����IDȡ��ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public Object getKcpd(String id){
		String sql = "select * from kcpd where id='" + id + "'";
		return this.queryForObject(sql, new KcpdMapper());
	}
	
	
	/**
	 * �����̵���Ϣ
	 * @param kcpd
	 * @param KcpdDescs
	 */
	public void saveKcpd(Kcpd kcpd,List kcpdDescs){
		String sql = "insert into kcpd(pdr,pdrq,remark,store_id,state,czr,cz_date,id) values(?,?,?,?,?,?,now(),?)";
		
		Object[] param = new Object[7];
	
		param[0] = kcpd.getPdr();
		param[1] = kcpd.getPdrq();
		param[2] = kcpd.getRemark();
		param[3] = kcpd.getStore_id();
		param[4] = kcpd.getState();
		param[5] = kcpd.getCzr();
		param[6] = kcpd.getId();
		
		this.getJdbcTemplate().update(sql,param); //�����̵���Ϣ
		this.addKcpdDescs(kcpdDescs, kcpd.getId());   //�����̵���ϸ
	}
	
	
	/**
	 * �����̵���Ϣ
	 * @param kcpd
	 * @param kcpdDescs
	 */
	public void updateKcpd(Kcpd kcpd,List kcpdDescs){
		String sql = "update kcpd set pdr=?,pdrq=?,remark=?,store_id=?,state=?,czr=?,cz_date=now() where id=?";
		
		Object[] param = new Object[7];
		param[0] = kcpd.getPdr();
		param[1] = kcpd.getPdrq();
		param[2] = kcpd.getRemark();
		param[3] = kcpd.getStore_id();
		param[4] = kcpd.getState();
		param[5] = kcpd.getCzr();
		param[6] = kcpd.getId();
		
		this.getJdbcTemplate().update(sql,param);
		this.delKcpdDescs(kcpd.getId());
		this.addKcpdDescs(kcpdDescs, kcpd.getId());
	}
	
	
	
	/**
	 * �����̵�IDȡ�̵���ϸ
	 * @param pd_id
	 * @return
	 */
	public List getKcpdDescs(String pd_id){
		String sql = "select a.*,b.qz_serial_num as qz_flag,b.dw from kcpd_desc a left join product b on b.product_id=a.product_id where  pd_id='" + pd_id + "'";
		
		return this.getResultList(sql);
	}
	
	/**
	 * �鿴����̵㵥�Ƿ��Ѿ��ύ
	 * 
	 * @param id
	 * @return
	 */
	public boolean isKcpdSubmit(String id) {
		boolean is = false;
		String sql = "select count(*) from kcpd where id='" + id
				+ "' and state='���ύ'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if (counts > 0) {
			is = true;
		}
		return is;
	}
	
	
	
	/**
	 * ɾ���̵���Ϣ
	 * @param pd_id
	 */
	public void delKcpd(String pd_id){
		String sql = "delete from kcpd where id='" + pd_id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from kcpd_desc where pd_id='" + pd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	
	/**
	 * ��ӿ���̵���ϸ��Ϣ
	 * @param kcpdDescs
	 * @param pd_id
	 */
	private void addKcpdDescs(List kcpdDescs,String pd_id){
		String sql = "";
		Object[] param = new Object[9];
		if(kcpdDescs != null && kcpdDescs.size()>0){
			for(int i=0;i<kcpdDescs.size();i++){
				KcpdDesc kcpdDesc = (KcpdDesc)kcpdDescs.get(i);
				if(kcpdDesc != null){
					if(!kcpdDesc.getProduct_name().equals("")){
						sql = "insert into kcpd_desc(pd_id,product_id,product_xh,product_name,kc_nums,sj_nums,yk,remark,qz_serial_num) values(?,?,?,?,?,?,?,?,?)";
						
						param[0] = pd_id;
						param[1] = kcpdDesc.getProduct_id();
						param[2] = kcpdDesc.getProduct_xh();
						param[3] = kcpdDesc.getProduct_name();
						param[4] = new Integer(kcpdDesc.getKc_nums());
						param[5] = new Integer(kcpdDesc.getSj_nums());
						param[6] = new Integer(kcpdDesc.getYk());
						param[7] = kcpdDesc.getRemark();
						param[8] = kcpdDesc.getQz_serial_num();   
						this.getJdbcTemplate().update(sql, param);
					}
				}
			}
		}
	}
	
	
	/**
	 * ɾ������̵���ϸ��Ϣ
	 * @param pd_id
	 */
	private void delKcpdDescs(String pd_id){
		String sql = "delete from kcpd_desc where pd_id='" + pd_id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String getKcpdID() {
		String sql = "select kcpdid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();

		// �����кż�1
		sql = "update cms_all_seq set kcpdid=kcpdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "PD" + day + "-" + curId;
	}
	
	/**
	 * ��װ����(����̵�)
	 * 
	 * @author liyt
	 * 
	 */
	class KcpdMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Kcpd kcpd = new Kcpd();

			kcpd.setId(rs.getString("id"));
			kcpd.setPdr(rs.getString("pdr"));
			kcpd.setPdrq(rs.getString("pdrq"));
			kcpd.setRemark(rs.getString("remark"));
			kcpd.setStore_id(rs.getString("store_id"));
			kcpd.setState(rs.getString("state"));
			kcpd.setCzr(rs.getString("czr"));
			
			return kcpd;
		}
	}
	
	
	/**
	 * ��װ����(����̵�)
	 * 
	 * @author liyt
	 * 
	 */
	class KcpdDescMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			KcpdDesc desc = new KcpdDesc();

			desc.setId(rs.getShort("id"));
			desc.setPd_id(rs.getString("pd_id"));
			desc.setProduct_id(rs.getString("product_id"));
			desc.setProduct_xh(rs.getString("product_xh"));
			desc.setProduct_name(rs.getString("product_name"));
			desc.setKc_nums(rs.getInt("kc_nums"));
			desc.setSj_nums(rs.getInt("sj_nums"));
			desc.setYk(rs.getInt("yk"));
			desc.setRemark(rs.getString("remark"));			
			desc.setQz_serial_num(rs.getString("qz_serial_num"));
			desc.setQz_flag(rs.getString("qz_flag"));
			return desc;
		}
	}

}
