
chrome.contextMenus.create({
    title: "Send",
    contexts:["selection"],
    onclick: sendContent,
});

function sendContent() {
}