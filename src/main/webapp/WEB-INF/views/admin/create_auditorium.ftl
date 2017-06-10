<#include "../templates/body.ftl"/>
<#macro menu>
<li><a href="/">Домашняя страница</a></li>
<li><a href="/admin">Личный кабинет</a></li>
<li><a href="/admin/users">Пользователи</a></li>
<li><a href="/admin/discipline">Предметы</a></li>
<li><a href="/admin/createBuilding">Создание здания</a></li>
<li class="active"><a href="/admin/createAuditorium">Создание аудитории</a></li>
</#macro>
<@body title="Создание аудитории">
<div class="container">
    <div class="text-center">
        <div id="week_schedule">
            <div class="widget-main col-lg-12">
                <div class="widget-main-title">
                    <h4 class="widget-title">Создание аудитории</h4>
                </div>
                <#if RequestParameters.error??>
                    <h3 style="color: red">Уже существует такая аудитория</h3>
                </#if>
                <#if RequestParameters.success??>
                    <h3 style="color: blue">Успешно создана</h3>
                </#if>
                <div class="widget-inner">
                    <div class="slider-testimonials">
                        <@form.form method="post" action="/admin/createAuditorium">
                            <label for="discipline_name">Номер аудитории</label>
                            <input required="required" name="number" type="number" class="form-control"
                                   id="discipline_name">
                            <label for="semester_number">Выберите здание</label>
                            <select name="building" required="required" class="form-control" id="semester_number">
                                <option disabled selected value="">Здание</option>
                                <#list buildings as building>
                                    <option value="${building.id}">${building.name}</option>
                                </#list>
                            </select>
                            <br>
                            <button class="btn btn-primary">Создать</button>
                        </@form.form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@body>