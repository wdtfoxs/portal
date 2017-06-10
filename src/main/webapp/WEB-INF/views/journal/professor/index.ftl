<#macro menu>
<li><a href="/cabinet">Личный кабинет</a></li>
<li><a href="/auditorium">Аудитории</a></li>
<li><a href="/timetable/professor">Расписание</a></li>
<li><a href="/exams/professor">Расписание экзаменов</a></li>
<li class="active"><a href="/journal/professor">Журнал посещений</a></li>
</#macro>
<#include "../../templates/body.ftl">
<@body title="Журнал посещений" styles=["/resources/css/journal.css", "/resources/css/jquery-ui.css"] scripts=["/resources/js/jquery-ui.min.js"]>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="col-md-3">
                <select id="course" class="form-control select-list dev_selected_discipline">
                    <option value="" selected disabled></option>
                    <#list disciplines as group, discipline>
                        <#list discipline as d>
                            <#assign x = group +'_' + d.id>
                        <#--<input hidden value="${x.isvalid == current_disc}">-->
                            <option value="${group}_${d.id}"
                                    <#if x == "${current_group!''}_${current_disc!''}">selected</#if>>${group} ${d.discipline}</option>
                        </#list>
                    </#list>
                </select>
            </div>
            <button type="submit" class="btn-primary btn-primary-journal dev_show" disabled>Показать</button>
            <button type="submit"
                    class="btn-primary btn-primary-journal dev_add" <#if !current_group?? && !current_disc??>
                    disabled </#if>>Добавить занятие
            </button>
            <button type="submit" class="btn-primary btn-primary-journal dev_save" hidden>Сохранить</button>
            <button type="submit" class="btn-primary btn-primary-journal dev_cancel" hidden>Отмена</button>
            <br><br>

            <div class="row col-md-14">

            </div>

            <#if journal??>
                <#assign x = 0>
                <#list journal as student, map>
                    <#if map?size != 0>
                        <#assign x = x + 1>
                        <#break>
                    </#if>
                </#list>
                <div class="widget-main col-lg-12 dev_hide_table" <#if x == 0> hidden</#if>>
                    <div class="widget-inner">
                        <div id="professor-journal" class="slider-testimonials">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th class="text-center dev_column_date"
                                        style="border: 1px solid #2e6da4;border-right: 1px solid white; color: #ffffff; background: #2e6da4"
                                        hidden><input size="10" class="dev_date"
                                                      style="cursor: pointer; background: #2e6da4; color: white; text-align: center;"
                                                      value="Дата"></th>
                                    <th class="text-center dev_column_n"
                                        style="border: 1px solid #2e6da4; border-left: 1px solid white; color: #ffffff; background: #2e6da4"
                                        hidden>Н
                                    </th>
                                    <#list journal as student, map>
                                        <#list map as time, daybook>
                                            <th class="dev_used_time">${time?string["dd.MM.yyyy"]}</th>
                                        </#list>
                                        <#break>
                                    </#list>
                                </tr>
                                </thead>
                                <tbody>
                                    <#list journal as student, map>
                                    <tr>
                                        <th scope="row"
                                            student_id="${student.id}">${student.surname} ${student.name}</th>
                                        <th scope="row" class="dev_column_add" hidden contenteditable="true"
                                            style="border: 1px solid #2e6da4"></th>
                                        <th scope="row" class="dev_column_add_n" hidden
                                            style="cursor: pointer; border: 1px solid #2e6da4"></th>
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
        $('.dev_date').datepicker({
            dateFormat: 'dd.mm.yy',
            closeText: 'Закрыть',
            prevText: 'Назад',
            nextText: 'Вперед',
            currentText: 'Сегодня',
            monthNames: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь',
                'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
            monthNamesShort: ['Январь', 'Февраль', 'Март', 'Апрель', 'Май', 'Июнь',
                'Июль', 'Август', 'Сентябрь', 'Октябрь', 'Ноябрь', 'Декабрь'],
            dayNames: ['воскресенье', 'понедельник', 'вторник', 'среда', 'четверг', 'пятница', 'суббота'],
            dayNamesShort: ['вск', 'пнд', 'втр', 'срд', 'чтв', 'птн', 'сбт'],
            dayNamesMin: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
            weekHeader: 'Нед'
        });
        $('.dev_selected_discipline').on('change', function () {
            $('.dev_show').prop('disabled', false);
        });
        $('.dev_show').on('click', function () {
            var dis = $('.dev_selected_discipline').val();
            location.href = location.origin + location.pathname + '?group_discipline=' + dis;
        });
        $('.dev_add').on('click', function () {
            $('.dev_column_add, .dev_column_add_n, .dev_column_date, .dev_column_n, .dev_save, .dev_hide_table, .dev_cancel').prop('hidden', false);
            $('.dev_add, .dev_show_if_not_exist').hide();
        });
        $('.dev_cancel').on('click', function () {
            $('.dev_column_add, .dev_column_add_n, .dev_column_date, .dev_column_n, .dev_save, .dev_cancel').prop('hidden', true);
            $('.dev_add').show();
            $('th[class="dev_column_add"]').each(function () {
                $(this).text('');
                $(this).parent().children().eq(2).text('')
            });
            $('.dev_date').val('Дата');
        });
        <#if journal?? && x == 0>
            $('.dev_cancel').on('click', function () {
                $('.dev_hide_table').prop('hidden', true);
                $('.dev_show_if_not_exist').show();
            });
        </#if>
        $('.dev_column_add_n').on('click', function () {
            if ($(this).text() === '')
                $(this).text('н');
            else
                $(this).text('');
        });
        $('.dev_save').on('click', function () {
            var dayBookList = [];
            var k = 0;
            var flag = true;
            if ($('.dev_date').val() === 'Дата') {
                alert('Выберите дату занятия!');
                return flag = false;
            }
            $('th[class="dev_used_time"]').each(function () {
                if (compare($(this).text(), $('.dev_date').val()) === 0) {
                    alert($(this).text() + ' - уже существует запись об этом занятии в данную дату');
                    return flag = false;
                }
            });
            if (!flag)
                return false;
            $('th[class="dev_column_add"]').each(function () {
                var temp = {};
                var group = {};
                temp['group'] = '${current_group!''}';
                temp['student'] = parseInt($(this).parent().children().first().attr('student_id'));
                temp['discipline'] = parseInt('${current_disc!''}');
                temp['date'] = $('.dev_date').val();
                temp['presence'] = $(this).parent().children().eq(2).text() === '';
                if ($(this).text() === '' || $.isNumeric($(this).text()) && parseInt($(this).text()) <= 50 && parseInt($(this).text()) >= 0)
                    temp['points'] = parseInt($(this).text());
                else {
                    if (!$.isNumeric($(this).text()))
                        alert('В поле студента "' + $(this).parent().children().first().text() + '" установлено значение отличное от числа');
                    else
                        alert('В поле студента "' + $(this).parent().children().first().text() + '" установлено недопустимое значение');
                    return flag = false;
                }
                dayBookList[k] = temp;
                k++;
            });
            if (!flag)
                return false;
            $.ajax({
                url: '/journal/professor?lesson',
                type: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(dayBookList),
                success: function (response) {
                    if (response === '200')
                        location.reload();
                    else
                        alert('Ooops, something went wrong :(')
                },
                error: function (e) {
                    alert('Ooops, something went wrong :(');
                    console.log(e);
                }
            });
        });
        function compare(dateTimeA, dateTimeB) {
            var momentA = moment(dateTimeA, "DD.MM.YYYY");
            var momentB = moment(dateTimeB, "DD.MM.YYYY");
            if (momentA > momentB) return 1;
            else if (momentA < momentB) return -1;
            else return 0;
        }

    });
</script>
</@body>