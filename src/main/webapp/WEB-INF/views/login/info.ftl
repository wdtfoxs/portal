<#macro menu>
<li><a  href="/">Домашная страница</a></li>
<li><a href="/event">Мероприятия</a></li>
<li><a href="/news">Новости</a></li>
</#macro>
<#include "../templates/body.ftl"/>
<@body title="Информация">
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="widget-item">
                <div class="main">
                    <h2>${message}</h2><br>
                </div>
            </div>
        </div>
    </div>
</div>
</@body>