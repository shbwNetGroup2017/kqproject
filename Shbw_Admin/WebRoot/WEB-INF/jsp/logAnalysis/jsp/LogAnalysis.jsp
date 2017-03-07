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
	<base href="<%=basePath%>"><!-- jsp文件头和头部 -->
	<%@ include file="../../system/admin/top.jsp"%> 
	
	<!--查看图片插件 -->
	<link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/zoomimage.css" />
    <link rel="stylesheet" media="screen" type="text/css" href="plugins/zoomimage/css/custom.css" />
    <script type="text/javascript" src="plugins/zoomimage/js/jquery.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/eye.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/utils.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/zoomimage.js"></script>
    <script type="text/javascript" src="plugins/zoomimage/js/layout.js"></script>
	<!--查看图片插件 -->
    <script src="static/js/jquery.form.js"></script>
	</head>
<body>
<div class="container-fluid" id="main-container">
<div id="page-content" class="clearfix">
  <div class="row-fluid">
	<div class="row-fluid">
			<%-- <!-- 检索  -->
			<form action="logAnalysis/uploadFile.do" method="post" name="Form"  id="Form" enctype="multipart/form-data">
			</form>
			<table>
				<tr>
					<td>
						<span class="input-icon">
							<input autocomplete="off" id="nav-search-input" type="text" name="keyword" value="" placeholder="这里输入关键词" />
							<i id="nav-search-icon" class="icon-search"></i>
						</span>
					</td>
					<td style="vertical-align:top;"><button class="btn btn-mini btn-light" onclick="search();"  title="检索"><i id="nav-search-icon" class="icon-search"></i></button></td>
					<c:if test="${QX.cha == 1 }">
					<td style="vertical-align:top;"><a class="btn btn-mini btn-light" onclick="toExcel();" title="导出到EXCEL"><i id="nav-search-icon" class="icon-download-alt"></i></a></td>
					</c:if>
				</tr>
			</table>
			<!-- 检索  --> --%>
			<form action="logAnalysis/uploadFile.do" method="post" name="Form1" target="logIframe" id="Form1" enctype="multipart/form-data">
				<div>
				   <span>信息检索：</span>
				   <input type="text" name="start" id="start" placeholder="开始的字符串"/>
				   <input type="text" name="start" id="start" placeholder="结束的字符串"/>
				</div>
				<div>
					<input type="file" name="upload" id="upload" />
					<input type="button" value="上传文件" onclick="uploadFile()"/>
				</div>

		   </form>
		   <iframe style="border: none;height: 100%;width: 100%;" id="logIframe" name="logIframe" src="about:blank">
		   		  
		   </iframe>

		</div>
</div>
</div>
</div>
		<!-- 引入 -->
		<script type="text/javascript">window.jQuery || document.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");</script>
		<script src="static/js/bootstrap.min.js"></script>
		<script src="static/js/ace-elements.min.js"></script>
		<script src="static/js/ace.min.js"></script>
		<!-- 引入 -->
<script type="text/javascript">
		 $(top.hangge()); 
		//检索
		function search(){
			top.jzts();
			$("#Form").submit();
		}
		function uploadFile(){
			$("#Form1").submit();
		}
		function uploadFile1(){
			$("#Form1").ajaxSubmit(function(data){
				alert(1);
			});
/* 			$('#Form1').ajaxSubmit({
		         target:'#qwer', //后台将把传递过来的值赋给该元素
		         url:'logAnalysis/uploadFile.do', //提交给哪个执行
		         type:'POST',
		         dataType:'json',
		         beforeSubmit:function(){alert('上传中....')},
		         success: function(msg){
		                 $("#qwer").html(msg.str);
		                 alert('成功');
		         } ,
		         error:function(){
		        	 alert('失败');
		         }
		   }); */
/* 			$("#Form1").submit(function(){
				alert(3);
				  var options = {
				         target:'#qwer', //后台将把传递过来的值赋给该元素
				         url:'logAnalysis/uploadFile.do', //提交给哪个执行
				         type:'POST',
				         dataType:'json',
				         success: function(msg){
				                 $("#qwer").html(msg.str);
				                 alert('成功');
				         } //显示操作提示
				   };
				   $('#Form1').ajaxSubmit(options);
				   //return false; //为了不刷新页面,返回false，反正都已经在后台执行完了，没事！
		  }); */
		}
</script>
		<style type="text/css">
		   li {list-style-type:none;}
		</style>
</body>
</html>