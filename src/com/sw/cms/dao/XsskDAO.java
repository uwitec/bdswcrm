package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
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
		String sql = "insert into xssk(sk_date,client_name,skzh,jsr,skje,state,remark,is_ysk,ysk_ye,czr,cz_date,delete_key,skfs,pos_id,id) values(?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?)";
		
		Object[] param = new Object[14];
		
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
		param[11] = xssk.getSkfs();
		param[12] = xssk.getPos_id();
		param[13] = xssk.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.saveXsskDesc(xsskDescs, xssk.getId());
		
	}
	
	
	/**
	 * ���������տ���Ϣ
	 * @param ysk
	 */
	public void updateXssk(Xssk xssk,List xsskDescs){
		String sql = "update xssk set sk_date=?,client_name=?,skzh=?,jsr=?,skje=?,state=?,remark=?,is_ysk=?,ysk_ye=?,czr=?,cz_date=now(),skfs=?,pos_id=? where id=?";
		
		Object[] param = new Object[13];
		
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
		param[10] = xssk.getSkfs();
		param[11] = xssk.getPos_id();
		param[12] = xssk.getId();
		
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
		
		return this.queryForObject(sql, new BeanRowMapper(Xssk.class));
	}
	
	
	/**
	 * �ж������տ��Ƿ��Ѿ��ύ
	 * @param id
	 * @return
	 */
	public boolean isXsskSub(String id){
		boolean is = false;
		String sql = "select count(*) as counts from xssk where id='" + id + "' and state='���ύ'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * ȡ�����տ���ϸ��Ϣ
	 * @param id
	 * @return
	 */
	public List getXsskDescs(String id){
		String sql = "select xsd_id,bcsk,xssk_id,remark,fsrq,fsje,ysk from xssk_desc where xssk_id='" + id + "'";
		
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
	 * ���������۶������ڳ�Ӧ�ա��������ˣ�Ӧ�գ�
	 * @param client_id
	 * @return
	 */
	public List getYskByClientId(String client_id){
		String sql = "select id as xsd_id,creatdate as fsrq,sjcjje as fsje,(sjcjje-skje) as ysk,skje as yisk from xsd where state='�ѳ���' and skxs<>'����' and client_name='" + client_id + "' order by id";
		
		//����Ӧ���ڳ���Ϣ,��������ڳ�ֵ������ҪUNION�ڳ�ֵ
		String init_sql = "select '�ڳ�Ӧ��' as xsd_id,DATE_FORMAT(cz_date,'%Y-%m-%d') as fsrq,ysqc as fsje,(ysqc-yishouje) as ysk,yishouje as yisk from client_wl_init where client_name='" + client_id + "' and round(ysqc,2)<>round(yishouje,2)";
		List list = this.getResultList(init_sql);
		if(list != null && list.size()>0){
			sql = "(" + sql + ") union (" + init_sql + ")";
		}
		
		//����Ӧ���ڳ���Ϣ,��������ڳ�ֵ������ҪUNION�ڳ�ֵ
		String pz_sql = "select id as jhd_id,DATE_FORMAT(cz_date,'%Y-%m-%d') as fsrq,pzje as fsje,(pzje-jsje) as yfje,jsje as yisk from pz where state='���ύ' and type='Ӧ��' and client_name='" + client_id + "' and round(jsje,2)<>round(pzje,2) order by id";
		List pzList = this.getResultList(pz_sql);
		if(pzList != null && pzList.size()>0){
			sql = "(" + sql + ") union (" + pz_sql + ")";
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
		
		Object obj = this.queryForObject(sql, new BeanRowMapper(Xssk.class));
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
					if(xsskDesc.getBcsk() != 0){
						if(xsskDesc.getXsd_id().equals("�ڳ�Ӧ��")){
							//�����ڳ�Ӧ��
							sql = "update client_wl_init set yishouje=yishouje+" + xsskDesc.getBcsk() + "where client_name='" + xssk.getClient_name() + "'";
							this.getJdbcTemplate().update(sql);
						}else if(xsskDesc.getXsd_id().indexOf("PZ") != -1){
							//������������
							sql = "update pz set jsje=jsje+" + xsskDesc.getBcsk() + " where id='" + xsskDesc.getXsd_id() + "'";
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
		Object[] param = new Object[7];
		
		if(xsskDescs != null && xsskDescs.size()>0){
			for(int i =0;i<xsskDescs.size();i++){
				
				XsskDesc xsskDesc = (XsskDesc)xsskDescs.get(i);
				if(xsskDesc != null){
					if(xsskDesc.getBcsk() != 0){
						sql = "insert into xssk_desc(xssk_id,xsd_id,bcsk,remark,fsrq,fsje,ysk) values(?,?,?,?,?,?,?)";
						
						param[0] = xssk_id;
						param[1] = xsskDesc.getXsd_id();
						param[2] = new Double(xsskDesc.getBcsk());
						param[3] = xsskDesc.getRemark();
						param[4] = xsskDesc.getFsrq();
						param[5] = xsskDesc.getFsje();
						param[6] = xsskDesc.getYsk();
						
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
	 * �����տ���ϸ�Ƿ���ڳ�ͻ(�����տԤ�ճ�Ӧ��)
	 * @param xssk_id
	 * @param xsd_id
	 * @param client_name
	 * @param bcsk
	 * @return
	 */
	public boolean isXsskDescExist(String xssk_id,String xsd_id,String client_name,double bcsk){
		boolean is = false;
		double skje=0;
		
		
		String sql = "select count(1) as counts from xssk_desc a join xssk b on b.id=a.xssk_id  where b.state<>'���ύ'  and a.xssk_id<>'" + xssk_id + "' and a.xsd_id='" + xsd_id + "' and b.client_name='" + client_name + "'";
		int count1 = this.getJdbcTemplate().queryForInt(sql);
		
		sql = "select count(1) as count from yushou_to_yingshou_desc a join yushou_to_yingshou b on b.id=a.yw_id  where b.state<>'���ύ'  and a.xsd_id='" + xsd_id + "' and b.client_name='" + client_name + "'";
		int count2 = this.getJdbcTemplate().queryForInt(sql);
		
		if(xsd_id.equals("�ڳ�Ӧ��")){
		   sql="select (ysqc-yishouje) as ysk from client_wl_init  where  client_name='" + client_name + "'";
		   skje = this.getJdbcTemplate().queryForLong(sql);	
		   
		}else if(xsd_id.indexOf("PZ") != -1){
		   sql="select (pzje-jsje) as ysk from pz where state='���ύ' and type='Ӧ��' and client_name='" + client_name + "'";
		   skje = this.getJdbcTemplate().queryForLong(sql);
		}else{
		   sql="select (sjcjje-skje) as ysk from xsd where state='�ѳ���'  and id='" + xsd_id + "' and  client_name='" + client_name + "'";	
		   skje = this.getJdbcTemplate().queryForLong(sql);
		}
		if((count1+count2) > 0){
			is = true;
		}
		
		if(bcsk>skje){
			is = true;
		}
		
		return is;
	}

}
