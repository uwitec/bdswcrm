package com.sw.cms.xls;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;

import com.sw.cms.service.KcMxReportService;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;
import com.sw.cms.xls.template.ExportXlsTemplate;

public class ExportFckcResult extends ExportXlsTemplate {

	private KcMxReportService kcMxReportService;

	public void writeExcelFile(HttpServletRequest request, WritableSheet sheet) {
		try{
			String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));
			String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
			String store_id = StringUtils.nullToStr(request.getParameter("store_id"));
			String state = StringUtils.nullToStr(request.getParameter("state"));

			String conStr = "";
			if(!product_kind.equals("")){
				String[] arryItems = product_kind.split(",");
				String kind_name = "";
				if(arryItems != null && arryItems.length >0){
					for(int i=0;i<arryItems.length;i++){
						if(kind_name.equals("")){
							kind_name = StaticParamDo.getProductKindNameById(arryItems[i]);
						}else{
							kind_name += "��" + StaticParamDo.getProductKindNameById(arryItems[i]);
						}
						
					}
				}
				conStr += "&nbsp;&nbsp;<b>��Ʒ���</b>" + kind_name;
			}
			if(!product_name.equals("")){
				conStr += "&nbsp;&nbsp;<B>��Ʒ����/���</B>" + product_name;
			}
			if(!store_id.equals("")){
				conStr += "&nbsp;&nbsp;<b>�ⷿ��</b>" + StaticParamDo.getStoreNameById(store_id);
			}

			//ͳ�ƽ��
			List productList = kcMxReportService.getProductList(product_kind, product_name, state);
			List store_list = kcMxReportService.getStoreList(store_id);
			Map fckcStatResult = kcMxReportService.getKcStatResult(product_kind, product_name, state, store_id);
			
			int maxCol = 4;  //�������Ĭ��Ϊ6
			if(store_list != null && store_list.size() > 0){
				maxCol += store_list.size();
			}
			
			Label label = null;
			
			//дͳ�Ʊ����
			sheet.mergeCells(0, 0, maxCol-1, 0);
			label = new Label(0,0,"��Ʒ����ë������",this.getFt_title());
			sheet.addCell(label);
			
			//дͳ������
			sheet.mergeCells(0, 1, maxCol-1, 1);
			label = new Label(0,1,conStr,this.getFt_item_center());
			sheet.addCell(label);
			
			//дͳ�Ʊ�ͷ
			label = new Label(0,2,"��Ʒ����",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(1,2,"��Ʒ����",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(2,2,"���",this.getFt_item_center_bold());
			sheet.addCell(label);			
			if(store_list != null && store_list.size() > 0){
				for(int i=0;i<store_list.size();i++){
					Map map = (Map)store_list.get(i);
					
					label = new Label(2+i+1,2,StringUtils.nullToStr(map.get("name")),this.getFt_item_center_bold());
					sheet.addCell(label);	
				}
			}
			label = new Label(maxCol-1,2,"�ܼ�",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			
			//дͳ������
			int curRow = 3;
			if(productList != null && productList.size()>0){
				for(int i=0;i<productList.size();i++){
					Map map = (Map)productList.get(i);
					
					int total = 0;
					
					label = new Label(0,curRow,StringUtils.nullToStr(map.get("product_id")),this.getFt_item_center());
					sheet.addCell(label);			
					label = new Label(1,curRow,StringUtils.nullToStr(map.get("product_name")),this.getFt_item_left());
					sheet.addCell(label);			
					label = new Label(2,curRow,StringUtils.nullToStr(map.get("product_xh")),this.getFt_item_left());
					sheet.addCell(label);
					
					if(store_list != null && store_list.size() > 0){
						for(int k=0;k<store_list.size();k++){
							Map storeMap = (Map)store_list.get(k);
							
							String num = StringUtils.nullToStr(fckcStatResult.get(StringUtils.nullToStr(map.get("product_id")) + StringUtils.nullToStr(storeMap.get("id"))));
							if(num.equals("")){
								num = "0";
							}							
							total += new Integer(num);
							
							label = new Label(2+k+1,curRow,num,this.getFt_item_center());
							sheet.addCell(label);	
							
						}
					}
					
					label = new Label(maxCol-1,curRow,total+"",this.getFt_item_center());
					sheet.addCell(label);			
					
					curRow++;
				}
			}
			
		}catch(Exception e){
			log.error("�����ֲֿ��ͳ�Ƴ���ԭ��" + e.getMessage());
		}
	}

	public KcMxReportService getKcMxReportService() {
		return kcMxReportService;
	}

	public void setKcMxReportService(KcMxReportService kcMxReportService) {
		this.kcMxReportService = kcMxReportService;
	}

}
