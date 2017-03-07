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
	function apply() {
		if ($("#gfmc").val() == "") {
			$("#gfmc").tips({
				side : 3,
				msg : '客户名称不能为空',
				bg : '#AE81FF',
				time : 2
			});
			$("#gfmc").focus();
			return false;
		}
		if ($("#gfsh").val() == "") {
			$("#gfsh").tips({
				side : 3,
				msg : '客户税号不能为空',
				bg : '#AE81FF',
				time : 2
			});
			$("#gfsh").focus();
			return false;
		}
		if ('${pd.bz}' == 1) {
			if ($("#tzdbh").val() == "") {
				$("#tzdbh").tips({
					side : 3,
					msg : '发票红冲必须输入通知单编号',
					bg : '#AE81FF',
					time : 2
				});
				$("#tzdbh").focus();
				return false;
			}
		}
		if ($("#sqyy").val() == "") {
			$("#sqyy").tips({
				side : 3,
				msg : '申请原因不能为空',
				bg : '#AE81FF',
				time : 2
			});
			$("#sqyy").focus();
			return false;
		}
		var tp = $("#tp").val();
		if (tp == "") {
			$("#tp").tips({
				side : 3,
				msg : '请上传图片!',
				bg : '#AE81FF',
				time : 2
			});
			$("#tp").focus();
			return false;
		}		
		//var fileName = tp.toLocaleLowerCase();
		var fileType=tp.substr(tp.lastIndexOf(".")).toLowerCase();//获得文件后缀名
		if (fileType == '.png'||fileType == '.jpg') {
			 $("#redVoidApplyForm").ajaxSubmit({
				url : 'server/redVoid/redVoidApplyAdd.do',
				success : function(data) {
					if ("success" == data.msg) {
						alert('红冲作废重打申请成功！');
						$("#zhongxin").hide();
						top.Dialog.close();
					} else {
						alert(data.checkResult);
					}
				},
				error : function(request) {
					alert("Connection error");
				}
			}); 
		} else {
			$("#tp").tips({
				side : 3,
				msg : '附件只能选择jpg或者png格式!',
				bg : '#AE81FF',
				time : 2
			});
			$("#tp").val('');
			return;
		}
		//}
	}
</script>
</head>
<body>
	<form action="server/redVoid/redVoidApplyAdd.do" name="redVoidApplyForm" id="redVoidApplyForm" method="post" enctype="multipart/form-data">
		<div id="zhongxin" style="text-align: center;">
			<input type="hidden" name="id" id="id" value="${pd.id}" />
			<table style="margin: auto;">
				<tr>
					<td><label>客户名称：</label></td>
					<td><input type="text" name="gfmc" id="gfmc" maxlength="100" value="${pd.gfmc}" readOnly="true" title="客户名称" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>操作类型：</label></td>
					<td><c:if test="${pd.bz==1}">
							<input type="hidden" name="kpbz" value="1" />
							<input type="text" id="czlx" maxlength="100" readOnly="true" value="红冲" title="操作类型" />
						</c:if> <c:if test="${pd.bz==2}">
							<input type="hidden" name="kpbz" value="2" />
							<input type="text" id="czlx" maxlength="100" readOnly="true" value="作废" title="操作类型" />
						</c:if>
						<c:if test="${pd.bz==3}">
							<input type="hidden" name="kpbz" value="3" />
							<input type="text" id="czlx" maxlength="100" readOnly="true" value="重打" title="操作类型" />
						</c:if></td>
				</tr>

				<tr>
					<td><label>客户税号：</label></td>
					<td><input type="text" name="gfsh" id="gfsh" maxlength="20" value="${pd.gfsh}" readOnly="true" title="客户税号" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<c:if test="${pd.bz==1}">
					<tr>
						<td><label>通知单编号:</label></td>
						<td><input type="text" id="tzdbh" name="tzdbh" maxlength="32" title="通知单编号" placeholder="请输入通知单编号" /></td>
						<td><span class="portmsg">*</span></td>
					</tr>
				</c:if>
				<tr>
					<td><label>申请原因：</label></td>
					<td><textarea id="sqyy" name="sqyy" maxlength="100" style="height: 87px; font-size: 14px;" placeholder="请输入申请原因"></textarea></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>附件&nbsp;&nbsp;:</label></td>
					<td><input type="file" style="width: 220px;" id="tp" name="tp" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="2"><a class="btn btn-mini btn-primary" onclick="apply();">提交</a> <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a></td>
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