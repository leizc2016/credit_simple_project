<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
  		   </c:if>
          <div role="tabpanel" class="tab-pane active" id="tab1">
            <div class="offset-t30 offset-l30">
              <h1 class="gray user-title">个人账户</h1>
              <form id="safForm" action="${ctx}/userperson/personManagersave" method="post"  onsubmit="return validatePSW()" >
              <input type="text" value="${bcuser.id}" id="id" name="id"  hidden=""/>
              <input type="text" value="${bcuser.loginName}" id="loginName1" name="loginName"  hidden=""/>
              <div class="form-group-user">
                <span class="icon-sm icon-account"></span>
                <input type="text" class="form-control" value="${bcuser.loginName}" disabled="disabled" >
              </div>
              <div class="form-group-user">
                <span class="icon-sm icon-account"></span>
                <input type="text" class="form-control required"  name="name"  value="${bcuser.name}"  placeholder="姓名" >
              </div>
               <div class="form-group-user">
                <span class="icon-sm icon-password "></span>
                <input type="password" name="oldPassword" class="form-control required checkUserExist" placeholder="旧密码">
              </div>
               <div class="form-group-user">
                <span class="icon-sm icon-key"></span>
                <input type="password" id="plainPassword" name="plainPassword" class="form-control" placeholder="新密码">
              </div>
              <div class="form-group-user">
                <span class="icon-sm icon-key"></span>
                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control"  placeholder="再次输入新密码" equalTo="#plainPassword">
              </div>
               <div class="form-actions">
								<input id="submit_btn" class="btn btn-primary" type="submit"
									value="确定" />&nbsp; <input id="cancel_btn" class="btn"
									type="button" value="返回" onclick="history.back()" />
				</div>
              </form>
            </div>
          </div><!-- /tab1 end -->
        </div>
      </div><!-- /tab end -->

    </div> <!-- /container -->
    <script>
	$(document).ready(function() {
		$.validator.addMethod("checkUserExist",function(value,element){
	        var oldPassword = value;
	        var id=$("#id").val();
	        $.ajax({
	            type:"POST",
	            async:false, 
	            url:"${ctx}/user/user_management/password_validate",
	            data:"id="+id+"&oldPassword="+oldPassword,
	            success:function(response){
	                if(response=='0'){
	                    res = false;
	                }else{
	                    res = true;
	                }
	            }
	        }); 
	        return res;
	    },"<font color='#E47068'>密码不正确</font>");
		$("#safForm").validate();
	});
	
	function validatePSW(){
		var psw = $("#plainPassword").val();
		var psw2 = $("#confirmPassword").val();
		if (psw != "" && psw2 != "") {
			//alert(psw);
			var regex = new RegExp('(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,30}');
			if(!regex.test(psw)){
				alert('密码中必须包含大小写字母、数字、特称字符，至少8个字符，最多30个字符。');
				return false;
			}
		}
		//console.log(regex.test('a123456-'));
		return true;
	}
</script>
