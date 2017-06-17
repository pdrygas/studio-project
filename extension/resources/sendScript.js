function getCategories() {
    $('#dropdownCategories').empty();
    $.ajax({
        headers: {'X-AUTH-TOKEN': window.token},
        timeout: 3000,
        type: 'GET',
        url: 'http://localhost:8080/api/categories'
    }).done(function (data, textStatus, jqXHR) {
        var res=$.parseJSON(data);
        jQuery.each(res, function(id,category ){
            var option = $('<option/>');
            option.text(category.title);
            $('#dropdownCategories').append(option);
        });
    }).fail(function (jqXHR, textStatus, errorThrown) {
        alert("Could not fetch categories");
    });
}

jQuery(document).ready(function ($) {
    var port = chrome.extension.connect({
        name: "Background Page communication"
    });

    getCategories();

    $('#createNewCategory').submit(function (event) {
        event.preventDefault();
        $.ajax({
            headers: {'X-AUTH-TOKEN': window.token},
            timeout: 3000,
            type: 'POST',
            data: {title: $('#newCategoryName').val()},
            url: 'http://localhost:8080/api/categories'
        }).done(function (data, textStatus, jqXHR) {
            getCategories();
            alert("Category added");
        }).fail(function (jqXHR, textStatus, errorThrown) {
            alert("Failed to add category");
        });
    });

    $('#sendNewContentWithCategory').submit(function (event) {
        event.preventDefault();

        categoryName = $('#dropdownCategories').find(":selected").text();
        title = $('#contentTitle').val();

        window.opener.triggerCategorizedContentTransport(title, categoryName);
    });
})