package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
 
import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Page;
import com.sw.cms.model.YushouToYingshou;
import com.sw.cms.model.YushouToYingshouDesc;
import com.sw.cms.util.DateComFunc;
 
/**
 * 预收转应收数据处理
 * @author liyt
 *
 */
public class YushouToYingshouDAO extends JdbcBaseDAO {
	
	/**
	 * 根据查询条件取预收转应收结算列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getYushouToYingshouList(String con,int curPage, int rowsPerPage){
		String sql = "select a.* from yushou_to_yingshou a left join clients b on b.id=a.client_name where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new YushouToYingShouRowMapper());
	}
	
	
	/**
	 * 更新预收转应收结算信息
	 * @param info
	 * @param descList
	 */
	public void updateYushouToYingshou(YushouToYingshou info,List descList){
		
		String sql = "select * from yushou_to_yingshou where id='" + info.getId() + "'";
		List list = this.getResultList(sql);
		
		if(list != null && list.size() > 0){
			//如果存在则更新
			sql = "update yushou_to_yingshou set client_name=?,create_date=?,jsr=?,total=?,state=?,czr=?,cz_date=now(),remark=? where id=?";
		}else{
			//如查不存在则插入
			sql = "insert into yushou_to_yingshou(client_name,create_date,jsr,total,state,czr,cz_date,remark,id) values(?,?,?,?,?,?,now(),?,?)";
		}
		
		Object[] param = new Object[8];
		
		param[0] = info.getClient_name();
		param[1] = info.getCreate_date();
		param[2] = info.getJsr();
		param[3] = info.getTotal();
		param[4] = info.getState();
		param[5] = info.getCzr();
		param[6] = info.getRemark();
		param[7] = info.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.delDesc(info.getId());
		
		this.saveDesc(info, descList);
	}
	
	
	/**
	 * 根据编号取应预收转应收基本信息
	 * @param id
	 * @return
	 */
	public YushouToYingshou getYushouToYingshou(String id){
		YushouToYingshou info = new YushouToYingshou();
		
		String sql = "select * from yushou_to_yingshou where id='" + id + "'";
		Object obj = this.queryForObject(sql, new YushouToYingShouRowMapper());
		
		if(obj != null){
			info = (YushouToYingshou)obj;
		}
		
		return info;
	}
	
	
	/**
	 * 根据单据编号取预收转应收明细信息明细信息
	 * @param id
	 * @return
	 */
	public List getYushouToYingshouDesc(String id){
		String sql = "select * from yushou_to_yingshou_desc where yw_id='" + id + "'";
		return this.getResultList(sql, new YushouToYingshouDescRowMapper());
	}
	
	
	/**
	 * 删除预收转应收
	 * @param id
	 */
	public void delYushouToYingshou(String id){
		String sql = "delete from yushou_to_yingshou where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from yushou_to_yingshou_desc where yw_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	
	/**
	 * 更新销售单已收金额及付款状态
	 * @param cgfkDescs
	 */
	public void updateXsdFk(YushouToYingshou info,List descList){
		String sql = "";
		
		if(descList != null && descList.size()>0){
			for(int i =0;i<descList.size();i++){
				
				YushouToYingshouDesc desc = (YushouToYingshouDesc)descList.get(i);
				if(desc != null){
					if(desc.getBcjs() != 0){
						if(desc.getXsd_id().equals("期初应收")){
							//处理期初应收
							sql = "update client_wl_init set yishouje=yishouje+" + desc.getBcjs() + "where client_name='" + info.getClient_name() + "'";
							this.getJdbcTemplate().update(sql);
						}else if(desc.getXsd_id().indexOf("PZ") != -1){
							//处理往来调账
							sql = "update pz set jsje=jsje+" + desc.getBcjs() + " where id='" + desc.getXsd_id() + "'";
							this.getJdbcTemplate().update(sql);
						}else{
							sql = "update xsd set skje=skje+" + desc.getBcjs() + ",skrq='" + info.getCreate_date() + "' where id='" + desc.getXsd_id() + "'";
							this.getJdbcTemplate().update(sql);
							
							sql = "select count(1) as nums from xsd where sjcjje>skje and id='" + desc.getXsd_id() + "'";
							int count = this.getJdbcTemplate().queryForInt(sql);
							
							if(count > 0){
								sql = "update xsd set skxs='部分已收' where id='" + desc.getXsd_id() + "'";
							}else{
								sql = "update xsd set skxs='已收' where id='" + desc.getXsd_id() + "'";
							}
							this.getJdbcTemplate().update(sql);
						}
					}
				}
			}
		}
	}
	
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getID() {
		String sql = "select yushoutoyingshouid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		String day = DateComFunc.getCurDay();
		// 将序列号加1
		sql = "update cms_all_seq set yushoutoyingshouid=yushoutoyingshouid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "YSJS" + day + "-" + curId;
	}
	
	
	/**
	 * 销售收款明细是否存在冲突(销售收款，预收冲应收)
	 * @param yw_id
	 * @param xsd_id
	 * @param client_name
	 * @param bcjs
	 * @return
	 */
	public boolean isXsskDescExist(String yw_id,String xsd_id,String client_name,double bcjs){
		boolean is = false;
		double skje=0;
		
		String sqlSkje="";
		String sql = "select count(1) as counts from xssk_desc a join xssk b on b.id=a.xssk_id  where b.state<>'已提交'  and a.xsd_id='" + xsd_id + "' and b.client_name='" + client_name + "'";
		int count1 = this.getJdbcTemplate().queryForInt(sql);
		
		sql = "select count(1) as count from yushou_to_yingshou_desc a join yushou_to_yingshou b on b.id=a.yw_id  where b.state<>'已提交'  and a.yw_id<>'" + yw_id + "' and a.xsd_id='" + xsd_id + "' and b.client_name='" + client_name + "'";
		int count2 = this.getJdbcTemplate().queryForInt(sql);
		
		if(xsd_id.equals("期初应收")){
			sqlSkje="select (ysqc-yishouje) as ysk from client_wl_init  where  client_name='" + client_name + "'";
		   
		}else if(xsd_id.indexOf("PZ") != -1){
			sqlSkje="select (pzje-jsje) as ysk from pz where id='" + xsd_id + "' and client_name='" + client_name + "'";
			
		}else{
			sqlSkje="select (sjcjje-skje) as ysk from xsd where  id='" + xsd_id + "' and  client_name='" + client_name + "'";	
		  
		}
		Map map = this.getResultMap(sqlSkje);
		if(map!= null){
			skje = map.get("ysk")==null?0:((Double)map.get("ysk")).doubleValue();
			
		}
		
		skje = (double)Math.round(skje*100)/100;


		
		if((count1+count2) > 0){
			is = true;
		}
		if(bcjs>skje){
			is = true;
		}
			
		return is;
	}
	
	
	/**
	 * 删除预收转应收明细信息
	 * @param yw_id
	 */
	private void delDesc(String yw_id){
		String sql = "delete from yushou_to_yingshou_desc where yw_id='" + yw_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 保存预收转应收明细信息
	 * @param info
	 * @param descList
	 */
	private void saveDesc(YushouToYingshou info,List descList){
		String sql = "";
		Object[] param = new Object[5];
		
		if(descList != null && descList.size()>0){
			for(int i =0;i<descList.size();i++){
				
				YushouToYingshouDesc desc = (YushouToYingshouDesc)descList.get(i);
				if(desc != null){
					if(desc.getBcjs() != 0){
						sql = "insert into yushou_to_yingshou_desc(yw_id,xsd_id,yingshouje,bcjs,remark) values(?,?,?,?,?)";
						
						param[0] = info.getId();
						param[1] = desc.getXsd_id();
						param[2] = desc.getYingshouje();
						param[3] = desc.getBcjs();
						param[4] = desc.getRemark();
						
						this.getJdbcTemplate().update(sql,param); 
					}
				}
			}
		}
	}
	
	
	/**
	 * 包装对象(预收转应收)	
	 * @author liyt
	 * 
	 */
	class YushouToYingShouRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			YushouToYingshou info = new YushouToYingshou();

			if(SqlUtil.columnIsExist(rs,"id")) info.setId(rs.getString("id"));
			if(SqlUtil.columnIsExist(rs,"client_name")) info.setClient_name(rs.getString("client_name"));
			if(SqlUtil.columnIsExist(rs,"create_date")) info.setCreate_date(rs.getString("create_date"));
			if(SqlUtil.columnIsExist(rs,"czr")) info.setCzr(rs.getString("czr"));
			if(SqlUtil.columnIsExist(rs,"jsr")) info.setJsr(rs.getString("jsr"));
			if(SqlUtil.columnIsExist(rs,"remark")) info.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"total")) info.setTotal(rs.getDouble("total"));
			if(SqlUtil.columnIsExist(rs,"state")) info.setState(rs.getString("state"));
			
			return info;
		}
	}
	
	
	/**
	 * 包装对象（预收转应收明细信息）
	 * @author liyt
	 *
	 */
	class YushouToYingshouDescRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			YushouToYingshouDesc info = new YushouToYingshouDesc();

			if(SqlUtil.columnIsExist(rs,"id")) info.setId(rs.getInt("id"));
			if(SqlUtil.columnIsExist(rs,"yw_id")) info.setYw_id(rs.getString("yw_id"));
			if(SqlUtil.columnIsExist(rs,"xsd_id")) info.setXsd_id(rs.getString("xsd_id"));
			if(SqlUtil.columnIsExist(rs,"yingshouje")) info.setYingshouje(rs.getDouble("yingshouje"));
			if(SqlUtil.columnIsExist(rs,"bcjs")) info.setBcjs(rs.getDouble("bcjs"));
			if(SqlUtil.columnIsExist(rs,"remark")) info.setRemark(rs.getString("remark"));
			
			return info;
		}
	}

}
