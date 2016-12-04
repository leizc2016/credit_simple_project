<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title></title>
	<script src="${ctx}/static/idcard_validation.js" type="text/javascript"></script>
</head>

<body>
	<div class="query-content" role="tabpanel">
<div class="query-content" role="tabpanel">

        <!-- Nav tabs -->
        <ul role="tablist" class="nav nav-tabs">
          <li class="tab1 active" role="presentation"><a href="#">征信查询</a></li>
          <li class="tab2" role="presentation"><a href="${ctx}/creditreport/history">查询历史</a></li>
        </ul>
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
                  <div class="title-line">基本信息</div>
                  <div class="row">
                    <div class="col-xs-3">
                      <span class="icon icon-addr"></span>
                      <span>常用消费地区：<span>${bcHisYLZC.commonLocation}</span></span>
                    </div>
                    <div class="col-xs-5">
                      <span class="icon icon-addr"></span>
                      <span>最近消费地区：<span>${bcHisYLZC.recentLocation}</span></span>
                    </div>
                    <div class="col-xs-4">
                      <span class="icon icon-time"></span>
                      <span>报告时间：<span><fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd HH:mm:ss" /></span></span>
                    </div>
                  </div>
                  <!-- 综合评价 -->
                  <table>
                  	<tr>
                  		<td width="600px;">
                  		   <div style="margin-top:0px;" class="title-line offset-b20 offset-t30">综合评价</div>
                  		</td>
                  		<td width="600px;">
                  		   <div class="title-line offset-b20 offset-t30">帐户明细</div>
		                   <div class="clearfix table-title-lg">
	                         <span>近12个月帐户明细</span>
	                       </div>
                  		</td>
                  	</tr>
                  	<tr>
                  		<td style="font-size:13px;">
                  			 <div style="text-align:left;margin-left:70px;margin-right:70px;margfont-weight:bold;">
	                  			<font style="font-weight:bold;TEXT-DECORATION:underline">${bcHisYLZC.bankCardNum}</font>
	                  			银行卡属于<font style="font-weight:bold;TEXT-DECORATION:underline">${bcHisYLZC.bankCardType}</font><br><br>
	                  			银行卡持卡人是<font style="font-weight:bold;TEXT-DECORATION:underline">${bcHisYLZC.cstScoreStr}</font>
	                  			属于 <font style="font-weight:bold;TEXT-DECORATION:underline">${bcHisYLZC.chvScoreStr}</font>,<br><br>
	                  			<!--<c:choose>
	                  			  <c:when test="${bcHisYLZC.bankCardType eq '信用卡'}">
	                  			  	 从过往的行为来看，该持卡人具有 <font style="font-weight:bold;TEXT-DECORATION:underline">${bcHisYLZC.cotScore}</font><br><br>
	                  			  </c:when>
	                  			</c:choose> -->
	                  			 从消费偏好来看，该卡持有人偏向 <font style="font-weight:bold;TEXT-DECORATION:underline">${bcHisYLZC.cnpScoreStr}</font>，<br><br>
	                  			 从使用频率来看， <font style="font-weight:bold;TEXT-DECORATION:underline">${bcHisYLZC.wlpScoreStr}</font>。
                  			</div>
                  			<br>
                  			<div>
                  				<font style="font-weight:bold;font-size:18px;text-align:left;margin-left:70px;">综合评分：${bcHisYLZC.summaryScore}</font>
                 			</div>
                  		</td>
                  		<td>
                  			  <table class="table table-bordered table-center">
		                      <tbody>
		                      	<tr>
		                      		<td></td>
		                      		<td>金额</td>
		                      		<td>笔数</td>
		                      	</tr>
		                     	<tr>
		                          <td width="15%">储蓄</td>
		                          <td width="35%">${bcHisYLZC.depositMoney12MonthsDetail}</td>
		                          <td width="15%">${bcHisYLZC.depositCount12MonthsDetail}</td>
		                        </tr>
		                        <tr class="tr2">
		                          <td width="15%">消费</td>
		                          <td width="35%">${bcHisYLZC.consumeMoney12MonthsDetail}</td>
		                          <td width="15%">${bcHisYLZC.consumeCount12MonthsDetail}</td>
		                        </tr>
		                        <tr class="tr1">
		                          <td width="15%">取现</td>
		                          <td width="35%">${bcHisYLZC.drawMoney12MonthsDetail}</td>
		                          <td width="15%">${bcHisYLZC.drawCount12MonthsDetail}</td>
		                        </tr>
		                        <tr class="tr1">
		                          <td width="15%">转入</td>
		                          <td width="35%">${bcHisYLZC.transIn12MonthsDetail}</td>
		                          <td width="15%">${bcHisYLZC.transInCount12MonthsDetail}</td>
		                        </tr>
		                        <tr class="tr1">
		                          <td width="15%">转出</td>
		                          <td width="35%">${bcHisYLZC.transOut12MonthsDetail}</td>
		                          <td width="15%">${bcHisYLZC.transOutCount12MonthsDetail}</td>
		                        </tr>
		                      </tbody>
		                    </table>
                  		</td>
                  	</tr>
                  </table>
                  
                  <table>
                  	<tr>
                  		<td width="600px;">
                  		   <div class="title-line offset-b20 offset-t30">风险矩阵</div>
                  		</td>
                  		<td width="600px;">
                  		   <div class="title-line offset-b20 offset-t30">指标说明</div>
                  		</td>
                  	</tr>
                  	<tr>
                  		<td style="font-size:15px;">
                  		  <!-- ECharts单文件引入 -->
						    <script src="${ctx}/static/echarts/echarts-all.js" type="text/javascript" charset="utf-8"></script>
						    
						    <c:choose>
	                  			  <c:when test="${bcHisYLZC.bankCardType eq '借记卡'}">
				                  		<script type="text/javascript">
									        var option = { //可以去官网上根据每个案例不同的option去写各种图形
									            title: {   //标题
									                text: ''
									            },
									            tooltip: {   //提示框，鼠标悬浮交互时的信息提示
									                show:true,
									                trigger: 'axis'
									            },
									            /* legend: {    //图例，每个图表最多仅有一个图例
									                x: 'center',
									                data: ['风险矩阵']
									            }, */
									            polar: [{    //极坐标
									                indicator: [{text: '逾期风险',max: 6},
									                            {text: '消费趋势',max: 6},
									                            {text: '消费能力',max: 6},
									                            {text: '消费自由',max: 6}
									                           ],
									                radius: 100,     
									                startAngle: 120  // 改变雷达图的旋转度数
									            }],
									            
									            series: [{   // 驱动图表生成的数据内容数组，数组中每一项为一个系列的选项及数据
									                name: '风险评分',
									                type: 'radar',
									                itemStyle: {//图形样式，可设置图表内图形的默认样式和强调样式（悬浮时样式）：
									                    normal: {
									                        areaStyle: {
									                            type: 'default'
									                        }
									                    }
									                },
									                data: [{
									                    value: [],      //外部加载，也可以通过ajax去加载外部数据。
									                    name: '',
									                    itemStyle:{
									                    	normal: {
									                            color: function(params) {
									                                var value = params.data
									                                return isNaN(value) 
									                                       ? undefined
									                                       : (value >= 3 ? 'green' : 'red')
									                            }
									                    	}
									                    }
									                }]
									            }]
									        };
									        $(function() {
									            option.series[0].data[0].value=['${bcHisYLZC.rskScoreInt}','${bcHisYLZC.cntScore}','${bcHisYLZC.cnaScore}','${bcHisYLZC.dsiScoreInt}'];  // 加载数据到data中
									            option.series[0].data[0].name ='风险矩阵';
									            var myChart = echarts.init(document.getElementById('chartdiv')); 
									            myChart.setOption(option, true);   //为echarts对象加载数据
									        });
									    </script> 
	                  			  </c:when>
	                  		</c:choose>
	                  		
	                  		<c:choose>
	                  			  <c:when test="${bcHisYLZC.bankCardType eq '信用卡'}">
				                  		<script type="text/javascript">
									        var option = { //可以去官网上根据每个案例不同的option去写各种图形
									            title: {   //标题
									                text: ''
									            },
									            tooltip: {   //提示框，鼠标悬浮交互时的信息提示
									                show:true,
									                trigger: 'axis'
									            },
									            /* legend: {    //图例，每个图表最多仅有一个图例
									                x: 'center',
									                data: ['风险矩阵']
									            }, */
									            polar: [{    //极坐标
									                indicator: [{text: '逾期风险',max: 6},
									                            {text: '消费趋势',max: 6},
									                            {text: '消费能力',max: 6},
									                            {text: '消费自由',max:6},
									                            {text: '套现风险',max:6},
									                           ],
									                radius: 100,     
									                startAngle: 120  // 改变雷达图的旋转度数
									            }],
									            
									            series: [{   // 驱动图表生成的数据内容数组，数组中每一项为一个系列的选项及数据
									                name: '风险评分',
									                type: 'radar',
									                itemStyle: {//图形样式，可设置图表内图形的默认样式和强调样式（悬浮时样式）：
									                    normal: {
									                        areaStyle: {
									                            type: 'default'
									                        }
									                    }
									                },
									                data: [{
									                    value: [],      //外部加载，也可以通过ajax去加载外部数据。
									                    name: '',
									                    itemStyle:{
									                    	normal: {
									                            color: function(params) {
									                                var value = params.data
									                                return isNaN(value) 
									                                       ? undefined
									                                       : (value >= 3 ? 'green' : 'red')
									                            }
									                    	}
									                    }
									                }]
									            }]
									        };
									        $(function() {
									            option.series[0].data[0].value=['${bcHisYLZC.rskScoreInt}','${bcHisYLZC.cntScore}','${bcHisYLZC.cnaScore}','${bcHisYLZC.dsiScoreInt}','${bcHisYLZC.cotScoreInt}'];  // 加载数据到data中
									            option.series[0].data[0].name ='风险矩阵';
									            var myChart = echarts.init(document.getElementById('chartdiv')); 
									            myChart.setOption(option, true);   //为echarts对象加载数据
									        });
									    </script> 
	                  			  </c:when>
	                  		</c:choose>
						    
                  		  <div id="chartdiv" style="width:495px; height:250px;margin:5px auto;"></div>
                  		</td>
                  		<td style="font-size:13px;">
                  			 <c:choose>
	                  	     	<c:when test="${bcHisYLZC.bankCardType eq '信用卡'}">
                  			 		<span style="font-weight:bold;">套现风险:</span>代表在未来6个月内发生套现风险的得分指标,数值越高，代表套现风险越小。<br><br>
                  			 	</c:when>
	                  		 </c:choose>
             			     <span style="font-weight:bold;">逾期风险:</span>代表在未来6个月内发生90天以上逾期风险的得分指标，数值越高,代表出现逾期的风险越低。<br><br>
             			     <span style="font-weight:bold;">消费趋势:</span>代表最近3个月与过去3个月相比消费变动趋势与全行业对比情况，数值越高，代表相比全行业越显著。<br><br>
             			     <span style="font-weight:bold;">消费能力:</span>代表3个月内的消费能力与全行业对比情况，数值越高，代表相比全行业越显著。<br><br>
             			     <span style="font-weight:bold;">消费自由:</span>代表最近6个月内持卡人在这张卡上的消费习惯，数值越高，代表这张卡上承载了越多的非生活必须类消费，自由度越高。<br><br><br><br>
                  			  <!-- <table class="table table-bordered table-center" style="font-size:13px;">
		                      <tbody>
		                      	<tr>
			                      <td width="20%" style="font-weight:bold;">风险分类:</td>
			                      <td style="text-align:left">决策系统预估未来6个月内发生90天以上逾期风险的得分指标，得分越高代表出现逾期的风险越高。</td>
		                      	</tr>
		                     	<tr>
		                          <td style="font-weight:bold;">消费趋势:</td>
		                      	  <td style="text-align:left;">代表最近3个月与过去3个月相比消费变动趋势与全行业对比情况，数值越高，代表相比全行业越显著。</td>
		                        </tr>
		                        <tr class="tr2">
		                          <td style="font-weight:bold;">消费能力:</td>
		                      	  <td style="text-align:left;">代表3个月内的消费能力与全行业对比情况，数值越高代表相比全行业越显著。</td>
		                        </tr>
		                        <tr class="tr1">
		                          <td style="font-weight:bold;">消费自由:</td>
		                      	  <td style="text-align:left;">代表最近6个月内持卡人在这张卡上的消费习惯，数值越高说明这张卡上承载了越多的非生活必须类消费，自由度越高。</td>
		                        </tr>
		                      </tbody>
		                    </table> -->
                  		</td>
                  	</tr>
                  </table>
                </div>
              </div>

            </div>
          </div><!-- /tab2 end -->
        </div>
	  <div style="font-weight:bold;text-align:center;">备注：以上评分、评级仅供参考，不能代替机构的放贷决策!</font></div>
      </div>
        <div class="text-left">
        	<button class="btn btn-primary btn-sm btn-search" onclick='document.execCommand("print")' >打 &nbsp; 印</button>
        </div>       

      </div>
      <script type="text/javascript">
          if('${message_info}'){
        	  alert('${message_info}');
          }
          
      </script>  
</body>
</html>
