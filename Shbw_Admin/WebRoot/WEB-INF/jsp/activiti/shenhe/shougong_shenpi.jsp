<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
<meta charset="utf-8" />
<title></title>
<%@ include file="../../system/admin/top.jsp"%>
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<link href="static/css/bootstrap.min.css" rel="stylesheet" />
<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
<link rel="stylesheet" href="static/css/font-awesome.min.css" />
<!-- 下拉框 -->
<link rel="stylesheet" href="static/css/chosen.css" />
<link rel="stylesheet" href="static/css/ace.min.css" />
<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
<link rel="stylesheet" href="static/css/ace-skins.min.css" />
<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>

<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function() {
		if ($("#id").val() != "") {
			$("#loginname").attr("readonly", "readonly");
			$("#loginname").css("color", "gray");
		}
		
	});
	function tuihui(id){
		if("退回"==id){
			//弹出选择指定人
			top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="退回页面";
			 diag.URL = '<%=basePath%>shenhe/toTuihuiPage.do';
			 diag.Width = 800;
			 diag.Height = 500;
			 diag.CancelEvent = function(){ //关闭事件
				 $('#yhzh').val(diag.innerFrame.contentWindow.document
							.getElementById('yhzh').value);
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
	}
	//保存
	function save() {
		if($("#outcome").val()=="退回"){
			if($("#yhzh").val()==""||$("#yhzh").val()==null){
				alert("退回没有指定审批人");
				return false;
			}
		}
		$("#shenheForm").submit();
		$("#zhongxin").hide();
		$("#zhongxin2").show();
	}
	//手工开票开票明细信息查询
	function serarchKpmx(kplsh){
		var diagTt = new top.Dialog();
		diagTt.Drag = true;
		diagTt.Title = "商品明细";
		diagTt.URL = '<%=basePath%>shenhe/serarchKpmx.do?kplsh='+kplsh;
		diagTt.Width = 1100;
		diagTt.Height = 500;
		diagTt.CancelEvent = function() { // 关闭事件
			diagTt.close();
		};
		diagTt.show();
	}
</script>
</head>
<body>
	<form action="shenhe/submitTask.do" name="shenheForm" id="shenheForm"
		method="post">
		<input name="taskid" hidden="true" value="${taskId}"> <input
			name="id" hidden="true" value="${kpls.id}">
		<div id="zhongxin" style="text-align: center;">
		<table style="margin:auto;">
			<tr>
				<td><label>开票单位名称：</label></td>
				<td><input type="text" disabled="disabled" name="xfmc"
					id="xfmc" value="${kpls.xfmc }" maxlength="20"/></td>
				<td><label>销方税号：</label></td>
				<td>
					<input type="text" disabled="disabled" id="xfsh" name="xfsh" value="${kpls.xfsh }"/>
				</td>
			</tr>
				<tr>
				<td><label>销方地址：</label></td>
				<td><input type="text" disabled="disabled" name="xfdz"
					id="xfdz" value="${kpls.xfdz }"/></td>
				<td><label>销方电话：</label></td>
				<td>
					<input type="text" disabled="disabled" id="xfdh" name="xfdh" value="${kpls.xfdh }"/>
				</td>
				</tr>
				
				<tr>
				<td><label>销方银行：</label></td>
				<td><input type="text" disabled="disabled" name="xfyh"
					id="xfyh" value="${kpls.xfyh }"/></td>
				<td><label>销方银行账号：</label></td>
				<td>
					<input type="text" disabled="disabled" id="xfyhzh" name="xfyhzh" value="${kpls.xfyhzh }"/>
				</td>
				</tr>
				<tr>
					<td><label>客户名称：</label></td>
					<td><input type="text" disabled="disabled" name="gfmc"
						id="gfmc" value="${kpls.gfmc}"/>
					</td>
					<td><label>客户税号：</label></td>
					<td><input type="text" disabled="disabled" name="gfsh"
						id="gfsh" value="${kpls.gfsh}"/>
					</td>
				</tr>

				<tr>
					<td><label>客户地址：</label></td>
					<td><input type="text" disabled="disabled" name="gfdz"
						id="gfdz" value="${kpls.gfdz}"/>
					</td>
					<td><label>客户电话：</label></td>
					<td><input type="text" disabled="disabled" name="gfdh"
						id="gfdh" value="${kpls.gfdh}"/>
					</td>
				</tr>
				<tr>
					<td><label>客户银行：</label></td>
					<td><input type="text" disabled="disabled" name="gfyh"
						id="gfyh" value="${kpls.gfyh}"/>
					</td>
					<td><label>客户银行账号：</label></td>
					<td><input type="text" disabled="disabled" name="gfyhzh"
						id="gfyhzh" value="${kpls.gfyhzh}"/>
					</td>
				</tr>
				
				<tr>
					<td><label>收款人：</label></td>
					<td><input type="text" disabled="disabled" name="fhr"
						id="fhr" value="${kpls.fhr}"/>
					</td>
					<td><label>复核人：</label></td>
					<td><input type="text" disabled="disabled" name="skr"
						id="skr" value="${kpls.skr}"/>
					</td>
				</tr>
				<tr>
				<td><label>开票人：</label></td>
					<td><input type="text" disabled="disabled" name="kpr"
						id="kpr" value="${kpls.kpr}"/>
					</td>
					<td><label>开票类型：</label></td>
					<td><select id="fplx" disabled="disabled" name="fplx"
						class="selectpicker">
							<option value="004"
								<c:if test="${004==kpls.fplx}">selected</c:if>>专票</option>
							<option value="007"
								<c:if test="${007==kpls.fplx}">selected</c:if>>普票</option>
							<option value="026"
								<c:if test="${026==kpls.fplx}">selected</c:if>>电子发票</option>
					<option value="0" <c:if test="${0==kpls.fplx}">selected</c:if>>收据</option>
					</select></td>
				</tr>
				
				<tr>
					<td><label>金额合计：</label></td>
					<td><input type="text" disabled="disabled" name="hjje"
						id="hjje" value="${kpls.hjje}"/></td>
					<td><label>税额合计：</label></td>
					<td><input type="text" disabled="disabled" name="hjse"
						id="hjse" value="${kpls.hjse}"/></td>
				</tr>
				<tr>
					<td><label>价税合计：</label></td>
					<td><input type="text" disabled="disabled" name="sjhj"
						id="sjhj" value="${kpls.jshj }"/></td>
					<td><label>明细信息：</label></td>
					<td><a style="width: 210px;" class="btn btn-small btn-info" onclick="serarchKpmx('${kpls.kplsh }')">点击查看</a></td>
				</tr>
				<tr>
					<td><label>收 件 人：</label></td>
					<td><input type="text" name="gflxr" disabled="disabled"
						maxlength="32" id="gflxr" value="${kpls.gflxr }"
						/></td>
					<td><label>收件人电话：</label></td>
					<td><input type="text" name="gflxrdh" disabled="disabled"
						maxlength="32" id="gflxrdh" value="${kpls.gflxrdh }"
						/></td>
				</tr>
				<tr>
					<td><label>邮 寄 地 址：</label></td>
					<td><input type="text" name="yjdz" id="yjdz" maxlength="200"
						value="${kpls.yjdz }" disabled="disabled"/></td>
					<td><label>邮 政 编 码：</label></td>
					<td><input type="text" name="gfyb" id="gfyb" maxlength="32"
						value="${kpls.gfyb }" disabled="disabled"/></td>
				</tr>
				<tr>
					<td><label> 备 注 ：</label></td>
					<td><textarea name="remark" disabled="disabled" id="remark">${kpls.remark }</textarea></td>
					<td><label>批注:</label></td>
					<td><textarea name="comment" id="comment" title="批注"
							placeholder="批注信息"></textarea></td>
				</tr>
				<tr>
					<td><label>处理结果：</label></td>
					<td><select id="outcome" name="outcome">
					<c:forEach items="${outcomeList}" var="item" varStatus="status">
						<option value="${item.id}" onclick="tuihui('${item.id}')">${item.name}</option>
					</c:forEach>
					</select></td>
					<input type="hidden" id="yhzh" name="yhzh"/>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="4"><a
						class="btn btn-small btn-info" onclick="save();">保存</a> <a
						class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
					</td>
				</tr>
			</table>
		</div>
		<!-- 审批的信息 -->
		<div>
			<table class="table table-striped table-bordered table-hover">
				<c:choose>
					<c:when test="${not empty commentList}">
						<h4>票据申请批注：</h4>
						<tr>
							<th>序号</th>
							<th width="60%">批注信息</th>
							<th>批注人</th>
							<th>审批时间</th>
						</tr>
						<c:forEach items="${commentList}" var="comment" varStatus="state">
							<tr>
								<td>${state.index+1}</td>
								<td>${comment.comment}</td>
								<td>${comment.creator}</td>
								<td>${fn:replace(comment.create_date,'.0','')}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="10" class="center">没有审批信息</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display: none">
			<br /> <br /> <br /> <br /> <img src="static/images/jiazai.gif" /><br />
			<h4 class="lighter block green"></h4>
		</div>

	</form>

	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("_$tag_______________________________________\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->

	<script type="text/javascript">
		$(function() {
			//单选框
			$(".chzn-select").chosen();
			$(".chzn-select-deselect").chosen({
				allow_single_deselect : true
			});
		});
	</script>

</body>
</html>