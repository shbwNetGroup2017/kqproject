<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8" />
<title></title>
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
<!-- 下拉框 -->
<link rel="stylesheet" href="static/css/chosen.css" />
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<style type="text/css">
		.portmsg{
		   color:red;
		   font-size: 15px;
		}
</style>
<script type="text/javascript">
	$(top.hangge());
	//保存
	function save() {
		if($("#spbm").val()==""){
			$("#spbm").tips({
				side:3,
	            msg:'商品编码不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#spbm").focus();
			return false;
		}
		if($("#yspbm").val()==""){
			$("#yspbm").tips({
				side:3,
	            msg:'原商品编码不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#yspbm").focus();
			return false;
		}
		if($("#yspmc").val()==""){
			$("#yspmc").tips({
				side:3,
	            msg:'原商品名称不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#yspmc").focus();
			return false;
		}
		if($("#sl").val()==""){
			$("#sl").tips({
				side:3,
	            msg:'税率不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sl").focus();
			return false;
		}
		if($("#sjly").val()==""){
			$("#sjly").tips({
				side:3,
	            msg:'数据来源不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sjly").focus();
			return false;
		}
		//提交表单
		$.ajax({
			type: "POST",
			url: '<%=basePath%>server/commodity/saveCommodityCodeInfo.do',
	    	data: $("#CommodityCodeInfoForm").serialize(),
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.msg){
					alert('保存成功！');
					$("#zhongxin").hide();
					top.Dialog.close();
				 }else if("validateError" == data.msg){
					 alert('验证失败!');
				 }else if("not exit"==data.msg){
					 alert('原商品编码不存在!');
				 }else{
					 alert('保存失败!');
				 }
			}
		});
	}
</script>
</head>
<body>
	<form action="server/customer/saveCommodityCodeInfo.do" name="CommodityCodeInfoForm" id="CommodityCodeInfoForm" method="post">
		<div id="zhongxin" style="text-align: center;">
			<table style="margin: auto;">
				<tr>
					<td><label>商品统一编码：</label></td>
					<td><input type="text" name="spbm" id="spbm" maxlength="32" value="${pd.spbm}" /><input type="hidden" name="id" id="id" maxlength="20" value="${pd.id}"  /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>原商品编码：</label></td>
					<td><input type="text" name="yspbm" id="yspbm" maxlength="32" value="${pd.yspbm}" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>商品名称：</label></td>
					<td><input type="text" name="yspmc" id="yspmc" maxlength="64" value="${pd.yspmc}" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>税率：</label></td>
					<td>
						<select name="sl" id="sl">
					        <option value="" >请选择</option>
							<option value="0.170" <c:if test="${pd.sl==0.170}">selected</c:if>>0.170</option>
							<option value="0.130" <c:if test="${pd.sl==0.130}">selected</c:if>>0.130</option>
							<option value="0.110" <c:if test="${pd.sl==0.110}">selected</c:if>>0.110</option>
							<option value="0.000" <c:if test="${pd.sl==0.000}">selected</c:if>>0.000</option>
							<option value="0.056" <c:if test="${pd.sl==0.056}">selected</c:if>>0.056</option>
							<option value="0.030" <c:if test="${pd.sl==0.030}">selected</c:if>>0.030</option>
							<option value="0.060" <c:if test="${pd.sl==0.060}">selected</c:if>>0.060</option>
					    </select>
					</td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>数据来源：</label></td>
					<td>
					    <select name="sjly" id="sjly">
					        <option value="" >请选择</option>
							<option value="0" <c:if test="${pd.sjly==0}">selected</c:if>>ODS数据仓库</option>
							<option value="1" <c:if test="${pd.sjly==1}">selected</c:if>>Oracle ERP系统</option>
							<option value="2" <c:if test="${pd.sjly==2}">selected</c:if>>进件系统</option>
							<option value="3" <c:if test="${pd.sjly==3}">selected</c:if>>商派</option>
					    </select>
					</td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2"><a class="btn btn-mini btn-primary" onclick="save();">保存</a> <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a></td>
				</tr>
			</table>
		</div>

		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <img src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green"></h4>
		</div>

	</form>

	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
</body>
</html>