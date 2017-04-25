jQuery(document).ready(function ($) {
    chrome.runtime.onMessage.addListener(
        function (request, sender, sendResponse) {
            console.log("co jest");
            if (request.command == "send") {
                $.ajax({
                    data: getSelectionText(),
                    headers: {'X-AUTH-TOKEN': request.token},
                    timeout: 1000,
                    type: 'POST',
                    data: {content: getSelectionText()},
                    url: 'http://localhost:8080/resources'
                }).done(function (data, textStatus, jqXHR) {
                    alert("Content sent");
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    alert("Could not send content"+textStatus+errorThrown);
                });
            }
            else
                console.log("co jest");
        });

    function getSelectionText() {
        var text = "";
        if (window.getSelection) {
            text = window.getSelection().toString();
        } else if (document.selection && document.selection.type != "Control") {
            text = document.selection.createRange().text;
        }
        return text;
    }
});
