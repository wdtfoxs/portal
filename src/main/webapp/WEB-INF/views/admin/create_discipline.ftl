<#include "../templates/body.ftl"/>
<#macro menu>
<li><a href="/">Домашняя страница</a></li>
<li><a href="/admin">Личный кабинет</a></li>
<li><a href="/admin/users">Пользователи</a></li>
<li class="active"><a href="/admin/discipline">Предметы</a></li>
<li><a href="/admin/createBuilding">Создание здание</a></li>
<li><a href="/admin/createAuditorium">Создание аудитории</a></li>
</#macro>
<@body title="Создание предмета">
<div class="container">
    <div class="text-center">
        <div id="week_schedule">
            <div class="widget-main col-lg-12">
                <div class="widget-main-title">
                    <h4 class="widget-title">Создание предмета</h4>
                </div>
                <#if RequestParameters.error??>
                    <h3 style="color: red">Уже существует такой предмет</h3>
                </#if>
                <#if RequestParameters.success??>
                    <h3 style="color: blue">Успешно создан</h3>
                </#if>
                <div class="widget-inner">
                    <div class="slider-testimonials">
                        <@form.form method="post" action="/admin/create_discipline">
                            <label for="discipline_name">Название предмета</label>
                            <input required="required" name="name" type="text" class="form-control" id="discipline_name">
                            <label  for="semester_number">Выберите семестр</label>
                            <select name="semester" required="required" class="form-control" id="semester_number">
                                <option disabled selected value="">Семестр</option>
                                <option value="1">Первый</option>
                                <option value="2">Второй</option>
                            </select>
                            <div class="checkbox">
                                <label><input name="physical" type="checkbox" value="physical">Это физкультура</label>
                            </div>
                            <button class="btn btn-primary">Cоздать</button>
                        </@form.form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@body>