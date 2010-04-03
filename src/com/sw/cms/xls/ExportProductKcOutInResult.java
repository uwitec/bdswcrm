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

/**
 * 库存数量汇总导出excel，逻辑处理类
 * extend ExportXlsTemplate
 * @author liyt
 *
 */
public class ExportProductKcOutInResult extends ExportXlsTemplate {
	
	private KcMxReportService kcMxReportService = new KcMxReportService();
	
	/**
	 * 写Excel文件
	 */
	public void writeExcelFile(HttpServletRequest request,WritableSheet sheet) {
		
		try{		
			String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
			String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
			String product_kind = StringUtils.nullToStr(request.getParameter("product_kind"));
			String product_name = StringUtils.nullToStr(request.getParameter("product_name"));
			String store_id = StringUtils.nullToStr(request.getParameter("store_id"));
	
			String isKc0 = StringUtils.nullToStr(request.getParameter("isKc0")); //是否显示0库存商品
			String isFse0 = StringUtils.nullToStr(request.getParameter("isFse0")); //是否显示0发生额商品
	
			String conStr = "";
	
			conStr += "日期：" + start_date + "至" + end_date;
	
			if(!store_id.equals("")){
				conStr += "　库房：" + StaticParamDo.getStoreNameById(store_id);
			}
			if(!product_kind.equals("")){
				conStr += "　商品类别：" + StaticParamDo.getProductKindNameById(product_kind);
			}
			
			Label label = null;
			


			
			//写统计表标题
			sheet.mergeCells(0, 0, 7, 0);
			label = new Label(0,0,"库存数量汇总",this.getFt_title());
			sheet.addCell(label);
			
			//写统计条件
			sheet.mergeCells(0, 1, 7, 1);
			label = new Label(0,1,conStr,this.getFt_item_center());
			sheet.addCell(label);
					
			//写统计表头
			label = new Label(0,2,"商品编码",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(1,2,"商品名称",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(2,2,"规格",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(3,2,"单位",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(4,2,"期初数量",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(5,2,"收入数量",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(6,2,"发出数量",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(7,2,"期末结存",this.getFt_item_center_bold());
			sheet.addCell(label);
	
			List productKcList = kcMxReportService.getProductKcList(product_kind,product_name,store_id);
	
			int hj_qc_nums = 0; //期初数量合计
			int hj_ck_nums = 0; //出库数量合计
			int hj_rk_nums = 0; //入库数量合计
			int hj_jc_nums = 0; //合计结存数量（库存总商品数）
	
			if(productKcList != null && productKcList.size()>0){
				
				int xh = 0;
				
				int k = 3;
				for(int i=0;i<productKcList.size();i++){
					Map map = (Map)productKcList.get(i);
					
					String product_id = StringUtils.nullToStr(map.get("product_id"));
					String product_name2 = StringUtils.nullToStr(map.get("product_name"));
					String product_xh = StringUtils.nullToStr(map.get("product_xh"));
					String dw = StringUtils.nullToStr(map.get("dw"));
					
					
					//根据商品编号、开始时间、库房编号取库存期初情况
					Map qcMap = kcMxReportService.getKcqcMxMap(product_id,start_date,store_id);
					
					String strNums = "0";   //期初数
					if(qcMap != null){
						strNums = StringUtils.nullToStr(qcMap.get("nums"));
						if(strNums.equals("")){
							strNums = "0";
						}
					}
					
					int qc_nums = new Integer(strNums).intValue();  //期初数
					int rk_nums = kcMxReportService.getRkNums(product_id,start_date,end_date,store_id); //收入数量
					int ck_nums = kcMxReportService.getCkNums(product_id,start_date,end_date,store_id); //发出数量
					
					int jc_nums = qc_nums + rk_nums - ck_nums;
					
					boolean is_kc = true;		
					if(isKc0.equals("否")){ //显示0库存商品
						if(jc_nums == 0){
							is_kc = false;
						}
					}
					
					boolean is_fs = true;
					if(isFse0.equals("否")){ //显示0发生额商品
						if(rk_nums == 0 && ck_nums == 0){
							is_fs = false;
						}
					}
					
					if(is_kc && is_fs){
					
						hj_qc_nums += qc_nums;
						hj_rk_nums += rk_nums;
						hj_ck_nums += ck_nums;		
						hj_jc_nums += jc_nums;
						
						xh++;
									
						label = new Label(0,k,product_id,this.getFt_item_center());
						sheet.addCell(label);			
						label = new Label(1,k,product_name2,this.getFt_item_left());
						sheet.addCell(label);			
						label = new Label(2,k,product_xh,this.getFt_item_left());
						sheet.addCell(label);			
						label = new Label(3,k,dw,this.getFt_item_center());
						sheet.addCell(label);
						label = new Label(4,k,qc_nums+"",this.getFt_item_right());
						sheet.addCell(label);
						label = new Label(5,k,rk_nums+"",this.getFt_item_right());
						sheet.addCell(label);
						label = new Label(6,k,ck_nums+"",this.getFt_item_right());
						sheet.addCell(label);
						label = new Label(7,k,jc_nums+"",this.getFt_item_right());
						sheet.addCell(label);
						
						k++;
					}
				}
				
				label = new Label(0,k,"合计",this.getFt_item_center_bold());
				sheet.addCell(label);					
				label = new Label(1,k,"",this.getFt_item_left());
				sheet.addCell(label);			
				label = new Label(2,k,"",this.getFt_item_left());
				sheet.addCell(label);			
				label = new Label(3,k,"",this.getFt_item_center());
				sheet.addCell(label);
				label = new Label(4,k,hj_qc_nums+"",this.getFt_item_right());
				sheet.addCell(label);
				label = new Label(5,k,hj_rk_nums+"",this.getFt_item_right());
				sheet.addCell(label);
				label = new Label(6,k,hj_ck_nums+"",this.getFt_item_right());
				sheet.addCell(label);
				label = new Label(7,k,hj_jc_nums+"",this.getFt_item_right());
				sheet.addCell(label);
			}
		}catch(Exception ex){
			log.info(ex);
		}
	}

	public KcMxReportService getKcMxReportService() {
		return kcMxReportService;
	}

	public void setKcMxReportService(KcMxReportService kcMxReportService) {
		this.kcMxReportService = kcMxReportService;
	}
	
}
