<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
						<table id="table_report" class="table table-striped table-bordered table-hover">

							<thead>
								<tr>
									<th class="center" onclick="selectAll()"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
									<th>序号</th>
									<th>抬头名称</th>
									<th>抬头税号</th>									
									<th>抬头地址</th>
									<th>抬头电话</th>	
									<th>抬头附件</th>			
								</tr>
							</thead>

							<tbody>

								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty customerTtInfoList}">
										<c:forEach items="${customerTtInfoList}" var="var" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;"><label><input type='checkbox' name='ids' value="${var.gfmc}" id="${var.id }" /><span class="lbl"></span></label></td>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td>${var.gfmc }</td>
												<td>${var.gfsh }</td>
												<td>${var.gfdz }</td>
												<td>${var.gfdh}</td>
												<td><c:if test="${var.fileName==null}">没有附件</c:if><c:if test="${var.fileName!=null}"><a href="server/customer/lookTtfj.do?url=${var.fileName}">查看抬头附件</a></c:if></td>	
											</tr>

										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="6" class="center">没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
						<div class="page-header position-relative">
							<table style="width: 100%;">
								<tr>		
									<td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
								</tr>
							</table>
						</div>				
			</div>
	</div>
	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i class="icon-double-angle-up icon-only"></i>
	</a>

	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>

	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
	<!-- 日期框 -->
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
	<!-- 确认窗口 -->
	<!-- 引入 -->


	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<!--提示框-->
	<script type="text/javascript">
		$(top.hangge());
		//清除收索输入
		function clearSearch() {
			$("input").val('');
			$("#gflx").val('');
		}
		//检索
		function search() {
			top.jzts();
			$("#customerInfoForm").submit();
		}
		//新增客户信息
		
		//批量操作
		function makeAll(msg){
				if(confirm(msg)){ 
						var str = '';
						var ttmc='';
						for(var i=0;i < document.getElementsByName('ids').length;i++)
						{
							  if(document.getElementsByName('ids')[i].checked){
							  	if(str=='') str +=document.getElementsByName('ids')[i].id;
							  	else str += ',' + document.getElementsByName('ids')[i].id; 
							  	if(ttmc=='') ttmc += document.getElementsByName('ids')[i].value;
							  	else ttmc += ',' + document.getElementsByName('ids')[i].value; 
							  }
						}
						if(str==''){
							alert("您没有选择任何内容!"); 
							return;
						}else{
							if(msg == '确定选中客户为抬头?'){	
								$("#ttIds").val(str);
								$("#ttmc").val(ttmc);
								top.Dialog.close();
						}
					}
				}
			}
			//全选 （是/否）
			function selectAll() {
				var checklist = document.getElementsByName("ids");
				if (document.getElementById("zcheckbox").checked) {
					for (var i = 0; i < checklist.length; i++) {
						checklist[i].checked = 1;
					}
				} else {
					for (var j = 0; j < checklist.length; j++) {
						checklist[j].checked = 0;
					}
				}
			}
	</script>

	<script type="text/javascript">
				
	</script>

</body>
</html>

