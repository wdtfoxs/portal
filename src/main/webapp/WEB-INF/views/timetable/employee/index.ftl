<#macro menu>
<li><a href="/cabinet">Личный кабинет</a></li>
<li class="active"><a href="/timetable/employee/week">Расписание</a></li>
<li><a href="/exams/employee">Расписание экзаменов</a></li>
<li><a href="/journal/employee">Журнал посещений</a></li>
<li><a href="/curriculum/employee">Учебный план</a></li>
</#macro>
<#include "../../templates/body.ftl"/>
<@body title="Расписание">
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
    <div class="text-center">
        <div style="display: none" class="schedule_edit_buttonr" id="sch_ed_btn">
            <button class="btn btn-primary" id="add_changes">Внести изменения</button>
        </div>
        <div style="display: none" class=" schedule_edit_button_after" id="sch_ed_btn_after">
            <button class="btn btn-primary" id="cancel_changes">Отменить изменения</button>
            <button class="btn btn-primary" id="save_changes">Сохранить изменения</button>
        </div>
        <div id="week_schedule">
        </div>

    </div>
</div>

<style>
    .text-center {
        display: block;
        margin-top: 10px;
    }
</style>
    <#include "../../jquery_templates/timetable_template.ftl"/>
<script type="application/javascript">
    $(document).on('ready', function () {
        var days = ["ПОНЕДЕЛЬНИК", "ВТОРНИК", "СРЕДА", "ЧЕТВЕРГ", "ПЯТНИЦА", "СУББОТА"];
        var classes = ["ПЕРВАЯ", "ВТОРАЯ", "ТРЕТЬЯ", "ЧЕТВЕРТАЯ", "ПЯТАЯ", "ШЕСТАЯ", "СЕДЬМАЯ"];

        let week_timetable = $('#week_schedule');
        $('#add_changes').click(function () {
            $('#sch_ed_btn').hide();
            $('#sch_ed_btn_after').show();
            week_timetable.find('select').attr('disabled', false);
            week_timetable.find('input').attr('disabled', false);
        });
        $('#save_changes').click(function () {
            let timetables = [];
            let needToStop = false;
            for (let i = 0; i < days.length && !needToStop; i++) {
                let day = days[i];
                for (let j = 0; j < classes.length; j++) {
                    let classQ = classes[j];
                    let timetable = {};
                    timetable.day = day;
                    timetable.classNum = classQ;
                    let types = $('.types');
                    for (let q = 0; q < types.length; q++) {
                        let type = types[q];
                        if (day === $(type).data('week') && classQ === $(type).data('class')) {
                            timetable.type = $(type).val();
                            break;
                        }
                    }
                    let disciplines = $('.disciplines');
                    for (let q = 0; q < disciplines.length; q++) {
                        let discipline = disciplines[q];
                        if (day === $(discipline).data('week')
                                && classQ === $(discipline).data('class')
                                && $(discipline).val() !== null
                                && $(discipline).val().length !== 0) {
                            let disciplineText = $(discipline).find(':selected').text();
                            timetable.culture = disciplineText === "Физкультура";
                            timetable.discipline = $(discipline).val();
                            break;
                        }
                    }
                    if (typeof timetable.discipline === 'undefined') {
                        continue;
                    }
                    let comments = $('.comments');
                    for (let q = 0; q < comments.length; q++) {
                        let comment = comments[q];
                        if (day === $(comment).data('week') && classQ === $(comment).data('class')) {
                            timetable.comment = $(comment).val();
                            break;
                        }
                    }
                    let weeks = $('.weeks');
                    for (let q = 0; q < weeks.length; q++) {
                        let week = weeks[q];
                        if (day === $(week).data('week') && classQ === $(week).data('class')) {
                            if ($(week).val() === null || $(week).val().length === 0) {
                                alert("Не заполнена неделя! День: " + day + ". Пара: " + classQ);
                                needToStop = true;
                            } else {
                                timetable.week = $(week).val();
                            }
                            break;
                        }
                    }
                    if (needToStop) {
                        break;
                    }
                    if (timetable.culture) {
                        timetables.push(timetable);
                    } else {
                        let professors = $('.professors');
                        for (let q = 0; q < professors.length; q++) {
                            let professor = professors[q];
                            if (day === $(professor).data('week') && classQ === $(professor).data('class')) {
                                if ($(professor).val() === null || $(professor).val().length === 0) {
                                    alert("Не заполнен преподаватель! День: " + day + ". Пара: " + classQ);
                                    needToStop = true;
                                } else {
                                    timetable.professor = $(professor).val();
                                }
                                break;
                            }
                        }
                        if (needToStop) {
                            break;
                        }
                        let auditoriums = $('.auditoriums');
                        for (let q = 0; q < auditoriums.length; q++) {
                            let auditorium = auditoriums[q];
                            if (day === $(auditorium).data('week') && classQ === $(auditorium).data('class')) {
                                if ($(auditorium).val() === null || $(auditorium).val().length === 0) {
                                    alert("Не заполнена аудитория! День: " + day + ". Пара: " + classQ);
                                    needToStop = true;
                                } else {
                                    timetable.auditorium = $(auditorium).val();
                                }
                                break;
                            }
                        }
                        if (!needToStop) {
                            timetables.push(timetable);
                        }
                    }
                }
            }
            if (!needToStop) {
                $.ajax({
                    url: '/timetable/save',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        timetables: JSON.stringify(timetables),
                        groupCode: group_select.find(':selected').val()
                    },
                    success: function (success) {
                        if (success) {

                            $('#sch_ed_btn_after').hide();
                            $('#sch_ed_btn').show();
                            week_timetable.find('select').attr('disabled', true);
                            week_timetable.find('input').attr('disabled', true);
                        }
                    }
                });

            }

        });
        $('#cancel_changes').click(function () {
            $('#sch_ed_btn_after').hide();
            $('#sch_ed_btn').show();
            let value = group_select.find(':selected').val();
            if (value.length !== 0) {
                $.ajax({
                    url: '/timetable/forGroup',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        group: value
                    },
                    success: function (timetables) {
                        let object_timetables = {};
                        object_timetables.timetables = timetables;
                        week_timetable.html($.tmpl('timetable_template', object_timetables));
                        if ($('#add_changes').is(':visible')) {
                            week_timetable.find('select').attr('disabled', true);
                            week_timetable.find('input').attr('disabled', true);
                        }
                    }
                });
            }
            week_timetable.find('select').attr('disabled', true);
            week_timetable.find('input').attr('disabled', true);
        });
        let group_select = $('#group_select');
        $('#course_select').on('change', function () {
            $('#sch_ed_btn').hide();
            $('#sch_ed_btn_after').hide();
            week_timetable.empty();
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
        $('#timetableID').template('timetable_template');
        group_select.on('change', function () {
            let value = $(this).val();
            if (value.length !== 0) {
                $.ajax({
                    url: '/timetable/forGroup',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        group: value
                    },
                    success: function (timetables) {
                        let object_timetables = {};
                        object_timetables.timetables = timetables;
                        if ($('#sch_ed_btn_after').is(':hidden')) {
                            $('#sch_ed_btn').show();
                        } else {
                            $('#sch_ed_btn_after').show();
                        }
                        week_timetable.html($.tmpl('timetable_template', object_timetables));
                        if ($('#add_changes').is(':visible')) {
                            week_timetable.find('select').attr('disabled', true);
                            week_timetable.find('input').attr('disabled', true);
                        }
                    }
                });
            }
        });
        week_timetable.on('change', '.buildings', function () {
            let auditoriums = $('.auditoriums');
            let building = $(this);
            let buildingId = building.val();
            let audit;
            $.each(auditoriums, function (i, auditorium) {
                if (building.data('week') === $(auditorium).data('week')
                        && building.data('class') === $(auditorium).data('class')) {
                    audit = $(auditorium);
                    $(auditorium).find('option').remove();
                    return;
                }
            });
            if (buildingId.length !== 0) {
                $.ajax({
                    url: '/auditorium',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        building: buildingId
                    },
                    success: function (auditoriums) {
                        audit.append($('<option>', {
                            value: "",
                            text: '',
                            selected: 'selected'
                        }));
                        $.each(auditoriums, function (i, auditorium) {
                            audit.append($('<option>', {
                                value: auditorium.value,
                                text: auditorium.text
                            }));
                        });
                    }
                });
            }

        });
        week_timetable.on('change', '.auditoriums', function () {
            let groupCode = group_select.find(':selected').val();
            let auditorium = $(this);
            let week = auditorium.data('week');
            let classQ = auditorium.data('class');
            let types = $('.types');
            let type;
            $.each(types, function (i, typeQ) {
                if ($(typeQ).data('week') === $(auditorium).data('week')
                        && $(typeQ).data('class') === $(auditorium).data('class')) {
                    type = $(typeQ);
                    return;
                }
            });
            if (auditorium.val().length !== 0) {
                $.ajax({
                    url: '/auditorium',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        classQ: classQ,
                        week: week,
                        groupCode: groupCode,
                        auditorium: auditorium.val()
                    },
                    success: function (result) {
                        if (type.val() === "ПРАКТИКА" && result) {
                            alert("Данная аудитория занята другой группой!");
                            auditorium.find('option[value=""]').attr('selected', 'selected');
                        }
                    }
                });
            }

        });
        week_timetable.on('change', '.disciplines', function () {
            let professors = $('.professors');
            let discipline = $(this);
            let disciplineId = discipline.val();
            let disciplineName = discipline.find(':selected').text();
            let prof;
            $.each(professors, function (i, professor) {
                if (discipline.data('week') === $(professor).data('week') &&
                        discipline.data('class') === $(professor).data('class')) {
                    prof = $(professor);
                    $(professor).find('option').remove();
                    return;
                }
            });
            if (disciplineId.length !== 0 && disciplineName !== "Физкультура") {
                $.ajax({
                    url: '/timetable/getProfessors',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        id: disciplineId
                    },
                    success: function (professors) {
                        prof.append($('<option>', {
                            value: "",
                            text: '',
                            selected: 'selected'
                        }));
                        $.each(professors, function (i, professor) {
                            prof.append($('<option>', {
                                value: professor.value,
                                text: professor.text
                            }));
                        });
                    }
                });
            }
        });
        week_timetable.on('change', '.professors', function () {
            let professor = $(this);
            let professorId = $(this).val();
            let weekQ = null;
            let weeks = $('.weeks');
            $.each(weeks, function (i, week) {
                if ($(week).data('week') === professor.data('week') &&
                        $(week).data('class') === professor.data('class')) {
                    weekQ = $(week);
                    return;
                }
            });
            let types = $('.types');
            let type;
            $.each(types, function (i, typeQ) {
                if ($(typeQ).data('week') === $(professor).data('week')
                        && $(typeQ).data('class') === $(professor).data('class')) {
                    type = $(typeQ);
                    return;
                }
            });
            if (professorId.length !== 0 && weekQ.val() !== null && weekQ.val().length !== 0) {
                $.ajax({
                    url: '/timetable/check',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        week: weekQ.val(),
                        day: weekQ.data('week'),
                        groupCode: group_select.find(':selected').val(),
                        professor: professorId,
                        classQ: weekQ.data('class')
                    },
                    success: function (result) {
                        if (type.val() === "ПРАКТИКА" && result) {
                            alert("Данный преподаватель занят в данное время");
                            professor.find('option[value=""]').attr('selected', 'selected');
                        }
                    }
                });
            }
        });
        week_timetable.on('change', '.types', function () {
            let type = $(this);
            let auditoriums = $('.auditoriums');
            $.each(auditoriums, function (i, auditorium) {
                if (type.data('week') === $(auditorium).data('week') &&
                        type.data('class') === $(auditorium).data('class')) {
                    $(auditorium).find('option[value=""]').attr('selected', 'selected');
                    return;
                }
            });
            let professors = $('.professors');
            $.each(professors, function (i, professor) {
                if (type.data('week') === $(professor).data('week') &&
                        type.data('class') === $(professor).data('class')) {
                    $(professor).find('option[value=""]').attr('selected', 'selected');
                    return;
                }
            });
        });

        week_timetable.on('change', '.weeks', function () {
            let week = $(this);
            let weekValue = $(this).val();
            let prof = null;
            let professors = $('.professors');
            $.each(professors, function (i, professor) {
                if (week.data('week') === $(professor).data('week') &&
                        week.data('class') === $(professor).data('class')) {
                    prof = $(professor);
                    return;
                }
            });
            let types = $('.types');
            let type;
            $.each(types, function (i, typeQ) {
                if ($(typeQ).data('week') === $(week).data('week')
                        && $(typeQ).data('class') === $(week).data('class')) {
                    type = $(typeQ);
                    return;
                }
            });
            if (weekValue.length !== 0 && prof.val() !== null && prof.val().length !== 0) {
                $.ajax({
                    url: '/timetable/check',
                    type: 'GET',
                    dataType: 'json',
                    data: {
                        week: weekValue,
                        day: week.data('week'),
                        groupCode: group_select.find(':selected').val(),
                        professor: prof.val(),
                        classQ: week.data('class')
                    },
                    success: function (result) {
                        if (type.val() === "ПРАКТИКА" && result) {
                            alert("Данный преподаватель занят в данную неделю.");
                            week.find('option[value=""]').attr('selected', 'selected');
                        }
                    }
                });
            }
        });
    });
</script>
</@body>