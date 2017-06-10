<#include "../../templates/body.ftl"/>

<#-- @ftlvariable name="timetable" type="java.util.Map<com.code405.entity.enumeration.WeekDay, java.util.Map<com.code405.entity.enumeration.Class,com.code405.entity.model.Timetable>>" -->
<#macro menu>
<li><a href="/cabinet">Личный кабинет</a></li>
<li><a href="/auditorium">Аудитории</a></li>
<li class="active"><a href="/timetable/professor">Расписание</a></li>
<li><a href="/exams/professor">Расписание экзаменов</a></li>
<li><a href="/journal/professor">Журнал посещений</a></li>
</#macro>

<@body title="Расписание">
<div class="container">
    <div class="text-center">
        <div class="week_type">
            <h3>Текущая неделя ${week_type}</h3>
        </div>
        <div class="schedule_type_buttons" id="sch_types">
            <button class="btn btn-primary " id="week">Неделя</button>
            <button class="btn btn-primary active" id="day">День</button>
        </div>
        <div id="day_schedule" style="display: none">

        </div>
        <div id="week_schedule">

        </div>
        <@spinner/>
    </div>
</div>
<script>
    $(document).ready(function () {
        dayClickFunction();
        $('#week').click(function () {
            weekClickFunction();
        });
        $('#day').click(function () {
            dayClickFunction();
        });
        function dayClickFunction() {
            $('#week_schedule').hide();
            $('#day_schedule').show();
            $('#week').removeClass('active');
            $('#day').addClass('active');
            $.ajax({
                url: "/timetable/professor/day",
                type: 'get',
                contentType: "application/json; charset=utf-8"
            }).success(function (data) {
                $("#day_schedule").html(data);
            }).error(function () {
                alert("error")
            })
        }
        function weekClickFunction() {
            $('#day_schedule').hide();
            $('#week_schedule').show();
            $('#day').removeClass('active');
            $('#week').addClass('active');
            $.ajax({
                url: "/timetable/professor/week",
                type: 'get',
                contentType: "application/json; charset=utf-8"
            }).success(function (data) {
                $("#week_schedule").html(data);
            }).error(function () {
                alert("error")
            })
        }
    });
</script>
</@body>