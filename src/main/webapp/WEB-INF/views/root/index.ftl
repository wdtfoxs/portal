<#include "../templates/body.ftl"/>
<#macro menu>
<li class="active"><a href="/">Домашная страница</a></li>
<li><a href="/event">Мероприятия</a></li>
<li><a href="/news">Новости</a></li>
    <@security.authorize access="isAuthenticated() and !hasRole('TEMPORARY_ACCESS')">
    <li><b><a href="/cabinet">Личный кабинет</a></b></li>
    </@security.authorize>
</#macro>
<@body title="Главная страница">
    <#include "../carousel.ftl"/>
<#if start??>
    <script>
        $.notify({
            title: 'Аккаунты: \n',
            message: "Сотрудник деканата - sotrudnik:1\nПреподаватель: vlada@gmail.com:1\nСтудент: birthright5050@gmail.com:1",
        }, {
            type: 'info',
            mouse_over: 'pause',
            delay: 0,
            placement: {
                from: "bottom",
                align: "left"
            },
        });
    </script>
</#if>
<div class="container">
    <div class="row">
        <!-- Here begin Main Content -->
        <div class="col-md-8">
            <div class="row">
                <div class="col-md-12">
                    <div class="widget-item">
                        <h2 class="welcome-text">Высшая школа информационных технологий и информационных систем</h2>

                        <p><strong>Высшая школа информационных технологий и информационных систем (ИТИС) - инновационный
                            ИТ-факультет КФУ, который был основан в 2011 году совместными усилиями Министерства
                            информатизации и связи РТ, Казанского федерального университета, мировых брендов IBM,
                            Microsoft, HP, Oracle, представителями крупнейших IT-компаний региона.</strong><br>
                            <br>ИТИС – это подготовка высококвалифицированных кадров, повышение квалификации и
                            профессиональная переподготовка сотрудников предприятий в области информационных технологий,
                            осуществление прикладных исследований для ИТ-индустрии.<br>
                            <br>Особенностью Высшей школы ИТИС является промышленно-прикладная направленность
                            исследований и обучения. Со второго курса студенты проходят практику в ИТ-компаниях,
                            работают над исследованиями и разработками в лабораториях, созданных ИТ-бизнесом и Высшей
                            школой ИТИС. В стенах Института сегодня функционирует более 20 подобных центров. Среди них
                            Fujitsu Lab, Cisco Innovation Center, IOS Lab, Samsung Android Lab, Digital Media Lab, Flat
                            Stack Lab, SmartHead Lab, BARS Group Lab и другие.</p>
                    </div>
                    <!-- /.widget-item -->
                </div>
                <!-- /.col-md-12 -->
            </div>
            <!-- /.row -->

            <div class="row">

                <!-- Show Latest Blog News -->
                <div class="col-md-6">
                    <div class="widget-main">
                        <div class="widget-main-title">
                            <h4 class="widget-title">Последние новости</h4>
                        </div>
                        <!-- /.widget-main-title -->
                        <div class="widget-inner">
                            <#if news??>
                                <#list news as item>
                                    <div class="blog-list-post clearfix">
                                        <div class="blog-list-thumb">
                                            <a href="/news/${item.getId()}"><img
                                                    src="${item.getImage()}" alt=""></a>
                                        </div>
                                        <div class="blog-list-details">
                                            <h5 class="blog-list-title"><a
                                                    href="/news/${item.getId()}">${item.getTitle()}</a></h5>

                                            <p class="blog-list-meta small-text"><span><a
                                                    href="/news/${item.getId()}">${item.getCreated()?string["dd MMMM yyyy HH:mm"]}</a></span>
                                            </p>
                                        </div>
                                    </div>
                                </#list><!-- /.blog-list-post -->
                            </#if><!-- /.blog-list-post -->
                        </div>
                        <!-- /.widget-inner -->
                    </div>
                    <!-- /.widget-main -->
                </div>
                <!-- /col-md-6 -->

                <!-- Show Latest Events List -->
                <div class="col-md-6">
                    <div class="widget-main">
                        <div class="widget-main-title">
                            <h4 class="widget-title">Мероприятия</h4>
                        </div>
                        <!-- /.widget-main-title -->
                        <div class="widget-inner">
                            <#if events??>
                                <#list events as event>
                                    <div class="event-small-list clearfix">
                                        <div class="calendar-small">
                                            <span class="s-month">${event.getEventDate()?string.MMMM}</span>
                                            <span class="s-date">${event.getEventDate()?string.dd}</span>
                                        </div>
                                        <div class="event-small-details">
                                            <h5 class="event-small-title"><a
                                                    href="/event/${event.getId()}">${event.getTitle()}</a></h5>

                                            <p class="event-small-meta small-text">
                                                с ${event.getTime().getFrom()?string["HH:mm"]}
                                                до ${event.getTime().getTo()?string["HH:mm"]}</p>
                                        </div>
                                    </div>
                                </#list>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">

            <div class="widget-main">
                <div class="widget-main-title">
                    <h4 class="widget-title">Руководство</h4>
                </div>
                <div class="widget-inner">
                    <div class="prof-list-item clearfix">
                        <div class="prof-thumb">
                            <img src="/resources/image/prof3.jpg" alt="Profesor Name">
                        </div> <!-- /.prof-thumb -->
                        <div class="prof-details">
                            <h5 class="prof-name-list">
                                <a href="https://vk.com/khasianov" target="_blank">Айрат Хасьянов</a></h5>
                            <p class="small-text">Директор<br/>
                                <i class="fa fa-phone"></i> 8(843)221-34-33 (доб. 25) <br/>
                                <i class="fa fa-home"></i> Аудитория: 1505</p>
                        </div> <!-- /.prof-details -->
                    </div> <!-- /.prof-list-item -->
                    <div class="prof-list-item clearfix">
                        <div class="prof-thumb">
                            <img src="/resources/image/prof4.jpg" alt="Profesor Name">
                        </div>
                        <div class="prof-details">
                            <h5 class="prof-name-list">
                                <a href="https://vk.com/mfn76" target="_blank">Марат Насрутдинов</a></h5>
                            <p class="small-text">Заместитель директора по учебной работе<br/>
                                <i class="fa fa-phone"></i> 8(843)221-34-33 (доб. 13) <br/>
                                <i class="prof-margin"><i class="fa fa-home"></i>Аудитория: 1506</i></p>
                        </div>
                    </div>
                    <div class="prof-list-item clearfix">
                        <div class="prof-thumb">
                            <img src="/resources/image/prof5.jpg" alt="Profesor Name">
                        </div>
                        <div class="prof-details">
                            <h5 class="prof-name-list">
                                <a href="https://vk.com/aiselu" target="_blank">Айсылу Бакирова</a></h5>
                            <p class="small-text">Заместитель директора по социальной и воспитательной работе<br/>
                                <i class="fa fa-phone"></i> 8(843)221-34-33 (доб. 14) <br/>
                                <i class="fa fa-home prof-margin"></i> Аудитория: 1510б</p>
                        </div>
                    </div>
                    <div class="prof-list-item clearfix">
                        <div class="prof-thumb">
                            <img src="/resources/image/prof6.jpg" alt="Profesor Name">
                        </div> <!-- /.prof-thumb -->
                        <div class="prof-details">
                            <h5 class="prof-name-list">
                                <a href="https://vk.com/id16573417" target="_blank">Татьяна Глумова</a></h5>
                            <p class="small-text">Cекретарь<br/>
                                <i class="fa fa-phone"></i> 8(843)221-34-33 <br/>
                                <i class="fa fa-home"></i> Аудитория: 1505</p>
                        </div> <!-- /.prof-details -->
                    </div> <!-- /.prof-list-item -->
                    <div class="prof-list-item clearfix">
                        <div class="prof-thumb">
                            <img src="/resources/image/prof2.jpg" alt="Profesor Name">
                        </div> <!-- /.prof-thumb -->
                        <div class="prof-details">
                            <h5 class="prof-name-list">
                                <a href="https://vk.com/live_imagine" target="_blank">Михаил Абрамский</a></h5>
                            <p class="small-text">Cтарший преподаватель<br/>
                                <i class="fa fa-phone"></i> 8-917-917-11-33 <br/>
                                <i class="fa fa-home"></i> Аудитория: 1409</p>
                        </div> <!-- /.prof-details -->
                    </div> <!-- /.prof-list-item -->
                    <div class="prof-list-item clearfix">
                        <div class="prof-thumb">
                            <img src="/resources/image/prof1.jpg" alt="Profesor Name">
                        </div> <!-- /.prof-thumb -->
                        <div class="prof-details">
                            <h5 class="prof-name-list">
                                <a href="https://vk.com/georgymoiseev" target="_blank">Георгий Моисеев</a></h5>
                            <p class="small-text">Доцент<br/>
                                <i class="fa fa-phone"></i> 8-917-919-71-71<br/>
                                <i class="fa fa-home"></i> Аудитория: 1401
                            </p>
                        </div> <!-- /.prof-details -->
                    </div> <!-- /.prof-list-item-->
                </div>
            </div>
        </div>
    </div>
</div>
</@body>