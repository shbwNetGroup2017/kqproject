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
		<link rel="stylesheet" href="static/css/bootstrap-3.3.4.css">
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
		<style type="text/css">
			input[type="text"]{   
			    height: 30px;
			    margin-top: 10px;
    		}    		
    		input[type="radio"]{margin-top: 5px !important; }
    		.select{width: 207px !important;}
    		.td{text-align: center;}
		</style>
		<script type="text/javascript">
			$(top.hangge());
		</script>			
	</head>
<body>
	<form action="" name="roleForm" id="roleForm" method="post">
		<div id="zhongxin" style="text-align: center;">
		<table style="margin:auto;">
			<tr>
				<td><label>角色名称：</label></td>
				<td><input type="text" name="jsmc" id="jsmc" disabled="disabled" value="${pd.jsmc}"  placeholder="" title="资源名称"/></td>
			</tr>
			<tr>
			    <td><label>资源类型：</label></td>
				<td>
				    <select name="selid" id="selid" data-style="btn-info" class="selectpicker select" multiple data-done-button="true" disabled="disabled"  title="--请选择资源类型--">						            		
						            	<c:choose>	       				                					            		
							            	<c:when test="${not empty fzyList}">								            		    	
													<c:forEach items="${fzyList}" var="item" varStatus="status">  
											 			<option value="${item.id}" <c:if test="${item.id==item.p}">selected</c:if>>${item.zymc}</option>
												    </c:forEach> 											
											</c:when>
										</c:choose>	
					 </select> 						
				</td>
			</tr>
			<tr>
			    <td><label>状态：</label></td>
				<td>
					<input type="radio" style="margin-top:-10px;width:15px;opacity:1;position: relative;" name="zt" id="zt" disabled="disabled" <c:if test="${pd.zt=='0'}">checked="checked"</c:if> value="0"/>启用
					<input type="radio" style="margin-top:-10px;width:15px;opacity:1;;position: relative;margin-left:50px;" disabled="disabled" <c:if test="${pd.zt=='1'}">checked="checked"</c:if> name="zt" id="zt" value="1"/>禁用
				</td>
			</tr>
			<tr>
			    <td><label>创建人：</label></td>
				<td><input type="text" name="creator" id="creator" disabled="disabled" value="${pd.creator}"   placeholder="" title="资源序号" /></td>
			</tr>
			<tr>
			    <td><label>创建时间：</label></td>
				<td><input type="text" name="create_date" id="create_date" disabled="disabled" value="${fn:replace(pd.create_date,'.0','')}"   placeholder="" title="资源序号" /></td>
			</tr>
			<tr>
			    <td><label>修改人：</label></td>
				<td><input type="text" name="updator" id="updator" disabled="disabled" value="${pd.updator}"   placeholder="" title="资源序号" /></td>
			</tr>
			<tr>
			    <td><label>修改时间：</label></td>
				<td><input type="text" name="update_date" id="update_date" disabled="disabled" value="${fn:replace(pd.update_date,'.0','')}"   placeholder="" title="资源描述"/></td>
			</tr>
			<tr>
			    <td><label>角色描述：</label></td>
				<td><input type="text" name="jsms" id="jsms" disabled="disabled" value="${pd.jsms}"   placeholder="" title="资源序号" /></td>
			</tr>
			<tr>
				<td class="td" colspan="2">
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>	
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>	
	</form>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.js"></script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
	
</body>
</html>