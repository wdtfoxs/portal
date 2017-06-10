/**
 * Created by wdtfoxs on 20.05.2017.
 */
$(document).ready(
    $(document).on('click', '.dev_order_btn', function () {
        $('.dev_order_btn').prop('disabled', true);
        var count = $('.dev_count_doc').val();
        var type = $('.dev_type_doc').val();
        $.ajax({
            url: 'cabinet?order&count=' + count + "&type=" + type,
            type: 'POST',
            success: function (response) {
                $('#cabinet_table_student').tmpl(response).prependTo('.tmpl');
            }
        });
    })
);