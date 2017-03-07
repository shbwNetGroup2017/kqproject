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
	<style type="text/css">
		th{text-align: center;}
	</style>
	</head> 
<body>
		
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">						
  <div class="row-fluid">
	<div class="row-fluid">
			<!-- 检索  -->
			<form action="kjpj/toKjpjDetails.do" method="post" name="kjpjForm" id="kjpjForm">
			<div id="zhongxin" style="text-align: center;">
			<input type="hidden" name="kplsh" id="kplsh" value="${pd.kplsh}">
			<table id="table_report" class="table table-striped table-bordered table-hover">				
				<thead>
					<tr>
						<th class="center">货物或应税劳务、服务名称</th>
						<th class="center">规格型号</th>
						<th class="center">单位</th>
						<th class="center">数量</th>
						<th class="center">单价</th>
						<th class="center">商品金额</th>
						<th class="center">税率</th>
						<th class="center">商品税额</th>
					</tr>
				</thead>										
				<tbody>					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty kpmxList}">
						<c:forEach items="${kpmxList}" var="kpmx">
							<tr>
								<td class="center">${kpmx.spmc}</td>
								<td class="center">${kpmx.ggxh}</td>
								<td class="center">${kpmx.dw}</td>
								<td class="center">${kpmx.spsl}</td>
								<td class="center">${kpmx.dj}</td>
								<td class="center">${kpmx.spje}</td>								
								<td class="center">${kpmx.sl}</td>
								<td class="center">${kpmx.spse}</td>
							</tr>						
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="13" class="center">没有相关数据</td>
						</tr>
					</c:otherwise>
				</c:choose>
				</tbody>
			</table>	
		<div class="page-header position-relative">
			<table style="width:100%;">
				<tr>
					<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${page.pageStr}</div></td>
				</tr>
			</table>
		</div>
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>		
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
			//检索
			function search(){
				top.jzts();
				$("#kjpjForm").submit();
			}
		</script>			
	</body>
</html>

