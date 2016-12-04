<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
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
          <li class="tab2" role="presentation"><a href="${ctx}/creditreport/history">查询历史</a></li>
        </ul>
		<div style="text-align:right;"><a onclick="history.go(-1)">返 回</a></div>
        <!-- Tab panes -->
        <div class="tab-content">
        <div class="font-mask"></div>
          <div id="tab2" class="tab-pane active" role="tabpanel">
            <div class="tabs-left sub-content" role="tabpanel">

              <!-- Nav tabs -->
              <ul role="tablist" class="nav nav-tabs">
                   ${manuAllowed}
              </ul>

              <!-- Tab panes -->
              <div class="tab-content">
                <div id="tab-a" class="tab-pane active" role="tabpanel">
                  <form class="form-inline search-bar" id="financial_form_id" action='${ctx}/creditreport/financialStableSearch'>
                  <div class="rows offset-b20">
						<div class="form-group offset-r30">
							<label>搜索条件：</label> 
							<input type="text" placeholder="公司名称关键字" name="q_companyName" id="companyName" class="form-control required input-sm input-xs" value='${companyName}'  >
						</div>
						<div class="form-group form-group-lt">
							<input type="button" value="搜公司" class="btn btn-primary btn-sm btn-search" onclick = "submitForm()" />
						</div>
						<div class="rows offset-b20">
							<div class="form-group offset-r30" style="width:50%">
								<label>注册地址：</label> 
								<select style="height:25px" class="form-control input-xs" name=''>
			                      <option value='' selected="selected">所有</option>
			                    </select>
								<select style="height:25px" class="form-control input-xs" name=''>
			                      <option value='' selected="selected">所有</option>
			                      <option value='上海市'>
			                      		上海市
								  </option>
			                    </select>
								<select style="height:25px" class="form-control input-xs" name='q_districtCode' id='districtCode' onchange="" >
			                      <option value='' selected="selected">所有</option>
			                      <option value='黄浦'  <c:if test="${districtCode=='黄浦'}">selected</c:if>>
			                      		黄浦区
								  </option>
			                     <%--  <option value='嘉定'  <c:if test="${districtCode=='嘉定'}">selected</c:if>>
			                      		嘉定区
			                      </option>
			                      <option value='杨浦'  <c:if test="${districtCode=='杨浦'}">selected</c:if>>
			                      		杨浦区
			                      </option>
			                      <option value='宝山'  <c:if test="${districtCode=='宝山'}">selected</c:if>>
			                      		宝山区
			                      </option> --%>
			                    </select>
							</div>
							<div class="form-group offset-r30" style="text-align:left;">
								<label>经营地址：</label> 
								<select style="height:25px" class="form-control input-xs" name=''>
			                      <option value='' selected="selected">所有</option>
			                    </select>
								<select style="height:25px" class="form-control input-xs" name=''>
			                      <option value='' selected="selected">所有</option>
			                      <option value='上海市'>
			                      		上海市
								  </option>
			                    </select>
								<select style="height:25px" class="form-control input-xs" name='q_businessdistrict'>
			                      <option value='' selected="selected">所有</option>
			                      <option value='黄浦'  <c:if test="${businessdistrict=='黄浦'}">selected</c:if>>
			                      		黄浦区
								  </option>
			                      <option value='嘉定'  <c:if test="${businessdistrict=='嘉定'}">selected</c:if>>
			                      		嘉定区
			                      </option>
			                      <option value='杨浦'  <c:if test="${businessdistrict=='杨浦'}">selected</c:if>>
			                      		杨浦区
			                      </option>
			                      <option value='宝山'  <c:if test="${businessdistrict=='宝山'}">selected</c:if>>
			                      		宝山区
			                      </option>
			                    </select>
							</div>
							<div class="rows offset-b20">
								<div class="form-group offset-r30" style="width:47.5%">
									<label>监测建议：</label> 
									<input type="checkbox" value="1" name="advice"/>无建议
									<input type="checkbox" value="2" name="advice"/>街道访谈
									<input type="checkbox" value="3" name="advice"/>与专业部门协同走访
									<input type="checkbox" value="4" name="advice"/>经侦部门介入
								</div>
								<div class="form-group offset-r30" style="text-align:left;">
									<label class="lt">风险排序：</label>
									<input type="radio" value="1" name="order">从高到低
									<input type="radio" value="2" name="order">从低到高
								</div>
							</div>
						</div>
					</div>
                   	<input type="hidden" name="lastRequestTimeKey"  value='073bfd5e748c409aa964ab772a97hellokitty'  >	
                  </form>
                  <div class="title-line offset-b20 offset-t30">详细信息</div>
                  <!-- 被执行信息  -->
                  <div class="offset-t30" id="executorInfo">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">企业列表</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="125">公司名称</th>
                          <th width="170">公司注册所在地</th>
                          <th width="170">公司经营所在地</th>
                          <th width="80">风控指标</th>
                          <th width="80">检测建议</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${financialStableBeans}" var="financialStable">
						<tr class="tr1">
							<input type="hidden" value="${financialStable.regID}" id="regId"/>
							<td id='name' class='cname_${financialStable.regID}'>
								<a href='${ctx}/creditreport/financialstable/enterpriseSearch?registerNum=${financialStable.regID}&idCardNum='>${financialStable.name}</a>
							</td>
							<td>${financialStable.address}</td>
							<td>${financialStable.opAddress}</td>
							<td id='risk_${financialStable.regID}'>高风险指标<a style='color:red' onClick='showRiskDetail(${financialStable.riskNums},
							${financialStable.regID},${financialStable.isExistDishPersons})'>${financialStable.riskNums}</a>个</td>
							<td id='dish_${financialStable.regID}'>${financialStable.monitorRcd}</td>
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
                  <!-- <input type="button" value="风险扫描" class="btn btn-primary btn-sm btn-search" onclick = "riskScan()" style="background:green;color:white "/> -->
                  <tags:bcpagination/>
                  <!-- 风险详情  -->
                  <div class="offset-t30" id="riskDetail" style="display:none;">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">风险详情 - <span id='companyname'></span></div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<tbody>
                          <tr class="tr1">
                            <td width="60%">高龄用户比例超过50%</td>
                            <td width="40%" id="td1">否</td>
                          </tr>
                          <tr class="tr2">
                            <td>注册地和经营地不一致</td>
                            <td id="td2">否</td>
                          </tr>
                          <tr class="tr1">
                            <td>实缴金额少于100万元</td>
                            <td id="td3">否</td>
                          </tr>
                          <tr class="tr2">
                            <td>企业租赁期限少于6个月</td>
                            <td id="td4">否</td>
                          </tr>
                          <tr class="tr2">
                            <td>存在失信人信息</td>
                            <td id="td5">否</td>
                          </tr>
                        </tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
                  
                </div>
              </div>

            </div>
          </div><!-- /tab2 end  -->
        </div>
        <div class="text-left">
        	<button class="btn btn-primary btn-sm btn-search" onclick='document.execCommand("print")' >打 &nbsp; 印</button>
        </div>
      </div>     
      <script type="text/javascript">
          if('${message_info}'){
        	  alert('${message_info}');
          }
          
          $(document).ready(function() {
        	  $("#financial_form_id").validate();
          });
        	
          var submited_flag = false;//重复提交标示
          function submitForm(){
        	  if(!$("#financial_form_id").valid()){//表单验证
        		  return;
        	  }
        	  if(!submitedFlag()){//是否已提交过了
        		  $("#riskDetail").hide();
	        	  document.getElementById('financial_form_id').submit();
        	  }
          }
          function riskScan(){
        	  $("#riskDetail").hide();
        	  var arrayObj = new Array();
        	  $("td[id='name']").each(function(index){
        		  arrayObj.push($(this).text());
        	  });
        	  if(arrayObj.length == 0){
        		  alert("未选择风险扫描公司!");
        		  return false;
        	  }
        	  $.ajax({
 				 type:"GET",
 				 url:"${ctx}/creditreport/getDishNums",
 				 data:{"keys":arrayObj.join(',')},
 				 dataType:"json",
 				 success:function(res){
 					 $("input[id='regId']").each(function(index){
 						 var rid = Math.floor(Math.random()*4+1)
 						 var url = "${ctx}/creditreport/enterpriseSearch?registerNum=" + $(this).val() +"&idCardNum=#dishPersonInfo";
 						 if(res[index] != 0){
 							$("#dish_" + $(this).val()).html("失信被执行<a target='_blank' style='color:red' href=" + url + ">" + res[index] + "</a>次");
 						 }else{
 							$("#dish_" + $(this).val()).html("失信被执行<a target='_blank' href=" + url + ">" + res[index] + "</a>次");
 						 }
 						 $("#risk_" + $(this).val()).html("高风险指标<a style='color:red' onClick='showRiskDetail(" + rid + "," + $(this).val() + ")'>" + rid + "</a>个");
 		        	 });
 				 },
 				 error:function(){
 					 alert("风险扫描出错!");
 				 }
 			});
        } 
        
        function showRiskDetail(rdNum,regId,isExistDishPersons){
        	$("#companyname").html($('.cname_'+regId).html());
        	for(var j=1;j<=5;j++){
        		$("#td"+j).text('否');
        	}
        	if(isExistDishPersons == 1){
        		$("#td5").text('是');
        		rdNum = rdNum - 1;
        	}
        	for(var i=1;i<=rdNum;i++){
        		$("#td"+i).text('是');
        	}
        	$("#riskDetail").show();
        	return false;
        }
      </script>  
</body>
</html>
