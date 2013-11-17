package com.sw.cms.xls.template;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class ExportXlsTemplate {
	
	protected transient final Log log = LogFactory.getLog(getClass());
	
	private WritableCellFormat ft_title = null;              //��ͷ��Ԫ���ʽ
	
	private WritableCellFormat ft_item_center = null;        //��Ԫ�����
	private WritableCellFormat ft_item_center_bold = null;   //��Ԫ����к���
	private WritableCellFormat ft_item_centergray = null;        //��Ԫ����в���ɫΪ��ɫ
	
	private WritableCellFormat ft_item_left = null;        //��Ԫ�������
	private WritableCellFormat ft_item_left_bold = null;   //��Ԫ����������
	private WritableCellFormat ft_item_leftgray = null;       //��Ԫ������벢��ɫΪ��ɫ
	
	private WritableCellFormat ft_item_right = null;       //��Ԫ���Ҷ���
	private WritableCellFormat ft_item_right_bold = null;  //��Ԫ���Ҷ������
	private WritableCellFormat ft_item_rightgray = null;       //��Ԫ���Ҷ��벢��ɫΪ��ɫ
	
	/**
	 * ���ݲ�ѯ���дexcel�ļ�
	 * @param request
	 * @param sheet
	 */
	public abstract void writeExcelFile(HttpServletRequest request,WritableSheet sheet);
	
	/**
	 * ����excel�ļ����õ�ǰʱ�����Ϊ�ļ���
	 * @param request
	 * @param servletContext
	 * @return
	 */
	public String getXlsFile(HttpServletRequest request,ServletContext servletContext){
		
		try{
			String rootPath = servletContext.getRealPath("/");
			
			long curTime = System.currentTimeMillis();
			String fileName = rootPath + "xls/" + (curTime+"") + ".xls";
			
			//���ļ� 
	        WritableWorkbook book = Workbook.createWorkbook(new File(fileName));
	        //������Ϊ��ͳ�ƽ�����Ĺ���������0��ʾ���ǵ�һҳ 
	        WritableSheet sheet  =  book.createSheet( "ͳ�ƽ��" ,  0 );			
	        
	        //дexcel�ļ�
	        this.writeExcelFile(request,sheet);
	        
	        book.write();
            book.close();
	        return fileName;
		}catch(Exception ex){
			log.info("����excel����" + ex);
		}
		return null;
	}

	/**
	 * ȡ������ⵥԪ���ʽ
	 * @return
	 */
	public WritableCellFormat getFt_title() {
		try{
			ft_title = new WritableCellFormat();
			ft_title.setFont(new WritableFont(WritableFont.TIMES, 16 ,WritableFont.BOLD));
			ft_title.setAlignment(Alignment.CENTRE);
			ft_title.setVerticalAlignment(VerticalAlignment.CENTRE);
			ft_title.setBorder(Border.ALL,BorderLineStyle.THIN);
		}catch(Exception e){
			log.info(e);
		}
		return ft_title;
	}


	/**
	 * ��Ԫ�����
	 * @return
	 */
	public WritableCellFormat getFt_item_center() {
		try{
			ft_item_center = new WritableCellFormat();
			ft_item_center.setFont(new WritableFont(WritableFont.TIMES, 10 ,WritableFont.NO_BOLD));
			ft_item_center.setAlignment(Alignment.CENTRE);
			ft_item_center.setVerticalAlignment(VerticalAlignment.CENTRE);
			ft_item_center.setBorder(Border.ALL,BorderLineStyle.THIN);
		}catch(Exception e){
			
		}
		return ft_item_center;
	}
	
	
	/**
	 * ���ܵ�Ԫ����У�����ɫΪ��ɫ
	 * @return
	 */
	public WritableCellFormat getFt_item_centergray() {
		try{
			ft_item_centergray = new WritableCellFormat();
			ft_item_centergray.setFont(new WritableFont(WritableFont.TIMES, 10 ,WritableFont.NO_BOLD));
			ft_item_centergray.setAlignment(Alignment.CENTRE);
			ft_item_centergray.setVerticalAlignment(VerticalAlignment.CENTRE);
			ft_item_centergray.setBorder(Border.ALL,BorderLineStyle.THIN);
			ft_item_centergray.setBackground(jxl.format.Colour.GRAY_25);
		}catch(Exception e){
			
		}
		return ft_item_centergray;
	}
	
	/**
	 * ��Ԫ������Һ���
	 * @param ft_item_bold
	 */
	public WritableCellFormat getFt_item_center_bold() {
		try{
			ft_item_center_bold = new WritableCellFormat();
			ft_item_center_bold.setFont(new WritableFont(WritableFont.TIMES, 10 ,WritableFont.BOLD));
			ft_item_center_bold.setAlignment(Alignment.CENTRE);
			ft_item_center_bold.setVerticalAlignment(VerticalAlignment.CENTRE);
			ft_item_center_bold.setBorder(Border.ALL,BorderLineStyle.THIN);
		}catch(Exception e){
			log.info(e);
		}
		return ft_item_center_bold;
	}

	
	/**
	 * ��Ԫ�������
	 * @return
	 */
	public WritableCellFormat getFt_item_left() {
		try{
			ft_item_left = new WritableCellFormat();
			ft_item_left.setFont(new WritableFont(WritableFont.TIMES, 10 ,WritableFont.NO_BOLD));
			ft_item_left.setAlignment(Alignment.LEFT);
			ft_item_left.setVerticalAlignment(VerticalAlignment.CENTRE);
			ft_item_left.setBorder(Border.ALL,BorderLineStyle.THIN);
		}catch(Exception e){
			log.info(e);
		}
		return ft_item_left;
	}
	
	
	/**
	 * ��Ԫ��������Һ���
	 * @return
	 */
	public WritableCellFormat getFt_item_left_bold() {
		try{
			ft_item_left_bold = new WritableCellFormat();
			ft_item_left_bold.setFont(new WritableFont(WritableFont.TIMES, 10 ,WritableFont.BOLD));
			ft_item_left_bold.setAlignment(Alignment.LEFT);
			ft_item_left_bold.setVerticalAlignment(VerticalAlignment.CENTRE);
			ft_item_left_bold.setBorder(Border.ALL,BorderLineStyle.THIN);
		}catch(Exception e){
			log.info(e);
		}
		return ft_item_left_bold;
	}

	/**
	 * ��Ԫ������벢��ɫΪ��ɫ
	 * @return
	 */
	public WritableCellFormat getFt_item_leftgray() {
		try{
			ft_item_leftgray = new WritableCellFormat();
			ft_item_leftgray.setFont(new WritableFont(WritableFont.TIMES, 10 ,WritableFont.NO_BOLD));
			ft_item_leftgray.setAlignment(Alignment.LEFT);
			ft_item_leftgray.setVerticalAlignment(VerticalAlignment.CENTRE);
			ft_item_leftgray.setBorder(Border.ALL,BorderLineStyle.THIN);
			ft_item_leftgray.setBackground(jxl.format.Colour.GRAY_25);
		}catch(Exception e){
			log.info(e);
		}
		return ft_item_leftgray;
	}
	
	/**
	 * ��Ԫ���Ҷ���
	 * @return
	 */
	public WritableCellFormat getFt_item_right() {
		try{
			ft_item_right = new WritableCellFormat();
			ft_item_right.setFont(new WritableFont(WritableFont.TIMES, 10 ,WritableFont.NO_BOLD));
			ft_item_right.setAlignment(Alignment.RIGHT);
			ft_item_right.setVerticalAlignment(VerticalAlignment.CENTRE);
			ft_item_right.setBorder(Border.ALL,BorderLineStyle.THIN);
		}catch(Exception e){
			log.info(e);
		}
		return ft_item_right;
	}

	
	/**
	 * ��Ԫ���Ҷ����Һ���
	 * @return
	 */
	public WritableCellFormat getFt_item_right_bold() {
		try{
			ft_item_right_bold = new WritableCellFormat();
			ft_item_right_bold.setFont(new WritableFont(WritableFont.TIMES, 10 ,WritableFont.BOLD));
			ft_item_right_bold.setAlignment(Alignment.RIGHT);
			ft_item_right_bold.setVerticalAlignment(VerticalAlignment.CENTRE);
			ft_item_right_bold.setBorder(Border.ALL,BorderLineStyle.THIN);
		}catch(Exception e){
			log.info(e);
		}
		return ft_item_right_bold;
	}

	/**
	 * ��Ԫ���Ҷ��벢��ɫΪ��ɫ
	 * @return
	 */
	public WritableCellFormat getFt_item_rightgray() {
		try{
			ft_item_rightgray = new WritableCellFormat();
			ft_item_rightgray.setFont(new WritableFont(WritableFont.TIMES, 10 ,WritableFont.NO_BOLD));
			ft_item_rightgray.setAlignment(Alignment.RIGHT);
			ft_item_rightgray.setVerticalAlignment(VerticalAlignment.CENTRE);
			ft_item_rightgray.setBorder(Border.ALL,BorderLineStyle.THIN);
			ft_item_rightgray.setBackground(jxl.format.Colour.GRAY_25);
		}catch(Exception e){
			log.info(e);
		}
		return ft_item_rightgray;
	}
}
