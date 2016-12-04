<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

  <div class="container wrap">

      <div role="tabpanel" class="query-content">

        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
          <li role="presentation" class="active tab1"><a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">用户管理</a></li>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
        <c:if test="${not empty message}">
			<div id="message" class="alert alert-danger"><button data-dismiss="alert" class="close">×</button>${message}</div>
     	</c:if>
     	
        <div class="tab-content">
          <div role="tabpanel" class="tab-pane active" id="tab1">
            <div class="offset-t30 offset-l30">
              <h1 class="gray user-title">用户创建</h1>
					<form id="safForm" action="${ctx}/user/user_management/adduser"
						method="post" class="form-inline" onsubmit="return validatePSW()" >
						<input type="text" id="insCode"  name="insCode"  value="${inscode}" hidden="" >
								<div class="form-group-user">
               						 <span class="icon-sm icon-account"></span>
               						 <input type="text" id="loginName" name="loginName" 
										class="form-control required email" placeholder="登录名(邮箱账号)" />
             					 </div>
             					 <div class="form-group-user">
               						 <span class="icon-sm icon-account"></span>
               						 <input type="text" id="name" name="name" 
										class="form-control required" placeholder="姓名" />
             					 </div>
							<div class="form-group-user">
              					  <span class="icon-sm icon-password "></span>
									<input type="password" id="plainPassword" name="plainPassword"
										class="form-control required" placeholder="密码" />
							</div>
							<div class="form-group-user">
            				    <span class="icon-sm icon-password "></span>
									<input type="password" id="confirmPassword"
										name="confirmPassword" class="form-control required"
										 equalTo="#plainPassword" placeholder="再次输入密码"/>
							</div>
							<div class="control-group">
							
								<div class="form-group-user">
							<span class="icon-sm icon-key "></span>
								<select  id="roles"  name="roles"
									class="form-control required" >
									<option value="">请选择权限</option>
									<option value="creditreport">报告查询</option>
									<option value="fund">资金管理</option>
									<option value="insdata">数据上传</option>
									<option value="account">用户管理</option>
									</select>
							</div>
						</div>
							<div class="form-actions">
								<input id="submit_btn" class="btn btn-primary" type="submit"
									value="提交" />&nbsp; <input id="cancel_btn" class="btn"
									type="button" value="返回" onclick="history.back()" />
							</div>
								
					</form>
			    </div>
          </div><!-- /tab1 end -->
        </div>
      </div><!-- /tab end -->
	</div>
   </div> <!-- /container -->
    
<script>
	$(document).ready(function() {
		$("#safForm").validate();
	});
	
	function validatePSW(){
		var psw = $("#plainPassword").val();
		//alert(psw);
		var regex = new RegExp('(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,30}');
		if(!regex.test(psw)){
			alert('密码中必须包含大小写字母、数字、特称字符，至少8个字符，最多30个字符。');
			return false;
		}
		//console.log(regex.test('a123456-'));
		return true;
	}
	
</script>
