<#include "../templates/body.ftl"/>
<#macro menu>
<li><a href="/">Домашняя страница</a></li>
<li><a href="/admin">Личный кабинет</a></li>
<li class="active"><a href="/admin/users">Пользователи</a></li>
<li><a href="/admin/discipline">Предмет</a></li>
<li><a href="/admin/createBuilding">Создание здание</a></li>
<li><a href="/admin/createAuditorium">Создание аудитории</a></li>
</#macro>
<@body title="Создать должность">
<div class="container">
    <div class="text-center">
        <div id="week_schedule">
            <div class="widget-main col-lg-12">
                <div class="widget-main-title">
                    <h4 class="widget-title">Создание должности</h4>
                </div>
                <#if RequestParameters.error??>
                    <h3 style="color: red">Уже существует такая должность</h3>
                </#if>
                <#if RequestParameters.success??>
                    <h3 style="color: blue">Успешно создана</h3>
                </#if>
                <div class="widget-inner">
                    <div class="slider-testimonials">
                        <@form.form method="post" action="/admin/createPosition">
                            <label for="discipline_name">Название должности</label>
                            <input type="text" required="required" name="position" class="form-control" id="position">
                          <br>
                            <button class="btn btn-primary">Создать</button>
                        </@form.form>
                    </div>
                </div>
                <!-- /.widget-inner -->
            </div>
        </div>
    </div>
</div>
</@body>