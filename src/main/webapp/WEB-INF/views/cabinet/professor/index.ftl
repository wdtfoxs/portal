<#macro menu>
<li class="active"><a href="/cabinet">Личный кабинет</a></li>
<li><a href="/auditorium">Аудитории</a></li>
<li><a href="/timetable/professor">Расписание</a></li>
<li><a href="/exams/professor">Расписание экзаменов</a></li>
<li><a href="/journal/professor">Журнал посещений</a></li>
</#macro>
<@body title="Страница преподавателя">
<script src="/resources/js/professor.js"></script>
<div class="container">
    <div class="text-center">
        <div class="widget-main-title">
            <h4 class="widget-title">${professor.name} ${professor.surname}</h4>
        </div>
        <div class="col-md-6">
            <div class="widget-main">
                <div class="widget-main-title">
                    <h4 class="widget-title">Занимаемые должности</h4>
                </div> <!-- /.widget-main-title -->
                <div class="widget-inner">
                    <#list professor.positions as p>
                        <div class="blog-list-post clearfix">
                            <div class="blog-list-details">
                                <h5 class="blog-list-title">${p.position}</h5>
                            </div>
                        </div> <!-- /.blog-list-post -->
                    </#list>
                </div> <!-- /.widget-inner -->
            </div> <!-- /.widget-main -->
        </div> <!-- /col-md-6 -->

        <!-- Show Latest Events List -->
        <div class="col-md-6">
            <div class="widget-main">
                <div class="widget-main-title">
                    <h4 class="widget-title">Преподоваемые дисциплины</h4>
                </div> <!-- /.widget-main-title -->
                <div class="widget-inner">
                    <#list professor.disciplines as d>
                        <div class="blog-list-post clearfix">
                            <div class="blog-list-details">
                                <h5 class="blog-list-title">${d.discipline}</h5>
                            </div>
                        </div>
                    </#list>
                </div> <!-- /.widget-inner -->
            </div> <!-- /.widget-main -->
        </div>
    </div>
</div>
</@body>