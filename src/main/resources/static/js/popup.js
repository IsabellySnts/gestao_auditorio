function openLoginForm(){
document.body.classList.add("showLoginForm");
}



const button = document.getElementById("saveButton");



button.addEventListener('click', function (e) {
e.preventDefault();
document.body.classList.add("showLoginForm");
})



function closeLoginForm(){
document.body.classList.remove("showLoginForm");
}