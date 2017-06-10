<#include "../templates/body.ftl"/>
<#-- @ftlvariable name="day" type="com.code405.entity.enumeration.WeekDay" -->
<#-- @ftlvariable name="time" type="com.code405.entity.enumeration.Class" -->
<#macro menu>
<li><a href="/cabinet">Личный кабинет</a></li>
<li class="active"><a href="/auditorium">Аудитории</a></li>
<li><a href="/timetable/professor">Расписание</a></li>
<li><a href="/exams/professor">Расписание экзаменов</a></li>
<li><a href="/journal/professor">Журнал посещений</a></li>
</#macro>
<@body title="Свободные аудитории" styles=["/resources/css/auditorium.css"]>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="table-title">
                <div class="container">
                    <div class="row">
                        <div class="col-md-3 col-sm-3 col-xs-3 sec">
                            <select class="form-control" id="day_select">
                                <option disabled selected value="">День</option>
                                <#list days as day>
                                    <option>${day.name()}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="col-md-3 col-sm-3 col-xs-3 sec">
                            <select class="form-control" id="time_select">
                                <option disabled selected value="">Пара</option>
                                <#list times as time>
                                    <option value="${time.name()}">${time.time}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="auditoriums_fill">

        </div>
        <@spinner/>
    </div>
</div>

<script>
    $(document).ready(function () {
                $("#time_select").on('change', function () {
                    let time = $(this).val();
                    let day = $("#day_select").val();
                    getAuditories(time, day)
                });
                $("#day_select").on('change', function () {
                    let day = $(this).val();
                    let time = $("#time_select").val();
                    getAuditories(time, day)
                });
                function getAuditories(time, day) {
                    if (time != null && time.length !== 0 && day != null && day.length !== 0) {
                        $.ajax({
                            url: "/auditorium/free",
                            type: 'get',
                            datatype: "json",
                            contentType: "application/json; charset=utf-8",
                            data: {
                                day: day,
                                time: time
                            }
                        }).success(function (data) {
                            $("#auditoriums_fill").html(data);
                        }).error(function () {
                            alert("На сервере произошла ошибка! Пожалуйста, повторите Ваш запрос позднее.")
                        });
                    }
                }
            }
    );
</script>
</@body>