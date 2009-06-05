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
	 * 返回销售收款列表（带分页）
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
	 * 保存销售收款信息
	 * @param ysk
	 */
	public void saveXssk(Xssk xssk,List xsskDescs){
		String sql = "insert into xssk(sk_date,client_name,skzh,jsr,skje,state,remark,is_ysk,ysk_ye,czr,cz_date,delete_key,id) values(?,?,?,?,?,?,?,?,?,?,now(),?,?)";
		
		Object[] param = new Object[12];
		
		String is_ysk = StringUtils.nullToStr(xssk.getIs_ysk());
		if(is_ysk.equals("")){
			is_ysk = "否";
		}
		
		double ysk_ye = 0;
		if(is_ysk.equals("是")){
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
	 * 更新销售收款信息
	 * @param ysk
	 */
	public void updateXssk(Xssk xssk,List xsskDescs){
		String sql = "update xssk set sk_date=?,client_name=?,skzh=?,jsr=?,skje=?,state=?,remark=?,is_ysk=?,ysk_ye=?,czr=?,cz_date=now() where id=?";
		
		Object[] param = new Object[11];
		
		String is_ysk = StringUtils.nullToStr(xssk.getIs_ysk());
		if(is_ysk.equals("")){
			is_ysk = "否";
		}
		
		double ysk_ye = 0;
		if(is_ysk.equals("是")){
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
	 * 取销售收款信息
	 * @param id
	 * @return
	 */
	public Object getXssk(String id){
		String sql = "select * from xssk where id='" + id + "'";
		
		return this.queryForObject(sql, new XsskRowMapper());
	}
	
	
	/**
	 * 取销售收款明细信息
	 * @param id
	 * @return
	 */
	public List getXsskDescs(String id){
		String sql = "select xsd_id,bcsk,xssk_id,remark,fsrq,fsje,(fsje-bcsk)as ysk from xssk_desc where xssk_id='" + id + "'";
		
		return this.getResultList(sql);
	}
	
	
	
	/**
	 * 删除销售收款信息
	 * @param id
	 */
	public void delXssk(String id){
		String sql = "delete from xssk where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from xssk_desc where xssk_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 返回客户当前所有应收款列表
	 * @param client_id
	 * @return
	 */
	public List getYskByClientId(String client_id){
		String sql = "select id as xsd_id,creatdate as fsrq,sjcjje as fsje,(sjcjje-skje) as ysk,skje as yisk from xsd where state='已出库' and skxs<>'已收' and client_name='" + client_id + "'";
		
		//处理应收期初信息,如果存在期初值，则需要UNION期初值
		String init_sql = "select '期初应收' as xsd_id,DATE_FORMAT(cz_date,'%Y-%m-%d') as fsrq,ysqc as fsje,(ysqc-yishouje) as ysk,yishouje as yisk from client_wl_init where client_name='" + client_id + "' and ysqc>yishouje";
		List list = this.getResultList(init_sql);
		if(list != null && list.size()>0){
			sql = "(" + sql + ") union (" + init_sql + ")";
		}
		return this.getResultList(sql);
	}
	
	
	/**
	 * 根据删除标记取销售收款信息，不存在返回null
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
	 * 返回客户所有预收款列表
	 * @param client_id
	 * @return
	 */
	public List getYushoukunList(String client_id){
		String sql = "select * from xssk where is_ysk='是' and state='已提交' and ysk_ye>0 order by sk_date";
		return this.getResultList(sql);
	}
	
	
	/**
	 * <p>根据出库单ID查看是否存在应收款</p>
	 * <p>如果存在则返回应收款编号，不存在返回空字符串</p>
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
	 * 取当前可用的序列号
	 * @return
	 */
	public String getXsskID() {
		String sql = "select yskid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		String day = DateComFunc.getCurDay();
		// 将序列号加1
		sql = "update cms_all_seq set yskid=yskid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "SK" + day + "-" + curId;
	}
	
	
	/**
	 * 根据客户编号取客户没有冲抵完的预收款列表
	 * @param client_id
	 * @return
	 */
	public List getYskListByClientId(String client_id){
		String sql = "select * from xssk where is_ysk='是' and state='已提交' and ysk_ye>0 order by sk_date";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 更新预收款余额
	 * @param id
	 * @param ysk_ye
	 */
	public void updateYskye(String id,double ysk_ye){
		String sql = "update xssk set ysk_ye=" + ysk_ye + " where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 更新销售单已收金额及付款状态
	 * @param cgfkDescs
	 */
	public void updateXsdFk(Xssk xssk,List xsskDescs){
		String sql = "";
		
		if(xsskDescs != null && xsskDescs.size()>0){
			for(int i =0;i<xsskDescs.size();i++){
				
				XsskDesc xsskDesc = (XsskDesc)xsskDescs.get(i);
				if(xsskDesc != null){
					if(xsskDesc.getBcsk() > 0){
						if(xsskDesc.getXsd_id().equals("期初应收")){
							//处理期初应收
							sql = "update client_wl_init set yishouje=yishouje+" + xsskDesc.getBcsk() + "where client_name='" + xssk.getClient_name() + "'";
							this.getJdbcTemplate().update(sql);
						}else{
							sql = "update xsd set skje=skje+" + xsskDesc.getBcsk() + ",skrq='" + xssk.getSk_date() + "' where id='" + xsskDesc.getXsd_id() + "'";
							this.getJdbcTemplate().update(sql);
							
							sql = "select count(1) as nums from xsd where sjcjje>skje and id='" + xsskDesc.getXsd_id() + "'";
							int count = this.getJdbcTemplate().queryForInt(sql);
							
							if(count > 0){
								sql = "update xsd set skxs='部分已收' where id='" + xsskDesc.getXsd_id() + "'";
							}else{
								sql = "update xsd set skxs='已收' where id='" + xsskDesc.getXsd_id() + "'";
							}
							this.getJdbcTemplate().update(sql);
						}
					}
				}
			}
		}
	}
	
	
	
	/**
	 * 保存销售收款明细
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
						
						this.getJdbcTemplate().update(sql,param); //添加付款明细
						
						
					}
				}
			}
		}
	}
	
	
	/**
	 * 删除销售收款明细
	 * @param xssk_id
	 */
	private void delXsskDesc(String xssk_id){
		String sql = "delete from xssk_desc where xssk_id='" + xssk_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 包装对象(销售收款)
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
	 * 包装对象(销售收款明细)
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
