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

<script type="text/javascript">
	$(top.hangge());
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
					<input type="hidden" name="sjjgbm" id="sjjgbm" value="${zzjg.sjjgbm}" maxlength="20"  />
				</tr>
				<tr>
					<td><label>机构名称：</label></td>
					<td><input type="text" name="xfmc" id="xfmc" value="${zzjg.xfmc}" maxlength="64"  readOnly="true" placeholder="这里输入机构名称" title="机构名称" /></td>
				</tr>
				<tr>
					<td><label>机构代码：</label></td>
					<td><input type="text" name="jgbm" id="jgbm" maxlength="32" value="${zzjg.jgbm}" readOnly="true" placeholder="这里输入机构代码" title="机构代码" /></td>
				</tr>
				<tr>
					<td><label>纳税人识别号：</label></td>
					<td><input type="text" name="xfsh" id="xfsh" maxlength="32" value="${zzjg.xfsh}" readOnly="true" placeholder="这里输入纳税人识别号" title="纳税人识别号" /></td>
				</tr>
				<tr>
					<td><label>机构电话：</label></td>
					<td><input type="text" name="xfdh" id="xfdh" maxlength="32" value="${zzjg.xfdh}" readOnly="true"  placeholder="这里输入机构电话" title="机构电话" /></td>
				</tr>
				<tr>
					<td><label>机构地址：</label></td>
					<td><input type="text" name="xfdz" id="xfdz" value="${zzjg.xfdz}" maxlength="60" readOnly="true" placeholder="这里输入机构地址" title="机构地址" /></td>
				</tr>
				<tr>
					<td><label>机构银行名称：</label></td>
					<td><input type="text" name="xfyh" id="xfyh" value="${zzjg.xfyh}" maxlength="60" readOnly="true" placeholder="这里输入机构银行" title="机构银行" /></td>
				</tr>
				<tr>
					<td><label>机构银行账号：</label></td>
					<td><input type="text" name="xfyhzh" id="xfyhzh" value="${zzjg.xfyhzh}" maxlength="18" readOnly="true" placeholder="这里输入机构银行账号" title="银行账号" /></td>
				</tr>
				<tr>
					<td><label>创建时间：</label></td>
					<td><input type="text" name="create_date" id="create_date" value="${zzjg.create_date}" maxlength="18" readOnly="true"  /></td>
				</tr>
				<tr>
					<td><label>创建人：</label></td>
					<td><input type="text" name="creator" id="creator" value="${zzjg.creator}" maxlength="18" readOnly="true"  /></td>
				</tr>
				<tr>
					<td><label>修改时间：</label></td>
					<td><input type="text" name="update_date" id="update_date" value="${zzjg.update_date}" maxlength="18" readOnly="true" /></td>
				</tr>
				<tr>
					<td><label>修改人：</label></td>
					<td><input type="text" name="updator" id="updator" value="${zzjg.updator}" maxlength="18" readOnly="true"  /></td>
				</tr>
				<tr>
					<td><label>是否启用：</label></td>
					<td><input type="radio" disabled style="margin-top: -10px; width: 15px; opacity: 1; position: relative;" name="del_flag" id="yes" value="0" <c:if test="${zzjg.del_flag==0}"> checked</c:if> />是 <input disabled type="radio"
						style="margin-top: -10px; width: 15px; opacity: 1;; position: relative; margin-left: 50px;" name="del_flag" id="no" value="1" <c:if test="${zzjg.del_flag==1}"> checked</c:if> />否</td>
				</tr>
				<tr>
					<td style="text-align: center;" colspan="3"> <a class="btn btn-mini btn-info"  style="width: 300px;" onclick="top.Dialog.close();">返回</a></td>
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
	</script>

</body>
</html>