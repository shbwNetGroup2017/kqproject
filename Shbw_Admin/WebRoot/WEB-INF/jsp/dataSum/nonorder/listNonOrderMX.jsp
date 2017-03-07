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
			<form action="nonorder/listNonOrders.do" method="post" name="nonOrderForm" id="nonOrderForm">
			

			<table id="table_report" class="table table-striped table-bordered table-hover">
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<!-- <th>开票流水号</th> -->
						<!-- <th>交易来源ID</th> -->
						<th>交易来源名称</th>
						<th>业务类型</th>
						<th>金额</th>
                        <th>终端类型</th>
                        <th>终端编号</th>
                        <!-- <th>结算类型</th> -->
                        <th>创建人</th>
                        <th>创建时间</th>
                        <th>修改人</th>
                        <th>修改时间</th>
                        <!-- <th>操作</th> -->
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty orderList}">
						<c:forEach items="${orderList}" var="ykfpcx" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${ykfpcx.jylsh }" id="${ykfpcx.jylsh }"/><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<%-- <td>${ykfpcx.kplsh }</td> --%>
								<%-- <td>${ykfpcx.jylsly }</td> --%>
								<td>${ykfpcx.jylsly_ch }</td>
								<td>${ykfpcx.ywlx }</td>
								<td>${ykfpcx.je }</td>
								<td>${ykfpcx.zdlx }</td>
								<td>${ykfpcx.zd }</td>
								<%-- <td>${ykfpcx.jslx }</td> --%>
								<td>${ykfpcx.creator }</td>
								<td>${ykfpcx.create_date}</td>
								<td>${ykfpcx.updator}</td>
								<td>${ykfpcx.update_date}</td>
								<%-- <td style="width: 60px;">
									<div class='hidden-phone visible-desktop btn-group'>
										<a class='btn btn-mini' title="查看正数" onclick="listNonOrderMX('${ykfpcx.jslx }','${ykfpcx.zd}','${ykfpcx.zdlx}',${ykfpcx.ywlx},0);"><i class='icon-tag'>查看正数</i></a>
										<a class='btn btn-mini' title="查看负数" onclick="listNonOrderMX('${ykfpcx.jslx }','${ykfpcx.zd}','${ykfpcx.zdlx}',${ykfpcx.ywlx},1);"><i class='icon-tag'>查看负数</i></a>
									</div> 
								</td> --%>
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
			
			$("#nonOrderForm").submit();
		}
		
		//重置
		function clearSearch(){
			$('input').val('');
		}

		
		//详情p1:结算类型(jslx)P2:终端类型(zhlx)P3:终端编号(zh)P4:业务类型(ywlx)P5:正负(zf0正1负)
		 function listNonOrderMX(jslx,zhlx,zd,ywlx,zf){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="详情";
				   diag.URL = "<%=basePath%>nonorder/listNonOrderMX.do?zd="+zd+"&jslx="+jslx+"&zdlx="+zd+"&ywlx="+ywlx+"&zf="+zf;
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

			