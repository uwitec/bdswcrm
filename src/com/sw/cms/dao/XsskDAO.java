package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Page;
import com.sw.cms.model.Xssk;
import com.sw.cms.model.XsskDesc;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;

public class XsskDAO extends JdbcBaseDAO {
	
	/**
	 * ���������տ��б�����ҳ��
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getXsskList(String con,int curPage, int rowsPerPage){
		String sql = "select a.*,b.name as wldwmc from xssk a left join clients b on b.id=a.client_name where 1=1";

		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * ���������տ���Ϣ
	 * @param ysk
	 */
	public void saveXssk(Xssk xssk,List xsskDescs){
		String sql = "insert into xssk(sk_date,client_name,skzh,jsr,skje,state,remark,is_ysk,ysk_ye,czr,cz_date,delete_key,id) values(?,?,?,?,?,?,?,?,?,?,now(),?,?)";
		
		Object[] param = new Object[12];
		
		String is_ysk = StringUtils.nullToStr(xssk.getIs_ysk());
		if(is_ysk.equals("")){
			is_ysk = "��";
		}
		
		double ysk_ye = 0;
		if(is_ysk.equals("��")){
			ysk_ye = xssk.getSkje();
		}
		
		param[0] = xssk.getSk_date();
		param[1] = xssk.getClient_name();
		param[2] = xssk.getSkzh();
		param[3] = xssk.getJsr();
		param[4] = new Double(xssk.getSkje());
		param[5] = xssk.getState();
		param[6] = xssk.getRemark();
		param[7] = is_ysk;
		param[8] = new Double(ysk_ye);
		param[9] = xssk.getCzr();
		param[10] = xssk.getDelete_key();
		param[11] = xssk.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.saveXsskDesc(xsskDescs, xssk.getId());
		
	}
	
	
	/**
	 * ���������տ���Ϣ
	 * @param ysk
	 */
	public void updateXssk(Xssk xssk,List xsskDescs){
		String sql = "update xssk set sk_date=?,client_name=?,skzh=?,jsr=?,skje=?,state=?,remark=?,is_ysk=?,ysk_ye=?,czr=?,cz_date=now() where id=?";
		
		Object[] param = new Object[11];
		
		String is_ysk = StringUtils.nullToStr(xssk.getIs_ysk());
		if(is_ysk.equals("")){
			is_ysk = "��";
		}
		
		double ysk_ye = 0;
		if(is_ysk.equals("��")){
			ysk_ye = xssk.getSkje();
		}
		
		param[0] = xssk.getSk_date();
		param[1] = xssk.getClient_name();
		param[2] = xssk.getSkzh();
		param[3] = xssk.getJsr();
		param[4] = new Double(xssk.getSkje());
		param[5] = xssk.getState();
		param[6] = xssk.getRemark();
		param[7] = is_ysk;
		param[8] = new Double(ysk_ye);
		param[9] = xssk.getCzr();
		param[10] = xssk.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.delXsskDesc(xssk.getId());
		this.saveXsskDesc(xsskDescs, xssk.getId());
	}
	

	/**
	 * ȡ�����տ���Ϣ
	 * @param id
	 * @return
	 */
	public Object getXssk(String id){
		String sql = "select * from xssk where id='" + id + "'";
		
		return this.queryForObject(sql, new XsskRowMapper());
	}
	
	
	/**
	 * ȡ�����տ���ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public List getXsskDescs(String id){
		String sql = "select xsd_id,bcsk,xssk_id,remark,fsrq,fsje,(fsje-bcsk)as ysk from xssk_desc where xssk_id='" + id + "'";
		
		return this.getResultList(sql);
	}
	
	
	
	/**
	 * ɾ�������տ���Ϣ
	 * @param id
	 */
	public void delXssk(String id){
		String sql = "delete from xssk where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from xssk_desc where xssk_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ���ؿͻ���ǰ����Ӧ�տ��б�
	 * @param client_id
	 * @return
	 */
	public List getYskByClientId(String client_id){
		String sql = "select id as xsd_id,creatdate as fsrq,sjcjje as fsje,(sjcjje-skje) as ysk,skje as yisk from xsd where state='�ѳ���' and skxs<>'����' and client_name='" + client_id + "'";
		
		//����Ӧ���ڳ���Ϣ,��������ڳ�ֵ������ҪUNION�ڳ�ֵ
		String init_sql = "select '�ڳ�Ӧ��' as xsd_id,DATE_FORMAT(cz_date,'%Y-%m-%d') as fsrq,ysqc as fsje,(ysqc-yishouje) as ysk,yishouje as yisk from client_wl_init where client_name='" + client_id + "' and ysqc>yishouje";
		List list = this.getResultList(init_sql);
		if(list != null && list.size()>0){
			sql = "(" + sql + ") union (" + init_sql + ")";
		}
		return this.getResultList(sql);
	}
	
	
	/**
	 * ����ɾ�����ȡ�����տ���Ϣ�������ڷ���null
	 * @param delete_key
	 * @return
	 */
	public Xssk getXsskByDeleteKey(String delete_key){
		Xssk xssk = null;
		
		String sql = "select * from xssk where delete_key='" + delete_key + "'";
		
		Object obj = this.queryForObject(sql, new XsskRowMapper());
		if(obj != null){
			xssk = (Xssk)obj;
		}
		
		return xssk;
	}
	
	
	/**
	 * ���ؿͻ�����Ԥ�տ��б�
	 * @param client_id
	 * @return
	 */
	public List getYushoukunList(String client_id){
		String sql = "select * from xssk where is_ysk='��' and state='���ύ' and ysk_ye>0 order by sk_date";
		return this.getResultList(sql);
	}
	
	
	/**
	 * <p>���ݳ��ⵥID�鿴�Ƿ����Ӧ�տ�</p>
	 * <p>��������򷵻�Ӧ�տ��ţ������ڷ��ؿ��ַ���</p>
	 * @param ckd_id
	 * @return
	 */
	public String getYskIdByCkdId(String ckd_id){
		String id = "";
		String sql = "select id from ysk where ckd_id='" + ckd_id + "'";
		
		List list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list.get(0);
			id = (String)map.get("id");
		}
		
		return id;
	}

	
	
	/**
	 * ȡ��ǰ���õ����к�
	 * @return
	 */
	public String getXsskID() {
		String sql = "select yskid from cms_all_seq";

		// ȡ��ǰ���к�
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		String day = DateComFunc.getCurDay();
		// �����кż�1
		sql = "update cms_all_seq set yskid=yskid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "SK" + day + "-" + curId;
	}
	
	
	/**
	 * ���ݿͻ����ȡ�ͻ�û�г�����Ԥ�տ��б�
	 * @param client_id
	 * @return
	 */
	public List getYskListByClientId(String client_id){
		String sql = "select * from xssk where is_ysk='��' and state='���ύ' and ysk_ye>0 order by sk_date";
		return this.getResultList(sql);
	}
	
	
	/**
	 * ����Ԥ�տ����
	 * @param id
	 * @param ysk_ye
	 */
	public void updateYskye(String id,double ysk_ye){
		String sql = "update xssk set ysk_ye=" + ysk_ye + " where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * �������۵����ս�����״̬
	 * @param cgfkDescs
	 */
	public void updateXsdFk(Xssk xssk,List xsskDescs){
		String sql = "";
		
		if(xsskDescs != null && xsskDescs.size()>0){
			for(int i =0;i<xsskDescs.size();i++){
				
				XsskDesc xsskDesc = (XsskDesc)xsskDescs.get(i);
				if(xsskDesc != null){
					if(xsskDesc.getBcsk() > 0){
						if(xsskDesc.getXsd_id().equals("�ڳ�Ӧ��")){
							//�����ڳ�Ӧ��
							sql = "update client_wl_init set yishouje=yishouje+" + xsskDesc.getBcsk() + "where client_name='" + xssk.getClient_name() + "'";
							this.getJdbcTemplate().update(sql);
						}else{
							sql = "update xsd set skje=skje+" + xsskDesc.getBcsk() + ",skrq='" + xssk.getSk_date() + "' where id='" + xsskDesc.getXsd_id() + "'";
							this.getJdbcTemplate().update(sql);
							
							sql = "select count(1) as nums from xsd where sjcjje>skje and id='" + xsskDesc.getXsd_id() + "'";
							int count = this.getJdbcTemplate().queryForInt(sql);
							
							if(count > 0){
								sql = "update xsd set skxs='��������' where id='" + xsskDesc.getXsd_id() + "'";
							}else{
								sql = "update xsd set skxs='����' where id='" + xsskDesc.getXsd_id() + "'";
							}
							this.getJdbcTemplate().update(sql);
						}
					}
				}
			}
		}
	}
	
	
	
	/**
	 * ���������տ���ϸ
	 * @param xsskDescs
	 * @param xssk_id
	 */
	private void saveXsskDesc(List xsskDescs,String xssk_id){
		String sql = "";
		Object[] param = new Object[6];
		
		if(xsskDescs != null && xsskDescs.size()>0){
			for(int i =0;i<xsskDescs.size();i++){
				
				XsskDesc xsskDesc = (XsskDesc)xsskDescs.get(i);
				if(xsskDesc != null){
					if(xsskDesc.getBcsk() > 0){
						sql = "insert into xssk_desc(xssk_id,xsd_id,bcsk,remark,fsrq,fsje) values(?,?,?,?,?,?)";
						
						param[0] = xssk_id;
						param[1] = xsskDesc.getXsd_id();
						param[2] = new Double(xsskDesc.getBcsk());
						param[3] = xsskDesc.getRemark();
						param[4] = xsskDesc.getFsrq();
						param[5] = xsskDesc.getFsje();
						
						this.getJdbcTemplate().update(sql,param); //��Ӹ�����ϸ
						
						
					}
				}
			}
		}
	}
	
	
	/**
	 * ɾ�������տ���ϸ
	 * @param xssk_id
	 */
	private void delXsskDesc(String xssk_id){
		String sql = "delete from xssk_desc where xssk_id='" + xssk_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ��װ����(�����տ�)
	 * 
	 * @author liyt
	 * 
	 */
	class XsskRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Xssk xssk = new Xssk();

			if(SqlUtil.columnIsExist(rs,"id")) xssk.setId(rs.getString("id"));
			if(SqlUtil.columnIsExist(rs,"sk_date")) xssk.setSk_date(rs.getString("sk_date"));
			if(SqlUtil.columnIsExist(rs,"client_name")) xssk.setClient_name(rs.getString("client_name"));
			if(SqlUtil.columnIsExist(rs,"skzh")) xssk.setSkzh(rs.getString("skzh"));
			if(SqlUtil.columnIsExist(rs,"jsr")) xssk.setJsr(rs.getString("jsr"));
			if(SqlUtil.columnIsExist(rs,"skje")) xssk.setSkje(rs.getDouble("skje"));
			if(SqlUtil.columnIsExist(rs,"state")) xssk.setState(rs.getString("state"));
			if(SqlUtil.columnIsExist(rs,"remark")) xssk.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"is_ysk")) xssk.setIs_ysk(rs.getString("is_ysk"));
			if(SqlUtil.columnIsExist(rs,"ysk_ye")) xssk.setYsk_ye(rs.getDouble("ysk_ye"));
			if(SqlUtil.columnIsExist(rs,"czr")) xssk.setCzr(rs.getString("czr"));
			if(SqlUtil.columnIsExist(rs,"delete_key")) xssk.setDelete_key(rs.getString("delete_key"));
			
			return xssk;
		}
	}
	
	
	/**
	 * ��װ����(�����տ���ϸ)
	 * 
	 * @author liyt
	 * 
	 */
	class XsskDescRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			XsskDesc xsskDesc = new XsskDesc();

			if(SqlUtil.columnIsExist(rs,"id")) xsskDesc.setId(rs.getInt("id"));
			if(SqlUtil.columnIsExist(rs,"xsd_id")) xsskDesc.setXsd_id(rs.getString("xsd_id"));
			if(SqlUtil.columnIsExist(rs,"bcsk")) xsskDesc.setBcsk(rs.getDouble("bcsk"));
			if(SqlUtil.columnIsExist(rs,"xssk_id")) xsskDesc.setXssk_id(rs.getString("xssk_id"));
			if(SqlUtil.columnIsExist(rs,"remark")) xsskDesc.setRemark(rs.getString("remark"));
			
			return xsskDesc;
		}
	}	

}
