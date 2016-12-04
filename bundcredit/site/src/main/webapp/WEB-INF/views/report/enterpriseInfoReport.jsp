<%@ page import="java.util.Date"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title></title>
	<script src="${ctx}/static/idcard_validation.js" type="text/javascript"></script>
	<!-- ECharts单文件引入 -->
	<script src="${ctx}/static/echarts/echarts-all.js" type="text/javascript" charset="utf-8"></script>
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
			  <div style="text-align:right;"><a onclick="history.go(-1)">返 回</a></div>
              <!-- Tab panes -->
              <div class="tab-content">
                <div id="tab-a" class="tab-pane active" role="tabpanel">
                  <div class="title-line">报告内容</div>
                  <div class="row">
                    <div class="col-xs-3">
                      <span class="icon icon-addr"></span>
                      <span>所在省市：<span>${businessInfoBean.city}</span></span>
                    </div>
                    <div class="col-xs-4">
                      <span class="icon icon-time"></span>
                      <span>报告时间：<span><fmt:formatDate value="<%=new Date()%>" pattern="yyyy-MM-dd HH:mm:ss" /></span></span>
                    </div>
                   <!--   <div class="col-xs-5">
                      <span class="icon icon-edit"></span>
                      <span>数据最后更新时间：<span><fmt:formatDate value="${enterpriseInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></span></span>
                    </div>-->
                  </div>
                   <!-- 公司简介 -->
                  <div class="title-line offset-b20 offset-t30">公司简介</div>
                  <div style="text-align:left;">
                  		${businessInfoBean.name}成立于2007年3月，注册资本3000万元，主要从事投资管理、投资咨询、受托资产管理业务，公司建立了与行业发展相适宜的优势激励机制，<br>
                  		同时公司吸收、培养了一支高度专业化的人才队伍,在业内具有深厚的资源积累,并且公司建立了一套成熟服务体系，为公司的持续发展和规范运作奠定了坚实的基础。 <br>
                  		核心管理团队持有公司相当比例股权。公司坚持“团队协作、专业执信、稳健回报、价值共赢”的核心价值观，以专业化经营、规范化运作和良好的投资业绩，赢得市场认可。
                  	   <div style="text-align:right;"><a>备注</a></div>
                  </div>
                  <!-- 工商信息 -->
                  <div class="title-line offset-b20 offset-t30">工商信息</div>
                   <div class="offset-t10">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">基本信息</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                      <tbody>
                     	<tr>
                          <td width="15%">注册号</td>
                          <td width="35%">${businessInfoBean.regID}</td>
                          <td width="15%">名称</td>
                          <td width="35%">${businessInfoBean.name}</td>
                        </tr>
                        <tr class="tr2">
                          <td>类型</td>
                          <td>${businessInfoBean.type}</td>
                          <td>法定代表人</td>
                          <td>${businessInfoBean.legalPerson}</td>
                        </tr>
                        <tr class="tr1">
                          <td>注册资本</td>
                          <td>${businessInfoBean.registeredCapital}</td>
                          <td>成立日期</td>
                          <td>${businessInfoBean.setupDate}</td>
                        </tr>
                        <tr class="tr2">
                          <td>住所</td>
                          <td>${businessInfoBean.address}</td>
                          <td>登记状态</td>
                          <td>${businessInfoBean.state}</td>
                        </tr>
                        <tr class="tr1">
                          <td>经营期限</td>
                          <td>${businessInfoBean.operatingPeriod}</td>
                        </tr>
                        <tr class="tr1">
                          <td>登记机关</td>
                          <td>${businessInfoBean.bureau}</td>
                          <td>核准日期</td>
                          <td>${businessInfoBean.awardDate}</td>
                        </tr>
                        <tr class="tr2">
                          <td>经营范围</td>
                          <td colspan="3" style="text-align:left;">${businessInfoBean.scope}</td>
                        </tr>
                        <tr class="tr1">
                          <td>公司网址</td>
                          <td>http://www.touziguanli.com</td>
                          <td>ICP备案号</td>
                          <td>沪ICP备10012900号</td>
                        </tr>
                        <tr class="tr1">
                          <td>办公场地租赁期限</td>
                          <td>2012-03-25至2032-03-24</td>
                          <td>50岁以上高龄用户占比</td>
                          <td>25%</td>
                        </tr>
                        <tr class="tr1">
                          <td>公司是否有负面信息</td>
                          <td>有</td>
                          <td>关联公司是否有负面信息</td>
                          <td>无</td>
                        </tr>
                      </tbody>
                    </table>
                  </div><!-- /table row end -->
                  <!-- 股东信息 -->
                  <div class="offset-t30" id="stockholder">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">股东信息</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">股东类型</th>
                          <th width="140">股东</th>
                          <th width="120">证件类型</th>
                          <th width="120">证件号码</th>
                          <th width="120">出资额（实缴)</th>
                          <th width="120">出资日期</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${businessInfoBean.partnerInformationBeans}" var="partnerInfo">
						<tr class="tr1">
							<td>${partnerInfo.partnerType}</td>
							<td>${partnerInfo.partnerName}</td>
							<td></td>
							<td></td>
							<td></td>
							<td></td>
						</tr>
						</c:forEach>
                      	</tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
 
                  <!-- 变更信息 -->
                  <div class="offset-t30" id="changeHistory">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">变更信息</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                        <tr>
                          <th width="85">变更日期</th>
                          <th width="140">变更项目</th>
                          <th width="120">变更前</th>
                          <th width="120">变更后</th>
                        </tr>
                    </table>
                  </div>
                  <!-- /table row end -->
                  
                  
                  <!-- 组织机构代码 -->
                  <div class="offset-t30" id="organizationCode">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">组织机构代码</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">机构代码</th>
                          <th width="140">机构名称</th>
                          <th width="120">登记证号</th>
                          <th width="120">有效期</th>
                        </tr>
                        </thead>
                        <tbody>
                        
						<tr class="tr1">
							<td>${orgCodeBean.orgCode}</td>
							<td>${orgCodeBean.orgName}</td>
							<td>${orgCodeBean.orgRegID}</td>
							<td>自${orgCodeBean.startDate}至${orgCodeBean.endDate}</td>
						</tr>
                      	</tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
                  <div class="title-line offset-b20 offset-t30">公司图谱</div>
                  <script type="text/javascript">
                      option7 = {
               		    title : {
               		        text: '',
               		        x:'right',
               		        y:'bottom'
               		    },
               		    tooltip : {
               		        trigger: 'item',
               		        formatter: '{b}'
               		    },
               		    legend: {
               		        x: 'left',
               		        data:['股东','投资']
               		    },
               		    series : [
               		        {
               		            type:'force',
               		            name : "公司图谱",
               		            ribbonType: false,
               		            categories : [
               		                {
               		                    name: '公司图谱'
               		                },
               		                {
               		                    name: '投资',
               		                    symbol:'rectangle',
               		                 	symbolSize:[80,15]
               		                },
               		                {
               		                    name:'股东',
               		                 	symbol:'rectangle',
               		                 	symbolSize:[80,15]
               		                }
               		            ],
               		            itemStyle: {
               		                normal: {
               		                    label: {
               		                        show: true,
               		                        textStyle: {
               		                            color: '#333'
               		                        }
               		                    },
               		                    nodeStyle : {
               		                        brushType : 'both',
               		                        borderColor : 'rgba(255,215,0,0.4)',
               		                        borderWidth : 1
               		                    }
               		                },
               		                emphasis: {
               		                    label: {
               		                        show: false
               		                        // textStyle: null      // 默认使用全局文本样式，详见TEXTSTYLE
               		                    },
               		                    nodeStyle : {
               		                        //r: 30
               		                    },
               		                    linkStyle : {}
               		                }
               		            },
               		            //minRadius : 15,
               		            //maxRadius : 25,
               		            gravity: 1.1,
               		            scaling: 1.2,
               		            draggable: false,
               		            linkSymbol: 'arrow',
               		         	symbol:'rectangle',
               		            steps: 10,
               		            coolDown: 0.9,
               		            //preventOverlap: true,
               		            nodes:[
               		                {
               		                    category:0, name: '${businessInfoBean.name}', value : 3,symbolSize:[150,15],initial:[400,200],fixX:true,fixY:true,draggable:true
               		                   /*  itemStyle: {
               		                        normal: {
               		                            label: {
               		                                position: 'outer',
               		                                textStyle: {
               		                                    color: 'black'
               		                                }
               		                            }
               		                        }
               		                    } */
               		                },
               		                {category:1, name: '天蓝蓝投资',value : 5,initial:[400,50],fixX:true,fixY:true,draggable:true},
               		                {category:1, name: '全星投资',value : 5,initial:[600,80],fixX:true,fixY:true,draggable:true},
               		             	{category:1, name: '协泰投资',value : 5,initial:[200,80],fixX:true,fixY:true,draggable:true},
               		             	{category:2, name: '${businessInfoBean.partnerInformationBeans[0].partnerName}',symbolSize:[85,15],initial:[600,320],fixX:true,fixY:true,draggable:true},
               		             	{category:2, name: '${businessInfoBean.partnerInformationBeans[1].partnerName}',symbolSize:[85,15],initial:[400,350],fixX:true,fixY:true,draggable:true},
               		             	/* {category:2, name: '${businessInfoBean.partnerInformationBeans[2].partnerName}',symbolSize:[85,15],initial:[200,320],fixX:true,fixY:true,draggable:true},
               		             	{category:2, name: '${businessInfoBean.partnerInformationBeans[3].partnerName}',symbolSize:[85,15],initial:[200,200],fixX:true,fixY:true,draggable:true},
	               		            {category:2, name: '${businessInfoBean.partnerInformationBeans[4].partnerName}',symbolSize:[85,15],initial:[600,600],fixX:true,fixY:true,draggable:true}, */
	               		            ],
               		            links : [
               		                {source : '${businessInfoBean.name}', target : '天蓝蓝投资', weight : 1, itemStyle: {
               		                    normal: {
               		                        width: 1.5,
               		                        color: 'red'
               		                    }
               		                }},
               		             	{source : '${businessInfoBean.name}', target : '全星投资', weight : 2, itemStyle: {
            		                    normal: {
            		                        width: 1.5,
            		                        color: 'red'
            		                    }
            		                }},
            		                {source : '${businessInfoBean.name}', target : '协泰投资', weight : 3, itemStyle: {
            		                    normal: {
            		                        width: 1.5,
            		                        color: 'red'
            		                    }
            		                }},
               		                {source : '${businessInfoBean.partnerInformationBeans[0].partnerName}', target : '${businessInfoBean.name}', weight : 4},
               		             	{source : '${businessInfoBean.partnerInformationBeans[1].partnerName}', target : '${businessInfoBean.name}', weight : 5},
               		          		/* {source : '${businessInfoBean.partnerInformationBeans[2].partnerName}', target : '${businessInfoBean.name}', weight : 6},
               		       			{source : '${businessInfoBean.partnerInformationBeans[3].partnerName}', target : '${businessInfoBean.name}', weight : 7},
               		    			{source : '${businessInfoBean.partnerInformationBeans[4].partnerName}', target : '${businessInfoBean.name}', weight : 8}, */
               		            ]
               		        }
               		    ]
               		};
			        $(function() {
			        	/* Array pbeans = '${businessInfoBean.partnerInformationBeans}';
			        	alert(pbeans.length);
			        	if(pbeans != null){
			        		for(var i=0;i<pbeans.size();i++){
			        			if(i==0){
			        				option7.series[0].nodes[4].name = '${pbeans[i].partnerName}'; 
			        			}
			        		}
			        	} */
			            //option.series[0].data[0].value=['3','6','2','5'];  // 加载数据到data中
			            //option.series[0].data[0].name ='风险矩阵';
			            var myChart = echarts.init(document.getElementById('chartdiv7')); 
			            myChart.setOption(option7, true);   //为echarts对象加载数据
			        });
				  </script>
                  <div id="chartdiv7" style="width:800px; height:400px;margin:5px auto;"></div> 
                  
                  <div class="title-line offset-b20 offset-t30">运营数据</div> 
                  <div style="text-align:center;">
                  	<table>
                  		<tr>
                  		  <td width="2100px" style="text-align:center;">
                  		  	<script type="text/javascript">
		                  		  option1 = {
		                  			    title : {
		                  			        text: '成交金额',
		                  			    },
		                  			    tooltip : {
		                  			        trigger: 'axis'
		                  			    },
		                  			    legend: {
		                  			        data:['企业自主报送(万元)','第三方机构(万元)']
		                  			    },
		                  			    calculable : true,
		                  			    xAxis : [
		                  			        {
		                  			            type : 'category',
		                  			            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
		                  			        }
		                  			    ],
		                  			    yAxis : [
		                  			        {
		                  			            type : 'value'
		                  			        }
		                  			    ],
		                  			    series : [
		                  			        {
		                  			            name:'企业自主报送(万元)',
		                  			            type:'bar',
		                  			            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3]
		                  			        },
		                  			        {
		                  			            name:'第三方机构(万元)',
		                  			            type:'bar',
		                  			            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3]
		                  			        }
		                  			    ]
		                  			};
							        $(function() {
							            //option.series[0].data[0].value=['3','6','2','5'];  // 加载数据到data中
							            //option.series[0].data[0].name ='风险矩阵';
							            var myChart = echarts.init(document.getElementById('chartdiv1')); 
							            myChart.setOption(option1, true);   //为echarts对象加载数据
							        });
							  </script>
			                  <div id="chartdiv1" style="width:495px; height:250px;margin:5px auto;"></div> 	
                  		  </td>
                  		  <td width="1800px" style="text-align:center;">
                  		  <script type="text/javascript">
		                     option2 = {
		                		    title : {
		                		        text: '借款期限',
		                		    },
		                		    tooltip : {
		                		        trigger: 'axis'
		                		    },
		                		    legend: {
		                		        data:['企业平均借款期限','行业平均借款期限']
		                		    },
		                		    calculable : true,
		                		    xAxis : [
		                		        {
		                		            type : 'category',
		                		            boundaryGap : false,
		                		            data : ['2015-10-01','2015-10-02','2015-10-03','2015-10-04','2015-10-05','2015-10-06','2015-10-07',
		                		                    '2015-10-08','2015-10-09','2015-10-10','2015-10-11','2015-10-12','2015-10-13','2015-10-14','2015-10-15']
		                		        }
		                		    ],
		                		    yAxis : [
		                		        {
		                		            type : 'value',
		                		            axisLabel : {
		                		                formatter: '{value}天'
		                		            }
		                		        }
		                		    ],
		                		    series : [
		                		        {
		                		            name:'企业平均借款期限',
		                		            type:'line',
		                		            data:[11,11,15,13,12,13,10,12,11,10,12,13,12,14,13],
		                		        },
		                		        {
		                		            name:'行业平均借款期限',
		                		            type:'line',
		                		            data:[11,12,15,13,12,13,10,12,11,10,11,10,12,13,13],
		                		        }
		                		    ]
		                		};
		                		   
						        $(function() {
						            //option.series[0].data[0].value=['3','6','2','5'];  // 加载数据到data中
						            //option.series[0].data[0].name ='风险矩阵';
						            var myChart = echarts.init(document.getElementById('chartdiv2')); 
						            myChart.setOption(option2, true);   //为echarts对象加载数据
						        });
						  </script>
		                  <div id="chartdiv2" style="width:100%; height:250px;margin:5px auto;"></div> 
                  		  </td>
                  		</tr>
                  		<tr>
                  		  <td>
                  		  	<script type="text/javascript">
                  		  		option3 = {
		                		    title : {
		                		        text: '综合利率',
		                		    },
		                		    tooltip : {
		                		        trigger: 'axis'
		                		    },
		                		    legend: {
		                		        data:['综合利率(%)','行业利率(%)']
		                		    },
		                		    calculable : true,
		                		    xAxis : [
		                		        {
		                		            type : 'category',
		                		            boundaryGap : false,
		                		            data : ['2015-10-01','2015-10-02','2015-10-03','2015-10-04','2015-10-05','2015-10-06','2015-10-07',
		                		                    '2015-10-08','2015-10-09','2015-10-10','2015-10-11','2015-10-12','2015-10-13','2015-10-14','2015-10-15']
		                		        }
		                		    ],
		                		    yAxis : [
		                		        {
		                		            type : 'value',
		                		            axisLabel : {
		                		                formatter: '{value}%'
		                		            }
		                		        }
		                		    ],
		                		    series : [
		                		        {
		                		            name:'综合利率(%)',
		                		            type:'line',
		                		            data:[11,11,13,13,12,13,10,14,11,11,15,13,12,13,12],
		                		        },
		                		        {
		                		            name:'行业利率(%)',
		                		            type:'line',
		                		            data:[11,12,15,13,12,13,10,13,11,11,14,12,12,13,15],
		                		        }
		                		    ]
		                		};
		                		   
						        $(function() {
						            //option.series[0].data[0].value=['3','6','2','5'];  // 加载数据到data中
						            //option.series[0].data[0].name ='风险矩阵';
						            var myChart = echarts.init(document.getElementById('chartdiv3')); 
						            myChart.setOption(option3, true);   //为echarts对象加载数据
						        });
						  </script>
		                  <div id="chartdiv3" style="width:495px; height:250px;margin:5px auto;"></div> 
                  		  </td>
                  		  <td>
                  		  <script type="text/javascript">
	                  		option4 = {
		                		    title : {
		                		        text: '逾期率',
		                		    },
		                		    tooltip : {
		                		        trigger: 'axis'
		                		    },
		                		    legend: {
		                		        data:['企业逾期率(%)','全国平均逾期率(%)']
		                		    },
		                		    calculable : true,
		                		    xAxis : [
		                		        {
		                		            type : 'category',
		                		            boundaryGap : false,
		                		            data : ['2015-10-01','2015-10-02','2015-10-03','2015-10-04','2015-10-05','2015-10-06','2015-10-07',
		                		                    '2015-10-08','2015-10-09','2015-10-10','2015-10-11','2015-10-12','2015-10-13','2015-10-14','2015-10-15']
		                		        }
		                		    ],
		                		    yAxis : [
		                		        {
		                		            type : 'value',
		                		            axisLabel : {
		                		                formatter: '{value}%'
		                		            }
		                		        }
		                		    ],
		                		    series : [
		                		        {
		                		            name:'企业逾期率(%)',
		                		            type:'line',
		                		            data:[1,2,2,1,2,2,1,2,2,1,2,2,1,2,2],
		                		        },
		                		        {
		                		            name:'全国平均逾期率(%)',
		                		            type:'line',
		                		            data:[1,2,3,1,2,3,1,2,3,1,2,2,1,2.5,2],
		                		        }
		                		    ]
		                		};
		                		   
						        $(function() {
						            //option.series[0].data[0].value=['3','6','2','5'];  // 加载数据到data中
						            //option.series[0].data[0].name ='风险矩阵';
						            var myChart = echarts.init(document.getElementById('chartdiv4')); 
						            myChart.setOption(option4, true);   //为echarts对象加载数据
						        });
						  </script>
		                  <div id="chartdiv4" style="width:495px; height:250px;margin:5px auto;"></div> 
                  		  </td>
                  		</tr>
                  		<tr>
                  		  <td>
                  		  <script type="text/javascript">
	                  		option5 = {
	                  			    title : {
	                  			        text: '借款人群年龄分布',
	                  			        x:'center'
	                  			    },
	                  			    tooltip : {
	                  			        trigger: 'item',
	                  			        formatter: "{a} <br/>{b} : {c} ({d}%)"
	                  			    },
	                  			    legend: {
	                  			        orient : 'vertical',
	                  			        x : 'left',
	                  			        data:['20-30岁','30-40岁','40-50岁','50-60岁','60-70岁']
	                  			    },
	                  			    calculable : true,
	                  			    series : [
	                  			        {
	                  			            name:'借款人群年龄分布',
	                  			            type:'pie',
	                  			            radius : '55%',
	                  			            center: ['50%', '60%'],
	                  			            data:[
	                  			                {value:280000, name:'20-30岁'},
	                  			                {value:320000, name:'30-40岁'},
	                  			                {value:860000, name:'40-50岁'},
	                  			                {value:120000, name:'50-60岁'},
	                  			                {value:700000, name:'60-70岁'}
	                  			            ]
	                  			        }
	                  			    ]
	                  			};
						        $(function() {
						            //option.series[0].data[0].value=['3','6','2','5'];  // 加载数据到data中
						            //option.series[0].data[0].name ='风险矩阵';
						            var myChart = echarts.init(document.getElementById('chartdiv5')); 
						            myChart.setOption(option5, true);   //为echarts对象加载数据
						        });
						  </script>
		                  <div id="chartdiv5" style="width:495px; height:250px;margin:5px auto;"></div> 
                  		  </td>
                  		  <td>
                  		  <script type="text/javascript">
	                  		option6 = {
	                  			    title : {
	                  			        text: '投资人群年龄分布',
	                  			        x:'center'
	                  			    },
	                  			    tooltip : {
	                  			        trigger: 'item',
	                  			        formatter: "{a} <br/>{b} : {c} ({d}%)"
	                  			    },
	                  			    legend: {
	                  			        orient : 'vertical',
	                  			        x : 'left',
	                  			        data:['20-30岁','30-40岁','40-50岁','50-60岁','60-70岁']
	                  			    },
	                  			    calculable : true,
	                  			    series : [
	                  			        {
	                  			            name:'投资人群年龄分布',
	                  			            type:'pie',
	                  			            radius : '55%',
	                  			            center: ['50%', '60%'],
	                  			            data:[
	                  			                {value:280000, name:'20-30岁'},
	                  			                {value:400000, name:'30-40岁'},
	                  			                {value:100000, name:'40-50岁'},
	                  			                {value:120000, name:'50-60岁'},
	                  			                {value:700000, name:'60-70岁'}
	                  			            ]
	                  			        }
	                  			    ]
	                  			};
						        $(function() {
						            //option.series[0].data[0].value=['3','6','2','5'];  // 加载数据到data中
						            //option.series[0].data[0].name ='风险矩阵';
						            var myChart = echarts.init(document.getElementById('chartdiv6')); 
						            myChart.setOption(option6, true);   //为echarts对象加载数据
						        });
						  </script>
		                  <div id="chartdiv6" style="width:495px; height:250px;margin:5px auto;"></div> 
                  		  </td>
                  		</tr>
                  		<tr>
                  			<td>
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
							            var myChart = echarts.init(document.getElementById('chartdiv8')); 
							            myChart.setOption(option8, true);   //为echarts对象加载数据
							        });
							    </script> 
							    <div id="chartdiv8" style="width:495px; height:250px;margin:5px auto;"></div> 
                  			</td>
                  			<td style="text-align:left;font-size:15px;">
                  				<span style="font-weight:bold;">投资指数：</span>数值越小，说明平台投资人受单一借款人违约影响较大。<br><br>
                  				<span style="font-weight:bold;">地域指数：</span>数值越小，投资人受舆论波及撤资可能越高。<br><br>
                  				<span style="font-weight:bold;">利率指数：</span>数值越小，借款利率偏离全行业平均水平越高，风险越大。<br><br>
                  				<span style="font-weight:bold;">期限指数：</span>数值越小，借款期限偏离全行业平均水平水平越高，风险越大。<br><br>
                  				<span style="font-weight:bold;">流动指数：</span>数值越小，流动性风险越高，存在较大的兑付压力。<br><br>
                  				<span style="font-weight:bold;">标的指数：</span>数值越小，非常规标的数量越大，平台的现金流稳定性越低。<br><br>
                  			</td>
                  		</tr>
                  	</table>
                  </div>
                  <div class="title-line offset-b20 offset-t30">企业被执行信息</div>
                  <!-- 被执行信息  -->
                  <div class="offset-t30" id="executorInfo">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">被执行信息</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">案号</th>
                          <th width="140">被执行名称</th>
                          <th width="120">组织机构代码</th>
                          <th width="120">执行法院</th>
                          <th width="120">立案时间</th>
                          <th width="120">执行标的</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${enterpriseExecutorBeans}" var="executor">
						<tr class="tr1">
							<td>${executor.caseCode}</td>
							<td>${executor.name}</td>
							<td>${executor.code}</td>
							<td>${executor.execCourt}</td>
							<td>${executor.filingTime}</td>
							<td>${executor.executiveSubject}</td>
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div>
                  <!-- 失信人信息  -->
                  <div class="offset-t30" id="dishPersonInfo">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">失信人信息</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="145">名称</th>
                          <th width="100">法人名称</th>
                          <th width="140">身份证号码/组织机构代码</th>
                          <th width="140">执行依据文号</th>
                          <th width="140">案号</th>
                          <th width="140">做出执行依据单位</th>
                          <th width="140">法律生效文书确定的义务</th>
                          <th width="100">被执行人的履行情况</th>
                          <th width="140">执行法院</th>
                          <th width="80">省份</th>
                          <th width="80">立案时间</th>
                          <th width="80">发布时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${enterpriseDishPersonBeans}" var="dishPerson">
						<tr class="tr1">
							<td>${dishPerson.name}</td>
							<td>${dishPerson.legalPerson}</td>
							<td>${dishPerson.code}</td>
							<td>${dishPerson.basisDocNo}</td>
							<td>${dishPerson.caseCode}</td>
							<td>${dishPerson.basisDept}</td>
							<td>${dishPerson.docContent}</td>
							<td>${dishPerson.execStatus}</td>
							<td>${dishPerson.execCourt}</td>
							<td>${dishPerson.province}</td>
							<td>${dishPerson.filingTime}</td>
							<td>${dishPerson.publishDate}</td>
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
                  <!-- 法院裁决文书  -->
                  <%-- <div class="offset-t30" id="judgementDoc">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">法院裁决文书</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">案号</th>
                          <th width="140">标题</th>
                          <th width="120">执行法院</th>
                          <th width="120">日期</th>
                          <th width="120">裁决文书</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${enterpriseCourtJudgmentDocBeans}" var="judgmentDoc">
						<tr class="tr1">
							<td>${judgmentDoc.caseCode}</td>
							<td>${judgmentDoc.title}</td>
							<td>${judgmentDoc.courtName}</td>
							<td>${judgmentDoc.judgeDate}</td>
							<td><a href="${judgmentDoc.judgmentDocUrl}" target="_blank">查看</a></td>
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
                  
                  <!-- /table row end -->
                  <!-- 法院公告  -->
                  <div class="offset-t30" id="courtBulletin">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">法院公告</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">公告类型</th>
                          <th width="140">公告人</th>
                          <th width="120">当事人</th>
                          <th width="120">公告时间</th>
                          <th width="120">公告内容</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${enterpriseCourtAnnouncementBeans}" var="courtAnnouncement">
						<tr class="tr1">
							<td>${courtAnnouncement.announcementType}</td>
							<td>${courtAnnouncement.announcement}</td>
							<td>${courtAnnouncement.litigant}</td>
							<td>${courtAnnouncement.announcementDate}</td>
							<td>${courtAnnouncement.content}</td>
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div> --%>
                  <!-- /table row end -->
                  <!-- <div class="title-line offset-b20 offset-t30">第三方支付指标</div> -->
                  
                  <div class="title-line offset-b20 offset-t30">法人被执行信息</div>
                  <!-- 被执行信息  -->
                  <div class="offset-t30" id="executorInfo">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">被执行信息</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">案号</th>
                          <th width="140">被执行名称</th>
                          <th width="120">身份证号码</th>
                          <th width="120">执行法院</th>
                          <th width="120">立案时间</th>
                          <th width="120">执行标的</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${personExecutorBeans}" var="executor">
						<tr class="tr1">
							<td>${executor.caseCode}</td>
							<td>${executor.name}</td>
							<td>${executor.code}</td>
							<td>${executor.execCourt}</td>
							<td>${executor.filingTime}</td>
							<td>${executor.executiveSubject}</td>
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div>
                  <!-- /table row end -->
                  <%-- <!-- 法院裁决文书  -->
                  <div class="offset-t30" id="judgementDoc">
                    <div class="clearfix table-title-lg">
                      <div class="pull-left">法院裁决文书</div>
                      <div class="pull-right">
                        <a><span class="icon icon-add"></span></a>
                      </div>
                    </div>
                    <table class="table table-bordered table-center">
                    	<thead>
                        <tr>
                          <th width="85">案号</th>
                          <th width="140">标题</th>
                          <th width="120">执行法院</th>
                          <th width="120">日期</th>
                          <th width="120">裁决文书</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${personCourtJudgmentDocBeans}" var="judgmentDoc">
						<tr class="tr1">
							<td>${judgmentDoc.caseCode}</td>
							<td>${judgmentDoc.title}</td>
							<td>${judgmentDoc.courtName}</td>
							<td>${judgmentDoc.judgeDate}</td>
							<td><a href="${judgmentDoc.judgmentDocUrl}" target="_blank">查看</a></td>
						</tr>
						</c:forEach>
                        </tbody>
                    </table>
                  </div> --%>
                  <!-- /table row end -->
                  <div class="title-line offset-b20 offset-t30">预警监测</div>
                  <div style="text-align:right;"><a>添加+</a></div>
                </div>
              </div>

            </div>
          </div><!-- /tab2 end -->
        </div>

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
        	  $("#full_form_id").validate();
      	  });
       
      	
      	 var submited_flag = false;//重复提交标示
         function submitForm(){
      	   if(!$("#full_form_id").valid()){//表单验证
      		  return;
      	   }
      	   if(!submitedFlag()){//是否已提交过了
	          document.getElementById('full_form_id').submit();
      	   }
         }
      </script>  
</body>
</html>
