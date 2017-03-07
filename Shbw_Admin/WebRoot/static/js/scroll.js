	//活动列表页滑动加载下一页
	/*$(window).scroll(function(){
		var scrollTop = $(this).scrollTop();//获取垂直滚动的距离
		var scrollHeight = $(document).height();//内容高度
		var windowHeight = $(this).height();//可见高度
		//滚动条到达顶部
		if(scrollTop==0){
			console.log("scrollTop:"+scrollTop+"+"+windowHeight+"="+scrollHeight);
			
		}
		//滚动条到达底部
		if(scrollHeight - (offsetHeight + scrollTop) < 10){
	        //console.log(scrollHeight+"-"+windowHeight+"<="+scrollTop);
	        //alert('2222');
	    }

	});*/
	
	
	
	//获取页面顶部被卷起来的高度
	  function scrollTop(){
	    return Math.max(
	      //chrome
	      document.body.scrollTop,
	      //firefox/IE
	      document.documentElement.scrollTop);
	  }
	  //获取页面浏览器视口的高度
	  function windowHeight(){
	    //document.compatMode有两个取值。BackCompat：标准兼容模式关闭。CSS1Compat：标准兼容模式开启。
	    return (document.compatMode == "CSS1Compat")?
	    document.documentElement.clientHeight:
	    document.body.clientHeight;
	  }
	  //获取页面文档的总高度
	  function documentHeight(){
	    //现代浏览器（IE9+和其他浏览器）和IE8的document.body.scrollHeight和document.documentElement.scrollHeight都可以
	    return Math.max(document.body.scrollHeight,document.documentElement.scrollHeight);
	  }
	  
	  //图片查询中正对浏览器主页面滚动事件处理(瀑布流)。只能使用window方式绑定，使用document方式不起作用
	  $(window).on('scroll',function(){
		    var height=$("#tabs-833474").outerHeight();
		    if(scrollTop() + windowHeight() >= (documentHeight() - 50/*滚动响应区域高度取50px*/)){
		      // alert('上拉加载');
		    }else if(scrollTop()==0){		    	
		       //alert('下拉刷新');
		    }
		    
	  });
	  
	
	
