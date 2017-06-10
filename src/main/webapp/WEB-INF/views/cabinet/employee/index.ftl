<#macro menu>
<li class="active"><a href="/cabinet">Личный кабинет</a></li>
<li><a href="/timetable/employee/week">Расписание</a></li>
<li><a href="/exams/employee">Расписание экзаменов</a></li>
<li><a href="/journal/employee">Журнал посещений</a></li>
<li><a href="/curriculum/employee">Учебный план</a></li>
</#macro>
<@body title="Страница сотрудника деканата">
<div class="container">
    <div class="text-center">
        <div class="widget-main-title">
            <h4 class="widget-title">${employee.name} ${employee.surname}</h4>
        </div>
        <div class="widget-main-title">
            <#list employee.positions as p>
                <div class="blog-list-post clearfix">
                    <div class="blog-list-details">
                        <h5 class="blog-list-title">${p.position}</h5>
                    </div>
                </div> <!-- /.blog-list-post -->
            </#list>
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
                    <#if documents?size == 0>
                        Нет справок на заказ
                    <#else>
                    <div class="slider-testimonials">
                        <table class="table">
                            <thead>
                            <tr>
                                <td><b>№</b></td>
                                <td><b>Наименование</b></td>
                                <td><b>Студент</b></td>
                                <td><b>Дата подачи</b></td>
                                <td><b>Статус</b></td>
                            </tr>
                            </thead>
                            <tbody>
                                <#list documents as d>
                                <tr>
                                    <td>${d.id}</td>
                                    <td>${d.type}</td>
                                    <td>${d.student.name} ${d.student.surname}, ${d.student.group.groupCode}</td>
                                <#--<td>Иванов Иван Иванович, 11-111, 3 курс, очное, контракт</td>-->
                                    <td>${d.created}</td>
                                    <td>
                                        <div class="input-group-btn">
                                            <select data-toggle="dropdown"
                                                    class="btn btn-default dropdown-toggle dev_status_doc"
                                                    dev_id_doc="${d.id}"><span class="caret"></span>
                                                <option value="1">Готова</option>
                                                <option selected value="2">Не готова</option>
                                            </select>
                                        </div>
                                    </td>
                                </tr>
                                </#list>
                            </tbody>
                        </table>
                    </#if>
                </div>
                </div>
            </div>
        </div>
    </div>
<script type="application/javascript">
    $(document).on('ready', function () {
        $('.dev_status_doc').on('change', function () {
            var id = $(this).attr('dev_id_doc');
            var status = $(this).val();
            $.ajax({
                url: 'cabinet?ready&id=' + id + "&status=" + status,
                type: 'POST',
                success: function (response) {
                    if (response === '200') {
                        console.log('Doc is ready');
                    }
                }
            })
        });
    });
</script>
</@body>