package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Page;
import com.sw.cms.model.YufuToYingfu;
import com.sw.cms.model.YufuToYingfuDesc;
import com.sw.cms.util.DateComFunc;

public class YufuToYingfuDAO extends JdbcBaseDAO {
	
	/**
	 * 根据查询条件取预付冲应付结算列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getYufuToYingfuList(String con,int curPage, int rowsPerPage){
		String sql = "select a.* from yufu_to_yingfu a left join clients b on b.id=a.client_name where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new YufuToYingfuRowMapper());
	}
	
	
	/**
	 * 更新预收转应收结算信息
	 * @param info
	 * @param descList
	 */
	public void updateYufuToYingfu(YufuToYingfu info,List descList){
		
		String sql = "select * from yufu_to_yingfu where id='" + info.getId() + "'";
		List list = this.getResultList(sql);
		
		if(list != null && list.size() > 0){
			//如果存在则更新
			sql = "update yufu_to_yingfu set client_name=?,create_date=?,jsr=?,total=?,state=?,czr=?,cz_date=now(),remark=? where id=?";
		}else{
			//如查不存在则插入
			sql = "insert into yufu_to_yingfu(client_name,create_date,jsr,total,state,czr,cz_date,remark,id) values(?,?,?,?,?,?,now(),?,?)";
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
	 * 根据编号取预付冲应付基本信息
	 * @param id
	 * @return
	 */
	public YufuToYingfu getYufuToYingfu(String id){
		YufuToYingfu info = new YufuToYingfu();
		
		String sql = "select * from yufu_to_yingfu where id='" + id + "'";
		Object obj = this.queryForObject(sql, new YufuToYingfuRowMapper());
		
		if(obj != null){
			info = (YufuToYingfu)obj;
		}
		
		return info;
	}
	
	
	/**
	 * 根据单据编号取预付冲应付明细信息
	 * @param id
	 * @return
	 */
	public List getYufuToYingfuDesc(String id){
		String sql = "select * from yufu_to_yingfu_desc where yw_id='" + id + "'";
		return this.getResultList(sql, new YufuToYingfuDescRowMapper());
	}
	
	
	/**
	 * 删除预付冲应付
	 * @param id
	 */
	public void delYufuToYingfu(String id){
		String sql = "delete from yufu_to_yingfu where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from yufu_to_yingfu_desc where yw_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 更新进货单已付金额及付款状态
	 * @param cgfkDescs
	 */
	public void updateJhdFkje(YufuToYingfu info,List descList){
		String sql = "";
		
		if(descList != null && descList.size()>0){
			for(int i =0;i<descList.size();i++){
				
				YufuToYingfuDesc desc = (YufuToYingfuDesc)descList.get(i);
				if(desc != null){
					if(desc.getBcjs() != 0){
						if(desc.getJhd_id().equals("期初应付")){
							//处理期初应收
							sql = "update client_wl_init set yifuje=yifuje+" + desc.getBcjs() + "where client_name='" + info.getClient_name() + "'";
							this.getJdbcTemplate().update(sql);
						}else if(desc.getJhd_id().indexOf("PZ") != -1){
							//处理往来调账
							sql = "update pz set jsje=jsje+" + desc.getBcjs() + " where id='" + desc.getJhd_id() + "'";
							this.getJdbcTemplate().update(sql);
						}else{
							sql = "update jhd set fkje=fkje+" + desc.getBcjs() + " where id='" + desc.getJhd_id() + "'";
							this.getJdbcTemplate().update(sql);
							
							sql = "select count(1) as nums from jhd where total>fkje and id='" + desc.getJhd_id() + "'";
							int count = this.getJdbcTemplate().queryForInt(sql);
							
							if(count > 0){
								sql = "update jhd set fklx='部分已付' where id='" + desc.getJhd_id() + "'";
							}else{
								sql = "update jhd set fklx='已付' where id='" + desc.getJhd_id() + "'";
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
		String sql = "select yufutoyingfuid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		String day = DateComFunc.getCurDay();
		// 将序列号加1
		sql = "update cms_all_seq set yufutoyingfuid=yufutoyingfuid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "YFJS" + day + "-" + curId;
	}

	
	/**
	 * 删除预付冲应付明细信息
	 * @param yw_id
	 */
	private void delDesc(String yw_id){
		String sql = "delete from yufu_to_yingfu_desc where yw_id='" + yw_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 保存预收转应收明细信息
	 * @param info
	 * @param descList
	 */
	private void saveDesc(YufuToYingfu info,List descList){
		String sql = "";
		Object[] param = new Object[5];
		
		if(descList != null && descList.size()>0){
			for(int i =0;i<descList.size();i++){
				
				YufuToYingfuDesc desc = (YufuToYingfuDesc)descList.get(i);
				if(desc != null){
					if(desc.getBcjs() != 0){
						sql = "insert into yufu_to_yingfu_desc(yw_id,jhd_id,yingfuje,bcjs,remark) values(?,?,?,?,?)";
						
						param[0] = info.getId();
						param[1] = desc.getJhd_id();
						param[2] = desc.getYingfuje();
						param[3] = desc.getBcjs();
						param[4] = desc.getRemark();
						
						this.getJdbcTemplate().update(sql,param); 
					}
				}
			}
		}
	}
	
	
	/**
	 * 包装对象(预付冲应付)	
	 * @author liyt
	 * 
	 */
	class YufuToYingfuRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			YufuToYingfu info = new YufuToYingfu();

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
	 * 包装对象（预付冲应付明细）
	 * @author liyt
	 *
	 */
	class YufuToYingfuDescRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			YufuToYingfuDesc info = new YufuToYingfuDesc();

			if(SqlUtil.columnIsExist(rs,"id")) info.setId(rs.getInt("id"));
			if(SqlUtil.columnIsExist(rs,"yw_id")) info.setYw_id(rs.getString("yw_id"));
			if(SqlUtil.columnIsExist(rs,"jhd_id")) info.setJhd_id(rs.getString("jhd_id"));
			if(SqlUtil.columnIsExist(rs,"yingfuje")) info.setYingfuje(rs.getDouble("yingfuje"));
			if(SqlUtil.columnIsExist(rs,"bcjs")) info.setBcjs(rs.getDouble("bcjs"));
			if(SqlUtil.columnIsExist(rs,"remark")) info.setRemark(rs.getString("remark"));
			
			return info;
		}
	}
}
