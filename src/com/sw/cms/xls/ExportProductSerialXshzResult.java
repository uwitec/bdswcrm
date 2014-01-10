package com.sw.cms.xls;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;

import com.sw.cms.service.ProductSerialNumXsHzService;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;
import com.sw.cms.xls.template.ExportXlsTemplate;

public class ExportProductSerialXshzResult extends ExportXlsTemplate {
	
	private ProductSerialNumXsHzService productSerialNumXsHzService;

	@Override
	public void writeExcelFile(HttpServletRequest request, WritableSheet sheet) {
		try{
			String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
			String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
			String dept_id = StringUtils.nullToStr(request.getParameter("dept_id"));

			String clientId = StringUtils.nullToStr(request.getParameter("clientId"));
			String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
			String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
			String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));
			
			String xsry_name = StringUtils.nullToStr(request.getParameter("xsry_name"));

			String con = "";
			con = "���ڣ�" + start_date + "��" + end_date;
			if(!clientId.equals("")){
				con += "  �ͻ����ƣ�" + clientId;
			}
			if(!dept_id.equals("")){
				con += "  ���ţ�" + StaticParamDo.getDeptNameById(dept_id);
			}
			
			if(!product_kind.equals("")){
				con += "  ��Ʒ���" + StaticParamDo.getProductKindNameById(product_kind);
			}
			con += "����������ʱ�䣺" + DateComFunc.getCurTime();
			
			Label label = null;
			
			//дͳ�Ʊ����
			sheet.mergeCells(0, 0, 7, 0);
			label = new Label(0,0,"��Ʒ���к����ۻ���",this.getFt_title());
			sheet.addCell(label);
			
			//дͳ������
			sheet.mergeCells(0, 1, 7, 1);
			label = new Label(0,1,con,this.getFt_item_center());
			sheet.addCell(label);
					
			//дͳ�Ʊ�ͷ
			label = new Label(0,2,"����",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(1,2,"���к�",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(2,2,"��Ʒ����",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(3,2,"��Ʒ���",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(4,2,"�ͻ�����",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(5,2,"�ͻ��绰",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(6,2,"����Ա",this.getFt_item_center_bold());	
			sheet.addCell(label);			
			label = new Label(7,2,"���ۼ۸�",this.getFt_item_center_bold());	
			sheet.addCell(label);
			
			List resultList = productSerialNumXsHzService.getSerialNumXsList(start_date, end_date, product_kind, product_name, dept_id, xsry_name,clientId);
			
			int hj_nums = 0;
			double hj_je = 0;
			int k = 3;
			
			//дͳ������
			if(resultList != null && resultList.size()>0){				
				for(int i=0;i<resultList.size();i++){
					Map map = (Map)resultList.get(i);
					double je = map.get("xsdj")==null?0:((Double)map.get("xsdj")).doubleValue();
					
					label = new Label(0,k,StringUtils.nullToStr(map.get("fs_date")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(1,k,StringUtils.nullToStr(map.get("serial_num")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(2,k,StringUtils.nullToStr(map.get("product_name")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(3,k,StringUtils.nullToStr(map.get("product_xh")),this.getFt_item_left());
					sheet.addCell(label);
					label = new Label(4,k,StringUtils.nullToStr(map.get("client_name")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(5,k,StringUtils.nullToStr(map.get("tel")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(6,k,StringUtils.nullToStr(map.get("jsr")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(7,k,JMath.round(je,2),this.getFt_item_right());
					sheet.addCell(label);
					
					k++;
				}
			}		

			
		}catch(Exception ex){
			log.info(ex);
		}
	}

	public ProductSerialNumXsHzService getProductSerialNumXsHzService() {
		return productSerialNumXsHzService;
	}

	public void setProductSerialNumXsHzService(ProductSerialNumXsHzService productSerialNumXsHzService) {
		this.productSerialNumXsHzService = productSerialNumXsHzService;
	}

}
