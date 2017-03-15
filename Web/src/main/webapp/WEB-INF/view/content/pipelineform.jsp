<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>

<div id="addProductForm">
	<sf:form role="form" action="${pageContext.request.contextPath}/pipelines/add" method="POST" commandName="context">
		<h3><i><s:message code="message.add.pipeline" /></i></h3>
		<div class="form-group" id="idYAMLformat">
			<label for="comment" class="col-sm-2 control-label"><s:message
					code="message.add.pipeline.comment" /></label>
			<div class="col-sm-6">
				<textarea name="context" class="form-control" rows="30" cols="58" id="comment"></textarea>
			</div>
		</div>
		<div class="col-sm-offset-2 col-sm-6">
				<button type="submit" class="btn btn-success"><s:message code="message.add.pipeline.save"/></button>
		</div>
	</sf:form>
</div>


