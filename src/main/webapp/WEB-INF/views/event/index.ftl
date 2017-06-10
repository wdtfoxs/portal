<#macro menu>
<li><a  href="/">Домашная страница</a></li>
<li class="active"><a href="/event">Мероприятия</a></li>
<li><a href="/news">Новости</a></li>
    <@security.authorize access="isAuthenticated() and !hasRole('TEMPORARY_ACCESS')">
    <li><b><a href="/cabinet">Личный кабинет</a></b></li>
    </@security.authorize>
</#macro>
<#include "../templates/body.ftl"/>
<@body title="Мероприятия">
<div class="container">
    <div class="page-title clearfix">
        <div class="row">
            <div class="col-md-12">
                <h6><a href="/">Домашная страница</a></h6>
                <h6><span class="page-active">Мероприятия</span></h6>
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
                    href="#eventModal">Добавить мероприятие
            </button>
            <div class="col-xs-3 col-md-5"></div>
        </div>
        <div id="eventEditModal" class="modal fade">

        </div>
        <div id="eventModal" class="modal fade">
            <div class="modal-dialog">
                <div class="modal-content">
                    <!-- Заголовок модального окна -->
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title">Добавить мероприятие</h4>
                    </div>
                    <!-- Модальное окно новостей -->
                    <@form.form enctype ="multipart/form-data" method="post" id="form1" action="/event/new">
                        <div class="modal-body" align="center">
                            <div class="form-group">
                                <label for="example-text-input" class="col-2 col-form-label">Заголовок: </label>
                                <div class="col-10">
                                    <input name="title" class="form-control" minlength="8" type="text"
                                           required="required"
                                           id="example-text-input">
                                </div>
                            </div>
                            <br><label id="create_error" style="display: none; color: red" class="control-label">Дата
                            проведения не может быть раньше сегодня</label>
                            <div class="form-group">
                                <label for="example-date-input" class="col-2 col-form-label">Дата проведения</label>
                                <div class="col-10">
                                    <input required="required" name="date" class="form-control" type="date"
                                           id="create_date">
                                </div>
                            </div>
                            <br><label id="create_time_error" style="display: none; color: red" class="control-label">Невалидное
                            время проведения</label>
                            <div class="form-group">
                                <label for="example-time-input" class="col-2 col-form-label">С</label>
                                <div class="col-10">
                                    <input required="required" name="from" class="form-control" type="time"
                                           id="create_from">
                                </div>
                            </div>

                            <div class="form-group">
                                <label for="example-time-input" class="col-2 col-form-label">До</label>
                                <div class="col-10">
                                    <input required="required" name="to" class="form-control" type="time"
                                           id="create_to">
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
        <div id="event_list" class="col-md-12">
            <#list events as event>
                <div id="event_${event.getId()}" class="list-event-item">
                    <div class="box-content-inner clearfix">
                        <div class="list-event-thumb">
                            <a href="/event/${event.getId()}">
                                <img src="${event.getImage()}" alt="">
                            </a>
                        </div>
                        <div class="list-event-header">
                            <span class="event-date small-text"><i
                                    class="fa fa-calendar-o"></i>${event.getEventDate()?string["dd MMMM yyyy"]} г.</span>
                            <@security.authorize access="hasAnyRole('EMPLOYEE', 'ADMIN')">
                                <div class="view-details"><a data-toggle="modal" href="#eventEditModal"
                                                             data-id="${event.getId()}"
                                                             class="lightBtn">Изменить</a></div>
                            </@security.authorize>
                            <@security.authorize access="hasRole('ADMIN')">
                                <div class="view-details"><a data-id="${event.getId()}"
                                                             class="lightBtnAdd">Удалить</a></div>
                            </@security.authorize>
                        </div>
                        <h5 class="event-title"><a href="/event/${event.getId()}">${event.getTitle()}</a></h5>
                        <p> с ${event.getTime().getFrom()?string["HH:mm"]}
                            до ${event.getTime().getTo()?string["HH:mm"]}</p>
                        <#if (event.getText()?length <= 100)>
                            <p>${event.getText()}</p>
                        <#else>
                            <p>${event.getText()[0..100]}...</p>
                        </#if>
                    </div>
                </div>
            </#list>
        </div>
    </div>
    <#if !(events?size < 5)>

        <div class="row">
            <div class="col-md-12">
                <div class="load-more-btn">
                    <a id="more" data-page="1" data-size="5">Загрузить еще</a>
                </div>
            </div>
        </div>

    </#if>
</div>
    <#include "../jquery_templates/event_template.ftl">
    <@security.authorize access="hasAnyRole('EMPLOYEE', 'ADMIN')">
        <#include "../jquery_templates/event_modal_template.ftl">
    </@security.authorize>
<script type="application/javascript">
        <@security.authorize access="hasAnyRole('EMPLOYEE', 'ADMIN')">
        $('#eventModalID').template('event_edit_template');
        $('#event_list').on('click', '.lightBtn', function () {
            let id = $(this).data('id');
            $.ajax({
                url: '/event',
                type: "GET",
                data: {
                    id: id
                },
                success: function (result) {
                    $('#eventEditModal').html($.tmpl('event_edit_template', result));
                    $('#file1').filestyle({buttonText: "Выбрать изображение"});
                }
            })
        });
        $('#eventEditModal').on('submit', "#form2", function (e) {
            $('#error1').hide();
            $('#create_error1').hide();
            $('#create_time_error1').hide();
            let beginningTime = moment($('#create_from1').val(), 'HH:mm');
            let endTime = moment($('#create_to1').val(), 'HH:mm');
            if ($('#tex1').val().trim().length < 8) {
                alert("Убирай пробелы");
                return false;
            }
            if($('#com1').val().trim().length<20){
                alert("Убирай пробелы");
                return false;
            }
            if (moment(moment.unix(new Date() / 1000).format('YYYY-MM-DD')).isAfter($('#create_date1').val())) {
                $('#create_error1').show();
                e.preventDefault();
            }
            if (!beginningTime.isBefore(endTime)) {
                $('#create_time_error1').show();
                e.preventDefault();
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
        $create_form = $('#form1');
        $create_form.submit(function (e) {
            $('#error').hide();
            $('#create_error').hide();
            $('#create_time_error').hide();
            if ($('#example-text-input').val().trim().length < 8) {
                alert("Убирай пробелы");
                return false;
            }
            if($('#comment').val().trim().length<20){
                alert("Убирай пробелы");
                e.preventDefault();
                return false;
            }
            let beginningTime = moment($('#create_from').val(), 'HH:mm');
            let endTime = moment($('#create_to').val(), 'HH:mm');

            if (moment(moment.unix(new Date() / 1000).format('YYYY-MM-DD')).isAfter($('#create_date').val())) {
                $('#create_error').show();
                e.preventDefault();
            }
            if (!beginningTime.isBefore(endTime)) {
                $('#create_time_error').show();
                e.preventDefault();
            }
            let file = $('#file');
            let fileType = file[0].files[0]['type'];
            let validImageTypes = ["image/gif", "image/jpeg", "image/png"];
            if ($.inArray(fileType, validImageTypes) < 0) {
                e.preventDefault();
                $('#error').show();
            }
        });
        </@security.authorize>

    $('.row').on('click', '.lightBtnAdd', function () {
        let id = $(this).data('id');
        if (confirm('Вы уверены, что хотите удалить данное мероприятие?')) {
            $.ajax({
                url: '/event/delete/' + id,
                type: "POST",
                success: function (result) {
                    if (result === 'OK') {
                        window.location.href = "/event";
                    }
                }
            });
        }
    });
    $('#eventID').template('event_template');

    $('#more').on('click', function () {
        let $this = $(this);
        let $page = $(this).data('page');
        let $size = $(this).data('size');
        $.ajax({
            type: "get",
            url: "/event/more",
            data: {
                size: $size,
                page: $page + 1
            },
            dataType: 'json',
            success: function (data) {
                if (data.length !== 0) {
                    $.tmpl('event_template', data).appendTo('#event_list');
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
</script>
</@body>