<#include "../templates/body.ftl"/>
<#macro menu>
<li><a  href="/">Домашная страница</a></li>
<li><a href="/event">Мероприятия</a></li>
<li><a href="/news">Новости</a></li>
    <@security.authorize access="isAuthenticated() and !hasRole('TEMPORARY_ACCESS')">
    <li><b><a href="/cabinet">Личный кабинет</a></b></li>
    </@security.authorize>
</#macro>

<@body title="Доступ запрещен" styles=["/resources/css/errors.css"]>
<div class="container">
    <div class="row" style="position: relative">
        <img src="/resources/image/403.png" class="error-img">
        <div class="error-block">
            <h2 class="error-h2" align="center">Доступ запрещен</h2>
        </div>
    </div>
</div>
</@body>