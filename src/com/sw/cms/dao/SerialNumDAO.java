package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.BeanRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Page;
import com.sw.cms.model.SerialNumFlow;
import com.sw.cms.model.SerialNumMng;
import com.sw.cms.model.SerialNumPd;
import com.sw.cms.model.Ykrk;
import com.sw.cms.model.YkrkProduct;
import com.sw.cms.util.StaticParamDo;

/**
 * 序列号维护、处理及查询页面
 * @author liyt
 *
 */

public class SerialNumDAO extends JdbcBaseDAO {
	
	/**
	 * 根据查询条件取序列号列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getSerialNumPage(String con, int curPage, int rowsPerPage){
		String sql = "select seq_id,serial_num,product_id,product_name,product_xh,state,store_id,yj_flag from serial_num_mng where 1=1";
		if(!con.equals("")){
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage, new SerialNumMngMapper());
	}
	
	
	/**
	 * <p>判断序列号是否存在</p>
	 * @param serial_num 序列号
	 * @return 存在返回true;不存在返回false
	 */
	public boolean chkSerialNum(String serial_num){
		boolean is = false;
		String sql = "select * from serial_num_mng where serial_num='" + serial_num + "'";
		List list = this.getResultList(sql);
		if(list != null && list.size()>0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * <p>保存、更新序列号</p>
	 * <p>如果序列号存在则更新</p>
	 * <p>如果序列号不存在则插入</p>
	 * @param serialNumMng
	 */
	public void updateSerialNumState(SerialNumMng serialNumMng){
		String sql = "select * from serial_num_mng where serial_num='" + serialNumMng.getSerial_num() + "'";
		List list = this.getJdbcTemplate().queryForList(sql);
		
		if(list != null && list.size() > 0){
			//序列号存在,更新
			sql = "update serial_num_mng set product_name=?,product_xh=?,state=?,product_id=?,store_id=?,yj_flag=? where serial_num=?";
		}else{
			//序列号不存在,插入
			sql = "insert into serial_num_mng(product_name,product_xh,state,product_id,store_id,yj_flag,serial_num) values(?,?,?,?,?,?,?)";
		}
		
		Object[] params = new Object[7];
		params[0] = serialNumMng.getProduct_name();
		params[1] = serialNumMng.getProduct_xh();
		params[2] = serialNumMng.getState();		
		params[3] = serialNumMng.getProduct_id();
		params[4] = serialNumMng.getStore_id();
		params[5] = serialNumMng.getYj_flag();
		params[6] = serialNumMng.getSerial_num();

		
		this.getJdbcTemplate().update(sql,params);
	}
	
	public void updateSerialNumStateDOA(Ykrk ykrk,YkrkProduct ykrkProduct)
	{   String sql = "select * from serial_num_mng where serial_num='" + ykrkProduct.getQz_serial_num() + "'";
	    List list = this.getJdbcTemplate().queryForList(sql);
	
	    if(list != null && list.size() > 0){
		//序列号存在,更新
		    sql="update serial_num_mng set product_name=?,product_xh=?,state=?,product_id=?,store_id=? where serial_num=?";
	    }else{
			//序列号不存在,插入
			sql = "insert into serial_num_mng(product_name,product_xh,state,product_id,store_id,serial_num) values(?,?,?,?,?,?)";
		}  
		Object[] params = new Object[6];
		params[0] = ykrkProduct.getProduct_name();
		params[1] = ykrkProduct.getProduct_xh();		
		params[2] = "在库";
		params[3] = ykrkProduct.getProduct_id();
		params[4] = ykrk.getRk_store_id();
		params[5] = ykrkProduct.getQz_serial_num();
		
		this.getJdbcTemplate().update(sql,params);
	}
	
	
	/**
	 * 更新序列号所在库房
	 * @param serial_num
	 * @param store_id
	 */
	public void updateSerialNumStore(String serial_num,String store_id){
		String sql = "update serial_num_mng set store_id='" + store_id + "' where serial_num='" + serial_num + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 编辑序列号
	 * @param serial_num
	 * @return
	 */
	public SerialNumMng editSerialNumMng(String serial_num){
		String sql = "select * from serial_num_mng where serial_num='" + serial_num + "'";
		return (SerialNumMng)this.queryForObject(sql, new SerialNumMngMapper());
	}
	
	/**
	 * 删除序列号
	 * @param serial_num
	 */
	public void delSerialNum(String serial_num){
		String sql = "delete from serial_num_mng where serial_num='" + serial_num + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 保存序列号流转过程
	 * @param serialNumFlow
	 */
	public void saveSerialFlow(SerialNumFlow serialNumFlow){
		
		String sql = "insert into serial_num_flow(serial_num,ywtype,client_name,jsr,yw_dj_id,kf_dj_id,fs_date,yw_url,kf_url,czr,tel,cz_date,xsdj) values(?,?,?,?,?,?,?,?,?,?,?,now(),?)";
		
		Object[] params = new Object[12];
		params[0] = serialNumFlow.getSerial_num();
		params[1] = serialNumFlow.getYwtype();
		params[2] = serialNumFlow.getClient_name();
		params[3] = serialNumFlow.getJsr();
		params[4] = serialNumFlow.getYw_dj_id();
		params[5] = serialNumFlow.getKf_dj_id();
		params[6] = serialNumFlow.getFs_date();
		params[7] = serialNumFlow.getYw_url();
		params[8] = serialNumFlow.getKf_url();		
		params[9] = serialNumFlow.getCzr();
		params[10] = serialNumFlow.getTel();		
		params[11] = serialNumFlow.getXsdj(); //销售单价
		
		this.getJdbcTemplate().update(sql,params);		
	}
	/**
	 * 根据序列号查询购买记录
	 * @param num
	 * @return
	 */
	public Object getXsRecord(String num)
	{
		String sql="select seq_id,serial_num,ywtype,yw_dj_id from serial_num_flow where seq_id=(select max(seq_id) from serial_num_flow where serial_num='"+num+"' group by serial_num)";
		return this.getResultMap(sql);
	}
	
	
	
	
	/**
	 * 查询序列号流程
	 * @param serial_num
	 * @return
	 */
	public List getSerialFlow(String serial_num){
		String sql = "select * from serial_num_flow where serial_num='" + serial_num + "' order by cz_date asc";
		return this.getResultList(sql);
	}
	
	
	/**
	 * 查询序列号当前状态
	 * 对应商品相关信息
	 * @param serial_num
	 * @return
	 */
	public Map getSerialState(String serial_num){
		String sql = "select * from serial_num_mng where serial_num='" + serial_num + "'";
		return this.getResultMap(sql);
	}
	
	
	/**
	 * 根据序列号取商品明细信息
	 * @param serial_num
	 * @return
	 */
	public Map getProductBySerialNum(String serial_num){
		String sql = "select b.* from serial_num_mng a left join product b on b.product_id=a.product_id where a.state='在库' and a.serial_num='" + serial_num + "'";
		return this.getResultMap(sql);
	}
	
	/**
	 * 判断当前的序列号是否存在
	 * 
	 * @return
	 */
	public boolean getSerialNumState(String product_id,String store_id,String serialNum){		
		boolean is = false;
		
		String sql = "select count(*) as counts from serial_num_mng where product_id='"+product_id+"' and store_id='"+store_id+"' and serial_num='"+serialNum+"' and state='在库'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			is = true;
		}
		
		return is;
	}
	
	/**
	 * 判断当前的序列号是否已售
	 * 
	 * @return
	 */
	public boolean getSerialNumStateXs(String product_id,String store_id,String serialNum){
		boolean is = false;
		
		String sql = "select count(*) as counts from serial_num_mng where product_id='"+product_id+"'  and serial_num='"+serialNum+"' and state='已售'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){
			is = true;
		}
		
		return is;
	}
	
	
	/**
	 * 根据库房ID取序列号列表
	 * @param store_id
	 * @return
	 */
	public List getSerialNumMngListByStoreId(String store_id){
		String sql = "select * from serial_num_mng where state='在库' and store_id=?";
		return this.getResultList(sql, new Object[]{store_id},new SerialNumMngMapper());
	}
	
	
	/**
	 * 盘点是否以保存
	 * @param cdate
	 * @param store_id
	 * @return
	 */
	public boolean isSerialNumPdExist(String cdate,String store_id){
		boolean is = false;
		String sql = "select count(*) as nums from serial_num_pd where cdate='" + cdate + "' and store_id='" + store_id + "'";
		int vl = this.getJdbcTemplate().queryForInt(sql);
		if(vl > 0){
			is = true;
		}
		return is;
	}
	
	/**
	 * 序列号盘点记录
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getSerialNumPdPage(String con,int curPage, int rowsPerPage){
		String sql = "select a.* from serial_num_pd a join sys_user b on b.user_id=a.jsr where 1=1";
		if(!con.equals("")){
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage, new BeanRowMapper(SerialNumPd.class));
	}
	
	
	/**
	 * 保存序列号盘点结果
	 * @param info
	 */
	public void insertSerialNumPd(SerialNumPd info){
		String sql = "insert into serial_num_pd(cdate,jsr,store_id,pd_result,cz_date) values(?,?,?,?,now())";
		Object[] params = new Object[4];
		params[0] = info.getCdate();
		params[1] = info.getJsr();
		params[2] = info.getStore_id();
		params[3] = info.getPd_result();
		this.update(sql,params);
	}
	
	
	/**
	 * 去序列号盘点记录
	 * @return
	 */
	public List getSerialNumPdList(){
		String sql = "select * from serial_num_pd";
		return this.getResultList(sql, new BeanRowMapper(SerialNumPd.class));
	}
	
	
	/**
	 * 包装对象(序列号)
	 * @author liyt
	 */
	class SerialNumMngMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SerialNumMng serialNumMng = new SerialNumMng();

			if(SqlUtil.columnIsExist(rs,"seq_id")) serialNumMng.setSeq_id(rs.getInt("seq_id"));
			if(SqlUtil.columnIsExist(rs,"serial_num")) serialNumMng.setSerial_num(rs.getString("serial_num"));
			if(SqlUtil.columnIsExist(rs,"product_id")) serialNumMng.setProduct_id(rs.getString("product_id"));
			if(SqlUtil.columnIsExist(rs,"product_name")) serialNumMng.setProduct_name(rs.getString("product_name"));
			if(SqlUtil.columnIsExist(rs,"product_xh")) serialNumMng.setProduct_xh(rs.getString("product_xh"));
			if(SqlUtil.columnIsExist(rs,"state")) serialNumMng.setState(rs.getString("state"));
			if(SqlUtil.columnIsExist(rs,"store_id")) {
				serialNumMng.setStore_id(rs.getString("store_id"));
				serialNumMng.setStore_name(StaticParamDo.getStoreNameById(rs.getString("store_id")));
			}
			if(SqlUtil.columnIsExist(rs,"yj_flag")) serialNumMng.setYj_flag(rs.getString("yj_flag"));
			
			return serialNumMng;
		}
	}

}
