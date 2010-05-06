package com.sw.cms.xls;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.write.Label;
import jxl.write.WritableSheet;

import com.sw.cms.service.ClientWlDzdService;
import com.sw.cms.util.DateComFunc;
import com.sw.cms.util.JMath;
import com.sw.cms.util.StaticParamDo;
import com.sw.cms.util.StringUtils;
import com.sw.cms.xls.template.ExportXlsTemplate;

/**
 * �����ͻ��������˵�
 * @author liyt
 *
 */

public class ExportClientWlDzdResult extends ExportXlsTemplate {
	
	private ClientWlDzdService clientWlDzdService; 

	public void writeExcelFile(HttpServletRequest request, WritableSheet sheet) {
		try{
			String start_date = StringUtils.nullToStr(request.getParameter("start_date"));
			String end_date = StringUtils.nullToStr(request.getParameter("end_date"));
			String client_name = StringUtils.nullToStr(request.getParameter("client_name"));
			String flag = StringUtils.nullToStr(request.getParameter("flag")); //��ʾ��ϸ
			
			//ͳ������
			String conStr = "���ڣ�" + start_date + "��" + end_date + " �ͻ����ƣ�" + StaticParamDo.getClientNameById(client_name);
			conStr += "����������ʱ�䣺" + DateComFunc.getCurTime();
			
			Label label = null;
			
			//дͳ�Ʊ����
			sheet.mergeCells(0, 0, 11, 0);
			label = new Label(0,0,"�ͻ��������˵�",this.getFt_title());
			sheet.addCell(label);
			
			
			//дͳ������
			sheet.mergeCells(0, 1, 11, 1);
			label = new Label(0,1,conStr,this.getFt_item_center());
			sheet.addCell(label);
					
			//дͳ�Ʊ�ͷ
			sheet.mergeCells(0, 2, 0, 3);
			label = new Label(0,2,"����",this.getFt_item_center_bold());
			sheet.addCell(label);	
			
			sheet.mergeCells(1, 2, 1, 3);
			label = new Label(1,2,"ҵ������",this.getFt_item_center_bold());
			sheet.addCell(label);	
			
			sheet.mergeCells(2, 2, 2, 3);
			label = new Label(2,2,"���ݱ��",this.getFt_item_center_bold());
			sheet.addCell(label);	
			
			sheet.mergeCells(3, 2, 3, 3);
			label = new Label(3,2,"��Ʒ����",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			sheet.mergeCells(4, 2, 4, 3);
			label = new Label(4,2,"��Ʒ���",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			sheet.mergeCells(5, 2, 5, 3);
			label = new Label(5,2,"����",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			sheet.mergeCells(6, 2, 8, 2);
			label = new Label(6,2,"Ӧ��",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(6,3,"Ӧ�տ�",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(7,3,"�տ�",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(8,3,"���",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			sheet.mergeCells(9, 2, 11, 2);
			label = new Label(9,2,"Ӧ��",this.getFt_item_center_bold());
			sheet.addCell(label);			
			label = new Label(9,3,"Ӧ����",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(10,3,"����",this.getFt_item_center_bold());
			sheet.addCell(label);
			label = new Label(11,3,"���",this.getFt_item_center_bold());
			sheet.addCell(label);
			
			//�����ڳ�
			Map qcMap = clientWlDzdService.getClientQcInfo(client_name,start_date);
			double ysqc = 0;
			double yfqc = 0;
			if(qcMap != null){
				ysqc = qcMap.get("ysqc")==null?0:((Double)qcMap.get("ysqc")).doubleValue();
				yfqc = qcMap.get("yfqc")==null?0:((Double)qcMap.get("yfqc")).doubleValue();
			}
			label = new Label(0,4,"�ڳ�",this.getFt_item_center());
			sheet.addCell(label);
			label = new Label(1,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(2,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(3,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(4,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(5,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(6,4,JMath.round(ysqc,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(7,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(8,4,JMath.round(ysqc,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(9,4,JMath.round(yfqc,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(10,4,"",this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(11,4,JMath.round(yfqc,2),this.getFt_item_right());
			sheet.addCell(label);
			
			
			List list = clientWlDzdService.getClientWlDzdList(start_date,end_date,client_name);
			
			double hj_ys = 0; //�ϼ�Ӧ��
			double hj_sk = 0; //�ϼ��տ�
			double ys_ye = ysqc; //Ӧ�����
			double hj_yf = 0; //�ϼ�Ӧ��
			double hj_fk = 0; //�ϼƸ���
			double yf_ye = yfqc; //Ӧ�����
			
			int k = 5;
			//дͳ������
			if(list != null && list.size()>0){				
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					
					String creatdate = StringUtils.nullToStr(map.get("creatdate"));
					String xwtype = StringUtils.nullToStr(map.get("xwtype"));
					String dj_id = StringUtils.nullToStr(map.get("dj_id"));  //���ݱ��
					
					double je = map.get("je")==null?0:((Double)map.get("je")).doubleValue();
					if(flag.equals("��")){
					//����
					label = new Label(0,k,creatdate,this.getFt_item_centergray());
					sheet.addCell(label);
					//ҵ������
					label = new Label(1,k,xwtype,this.getFt_item_centergray());
					sheet.addCell(label);
					//���ݱ��
					label = new Label(2,k,dj_id,this.getFt_item_centergray());
					sheet.addCell(label);
					sheet.setColumnView(2, 40);
					//��Ʒ����
					label = new Label(3,k,"",this.getFt_item_leftgray());
					sheet.addCell(label);
					sheet.setColumnView(3, 40);
					//��Ʒ���
					label = new Label(4,k,"",this.getFt_item_leftgray());
					sheet.addCell(label);
					sheet.setColumnView(4, 40);
					//����
					label = new Label(5,k,"",this.getFt_item_centergray());
					sheet.addCell(label);
					
					if(xwtype.equals("����") || xwtype.equals("�����˻�")){
						hj_ys += je;
						ys_ye += je;	
						//Ӧ�տ�
						label = new Label(6,k,JMath.round(je,2),this.getFt_item_rightgray());
						sheet.addCell(label);
						//�տ�
						label = new Label(7,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//���
						label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_rightgray());
						sheet.addCell(label);
						//Ӧ����
						label = new Label(9,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//����
						label = new Label(10,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//���
						label = new Label(11,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
					}else if(xwtype.equals("�տ�")){
						hj_sk += je;
						ys_ye -= je;
						//Ӧ�տ�
						label = new Label(6,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//�տ�
						label = new Label(7,k,JMath.round(je,2),this.getFt_item_rightgray());
						sheet.addCell(label);						
						//���
						label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_rightgray());
						sheet.addCell(label);
						//Ӧ����
						label = new Label(9,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//����
						label = new Label(10,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//���
						label = new Label(11,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
					}else if(xwtype.equals("�ɹ�") || xwtype.equals("�ɹ��˻�")){
						hj_yf += je;
						yf_ye += je;
						
                        //Ӧ�տ�
						label = new Label(6,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//�տ�
						label = new Label(7,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);						
						//���
						label = new Label(8,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//Ӧ����
						label = new Label(9,k,JMath.round(je,2),this.getFt_item_rightgray());
						sheet.addCell(label);
						//����
						label = new Label(10,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//���
						label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_rightgray());
						sheet.addCell(label);
					}else if(xwtype.equals("����")){
						hj_fk += je;
						yf_ye -= je;
						
                        //Ӧ�տ�
						label = new Label(6,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//�տ�
						label = new Label(7,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);						
						//���
						label = new Label(8,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//Ӧ����
						label = new Label(9,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//����			
						label = new Label(10,k,JMath.round(je,2),this.getFt_item_rightgray());
						sheet.addCell(label);
                        //���
						label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_rightgray());
						sheet.addCell(label);
					}else if(xwtype.equals("��������(Ӧ��)")){
						hj_ys += je;
						ys_ye += je;
						//Ӧ�տ�
						label = new Label(6,k,JMath.round(je,2),this.getFt_item_rightgray());
						sheet.addCell(label);	
                        //�տ�
						label = new Label(7,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);						
						//���
						label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_rightgray());
						sheet.addCell(label);
                        //Ӧ����
						label = new Label(9,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
						//����			
						label = new Label(10,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
                        //���
						label = new Label(11,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
					}else if(xwtype.equals("��������(Ӧ��)")){
						hj_yf += je;
						yf_ye += je;
                        //Ӧ�տ�
						label = new Label(6,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);	
                        //�տ�
						label = new Label(7,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);						
						//���
						label = new Label(8,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
                        //Ӧ����
						label = new Label(9,k,JMath.round(je,2),this.getFt_item_rightgray());
						sheet.addCell(label);	
                        //����			
						label = new Label(10,k,"",this.getFt_item_rightgray());
						sheet.addCell(label);
                        //���
						label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_rightgray());
						sheet.addCell(label);
					}
					}
					else
					{
                        //����
						label = new Label(0,k,creatdate,this.getFt_item_center());
						sheet.addCell(label);
						//ҵ������
						label = new Label(1,k,xwtype,this.getFt_item_center());
						sheet.addCell(label);
						//���ݱ��
						label = new Label(2,k,dj_id,this.getFt_item_center());
						sheet.addCell(label);
						sheet.setColumnView(2, 40);
						//��Ʒ����
						label = new Label(3,k,"",this.getFt_item_left());
						sheet.addCell(label);
						sheet.setColumnView(3, 40);
						//��Ʒ���
						label = new Label(4,k,"",this.getFt_item_left());
						sheet.addCell(label);
						sheet.setColumnView(4, 40);
						//����
						label = new Label(5,k,"",this.getFt_item_center());
						sheet.addCell(label);
						
						if(xwtype.equals("����") || xwtype.equals("�����˻�")){
							hj_ys += je;
							ys_ye += je;	
							//Ӧ�տ�
							label = new Label(6,k,JMath.round(je,2),this.getFt_item_right());
							sheet.addCell(label);
							//�տ�
							label = new Label(7,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//���
							label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_right());
							sheet.addCell(label);
							//Ӧ����
							label = new Label(9,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//����
							label = new Label(10,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//���
							label = new Label(11,k,"",this.getFt_item_right());
							sheet.addCell(label);
						}else if(xwtype.equals("�տ�")){
							hj_sk += je;
							ys_ye -= je;
							//Ӧ�տ�
							label = new Label(6,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//�տ�
							label = new Label(7,k,JMath.round(je,2),this.getFt_item_right());
							sheet.addCell(label);						
							//���
							label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_right());
							sheet.addCell(label);
							//Ӧ����
							label = new Label(9,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//����
							label = new Label(10,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//���
							label = new Label(11,k,"",this.getFt_item_right());
							sheet.addCell(label);
						}else if(xwtype.equals("�ɹ�") || xwtype.equals("�ɹ��˻�")){
							hj_yf += je;
							yf_ye += je;
							
	                        //Ӧ�տ�
							label = new Label(6,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//�տ�
							label = new Label(7,k,"",this.getFt_item_right());
							sheet.addCell(label);						
							//���
							label = new Label(8,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//Ӧ����
							label = new Label(9,k,JMath.round(je,2),this.getFt_item_right());
							sheet.addCell(label);
							//����
							label = new Label(10,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//���
							label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_right());
							sheet.addCell(label);
						}else if(xwtype.equals("����")){
							hj_fk += je;
							yf_ye -= je;
							
	                        //Ӧ�տ�
							label = new Label(6,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//�տ�
							label = new Label(7,k,"",this.getFt_item_right());
							sheet.addCell(label);						
							//���
							label = new Label(8,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//Ӧ����
							label = new Label(9,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//����			
							label = new Label(10,k,JMath.round(je,2),this.getFt_item_right());
							sheet.addCell(label);
	                        //���
							label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_right());
							sheet.addCell(label);
						}else if(xwtype.equals("��������(Ӧ��)")){
							hj_ys += je;
							ys_ye += je;
							//Ӧ�տ�
							label = new Label(6,k,JMath.round(je,2),this.getFt_item_right());
							sheet.addCell(label);	
	                        //�տ�
							label = new Label(7,k,"",this.getFt_item_right());
							sheet.addCell(label);						
							//���
							label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_right());
							sheet.addCell(label);
	                        //Ӧ����
							label = new Label(9,k,"",this.getFt_item_right());
							sheet.addCell(label);
							//����			
							label = new Label(10,k,"",this.getFt_item_right());
							sheet.addCell(label);
	                        //���
							label = new Label(11,k,"",this.getFt_item_right());
							sheet.addCell(label);
						}else if(xwtype.equals("��������(Ӧ��)")){
							hj_yf += je;
							yf_ye += je;
	                        //Ӧ�տ�
							label = new Label(6,k,"",this.getFt_item_right());
							sheet.addCell(label);	
	                        //�տ�
							label = new Label(7,k,"",this.getFt_item_right());
							sheet.addCell(label);						
							//���
							label = new Label(8,k,"",this.getFt_item_right());
							sheet.addCell(label);
	                        //Ӧ����
							label = new Label(9,k,JMath.round(je,2),this.getFt_item_right());
							sheet.addCell(label);	
	                        //����			
							label = new Label(10,k,"",this.getFt_item_right());
							sheet.addCell(label);
	                        //���
							label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_right());
							sheet.addCell(label);
						}
					}
					if(flag.equals("��")){
						if(xwtype.equals("����") || xwtype.equals("�����˻�") || xwtype.equals("�ɹ�") || xwtype.equals("�ɹ��˻�")){
							List mxList = clientWlDzdService.getDjMxList(dj_id,xwtype);
							if(mxList != null && mxList.size()>0){
								for(int m=0;m<mxList.size();m++){
									Map mxMap = (Map)mxList.get(m);
									
									k++;
									double xj = mxMap.get("xj")==null?0:((Double)mxMap.get("xj")).doubleValue();
									//��Ʒ����
									label = new Label(3,k,StringUtils.nullToStr(mxMap.get("product_name")),this.getFt_item_left());
									sheet.addCell(label);	
									//��Ʒ���
									label = new Label(4,k,StringUtils.nullToStr(mxMap.get("product_xh")),this.getFt_item_left());
									sheet.addCell(label);
									//����
									label = new Label(5,k,StringUtils.nullToStr(mxMap.get("nums")),this.getFt_item_center());
									sheet.addCell(label);
									
									if(xwtype.equals("����") || xwtype.equals("�����˻�")){	
										//Ӧ�տ�
										label = new Label(6,k,JMath.round(xj,2),this.getFt_item_right());
										sheet.addCell(label);
                                        //�տ�
										label = new Label(7,k,"",this.getFt_item_right());
										sheet.addCell(label);						
										//���
										label = new Label(8,k,"",this.getFt_item_right());
										sheet.addCell(label);
				                        //Ӧ����
										label = new Label(9,k,"",this.getFt_item_right());
										sheet.addCell(label);	
				                        //����			
										label = new Label(10,k,"",this.getFt_item_right());
										sheet.addCell(label);
				                        //���
										label = new Label(11,k,"",this.getFt_item_right());
										sheet.addCell(label);
										
									}else if(xwtype.equals("�ɹ�") || xwtype.equals("�ɹ��˻�")){
                                        //Ӧ�տ�
										label = new Label(6,k,"",this.getFt_item_right());
										sheet.addCell(label);
                                        //�տ�
										label = new Label(7,k,"",this.getFt_item_right());
										sheet.addCell(label);						
										//���
										label = new Label(8,k,"",this.getFt_item_right());
										sheet.addCell(label);
				                        //Ӧ����
										label = new Label(9,k,JMath.round(xj,2),this.getFt_item_right());
										sheet.addCell(label);
                                        //����			
										label = new Label(10,k,"",this.getFt_item_right());
										sheet.addCell(label);
				                        //���
										label = new Label(11,k,"",this.getFt_item_right());
										sheet.addCell(label);
									}
								}
							}
						}
					}
					
					k++;
				}
			}
			
			//д�ϼ�
			label = new Label(0,k,"�ϼ�",this.getFt_item_center());
			sheet.addCell(label);
			
//			ҵ������
			label = new Label(1,k,"",this.getFt_item_center());
			sheet.addCell(label);
			//���ݱ��
			label = new Label(2,k,"",this.getFt_item_center());
			sheet.addCell(label);
			//��Ʒ����
			label = new Label(3,k,"",this.getFt_item_left());
			sheet.addCell(label);
			//��Ʒ���
			label = new Label(4,k,"",this.getFt_item_left());
			sheet.addCell(label);
			//����
			label = new Label(5,k,"",this.getFt_item_center());
			sheet.addCell(label);
			
			
			label = new Label(6,k,JMath.round(hj_ys,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(7,k,JMath.round(hj_sk,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(8,k,JMath.round(ys_ye,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(9,k,JMath.round(hj_yf,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(10,k,JMath.round(hj_fk,2),this.getFt_item_right());
			sheet.addCell(label);
			label = new Label(11,k,JMath.round(yf_ye,2),this.getFt_item_right());
			sheet.addCell(label);
			
		}catch(Exception ex){
			log.info(ex);
		}
	}

	public ClientWlDzdService getClientWlDzdService() {
		return clientWlDzdService;
	}

	public void setClientWlDzdService(ClientWlDzdService clientWlDzdService) {
		this.clientWlDzdService = clientWlDzdService;
	}

}
