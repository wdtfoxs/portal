<#macro menu>
<li><a  href="/">Домашная страница</a></li>
<li><a href="/event">Мероприятия</a></li>
<li><a href="/news">Новости</a></li>
</#macro>
<#include "../templates/body.ftl"/>
<@body title="Сброс пароля">
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="widget-item">
                <div class="main">
                    <h1 style="text-align: center">Сброс пароля</h1><br>
                    <#if message??>
                        <p style="margin: 0 auto; display: table; color: red">${message}</p>
                    </#if>
                    <@form.form method="post" action="/login?reset_password" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label">Email: </label>
                            <div class="col-sm-10">
                                <input required="required" type="email" name="email" class="form-control" id="inputEmail3"
                                       placeholder="Email для сброса пароля">
                            </div>
                        </div>
                        <div style="margin: 5% 10%;" class="form-group">

                            <button type="submit" class="login_button">Сохранить
                            </button>
                        </div>
                    </@form.form>
                </div>
            </div>
        </div>
    </div>
</div>
</@body>