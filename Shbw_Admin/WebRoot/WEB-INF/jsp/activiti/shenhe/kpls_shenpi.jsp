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
	//订单信息
	function searchDd(){
		var jylshs = $("#ddjylsh").val();
		var diagTt = new top.Dialog();
		diagTt.Drag = true;
		diagTt.Title = "订单信息详情";
		diagTt.URL = '<%=basePath%>leaveBill/searchDdDeatils.do?jylshs='+jylshs;
		diagTt.Width = 1100;
		diagTt.Height = 400;
		diagTt.CancelEvent = function() { // 关闭事件
			$("#comment").focus();
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
			<table style="margin: auto;">
				<tr>
					<td><label>开票单位名称：</label></td>
					<td><input type="text" disabled="disabled" name="xfmc"
						id="xfmc" value="${kpls.xfmc }" maxlength="20"/></td>
					<td><label>申请类型：</label></td>
					<td><select id="sqlx" disabled="disabled" name="sqlx"
						class="selectpicker">
							<option value="0" <c:if test="${0==kpls.sqlx}">selected</c:if>>固定开票申请（新增）</option>
							<option value="1" <c:if test="${1==kpls.sqlx}">selected</c:if>>非固定开票申请</option>
							<option value="2" <c:if test="${2==kpls.sqlx}">selected</c:if>>固定开票申请（变更）</option>
					</select></td>

				</tr>
				<tr>
					<td><label>公司名称：</label></td>
					<td><input type="text" disabled="disabled" name="ygsidmc"
						id="ygsidmc" value="${kpls.ygsidmc}"/></td>
					<td><label>发票抬头：</label></td>
					<td><input type="text" disabled="disabled" name="gfmc"
						id="gfmc" value="${kpls.gfmc}"/>
					</td>
				</tr>
<tr>
				<td><lable>数据来源</lable></td>
					<td>
					<select id="sjly" disabled="disabled" name="sjly">
						<option onclick="sjlybh();" value="0" <c:if test="${0==kpls.sjly}">selected</c:if>>ODS数据仓库</option>
						<option onclick="sjlybh();" value="1" <c:if test="${1==kpls.sjly}">selected</c:if>>Oracle ERP系统</option>
						<option onclick="sjlybh();" value="2" <c:if test="${2==kpls.sjly}">selected</c:if>>进件系统</option>
						<option onclick="sjlybh();" value="3" <c:if test="${3==kpls.sjly}">selected</c:if>>商派</option>
					</select>
					</td>
				<td><label>开票类型：</label></td>
				<td>
				<select id="fplx" disabled="disabled" name="fplx" class="selectpicker">
				        <option value="004" <c:if test="${004==kpls.fplx}">selected</c:if>>专票</option>
				        <option value="007" <c:if test="${007==kpls.fplx}">selected</c:if>>普票</option>
				        <option value="026" <c:if test="${026==kpls.fplx}">selected</c:if>>电子发票</option>
				     <option value="0" <c:if test="${0==kpls.fplx}">selected</c:if>>收据</option>
				     </select>
				</td>
				</tr>
				<tr>
				<td>收款类型:</td>
				<td>
				<select id="sklx" disabled="disabled" name="sklx" class="selectpicker">
						<option value="0" <c:if test="${'0'==kpls.sklx}">selected</c:if>>先票后款</option>
				        <option value="1" <c:if test="${'1'==kpls.sklx}">selected</c:if>>先款后票</option>
					</select>
				</td>
				<td><label id="cplbtips">产品类别：</label></td>
					<td><select id="cplbTemp" disabled="disabled" name="cplbTemp">
				        <option value="0" <c:if test="${0==kpls.cplb}">selected</c:if>>订单</option>
				        <option value="1" <c:if test="${1==kpls.cplb}">selected</c:if>>手续费</option>
				     </select>
				     <input type="hidden" id="dingDanJylshs" name="dingDanJylshs" value="${kpls.dingDanJylshs }"/>
				     <input type="hidden" id="dingDanIds" name="dingDanIds" value="${kpls.dingDanIds }"/>
				     <input type="hidden" id="cplb" name="cplb" value="${kpls.cplb}">
				     </td>
				<td><c:if test="${0==kpls.cplb}">
							<a class="btn btn-small btn-info" onclick="searchDd();">订单</a>
						</c:if></td>
						<input type="hidden" id="ddjylsh" name="ddjylsh"
						value="${kpls.ddjylsh }" />
				</tr>
				
				<tr>
					<td><label>业务类型：</label></td>
					<td><select id="ywlx" name="ywlx" class="selectpicker"
						disabled="disabled">
							<c:forEach items="${ywlxList}" var="item" varStatus="status">
								<option value="${item.dmbh}"
									<c:if test="${item.id==kpls.ywlx}">selected</c:if>>${item.lxmc}</option>
							</c:forEach>
					</select></td>

					<td><label>结算类型：</label></td>
					<td><select id="jslx" name="jslx" disabled="disabled"
						class="selectpicker">
							<option value="0" <c:if test="${0==kpls.jslx}">selected</c:if>>交易开始时间</option>
							<option value="1" <c:if test="${0==kpls.jslx}">selected</c:if>>交易结束时间</option>
							<option value="2" <c:if test="${0==kpls.jslx}">selected</c:if>>交易结算前时间</option>
							<option value="3" <c:if test="${1==kpls.jslx}">selected</c:if>>交易结算后时间</option>
					</select></td>
				</tr>
				<tr>
					<td><label>账号类型:</label></td>
					<td><select id="zhlx" name="zhlx" disabled="disabled"
						class="selectpicker form-control">
							<option value="terminal_id"
								<c:if test="${'terminal_id'==kpls.zhlx}">selected</c:if>>终端编号</option>
							<option value="jszh"
								<c:if test="${'jszh'==kpls.zhlx}">selected</c:if>>结算账户</option>
							<option value="kqzh"
								<c:if test="${'kqzh'==kpls.zhlx}">selected</c:if>>快钱账号</option>
							<option value="merchant_id"
								<c:if test="${'merchant_id'==kpls.zhlx}">selected</c:if>>商户编号</option>
					</select></td>
					<td><label>账号编码：</label></td>
					<td><input type="text" name="zhbh" id="zhbh" maxlength="64"
						disabled="disabled" value="${kpls.zhbh }" /></td>
				</tr>
				
				<tr>
					<td><label>可开票金额：</label></td>
					<td><input type="text" disabled="disabled" name="zje" id="zje"
						value="${kpls.zje }" maxlength="20"/></td>
					<td><label>开票金额：</label></td>
					<td><input type="text" disabled="disabled" name="ykpje"
						id="ykpje" value="${kpls.jshj }"/></td>
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