<#include "../templates/body.ftl"/>
<#macro menu>
<li><a href="/">Домашняя страница</a></li>
<li><a href="/admin">Личный кабинет</a></li>
<li><a href="/admin/users">Пользователи</a></li>
<li><a href="/admin/discipline">Предметы</a></li>
<li class="active"><a href="/admin/createBuilding">Создание здание</a></li>
<li><a href="/admin/createAuditorium">Создание аудитории</a></li>
</#macro>
<@body title="Создание здания">
<div class="container">
    <div class="text-center">
        <div id="week_schedule">
            <div class="widget-main col-lg-12">
                <div class="widget-main-title">
                    <h4 class="widget-title">Создание здания</h4>
                </div>
                <#if RequestParameters.error??>
                    <h3 style="color: red">Уже существует такое здание</h3>
                </#if>
                <#if RequestParameters.success??>
                    <h3 style="color: blue">Успешно создана</h3>
                </#if>
                <div class="widget-inner">
                    <div class="slider-testimonials">
                        <@form.form method="post" action="/admin/createBuilding">
                            <label for="discipline_name">Название здания</label>
                            <input required="required" name="name" type="text" class="form-control"
                                   id="discipline_name">
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