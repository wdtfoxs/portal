<#include "../../templates/body.ftl"/>

<#macro menu>
<li><a href="/cabinet">Личный кабинет</a></li>
<li><a href="/timetable/employee/week">Расписание</a></li>
<li class="active"><a href="/exams/employee">Расписание экзаменов</a></li>
<li><a href="/journal/employee">Журнал посещений</a></li>
<li><a href="/curriculum/employee">Учебный план</a></li>
</#macro>

<@body title="Расписание экзаменов">
<div id="examsModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Заголовок модального окна -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 id="add_ex" class="modal-title text-center"></h4>
            </div>
            <!-- Модальное окно новостей -->
            <@form.form enctype ="multipart/form-data" method="post" id="form1" action="/exams/employee/add">
                <div class="modal-body" align="center">
                    <br><label id="create_error" style="display: none; color: red" class="control-label">Дата
                    проведения не может быть раньше сегодня</label>
                    <div class="form-group">
                        <label for="example-date-input" class="col-2 col-form-label">Дата проведения</label>
                        <div class="col-10">
                            <input required="required" name="date" class="form-control" type="date"
                                   id="create_date">
                        </div>
                    </div>
                    <br><label id="create_time_error" style="display: none; color: red" class="control-label">Невалидное
                    время проведения</label>
                    <div class="form-group">
                        <label for="example-time-input" class="col-2 col-form-label">С</label>
                        <div class="col-10">
                            <input required="required" name="from" class="form-control" type="time"
                                   id="create_from">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="example-time-input" class="col-2 col-form-label">До</label>
                        <div class="col-10">
                            <input required="required" name="to" class="form-control" type="time"
                                   id="create_to">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="comment">Тип</label>
                        <select required="required" class="form-control" id="type_select">
                            <option selected="selected" value="ЭКЗАМЕН">Экзамен</option>
                            <option value="ЗАЧЕТ">Зачет</option>
                            <option value="ДИФ_ЗАЧЕТ">Диф. зачет</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="comment">Здание</label>
                        <select required="required" class="form-control" id="building_select">
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="comment">Аудитория</label>
                        <select required="required" class="form-control" id="auditorium_select">
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="comment">Предмет</label>
                        <select required="required" class="form-control" id="discipline_select">
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="comment">Преподаватель</label>
                        <select required="required" class="form-control" id="professor_select">
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">Добавить</button>
                </div>
            </@form.form>
        </div>
    </div>
</div>
<div id="editModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- Заголовок модального окна -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 id="add_ex" class="modal-title text-center">Изменить время</h4>
            </div>
            <!-- Модальное окно новостей -->
            <@form.form enctype ="multipart/form-data" method="post" id="form2" action="/exams/employee/update">
                <div class="modal-body" align="center">
                    <br><label id="create_error1" style="display: none; color: red" class="control-label">Дата
                    проведения не может быть раньше сегодня</label>
                    <div class="form-group">
                        <label for="example-date-input" class="col-2 col-form-label">Дата проведения</label>
                        <div class="col-10">
                            <input required="required" name="date" class="form-control" type="date"
                                   id="create_date1">
                        </div>
                    </div>
                    <br><label id="create_time_error1" style="display: none; color: red" class="control-label">Невалидное
                    время проведения</label>
                    <div class="form-group">
                        <label for="example-time-input" class="col-2 col-form-label">С</label>
                        <div class="col-10">
                            <input required="required" name="from" class="form-control" type="time"
                                   id="create_from1">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="example-time-input" class="col-2 col-form-label">До</label>
                        <div class="col-10">
                            <input required="required" name="to" class="form-control" type="time"
                                   id="create_to1">
                        </div>
                    </div>
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
    <div id="container" hidden class="text-center">
        <div class="exam_buttons">
            <button class="btn btn-primary" id="add_exam_button" href="#examsModal" data-toggle="modal">Добавить
                экзамен
            </button>
        </div>
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
                                <td>Группа</td>
                                <td>Предмет</td>
                                <td>Тип</td>
                                <td>Действие</td>
                            </tr>
                            </thead>
                            <tbody id="exam_body">
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<script type="application/javascript">
    $(document).ready(function () {

        let buildings_select = $('#building_select');
        let auditorium_select = $('#auditorium_select');
        let professor_select = $('#professor_select');
        let disciplines_select = $('#discipline_select');
        $('#add_exam_button').on('click', function () {
            disciplines_select.find('option').remove();
            fill_disciplines();
            let groupCode = group_select.find(':selected').val();
            $('#add_ex').text("Добавить экзамен " + groupCode + " группе")
        });
        var current_exam;
        $('#exam_body').on('click', 'a', function () {
            current_exam = $(this).data('id');
        });
        buildings_select.on('change', function () {
            let value = $(this).val();
            auditorium_select.find('option').remove();
            $.ajax({
                url: '/auditorium',
                type: 'GET',
                dataType: 'json',
                data: {
                    building: value
                },
                success: function (auditoriums) {
                    auditorium_select.append($('<option>', {
                        value: "",
                        text: 'Выберите аудиторию',
                        selected: 'selected',
                        disabled: 'disabled'
                    }));
                    $.each(auditoriums, function (i, auditorium) {
                        auditorium_select.append($('<option>', {
                            value: auditorium.value,
                            text: auditorium.text
                        }));
                    });
                }
            });
        });
        disciplines_select.on('change', function () {
            let discipline = $(this);
            let disciplineId = discipline.val();
            let disciplineName = discipline.find(':selected').text();
            professor_select.find('option').remove();
            if (disciplineName === "Физкультура") {
                professor_select.append($('<option>', {
                    value: "-1",
                    text: '',
                    selected: 'selected'
                }));
            }
            if (disciplineId.length !== 0 && disciplineName !== "Физкультура") {
                $.ajax({
                    url: '/timetable/getProfessors',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        id: disciplineId
                    },
                    success: function (professors) {

                        professor_select.append($('<option>', {
                            value: "",
                            text: 'Выберите преподавателя',
                            selected: 'selected',
                            disabled: 'disabled'
                        }));
                        $.each(professors, function (i, professor) {
                            professor_select.append($('<option>', {
                                value: professor.value,
                                text: professor.text
                            }));
                        });
                    }
                });
            }
        });


        function fill_disciplines() {
            $.ajax({
                url: '/timetable/disciplines',
                type: 'GET',
                dataType: 'json',
                data: {
                    groupCode: group_select.find(':selected').val()
                },
                success: function (disciplines) {
                    professor_select.find('option').remove();
                    disciplines_select.append($('<option>', {
                        value: "",
                        text: 'Выберите предмет',
                        selected: 'selected',
                        disabled: 'disabled'
                    }));
                    $.each(disciplines, function (i, discipline) {
                        disciplines_select.append($('<option>', {
                            value: discipline[0],
                            text: discipline[1]
                        }));
                    });
                }
            })
        }

        fill_build();
        function fill_build() {
            $.ajax({
                url: '/timetable/buildings',
                type: 'GET',
                dataType: 'json',
                success: function (buildings) {
                    auditorium_select.find('option').remove();
                    buildings_select.append($('<option>', {
                        value: "",
                        text: 'Выберите здание',
                        selected: 'selected',
                        disabled: 'disabled'
                    }));
                    $.each(buildings, function (i, building) {
                        buildings_select.append($('<option>', {
                            value: building.id,
                            text: building.name
                        }));
                    });
                }
            });
        }

        let exam_timetable = $('#container');
        let group_select = $('#group_select');
        let exam_body = $('#exam_body');
        $('#course_select').on('change', function () {
            exam_timetable.hide();
            let value = $(this).val();
            group_select.find('option').remove();
            if (value.length !== 0) {
                $.ajax({
                    url: '/timetable/groups',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        course: value
                    },
                    success: function (groups) {
                        group_select.append($('<option>', {
                            value: "",
                            text: "Выберите группу",
                            disabled: 'disabled',
                            selected: 'selected'
                        }));
                        $.each(groups, function (i, group) {
                            group_select.append($('<option>', {
                                value: group.groupCode,
                                text: group.groupCode
                            }));
                        });
                    }
                });
            }
        });
        group_select.on('change', function () {
            let value = $(this).val();
            if (value.length !== 0) {
                $.ajax({
                    url: '/exams/employee/show',
                    type: 'GET',
                    dataType: 'html',
                    data: {
                        group: value
                    },
                    success: function (exams) {
                        exam_body.html(exams);
                        exam_timetable.show();
                    }
                });
            }
        });
        $('#form1').submit(function (e) {
            e.preventDefault();
            $('#error').hide();
            $('#create_error').hide();
            $('#create_time_error').hide();
            let beginningTime = moment($('#create_from').val(), 'HH:mm');
            let endTime = moment($('#create_to').val(), 'HH:mm');
            let createDate = $('#create_date').val();
            let type = $('#type_select').val();
            let auditorium = $('#auditorium_select').val();
            let discipline = $('#discipline_select').val();
            let professor = $('#professor_select').val();
            if (moment(moment.unix(new Date() / 1000).format('YYYY-MM-DD')).isAfter(createDate)) {
                $('#create_error').show();
                return;
            }
            if (!beginningTime.isBefore(endTime)) {
                $('#create_time_error').show();
                return;
            }
            $.ajax({
                url: "/exams/employee/add",
                dataType: 'json',
                type: 'post',
                data: {
                    createDate: createDate,
                    beginningTime: $('#create_from').val(),
                    endTime: $('#create_to').val(),
                    type: type,
                    auditorium: auditorium,
                    discipline: discipline,
                    professor: professor,
                    groupCode: group_select.find(':selected').val()
                },
                success: function (result) {
                    if (result === "Добавлено") {
                        $('.close').click();
                        $.ajax({
                            url: '/exams/employee/show',
                            type: 'GET',
                            dataType: 'html',
                            data: {
                                group: group_select.find(':selected').val()
                            },
                            success: function (exams) {
                                exam_body.html(exams);
                            }
                        });
                    } else {
                        alert(result);
                    }

                },
                statusCode: {
                    400: function (result) {
                        alert(result);
                    }
                }
            })
        });
        $('#editModal').on('hidden.bs.modal', function () {
            $('#form2').find('input').val("");
        });
        $('#examsModal').on('hidden.bs.modal', function () {
            $('#form1').find('input').val("");
            let elements = $('#form1').find('select');
            for (let i = 0; i < elements.length; i++) {
                let element = $(elements[i]);
                if (element.attr('id') !== 'type_select' && element.attr('id') !== 'building_select') {
                    element.find('option').remove();
                }
            }
        });

        $('#form2').submit(function (e) {
            e.preventDefault();
            $('#create_error1').hide();
            $('#create_time_error1').hide();
            let beginningTime = moment($('#create_from1').val(), 'HH:mm');
            let endTime = moment($('#create_to1').val(), 'HH:mm');
            let createDate = $('#create_date1').val();
            if (moment(moment.unix(new Date() / 1000).format('YYYY-MM-DD')).isAfter(createDate)) {
                $('#create_error1').show();
                return false;
            }
            if (!beginningTime.isBefore(endTime)) {
                $('#create_time_error1').show();
                return false;
            }
            console.log(endTime);
            console.log(current_exam);
            $.ajax({
                url: "/exams/employee/update",
                dataType: 'json',
                type: 'post',
                data: {
                    createDate: createDate,
                    beginningTime: $('#create_from1').val(),
                    endTime: $('#create_to1').val(),
                    id: current_exam
                },
                success: function (result) {
                    if (result === "Изменено") {
                        $('.close').click();
                        $.ajax({
                            url: '/exams/employee/show',
                            type: 'GET',
                            dataType: 'html',
                            data: {
                                group: group_select.find(':selected').val()
                            },
                            success: function (exams) {
                                exam_body.html(exams);
                            }
                        });
                    } else {
                        alert(result);
                    }
                },
                statusCode: {
                    400: function (result) {
                        alert(result);
                    }
                }
            })
        })
    });
</script>
</@body>