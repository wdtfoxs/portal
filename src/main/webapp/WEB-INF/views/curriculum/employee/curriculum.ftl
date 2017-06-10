<#if curriculumView?has_content>
<div class="row">
    <div class="widget-inner">
        <div class="slider-testimonials">
            <div class="col-md-12">
                <table class="table table-sm table-striped">
                    <thead>
                    <tr>
                        <th>
                            #
                        </th>
                        <th>
                            Дисциплина
                        </th>
                        <th>
                            Всего
                        </th>
                        <th class="rotate">
                            <div><span>Лекционных</span></div>
                        </th>
                        <th class="rotate">
                            <div><span>Практических</span></div>
                        </th>
                        <th class="rotate">
                            <div><span>Лабораторных</span></div>
                        </th>
                        <th class="rotate">
                            <div><span>Тип</span></div>
                        </th>
                        <th class="rotate">
                            <div><span>Лекционных</span></div>
                        </th>
                        <th class="rotate">
                            <div><span>Практических</span></div>
                        </th>
                        <th class="rotate">
                            <div><span>Лабораторных</span></div>
                        </th>
                        <th class="rotate">
                            <div><span>Тип</span></div>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                        <#list curriculumView as curriculum>
                        <tr>
                            <td>
                            ${curriculum?index + 1}
                            </td>
                            <td>
                            ${curriculum.name}
                            </td>
                            <td>
                            ${curriculum.total}
                            </td>
                            <#if curriculum.first??>
                                <td>
                                ${curriculum.first.lectureAmount}
                                </td>
                                <td>
                                ${curriculum.first.practiceAmount}
                                </td>
                                <td>
                                ${curriculum.first.labAmount}
                                </td>
                                <td>
                                ${curriculum.first.passType.name()}
                                </td>
                            <#else>
                                <td>-</td>
                                <td>-</td>
                                <td>-</td>
                                <td>-</td>
                            </#if>
                            <#if curriculum.second??>
                                <td>
                                ${curriculum.second.lectureAmount}
                                </td>
                                <td>
                                ${curriculum.second.practiceAmount}
                                </td>
                                <td>
                                ${curriculum.second.labAmount}
                                </td>
                                <td>
                                ${curriculum.second.passType.name()}
                                </td>
                            <#else>
                                <td>-</td>
                                <td>-</td>
                                <td>-</td>
                                <td>-</td>
                            </#if>
                        </tr>
                        </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<#else >
<div class="row col-md-12" style="text-align: center">
    <span style="color: #048af7;">Для выбранной группы нет учебного плана</span>
</div>
</#if>