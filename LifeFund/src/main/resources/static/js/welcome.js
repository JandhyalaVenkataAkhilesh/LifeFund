document
    .getElementById("loginBtn")
    .addEventListener("click",()=>{

        window.location.href="login.html";

    });


document
    .getElementById("signupBtn")
    .addEventListener("click",()=>{

        window.location.href="signup.html";

    });


document
    .getElementById("googleBtn")
    .addEventListener("click",()=>{

        window.location.href=
            "/oauth2/authorization/google";

    });