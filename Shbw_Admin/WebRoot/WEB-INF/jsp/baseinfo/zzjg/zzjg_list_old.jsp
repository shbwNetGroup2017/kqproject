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
<link rel="stylesheet" type="text/css" href="static/css/bootstrap-treeview.css">
<script type="text/javascript" src="static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="static/js/bootstrap-treeview.js"></script>

<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<!-- 检索  -->
					<form action="server/zzgl/listZzgl.do" method="post" name="Form" id="Form">
						<table>
							<tr>
								<td><span class="input-icon"> <input autocomplete="off" id="nav-search-input" type="text" name="xfmc" value="" placeholder="机构名称" /> <i id="nav-search-icon" class="icon-search"></i>
								</span></td>
								<td><span class="input-icon"> <input autocomplete="off" id="nav-search-input" type="text" name="xfsh" value="" placeholder="纳税人识别号" /> <i id="nav-search-icon" class="icon-search"></i>
								</span></td>
								<td style="vertical-align: top;"><button class="btn btn-mini btn-light" onclick="search();" title="检索">
										<i id="nav-search-icon" class="icon-search"></i>
									</button></td>
							</tr>
						</table>
						<!-- 检索  -->
						<div id="c" style="float: left; display: inline">
							<!-- 组织管理树结构 begin-->
							<div id="a" style="float: left; width: 200px; display: inline">
								<div id="tree"></div>
							</div>
							<!-- 组织管理树结构 end-->
							<!-- 组织机构table -->
							<div id="b" style="float: left; width: 1000px; display: inline">
								<table id="table_report" class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th class="center" onclick="selectAll()"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
											<th>序号</th>
											<th>机构名称</th>
											<th>机构代码</th>
											<th>纳税人识别号</th>
											<th>状态</th>
											<th>创建人</th>
											<th>创建时间</th>
											<th class="center">操作</th>
										</tr>
									</thead>

									<tbody>

										<!-- 开始循环 -->
										<c:choose>
											<c:when test="${not empty zzglList}">
												<c:forEach items="${zzglList}" var="var" varStatus="vs">
													<tr>
														<td class='center' style="width: 30px;"><label><input type='checkbox' name='ids' value="${var.id }" id="${user.id }" /><span class="lbl"></span></label></td>
														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td>${var.xfmc}</td>
														<td>${var.jgbm}</td>
														<td>${var.xfsh}</td>
														<td>${var.del_flag}</td>
														<td>${var.creator}</td>
														<td>${var.create_date}</td>
														<td width="60px;">
															<div class='hidden-phone visible-desktop btn-group'>
																<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${var.id}');"><i class='icon-edit'></i></a> <a class='btn btn-mini' title="详情" onclick="detail('${var.id }');"><i class='icon-home'></i></a>
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
									<table style="width: 100%;">
										<tr>
											<td style="vertical-align: top;"><a class="btn btn-small btn-success" onclick="add();">新增</a> <a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');"><i class='icon-trash'></i></a></td>
											<td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
										</tr>
									</table>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
	<!-- 引入 -->
	<script type="text/javascript">window.jQuery|| document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<!-- 引入 -->
	<script type="text/javascript">
	$(top.hangge()); 
	//增加新类型
	function add() {
		var arr = $('#tree').treeview('getSelected')[0];
		if (arr == null || arr == '') {
			alert('请选择上级机构!');
			return;
		}
		var sjjgbm = arr.jgbm;
		var text = arr.text;
		top.jzts();
		var diag = new top.Dialog();
		diag.Drag = true;
		diag.Title = "新增组织机构";
		diag.URL = '<%=basePath%>server/zzgl/addZzjg.do?sjjgbm='+sjjgbm+'&text='+text;
		diag.Width = 600;
		diag.Height = 400;
		diag.CancelEvent = function() { // 关闭事件
			if ('${page.currentPage}' == '0') {
				top.jzts();
				
			} else {
				nextPage = ${page.currentPage};
				//nextPage(nextPage);
			}
			setTimeout("self.location=self.location", 100);
			diag.close();
		};
		diag.show();
	}
	//修改类型
	function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑组织机构";
			 diag.URL = '<%=basePath%>server/zzgl/addZzjg.do?id='+Id;
			 diag.Width = 600;
			 diag.Height = 400;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 var nextPage=${page.currentPage}
					 setTimeout("self.location=self.location", 100);
					 //nextPage(nextPage);
				}
				diag.close();
			 };
			 diag.show();
		}
	//查看详细
	//修改类型
	function detail(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="组织机构详情";
			 diag.URL = '<%=basePath%>server/zzgl/lookZzjg.do?id='+Id;
			 diag.Width = 600;
			 diag.Height = 600;
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 var nextPage=${page.currentPage}
					 setTimeout("self.location=self.location", 100);
					 //nextPage(nextPage);
				}
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
								url: '<%=basePath%>server/zzgl/deleteZzjg.do',
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
	<style type="text/css">
li {
	list-style-type: none;
}
</style>

	<script type="text/javascript">
		///
		///
		$(function() {
			getTree();
			// list-group-item node-tree node-selected

		})
		function selectZzjgBySjjgbm() {
			// var arr = $('#tree').treeview('getSelected');
			var tt = '';
			alert(999999);
			/*
			 * if(node.state.selected){ var sjjgbm; node; }
			 */
		}
		// 节点点击事情函数
		function getTree() {
			var datas;
			$.ajax({
				url : 'server/zzgl/zzjgTree.do',
				type : 'POST', // GET
				async : false, // 或false,是否异步
				dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
				beforeSend : function(xhr) {
				},
				success : function(data, textStatus, jqXHR) {
					//datas = JSON.stringify(data.tree);
					var c = $("#c");
					$('#tree').treeview({
						//data : datas,
						data : data.tree,
						levels : 5,
						enableLinks : true,
						//onNodeChecked : selectZzjgBySjjgbm(),
						showBorder : false,
						multiSelect : false
					});

				},
				error : function(xhr, textStatus) {

				},
				complete : function() {

				}
			})
		}
		// ////////////////////////
	</script>
</body>
</html>