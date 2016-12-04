<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>  
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
  <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">               
                        <li>
                            <a href="#"><i class="fa fa-wrench fa-fw"></i> 机构管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">	
                            <shiro:hasAnyRoles name="admin,commercial">
                               <li>
                                    <a href="${ctx}/insadmin/institutions">机构维护</a>
                                </li>    
                                </shiro:hasAnyRoles>
                                <shiro:hasAnyRoles name="admin,commercial">              
                                <li>
                                    <a href="${ctx}/insadmin/history">工作查询</a>
                                </li>
                                </shiro:hasAnyRoles>
                                <shiro:hasAnyRoles name="admin,financial">                           
                                <li>
                                    <a href="${ctx}/insadmin/transactions">交易日志</a>
                                </li> 
                                </shiro:hasAnyRoles>
                                <shiro:hasAnyRoles name="admin,examine">
                                <li>
                                    <a href="${ctx}/insadmin/files">机构上传</a>
                                </li>
                                </shiro:hasAnyRoles>
                                <shiro:hasAnyRoles name="admin,financial">
                                 <li>
                                    <a href="${ctx}/insadmin/raninstitutionst">机构交易</a>
                                </li>
                                </shiro:hasAnyRoles>
                                <shiro:hasAnyRoles name="admin">
                                <li>
                                    <a href="${ctx}/admin/user">账户管理</a>  
                                 </li>
                                </shiro:hasAnyRoles>   
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        <shiro:hasAnyRoles name="admin,examine">
						<li>
                            <a href="#"><i class="fa fa-files-o fa-fw"></i> 征信管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse" aria-expanded="false" style="height: 0px;">
                                 <shiro:hasAnyRoles name="admin,examine">   
                                <li>
                                    <a href="${ctx}/creditadmin/persons">征信备注</a>
                                </li>
                                </shiro:hasAnyRoles>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        </shiro:hasAnyRoles>
                        <shiro:hasAnyRoles name="admin">
						<li>
                            <a href="#"><i class="fa fa-files-o fa-fw"></i> 产品管理<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse" aria-expanded="false" style="height: 0px;">
                                 <shiro:hasAnyRoles name="admin">   
                                <li>
                                    <a href="${ctx}/admin/product">产品管理</a>
                                </li>
                                </shiro:hasAnyRoles>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                        </shiro:hasAnyRoles>  
                        </ul>
                        <!--<li><a href="${ctx}/task">回老版界面</a></li>-->
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->