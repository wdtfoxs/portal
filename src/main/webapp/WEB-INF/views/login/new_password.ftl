<#macro menu>
<li><a  href="/">Домашная страница</a></li>
<li><a href="/event">Мероприятия</a></li>
<li><a href="/news">Новости</a></li>
</#macro>
<#include "../templates/body.ftl"/>
<@body title="Новый пароль">
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="widget-item">
                <div class="main">
                    <h1>Новый пароль</h1><br>
                    <#if message??>
                        <p style="margin: 0 auto; display: table; color: red">${message}</p>
                    </#if>
                    <@form.form method="post" action="/login/new_password" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label">Пароль: </label>
                            <div class="col-sm-10">
                                <input type="password" required="required" name="password" class="form-control" id="inputEmail3">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label">Подтверждение: </label>
                            <div class="col-sm-10">
                                <input type="password" required="required" name="matchingPassword" class="form-control" id="inputEmail3"
                                >
                            </div>
                        </div>
                        <div style="    margin: 0 auto;display: table;" class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-default">Сбросить пароль</button>
                            </div>
                        </div>
                    </@form.form>
                </div>
            </div>
        </div>
    </div>
</div>
</@body>