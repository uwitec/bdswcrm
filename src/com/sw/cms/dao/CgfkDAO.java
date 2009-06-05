package com.sw.cms.dao;

/**
 * �ɹ��������ݿ����
 * author by liyt
 * 2008-03-27
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Cgfk;
import com.sw.cms.model.CgfkDesc;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;

public class CgfkDAO extends JdbcBaseDAO {
	
	
	/**
	 * ȡ�ɹ�Ӧ�����б�
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getCgfks(String con,int curPage, int rowsPerPage){
		String sql = "select a.*,b.name as gysmc from cgfk a left join clients b on b.id=a.gysbh  where 1=1";
		
		if(!con.equals("")){
			sql = sql + " " + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * ���ݲɹ�����IDȡ�����Ϣ
	 * @param yfk_id
	 * @return
	 */
	public Object getCgfk(String id){
		String sql = "select * from cgfk where id='" + id + "'";
		return this.queryForObject(sql, new CgfkRowMapper());
	}
	
	
	/**
	 * ���ݲɹ�����IDȡ��ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public List getCgfkDesc(String id){
		String sql = "select jhd_id,fsrq,fsje,(fsje-bcfk) as yfje,bcfk,remark from cgfk_desc where cgfk_id='" + id + "'";
		return this.getResultList(sql);
	}
	
	
	/**
	 * ���ݹ�Ӧ�̱��ȡ����Ӧ�������
	 * @param gysbh
	 * @return
	 */
	public List getDfkDesc(String gysbh){
		String sql = "select id as jhd_id,cg_date as fsrq,total as fsje,(total-fkje) as yfje,fkje as yifje from jhd where state<>'�ѱ���' and fklx<>'�Ѹ�' and gysbh='" + gysbh + "'";
		
		//����Ӧ���ڳ���Ϣ,��������ڳ�ֵ������ҪUNION�ڳ�ֵ
		String init_sql = "select '�ڳ�Ӧ��' as jhd_id,DATE_FORMAT(cz_date,'%Y-%m-%d') as fsrq,yfqc as fsje,(yfqc-yifuje) as yfje,yifuje as yisk from client_wl_init where client_name='" + gysbh + "' and yfqc>yifuje";
		List list = this.getResultList(init_sql);
		if(list != null && list.size()>0){
			sql = "(" + sql + ") union (" + init_sql + ")";
		}
		
		return this.getResultList(sql);
	}

	
	/**
	 * ɾ���ɹ�������Ϣ���ݲɹ�����ID
	 * @param jhd_id
	 * @return
	 */
	public void delCgfk(String id){
		String sql = "delete from cgfk where id='" + id + "'";		
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from cgfk_desc where cgfk_id='" + id + "'";		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ����ɹ�������Ϣ��������ϸ��Ϣ
	 * @param cgfk
	 * @param cgfkDescs
	 */
	public void saveCgfk(Cgfk cgfk,List cgfkDescs){
		String sql = "insert into cgfk(id,fk_date,gysbh,fkzh,jsr,fkje,remark,state,is_yfk,yfk_ye,czr,cz_date,delete_key) values(?,?,?,?,?,?,?,?,?,?,?,now(),?)";
		
		String is_yfk = StringUtils.nullToStr(cgfk.getIs_yfk());
		if(is_yfk.equals("")){
			is_yfk = "��";
		}
		
		double yfk_ye = 0;
		if(is_yfk.equals("��")){
			yfk_ye = cgfk.getFkje();
		}
		
		Object[] param = new Object[12];
		
		param[0] = cgfk.getId();
		param[1] = cgfk.getFk_date();
		param[2] = cgfk.getGysbh();
		param[3] = cgfk.getFkzh();
		param[4] = cgfk.getJsr();
		param[5] = new Double(cgfk.getFkje());
		param[6] = cgfk.getRemark();
		param[7] = cgfk.getState();
		param[8] = is_yfk;
		param[9] = new Double(yfk_ye);
		param[10] = cgfk.getCzr();
		param[11] = cgfk.getDelete_key();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.saveCgfkDesc(cgfkDescs, cgfk.getId());
	}
	
	
	/**
	 * ���²ɹ����������Ϣ
	 * @param cgfk
	 * @param cgfkDescs
	 */
	public void updateCgfk(Cgfk cgfk,List cgfkDescs){
		String sql = "update cgfk set fk_date=?,gysbh=?,fkzh=?,jsr=?,fkje=?,remark=?,state=?,is_yfk=?,yfk_ye=?,czr=?,cz_date=now() where id=?";
		
		String is_yfk = StringUtils.nullToStr(cgfk.getIs_yfk());
		if(is_yfk.equals("")){
			is_yfk = "��";
		}
		
		double yfk_ye = 0;
		if(is_yfk.equals("��")){
			yfk_ye = cgfk.getFkje();
		}
		
		Object[] param = new Object[11];		
		
		param[0] = cgfk.getFk_date();
		param[1] = cgfk.getGysbh();
		param[2] = cgfk.getFkzh();
		param[3] = cgfk.getJsr();
		param[4] = new Double(cgfk.getFkje());
		param[5] = cgfk.getRemark();
		param[6] = cgfk.getState();
		param[7] = is_yfk;
		param[8] = new Double(yfk_ye);
		param[9] = cgfk.getCzr();
		param[10] = cgfk.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.delCgfkDesc(cgfk.getId());
		
		this.saveCgfkDesc(cgfkDescs, cgfk.getId());
	}
	
	
	/**
	 * ɾ���ɹ�������ϸ���ݲɹ�����ID
	 * @param yfk_id
	 * @return
	 */
	private void delCgfkDesc(String id){
		String sql = "delete from cgfk_desc where cgfk_id='" + id + "'";		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 
	 * @param cgfkDescs
	 * @param cgfk_id
	 */
	private void saveCgfkDesc(List cgfkDescs,String cgfk_id){
		String sql = "";
		Object[] param = new Object[6];
		
		if(cgfkDescs != null && cgfkDescs.size()>0){
			for(int i =0;i<cgfkDescs.size();i++){
				
				CgfkDesc cgfkDesc = (CgfkDesc)cgfkDescs.get(i);
				if(cgfkDesc != null){
					if(cgfkDesc.getBcfk() != 0){
						sql = "insert into cgfk_desc(cgfk_id,jhd_id,bcfk,remark,fsrq,fsje) values(?,?,?,?,?,?)";
						
						param[0] = cgfk_id;
						param[1] = cgfkDesc.getJhd_id();
						param[2] = new Double(cgfkDesc.getBcfk());
						param[3] = cgfkDesc.getRemark();
						param[4] = cgfkDesc.getFsrq();
						param[5] = cgfkDesc.getFsje();
						
						this.getJdbcTemplate().update(sql,param); //��Ӹ�����ϸ
						
						
					}
				}
			}
		}
	}
	
	
	
	/**
	 * ���½������Ѹ�������״̬
	 * @param cgfkDescs
	 */
	public void updateJhdFkje(Cgfk cgfk,List cgfkDescs){
		String sql = "";
		
		if(cgfkDescs != null && cgfkDescs.size()>0){
			for(int i =0;i<cgfkDescs.size();i++){
				
				CgfkDesc cgfkDesc = (CgfkDesc)cgfkDescs.get(i);
				if(cgfkDesc != null){
					if(cgfkDesc.getBcfk() > 0){
						if(cgfkDesc.getJhd_id().equals("�ڳ�Ӧ��")){
							//�����ڳ�Ӧ��
							sql = "update client_wl_init set yifuje=yifuje+" + cgfkDesc.getBcfk() + "where client_name='" + cgfk.getGysbh() + "'";
							this.getJdbcTemplate().update(sql);
						}else{
							sql = "update jhd set fkje=fkje+" + cgfkDesc.getBcfk() + " where id='" + cgfkDesc.getJhd_id() + "'";
							this.getJdbcTemplate().update(sql);
							
							sql = "select count(1) as nums from jhd where total>fkje and id='" + cgfkDesc.getJhd_id() + "'";
							int count = this.getJdbcTemplate().queryForInt(sql);
							
							if(count > 0){
								sql = "update jhd set fklx='�����Ѹ�' where id='" + cgfkDesc.getJhd_id() + "'";
							}else{
								sql = "update jhd set fklx='�Ѹ�' where id='" + cgfkDesc.getJhd_id() + "'";
							}
							this.getJdbcTemplate().update(sql);
						}
					}
				}
			}
		}
	}

	

	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String getCgfkID() {
		String sql = "select cgyfkid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();
		
		// �����кż�1
		sql = "update cms_all_seq set cgyfkid=cgyfkid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "FK" + day + "-" + curId;
	}
	
	
	/**
	 * ���ݿͻ����ȡ����Ԥ�����б�
	 * @param client_id
	 * @return
	 */
	public List getYfkListByClient(String client_id){
		String sql = "select * from cgfk where state='���ύ' and is_yfk='��' and yfk_ye>0 and gysbh='" + client_id + "'";
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * ����Ԥ�������
	 * @param id
	 * @param yfk_ye
	 */
	public void updateYfkye(String id,double yfk_ye){
		String sql = "update cgfk set yfk_ye=" + yfk_ye + " where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ���ݶ����ɾ���ؼ���ȡ�ɹ�����
	 * @param delete_key
	 * @return
	 */
	public Cgfk getCgfkByDeleteKey(String delete_key){
		Cgfk cgfk = null;
		
		String sql = "select * from cgfk where delete_key='" + delete_key + "'";
		Object obj = this.queryForObject(sql, new CgfkRowMapper());
		
		if(obj != null){
			cgfk = (Cgfk)obj;
		}
		
		return cgfk;
	}
	
	
	/**
	 * ��װ����(�ɹ�����)
	 * 
	 * @author liyt
	 * 
	 */
	class CgfkRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Cgfk cgfk = new Cgfk();

			if(SqlUtil.columnIsExist(rs,"id")) cgfk.setId(rs.getString("id"));
			if(SqlUtil.columnIsExist(rs,"fk_date")) cgfk.setFk_date(rs.getString("fk_date"));
			if(SqlUtil.columnIsExist(rs,"gysbh")) cgfk.setGysbh(rs.getString("gysbh"));
			if(SqlUtil.columnIsExist(rs,"fkzh")) cgfk.setFkzh(rs.getString("fkzh"));
			if(SqlUtil.columnIsExist(rs,"fkje")) cgfk.setFkje(rs.getDouble("fkje"));
			if(SqlUtil.columnIsExist(rs,"jsr")) cgfk.setJsr(rs.getString("jsr"));
			if(SqlUtil.columnIsExist(rs,"remark")) cgfk.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"state")) cgfk.setState(rs.getString("state"));
			
			if(SqlUtil.columnIsExist(rs,"is_yfk")) cgfk.setIs_yfk(rs.getString("is_yfk"));
			if(SqlUtil.columnIsExist(rs,"yfk_ye")) cgfk.setYfk_ye(rs.getDouble("yfk_ye"));
			if(SqlUtil.columnIsExist(rs,"czr")) cgfk.setCzr(rs.getString("czr"));
			
			if(SqlUtil.columnIsExist(rs,"delete_key")) cgfk.setDelete_key(rs.getString("delete_key"));
			
			return cgfk;
		}
	}	

}
