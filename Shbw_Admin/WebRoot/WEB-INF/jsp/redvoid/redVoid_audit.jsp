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
<script type="text/javascript" src="static/js/jquery.form.js"></script>
<style type="text/css">
.portmsg {
	color: red;
	font-size: 15px;
}
</style>
<script type="text/javascript">
	$(top.hangge());
	//保存
	function save() {
		var audit=$("#audit").val();
		var id=$("#id").val();
		if(audit==""){
			$("#audit").tips({
				side:3,
	            msg:'请先审核再提交!',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#audit").focus();
			return false;
		}
		$.ajax({
			type: "POST",
			url: '<%=basePath%>server/redVoid/redVoidAudit.do',
			data : {
				lcjd : audit,
				id : id
			},
			dataType : 'json',
			cache : false,
			success : function(data) {
				if ("success" == data.msg) {
					if ('${pd.kpbz}' == 1) {
						alert('发票红冲审核成功!')
					} else if ('${pd.kpbz}' == 2) {
						alert('发票作废审核成功!')
					} else if ('${pd.kpbz}' == 3) {
						alert('发票重打审核成功!')
					}
					top.Dialog.close();
                 
				} else {
					alert(data.checkResult);
				}
			}
		});
	}
</script>
</head>
<body>
	<form action="server/redVoid/redVoidApplyAdd.do" name="redVoidAuditForm" id="redVoidAuditForm" method="post" enctype="multipart/form-data">
		<div id="zhongxin" style="text-align: center;">
			<input type="hidden" name="id" id="id" value="${pd.id}" />
			<table style="margin: auto;">
				<tr>
					<td><label>客户名称：</label></td>
					<td><input type="text" name="gfmc" id="gfmc" maxlength="100" value="${pd.gfmc}" readOnly="true" title="客户名称" /></td>

				</tr>
				<tr>
					<td><label>申请类型：</label></td>
					<td><c:if test="${pd.kpbz==1}">
							<input type="text" maxlength="100" readOnly="true" value="红冲" title="申请类型" />
						</c:if> <c:if test="${pd.kpbz==2}">
							<input type="text" maxlength="100" readOnly="true" value="作废" title="申请类型" />
						</c:if> <c:if test="${pd.kpbz==3}">
							<input type="text" maxlength="100" readOnly="true" value="重打" title="申请类型" />
						</c:if></td>
				</tr>

				<tr>
					<td><label>客户税号：</label></td>
					<td><input type="text" name="gfsh" id="gfsh" maxlength="20" value="${pd.gfsh}" readOnly="true" title="客户税号" /></td>

				</tr>

				<tr>
					<td><label>申请原因：</label></td>
					<td><textarea id="sqyy" maxlength="100" style="height: 87px; font-size: 14px;" readOnly="true">${pd.sqyy}</textarea></td>
				</tr>
				<tr>
					<td><label>附件&nbsp;&nbsp;:</label></td>
					<td><a style="width: 217px;" href="server/redVoid/redVoidTpShow.do?picurl=${pd.fileName}" target="_blank"><img alt="视频图片" style="height: 100px;" src="server/redVoid/redVoidTpShow.do?picurl=${pd.fileName}"></a></td>
				</tr>
				<tr>
					<td><label>审核：</label></td>
					<td><select id="audit">
							<option value="">请选择</option>
							<option value="3">同意</option>
							<option value="0">退回</option>
					</select></td>
				</tr>
				<br />
				<tr>
					<td style="text-align: center;" colspan="2"><a class="btn btn-mini btn-primary" onclick="save();">提交</a> <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a></td>
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