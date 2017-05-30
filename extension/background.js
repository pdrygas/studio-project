var authToken = null;

chrome.contextMenus.create({
    title: "QuickSend",
    contexts:["selection","image"],
    onclick: function() {
        triggerContentTransport();
    }
});

chrome.contextMenus.create({
    title: "Send",
    contexts: ["selection","image"],
    onclick: function() {
        popupwindow('resources/sendPopup.html','Menu',400,200);
    }
});

chrome.extension.onConnect.addListener(function(port) {
    port.onMessage.addListener(function(msg) {
        if (msg.command == "save") {
            authToken = msg.token;
        } else if (msg.command == "send") {
            triggerContentTransport(msg.title, msg.category)
        } else if (msg.command == "isTokenSet") {
            if (authToken) {
                port.postMessage({command: "tokenState", state: "SET"})
            } else {
                port.postMessage({command: "tokenState", state: "NOTSET"})
            }
        } else {
            alert("Background page received unexpected command on port")
        }
    });
});

function popupwindow(url, title, w, h) {
    var left = (screen.width/2)-(w/2);
    var top = (screen.height/2)-(h/2);
    var newWindow = window.open(url, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left);
    newWindow.token = authToken;
    return newWindow;
}

function triggerContentTransport() {
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.sendMessage(tabs[0].id, {
            command: "send",
            token: authToken});
    });
}

function triggerCategorizedContentTransport(title, category) {
    chrome.tabs.query({active: true, currentWindow: false}, function(tabs) {
        chrome.tabs.sendMessage(tabs[0].id, {
            command: "send",
            token: authToken,
            title: title,
            category: category,});
    });
}