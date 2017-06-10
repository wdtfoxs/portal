<#macro menu>
<li><a href="/cabinet">Личный кабинет</a></li>
<li><a href="/rating">Рейтинг</a></li>
<li><a href="/timetable/student">Расписание</a></li>
<li><a href="/exams/student">Расписание экзаменов</a></li>
<li class="active"><a href="/journal/student">Журнал посещений</a></li>
<li><a href="/curriculum/student">Учебный план</a></li>
</#macro>

<#include "../../templates/body.ftl"/>
<@body title="Журнал посещений" styles=["/resources/css/journal.css"]>
<div class="container">
    <div class="row">
        <div class="col-md-12">

            <div class="col-md-4">
                <select id="subject" class="form-control select-list dev_selected_discipline">
                    <option value="" selected disabled></option>
                    <#list disciplines as d>
                        <option value="${d.id}" <#if d.id == discipline!''>selected</#if>>${d.discipline}</option>
                    </#list>
                </select>
            </div>

            <button type="submit" class="btn-primary btn-primary-journal dev_show" disabled>Показать</button>
            <div class="row col-md-12 teacher-margin">
            </div>
            <#if journal??>
                <#assign x = 0>
                <#list journal as student, map>
                    <#if map?size != 0>
                        <#assign x = x + 1>
                        <#break>
                    </#if>
                </#list>
                <div class="widget-main col-lg-12" <#if x == 0> hidden</#if>>
                    <div class="widget-inner">
                        <div id="student-journal" class="slider-testimonials">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th></th>
                                    <#list journal as student, map>
                                        <#list map as time, daybook>
                                            <th class="text-center">${time?string["dd.MM.yyyy"]}</th>
                                        </#list>
                                        <#break>
                                    </#list>

                                </tr>
                                </thead>
                                <tbody>
                                    <#list journal as student, map>
                                    <tr>
                                        <th scope="row">${student.surname} ${student.name}</th>
                                        <#list map as time, daybook>
                                            <th scope="row">${daybook!''}</th>
                                        </#list>
                                    </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </#if>
            <div class="row col-md-12 teacher-margin dev_show_if_not_exist" <#if journal?? && x != 0 > hidden </#if>>
                <span class="teacher">Для выбранной дисциплины еще нет журнала посещений</span>
            </div>
        </div>
    </div>
</div>
<script type="application/javascript">
    $(document).on('ready', function () {
        $('.dev_selected_discipline').on('change', function () {
            $('.dev_show').prop('disabled', false);
        });
        $('.dev_show').on('click', function () {
            var dis = $('.dev_selected_discipline').val();
            location.href = location.origin + location.pathname + '?discipline=' + dis;
        });
    });
</script>
</@body>