<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

                <div class="pull-left">
                      <div class="table-title-sm offset-l40">
                        <span>备注信息</span>
                      </div>
                      <table class="table table-bordered table-sm table-center" style="table-layout:fixed;word-wrap:break-word;">
                        <tbody>
                          <tr class="tr2">
                            <td>备注类型</td>
                            <td>备注内容</td>
                            <td>更新时间</td>
                          </tr>
                          <c:forEach items="${bcComment}" var="c">
                          <tr class="tr1">
                            <!--<td>${c.commentType }</td> -->
                            <td>${c.commentTypeStr}</td>
                            <td>${c.content}</td>
                            <td><fmt:formatDate value="${c.lastUpdateDatetime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                          </tr>
                          </c:forEach>
                          <!-- <tr class="tr1">
                            <td colspan="3">&nbsp;</td>
                          </tr>
                          <tr class="tr2">
                            <td colspan="3">&nbsp;</td>
                          </tr>
                          <tr class="tr1">
                            <td colspan="3">&nbsp;</td>
                          </tr>
                          <tr class="tr2">
                            <td colspan="3">&nbsp;</td>
                          </tr> -->
                        </tbody>
                      </table>
                    </div>