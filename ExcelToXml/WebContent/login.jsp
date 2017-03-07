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
<script src="js/jquery-2.1.1.js"></script>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<title>登录页面</title>
<style type="text/css">
     .Img{width:width:70px;height: 55px;float: left;margin-left:180px;}
     .logoText{color:blue;font-size: 30px;margin-left:180px;padding-top:10px;}
     .zhdl{color:#C9BBBB;font-size: 15px;margin-top: -55px;margin-left: 970px;}
     .dlBtn{width:80px;height: 30px;background-color: blue;color: #FFF;text-align:center;
	       font-size: 17px;border-radius:5px;padding: 5px;margin-top: -50px;margin-left: 1100px;}
	 .dl{float: left;}
	 .logoDiv{background-color: #FBF7F7;height: 5px;width: 1000px;margin-left:180px;}
	 .zh{float: left;height: 42px;width: 75px;margin-right: 10px;line-height: 42px;font-size: 16px;
	     color: #666;font-weight: bold;text-align: right;}
	 .textZw{margin-top:80px;}
	 .text{margin-left:200px;margin-top:30px;}
	 .userInput{margin-top:0px;border:1px solid #dfdfdf;width:270px;height: 40px;border-radius:5px;}
	 .zhuce{width:25%;height: 30px;background-color: blue;color: #FFF;text-align:center;
	       font-size: 17px;border-radius:5px;padding: 5px;margin-top: 40px;margin-left: 200px;cursor:pointer; }
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
                <p class="zhdl">没有账号，现在就<p class="dlBtn" onclick="zhuce()">注册</p></p> 
            </div>
            <div id="logoDiv" class="logoDiv"></div>
         </div>
         
         <div id="text" class="textZw">
           <form action="<%=basePath %>xml/login.action" method="post" id="form1">
             <div class="text">
                <div class="zh">用户账号</div>
                <div ><input type="text" name="userName" id="userName" class="userInput" onfocus="qwer('userName')" onblur="qwer1('userName')" placeholder="请输入用户账号"></div>
             </div>
             <div class="text">
                <div class="zh">用户密码</div>
                <div ><input type="password" name="password" id="password" class="userInput" onfocus="qwer('password')" onblur="qwer1('password')" placeholder="请输入用户密码"></div>
             </div>
             <div>
                <p class="errorMsg"><s:property value="#request.error"></s:property> </p>
                <p class="zhuce" onclick="login()">登录</p>
             </div>
             </form>
        </div>
          <div>
            <img alt="" src="images/t3.jpg" class="textImg">
         </div>
         </div>
         
         <div id="bottom" class="bottom">
               <div>2015©上海百旺</div>
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
   function login(){
	   if($("#userName").val()==""){
		   alert('用户账号不能为空！');
		   return false;
	   }else if($("#password").val()==""){
		   alert('用户密码不能为空！');
		   return false;
	   }else
		   $("#form1").submit();
   }
   function zhuce(){
	   window.location.href="xml/goResPage.action";
   }
   
   //拦截器判断，让整个页面都跳转到登录页面
   $(function(){
	   if("${login}"=="login")
	      window.parent.location.href="xml/goLoginPage.action";
   })
</script>
</html>