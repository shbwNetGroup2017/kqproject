<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
	<%@ include file="../admin/top.jsp"%> 
	<style type="text/css">
		select{width:150px;}
	</style>
	</head> 
<body>
		
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">					
  <div class="row-fluid">
	<div class="row-fluid">	
			<!-- 检索  -->
			<form action="resource/listResource.do" method="post" name="resourceForm" id="resourceForm">
			<table style="text-align: center;margin:auto;">
				<tr>
					<td style="width: 100px;">
					   <span style="font-size: 15px;">资源名称:</span>
					</td>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text"  name="zymc" value="${pd.zymc}" placeholder="这里输入资源名称" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>						
					</td>
					<td style="width: 100px;">
					   <span style="font-size: 15px;">上级资源:</span>
					</td>
					<td>
						<span class="input-icon">
							<c:choose>	
				                <c:when test="${not empty fzyList}">	
				                	<select name="zyxh" id="zyxh">
					            		<option value="">--请选择资源--</option>
										<c:forEach items="${fzyList}" var="item" varStatus="status">  
								 			<option value="${item.zyxh}" <c:if test="${pd.zyxh==item.zyxh}">selected</c:if>>${item.zymc}</option>
									    </c:forEach>  
									</select>
								</c:when>
							</c:choose>	
						</span>	
					</td>
				</tr>
				<tr>
						<td style="vertical-align: top;" colspan="4">
							<button class="btn btn-mini btn-light" onclick="search();"  title="检索">
								<i id="nav-search-icon" class="icon-search">查询</i>
							</button>
							&nbsp;&nbsp;
							<a class="btn btn-mini btn-light" onclick="clean();" title="重置">
								<i id="nav-search-icon" class="icon-refresh">重置</i>
							</a>
						</td>
				</tr>
			</table>				
			<!-- 检索  -->
			<div style="margin-bottom: 10px;border-bottom: 1px solid #eee;">
					<a class="btn btn-small btn-success" onclick="add();">新增</a>
					<a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" ><i class='icon-trash'></i>删除</a>
			</div>
			<table id="table_report" class="table table-striped table-bordered table-hover">				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>序号</th>
						<th>资源名称</th>
						<th>状态</th>
						<th>上级资源</th>
						<th>资源路径</th>
						<th>资源序号</th>
						<th>创建人</th>
						<th>创建时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
														
				<tbody>					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty resourceList}">
						<c:forEach items="${resourceList}" var="resource" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${resource.id }|${resource.zyxh}" id="${resource.id}"/><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>${resource.zymc}</td>
								<td>
									<c:choose>
										<c:when test="${resource.zt=='0'}">
											启用
										</c:when>
										<c:otherwise>
											<font color="red">禁用</font>
										</c:otherwise>
									</c:choose>		
								</td>
								<td>${resource.fzy}</td>
								<td>${resource.zylj}</td>
								<td>${resource.zyxh}</td>
								<td>${resource.creator}</td>
								<td>${fn:replace(resource.create_date,'.0','')}</td>
								<td style="width: 60px;">
									<div class='hidden-phone visible-desktop btn-group'>
										<a class='btn btn-mini btn-info' title="编辑" onclick="editResource('${resource.id }','${resource.zyxh }');"><i class='icon-edit'></i>编辑</a>
				                        <a class='btn btn-mini' title="查看" onclick="tagResource('${resource.id }','${resource.zyxh}');"><i class='icon-tag'></i>查看</a>
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
		<script type="text/javascript" src="static/js/jquery.tips.js"></script><!--提示框-->
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		<script type="text/javascript" src="static/js/bootstrap-datepicker.min.js"></script><!-- 日期框 -->
		<script type="text/javascript" src="static/js/bootbox.min.js"></script><!-- 确认窗口 -->
		<!-- 引入 -->
				
		<script type="text/javascript">
		
			$(top.hangge());
			
			//检索
			function search(){
				top.jzts();
				$("#resourceForm").submit();
			}
			
			//重置
			function clean(){
			    $('#nav-search-input').val('');		   
				$('#zyxh').val('');
			}
			
			//新增界面
			function add(){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="新增资源";
				 diag.URL = "<%=basePath%>resource/toAddUpdateResource.do";
				 diag.Width = 350;
				 diag.Height = 450;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 if('${page.currentPage}' == '0'){
							 top.jzts();
							 setTimeout("self.location=self.location",100);
						 }else{
							 nextPage('${page.currentPage}');
						 }
					}
					diag.close();
				 };
				 diag.show();
			}
			
			//修改
			function editResource(id,zyxh){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="修改资源";
				 diag.URL = "<%=basePath%>resource/toAddUpdateResource.do?id="+id+"&zyxh="+zyxh;
				 diag.Width = 350;
				 diag.Height = 350;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						nextPage('${page.currentPage}');
					}
					diag.close();
				 };
				 diag.show();
			}
			
			//详情
			function tagResource(id,zyxh){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="资源详情";
				 diag.URL = "<%=basePath%>resource/toDetailsResource.do?id="+id+"&zyxh="+zyxh;
				 diag.Width = 350;
				 diag.Height = 450;
				 diag.CancelEvent = function(){ //关闭事件
					if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						nextPage('${page.currentPage}');
					}
					diag.close();
				 };
				 diag.show();
			}
			
			
			//批量操作
			function makeAll(msg){
				bootbox.confirm(msg, function(result) {
					if(result) {
						var str = '';
						var emstr = '';
						var phones = '';
						for(var i=0;i < document.getElementsByName('ids').length;i++)
						{
							  if(document.getElementsByName('ids')[i].checked){
							  	if(str=='') str += document.getElementsByName('ids')[i].value;
							  	else str += ',' + document.getElementsByName('ids')[i].value;
							  	
							  	if(emstr=='') emstr += document.getElementsByName('ids')[i].id;
							  	else emstr += ';' + document.getElementsByName('ids')[i].id;
							  	
							  	if(phones=='') phones += document.getElementsByName('ids')[i].alt;
							  	else phones += ';' + document.getElementsByName('ids')[i].alt;
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
									url: '<%=basePath%>resource/deleteResource.do?tm='+new Date().getTime(),
							    	data: {id:str},
									dataType:'json',
									cache: false,
									success: function(data){
										 if(data.msg=='success'){
										 	nextPage('${page.currentPage}');
										 }else{
										 	alert('删除失败！');
										 	nextPage('${page.currentPage}');
										 }
									}
								});
							}
						}
					}
				});
			}
		
		</script>
		
		<script type="text/javascript">
		
			$(function() {
				
				//日期框
				$('.date-picker').datepicker();
				
				//下拉框
				$(".chzn-select").chosen(); 
				$(".chzn-select-deselect").chosen({allow_single_deselect:true}); 
				
				//复选框
				$('table th input:checkbox').on('click' , function(){
					var that = this;
					$(this).closest('table').find('tr > td:first-child input:checkbox')
					.each(function(){
						this.checked = that.checked;
						$(this).closest('tr').toggleClass('selected');
					});
						
				});
				
			});
		
		</script>
		
	</body>
</html>

