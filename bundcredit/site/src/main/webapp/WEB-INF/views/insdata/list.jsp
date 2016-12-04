<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="applyFile" value="1"/>
<c:set var="accountFile" value="2"/>
<c:set var="historyFile" value="3"/>
<c:set var="commentFile" value="4"/>
<html>
  <head>

     <link href="${ctx}/static/bc/css/common.css" rel="stylesheet">
     <script type="text/javascript">
         var FIVE_M = 5*1024*1024;
         function uploadFile(){
        	 if(!validateEmpty()){
        		 return;
        	 }
        	// waittingFuc();
        	 var uploadType = $('#upType:checked').val();
        	 //alert(uploadType);
        	 document.getElementById('form1').action='${ctx}/insdata/'+uploadType+'/upload';
        	 document.getElementById('form1').submit();
         }
         
         
         function validateUpload(){
        	 var ele = document.getElementById('file1');
        	 if(FIVE_M < ele.files[0].size){
     	    	alert('操作失败，文件大小不能超过5M');
     	    	return;
     	     }
        	 var fileName = $('#file1').val();
       		 if(fileName.lastIndexOf(".txt") == -1 && fileName.lastIndexOf(".xls") == -1 && fileName.lastIndexOf(".xlsx") == -1 && fileName.lastIndexOf(".zip") == -1){
       			 $('#file1').val('');
       			 $('#nameShow').val('');
       			 alert('操作失败，文件格式应为txt, xls, xlsx, zip');
       			 return;
       		 }
       		$('#nameShow').val(fileName);
         }
         
         function validateEmpty(){
       		 if(!$('#file1').val()){
       			 alert('操作失败，上传文件不能为空');
       			 return false;
       		 }
       		 return true;
         }
     </script>
     
  </head>

  <body>
  		<div role="tabpanel" class="query-content">

        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
          <li role="presentation" class="active tab1"><a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">数据上传</a></li>
        </ul>

        <!-- Tab panes   -->
        <div class="tab-content">
          <div role="tabpanel" class="tab-pane active" id="tab1">
            <div class="title-line">
            <div class="pull-right"><span><i class="icon icon-data-spe"></i><a target="_blank" href="${ctx}/static/SpecV3.0.1.pdf">数据上传格式规范</a></span><span><i class="icon icon-data-tep"></i><a href="${ctx}/static/TemplateV1.1.zip">数据上传格式模板</a></span></div>
           	 数据上传</div>
              <div class="search-wrap">
          <form class="form-inline data-bar" id="form1" method="post" enctype="multipart/form-data">
              <div class="clearfix offset-b10">
                  <input type="text" class="form-control input-sm" id="nameShow" placeholder="">
                  <div class="radio offset-r30">
                  <label>
                      <input name="upType" id='upType' type="radio" value="1" checked="checked" >
                                                                   申请信息
                  </label>
              </div>
              <div class="radio offset-r30">
                  <label>
                      <input name="upType" id='upType' type="radio" value="2">
                                                                  行为信息  
                  </label>
              </div>
              <div class="radio offset-r30">
                  <label>
                      <input name="upType" id='upType' type="radio" value="4">
                                                                   备注信息
                  </label>
              </div>
              <a class="file btn btn-warning btn-sm">选择文件
              <input type="file" name="files" id="file1" onchange="validateUpload()" >
              </a> <a class="btn btn-primary btn-sm offset-r50" href="javascript:uploadFile()" >上传</a> </div>
          </form>
        </div>
            <div class="clearfix table-title-lg">
              <div class="pull-left">账户信息</div>
            </div>
           <table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead class="select-th">
		<tr><th>上传时间</th><th>机构代码</th>
		<th>用户名</th><th>文件名</th><th>文件类型</th>
		<th>文件估值</th><th>文件状态</th></thead>
		<tbody>
		<c:forEach items="${insFiles}" var="f">
			<tr>
				<td><fmt:formatDate value="${f.uploadDatetime}" pattern="yyyy-MM-dd  HH:mm:ss" /></td>
				<td>${f.insCode}</td>
				<td>${f.uploadman}</td>
				<td>${f.fileName}</td>
				<td>${f.fileTypeStr}</td>
				<td>${f.valAmt}</td>
				<td>${f.fileStatusStr4Site}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
          </div><!-- /tab1 end -->
	<tags:bcpagination/>
        </div>

      </div><!-- /tab end -->
     <div id='waitting' style='display: none;'>
	    <div class="mask-bg"></div>
	    <div class="text-center upload-img">
	      <img src="${ctx}/static/bc/img/uploading.gif">
	    </div>
     </div>


    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script type="text/javascript">
      onload=function(){
        var trs=$(".table > tbody > tr")
        for(var i=0;i<trs.length;i++)
        {
        if(i%2==0)
                trs[i].className="tr1";
            else
              trs[i].className="tr2";
        }
      } 
      function waittingFuc(){
	      document.getElementById('waitting').style.display='inline';
      }
      if('${message_info}'){
    	  alert('${message_info}');
      }
    </script>
  </body>
</html>





