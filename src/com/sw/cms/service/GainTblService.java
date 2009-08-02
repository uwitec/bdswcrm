package com.sw.cms.service;

import java.util.Map;

import com.sw.cms.dao.GainTblDAO;

public class GainTblService {
	
	private GainTblDAO gainTblDao;
	
	/**
	 * ͳ����Ӫҵ������<BR>
	 * ���п����Ʒ���������루����-�˻���
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statZyywSr(String ny){
		return gainTblDao.statZyywSr(ny);
	}
	
	
	/**
	 * ͳ����Ӫҵ��ɱ�<BR>
	 * �������п����Ʒ�ϼƲɹ��ɱ�������-�˻���
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statZyywCb(String ny){
		return gainTblDao.statZyywCb(ny);
	}
	
	
	/**
	 * ͳ������ҵ������
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statQtywSr(String ny){
		return gainTblDao.statQtywSr(ny);
	}
	
	
	/**
	 * ͳ������ҵ��ɱ�
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statQtywCb(String ny){
		return gainTblDao.statQtywCb(ny);
	}
	
	
	/**
	 * Ӫҵ������
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statYywSr(String ny){
		return gainTblDao.statYywSr(ny);
	}
	
	
	/**
	 * Ӫҵ��֧��<BR>
	 * ȡ��һ����ñ�<BR>
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statYywZc(String ny){
		return gainTblDao.statYywZc(ny);
	}
	
	
	/**
	 * ��Ʒ��������
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statSpbySr(String ny){
		return gainTblDao.statSpbySr(ny);
	}
	
	
	/**
	 * ��Ʒ����֧��
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statSpbsZc(String ny){
		return gainTblDao.statSpbsZc(ny);
	}
	
	
	/**
	 * ������������<BR>
	 * Ӧ�ռ�+Ӧ����<BR>
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statWltzSr(String ny){
		return gainTblDao.statWltzSr(ny);
	}
	
	
	/**
	 * ��������֧��<BR>
	 * Ӧ�ռ�+Ӧ����<BR>
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statWltzZc(String ny){
		return gainTblDao.statWltzZc(ny);
	}
	
	
	/**
	 * ��̯����
	 * @param ny  ��ǰ����
	 * @return  Map(key,value)  <BR>
	 * key=curMonth ���½���� <BR>
	 * key=allMonth  ������ۻ�
	 */
	public Map statDtfy(String ny){
		return gainTblDao.statDtfy(ny);
	}


	public GainTblDAO getGainTblDao() {
		return gainTblDao;
	}


	public void setGainTblDao(GainTblDAO gainTblDao) {
		this.gainTblDao = gainTblDao;
	}

}
