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
<meta charset="utf-8" />
<title></title>
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
<style type="text/css">
		.portmsg{
		   color:red;
		   font-size: 15px;
		}
			.company{
		}
</style>
<script type="text/javascript">
	$(top.hangge());
	//保存
	function save() {
		if($("#gfmc").val()==""){
			$("#gfmc").tips({
				side:3,
	            msg:'客户名称不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gfmc").focus();
			return false;
		}
		if($("#gflx").val()=="0"){
		if($("#gfsh").val()==""){
			$("#gfsh").tips({
				side:3,
	            msg:'客户税号不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gfsh").focus();
			return false;
		}
		if($("#gfyh").val()==""){
			$("#gfyh").tips({
				side:3,
	            msg:'客户银行不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gfyh").focus();
			return false;
		}
		if($("#gfyhzh").val()==""){
			$("#gfyhzh").tips({
				side:3,
	            msg:'客户银行账号不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gfyhzh").focus();
			return false;
		}
		if($("#gfdz").val()==""){
			$("#gfdz").tips({
				side:3,
	            msg:'客户地址不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gfdz").focus();
			return false;
		}
		if($("#gfdh").val()==""){
			$("#gfdh").tips({
				side:3,
	            msg:'客户电话不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gfdh").focus();
			return false;
		}
		/* if($("#gflxr").val()==""){
			$("#gflxr").tips({
				side:3,
	            msg:'客户联系人不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gflxr").focus();
			return false;
		}
		if($("#gflxrdh").val()==""){
			$("#gflxrdh").tips({
				side:3,
	            msg:'联系人电话不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gflxrdh").focus();
			return false;
		} */
		}
		var nsrsbh=$("#gfsh").val();
		if(nsrsbh!=''&&nsrsbh.length!=15&&nsrsbh.length!=18){
			$("#gfsh").tips({
				side:3,
	            msg:'纳税人识别号只能是15或者18位',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gfsh").focus();
			return false;	
		}
		if($("#zje").val()==""){
			$("#zje").tips({
				side:3,
	            msg:'可开票总金额不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#zje").focus();
			return false;
		}
		if($("#ykpje").val()==""){
			$("#ykpje").tips({
				side:3,
	            msg:'已开票总金额不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#ykpje").focus();
			return false;
		}
		if($("#gf_yxtid").val()==""){
			$("#gf_yxtid").tips({
				side:3,
	            msg:'原系统ID不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#gf_yxtid").focus();
			return false;
		}
		//提交表单
		$.ajax({
			type: "POST",
			url: '<%=basePath%>server/customer/saveCustomerInfo.do',
	    	data: $("#customerInfoForm").serialize(),
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.msg){
					$("#addBz").val("success");
					alert('修改成功！');
					$("#zhongxin").hide();
					top.Dialog.close();
				 }else{
					 alert('添加失败!')
				 }
			}
		});
	}
</script>
</head>
<body>
	<form action="server/customer/saveCustomerInfo.do" name="customerInfoForm" id="customerInfoForm" method="post">
		<div id="zhongxin" style="text-align: center;">
			<table style="margin: auto;">
				<tr>
					<td><label>客户类型：</label></td>
					<td><select name="gflx" id="gflx" onchange="gflxSelect()">
							<option value="0">企业</option>
							<option value="1">个人</option>
					</select></td>
				</tr>
				<tr>
					<td><label>客户名称：</label></td>
					<td><input type="text" name="gfmc" id="gfmc" maxlength="100" value="${customerInfo.gfmc}" /><input type="hidden" name="id" id="customerInfoId" maxlength="20" value="${customerInfo.id}"  /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>客户税号：</label></td>
					<td><input type="text" name="gfsh" id="gfsh" maxlength="20" value="${customerInfo.gfsh}" /></td>
					<td><div class="company" style="display:block;" ><span class="style="display:none;"">*</span></div></td>
				</tr>
				<tr>
					<td><label>客户银行：</label></td>
					<td><input type="text" name="gfyh" id="gfyh" maxlength="64" value="${customerInfo.gfyh}" /></td>
					<td><div class="company" style="display:block;" ><span class="style="display:none;"">*</span></div></td>
				</tr>
				<tr>
					<td><label>客户银行账号：</label></td>
					<td><input type="text" name="gfyhzh" id="gfyhzh" maxlength="64" value="${customerInfo.gfyhzh}" /></td>
					<td><div class="company" style="display:block;" ><span class="style="display:none;"">*</span></div></td>
				</tr>
				<tr>
					<td><label>客户地址：</label></td>
					<td><input type="text" name="gfdz" id="gfdz" maxlength="200" value="${customerInfo.gfdz}" /></td>
					<td><div class="company" style="display:block;" ><span class="style="display:none;"">*</span></div></td>
				</tr>
				<tr>
					<td><label>客户电话：</label></td>
					<td><input type="text" name="gfdh" id="gfdh" maxlength="32" value="${customerInfo.gfdh}" /></td>
					<td><div class="company" style="display:block;" ><span class="style="display:none;"">*</span></div></td>
				</tr>
				
				<%--<tr>
					<td><label>客户联系人：</label></td>
					<td><input type="text" name="gflxr" id="gflxr" maxlength="64" value="${customerInfo.gflxr}" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>客户联系人电话：</label></td>
					<td><input type="text" name="gflxrdh" id="gflxrdh" maxlength="32" value="${customerInfo.gflxrdh}" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>邮寄地址：</label></td>
					<td><input type="text" name="yjdz" id="yjdz" maxlength="60" value="${customerInfo.yjdz}" /></td>
				</tr>
				<tr>
					<td><label>邮政编码：</label></td>
					<td><input type="text" name="gfyb" id="gfyb" maxlength="60" value="${customerInfo.gfyb}" /></td>
				</tr> --%>
				<%-- <tr>
					<td><label>可开票总额：</label></td>
					<td><input type="text" name="zje" id="zje" maxlength="18" value="${customerInfo.zje}" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>已开票总额：</label></td>
					<td><input type="text" name="ykpje" id="ykpje"  maxlength="18" value="${customerInfo.ykpje}" /></td>
					<td><span class="portmsg">*</span></td>
				</tr> --%>
				<!-- 新加原系统ID和数据来源Begin -->
				<tr>
					<td><label>原系统ID：</label></td>
					<td><input type="text" name="yxtid" id="gf_yxtid" maxlength="32" value="${customerInfo.yxtid}" placeholder="这里输入原系统ID" title="原系统ID" /></td>
					<td><input type="hidden"   id="khid" name="gf_yxtid" maxlength="32" value="${customerInfo.gf_yxtid}"  /><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>数据来源：</label></td>
					<td><select name="sjly" id="sjly">//
							<option value="0">ODS数据仓库</option>
							<option value="1">Oracle ERP系统</option>
							<option value="2">进件系统</option>
							<option value="3">商派</option>
					</select></td>
				</tr>
				<tr>
					<td><label>抬头新增：</label></td>
					<td><a style="width: 210px;" class="btn btn-mini btn-info" onclick="addTt()">新增抬头</a></td>
				</tr>
				<tr>
					<td> <input id="addBz" value="fail" type="hidden" name="addBz"/> <input id="ttIds" type="hidden" name="ttIds"/> <input id="ttfjName" type="hidden" name="ttfjName"/> </td>
				</tr>
				<tr>
					<td><label>新增抬头名称：</label></td>
					<td ><input type="text" name="ttmc" id="ttmc" readOnly="true" /></td>
					
				</tr>
				<!-- 新加原系统ID和数据来源End -->
				<!-- <tr>
					<td><label>开票类型：</label></td>
					<td><select name="kplx" id="kplx">
							<option value="0">增值税普通发票</option>
							<option value="1">增值税专用发票</option>
							<option value="2">不开</option>
					</select></td>
				</tr> -->
				<tr>
					<td style="text-align: center;" colspan="2"><a class="btn btn-mini btn-primary" onclick="save();">保存</a> <a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a></td>
				</tr>
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
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script>
	<!-- 下拉框 -->

	<script type="text/javascript">
		$(function() {
			var gflx='${customerInfo.gflx}';
			if(gflx=='企业'){
				$("#gflx").val(0);
				$(".company").css('display','block');
			}else if(gflx=='个人'){
				$("#gflx").val(1);
				$(".company").css('display','none');
			}
			
			$("#sjly").val('${customerInfo.sjly}');

		});
		//购方类型选择
		function gflxSelect(){
			var gflx=$("#gflx").val();
			if(gflx=='0'){
				$(".company").css('display','block');
			}else{
				$(".company").css('display','none');
			}
		}
		//增加抬头
		function addTt() {
			//top.jzts();
			var ttfjName=$("#ttfjName").val();
			if(ttfjName!=null&&ttfjName!=''){
    			$.ajax({
    				type: "POST",
    				url: 'server/customer/deleteCustomerTtFj.do',
    	    		data: {
    	    			ttfjName:ttfjName
    	    		},
    				dataType:'json',
    				cache: false,
    				success: function(data){
    				 
    				}
    			});
    		}
			var khid=$('#khid').val();
			var diagTt = new top.Dialog();
			diagTt.Drag = true;
			diagTt.Title = "抬头信息添加";
			diagTt.URL = '<%=basePath%>server/customer/customerTtList.do?khid='+khid
			diagTt.Width = 1100;
			diagTt.Height = 400;
			diagTt.CancelEvent = function() { // 关闭事件
					$('#ttIds').val(diagTt.innerFrame.contentWindow.document
							.getElementById('ttIds').value);
					$('#ttmc').val(diagTt.innerFrame.contentWindow.document
							.getElementById('ttmc').value);
					$('#ttfjName').val(diagTt.innerFrame.contentWindow.document
							.getElementById('ttfjName').value);
					diagTt.close();
				};
				diagTt.show();
			}
	</script>

</body>
</html>