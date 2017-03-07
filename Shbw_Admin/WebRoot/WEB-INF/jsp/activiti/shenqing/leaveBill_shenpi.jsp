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
	$(document).ready(function(){
		if($("#id").val()!=""){
			$("#loginname").attr("readonly","readonly");
			$("#loginname").css("color","gray");
		}
	});
	
	//保存
	function save(){
		if($("#days").val()==""){
			hasU();
		}else{
			$("#userForm").submit();
			$("#zhongxin").hide();
			$("#zhongxin2").show();
			//top.Dialog.close();关闭新增页面
		}
	}
	
	//判断用户名是否存在
	function hasU(){
		alert("当前数据为空");
		
	}
	
	
	

	
</script>
	</head>
<body>
	<form action="leaveBill/submitTask.do" name="userForm" id="userForm" name="userForm" id="userForm" method="post">
		<input name="taskid" hidden="true" value="${taskId }">
		<input name="id" hidden="true" value="${leaveBill.id}">
		<div id="zhongxin" style="text-align: center;">
			<table style="margin:auto;">
			<tr>
				<td><label>开票金额：</label></td>
				<td><input type="text" readonly="readonly" name="days" id="days" value="${leaveBill.days}" maxlength="20" placeholder="这里输入开票金额" title="开票金额"/></td>
			</tr>
			<tr>
				<td><label>开票内容：</label></td>
				<td><input type="text" name="content" readonly="readonly" id="content" value="${leaveBill.content}" maxlength="20" placeholder="这里输入开票内容" title="开票内容"/></td>
			</tr>
			<tr>
				<td><label>备注</label></td>
				<td><textarea name="remark" readonly="readonly" id="remark" title="备注">${leaveBill.remark}</textarea></td>
			</tr>
			<tr>
				<td><label>开票人员：</label></td>
				<td><input type="text" name="userid" readonly="readonly" id="userid" value="${leaveBill.userid}" maxlength="20" placeholder="这里输入开票人员" title="开票人员"/></td>
			</tr>
			<tr>
			<td><label>处理结果：</label></td>
			<td>
			  <select name="outcome">
                                <option value="通过" selected="selected">通过</option>
                                <option value="退回">退回</option>
                                <option value="拒绝">拒绝</option>
                    </select>
			</td>
					
			</tr>
			<tr>
			<td><label>批注:</label></td>
			<td>
			<textarea name="comment" id="comment" title="批注"  placeholder="批注信息"></textarea>
			</td>
			</tr>
			<tr>
				<td style="text-align: center;" colspan="2">
					<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
					<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
				</td>
			</tr>
		</table>
		</div>
		<!-- 审批的信息 -->
		<div>
		<h4>显示开票申请的批注信息</h4>
		<table style="margin:auto;">
		
		<c:choose>
			<c:when test="${not empty commentList}">
			 <tr>
	        <!-- <td width="15%" height="20" bgcolor="d3eaef"><div align="center"><span>时间</span></div></td> -->
	        <th width="15%" height="20"><div align="center"><span>批注人</span></div></th>
	        <th width="70%" height="20"><div align="center"><span>批注信息</span></div></th>
 		</tr>
				<c:forEach items="${commentList}" var="comment">
							<tr>
								<%-- <td>${comment.time}</td> --%>
								<td>${comment.userId}</td>
								<td>${comment.fullMessage}</td>
							</tr>
						</br>
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
		<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"></h4></div>
		
	</form>
	
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<script type="text/javascript" src="static/js/chosen.jquery.min.js"></script><!-- 下拉框 -->
		
		<script type="text/javascript">
		$(function() {
			//单选框
			$(".chzn-select").chosen(); 
			$(".chzn-select-deselect").chosen({allow_single_deselect:true});
			//日期框
			$('.date-picker').datepicker();
		});
		</script>
	
</body>
</html>