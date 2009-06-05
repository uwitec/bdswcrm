package com.sw.cms.xls;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;

import com.sw.cms.service.HpxsHzService;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;
import com.sw.cms.xls.template.ExportXlsTemplate;

/**
 * ��Ʒ����ë�����ܵ���
 * @author liyt
 *
 */
public class ExportHpxsMlHzResult extends ExportXlsTemplate {

	private HpxsHzService hpxsHzService;

	public void writeExcelFile(HttpServletRequest request, WritableSheet sheet) {

		try{
			String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
			String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
			String xsry_id = StringUtils.nullToStr(request.getParameter("xsry_id"));

			String clientId = StringUtils.nullToStr(request.getParameter("clientId"));
			String clientName = StringUtils.nullToStr(request.getParameter("clientName"));

			String kind_name = StringUtils.nullToStr(request.getParameter("kind_name"));
			String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));
			String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
			String product_xh = StringUtils.nullToStr(request.getParameter("product_xh"));

			String con = "";
			con = "���ڣ�" + start_date + "��" + end_date;
			if(!clientId.equals("")){
				con += "  �ͻ����ƣ�" + clientId;
			}
			if(!xsry_id.equals("")){
				con += "  ������Ա��" + StaticParamDo.getRealNameById(xsry_id);
			}
			if(!kind_name.equals("")){
				con += "  ��Ʒ���" + kind_name;
			}
			if(!product_name.equals("")){
				con += "  ��Ʒ���ƣ�" + product_name;
			}
			if(!product_xh.equals("")){
				con += "  ��Ʒ���" + product_xh;
			}
			con += "����������ʱ�䣺" + DateComFunc.getCurTime();
			
			Label label = null;
			
			//дͳ�Ʊ����
			sheet.mergeCells(0, 0, 7, 0);
			label = new Label(0,0,"��Ʒ����ë������",this.getFt_title());
			sheet.addCell(label);
			
			//дͳ������
			sheet.mergeCells(0, 1, 7, 1);
			label = new Label(0,1,con,this.getFt_item_center());
			sheet.addCell(label);
					
			//дͳ�Ʊ�ͷ
			label = new Label(0,2,"��Ʒ����",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(1,2,"��Ʒ����",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(2,2,"���",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(3,2,"����",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(4,2,"��������",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(5,2,"�ɱ�",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(6,2,"ë��",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(7,2,"ë����",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			List resultList = hpxsHzService.getHpxsmlhzTjResult(product_kind, product_name, product_xh, start_date, end_date, clientName, xsry_id);
			
			int hj_nums = 0;
			double hj_xssr = 0;
			double hj_cb = 0;
			double hj_ml = 0;
			int k = 3;
			
			//дͳ������
			if(resultList != null && resultList.size()>0){				
				for(int i=0;i<resultList.size();i++){
					Map map = (Map)resultList.get(i);
				
					String prop = StringUtils.nullToStr(map.get("prop"));   //��Ʒ����(����̿ڡ�����/����)

					int nums = new Integer(StringUtils.nullToStr(map.get("nums"))).intValue(); //����
					double xssr = map.get("hjje")==null?0:((Double)map.get("hjje")).doubleValue(); //��������
					double cb = map.get("hjcb")==null?0:((Double)map.get("hjcb")).doubleValue(); //�ɱ�
							
					if(prop.equals("����/����")){ //����Ƿ���/��������Ʒ���ɱ���Ϊ0
						cb = 0;
					}
					
					double ml = xssr - cb;

					hj_nums += nums;
					hj_xssr += xssr;
					hj_cb += cb;
					hj_ml += ml;
					
					label = new Label(0,k,StringUtils.nullToStr(map.get("product_id")),this.getFt_item_center());
					sheet.addCell(label);			
					label = new Label(1,k,StringUtils.nullToStr(map.get("product_name")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(2,k,StringUtils.nullToStr(map.get("product_xh")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(3,k,nums+"",this.getFt_item_center());
					sheet.addCell(label);
					label = new Label(4,k,JMath.round(xssr,2),this.getFt_item_right());
					sheet.addCell(label);
					label = new Label(5,k,JMath.round(cb,2),this.getFt_item_right());
					sheet.addCell(label);
					label = new Label(6,k,JMath.round(ml,2),this.getFt_item_right());
					sheet.addCell(label);
					label = new Label(7,k,JMath.percent(ml,xssr),this.getFt_item_right());
					sheet.addCell(label);
					
					k++;
				}
			}
			
			//д�ϼ�
			label = new Label(0,k,"�ϼ�",this.getFt_item_center());
			sheet.addCell(label);
			label = new Label(3,k,hj_nums+"",this.getFt_item_center());
			sheet.addCell(label);
			label = new Label(4,k,JMath.round(hj_xssr,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(5,k,JMath.round(hj_cb,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(6,k,JMath.round(hj_ml,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(7,k,JMath.percent(hj_ml,hj_xssr),this.getFt_item_right());
			sheet.addCell(label);
			
		}catch(Exception ex){
			log.info(ex);
		}

	}

	public HpxsHzService getHpxsHzService() {
		return hpxsHzService;
	}

	public void setHpxsHzService(HpxsHzService hpxsHzService) {
		this.hpxsHzService = hpxsHzService;
	}

}
