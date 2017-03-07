<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>预览</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link href="static/css/bootstrap.min.css" rel="stylesheet" />
	<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());
	</script>
	<style type="text/css">
        @page {
            size: 297mm 210mm;;

        }

        @media (max-width: 297mm) {
            @page {
                size: 297mm 210mm;;

            }
        }
        body {
            margin-left: 1px;
            margin-right: 1px;
            font-family: Arial Unicode MS;
            font-size: 12px;
        }

        span {
            color: black;
        }

        #div {
            width: 570px;
            height: 595px;
        }

        #bodyer {
            height: 400px;
        }

        h2 {
            color: #9E520A;
        }

        .header_t1 {
            height: 93px;
            width: 100%;
            margin-left: 2px;
            padding: 5px;
            margin-top: 5px;
        }

        .boyder_t1 {
            border: 1px #9E520A solid;
            margin-left: 2px;
            margin-top: 2px;
            width: 100%;
        }

        .boyder_t2 {

            height: 105px;
            border-bottom: 1px solid #9E520A;
            border-collapse: collapse;

        }

        .boyder_td1 {

            width: 25px;
            height: 105px;
            border-right: 1px solid #da731b;
            font-size: 9pt;

            color: #9E520A;
        }

        .boyder_td2 {
            width: 50%;
            height: 105px;
            border-right: 1px solid #da731b;
        }

        .boyder_tr1 {
            text-align: center;
            vertical-align: top;
            border-top: 0px solid #da731b;
        }

        .td {
            border-right: 1px solid #da731b;
            font-size: 9pt;
            color: #9E520A;
        }

        .mxtd {
            border-right: 1px solid #da731b;
            font-size: 9pt;
            padding: 2px;
        }

        .boyder_t3 {
            width: 100%;
            border-bottom: 1px solid #da731b;
        }

        .footer_t1 {
            margin-left: 2px;

            height: 65px;
        }

        .titletd {
            font-size: 9pt;
            color: #9E520A;
        }

        .notd {
            font-size: 9pt;

            color: black;
        }

        .notdjym {
            font-size: 9pt;
            color: black;
            width: 200px;
            padding: 5px 5px;
        }

        .title {
            font-size: 16pt;

            color: #9E520A;

        }


    </style>
  </head>
  
  <body>
    	<div id="tt" class="easyui-tabs" style="width:1200px;min-height:200px;">
	    <div style="padding:20px;">
	        <div id="zhongxin" style="text-align: center;">
	        <table class="d2" style="width: 100%;table-layout:fixed;">
	            <tr>
	                <td>
	                    <div class="boyder">
	                        <table class="boyder_t1"
	                               style="border: solid 1px  #da731b; border-collapse: collapse;border: 1px #9E520A solid;margin-left: 2px;margin-top: 2px;width: 100%;" cellpadding="0"
	                               cellspacing="0">
	                            <tr>
	                                <td class="boyder_t2" style="height: 205px; border-bottom: 1px solid #9E520A;border-collapse: collapse;">
	                                    <table style="width: 100%;height: 100%;table-layout:fixed;" cellspacing="0" cellpadding="0">
	                                        <tr style="height:0;">
	                                            <th style="width:25px"></th>
	                                            <th></th>
	                                            <th style="width:25px"></th>
	                                            <th style="width:300px"></th>
	                                        </tr>
	                                        <tr>
	                                            <td class="boyder_td1" align="center" style="width: 25px;height: 105px;border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">购<br/>买<br/>方<br/></td>
	                                            <td class="boyder_td2" style=" width: 50%;height: 105px;border-right: 1px solid #da731b;">
	                                                <table cellpadding="2px" style="width: 100%;table-layout:fixed;">
	                                                    <tr>
	                                                        <td class="titletd" style=" font-size: 9pt;color: #9E520A;font-size: 9pt;color: #9E520A;">名&emsp;&emsp;&emsp;&emsp;称：<span style="color: black;">${pd.gfmc}</span>
	                                                        </td>
	                                                    </tr>
	                                                    <tr>
	                                                        <td class="titletd" style=" font-size: 9pt;color: #9E520A;font-size: 9pt;color: #9E520A;">纳税人识别号：<span style="color: black;">${pd.gfsh==null?"":pd.gfsh}</span></td>
	                                                    </tr>
	                                                    <tr>
	                                                        <td class="titletd" style=" font-size: 9pt;color: #9E520A;font-size: 9pt;color: #9E520A;">&emsp;地址、电话：<span style="color: black;">${pd.gfdz==null?"":pd.gfdz}&emsp;&emsp;${pd.gfdh==null?"":pd.gfdh}</span>
	                                                        </td>
	                                                    </tr>
	                                                    <tr>
	                                                        <td class="titletd" style=" font-size: 9pt;color: #9E520A;font-size: 9pt;color: #9E520A;">开户行及账号：<span style="color: black;">${pd.gfyh==null?"":pd.gfyh}&emsp;&emsp;${pd.gfyhzh==null?"":pd.gfyhzh}</span>
	                                                        </td>
	                                                    </tr>
	                                                </table>
	                                            </td>
	                                            <td class="boyder_td1" style="width: 25px;height: 105px;border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;text-align: center">密<br/><br/> 码<br/><br/>区
	                                            </td>
	                                            <td width="200px" align="left"
	                                                style="font-family: Courier New; font-size: 12pt;padding: 5px 5px;vertical-align: top;WORD-WRAP: break-word">
	
	                                            </td>
	                                        </tr>
	                                    </table>
	                                </td>
	                            </tr>
	
	                            <tr>
	                                <td>
	                                    <table class="boyder_t3" style="width: 100%;height: 100%; table-layout: fixed;border-bottom: 1px solid #da731b;"
	                                           cellpadding="0" cellspacing="0">
	                                        <tr class="boyder_tr1" style="text-align: center;vertical-align: top;border-top: 0px solid #da731b;">
	                                            <td class="td" width="200x" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">货物或应税劳务、服务名称</td>
	                                            <td width="100px" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">规格型号</td>
	                                            <td width="70px" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">单位</td>
	                                            <td width="100px" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">数量</td>
	                                            <td width="110px" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">单价</td>
	                                            <td width="130px" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">金额</td>
	                                            <td width="30px" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;">税率</td>
	                                            <td class="titletd" width="130px" style=" font-size: 9pt;color: #9E520A;font-size: 9pt;color: #9E520A;">税额</td>
	                                        </tr>
	                                        <!-- 开始循环 -->	
											<c:choose>
												<c:when test="${not empty kpmxList}">
													<c:forEach items="${kpmxList}" var="kpmx">
				                                        <tr>
				                                            <td class="mxtd" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;">${kpmx.spmc}</td>
				                                            <td class="mxtd" width="64px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;">${kpmx.ggxh==null?"":kpmx.ggxh}</td>
				                                            <td class="mxtd" width="50px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;">${kpmx.dw==null?"":kpmx.dw}</td>
				                                            <td class="mxtd" width="30px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;">
				                                                ${kpmx.spsl==null?"":kpmx.spsl}
				                                            </td>
				                                            <td class="mxtd" width="110px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;">
				                                                ${kpmx.bhsdj==null?"":kpmx.bhsdj}
				                                            </td>
				                                            <td class="mxtd" width="110px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;">	                                               
				                                               <fmt:formatNumber value="${kpmx.bhsspje==null?'':kpmx.bhsspje}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>  
				                                            </td>
				                                            <td class="mxtd" width="30px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;">${kpmx.sl}
				                                            </td>
				                                            <td width="150px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;">
				                                            	<fmt:formatNumber value="${kpmx.spse}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber>  
				                                            </td>
				                                        </tr>
				                                	</c:forEach>
												</c:when>
												<c:otherwise>
													<tr>
														 	<td class="mxtd" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;">(详见销货清单)</td>
				                                            <td class="mxtd" width="64px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;"></td>
				                                            <td class="mxtd" width="50px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;"></td>
				                                            <td class="mxtd" width="30px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;"></td>
				                                            <td class="mxtd" width="110px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;"></td>
				                                            <td class="mxtd" width="110px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;"></td>
				                                            <td class="mxtd" width="30px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;"></td>
				                                            <td width="150px" style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;"></td>
													</tr>
												</c:otherwise>
											</c:choose>
	                                        <tr class="boyder_tr1" style="text-align: center;vertical-align: bottom;border-top: 0px solid #da731b;">
	                                            <td class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;"><br/><br/><br/>合&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;计</td>
	                                            <td class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;"><br/><br/><br/></td>
	                                            <td class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;"><br/><br/><br/></td>
	                                            <td class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;"><br/><br/><br/></td>
	                                            <td class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;"><br/><br/><br/></td>
	                                            <td class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;text-align:right"><br/><br/><br/><span style="color: black;">￥<fmt:formatNumber value="${pd.hjje==null?'':pd.hjje}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></span>
	                                            </td>
	                                            <td class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;"><br/><br/><br/></td>
	                                            <td style="border-right: 1px solid #da731b;font-size: 9pt;padding: 2px;text-align: right;"><br/><br/><br/><span style="color: black;">￥<fmt:formatNumber value="${pd.hjse}" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></span></td>
	                                        </tr>
	                                    </table>
	                                </td>
	                            </tr>
	                            <tr height="30px;">
	                                <td>
	                                    <table class="boyder_t3" cellpadding="2px" cellspacing="0" style="width: 100%;border-bottom: 0px solid #da731b;">
	                                        <tr style="vertical-align: bottom;">
	                                            <td width="336.5px" align="center" class="td" style=" border-right: 1px solid #da731b;font-size: 9pt;color: #9E520A;height: 32px;">价税合计(大写)</td>
	                                            <td align="left" width="260px" valign="center" style="padding-left: 30px;font-size: 9pt;">
	                                                ${pd.dxjshj}
	                                            </td>
	                                            <td align="center"><span style="color: #9E520A;font-size: 9pt;">(小写)</span><span style="color: black;font-size: 9pt;">￥ ${pd.jshj}</span></td>
	                                        </tr>
	                                    </table>
	                                </td>
	                            </tr>
	                        </table>
	                    </div>
	                </td>
	             </tr>
	         </table>
	         <div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>		
	    </div>
	</div>
	
	<!-- 引入 -->
	<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
  </body>
</html>
