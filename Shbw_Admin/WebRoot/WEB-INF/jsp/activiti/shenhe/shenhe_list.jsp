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
	<%@ include file="../../system/admin/top.jsp"%>
	<link rel="stylesheet" href="static/css/bootstrap-select.css" />
	<script type="text/javascript" src="static/js/bootstrap-select.js"></script> 
	<script type="text/javascript">
	</script>
	</head> 
<body>
		
<div class="container-fluid" id="main-container">


<div id="page-content" class="clearfix">
						
  <div class="row-fluid">


	<div class="row-fluid">
			<!-- 检索  -->
			<form action="shenhe/listLeaveBills.do" method="post" name="kplsForm" id="kplsForm">
			<table style="text-align: center;margin:auto;">
				<tr>
					<td>
					<label>开票单位名称：</label>
					</td>
					<td>
						<span class="input-icon">
							<input class="span12" autocomplete="off" id="nav-search-xfmc" type="text" name="xfmc" value="" placeholder="这里输入关键词" />
						</span>
					</td>
					<td>
					<label>&nbsp;&nbsp;&nbsp;客户名称：</label>
					</td>
					<td>
					<span class="input-icon">
							<input class="span12" autocomplete="off" id="nav-search-gfmc" type="text" name="gfmc" value="" placeholder="这里输入关键词" />
						</span>
					</td>
					<td>
					<label>&nbsp;&nbsp;&nbsp;收款类型：</label>
					</td>
					<td>
					<select id="sklx" class="span12" name="sklx">
				 		<option value="">全部</option>
				        <option value="0">先票后款</option>
				        <option value="1">先款后票</option>
				     </select>
					</td>
						<td>
					<label>&nbsp;&nbsp;&nbsp;开始日期：</label>
					</td>
					<td><input class="span12 date-picker" name="sqsjStart" id="sqsjStart" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="开始日期" title="开始日期"/></td>
				
				</tr>
				<tr>
				<td>
					<label>开票类型：</label>
					</td>
					<td>
				 	<select id="fplx" class="span12" name="fplx">
				 		<option value="">全部</option>
				        <option value="004">专票</option>
				        <option value="007">普票</option>
				        <option value="026">电子发票</option>
				        <option value="0">收据</option>
				     </select>
					</td>
					<td>
					<label>&nbsp;&nbsp;&nbsp;申请类型：</label>
					</td>
					<td>
						<select id="sqlx" class="span12" name="sqlx">
				 			<option value="">全部</option>
				        	<option value="0">固定开票申请（新增）</option>
				        	<option value="1">非固定开票申请</option>
				        	<option value="2">固定开票申请（变更）</option>
				    	</select>
					</td>
				
					<td>
					<label>&nbsp;&nbsp;&nbsp;结束日期：</label>
					</td>
					<td><input class="span12 date-picker" name="sqsjEnd" id="sqsjEnd"  type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="结束日期" title="结束日期"/></td>
				</tr>
				<tr>
					<td style="vertical-align: top;text-align: right;" colspan="3">
						<button class="btn btn-mini btn-light" onclick="search();"  title="查询"><i id="nav-search-icon" class="icon-search">查询</i></button>
					</td>
					<td style="vertical-align: top;text-align: right;" colspan="2">
						<button class="btn btn-mini btn-light"  type="reset" title="重置"><i id="nav-reset-icon" class="icon-refresh">重置</i></button>
					</td>
				</tr>
			</table>
			<!-- 检索  -->
			<td style="vertical-align:top;">
			<a class="btn btn-small btn-success" onclick="fenpei('确定要分配选中的数据吗?');">分配</a>
			<a title="领取" class="btn btn-small btn-success" onclick="lingqu('确定要领取选中的数据吗?');" ><i class='icon-cog'>领取</i></a>
				
			</td>
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead>
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>申请编号</th>
						<!-- <th>任务ID</th> -->
						<th>开票单位名称</th>
						<th>申请类型</th>
						<th>客户名称</th>
						<th>发票抬头</th>
						<th>开票类型</th>
						<th>快钱账号</th>
						<th>开票金额</th>
						<th>备注</th>
						<th>申请状态</th>
						<th>领取状态</th>
						<th>申请时间</th>
						<th class="center">操作</th>
					</tr>
				</thead>
										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty leaveBillList}">
						<c:forEach items="${leaveBillList}" var="leaveBill">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${leaveBill.sjzclq}" id="${leaveBill.taskid}"/><span class="lbl"></span></label>
								</td>
								<td>${leaveBill.id }</td>
								<%-- <td>${leaveBill.taskid }</td> --%>
								<td>${leaveBill.xfmc}</td>
								<td>
								<c:choose>
									<c:when test="${leaveBill.sqlx==0}">
									固定开票申请（新增）
									</c:when>
									<c:when test="${leaveBill.sqlx==1}">
									非固定开票申请
									</c:when>
									<c:when test="${leaveBill.sqlx==2}">
									固定开票申请（变更）
									</c:when>
									<c:otherwise>
										无效类型
									</c:otherwise>
								</c:choose>
								
								</td>
								<td>${leaveBill.khmc }</td>
								<td>${leaveBill.gfmc }</td>
								<td>
								<c:choose>
									<c:when test="${leaveBill.fplx==004}">
									专票
									</c:when>
									<c:when test="${leaveBill.fplx==007}">
									普票
									</c:when>
									<c:when test="${leaveBill.fplx==026}">
									电子发票
									</c:when>
									<c:when test="${leaveBill.fplx==0}">
									收据
									</c:when>
									<c:otherwise>
										无效类型
									</c:otherwise>
								</c:choose>
								
								</td>
								<td>${leaveBill.zhbh}</td>
								<td>${leaveBill.jshj}</td>
								<td>${leaveBill.remark}</td>
								
								<td>
								<c:choose>
									<c:when test="${leaveBill.lcjd==0}">
									草稿
									</c:when>
									<c:when test="${leaveBill.lcjd==1}">
									申请
									</c:when>
									<c:when test="${leaveBill.lcjd==2}">
									审核中
									</c:when>
									<c:when test="${leaveBill.lcjd==3}">
									已审核
									</c:when>
									<c:when test="${leaveBill.lcjd==3}">
									已开票
									</c:when>
									<c:when test="${leaveBill.lcjd==3}">
									开票失败
									</c:when>
									<c:otherwise>
										无效状态
									</c:otherwise>
								</c:choose>
								</td>
								
								<td>
								<c:choose>
									<c:when test="${leaveBill.sjzclq==0}">
									已领取
									</c:when>
									<c:when test="${leaveBill.sjzclq==1}">
									未领取
									</c:when>
									<c:otherwise>
										无效状态
									</c:otherwise>
								</c:choose>
								</td>
								
								<td>${fn:replace(leaveBill.sqsj,'.0','')}</td>
								<c:choose>
									<c:when test="${leaveBill.lcjd==2}">
										<td style="width: 60px;">
									<div class='hidden-phone visible-desktop btn-group'>
										<a class='btn btn-mini btn-info' title="处理" onclick="shenpi('${leaveBill.taskid }','${leaveBill.id }','${leaveBill.sjzclq }');"><i class='icon-edit'>处理</i></a>
				                        <a class='btn btn-mini' title="查看" onclick="lookDetails('${leaveBill.id }');"><i class='icon-tag'>查看</i></a>
										<%-- <a class='btn btn-mini btn-info' title="流程图" onclick="viewCurrentImage('${leaveBill.taskid }');"><i class='icon-search'>流程图</i></a> --%>
									</div>
								</td>
									</c:when>
									<c:otherwise>
										<td>
										<a class='btn btn-mini' title="查看" onclick="lookDetails('${leaveBill.id }');"><i class='icon-tag'>查看</i></a>
										</td>
									</c:otherwise>
								</c:choose>
							</tr>
						
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="14" class="center">没有相关数据</td>
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
			$("#kplsForm").submit();
		}
		//批量分配
		function fenpei(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str==''){
						  		var check = document.getElementsByName('ids')[i].id;
						  		str += document.getElementsByName('ids')[i].id;
						  	}
						  	else{
						  		str += ',' + document.getElementsByName('ids')[i].id;
						  		}
						 	if(document.getElementsByName('ids')[i].value==0){
						  		bootbox.dialog("只有未领取的可以分配!", 
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
						  		return;
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
						if(msg == '确定要分配选中的数据吗?'){
							top.jzts();
							var diag = new top.Dialog();
							diag.Drag=true;
							diag.Title ="分配页面";
							diag.URL = '<%=basePath%>shenhe/toFenPeiPage.do?fenpeiIds='+str;
							diag.Width = 400;
							diag.Height = 300;
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
						}else if(msg == '确定要给选中的用户发送邮件吗?'){
							//sendEmail(emstr);
						}else if(msg == '确定要给选中的用户发送短信吗?'){
							//sendSms(phones);
						}
						
						
					}
				}
			});
		}
		//批量领取
		function lingqu(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						  if(document.getElementsByName('ids')[i].checked){
						  	if(str==''){
						  		var check = document.getElementsByName('ids')[i].id;
						  		str += document.getElementsByName('ids')[i].id;
						  	}
						  	else{
						  		str += ',' + document.getElementsByName('ids')[i].id;
						  		}
						 	if(document.getElementsByName('ids')[i].value==0){
						  		bootbox.dialog("只有未领取的可以领取!", 
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
						  		return;
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
						if(msg == '确定要领取选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>shenhe/updateLingQu.do?tm='+new Date().getTime(),
						    	data: {ids:str},
								dataType:'json',
								cache: false,
								success: function(data){
									if(data.msg=="success"){
										alert("领取成功");
										nextPage('1');
									}else{
										alert("领取失败:"+data.msg);
										nextPage('1');
									}
								}
							});
						}else if(msg == '确定要给选中的用户发送邮件吗?'){
							//sendEmail(emstr);
						}else if(msg == '确定要给选中的用户发送短信吗?'){
							//sendSms(phones);
						}
						
						
					}
				}
			});
		}
		
		//审批
		function shenpi(taskid,id,sjzclq){
			if(sjzclq==1){
				alert("请领取后，再处理");
				return false;
			}
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="资料";
			 diag.URL = '<%=basePath%>shenhe/toAudit.do?taskid='+taskid+'&id='+id;
			 diag.Width = 800;
			 diag.Height = 800;
			 diag.CancelEvent = function(){ //关闭事件
				nextPage('1');
				diag.close();
			 };
			 diag.show();
		}
		
		//查看流程图
		function viewCurrentImage2(taskid){
			top.jzts();
			alert("查看流程图");
			var url = "<%=basePath%>shenhe/viewCurrentImage.do?taskid="+taskid;
			$.get(url,function(data){
				nextPage('${page.currentPage}');
			});
		}
		function viewCurrentImage(taskid){
			 if(taskid==""){
			 	alert("选中的数据还是草稿");
			 	return;
			 }
			 
			 var newWindow = window.open("<%=basePath%>shenhe/viewCurrentImage.do?taskid="+taskid);
		}
	
		//详情
		function lookDetails(id){
			top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="详情";
			 diag.URL = '<%=basePath%>shenhe/lookDetails.do?id='+id+'';
			 diag.Width = 800;
			 diag.Height = 600;
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
		
		
		
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
			//日期框
			
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
		});
		
		//导出excel
		function toExcel(){
			var USERNAME = $("#nav-search-input").val();
			var sqsjStart = $("#sqsjStart").val();
			var sqsjEnd = $("#sqsjEnd").val();
			var ROLE_ID = $("#role_id").val();
			window.location.href='<%=basePath%>user/excel.do?USERNAME='+USERNAME+'&sqsjStart='+sqsjStart+'&sqsjEnd='+sqsjEnd+'&ROLE_ID='+ROLE_ID;
		}
		
		//打开上传excel页面
		function fromExcel(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="EXCEL 导入到数据库";
			 diag.URL = '<%=basePath%>user/goUploadExcel.do';
			 diag.Width = 300;
			 diag.Height = 150;
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
		
		</script>
		
	</body>
</html>

