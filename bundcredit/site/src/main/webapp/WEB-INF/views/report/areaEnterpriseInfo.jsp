<%@page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
	<title></title>
	<!-- ECharts单文件引入 -->
	<script src="${ctx}/static/echarts/echarts-all.js" type="text/javascript" charset="utf-8"></script>
</head>

<body>
	<div class="query-content" role="tabpanel">
		<div class="query-content" role="tabpanel">

			<!-- Nav tabs -->
			<ul role="tablist" class="nav nav-tabs">
				<li class="tab1 active" role="presentation"><a href="#">征信查询</a></li>
				<li class="tab2" role="presentation"><a
					href="${ctx}/creditreport/history">查询历史</a></li>
			</ul>
			<div style="text-align:right;"><a onclick="history.go(-1)">返 回</a></div>
			<!-- Tab panes -->
			<div class="tab-content">
				<div class="font-mask"></div>
				<div id="tab2" class="tab-pane active" role="tabpanel">
					<div class="tabs-left sub-content" role="tabpanel">

						<!-- Nav tabs -->
						<ul role="tablist" class="nav nav-tabs">${manuAllowed}
						</ul>

						<!-- Tab panes -->
						<div class="tab-content">
							<div id="tab-a" class="tab-pane active" role="tabpanel">

								<div class="title-line offset-b30 offset-t30">报告内容</div>
								<div class="row">
									<div class="col-xs-4">
										<span class="icon icon-name"></span> <span>统计区域：<span>上海市某行政区</span></span>
									</div>
									<div class="col-xs-5">
										<span class="icon icon-time"></span> <span>报告时间：<span><fmt:formatDate
													value="<%=new Date()%>" pattern="yyyy-MM-dd HH:mm:ss" /></span></span>
									</div>
								</div>

								<div style="overflow: auto">
								
									<div style="float: left; width: 50%">
										<script type="text/javascript">
										var option99 = {
											    tooltip : {
											        trigger: 'axis'
											    },
											    toolbox: {
											        show : false,
											        feature : {
											            mark : {show: true},
											            dataView : {show: true, readOnly: false},
											            magicType: {show: true, type: ['line', 'bar']},
											            restore : {show: true},
											            saveAsImage : {show: true}
											        }
											    },
											    calculable : true,
											    legend: {
											        data:['成交量（万元）','综合利率']
											    },
											    xAxis : [
											        {
											            type : 'category',
											            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
											        }
											    ],
											    yAxis : [
											        {
											            type : 'value',
											            name : '成交量',
											            axisLabel : {
											                formatter: '{value} 万元'
											            }
											        },
											        {
											            type : 'value',
											            name : '利率',
											            axisLabel : {
											                formatter: '{value} %'
											            }
											        }
											        
											    ],
											    series : [

											        {
											            name:'成交量',
											            type:'bar',
											            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
											        },
											        {
											            name:'利率',
											            type:'line',
											            yAxisIndex: 1,
											            data:[2.0, 2.2, 3.3, 4.5, 6.3, 10.2, 20.3, 23.4, 23.0, 16.5, 12.0, 6.2]
											        }
											    ]
											};
								        $(function() {
								            //option8.series[0].data[0].value=['4','5','5','4','5','5'];  // 加载数据到data中
								            //option.series[0].data[0].name ='风险矩阵';
								            var myChart = echarts.init(document.getElementById('chartdiv99')); 
								            myChart.setOption(option99, true);   //为echarts对象加载数据
								        });
								    </script> 
							    	
							    	<div style="text-align: left; margin-left: 0px; margin-right: 70px; margfont-weight: bold; font-size: 13px;" class="offset-t30">
							    	(<b>某某区</b>)，(<b>投资管理</b>)类企业的整体情况如下:<br></div>
							    	<div id="chartdiv99" style="width:100%; height:380px;margin:5px auto;"></div>
							    	
							    </div>
							    
							    <div style="float: left; width: 50%">
										<script type="text/javascript">
								        var option8 = { //可以去官网上根据每个案例不同的option去写各种图形
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
								                indicator: [{text: '投资指数',max: 6},
								                            {text: '地域指数',max: 6},
								                            {text: '利率指数',max: 6},
								                            {text: '期限指数',max: 6},
								                            {text: '流动指数',max: 6},
								                            {text: '标的指数',max: 6}
								                           ],
								                radius: 100,     
								                startAngle: 90  // 改变雷达图的旋转度数
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
								            option8.series[0].data[0].value=['4','5','5','4','5','5'];  // 加载数据到data中
								            //option.series[0].data[0].name ='风险矩阵';
								            var myChart = echarts.init(document.getElementById('chartdiv9')); 
								            myChart.setOption(option8, true);   //为echarts对象加载数据
								        });
								    </script> 
							    	
							    	<div style="text-align: left; margin-left: 0px; margin-right: 70px; margfont-weight: bold; font-size: 13px;" class="offset-t30">
							    	(<b>某某区</b>)，(<b>投资管理</b>)类企业的风险矩阵示意图如下:<br></div>
							    	<div id="chartdiv9" style="width:100%; height:380px;margin:5px auto;"></div>
							    	
							    </div>
							    
					
								
									<div style="float: left; width: 50%">
										<div
											style="text-align: left; margin-left: 0px; margin-right: 70px; margfont-weight: bold; font-size: 13px;"
											class="offset-t30">
											目前(<b>某某区</b>)共有(<b>50</b>)家企业包含(<b>投资管理</b>)列表在工商登记注册，工商注册地在区域内的企业分布如下:<br>
											<iframe id="Main" frameborder="0" scrolling="no" width="100%"
												height="380px" src="${ctx}/static/heatmap/op.html">
											</iframe>
										</div>
									</div>

									<div style="float: left; width: 50%">
										<div
											style="text-align: left; margin-left: 0px; margin-right: 70px; margfont-weight: bold; font-size: 13px;"
											class="offset-t30">
											目前(<b>某某区</b>)共有(<b>43</b>)家企业包含(<b>投资管理</b>)列表在本区域经营，经营地在区域内的企业分布如下:<br>
											<iframe id="Main" frameborder="0" scrolling="no" width="100%"
												height="380px" src="${ctx}/static/heatmap/reg.html">
											</iframe>
										</div>
									</div>

									<div style="float: left; width: 50%">
										<div
											style="text-align: left; margin-left: 0px; margin-right: 70px; margfont-weight: bold; font-size: 13px;"
											class="offset-t30">
											目前在(<b>某某区</b>)内经营或注册的上述企业共有(<b>67</b>)家，这些企业实际经营地的分布如下:<br>
											<iframe id="Main" frameborder="0" scrolling="no" width="100%"
												height="380px" src="${ctx}/static/heatmap/op.html">
											</iframe>
										</div>
									</div>

									<div style="float: left; width: 50%">
										<div
											style="text-align: left; margin-left: 0px; margin-right: 70px; margfont-weight: bold; font-size: 13px;"
											class="offset-t30">
											经过风险排查，目前注册在(<b>某某区</b>)的企业，有一定潜在风险的企业共有(<b>33</b>)家，注册地的分布如下:<br>
											<iframe id="Main" frameborder="0" scrolling="no" width="100%"
												height="380px" src="${ctx}/static/heatmap/reg.html">
											</iframe>
										</div>
									</div>

									<div style="float: left; width: 50%">
										<div
											style="text-align: left; margin-left: 0px; margin-right: 70px; margfont-weight: bold; font-size: 13px;"
											class="offset-t30">
											经过风险排查，目前实际经营地在(<b>某某区</b>)共有(<b>50</b>)的企业，有一定潜在风险的企业共有(<b>36</b>)家，其实际经营地的分布如下:<br>
											<iframe id="Main" frameborder="0" scrolling="no" width="100%"
												height="380px" src="${ctx}/static/heatmap/op.html">
											</iframe>
										</div>
									</div>

									<div style="float: left; width: 50%">
										<div
											style="text-align: left; margin-left: 0px; margin-right: 70px; margfont-weight: bold; font-size: 13px;"
											class="offset-t30">
											经过风险排查，目前实际经营地和注册地在(<b>某某区</b>)的企业，有一定潜在风险的企业共有(<b>32</b>)家，其实际经营地的分布如下:<br>
											<iframe id="Main" frameborder="0" scrolling="no" width="100%"
												height="380px" src="${ctx}/static/heatmap/reg.html">
											</iframe>
										</div>
									</div>
									
	

								</div>

							</div>
						</div>

					</div>
				</div>
				<!-- /tab2 end -->
			</div>

		</div>
		<div class="text-left">
			<button class="btn btn-primary btn-sm btn-search"
				onclick='document.execCommand("print")'>打 &nbsp; 印</button>
		</div>

	</div>
	<script type="text/javascript">
		if ('${message_info}') {
			alert('${message_info}');
		}

		$(document).ready(function() {
			$("#full_form_id").validate();
		});

		var submited_flag = false;//重复提交标示
		function submitForm() {
			if (!$("#full_form_id").valid()) {//表单验证
				return;
			}
			if (!submitedFlag()) {//是否已提交过了
				document.getElementById('full_form_id').submit();
			}
		}
	</script>
</body>
</html>