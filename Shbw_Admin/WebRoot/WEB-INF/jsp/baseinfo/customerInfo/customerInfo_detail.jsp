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

<script type="text/javascript">
	$(top.hangge());
	//保存
	function save() {
		$("#customerInfoForm").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
</script>
</head>
<body>
	<form action="server/customer/saveCustomerInfo.do" name="customerInfoForm" id="customerInfoForm" method="post">
		<div id="zhongxin" style="text-align: center;">
			<table style="margin: auto;">
				<tr>
					<td><label>客户类型：</label></td>
					<td><select name="gflx" disabled="disabled" id="gflx">
							<option value="企业">企业</option>
							<option value="个人">个人</option>
					</select></td>
				</tr>
				<tr>
					<td><label>客户名称：</label></td>
					<td><input type="text" name="gfmc" readOnly="true" id="gfmc" maxlength="20" value="${customerInfo.gfmc}" /><input type="hidden" name="id" id="customerInfoId" maxlength="20" value="${customerInfo.id}" /></td>
				</tr>
				<tr>
					<td><label>客户税号：</label></td>
					<td><input type="text" name="gfsh" id="gfsh" readOnly="true" maxlength="32" value="${customerInfo.gfsh}" /></td>
				</tr>
				<tr>
					<td><label>客户银行：</label></td>
					<td><input type="text" name="gfyh" id="gfyh" readOnly="true" maxlength="60" value="${customerInfo.gfyh}" /></td>
				</tr>
				<tr>
					<td><label>客户银行账号：</label></td>
					<td><input type="text" name="gfyhzh" id="gfyhzh" readOnly="true" maxlength="18" value="${customerInfo.gfyhzh}" /></td>
				</tr>
				<tr>
					<td><label>客户地址：</label></td>
					<td><input type="text" name="gfdz" id="gfdz" readOnly="true" maxlength="32" value="${customerInfo.gfdz}" /></td>
				</tr>
				<tr>
					<td><label>客户电话：</label></td>
					<td><input type="text" name="gfdh" id="gfdh" readOnly="true" maxlength="60" value="${customerInfo.gfdh}" /></td>
				</tr>
				<%-- <tr>
					<td><label>客户联系人：</label></td>
					<td><input type="text" name="gflxr" id="gflxr" readOnly="true" maxlength="60" value="${customerInfo.gflxr}" /></td>
				</tr>
				<tr>
					<td><label>客户联系人电话：</label></td>
					<td><input type="text" name="gflxrdh" id="gflxrdh" readOnly="true" maxlength="60" value="${customerInfo.gflxrdh}" /></td>
				</tr>
				<tr>
					<td><label>邮寄地址：</label></td>
					<td><input type="text" name="yjdz" id="yjdz" readOnly="true" maxlength="60" value="${customerInfo.yjdz}" /></td>
				</tr>
				<tr>
					<td><label>邮政编码：</label></td>
					<td><input type="text" name="gfyb" id="gfyb" readOnly="true" maxlength="60" value="${customerInfo.gfyb}" /></td>
				</tr> --%>
				
				<!-- <tr>
					<td><label>开票类型：</label></td>
					<td><select name="kplx" id="kplx" disabled="disabled">
							<option value="0">增值税普通发票</option>
							<option value="1">增值税专用发票</option>
							<option value="2">不开</option>
					</select></td>
				</tr> -->
				<!-- 新加原系统ID和数据来源Begin -->
				<tr>
					<td><label>原系统ID：</label></td>
					<td><input type="text" name="gf_yxtid" readOnly="true" id="gf_yxtid" maxlength="32" value="${customerInfo.yxtid}" placeholder="这里输入原系统ID" title="原系统ID" /></td>
				</tr>
				<tr>
					<td><label>数据来源：</label></td>
					<td><select name="sjly" disabled="disabled" id="sjly">//
							<option value="0">ODS数据仓库</option>
							<option value="1">Oracle ERP系统</option>
							<option value="2">进件系统</option>
							<option value="3">商派</option>
					</select></td>
				</tr>
				<!-- 新加原系统ID和数据来源End -->
				<tr>
					<td><label>创建人：</label></td>
					<td><input type="text" name="creator" id="creator" value="${customerInfo.creator}" maxlength="18" readOnly="true" /></td>
				</tr>
				<tr>
					<td><label>创建时间：</label></td>
					<td><input type="text" name="create_date" id="create_date" value="${customerInfo.create_date}" maxlength="18" readOnly="true" /></td>
				</tr>
				<tr>
					<td><label>修改人：</label></td>
					<td><input type="text" name="updator" id="updator" value="${customerInfo.updator}" maxlength="18" readOnly="true" /></td>
				</tr>
				<tr>
					<td><label>修改时间：</label></td>
					<td><input type="text" name="updata_date" id="updata_date" value="${customerInfo.updata_date}" maxlength="18" readOnly="true" /></td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="3"><a class="btn btn-mini btn-info" style="width: 320px;" onclick="top.Dialog.close();">返回</a></td>
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

	<script type="text/javascript">
		$(function() {
			var gflx='${customerInfo.gflx}';
			if(gflx=='企业'){
				$("#gflx").val(0);
			}else if(gflx=='个人'){
				$("#gflx").val(1);
			}
			$("#sjly").val('${customerInfo.sjly}');

		});
	</script>

</body>
</html>