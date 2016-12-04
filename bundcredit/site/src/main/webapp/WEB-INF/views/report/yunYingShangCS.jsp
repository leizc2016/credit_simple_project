<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title></title>
<script src="${ctx}/static/region_select.js" type="text/javascript"></script>
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
			<div role="tabpanel" class="tab-pane active padding0" id="tab2">
				<div role="tabpanel" class="tabs-left sub-content">
					<ul class="nav nav-tabs" role="tablist">${manuAllowed}
					</ul>
					<div class="tab-content">
						<div class="font-mask"></div>
						<div role="tabpanel" class="tab-pane active" id="tab-c">
							<div class="search-wrap">
								<form class="form-inline search-bar" id="yunYinShangcs_form_id" 
								action="${ctx}/creditreport/yunYingShangCSSearch" method="post">
									<div class="rows offset-b20">
										<div class="form-group offset-r30">
											<label>姓名：</label> 
											<input placeholder="请输入姓名" name="userName"
												id="userName" class="form-control input-sm input-xs"
												value="${bcHisYYSCS.userName}" type="text">
										</div>
										<div class="form-group form-group-lt">
											<label class="lt">家庭地址：</label>
											<div>
												<select name="homeProvince" id="location_p"></select>
												<select name="homeCity" id="location_c"></select>
												<select name="homeDistrict" id="location_a"></select>
												<input placeholder="请输入除省(直辖市)市(辖区/县)县(城区)外的详细地址" name="homeAddr"
													id="homeDetailAddr" class="form-control input-sm input-xs"
													value="${bcHisYYSCS.homeAddr}" type="text"> 
											</div>
										</div>
										<div class="rows offset-b20">
											<div class="form-group offset-r30">
												<label>身份证号：</label> <input placeholder="请输入身份证号"
													name="idCardNum" id="idCard"
													class="form-control input-sm input-xs isIdCardNo" value="${bcHisYYSCS.idCardNum}" type="text">
											</div>
											<div class="form-group form-group-lt">
												<label class="lt">工作地址：</label>
												<div>
													<select name="workingProvince" id="location_p1">
													</select> <select name="workingCity" id="location_c1">
													</select> <select name="workingDistrict" id="location_a1">
													</select> 
														<input placeholder="请输入除省(直辖市)市(辖区/县)县(城区)外的详细地址" name="workingSite"
															   id="workDetailAddr" class="form-control input-sm input-xs"
															   value="${bcHisYYSCS.workingSite}" type="text"> 
												</div>
											</div>
											<div class="rows offset-b20">
												<div class="form-group offset-r30">
													<label>手机号码：</label> <input placeholder="" name="cellNum"
														id="cellNum" class="form-control input-sm input-xs"
														value="${bcHisYYSCS.cellNum}" type="text" readonly="readonly">
												</div>
												<div class="form-group offset-r30">
													<label class="lt">关联手机号码：</label> <input
														placeholder="请输入关联手机号码" name="contactNum"
														id="refTelephoneNum"
														class="form-control input-sm input-xs"
														value="${bcHisYYSCS.contactNum}" type="text">
												</div>
												<div class="form-group offset-r30">
													<label class="lt">机构授权码：</label> <input placeholder=""
														name="authCode" id="authCode"
														class="form-control input-sm input-xs" value="${bcHisYYSCS.authCode}"
														type="text" readonly="readonly">
												</div>
											</div>
										</div>
										
										<div class="text-center">
										    <input type="button" value="查 &nbsp; 询" class="btn btn-primary btn-sm btn-search" onclick = "return onSubmit();" />
										    <input type="hidden" name="lastRequestTimeKey"  value='073bfd5e748c409aa964ab772a9aaaaa'  >
										</div>
									</div>
								</form>
							</div>
							
							<div class="clearfix">
								<div class="title-line offset-b20 offset-t30">查询内容</div>
								<div class="table-title-xs">
									<span>基础信息</span>
								</div>
								<table class="table table-bordered table-xs">
									<tbody>
										<tr>
											<td>实名制</td>
											<td>${bcHisYYSCS.matchLevel}</td>
										</tr>
										<tr>
											<td>身份证匹配</td>
											<td>${bcHisYYSCS.matchIdCard}</td>
										</tr>
										<tr>
											<td>在网时间</td>
											<td>${bcHisYYSCS.netTime}天</td>
										</tr>
										<tr>
											<td>在网使用中手机卡数量</td>
											<td>${bcHisYYSCS.cellAccount}</td>
										</tr>
										<tr>
											<td>在网使用中固话数量</td>
											<td>${bcHisYYSCS.fixedAccount}</td>
										</tr>
									</tbody>
								</table>
							</div>
							<div class="title-line offset-b20 offset-t30">详细内容</div>
							<div id="warningdetail" style="display:none;"><span style="color:red;">系统查询耗时预计为3-6小时，请稍后在历史查询中查看。</span></div>
							<div style="display:none;" id="showdetail">
								
								<div class="clearfix">
									<div class="pull-left">
										<div class="table-title-sm table-dz">
											<span>地址匹配</span>
										</div>
										<table class="table table-bordered table-xs table-dz">
											<tbody>
												<tr>
													<td>工作地址匹配</td>
													<td>${bcHisYYSCS.workAddMatch}</td>
												</tr>
												<tr>
													<td>家庭地址匹配</td>
													<td>${bcHisYYSCS.homeAddMatch}</td>
												</tr>
											</tbody>
										</table>
									</div>
									<div class="pull-left">
										<div class="table-title-sm table-th offset-l40">
											<span>通话频次</span>
										</div>
										<table
											class="table table-bordered table-sm table-th table-center">
											<tbody>
												<tr>
													<td>关联手机号</td>
													<td>${bcHisYYSCS.contactNum}</td>
												</tr>
												<tr>
													<td>通话频次</td>
													<td>${bcHisYYSCS.recentCallFrequency}</td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								<div class="clearfix">
									<div class="clearfix table-title-lg">
										<span>最近六个月账单</span>
									</div>
									<table class="table table-bordered cupay">
										<tbody>
											<tr>
												<!-- <td>账单日期</td> -->
												<td>账单金额(单位:元)</td>
												<td>${bcHisYYSCS.billAmount}</td>
											</tr>
											<tr>
												<td>当月流量(单位:M)</td>
												<td>${bcHisYYSCS.billData}</td>
											</tr>
											<!-- tr>
												<td>N/A</td>
												<td>N/A</td>
												<td>N/A</td>
											</tr>
											<tr>
												<td>N/A</td>
												<td>N/A</td>
												<td>N/A</td>
											</tr>
											<tr>
												<td>N/A</td>
												<td>N/A</td>
												<td>N/A</td>
											</tr> -->
										</tbody>
									</table>
								</div>
								<!--<div class="text-center offset-t20">
									<a class="btn btn-warning btn-export">导出PDF文件</a>
								</div>-->
							</div>
						</div>
						<!-- /tab2 end -->
					</div>
				</div>
			</div>
		</div>
		<div class="text-left">
        	<button class="btn btn-primary btn-sm btn-search" onclick='document.execCommand("print")' >打 &nbsp; 印</button>
        </div>
	</div>
	<script type="text/javascript">
	      $(document).ready(function() {
	    	  if('${message_info}'){
	        	  alert('${message_info}');
	          }
	    	  new PCAS('homeProvince', 'homeCity', 'homeDistrict', '<%=request.getParameter("homeProvince")%>' , '<%=request.getParameter("homeCity")%>',
	    			  '<%=request.getParameter("homeDistrict")%>');
	    	  new PCAS('workingProvince', 'workingCity', 'workingDistrict', '<%=request.getParameter("workingProvince")%>' , '<%=request.getParameter("workingCity")%>',
	    			  '<%=request.getParameter("workingDistrict")%>');
	    	  if('${bcHisYYSCS.showWarn}'== '1'){
	    		  $("#warningdetail").css('display','block'); 
	    		  $("#showdetail").css('display','none'); 
	    	  }else if('${bcHisYYSCS.showWarn}'== '2'){
	    		  $("#warningdetail").css('display','none'); 
	    		  $("#showdetail").css('display','block'); 
	    	  }else if('${bcHisYYSCS.showWarn}'== '3'){
	    		  $("#warningdetail").css('display','none'); 
	    		  $("#showdetail").css('display','none'); 
	    	  }
		  });
	      
	      function onSubmit(){
	    	  if($("#location_p").val() == '请选择' && $("#location_c").val() == '请选择' && 
	    		 $("#location_a").val() == '请选择' && $("#homeDetailAddr").val().trim() != ''){
	    		  alert("请选择家庭地址!");
	    		  return;
	    	  }
	    	  
	    	  if($("#location_p1").val() == '请选择' && $("#location_c1").val() == '请选择' && 
	    		 $("#location_a1").val() == '请选择' && $("#workDetailAddr").val().trim() != ''){
	    		  alert("请选择工作地址!");
	    		  return;
	    	  }
	    	 
	    	  /* if($("#location_p").val() != '请选择' && 
	    		 $("#location_c").val() != '市辖区'&&
	    		 $("#location_c").val() != '市辖县'&&
	    		 $("#location_a").val() != '请选择'){
	    		  if($("#location_a").val() == '市辖区'){
	    			  $("#homeAddr").val($("#location_p").val() + $("#location_c").val() +  $("#homeDetailAddr").val()); 
	    		  }else{
	    			  $("#homeAddr").val($("#location_p").val() + $("#location_c").val() +  $("#location_a").val() +  $("#homeDetailAddr").val());
	    		  }
	    	  }else if($("#location_p").val() != '请选择' && 
		    		   ($("#location_c").val() == '市辖区'||
		    		   $("#location_c").val() == '市辖县')&&
		    		   $("#location_a").val() != '请选择'){
	    		  $("#homeAddr").val($("#location_p").val() +  $("#location_a").val() +  $("#homeDetailAddr").val()); 
	    	  }
	    	  
	    	  if($("#location_p1").val() != '请选择' && 
	    		 $("#location_c1").val() != '市辖区'&&
		    	 $("#location_c1").val() != '市辖县'&&
	    		 $("#location_a1").val() != '请选择'){
	    		  if($("#location_a1").val() == '市辖区'){
	    			  $("#workAddr").val($("#location_p1").val() + $("#location_c1").val() +  $("#workDetailAddr").val()); 
	    		  }else{
	    			  $("#workAddr").val($("#location_p1").val() + $("#location_c1").val() +  $("#location_a1").val() +  $("#workDetailAddr").val());
	    		  } 
		      }else if($("#location_p1").val() != '请选择' && 
			    		 ($("#location_c1").val() == '市辖区'||
				    	 $("#location_c1").val() == '市辖县')&&
			    		 $("#location_a1").val() != '请选择'){
		    	  $("#workAddr").val($("#location_p1").val() + $("#location_a1").val() +  $("#workDetailAddr").val()); 
		      }
	    	   */
	    	  if($("#location_p").val() == '请选择' || $("#location_c").val() == '请选择' || $("#location_a").val() == '请选择'){
	    		  $("#location_p").val("");
	    		  $("#location_c").val("");
	    		  $("#location_a").val("");
	    	  }
	    	  
	    	  if($("#location_p1").val() == '请选择' || $("#location_c1").val() == '请选择' || $("#location_a1").val() == '请选择'){
	    		  $("#location_p1").val("");
	    		  $("#location_c1").val("");
	    		  $("#location_a1").val("");
	    	  } 
	    	  
	    	  var submitedFlag = false;//重复提交标示
	    	  if(!$("#yunYinShangcs_form_id").valid()){//表单验证
	      		  return;
	      	  }else{
	      		$('#yunYinShangcs_form_id').submit();
	      	  }
	      	  /* if(!submitedFlag()){//是否已提交过了
		          document.getElementById('yunYinShangcs_form_id').submit();
	      	  } */
	      }
	      
          
          
		$(document).ready(function() {
			$("#yunYinShangcs_form_id").validate();
			$.validator.addMethod("isIdCardNo", function(value, element) {
				return this.optional(element) || isIdCardNo(value);
			}, "<font color='#E47068'>格式错误</font>");
		});
		
		function isIdCardNo(num) {
			var checkNum = /^[A-Za-z0-9]+$/;
			if (num.length == 15 || num.length == 18 || num.length == 8
					|| num.length == 9) {
				if (checkNum.test(num)) {
					if (num.length == 15 || num.length == 18) {
						var idflag = checkIdCardNo(num);
					}

					if (num.length == 8 || num.length == 9) {
						var passportflag = checkPassport(num);
					}

					return idflag || passportflag;
				} else {
					return false;
				}

			} else {
				return false;
			}
		}
		function checkPassport(number) {
			var str = number;
			//在JavaScript中，正则表达式只能使用"/"开头和结束，不能使用双引号
			var Expression = /(P\d{7})|(G\d{8})/;
			var objExp = new RegExp(Expression);
			if (objExp.test(str) == true) {
				return true;
			} else {
				return false;
			}
		}
		//增加身份证验证
		function checkIdCardNo(num) {
			var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
					5, 8, 4, 2, 1);
			var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5",
					"4", "3", "2");
			var varArray = new Array();
			var intValue;
			var lngProduct = 0;
			var intCheckDigit;
			var intStrLen = num.length;
			var idNumber = num;
			// initialize
			if ((intStrLen != 15) && (intStrLen != 18)) {
				return false;
			}
			// check and set value
			for (i = 0; i < intStrLen; i++) {
				varArray[i] = idNumber.charAt(i);
				if (((varArray[i] < 0) || (varArray[i] > 9)) && (i != 17)) {
					return false;
				} else if (i < 17) {
					varArray[i] = varArray[i] * factorArr[i];
				}
			}
			if (intStrLen == 18) {
				//check date
				var date8 = idNumber.substring(6, 14);
				if (isDate8(date8) == false) {
					return false;
				}
				// calculate the sum of the products
				for (i = 0; i < 17; i++) {
					lngProduct = lngProduct + varArray[i];
				}
				// calculate the check digit
				intCheckDigit = parityBit[lngProduct % 11];
				// check last digit
				if (varArray[17].toLowerCase() == "x") {
					varArray[17] = "X";
				}

				if (varArray[17] != intCheckDigit) {
					return false;
				}
			} else { //length is 15
				//check date
				var date6 = idNumber.substring(6, 12);
				if (isDate6(date6) == false) {
					return false;
				}
			}
			return true;
		}
		function isDate6(sDate) {
			if (!/^[0-9]{6}$/.test(sDate)) {
				return false;
			}
			var year, month, day;
			year = sDate.substring(0, 4);
			month = sDate.substring(4, 6);
			if ((year < 1700) || (year > 2500))
				return false
			if ((month < 1) || (month > 12))
				return false
			return true
		}

		function isDate8(sDate) {
			if (!/^[0-9]{8}$/.test(sDate)) {
				return false;
			}
			var year, month, day;
			year = sDate.substring(0, 4);
			month = sDate.substring(4, 6);
			day = sDate.substring(6, 8);
			var iaMonthDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ]
			if ((year < 1700) || (year > 2500))
				return false
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0))
				iaMonthDays[1] = 29;
			if ((month < 1) || (month > 12))
				return false
			if ((day < 1) || (day > iaMonthDays[month - 1]))
				return false
			return true
		}
		
	</script>
</body>
</html>
