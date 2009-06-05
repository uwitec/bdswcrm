package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.YushouToYingshou;
import com.sw.cms.model.Yushouk;

/**
 * 预收款处理，包括添加、结算等
 * @author liyt
 *
 */
public class YushoukDAO extends JdbcBaseDAO {
	
	/**
	 * 保存预收款信息
	 * @param ysk
	 */
	public void saveYushouk(Yushouk ysk){
		String sql = "insert into yushouk_list(client_name,hjje,jsje,js_date,url,ywdj_id,ywdj_name,remark,czr,cz_date) " +
				"values(?,?,?,?,?,?,?,?,?,now())";
		
		Object[] param = new Object[9];
		
		param[0] = ysk.getClient_name();
		param[1] = ysk.getHjje();
		param[2] = ysk.getJsje();
		param[3] = ysk.getJs_date();
		param[4] = ysk.getUrl();
		param[5] = ysk.getYwdj_id();
		param[6] = ysk.getYwdj_name();
		param[7] = ysk.getRemark();
		param[8] = ysk.getCzr();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 取客户合计预收款金额
	 * @param client_id
	 * @return
	 */
	public double getYushoukByClientId(String client_id){
		double ysk = 0;
		
		String sql = "select (sum(hjje)-sum(jsje)) as ysk from yushouk_list where client_name='" + client_id + "'";
		Map map = this.getResultMap(sql);
		
		if(map != null){
			ysk = map.get("ysk")==null?0:((Double)map.get("ysk")).doubleValue();
		}
		
		return ysk;
	}	
	
	
	/**
	 * 根据业务单据ID删除预收款信息
	 * @param ywdj_id
	 */
	public void delYushouk(String ywdj_id){
		String sql = "delete from yushouk_list where ywdj_id='" + ywdj_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	
	/**
	 * 更新预收款的结算金额
	 * @param info
	 */
	public void updateYushoukJsje(YushouToYingshou info){
		
		double total = info.getTotal();  //本次结算总金额
		
		String sql = "select * from yushouk_list where client_name='" + info.getClient_name() + "' and hjje>jsje order by id";
		
		//待结算预收款列表
		List list = this.getResultList(sql, new YushoukRowMapper());
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				
				if(total <= 0){
					break;
				}
				
				Yushouk yushouk = (Yushouk)list.get(i);  //预收款信息
				
				double hjje = yushouk.getHjje();  //预收款总金额
				double jsje = yushouk.getJsje();  //预收款已结算金额
				
				double djsje = hjje - jsje;
				
				if(total - djsje > 0){
					sql = "update yushouk_list set jsje=hjje where id=" + yushouk.getId();
					this.getJdbcTemplate().update(sql);
				}else{
					sql = "update yushouk_list set jsje=jsje+" + total + " where id=" + yushouk.getId();
					this.getJdbcTemplate().update(sql);
				}
				total = total - djsje;
			}
		}
		
	}

	
	/**
	 * 包装对象(预收款)	
	 * @author liyt
	 * 
	 */
	class YushoukRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Yushouk info = new Yushouk();

			if(SqlUtil.columnIsExist(rs,"client_name")) info.setClient_name(rs.getString("client_name"));
			if(SqlUtil.columnIsExist(rs,"czr")) info.setCzr(rs.getString("czr"));
			if(SqlUtil.columnIsExist(rs,"hjje")) info.setHjje(rs.getDouble("hjje"));
			if(SqlUtil.columnIsExist(rs,"id")) info.setId(rs.getInt("id"));
			if(SqlUtil.columnIsExist(rs,"js_date")) info.setJs_date(rs.getString("js_date"));
			if(SqlUtil.columnIsExist(rs,"jsje")) info.setJsje(rs.getDouble("jsje"));
			if(SqlUtil.columnIsExist(rs,"remark")) info.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"url")) info.setUrl(rs.getString("url"));
			if(SqlUtil.columnIsExist(rs,"ywdj_id")) info.setYwdj_id(rs.getString("ywdj_id"));
			if(SqlUtil.columnIsExist(rs,"ywdj_name")) info.setYwdj_name(rs.getString("ywdj_name"));
			
			return info;
		}
	}
}
