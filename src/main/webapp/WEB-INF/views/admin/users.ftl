<#include "../templates/body.ftl"/>

<#-- @ftlvariable name="exam" type="com.code405.entity.model.Exam" -->
<#macro menu>
<li><a href="/">Домашняя страница</a></li>
<li ><a href="/admin">Личный кабинет</a></li>
<li class="active"><a href="/admin/users">Пользователи</a></li>
<li ><a href="/admin/discipline">Предметы</a></li>
<li><a href="/admin/createBuilding">Создание здание</a></li>
<li><a href="/admin/createAuditorium">Создание аудитории</a></li>
</#macro>

<@body title="Пользователи">
<div class="container">
    <div class="text-center">
        <div id="week_schedule">
            <div class="widget-main col-lg-12">
                <div class="widget-main-title">
                    <h4 class="widget-title">Окно пользователей</h4>
                </div>
                <div class="widget-inner">
                    <div class="slider-testimonials">
                        <a href="/admin/users/create_user" class="btn btn-primary" role="button">Создать пользователя</a>
                        <a href="/admin/createPosition" class="btn btn-primary" role="button">Создать должность</a>
                        <a href="/admin/users/all_users" class="btn btn-primary" role="button">Список пользователей</a>
                    </div>
                </div> <!-- /.widget-inner -->
            </div>
        </div>
    </div>
</div>

</@body>