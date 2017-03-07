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
			.portmsg{color:red;font-size: 15px;}
		</style>
		<script type="text/javascript">
			$(top.hangge());			
			
			//添加
			function save(){
				  if($("#jsmc").val()==""){									
						$("#jsmc").tips({
							side:3,
				            msg:'填写角色名称',
				            bg:'#AE81FF',
				            time:2
				        });
						
						$("#jsmc").focus();
						return false;
					}
					
					if($("#zyid").val()==null){										
						$("#zyid").tips({
							side:3,
				            msg:'选择资源',
				            bg:'#AE81FF',
				            time:3
				        });
						
						$("#zyid").focus();
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
					
					if($("#jsms").val()==""){
						
						$("#jsms").tips({
							side:3,
				            msg:'填写角色描述',
				            bg:'#AE81FF',
				            time:3
				        });
						$("#jsms").focus();
						return false;
					}
					
					editRole();
					
			};
			
			//修改角色信息
			function editRole(){
				$("#zyid").val($("#selid").val());
				$.ajax({
					type: "POST",
					url: '<%=basePath%>role/updateRole.do',
			    	data: $("#roleForm").serialize(),
					dataType:'json',
					cache: false,
					success: function(data){
						 if("success" == data.msg){
							alert('修改成功！');
							document.getElementById('zhongxin').style.display = 'none';
			                top.Dialog.close();
						 }else{
						 	alert('修改失败，你输入的信息有误！');						 	
						 }
					}
				});
			}
			
			//重置
			function reset(){
				document.getElementById('roleForm').reset();
			}
		</script>
	</head>
<body>
	<form action="role/updateRole.do" name="roleForm" id="roleForm" method="post">
		<input type="hidden" name="id" id="id" value="${pd.id}"/>
		<input type="hidden" name="jsid" id="jsid" value="${pd.id}"/>
	    <input type="hidden" name="zyid" id="zyid" value=""/>
		<div id="zhongxin" style="text-align: center;">
		<table style="margin:auto;">
			<tr>
				<td><label>角色名称：</label></td>
				<td><input type="text" name="jsmc" id="jsmc" value="${pd.jsmc}" maxlength="64" placeholder="这里输入资源名称" title="资源名称"/></td>
				<td><span class="portmsg">*</span></td>
			</tr>
			<tr>
			    <td><label>资源类型：</label></td>
				<td>												
							<select name="selid" id="selid" data-style="btn-info" class="selectpicker select" multiple data-done-button="true"  title="--请选择资源类型--">						            		
						            	<c:choose>	       				                					            		
							            	<c:when test="${not empty fzyList}">								            		    	
													<c:forEach items="${fzyList}" var="item" varStatus="status">  
											 			<option value="${item.id}" <c:if test="${item.id==item.p}">selected</c:if>>${item.zymc}</option>
												    </c:forEach> 											
											</c:when>	
										</c:choose>	
							 </select> 																		
				</td>
				<td><span class="portmsg">*</span></td>
			</tr>
			<tr>
			    <td><label>状态：</label></td>
				<td>
					<input type="radio" style="margin-top:-10px;width:15px;opacity:1;position: relative;" name="zt" id="zt" checked="checked" value="0"/>启用
					<input type="radio" style="margin-top:-10px;width:15px;opacity:1;;position: relative;margin-left:50px;" name="zt" id="zt" value="1"/>禁用
				</td>
			</tr>
			<tr>
			    <td><label>角色描述：</label></td>
				<td><input type="text" name="jsms" id="jsms" value="${pd.jsms}"  maxlength="100" placeholder="输入角色描述" title="角色描述"/></td>
				<td><span class="portmsg">*</span></td>
			</tr>
			<tr>
				<td class="td" colspan="3">
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
		<script src="static/js/bootstrap.js"></script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
	
</body>
</html>