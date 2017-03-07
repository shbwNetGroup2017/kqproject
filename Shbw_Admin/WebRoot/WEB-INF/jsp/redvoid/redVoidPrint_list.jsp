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
<%@ include file="../system/admin/top.jsp"%>
</head>
<body>

	<div class="container-fluid" id="main-container">


		<div id="page-content" class="clearfix">

			<div class="row-fluid">


				<div class="row-fluid">

					<!-- 检索  -->
					<form action="server/redVoid/redVoidShow.do?kpbz=3" method="post" name="redVoidListForm" id="redVoidListForm">
						<table style="text-align: center; margin: auto;">
							<tr>
								<td><label>开票单位名称：</label></td>
								<td><span class="input-icon"> <input class="span12" autocomplete="off" id="nav-search-xfmc" type="text" name="xfmc" value="${pd.xfmc}" placeholder="这里输入开票单位" />
								</span></td>
								<td><label>客户名称：</label></td>
								<td><span class="input-icon"> <input class="span12" autocomplete="off" id="nav-search-gfmc" type="text" name="gfmc" value="${pd.gfmc}" placeholder="这里输入客户名称" />
								</span></td>
								<td><label>收款类型：</label></td>
								<td><span class="input-icon"> <input class="span12" autocomplete="off" id="nav-search-sklx" type="text" name="sklx" value="${pd.sklx}" placeholder="这里输入收款类型" />
								</span></td>
								<td><label>开始日期：</label></td>
								<td><input class="span12 date-picker" name="sqsjStart" id="sqsjStart" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="开始日期" title="开始日期"/></td>

							</tr>
							<tr>
								<td><label>开票申请编码：</label></td>
								<td><span class="input-icon"> <input class="span12" autocomplete="off" id="nav-search-kpsqm" type="text" name="kpsqbm" value="${pd.kpsqbm}" placeholder="这里输入开票申请码" />
								</span></td>
								<td><label>申请类型：</label></td>
								<td><select id="sqlx" class="span12" name="sqlx">
										<option value="">全部</option>
										<option value="0">固定开票申请（新增）</option>
										<option value="1">非固定开票申请</option>
										<option value="2">固定开票申请（变更）</option>
								</select></td>
								<td><label>发票类型：</label></td>
								<td><select id="fplx" class="span12" name="fplx">
										<option value="">全部</option>
										<option value="004">专票</option>
										<option value="007">普票</option>
										<option value="026">电子发票</option>
								</select></td>								
								<td><label>结束日期：</label></td>
								<td><input class="span12 date-picker" name="sqsjEnd" id="sqsjEnd"  type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="结束日期" title="结束日期"/></td>
							</tr>
							<tr>
								<td style="vertical-align: top; text-align: right;" colspan="3">
									<button class="btn btn-mini btn-light" onclick="search();" title="查询">
										<i id="nav-search-icon" class="icon-search">查询</i>
									</button>
								</td>
								<td style="vertical-align: top; text-align: right;" colspan="2">
									<a class="btn btn-mini btn-light" onclick="clearSearch()" title="重置">
										<i id="nav-reset-icon" class="icon-refresh">重置</i>
									</a>
								</td>
							</tr>
						</table>
						<!-- 检索  -->

						<!-- <td style="vertical-align: top;"><a class="btn btn-small btn-success" onclick="add();">新增</a> <a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');"><i class='icon-trash'></i>删除</a></td> -->
						<table id="table_report" class="table table-striped table-bordered table-hover">

							<thead>
								<tr>
									<th class="center" onclick="selectAll()"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
									<th>序号</th>
									<th>快钱Id</th>
									<th>开票单位名称</th>
									<th>开票单位识别号</th>
									<th>客户名称</th>
									<th>客户识别号</th>
									<th>开票类型</th>
									<th>发票代码</th>
									<th>开票日期</th>
									<th>价税合计</th>
									<th>金额合计</th>
									<th>税额合计</th>
									<th>申请类型</th>
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
												<td>${var.id }</td>
												<td>${var.xfmc }</td>
												<td>${var.xfsh }</td>
												<td>${var.gfmc }</td>
												<td>${var.gfsh }</td>
												<td><c:choose>
														<c:when test="${var.fplx==004}">
									专票
									</c:when>
														<c:when test="${var.fplx==007}">
									普票
									</c:when>
														<c:when test="${var.fplx==026}">
									电子发票
									</c:when>
														<c:otherwise>
										无效类型
									</c:otherwise>
													</c:choose></td>
												<td>${var.fpdm }</td>
												<td>${var.kprq }</td>
												<td>${var.jshj}</td>
												<td>${var.hjje}</td>
												<td>${var.hjse}</td>
												<%-- <td>${var.zje}</td>
												<td>${var.ykpje}</td>
												<td>${var.ykpje}</td> --%>
												<td><c:if test="${var.kpbz==1}">红冲</c:if><c:if test="${var.kpbz==2}">作废</c:if><c:if test="${var.kpbz==3}">重打</c:if></td>
												<td width="60px;">
													<div class='hidden-phone visible-desktop btn-group'>
														<a class='btn btn-mini btn-info' title="预览" onclick="preview('${var.id}','${var.kplsh}');"><i class='icon-tag'></i>预览</a> <a class='btn btn-mini' title="开具" onclick="openkp('${var.id}','${pd.kpr}','${var.fplx}','${var.kplsh}','${var.kpbz}','${var.fpdm}','${var.fphm}');"><i class='icon-edit'></i>开具</a>
													</div>
												</td>
											</tr>

										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="15" class="center">没有相关数据</td>
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
			$("select").val('');
			$("#gflx").val('');
		}
		//检索
		function search() {
			top.jzts();
			$("#redVoidListForm").submit();
		}
		
		function openkp(id,kpr,fplx,kplsh,kpbz,fpdm,fphm){			
			if(kpbz==3){//重打			
				var data = top.getJava("fpcd",fplx,kplsh,kpr,fpdm,fphm,id);
				alert(data); 
			}else{
				var result=top.getJava("getKpXml",fplx,kplsh,kpr,id);
				alert(result);
				var json=jQuery.parseJSON(result);
				if(json.state=="error"){
					alert("无法进行开票操作,"+json.msg+"!");
				}else if(json.state=="0"){
					alert("开票成功！");
				}else if(json.state=="1"){
					alert("开票失败,"+json.msg+"!");
				}else if(json.state=="fail"){
					alert(json.msg);
				} 
			}
		}
		
	</script>

	<script type="text/javascript">
	//预览
	function preview(id,kplsh){		    
		 top.jzts();
		 var diag = new top.Dialog();
		 diag.Drag=true;
		 diag.Title ="预览";
		 diag.URL = "<%=basePath%>kjpj/toKjpjPreview.do?id="+id+"&kplsh="+kplsh;
		 diag.Width = 1250;
		 diag.Height = 410;
		 diag.CancelEvent = function(){ //关闭事件
				if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					nextPage('${page.currentPage}');
				}
				diag.close();
		  };
		 diag.show();
	}
		$(function() {
			$(function() {
				// $("#jumpMenu").val(要选中的option的value值即可);
				$("#sqlx").val('${pd.sqlx}');
				$("#fplx").val('${pd.fplx}');
			});
			var startTime="";
			var endTime="";
			$('#sqsjStart').datepicker().on('changeDate',function(dateText){
				if(endTime!=""){
					if(dateText.date>endTime){
						alert("开始日期要小于开始日期");
					}
				}
				startTime=dateText.date; //保存开始时间，用于下次比较
				});
			$('#sqsjEnd').datepicker().on('changeDate',function(dateText){
				if(startTime!=""){
					if(dateText.date<startTime){
						alert("结束日期要大于开始日期");
					}
				}
				endTime = dateText.date;//保存结束时间，用于下次比较
				});
		})
	</script>

</body>
</html>

