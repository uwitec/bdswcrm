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
 * <p>ϵͳ��������</p>
 * @author liyt
 *
 */
public class SysInitSetDAO extends JdbcBaseDAO {
	
	
	/**
	 * ����ϵͳ��������
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
	 * ���ó�ʼ���
	 * @param sysInitSet
	 */
	public void setCswc(SysInitSet sysInitSet){
		String sql = "select * from sys_init_set";
		List list = this.getResultList(sql);
		
		if(list.size() > 0){
			//��¼���������
			sql = "update sys_init_set set cswcrq=?,flag=?,czr=?,cz_date=now()";
			
			Object[] param = new Object[3];
			param[0] = sysInitSet.getCswcrq();
			param[1] = sysInitSet.getFlag();
			param[2] = sysInitSet.getCzr();
			
			this.getJdbcTemplate().update(sql,param);
		}else{
			//��¼�����������²���			
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
	 * <p>��ȡ��ǰϵͳ�Ƿ���ɳ�ʼ</p>
	 * @return flag 0:δ��ɳ�ʼ��1:��ɳ�ʼ
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
	 * ȡϵͳ��ʼ������
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
	 * ���ϵͳ����
	 */
	public void updateSys_ClearData(){
		String[] sqls = new String[77];
		
		sqls[0] = "delete from xxfb_nbgg";   //�ڲ�����
		sqls[1] = "delete from account_dzd"; //���˵�
		sqls[2] = "delete from account_qc";  //�˻��ڳ�
		sqls[3] = "delete from accounts where id<>'AD0001'";  //�˻�
		sqls[4] = "delete from bjd";  //���۵�
		sqls[5] = "delete from bjd_desc";  //���۵���ϸ
		sqls[6] = "delete from cgfk";  //�ɹ�����
		sqls[7] = "delete from cgfk_desc";  //�ɹ�������ϸ
		sqls[8] = "delete from cgthd";  //�ɹ��˻���
		sqls[9] = "delete from cgthd_product";  //�ɹ��˻�����Ʒ
		sqls[10] = "delete from chtj";  //�������
		sqls[11] = "delete from chtj_desc";  //���������ϸ
		sqls[12] = "delete from ckd";  //���ⵥ
		sqls[13] = "delete from ckd_product";  //���ⵥ��Ʒ
		sqls[14] = "delete from client_qc";  //�ͻ��ڳ�
		sqls[15] = "delete from client_wl_init";  //�ͻ�������ʼ
		sqls[16] = "delete from clients";  //�����ͻ�
		sqls[17] = "update cms_all_seq set jhdid=1,gysid=1,cgyfkid=1,rkdid=1,kcid=1," +
				   "kcpdid=1,kfid=1,ckdid=1,clientid=1,xsdid=1,yskid=1,lsdid=1,userid=2," +
				   "roleid=2,pzid=1,thdid=1,dbsqid=1,kfdbid=1,chtjid=1,accountid=1," +
				   "qtsrid=1,qtzcid=1,nbzzid=1,cgthdid=1,lsyskid=1,lsthdid=1,yushoutoyingshouid=1,yufutoyingfuid=1,fysqid=1,txfkid=1,txfkdescid=1,kcpdyk_id=1,linkman_id=1,bxdid=1"; //����ϵͳ��������
		sqls[18] = "delete from dbsq";  //��������
		sqls[19] = "delete from dbsq_product"; //����������ز�Ʒ
		sqls[20] = "delete from fxdd";  //��������
		sqls[21] = "delete from jhd";  //������
		sqls[22] = "delete from jhd_product";  //��������ز�Ʒ
		sqls[23] = "delete from kcpd";  //����̵�
		sqls[24] = "delete from kcpd_desc";  //����̵���ϸ
		sqls[25] = "delete from kfdb";  //�ⷿ����
		sqls[26] = "delete from kfdb_product";  //�ⷿ������ز�Ʒ
		sqls[27] = "delete from lsd";  //���۵�
		sqls[28] = "delete from lsd_product";  //���۵���ز�Ʒ
		sqls[29] = "delete from lsysk";  //����Ԥ�տ�
		sqls[30] = "delete from nbzz";  //�ڲ�ת��
		sqls[31] = "delete from product";  //��Ʒ��
		sqls[32] = "delete from product_kc";  //��Ʒ���
		sqls[33] = "delete from product_kc_qc";  //��Ʒ����ڳ�
		sqls[34] = "delete from product_kind";  //��Ʒ���
		sqls[35] = "delete from pz";  //�������ˣ�ƽ�ˣ�
		sqls[36] = "delete from qtsr";  //��������
		sqls[37] = "delete from qtzc";  //����֧��
		sqls[38] = "delete from rkd";  //��ⵥ
		sqls[39] = "delete from rkd_product";  //��ⵥ��ز�Ʒ
		sqls[40] = "delete from role where role_id<>'RL00000001'";  //��ɫ
		sqls[41] = "delete from role_func where role_id<>'RL00000001'";  //��ɫ���ܱ�
		sqls[42] = "delete from serial_num_flow";  //���к���ת���̱�
		sqls[43] = "delete from serial_num_mng";  //���кŹ����
		sqls[44] = "delete from storehouse where id<>'AD00000001'";  //�ֿ�
		sqls[45] = "delete from sys_init_set";  //ϵͳ��ʼ�������
		sqls[46] = "delete from sys_user where user_id<>'AD00000001'";  //ϵͳ�û���
		sqls[47] = "delete from thd";  //�˻���
		sqls[48] = "delete from thd_product";  //�˻�����ز�Ʒ
		sqls[49] = "delete from user_role where user_id<>'AD00000001'";  //�û���ɫ��
		sqls[50] = "delete from xsd";  //���۶���
		sqls[51] = "delete from xsd_product";  //���۶�����ز�Ʒ
		sqls[52] = "delete from xssk";  //�����տ�
		sqls[53] = "delete from xssk_desc";  //�����տ���ϸ
		sqls[54] = "update accounts set qcje=0,dqje=0 where id='AD0001'";
		sqls[55] = "delete from fysq";  //��������
		sqls[56] = "delete from yufuk_list";  //Ԥ����
		sqls[57] = "delete from yufu_to_yingfu"; //Ԥ����Ӧ��
		sqls[58] = "delete from yufu_to_yingfu_desc"; //Ԥ����Ӧ����ϸ
		sqls[59] = "delete from yushouk_list";  //Ԥ�տ�
		sqls[60] = "delete from yushou_to_yingshou"; //Ԥ�ճ�Ӧ��
		sqls[61] = "delete from yushou_to_yingshou_desc"; //Ԥ�ճ�Ӧ����ϸ
		sqls[62] = "delete from txfk";  //̯������
		sqls[63] = "delete from txfk_desc"; //̯��������ϸ
		sqls[64] = "delete from cesp_right_roles";  //��������Ȩ��
		sqls[65] = "delete from clients_follow";    //�ͻ������ƻ�
		sqls[66] = "delete from clients_linkman";   //�ͻ���ϵ��
		sqls[67] = "delete from cqsp_right_roles";  //��������Ȩ��
		sqls[68] = "delete from jgsp_right_roles";  //�ͼ�����Ȩ��
		sqls[69] = "delete from kcpd_yk_tbl";       //����̵�ӯ��
		sqls[70] = "delete from linkman";           //������ϵ��
		sqls[71] = "delete from lssd";              //����˰��
		sqls[72] = "delete from pos_type";          //POS������
		sqls[73] = "delete from product_kc_init";   //����ڳ�����
		sqls[74] = "delete from send_msg_log";      //���ŷ�����־
		sqls[75] = "delete from sendsms";           //���ŷ����б�
		sqls[76] = "delete from sys_msg";           //ϵͳ��Ϣ
		sqls[77] = "delete from bxd";               //���޵�
		sqls[78] = "delete from bxd_product";       //���޵���Ʒ
		sqls[79] = "delete from  bxfhd";            //���޷�����
		sqls[80] = "delete from  bxfhd_product";    //���޷�������Ʒ
		sqls[81] = "delete from  fhkhd";            //�����ͻ���
		sqls[82] = "delete from  fhkhd_product";    //�����ͻ�����Ʒ
		sqls[83] = "delete from  jjd";              //�Ӽ���
		sqls[84] = "delete from  jjd_product";      //�Ӽ�����Ʒ
		sqls[85] = "delete from  sh_serial_num_flow ";//�ۺ������к���ת��¼
		sqls[86] = "delete from  shkc";               //�ۺ���
		sqls[87] = "delete from  wxrkd";             //ά����ⵥ
		sqls[88] = "delete from  wxrkd_product";     //ά����ⵥ��Ʒ
		sqls[89] = "delete from  sfd";                //�ۺ����
		sqls[89] = "delete from  pgd";                //�ɹ���	
		sqls[89] = "delete from  wxcld";                //ά�޴���
		sqls[89] = "delete from  wxcld_product";        //ά�޴�����Ʒ
		sqls[90] = "delete from  ykrk";
		sqls[91] = "delete from  ykrk_product";
		sqls[92] = "delete from  ykck";
		sqls[93] = "delete from  ykck_product";
	    
		
		
		this.getJdbcTemplate().batchUpdate(sqls);
	}
	
	
	/**
	 * �ж��Ƿ���ڸ����
	 * @return
	 */
	public boolean isExistFkc(){
		boolean is = false;
		String sql = "SELECT a.*,b.prop FROM product_kc a JOIN product b on b.product_id=a.product_id where b.prop='�����Ʒ' and a.nums<0";
		
		List list = this.getResultList(sql);
		
		if(list != null && list.size() > 0){
			is = true;
		}
		return is;
	}
	
	
	/**
	 * ����ϵͳLOGO����
	 * @return
	 */
	public Map getSysLogo(){
		String sql = "select * from logo_set";
		return this.getResultMap(sql);
	}
	
	
	/**
	 * ����ϵͳLOGO����
	 * @param cpy_name
	 * @param logo_url
	 */
	public void saveSysLogo(String cpy_name,String logo_url){
		String sql = "update logo_set set cpy_name='" + cpy_name + "',logo_url='" + logo_url + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ���ر����������
	 * @return
	 */
	public Map getReportSet(){
		String sql = "select * from report_set";
		return this.getResultMap(sql);
	}
	
	
	/**
	 * ���汨���������
	 * @param title_name
	 * @param foot_name
	 */
	public void saveReportSet(String title_name,String foot_name){
		String sql = "update report_set set title_name='" + title_name + "',foot_name='" + foot_name + "'";
		this.getJdbcTemplate().update(sql);
	}
	
	
	/**
	 * ��װ����
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
