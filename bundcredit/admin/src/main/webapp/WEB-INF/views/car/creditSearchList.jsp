<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>任务管理</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<div class="row">
		<div class="span4 offset7">
			<form class="form-search" action="${ctx}/creditSearch/search">
				<label>证件号：</label> <input type="text" name="search_idCardNum" class="input-medium" value="${param.search_idCardNum}"> 
				<button type="submit" class="btn" id="search_btn">Search</button>
		    </form>
	    </div>
	</div>
	<table style="width: 100%">
	    <tr>
	        <td width="50%">
	            <table id="contentTable" style="width: 100%" class="table table-striped table-bordered table-condensed">
				    <thead><tr><th colspan="2">基本信息</th></tr></thead>
					<tbody>
						<tr>
						    <td width="50%">姓名</td>
							<td>${personBasicInfo.fullName}</td>
						</tr>
						<tr>	
							<td>证件号码</td>
							<td>${personBasicInfo.idCardNum}</td>
						</tr>
						<tr>
							<td>所在省市</td>
							<td>${personBasicInfo.location}</td>
						</tr>
						<tr>
							<td>报告时间</td>
							<td><fmt:formatDate value="${personBasicInfo.reportDatetime}" pattern="yyyy年MM月dd日  HH时mm分ss秒" /></td>
						</tr>
						<tr>
							<td>最后更新时间</td>
							<td><fmt:formatDate value="${personBasicInfo.lastUpdateDatetime}" pattern="yyyy年MM月dd日  HH时mm分ss秒" /></td>
						</tr>
					</tbody>
				</table>
	        </td>
	        <td>
	        </td>
	    </tr>
	    <tr>
	        <td>
	        	<table id="contentTable2" style="width: 100%" class="table table-striped table-bordered table-condensed">
				    <thead><tr><th colspan="2">报告内容-概要信息</th></tr></thead>
					<tbody>
						<tr>
						    <td width="50%">总贷款账户数</td>
							<td>${personSummaryReport.loanCnt}</td>
						</tr>
						<tr>
						    <td>未结清账户数</td>
							<td>${personSummaryReport.openLoanCnt}</td>
						</tr>
						<tr>
						    <td>当前总余额</td>
							<td>${personSummaryReport.openLoanTotalAmount}</td>
						</tr>
						<tr>
						    <td>下期应还款总额</td>
							<td>${personSummaryReport.nextLoanRepayAmount}</td>
						</tr>
						<tr>
						    <td>发生过逾期的贷款账户数</td>
							<td>${personSummaryReport.overdueLoanAccCnt}</td>
						</tr>
						<tr>
						    <td>发生过90天以上逾期的贷款账户数</td>
							<td>${personSummaryReport.overdue90LoanAccCnt}</td>
						</tr>
						<tr>
						    <td>90天内申请次数</td>
							<td>${personSummaryReport.in90LoanApplyCnt}</td>
						</tr>
					</tbody>
				</table>
	        </td>
	        <td>
	        </td>
	    </tr>
	    
	    <tr>
	        <td colspan="2">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead><tr>
						       <th>机构</th>
						       <th>账户代码</th>
						       <th>更新日期</th>
						       <th>总额度</th>
						       <th>贷款期起</th>
						       <th>贷款期止</th>
						       <th>余额</th>
						       <th>下期应还款日期</th>
						       <th>下期应还款额</th>
						       <th>当前贷款状态</th>
						       <th>近24个月状态</th>
					       </tr>
					</thead>
					<tbody>
					<c:forEach items="${personAccDetails}" var="personAccDetail">
						<tr>
							<td>${personAccDetail.insCode}</td>
							<td>${personAccDetail.loanAccId}</td>
							<td><fmt:formatDate value="${personAccDetail.updateDate}" pattern="yyyy年MM月dd日  HH时mm分" /></td>
							<td>${personAccDetail.totalAllowedAmount}</td>
							<td><fmt:formatDate value="${personAccDetail.loanBeginDate}" pattern="yyyy年MM月dd日 " /></td>
							<td><fmt:formatDate value="${personAccDetail.loanEndDate}" pattern="yyyy年MM月dd日" /></td>
							<td>${personAccDetail.loanBalance}</td>
							<td><fmt:formatDate value="${personAccDetail.nextLoanRepayDate}" pattern="yyyy年MM月dd日" /></td>
							<td>${personAccDetail.nextLoanRepayAmt}</td>
							<td>${personAccDetail.loanStatus}</td>
							<td>查看那个啥</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
	        </td>
	    </tr>
	    
	    <tr>
	        <td colspan="2">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead><tr>
						       <th>机构</th>
						       <th>申请时间</th>
						       <th>申请金额</th>
						       <th>审批结果</th>
						       <th>审批金额</th>
						       <th>申请省市</th>
						       <th>ip地址</th>
						       <th>家庭地址</th>
						       <th>手机号码</th>
					       </tr>
					</thead>
					<tbody>
					<c:forEach items="${personApplyDetails}" var="personApplyDetail">
						<tr>
							<td>${personApplyDetail.insCode}</td>
							<td><fmt:formatDate value="${personApplyDetail.applyDatetime}" pattern="yyyy年MM月dd日" /></td>
							<td>${personApplyDetail.applyAmount}</td>
							<td>${personApplyDetail.applyType}</td>
							<td>${personApplyDetail.approveAmount}</td>
							<td>${personApplyDetail.applyProCity}</td>
							<td>${personApplyDetail.applyIp}</td>
							<td>${personApplyDetail.homeAddr}</td>
							<td>${personApplyDetail.selPhoneNum}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
	        </td>
	    </tr>
	    
	</table>
	
    
</body>
</html>
