package com.sw.cms.dao;

import java.util.List;
import java.util.Map;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;

import com.sw.cms.model.Clients;
import com.sw.cms.model.Page;
import com.sw.cms.model.Hykzz;
import com.sw.cms.model.Hykfl;
import com.sw.cms.model.Hykda;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StaticParamDo;

/**
 * 会员卡档案
 * @author zuohj
 *
 */

public class HykdaDAO extends JdbcBaseDAO {
	
	/**
	 * 取会员卡档案列表
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getHykdaList(int curPage, int rowsPerPage,String con){
		
		String sql = "select * from hykda where 1=1 ";
		if (!con.equals("")) {
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage,new BeanRowMapper(Hykda.class));
	}
	
	/**
	 * 取所有会员卡分类列表
	 * @return
	 */
	public List getAllHykflList(){
		String sql = "select * from hykfl";
		
		return this.getJdbcTemplate().query(sql, new BeanRowMapper(Hykfl.class));
	}
		
	
	/**
	 * 更新会员卡档案信息
	 * @param info
	 */
	public void updateHykda(Hykda info){
		String sql = "update hykda set sfty=?,hymc=?,lxrname=?,lxdh=?,mobile=?,address=?,mail=?,sfzh=?,sex=?,gzdw=?,cz_date=now(),czr=?,birth=? where id=?";
		Object[] param = new Object[13];
		
		param[0] = info.getSfty();
		param[1] = info.getHymc();
		param[2] = info.getLxrname();
		param[3] = info.getLxdh();
		param[4] = info.getMobile();
		param[5] = info.getAddress();
		param[6] = info.getMail();
		param[7] = info.getSfzh();
		param[8] = info.getSex();
		param[9] = info.getGzdw();
		param[10] = info.getCzr();
		param[11] = info.getBirth();
		param[12] = info.getId();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 根据ID取会员卡档案信息
	 * @param id
	 * @return
	 */
	public Hykda getHykda(String hykh){
		Hykda info = new Hykda();
		String sql = "select * from hykda where hykh='" + hykh + "'";		
		Object obj =this.queryForObject(sql, new BeanRowMapper(Hykda.class));
		if(obj != null){
			info = (Hykda)obj;
		}
		return info;
	}
	 /**
	    * 返回会员卡档案信息
	    * @param hykh
	    * @return
	    */
	   public Object getHykdaByHykh(String hykh)
	   {
		   String sql="select p.*,s.card_type,s.dept,s.csjf,s.csje,s.yxrq,s.sxrq,s.sfcz,s.ssfl from  hykda p left join hykzz s on p.hykh=s.hykh where s.hykh='"+hykh+"'";
		   return this.getResultMap(sql);
	   }
	
	/**
	 * 删除会员卡档案信息
	 * @param hykh
	 */
	public void delHykda(String hykh){
		String sql = "delete from hykda where hykh='" + hykh + "'";
		this.getJdbcTemplate().update(sql);
		
	}

	
	/**
	 * 查看会员卡档案是否已经提交
	 * @param hykda_id
	 * @return
	 */
	public boolean isHykdaSubmit(String hykda_id){
		boolean is = false;
		String sql = "select count(*) from hykda where id='" + hykda_id + "'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	/**
	 * 返回会员卡档案可用ID
	 * 
	 * @return
	 */
	public String getHykdaId() {
		String sql = "select hykdaid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";

		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set hykdaid=hykdaid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "HYDA" + day + "-" + curId;

	}
	
	/**
	 * 保存会员卡档案信息
	 * @param info
	 */
	public void saveHykda(Hykda info){
		String sql = "insert into hykda(id,hykh,state,zkrq,zkjsr) values(?,?,?,now(),?)";
		Object[] param = new Object[4];
		param[0] = info.getId();
		param[1] = info.getHykh();
		param[2] = "未使用";			
		param[3] = info.getCzr();
		
		this.getJdbcTemplate().update(sql,param);
		
		
	}
	
	/**
	 * 保存会员卡发卡单位信息
	 * @param info
	 */
	public void saveHykfk(Hykda info){
		String sql = "update hykda set state=?,hymc=?,hybh=?,lxrname=?,lxdh=?,address=?,fkbz=?,fkrq=?,fkjsr=?,ffjg=?,mobile=?,mail=?,sfzh=?,sex=?,gzdw=? where hykh=?";
		Object[] param = new Object[16];
		
		param[0] = "已领用";
		
		param[1] = info.getHymc();
		param[2] = info.getHybh();			
		param[3] = info.getLxrname();
		param[4] = info.getLxdh();
		param[5] = info.getAddress();			
		param[6] = info.getFkbz();
		param[7] = info.getFkrq();			
		param[8] = info.getFkjsr();
		param[9] = info.getFfjg();	
		param[10] = info.getMobile();
		param[11] = info.getMail();	
		param[12] = info.getSfzh();	
		param[13] = info.getSex();
		param[14] = info.getGzdw();
		
		param[15] = info.getHykh();
		
		this.getJdbcTemplate().update(sql,param);
		
		
	}
	
	
}
