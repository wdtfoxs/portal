<#include "../../templates/body.ftl"/>
<#-- @ftlvariable name="curriculum" type="com.code405.web.dto.CurriculumView" -->
<#macro menu>
<li><a href="/cabinet">Личный кабинет</a></li>
<li><a href="/timetable/employee/week">Расписание</a></li>
<li><a href="/exams/employee">Расписание экзаменов</a></li>
<li><a href="/journal/employee">Журнал посещений</a></li>
<li class="active"><a href="/curriculum/employee">Учебный план</a></li>
</#macro>
<@body title="Учебный план">
<div id="curicModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Заголовок модального окна -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 id="add_ex" class="modal-title text-center">Добавить учебный план</h4>
            </div>
            <!-- Модальное окно новостей -->
            <@form.form enctype ="multipart/form-data" method="post" id="form1" action="/curriculum/employee/save">
                <div class="modal-body" align="center">
                    <div class="form-group">
                        <label for="example-date-input" class="col-2 col-form-label">Курс</label>
                        <div class="col-10">
                            <select name="course" required="required" class="form-control" id="course">
                                <option selected="selected" disabled="disabled">Выберите курс</option>
                                <option value="1">1 курс</option>
                                <option value="2">2 курс</option>
                                <option value="3">3 курс</option>
                                <option value="4">4 курс</option>
                            </select>
                        </div>
                    </div>
                    <br>
                    <div class="form-group">
                        <label for="example-date-input" class="col-2 col-form-label">Группа</label>
                        <div class="col-10">
                            <select name="groupCode" required="required" class="form-control" id="group">
                            </select>
                        </div>
                    </div>
                    <label class="control-label">TXT Файл с учебным планом: </label>
                    <p>Файл в формате: лекц. часов, практ. часов, лекц. часов, предмет, тип, семестр<br>
                    Пример: 36,0,36,Физика,ДИФ_ЗАЧЕТ,2</p>
                    <br><label id="error" style="display: none; color: red" class="control-label">Не является текстовым документом</label>
                    <input name="file" id="file" type="file" required="required" class="filestyle"
                           data-buttonText="Выбрать изображение">
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </div>
            </@form.form>
        </div>
    </div>
</div>
<div class="container">
    <div id="cource_selector">
        <select class="form-control" id="course_select">
            <option disabled selected value="">Выберите курс</option>
            <option value="1">1 курс</option>
            <option value="2">2 курс</option>
            <option value="3">3 курс</option>
            <option value="4">4 курс</option>
        </select>
        <select class="form-control" id="group_select">

        </select>
    </div>
    <#if message??>
    <h2 style="text-align: center; padding-bottom: 10px">${message}</h2>
    </#if>
    <div class="exam_buttons text-center">
        <button class="btn btn-primary" id="add_exam_button" href="#curicModal" data-toggle="modal">Добавить/изменить
            учебный план
        </button>
    </div>
    <div class="container" id="curriculum">

    </div>
</div>
<script>
    $(document).ready(function () {
        $('#course_select').on('change', function () {
            $('#curriculum').hide();
            let value = $(this).val();
            if (value != null) {
                $.ajax({
                    url: "/curriculum/employee/groups",
                    type: 'get',
                    contentType: "application/json; charset=utf-8",
                    data: {
                        course: value
                    }
                }).success(function (data) {
                    $("#group_select").html(data);
                }).error(function () {
                    alert("error")
                });
            }
        });
        let groupQ = $('#group');
        $('#course').on('change', function () {
            let value = $(this).val();
            groupQ.find('option').remove();
            if (value.length !== 0) {
                $.ajax({
                    url: '/timetable/groups',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        course: value
                    },
                    success: function (groups) {
                        groupQ.append($('<option>', {
                            value: "",
                            text: "Выберите группу",
                            disabled: 'disabled',
                            selected: 'selected'
                        }));
                        $.each(groups, function (i, group) {
                            groupQ.append($('<option>', {
                                value: group.groupCode,
                                text: group.groupCode
                            }));
                        });
                    }
                });
            }
        });
        $("#group_select").on('change', function () {
            let value = $(this).val();
            if (value != null) {
                $.ajax({
                    url: '/curriculum/employee/curriculum',
                    type: 'get',
                    contentType: "application/json; charset=utf-8",
                    data: {
                        group: value
                    }
                }).success(function (data) {
                    $("#curriculum").html(data);
                    $('#curriculum').show();
                }).error(function () {
                    alert("error")
                });
            }
        });
        $('#form1').on('submit', function (e) {
            $('#error').hide();
            let file = $('#file');
            let fileType = file[0].files[0]['type'];
            let validImageTypes = ["text/plain"];
            if ($.inArray(fileType, validImageTypes) < 0) {
                e.preventDefault();
                $('#error').show();
            }
        });
    });
</script>

</@body>