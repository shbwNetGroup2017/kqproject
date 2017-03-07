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
<base href="<%=basePath%>">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<title>注册页面</title>
<style type="text/css">
     .Img{width:width:70px;height: 55px;float: left;margin-left:180px;}
     .logoText{color:blue;font-size: 30px;margin-left:180px;padding-top:10px;}
     .zhdl{color:#C9BBBB;font-size: 15px;margin-top: -55px;margin-left: 970px;}
     .dlBtn{width:80px;height: 30px;background-color: blue;color: #FFF;text-align:center;
	       font-size: 17px;border-radius:5px;padding: 5px;margin-top: -50px;margin-left: 1100px;cursor:pointer;}
	 .dl{float: left;}
	 .logoDiv{background-color: #FBF7F7;height: 5px;width: 1000px;margin-left:180px;}
	 .zh{float: left;height: 42px;width: 75px;margin-right: 10px;line-height: 42px;font-size: 16px;
	     color: #666;font-weight: bold;text-align: right;}
	 .textZw{margin-top:80px;}
	 .text{margin-left:200px;margin-top:30px;}
	 .userInput{margin-top:0px;border:1px solid #dfdfdf;width:270px;height: 40px;border-radius:5px;}
	 .zhuce{width:25%;height: 30px;background-color: blue;color: #FFF;text-align:center;
	       font-size: 17px;border-radius:5px;padding: 10px;margin-top: 40px;margin-left: 200px;cursor:pointer; }
	 .bottom{font-size: 11px;color:blue;text-align: center;margin-top:50px;}
	 .textImg{margin-top:-300px;margin-left:700px;}
	 .errorMsg{color:red;font-size: 13px;margin-left:150px;}
</style>
</head>
<body>
    <div id="main">
         <div id="header">
            <div id="logoImg">
               <img src="images/logo.png" class="Img"/><p class="logoText">上海百旺金赋科技有限公司</p>
               <p class="zhdl">已有账号，现在就<p class="dlBtn" onclick="dlJM()">登录</p></p>
            </div>
            <div id="logoDiv" class="logoDiv"></div>
         </div>
         
         <div id="text" class="textZw">
         	<form action="<%=basePath %>xml/zhuce.action" method="post" id="form1">
             <div class="text">
                <div class="zh">用户账号</div>
                <div ><input type="text" name="userName" id="userName" class="userInput" onfocus="qwer('userName')" onblur="qwer1('userName')" placeholder="请输入用户账号"></div>
             </div>
             <div class="text">
                <div class="zh">用户密码</div>
                <div ><input type="password" name="password" id="password" class="userInput" onfocus="qwer('password')" onblur="qwer1('password')" placeholder="请输入用户密码"></div>
             </div>
             <div class="text">
                <div class="zh">公司名称</div>
                <div ><input type="text" name="GSMC" id="GSMC" class="userInput" onfocus="qwer('GSMC')" onblur="qwer1('GSMC')" placeholder="请输入公司名称"></div>
             </div>
             <div class="text">
                <div class="zh">公司税号</div>
                <div ><input type="text" name="SH" id="SH" class="userInput" onfocus="qwer('SH')" onblur="qwer1('SH')" placeholder="请输入公司税号"></div>
             </div>
             <div>
             <p class="errorMsg"><s:property value="#request.msg"></s:property></p>
                <p class="zhuce" onclick="zhuce()">注册</p>
             </div>
             </form>
        </div>
         <div>
            <img alt="" src="images/t3.jpg" class="textImg">
         </div>
         </div>
         <div id="bottom" class="bottom">
               <div onclick="jsonMenthod()">2015©上海百旺</div>
         </div>
    </div>
</body>
<script type="text/javascript">
   function qwer(ser){
      $("#"+ser).css("border-color","blue");
   }
   function qwer1(ser){
	      $("#"+ser).css("border-color","#dfdfdf");
   }
   function zhuce(){
	   if(vlice()){
		   $("#form1").submit();
	   }
   }
   function dlJM(){
	   window.location.href="xml/goLoginPage.action";
   }
   function vlice(){
	   var reg = /^[0-9a-zA-Z]+$/
	   if($("#userName").val()==""){
		   alert('用户账号不能为空！');
		   return false;
	   }else if(!reg.test($("#userName").val())){
		   alert('用户账号只能是字母和数字！');
		   return false;
	   }else if($("#password").val()==""){
		   alert('用户密码不能为空！');
		   return false;
	   }else if($("#GSMC").val()==""){
		   alert('公司名称不能为空！');
		   return false;
	   }else if($("#SH").val()==""){
		   alert('公司税号不能为空！');
		   return false;
	   }else{
		   return true;
	   }
   }
   function jsonMenthod(){
	   
	   $.ajax({
		   url:'xml/jsonAction.action',
		   type:'post',
		   dataType:'json',
		   success:function(data){
			   alert(data);
		   }
	   })
   }
   function init(){
		if (window.location.protocol == 'http:') {
			url = 'ws://' + window.location.host + "/kpjk/test";
		} else {
			url = 'wss://' + window.location.host + "/kpjk/test";
		}
		if ('WebSocket' in window) {
			ws = new WebSocket(url);
		} else {
			//ws = new SockJS(urlJs, undefined, {protocols_whitelist : transports});
		}

		ws.onopen = function() {
			console.log('Socket 连接打开，请稍候...');
			alert('Socket 连接打开，请稍候...');
		};
		ws.onmessage = function(event) {
			console.log(evt.data);
			alert(evt.data);
		};
		ws.onclose = function(event) {
			console.log('Socket 连接关闭，请稍候...');
			alert('Socket 连接已关闭');
		};
	   
	   
/*    	var wsServer = "ws://localhost:8080/kpjk/test"; //服务器地址http://localhost:8080/kpjk/xml/goResPage.action
   	//var wsServer = "ws://localhost:8080/kpjk/xml/goResPage.action"; 
   	var websocket = new WebSocket(wsServer); //创建WebSocket对象
   	websocket.send("hello");//向服务器发送消息
   	alert(websocket.readyState);//查看websocket当前状态
   	websocket.onopen = function (evt) {
   	 //已经建立连接
   	 console.log("已经建立连接");
   	};
   	websocket.onclose = function (evt) {
   	 //已经关闭连接
   	};
   	websocket.onmessage = function (evt) {
   	 //收到服务器消息，使用evt.data提取
   		console.log(evt.data);
   	};
   	websocket.onerror = function (evt) {
   	 //产生异常
   	};*/
   } 
</script>
</html>