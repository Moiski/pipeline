<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>

<div class="panel-heading">
		<b><i><s:message code="message.welcome"/></i></b>
</div>
<div class="panel panel-success">
	<div class="panel-heading">
		<i class="panel-title"><s:message code="message.nav.bar"/></i>
	</div>
	<div class="panel-body">
		<form id="addNewPipelineButton" action="${pageContext.request.contextPath}/action/getpipelineform" method="GET" >
			<input class="btn btn-default btn-block btn-info" type="submit" value="<s:message code="message.nav.bar.add.pipeline"/>" />
		</form><br/>
		<form id="showPipelineButton" action="${pageContext.request.contextPath}/pipelines/all" method="GET" >
			<input class="btn btn-default btn-block btn-info" type="submit" value="<s:message code="message.nav.bar.all.pipelines"/>"/>
		</form><br/>	
	</div>
</div>