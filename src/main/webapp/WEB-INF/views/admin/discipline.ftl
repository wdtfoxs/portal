<#include "../templates/body.ftl"/>
<#macro menu>
<li><a href="/">Домашняя страница</a></li>
<li ><a href="/admin">Личный кабинет</a></li>
<li><a href="/admin/users">Пользователи</a></li>
<li class="active"><a href="/admin/discipline">Предметы</a></li>
<li><a href="/admin/createBuilding">Создание здания</a></li>
<li><a href="/admin/createAuditorium">Создание аудитории</a></li>
</#macro>
<@body title="Предметы">
<div class="container">
    <div class="text-center">
        <div id="week_schedule">
            <div class="widget-main col-lg-12">
                <div class="widget-main-title">
                    <h4 class="widget-title">Предметы</h4>
                </div>
                <div class="widget-inner">
                    <div class="slider-testimonials">
            <a href="/admin/create_discipline" class="btn btn-primary" role="button">Создать предмет</a>
            <a href="/admin/all_disciplines" class="btn btn-primary" role="button">Список предметов</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</@body>