<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
                    <div class="pull-left">
                      <div class="table-title-xs">
                        <span>概要信息</span>
                      </div>
                      <table class="table table-bordered table-xs">
                        <tbody>
                          <tr class="tr1">
                            <td width="60%">总贷款账户数</td>
                            <td width="40%">${personSummaryReport.loanCnt}</td>
                          </tr>
                          <tr class="tr2">
                            <td>未结清账户数</td>
                            <td>${personSummaryReport.openLoanCnt}</td>
                          </tr>
                          <tr class="tr1">
                            <td>当前总余额</td>
                            <td>${personSummaryReport.openLoanTotalAmountStr}</td>
                          </tr>
                          <tr class="tr2">
                            <td>最近应还款总额</td>
                            <td>${personSummaryReport.nextLoanRepayAmountStr}</td>
                          </tr>
                          <tr class="tr1">
                            <td>发生过逾期的贷款账户数</td>
                            <td>${personSummaryReport.overdueLoanAccCnt}</td>
                          </tr>
                          <tr class="tr2">
                            <td>发生过90天以上逾期的贷款账户数</td>
                            <td>${personSummaryReport.overdue90LoanAccCnt}</td>
                          </tr>
                          <tr class="tr1">
                            <td>90天内申请次数</td>
                            <td>${personSummaryReport.in90LoanApplyCnt}</td>
                          </tr>
                        </tbody>
                      </table>
                    </div>