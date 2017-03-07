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
		if($("#kpdmc").val()==""){
			$("#kpdmc").tips({
				side:3,
	            msg:'开票点名称不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#kpdmc").focus();
			return false;
		}
		if($("#skph").val()==""){
			$("#skph").tips({
				side:3,
	            msg:'税控盘号不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#skph").focus();
			return false;
		}
		if($("#zzjgid").val()==""){
			$("#zzjgid").tips({
				side:3,
	            msg:'请选择组织机构',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#zzjgid").focus();
			return false;
		}
		if($("#zcm").val()==""){
			$("#zcm").tips({
				side:3,
	            msg:'注册码不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#zcm").focus();
			return false;
		}
		if($("#skpmm").val()==""){
			$("#skpmm").tips({
				side:3,
	            msg:'税控盘密码不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#skpmm").focus();
			return false;
		}
		if($("#skpkl").val()==""){
			$("#skpkl").tips({
				side:3,
	            msg:'税控盘密码不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#skpkl").focus();
			return false;
		}
		//提交表单
		$.ajax({
			type: "POST",
			url: '<%=basePath%>server/ticketedPoint/saveTicketedPointInfo.do',
	    	data: $("#TicketedPointInfoForm").serialize(),
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.msg){
					alert('保存成功！');
					$("#zhongxin").hide();
					top.Dialog.close();
				 }else if("validateError" == data.msg){
					 alert('验证失败!'+data.errorMsg);
				 }else if("exit" == data.msg){
					 alert('当前开票点名称已存在!');
				 }else{
					 alert('保存失败!')
				 }
			}
		});
	}
</script>
</head>
<body>
	<form action="server/ticketedPoint/saveTicketedPointInfo.do" name="TicketedPointInfoForm" id="TicketedPointInfoForm" method="post">
		<div id="zhongxin" style="text-align: center;">
			<table style="margin: auto;">
				<tr>
					<td><label>开票点名称：</label></td>
					<td><input type="text" name="kpdmc" id="kpdmc" maxlength="64" value="${pd.kpdmc}" /><input type="hidden" name="id" id="id" maxlength="20" value="${pd.id}"  /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>税控盘号：</label></td>
					<td><input type="text" name="skph" id="skph" maxlength="100" value="${pd.skph}" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>组织机构：</label></td>
					<td>
						<select name="zzjgId" id="zzjgId">
					        <option value="" >请选择</option>
	                        <c:forEach items="${list}" var="var" varStatus="vs">
	                           <option value="${var.id}" <c:if test="${var.id==pd.zzjgId }">selected</c:if> >${var.xfmc}</option>
	                        </c:forEach>
					    </select>
                    </td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>注册码：</label></td>
					<td><input type="text" name="zcm" id="zcm" maxlength="200" value="${pd.zcm}" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>税控盘密码：</label></td>
					<td><input type="password" name="skpmm" id="skpmm" maxlength="20" value="${pd.skpmm}" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>税控盘口令：</label></td>
					<td><input type="password" name="skpkl" id="skpkl" maxlength="20" value="${pd.skpkl}" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>备注信息：</label></td>
					<td><textarea rows="5" cols="10" name="remark">${pd.remark}</textarea></td>
					<td></td>
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
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
</body>
</html>