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
 * Ԥ�����������Ԥ���Ӧ����תԤ�����
 * @author liyt
 *
 */
public class YufukDAO extends JdbcBaseDAO {

	/**
	 * ����Ԥ������Ϣ
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
	 * ȡ�ͻ��ϼ�Ԥ������
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
	 * ����ҵ�񵥾ݱ��ɾ��Ԥ������Ϣ
	 * @param ywdj_id
	 */
	public void delYufuk(String ywdj_id){
		String sql = "delete from yufuk_list where ywdj_id='" + ywdj_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ����Ԥ����Ľ�����
	 * @param info
	 */
	public void updateYufukJsje(YufuToYingfu info){
		
		double total = info.getTotal();  //���ν����ܽ��
		
		String sql = "select * from yufuk_list where client_name='" + info.getClient_name() + "' and hjje>jsje order by id";
		
		//������Ԥ�տ��б�
		List list = this.getResultList(sql, new YufukRowMapper());
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				
				if(total <= 0){
					break;
				}
				
				Yufuk yufuk = (Yufuk)list.get(i);  //Ԥ������Ϣ
				
				double hjje = yufuk.getHjje();  //Ԥ�����ܽ��
				double jsje = yufuk.getJsje();  //Ԥ�����ѽ�����
				
				double djsje = hjje - jsje;    //Ԥ�����������
				
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
	 * ��װ����(Ԥ����)	
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
