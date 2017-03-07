<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		if($("#id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	
	//保存
	function save(){
		if($("#filename").val()==""){
			$("#filename").tips({
				side:3,
	            msg:'流程名称不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#filename").focus();
			return false;
		}
		if($("#zipFile").val()==""){
			$("#zipFile").tips({
				side:3,
	            msg:'流程文件不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#zipFile").focus();
			return false;
		}else{
			var str = $("#zipFile").val();
			var pos = str.lastIndexOf("."); 
	        var lastname = str.substring(pos,str.length);  
	        if ((lastname.toLowerCase() != ".zip" ))
	        { 
	            alert("文件必须为.zip"); 
	            return false;
	        } 
		}
			//$("#userForm").submit();
			
			$("#userForm").ajaxSubmit({
				url : 'leaveBill/newdeploy.do',
				success : function(data) {
					if ("success" == data.msg) {
						alert('部署成功！');
						$("#zhongxin").hide();
						$("#zhongxin2").show();
						top.Dialog.close();
					} else {
						alert(data.msg)
					}
				},
				error : function(request) {
					alert("Connection error");
				}
			});
	}
	
</script>
	</head>
<body>
	<form action="leaveBill/newdeploy.do" name="userForm" id="userForm" enctype="multipart/form-data" method="post">
		<div id="zhongxin" style="text-align: center;">
		<table style="margin:auto;">
			<br/>
			<tr>
				<td><label>流程名称：</label></td>
				<td><input type="text" name="filename" id="filename" maxlength="20" placeholder="这里输入流程名称" title="流程名称"/></td>
			</tr>
			
			<tr>
				<td><label>流程文件(zip格式)</label></td>
				<td><input type="file" name="file" id="zipFile"/></td>
			</tr>
			
			<tr>
				<td style="text-align: center;" colspan="2">
				<br/>
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		
	
</body>
</html>