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
		<script type="text/javascript" src="static/js/util/fromValidate.js"></script>
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
	
	//保存
	function save(lcjd){
		$("#saveOrsubmit").val(lcjd);
		if($("#xfid").val()==""){
			$("#xfid").tips({
				side:3,
	            msg:'开票单位不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#xfid").focus();
			return false;
		}
		if($("#sqlx").val()==""){
			$("#sqlx").tips({
				side:3,
	            msg:'申请类型不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#sqlx").focus();
			return false;
		}
		
		if($("#ygsid").val()==""){
			$("#ygsidmc").tips({
				side:3,
	            msg:'公司名称不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ygsid").focus();
			return false;
		}
		if($("#gfmc").val()=="" || $("#gfmc").val()==null){
			$("#gfmc").tips({
				side:3,
	            msg:'发票抬头不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gfmc").focus();
			return false;
		}
		if($("#cplbTemp").val()==""){
			$("#cplbtips").tips({
				side:3,
	            msg:'产品类别不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			return false;
		}
		if($("#ywlx").val()==""||$("#ywlx").val()==null){
			$("#ywlx").tips({
				side:3,
	            msg:'业务类型不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			return false;
		}
		if($("#cplb").val()==1){//手续费
			if($("#zhlx").val()==""){
				$("#zhlx").tips({
					side:3,
					msg:'账号类型不能为空',
					bg:'#AE81FF',
		            time:2
				});
				return false;
			}
			if($("#zhbh").val()==""){
				$("#zhbh").tips({
					side:3,
		            msg:'账户编码不能为空',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#zhbh").focus();
				return false;
			}
		}
		
		if($("#fplx").val()==""){
			$("#fplx").tips({
				side:3,
	            msg:'发票类型不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#fplx").focus();
			return false;
		}
		if($("#cplb").val()==""){
			$("#cplbtips").tips({
				side:3,
	            msg:'产品类别不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#cplb").focus();
			return false;
		}
		if($("#zje").val()==""){
			$("#zje").tips({
				side:3,
	            msg:'可开票金额不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			//$("#zje").focus();
			return false;
		}
		if($("#zje").val()<=0){
			$("#zje").tips({
				side:3,
	            msg:'可开票金额需要大于0',
	            bg:'#AE81FF',
	            time:2
	        });
			return false;
		}
		if($("#ykpje").val()==""){
			$("#ykpje").tips({
				side:3,
	            msg:'开票金额不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ykpje").focus();
			return false;
		}
		if($("#ykpje").val()<=0){
			$("#ykpje").tips({
				side:3,
	            msg:'开票金额要大于0',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ykpje").focus();
			return false;
		}
		if($("#gflxr").val()==""){
			$("#gflxr").tips({
				side:3,
	            msg:'收件人不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gflxr").focus();
			return false;
		}
		if($("#gflxrdh").val()==""){
			$("#gflxrdh").tips({
				side:3,
	            msg:'收件人电话不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gflxrdh").focus();
			return false;
		}
		if(!isPhoneOrTel($("#gflxrdh").val())){
			$("#gflxrdh").tips({
				side:3,
	            msg:'收件人电话格式有误',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gflxrdh").focus();
			return false;
		}
		if($("#yjdz").val()==""){
			$("#yjdz").tips({
				side:3,
	            msg:'邮寄地址不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#yjdz").focus();
			return false;
		}
		if($("#gfyb").val()==""){
			$("#gfyb").tips({
				side:3,
	            msg:'邮编不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gfyb").focus();
			return false;
		}
		if(!isPostCode($("#gfyb").val())){
			$("#gfyb").tips({
				side:3,
	            msg:'邮政编码格式有误',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gfyb").focus();
			return false;
		}
		//$("#userForm").submit();
		//提交表单
		$.ajax({
			type: "POST",
			url: '<%=basePath%>leaveBill/save.do',
	    	data: $("#userForm").serialize(),
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.msg){
					$("#zhongxin").hide();
					$("#zhongxin2").show();
					alert('添加成功！');
					top.Dialog.close();
				 }else{
					 alert(data.msg)
				 }
			}
		});
		
	}
	
	//增加抬头
	function addTt() {
		//top.jzts();
		var diagTt = new top.Dialog();
		diagTt.Drag = true;
		diagTt.Title = "抬头信息添加";
		diagTt.URL = '<%=basePath%>leaveBill/customerTtList.do'
		diagTt.Width = 1100;
		diagTt.Height = 600;
		diagTt.CancelEvent = function() { // 关闭事件
			$('#ttIds').val(diagTt.innerFrame.contentWindow.document
						.getElementById('ttIds').value);
			$('#ttfjName').val(diagTt.innerFrame.contentWindow.document
						.getElementById('ttfjName').value);
			var ids = diagTt.innerFrame.contentWindow.document
			.getElementById('ttIds').value.split(",");
			var names = diagTt.innerFrame.contentWindow.document
			.getElementById('ttmc').value.split(",");
			
			for(var i=0;i<ids.length;i++){
				if(names[i]!=null && "" !=names[i]){
					$("#gfmc").append("<option value='"+ids[i]+"'>"+names[i]+"</option>");
				}
				
			}
			$("#gflxr").focus();
				diagTt.close();
			};
			diagTt.show();
		}
	
	//查询抬头
	function searchTt(id){
		$.ajax({
			type: "POST",
			url: '<%=basePath%>leaveBill/searchTt.do?tm='+new Date().getTime(),
	    	data: {id:id},
			dataType:'json',
			cache: false,
			success: function(data){
				$("#gfmc").empty();
				for(var i=0;i<data.list.length;i++){
					if(data.list[i].gfmc!=null && data.list[i].gfmc!=""){
					 $("#gfmc").append("<option value='"+data.list[i].id+"'>"+data.list[i].gfmc+"</option>");
					}
				}
			}
		});
	}
		//查询公司名称
		function searchKhxx(){
			//top.jzts();
			var diagTt = new top.Dialog();
			diagTt.Drag = true;
			diagTt.Title = "公司名称查询";
			diagTt.URL = '<%=basePath%>leaveBill/listCustomerMc.do'
			diagTt.Width = 1100;
			diagTt.Height = 600;
			diagTt.CancelEvent = function() { // 关闭事件
					var id = diagTt.innerFrame.contentWindow.document
					.getElementById('ttIds').value;
					var name = diagTt.innerFrame.contentWindow.document
							.getElementById('ttmc').value;
					if(name!=null && name !=""){
						$('#ygsidmc').val(diagTt.innerFrame.contentWindow.document
								.getElementById('ttmc').value);
					}
					if(id!=null && id!=""){
						$('#ygsid').val(diagTt.innerFrame.contentWindow.document
								.getElementById('ttIds').value);
						searchTt(id);//查询发票抬头
					}
					
					$("#gflxr").focus();
					diagTt.close();
					
				};
				diagTt.show();
		}
	//产品类别变化后，用于确定可开票金额
	function cplbSxf(){
		$("#ykpje").attr("readOnly",false);
		$("#showOrhidden").show();
		$("#cplb").val("1");
		$("#zhbh").val("");
		$("#zhlx").val("");
		$("#zje").val("");
	}
	//手续费计算开票金额
	function sxfJsKpje(){
		var cplb = $("#cplb").val();
		var ygsid = $("#ygsid").val();
		var zhlx = $("#zhlx").val();
		var zhbh = $("#zhbh").val();
		var ywlx = $("#ywlx").val();
		var sjly = $("#sjly").val();//数据来源
		var jslx = $("#jslx").val();//结算类型
		
		$("#ykpje").attr("readOnly",false);
		$.ajax({
			type: "POST",
			url: '<%=basePath%>leaveBill/searchKkpje.do?tm='+new Date().getTime(),
	    	data: {ygsid:ygsid,zhlx:zhlx,cplb:cplb,zhbh:zhbh,ywlx:ywlx,jslx:jslx,jylsly:sjly},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.msg){
						$("#zje").val(data.pdzje.zje);
					 }else{
						 alert(data.msg);
						 $("#zje").val("");
					 }
			}
		});
	}
	//校验已开票金额要小于等于可开票金额
	function checkYkpje(){
		if($("#zje").val()!=null && $("#zje").val()!=""){
			if(parseFloat($("#ykpje").val())>parseFloat($("#zje").val())){
				alert("开票金额不能大于可开票金额");
			}
			if($("#ykpje").val()<=0){
				alert("开票金额要大于0");
			}
			
		}else{
			alert("可开票金额不能为空");
		}
		
		
	}
	//订单信息
	function searchDd(){
		if($("#xfid").val()==""||$("#xfid").val()==null){
			alert("请选择开票单位");
			return false;
		}
		if($("#fplx").val()==""||$("#fplx").val()==null){
			alert("请选择开票类型")
			return false;
		}
		var xfid = $("#xfid").val();
		var fplx = $("#fplx").val();
		
		$("#showOrhidden").hide();
		$("#cplb").val("0");
		var diagTt = new top.Dialog();
		diagTt.Drag = true;
		diagTt.Title = "订单信息添加";
		diagTt.URL = '<%=basePath%>leaveBill/searchDd.do?xfid='+xfid+'&fplx='+fplx
		diagTt.Width = 1100;
		diagTt.Height = 600;
		diagTt.CancelEvent = function() { // 关闭事件
			$('#zje').val(diagTt.innerFrame.contentWindow.document
						.getElementById('zje').value);
			$('#dingDanJylshs').val(diagTt.innerFrame.contentWindow.document
				.getElementById('dingDanJylshs').value);
			$('#dingDanIds').val(diagTt.innerFrame.contentWindow.document
					.getElementById('dingDanIds').value);
			$('#ykpje').val(diagTt.innerFrame.contentWindow.document
					.getElementById('zje').value);
			$("#ykpje").attr("readOnly","true");
			$("#gflxr").focus();
				diagTt.close();
			};
			diagTt.show();
	}
	//查询客户账号编号
	function selectKhbm(){
		$("#zje").val("");
		$("#zhbh").val("");
		var sqlx = $("#sqlx").val();
		var cplb = $("#cplb").val();
		var ygsid = $("#ygsid").val();
		var zhlx = $("#zhlx").val();
		var ywlx = $("#ywlx").val();
		var sjly = $("#sjly").val();//数据来源
		var jslx = $("#jslx").val();//结算类型
		
		if(sqlx==""){
			alert("申请类型不能为空");
			return false;
		}
		if(ygsid==""){
			alert("公司名称不能为空");
			return false;
		}if(ywlx==""){
			alert("业务类型不能为空");
			return false;
		}
		$.ajax({
			type: "POST",
			url: '<%=basePath%>leaveBill/selectKhbm.do?tm='+new Date().getTime(),
	    	data: {zhlx:zhlx,ygsid:ygsid,ywlx:ywlx,jslx:jslx,jylsly:sjly},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.msg){
					$("#zhbh").val(data.pdKhbm.khbm);
					$("#zhbh").attr("readOnly","readOnly");
					//成功后查询开票金额start
					var zhbh = $("#zhbh").val();
					$.ajax({
						type: "POST",
						url: '<%=basePath%>leaveBill/searchKkpje.do?tm='+new Date().getTime(),
				    	data: {ygsid:ygsid,zhlx:zhlx,cplb:cplb,zhbh:zhbh,ywlx:ywlx,jslx:jslx,jylsly:sjly},
						dataType:'json',
						cache: false,
						success: function(data){
							 if("success" == data.msg){
									$("#zje").val(data.pdzje.zje);
							}else{
									 alert(data.msg);
									 $("#zje").val("");
								 }
						}
					});
					//成功后查询开票金额end
				 }else{
					 alert(data.msg);
				 }
			}
		});
	}
	function pdCplx(sqlx){
		$("#showOrhidden").show();
		if(sqlx==1){
			$("#cplbTemp").attr("disabled",false);
		}else{
			$("#cplbTemp").val("1");
			$("#cplbTemp").attr("disabled","disabled");
			$("#cplb").val("1");
			$("#ykpje").attr("readOnly",false);
			$("#showOrhidden").show();
			$("#cplb").val("1");
			$("#zhbh").val("");
			$("#zhlx").val("");
			$("#zje").val("");
		}
		
	}
	//业务类型变化后，清空账号类型，编号，开票金额等
	function ywlxbh(dmbh){
		var cplb = $("#cplb").val();
		if(cplb!=0){
			$("#zhlx").val("");
			$("#zhbh").val("");
			$("#zje").val("");
		}
	}
	//结算类型变化后，做相应清空
	function jslxbh(jslx){
		var cplb = $("#cplb").val();
		if(cplb!=0){
			$("#zhlx").val("");
			$("#zhbh").val("");
			$("#zje").val("");
		}
		
	}
	//数据来源变化后，做相应清空
	function sjlybh(){
		var cplb = $("#cplb").val();
		if(cplb!=0){
			$("#zhlx").val("");
			$("#zhbh").val("");
			$("#zje").val("");
		}
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
			    <select name="xfid" id="xfid"  class="selectpicker" data-done-button="true"  title="请选择开票单位">
					<c:forEach items="${xflist}" var="item" varStatus="status">  
						<option value="${item.id}" <c:if test="${item.id==pdKpls.xfid}">selected</c:if>>${item.xfmc}</option>
					</c:forEach> 
				</select>
				<span class="portmsg">*</span>
			</td>
				<td><label>申请类型：</label></td>
			<td><select id="sqlx" name="sqlx">
						<option value="" selected>请选择申请类型</option>
				        <option value="0" onclick="pdCplx(0);"<c:if test="${0==pdKpls.sqlx}">selected</c:if>>固定开票申请（新增）</option>
				        <option value="1"onclick="pdCplx(1);"<c:if test="${1==pdKpls.sqlx}">selected</c:if>>非固定开票申请</option>
				        <option value="2" onclick="pdCplx(2);"<c:if test="${2==pdKpls.sqlx}">selected</c:if>>固定开票申请（变更）</option>
				     </select>
				     <span class="portmsg">*</span>
				     </td>
			
			</tr>
			<tr>
				<td><label>公司名称：</label></td>
			<td>
			<input type="text" onClick="searchKhxx()" readOnly="true" name="ygsidmc" id="ygsidmc" value="${pdKpls.ygsidmc}" maxlength="20" placeholder="公司名称" title="公司名称"/><span class="portmsg">*</span>
			<input type="hidden" name="ygsid" id="ygsid" value="${pdKpls.ygsid }" maxlength="20" placeholder="公司名称" title="公司名称"/>
			</td>
			<td><label>发票抬头：</label></td>
			<td>
			
			 <select name="gfmc" id="gfmc" data-live-search="true" data-live-search-style="begins"  title="--请选择发票抬头--">
			</select><span class="portmsg">*</span>
			<input type="hidden" name="ttIds" id="ttIds"/>
			 <input id="ttfjName" type="hidden" name="ttfjName"/>
			</td>
			<td>
			<a class="btn btn-small btn-primary" onclick="addTt();">申请</a>
			</td>
			</tr>
		<tr>
					<td><lable>数据来源</lable></td>
					<td>
					<select id="sjly" name="sjly">
						<option onclick="sjlybh();" value="0" selected="selected">ODS数据仓库</option>
						<option onclick="sjlybh();" value="1">Oracle ERP系统</option>
						<option onclick="sjlybh();" value="2">进件系统</option>
						<option onclick="sjlybh();" value="3">商派</option>
					</select>
					</td>
					<td><label>开票类型：</label></td>
				<td>
				<select id="fplx" name="fplx" class="selectpicker">
				        <option value="004" <c:if test="${004==pdKpls.fplx}">selected</c:if>>专票</option>
				        <option value="007" <c:if test="${007==pdKpls.fplx}">selected</c:if>>普票</option>
				        <option value="026" <c:if test="${026==pdKpls.fplx}">selected</c:if>>电子发票</option>
				        <option value="0" <c:if test="${0==pdKpls.fplx}">selected</c:if>>收据</option>
				     </select><span class="portmsg">*</span>
				</td>
					
				</tr>
			<tr>
				<td><label id="cplbtips">产品类别：</label></td>
					<td><select id="cplbTemp" name="cplbTemp">
						<option value="">请选择产品类型</option>
				        <option value="0" onclick="searchDd()">订单</option>
				        <option value="1"onclick="cplbSxf()">手续费</option>
				     </select><span class="portmsg">*</span>
				     <input type="hidden" id="dingDanJylshs" name="dingDanJylshs"/>
				     <input type="hidden" id="dingDanIds" name="dingDanIds"/>
				     <input type="hidden" id="cplb" name="cplb">
				     
				     </td>
				<td>收款类型:</td>
				<td>
				<select id="sklx" name="sklx" class="selectpicker">
						<option value="0" >先票后款</option>
				        <option value="1">先款后票</option>
					</select><span class="portmsg">*</span>
				</td>
			</tr>
				
				
			<tr>
				<td><label>业务类型：</label></td>
				<td>
					<select id="ywlx" name="ywlx">
						<c:forEach items="${ywlxList}" var="item" varStatus="status">  
						<option onclick="ywlxbh(${item.dmbh})" value="${item.dmbh}" <c:if test="${item.id==pdKpls.ywlx}">selected</c:if>>${item.lxmc}</option>
						</c:forEach> 
					</select><span class="portmsg">*</span>
				</td>
				<td><label>结算类型：</label></td>
				<td>
				<select id="jslx" name="jslx">
				        <option onclick="jslxbh()" value="0" <c:if test="${0==kpls.jslx}">selected</c:if>>交易开始时间</option>
						<option onclick="jslxbh()" value="1" <c:if test="${1==kpls.jslx}">selected</c:if>>交易结束时间</option>
						<option onclick="jslxbh()" value="2" <c:if test="${2==kpls.jslx}">selected</c:if>>交易结算前时间</option>
						<option onclick="jslxbh()" value="3" <c:if test="${3==kpls.jslx}">selected</c:if>>交易结算后时间</option>
				  </select>
				</td>
				</tr>
			
			<tr>
			
			</tr>
			<tr id="showOrhidden">
				<td><label>账号类型：</label></td>
				<td>
					<select id="zhlx" name="zhlx">
						<option value="" selected>请选择账号类型</option>
				        <option value="terminal_id" onclick="selectKhbm();">终端编号</option>
				        <option value="jszh" onclick="selectKhbm();">结算账户</option>
				        <option value="kqzh" onclick="selectKhbm();">快钱账号</option>
				        <option value="merchant_id" onclick="selectKhbm();">商户编号</option>
				     </select>
				</td>
				<td><label>账号编号：</label></td>
				<td><input type="text" name="zhbh" id="zhbh" maxlength="128" placeholder="账户编码" title="账户编码"/></td>
			</tr>
			<tr>
				<td><label>可开票金额：</label></td>
				<td><input type="text" name="zje" id="zje" readonly="readonly" value="${pdKpls.zje }" maxlength="20" placeholder="可开票金额" title="可开票金额"/><span class="portmsg">*</span></td>
				<td><label>开票金额：</label></td>
				<td><input type="text" name="ykpje" id="ykpje" onblur="checkYkpje()" maxlength="20" placeholder="开票金额" title="开票金额"/><span class="portmsg">*</span></td>
			</tr>
			<tr>
				<td><label>收 件  人：</label></td>
				<td><input type="text" name="gflxr" autocomplete="off" maxlength="32" id="gflxr" value="${pdKpls.gflxr }" placeholder="收 件  人" title="收 件  人"/><span class="portmsg">*</span></td>
				<td><label>收件人电话：</label></td>
				<td><input type="text" name="gflxrdh" autocomplete="off" maxlength="32" id="gflxrdh" value="${pdKpls.gflxrdh }" placeholder="收件人电话" title="收件人电话"/><span class="portmsg">*</span></td>
			</tr>
			<tr>
				<td><label>邮 寄 地 址：</label></td>
				<td><input type="text" name="yjdz" id="yjdz" maxlength="200" value="${pdKpls.yjdz }" placeholder="邮 寄 地 址" title="邮 寄 地 址"/><span class="portmsg">*</span></td>
				<td><label>邮 政 编 码：</label></td>
				<td><input type="text" name="gfyb" id="gfyb" maxlength="32" value="${pdKpls.gfyb }" placeholder="邮 政 编 码" title="邮 政 编 码"/><span class="portmsg">*</span></td>
			</tr>
			<tr>
				<td><label> 备  注 ：</label></td>
				<td><textarea name="remark" id="remark" placeholder="备注"maxlength="100" title="备注">${pdKpls.remark }</textarea></td>
			
			</tr>
			<tr>
				<td><input type="hidden" id="saveOrsubmit" name="saveOrsubmit"/></td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="4">
					<a class="btn btn-small btn-primary" onclick="save(1);">提交</a>
					<a class="btn btn-small btn-info" onclick="save(0);">保存</a>
					<a class="btn btn-small btn-danger" onclick="top.Dialog.close();">取消</a>
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