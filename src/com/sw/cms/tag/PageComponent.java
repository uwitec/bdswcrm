package com.sw.cms.tag;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.sw.cms.model.Page;

/**
 * ��ҳ��ǩ
 * @author liyt
 *
 */

public class PageComponent extends Component {
	
	private Log log = LogFactory.getLog(getClass());
	
	

	public PageComponent(OgnlValueStack stack) {
		super(stack);
	}

	private String value;
	private String formname = "myform";
	

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getFormname() {
		return formname;
	}

	public void setFormname(String formname) {
		this.formname = formname;
	}

	public boolean start(Writer writer) {
		boolean result = super.start(writer);
		
		int totalNum = 0;  //�ܼ�¼��
		int curPage = 1;   //��ǰҳ
		int perPage = 15;   //ÿҳ��¼��
		int totalPage = 0; //��ҳ��	
		
		if (value == null) {
			value = "top";
		} else if (altSyntax()) {
			if (value.startsWith("%{") && value.endsWith("}")) {
				value = value.substring(2, value.length() - 1);
			}
		}

		Page page = (Page) this.getStack().findValue(value);
		
		totalNum = page.getTotalNum();
		curPage = page.getCurPage();
		perPage = page.getPerPage();
		totalPage = page.getTotalPage();

		StringBuffer pageScript = new StringBuffer();
		
		pageScript.append("<STYLE TYPE=\"text/css\">");
		pageScript.append("<!--");
		pageScript.append("A:link");
		pageScript.append("{");
		pageScript.append("    color:#000000;");
		pageScript.append("    TEXT-DECORATION: none;");
		pageScript.append("}");
		pageScript.append("A:active");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          
		pageScript.append("{");
		pageScript.append("    COLOR:#FFFFFF;");
		pageScript.append("    FONT-SIZE: 9pt;");
		pageScript.append("    TEXT-DECORATION:none;");
		pageScript.append("}");
		pageScript.append("A:visited");
		pageScript.append("{");
		pageScript.append("	color:#696969;");
		pageScript.append("	TEXT-DECORATION: none;");
		pageScript.append("}");
		pageScript.append("A:hover");
		pageScript.append("{");
		pageScript.append("    COLOR:#000000;");
		pageScript.append("    FONT-SIZE: 9pt; ");
		pageScript.append("    TEXT-DECORATION: underline");
		pageScript.append("}");
		pageScript.append("--> ");
		pageScript.append("</STYLE>");
		
		pageScript.append("<script type=\"text/javascript\">\n");
		
		pageScript.append("function goPage(pageNum){\n");
		pageScript.append("		document." + formname + ".curPage.value = pageNum;\n");
		pageScript.append("		document." + formname + ".submit();\n");
		pageScript.append("}\n");
		
		pageScript.append("function OnlyNumber(){\n");
		pageScript.append("		if ( !(((window.event.keyCode >= 48) && (window.event.keyCode <= 57)) || (window.event.keyCode == 13) || (window.event.keyCode == 46) || (window.event.keyCode == 45))){\n");
		pageScript.append("			window.event.keyCode = 0 ;\n");
		pageScript.append("	    }\n");
		pageScript.append("}\n");		
		
		pageScript.append("function jumping(){\n");
		pageScript.append("		var max = " + totalPage + ";\n");
		pageScript.append("		if(document." + formname + ".showPage.length==0){\n");
		pageScript.append("			alert(\"��������תҳ��\");\n");
		pageScript.append("			return;\n");
		pageScript.append("		}else if(document." + formname + ".showPage.value>max){\n");
		pageScript.append("			alert(\"������תҳ�����\");\n");
		pageScript.append("			return;\n");		
		pageScript.append("		}else if(document." + formname + ".showPage.value <= \"0\"){\n");
		pageScript.append("			alert(\"������תҳ�����\");\n");
		pageScript.append("			return;\n");
		pageScript.append("		}\n");
		pageScript.append("		document." + formname + ".curPage.value = document." + formname + ".showPage.value;\n");
		pageScript.append("		document." + formname + ".submit();\n");
		pageScript.append("}\n");
		
		pageScript.append("</script>\n");
		
		pageScript.append("<input  name=\"curPage\" type=\"hidden\" value=\"" + curPage + "\">&nbsp;&nbsp;&nbsp;&nbsp;");
		pageScript.append("������&nbsp;<input  name=\"showPage\" type=\"text\" size=\"2\" onkeypress=\"OnlyNumber()\">&nbsp;ҳ&nbsp;&nbsp;&nbsp;");
		pageScript.append("<a href=\"#\" title=\"��ת������ҳ\" onclick=\"javascript:jumping();\">��ת</a>&nbsp;&nbsp;&nbsp;&nbsp;");
		
		pageScript.append("��" + totalNum + "����ÿҳ" + perPage+ "��     &nbsp;&nbsp;��" + curPage +"ҳ/��" + totalPage + "ҳ&nbsp;&nbsp");
		
		if(curPage==1){ //�����ǰ�ǵ�һҳ
			if(totalPage>1){  //�����ҳ������1,��ʾ��һҳ��ĩҳ��������ʾ�κ���Ϣ
				pageScript.append("<a href='#' onclick='goPage(\"" + (curPage+1) + "\");'>��һҳ</a>&nbsp;&nbsp");
				pageScript.append("<a href='#' onclick='goPage(\"" + totalPage + "\");'>ĩ&nbsp;&nbsp;ҳ</a>&nbsp;&nbsp");
			}		
		}else{ //�����ǰ���ǵ�һҳ
			if(curPage == totalPage){  //�����ǰҳ�����һҳ
				pageScript.append("<a href='#' onclick='goPage(1);'>��&nbsp;&nbsp;ҳ</a>&nbsp;&nbsp");
				pageScript.append("<a href='#' onclick='goPage(\"" + (curPage-1) + "\");'>��һҳ</a>&nbsp;&nbsp");
			}else{
				pageScript.append("<a href='#' onclick='goPage(1)'>��&nbsp;&nbsp;ҳ</a>&nbsp;&nbsp");
				pageScript.append("<a href='#' onclick='goPage(\"" + (curPage-1) + "\");'>��һҳ</a>&nbsp;&nbsp");
				pageScript.append("<a href='#' onclick='goPage(\"" + (curPage+1) + "\");'>��һҳ</a>&nbsp;&nbsp");
				pageScript.append("<a href='#' onclick='goPage(\"" + totalPage + "\");'>ĩ&nbsp;&nbsp;ҳ</a>&nbsp;&nbsp");
			}
		}
		
		try {
			writer.write(pageScript.toString());
		} catch (IOException e) {
			log.error(e);
		}
		
		return result;
	}

}
