window.addEventListener('load', function(evt) {
    document.getElementById("loginForm").addEventListener('submit', attemptLogin);
});

function attemptLogin() {
    event.preventDefault();
    document.body.style.backgroundColor="red";
}