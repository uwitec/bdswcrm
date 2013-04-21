package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Lsd;
import com.sw.cms.model.LsdProduct;
import com.sw.cms.model.Page;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.StringUtils;

/**
 * 零售单处理
 * @author liyt
 *
 */

public class LsdDAO extends JdbcBaseDAO {
	
	/**
	 * 获取零售单列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getLsdList(String con,int curPage, int rowsPerPage){
		String sql = "select a.id,a.client_name,a.lsdje,a.creatdate,a.state,b.real_name as xsry,a.czr,a.lxr,a.lxdh,a.sp_state from lsd a left join sys_user b on b.user_id=a.xsry where 1=1";
		if(!con.equals("")) {
			sql = sql + con;
		}	
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * 取待审批零售单列表
	 * @return
	 */
	public List getDspLsdList(){
		String sql = "select a.*,b.real_name,c.dept_name from lsd a left join sys_user b on b.user_id=a.xsry left join dept c on c.dept_id=b.dept where a.sp_state='2'";
		return this.getResultList(sql);
	}
	
	/**
	 * 保存零售单信息
	 * @param lsd
	 * @param lsdProducts
	 * @return
	 */
	public void saveLsd(Lsd lsd,List lsdProducts){
		
		String sql = "insert into lsd(creatdate,xsry,fkfs,state,store_id,client_name,lxr,lxdh,mobile,mail,msn,address,p_code,fplx,khhzh,sh,fpxx,yhje,lsdje,skzh,skje,lsdcbj,ms,czr,cz_date,has_yushk,yushk_id,yushkje,id,kp_mc,kp_address,kp_dh,lsdkhcb,sp_state,pos_id,hyk_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] param = new Object[35];
		
		param[0] = lsd.getCreatdate();
		param[1] = lsd.getXsry();
		param[2] = lsd.getFkfs();
		param[3] = lsd.getState();
		param[4] = lsd.getStore_id();
		param[5] = lsd.getClient_name();
		param[6] = lsd.getLxr();
		param[7] = lsd.getLxdh();
		param[8] = lsd.getMobile();
		param[9] = lsd.getMail();
		param[10] = lsd.getMsn();
		param[11] = lsd.getAddress();
		param[12] = lsd.getP_code();
		param[13] = lsd.getFplx();
		param[14] = lsd.getKhhzh();
		param[15] = lsd.getSh();
		param[16] = lsd.getFpxx();
		param[17] = new Double(lsd.getYhje());
		param[18] = new Double(lsd.getLsdje());
		param[19] = lsd.getSkzh();
		param[20] = new Double(lsd.getSkje());
		param[21] = new Double(lsd.getLsdcbj());
		param[22] = lsd.getMs();
		param[23] = lsd.getCzr();
		param[24] = lsd.getHas_yushk();
		param[25] = lsd.getYushk_id();
		param[26] = new Double(lsd.getYushkje());
		param[27] = lsd.getId();
		param[28] = lsd.getKp_mc();
		param[29] = lsd.getKp_address();
		param[30] = lsd.getKp_dh();
		param[31] = lsd.getLsdkhcb();
		param[32] = lsd.getSp_state();
		param[33] = lsd.getPos_id();
		param[34] = lsd.getHyk_id();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.addLsdProducts(lsdProducts,lsd);
		
	}
	
	
	/**
	 * 更新零售单信息
	 * @param lsd
	 * @param lsdProducts
	 */
	public void updateLsd(Lsd lsd,List lsdProducts){
		String sql = "update lsd set creatdate=?,xsry=?,fkfs=?,state=?,store_id=?,client_name=?,lxr=?,lxdh=?,mobile=?,mail=?,msn=?,address=?,p_code=?,fplx=?,khhzh=?,sh=?,fpxx=?,yhje=?,lsdje=?,skzh=?,skje=?,lsdcbj=?,ms=?,czr=?,cz_date=now(),has_yushk=?,yushk_id=?,yushkje=?,kp_mc=?,kp_address=?,kp_dh=?,lsdkhcb=?,sp_state=?,pos_id=?,hyk_id=? where id=?";
		
		Object[] param = new Object[35];
		
		param[0] = lsd.getCreatdate();
		param[1] = lsd.getXsry();
		param[2] = lsd.getFkfs();
		param[3] = lsd.getState();
		param[4] = lsd.getStore_id();
		param[5] = lsd.getClient_name();
		param[6] = lsd.getLxr();
		param[7] = lsd.getLxdh();
		param[8] = lsd.getMobile();
		param[9] = lsd.getMail();
		param[10] = lsd.getMsn();
		param[11] = lsd.getAddress();
		param[12] = lsd.getP_code();
		param[13] = lsd.getFplx();
		param[14] = lsd.getKhhzh();
		param[15] = lsd.getSh();
		param[16] = lsd.getFpxx();
		param[17] = new Double(lsd.getYhje());
		param[18] = new Double(lsd.getLsdje());
		param[19] = lsd.getSkzh();
		param[20] = new Double(lsd.getSkje());
		param[21] = new Double(lsd.getLsdcbj());
		param[22] = lsd.getMs();
		param[23] = lsd.getCzr();
		param[24] = lsd.getHas_yushk();
		param[25] = lsd.getYushk_id();
		param[26] = new Double(lsd.getYushkje());
		param[27] = lsd.getKp_mc();
		param[28] = lsd.getKp_address();
		param[29] = lsd.getKp_dh();
		param[30] = lsd.getLsdkhcb();
		param[31] = lsd.getSp_state();
		param[32] = lsd.getPos_id();
		param[33] = lsd.getHyk_id();
		param[34] = lsd.getId();
		
		this.getJdbcTemplate().update(sql,param);
		
		this.delLsdProducts(lsd.getId());
		
		this.addLsdProducts(lsdProducts, lsd);
	} 
	
	
	/**
	 * 保存零售单的审批结果
	 * @param lsd_id
	 * @param sp_state
	 * @param spr
	 */
	public void saveSp(String lsd_id,String sp_state,String spr){
		
		//保存审批信息
		String sql = "update lsd set spr='" + spr + "',sp_state='" + sp_state + "',sp_date=now() where id='" + lsd_id + "'";
		this.getJdbcTemplate().update(sql);
		
		//审批通过的工分打半折
		//sql = "update lsd_product set gf=gf/2 where lsd_id='" + lsd_id + "'";
		//this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 根据零售单ID取零售单信息
	 * @param id
	 * @return
	 */
	public Object getLsd(String id){
		String sql = "select * from lsd where id='" + id + "'";
		
		return this.queryForObject(sql, new LsdRowMapper());
	}

	
	/**
	 * 根据零售单ID和零售单销售货品的序列号查询销售货品记录以及购买人的记录
	 * @param id
	 * @param num
	 * @return
	 */
	public Object getLsdByIdBySerailNum(String id,String num){
		String sql="select l.client_name,l.lxr,l.lxdh,l.mobile,l.mail,l.address,p.product_id,p.product_xh,product_name,p.qz_serial_num from lsd l left join lsd_product p on l.id=p.lsd_id where l.id='"+id+"'and p.qz_serial_num like '%"+num+"%'";
		return this.getResultMap(sql);
	}
	
	
	/**
	 * 根据lsd_id取相关联商品
	 * @param id
	 * @return
	 */
	public List getLsdProducts(String id){
		String sql = "select a.*,b.qz_serial_num as qz_flag,b.dw from lsd_product a left join product b on b.product_id=a.product_id where lsd_id='" + id + "'";		
		return this.getJdbcTemplate().query(sql, new LsdProductRowMapper());
	}
	
	
	/**
	 * 删除零售单信息（包括关联商品）
	 * @param id
	 */
	public void delLsd(String id){
		String sql = "delete from lsd where id='" + id + "'";		
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from lsd_product where lsd_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 添加零售单相关商品
	 * 商品列表中低于限价的商品工分减半
	 * @param lsdProducts
	 * @param lsd_id
	 */
	private void addLsdProducts(List lsdProducts,Lsd lsd){
		String sql = "";
		Object[] param = new Object[20];
		
		String lsd_id = lsd.getId();
		
		double sd = 0;
		
		if(!lsd.getFplx().equals("出库单")){
			sd = getLssd();
		}
		
		//提成比例
		Map tcblMap = getTcbl();
		double basic_ratio = 0;
		double out_ratio = 0;
		double ds_ratio = 0;
		if(tcblMap != null){
			basic_ratio = tcblMap.get("basic_ratio")==null?0:((Double)tcblMap.get("basic_ratio")).doubleValue();
			out_ratio = tcblMap.get("out_ratio")==null?0:((Double)tcblMap.get("out_ratio")).doubleValue();
			ds_ratio = tcblMap.get("ds_ratio")==null?0:((Double)tcblMap.get("ds_ratio")).doubleValue();
		}
		
		if(lsdProducts != null && lsdProducts.size()>0){
			for(int i=0;i<lsdProducts.size();i++){
				LsdProduct lsdProduct = (LsdProduct)lsdProducts.get(i);
				if(lsdProduct != null){
					if(!lsdProduct.getProduct_id().equals("") && !lsdProduct.getProduct_name().equals("")){
						sql = "insert into lsd_product(lsd_id,product_id,product_xh,product_name,price,nums,xj,remark,cbj,qz_serial_num,kh_cbj,gf,sd,bhsje,basic_ratio,out_ratio,ds,lsxj,ygcbj,sfcytc) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						
						param[0] = lsd_id;
						param[1] = lsdProduct.getProduct_id();
						param[2] = lsdProduct.getProduct_xh();
						param[3] = lsdProduct.getProduct_name();
						param[4] = new Double(lsdProduct.getPrice());
						param[5] = new Integer(lsdProduct.getNums());//数量
						param[6] = new Double(lsdProduct.getXj());   //含税金额
						param[7] = lsdProduct.getRemark();           //备注
						
						double gf = 0l;  //比例点杀
						Map map = this.getProductInfo(lsdProduct.getProduct_id());
						double lsxj = 0l;   //零售限价
						double ds = 0l;     //金额点杀
						double ygcbj = 0l;  //预估成本价
						double cbj = 0l;    //成本价
						double khcbj = 0l;  //考核成本价
						String sfcytc = ""; //是否参与提成
						if(map != null){
							lsxj = map.get("lsxj")==null?0:((Double)map.get("lsxj")).doubleValue();
							gf = map.get("gf")==null?0:((Double)map.get("gf")).doubleValue();
							ds = map.get("dss")==null?0:((Double)map.get("dss")).doubleValue();
							ygcbj = map.get("ygcbj")==null?0:((Double)map.get("ygcbj")).doubleValue();
							cbj = map.get("price")==null?0:((Double)map.get("price")).doubleValue();
							khcbj = map.get("khcbj")==null?0:((Double)map.get("khcbj")).doubleValue();
							sfcytc = StringUtils.nullToStr(map.get("sfcytc"));
						}
						//不含税单价低于零售限价时 点杀需要乘以比例
						if((lsdProduct.getPrice()/ (1 + sd/100)) < lsxj){
							ds = ds * ds_ratio/100; //金额点杀
						}
						
						param[8] = cbj;  //成本价
						param[9] = lsdProduct.getQz_serial_num();    //序列号
						param[10] = khcbj;          //考核成本价
						param[11] = gf;    //工分(比例点杀)
						param[12] = sd;    //税点
						param[13] = lsdProduct.getXj() / (1 + sd/100);  //不含税金额
						param[14] = basic_ratio;
						param[15] = out_ratio;
						param[16] = ds;
						param[17] = lsxj;
						param[18] = ygcbj;
						param[19] = sfcytc;
						
						this.getJdbcTemplate().update(sql,param);
					}
				}
			}
		}
	}
	
	
	
	/**
	 * 根据商品编号取商品信息
	 * @param product_id
	 * @return
	 */
	private Map getProductInfo(String product_id){
		String sql = "select * from product where product_id='" + product_id + "'";
		return this.getResultMap(sql);
	}
	
	
	/**
	 * 删除零售单关联商品
	 * @param lsd_id
	 */
	private void delLsdProducts(String lsd_id){
		String sql = "delete from lsd_product where lsd_id='" + lsd_id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取当前可用的序列号
	 * @return
	 */
	public String getLsdID() {
		String sql = "select lsdid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();

		// 将序列号加1
		sql = "update cms_all_seq set lsdid=lsdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "LS" + day + "-" + curId;
	}
	
	
	/**
	 * 取零售税点
	 * @return
	 */
	public double getLssd(){
		double sd = 0;
		String sql = "select sd from lssd";
		Map map = this.getResultMap(sql);
		if(map != null){
			sd = ((Double)map.get("sd")).doubleValue();
		}
		return sd;
	}
	
	
	/**
	 * 保存零售税点
	 * @param sd
	 */
	public void saveLssd(String sd){
		String sql = "delete from lssd";
		this.getJdbcTemplate().update(sql);
		
		sql = "insert into lssd(sd) values(" + sd + ")";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取提成比例
	 * @return
	 */
	public Map getTcbl(){
		String sql = "select * from tcbl_set";
		return this.getResultMap(sql);
	}
	
	
	/**
	 * 保存提成比例
	 * @param basic_ratio
	 * @param out_ratio
	 */
	public void saveTcbl(String basic_ratio,String out_ratio,String ds_ratio){
		String sql = "delete from tcbl_set";
		this.getJdbcTemplate().update(sql);
		
		sql = "insert into tcbl_set(basic_ratio,out_ratio,ds_ratio) values('" + basic_ratio + "','" + out_ratio + "','" + ds_ratio + "')";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 查看零售单是否已经提交
	 * @param ckd_id
	 * @return
	 */
	public boolean isLsdSubmit(String lsd_id){
		boolean is = false;
		String sql = "select count(*) from lsd where id='" + lsd_id + "' and state='已提交'";
		int counts = this.getJdbcTemplate().queryForInt(sql);
		if(counts > 0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * 包装对象(零售单)
	 * 
	 * @author liyt
	 * 
	 */
	class LsdRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Lsd lsd = new Lsd();

			if(SqlUtil.columnIsExist(rs,"id")) lsd.setId(rs.getString("id"));
			if(SqlUtil.columnIsExist(rs,"creatdate")) lsd.setCreatdate(rs.getString("creatdate"));
			if(SqlUtil.columnIsExist(rs,"xsry")) lsd.setXsry(rs.getString("xsry"));
			if(SqlUtil.columnIsExist(rs,"fkfs")) lsd.setFkfs(rs.getString("fkfs"));
			if(SqlUtil.columnIsExist(rs,"state")) lsd.setState(rs.getString("state"));
			if(SqlUtil.columnIsExist(rs,"store_id")) lsd.setStore_id(rs.getString("store_id"));
			
			if(SqlUtil.columnIsExist(rs,"client_name")) lsd.setClient_name(rs.getString("client_name"));
			if(SqlUtil.columnIsExist(rs,"lxr")) lsd.setLxr(rs.getString("lxr"));
			if(SqlUtil.columnIsExist(rs,"lxdh")) lsd.setLxdh(rs.getString("lxdh"));
			if(SqlUtil.columnIsExist(rs,"mobile")) lsd.setMobile(rs.getString("mobile"));
			if(SqlUtil.columnIsExist(rs,"mail")) lsd.setMail(rs.getString("mail"));
			if(SqlUtil.columnIsExist(rs,"msn")) lsd.setMsn(rs.getString("msn"));
			if(SqlUtil.columnIsExist(rs,"address")) lsd.setAddress(rs.getString("address"));
			if(SqlUtil.columnIsExist(rs,"p_code")) lsd.setP_code(rs.getString("p_code"));
			
			if(SqlUtil.columnIsExist(rs,"fplx")) lsd.setFplx(rs.getString("fplx"));
			if(SqlUtil.columnIsExist(rs,"khhzh")) lsd.setKhhzh(rs.getString("khhzh"));
			if(SqlUtil.columnIsExist(rs,"sh")) lsd.setSh(rs.getString("sh"));
			if(SqlUtil.columnIsExist(rs,"fpxx")) lsd.setFpxx(rs.getString("fpxx"));
			
			if(SqlUtil.columnIsExist(rs,"yhje")) lsd.setYhje(rs.getDouble("yhje"));
			if(SqlUtil.columnIsExist(rs,"lsdje")) lsd.setLsdje(rs.getDouble("lsdje"));
			if(SqlUtil.columnIsExist(rs,"lsdcbj")) lsd.setLsdcbj(rs.getDouble("lsdcbj"));
			if(SqlUtil.columnIsExist(rs,"skje")) lsd.setSkje(rs.getDouble("skje"));
			if(SqlUtil.columnIsExist(rs,"skzh")) lsd.setSkzh(rs.getString("skzh"));
			
			if(SqlUtil.columnIsExist(rs,"ms")) lsd.setMs(rs.getString("ms"));
			if(SqlUtil.columnIsExist(rs,"czr")) lsd.setCzr(rs.getString("czr"));
			
			if(SqlUtil.columnIsExist(rs,"has_yushk")) lsd.setHas_yushk(rs.getString("has_yushk"));
			if(SqlUtil.columnIsExist(rs,"yushk_id")) lsd.setYushk_id(rs.getString("yushk_id"));
			if(SqlUtil.columnIsExist(rs,"yushkje")) lsd.setYushkje(rs.getDouble("yushkje"));
			
			if(SqlUtil.columnIsExist(rs,"kp_mc")) lsd.setKp_mc(rs.getString("kp_mc"));
			if(SqlUtil.columnIsExist(rs,"kp_address")) lsd.setKp_address(rs.getString("kp_address"));
			if(SqlUtil.columnIsExist(rs,"kp_dh")) lsd.setKp_dh(rs.getString("kp_dh"));
			if(SqlUtil.columnIsExist(rs,"lsdkhcb")) lsd.setLsdkhcb(rs.getDouble("lsdkhcb"));
			
			if(SqlUtil.columnIsExist(rs,"sp_state")) lsd.setSp_state(rs.getString("sp_state"));
			if(SqlUtil.columnIsExist(rs,"spr")) lsd.setSpr(rs.getString("spr"));
			if(SqlUtil.columnIsExist(rs,"sp_date") && rs.getTimestamp("sp_date") != null){
				lsd.setSp_date(rs.getTimestamp("sp_date").toString());
			}
			if(SqlUtil.columnIsExist(rs,"sp_opinion")) lsd.setSp_opinion(rs.getString("sp_opinion"));
			if(SqlUtil.columnIsExist(rs,"pos_id")) lsd.setPos_id(rs.getString("pos_id"));
			if(SqlUtil.columnIsExist(rs,"hyk_id")) lsd.setHyk_id(rs.getString("hyk_id"));
			
			return lsd;
		}
	}
	
	
	/**
	 * 包装对象(零售单商品)
	 * 
	 * @author liyt
	 * 
	 */
	class LsdProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			LsdProduct lsdProduct = new LsdProduct();

			if(SqlUtil.columnIsExist(rs,"id")) lsdProduct.setId(rs.getInt("id"));
			if(SqlUtil.columnIsExist(rs,"lsd_id")) lsdProduct.setLsd_id(rs.getString("lsd_id"));
			if(SqlUtil.columnIsExist(rs,"product_id")) lsdProduct.setProduct_id(rs.getString("product_id"));
			if(SqlUtil.columnIsExist(rs,"product_xh")) lsdProduct.setProduct_xh(rs.getString("product_xh"));
			if(SqlUtil.columnIsExist(rs,"product_name")) lsdProduct.setProduct_name(rs.getString("product_name"));
			if(SqlUtil.columnIsExist(rs,"price")) lsdProduct.setPrice(rs.getDouble("price"));
			if(SqlUtil.columnIsExist(rs,"nums")) lsdProduct.setNums(rs.getInt("nums"));
			if(SqlUtil.columnIsExist(rs,"xj")) lsdProduct.setXj(rs.getDouble("xj"));
			if(SqlUtil.columnIsExist(rs,"remark")) lsdProduct.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"cbj")) lsdProduct.setCbj(rs.getDouble("cbj"));
			if(SqlUtil.columnIsExist(rs,"qz_serial_num")) lsdProduct.setQz_serial_num(rs.getString("qz_serial_num"));
			if(SqlUtil.columnIsExist(rs,"kh_cbj")) lsdProduct.setKh_cbj(rs.getDouble("kh_cbj"));
			if(SqlUtil.columnIsExist(rs,"qz_flag")) lsdProduct.setQz_flag(rs.getString("qz_flag"));
			if(SqlUtil.columnIsExist(rs,"gf")) lsdProduct.setGf(rs.getDouble("gf"));
			if(SqlUtil.columnIsExist(rs,"dw")) lsdProduct.setDw(rs.getString("dw"));
			
			if(SqlUtil.columnIsExist(rs,"sd")) lsdProduct.setSd(rs.getDouble("sd"));
			if(SqlUtil.columnIsExist(rs,"ds")) lsdProduct.setDs(rs.getDouble("ds"));
			if(SqlUtil.columnIsExist(rs,"basic_ratio")) lsdProduct.setBasic_ratio(rs.getDouble("basic_ratio"));
			if(SqlUtil.columnIsExist(rs,"out_ratio")) lsdProduct.setOut_ratio(rs.getDouble("out_ratio"));
			if(SqlUtil.columnIsExist(rs,"lsxj")) lsdProduct.setLsxj(rs.getDouble("lsxj"));
			if(SqlUtil.columnIsExist(rs,"ygcbj")) lsdProduct.setYgcbj(rs.getDouble("ygcbj"));
			if(SqlUtil.columnIsExist(rs,"sfcytc")) lsdProduct.setSfcytc(rs.getString("sfcytc"));
			
			return lsdProduct;
		}
	}

}
