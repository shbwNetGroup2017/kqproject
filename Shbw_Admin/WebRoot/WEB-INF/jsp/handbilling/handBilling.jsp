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
<!-- jsp文件头和头部 -->
<%@ include file="../system/admin/top.jsp"%>
<style type="text/css">
.hrRes {
	margin: 0;
	padding: 0;
}

.customerDiv {
	width: 302px;
	display: none;
	position: absolute;
	z-index: 9999;
	background-color: #FFF;
	border: 1px solid #FF77FF;
	margin-top: -3.5px;
}

a:link {
	text-decoration: none;
}

.customer {
	
}

.customer:hover {
	background: #D9FFFF;
}

li {
	margin: 0;
	padding: 0;
}

ul {
	margin: 0;
	padding: 0;
}

.spgd {
	height: 25px;
}
</style>
</head>
<link rel="stylesheet" type="text/css" href="plugins/zTree/3.5/zTreeStyle.css">
<!-- <script type="text/javascript" src="static/js/jquery-1.11.1.min.js"></script>
 -->
<script type="text/javascript" src="static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>plugins/zTree/3.5/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript">
 var count=1;
 var zpzdje=0;
 var ppzdje=0;
</script>
<body>
	<div class="container-fluid" id="main-container">
		<div id="page-content" class="clearfix">
			<div class="row-fluid">
				<form id="sgkpForm" action="server/handbilling/addSgkp.do" method="post">
					手工开票
					<hr class="hrRes">
					<div style="width: 900px; margin: 0 auto;">
						<div style="text-align: left;">
							<tr>
								<td colspan="1" style="width: 100px;"><font color="red">*</font>发票类型&nbsp;:</td>
								<td colspan="1" align="left"><select name="fplx" id="fplx" onchange="fplxSelect()">
										<option value="007">增值税普通发票</option>
										<option value="004">增值税专用发票</option>
								</select></td>
								<%-- <td colspan="1" style="width: 100px;"><font color="red">*</font>开票单位名称&nbsp;:</td>
								<td colspan="1" align="left"><select name="kpdw" id="xfmc" ><!--  -->
										<option value="">请选择开票单位</option>
										<c:forEach items="${zzjgs}" var="var" varStatus="vs">
											<option value="${var.id}">${var.xfmc }</option>
										</c:forEach>
								</select></td> --%>
							</tr>
							<tr>
								<br>
								<!-- <td colspan="1" style="width: 100px;"><font color="red">*</font>开票点&nbsp;&nbsp;:</td>
								<td colspan="1" align="left"><select name="kpd" id="kpd">
										<option value="">请选择开票点</option>
								</select></td> -->
								<input name="count" type="hidden" value="1" id="count">
							</tr>
						</div>
					</div>
					<hr>
					<div style="width: 900px; margin-left: 25px">
						<table id="khxxTd" width="900px">
							<tr>
								<td rowspan="4" style="width: 10px;">客户&nbsp;&nbsp;</td>
								<td style="width: 120px">纳税人识别号:</td>
								<td colspan="1"><input readOnly="true" id="gfsh" name="gfsh" maxlength="20" style="width: 300px;"></td>
							</tr>
							<tr>
								<td><font color="red">*</font>名 &nbsp;&nbsp;&nbsp;称:</td>
								<td colspan="1">
									<!-- <div style="position: relative; margin-top: 5px;"> --> <%-- <span style="margin-left: 90px; width: 20px; overflow: hidden;"> <select style="width: 305px; margin-left: -90px;" id="customer" onchange="customerSelect()">
												<option value="">请选择</option>
												<c:forEach items="${khxxs}" var="var" varStatus="vs">
													<option value="${var.id}">${var.gfmc }</option>
												</c:forEach>
										</select>
										</span> --%> <input id="customer" onfocus="customerSearch()" onblur="gfmcTx()" oninput="customerSearch(event)" onpropertychange="customerSearch(event)" maxlength="20" style="width: 300px; height: 23px;"> <input type="hidden" id="customerId" name="customerId" /><input type="hidden"
									id="customerName" maxlength="100" name="gfmc" style="width: 260px; position: absolute; left: 0px;" placeholder="这里输入客户名称"><a class="btn btn-small btn-success " onclick="clearInput('gf')">填写</a>
									<div id="customerSearch" class="customerDiv" style=""></div>

								</td>
							</tr>
							<tr>
								<td>地址电话&nbsp;&nbsp;:</td>
								<td colspan="4"><input readOnly="true" id="gfdzdh" name="gfdzdh" onblur="gfdzdhInput()" maxlength="100" style="width: 300px;"><input type="hidden" id="gfdz" name="gfdz" maxlength="100" style="width: 100px;"><input type="hidden" id="gfdh" name="gfdh" maxlength="100"
									style="width: 100px;"></td>
							</tr>
							<tr>
								<td>开户行及账号:</td>
								<td colspan="4"><input readOnly="true" id="gfyhyhzh" name="gfyhyhzh" onblur="gfyhyhzhInput()" maxlength="100" style="width: 300px;"><input type="hidden" id="gfyh" name="gfyh" maxlength="100" style="width: 100px;"><input type="hidden" id="gfyhzh" name="gfyhzh"
									maxlength="100" style="width: 100px;"></td>
							</tr>

						</table>
					</div>
					<div style="width: 330px; height: 95px; background-color: #8FBCE8; margin-left: 600px; margin-top: -100px; border: 1px solid;">
						<p style="text-align: center; margin-top: 33px; font-size: 25px;">密码区</p>
					</div>
					<br> 商品信息
					<hr class="hrRes">
					<div id="tb" style="height: auto">
						<a href="javascript:void(0)" id="zh" onclick="addLine()">增行</a> <a href="javascript:void(0)" onclick="deleteLine()">删行</a><input type="radio" style="width: 15px; opacity: 1; position: relative;" name="hs" id="hs" onclick="taxFunc(0)" value="hs" checked />含税<input type="radio"
							style="width: 15px; opacity: 1; position: relative;" onclick="taxFunc(1)" name="hs" id="bhs" value="bhs" />不含税

					</div>
					<table id="dg" border="1" title="商品明细" style="width: 932px; margin-top: 10px; height: auto">
						<thead>
							<tr>
								<th>货物或应税劳务名称</th>
								<th>规格型号</th>
								<th>单位</th>
								<th>数量</th>
								<th>单价</th>
								<th>金额</th>
								<th>税率</th>
								<th>税额</th>
							</tr>
						</thead>
						<tr id="fristTr">
							<td style="width: 180px;"><input class="spgd" type="hidden" name="hwmc1" maxlength="100" id="hwmc1"> <select class="hrRes" name="spbm1" maxlength="100" id="spbm1" onchange="spmcSelect(1)">
									<option value="">请选择</option>
									<c:forEach items="${spbmList}" var="var" varStatus="vs">
										<option value="${var.spbm}">${var.yspmc }</option>
									</c:forEach>
							</select></td>
							<td style="width: 150px;"><input class="spgd" name="ggxh1" maxlength="30"></td>
							<td style="width: 80px;"><input class="spgd" name="dw1" maxlength="20" style="width: 80px;"></td>
							<td style="width: 80px;"><input class="spgd" name="sls1" id="sls1" maxlength="18" onblur="slsInput(1)" style="width: 80px;"></td>
							<td style="width: 80px;"><input class="spgd" name="dj1" id="dj1" maxlength="18" onblur="djInput(1)" style="width: 80px;"></td>
							<td style="width: 80px;"><input class="spgd" name="je1" id="je1" value="0.00" readOnly="true" style="width: 80px;"></td>
							<td style="width: 80px;"><select name="sl1" id="sl1" class="hrRes" onchange="slInput(1)">
									<option value="0.00">0.00</option>
									<option value="0.03">0.03</option>
									<option value="0.056">0.056</option>
									<option value="0.06">0.06</option>
									<option value="0.11">0.11</option>
									<option value="0.13">0.13</option>
									<option value="0.17">0.17</option>
							</select> <!-- <input name="sl1" id="sl1" maxlength="18" onblur="slInput(1)" style="width: 80px;"> --></td>
							<td style="width: 80px;"><input name="se1" id="se1" value="0.00" readOnly="true" style="width: 80px;"></td>
						</tr>
					</table>
					<br> 价税合计: <input id="jsTotal" name="jshj" readOnly="ture" value="0.00" style="width: 150px; background-color: #E0ECFF; border: 1px solid #99BBE8;"> &nbsp;&nbsp;金额合计: <input id="jeTotal" name="hjje" readOnly="ture" value="0.00"
						style="width: 130px; background-color: #E0ECFF; border: 1px solid #99BBE8;"> &nbsp;&nbsp;税额合计: <input id="seTotal" name="hjse" value="0.00" readOnly="ture" style="width: 150px; background-color: #E0ECFF; border: 1px solid #99BBE8;">
					<hr>
					<div style="width: 900px; margin-left: 25px">
						<table id="xfxxTd" width="900px">
							<tr>
							</tr>
							<tr>
								<td rowspan="4" style="width: 10px; margin-right: 100px;">销方&nbsp;&nbsp;</td>
								<td style="width: 100px"><font color="red">*</font>纳税人识别号:</td>
								<td colspan="1"><input readOnly="true" id="xfsh" name="xfsh" maxlength="20" style="width: 300px;"></td>
							</tr>
							<tr>
								<td><font color="red">*</font>名 &nbsp;&nbsp; &nbsp;称:</td>
								<td colspan="4">
									<div style="position: relative; margin-top: 5px;">
										<span style="margin-left: 90px; width: 20px; overflow: hidden;"> <select style="width: 305px; margin-left: -90px;" id="xfcustomer" onchange="xfxxSelect()">
												<option value="">请选择</option>
												<c:forEach items="${zzjgs}" var="var" varStatus="vs">
													<option value="${var.id}">${var.xfmc }</option>
												</c:forEach>
										</select>
										</span><input type="hidden" id="xfcustomerId" name="xfcustomerId" /><input readOnly="true" type="text" id="xfcustomerName" name="xfmc" maxlength="100" style="width: 260px; position: absolute; left: 0px;" placeholder="这里输入销方名称">
										<!-- <a class="btn btn-small btn-success " onclick="clearInput('xf')">新增</a> -->
									</div>
								</td>
							</tr>
							<tr>
								<td><font color="red">*</font>地址电话&nbsp;&nbsp;:</td>
								<td colspan="4"><input readOnly="true" id="xfdzdh" maxlength="100" name="xfdzdh" style="width: 300px;"><input id="xfdz" type="hidden" maxlength="100" name="xfdz" style="width: 100px;"><input type="hidden" id="xfdh" maxlength="100" name="xfdh" style="width: 100px;"></td>
							</tr>
							<tr>
								<td><font color="red">*</font>开户行及账号:</td>
								<td colspan="4"><input readOnly="true" id="xfyhyhzh" maxlength="100" name="xfyhyhzh" style="width: 300px;"><input type="hidden" id="xfyh" maxlength="100" name="xfyh" style="width: 100px;"><input type="hidden" id="xfyhzh" maxlength="100" name="xfyhzh"
									style="width: 100px;"></td>
							</tr>
						</table>
						<div style="width: 10px; margin-left: 555px; margin-top: -90px;">备注</div>
						<textarea id="bz" name="xfbz" maxlength="100" style="width: 330px; height: 87px; font-size: 14px; margin-left: 575px; margin-top: -51px;"></textarea>
					</div>
					<hr class="hrRes">
					<div style="margin-left: 15px; margin-top: 15px; float: left;">
						收款人:<input id="skr" name="skr" style="width: 150px;" />&nbsp;&nbsp;最大开票金额:<input id="zdje" readOnly="true" name="zdje" style="width: 150px;" />&nbsp;&nbsp; 复核人&nbsp;&nbsp;:<input readOnly="true" name="fhr" id="fhr" style="width: 150px;" /> &nbsp;&nbsp;开票人:<input readOnly="true" name="kpr"
							id="kpr" style="width: 140px;" />
					</div>
					<br> <br> <br>
					<div style="margin-left: 415px;">
						<a class="btn btn-mini btn-primary" onclick="save();">申请开票</a>&nbsp;&nbsp;&nbsp;&nbsp; <a class="btn btn-mini btn-danger" onclick="closeTab(this);">返回</a>
					</div>
			</div>
			</form>
		</div>
	</div>
	</div>
	<!-- 引入 -->
	<script type="text/javascript">
		window.jQuery
				|| document
						.write("<script src='static/js/jquery-1.9.1.min.js'>\x3C/script>");
	</script>
	<script src="static/js/bootstrap.min.js"></script>
	<script src="static/js/ace-elements.min.js"></script>
	<script src="static/js/ace.min.js"></script>
	<!-- 引入 -->
	<script type="text/javascript">

		$(top.hangge());
		function gfdzdhInput(){
			var gfdzdh=$('#gfdzdh').val();
			if(gfdzdh!=""&&(gfdzdh.indexOf('/')<0||gfdzdh.indexOf('/')==gfdzdh.length-1)){
				 if(gfdzdh!=""){
					$("#gfdzdh").tips({
						side:3,
			            msg:'电话和地址之间请用"/"连接!',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#gfdzdh").focus();
					return false;
				} }
			var strs= new Array();
			strs=gfdzdh.split('/');
			$("#gfdz").val(strs[0]);
			$("#gfdh").val(strs[1]);			
		}
		function gfyhyhzhInput(){
			var gfyhyhzh=$('#gfyhyhzh').val();
			if(gfyhyhzh!=""&&(gfyhyhzh.indexOf('/')<0||gfyhyhzh.indexOf('/')==gfyhyhzh.length-1)){
				 if(gfyhyhzh!=""){
					$("#gfyhyhzh").tips({
						side:3,
			            msg:'银行和银行账号之间请用"/"连接!',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#gfyhyhzh").focus();
					return false;
				} }
			var strs= new Array();
			strs=gfyhyhzh.split('/');
			$("#gfyh").val(strs[0]);
			$("#gfyhzh").val(strs[1]);
		}
		function closeTab (a) {
			    var li_id = $(a).parent().parent().attr('id');
			    var id = li_id.replace("tab_li_","");
			     
			     //如果关闭的是当前激活的TAB，激活他的前一个TAB
			     if ($("li.active").attr('id') == li_id) {
			         $("li.active").prev().find("a").click();
			    }
			     
			     //关闭TAB
			     $("#" + li_id).remove();
			     $("#tab_content_" + id).remove();
			 };
		//选择商品是否含税
		function taxFunc(type){
				var jeTotal=0;
				var seTotal=0;
				for(var i=1;i<=count;i++){
					var sls=$("#sls"+i).val().replace(/\s+/g,"");
					var dj=$("#dj"+i).val().replace(/\s+/g,"");
					var sl=$("#sl"+i).val().replace(/\s+/g,"");
					if((sls!=''&&sls!=null)&&(dj!=''&&dj!=null)&&(sl!=''&&sl!=null)){
						var je=0;
						if(type==0){//含税行内金额计算
							var djFinal=(dj/(1+Number(sl))).toFixed(6);
							je=sls*djFinal; 
						}else if(type==1){
							je=sls*dj
						}
						var se=je*sl;
						jeTotal=jeTotal+je;
						seTotal=seTotal+se;
						$("#je"+i).val(je.toFixed(2));
						$("#se"+i).val(se.toFixed(2));
					}
				}
				$("#jeTotal").val(jeTotal.toFixed(2));
				$("#seTotal").val(seTotal.toFixed(2));
				$("#jsTotal").val((jeTotal+seTotal).toFixed(2));
		}
		function djInput(i){		
			var dj=$("#dj"+i).val().replace(/\s+/g,"");
			var type=/^0{1}([.]\d{1,2})?$|^[1-9]\d*([.]{1}[0-9]{1,2})?$/;//两位小数/^0{1}([.]\d{1,2})?$|^[1-9]\d*([.]{1}[0-9]{1,2})?$/;
			if(type.test(dj)){
				var sls=$("#sls"+i).val().replace(/\s+/g,"");
				if(sls!=null&&sls!=''){
					var je=0;
					//行内金额保留2位小数
					//获取含税标志
					var hs = $('input[name="hs"]:checked ').val();					
					//不含税金额赋值
					if(hs=='bhs'){
						je=sls*dj;
						$("#je"+i).val(je.toFixed(2));
					}					
					//税额修改
					var sl=$("#sl"+i).val();					
					if(sl!=null&&sl!=''){
						if(hs=='hs'){//含税行内金额赋值
							var djFinal=(dj/(1+Number(sl))).toFixed(6);
							je=sls*djFinal;
							$("#je"+i).val(je.toFixed(2));
						}
						var seNew=(je*sl).toFixed(2);
						$("#se"+i).val(seNew);
						//总税额赋值
						var seTotal=0;
						for(var j=1;j<=count;j++){
							seTotal=seTotal+Number($("#se"+j).val());
						}
						$("#seTotal").val(seTotal.toFixed(2));
					}
					//总金额赋值
					var jeTotal=0;
					for(var j=1;j<=count;j++){
						jeTotal=jeTotal+Number($("#je"+j).val());
					}
					$("#jeTotal").val(jeTotal.toFixed(2));				
					//价税合计赋值
					var jsTotal=Number($("#seTotal").val())+Number($("#jeTotal").val());
					$("#jsTotal").val(jsTotal.toFixed(2));
				}
			}else{
				var spdj="#dj"+i;
				$(spdj).tips({
					side:3,
		            msg:'请输入正确的单价，单价可以保留两位小数!',
		            bg:'#AE81FF',
		            time:2
		        });
				$(spdj).focus();	
			}           
		}
		function slsInput(i){
			var sls=$("#sls"+i).val().replace(/\s+/g,"");
			var type=/^0{1}([.]\d{1,6})?$|^[1-9]\d*([.]{1}[0-9]{1,6})?$/;
			if(type.test(sls)){
				var dj=$("#dj"+i).val().replace(/\s+/g,"");
				if(dj!=null&&dj!=''){
					var je=0;
					//行内金额保留2位小数
					//获取含税标志
					var hs = $('input[name="hs"]:checked ').val();					
					//不含税金额赋值
					if(hs=='bhs'){
						je=sls*dj;
						$("#je"+i).val(je.toFixed(2));
					}
					//税额修改
					var sl=$("#sl"+i).val();					
					if(sl!=null&&sl!=''){
						if(hs=='hs'){//含税行内金额赋值
							var djFinal=(dj/(1+Number(sl))).toFixed(6);
							je=sls*djFinal;
							$("#je"+i).val(je.toFixed(2));
						}
						var seNew=(je*sl).toFixed(2);
						$("#se"+i).val(seNew);
						
						//总税额赋值
						var seTotal=0;
						for(var j=1;j<=count;j++){
							seTotal=seTotal+Number($("#se"+j).val());
						}
						$("#seTotal").val(seTotal.toFixed(2));
					}
					//总金额赋值
					var jeTotal=0;
					for(var j=1;j<=count;j++){
						jeTotal=jeTotal+Number($("#je"+j).val());
					}
					$("#jeTotal").val(jeTotal.toFixed(2));
					
					//价税合计赋值
					var jsTotal=Number($("#seTotal").val())+Number($("#jeTotal").val());
					$("#jsTotal").val(jsTotal.toFixed(2));
				}
			}else{
				var spsl="#sls"+i;
				$(spsl).tips({
					side:3,
		            msg:'请输入正确的数量，数量可以保留六位小数!',
		            bg:'#AE81FF',
		            time:2
		        });
				$(spsl).focus();				
			}
		}
		function slInput(i){
			var sl=$("#sl"+i).val();
			var type=/^[+-]?\d*\.?\d{1,3}$/;
			if(type.test(sl)){
				//var je=$("#je"+i).val();
				var sls=$("#sls"+i).val().replace(/\s+/g,"");
				var dj=$("#dj"+i).val().replace(/\s+/g,"");
				if((sls!=null&&sls!='')&&(dj!=null&&dj!='')){
					//获取含税标志
					var hs = $('input[name="hs"]:checked ').val();
					//税额保留两位小数
					var je=0;
					//不含税行内金额,税额赋值
					if(hs=='bhs'){
						je=sls*dj;
						$("#je"+i).val(je.toFixed(2));						
					}else if(hs=='hs'){//含税行内金额赋值
						var djFinal=(dj/(1+Number(sl))).toFixed(6);
						je=sls*djFinal;
						$("#je"+i).val(je.toFixed(2));
					}	
					$("#se"+i).val((je*sl).toFixed(2));//行内税额赋值
					//总金额赋值
					var jeTotal=0;
					for(var j=1;j<=count;j++){
						jeTotal=jeTotal+Number($("#je"+j).val());
					}
					$("#jeTotal").val(jeTotal.toFixed(2));	
					//总税额赋值
					var seTotal=0;
					for(var j=1;j<=count;j++){
						seTotal=seTotal+Number($("#se"+j).val());
					}
					$("#seTotal").val(seTotal.toFixed(2));					
					//价税合计赋值
					var jsTotal=Number($("#seTotal").val())+Number($("#jeTotal").val());
					$("#jsTotal").val(jsTotal.toFixed(2));
				}
			}else{				
				$(spsl).focus();
				alert('请输入正确的税率，税率可以保留六位小数!');
				$("#sl"+i)[0].focus();
			}
		}
		//增加一行输入商品信息
		function addLine() {
			var spbm=$("#spbm"+count).val();
			var sls=$("#sls"+count).val().replace(/\s+/g,"");
			var dj=$("#dj"+count).val().replace(/\s+/g,"");
			var sl=$("#sl"+count).val();
			if(spbm==null||spbm==''){
				alert('请输入货物名称或应税劳务名称再新增行!');
				return;
			}
			if(sls==null||sls==''){
				alert('请输入数量再新增行!');
				return;
			}
			if(dj==null||dj==''){
				alert('请输入单价再新增行');
				return;
			}
			if(sl==null||sl==''){
				alert('请输入税率再新增行');
				return;
			}
			count++;
			//
			
			var line=
			'<tr>'+
			'<td style="width: 180px;"><input  class="spgd" type="hidden" name="hwmc'+count+'" id="hwmc'+count+'" >'+
			'<select class="hrRes" name="spbm'+count+'" id="spbm'+count+'" onchange="spmcSelect('+count+')" >'+
			'<option value="">请选择</option>'+
			'<c:forEach items="${spbmList}" var="var" varStatus="vs">'+
				'<option value="${var.spbm}">${var.yspmc }</option>'+
			'</c:forEach>'+
			'</td>'+
			'<td style="width: 150px;"><input maxlength="30"  class="spgd" name="ggxh'+count+'"></td>'+
			'<td style="width: 80px;"><input  maxlength="20"  class="spgd" name="dw'+count+'" style="width: 80px;"></td>'+
			'<td style="width: 80px;"><input  maxlength="18"  class="spgd" name="sls'+count+'" id="sls'+count+'" onblur="slsInput('+count+')" style="width: 80px;"></td>'+
			'<td style="width: 80px;"><input maxlength="18"   class="spgd" name="dj'+count+'" id="dj'+count+'" onblur="djInput('+count+')" style="width: 80px;"></td>'+
			'<td style="width: 80px;"><input class="spgd" name="je'+count+'" id="je'+count+'" readOnly="true" style="width: 80px;"></td>'+
			'<td style="width: 80px;"><select  class="hrRes" name="sl'+count+'" id="sl'+count+'" onchange="slInput('+count+')" > <option value="0.00">0.00</option> <option value="0.03">0.03</option> <option value="0.056">0.056</option> <option value="0.06">0.06</option> <option value="0.11">0.11</option> <option value="0.13">0.13</option> <option value="0.17">0.17</option> </select></td>'+
			'<td style="width: 80px;"><input class="spgd" name="se'+count+'" id="se'+count+'" readOnly="true" style="width: 80px;"></td>'+
			'</tr>';
			$("#dg").append(line);
			$("#count").val(count);
		}
		//删除一行商品信息
		function deleteLine() {
			var tb = document.getElementById("dg");
			var lines = tb.rows.length
			if (lines <= 2) {
				return;
			} else {
				tb.deleteRow(lines - 1);
				count--;
				var seTotal=0;
				var jeTotal=0;
				for(var j=1;j<=count;j++){
					seTotal=seTotal+Number($("#se"+j).val());
					jeTotal=jeTotal+Number($("#je"+j).val());
				}
				$("#seTotal").val(seTotal.toFixed(2));
				$("#jeTotal").val(jeTotal.toFixed(2));	
				//价税合计赋值
				var jsTotal=Number($("#seTotal").val())+Number($("#jeTotal").val());
				$("#jsTotal").val(jsTotal.toFixed(2));
			}
			$("#count").val(count);
		}
		// 手动输入客户信息时清除之前的赋值
		function clearInput(type){
			if(type=='gf'){
				$("#khxxTd input").val('');
				$("#khxxTd input").removeAttr('readOnly');
				$("#gfsh")[0].focus();
			}else if(type=='xf'){
				$("#xfxxTd input").val('');
				$("#xfxxTd input").removeAttr('readOnly');
				$("#xfsh")[0].focus();
			}
		}
		//当选择了下拉框中的客户时自动给其他区域赋值
		function customerSelect(id){	
			
			var checkValue=id;
			if(checkValue==null||checkValue==''){
				return;
			}
			$("#customerSearch").hide();
			$("#customerId").val(checkValue);
			$.ajax({
				type: "POST",
				url: '<%=basePath%>server/handbilling/getCustomerInfoById.do',
				data : {
					id : checkValue
				},
				dataType : 'json',
				//beforeSend: validateData,
				cache : false,
				success : function(data) {
					var customerInfo=data.customerInfo;
					$("#gfsh").val(customerInfo.gfsh);
					$("#gfdzdh").val(customerInfo.gfdz+"/"+customerInfo.gfdh);
					$("#gfdz").val(customerInfo.gfdz);	
					$("#gfdh").val(customerInfo.gfdh);	
					$("#gfyhyhzh").val(customerInfo.gfyh+"/"+customerInfo.gfyhzh);
					$("#gfyh").val(customerInfo.gfyh);
					$("#gfyhzh").val(customerInfo.gfyhzh);
					$("#customerName").val(customerInfo.gfmc);
					$("#customer").val(customerInfo.gfmc);
					$("#khxxTd input").attr('readOnly','true');
				}
			});
		}
		//当选择了下拉框中的销方时自动给其他区域赋值
		function xfxxSelect(){
			var checkText=$("#xfcustomer").find("option:selected").text();
			var checkValue=$("#xfcustomer").val();
			if(checkValue==null||checkValue==''){
				return;
			}
			$("#xfcustomerName").val(checkText);
			$("#xfcustomerId").val(checkValue);
			$.ajax({
				type: "POST",
				url: '<%=basePath%>server/handbilling/getXfxxById.do',
				data : {
					id : checkValue
				},
				dataType : 'json',
				//beforeSend: validateData,
				cache : false,
				success : function(data) {
					var customerInfo=data.xfxx;
					$("#xfsh").val(customerInfo.xfsh);
					$("#xfdzdh").val(customerInfo.xfdz+"/"+customerInfo.xfdh);
					$("#xfdz").val(customerInfo.xfdz);
					$("#xfdh").val(customerInfo.xfdh);
					$("#xfyhyhzh").val(customerInfo.xfyh+"/"+customerInfo.xfyhzh);
					$("#xfyh").val(customerInfo.xfyh);
					$("#xfyhzh").val(customerInfo.xfyhzh);
					$("#xfxxTd input").attr('readOnly','true');
					var fplx=$("#fplx").val();
					zpzdje=customerInfo.zpzdje;
					ppzdje=customerInfo.ppzdje;
					if(fplx=="004"){
						$("#zdje").val(zpzdje);
					}else if(fplx=="007"){
						$("#zdje").val(ppzdje);
					}
				}
			});
		}
		//选择开票单位时自动获取开票点信息数据
		function xfmcSelect() {
			var objS = document.getElementById("xfmc");
			var zzjgId = objS.options[objS.selectedIndex].value;
			if(zzjgId==null||zzjgId==''){
				return
			}
			$.ajax({
				type: "POST",
				url: '<%=basePath%>server/handbilling/getKpds.do',
				data : {
					zzjgId : zzjgId
				},
				dataType : 'json',
				//beforeSend: validateData,
				cache : false,
				success : function(data) {
					$.each(data.kpdList,function(i,a){
						 $("#kpd").append("<option value="+a.id+">"+a.xfmc+"</option>");
					 });
				}
			});
		}
		function save(){
			if($("#fplx").val()==""){
				$("#fplx").tips({
					side:3,
		            msg:'发票类型必须选择!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#fplx").focus();
				return false;
			}
			if($("#xfmc").val()==""){
				$("#xfmc").tips({
					side:3,
		            msg:'开票单位必须选择!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#xfmc").focus();
				return false;
			}
			/* if($("#kpd").val()==""){
				$("#kpd").tips({
					side:3,
		            msg:'开票点必须选择!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#kpd").focus();
				return false;
			} */
			///////客户信息校验begin
			if($("#customer").val()==""){
				$("#customer").tips({
					side:3,
		            msg:'客户名称不能为空!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#customer").focus();
				return false;
			}
			//发票为专票时客户税号银行电话不能为空
			if($("#fplx").val()=="004"){
				if($("#gfsh").val()==""){
					$("#gfsh").tips({
						side:3,
			            msg:'专票客户税号不能为空!',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#gfsh").focus();
					return false;
				}
				if($("#gfdzdh").val()==""){
					$("#gfdzdh").tips({
						side:3,
			            msg:'专票客户地址电话不能为空!',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#gfdzdh").focus();
					return false;
				}			
				if($("#gfyhyhzh").val()==""){
					$("#gfyhyhzh").tips({
						side:3,
			            msg:'专票客户银行和银行账号不能为空!',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#gfyhyhzh").focus();
					return false;
				}
				
				var gfdzdh=$("#gfdzdh").val();
				if(gfdzdh!=""&&(gfdzdh.indexOf('/')<0||gfdzdh.indexOf('/')==gfdzdh.length-1)){
				 if(gfdzdh!=""){
					$("#gfdzdh").tips({
						side:3,
			            msg:'电话和地址之间请用"/"连接!',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#gfdzdh").focus();
					return false;
				} }
				var gfyhyhzh=$("#gfyhyhzh").val();
				if(gfyhyhzh!=""&&(gfyhyhzh.indexOf('/')<0||gfyhyhzh.indexOf('/')==gfyhyhzh.length-1)){
				   if(gfyhyhzh!=""){
					$("#gfyhyhzh").tips({
						side:3,
			            msg:'客户银行和账号之间请用"/"连接!',
			            bg:'#AE81FF',
			            time:2
			        });
					$("#gfyhyhzh").focus();
					return false;
				} }
				
			}
			
		///////客户信息校验End
		//商品信息校验begin
			var spbm="#spbm"+count;
			if($(spbm).val()==""){
				$(spbm).tips({
					side:3,
		            msg:'货物或应税劳务名称不能为空!',
		            bg:'#AE81FF',
		            time:2
		        });
				$(spbm).focus();
				return false;
			}
			var sls="#sls"+count;
			if($(sls).val()==""){
				$(sls).tips({
					side:3,
		            msg:'商品数量名称不能为空!',
		            bg:'#AE81FF',
		            time:2
		        });
				$(sls).focus();
				return false;
			}
			var dj="#dj"+count;
			if($(dj).val()==""){
				$(dj).tips({
					side:3,
		            msg:'商品单价不能为空!',
		            bg:'#AE81FF',
		            time:2
		        });
				$(spbm).focus();
				return false;
			}
			var sl="#sl"+count;
			if($(sl).val()==""){
				$(sl).tips({
					side:3,
		            msg:'商品税率不能为空!',
		            bg:'#AE81FF',
		            time:2
		        });
				$(sl).focus();
				return false;
			}
			//商品信息校验End
			//销方信息校验begin
			//   
			 if($("#xfsh").val()==""){
				$("#xfsh").tips({
					side:3,
		            msg:'销方税号不能为空!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#xfsh").focus();
				return false;
			}
			if($("#xfcustomerName").val()==""){
				$("#xfcustomerName").tips({
					side:3,
		            msg:'销方名称不能为空!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#xfcustomerName").focus();
				return false;
			}
			if($("#xfdzdh").val()==""){
				$("#xfdzdh").tips({
					side:3,
		            msg:'销方地址和电话不能为空!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#xfdzdh").focus();
				return false;
			}
			if($("#xfdzdh").val()==""){
				$("#xfdzdh").tips({
					side:3,
		            msg:'销方地址和电话不能为空!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#xfdzdh").focus();
				return false;
			}
			if($("#xfyhyhzh").val()==""){
				$("#xfyhyhzh").tips({
					side:3,
		            msg:'销方银行和银行账号不能为空!',
		            bg:'#AE81FF',
		            time:2
		        });
				$("#xfyhyhzh").focus();
				return false;
			}
			//销方信息校验End
			//最大开票金额校验begin
			var jshj=Number($("#jsTotal").val());
			var zdje=Number($("#zdje").val());
			if(jshj>zdje){
				alert("价税合计不能大于最大开票金额!");
				return;
			}
			//最大开票金额校验end
		var num=$("#count").val();
			//提交表单
		$.ajax({
			type: "POST",
			url: '<%=basePath%>server/handbilling/addSgkp.do',
	    	data: $("#sgkpForm").serialize(),
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.msg){
					alert('手工开票新增成功!');	
					setTimeout("self.location=self.location", 100);
					
					
				 }else{
					 alert(data.checkResult.cwmx+',手工开票失败,请检查数据!');
				 }
			}
		});
		}
	function customerSearch(){
		if($("#customer").attr('readOnly')){
			return;
		}
		var gfmc=$("#customer").val();
		$.ajax({
			type: "POST",
			url: '<%=basePath%>server/handbilling/listCustomers.do',
	    	data: {
	    		gfmc:gfmc
	    	},
			dataType:'json',
			cache: false,
			success: function(data){
				 if("success" == data.msg){
					// var html="";
					 /* $.each(data.data,function(i,a){
					    html=html+"<a href='javascript:void(0)'  onclick='customerSelect("+a.id+")' ><div class='customer'><li>"+a.gfmc+"</li></div></a>";
					 }); */
					 //alert(html);
					 $("#customerSearch").css("display","block")
					 $("#customerSearch").html(data.data);
					 //$("#customerSearch").show();					
				 }else{						
					 $("#customerSearch").html('');
				 }
			}
		});		
		//<a  href="javascript:void(0)" onclick="customerSelect()"><div class="customer"><li>阿里巴巴</li><input id="gfids" type="text"></div></a>	
	}
	function spmcSelect(i){
		var spbm="#spbm"+i;
		var hwmc="#hwmc"+i;
		var checkText=$(spbm).find("option:selected").text();
		$(hwmc).val(checkText);
		
	}
	function gfmcTx(){
		var gfmc=$("#customer").val();
		$("#customerName").val(gfmc);
	}
	function fplxSelect(){
		 var fplx=$("#fplx").val();
		 if(fplx=="004"){
			$("#zdje").val(zpzdje);
		 }else if(fplx=="007"){
			$("#zdje").val(ppzdje);
		 }
	}
	$(function(){
		$("body").click(function(e){
			var eId=$(e.target).attr("id");
			if(eId=='customer'){
				return;
			}
			$("#customerSearch").hide();			
		});
		count=1;
	})
	</script>
	<style type="text/css">
li {
	list-style-type: none;
}
</style>

</body>
</html>