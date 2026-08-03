/*
==========================================
            ELEMENTS
==========================================
*/

const loginForm = document.getElementById("loginForm");

const email = document.getElementById("email");

const password = document.getElementById("password");

const togglePassword = document.getElementById("togglePassword");

const loginButton = document.getElementById("loginButton");

const loginText = document.getElementById("loginText");

const loginSpinner = document.getElementById("loginSpinner");

const googleLogin = document.getElementById("googleLogin");

/*
==========================================
        REDIRECT IF ALREADY LOGGED IN
==========================================
*/

redirectIfLoggedIn();

/*
==========================================
        SHOW / HIDE PASSWORD
==========================================
*/

togglePassword.addEventListener("click", () => {

    if (password.type === "password") {

        password.type = "text";

        togglePassword.classList.replace(
                "fa-eye",
                "fa-eye-slash"
        );

    } else {

        password.type = "password";

        togglePassword.classList.replace(
                "fa-eye-slash",
                "fa-eye"
        );

    }

});

/*
==========================================
            GOOGLE LOGIN
==========================================
*/

googleLogin.addEventListener("click", () => {

    window.location.href = AUTH_API.GOOGLE_LOGIN;

});

/*
==========================================
            LOGIN
==========================================
*/

loginForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    loginButton.disabled = true;

    loginText.style.display = "none";

    loginSpinner.style.display = "inline-block";

    const request = {

        email: email.value.trim(),

        password: password.value

    };

    try {

        const response = await fetch(AUTH_API.LOGIN, {

            method: "POST",

            headers: {

                "Content-Type": "application/json"

            },

            body: JSON.stringify(request)

        });

        const data = await response.json();

        if (response.ok) {

            saveToken(data.token);

            showSuccessToast(
                    "Login Successful"
            );

            setTimeout(() => {

                window.location.href =
                        "dashboard.html";

            }, 1000);

        } else {

            const message =
                    data.error?.message ||
                    data.message ||
                    "Invalid Email or Password";

            showErrorToast(message);

        }

    } catch (error) {

        console.error(error);

        showErrorToast(
                "Unable to connect to server."
        );

    } finally {

        loginButton.disabled = false;

        loginSpinner.style.display = "none";

        loginText.style.display = "inline";

    }

});