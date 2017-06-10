<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign form=JspTaglibs["http://www.springframework.org/tags/form"]/>
<#assign spring=JspTaglibs["http://www.springframework.org/tags"]/>
<#assign url = "${springMacroRequestContext.getRequestUri()}">
<#macro body title="Главная" styles=[] scripts=[]>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/resources/images/favicon.ico" rel="icon" type="image/x-icon"/>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>

    <link href='//fonts.googleapis.com/css?family=Viga' rel='stylesheet' type='text/css'>
    <link href="/resources/css/css.css" rel="stylesheet" type="text/css">
    <!-- CSS Bootstrap & Custom -->
    <link href="/resources/css/bootstrap.css" rel="stylesheet" type="text/css">
    <link href="/resources/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="/resources/css/animate.css" rel="stylesheet" type="text/css">

    <link href="/resources/css/style.css" rel="stylesheet" type="text/css">
    <link href="/resources/css/schedule_style.css" rel="stylesheet" type="text/css">
    <link href="/resources/css/color-scheme.css" rel="stylesheet" type="text/css">

    <script src="/resources/js/jquery-1.10.2.min.js"></script>
    <script src="/resources/js/jquery-migrate-1.2.1.min.js"></script>
    <script src="/resources/js/modernizr.js"></script>
    <script src="/resources/js/jquery.tmpl.js"></script>
    <script src="/resources/js/moment-with-locales.js"></script>
    <script type="application/javascript" src="/resources/js/include_csrf.js"></script>
    <script type="application/javascript" src="/resources/js/purify.min.js"></script>
    <script type="application/javascript" src="/resources/js/bootstrap-filestyle.min.js"></script>
    <script type="application/javascript" src="/resources/js/bootstrap-notify.min.js"></script>
    <script type="application/javascript">
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);
        function hideURLbar() {
            window.scrollTo(0, 1);
        } </script>
    <#list styles as style>
        <link href="${style}" rel="stylesheet" type="text/css">
    </#list>
    <#list scripts as script>
        <script type="application/javascript" src="${script}"></script>
    </#list>
    <title>${title}</title>
</head>
<body>

    <#include "../header.ftl"/>
<div class="wrapper">
    <div class="site-content">
        <#nested>
    </div>
    <#include "../footer.ftl"/>
</div>
<script src="/resources/js/bootstrap.min.js"></script>
<script src="/resources/js/plugins.js"></script>
<script src="/resources/js/custom.js"></script>
    <@security.authorize access="hasAnyRole('PROFESSOR', 'STUDENT') or isAnonymous()">
    <script type="application/javascript">
        $(document).ready(function () {
            var newsCount = 0;
            var eventsCount = 0;
            $.ajax({
                url: '/count/news',
                type: 'get',
                dataType: 'json',
                success: function (count) {
                    newsCount = count;
                    setInterval(countNews, 10000);
                }
            });
            $.ajax({
                url: '/count/event',
                type: 'get',
                dataType: 'json',
                success: function (count) {
                    eventsCount = count;
                    setInterval(countEvents, 10000);
                }
            });
            let $loading = $('#spinner').hide();
            $(document)
                    .ajaxStart(function () {
                        $loading.show();
                    })
                    .ajaxStop(function () {
                        $loading.hide();
                    });
            function countNews() {
                $.ajax({
                    url: '/count/news',
                    type: 'get',
                    dataType: 'json',
                    success: function (count) {
                        if (count > newsCount) {
                            $.ajax({
                                url: '/count/news/get',
                                type: 'get',
                                dataType: 'json',
                                success: function (news) {
                                    console.log(news);
                                    $.notify({
                                        title: 'Новая новость: ',
                                        message: news.title,
                                        url: '/news/' + news.id,
                                        target: '_blank'
                                    }, {
                                        type: 'info',
                                        mouse_over: 'pause',
                                        placement: {
                                            from: "top",
                                            align: "right"
                                        },
                                    });
                                }
                            });
                        }
                        newsCount = count;
                    }
                });
            }

            function countEvents() {
                $.ajax({
                    url: '/count/event',
                    type: 'get',
                    dataType: 'json',
                    success: function (count) {
                        if (count > eventsCount) {
                            $.ajax({
                                url: '/count/event/get',
                                type: 'get',
                                dataType: 'json',
                                success: function (event) {
                                    $.notify({
                                        title: 'Новое мероприятие: ',
                                        message: event.title,
                                        url: '/event/' + event.id,
                                        target: '_blank'
                                    }, {
                                        type: 'info',
                                        mouse_over: 'pause',
                                        placement: {
                                            from: "top",
                                            align: "right"
                                        },
                                    });
                                }
                            });
                        }
                        eventsCount = count;
                    }
                });
            }
        });


    </script>
    </@security.authorize>
</body>
</html>
</#macro>
<#macro spinner>
<div class="sk-circle" id="spinner">
    <div class="sk-circle1 sk-child"></div>
    <div class="sk-circle2 sk-child"></div>
    <div class="sk-circle3 sk-child"></div>
    <div class="sk-circle4 sk-child"></div>
    <div class="sk-circle5 sk-child"></div>
    <div class="sk-circle6 sk-child"></div>
    <div class="sk-circle7 sk-child"></div>
    <div class="sk-circle8 sk-child"></div>
    <div class="sk-circle9 sk-child"></div>
    <div class="sk-circle10 sk-child"></div>
    <div class="sk-circle11 sk-child"></div>
    <div class="sk-circle12 sk-child"></div>
</div>
</#macro>
