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
</script>
</head>
<body>
	<form action="server/customer/saveCustomerInfo.do" name="customerInfoForm" id="customerInfoForm" method="post">
		<div id="zhongxin" style="text-align: center;">
			<table style="margin: auto;">
				<tr>
					<td><label>用户组名称：</label></td>
					<td><input type="text" name="yhzmc" readOnly="true" id="yhzmc" maxlength="64" value="${pd.yhzmc}" /></td>
				</tr>
				<tr>
					<td><label>用户组描述：</label></td>
					<td><input type="text" name="yhzms" readOnly="true" id="yhzms" maxlength="100" value="${pd.yhzms}" /></td>
				</tr>
				<tr>
					<td><label>状态：</label></td>
					<td>
					<input type="radio" style="margin-top:-10px;width:15px;opacity:1;position: relative;" name="zt" id="zt" value="0" 
					<c:if test="${pd.zt==0}">checked='checked'</c:if>  disabled
					/>启用
					<input type="radio" style="margin-top:-10px;width:15px;opacity:1;;position: relative;margin-left:50px;" name="zt" id="zt" value="1"
					<c:if test="${pd.zt==1}">checked='checked'</c:if>  disabled
					/>锁定
				    </td>
					<td></td>
				</tr>
				<tr>
					<td><label>创建人：</label></td>
					<td><input type="text" name="creator" id="creator" value="${pd.creator}" maxlength="18" readOnly="true" /></td>
				</tr>
				<tr>
					<td><label>创建时间：</label></td>
					<td><input type="text" name="create_date" id="create_date" value="${pd.create_date}" maxlength="18" readOnly="true" /></td>
				</tr>
				<tr>
					<td><label>修改人：</label></td>
					<td><input type="text" name="updator" id="updator" value="${pd.updator}" maxlength="18" readOnly="true" /></td>
				</tr>
				<tr>
					<td><label>修改时间：</label></td>
					<td><input type="text" name="update_date" id="updata_date" value="${pd.update_date}" maxlength="18" readOnly="true" /></td>
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
    <script src='static/js/jquery-1.9.1.min.js'></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->

</body>
</html>