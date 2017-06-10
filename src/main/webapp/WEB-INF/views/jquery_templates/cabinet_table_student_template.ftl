<script type="text/x-jquery-tmpl" id="cabinet_table_student">
<tr>
    <td>${"$"}{id}</td>
    <td>{{if type == "ABOUT_STUDING"}}Об обучении{{else}}Для работы{{/if}}</td>
    <td>${"$"}{getDate(created)}</td>
    <td>Не готова</td>
</tr>
</script>
<script type="application/javascript">
    function getDate(created) {
        moment.locale('ru');
        return moment.unix(created/1000).format('DD-MM-YYYY HH:mm:ss');
    }
</script>