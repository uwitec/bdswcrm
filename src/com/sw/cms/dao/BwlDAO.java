package com.sw.cms.dao;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.model.Bwl;
import com.sw.cms.model.Page;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.UUIDGenerator;

/**
 * 信息发布内部公告
 * @author liyt
 *
 */

public class BwlDAO extends JdbcBaseDAO {
	
	/**
	 * 取备忘录信息列表
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getBwlList(int curPage, int rowsPerPage,String user_id){
		
		String sql = "select * from bwl  where czr='"+user_id+"' order by cz_date desc";
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(Bwl.class));
	}
	
	/**
	 * 取备忘录共享信息列表
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getBwlShareList(int curPage, int rowsPerPage,String user_id){
		
		String sql = "select distinct a.* from bwl a left join bwl_share b on a.id=b.bwl_id where a.czr='"+user_id+"' or b.share like '"+StaticParamDo.getRealNameById(user_id)+"'  order by cz_date desc";
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(Bwl.class));
	}
	
	/**
	 * 取用户名信息列表
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getLxrAll(String real_name, int curPage, int rowsPerPage){
		String sql = "select * from sys_user where is_sys_user='1' and is_del='0' and real_name<>'系统管理员' ";
		
		if(!(real_name.equals("")))
		{
			sql+=" and real_name like '%" + real_name + "%'";
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
		
	/**
	 * 保存备忘录信息
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
	 * 保存报废单商品信息
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
	 * 更新备忘录信息
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
	 * 根据ID取备忘录信息
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
	 * 删除备忘录信息
	 * @param id
	 */
	public void delBwl(String id){
		String sql = "delete from bwl where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		this.delBwlShare(id);
	}

	/**
	 * 删除备忘录共享信息
	 * @param id
	 */
	public void delBwlShare(String id){
		String sql = "delete from bwl_share where bwl_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 查看备忘录是否已经提交
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
