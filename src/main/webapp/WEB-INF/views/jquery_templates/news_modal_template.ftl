<script type="text/x-jquery-tmpl" id="newsModalID">
    <div class="modal-dialog">
            <div class="modal-content">
                <!-- Заголовок модального окна -->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">Изменить новость</h4>
                </div>
                <!-- Модальное окно новостей -->
                <@form.form enctype="multipart/form-data" method="post" id="form2" action="/news/update/{%= id%}">
                    <div class="modal-body" align="center">
                        <div class="form-group">
                            <label for="example-text-input" class="col-2 col-form-label">Заголовок: </label>
                            <div class="col-10">
                                <input name="title" value="{%= title%}" class="form-control" minlength="8" type="text" required="required"
                                       id="example-text-input1">
                            </div>
                        </div>
                        <label class="control-label">Обновить изображение (опц.): </label>
                        <br><label id="error1" style="display: none; color: red" class="control-label">Не является
                            изображением</label>
                        <input name="file" id="file1" type="file" class="filestyle"
                               data-buttonText="Выбрать изображение">
                        <div class="form-group">
                            <label for="comment">Описание (допустимы html теги): </label>
                            <textarea name="text" style="resize: vertical" minlength="20" required="required"
                                      maxlength="1000" class="form-control" rows="5" id="comment1">{%= text%}</textarea>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-primary">Добавить</button>
                    </div>
                    </@form.form>
            </div>
        </div>
</script>