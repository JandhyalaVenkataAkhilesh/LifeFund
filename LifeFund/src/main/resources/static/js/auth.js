/*
==========================================
        TOKEN STORAGE
==========================================
*/

const TOKEN_KEY = "token";

/*
==========================================
        SAVE JWT TOKEN
==========================================
*/

function saveToken(token){

    localStorage.setItem(TOKEN_KEY, token);

}

function saveGoogleToken(){

    const params = new URLSearchParams(window.location.search);

    const token = params.get("token");

    if(token){

        saveToken(token);

        window.history.replaceState(
            {},
            document.title,
            window.location.pathname
        );

    }

}

/*
==========================================
        GET JWT TOKEN
==========================================
*/

function getToken(){

    return localStorage.getItem(TOKEN_KEY);

}

/*
==========================================
        REMOVE JWT TOKEN
==========================================
*/

function removeToken(){

    localStorage.removeItem(TOKEN_KEY);

}

/*
==========================================
        CHECK LOGIN
==========================================
*/

function isLoggedIn(){

    return getToken() !== null;

}

/*
==========================================
        AUTHORIZATION HEADER
==========================================
*/

function getAuthorizationHeader(){

    return {

        "Authorization": "Bearer " + getToken(),

        "Content-Type": "application/json"

    };

}

function getMultipartAuthorizationHeader(){

    return {

        "Authorization": "Bearer " + getToken()

    };

}

/*
==========================================
        LOGOUT
==========================================
*/

function logout(){

    removeToken();

    window.location.href = "login.html";

}

/*
==========================================
        REDIRECT IF NOT LOGGED IN
==========================================
*/

function requireLogin(){

    saveGoogleToken();

    if(!isLoggedIn()){

        showWarningToast("Please login first.");

        setTimeout(()=>{

            window.location.href="login.html";

        },1000);

    }

}

/*
==========================================
        REDIRECT IF ALREADY LOGGED IN
==========================================
*/

function redirectIfLoggedIn(){

    if(isLoggedIn()){

        window.location.href="dashboard.html";

    }

}