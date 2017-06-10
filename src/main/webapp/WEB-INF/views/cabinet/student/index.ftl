<#macro menu>
<li class="active"><a href="/cabinet">Личный кабинет</a></li>
<li><a href="/rating">Рейтинг</a></li>
<li><a href="/timetable/student">Расписание</a></li>
<li><a href="/exams/student">Расписание экзаменов</a></li>
<li><a href="/journal/student">Журнал посещений</a></li>
<li><a href="/curriculum/student">Учебный план</a></li>
</#macro>
<@body title="Страница студента" scripts=["/resources/js/student.js"]>
    <#include "../../jquery_templates/cabinet_table_student_template.ftl">
<div class="container">
    <div class="text-center">
        <div class="widget-main-title">
            <h4 class="widget-title">${student.name} ${student.surname}</h4>
        </div>
    </div>
</div>
<div class="container">
    <div class="text-center">
        <div class="widget-main col-lg-12">
            <div class="widget-main-title">
                <h4 class="widget-title">Заявляния по справкам</h4>
            </div>
            <div class="widget-inner">
                <div class="slider-testimonials">
                <table class="table">
                    <thead>
                    <tr>
                        <td><b>Учебная группа</b></td>
                        <td><b>Количество</b></td>
                        <td><b>Тип</b></td>
                        <td></td>
                    </tr>
                    </thead>
                    <tbody>
                    <td>${student.group.groupCode}</td>
                    <td>
                        <div class="input-group-btn">
                            <select data-toggle="dropdown" class="btn btn-default dropdown-toggle dev_count_doc"><span
                                    class="caret"></span>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                            </select>
                        </div>
                    </td>
                    <td>
                        <div class="input-group-btn">
                            <select data-toggle="dropdown" class="btn btn-default dropdown-toggle dev_type_doc"><span
                                    class="caret"></span>
                                <option value="1">Справка об обучении</option>
                                <option value="2">Справка для работы</option>
                            </select>
                        </div>
                    </td>
                    <td>
                        <button class="btn btn-default dev_order_btn">Заказать</button>
                    </td>
                    </tbody>
                </table>
                </div>
            <div>
                    <div class="slider-testimonials">
                        <table class="table">
                            <thead>
                            <tr>
                                <td><b>№</b></td>
                                <td><b>Тип документа</b></td>
                                <td><b>Дата подачи</b></td>
                                <td><b>Статус</b></td>
                            </tr>
                            </thead>
                            <tbody class="tmpl">
                                <#list documents as r>
                                <tr>
                                    <td>${r.id}</td>
                                    <td>${r.type}</td>
                                    <td>${r.created}</td>
                                    <td>${r.status}</td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@body>