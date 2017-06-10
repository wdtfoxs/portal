<#list exams as exam>
<tr>
    <td class="tg-031e">${(exam.time.from)?string["dd MMMM"]}</td>
    <td class="tg-031e">${(exam.time.from)?string["HH:mm"]}
        - ${(exam.time.to)?string["HH:mm"]}</td>
    <td class="tg-031e"><#if exam.auditorium??>${exam.auditorium.number} (${exam.auditorium.building.name})</#if></td>
    <td class="tg-031e">${exam.group.groupCode}</td>
    <td class="tg-031e">${exam.discipline.discipline}</td>
    <td class="tg-031e">${exam.passType.name()}</td>
    <td class="tg-031e"><a data-id="${exam.id}" href="#editModal" data-toggle="modal" style="color: red">Изменить время</a></td>
</tr>
</#list>