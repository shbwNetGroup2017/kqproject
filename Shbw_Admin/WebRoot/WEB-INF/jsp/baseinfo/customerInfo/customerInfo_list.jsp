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
					<form action="server/customer/customerList.do" method="post" name="customerInfoForm" id="customerInfoForm">
						<table style="text-align: center; margin: auto;">
							<tr>
								<td style="width: 80px;"><span style="font-size: 15px;">客户税号:</span></td>
								<td><span class="input-icon"> <input autocomplete="off" id="nav-search-input" type="text" name="gfsh" value="${pd.gfsh}" placeholder="这里输入客户税号" /> <i id="nav-search-icon" class="icon-search"></i>
								</span></td>
								<td style="width: 80px;"><span style="font-size: 15px;">客户名称:</span></td>
								<td><span class="input-icon"> <input autocomplete="off" id="nav-search-input" type="text" name="gfmc" value="${pd.gfmc}" placeholder="这里输入客户名称" /> <i id="nav-search-icon" class="icon-search"></i>
								</span></td>
								<td style="width: 80px;"><span style="font-size: 15px;">客户类型:</span></td>
								<td><span class="input-icon"> <select name="gflx" id="gflx">
											<option value="企业">企业</option>
											<option value="个人">个人</option>
											<option value="" selected="selected">全部</option>
									</select>
								</span></td>
							</tr>
							<tr>
								<td style="vertical-align: top; text-align: right;" colspan="3">
									<button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search">查询</i>
									</button>
								</td>
								<td style="vertical-align: top; text-align: left;" colspan="2"><a class="btn btn-mini btn-light" onclick="clearSearch()" title="重置"> <i id="nav-search-icon" class="icon-refresh">重置</i>
								</a></td>
							</tr>
						</table>
						<!-- 检索  -->

						<td style="vertical-align: top;"><a class="btn btn-small btn-success" onclick="add();">新增</a> <a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');"><i class='icon-trash'></i>删除</a></td>
						<table id="table_report" class="table table-striped table-bordered table-hover">

							<thead>
								<tr>
									<th class="center" onclick="selectAll()"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
									<th>序号</th>
									<th>客户名称</th>
									<th>客户税号</th>
									<th>客户类型</th>
									<th>客户地址</th>
									<th>客户电话</th>
									<th>开户银行</th>
									<th>开户银行账号</th>
									<!-- <th>可开票金额</th>
									<th>已开票金额</th>
									<th>正在开票金额</th> -->
									<th class="center">操作</th>
								</tr>
							</thead>

							<tbody>

								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty customerInfoList}">
										<c:forEach items="${customerInfoList}" var="var" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;"><label><input type='checkbox' name='ids' value="${var.id }" id="${var.id }" /><span class="lbl"></span></label></td>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td>${var.gfmc }</td>
												<td>${var.gfsh }</td>
												<td>${var.gflx }</td>
												<td>${var.gfdz }</td>
												<td>${var.gfdh}</td>
												<td>${var.gfyh}</td>
												<td>${var.gfyhzh}</td>
												<%-- <td>${var.zje}</td>
												<td>${var.ykpje}</td>
												<td>${var.ykpje}</td> --%>
												<td width="60px;">
													<div class='hidden-phone visible-desktop btn-group'>
														<a class='btn btn-mini btn-info' title="修改" onclick="edit('${var.id}');"><i class='icon-edit'></i>修改</a> <a class='btn btn-mini' title="查看详情" onclick="detail('${var.id }');"><i class='icon-tag'></i>查看</a> <a class='btn btn-mini' title="查看抬头" onclick="lookTt('${var.gf_yxtid }');"><i
															class='icon-tag'></i>查看抬头</a>
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
		//增加新类型
		function add() {
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag = false;
			diag.Title = "新增客户信息";
			diag.URL = '<%=basePath%>server/customer/editCustomerInfo.do?type=0&size='+'${size}';
			diag.Width = 600;
			diag.Height = 590;
			diag.CancelEvent = function(i) { // 关闭事件
				var addBz=diag.innerFrame.contentWindow.document
						.getElementById('addBz').value;
			    var ttfjName=diag.innerFrame.contentWindow.document
				.getElementById('ttfjName').value;
			    //不打算添加新客户时删除已经上传的附件
			    if(addBz=='fail'){
			    	if(ttfjName!=null&&ttfjName!=''){
			    		$.ajax({
			    			type: "POST",
			    			url: 'server/customer/deleteCustomerTtFj.do',
			    	    	data: {
			    	    		ttfjName:ttfjName
			    	    	},
			    			dataType:'json',
			    			cache: false,
			    			success: function(data){
			    				 
			    			}
			    		});
			    	}
			    }
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
			window.location='<%=basePath%>server/customer/editCustomerInfo.do?type=0';
		}
		//增加新类型
		function edit(Id) {
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag = true;
			diag.Title = "编辑客户信息";
			diag.URL = '<%=basePath%>server/customer/editCustomerInfo.do?type=1&id='+Id;
			diag.Width = 600;
			diag.Height = 620;
			diag.CancelEvent = function() { // 关闭事件
				var addBz=diag.innerFrame.contentWindow.document
				.getElementById('addBz').value;
	    		var ttfjName=diag.innerFrame.contentWindow.document.getElementById('ttfjName').value;
	    		//不打算添加新客户时删除已经上传的附件
	   			 if(addBz=='fail'){
	    			if(ttfjName!=null&&ttfjName!=''){
	    			$.ajax({
	    				type: "POST",
	    				url: 'server/customer/deleteCustomerTtFj.do',
	    	    		data: {
	    	    			ttfjName:ttfjName
	    	    		},
	    				dataType:'json',
	    				cache: false,
	    				success: function(data){
	    				 
	    				}
	    			});
	    		}
	   		 }
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
		//查看抬头信息
		function lookTt(khid){
			top.jzts();
			var diagTt = new top.Dialog();
			diagTt.Drag = true;
			diagTt.Title = "客户抬头信息";
			diagTt.URL = '<%=basePath%>server/customer/lookCustomerTtInfo.do?khid='+khid;
			diagTt.Width = 800;
			diagTt.Height = 300;
			diagTt.CancelEvent = function() { // 关闭事件
				if ('${page.currentPage}' == '0') {
					top.jzts();

				} else {
					nextPage = '${page.currentPage}';
				}
				setTimeout("self.location=self.location", 100);
				diagTt.close();
			};
			diagTt.show();
		}
		//查看客户信息详情
		function detail(Id) {
			top.jzts();
			var diag = new top.Dialog();
			diag.Drag = true;
			diag.Title = "客户详情信息";
			diag.URL = '<%=basePath%>server/customer/editCustomerInfo.do?type=2&id='+Id;
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
						for(var i=0;i < document.getElementsByName('ids').length;i++)
						{
							  if(document.getElementsByName('ids')[i].checked){
							  	if(str=='') str += "'"+document.getElementsByName('ids')[i].value+"'";
							  	else str += ',' + "'"+document.getElementsByName('ids')[i].value+"'";
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
									url: '<%=basePath%>server/customer/deleteCustomerInfo.do',
									data : {
										DATA_IDS : str
									},
									dataType : 'json',
									//beforeSend: validateData,
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

