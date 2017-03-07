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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>聊天消息测试</title>
<script src="js/jquery-2.1.1.js"></script>
<script src="js/jquery.json-2.3.js"></script>
<style type="text/css">
.msgContent {
	font-size: 16px;
	color: blue;
	margin-left:5px;
}

body {
	SCROLLBAR-FACE-COLOR: blue;
	SCROLLBAR-3DLIGHT-COLOR: blue;
	SCROLLBAR-DARKSHADOW-COLOR: blue;
	Scrollbar-Darkshadow-Color: blue;
}
.msgDiv{
   float: left;
}
.connect{
   margin-left:5px;
   float: left;
}
li{
  list-style: none;
}
.Msg{
  color:blue;
  font-size: 14px;
  font-family: monospace;
}
.text_msg{
   border:1px solid #CDE6FC;
   background-color:#CDE6FC; 
  /*  border-radius:10px 10px 0px 0px; */
   margin-bottom: -2px;
   width:700px;
}
.senBut{
   background-color: #CDE6FC;
   border:none;
   color:blue;
   float:right;
   padding:4px;
}
</style>
<script type="text/javascript">
	//获取服务器的链接
	if (window.location.protocol == 'http:') {
		var urlinfo = window.location.href;
		var userName = 'bwkf001';//decodeURI(urlinfo.split("?")[1].split("=")[1]);
		url = 'ws://' + window.location.host + "/ExcelToXml/webSocket/" + userName;
	} else {
		var urlinfo = window.location.href;
		var userName = decodeURI(urlinfo.split("?")[1].split("=")[1]);
		url = 'wss://' + window.location.host + "/ExcelToXml/webSocket/" + userName;
	}
	//创建服务器
	if ('WebSocket' in window) {
		alert(1);
		ws = new WebSocket(url);
		alert(2);
	}
	/**
	 * 与服务器进行连接
	 */
	$(function() {
		ws.onopen = function(evt) {
			console.log('Socket 连接打开，请稍候...');
		};
	});
	//接收服务器的消息
	ws.onmessage = function(evt) {
		var embed= document.getElementById("sound");
		var evtJson = JSON.parse(evt.data);
		//客户连接时，接收服务器的传来的消息列表。
		if (evtJson.length != undefined) {//客户连接消息
			$
					.each(
							evtJson,
							function(i, a) {
								$("#kefu")
										.append(
												"<li id='"+a.kf+"li' style='color:#16CEBA;'><input type='radio' value='"
														+ a.kf
														+ "' name='radioKF' id='"
														+ a.kf
														+ "' onclick='connectKf(this)'>"
														+ a.kf + "</li>");
								$("#msgs")
										.append(
												"<div id='acpMsg"+a.kf+"'  class='Msg' style='display: none; border: 1px solid #CDE6FC;height:200px;width:700px;overflow-Y:auto;'></div>");
							})
		} else if (evtJson.operation == "kf_connect_send") {//客服上线的消息
			if ($("#" + evtJson.kf).val() == undefined) {
				$("#kefu").append(
						"<li id='"+evtJson.kf+"li' style='color:#16CEBA'><input type='radio' value='"
								+ evtJson.kf + "' name='radioKF' id='"
								+ evtJson.kf + "' onclick='connectKf(this)'>"
								+ evtJson.kf + "</li>");
				$("#msgs")
						.append(
								"<div id='acpMsg"+evtJson.kf+"'  class='Msg' style='display: none; border: 1px solid #CDE6FC;height:200px;width:700px;overflow-Y:auto;'></div>");
			}
		} else if (evtJson.operation == "kf_connect_clsoe") {//客服下线时的消息
			$("#" + evtJson.kf + "li").remove();
		} else if (evtJson.operation == "kf_connect") {//客服连接成功消息
			//客服连接时，接收服务器响应信息。
			$("#acpMsg1").append("<li style='list-style:none;'>"+evtJson.msg + "</li>\n");
		} else if (evtJson.operation == "message") {//回话消息
			embed.play();
			$("#acpMsg1").append("<li style='list-style:none;'>"+evtJson.content + "</li><br>");
			if ($("#" + evtJson.from).val() == undefined) {
				$("#kehu")
						.append(
								"<li id='"+evtJson.from+"li' style='color:red;'><input type='radio' name='radioKU' value='"
										+ evtJson.from
										+ "' id='"
										+ evtJson.from
										+ "' onclick='connectKf(this)'>"
										+ evtJson.from + "</li>");
			}
			//判断当前的回话窗口是否是传来消息者
			if ($("#acpMsg" + evtJson.from).val() != undefined) {//判断本页面是否有该窗口
				$("#" + evtJson.from + "li").css("color", "red");
				$("#acpMsg" + evtJson.from).append("<li style='list-style:none;text-align: right;'>"+evtJson.content + "</li><br>");
			} else {
				$("#msgs")
						.append(
								"<div id='acpMsg"+evtJson.from+"' class='Msg' style='display: none; border: 1px solid #CDE6FC;height:200px;width:700px;overflow-Y:auto;'></div>");
				$("#acpMsg" + evtJson.from).append("<li style='list-style:none;text-align: right;'>"+evtJson.content + "</li><br>");
			}
		}
		scrollText();//控制滚动条
	};
	//关闭与服务器的链接
	ws.onclose = function(event) {
		console.log('Socket 连接关闭，请稍候...');
	};
	//发送消息 json数据
	function send() {
		var req = {
			to : $("#to").val(),
			content : $("#sendMsg").val()
		};
		var encoded = $.toJSON(req);
		$("#acpMsg" + $("#nowhh").val()).append(
				"<li style='list-style:none;'>我：" + $("#sendMsg").val() + "</li><br>");
		$("#sendMsg").val('');
		$("#" + $("#to").val() + "li").css("color", "#16CEBA");
		scrollText();
		if($("#nowhh").val()!=='1'&&$("#nowhh").val()!=''){
			ws.send(encoded);
		}else{
			alert('请选择你要发送消息的用户');
		}
		
	}
	//选中当前的客服账号
	function connectKf(kefu) {
		$("#" + $("#to").val() + "li").css("color", "#16CEBA");
		$("#to").val(kefu.value);
		//隐藏当前显示的回话窗口
		$("#acpMsg" + $("#nowhh").val()).css("display", "none");
		//打开回话窗口
		$("#acpMsg" + kefu.value).css("display", "block");
		$("#nowhh").val(kefu.value);
		//去除radio的样式
		$("#" + kefu.value + "li").css("color", "#16CEBA");
	}
	function scrollText() {
		var height = $("#acpMsg" + $("#nowhh").val())[0].scrollHeight;
		$("#acpMsg" + $("#nowhh").val()).scrollTop(height);
	}
	//当页面被刷新时，初始化参数
	function refresh(){
		$("#nowhh").val('1');
		$("#to").val('');
	}
	function screenShot(){
		$.ajax({
			url:'sshot',
			type:'get',
			dataType:'json',
			success:function(data){
			   alert(data);	
			}
		})
	}
</script>
</head>
<body onload="refresh()">
	<input type="hidden" id="userName" value="">
	<input type="hidden" id="to" value="">
	<input type="hidden" id="nowhh" value="1">
	<div class="msgDiv">
		<div class="text_msg"><span class="msgContent">消息:</span></div>
		<div id="msgs">
			<div  style="display: block; border: 1px solid #CDE6FC;height:200px;width:700px; overflow-Y:auto;"
				id="acpMsg1" class="Msg" ></div>
		</div>
        <br>
		<div class="text_msg"><span class="msgContent">发送:</span><span class="msgContent" onclick="screenShot()" style="float:right;cursor: pointer;">截图</span></div>
		<div id="sendTest">
			<textarea rows="5" cols="100" id="sendMsg" class="Msg"
				style="border: 1px solid #CDE6FC;width:698px;"></textarea>
			<br> <input type="button" value="发送" style="margin-top: 20px;" class="senBut"
				onclick="send()">
		</div>
		<br>
	</div>
	<div class="connect" > 
	<div style="padding:13px;float: left;">
		<span class="msgContent">客服人员：<s:property value="#request.msg"></s:property></span>
		<div id="kefu"></div>
	</div>
	<div style="padding:13px;">
		<span class="msgContent">客户人员：</span>
		<div id="kehu"></div>
	</div>
	</div>
	<br>
	<div id="cutImage" style="display: none;">
      <div class="bigImg" style="float: left;">
          <img id="srcImg" src="image/a.jpg" width="400px" height="270px"/>
      </div>
      <div id="preview_box" class="previewImg">
          <img id="previewImg" src="" width="120px"/>
      </div>
      <div >
     <form action="" method="post" id="crop_form">
           <input type="hidden" id="bigImage" name="bigImage"/>
           <input type="hidden" id="x" name="x" />
           <input type="hidden" id="y" name="y" />
           <input type="hidden" id="w" name="w" />
           <input type="hidden" id="h" name="h" />
<!--     <P><input type="button" value="确认" id="crop_submit"/></P> -->
     </form>
  </div>
</div>
  <audio id="sound" autostart=false  src="sound/msg.mp3"> </audio>
</body>
</html>