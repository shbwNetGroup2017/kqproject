<%
	String pathl = request.getContextPath();
	String basePathl = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+pathl+"/";
%>
		<div id="sidebar" class="menu-min">
				<div id="sidebar-shortcuts">
					<div id="sidebar-shortcuts-mini">
						<span class="btn btn-success"></span>
						<span class="btn btn-info"></span>
						<span class="btn btn-warning"></span>
						<span class="btn btn-danger"></span>
					</div>
				</div>


				<ul class="nav nav-list">
					<li class="active" id="fhindex">
					  <a href="main/index"><i class="icon-dashboard"></i><span>后台首页</span></a>
					</li>
			<!--菜单的显示  -->
			<c:forEach items="${menuList}" var="menu">
			   <c:if test="${menu.hasMenu}">
				<li id="lm${menu.id }">
					  <a style="cursor:pointer;" class="dropdown-toggle" >
						<i class="icon-desktop"></i>
						<span>${menu.zymc }</span>
						<b class="arrow icon-angle-down"></b>
					  </a>
					  <ul class="submenu">
							<c:forEach items="${menu.subMenu}" var="sub">
								<c:choose>
									<c:when test="${not empty sub.zylj}">
									<li id="z${sub.id }">
									<a style="cursor:pointer;" target="mainFrame"  onclick="siMenu('z${sub.id }','lm${menu.id }','${sub.zymc }','${sub.zylj }')"><i class="icon-double-angle-right"></i>${sub.zymc }</a></li>
									</c:when>
									<c:otherwise>
									<li><a href="javascript:void(0);"><i class="icon-double-angle-right"></i>${sub.zymc }</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
				  		</ul>
				</li>
				</c:if>
			</c:forEach>
			</ul>

			<div id="sidebar-collapse"><i class="icon-double-angle-left"></i></div>

		</div>

