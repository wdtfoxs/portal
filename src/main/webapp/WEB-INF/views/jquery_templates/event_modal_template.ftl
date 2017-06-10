<script type="text/x-jquery-tmpl" id="eventModalID">
  <div class="modal-dialog">
                <div class="modal-content">
                    <!-- Заголовок модального окна -->
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h4 class="modal-title">Изменить мероприятие</h4>
                    </div>
                    <!-- Модальное окно новостей -->
                    <@form.form enctype="multipart/form-data" method="post" id="form2" action="/event/update/{%= id%}">

                        <div class="modal-body" align="center">
                            <div class="form-group">
                                <label for="example-text-input" class="col-2 col-form-label">Заголовок: </label>
                                <div class="col-10">
                                    <input name="title" id="tex1" class="form-control" minlength="8" type="text"
                                           required="required" value="{%= title%}">

                                </div>
                            </div>
                            <br><label id="create_error1" style="display: none; color: red" class="control-label">Дата
                            проведения не может быть раньше сегодня</label>
                            <div class="form-group">
                                <label for="example-date-input" class="col-2 col-form-label">Дата проведения</label>
                                <div class="col-10">
                                    <input required="required" name="date" class="form-control" id="create_date1"  type="date" value="{%= moment.unix(eventDate/1000).format('YYYY-MM-DD')%}">

                                </div>
                            </div>
                            <br><label id="create_time_error1" style="display: none; color: red" class="control-label">Невалидное
                            время проведения</label>
                            <div class="form-group">
                                <label for="example-time-input" class="col-2 col-form-label">С</label>
                                <div class="col-10">
                                    <input required="required" name="from" class="form-control" type="time"
                                           id="create_from1" value="{%= moment.unix(time.from/1000).format('HH:mm') %}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="example-time-input" class="col-2 col-form-label">До</label>
                                <div class="col-10">
                                    <input required="required" name="to" class="form-control" type="time"
                                           id="create_to1" value="{%= moment.unix(time.to/1000).format('HH:mm')%}">
                                </div>
                            </div>

                            <label class="control-label">Изменить изображение (опц.): </label>
                            <br><label id="error1" style="display: none; color: red" class="control-label">Не является
                            изображением</label>
                            <input name="file" id="file1" type="file"
                                   data-buttonText="Выбрать изображение">
                            <div class="form-group">
                                <label for="comment">Описание (допустимы html теги): </label>
                                <textarea id="com1"  name="text" style="resize: vertical" minlength="20" required="required"
                                          maxlength="1000" class="form-control" rows="5">{%= text%}</textarea>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="submit" class="btn btn-primary">Добавить</button>
                        </div>
                    </@form.form>
                </div>
            </div>
</script>