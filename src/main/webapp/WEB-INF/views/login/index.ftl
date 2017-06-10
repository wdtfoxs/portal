<#include "../templates/body.ftl"/>
<#macro menu>
<li><a href="/">Домашная страница</a></li>
<li><a href="/event">Мероприятия</a></li>
<li><a href="/news">Новости</a></li>
</#macro>
<@body title="Вход в систему">
<!-- Вход -->
<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="widget-item">
                <div class="main">

                    <h1 style="text-align: center">Вход в систему</h1><br>
                    <#if RequestParameters.error??>
                        <p style="margin: 2% auto; display: table; color: red">Неверные данные</p>
                    </#if>
                    <@form.form method="post" action="/login_processing" class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-2 control-label">Логин</label>
                            <div class="col-sm-10">
                                <input required="required" type="text" name="email" class="form-control"
                                       id="inputEmail3"
                                       placeholder="Логин или Email">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-2 control-label">Пароль</label>
                            <div class="col-sm-10">
                                <input required="required" style="height: 34px" type="password" name="password"
                                       class="form-control"
                                       id="inputPassword3" placeholder="Пароль">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="remember-me" class="col-sm-2 control-label" style="padding-top: 0">Запомнить</label>
                            <div class="col-sm-10">
                                <input id="remember-me" name="remember-me" type="checkbox" value="">
                            </div>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="login_button">Войти
                            </button>
                        </div>
                        <div class="login-help">
                            <a href="/login?reset_password">Забыли пароль?</a>
                        </div>
                    </@form.form>

                </div>


            </div>
        </div>
    </div>
</div>

</@body>