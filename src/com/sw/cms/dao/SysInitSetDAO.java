package com.sw.cms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.dao.base.JdbcBaseDAO;
import com.sw.cms.dao.base.SqlUtil;
import com.sw.cms.model.SysInitSet;

/**
 * <p>系统启用设置</p>
 * @author liyt
 *
 */
public class SysInitSetDAO extends JdbcBaseDAO {
	
	
	/**
	 * 设置系统启动日期
	 * @param sysInitSet
	 */
	public void setQyrq(SysInitSet sysInitSet){
		String sql = "delete from sys_init_set";
		this.getJdbcTemplate().update(sql);
		
		sql = "insert into sys_init_set(qyrq,czr,cz_date) values(?,?,now())";
		Object[] param = new Object[2];
		
		param[0] = sysInitSet.getQyrq();
		param[1] = sysInitSet.getCzr();
		
		this.getJdbcTemplate().update(sql,param);
	}
	
	
	/**
	 * 设置初始完成
	 * @param sysInitSet
	 */
	public void setCswc(SysInitSet sysInitSet){
		String sql = "select * from sys_init_set";
		List list = this.getResultList(sql);
		
		if(list.size() > 0){
			//记录存在则更新
			sql = "update sys_init_set set cswcrq=?,flag=?,czr=?,cz_date=now()";
			
			Object[] param = new Object[3];
			param[0] = sysInitSet.getCswcrq();
			param[1] = sysInitSet.getFlag();
			param[2] = sysInitSet.getCzr();
			
			this.getJdbcTemplate().update(sql,param);
		}else{
			//记录不存在则重新插入			
			sql = "insert inot sys_init_set(qyrq,cswcrq,flag,czr,cz_date) values(?,?,?,?,now())";
			
			Object[] param = new Object[4];
			param[0] = sysInitSet.getQyrq();
			param[1] = sysInitSet.getCswcrq();
			param[2] = sysInitSet.getFlag();
			param[3] = sysInitSet.getCzr();
			
			this.getJdbcTemplate().update(sql,param);
		}
	}
	
	
	/**
	 * <p>获取当前系统是否完成初始</p>
	 * @return flag 0:未完成初始；1:完成初始
	 */
	public String getQyFlag(){
		String flag = "0";
		String sql = "select flag from sys_init_set";
		List list = this.getResultList(sql);
		
		if(list.size() > 0){
			Map map = (Map)list.get(0);
			flag = (String)map.get("flag");
		}
		
		return flag;
	}
	
	
	/**
	 * 取系统初始化对象
	 * @return
	 */
	public SysInitSet getSysInitSet(){
		
		SysInitSet sysInitSet = new SysInitSet();
		
		String sql = "select * from sys_init_set";
		
		List list = this.getResultList(sql, new SysInitSetRowMapper());
		
		if(list.size() > 0){
			sysInitSet = (SysInitSet)list.get(0);
		}
		
		return sysInitSet;
	}
	
	
	/**
	 * 清空系统数据（全部数据都清空）
	 */
	public void updateSys_ClearData(){
		String[] sqls = new String[120];
		
		sqls[0] = "delete from xxfb_nbgg";   //内部公告
		sqls[1] = "delete from account_dzd"; //对账单
		sqls[2] = "delete from account_qc";  //账户期初
		sqls[3] = "delete from accounts where id<>'AD0001'";  //账户
		sqls[4] = "delete from bjd";  //报价单
		sqls[5] = "delete from bjd_desc";  //报价单名细
		sqls[6] = "delete from cgfk";  //采购付款
		sqls[7] = "delete from cgfk_desc";  //采购付款明细
		sqls[8] = "delete from cgthd";  //采购退货单
		sqls[9] = "delete from cgthd_product";  //采购退货单商品
		sqls[10] = "delete from chtj";  //存货调价
		sqls[11] = "delete from chtj_desc";  //存货调价明细
		sqls[12] = "delete from ckd";  //出库单
		sqls[13] = "delete from ckd_product";  //出库单商品
		sqls[14] = "delete from client_qc";  //客户期初
		sqls[15] = "delete from client_wl_init";  //客户往来初始
		sqls[16] = "delete from clients";  //往来客户
		sqls[17] = "update cms_all_seq set jhdid=1,gysid=1,cgyfkid=1,rkdid=1,kcid=1," +
				   "kcpdid=1,kfid=1,ckdid=1,clientid=1,xsdid=1,yskid=1,lsdid=1,userid=10," +
				   "roleid=2,pzid=1,thdid=1,dbsqid=1,kfdbid=1,chtjid=1,accountid=1," +
				   "qtsrid=1,qtzcid=1,nbzzid=1,cgthdid=1,lsyskid=1,lsthdid=1,yushoutoyingshouid=1," +
				   "yufutoyingfuid=1,fysqid=1,txfkid=1,txfkdescid=1,kcpdyk_id=1,linkman_id=1,bxdid=1," +
				   "cxdid=1,cnfkdid=1,zzdid=1,bfdid=1,hjdid=1,hykflid=1,hykzzid=1,hykdaid=1,zxgdid=1," +
				   "ykrkid=1,ykckid=1,jjdid=1,bxfhdid=1,fhkhdid=1,sfdid=1,pgdid=1"; //更新系统所有序列
		sqls[18] = "delete from dbsq";  //调拨申请
		sqls[19] = "delete from dbsq_product"; //调拨申请相关商品
		sqls[20] = "delete from fxdd";  //分销订单
		sqls[21] = "delete from jhd";  //进货单
		sqls[22] = "delete from jhd_product";  //进货单相关商品
		sqls[23] = "delete from kcpd";  //库存盘点
		sqls[24] = "delete from kcpd_desc";  //库存盘点明细
		sqls[25] = "delete from kfdb";  //库房调拨
		sqls[26] = "delete from kfdb_product";  //库房调拨相关商品
		sqls[27] = "delete from lsd";  //零售单
		sqls[28] = "delete from lsd_product";  //零售单相关商品
		sqls[29] = "delete from lsysk";  //零售预收款
		sqls[30] = "delete from nbzz";  //内部转账
		sqls[31] = "delete from product";  //商品表
		sqls[32] = "delete from product_kc";  //商品库存
		sqls[33] = "delete from product_kc_qc";  //商品库存期初
		sqls[34] = "delete from product_kind";  //商品类别
		sqls[35] = "delete from pz";  //往来调账（平账）
		sqls[36] = "delete from qtsr";  //其它收入
		sqls[37] = "delete from qtzc";  //其它支出
		sqls[38] = "delete from rkd";  //入库单
		sqls[39] = "delete from rkd_product";  //入库单相关商品
		sqls[40] = "delete from role where role_id<>'RL00000001'";  //角色
		sqls[41] = "delete from role_func where role_id<>'RL00000001'";  //角色功能表
		sqls[42] = "delete from serial_num_flow";  //序列号流转过程表
		sqls[43] = "delete from serial_num_mng";  //序列号管理表
		sqls[44] = "delete from storehouse where id<>'AD00000001'";  //仓库
		sqls[45] = "delete from sys_init_set";  //系统初始相关设置
		sqls[46] = "delete from sys_user where user_id<>'AD00000001'";  //系统用户表
		sqls[47] = "delete from thd";  //退货单
		sqls[48] = "delete from thd_product";  //退货单相关商品
		sqls[49] = "delete from user_role where user_id<>'AD00000001'";  //用户角色表
		sqls[50] = "delete from xsd";  //销售订单
		sqls[51] = "delete from xsd_product";  //销售订单相关商品
		sqls[52] = "delete from xssk";  //销售收款
		sqls[53] = "delete from xssk_desc";  //销售收款明细
		sqls[54] = "update accounts set qcje=0,dqje=0 where id='AD0001'";
		sqls[55] = "delete from fysq";  //费用申请
		sqls[56] = "delete from yufuk_list";  //预付款
		sqls[57] = "delete from yufu_to_yingfu"; //预付冲应付
		sqls[58] = "delete from yufu_to_yingfu_desc"; //预付冲应付明细
		sqls[59] = "delete from yushouk_list";  //预收款
		sqls[60] = "delete from yushou_to_yingshou"; //预收冲应收
		sqls[61] = "delete from yushou_to_yingshou_desc"; //预收冲应收明细
		sqls[62] = "delete from txfk";  //摊销付款
		sqls[63] = "delete from txfk_desc"; //摊销付款明细
		sqls[64] = "delete from cesp_right_roles";  //超额审批权限
		sqls[65] = "delete from clients_follow";    //客户跟进计划
		sqls[66] = "delete from clients_linkman";   //客户联系人
		sqls[67] = "delete from cqsp_right_roles";  //超期审批权限
		sqls[68] = "delete from jgsp_right_roles";  //低价审批权限
		sqls[69] = "delete from kcpd_yk_tbl";       //库存盘点盈余
		sqls[70] = "delete from linkman";           //短信联系人
		sqls[71] = "delete from lssd";              //零售税点
		sqls[72] = "delete from pos_type";          //POS机设置
		sqls[73] = "delete from product_kc_init";   //库存期初设置
		sqls[74] = "delete from send_msg_log";      //短信发送日志
		sqls[75] = "delete from sendsms";           //短信发送列表
		sqls[76] = "delete from sys_msg";           //系统消息
		sqls[77] = "delete from bxd";               //报修单
		sqls[78] = "delete from bxd_product";       //报修单商品
		sqls[79] = "delete from  bxfhd";            //报修返还单
		sqls[80] = "delete from  bxfhd_product";    //报修返还单商品
		sqls[81] = "delete from  fhkhd";            //返还客户单
		sqls[82] = "delete from  fhkhd_product";    //返还客户单商品
		sqls[83] = "delete from  jjd";              //接件单
		sqls[84] = "delete from  jjd_product";      //接件单商品
		sqls[85] = "delete from  sh_serial_num_flow ";//售后库存序列号流转记录
		sqls[86] = "delete from  shkc";               //售后库存
		sqls[87] = "delete from  wxrkd";             //维修入库单
		sqls[88] = "delete from  wxrkd_product";     //维修入库单商品
		sqls[89] = "delete from  sfd";                //售后服务单
		sqls[89] = "delete from  pgd";                //派工单	
		sqls[89] = "delete from  wxcld";                //维修处理单
		sqls[89] = "delete from  wxcld_product";        //维修处理单商品
		sqls[90] = "delete from  ykrk";
		sqls[91] = "delete from  ykrk_product";
		sqls[92] = "delete from  ykck";
		sqls[93] = "delete from  ykck_product";
		sqls[94] = "delete from cxd";                  //拆卸单
		sqls[95] = "delete from cxd_product";          //拆卸单明细
		sqls[96] = "delete from zzd";                  //组装单
		sqls[97] = "delete from zzd_product";          //组装单明细
		sqls[98] = "delete from sp_right";             //审批设置
		sqls[99] = "delete from cnfkd";                //出纳付款单
		sqls[100] = "delete from clients_pay_info";    //客户支付信息
		
		sqls[101] = "delete from calendar_plan";      //日程安排
		sqls[102] = "delete from cgfpd";              //采购发票单
		sqls[103] = "delete from bfd";                //报废单
		sqls[104] = "delete from bfd_product";        //报废单明细
		sqls[105] = "delete from bwl";                //备忘录
		sqls[106] = "delete from bwl_share";          //备忘录共享
		sqls[107] = "delete from hjd";                //
		sqls[108] = "delete from hjd_product";
		sqls[109] = "delete from hykda";              //会员卡档案
		sqls[110] = "delete from hykfl";              //会员卡分类
		sqls[111] = "delete from hykzz";              //会员卡制做
		sqls[112] = "delete from jfgz";               //积分规则
		sqls[113] = "delete from mail_set";           //邮件设置
		sqls[114] = "delete from tcbl_set";           //提成比例设置
		sqls[115] = "delete from zxgd";               //咨询工单
		sqls[116] = "delete from zzd";                //咨询单
		sqls[117] = "delete from zzd_product";        //咨询单明细
	    
		sqls[118] = "delete from dept";              //部门
		sqls[119] = "delete from fy_type where parent_id<>'00'";           //费用类型
		
		this.getJdbcTemplate().batchUpdate(sqls);
	}
	
	
	/**
	 * 清空系统数据（只清空业务数据）
	 */
	public void updateSys_ClearYwData(){
		String[] sqls = new String[98];
		
		sqls[0] = "delete from xxfb_nbgg";   //内部公告
		sqls[1] = "delete from account_dzd"; //对账单
		sqls[2] = "delete from account_qc";  //账户期初
		sqls[3] = "update accounts set qcje=0,dqje=0";  //账户
		sqls[4] = "delete from bjd";  //报价单
		sqls[5] = "delete from bjd_desc";  //报价单名细
		sqls[6] = "delete from cgfk";  //采购付款
		sqls[7] = "delete from cgfk_desc";  //采购付款明细
		sqls[8] = "delete from cgthd";  //采购退货单
		sqls[9] = "delete from cgthd_product";  //采购退货单商品
		sqls[10] = "delete from chtj";  //存货调价
		sqls[11] = "delete from chtj_desc";  //存货调价明细
		sqls[12] = "delete from ckd";  //出库单
		sqls[13] = "delete from ckd_product";  //出库单商品
		sqls[14] = "delete from client_qc";  //客户期初
		sqls[15] = "delete from client_wl_init";  //客户往来初始
		sqls[16] = "update cms_all_seq set jhdid=1,gysid=1,cgyfkid=1,rkdid=1,kcid=1," +
				   "kcpdid=1,ckdid=1,xsdid=1,yskid=1,lsdid=1," +
				   "pzid=1,thdid=1,dbsqid=1,kfdbid=1,chtjid=1," +
				   "qtsrid=1,qtzcid=1,nbzzid=1,cgthdid=1,lsyskid=1,lsthdid=1,yushoutoyingshouid=1," +
				   "yufutoyingfuid=1,fysqid=1,txfkid=1,txfkdescid=1,kcpdyk_id=1,bxdid=1," +
				   "cxdid=1,cnfkdid=1,zzdid=1,bfdid=1,hjdid=1,hykflid=1,hykzzid=1,hykdaid=1,zxgdid=1," +
				   "ykrkid=1,ykckid=1,jjdid=1,bxfhdid=1,fhkhdid=1,sfdid=1,pgdid=1"; //更新系统所有序列
		sqls[17] = "delete from dbsq";  //调拨申请
		sqls[18] = "delete from dbsq_product"; //调拨申请相关商品
		sqls[19] = "delete from fxdd";  //分销订单
		sqls[20] = "delete from jhd";  //进货单
		sqls[21] = "delete from jhd_product";  //进货单相关商品
		sqls[22] = "delete from kcpd";  //库存盘点
		sqls[23] = "delete from kcpd_desc";  //库存盘点明细
		sqls[24] = "delete from kfdb";  //库房调拨
		sqls[25] = "delete from kfdb_product";  //库房调拨相关商品
		sqls[26] = "delete from lsd";  //零售单
		sqls[27] = "delete from lsd_product";  //零售单相关商品
		sqls[28] = "delete from lsysk";  //零售预收款
		sqls[29] = "delete from nbzz";  //内部转账
		sqls[30] = "delete from product_kc";  //商品库存
		sqls[31] = "delete from product_kc_qc";  //商品库存期初
		sqls[32] = "delete from pz";  //往来调账（平账）
		sqls[33] = "delete from qtsr";  //其它收入
		sqls[34] = "delete from qtzc";  //其它支出
		sqls[35] = "delete from rkd";  //入库单
		sqls[36] = "delete from rkd_product";  //入库单相关商品
		sqls[37] = "delete from serial_num_flow";  //序列号流转过程表
		sqls[38] = "delete from serial_num_mng";  //序列号管理表
		sqls[39] = "delete from sys_init_set";  //系统初始相关设置
		sqls[40] = "delete from thd";  //退货单
		sqls[41] = "delete from thd_product";  //退货单相关商品
		sqls[42] = "delete from xsd";  //销售订单
		sqls[43] = "delete from xsd_product";  //销售订单相关商品
		sqls[44] = "delete from xssk";  //销售收款
		sqls[45] = "delete from xssk_desc";  //销售收款明细
		sqls[46] = "delete from fysq";  //费用申请
		sqls[47] = "delete from yufuk_list";  //预付款
		sqls[48] = "delete from yufu_to_yingfu"; //预付冲应付
		sqls[49] = "delete from yufu_to_yingfu_desc"; //预付冲应付明细
		sqls[50] = "delete from yushouk_list";  //预收款
		sqls[51] = "delete from yushou_to_yingshou"; //预收冲应收
		sqls[52] = "delete from yushou_to_yingshou_desc"; //预收冲应收明细
		sqls[53] = "delete from txfk";  //摊销付款
		sqls[54] = "delete from txfk_desc"; //摊销付款明细
		sqls[55] = "delete from clients_follow";    //客户跟进计划
		sqls[56] = "delete from kcpd_yk_tbl";       //库存盘点盈余
		sqls[57] = "delete from linkman";           //短信联系人
		sqls[58] = "delete from product_kc_init";   //库存期初设置
		sqls[59] = "delete from send_msg_log";      //短信发送日志
		sqls[60] = "delete from sendsms";           //短信发送列表
		sqls[61] = "delete from sys_msg";           //系统消息
		sqls[62] = "delete from bxd";               //报修单
		sqls[63] = "delete from bxd_product";       //报修单商品
		sqls[64] = "delete from  bxfhd";            //报修返还单
		sqls[65] = "delete from  bxfhd_product";    //报修返还单商品
		sqls[66] = "delete from  fhkhd";            //返还客户单
		sqls[67] = "delete from  fhkhd_product";    //返还客户单商品
		sqls[68] = "delete from  jjd";              //接件单
		sqls[69] = "delete from  jjd_product";      //接件单商品
		sqls[70] = "delete from  sh_serial_num_flow ";//售后库存序列号流转记录
		sqls[71] = "delete from  shkc";               //售后库存
		sqls[72] = "delete from  wxrkd";             //维修入库单
		sqls[73] = "delete from  wxrkd_product";     //维修入库单商品
		sqls[74] = "delete from  sfd";                //售后服务单
		sqls[75] = "delete from  pgd";                //派工单	
		sqls[76] = "delete from  wxcld";                //维修处理单
		sqls[77] = "delete from  wxcld_product";        //维修处理单商品
		sqls[78] = "delete from  ykrk";
		sqls[79] = "delete from  ykrk_product";
		sqls[80] = "delete from  ykck";
		sqls[81] = "delete from  ykck_product";
		sqls[82] = "delete from cxd";                  //拆卸单
		sqls[83] = "delete from cxd_product";          //拆卸单明细
		sqls[84] = "delete from zzd";                  //组装单
		sqls[85] = "delete from zzd_product";          //组装单明细
		sqls[86] = "delete from cnfkd";                //出纳付款单
		
		sqls[87] = "delete from calendar_plan";      //日程安排
		sqls[88] = "delete from cgfpd";              //采购发票单
		sqls[89] = "delete from bfd";                //报废单
		sqls[90] = "delete from bfd_product";        //报废单明细
		sqls[91] = "delete from bwl";                //备忘录
		sqls[92] = "delete from bwl_share";          //备忘录共享
		sqls[93] = "delete from hjd";                //
		sqls[94] = "delete from hjd_product";
		sqls[95] = "delete from zxgd";               //咨询工单
		sqls[96] = "delete from zzd";                //咨询单
		sqls[97] = "delete from zzd_product";        //咨询单明细
	    
		this.getJdbcTemplate().batchUpdate(sqls);
	}
	
	
	/**
	 * 判断是否存在负库存
	 * @return
	 */
	public boolean isExistFkc(){
		boolean is = false;
		String sql = "SELECT a.*,b.prop FROM product_kc a JOIN product b on b.product_id=a.product_id where b.prop='库存商品' and a.nums<0";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size() > 0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * 返回系统LOGO设置
	 * @return
	 */
	public Map getSysLogo(){
		String sql = "select * from logo_set";
		return this.getResultMap(sql);
	}
	
	
	/**
	 * 保存系统LOGO设置
	 * @param cpy_name
	 * @param logo_url
	 */
	public void saveSysLogo(String cpy_name,String logo_url){
		String sql = "update logo_set set cpy_name='" + cpy_name + "',logo_url='" + logo_url + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 返回报表相关设置
	 * @return
	 */
	public Map getReportSet(){
		String sql = "select * from report_set";
		return this.getResultMap(sql);
	}
	
	
	/**
	 * 保存报表相关设置
	 * @param title_name
	 * @param foot_name
	 */
	public void saveReportSet(String title_name,String foot_name){
		String sql = "update report_set set title_name='" + title_name + "',foot_name='" + foot_name + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * 包装对象
	 * 
	 * @author liyt
	 * 
	 */
	class SysInitSetRowMapper implements RowMapper {
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			SysInitSet sysInitSet = new SysInitSet();

			if(SqlUtil.columnIsExist(rs,"id")) sysInitSet.setId(rs.getInt("id"));
			if(SqlUtil.columnIsExist(rs,"qyrq")) sysInitSet.setQyrq(rs.getString("qyrq"));
			if(SqlUtil.columnIsExist(rs,"cswcrq")) sysInitSet.setCswcrq(rs.getString("cswcrq"));
			if(SqlUtil.columnIsExist(rs,"flag")) sysInitSet.setFlag(rs.getString("flag"));
			if(SqlUtil.columnIsExist(rs,"czr")) sysInitSet.setCzr(rs.getString("czr"));
			
			return sysInitSet;
		}
	}
	
}
