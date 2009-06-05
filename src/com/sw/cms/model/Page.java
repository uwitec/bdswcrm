package com.sw.cms.model;

import java.util.List;

/**
 * ��״�ķ�ҳ����
 * @author liyt
 *
 */

public class Page {
	
	private int totalNum;  //�ܼ�¼��
	private int curPage;   //��ǰҳ
	private int perPage;   //ÿҳ��¼��
	private int totalPage; //��ҳ��
	
	private List results;   //��ѯ���
	private String formName; //��������form����
	
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getPerPage() {
		return perPage;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List getResults() {
		return results;
	}
	public void setResults(List results) {
		this.results = results;
	}
	public String getFormName() {
		return formName;
	}
	public void setFormName(String formName) {
		this.formName = formName;
	}
	
	/**
	 * ���ط���ű�
	 * @param formName
	 * @return
	 */
	public String getPageScript(String formName){
		String pageScript = "";
		
		pageScript = "<script type=\"text/javascript\">\n";
		
		pageScript += "function goPage(pageNum){\n";
		pageScript += "		document." + formName + ".curPage.value = pageNum;\n";
		pageScript += "		document." + formName + ".submit();\n";
		pageScript += "}\n";
		
		pageScript += "function OnlyNumber(){\n";
		pageScript += "		if ( !(((window.event.keyCode >= 48) && (window.event.keyCode <= 57)) || (window.event.keyCode == 13) || (window.event.keyCode == 46) || (window.event.keyCode == 45))){\n";
		pageScript += "			window.event.keyCode = 0 ;\n";
		pageScript += "	    }\n";
		pageScript += "}\n";		
		
		pageScript += "function jumping(){\n";
		pageScript += "		var max = " + this.totalPage + ";\n";
		pageScript += "		if(document." + formName + ".showPage.value.length==0){\n";
		pageScript += "			alert(\"��������תҳ��\");\n";
		pageScript += "			return;\n";
		pageScript += "		}else if(document." + formName + ".showPage.value>max){\n";
		pageScript += "			alert(\"������תҳ�����\");\n";
		pageScript += "			return;\n";		
		pageScript += "		}else if(document." + formName + ".showPage.value <= \"0\"){\n";
		pageScript += "			alert(\"������תҳ�����\");\n";
		pageScript += "			return;\n";
		pageScript += "		}\n";
		pageScript += "		document." + formName + ".curPage.value = document." + formName + ".showPage.value;\n";		
		pageScript += "		document." + formName + ".submit();\n";
		pageScript += "}\n";
		
		pageScript += "</script>\n";
				
		pageScript += "<input  name=\"curPage\" type=\"hidden\" value=\"" + this.curPage + "\">&nbsp;";
		pageScript += "������ <input  name=\"showPage\" type=\"text\" size=\"3\" onkeypress=\"OnlyNumber()\">ҳ&nbsp;";
		pageScript += "<img src=\"./images/button_go.gif\" width=\"35\" height=\"20\" align=\"absmiddle\" onclick=\"javascript:jumping()\" style=\"cursor:hand\">&nbsp;&nbsp;&nbsp;&nbsp;";
		
		pageScript += "��" + this.totalNum + "����ÿҳ" + this.perPage+ "��     &nbsp;&nbsp;��" + this.curPage +"ҳ/��" + this.totalPage + "ҳ&nbsp;&nbsp";
		
		if(this.curPage==1){ //�����ǰ�ǵ�һҳ
			if(this.totalPage>1){  //�����ҳ������1,��ʾ��һҳ��ĩҳ��������ʾ�κ���Ϣ
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(\"" + (this.curPage+1) + "\")'>��һҳ</a>&nbsp;&nbsp";
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(\"" + this.totalPage + "\")'>ĩ&nbsp;&nbsp;ҳ</a>";
			}		
		}else{ //�����ǰ���ǵ�һҳ
			if(this.curPage == this.totalPage){  //�����ǰҳ�����һҳ
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(1)'>��&nbsp;&nbsp;ҳ</a>&nbsp;&nbsp";
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(\"" + (this.curPage-1) + "\")'>��һҳ</a>";
			}else{
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(1)'>��&nbsp;&nbsp;ҳ</a>&nbsp;&nbsp";
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(\"" + (this.curPage-1) + "\")'>��һҳ</a>&nbsp;&nbsp";
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(\"" + (this.curPage+1) + "\")'>��һҳ</a>&nbsp;&nbsp";
				pageScript += "<a href='#' class=\"xxlb\" onclick='goPage(\"" + this.totalPage + "\")'>ĩ&nbsp;&nbsp;ҳ</a>";
			}
		}
		
		return pageScript;		
	}
	
	/**
	 * ���ط�ҳ�ű���Ĭ��formΪmyform
	 * @return
	 */
	public String getPageScript() {
		return getPageScript("myform");
	}
	
}
