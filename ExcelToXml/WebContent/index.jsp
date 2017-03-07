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
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="js/jquery-2.1.1.js"></script>
<link href="css/style.css" rel="stylesheet" type="text/css" />
<title>Excel数据转换XML</title>
<script src="js/jquery-2.1.1.js"></script>
<style type="text/css">
    .logo{color:blue;font-size: 30px;margin-left:65px;padding-top:10px;} 
    .logoImg{margin-left:65px;padding-top:5px;width:70px;height: 55px;float: left;}
   ul{list-style: none; border-bottom: 1.5px solid #dfdfdf;padding-bottom: 10px;font-size: 14px;}
   li,a{list-style: none;margin-top:15px;margin-left:10px;color:#7A7373;padding: 5px;}
   a{margin-left:-1px;text-decoration:none;}
   #li1,#li2,#li3,#li4{/*  margin-left:-20px; */ color:#B6A7A7;}
   .manue{background-color: green;list-style: none;margin-top:15px;margin-left:10px;color:#FFF;}
   .user{margin-left:85%;margin-top:-30px;color:blue;font-weight: bold;font-size: 13px;}
   .exit{background-color: blue;border-radius:5px;width:50px;height: 30px;color: #FFF;margin-left:10px;}
</style>
<script type="text/javascript">
      //上次点击的标签
      var upBJ="";
      //菜单的绑定方法
       function qwer(str){
    	  //目前点击的标签
    	  var now="#li"+str;
    	  $(now).parent().addClass("manue"); 
    	  $(now).css("color","#FFF");
    	  if(upBJ!=now){
        	  $(upBJ).parent().attr("class","");
        	  $(upBJ).css("color","#7A7373"); 
    	  }
    	  //提示系统正在开发
    	  if(str.substring(0,1)!='2'){
    		  alert("对不起！系统正在开发中，请耐心等候。");
    	  }
    	  upBJ=now;
      } 
</script>
</head>
<body style="background-color: rgb(243, 237, 237);">
      <!--头部信息展示  -->
      <div>
           <div style="background-color: #FFF;height: 60px;width: 101%;margin-top:-30px;margin-left:-8px;">
              <img src="images/logo.png" class="logoImg"/><p class="logo">上海百旺金赋科技有限公司</p>
           </div>
           <div class="user">
                                    你好，${userName}!<a href="xml/exit.action" class="exit">退出</a>
           </div>
      </div>
     <div style="background-color: #FFF;height: 700px;width: 90%;margin-left: 60px;margin-top:45px;">
          <div style="float: left;width:15%;height: 700px;border: 1px solid #dfdfdf;">
             <ul>
                <li id="li1">用户</li>
                <li><a href="javascript:void(0)" target="text" id="li11" onclick="qwer('11')">账号管理</a></li>
                <li><a href="javascript:void(0)" target="text" id="li12" onclick="qwer('12')">修改密码</a></li>
                <li><a href="javascript:void(0)" target="text" id="li13" onclick="qwer('13')">公司信息</a></li>
             </ul>
             <ul>
                <li id="li2">数据</li>
                <li><a href="excelToXml.jsp" target="text" id="li21" onclick="qwer('21')">开票数据转换</a></li>
             </ul>
             <ul>
                <li id="li3">问题</li>
                <li><a href="error.jsp" target="text" id="li31" onclick="qwer('31')">问题反馈</a></li>
                <li><a href="javascript:void(0)" target="text" id="li32" onclick="qwer('32')">常见问题</a></li>
                <li><a href="<%=basePath %>xml/index_chat.action?userName=${userName}" target="text" id="li33" onclick="qwer('33')">在线答疑</a></li>
             </ul>
          </div>
          <div style="float: left;width:84.6%;">
           <iframe style="width:100%;height:700px;border: 1px solid #dfdfdf;" name = "text" src = ""></iframe>
          </div>
     </div> 
</body>
</html>