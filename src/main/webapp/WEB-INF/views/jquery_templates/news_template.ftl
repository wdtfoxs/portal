<script type="text/x-jquery-tmpl" id="newsID">
  <div class="list-event-item">
                    <div class="box-content-inner clearfix">
                        <div class="list-event-thumb">
                            <a href="/news/{%= id%}">
                                <img src="{%= image%}" alt="">
                            </a>
                        </div>
                        <div class="list-event-header">
                            <span class="event-date small-text"><i
                                    class="fa fa-calendar-o"></i>{%= getDate(created)%}</span>
                          <@security.authorize access="hasAnyRole('EMPLOYEE', 'ADMIN')">
                                <div class="view-details"><a data-toggle="modal" href="#newsEditModal"
                                                             data-id="{%= id%}"
                                                             class="lightBtn">Изменить</a></div>
                            </@security.authorize>
                            <@security.authorize access="hasRole('ADMIN')">
                                <div class="view-details"><a data-id="{%= id%}"
                                                             class="lightBtnAdd">Удалить</a></div>
                            </@security.authorize>
                        </div>
                        <h5 class="event-title"><a href="/news/{%= id%}">{%= title%}</a></h5>
                        <p>{%= text.substring(0, 101)%}...</p>
                    </div>
                </div>
</script>
<script type="application/javascript">
    function getDate(created) {
        moment.locale('ru');
        return moment.unix(created/1000).format("LL");
    }
</script>