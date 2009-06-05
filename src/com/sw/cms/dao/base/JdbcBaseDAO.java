package com.sw.cms.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.sw.cms.model.Page;

/**
 * ����DAO���Ҫ�̳д��ֲ࣬����JdbcTemplate
 * ��ӷ�ҳ����������Ĵ����޲���JdbcTemplate������� 
 * @author liyt
 *
 */

public class JdbcBaseDAO {
	
	protected transient final Log log = LogFactory.getLog(getClass());
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	/**
	 * ��ѯ��ҳ���
	 * ���ط�ҳ����page
	 * page.getResults()����Ϊlist��list��ΪMap����
	 * �ֶ���Ϊkey,ֵΪvalue
	 * @param sql
	 * @param curPage //ҳ�� ��1��ʼ
	 * @param perPage //ÿҳ����
	 * @return page
	 */
	public Page getResultByPage(String sql,int curPage,int rowsPerPage){
		Page page = new Page();
	    
	    int count=curPage*rowsPerPage-rowsPerPage;  //��¼���
	    
	    //��sql�������ٰ�װ����ӷ�ҳ
	    //���spring jdbc queryForList���Ӳ�ѯ�޷�������ʾ���������
	    String strSql ="select t.* from (" + sql + " limit "+rowsPerPage +" offset "+count + ") as t";
	    
	    List result = jdbcTemplate.queryForList(strSql);
	    
	    
	    String countSql = "select count(*) as allcount from (" + sql + ") as t" ;
	    int allcount = jdbcTemplate.queryForInt(countSql);
	    
	    int totalPage = 0;
	    if(allcount % rowsPerPage ==0){
	    	totalPage = allcount / rowsPerPage;
	    }else{
	    	totalPage = allcount / rowsPerPage + 1;
	    }
	    
	    page.setCurPage(curPage);
	    page.setPerPage(rowsPerPage);
	    page.setResults(result);
	    page.setTotalNum(allcount);
	    page.setTotalPage(totalPage); 
		
		return page;
	}
	
	
	/**
	 * ��ѯ��ҳ���
	 * ���ط�ҳ����page
	 * page.getResults()����Ϊlist��list��ΪMap����
	 * �ֶ���Ϊkey,ֵΪvalue
	 * @param sql
	 * @param param ������
	 * @param curPage ҳ�� ��1��ʼ
	 * @param perPage ÿҳ����
	 * @return page
	 */
	public Page getResultByPage(String sql,Object[] param,int curPage,int rowsPerPage){
		Page page = new Page();
	    
	    int count=curPage*rowsPerPage-rowsPerPage;  //��¼���
	    
	    //��sql�������ٰ�װ����ӷ�ҳ
	    //���spring jdbc queryForList���Ӳ�ѯ�޷�������ʾ���������
	    String strSql ="select t.* from (" + sql + " limit "+rowsPerPage +" offset "+count + ") as t";
	    
	    List result = jdbcTemplate.queryForList(strSql,param);
	    
	    
	    String countSql = "select count(*) as allcount from (" + sql + ") as t" ;
	    int allcount = jdbcTemplate.queryForInt(countSql);
	    
	    int totalPage = 0;
	    if(allcount % rowsPerPage ==0){
	    	totalPage = allcount / rowsPerPage;
	    }else{
	    	totalPage = allcount / rowsPerPage + 1;
	    }
	    
	    page.setCurPage(curPage);
	    page.setPerPage(rowsPerPage);
	    page.setResults(result);
	    page.setTotalNum(allcount);
	    page.setTotalPage(totalPage); 
		
		return page;
	}	
	
	/**
	 * 
	 * ��ѯ��ҳ���
	 * ���ط�ҳ����page
	 * page.getResults()����Ϊlist��list��Ϊ�����װ��Ķ���
	 * @param sql
	 * @param curPage //ҳ�� ��1��ʼ
	 * @param perPage //ÿҳ����
	 * @return page
	 */
	public Page getResultByPage(String sql,int curPage,int rowsPerPage,RowMapper rowMapper){		
		Page page = new Page();
		
	    int count=curPage*rowsPerPage-rowsPerPage;  //��¼���
	    
	    //��sql�������ٰ�װ����ӷ�ҳ
	    //���spring jdbc queryForList���Ӳ�ѯ�޷�������ʾ���������
	    String strSql = "select t.* from (" + sql + " limit "+rowsPerPage +" offset "+count + ") as t";
	    
	    List result = jdbcTemplate.query(strSql, rowMapper);
	    
	    
	    String countSql = "select count(*) as allcount from (" + sql + ") as t" ;
	    int allcount = jdbcTemplate.queryForInt(countSql);
	    
	    int totalPage = 0;
	    if(allcount % rowsPerPage ==0){
	    	totalPage = allcount / rowsPerPage;
	    }else{
	    	totalPage = allcount / rowsPerPage + 1;
	    }
	    
	    page.setCurPage(curPage);
	    page.setPerPage(rowsPerPage);
	    page.setResults(result);
	    page.setTotalNum(allcount);
	    page.setTotalPage(totalPage);
	    
		return page;		
	}
	
	
	/**
	 * 
	 * ��ѯ��ҳ���
	 * ���ط�ҳ����page
	 * page.getResults()����Ϊlist��list��Ϊ�����װ��Ķ���
	 * @param sql
	 * @param param ������
	 * @param curPage //ҳ�� ��1��ʼ
	 * @param perPage //ÿҳ����
	 * @return page
	 */
	public Page getResultByPage(String sql,int curPage,Object[] param,int rowsPerPage,RowMapper rowMapper){		
		Page page = new Page();
		
	    int count=curPage*rowsPerPage-rowsPerPage;  //��¼���
	    
	    //��sql�������ٰ�װ����ӷ�ҳ
	    //���spring jdbc queryForList���Ӳ�ѯ�޷�������ʾ���������
	    String strSql = "select t.* from (" + sql + " limit "+rowsPerPage +" offset "+count + ") as t";
	    
	    List result = jdbcTemplate.query(strSql,param,rowMapper);
	    
	    
	    String countSql = "select count(*) as allcount from (" + sql + ") as t" ;
	    int allcount = jdbcTemplate.queryForInt(countSql);
	    
	    int totalPage = 0;
	    if(allcount % rowsPerPage ==0){
	    	totalPage = allcount / rowsPerPage;
	    }else{
	    	totalPage = allcount / rowsPerPage + 1;
	    }
	    
	    page.setCurPage(curPage);
	    page.setPerPage(rowsPerPage);
	    page.setResults(result);
	    page.setTotalNum(allcount);
	    page.setTotalPage(totalPage);
	    
		return page;		
	}	
	
	
	/**
	 * ���ز�ѯ���ΪList
	 * list��Ϊmap����
	 * 
	 */
	public List getResultList(String sql){
		sql = "select * from (" + sql + ") as x";
		return jdbcTemplate.queryForList(sql);
	}
	
	
	/**
	 * ���ز�ѯ���Ϊlist
	 * list��Ϊ��װ�õĶ���
	 * @param sql
	 * @param rowMapper
	 * @return
	 */
	public List getResultList(String sql,RowMapper rowMapper){
		sql = "select * from (" + sql + ") as x";
		return this.getJdbcTemplate().query(sql, rowMapper);
	}
	
	
	/**
	 * ���ز�ѯ���ΪMap
	 * @param sql
	 * @return
	 */
	public Map getResultMap(String sql){
		Map map = null;
		sql = "select * from (" + sql + ") as x";
		List list = jdbcTemplate.queryForList(sql);
		if(list != null && list.size()>0){
			map = (Map)list.get(0);
		}
		return map;
	}
	
	
	/**
	 * ��ѯ��������
	 * ��spring JdbcTemplate�������ٰ�װ
	 * @param sql
	 * @param rowMapper
	 * @return Object
	 */
	public Object queryForObject(String sql,RowMapper rowMapper){
		List list = jdbcTemplate.query(sql, rowMapper);
		
		if(list != null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * ��ѯ��������
	 * ��spring JdbcTemplate�������ٰ�װ
	 * @param sql
	 * @param rowMapper
	 * @return Object
	 */
	public Object queryForObject(String sql,Object[] param,RowMapper rowMapper){
		List list = jdbcTemplate.query(sql, param, rowMapper);
		
		if(list != null && list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
	}	

}
