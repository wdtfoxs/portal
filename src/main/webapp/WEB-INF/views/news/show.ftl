<#macro menu>
<li><a href="/">Домашная страница</a></li>
<li><a href="/event">Мероприятия</a></li>
<li class="active"><a href="/news">Новости</a></li>
    <@security.authorize access="isAuthenticated() and !hasRole('TEMPORARY_ACCESS')">
    <li><b><a href="/cabinet">Личный кабинет</a></b></li>
    </@security.authorize>
</#macro>
<#include "../templates/body.ftl"/>
<@body title="${news.getTitle()}">
<div class="container">
    <div class="page-title clearfix">
        <div class="row">
            <div class="col-md-12">
                <h6><a href="/">Домашняя страница</a></h6>
                <h6><a href="/news">Новости</a></h6>
                <h6><span class="page-active">${news.getTitle()}</span></h6>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="event-container clearfix">
                <div class="left-event-content">
                    <img width="225px" height="240px" id="img"
                         src="/resources/image/uploading.gif"
                         data-img="${news.getImage()}"
                         alt="${news.getTitle()}">
                </div> <!-- /.left-event-content -->
                <div class="right-event-content">
                    <h2 class="event-title">${news.getTitle()}</h2>
                    <span class="event-time">${news.getCreated()?string["dd MMMM yyyy HH:mm"]}</span>
                    <p>  ${news.getText()}</p>
                </div> <!-- /.right-event-content -->
            </div> <!-- /.event-container -->
        </div>
    </div>
</div>
<script type="application/javascript">
    $(document).ready(function () {
        let img = $('#img');
        let img_src = img.data('img');
        if (urlExists(img_src)) {
            img.attr('src', img_src);
        } else {
            let i = 0;
            let interval = setInterval(function () {
                if (urlExists(img_src)) {
                    img.attr('src', img_src);
                    clearInterval(interval);
                }
            }, 800);
            i++;
            if (i > 30) {
                clearInterval(interval);
            }
        }

        function urlExists(url) {
            let http = new XMLHttpRequest();
            http.open('HEAD', url, false);
            http.send();
            return http.status !== 404;
        }
    });
</script>
</@body>