package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Fxdd;
import com.sw.cms.model.Page;
import com.sw.cms.model.Xsd;
import com.sw.cms.model.XsdProduct;
import com.sw.cms.util.DateComFunc;

/**
 * 销售订单，分销订单
 * @author liyt
 *
 */

public class XsdDAO extends JdbcBaseDAO {
	
	
	/**
	 * 销售订单列表(带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getXsdList(String con,int curPage, int rowsPerPage){
		String sql = "select id,client_name,state,xsdje,creatdate,fzr,czr,skxs,sp_state,sklx,th_flag from xsd where 1=1";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * 取待审批销售单列表
	 * @param con
	 * @return
	 */
	public List getDspXsdList(String con){
		String sql = "select a.id,b.real_name,c.dept_name,a.creatdate from xsd a left join sys_user b on b.user_id=a.fzr left join dept c on c.dept_id=b.dept where a.sp_state='2'";
		
		if(!con.equals("")){
			sql += " and (" + con + ")";
		}
		
		return this.getResultList(sql);
	}
	
	
	/**
	 * 分销订单（分销业务->采购订单）列表
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getFxddList(String con,int curPage,int rowsPerPage){
		String sql = "select * from fxdd where 1=1";
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage,new FxddRowMapper());
	}
	
	
	/**
	 * 保存销售单信息
	 * @param xsd
	 * @param xsdProducts
	 */
	public void saveXsd(Xsd xsd,List xsdProducts){
		
		String sql = "insert into xsd(creatdate,fzr,client_name,sklx,state,yhje,xsdje,xsdcbj,skje,skzh,ms,czr,cz_date," +
				"skxs,skrq,ysrq,kh_address,kh_lxr,kh_lxdh,ysfs,id,store_id,xjd,ysje,zq,sjcjje,xsdkhcb,sp_state,sp_type,skfs,pos_id,yfzf_type) " +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		String xsd_id = xsd.getId();
		
		Object[] param = new Object[31];
		
		param[0] = xsd.getCreatdate();
		param[1] = xsd.getFzr();
		param[2] = xsd.getClient_name();
		param[3] = xsd.getSklx();
		param[4] = xsd.getState();
		param[5] = new Double(xsd.getYhje());
		param[6] = new Double(xsd.getXsdje());
		param[7] = new Double(xsd.getXsdcbj());
		param[8] = new Double(xsd.getSkje());
		param[9] = xsd.getSkzh();
		param[10] = xsd.getMs();
		param[11] = xsd.getCzr();
		param[12] = xsd.getSkxs();
		param[13] = xsd.getSkrq();
		param[14] = xsd.getYsrq();
		param[15] = xsd.getKh_address();
		param[16] = xsd.getKh_lxr();
		param[17] = xsd.getKh_lxdh();
		param[18] = xsd.getYsfs();
		param[19] = xsd_id;
		param[20] = xsd.getStore_id();
		param[21] = xsd.getXjd();
		param[22] = xsd.getYsje();
		param[23] = xsd.getZq();
		param[24] = xsd.getSjcjje();
		param[25] = xsd.getXsdkhcb();
		param[26] = xsd.getSp_state();
		param[27] = xsd.getSp_type();
		param[28] = xsd.getSkfs();
		param[29] = xsd.getPos_id();
		param[30] = xsd.getYfzf_type();
		
		this.getJdbcTemplate().update(sql,param);  //保存销售单信息
		
		delXsdProducts(xsd_id);    //删除相应销售单产品
		
		addXsdProducts(xsdProducts,xsd);   //添加销售单相关产品		
		
	}
	
	
	/**
	 * 保存分销订单信息
	 * @param fxdd
	 * @param xsdProducts
	 */
	public void saveFxdd(Fxdd fxdd,List xsdProducts){
		String sql = "insert into fxdd(creatdate,client_name,ysfs,hjje,state,remark,wlzt,address,lxr,lxdh,cz_date,fxdd_id) " +
				"values(?,?,?,?,?,?,?,?,?,?,now(),?)";
		
		Object[] param = new Object[11];
		
		param[0] = fxdd.getCreatdate();
		param[1] = fxdd.getClient_name();
		param[2] = fxdd.getYsfs();
		param[3] = fxdd.getHjje();
		param[4] = fxdd.getState();
		param[5] = fxdd.getRemark();
		param[6] = fxdd.getWlzt();
		param[7] = fxdd.getAddress();
		param[8] = fxdd.getLxr();
		param[9] = fxdd.getLxdh();
		param[10] = fxdd.getFxdd_id();

		this.getJdbcTemplate().update(sql,param);
		
		Xsd xsd = new Xsd();
		xsd.setId(fxdd.getFxdd_id());
		
		addXsdProducts(xsdProducts,xsd);   //添加分销订单相关产品
	}
	
	
	/**
	 * 更新销售订单信息
	 * @param xsd
	 * @param xsdProducts
	 */
	public void updateXsd(Xsd xsd,List xsdProducts){
		
		String xsd_id = xsd.getId();
		
		String sql = "select * from xsd where id='" + xsd_id + "'";
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			//存在更新
			sql = "update xsd set creatdate=?,fzr=?,client_name=?,sklx=?,state=?,yhje=?,xsdje=?,xsdcbj=?,skje=?,skzh=?,ms=?,czr=?,skxs=?," +
					"skrq=?,ysrq=?,cz_date=now(),kh_address=?,kh_lxr=?,kh_lxdh=?,ysfs=?,store_id=?,xjd=?,ysje=?,zq=?," +
					"sjcjje=?,xsdkhcb=?,sp_state=?,sp_type=?,skfs=?,pos_id=?,fplx=?,kp_mc=?,kp_address=?,kp_dh=?,khhzh=?,sh=?,fpxx=?,yfzf_type=? where id=?";
		}else{
			//不存在添加
			sql = "insert into xsd(creatdate,fzr,client_name,sklx,state,yhje,xsdje,xsdcbj,skje,skzh,ms,czr,skxs,skrq,ysrq,cz_date," +
					"kh_address,kh_lxr,kh_lxdh,ysfs,store_id,xjd,ysje,zq,sjcjje,xsdkhcb,sp_state,sp_type,skfs,pos_id,fplx,kp_mc,kp_address,kp_dh,khhzh,sh,fpxx,yfzf_type,id) " +
					"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}
		
		
		Object[] param = new Object[38];
		
		param[0] = xsd.getCreatdate();
		param[1] = xsd.getFzr();
		param[2] = xsd.getClient_name();
		param[3] = xsd.getSklx();
		param[4] = xsd.getState();
		param[5] = new Double(xsd.getYhje());
		param[6] = new Double(xsd.getXsdje());
		param[7] = new Double(xsd.getXsdcbj());
		param[8] = new Double(xsd.getSkje());
		param[9] = xsd.getSkzh();
		param[10] = xsd.getMs();
		param[11] = xsd.getCzr();
		param[12] = xsd.getSkxs();
		param[13] = xsd.getSkrq();
		param[14] = xsd.getYsrq();
		param[15] = xsd.getKh_address();
		param[16] = xsd.getKh_lxr();
		param[17] = xsd.getKh_lxdh();
		param[18] = xsd.getYsfs();
		param[19] = xsd.getStore_id();
		param[20] = xsd.getXjd();
		param[21] = xsd.getYsje();
		param[22] = xsd.getZq();
		param[23] = xsd.getSjcjje();
		param[24] = xsd.getXsdkhcb();
		param[25] = xsd.getSp_state();
		param[26] = xsd.getSp_type();
		param[27] = xsd.getSkfs();
		param[28] = xsd.getPos_id();		
		param[29] = xsd.getFplx();
		param[30] = xsd.getKp_mc();
		param[31] = xsd.getKp_address();
		param[32] = xsd.getKp_dh();
		param[33] = xsd.getKhhzh();
		param[34] = xsd.getSh();
		param[35] = xsd.getFpxx();	
		param[36] = xsd.getYfzf_type();
		param[37] = xsd_id;
		
		
		this.getJdbcTemplate().update(sql,param);  //更新销售单信息
		
		//修改退回标记
		if(!xsd.getState().equals("已保存")){
			sql = "update xsd set th_flag='0' where id='" + xsd.getId() + "'";
			this.getJdbcTemplate().update(sql);
		}
		
		delXsdProducts(xsd_id);    //删除相应销售单产品
		
		addXsdProducts(xsdProducts,xsd);   //添加销售单相关产品		
	}
	
	
	/**
	 * 更新分销订单信息
	 * @param fxdd
	 * @param xsdProducts
	 */
	public void updateFxdd(Fxdd fxdd,List xsdProducts){
		String sql = "update fxdd set creatdate=?,client_name=?,ysfs=?,hjje=?,state=?,remark=?,wlzt=?,address=?,lxr=?,lxdh=?,cz_date=now() where fxdd_id=?";
		
		Object[] param = new Object[11];
		
		param[0] = fxdd.getCreatdate();
		param[1] = fxdd.getClient_name();
		param[2] = fxdd.getYsfs();
		param[3] = fxdd.getHjje();
		param[4] = fxdd.getState();
		param[5] = fxdd.getRemark();
		param[6] = fxdd.getWlzt();
		param[7] = fxdd.getAddress();
		param[8] = fxdd.getLxr();
		param[9] = fxdd.getLxdh();
		param[10] = fxdd.getFxdd_id();

		this.getJdbcTemplate().update(sql,param);
		
		delXsdProducts(fxdd.getFxdd_id());    //删除相应分销订单产品
		
		Xsd xsd = new Xsd();
		xsd.setId(fxdd.getFxdd_id());
		
		addXsdProducts(xsdProducts,xsd);   //添加分销订单相关产品
	}
	
	
	/**
	 * 根据编号获取销售单信息
	 * @param id
	 * @return
	 */
	public Object getXsd(String id){
		String sql = "select * from xsd where id='" + id + "'";
		
		return this.queryForObject(sql, new XsdRowMapper());
	}
	
	
	/**
	 * 根据分销定单编号取分销订单信息
	 * @param fxdd_id
	 * @return
	 */
	public Object getFxdd(String fxdd_id){
		String sql = "select * from fxdd where fxdd_id='" + fxdd_id + "'";
		return this.queryForObject(sql, new FxddRowMapper());
	}
	
	
	/**
	 * 更新销售单回款形式及收款金额
	 * @param id
	 * @param state
	 * @param skje
	 */
	public void updateXsdStateAndSkje(String id,String skxs,double skje){
		String sql = "update xsd set skxs='" + skxs + "',skje=" + skje + " where id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 更新销售单实际成交金额
	 * @param xsd_id
	 * @param sjcjje
	 */
	public void updateXsdSjcjje(String xsd_id,double sjcjje){
		String sql = "update xsd set sjcjje=" + sjcjje + " where id='" + xsd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	/**
	 * 更新销售单产品实际成交数
	 * @param xsd_id
	 * @param product_id
	 * @param nums
	 * @param sjcj_xj
	 */
	public void updateXsdSjcjNums(String xsd_id,String product_id,int nums,double sjcj_xj){
		String sql = "update xsd_product set sjcj_nums=" + nums + ",sjcj_xj=" + sjcj_xj + " " +
				"where xsd_id='" + xsd_id + "' and product_id='" + product_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 根据编号获取销售相关产品
	 * @param id
	 * @return
	 */
	public List getXsdProducts(String id){
		String sql = "select a.*,b.qz_serial_num as qz_flag,b.dw from xsd_product a left join product b on b.product_id=a.product_id where xsd_id='" + id + "'";
		
		return this.getResultList(sql, new XsdProductRowMapper());
	}
	
	/**
	 * 保存销售订单的审批结果
	 * @param lsd_id
	 * @param sp_state
	 * @param spr
	 */
	public void saveSp(String xsd_id,String sp_state,String spr){
		String sql = "update xsd set spr='" + spr + "',sp_state='" + sp_state + "',sp_date=now() where id='" + xsd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 删除销售单信息
	 * @param id
	 */
	public void delXsd(String id){
		
		String sql = "delete from xsd where id='" + id + "'";		
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from xsd_product where xsd_id='" + id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 删除分销订单信息
	 * @param fxdd_id
	 */
	public void delFxdd(String fxdd_id){
		String sql = "delete from fxdd where fxdd_id='" + fxdd_id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "delete from xsd_product where xsd_id='" + fxdd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 添加销售单相关产品
	 * @param XsdProducts
	 * @param xsd_id
	 */
	private void addXsdProducts(List xsdProducts,Xsd xsd){
		String sql = "";
		Object[] param = new Object[22];
		
		double sd = 0;
		
		if(!xsd.getFplx().equals("出库单")){
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
		
		String xsd_id = xsd.getId();
		
		if(xsdProducts != null && xsdProducts.size()>0){
			for(int i=0;i<xsdProducts.size();i++){
				XsdProduct xsdProduct = (XsdProduct)xsdProducts.get(i);
				if(xsdProduct != null){
					if(!xsdProduct.getProduct_id().equals("")){
						sql = "insert into xsd_product(xsd_id,product_id,product_xh,product_name,price,jgtz,nums,remark,xj,cbj,qz_serial_num,sjcj_nums,sjcj_xj,kh_cbj,gf,sd,bhsje,basic_ratio,out_ratio,ds,lsxj,ygcbj) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
						
						param[0] = xsd_id;
						param[1] = xsdProduct.getProduct_id();
						param[2] = xsdProduct.getProduct_xh();
						param[3] = xsdProduct.getProduct_name();
						param[4] = new Double(xsdProduct.getPrice());
						param[5] = new Double(xsdProduct.getJgtz());
						param[6] = new Integer(xsdProduct.getNums());
						param[7] = xsdProduct.getRemark();
						param[8] = new Double(xsdProduct.getXj());
						param[9] = new Double(xsdProduct.getCbj());
						param[10] = xsdProduct.getQz_serial_num();
						param[11] = xsdProduct.getSjcj_nums();
						param[12] = xsdProduct.getSjcj_xj();
						param[13] = xsdProduct.getKh_cbj(); 
						
						double gf = 0l;
						Map map = this.getProductInfo(xsdProduct.getProduct_id());
						double lsxj = 0l;
						double ds = 0l;
						double ygcbj = 0l;
						if(map != null){
							lsxj = map.get("lsxj")==null?0:((Double)map.get("lsxj")).doubleValue();
							gf = map.get("gf")==null?0:((Double)map.get("gf")).doubleValue();
							ds = map.get("dss")==null?0:((Double)map.get("dss")).doubleValue();
							ygcbj = map.get("ygcbj")==null?0:((Double)map.get("ygcbj")).doubleValue();
						}
						
						//不含税单价低于零售限价时 点杀需要乘以比例
						if((xsdProduct.getPrice()/ (1 + sd/100)) < lsxj){
							ds = ds * ds_ratio/100;
						}
						
						param[14] = gf;
						param[15] = sd;    //税点
						param[16] = xsdProduct.getXj() / (1 + sd/100);  //不含税金额
						
						param[17] = basic_ratio;
						param[18] = out_ratio;
						param[19] = ds;
						param[20] = lsxj;
						param[21] = ygcbj;
						
						this.getJdbcTemplate().update(sql,param);
						
					}
				}
			}
		}
	}
	
	
	/**
	 * 更新销售单商品信息
	 * @param xsdProducts
	 */
	public void updateXsdProducts(List xsdProducts){
		if(xsdProducts != null && xsdProducts.size() > 0){
			for(int i= 0; i<xsdProducts.size(); i++){
				XsdProduct xsdProduct = (XsdProduct)xsdProducts.get(i);
				String sql = "update xsd_product set cbj=" + xsdProduct.getCbj() + 
						",kh_cbj=" + xsdProduct.getKh_cbj() + ",ds=" + xsdProduct.getDs() + 
						",ygcbj=" + xsdProduct.getYgcbj() + ",gf=" + xsdProduct.getGf() + 
						" where id=" + xsdProduct.getId();
				
				this.getJdbcTemplate().update(sql);
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
	 * 删除销售单相关产品
	 * @param xsd_id
	 */
	private void delXsdProducts(String xsd_id){
		String sql = "delete from xsd_product where xsd_id='" + xsd_id + "'";
		
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 根据销售单出库信息更新相应销售订单物流信息
	 * @param xsd_id    销售单编号
	 * @param state     状态
	 * @param ysfs      运输方式
	 * @param cx_tel    查询电话
	 * @param job_no    货单号
	 * @param send_time 发货时间
	 * @param store_id  库房编号
	 * @param ck_jsr    出库经手人
	 * @param ck_date   出库时间
	 */
	public void updateXsdState(String xsd_id,String state,String ysfs,String cx_tel,String job_no,String send_time,String store_id,String ck_jsr,String ck_date){
		String sql = "update xsd set state='" + state + "',ysfs='" + ysfs + "',cx_tel='" + cx_tel + "'," +
				"job_no='" + job_no + "',send_time='" + send_time + "',cz_date=now(),store_id='" + store_id + "',ck_jsr='" + ck_jsr + "',ck_date='" + ck_date + "' where id='" + xsd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 根据出库单信息更新分销订单物流状态
	 * @param fxdd_id
	 * @param state
	 */
	public void updateFxddState(String fxdd_id,String state,String ysfs,String cx_tel,String job_no,String send_time){
		String sql = "update fxdd set wlzt='" + state + "',ysfs='" + ysfs + "',cx_tel='" + cx_tel + "'," +
				"job_no='" + job_no + "',send_time='" + send_time + "' where fxdd_id='" + fxdd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 出库单退回时销售单的处理
	 * @param xsd_id
	 * @param state
	 * @param th_flag
	 */
	public void updateXsdAfterCkdTh(String xsd_id,String state,String th_flag){
		String sql = "update xsd set state='" + state + "',th_flag='" + th_flag + "' where id='" + xsd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取当销售订单前可用的序列号
	 * @return
	 */
	public String getXsdID() {
		String sql = "select xsdid from cms_all_seq";

		// 取当前序列号
		String curId = this.getJdbcTemplate().queryForInt(sql) + "";
		
		String day = DateComFunc.getCurDay();
		// 将序列号加1
		sql = "update cms_all_seq set xsdid=xsdid+1";
		this.getJdbcTemplate().update(sql);

		for (int i = curId.length(); i < 3; i++) {
			curId = "0" + curId;
		}

		return "XS" + day + "-" + curId;
	}
	
	
	/**
	 * 根据客户编号取客户应收货款
	 * @param client_name
	 * @return
	 */
	public double getYshkByClientId(String client_name){
		double yshk = 0;
		String sql = "select a.* from (select sum(xsdje-skje) as ysje from xsd where (state='已提交' or state='已出库') and skxs<>'已收' and client_name='" + client_name + "') a" ;		
		Map map = this.getJdbcTemplate().queryForMap(sql);
		if(map != null){
			yshk = map.get("ysje")==null?0:((Double)map.get("ysje")).doubleValue();
		}
		return yshk;
	}
	
	
	/**
	 * 取客户应收款列表
	 * @param client_id
	 * @return
	 */
	public List getYskListByClientId(String client_id){
		String sql = "select * from xsd where state='已出库' and skxs<>'已收' and client_name='" + client_id + "' order by creatdate";
		return this.getJdbcTemplate().queryForList(sql);
	}
	
	
	/**
	 * 根据销售单编号判断销售单是否存在
	 * @param xsd_id
	 * @return
	 */
	public boolean isHasXsdByID(String xsd_id){
		boolean is = false;
		String sql = "select * from xsd where id='" + xsd_id + "'";
		
		List list = this.getJdbcTemplate().queryForList(sql);
		
		if(list != null && list.size()>0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * 取零售税点
	 * @return
	 */
	private double getLssd(){
		double sd = 0;
		String sql = "select sd from lssd";
		Map map = this.getResultMap(sql);
		if(map != null){
			sd = ((Double)map.get("sd")).doubleValue();
		}
		return sd;
	}
	
	/**
	 * 取提成比例
	 * @return
	 */
	private Map getTcbl(){
		String sql = "select * from tcbl_set";
		return this.getResultMap(sql);
	}
	
	
	/**
	 * 判断销售单中是否存在该产品
	 * @param xsd_id
	 * @param product_id
	 * @return
	 */
	public boolean isHasXsdProduct(String xsd_id,String product_id){
		boolean is = false;
		String sql = "select * from xsd_product where xsd_id='" + xsd_id + "' and product_id='" + product_id + "'";
		
		List list = this.getJdbcTemplate().queryForList(sql);
		
		if(list != null && list.size()>0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * 更新分销订单物流状态
	 * @param fxdd_id
	 * @param wlzt
	 */
	public void updateFxddWlzt(String fxdd_id,String wlzt){
		String sql = "update fxdd set wlzt='" + wlzt + "' where fxdd_id='" + fxdd_id + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 取具超额审批权限角色列表
	 * @return
	 */
	public String getCespRoles(){
		String roles = "";
		
		String sql = "select * from cesp_right_roles";		
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);				
				if(roles.equals("")){
					roles = (String)map.get("role_id");
				}else{
					roles += "," + (String)map.get("role_id");
				}
			}
		}
		
		return roles;
	}
	
	
	/**
	 *  根据销售单ID和销售单销售货品的序列号查询销售货品记录以及购买人的记录
	 */
	public Object getXsdByIdBySerailNum(String id,String num){
		String sql="select x.client_name,x.kh_lxr,x.kh_address,x.kh_lxdh,p.product_id,p.product_xh,p.product_name,p.qz_serial_num from xsd x left join xsd_product p on x.id=p.xsd_id where x.id='"+id+"' and p.qz_serial_num like '%"+num+"%'";
		return this.getResultMap(sql);
	}
	
	
	/**
	 * 包装对象(销售单)
	 * 
	 * @author liyt
	 * 
	 */
	class XsdRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Xsd xsd = new Xsd();

			if(SqlUtil.columnIsExist(rs,"id")) xsd.setId(rs.getString("id"));
			if(SqlUtil.columnIsExist(rs,"creatdate")) xsd.setCreatdate(rs.getString("creatdate"));
			if(SqlUtil.columnIsExist(rs,"fzr")) xsd.setFzr(rs.getString("fzr"));
			if(SqlUtil.columnIsExist(rs,"client_name")) xsd.setClient_name(rs.getString("client_name"));
			if(SqlUtil.columnIsExist(rs,"sklx")) xsd.setSklx(rs.getString("sklx"));
			if(SqlUtil.columnIsExist(rs,"state")) xsd.setState(rs.getString("state"));
			
			if(SqlUtil.columnIsExist(rs,"yhje")) xsd.setYhje(rs.getDouble("yhje"));
			if(SqlUtil.columnIsExist(rs,"xsdje")) xsd.setXsdje(rs.getDouble("xsdje"));
			if(SqlUtil.columnIsExist(rs,"xsdcbj")) xsd.setXsdcbj(rs.getDouble("xsdcbj"));
			if(SqlUtil.columnIsExist(rs,"skje")) xsd.setSkje(rs.getDouble("skje"));
			if(SqlUtil.columnIsExist(rs,"skzh")) xsd.setSkzh(rs.getString("skzh"));
			
			if(SqlUtil.columnIsExist(rs,"ms")) xsd.setMs(rs.getString("ms"));
			if(SqlUtil.columnIsExist(rs,"czr")) xsd.setCzr(rs.getString("czr"));	
			
			if(SqlUtil.columnIsExist(rs,"skxs")) xsd.setSkxs(rs.getString("skxs"));
			if(SqlUtil.columnIsExist(rs,"skrq")) xsd.setSkrq(rs.getString("skrq"));
			if(SqlUtil.columnIsExist(rs,"ysrq")) xsd.setYsrq(rs.getString("ysrq"));
			
			if(SqlUtil.columnIsExist(rs,"kh_address")) xsd.setKh_address(rs.getString("kh_address"));
			if(SqlUtil.columnIsExist(rs,"kh_lxr")) xsd.setKh_lxr(rs.getString("kh_lxr"));
			if(SqlUtil.columnIsExist(rs,"kh_lxdh")) xsd.setKh_lxdh(rs.getString("kh_lxdh"));
			if(SqlUtil.columnIsExist(rs,"ysfs")) xsd.setYsfs(rs.getString("ysfs"));
			if(SqlUtil.columnIsExist(rs,"store_id")) xsd.setStore_id(rs.getString("store_id"));
			if(SqlUtil.columnIsExist(rs,"ck_jsr")) xsd.setCk_jsr(rs.getString("ck_jsr"));
			if(SqlUtil.columnIsExist(rs,"ck_date")) xsd.setCk_date(rs.getString("ck_date"));
			if(SqlUtil.columnIsExist(rs,"xjd")) xsd.setXjd(rs.getDouble("xjd"));
			if(SqlUtil.columnIsExist(rs,"ysje")) xsd.setYsje(rs.getDouble("ysje"));
			if(SqlUtil.columnIsExist(rs,"zq")) xsd.setZq(rs.getInt("zq"));
			if(SqlUtil.columnIsExist(rs,"sjcjje")) xsd.setSjcjje(rs.getDouble("sjcjje"));
			if(SqlUtil.columnIsExist(rs,"th_flag")) xsd.setTh_flag(rs.getString("th_flag"));
			if(SqlUtil.columnIsExist(rs,"cz_date") && rs.getTimestamp("cz_date") != null){
				xsd.setCz_date(rs.getTimestamp("cz_date").toString());
			}
			if(SqlUtil.columnIsExist(rs,"xsdkhcb")) xsd.setXsdkhcb(rs.getDouble("xsdkhcb"));
			
			if(SqlUtil.columnIsExist(rs,"sp_state")) xsd.setSp_state(rs.getString("sp_state"));
			if(SqlUtil.columnIsExist(rs,"sp_type")) xsd.setSp_type(rs.getString("sp_type"));
			
			if(SqlUtil.columnIsExist(rs,"spr")) xsd.setSpr(rs.getString("spr"));
			if(SqlUtil.columnIsExist(rs,"sp_date") && rs.getTimestamp("sp_date") != null){
				xsd.setSp_date(rs.getTimestamp("sp_date").toString());
			}
			if(SqlUtil.columnIsExist(rs,"sp_opinion")) xsd.setSp_opinion(rs.getString("sp_opinion"));
			
			if(SqlUtil.columnIsExist(rs,"skfs")) xsd.setSkfs(rs.getString("skfs"));
			if(SqlUtil.columnIsExist(rs,"pos_id")) xsd.setPos_id(rs.getString("pos_id"));
			
			if(SqlUtil.columnIsExist(rs,"fplx")) xsd.setFplx(rs.getString("fplx"));
			if(SqlUtil.columnIsExist(rs,"kp_mc")) xsd.setKp_mc(rs.getString("kp_mc"));
			if(SqlUtil.columnIsExist(rs,"kp_address")) xsd.setKp_address(rs.getString("kp_address"));
			if(SqlUtil.columnIsExist(rs,"kp_dh")) xsd.setKp_dh(rs.getString("kp_dh"));
			if(SqlUtil.columnIsExist(rs,"khhzh")) xsd.setKhhzh(rs.getString("khhzh"));
			if(SqlUtil.columnIsExist(rs,"sh")) xsd.setSh(rs.getString("sh"));
			if(SqlUtil.columnIsExist(rs,"fpxx")) xsd.setFpxx(rs.getString("fpxx"));
			
			if(SqlUtil.columnIsExist(rs,"ysks")) xsd.setYsfs(rs.getString("ysfs"));
			if(SqlUtil.columnIsExist(rs,"job_no")) xsd.setJob_no(rs.getString("job_no"));
			if(SqlUtil.columnIsExist(rs,"cx_tel")) xsd.setCx_tel(rs.getString("cx_tel"));
			if(SqlUtil.columnIsExist(rs,"send_time")) xsd.setSend_time(rs.getString("send_time"));
			if(SqlUtil.columnIsExist(rs,"yfzf_type")) xsd.setYfzf_type(rs.getString("yfzf_type"));
			
			
			return xsd;
		}
	}

	
	/**
	 * 根据销售单编号及产品编号取销售明细信息
	 * @param xsd_id
	 * @param product_id
	 * @return
	 */
	public Object getXsdProductInfo(String xsd_id,String product_id){
		String sql = "select *,'' as qz_flag from xsd_product where xsd_id='" + xsd_id + "' and product_id='" + product_id + "'";
		return this.queryForObject(sql, new XsdProductRowMapper());
	}
	
	
	/**
	 * 包装分销订单对象
	 * @author liyt
	 *
	 */
	class FxddRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			Fxdd fxdd = new Fxdd();
			
			if(SqlUtil.columnIsExist(rs,"fxdd_id")) fxdd.setFxdd_id(rs.getString("fxdd_id"));
			if(SqlUtil.columnIsExist(rs,"client_name")) fxdd.setClient_name(rs.getString("client_name"));
			if(SqlUtil.columnIsExist(rs,"creatdate")) fxdd.setCreatdate(rs.getString("creatdate"));
			if(SqlUtil.columnIsExist(rs,"hjje")) fxdd.setHjje(rs.getDouble("hjje"));
			if(SqlUtil.columnIsExist(rs,"remark")) fxdd.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"state")) fxdd.setState(rs.getString("state"));
			if(SqlUtil.columnIsExist(rs,"wlzt")) fxdd.setWlzt(rs.getString("wlzt"));
			if(SqlUtil.columnIsExist(rs,"ysfs")) fxdd.setYsfs(rs.getString("ysfs"));
			if(SqlUtil.columnIsExist(rs,"address")) fxdd.setAddress(rs.getString("address"));
			if(SqlUtil.columnIsExist(rs,"lxdh")) fxdd.setLxdh(rs.getString("lxdh"));
			if(SqlUtil.columnIsExist(rs,"lxr")) fxdd.setLxr(rs.getString("lxr"));			
			if(SqlUtil.columnIsExist(rs,"cx_tel")) fxdd.setCx_tel(rs.getString("cx_tel"));
			if(SqlUtil.columnIsExist(rs,"send_time")) fxdd.setSend_time(rs.getString("send_time"));
			if(SqlUtil.columnIsExist(rs,"job_no")) fxdd.setJob_no(rs.getString("job_no"));
			
			return fxdd;
		}
	}
	
	
	/**
	 * 包装对象(销售单产品)
	 * 
	 * @author liyt
	 * 
	 */
	class XsdProductRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			XsdProduct xsdProduct = new XsdProduct();

			if(SqlUtil.columnIsExist(rs,"id")) xsdProduct.setId(rs.getInt("id"));
			if(SqlUtil.columnIsExist(rs,"xsd_id")) xsdProduct.setXsd_id(rs.getString("xsd_id"));
			if(SqlUtil.columnIsExist(rs,"product_id")) xsdProduct.setProduct_id(rs.getString("product_id"));
			if(SqlUtil.columnIsExist(rs,"product_xh")) xsdProduct.setProduct_xh(rs.getString("product_xh"));
			if(SqlUtil.columnIsExist(rs,"product_name")) xsdProduct.setProduct_name(rs.getString("product_name"));
			if(SqlUtil.columnIsExist(rs,"price")) xsdProduct.setPrice(rs.getDouble("price"));
			if(SqlUtil.columnIsExist(rs,"nums")) xsdProduct.setNums(rs.getInt("nums"));
			if(SqlUtil.columnIsExist(rs,"xj")) xsdProduct.setXj(rs.getDouble("xj"));
			if(SqlUtil.columnIsExist(rs,"remark")) xsdProduct.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"jgtz")) xsdProduct.setJgtz(rs.getDouble("jgtz"));
			if(SqlUtil.columnIsExist(rs,"cbj")) xsdProduct.setCbj(rs.getDouble("cbj"));
			if(SqlUtil.columnIsExist(rs,"qz_serial_num")) xsdProduct.setQz_serial_num(rs.getString("qz_serial_num"));
			if(SqlUtil.columnIsExist(rs,"qz_flag")) xsdProduct.setQz_flag(rs.getString("qz_flag"));
			
			if(SqlUtil.columnIsExist(rs,"sjcj_nums")) xsdProduct.setSjcj_nums(rs.getInt("sjcj_nums"));
			if(SqlUtil.columnIsExist(rs,"sjcj_xj")) xsdProduct.setSjcj_xj(rs.getDouble("sjcj_xj"));
			if(SqlUtil.columnIsExist(rs,"kh_cbj")) xsdProduct.setKh_cbj(rs.getDouble("kh_cbj"));
			if(SqlUtil.columnIsExist(rs,"gf")) xsdProduct.setGf(rs.getDouble("gf"));
			
			if(SqlUtil.columnIsExist(rs,"dw")) xsdProduct.setDw(rs.getString("dw"));
			if(SqlUtil.columnIsExist(rs,"sd")) xsdProduct.setSd(rs.getDouble("sd"));
			if(SqlUtil.columnIsExist(rs,"ds")) xsdProduct.setDs(rs.getDouble("ds"));
			if(SqlUtil.columnIsExist(rs,"basic_ratio")) xsdProduct.setBasic_ratio(rs.getDouble("basic_ratio"));
			if(SqlUtil.columnIsExist(rs,"out_ratio")) xsdProduct.setOut_ratio(rs.getDouble("out_ratio"));
			if(SqlUtil.columnIsExist(rs,"lsxj")) xsdProduct.setLsxj(rs.getDouble("lsxj"));
			if(SqlUtil.columnIsExist(rs,"ygcbj")) xsdProduct.setYgcbj(rs.getDouble("ygcbj"));
			
			return xsdProduct;
		}
	}
}
