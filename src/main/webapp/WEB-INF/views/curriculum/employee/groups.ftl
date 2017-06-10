<#-- @ftlvariable name="group" type="com.code405.entity.model.Group" -->
<#if groups??>
<option disabled selected value="">Выберите группу</option>
    <#list groups as group>
    <option value="${group.groupCode}">${group.groupCode}</option>
    </#list>
</#if>