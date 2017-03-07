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
<link rel="stylesheet" type="text/css" href="plugins/zTree/3.5/zTreeStyle.css">
<!-- <script type="text/javascript" src="static/js/jquery-1.11.1.min.js"></script>
 -->
<script type="text/javascript" src="static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<div class="row-fluid">
					<!-- 检索  -->
					<form action="server/zzgl/listZzgl.do" method="post" name="Form" id="Form">
						<!-- 查询的Table -->
						<table style="text-align: center; margin: auto;">
							<tr>
								<td style="width: 100px;"><span style="font-size: 15px;">机构名称:</span></td>
								<td><span class="input-icon">
										<td><span class="input-icon"><input type="hidden" id="sjjgbm" name="sjjgbm" value="" placeholder="快钱机构" /> <input autocomplete="off" id="nav-search-input" type="text" name="xfmc" value="${pd.xfmc}" placeholder="机构名称" /> <i id="nav-search-icon" class="icon-search"></i> </span></td> 
								</span></td>
								<td style="width: 100px;"><span style="font-size: 15px;">纳税人识别号:</span></td>
								<td><span class="input-icon"> <span class="input-icon"> <input id="nav-search-input" type="text" name="xfsh" value="${pd.xfsh}" placeholder="纳税人识别号" /> <i id="nav-search-icon" class="icon-search"></i>
									</span></td>
							</tr>
							<tr>
								<td style="vertical-align: top;text-align: right;" colspan="3">
									<button class="btn btn-mini btn-light" onclick="search();"  title="检索">
									<i id="nav-search-icon" class="icon-search">查询</i>
									</button>
								</td>
								<td style="vertical-align: top;text-align:left;" colspan="2">
						    		<a class="btn btn-mini btn-light" onclick="clearSearch()" title="重置">
									<i id="nav-search-icon" class="icon-refresh">重置</i>
									</a>
								</td>						
							</tr>
						</table>
						<!-- 查询的Table End -->
						<!-- 检索  -->
						<div id="c" style="float: left; display: inline">
							<!-- 组织管理树结构 begin-->
							<div id="a" style="float: left; width: 190px; display: inline">
								<div class="zTreeDemoBackground left">
									<ul id="tree" class="ztree"></ul>
								</div>
							</div>
							<!-- 组织管理树结构 end-->
							<!-- 组织机构table -->

							<div id="b" style="float: left; width: 900px; display: inline">
								<td style="vertical-align: top;"><a class="btn btn-small btn-success" onclick="add();">新增</a> <a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');"><i class='icon-trash'></i>删除</a></td>
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
														<td class='center' style="width: 30px;"><label><input type='checkbox' name='ids' value="${var.id }" id="${var.jgbm }" /><span class="lbl"></span></label></td>
														<td class='center' style="width: 30px;">${vs.index+1}</td>
														<td>${var.xfmc}</td>
														<td>${var.jgbm}</td>
														<td>${var.xfsh}</td>
														<td><c:if test="${var.zt==0}">启用</c:if> <c:if test="${var.zt==1}">未启用</c:if></td>
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
													<td colspan="10" class="center">没有相关数据</td>
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

							<!-- 组织机构tableEnd -->
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
		var node;
		var sjjgbm ;
		var text;
		if("true"=="${empty zzglList}"){
			sjjgbm=0;
			text='顶级机构';
		}else{
			var treeObj=$.fn.zTree.getZTreeObj("tree"),
			nodes=treeObj.getSelectedNodes(true);
			if (nodes == null || nodes == '') {
				alert('请选择上级机构!');
				return;
			}
			if(nodes.length>1){
				alert('只能选择一个上级机构');
				return;
			}
			 node=nodes[0];
			 sjjgbm = node.id;
			 text = node.name;
		}	
		top.jzts();
		var diag = new top.Dialog();
		diag.Drag = true;
		diag.Title = "新增组织机构";
		diag.URL = '<%=basePath%>server/zzgl/addZzjg.do?sjjgbm='+sjjgbm+'&text='+text;
		diag.Width = 600;
		diag.Height = 410;
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
			 diag.Height = 410;
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
					var jgbm='';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += "'"+document.getElementsByName('ids')[i].value+"'";
						  	else str += ',' + "'"+document.getElementsByName('ids')[i].value+"'";
						  	if(jgbm=='') jgbm += document.getElementsByName('ids')[i].id;
						  	else jgbm += ',' +document.getElementsByName('ids')[i].id;
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
								DATA_IDS : str,
								jgbm : jgbm
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
		function clearSearch() {
			$("input").val('');
		}
		/* function onClick(e, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("tree");
			zTree.expandNode(treeNode);
		} */
		function onClick(event, treeId, treeNode, clickFlag) {
			treeNode;
			var sjjgbm = treeNode.id;
			$("#Form")[0].reset();
			$("#sjjgbm").val(sjjgbm);
			$("#Form").submit();
			/* $.ajax({
				url : 'server/zzgl/zzjgByTreeId.do',
				type : 'POST',
				data : {
					id : treeNode.id
				},
				async : false,
				dataType : 'json',
				success : function(data) {
					alert('success');
				}
			}) */
		}
		// 节点点击事情函数
		function getTree() {
			var datas;
			$.ajax({
				url : 'server/zzgl/zzjgTree.do',
				//url:'server/customer/test',
				type : 'POST', // GET
				async : false, // 或false,是否异步
				dataType : 'json', // 返回的数据格式：json/xml/html/script/jsonp/text
				beforeSend : function(xhr) {
				},
				success : function(data, textStatus, jqXHR) {
					//datas = JSON.stringify(data.tree);
					var setting = {
						view : {
							dblClickExpand : false,
							showIcon : true,
							showLine : true
						//屏蔽掉双击事件
						},
						data : {
							simpleData : {
								enable : true
							}
						},
						callback : {
							onClick : onClick
						}
					};
					zTreeObj = $.fn.zTree.init($("#tree"), setting, data.tree);
					zTreeObj.expandAll(true);
					//设置选中状态	
					var treeObj = $.fn.zTree.getZTreeObj("tree");
					var node = treeObj.getNodeByParam("id", "${jgbm}");
					treeObj.selectNode(node, false);

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