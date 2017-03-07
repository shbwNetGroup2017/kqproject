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
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
	<!-- 	<link rel="stylesheet" href="static/css/ace.min.css" /> -->
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<link rel="stylesheet" href="static/css/bootstrap-select.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/bootstrap-select.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript" src="static/js/util/fromValidate.js"></script>
		<style type="text/css">
		.portmsg{
		   color:red;
		   font-size: 15px;
		}
		</style>
<script type="text/javascript">
	$(top.hangge());
	//保存和验证用户信息
	function save(){
 		if($("#yhzh").val()==""){
			$("#yhzh").tips({
				side:3,
	            msg:'账号不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#yhzh").focus();
			return false;
		} 
		if($("#yhmc").val()==""){
			$("#yhmc").tips({
				side:3,
	            msg:'名称不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#yhmc").focus();
			return false;
		}
		if($("#yhmm").val()!=$("#chkpwd").val()){
			$("#chkpwd").tips({
				side:3,
	            msg:'密码不匹配',
	            bg:'#AE81FF',
	            time:3
	        });
			return false;
		}
		if($("#sjhm").val()!=""){
			if(!isPhoneOrTel($("#sjhm").val())){
				$("#sjhm").tips({
					side:3,
		            msg:'手机号码格式不对',
		            bg:'#AE81FF',
		            time:3
		        });
				return false;
			}
		}
		saveUser();
	}
	
	//保存用户信息
	function saveUser(){
		$("#roleId").val($("#jsid").val());
		$("#userGroupId").val($("#yhzid").val());
		$.ajax({
			type: "POST",
			url: '<%=basePath%>user/addOrEditUser.do',
	    	data: $("#userForm").serialize(),
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.msg){
					alert('保存成功！');
				 }else if("exit" == data.msg){
					alert('此用户名已存在！');
				 }else if("validate"== data.msg){
					 alert(data.msgInfo);
				 }else if("error" == data.msg){
					 alert('程序异常！');
				 }
			}
		});
	}
	
	function editUserPassword(){
		$("#editPwd").attr('onclick','unEdit();');
		$(".icon-edit").html('锁定');
		$("#yhmm").removeAttr('disabled');
		$("#chkpwd").removeAttr('disabled');
		$("#yhmm").val('');
		$("#chkpwd").val('');
	}
	
	function unEdit(){
		$("#editPwd").attr('onclick','editUserPassword();');
		$(".icon-edit").html('修改');
		$("#yhmm").attr('disabled','disabled');
		$("#chkpwd").attr('disabled','disabled');
		$("#yhmm").val('123456');
		$("#chkpwd").val('123456');
		
	}
	
</script>
</head>
<body>
	<form action="user/addOrEdit.do" name="userForm" id="userForm" method="post">
		<input type="hidden" name="id" id="id" value="${pdUser.id }"/>
		<input type="hidden" name="roleId" id="roleId" value=""/>
		<input type="hidden" name="userGroupId" id="userGroupId" value=""/>
		<div id="zhongxin" style="text-align: center;">
		<table style="margin:auto;">
			<tr>
				<td><label>用户账号：</label></td>
				<td><input type="text" name="yhzh" id="yhzh" value="${pdUser.yhzh }" maxlength="20" placeholder="这里输入用户名" title="用户名"/></td>
				<td><span class="portmsg">*</span></td>
			</tr>
			<tr>
			    <td><label>用户名称：</label></td>
				<td><input type="text" name="yhmc" id="yhmc" value="${pdUser.yhmc }" maxlength="30" placeholder="这里输入编号" title="编号"/></td>
				<td><span class="portmsg">*</span></td>
			</tr>
			<tr>
			    <td><label>用户密码：</label></td>
				<td>
				   <input type="password" name="yhmm" id="yhmm"  maxlength="20" 
				   <c:if test="${not empty pdUser.id }">value="123456" disabled</c:if>
				   placeholder="输入密码" title="密码" />
			    </td>
			    <td><span class="portmsg">*</span></td>
			    <c:if test="${not empty pdUser.id }">
				    <td>
				       <a class='btn btn-mini btn-info' id="editPwd" title="修改" onclick="editUserPassword();"><i class='icon-edit'>修改</i></a>
				    </td>
			    </c:if>
         	</tr>
			<tr>
			    <td><label>确认密码：</label></td>
				<td>
					<input type="password" name="chkpwd" id="chkpwd"  maxlength="20" 
					<c:if test="${not empty pdUser.id }">value="123456" disabled</c:if>
					placeholder="确认密码" title="确认密码"/>
			    </td>
			</tr>
			<tr>
			    <td><label>用户角色：</label></td>
				<td>
				    <select name="jsid" id="jsid" data-style="btn-info" class="selectpicker" style="border-ra" multiple data-done-button="true"  title="--请选择角色--">
						<c:forEach items="${roleList}" var="item" varStatus="status">  
							<option value="${item.id}" ${item.p}>${item.jsmc}</option>
						</c:forEach> 
					</select>
				</td>
			</tr>
			<tr>
			    <td><label>用户组：</label></td>
				<td>
				    <select name="yhzid" id="yhzid" data-style="btn-info" class="selectpicker" multiple data-done-button="true"  title="--请选择用户组--">
						<c:forEach items="${userGroupList}" var="item" varStatus="status">  
							<option value="${item.id}" ${item.p}>${item.yhzmc}</option>
						</c:forEach> 
					</select>
				</td>
			</tr>
			<tr>
			    <td><label>性别：</label></td>
				<td>
				<input type="radio" style="margin-top:-10px;width:15px;opacity:1;position: relative;" checked="checked" name="xb" id="xbn" value="0" 
				<c:if test="${pdUser.xb==0}">checked="checked"</c:if>
				/>男
				<input type="radio" style="margin-top:-10px;width:15px;opacity:1;;position: relative;margin-left:50px;" name="xb" id="xbv" value="1"
				<c:if test="${pdUser.xb==1}">checked="checked"</c:if>
				/>女
				</td>
			</tr>
			<tr>
			    <td><label>手机号码：</label></td>
				<td><input type="text" name="sjhm" id="sjhm"  value="${pdUser.sjhm }"  maxlength="12" placeholder="这里输入手机号" title="手机号"/></td>
			</tr>
			<tr>
			    <td><label>状态：</label></td>
				<td>
				<input type="radio" style="margin-top:-10px;width:15px;opacity:1;position: relative;" checked="checked" name="zt" id="ztn" value="0" 
				<c:if test="${pdUser.zt==0}">checked="checked"</c:if>
				/>启用
				<input type="radio" style="margin-top:-10px;width:15px;opacity:1;;position: relative;margin-left:50px;" name="zt" id="ztv" value="1"
				<c:if test="${pdUser.zt==1}">checked="checked"</c:if>
				/>禁用
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>	
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>	
	</form>
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
	
</body>
</html>