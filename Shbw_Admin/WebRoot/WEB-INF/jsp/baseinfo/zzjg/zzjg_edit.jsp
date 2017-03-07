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
.portmsg {
	color: red;
	font-size: 15px;
}
</style>
<script type="text/javascript">
    var len=0;
    var sjjgbm;
	$(top.hangge());
	//保存
	function save() {
		if($("#xfmc").val()==""){
			$("#xfmc").tips({
				side:3,
	            msg:'机构名称不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#xfmc").focus();
			return false;
		}
		var jsbmValue=$("#jgbm").val();
		if(jsbmValue==""){
			$("#jgbm").tips({
				side:3,
	            msg:'机构代码不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#jgbm").focus();
			return false;
		}
		if((sjjgbm!='0')&&('${zzjg.jgbm}' == '' || '${zzjg.jgbm}' == null)){
		if(jsbmValue.length!=len+2){
			$("#jgbm").tips({
				side:3,
	            msg:'请输入'+(len+2)+'位的机构编码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#jgbm").focus();
			return false;
		}
		if(jsbmValue.substring(0,len)!=sjjgbm){
			$("#jgbm").tips({
				side:3,
	            msg:'请输入正确格式的机构编码',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#jgbm").val(sjjgbm);
			$("#jgbm").focus();
			return false;
		}}
		var nsrsbh=$("#xfsh").val();
		if(nsrsbh==""){
			$("#xfsh").tips({
				side:3,
	            msg:'纳税人识别号不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#xfsh").focus();
			return false;
		}
		if(nsrsbh.length!=15&&nsrsbh.length!=18){
			$("#xfsh").tips({
				side:3,
	            msg:'纳税人识别号只能是15或者18位',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#xfsh").focus();
			return false;	
		}
		if($("#xfdh").val()==""){
			$("#xfdh").tips({
				side:3,
	            msg:'机构电话不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#xfdh").focus();
			return false;
		}
		if($("#xfdz").val()==""){
			$("#xfdz").tips({
				side:3,
	            msg:'机构地址不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#xfdz").focus();
			return false;
		}
		if($("#xfyh").val()==""){
			$("#xfyh").tips({
				side:3,
	            msg:'机构银行不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#xfyh").focus();
			return false;
		}
		if($("#xfyhzh").val()==""){
			$("#xfyhzh").tips({
				side:3,
	            msg:'机构银行账号不能为空',
	            bg:'#AE81FF',
	            time:2
	        });
			$("#xfyhzh").focus();
			return false;
		}
		//提交表单
		$.ajax({
			type: "POST",
			url: '<%=basePath%>server/zzgl/saveZzjg.do',
			data : $("#zzjgForm").serialize(),
			dataType : 'json',
			cache : false,
			success : function(data) {
				if ("success" == data.msg) {
					alert('保存成功！');
					$("#zhongxin").hide();
					top.Dialog.close();
				} else {
					alert(data.result);
				}
			}
		});

	}
</script>
</head>
<body>
	<form action="server/zzgl/saveZzjg.do" name="zzjgForm" id="zzjgForm" method="post">
		<input type="hidden" name="id" id="id" value="${zzjg.id }" />
		<div id="zhongxin" style="text-align: center;">
			<table style="margin: auto;">
				<tr>
					<td><label>上级机构：</label></td>
					<td><input type="text" name="sjjg" id="sjjg" value="${zzjg.sjjg}" maxlength="20" readOnly="true" /></td>
					<input type="hidden" name="sjjgbm" id="sjjgbm" value="${zzjg.sjjgbm}" maxlength="20" />
				</tr>
				<tr>
					<td><label>机构名称：</label></td>
					<td><input type="text" name="xfmc" id="xfmc" value="${zzjg.xfmc}" maxlength="33" placeholder="这里输入机构名称" title="机构名称" /></td>
					<c:if test='${empty add }'><td><input type="hidden" name="jgbm"  maxlength="32" value="${zzjg.jgbm}" placeholder="这里输入机构代码" title="机构代码" /></td></c:if>
					<td><span class="portmsg">*</span></td>
				</tr>
				<c:if test='${not empty add }'> 
				<tr>
					<td><label>机构代码：</label></td>
					<td><input type="text" name="jgbm" id="jgbm" maxlength="32" value="${zzjg.jgbm}" placeholder="这里输入机构代码" title="机构代码" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				</c:if>
				<tr>
					<td><label>纳税人识别号：</label></td>
					<td><input type="text" name="xfsh" id="xfsh" maxlength="20" value="${zzjg.xfsh}" placeholder="这里输入纳税人识别号" title="纳税人识别号" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>机构电话：</label></td>
					<td><input type="text" name="xfdh" id="xfdh" maxlength="64" value="${zzjg.xfdh}" placeholder="这里输入机构电话" title="机构电话" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>机构地址：</label></td>
					<td><input type="text" name="xfdz" id="xfdz" value="${zzjg.xfdz}" maxlength="64" placeholder="这里输入机构地址" title="机构地址" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>机构银行名称：</label></td>
					<td><input type="text" name="xfyh" id="xfyh" value="${zzjg.xfyh}" maxlength="64" placeholder="这里输入机构银行" title="机构银行" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>机构银行账号：</label></td>
					<td><input type="text" name="xfyhzh" id="xfyhzh" value="${zzjg.xfyhzh}" maxlength="64" placeholder="这里输入机构银行账号" title="银行账号" /></td>
					<td><span class="portmsg">*</span></td>
				</tr>
				<tr>
					<td><label>状态：</label></td>
					<td><input type="radio" style="margin-top: -10px; width: 15px; opacity: 1; position: relative;" name="zt" id="yes" value="0" <c:if test="${empty zzjg.zt}">checked</c:if> <c:if test="${zzjg.zt==0}"> checked</c:if> />启用 <input type="radio"
						style="margin-top: -10px; width: 15px; opacity: 1;; position: relative; margin-left: 50px;" name="zt" id="no" value="1" <c:if test="${zzjg.zt==1}"> checked</c:if> />禁用</td>
				</tr>
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
			if ('${zzjg.jgbm}' == '' || '${zzjg.jgbm}' == null) {
				sjjgbm = '${zzjg.sjjgbm}';
				len = sjjgbm.length;
				$("#jgbm").val(sjjgbm);
			}
		});
	</script>

</body>
</html>