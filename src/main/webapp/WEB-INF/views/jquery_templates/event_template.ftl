<script type="text/x-jquery-tmpl" id="eventID">
  <div class="list-event-item">
                    <div class="box-content-inner clearfix">
                        <div class="list-event-thumb">
                            <a href="/event/{%= id%}">
                                <img src="{%= image%}" alt="">
                            </a>
                        </div>
                        <div class="list-event-header">
                            <span class="event-date small-text"><i
                                    class="fa fa-calendar-o"></i>{%= getDate(eventDate)%}</span>
                            <@security.authorize access="hasAnyRole('EMPLOYEE', 'ADMIN')">
                                <div class="view-details"><a data-toggle="modal" href="#eventEditModal"
                                                             data-id="{%= id%}"
                                                             class="lightBtn">Изменить</a></div>
                            </@security.authorize>
                            <@security.authorize access="hasRole('ADMIN')">
                                <div class="view-details"><a data-id="{%= id%}"
                                                             class="lightBtnAdd">Удалить</a></div>
                            </@security.authorize>
                        </div>
                        <h5 class="event-title"><a href="/event/{%= id%}">{%= title%}</a></h5>
                        <p>с {%= getTime(time.from)%} до {%= getTime(time.to)%}</p>
                        <p>{%= text.substring(0, 101)%}...</p>
                    </div>
                </div>
</script>
<script type="application/javascript">
    function getDate(eventDate) {
        moment.locale('ru');
        return moment.unix(eventDate/1000).format("LL");
    }
    function getTime(time){
        moment.locale('ru');
        return moment.unix(time/1000).format("LT");
    }
</script>