<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<link rel="stylesheet" href="static/css/bootstrap-select.css">
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/bootstrap-select.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
			$(top.hangge());
		</script>			
	</head>
<body>
	<form action="" name="roleForm" id="roleForm" method="post">
		<div id="zhongxin" style="text-align: center;">
		<table style="margin:auto;">
			<tr>
				<td><label>用户账号：</label></td>
				<td><input type="text" name="yhzh" id="yhzh" disabled="disabled" value="${pdUser.yhzh }" maxlength="5" placeholder="这里输入用户名" title="用户名"/></td>
			</tr>
			<tr>
			    <td><label>用户名称：</label></td>
				<td><input type="text" name="yhmc" id="yhmc" disabled="disabled" value="${pdUser.yhmc }" maxlength="5" placeholder="这里输入编号" title="编号"/></td>
			</tr>
			<tr>
			    <td><label>手机号码：</label></td>
				<td><input type="number" name="sjhm" id="sjhm"  value="${pdUser.sjhm }" disabled="disabled"  maxlength="12" placeholder="这里输入手机号" title="手机号"/></td>
			</tr>			
			<tr>
			    <td><label>用户角色：</label></td>
				<td>
				    <select name="jsid" id="jsid" data-style="btn-info" class="selectpicker" disabled="disabled" multiple data-done-button="true"  title="--请选择角色--">
						<c:forEach items="${roleList}" var="item" varStatus="status">  
							<option value="${item.id}" ${item.p}>${item.jsmc}</option>
						</c:forEach> 
					</select>
				</td>
			</tr>
			<tr>
			    <td><label>用户组：</label></td>
				<td>
				    <select name="yhzid" id="yhzid" data-style="btn-info" class="selectpicker" disabled="disabled" multiple data-done-button="true"  title="--请选择用户组--">
						<c:forEach items="${userGroupList}" var="item" varStatus="status">  
							<option value="${item.id}" ${item.p}>${item.yhzmc}</option>
						</c:forEach> 
					</select>
				</td>
			</tr>
			<tr>
			<td><label>创建人：</label></td>
				<td><input type="text" name="creator" id="creator" disabled="disabled" maxlength="20" value="${pdUser.creator}" placeholder="输入创建人" title="输入创建人"/></td>
			</tr>
			<tr>
			    <td><label>创建时间：</label></td>
				<td><input type="text" name="create_date" id="create_date" disabled="disabled" value="${pdUser.create_date}"  placeholder="输入创建时间" title="输入创建时间" /></td>
			</tr>
			<td><label>修改人：</label></td>
				<td><input type="text" name="updator" id="updator" disabled="disabled" maxlength="20" value="${pdUser.updator}" placeholder="输入修改人" title="输入修改人"/></td>
			</tr>
			<tr>
			    <td><label>修改时间：</label></td>
				<td><input type="text" name="update_date" id="update_date" disabled="disabled" value="${pdUser.update_date}" placeholder="输入修改时间" title="输入修改时间" /></td>
			</tr>
			<tr>
			    <td><label>性别：</label></td>
				<td>
				<input type="radio" disabled style="margin-top:-10px;width:15px;opacity:1;position: relative;" checked="checked" name="xb" id="xbn" value="0" 
				<c:if test="${pdUser.xb==0}">checked="checked"</c:if>
				/>男
				<input type="radio" style="margin-top:-10px;width:15px;opacity:1;;position: relative;margin-left:50px;" name="xb" id="xbv" value="1"
				<c:if test="${pdUser.xb==1}">checked="checked"</c:if>
				/>女
				</td>
			</tr>
			
			<tr>
			    <td><label>状态：</label></td>
				<td>
				<input type="radio" disabled style="margin-top:-10px;width:15px;opacity:1;position: relative;" checked="checked" name="zt" id="ztn" value="0" 
				<c:if test="${pdUser.zt==0}">checked="checked"</c:if>
				/>启用
				<input type="radio" disabled style="margin-top:-10px;width:15px;opacity:1;;position: relative;margin-left:50px;" name="zt" id="ztv" value="1"
				<c:if test="${pdUser.zt==1}">checked="checked"</c:if>
				/>禁用
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
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
	
</body>
</html>