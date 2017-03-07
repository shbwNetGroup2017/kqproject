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
			<!-- 检索  -->
			<form action="order/listOrders.do" method="post" name="orderForm" id="orderForm">
			<table style="text-align: center;margin:auto;">
				<tr>
					<td style="width: 100px;">
					   <span style="font-size: 15px;">交易流水号：</span>
					</td>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="jylsh" value="${pd.jylsh}" placeholder="这里输入用户名称" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td style="width: 100px;">
					   <span style="font-size: 15px;">客户名称：</span>
					</td>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="khmc" value="${pd.khmc}" placeholder="这里输入用户账号" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<tr>
						<td style="vertical-align: top;text-align: right;" colspan="2">
							<button class="btn btn-mini btn-light" onclick="search();"  title="检索">
							<i id="nav-search-icon" class="icon-search">查询</i></button>
						</td>
						<td style="vertical-align: top;text-align:left;" colspan="2">
						    <a class="btn btn-mini btn-light" onclick="clearSearch()" title="重置">
							<i id="nav-search-icon" class="icon-refresh">重置</i>
						</a></td>
					</tr>
				</tr>
			</table>
			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>交易流水号</th>
						<th>交易流水时间</th>
						<th>购方客户名称</th>
						<th>价税合计</th>
						<th>创建时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty orderList}">
						<c:forEach items="${orderList}" var="order" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${order.jylsh }" id="${order.jylsh }"/><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>${order.jylsh }</td>
								<td>${order.jylssj }</td>
								<td>${order.gfmc }</td>
								<td>${order.jshj }</td>
								<td>${order.kpzt}</td>
								<td style="width: 60px;">
									<div class='hidden-phone visible-desktop btn-group'>
										<a class='btn btn-mini' title="查看" onclick="tagUser('${order.jylsh }');"><i class='icon-tag'>查看</i></a>
									</div>
								</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="10" class="center">没有相关数据</td>
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
			$("#orderForm").submit();
		}
		
		//重置
		function clearSearch(){
			$('input').val('');
		}

		
		//详情
		function tagUser(jylsh){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="详情";
				 diag.URL = "<%=basePath%>order/listOrderMX.do?jylsh="+jylsh;
				 diag.Width = 750;
				 diag.Height = 600;
				 diag.CancelEvent = function(){ //关闭事件
					nextPage('${page.currentPage}');
					diag.close();
				 };
				 diag.show();
		}
		
		
		
		//批量删除
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					var emstr = '';
					var phones = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') {
						  		str += document.getElementsByName('ids')[i].value;
						  	}else {
						  		str += ',' + document.getElementsByName('ids')[i].value;
						  	}
						  }
					}
					if(str==''){
						bootbox.dialog("您没有选择任何内容!", 
							[
							  {
								"label" : "关闭",
								"class" : "btn-small btn-success",
								"callback": function() {
									//Example.show("great success");
									}
								}
							 ]
						);
						$("#zcheckbox").tips({
							side:3,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>order/deleteOrder.do',
						    	data: {ids:str},
								dataType:'json',
								cache: false,
								success: function(data){
									if(data.msg='success'){
										nextPage('${page.currentPage}');
									}else{
										alert('删除失败！');
									}
								}
							});
						}
					}
				}
			});
		}		
		</script>	
	</body>
</html>

			