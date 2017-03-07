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
					<td><label>商品编码：</label></td>
					<td><input type="text" name="gfmc" readOnly="true" id="spbm" maxlength="20" value="${pd.spbm}" /></td>
				</tr>
				<tr>
					<td><label>原商品编码：</label></td>
					<td><input type="text" name="yspbm" id="yspbm" readOnly="true" maxlength="32" value="${pd.yspbm}" /></td>
				</tr>
				<tr>
					<td><label>原商品名称：</label></td>
					<td><input type="text" name="yspmc" id="yspmc" readOnly="true" maxlength="60" value="${pd.yspmc}" /></td>
				</tr>
				<tr>
					<td><label>税率：</label></td>
					<td><input type="text" name="sl" id="sl" readOnly="true" maxlength="18" value="${pd.sl}" /></td>
				</tr>
				<tr>
					<td><label>数据来源：</label></td>
					<td>
					   	<select name="sjly" id="sjly"  disabled="disabled" >
					        <option value="" >请选择</option>
							<option value="0" <c:if test="${pd.sjly==0}">selected</c:if>>ODS数据仓库</option>
							<option value="1" <c:if test="${pd.sjly==1}">selected</c:if>>Oracle ERP系统</option>
							<option value="2" <c:if test="${pd.sjly==2}">selected</c:if>>进件系统</option>
							<option value="3" <c:if test="${pd.sjly==3}">selected</c:if>>商派</option>
					    </select>
					</td>
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
					<td><input type="text" name="updata_date" id="updata_date" value="${pd.updata_date}" maxlength="18" readOnly="true" /></td>
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