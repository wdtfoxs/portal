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

<@body title="Создание сотрудника деканата">
<div class="container">
    <div class="text-center">
        <div id="week_schedule">
            <div class="widget-main col-lg-12">
                <div class="widget-main-title">
                    <h4 class="widget-title">Создание сотрудника деканата</h4>
                </div>
                <div class="widget-inner">
                    <div class="slider-testimonials">
                        <label for="username">Логин:</label>
                        <input type="text" class="form-control" id="username">
                        <label for="email">Эл.почта:</label>
                        <input type="text" class="form-control" id="email">
                        <label for="name">Имя:</label>
                        <input type="text" class="form-control" id="name">
                        <label for="surname">Фамилия:</label>
                        <input type="text" class="form-control" id="surname">
                        <label for="phone_number">Телефое:</label>
                        <input type="text" class="form-control" id="phone_number">
                        <label for="birthday">Дата рождения:</label>
                        <input type="date" class="form-control" id="birthday">
                        <label>Пол:</label>
                        <label class="radio-inline"><input type="radio" name="gender">Мужской</label>
                        <label class="radio-inline"><input type="radio" name="gender">Женский</label>
                        <br>
                        <label for="pass">Пароль:</label>
                        <input type="password" class="form-control" id="pass">
                        <button class="btn btn-primary">Cоздать</button>
                    </div>
                </div> <!-- /.widget-inner -->
            </div>
        </div>
    </div>
</div>

</@body>