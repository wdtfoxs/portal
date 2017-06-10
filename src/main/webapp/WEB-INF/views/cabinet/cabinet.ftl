<#include "../templates/body.ftl"/>
<@security.authorize access="hasRole('STUDENT')">
    <#include "student/index.ftl">
</@security.authorize>
<@security.authorize access="hasRole('PROFESSOR')">
    <#include "professor/index.ftl">
</@security.authorize>
<@security.authorize access="hasRole('EMPLOYEE')">
    <#include "employee/index.ftl">
</@security.authorize>
