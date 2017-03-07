(function(_window,$){
	var aMsg = [
				//"不清楚",
				"不满意",
				"满意",
				"很满意"
				]
	var $star = $("#star");
	
	var $li1 = $star.find("#d1").find("ul").find("li");
	var $li2 = $star.find("#d2").find("ul").find("li");
	var $li3 = $star.find("#d3").find("ul").find("li");
	
	$li1.click(function(){
		exe($li1,$(this));
		
	});
	$li2.click(function(){
		exe($li2,$(this));
		
	});
	$li3.click(function(){
		exe($li3,$(this));
		
	});
	
	function exe($li,_this){
		//console.log("li===="+$li);
		//console.log($(this));
		var d = $li.parents(".d");
		_this.addClass("originaled");
		var curNumber = _this.children("a").text();
		if(curNumber === "1"){
			addStar(1,d);
		}else if(curNumber === "2"){			
			  addStar(2,d);			  
		}else if(curNumber === "3"){
			addStar(3,d);			
		}else if(curNumber === "4"){			
			addStar(4,d);
		}
		console.log(curNumber);
	}
	
	
	function addStar(num,ele){
		ele.find("ul").find("li").removeClass("originaled");
		for(var i = 0;i<num;i++){
			ele.find("ul").find("li").eq(i).addClass("originaled");
		}
		var o = "<span class='showText' style='color:red;'>"+num+"分</span><span class='showExplain'>("+aMsg[num-1]+")</span>";
		ele.find(".showWrap").html(o);
	}
	
	
})(window,$);