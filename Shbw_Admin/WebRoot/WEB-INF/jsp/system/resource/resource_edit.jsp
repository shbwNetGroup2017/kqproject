<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
		<style type="text/css">
			.portmsg{color:red;font-size: 15px;}
		</style>
		<script type="text/javascript">
			$(top.hangge());			
			
			function save(){
				   if($("#zymc").val()==""){									
						$("#zymc").tips({
							side:3,
				            msg:'填写资源名称',
				            bg:'#AE81FF',
				            time:2
				        });
						
						$("#zymc").focus();
						return false;
					}
					if($("#zylj").val()=="" && $("#fzy").val()!=""){
						
						$("#zylj").tips({
							side:3,
				            msg:'填写资源路径',
				            bg:'#AE81FF',
				            time:3
				        });
						$("#zylj").focus();
						return false;
					}
					if($("#zt").val()==""){
						
						$("#zt").tips({
							side:3,
				            msg:'请选择状态',
				            bg:'#AE81FF',
				            time:3
				        });
						$("#zt").focus();
						return false;
					}
					
					if($("#zyms").val()==""){
						
						$("#zyms").tips({
							side:3,
				            msg:'填写资源描述',
				            bg:'#AE81FF',
				            time:3
				        });
						$("#zyms").focus();
						return false;
					}					     
			   		editResource();		   				   				   						
			};	
			
			//修改资源信息
			function editResource(){
				$.ajax({
					type: "POST",
					url: '<%=basePath%>resource/updateResource.do',
			    	data: $("#resourceForm").serialize(),
					dataType:'json',
					cache: false,
					success: function(data){					
						 if("success" == data.msg){
							alert('修改成功！');
							document.getElementById('zhongxin').style.display = 'none';
			                top.Dialog.close();
						 }else if("error" == data.msg){
						 	alert('修改失败，你输入的信息有误！');						 	
						 }else if("fail" == data.msg){
						 	alert('修改失败，该序号已存在！');						 	
						 }
					}
				});
			}
					
		</script>
		<script type="text/javascript">
			$(function(){
				$('#fzy').change(function(){			     
			   		var fzy=$(this).val();
			   		var zyxh=$('#zyxh').val();	   
			   		var v=zyxh.substring(2,4);
			   		$('#zyxh').val(fzy+v);
		   		});		   		
			});			
		</script>
	</head>
<body>
	<form action="resource/updateResource.do" name="resourceForm" id="resourceForm" method="post">
	    <input type="hidden" name="id" id="id" value="${pd.id}"/>
	    <input type="hidden" name="jzyxh" id="jzyxh" value="${pd.zyxh}"/>
	    <input type="hidden" name="zyxh" id="zyxh" value="${pd.zyxh}" />
		<div id="zhongxin" style="text-align: center;">
		<table style="margin:auto;">
			<tr>
				<td><label>资源名称：</label></td>
				<td><input type="text" name="zymc" id="zymc" value="${pd.zymc}" maxlength="64" placeholder="这里输入资源名称" title="资源名称"/></td>
				<td><span class="portmsg">*</span></td>
			</tr>
			<tr>
			    <td><label>上级资源：</label></td>
				<td>
					<c:choose>					       				                					            		
						            	<c:when test="${not empty fzyList}">	
						            		<select name="fzy" id="fzy">
						            			<option value="">--请选择资源--</option>
												<c:forEach items="${fzyList}" var="item" varStatus="status">  
										 			<option value="${item.zyxh}" <c:if test="${item.zymc==pd.fzy}">selected</c:if>>${item.zymc}</option>
											    </c:forEach> 
											 </select> 
										</c:when>
										<c:otherwise>
										   	 <select name="fzy" id="fzy">	
										   	 	<option value="">无上级资源</option>
										   	 </select>
										</c:otherwise>															
					</c:choose>	
				</td>
			</tr>
			<tr>
			    <td><label>资源路径：</label></td>
				<td>
					<c:choose>					       				                					            		
						            	<c:when test="${fn:length(pd.zyxh)>2}">	
						            		<input type="text" name="zylj" id="zylj" value="${pd.zylj}"  maxlength="100" placeholder="输入资源路径" title="资源路径"/>
										</c:when>
										<c:otherwise>
										   	<input type="text" name="zylj" id="zylj" disabled="disabled" value="${pd.zylj}"  maxlength="100" placeholder="输入资源路径" title="资源路径"/>
										</c:otherwise>															
					</c:choose>	
					
				</td>
			</tr>
			<tr>
			    <td><label>状态：</label></td>
				<td>
					<input type="radio" style="margin-top:-10px;width:15px;opacity:1;position: relative;" name="zt" id="zt" <c:if test="${pd.zt=='0'}">checked="checked"</c:if> value="0"/>启用
					<input type="radio" style="margin-top:-10px;width:15px;opacity:1;;position: relative;margin-left:50px;" <c:if test="${pd.zt=='1'}">checked="checked"</c:if> name="zt" id="zt" value="1"/>禁用
				</td>
			</tr>
			<tr>
			    <td><label>资源描述：</label></td>
				<td><input type="text" name="zyms" id="zyms" value="${pd.zyms}"  maxlength="100" placeholder="输入资源描述" title="资源描述"/></td>
				<td><span class="portmsg">*</span></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="10">
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