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
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<title></title>
		<meta name="description" content="overview & stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link href="static/css/bootstrap.min.css" rel="stylesheet" />
		<link href="static/css/bootstrap-responsive.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="static/css/font-awesome.min.css" />
		<link rel="stylesheet" href="static/css/bootstrap-select.css" />
		
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/css/chosen.css" />
		<link rel="stylesheet" href="static/css/ace-responsive.min.css" />
		<link rel="stylesheet" href="static/css/ace-skins.min.css" />
		<script type="text/javascript" src="static/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="static/js/bootstrap-select.js"></script>
		<!--提示框-->
		<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<style type="text/css">
		.portmsg{
		   color:red;
		   font-size: 15px;
		}
		</style>
<script type="text/javascript">
	$(top.hangge());
	$(document).ready(function(){
		if($("#id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	
	
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
			diagTt.close();
		};
		diagTt.show();
		}
</script>
	</head>
<body>
	<form action="leaveBill/save.do" name="userForm" id="userForm" method="post">
		<input type="hidden" name="id" id="id" value="${pdKpls.id }"/>
		<div id="zhongxin" style="text-align: center;">
		<table style="margin:auto;">
			<tr>
				<td><label>开票单位名称：</label></td>
			<td>
			   <input type="text" disabled="disabled" name="xfmc"
						id="xfmc" value="${pdKpls.xfmc }" maxlength="20"/>
			</td>
				<td><label>申请类型：</label></td>
			<td><select id="sqlx" disabled="disabled" name="sqlx">
						<option value="" selected>请选择申请类型</option>
				        <option value="0" onclick="pdCplx(0);"<c:if test="${0==pdKpls.sqlx}">selected</c:if>>固定开票申请（新增）</option>
				        <option value="1"onclick="pdCplx(1);"<c:if test="${1==pdKpls.sqlx}">selected</c:if>>非固定开票申请</option>
				        <option value="2" onclick="pdCplx(2);"<c:if test="${2==pdKpls.sqlx}">selected</c:if>>固定开票申请（变更）</option>
				     </select>
				     </td>
			
			</tr>
			<tr>
				<td><label>公司名称：</label></td>
			<td>
			<input type="text" onClick="searchKhxx()" disabled="disabled" readOnly="true" name="ygsidmc" id="ygsidmc" value="${pdKpls.ygsidmc}" maxlength="20"/>
			<input type="hidden" name="ygsid" id="ygsid" value="${pdKpls.ygsid }" maxlength="20" />
			</td>
			<td><label>发票抬头：</label></td>
			<td>
			<input type="text" disabled="disabled" name="gfmc"
						id="gfmc" value="${pdKpls.gfmc}"/>
			<input type="hidden" id="ttIds"/>
			</td>
			<td>
			<!-- <a class="btn btn-small btn-primary" onclick="addTt();">申请</a> -->
			</td>
			</tr>
			<tr>
				<td><lable>数据来源</lable></td>
					<td>
					<select id="sjly" disabled="disabled" name="sjly">
						<option onclick="sjlybh();" value="0" <c:if test="${0==pdKpls.sjly}">selected</c:if>>ODS数据仓库</option>
						<option onclick="sjlybh();" value="1" <c:if test="${1==pdKpls.sjly}">selected</c:if>>Oracle ERP系统</option>
						<option onclick="sjlybh();" value="2" <c:if test="${2==pdKpls.sjly}">selected</c:if>>进件系统</option>
						<option onclick="sjlybh();" value="3" <c:if test="${3==pdKpls.sjly}">selected</c:if>>商派</option>
					</select>
					</td>
				<td><label>开票类型：</label></td>
				<td>
				<select id="fplx" disabled="disabled" name="fplx" class="selectpicker">
				        <option value="004" <c:if test="${004==pdKpls.fplx}">selected</c:if>>专票</option>
				        <option value="007" <c:if test="${007==pdKpls.fplx}">selected</c:if>>普票</option>
				        <option value="026" <c:if test="${026==pdKpls.fplx}">selected</c:if>>电子发票</option>
				     <option value="0" <c:if test="${0==pdKpls.fplx}">selected</c:if>>收据</option>
				     </select>
				</td>
					
				</tr>
			<tr>
			<td>收款类型:</td>
				<td>
				<select id="sklx" disabled="disabled" name="sklx" class="selectpicker">
						<option value="0" <c:if test="${'0'==pdKpls.sklx}">selected</c:if>>先票后款</option>
				        <option value="1" <c:if test="${'1'==pdKpls.sklx}">selected</c:if>>先款后票</option>
					</select>
				</td>
				<td><label id="cplbtips">产品类别：</label></td>
					<td><select id="cplbTemp" disabled="disabled" name="cplbTemp">
				        <option value="0" onclick="searchDd()" <c:if test="${0==pdKpls.cplb}">selected</c:if>>订单</option>
				        <option value="1"onclick="cplbSxf()" <c:if test="${1==pdKpls.cplb}">selected</c:if>>手续费</option>
				     </select>
				     <input type="hidden" id="dingDanJylshs" name="dingDanJylshs" value="${pdKpls.dingDanJylshs }"/>
				     <input type="hidden" id="dingDanIds" name="dingDanIds" value="${pdKpls.dingDanIds }"/>
				     <input type="hidden" id="cplb" name="cplb" value="${pdKpls.cplb}">
				     </td>
				<td><c:if test="${0==pdKpls.cplb}">
							<a class="btn btn-small btn-info" onclick="searchDd();">订单</a>
						</c:if></td>
						<input type="hidden" id="ddjylsh" name="ddjylsh"
						value="${pdKpls.ddjylsh }" />
			</tr>
				
				
			<tr>
				<td><label>业务类型：</label></td>
				<td>
					<select id="ywlx" disabled="disabled" name="ywlx">
						<c:forEach items="${ywlxList}" var="item" varStatus="status">  
						<option onclick="ywlxbh(${item.dmbh})" value="${item.dmbh}" <c:if test="${item.dmbh==pdKpls.ywlx}">selected</c:if>>${item.lxmc}</option>
						</c:forEach> 
					</select>
				</td>
				<td><label>结算类型：</label></td>
				<td>
					<select id="jslx" disabled="disabled" name="jslx">
				        <option onclick="jslxbh()" value="0" <c:if test="${'0'==pdKpls.jslx}">selected</c:if>>交易开始时间</option>
						<option onclick="jslxbh()" value="1" <c:if test="${'1'==pdKpls.jslx}">selected</c:if>>交易结束时间</option>
						<option onclick="jslxbh()" value="2" <c:if test="${'2'==pdKpls.jslx}">selected</c:if>>交易结算前时间</option>
						<option onclick="jslxbh()" value="3" <c:if test="${'3'==pdKpls.jslx}">selected</c:if>>交易结算后时间</option>
				  </select>
				</td>
				<input id="old_jslx" type="hidden" name="old_jslx" value="${pdKpls.jslx}"/>
				</tr>
			
			<tr>
			
			</tr>
			<tr id="showOrhidden">
				<td><label>账号类型：</label></td>
				<td>
					<select id="zhlx" disabled="disabled" name="zhlx">
						<option value="" selected></option>
				        <option value="terminal_id" <c:if test="${'terminal_id'==pdKpls.zhlx}">selected</c:if> onclick="selectKhbm();">终端编号</option>
				        <option value="jszh" <c:if test="${'jszh'==pdKpls.zhlx}">selected</c:if> onclick="selectKhbm();">结算账户</option>
				        <option value="kqzh" <c:if test="${'kqzh'==pdKpls.zhlx}">selected</c:if> onclick="selectKhbm();">快钱账号</option>
				        <option value="merchant_id" <c:if test="${'merchant_id'==pdKpls.zhlx}">selected</c:if> onclick="selectKhbm();">商户编号</option>
				     </select>
				</td>
				<td><label>账号编号：</label></td>
				<td><input type="text" disabled="disabled" name="zhbh" value="${pdKpls.zhbh }" id="zhbh" maxlength="128" /></td>
			</tr>
			<tr>
				<td><label>可开票金额：</label></td>
				<td><input type="text" name="zje" id="zje" disabled="disabled" readonly="readonly" value="${pdKpls.zje }" maxlength="20"/></td>
				<td><label>开票金额：</label></td>
				<td><input type="text" name="ykpje"disabled="disabled" value="${pdKpls.ykpje }" id="ykpje" <c:if test="${0==pdKpls.cplb}">readonly="readonly"</c:if> onblur="checkYkpje()" maxlength="20"/></td>
			</tr>
			<tr>
				<td><label>收 件  人：</label></td>
				<td><input type="text" name="gflxr" disabled="disabled" maxlength="32" id="gflxr" value="${pdKpls.gflxr }" /></td>
				<td><label>收件人电话：</label></td>
				<td><input type="text" name="gflxrdh" disabled="disabled" maxlength="32" id="gflxrdh" value="${pdKpls.gflxrdh }" /></td>
			</tr>
			<tr>
				<td><label>邮 寄 地 址：</label></td>
				<td><input type="text" name="yjdz" disabled="disabled" id="yjdz" maxlength="200" value="${pdKpls.yjdz }"/></td>
				<td><label>邮 政 编 码：</label></td>
				<td><input type="text" name="gfyb" disabled="disabled" id="gfyb" maxlength="32" value="${pdKpls.gfyb }" /></td>
			</tr>
			<tr>
				<td><label> 备  注 ：</label></td>
				<td><textarea name="remark" id="remark" disabled="disabled">${pdKpls.remark }</textarea></td>
			
			</tr>
			<!-- 修改需要隐藏的数据 -->
			<tr>
				<td><input type="hidden" name="kplsh" id="kplsh" value="${pdKpls.kplsh }" /></td>
				<td><input type="hidden" name="dingDanIds" id="dingDanIds" value="${pdKpls.dingDanIds}" /></td>
				<td><input type="hidden" name="editCplb" id="editCplb" value="${pdKpls.cplb }" /></td>
			</tr>
			<tr>
				<td><input type="hidden" id="saveOrsubmit" name="saveOrsubmit"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="4">
				<a class="btn btn-small btn-info" onclick="top.Dialog.close();">返回</a>
				</td>
			</tr>
		</table>
		</div>
		
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		
		<script type="text/javascript">
		//判断用户名是否存在
		$(function() {
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true});
			//日期框			
		})
		</script>
	
</body>
</html>