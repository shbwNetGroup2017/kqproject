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
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />
<link rel="stylesheet" href="static/assets/css/font-awesome.css" />
<!-- ace styles -->
<link rel="stylesheet" href="static/assets/css/ace.css" class="ace-main-stylesheet" id="main-ace-style" />
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
<!-- 下拉框 -->
<link rel="stylesheet" href="static/css/chosen.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<script type="text/javascript" src="static/js/jquery.form.js"></script>
</head>
<body>

	<div class="container-fluid" id="main-container">


		<div id="page-content" class="clearfix">

			<div class="row-fluid">


				<div class="row-fluid">

					<!-- 检索  -->
					<form action="leaveBill/customerTtList.do" method="post" name="customerInfoForm" id="customerInfoForm">
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
								<td style="vertical-align: top;text-align: right;" colspan="3">
									<button class="btn btn-mini btn-light" onclick="search();"  title="检索">
									<i id="nav-search-icon" class="icon-search">查询</i>
									</button>
								</td>
								<td style="vertical-align: top;text-align:left;" colspan="1">
						    		<a class="btn btn-mini btn-light" onclick="clearSearch()" title="重置">
									<i id="nav-search-icon" class="icon-refresh">重置</i>
									</a>
								</td>						
							</tr>
						</table>
						<!-- 检索  -->

						<div>
								<a style="width:100px;" class="btn btn-small btn-success" onclick="makeAll('确定提交?');">确定</a>
						</div>
						<table id="table_report" class="table table-striped table-bordered table-hover">

							<thead>
								<tr>
									<th class="center"><label><span class="lbl"></span></label></th>
									<th>序号</th>
									<th>客户名称</th>
									<th>客户税号</th>
									<th>客户类型</th>
									<th>客户地址</th>
									<th>客户电话</th>
									<th>开户银行</th>
									<th>开户银行账号</th>
									<th style="width: 150px;">操作</th>
								<!-- 	<th>可开票金额</th>
									<th>已开票金额</th>
									<th>正在开票金额</th> -->					
								</tr>
							</thead>

							<tbody>

								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${not empty customerInfoList}">
										<c:forEach items="${customerInfoList}" var="var" varStatus="vs">
											<tr>
												<td class='center' style="width: 30px;"><label><input type='radio' name='ids' value="${var.gfmc}" id="${var.id }" /><span class="lbl"></span></label></td>
												<td class='center' style="width: 30px;">${vs.index+1}</td>
												<td>${var.gfmc }</td>
												<td>${var.gfsh }</td>
												<td>${var.gflx }</td>
												<td>${var.gfdz }</td>
												<td>${var.gfdh}</td>
												<td>${var.gfyh}</td>
												<td>${var.gfyhzh}</td>
												<td><input type="file" style="width: 150px;" id="ttfj${vs.index+1}" name="ttfj${var.id }" onchange="fileType(this,${vs.index+1})" /></td>
											<%-- 	<td>${var.zje}</td>
												<td>${var.ykpje}</td>
												<td>${var.ykpje}</td> --%>
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
								    <td><input id="ttIds" name="ttfjIds" type="hidden"> <input id="ttmc" type="hidden"><input id="ttfjName" type="hidden"></td>
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
		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='static/assets/js/jquery.js'>"+"<"+"/script>");
		</script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script type="text/javascript">
		 	window.jQuery || document.write("<script src='static/assets/js/jquery1x.js'>"+"<"+"/script>");
		</script>
		<![endif]-->
		<script src="static/js/bootstrap.min.js"></script>
		<!-- ace scripts -->
		<script src="static/assets/js/ace/elements.fileinput.js"></script>
		<script src="static/assets/js/ace/ace.js"></script>
		<!--提示框-->
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
							if(msg == '确定提交?'){
								$("#ttIds").val(str);
								$("#ttmc").val(ttmc);
								for(var i=0;i < document.getElementsByName('ids').length;i++)
								{
									  if(document.getElementsByName('ids')[i].checked){
										var fjName='ttfj'+document.getElementsByName('ids')[i].id;
										var ttFjName=document.getElementsByName('ttfj'+fjName).value;
										var ttFjName=$('input[name="'+fjName+'"]').val();
										if(ttFjName==''){
											alert('请为选中客户添加抬头附件!');
											return;
										}
									  	
									  }
								}
								var form=$("#customerInfoForm");
								$("#customerInfoForm").ajaxSubmit({
									url : 'leaveBill/customerTtFjInfo.do',
									success : function(data) {
										    var fjxx=JSON.stringify(data);
										    $("#ttfjName").val(fjxx);
											top.Dialog.close();																		
									},
									error : function(request) {
										alert("Connection error");
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
				// $("#jumpMenu").val(要选中的option的value值即可);
				// 
				$("#gflx").val('${pd.gflx}');
				var size='${size}';
				//上传控件渲染
				for(var i=1;i<=size;i++){
					$('#ttfj'+i).ace_file_input({
						no_file:'抬头附件',
						btn_choose:'选择',
						btn_change:'更改',
						droppable:false,
						onchange:null,
						thumbnail:false, //| true | large
						whitelist:'doc|pdf|docx',
						blacklist:'doc|pdf|docx'
						//onchange:''
						//
					});
				}
			});		
			
			//前端对文件类型的验证
			function fileType(obj,i){
				var fileType=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();//获得文件后缀名
			    if(fileType != '.doc'&&fileType != '.pdf'){
			    	$("#ttfj"+i).tips({
						side:3,
			            msg:'请上传doc或者pdf格式的文件',
			            bg:'#AE81FF',
			            time:3
			        });
			    	$("#ttfj"+i).val('');
			    	document.getElementById("ttfj"+i).files[0] = '请选择doc或者pdf格式的文件';
			    }
			}
	</script>

</body>
</html>

