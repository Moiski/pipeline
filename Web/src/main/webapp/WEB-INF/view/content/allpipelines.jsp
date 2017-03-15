<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div class="table-responsive">
	<b><s:message code="message.all.pipelines" /></b><br> <br>
	<c:forEach var="pipeline" items="${pipelinelist}" varStatus="status">
		<b><s:message code="message.pipeline.name" /></b>
		<c:out value="${pipeline.name}" />
		<br>
		<b><s:message code="message.pipeline.description" /></b>
		<c:out value="${pipeline.description}" />
		<br>
		<b><s:message code="message.pipeline.status" /></b>
		<c:out value="${pipeline.statusType}" />
		<br>
		<b><s:message code="message.pipeline.starttime" /></b>
		<c:out value="${pipeline.startTime}" />
		<br>
		<b><s:message code="message.pipeline.endtime" /></b>
		<c:out value="${pipeline.endTime}" />
		<br>
		<table class="table table-bordered table-striped ">
			<tr class="info" align="left">
				<th><s:message code="message.task.name" /></th>
				<th><s:message code="message.task.desription" /></th>
				<th><s:message code="message.task.action" /></th>
				<th><s:message code="message.task.status" /></th>
				<th><s:message code="message.task.starttime" /></th>
				<th><s:message code="message.task.endtime" /></th>
			</tr>
			<c:forEach var="task" items="${pipeline.tasks}" varStatus="status">
				<tr>
					<td><c:out value="${task.name}" /></td>
					<td><c:out value="${task.description}" /></td>
					<td><c:out value="${task.action.type}" /></td>
					<td><c:out value="${task.statusType}"/></td>
					<td><c:out value="${task.startTime}"/></td>
					<td><c:out value="${task.endTime}"/></td>
				</tr>
			</c:forEach>
		</table>
		<sf:form id="startPipeline" action="${pageContext.request.contextPath}/pipelines/start/${pipeline.pipelineId}" method="POST" modelAttribute="pipeline">
			<sf:input type="hidden" path="pipelineId" value="${pipeline.pipelineId}" />
			<button class="btn btn-default btn-block btn-info" type="submit">
				<s:message code="message.pipeline.action"/>
			</button>
		</sf:form>
		<br>
		<sf:form id="deletePipeline" action="${pageContext.request.contextPath}/pipelines/delete/${pipeline.pipelineId}" method="POST" modelAttribute="pipeline">
			<sf:input type="hidden" path="pipelineId" value="${pipeline.pipelineId}" />
			<button class="btn btn-default btn-block btn-info" type="submit">
				<s:message code="message.pipeline.action.delete"/>
			</button>
		</sf:form>
	</c:forEach>
</div>