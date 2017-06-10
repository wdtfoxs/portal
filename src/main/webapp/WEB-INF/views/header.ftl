<!-- This one in here is responsive menu for tablet and mobiles -->
<div class="responsive-navigation visible-sm visible-xs">
    <a href="#" class="menu-toggle-btn">
        <i class="fa fa-bars"></i>
    </a>
    <div class="responsive_menu">
        <ul class="main_menu">
            <li></li>
        <@menu></@menu>

        </ul>
        <ul class="social_icons">
            <li><a href="https://vk.com/itis_kpfu" target="_blank"><i class="fa fa-vk"></i></a></li>
            <li><a href="https://www.instagram.com/itis_kfu/" target="_blank"><i class="fa fa-instagram"></i></a></li>
            <li>
                <a href="https://ru.linkedin.com/in/%D0%B2%D1%8B%D1%81%D1%88%D0%B0%D1%8F-%D1%88%D0%BA%D0%BE%D0%BB%D0%B0-%D0%B8%D1%82%D0%B8%D1%81-information-technologies-institute-35a17679"
                   target="_blank"><i class="fa fa-linkedin"></i></a></li>
        </ul>
    </div>
</div>
<@security.authorize access="isAnonymous()">
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true"
     style="display: none;">
    <div class="modal-dialog">
        <div class="loginmodal-container">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
            <h1>Вход в систему</h1><br>
            <@form.form method="post" action="/login_processing">
                <input type="text" name="email" placeholder="E-mail / имя пользователя">
                <input type="password" name="password" placeholder="Пароль">
                <div class="checkbox">
                    <label><input name="remember-me" type="checkbox" value="">Запомнить меня</label>
                </div>
                <input type="submit" name="login" class="login loginmodal-submit" value="Войти">
            </@form.form>
            <div class="login-help">
                <a href="/login?reset_password">Забыли пароль?</a>
            </div>
        </div>
    </div>
</div>
</@security.authorize>
<header class="site-header">
    <div class="container">
        <div class="row">
            <div class="col-md-8 header-left">
                <a href="/" title="Universe" rel="home">
                    <img src="/resources/image/logo-1.png" alt="ITIS">
                    <img class="logo-adaptive" src="/resources/image/logo-2.png" alt="ITIS">
            </div>
            <div class="col-md-4 header-right">
                <ul class="small-links">
                <@security.authorize access="isAnonymous()">
                    <li><b><a href="#" data-toggle="modal" data-target="#login-modal">Войти в систему</a></b></li>
                </@security.authorize>
                <@security.authorize access="isAuthenticated() and !hasRole('TEMPORARY_ACCESS')">
                    <li><b><a href="/cabinet"><@security.authentication property="principal.user.name"/>   <@security.authentication property="principal.user.surname"/></a></b></li>
                    <@form.form action="/logout" method="post">
                        <input type="submit" value="Выйти">
                    </@form.form>
                </@security.authorize>
                </ul>
                <div class="search-form">
                    <p><i class="fa fa-phone"></i>8 (843) 221-34-33</p>
                    <p><i class="fa fa-envelope"></i> <a class="search-form" href="mailto:itis@kpfu.ru">itis@kpfu.ru</a></p>
                </div>
            </div>
        </div>
    </div>
    <div class="nav-bar-main" role="navigation">
        <div class="container">
            <nav class="main-navigation clearfix visible-md visible-lg" role="navigation">
                <ul class="main-menu sf-menu sf-js-enabled sf-arrows">
                <@menu></@menu>
                </ul>
                <ul class="social-icons pull-right">
                    <li><a href="https://vk.com/itis_kpfu" data-toggle="tooltip" title="" target="_blank"
                           data-original-title="VK"><i class="fa fa-vk"></i></a></li>
                    <li><a href="https://www.instagram.com/itis_kfu/" data-toggle="tooltip" title="" target="_blank"
                           data-original-title="Instagram"><i class="fa fa-instagram"></i></a></li>
                    <li>
                        <a href="https://ru.linkedin.com/in/%D0%B2%D1%8B%D1%81%D1%88%D0%B0%D1%8F-%D1%88%D0%BA%D0%BE%D0%BB%D0%B0-%D0%B8%D1%82%D0%B8%D1%81-information-technologies-institute-35a17679"
                           data-toggle="tooltip" title="" target="_blank"
                           data-original-title="Linkedin"><i class="fa fa-linkedin"></i></a></li>
                </ul>
            </nav>
        </div>
    </div>
</header>