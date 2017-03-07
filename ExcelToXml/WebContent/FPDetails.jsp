<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="s" uri="/s"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta   HTTP-EQUIV= "Pragma "   CONTENT= "no-cache ">
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript">
        $(function(){
        	var height=$("#div2").height();
        	$("#div1").css("height",height);
        	var height=$("#div4").height();
        	$("#div3").css("height",height);
        	$("#div5").css("height",height);
        })
</script>
<title>Excel数据转换XML</title>
</script>
</head>
<body>
	<!-- 文件内容 -->
		<div id="node_div" style="width: 100%;">
			<s:iterator value="#request.schlList" var="excel" status="index">
		    <s:if test="#index.first == true">
		    		<div style="font-size: 13px;text-align: left;background-color:rgb(246, 120, 120);">
					<div id="div1" style="text-align:center; color:#ED7F22;border:1px solid #dfdfdf; width:40px;height: auto;float: left;background-color:#FFF;"><br>购<br>买<br>方<br></div>
				    <div id="div2" style="border:1px solid #dfdfdf;width:60%;height: auto;float: left;background-color:#FFF;">
				        <div style="padding-bottom: 5px;"><div style="float: left;color:#ED7F22;">购方名称：</div><div style="color:blue;"><s:property value='#excel.GFMC'></s:property></div></div>
					    <div style="padding-bottom: 5px;"><div style="float: left;color:#ED7F22;">购方税号：</div><div style="color:blue;"><s:property value='#excel.GFSBH'></s:property></div></div>
					    <div style="padding-bottom: 5px;"><div style="float: left;color:#ED7F22;">购方地址电话：</div> <div style="color:blue;"><s:property value='#excel.GFDZDH'></s:property></div></div>
					    <div style="padding-bottom: 5px;"><div style="float: left;color:#ED7F22;">购方银行帐号：</div><div style="color:blue;"><s:property value='#excel.GFYHZH'></s:property></div></div>
					</div>
				</div><br>
				<table class="table" width="100%" layoutH="170" border="1px" rules=none >
				<thead>
				<tr style="font-size: 13px;text-align: center;color:#ED7F22">
					<td style="width:200px;">货物或应税劳务、服务名称</td>
					<td style="width:70px;">规格型号</td>
					<td style="width:50px;">单位</td>
					<td style="width:70px;">数量</td>
					<td style="width:70px;">单价（不含税）</td>
					<td style="width:60px;">金额（不含税）</td>
					<td style="width:50px;">税率</td>
					<td style="width:80px;">税额</td>
				</tr>
				</thead>
				</s:if>
					<tr style="font-size: 13px;color:blue;">
						<td style="width:200px;"><s:property value='#excel.HWMC'></s:property></td>
						<td style="width:70px;"><s:property value='#excel.GG'></s:property></td>
						<td style="width:50px;"><s:property value='#excel.JLDW'></s:property></td>
						<td style="width:70px;"><s:property value='#excel.SPSL'></s:property></td>
						<td><s:property value='#excel.DJ_BHS'></s:property></td>
						<td style="width:70px;"><s:property value='#excel.JE_BHS_FPMX'></s:property></td>
						<td style="width:50px;"><s:property value='#excel.SL'></s:property></td>
                        <td style="width:80px;"><s:property value='#excel.SE'></s:property></td>
					</tr>
				</s:iterator>
				    <tr>
				    <td style="text-align: center;color:#ED7F22">&nbsp;合&nbsp;&nbsp;&nbsp;计&nbsp;</td>
				    <td></td>
				    <td></td>
				    <td></td>
				    <td></td>
				    <td style="text-align: left;color:blue"><s:property value='#request.JE_BHS'></s:property></td>
				    <td></td>
				    <td style="text-align: left;color:blue"><s:property value='#request.SE'></s:property></td>
				    </tr>
				    <tr>
				    <td style="text-align: center;color:#ED7F22">&nbsp;价税合计&nbsp;</td>
				    <td></td>
				    <td></td>
				    <td></td>
				    <td></td>
				    <td style="text-align: left;color:blue"></td>
				    <td></td>
				    <td style="text-align: left;color:blue"><s:property value='#request.ZJE'></s:property>（小写）</td>
				    </tr>
				<tr style="font-size: 13px;width:200px;text-align: center;background-color:#FFF;">
				 		<td style="color:blue;">收款人：<s:property value='#excel.SKR'></s:property></td>
				 		<td style="margin-left:40px;color:blue;">复核：<s:property value='#excel.FHR'></s:property></td>
						<td></td>
				        <td></td>
				        <td></td>
				        <td></td>
				        <td></td>
				        <td></td>
				</tr>
			</table>
				<div style="font-size: 13px;text-align: left;background-color:rgb(246, 120, 120);">
					<div id="div3" style="text-align:center; color:#ED7F22;border:1px solid #dfdfdf; width:40px;height: auto;float: left;background-color:#FFF;"><br>销<br>售<br>方<br></div>
				    <div id="div4" style="border:1px solid #dfdfdf;width:60%;height: auto;float: left;background-color:#FFF;">
				        <div style="padding-bottom: 5px;"><div style="float: left;color:#ED7F22;">销方名称：</div><div style="color:blue;"></div></div><br>
					    <div style="padding-bottom: 5px;"><div style="float: left;color:#ED7F22;">销方税号：</div><div style="color:blue;"></div></div><br>
					    <div style="padding-bottom: 5px;"><div style="float: left;color:#ED7F22;">销方地址电话：</div> <div style="color:blue;"></div></div><br>
					    <div style="padding-bottom: 5px;"><div style="float: left;color:#ED7F22;">销方银行帐号：</div><div style="color:blue;"></div></div><br>
					</div>
				<div id="div5" style="text-align:left; color:#ED7F22;border:1px solid #dfdfdf; width:35.2%;height: auto;float: left;background-color:#FFF;">备注：<s:property value='#request.BZ'></s:property></div>
				</div>
				<br>
		</div>
	<div style="margin-top:80px;margin-left:900px;width:80px;height: 30px;text-align:center;
	       font-size: 17px;border-radius:5px;background-color: blue;padding: 5px;"><a href="xml/fpHz.action" style="color:#FFF;">返回</a></div>

</body>
</html>