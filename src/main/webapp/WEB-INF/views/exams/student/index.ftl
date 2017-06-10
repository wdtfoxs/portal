<#include "../../templates/body.ftl"/>

<#-- @ftlvariable name="exam" type="com.code405.entity.model.Exam" -->
<#macro menu>
<li><a href="/cabinet">Личный кабинет</a></li>
<li><a href="/rating">Рейтинг</a></li>
<li><a href="/timetable/student">Расписание</a></li>
<li class="active"><a href="/exams/student">Расписание экзаменов</a></li>
<li><a href="/journal/student">Журнал посещений</a></li>
<li><a href="/curriculum/student">Учебный план</a></li>
</#macro>

<@body title="Расписание экзаменов">
<div class="container">
    <div class="text-center">
        <div id="week_schedule">
            <div class="widget-main col-lg-12">
                <div class="widget-main-title">
                    <h4 class="widget-title">Экзамены на текущий семестр</h4>
                </div>
                <div class="widget-inner">
                    <div class="slider-testimonials">
                        <table class="table">
                            <thead>
                            <tr>
                                <td>Дата</td>
                                <td>Время</td>
                                <td>Аудитория</td>
                                <td>Преподаватель</td>
                                <td>Предмет</td>
                                <td>Тип</td>
                            </tr>
                            </thead>
                            <#list exams as exam>
                                <tr>
                                    <td class="tg-031e">${(exam.time.from)?string["dd MMMM"]}</td>
                                    <td class="tg-031e">${(exam.time.from)?string["HH:mm"]} - ${(exam.time.to)?string["HH:mm"]}</td>
                                    <td class="tg-031e"><#if exam.auditorium??>${exam.auditorium.number} (${exam.auditorium.building.name})</#if></td>
                                    <td class="tg-031e"><#if exam.professor??>${exam.professor.surname} ${exam.professor.name}</#if></td>
                                    <td class="tg-031e">${exam.discipline.discipline}</td>
                                    <td class="tg-031e">${exam.passType.name()}</td>
                                </tr>
                            </#list>
                        </table>
                    </div>
                </div> <!-- /.widget-inner -->
            </div>
        </div>
    </div>
</div>

</@body>