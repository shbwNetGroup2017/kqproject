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
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
			<form action="kjpj/listKjpj.do" method="post" name="kjpjForm" id="kjpjForm">
			<table style="text-align: center;margin:auto;">
				<tr>
					<td>
						<label>开票单位名称：</label>
					</td>
					<td>
						<span class="input-icon">
							<input class="span12" autocomplete="off" id="nav-search-xfmc" type="text" name="xfmc" value="${pd.xfmc}" placeholder="这里输入关键词" />
						</span>
					</td>
					<td>
						<label>&nbsp;&nbsp;&nbsp;客户名称：</label>
					</td>
					<td>
						<span class="input-icon">
							<input class="span12" autocomplete="off" id="nav-search-gfmc" type="text" name="gfmc" value="${pd.gfmc}" placeholder="这里输入关键词" />
						</span>
					</td>
					<td>
						<label>&nbsp;&nbsp;&nbsp;收款类型：</label>
					</td>
					<td>
						<span class="input-icon">
							<input class="span12" autocomplete="off" id="nav-search-sklx" type="text" name="sklx" value="" placeholder="这里输入关键词" />
						</span>
					</td>
					<td>
						<label>&nbsp;&nbsp;&nbsp;开始日期：</label>
					</td>
					<td><input class="span12 date-picker" name="sqsjLoginStart" id="sqsjLoginStart" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="开始日期" title="开始日期"/></td>
				
				</tr>
				<tr>
					<td>
						<label>开票申请编码：</label>
					</td>
					<td>
						<span class="input-icon">
							<input class="span12" autocomplete="off" id="nav-search-xfmc" type="text" name="xfsh" value="${pd.xfsh}" placeholder="这里输入关键词" />
						</span>
					</td>
					<td>
						<label>&nbsp;&nbsp;&nbsp;发票类型：</label>
					</td>
					<td>
					 	<select id="fplx" class="span12" name="fplx">
					 		<option value="">全部</option>
					        <option value="004" <c:if test="${pd.fplx=='004'}">selected</c:if>>专票</option>
					        <option value="007" <c:if test="${pd.fplx=='007'}">selected</c:if>>普票</option>
					        <option value="026" <c:if test="${pd.fplx=='026'}">selected</c:if>>电子发票</option>
					     </select>
					</td>
					<td>
						<label>&nbsp;&nbsp;&nbsp;申请类型：</label>
					</td>
					<td>
						<select id="sqlx" class="span12" name="sqlx">
				 			<option value="">全部</option>
				        	<option value="0" <c:if test="${pd.sqlx=='0'}">selected</c:if>>固定开票申请（新增）</option>
				        	<option value="1" <c:if test="${pd.sqlx=='1'}">selected</c:if>>非固定开票申请</option>
				        	<option value="2" <c:if test="${pd.sqlx=='2'}">selected</c:if>>固定开票申请（变更）</option>
				    	</select>
					</td>
					<td>
						<label>&nbsp;&nbsp;&nbsp;结束日期：</label>
					</td>
					<td><input class="span12 date-picker" name="sqsjLoginEnd" id="sqsjLoginEnd"  type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="结束日期" title="结束日期"/></td>
				</tr>
				<tr>
					<td style="vertical-align: top;" colspan="8">
						<button class="btn btn-mini btn-light" onclick="search();"  title="检索">
								<i id="nav-search-icon" class="icon-search">查询</i>
						</button>
							&nbsp;&nbsp;
						<button class="btn btn-mini btn-light"  type="reset" title="重置">
							<i id="nav-search-icon" class="icon-refresh">重置</i>
						</button>
					</td>
				</tr>
			</table>
			<!-- 检索  -->
			<div style="margin-bottom: 10px;border-bottom: 1px solid #eee;">					
					<a title="批量开票" class="btn btn-small btn-success" onclick="makeAll('确定要对选中的数据进行批量开票吗?');">批量开票</a>
					<a title="刷新" class="btn btn-small btn-success" onclick="window.location.reload();">刷新</a>
			</div>
		
			<table id="table_report" class="table table-striped table-bordered table-hover">
				
				<thead >
					<tr>
						<th class="center">
						<label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label>
						</th>
						<th>快钱id</th>
						<th>开票单位名称</th>
						<th>开票单位识别号</th>
						<th>客户名称</th>
						<th>客户识别号</th>
						<th>发票抬头</th>
						<th>开票类型</th>
						<th>开票金额</th>
						<th>金额合计</th>
						<th>税额合计</th>
						<th>收款人</th>
						<th class="center" width="11%;">操作</th>
					</tr>
				</thead>										
				<tbody>
					
				<!-- 开始循环 -->	
				<c:choose>
					<c:when test="${not empty kjpjList}">
						<c:forEach items="${kjpjList}" var="kjpj">
							<tr>
								<td class='center' style="width: 30px;">
									<label><input type='checkbox' name='ids' value="${kjpj.id},${pd.kpr},${kjpj.fplx},${kjpj.kplsh},${kjpj.xfmc},${kjpj.xfsh}" id="${kjpj.id}"/><span class="lbl"></span></label>
								</td>
								<td>${kjpj.id }</td>
								<td>${kjpj.xfmc}</td>
								<td>${kjpj.xfsh}</td>
								<td>${kjpj.khmc}</td>
								<td>${kjpj.khsh}</td>
								<td>${kjpj.gfmc}</td>
								<td>
									<c:choose>
										<c:when test="${kjpj.fplx==004}">
										专票
										</c:when>
										<c:when test="${kjpj.fplx==007}">
										普票
										</c:when>
										<c:when test="${kjpj.fplx==026}">
										电子发票
										</c:when>
										<c:otherwise>
											无效类型
										</c:otherwise>
									</c:choose>								
								</td>
								<td><fmt:formatNumber value="${kjpj.jshj}" pattern="0.00"/></td>
								<td><fmt:formatNumber value="${kjpj.hjje}" pattern="0.00"/></td>
								<td><fmt:formatNumber value="${kjpj.hjse}" pattern="0.00"/></td>
								<td>${kjpj.skr}</td>
								<td class="center">
									<button type="button" onclick="preview('${kjpj.id}','${kjpj.kplsh}');" class="btn btn-link">预览</button>
									<c:if test="${kjpj.len>8}">
										<button type="button" onclick="details('${kjpj.kplsh}');" class="btn btn-link">详情清单</button>
									</c:if>
									<button type="button" onclick="openkp('${kjpj.id}','${pd.kpr}','${kjpj.fplx}','${kjpj.kplsh}','${kjpj.xfmc}','${kjpj.xfsh}');" class="btn btn-link">开票</button>
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
			$("#kjpjForm").submit();
		}
		
		//预览
		function preview(id,kplsh){		    
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="预览";
			 diag.URL = "<%=basePath%>kjpj/toKjpjPreview.do?id="+id+"&kplsh="+kplsh;
			 diag.Width = 1200;
			 diag.Height = 410;
			 diag.CancelEvent = function(){ //关闭事件
					if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						nextPage('${page.currentPage}');
					}
					diag.close();
			  };
			 diag.show();
		}
		
		//详情清单
		function details(kplsh){		    
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="详情清单";
			 diag.URL = "<%=basePath%>kjpj/toKjpjDetails.do?kplsh="+kplsh;
			 diag.Width = 680;
			 diag.Height = 550;
			 diag.CancelEvent = function(){ //关闭事件
					if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
						nextPage('${page.currentPage}');
					}
					diag.close();
			  };
			 diag.show();
		}
		
		//开票
		function openkp(id,kpr,fplx,kplsh,xfmc,xfsh){	
		  if(confirm("确定要进行开票操作吗？")){
		  		var result=top.getJava("getKpXml",fplx,kplsh,kpr,id,xfmc,xfsh);
				var json=jQuery.parseJSON(result);
				if(json.state=="error"){
					alert("无法进行开票操作,"+json.msg);
				}else if(json.state=="0"){
					alert("开票成功！发票号码为:"+json.fphm+",发票代码为:"+json.fpdm);					
				}else if(json.state=="1"){
					alert("开票失败,"+json.msg+"!");
				}else if(json.state=="fail"){
					alert(json.msg);
				}else if(json.state=="2"){
					alert(json.msg);
				}
				nextPage('${page.currentPage}');
	       }				 			 
		}
				
		//批量开票
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++)
					{
						if(document.getElementsByName('ids')[i].checked){
						  	if(str=='') str += document.getElementsByName('ids')[i].value;
						  	else str += '#' + document.getElementsByName('ids')[i].value;
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
						if(msg == '确定要对选中的数据进行批量开票吗?'){
						    for(var i=0;i<str.split("#").length;i++){
									//参数(开票流水表id)
									var id=str.split("#")[i].split(",")[0];
									//参数(开票人)
									var kpr=str.split("#")[i].split(",")[1];
									//参数(发票类型)
									var fplx=str.split("#")[i].split(",")[2];
									//参数(开票流水号)
									var kplsh=str.split("#")[i].split(",")[3];	
									//参数(销方名称)
									var xfmc=str.split("#")[i].split(",")[4];	
									//参数(销方税号)
									var xfsh=str.split("#")[i].split(",")[5];	
									
										var result=top.getJava("getKpXml",fplx,kplsh,kpr,id,xfmc,xfsh);
										var json=jQuery.parseJSON(result);
										if(json.state=="error"){
											alert("无法进行开票操作,"+json.msg);
										}else if(json.state=="0"){
											alert("开票成功！发票号码为:"+json.fphm+",发票代码为:"+json.fpdm);										
										}else if(json.state=="1"){
											alert("开票失败,"+json.msg+"!");
										}else if(json.state=="fail"){
											alert(json.msg);
										}else if(json.state=="2"){
											alert(json.msg);
										}												
							}
							nextPage('${page.currentPage}');		
						}
					}
				}
			});
		}
		</script>
		
		<script type="text/javascript">
		
		$(function() {
			
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
			$('#sqsjLoginStart').datepicker().on('changeDate',function(dateText){
				if(endTime!=""){
					if(dateText.date>endTime){
						alert("开始日期要小于开始日期");
					}
				}
				startTime=dateText.date; //保存开始时间，用于下次比较
				});
			$('#sqsjLoginEnd').datepicker().on('changeDate',function(dateText){
				if(startTime!=""){
					if(dateText.date<startTime){
						alert("结束日期要大于开始日期");
					}
				}
				endTime = dateText.date;//保存结束时间，用于下次比较
				});
		});
		
		</script>
	</body>
</html>

