<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="s" uri="/s"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> 
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>Excel数据转换XML</title>
<style type="text/css">
  input{border-radius: 5px;background-color: blue;border:none;color:#FFF;}
  .div{height: 200px;display: none;position:absolute;
 left:338px;
 top:91px;
 width:446px;
 height:294px;
 z-index:1;
 border:solid #7A7A7A 4px; }
</style>
<script type="text/javascript">
   //用户下载xml文件
   function scXml(){
	   var error="${errorStr}";
	   if(error!=""){
		   alert('文件检查失败，请先修改,再上传！');
	   }else
	      window.location.href="<%=basePath%>xml/xmlDl.action";
	   
   }
   
/*     //上传文件类型的验证
   function vlicet(name){
	   var extStart=name.lastIndexOf(".");
	   var ext=name.substring(extStart,name.length).toUpperCase();
	   if(ext!=".XLS"&&ext!=".XLSX"){
	     alert("上传文件必须是excel文件！"); //检测允许的上传文件类型
	     //document.getElementById("upex").disabled="true";
	   }
    }*/
   
   //点击上传按钮
   function wjsc(){
	   if(document.getElementById("excelFile").value==""){
		   alert('请先点浏览，选择上传您要文件')
	   }else{
		   document.getElementById("wjscform").submit();
	   }
   }

</script>
</head>
<body>
	<form id="wjscform" action="<%=basePath %>xml/exceltoxml.action"
		encType="multipart/form-data" method="post" >
		<div style="text-align: center; margin-top: 5%;" >
			<span style="font-size: 17px;color:rgb(49, 97, 236);float:left;margin-left:250px;">上传excel数据：</span>
<!-- 			<input type="text" id="excelFile1"
				style="border: 1px solid blue; background-color:#FFF; height: 30px;width: 230px;color:black;float:left;" /> -->
			<input type="file" name="excelFile" id="excelFile" style="display:block;float:left;" />
           <!--  <input type="button" id="upex1" value="浏览" style="margin-left: 1%;width:80px;height: 30px;font-size: 17px;"/> -->
			<input type="button" id="upex" value="上传" style="margin-left: -30%;width:80px;height: 25px;font-size: 17px;" onclick="wjsc()"/>
		</div>
 	</form>
		<div style="margin-top:20px;">文件内容：
		     <p style="marigin-left:-50%;color:red;font-weight: bold;" id="errorMsg">${errorStr}
		     </p>
		</div>
		<!-- 文件内容 -->	
		<div id="node_div" style="width: 100%;">
				<table class="table" width="100%" layoutH="170">
				<thead>
				<s:iterator value="#request.schlList" var="excel" status="index">
		        <s:if test="#index.first == true">
				<tr style="font-size: 13px;text-align: center;background-color:rgb(246, 120, 120);">
					<td>行号</td>
					<td>单据号</td>
					<td>购方名称</td>
					<td>购方税号</td>
					<td>购方地址电话</td>
					<td>购方银行帐号</td>
					<td>价税合计</td>
					<td>金额</td>
					<td>税额</td>
					<td style="width:50px;">明细</td>
				</tr>
				</thead>
				</s:if>
				 <s:if test="#index.odd == true">
					<tr style="font-size: 13px; background-color:#dfdfdf;" id="qwer">
					    <td><s:property value='#index.index+1'></s:property></td>
						<td><s:property value='#excel.DJH'></s:property></td>
						<td><s:property value='#excel.GFMC'></s:property></td>
						<td><s:property value='#excel.GFSBH'></s:property></td>
						<td><s:property value='#excel.GFDZDH'></s:property></td>
						<td><s:property value='#excel.GFYHZH'></s:property></td>
						<td><s:property value='#excel.ZJE'></s:property></td>
						<td><s:property value='#excel.JE_BHS'></s:property></td>
						<td><s:property value='#excel.SE'></s:property></td>
						<td>共<a href="xml/FPDetails.action?ZJE=<s:property value='#excel.ZJE'></s:property>&JE_BHS=<s:property value='#excel.JE_BHS'></s:property>
						&SE=<s:property value='#excel.SE'></s:property>&
						EXCELFILEBS=<s:property value='#excel.EXCELFILEBS'></s:property>&DJH=<s:property value='#excel.DJH'></s:property>">
						<s:property value='#excel.MXTS'></s:property>行</a></td>
					</tr>
					</s:if>
					<s:if test="#index.even == true">
					  <tr style="font-size: 13px; background-color:rgb(173, 173, 183);">
					    <td><s:property value='#index.index+1'></s:property></td>
						<td><s:property value='#excel.DJH'></s:property></td>
						<td><s:property value='#excel.GFMC'></s:property></td>
						<td><s:property value='#excel.GFSBH'></s:property></td>
						<td><s:property value='#excel.GFDZDH'></s:property></td>
						<td><s:property value='#excel.GFYHZH'></s:property></td>
						<td><s:property value='#excel.ZJE'></s:property></td>
						<td><s:property value='#excel.JE_BHS'></s:property></td>
						<td><s:property value='#excel.SE'></s:property></td>
						<td>共<a href="xml/FPDetails.action?ZJE=<s:property value='#excel.ZJE'></s:property>&JE_BHS=<s:property value='#excel.JE_BHS'></s:property>
						&SE=<s:property value='#excel.SE'></s:property>&
						EXCELFILEBS=<s:property value='#excel.EXCELFILEBS'></s:property>&DJH=<s:property value='#excel.DJH'></s:property>">
						<s:property value='#excel.MXTS'></s:property>行</a></td>
					</tr>
					</s:if>
				</s:iterator>
			</table>
		</div>
		<s:if test="#request.schlList.size()!=0">
		<div style="text-align: right;margin-top:10px;height: 20px;">
		   <input type="button" value="生成开票文件" onclick="scXml()" style="height: 40px;">
		</div>
		</s:if>
		<div style="text-align: right;margin-top:25px;height: 20px;">
		   <!-- <a href="/kpjk/xml/zyDl.action?fileName=csData">测试数据的下载</a> -->
		   <a style="margin-left:30px;" id="ExcelMouble" href="/kpjk/xml/zyDl.action?fileName=csMB">百旺Excel模板下载</a>
		   <a style="margin-left:30px;" id="ExcelMouble" href="/kpjk/xml/zyDl.action?fileName=czSC">操作手册</a>
		</div>
		<div style="font-size:13px;color:rgb(153, 145, 145);margin-top:20px;">
		<p style="line-height:7px">操作步骤：</p>
        <p style="line-height:7px">1.&nbsp;第一步，下载“百旺excel模板下载”文件，按照文件内容填写。表单中，“单据号”是您自己编写的，</p>
        <p style="line-height:7px">&nbsp;&nbsp;&nbsp;&nbsp;把想在一张票上开的行，取同一个单据号。一个excel可以有多张票。</p>
        <p style="line-height:7px">2.&nbsp;第二步，通过页面上的“浏览”按钮找到您填写好的文件，然后点“上传”。系统会检查您的数据，比如必</P>
        <p style="line-height:7px">&nbsp;&nbsp;&nbsp;&nbsp;填项是否都填写了等，系统自动做价税分离。如果通过检查，页面会显示您文件里有几张票。如果没有</p>
        <p style="line-height:7px">&nbsp;&nbsp;&nbsp;&nbsp;通过检查，需要您先修改excel文件，再点浏览、上传。</p>
        <p style="line-height:7px">3.&nbsp;第三步，您检查过数据后，认为数据没有问题，点“生成开票文件”，这个文件会下载到您的电脑。然后，</p>
        <p style="line-height:7px">&nbsp;&nbsp;&nbsp;&nbsp;请在开票软件中导入这个文件，完成开票。</p>
		<p style="line-height:7px">&nbsp;&nbsp;&nbsp;&nbsp;详情请参考操作手册。 您有任何问题或建议，请通过该系统的“问题反馈”或“在线答疑”提出。（项目开发中）</p>
		</div>
	
</body>

</html>