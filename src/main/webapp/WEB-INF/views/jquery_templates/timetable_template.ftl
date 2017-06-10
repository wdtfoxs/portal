<script type="text/x-jquery-tmpl" id="timetableID">
{%each(week, classes) timetables%}
                <div class="widget-main col-lg-12">
                    <div class="widget-main-title">
                        <h4 class="widget-title">{%= week%}</h4>
                    </div>
                    <div class="widget-inner">
                        <div class="slider-testimonials">
                            <table class="table">
                                <thead>
                                <tr>
                                    <td>Время</td>
                                    <td>Тип</td>
                                    <td>Здание</td>
                                    <td>Аудитория</td>
                                    <td>Предмет</td>
                                    <td>Преподаватель</td>
                                    <td>Неделя</td>
                                    <td>Комментарий</td>
                                </tr>
                                </thead>

                                {%each(classQ, selects) classes%}
                                 <tr>
                                    <td class="tg-031e">{%= getTimeByClass(classQ)%}</td>
                                    <td class="tg-031e">
                                        <select data-week="{%= week%}" data-class="{%= classQ%}" class="form-control types">
                                          {%each selects.types%}
                                         <option {%if selected%}selected="selected"{%/if%} value="{%= value%}">{%= text%}</option>
                                         {%/each%}
                                        </select>
                                    </td>
                                    <td class="tg-031e">

                                        <select data-week="{%= week%}" data-class="{%= classQ%}" class="form-control buildings">
                                         {%each selects.buildings%}
                                         <option {%if selected%}selected="selected"{%/if%} value="{%= value%}">{%= text%}</option>

                                         {%/each%}
                                        </select>
                                    </td>
                                    <td class="tg-031e">
                                        <select data-week="{%= week%}" data-class="{%= classQ%}" class="form-control auditoriums">
                                        {%if selects.auditoriums !== null%}
                                             {%each selects.auditoriums%}
                                         <option {%if selected%}selected="selected"{%/if%} value="{%= value%}">{%= text%}</option>

                                         {%/each%}
                                         {%/if%}
                                        </select>
                                    </td>

                                    <td class="tg-031e">
                                        <select data-week="{%= week%}" data-class="{%= classQ%}" class="form-control disciplines">
                                            {%each selects.disciplines%}
                                         <option {%if selected%}selected="selected"{%/if%} value="{%= value%}">{%= text%}</option>
                                         {%/each%}
                                        </select>
                                    </td>
                                    <td class="tg-031e">
                                        <select data-week="{%= week%}" data-class="{%= classQ%}" class="form-control professors">
                                       {%if selects.professors !== null%}
                                             {%each selects.professors%}
                                         <option {%if selected%}selected="selected"{%/if%} value="{%= value%}">{%= text%}</option>

                                         {%/each%}
                                         {%/if%}
                                        </select>
                                    </td>

                                    <td class="tg-031e">
                                        <select data-week="{%= week%}" data-class="{%= classQ%}" class="form-control weeks">
                                             {%each selects.weeks%}
                                         <option {%if selected%}selected="selected"{%/if%} value="{%= value%}">{%= text%}</option>

                                         {%/each%}
                                        </select>
                                    </td>
                                    <td class="tg-031e">
                                     {%each selects.comment%}
                                         <input data-week="{%= week%}" data-class="{%= classQ%}" type="text" value="{%= text%}" class="form-control comments">
                                     {%/each%}

                                    </td>
                                </tr>
                                {%/each%}
                            </table>
                        </div>
                    </div> <!-- /.widget-inner -->
                </div>
            {%/each%}

</script>
<script type="application/javascript">
    function getTimeByClass(classNum) {
        switch (classNum){
            case "ПЕРВАЯ":
                return "08.30 - 10.00";
            case "ВТОРАЯ":
                return "10.10 - 11.40";

            case "ТРЕТЬЯ":
                return "11.50 - 13.20";

            case "ЧЕТВЕРТАЯ":
                return "13.35 - 15.05";

            case "ПЯТАЯ":
                return "15.20 - 16.50";

            case "ШЕСТАЯ":
                return "17.00 - 18.30";

            case "СЕДЬМАЯ":
                return "18.40 - 20.10";
        }
    }
</script>