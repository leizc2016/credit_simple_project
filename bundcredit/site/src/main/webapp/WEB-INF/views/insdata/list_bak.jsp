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
         function uploadFile(fileType){
        	 if(!validateEmpty(fileType)){
        		 return;
        	 }
        	 waittingFuc();
        	 document.getElementById('form'+fileType).submit();
         }
         
         
         function validateUpload(fileType){
        	 var ele = document.getElementById('file'+fileType);
        	 if(FIVE_M < ele.files[0].size){
     	    	alert('操作失败，文件大小不能超过5M');
     	    	return;
     	     }
        	 var fileName = $('#file'+fileType).val();
        	 if(!fileName){
       			 alert('操作失败，请选择上传文件');
       			 return;
       		 }
       		 if(fileName.lastIndexOf(".txt") == -1 && fileName.lastIndexOf(".xls") == -1 && fileName.lastIndexOf(".xlsx") == -1){
       			 $('#file'+fileType).val('');
       			 $('#nameShow'+fileType).val('');
       			 alert('操作失败，文件格式应为txt, xls, xlsx');
       			 return;
       		 }
       		$('#nameShow'+fileType).val(fileName);
         }
         
         function validateEmpty(fileType){
       		 if(!$('#file'+fileType).val()){
       			 alert('操作失败，上传文件不能为空');
       			 return false;
       		 }
       		 return true;
         }
     </script>
     
  </head>

  <body>

    <!-- Static navbar
    <div class="banner-blue"></div>
     -->



      <div role="tabpanel" class="query-content">

        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
          <li role="presentation" class="active tab1"><a href="#tab1" aria-controls="tab1" role="tab" data-toggle="tab">数据上传</a></li>
        </ul>

        <!-- Tab panes   -->
        <div class="tab-content">
          <div role="tabpanel" class="tab-pane active" id="tab1">
            <div class="title-line">数据上传</div>
              <div class="form-inline data-bar text-center" id='uploadForm'>
                <table>
                	<tr>
                		<td>
		                  <form action="${ctx}/insdata/1/upload" id=form1 method="post" class="clearfix offset-b20"
								enctype="multipart/form-data">
			                  <input type="text" class="form-control input-sm" id="nameShow1" placeholder="申请纪录">
			                  <a class="file btn btn-warning btn-sm">选择文件
			                      <input type="file" name="files" id="file1" onchange="validateUpload(${applyFile})">
			                  </a>
			                  <a class="btn btn-primary btn-sm offset-r50" onclick="uploadFile(${applyFile}); ">上传</a>
		                  </form>
                		</td>
                		<td>
		                  <form action="${ctx}/insdata/2/upload" id=form2 method="post" class="clearfix offset-b20"
								enctype="multipart/form-data">
			                  <input type="text" class="form-control input-sm" id="nameShow2" placeholder="行为信息">
			                  <a class="file btn btn-warning btn-sm">选择文件
			                      <input type="file" name="files" id="file2" onchange="validateUpload(${accountFile})" >
			                  </a>
			                  <a class="btn btn-primary btn-sm" onclick="uploadFile(${accountFile})">上传</a>
		                  </form>
                		</td>
                	</tr>
                	<tr>
                		<td>
			                <form action="${ctx}/insdata/3/upload" id=form3 method="post"
									enctype="multipart/form-data" class="clearfix offset-b20">
			                  <input type="text" class="form-control input-sm" id="nameShow3" placeholder="贷款信息修改">
			                  <a class="file btn btn-warning btn-sm">选择文件
			                      <input type="file" name="files" id="file3" onchange="validateUpload(${historyFile})">
			                  </a>
			                  <a class="btn btn-primary btn-sm offset-r50" onclick="uploadFile(${historyFile})">上传</a>
			                  </form>
                		</td>
                		<td>
		                  <form action="${ctx}/insdata/${commentFile}/upload" id=form4 method="post"
								enctype="multipart/form-data" class="clearfix offset-b20">
		                  <input type="text" class="form-control input-sm" id="nameShow4" placeholder="备注推送">
		                  <a class="file btn btn-warning btn-sm">选择文件
		                      <input type="file" name="files" id="file4" onchange="validateUpload(${commentFile})" >
		                  </a>
		                  <a class="btn btn-primary btn-sm" onclick="uploadFile(${commentFile})">上传</a>
		                  </form>
                		</td>
                	</tr>
                </table>
                <div class="offset-b20">
                  <a class="btn btn-success btn-sm btn-help" target="_blank" href="${ctx}/static/V1.0.pdf">
                  	数据上传格式规范 </a>
                </div>
              </div>
            <div class="clearfix table-title-lg">
              <div class="pull-left">账户信息</div>
            </div>
           <table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
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





