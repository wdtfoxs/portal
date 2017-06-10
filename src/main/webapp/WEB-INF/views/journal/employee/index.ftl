<#macro menu>
<li class="active"><a href="/cabinet">Личный кабинет</a></li>
<li><a href="/timetable/employee/week">Расписание</a></li>
<li><a href="/exams/employee">Расписание экзаменов</a></li>
<li class="active"><a href="/journal/employee">Журнал посещений</a></li>
<li><a href="/curriculum/employee">Учебный план</a></li>
</#macro>

<#include "../../templates/body.ftl"/>
<@body title="Журнал посещений" styles=["/resources/css/journal.css"]>
<div class="container">
    <div id="cource_selector">
        <select class="form-control dev_course">
            <option disabled selected value="">Выберите курс</option>
            <option value="1">1 курс</option>
            <option value="2">2 курс</option>
            <option value="3">3 курс</option>
            <option value="4">4 курс</option>
        </select>
        <select class="form-control dev_group">
            <#--<option value="" selected disabled>Выберите группу</option>-->
        </select>
        <select class="form-control dev_disc">
            <#--<option value="" selected disabled>Выберите предмет</option>-->
        </select>
    </div>

    <div class="row">
        <div class="col-md-12">
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
        $('.dev_course').on('change', function () {
            $.ajax({
                url: '/timetable/groups',
                type: 'GET',
                dataType: 'json',
                data: {
                    course: $(this).val()
                },
                success: function (groups) {
                    $('.dev_group').html('');
                    $('.dev_group').append($('<option>', {
                        value: "",
                        text: "Выберите группу",
                        disabled: 'disabled',
                        selected: 'selected'
                    }));
                    $.each(groups, function (i, group) {
                        $('.dev_group').append($('<option>', {
                            value: group.groupCode,
                            text: group.groupCode
                        }));
                    });
                }
            });
        });
        $('.dev_group').on('change', function () {
            $.ajax({
                url: '/journal/employee?disc&course=' +  $('.dev_course').val() + '&group=' + $(this).val(),
                type: 'GET',
                dataType: 'json',
                success: function (disciplines) {
                    $('.dev_disc').html('');
                    $('.dev_disc').append($('<option>', {
                        value: "",
                        text: "Выберите предмет",
                        disabled: 'disabled',
                        selected: 'selected'
                    }));
                    $.each(disciplines, function (i, discipline) {
                        $('.dev_disc').append($('<option>', {
                            value: discipline.id,
                            text: discipline.discipline
                        }));
                    });
                }
            });
        });
        $('.dev_disc').on('change', function () {
            location.href = location.origin + location.pathname + '?discipline=' + $(this).val()
                    + '&group=' + $('.dev_group').val() + '&course=' + $('.dev_course').val();
        });

    });
</script>
</@body>