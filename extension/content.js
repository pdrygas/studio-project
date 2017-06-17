var clickedEl = null;

jQuery(document).ready(function ($) {
    document.addEventListener("mousedown", function(event){
        //right click
        if(event.button == 2) {
            clickedEl = event.target;
        }
    }, true);

    chrome.runtime.onMessage.addListener(
        function (request, sender, sendResponse) {
            if (request.command == "send") {
                $.ajax({
                    headers: {'X-AUTH-TOKEN': request.token},
                    timeout: 3000,
                    type: 'POST',
                    data: {
                        content: getSelectionText(),
                        title: request.title,
                        category: request.category
                    },
                    url: 'http://localhost:8080/api/resources'
                }).done(function (data, textStatus, jqXHR) {
                    alert("Content sent");
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    alert("Could not send content"+textStatus+errorThrown);
                });
            } else if (request.command == "sendImage") {
                $.ajax({
                    headers: {'X-AUTH-TOKEN': request.token},
                    timeout: 3000,
                    type: 'POST',
                    data: {
                        url: clickedEl.src,
                    },
                    url: 'http://localhost:8080/api/images'
                }).done(function (data, textStatus, jqXHR) {
                    alert("Image sent");
                }).fail(function (jqXHR, textStatus, errorThrown) {
                    alert("Could not send image"+textStatus+errorThrown);
                });
            } else {
                console.log("Content script listener received unexpected command");
            }
        });
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
