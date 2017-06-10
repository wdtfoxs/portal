<#-- @ftlvariable name="auditorium" type="com.code405.entity.model.Auditorium" -->

<table class="table-fill">
    <thead>
    <tr>
        <th class="text-center">Номер</th>
        <th class="text-center">Вместимость</th>
        <th class="text-center">Время</th>
        <th class="text-center">Статус</th>
    </tr>
    </thead>
    <tbody class="table-hover">
    <#list auditoriums as auditorium>
    <tr>
        <td class="text-center">${auditorium.number}</td>
        <td class="text-center">${auditorium.capacity}</td>
        <td class="text-center">${time}</td>
        <td class="text-center">свободна</td>
    </tr>
    </#list>
    </tbody>
</table>
