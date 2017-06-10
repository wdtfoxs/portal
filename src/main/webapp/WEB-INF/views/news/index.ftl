<#macro menu>
<li><a href="/">Домашная страница</a></li>
<li><a href="/event">Мероприятия</a></li>
<li class="active"><a href="/news">Новости</a></li>
    <@security.authorize access="isAuthenticated() and !hasRole('TEMPORARY_ACCESS')">
    <li><b><a href="/cabinet">Личный кабинет</a></b></li>
    </@security.authorize>
</#macro>
<#include "../templates/body.ftl"/>
<@body title="Новости">
<div class="container">
    <div class="page-title clearfix">
        <div class="row">
            <div class="col-md-12">
                <h6><a href="/">Домашная страница</a></h6>
                <h6><span class="page-active">Новости</span></h6>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <@security.authorize access="hasAnyRole('EMPLOYEE', 'ADMIN')">
        <br>
        <div class="row">
            <div class="col-xs-3 col-md-5"></div>
            <button class="btn btn-primary col-xs-6 col-md-2" style="white-space: normal" data-toggle="modal"
                    href="#newsModal">Добавить новость
            </button>
            <div class="col-xs-3 col-md-5"></div>
        </div>
        <div id="newsEditModal" class="modal fade">

        </div>
        <div id="newsModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <!-- Заголовок модального окна -->
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title">Добавить новость</h4>
                    </div>
                    <!-- Модальное окно новостей -->
                    <@form.form enctype ="multipart/form-data" method="post" id="form1" action="/news/new">
                        <div class="modal-body" align="center">
                            <div class="form-group">
                                <label for="example-text-input" class="col-2 col-form-label">Заголовок: </label>
                                <div class="col-10">
                                    <input name="title" class="form-control" minlength="8" type="text"
                                           required="required"
                                           id="example-text-input">
                                </div>
                            </div>
                            <label class="control-label">Загрузка изображения: </label>
                            <br><label id="error" style="display: none; color: red" class="control-label">Не является
                            изображением</label>
                            <input name="file" id="file" type="file" required="required" class="filestyle"
                                   data-buttonText="Выбрать изображение">
                            <div class="form-group">
                                <label for="comment">Описание (допустимы html теги): </label>
                                <textarea name="text" style="resize: vertical" minlength="20" required="required"
                                          maxlength="1000" class="form-control" rows="5" id="comment"></textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Добавить</button>
                        </div>
                    </@form.form>
                </div>
            </div>
        </div>
    </@security.authorize>
    <div class="row">
        <div id="news_list" class="col-md-12">
            <#list news as item>
                <div class="list-event-item">
                    <div class="box-content-inner clearfix">
                        <div class="list-event-thumb">
                            <a href="/news/${item.getId()}">
                                <img src="${item.getImage()}" alt="">
                            </a>
                        </div>
                        <div class="list-event-header">
                            <span class="event-date small-text"><i
                                    class="fa fa-calendar-o"></i>${item.getCreated()?string["dd MMMM yyyy"]} г.</span>
                            <@security.authorize access="hasAnyRole('EMPLOYEE', 'ADMIN')">
                                <div class="view-details"><a data-toggle="modal" href="#newsEditModal"
                                                             data-id="${item.getId()}"
                                                             class="lightBtn">Изменить</a></div>
                            </@security.authorize>
                            <@security.authorize access="hasRole('ADMIN')">
                                <div class="view-details"><a data-id="${item.getId()}"
                                                             class="lightBtnAdd">Удалить</a></div>
                            </@security.authorize>
                        </div>
                        <h5 class="event-title"><a href="/news/${item.getId()}">${item.getTitle()}</a></h5>
                        <#if (item.getText()?length <= 100)>
                            <p>${item.getText()}</p>
                        <#else>
                            <p>${item.getText()[0..100]}...</p>
                        </#if>
                    </div>
                </div>
            </#list>
        </div>
    </div>
    <#if !(news?size < 5)>

        <div class="row">
            <div class="col-md-12">
                <div class="load-more-btn">
                    <a id="more" data-page="1" data-size="5">Загрузить еще</a>
                </div>
            </div>
        </div>

    </#if>
</div>
    <#include "../jquery_templates/news_template.ftl">
    <@security.authorize access="hasAnyRole('EMPLOYEE', 'ADMIN')">
        <#include "../jquery_templates/news_modal_template.ftl">
    </@security.authorize>
<script type="application/javascript">
    $(document).ready(function () {

        <@security.authorize access="hasAnyRole('EMPLOYEE', 'ADMIN')">
            $('#newsModalID').template('news_edit_template');
            $('#form1').submit(function (e) {
                if ($('#example-text-input').val().trim().length < 8) {
                    alert("Убирай пробелы");
                    return false;
                }
                if($('#comment').val().trim().length<20){
                    alert("Убирай пробелы");
                    return false;
                }
                let file = $('#file');
                let fileType = file[0].files[0]['type'];
                let validImageTypes = ["image/gif", "image/jpeg", "image/png"];
                if ($.inArray(fileType, validImageTypes) < 0) {
                    e.preventDefault();
                    $('#error').show();
                }
            });
            $('#news_list').on('click', '.lightBtn', function () {
                let id = $(this).data('id');
                $.ajax({
                    url: '/news',
                    type: "GET",
                    data: {
                        id: id
                    },
                    success: function (result) {
                        $('#newsEditModal').html($.tmpl('news_edit_template', result));
                        $('#file1').filestyle({buttonText: "Выбрать изображение"});
                    }
                })
            });

            $('#newsEditModal').on('submit', '#form2', function (e) {
                $('#error1').hide();
                if ($('#example-text-input1').val().trim().length < 8) {
                    alert("Убирай пробелы");
                    return false;
                }
                if($('#comment1').val().trim().length<20){
                    alert("Убирай пробелы");
                    e.preventDefault();
                    return false;
                }
                let file = $('#file1');
                if (typeof file[0].files[0] !== 'undefined') {
                    let fileType = file[0].files[0]['type'];
                    let validImageTypes = ["image/gif", "image/jpeg", "image/png"];
                    if ($.inArray(fileType, validImageTypes) < 0) {
                        e.preventDefault();
                        $('#error1').show();
                    }
                }
            });
            $('.row').on('click', '.lightBtnAdd', function () {
                let id = $(this).data('id');
                if (confirm('Вы уверены, что хотите удалить данную новость?')) {
                    $.ajax({
                        url: '/news/delete/' + id,
                        type: "POST",
                        success: function (result) {
                            if (result === 'OK') {
                                window.location.href = "/news";
                            }
                        }
                    });
                }
            });
        </@security.authorize>
        $('#newsID').template('news_template');

        $('#more').on('click', function () {
            let $this = $(this);
            let $page = $(this).data('page');
            let $size = $(this).data('size');
            $.ajax({
                type: "get",
                url: "/news/more",
                data: {
                    size: $size,
                    page: $page + 1
                },
                dataType: 'json',
                success: function (data) {
                    if (data.length !== 0) {
                        $.tmpl('news_template', data).appendTo('#news_list');
                        if (data.length < $size) {
                            $this.hide();
                        }
                        $this.data('page', parseInt($page) + 1);
                    } else {
                        $this.hide();
                    }
                }
            });
        });
    });


</script>
</@body>