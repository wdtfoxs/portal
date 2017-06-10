<#-- @ftlvariable name="timetable" type="java.util.Map<com.code405.entity.enumeration.WeekDay, java.util.Map<com.code405.entity.enumeration.Class,com.code405.entity.model.Timetable>>" -->
<#list timetable as week, map>
<div class="widget-main col-lg-12">
    <div class="widget-main-title">
        <h4 class="widget-title">${week.name()}</h4>
    </div>
    <div class="widget-inner">
        <div class="slider-testimonials">
            <table class="table">
                <thead>
                <tr>
                    <td>Время</td>
                    <td>Аудитория</td>
                    <td>Группа</td>
                    <td>Предмет</td>
                    <td>Тип</td>
                    <td>Комментарий</td>
                </tr>
                </thead>
                <#list map as class, lesson>
                    <tr>
                        <td class="tg-031e">${class.time}</td>
                        <td class="tg-031e">${(lesson.auditorium.number)!''}</td>
                        <td class="tg-031e">${(lesson.group.groupCode)!''}</td>
                        <td class="tg-031e">${(lesson.discipline.discipline)!''}</td>
                        <td class="tg-031e">${(lesson.type.name())!''}</td>
                        <td class="tg-031e"><#if (lesson.week.getType())??>${lesson.week.getType()};</#if> ${(lesson.comment)!''}</td>
                    </tr>
                </#list>
            </table>
        </div>
    </div> <!-- /.widget-inner -->
</div>
</#list>
