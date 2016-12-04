<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title></title>
<script src="${ctx}/static/idcard_validation.js" type="text/javascript"></script>
</head>

<body>
	<div class="query-content" role="tabpanel">
		<!-- Nav tabs -->
		<ul role="tablist" class="nav nav-tabs">
			<li class="tab1 active" role="presentation"><a href="#">征信查询</a></li>
			<li class="tab2" role="presentation"><a
				href="${ctx}/creditreport/history">查询历史</a></li>
		</ul>



		<!-- Tab panes -->
		<div class="tab-content">
			<div class="font-mask"></div>
			<div role="tabpanel" class="tab-pane active padding0" id="tab2">
				<div role="tabpanel" class="tabs-left sub-content">
					<ul class="nav nav-tabs" role="tablist">${manuAllowed}
					</ul>
					<div role="tabpanel" class="tab-pane active" id="tab-c">
						<!--<form class="form-inline search-bar" id="form1" action="${ctx}/creditreport/financialStable">
     	   <div class="form-group offset-r30" style=“margin-top:10px;margin-bottom:10px;width:50%;">
     	   	 <label class="control-label">查询分类:</label>
             <select name="type">
             	<option value="0" selected="selected">运营中企业监测</option>
             	<option value="1">各区域企业信息汇总</option>
             </select>
           </div><br>
           <input type="hidden" name="lastRequestTimeKey"  value='dc5fe29d-70af-11e5-a0f3-bae5d92fd22e'>	
           <input type="button" class="btn btn-primary btn-sm btn-search"  style="margin-left:100px;" value="查 询" onclick = "submitForm()" />
<!--            <input type="submit" class="btn btn-primary btn-sm btn-search"  style="margin-left:100px;" value="下一步"/> -->
						<!--</form> -->
						<div style="padding-top: 50px; float: left;">
							<img alt="" src="${ctx}/static/images/shmap.jpg" usemap="#Map">
							<map name="Map">
								<area shape="rect" href="javascript:;" coords="300,150,500,450" onclick="distributeFunc(3)">
							</map>
						</div>
						<div style="padding-top: 50px; float: left;">
							<div style="margin-top:20px;">
								<input type="submit" class="btn btn-primary btn-sm btn-search"
									style="margin-left: 100px; width: 200px;" value="注册核验"
									onclick="distributeFunc(1)" />
							</div>
							<div style="margin-top:20px;">
								<input type="submit" class="btn btn-primary btn-sm btn-search"
									style="margin-left: 100px; width: 200px;" value="运营监测"
									onclick="distributeFunc(2)" />
							</div>
							<!--
							<div style="margin-top:20px;">
								<input type="submit" class="btn btn-primary btn-sm btn-search"
									style="margin-left: 100px; width: 200px;" value="区域汇总 "
									onclick="distributeFunc(3)" />
							</div>-->
							<div style="margin-top:20px;">
								<input type="submit" class="btn btn-primary btn-sm btn-search"
									style="margin-left: 100px; width: 200px;" value="信息反馈 "
									onclick="distributeFunc(4)" />
							</div>
							<div style="margin-top:20px;">
								<input type="submit" class="btn btn-primary btn-sm btn-search"
									style="margin-left: 100px; width: 200px;" value="预警分发"
									onclick="distributeFunc(5)" />
							</div>

						</div>
					</div>
				</div>
				<!-- /tab5 end -->
			</div>
			<div style="width:500px;height:700px;"></div>
		</div>
	</div>
	<script type="text/javascript">
		if ('${message_info}') {
			alert('${message_info}');
		}
		$(document).ready(function() {
			$("#form1").validate();
		});

		var submited_flag = false;//重复提交标示
		function submitForm() {
			if (!$("#form1").valid()) {//表单验证
				return;
			}
			if (!submitedFlag()) {//是否已提交过了
				document.getElementById('form1').submit();
			}
		}

		function distributeFunc(funcNum) {
			var url = "${ctx}/creditreport/financialStableDistribute?funcNum="
					+ funcNum;
			window.location.href = url;
		}
	</script>
</body>
</html>
