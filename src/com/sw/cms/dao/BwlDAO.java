package com.sw.cms.dao;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Bwl;
import com.sw.cms.model.Page;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.UUIDGenerator;

/**
 * ��Ϣ�����ڲ�����
 * @author liyt
 *
 */

public class BwlDAO extends JdbcBaseDAO {
	
	/**
	 * ȡ����¼��Ϣ�б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getBwlList(int curPage, int rowsPerPage,String user_id){
		
		String sql = "select * from bwl  where czr='"+user_id+"' order by cz_date desc";
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(Bwl.class));
	}
	
	/**
	 * ȡ����¼������Ϣ�б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getBwlShareList(int curPage, int rowsPerPage,String user_id){
		
		String sql = "select distinct a.* from bwl a left join bwl_share b on a.id=b.bwl_id where a.czr='"+user_id+"' or b.share like '"+StaticParamDo.getRealNameById(user_id)+"'  order by cz_date desc";
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(Bwl.class));
	}
	
	/**
	 * ȡ�û�����Ϣ�б�
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getLxrAll(String real_name, int curPage, int rowsPerPage){
		String sql = "select * from sys_user where is_sys_user='1' and is_del='0' and real_name<>'ϵͳ����Ա' ";
		
		if(!(real_name.equals("")))
		{
			sql+=" and real_name like '%" + real_name + "%'";
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
		
	/**
	 * ���汸��¼��Ϣ
	 * @param info
	 */
	public void saveBwl(Bwl info){
		String sql = "insert into bwl(title,content,czr,cz_date,id,gxr) values(?,?,?,now(),?,?)";
		Object[] param = new Object[5];
		
		param[0] = info.getTitle();
		param[1] = info.getContent();
		param[2] = info.getCzr();
		String id=UUIDGenerator.getUUID();
		param[3] = id;
		param[4] = info.getGxr();
		
		
		
		this.getJdbcTemplate().update(sql,param);
		
		this.saveBwlShare(info,id);
	}
	
	
	/**
	 * ���汨�ϵ���Ʒ��Ϣ
	 * 
	 * @param bwl
	 */
	public void saveBwlShare(Bwl bwl,String id) 
	{ 
		String sql = "";
		Object[] param = new Object[2];	
		String bwl_id = id;
		String[] shares = bwl.getGxr().split(",");		
		if(shares != null && shares.length > 0){
			
			for(int i=0;i<shares.length;i++)
			{
				   sql = "insert into bwl_share(bwl_id,share) values(?,?)";
             		   param[0] = bwl_id;
		               param[1] = shares[i] ;
		               
		               this.getJdbcTemplate().update(sql,param);
					
			}
		}
	}
	
	
	/**
	 * ���±���¼��Ϣ
	 * @param info
	 */
	public void updateBwl(Bwl info){
		String sql = "update bwl set title=?,content=?,czr=?,cz_date=now(),gxr=? where id=?";
		Object[] param = new Object[5];
		
		param[0] = info.getTitle();
		param[1] = info.getContent();
		param[2] = info.getCzr();	
		param[3] = info.getGxr();
		param[4] = info.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		delBwlShare(info.getId());
		
		this.saveBwlShare(info,info.getId());
	}
	
	
	/**
	 * ����IDȡ����¼��Ϣ
	 * @param id
	 * @return
	 */
	public Bwl getBwl(String id){
		Bwl info = new Bwl();
		String sql = "select * from bwl where id='" + id + "'";		
		Object obj =this.queryForObject(sql, new BeanRowMapper(Bwl.class));
		if(obj != null){
			info = (Bwl)obj;
		}
		return info;
	}
	
	
	/**
	 * ɾ������¼��Ϣ
	 * @param id
	 */
	public void delBwl(String id){
		String sql = "delete from bwl where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		this.delBwlShare(id);
	}

	/**
	 * ɾ������¼������Ϣ
	 * @param id
	 */
	public void delBwlShare(String id){
		String sql = "delete from bwl_share where bwl_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * �鿴����¼�Ƿ��Ѿ��ύ
	 * @param ckd_id
	 * @return
	 */
	public boolean isBwlSubmit(String bwl_id){
		boolean is = false;
		String sql = "select count(*) from bwl where id='" + bwl_id + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
}
