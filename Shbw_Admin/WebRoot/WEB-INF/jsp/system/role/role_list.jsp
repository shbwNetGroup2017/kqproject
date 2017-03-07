﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
			<form action="role/listRole.do" method="post" name="roleForm" id="roleForm">
			<table style="text-align: center;margin:auto;">
				<tr>
				    <td style="width: 100px;">
					   <span style="font-size: 15px;">角色名称:</span>
					</td>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text"  name="jsmc" value="${pd.jsmc}" placeholder="这里输入资源名称" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>						
					</td>
				</tr>
				<tr>
					<td style="vertical-align: top;" colspan="2">
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
						<th>角色名称</th>
						<th>状态</th>
						<th>创建时间</th>
						<th>修改时间</th>
						<th>角色描述</th>
						<th class="center">操作</th>
					</tr>
				</thead>
														
				<tbody>					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty roleList}">
						<c:forEach items="${roleList}" var="role" varStatus="vs">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${role.id }" id="${role.id }"/><span class="lbl"></span></label>
								</td>
								<td class='center' style="width: 30px;">${vs.index+1}</td>
								<td>${role.jsmc}</td>
								<td>	
									<c:choose>
										<c:when test="${role.zt=='0'}">
											启用
										</c:when>
										<c:otherwise>
											<font color="red">禁用</font>
										</c:otherwise>
									</c:choose>																
								</td>
								<td>${fn:replace(role.create_date,'.0','')}</td>
								<td>${fn:replace(role.update_date,'.0','')}</td>
								<td>${role.jsms}</td>								
								<td style="width: 60px;">
									<div class='hidden-phone visible-desktop btn-group'>
										<a class='btn btn-mini btn-info' title="编辑" onclick="editRole('${role.id }');"><i class='icon-edit'></i>编辑</a>
				                        <a class='btn btn-mini' title="查看" onclick="tagRole('${role.id }');"><i class='icon-tag'></i>查看</a>
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
				$("#roleForm").submit();
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
				 diag.Title ="新增角色";
				 diag.URL = "<%=basePath%>role/toAddUpdateRole.do";
				 diag.Width = 350;
				 diag.Height = 400;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						 if('${page.currentPage}' == '0'){
							 top.jzts();
							 setTimeout("self.location=self.location",100);
						 }else{
							 nextPage( ${page.currentPage} );
						 }
					}
					diag.close();
				 };
				 diag.show();
			}
			
			//修改
			function editRole(id){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="修改角色";
				 diag.URL = "<%=basePath%>role/toAddUpdateRole.do?id="+id;
				 diag.Width = 350;
				 diag.Height = 350;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						nextPage(${page.currentPage});
					}
					diag.close();
				 };
				 diag.show();
			}
			
			//详情
			function tagRole(id){
				 top.jzts();
				 var diag = new top.Dialog();
				 diag.Drag=true;
				 diag.Title ="角色详情";
				 diag.URL = "<%=basePath%>role/toDetailsRole.do?id="+id;
				 diag.Width = 350;
				 diag.Height = 450;
				 diag.CancelEvent = function(){ //关闭事件
					 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						nextPage(${page.currentPage});
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
									url: '<%=basePath%>role/deleteRole.do?tm='+new Date().getTime(),
							    	data: {id:str},
									dataType:'json',
									cache: false,
									success: function(data){										
										 if(data.msg=='success'){
										 	nextPage(${page.currentPage});
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

