package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.YufuToYingfu;
import com.sw.cms.model.Yufuk;

/**
 * 预付款管理，包括预付款及应付款转预付款部分
 * @author liyt
 *
 */
public class YufukDAO extends JdbcBaseDAO {

	/**
	 * 保存预付款信息
	 * @param ysk
	 */
	public void saveYufuk(Yufuk yfk){
		String sql = "insert into yufuk_list(client_name,hjje,jsje,js_date,url,ywdj_id,ywdj_name,remark,czr,cz_date) " +
				"values(?,?,?,?,?,?,?,?,?,now())";
		
		Object[] param = new Object[9];
		
		param[0] = yfk.getClient_name();
		param[1] = yfk.getHjje();
		param[2] = yfk.getJsje();
		param[3] = yfk.getJs_date();
		param[4] = yfk.getUrl();
		param[5] = yfk.getYwdj_id();
		param[6] = yfk.getYwdj_name();
		param[7] = yfk.getRemark();
		param[8] = yfk.getCzr();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 取客户合计预付款金额
	 * @param client_id
	 * @return
	 */
	public double getYufukByClientId(String client_id){
		double yfk = 0;
		
		String sql = "select sum(hjje-jsje) as yfk from yufuk_list where client_name='" + client_id + "' and hjje>jsje";
		Map map = this.getResultMap(sql);
		
		if(map != null){
			yfk = map.get("yfk")==null?0:((Double)map.get("yfk")).doubleValue();
		}
		
		return yfk;
	}
	
	
	/**
	 * 根据业务单据编号删除预付款信息
	 * @param ywdj_id
	 */
	public void delYufuk(String ywdj_id){
		String sql = "delete from yufuk_list where ywdj_id='" + ywdj_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 更新预付款的结算金额
	 * @param info
	 */
	public void updateYufukJsje(YufuToYingfu info){
		
		double total = info.getTotal();  //本次结算总金额
		
		String sql = "select * from yufuk_list where client_name='" + info.getClient_name() + "' and hjje>jsje order by id";
		
		//待结算预收款列表
		List list = this.getResultList(sql, new YufukRowMapper());
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				
				if(total <= 0){
					break;
				}
				
				Yufuk yufuk = (Yufuk)list.get(i);  //预付款信息
				
				double hjje = yufuk.getHjje();  //预付款总金额
				double jsje = yufuk.getJsje();  //预付款已结算金额
				
				double djsje = hjje - jsje;    //预付款待结算金额
				
				if(total - djsje > 0){
					sql = "update yufuk_list set jsje=hjje where id=" + yufuk.getId();
					this.getJdbcTemplate().update(sql);
				}else{
					sql = "update yufuk_list set jsje=jsje+" + total + " where id=" + yufuk.getId();
					this.getJdbcTemplate().update(sql);
				}
				total = total - djsje;
			}
		}
		
	}
	
	
	/**
	 * 包装对象(预付款)	
	 * @author liyt
	 * 
	 */
	class YufukRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Yufuk info = new Yufuk();

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
