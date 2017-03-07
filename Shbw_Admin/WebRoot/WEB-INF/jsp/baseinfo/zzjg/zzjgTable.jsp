<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="b" style="float: left; width: 1000px; display: inline">
	<table id="table_report" class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th class="center" onclick="selectAll()"><label><input type="checkbox" id="zcheckbox" /><span class="lbl"></span></label></th>
				<th>序号</th>
				<th>机构名称</th>
				<th>机构代码</th>
				<th>纳税人识别号</th>
				<th>状态</th>
				<th>创建人</th>
				<th>创建时间</th>
				<th class="center">操作</th>
			</tr>
		</thead>

		<tbody>

			<!-- 开始循环 -->
			<c:choose>
				<c:when test="${not empty zzglList}">
					<c:forEach items="${zzglList}" var="var" varStatus="vs">
						<tr>
							<td class='center' style="width: 30px;"><label><input type='checkbox' name='ids' value="${var.id }" id="${user.id }" /><span class="lbl"></span></label></td>
							<td class='center' style="width: 30px;">${vs.index+1}</td>
							<td>${var.xfmc}</td>
							<td>${var.jgbm}</td>
							<td>${var.xfsh}</td>
							<td><c:if test="${var.del_flag==0}">启用</c:if><c:if test="${var.del_flag==1}">未启用</c:if></td>
							<td>${var.creator}</td>
							<td>${var.create_date}</td>
							<td width="60px;">
								<div class='hidden-phone visible-desktop btn-group'>
									<a class='btn btn-mini btn-info' title="编辑" onclick="edit('${var.id}');"><i class='icon-edit'></i></a> <a class='btn btn-mini' title="详情" onclick="detail('${var.id }');"><i class='icon-home'></i></a>
								</div>
							</td>
						</tr>

					</c:forEach>
				</c:when>
				<c:otherwise>
					<tr class="main_info">
						<td colspan="10" class="center">没有相关数据</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</tbody>
	</table>

	<div class="page-header position-relative">
		<table style="width: 100%;">
			<tr>
				<td style="vertical-align: top;"><a class="btn btn-small btn-success" onclick="add();">新增</a> <a title="批量删除" class="btn btn-small btn-danger" onclick="makeAll('确定要删除选中的数据吗?');"><i class='icon-trash'></i></a></td>
				<td style="vertical-align: top;"><div class="pagination" style="float: right; padding-top: 0px; margin-top: 0px;">${page.pageStr}</div></td>
			</tr>
		</table>
	</div>
</div>