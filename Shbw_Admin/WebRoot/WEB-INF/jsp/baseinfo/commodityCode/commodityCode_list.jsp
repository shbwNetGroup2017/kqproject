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
			<div class="row-fluid">
				<div class="row-fluid">
					<!-- 检索  -->
					<form action="server/commodity/code.do" method="post" name="customerInfoForm" id="customerInfoForm">
						<table style="text-align: center; margin: auto;">
							<tr>
								<td style="width: 80px;"><span style="font-size: 15px;">商品统一编码:</span></td>
								<td><span class="input-icon"> <input autocomplete="off" id="nav-search-input" type="text" name="spbm" value="${pd.spbm}" placeholder="这里输入商品统一编码" /> <i id="nav-search-icon" class="icon-search"></i>
								</span></td>
								<td style="width: 80px;"><span style="font-size: 15px;">商品名称:</span></td>
								<td><span class="input-icon"> <input autocomplete="off" id="nav-search-input" type="text" name="yspmc" value="${pd.yspmc}" placeholder="这里输入商品名称" /> <i id="nav-search-icon" class="icon-search"></i>
								</span></td>										
							</tr>
							<tr>
								<td style="vertical-align: top;text-align:center;" colspan="4">
									<button class="btn btn-mini btn-light" onclick="search();"  title="查询">
									<i id="nav-search-icon" class="icon-search">查询</i>
									</button>
						    		<a class="btn btn-mini btn-light" onclick="clearSearch()" title="重置">
									<i id="nav-search-icon" class="icon-refresh">重置</i>
									</a>
									<a class="btn btn-mini btn-light" onclick="fromExcel();" title="从EXCEL导入"><i id="nav-search-icon" class="icon-cloud-upload">上传</i></a>
								</td>					
							</tr>
						</table>
						<!-- 检索  -->
						<td style="vertical-align: top;"><a class="btn btn-small btn-success" onclick="add();">新增</a> <a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');"><i class='icon-trash'></i>删除</a></td>
						<table id="table_report" class="table table-striped table-bordered table-hover">
							<thead>
								<tr>
									<th class="center" onclick="selectAll()"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
									<th>序号</th>
									<th>商品统一编码</th>
									<th>原商品编码</th>
									<th>原商品名称</th>
									<th>税率</th>
									<th>数据来源</th>
									<th>创建人</th>
									<th>创建时间</th>
									<th class="center">操作</th>
								</tr>
							</thead>

							<tbody>

								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty list}">
										<c:forEach items="${list}" var="var" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;"><label><input type='checkbox' name='ids' value="${var.id }" id="${var.id }" /><span class="lbl"></span></label></td>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td>${var.spbm }</td>
												<td>${var.yspbm }</td>
												<td>${var.yspmc }</td>
												<td>${var.sl }</td>
												<td>
												  <c:if test="${var.sjly==0}">ODS数据仓库</c:if>
												  <c:if test="${var.sjly==1}">Oracle ERP系统</c:if>
												  <c:if test="${var.sjly==2}">进件系统</c:if>
												  <c:if test="${var.sjly==3}">商派</c:if>
												</td>
												<td>${var.creator}</td>
												<td>${var.create_date}</td>
												<td width="60px;">
													<div class='hidden-phone visible-desktop btn-group'>
														<a class='btn btn-mini btn-info' title="修改" onclick="edit('${var.id}');"><i class='icon-edit'></i>修改</a> <a class='btn btn-mini' title="查看详情" onclick="detail('${var.id }');"><i class='icon-tag'></i>查看</a>
													</div>
												</td>
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
							<table style="width: 100%;">
								<tr>
									<td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>

		</div>
	</div>

	<!-- 返回顶部  -->
	<a href="#" id="btn-scroll-up" class="btn btn-small btn-inverse"> <i class="icon-double-angle-up icon-only"></i>
	</a>

	<!-- 引入 -->
    <script src='static/js/jquery-1.9.1.min.js'></script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script>
	<script type="text/javascript" src="static/js/bootbox.min.js"></script>
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
		//打开上传excel页面
		function fromExcel(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="EXCEL 导入到数据库";
			 diag.URL = '<%=basePath%>server/commodity/goCommodityCodeExcel.do';
			 diag.Width = 450;
			 diag.Height = 300;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${page.currentPage}' == '0'){
						 top.jzts();
						 setTimeout("self.location.reload()",100);
					 }else{
						 nextPage('${page.currentPage}');
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		//新增商品编码信息
		function add() {
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag = false;
			diag.Title = "新增商品编码信息";
			diag.URL = '<%=basePath%>server/commodity/editCommodityCodeInfo.do?type=0'
			diag.Width = 500;
			diag.Height = 520;
			diag.CancelEvent = function() { // 关闭事件
				if ('${page.currentPage}' == '0') {
					top.jzts();
				} else {
					nextPage = '${page.currentPage}';
				}
				setTimeout("self.location=self.location", 100);
				diag.close();
			};
			diag.show();
		}
		//新增客户
		function addKh(){
			window.location='<%=basePath%>server/commodity/editCustomerInfo.do?type=0';
		}
		
		//编辑信息
		function edit(Id) {
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag = true;
			diag.Title = "编辑商品编码信息";
			diag.URL = '<%=basePath%>server/commodity/editCommodityCodeInfo.do?type=1&id='+Id;
			diag.Width = 500;
			diag.Height = 520;
			diag.CancelEvent = function() { // 关闭事件
				if ('${page.currentPage}' == '0') {
					top.jzts();
				} else {
					nextPage = '${page.currentPage}';
				}
				setTimeout("self.location=self.location", 100);
				diag.close();
			};
			diag.show();
		}
		
		//查看客户信息详情
		function detail(Id) {
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag = true;
			diag.Title = "商品编码详情信息";
			diag.URL = '<%=basePath%>server/commodity/editCommodityCodeInfo.do?type=2&id='+Id;
			diag.Width = 600;
			diag.Height = 700;
			diag.CancelEvent = function() { // 关闭事件
				if ('${page.currentPage}' == '0') {
					top.jzts();
				} else {
					nextPage = '${page.currentPage}';
				}
				setTimeout("self.location=self.location", 100);
				diag.close();
			};
			diag.show();
		}
		//批量操作
		function makeAll(msg){
				if(confirm(msg)){ 
						var str = '';
						for(var i=0;i < document.getElementsByName('ids').length;i++){
							if(document.getElementsByName('ids')[i].checked){
							  	if(str==''){
							  		str += "'"+document.getElementsByName('ids')[i].value+"'";
							  	}else{
							  		str += ',' + "'"+document.getElementsByName('ids')[i].value+"'";
							  	}
							}
						}
						if(str==''){
							alert("您没有选择任何内容!"); 
							return;
						}else{
							if(msg == '确定要删除选中的数据吗?'){
								top.jzts();
								$.ajax({
									type: "POST",
									url: '<%=basePath%>server/commodity/deleteCommodityCodeInfo.do',
									data : {
										DATA_IDS : str
									},
									dataType : 'json',
									cache : false,
									success : function(data) {
										if (data.msg == 'ok') {
											alert('删除成功！');
										} else {
											alert('删除失败！');
										}
										location.replace(location);
									}
								});
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
		$(function() {
			$(function() {
				// $("#jumpMenu").val(要选中的option的value值即可);
				$("#gflx").val('${pd.gflx}');
			});
		})
	</script>

</body>
</html>

