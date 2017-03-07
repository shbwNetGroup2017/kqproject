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
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%>
	</head> 
<body>
		
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
		<form name="fenpeiForm" id="fenpeiForm" method="post">
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
				<input id="yhzh" name="yhzh" type="hidden">
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
		<div class="page-header position-relative">
		</div>
		</form>
	</div>
  </div>
</div>
</div>
		
		<!-- 返回顶部  -->
		<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse">
			<i class="icon-double-angle-up icon-only"></i>
		</a>
		
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript">
		
		$(top.hangge());
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
			}else{
				$("#yhzh").val($("#yh").val());
				top.Dialog.close();
			}

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
									$("#yh").append("<option value='"+list[i].yhzh+"'>"+list[i].yhmc+"</option>");
								}
								
							}
					 }else{
						 alert("查询失败")
					 }
				}
			});
		}
		
		</script>	
	</body>
</html>

			