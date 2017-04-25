var authToken = null;

chrome.contextMenus.create({
    title: "Send",
    contexts:["selection","image"],
    onclick: fireContentTransport
});

function fireContentTransport() {
    chrome.tabs.query({active: true, currentWindow: true}, function(tabs) {
        chrome.tabs.sendMessage(tabs[0].id, {command: "send", token: authToken});
    });
}

chrome.extension.onConnect.addListener(function(port) {
    port.onMessage.addListener(function(msg) {
        if (msg.command == "save") {
            authToken = msg.token;
        } else if (msg.command == "isTokenSet") {
            if (authToken)
                port.postMessage({command: "tokenState", state: "SET"})
            else
                port.postMessage({command: "tokenState", state: "NOTSET"})
        }
    });
});