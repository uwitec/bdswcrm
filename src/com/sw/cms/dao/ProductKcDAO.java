package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.SysInitSetDAO.SysInitSetRowMapper;
import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.Page;
import com.sw.cms.model.Product;
import com.sw.cms.model.ProductKc;
import com.sw.cms.model.Rkd;
import com.sw.cms.model.RkdProduct;
import com.sw.cms.model.SysInitSet;
import com.sw.cms.model.Ykrk;
import com.sw.cms.model.YkrkProduct;
import com.sw.cms.util.StringUtils;

public class ProductKcDAO extends JdbcBaseDAO {
	
	
	/**
	 * 根据查询条件查询库存列表（带分页）
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProductKcList(String con,int curPage, int rowsPerPage){
		
		String sql = "select a.product_id, a.product_name,a.product_xh,a.gysmc,a.price,a.fxxj,a.lsxj,a.khcbj,a.prop,a.dw,a.lsbj,a.fxbj,a.qz_serial_num,a.gf,a.dss,a.ygcbj,a.sfcytc,(select sum(nums) from product_kc b where b.product_id=a.product_id) as kc_nums from product a where 1=1";
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * 根据查询条件查询库存初始值
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getProductInitList(String con,String store_id,int curPage, int rowsPerPage){
		//取系统启用日期
		String qyrqSql = "select * from sys_init_set";
		List list = this.getResultList(qyrqSql);
		String qyrq = "";
		if(list.size() > 0){
			Map qyrqMap = (Map)list.get(0);
			if(qyrqMap != null){
				qyrq = StringUtils.nullToStr(qyrqMap.get("qyrq"));
			}
		}
		
		if(qyrq.equals("")){
			//如果没有设置启用日期，返回空；
			return new Page();
		}
		
		//没有保存仓库信息列表为空
		if(store_id == null || store_id.equals("")){
			return new Page();
		}
		
		String sql = "select a.product_id,a.product_name,a.product_xh,a.dw,a.qz_serial_num,b.cdate as init_date,b.nums as init_nums from product a " +
				     "left join " +
				     "(select cdate,nums,store_id,product_id from product_kc_qc where cdate='" + qyrq + "' and store_id='" + store_id + "') b " +
				     "on b.product_id=a.product_id " +
				     "where a.prop='库存商品'";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * <p>根据查询条件取库存列表</p>
	 * <p>分销商专用，不显示库存数量</p>
	 * @param con
	 * @return
	 */
	public Page getFxddProductKcList(String con,int curPage, int rowsPerPage){
		String sql = "select a.product_id, a.product_name,a.product_xh,a.fxbj,a.fxxj,a.dw from product a  where (select sum(nums) from product_kc b where b.product_id=a.product_id)>0 and a.prop='库存商品' and a.state='正常'";
		
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage, new ProductKcRowMapper());
	}
	
	
	/**
	 * 返回某一商品的库存情况
	 * @param product_id
	 * @return
	 */
	public int getProductKcByProductId(String product_id,String store_id){
		
		int nums = 0;
		
		String sql = "select * from product_kc where product_id='" + product_id + "' and store_id='" + store_id + "'";
		
		List list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size()>0){
			Map map = (Map)list.get(0);
			String strNums = StringUtils.nullToStr(map.get("nums"));
			if(!strNums.equals("")){
				nums = new Integer(strNums).intValue();
			}
		}
		
		return nums;
	}
	
	
	/**
	 * 返回某一商品的库存情况
	 * @param product_id
	 * @return
	 */
	public HashMap getProductKcByProductId(String product_id){
		
		HashMap map = new HashMap();
		
		String sql = "select * from product_kc where product_id='" + product_id + "'";
		
		List list = this.getJdbcTemplate().queryForList(sql);
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Map kcMap = (Map)list.get(i);
				String key = (String)kcMap.get("store_id");
				map.put(key, kcMap.get("nums"));
			}
		}
		return map;
	}
	
	
	
	/**
	 * 取所有库存商品列表
	 * @param con
	 * @return
	 */
	public Page getAllProductKc(String con,int curPage, int rowsPerPage){
		String sql = "select a.product_id,b.product_name,b.product_xh,b.product_kind,b.price,b.fxxj,b.lsxj,a.store_id,a.nums,b.state from product_kc a left join product b on b.product_id=a.product_id where 1=1";
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * 取所有库存商品列表（包括零库存商品）<BR>
	 * 库存盘点时调用，其它调用会有问题
	 * @param con
	 * @param curPage
	 * @param rowsPerPage
	 * @return
	 */
	public Page getAllProductKcIncludeZero(String con,String store_id,int curPage, int rowsPerPage){
		String sql = "select a.product_id,a.product_name,a.product_xh,a.product_kind,a.price,a.fxxj,a.lsxj,a.state,a.qz_serial_num,(select nums from product_kc b where b.product_id=a.product_id and b.store_id='" + store_id + "') as nums from product a where a.state='正常' and prop='库存商品'";
		if(!con.equals("")){
			sql = sql + con;
		}
		
		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	/**
	 * 初始化库存
	 * @param product_id
	 * @param store_id
	 * @param nums
	 */
	public void saveProductKc(String product_id,String store_id,String nums){
	
		String sql = "delete from product_kc where product_id='" + product_id + "' and store_id='" + store_id + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "insert into product_kc (product_id,store_id,nums) values('"+ product_id + "','" + store_id + "'," + nums + ")";
		this.getJdbcTemplate().update(sql);

	}

	
	/**
	 * 库存初始时插入序列号
	 * @param product_id
	 * @param serial_num
	 */
	public void insertSerialNum(String product_id,String store_id,String serial_num){
		String sql = "insert into serial_num_mng(select 0 as seq_id,'" + serial_num + "' as serial_num,product_id,product_name,product_xh,'在库' as state,'" + store_id + "' as store_id,'0' as yj_flag from product where product_id='" + product_id + "')";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 强制序列号商品初始库存操作
	 * 序列号存在则更新，不存在则添加
	 * @param product
	 * @param store_id
	 * @param serial_num
	 */
	public void updateSerialNum(Product product,String store_id,String serial_num){
		String sql = "select * from serial_num_mng where serial_num='" + serial_num + "'";
		List list = this.getResultList(sql);
		
		if(list != null && list.size() > 0){
			//序列号存在更新
			sql = "update serial_num_mng set product_id='" + product.getProductId() + "',product_name='" + product.getProductName() + "',product_xh='" + product.getProductXh() + "',store_id='" + store_id + "',state='在库',yj_flag='0' where serial_num='" + serial_num + "'";
			this.getJdbcTemplate().update(sql);
		}else{
			//序列号不存在保存
			sql = "insert into serial_num_mng(product_id,product_name,product_xh,store_id,state,yj_flag,serial_num) " +
					"values('" + product.getProductId() + "','" + product.getProductName() + "','" + product.getProductXh() + "','" + store_id + "','在库','0','" + serial_num + "')";
			this.getJdbcTemplate().update(sql);
		}
		
	}
	
	
	
	/**
	 * 根据入库单及入库单商品列表更新商品库存
	 * 成本价采用加权平均算法
	 * @param RkdProducts
	 */
	public void updateProductKc(Rkd rkd,List rkdProducts){
		String sql = "";	
		
		if(rkdProducts != null && rkdProducts.size()>0){
			for(int i =0;i<rkdProducts.size();i++){
				
				RkdProduct rkdProduct = (RkdProduct)rkdProducts.get(i);				
				String product_id = rkdProduct.getProduct_id();	 //入库商品编号
				
				if(!product_id.equals("")){
					
					sql = "select * from product where product_id='"+ product_id +"'";
					Map productMap = this.getResultMap(sql);
					
					String prop = StringUtils.nullToStr(productMap.get("prop"));
					
					//只有库存商品才更新成本价及库存数量
					if(prop.equals("库存商品")){
						
						//当前商品总库存数
						int nums = 0;
						sql = "select sum(nums) as nums from product_kc where product_id='" + product_id + "'";	
						Map map = this.getResultMap(sql);
						String strNums = StringUtils.nullToStr(map.get("nums"));
						if(!strNums.equals("")){
							nums = (new Integer(strNums)).intValue();
						}
						
						//当前库存成本价
						double price = 0; 
						sql = "select price from product where product_id='" + product_id + "'";
						Map priceMap = this.getResultMap(sql);
						if(priceMap != null){
							price = priceMap.get("price")==null?0:((Double)priceMap.get("price")).doubleValue();
						}
						
						double dqkczz = price * nums;  //当前库存总值
						
						int rk_product_nums = rkdProduct.getNums();        //入库商品数量
						//double rk_product_price = rkdProduct.getPrice();   //入库商品价格
						
						//double rkzz = rk_product_nums * rk_product_price;  //入库商品总值
						double rkzz = rkdProduct.getBhsje();   //入库商品总值(不含税金额)
						
						//采用加权平均后的成本价
						double cbj = price;						
						if(nums + rk_product_nums != 0){   //只有在入库数+库存数不为0的情况下，计算成本价
							cbj = (dqkczz + rkzz)/(nums + rk_product_nums);
						}
						
						String store_id = rkd.getStore_id();   //入库仓库
						
						//判断要入库的仓库中是否有该商品
						sql = "select  count(*) as allcount from product_kc where product_id='" + product_id + "' and store_id='" + store_id + "'";				
						int counts = this.getJdbcTemplate().queryForInt(sql);
						
						if(counts > 0){ 
							// 该仓库中有该商品，需更新库存数量
							sql = "update product_kc set nums=nums+" + rkdProduct.getNums() + " where product_id='" + product_id + "' and store_id='" + store_id + "'";
							this.getJdbcTemplate().update(sql);
							
						}else{   
							//库存中没有该商品，添加即可
							sql = "insert into product_kc(store_id,product_id,nums) values(?,?,?)";
							Object[] param = new Object[3];
						
							param[0] = rkd.getStore_id();
							param[1] = rkdProduct.getProduct_id();
							param[2] = new Integer(rkdProduct.getNums());
							
							this.getJdbcTemplate().update(sql,param);
						}
						
						//更新商品成本价
						sql = "update product set price=" + cbj + " where product_id='" + product_id + "'";						
						this.getJdbcTemplate().update(sql);	
					}
				}
			}
		}
	}
	
	
	public void updateProductKcDOA(Ykrk ykrk,YkrkProduct ykrkProduct)
	{
	    String	sql = "select  count(*) as allcount from product_kc where product_id='" +ykrkProduct.getProduct_id()  + "' and store_id='" + ykrk.getRk_store_id() + "'";				
		int counts = this.getJdbcTemplate().queryForInt(sql);
		
		if(counts > 0){ 
			// 该仓库中有该商品，需更新库存数量
			sql = "update product_kc set nums=nums+1 where product_id='" + ykrkProduct.getProduct_id() + "' and store_id='" + ykrk.getRk_store_id() + "'";
			this.getJdbcTemplate().update(sql);
			
		}else{   
			//库存中没有该商品，添加即可
			sql = "insert into product_kc(store_id,product_id,nums) values(?,?,?)";
			Object[] param = new Object[3];
		
			param[0] =ykrk.getRk_store_id();
			param[1] = ykrkProduct.getProduct_id();
			param[2] = new Integer(1);
			
			this.getJdbcTemplate().update(sql,param);
		}
	}
	
	/**
	 * 取商品最后一次入库时的成本价
	 * @param product_id
	 * @return
	 */
	public double getLastProductRkCbj(String product_id){
		double cbj = 0;
		
		String sql = "select a.price from rkd_product a  left join rkd b on b.rkd_id=a.rkd_id where a.product_id='" + product_id + "' order by b.cz_date desc";
		
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			Map map = (Map)list.get(0);
			
			cbj = map.get("price")==null?0:((Double)map.get("price")).doubleValue();
		}
		
		return cbj;
	}
	
	
	
	/**
	 * 减少商品库存
	 * @param store_id
	 * @param product_id
	 * @param nums
	 */
	public void updateProductKc(String store_id,String product_id,int nums){		
		//只有库存商品才会修改库存值
		String sql = "select count(*) from product where prop='库存商品' and product_id='" + product_id + "'";
		int tempVl = this.getJdbcTemplate().queryForInt(sql);
		if(tempVl > 0){
			sql = "update product_kc set nums=nums-" + nums + " where product_id='" + product_id + "' and store_id='" + store_id + "'";
			int returnValue = this.getJdbcTemplate().update(sql);
			
			if(returnValue<1){ //没有更新成功，出现原因为商品库存不存在，需要重新插入
				sql = "insert into product_kc(product_id,store_id,nums) values('" +  product_id + "','" + store_id + "'," + (0-nums) + ")";
				this.getJdbcTemplate().update(sql);
			}
		}
	}
	
	
	/**
	 * 加商品库存
	 * @param store_id
	 * @param product_id
	 * @param nums
	 */
	public void addProductKc(String store_id,String product_id,int nums){
		
		//只有库存商品才会修改库存值
		String sql = "select count(*) from product where prop='库存商品' and product_id='" + product_id + "'";
		int tempVl = this.getJdbcTemplate().queryForInt(sql);
		if(tempVl > 0){
			sql = "update product_kc set nums=nums+" + nums + " where product_id='" + product_id + "' and store_id='" + store_id + "'";
			int returnValue = this.getJdbcTemplate().update(sql);
			
			if(returnValue<1){ //没有更新成功，出现原因为商品库存不存在，需要重新插入
				sql = "insert into product_kc(product_id,store_id,nums) values('" +  product_id + "','" + store_id + "'," + nums + ")";
				this.getJdbcTemplate().update(sql);
			}			
		}
	}
	
	
	/**
	 * 根据库存商品返回是否存是该库存
	 * @param productKc
	 * @return
	 */
	public int getKcCounts(ProductKc productKc){
		String sql = "select count(*) as allcount from product_kc where product_id='" + productKc.getProduct_id() + "' and store_id='" + productKc.getStore_id() + "'";
		
		return this.getJdbcTemplate().queryForInt(sql);
	}
	
	
	/**
	 * 返回某一商品库存数
	 * @param product_id
	 * @param store_id
	 * @return
	 */
	public int getKcNums(String product_id,String store_id){
		int nums = 0;
		String sql = "select sum(nums) as nums from product_kc where 1=1";
		if(!product_id.equals("")){
			sql += " and product_id='" + product_id + "'";
		}
		if(!store_id.equals("")){
			sql += " and store_id='" + store_id + "'";
		}
		
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			Map map = (Map)list.get(0);
			String strNum = StringUtils.nullToStr(map.get("nums"));
			
			if(!strNum.equals("")){
				nums = Integer.parseInt(strNum);
			}
		}
		return nums;
	}
	
		
	/**
	 * 返回某一商品在好件库的库存数(强制序列号)
	 * @param product_id
	 * 
	 * @return
	 */
	public int getHaoKcNums(String product_id){
		int nums = 0;
		
		String sqlStore=""; 
		sqlStore= "select id from storehouse where name='好件库'";		
		Map mapStore=getResultMap(sqlStore);		
		String storeId= (String)mapStore.get("id") ;
		  
		String sql = "select sum(nums) as nums from shkc where 1=1";
		if(!product_id.equals("")){
			sql += " and product_id='" + product_id + "'";
		}
		if(!storeId.trim().equals("")){
			sql += " and store_id='" + storeId + "' and state='3'";
		}
		
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			Map map = (Map)list.get(0);
			String strNum = StringUtils.nullToStr(map.get("nums"));
			
			if(!strNum.equals("")){
				nums = Integer.parseInt(strNum);
			}
		}
		return nums;
	}
	
	/**
	 * 返回某一商品在好件库的库存数
	 * @param product_id
	 * 
	 * @return
	 */
	public int getHaoKcNumsNqz(String product_id){
		int nums = 0;
		
		String sqlStore=""; 
		sqlStore= "select id from storehouse where name='好件库'";		
		Map mapStore=getResultMap(sqlStore);		
		String storeId= (String)mapStore.get("id") ;
		  
		String sql = "select nums as nums from shkc where 1=1";
		if(!product_id.equals("")){
			sql += " and product_id='" + product_id + "'";
		}
		if(!storeId.trim().equals("")){
			sql += " and store_id='" + storeId + "' and state='3'";
		}
		
		List list = this.getResultList(sql);
		if(list != null && list.size() > 0){
			Map map = (Map)list.get(0);
			String strNum = StringUtils.nullToStr(map.get("nums"));
			
			if(!strNum.equals("")){
				nums = Integer.parseInt(strNum);
			}
		}
		return nums;
	}
	/**
	 * 返回库存查询明细结果
	 * @param product_id
	 * @param product_name
	 * @param product_xh
	 * @return
	 */
	public Page getProductKcMx(String kc_con,int curPage,int rowsPerPage){
		kc_con = ((kc_con.replace("　", " ")).replace(",", "")).replace("，", " ");
		String[] arryCon = kc_con.split(" ");
		String sql = "SELECT a.*,(select sum(nums) from product_kc b where b.product_id=a.product_id) as nums from product a  where a.state='正常' and a.prop='库存商品'";
		
		String tempSql = "";
		if(arryCon != null && arryCon.length > 0){
			sql += " and(";
			for(int i=0;i<arryCon.length;i++){
				if(tempSql.equals("")){
					tempSql = "(a.product_id like '%" + arryCon[i] + "%' or a.product_name like '%" + arryCon[i] + "%' or a.product_xh like '%" + arryCon[i] + "%' or a.sp_txm like '%" + arryCon[i] + "%')";
				}else{
					tempSql = tempSql + " and (a.product_id like '%" + arryCon[i] + "%' or a.product_name like '%" + arryCon[i] + "%' or a.product_xh like '%" + arryCon[i] + "%' or a.sp_txm like '%" + arryCon[i] + "%')";
				}
			}
			sql = sql + tempSql + ")";
		}

		return this.getResultByPage(sql, curPage, rowsPerPage);
	}
	
	
	public Page getProductKcMxWts(String kc_con,int curPage,int rowsPerPage){
		String sql = "SELECT a.*,(select sum(nums) from product_kc b where b.product_id=a.product_id) as nums from product a  where state='正常'";
		
		if(!kc_con.equals("")){
			sql = sql + " and( a.product_id like '%" + kc_con + "%' or a.product_name like '%" + kc_con + "%' or a.product_xh like '%" + kc_con + "%' or a.sp_txm like '%" + kc_con + "%')";
		}

		return this.getResultByPage(sql, curPage, rowsPerPage);		
	}
	
	
	/**
	 * 生成库房库存期初
	 * @param product_id
	 * @param store_id
	 * @param cdate
	 */
	public void genKcqc(String product_id,String cdate){
		String sql = "delete from product_kc_qc where product_id='" + product_id + "' and cdate='" + cdate + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "insert into product_kc_qc (select 0 as seq_id,a.product_id,a.store_id,a.nums,'" + cdate + "' as cdate,b.price as price from product_kc a " +
				"left join product b on b.product_id=a.product_id where a.product_id='" + product_id + "')";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 生成库存期初值
	 * @param product_id
	 * @param cdate
	 * @param store_id
	 * @param nums
	 */
	public void genKcqc(String product_id,String cdate,String store_id,int nums){
		String sql = "delete from product_kc_qc where product_id='" + product_id + "' and store_id='" + store_id + "' and cdate='" + cdate + "'";
		this.getJdbcTemplate().update(sql);
		
		sql = "insert into product_kc_qc(product_id,store_id,nums,cdate,price) values(?,?,?,?,(select price from product where product_id=?))";
		
		Object[] param = new Object[5];
		param[0] = product_id;
		param[1] = store_id;
		param[2] = nums;
		param[3] = cdate;
		param[4] = product_id;
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 取某一天某一仓库某一商品的出库数量
	 * @param cdate
	 * @return
	 */
	public int getCkNums(String product_id,String store_id,String cdate){
		int nums = 0;
		
		String sql = "select sum(a.nums) as nums from ckd_product a left join ckd b on b.ckd_id=a.ckd_id " +
				"where b.state='已出库' and a.product_id='" + product_id + "' and b.store_id='" + store_id + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')='" + cdate + "'";

		List list = this.getResultList(sql);
		
		if(list != null && list.size() > 0){
			Map map = (Map)list.get(0);
			String strNum = StringUtils.nullToStr(map.get("nums"));
			
			if(!strNum.equals("")){
				nums = Integer.parseInt(strNum);
			}
		}
		
		sql = "select sum(a.nums) as nums from kfdb_product a left join kfdb b on b.id=a.kfdb_id " +
				"where b.state='已出库' and a.product_id='" + product_id + "' and b.ck_store_id='" + store_id + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')='" + cdate + "'";
		list = this.getResultList(sql);
		
		if(list != null && list.size() > 0){
			Map map = (Map)list.get(0);
			String strNum = StringUtils.nullToStr(map.get("nums"));
			
			if(!strNum.equals("")){
				nums += Integer.parseInt(strNum);
			}
		}
		
		return nums;
	}

	
	/**
	 * 取某一天某一仓库某一商品的入库数量
	 * 时间取实际入库的操作时间
	 * @param cdate
	 * @return
	 */
	public int getRkNums(String product_id,String store_id,String cdate){
		int nums = 0;
		
		String sql = "select sum(a.nums) as nums from rkd_product a left join rkd b on b.rkd_id=a.rkd_id " +
					"where a.product_id='" + product_id + "' and b.store_id='" + store_id + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')='" + cdate + "'";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size() > 0){
			Map map = (Map)list.get(0);
			String strNum = StringUtils.nullToStr(map.get("nums"));
			
			if(!strNum.equals("")){
				nums = Integer.parseInt(strNum);
			}
		}
		
		sql = "select sum(a.nums) as nums from kfdb_product a left join kfdb b on b.id=a.kfdb_id " +
				"where b.state='已出库' and a.product_id='" + product_id + "' and b.rk_store_id='" + store_id + "' and DATE_FORMAT(b.cz_date,'%Y-%m-%d')='" + cdate + "'";
		list = this.getResultList(sql);
		
		if(list != null && list.size() > 0){
			Map map = (Map)list.get(0);
			String strNum = StringUtils.nullToStr(map.get("nums"));
			
			if(!strNum.equals("")){
				nums += Integer.parseInt(strNum);
			}
		}
		
		return nums;
	}
	
	
	/**
	 * 更新商品库存数量
	 * @param product_id
	 * @param store_id
	 * @param nums
	 */
	public void updateProductKcNums(String product_id,String store_id,int nums){
		
		String sql = "select count(*) from product_kc where product_id='" + product_id + "' and store_id='" + store_id + "'";
		
		if(this.getJdbcTemplate().queryForInt(sql) > 0){
			sql = "update product_kc set nums=" + nums + " where product_id='" + product_id + "' and store_id='" + store_id + "'";
		}else{
			sql = "insert into product_kc(product_id,store_id,nums) values('" + product_id + "','" + store_id + "'," + nums + ")";
		}
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 包装对象(商品库存)
	 * 
	 * @author liyt
	 * 
	 */
	class ProductKcRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			ProductKc productKc = new ProductKc();
			
			if(SqlUtil.columnIsExist(rs,"kc_id")) productKc.setKc_id(rs.getString("kc_id"));
			if(SqlUtil.columnIsExist(rs,"product_id")) productKc.setProduct_id(rs.getString("product_id"));
			if(SqlUtil.columnIsExist(rs,"store_id")) productKc.setStore_id(rs.getString("store_id"));
			if(SqlUtil.columnIsExist(rs,"product_xh")) productKc.setProduct_xh(rs.getString("product_xh"));
			if(SqlUtil.columnIsExist(rs,"product_name")) productKc.setProduct_name(rs.getString("product_name"));
			if(SqlUtil.columnIsExist(rs,"nums")) productKc.setNums(rs.getInt("nums"));
			if(SqlUtil.columnIsExist(rs,"price")) productKc.setPrice(rs.getDouble("price"));
			if(SqlUtil.columnIsExist(rs,"fxxj")) productKc.setFxxj(rs.getDouble("fxxj"));
			if(SqlUtil.columnIsExist(rs,"fxbj")) productKc.setFxbj(rs.getDouble("fxbj"));
			if(SqlUtil.columnIsExist(rs,"lsxj")) productKc.setLsxj(rs.getDouble("lsxj"));
			if(SqlUtil.columnIsExist(rs,"gf")) productKc.setGf(rs.getInt("gf"));
			if(SqlUtil.columnIsExist(rs,"remark")) productKc.setRemark(rs.getString("remark"));
			if(SqlUtil.columnIsExist(rs,"flag")) productKc.setFlag(rs.getString("flag"));
			if(SqlUtil.columnIsExist(rs,"gxrq")) productKc.setGxrq(rs.getDate("gxrq"));
			if(SqlUtil.columnIsExist(rs,"prop")) productKc.setProp(rs.getString("prop"));
			if(SqlUtil.columnIsExist(rs,"dw")) productKc.setDw(rs.getString("dw"));
			
			return productKc;
		}
	}

}
