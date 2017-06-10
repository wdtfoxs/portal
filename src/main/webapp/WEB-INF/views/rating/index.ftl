<#include "../templates/body.ftl"/>
<#macro menu>
<li><a href="/cabinet">Личный кабинет</a></li>
<li class="active"><a href="/rating">Рейтинг</a></li>
<li><a href="/timetable/student">Расписание</a></li>
<li><a href="/timetable/student">Расписание экзаменов</a></li>
<li><a href="/journal/student">Журнал посещений</a></li>
<li><a href="/curriculum/student">Учебный план</a></li>
</#macro>
<@body title="Рейтинг">
<!-- Rating Table Begin -->
<div class="container">
    <div class="widget-main col-lg-12">
        <div class="widget-inner">
            <div class="slider-testimonials">
                <div class="flex-space-around">
                    <p class="widget-title">Место в группе: ${place_group!'Неизвестно'}</p>
                    <p class="widget-title">Средний балл за семестр: ${average_rating?string(",##0.0")}</p>
                    <p class="widget-title">Место на курсе: ${place_course!'Неизвестно'}</p>
                </div>
                <br>
                <table class="table">
                    <tr>
                        <td class="rating-left"></td>
                        <td class="rating">Дата сдачи</td>
                        <td class="rating">Балл за семестр</td>
                        <td class="rating">Балл за экзамен</td>
                        <td class="rating">Итоговый балл</td>
                        <td class="rating">Средний балл в группе</td>
                        <td class="rating">Средний балл на курсе</td>
                        <td class="rating">Семестр</td>
                    </tr>
                    <#if rating_second??>
                        <#list rating_second as discipline>
                            <@rating discipline 2/>
                        </#list>
                    </#if>
                    <#if rating_first??>
                        <#list rating_first as discipline>
                            <@rating discipline 1/>
                        </#list>
                    </#if>
                </table>
            </div>
        </div>
    </div>
</div>
    <#macro rating discipline semester>
    <tr>
        <td class="rating-left">${discipline.discipline.discipline}</td>
        <td class="rating">${discipline.passTime.from?string["dd MMMM"]}</td>
        <td class="rating">${discipline.semesterPoints?string(",##0.0")}</td>
        <td class="rating">${discipline.examPoints?string(",##0.0")}</td>
        <td class="rating">${discipline.totalPoints?string(",##0.0")}</td>
        <td class="rating">${discipline.averageGroupPoints?string(",##0.0")}</td>
        <td class="rating">${discipline.averageCoursePoints?string(",##0.0")}</td>
        <td class="rating">${semester}</td>
    </tr>
    </#macro>
</@body>