<#include "../templates/body.ftl"/>

<#-- @ftlvariable name="exam" type="com.code405.entity.model.Exam" -->
<#macro menu>
<li><a href="/">Домашняя страница</a></li>
<li><a href="/admin">Личный кабинет</a></li>
<li><a href="/admin/users">Пользователи</a></li>
<li class="active"><a href="/admin/discipline">Предметы</a></li>
<li><a href="/admin/createBuilding">Создание здания</a></li>
<li><a href="/admin/createAuditorium">Создание аудитории</a></li>
</#macro>

<@body title="Список предметов">
<div class="container">
    <div class="text-center">
        <div id="week_schedule">
            <div class="widget-main col-lg-12">
                <div class="widget-main-title">
                    <h4 class="widget-title">Список предметов</h4>
                </div>
                <div class="widget-inner">

                </div> <!-- /.widget-inner -->
            </div>
        </div>
    </div>
</div>

</@body>