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
		<link rel="stylesheet" href="static/css/bootstrap-select.css" />
		
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/bootstrap-select.js"></script>
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
	$(document).ready(function(){
		if($("#id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	
	//保存
	function save(){
		if($("#yh").val()=="" || $("#yh").val()==null){
			$("#yh").tips({
				side:3,
	            msg:'用户不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			return false;
		}
		//提交表单
		$.ajax({
			type: "POST",
			url: '<%=basePath%>leaveBill/inserFenPei.do',
	    	data: $("#fenpeiForm").serialize(),
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.msg){
					$("#zhongxin").hide();
					$("#zhongxin2").show();
					alert('分配成功！');
					top.Dialog.close();
				 }else{
					 alert(data.msg)
				 }
			}
		});
		
	}
	
	//查找用户
	function selectYh(yhzid){
		$.ajax({
			type: "POST",
			url: '<%=basePath%>leaveBill/selectYhByYhzid.do',
	    	data: {yhzid:yhzid},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.msg){
					 $("#yh").empty();
					 var list = data.pdYh;
					 	for(var i=0;i<list.length;i++){
							if(list[i]!=null && "" !=list[i]){
								$("#yh").append("<option value='"+list[i].yhmc+"'>"+list[i].yhmc+"</option>");
							}
							
						}
				 }else{
					 alert("查询失败")
				 }
			}
		});
	}
	
</script>
	</head>
<body>
	<form action="leaveBill/save.do" name="fenpeiForm" id="fenpeiForm" method="post">
		<input type="hidden" id="fenpeiIds" name="fenpeiIds" value="${pd.fenpeiIds}"/>
		<div id="zhongxin" style="text-align: center;">
		<table style="margin:auto;">
			<tr>
			    <td><label>用户组：</label></td>
				<td>
				    <select name="yhzid" id="yhzid"  data-done-button="true"  title="--请选择用户组--">
						<c:forEach items="${list}" var="item" varStatus="status">  
							<option value="${item.id}" onclick="selectYh(${item.id});">${item.yhzmc}</option>
						</c:forEach> 
					</select>
					<span class="portmsg">*</span>
				</td>
			</tr>
			<tr>
				<td><label>用户</label></td>
				<td>
					<select id="yh" name="yh"></select>
					<span class="portmsg">*</span>
				</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="4">
					<br/>
					<a class="btn btn-small btn-info" onclick="save();">确定</a>
					<a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
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
		
		<script type="text/javascript">
		//判断用户名是否存在
		$(function() {
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true});
			//日期框			
		})
		</script>
	
</body>
</html>