/*
==========================================
            ELEMENTS
==========================================
*/

const signupForm = document.getElementById("signupForm");

const nameField = document.getElementById("name");

const emailField = document.getElementById("email");

const phoneNumberField = document.getElementById("phoneNumber");

const passwordField = document.getElementById("password");

const togglePassword = document.getElementById("togglePassword");

const signupButton = document.getElementById("signupButton");

const signupText = document.getElementById("signupText");

const signupSpinner = document.getElementById("signupSpinner");

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

    if(passwordField.type === "password"){

        passwordField.type = "text";

        togglePassword.classList.replace(
                "fa-eye",
                "fa-eye-slash"
        );

    }else{

        passwordField.type = "password";

        togglePassword.classList.replace(
                "fa-eye-slash",
                "fa-eye"
        );

    }

});

/*
==========================================
            SIGNUP
==========================================
*/

signupForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    signupButton.disabled = true;

    signupText.style.display = "none";

    signupSpinner.style.display = "inline-block";

    const request = {

        name: nameField.value.trim(),

        email: emailField.value.trim(),

        phoneNumber: phoneNumberField.value.trim(),

        password: passwordField.value

    };

    try{

        /*
        ==========================================
                REGISTER USER
        ==========================================
        */

        const signupResponse = await fetch(AUTH_API.SIGNUP,{

            method:"POST",

            headers:{

                "Content-Type":"application/json"

            },

            body:JSON.stringify(request)

        });

        const signupData = await signupResponse.json();

        if(!signupResponse.ok){

            const message =
                    signupData.error?.message ||
                    signupData.message ||
                    "Registration Failed";

            showErrorToast(message);

            return;

        }

        /*
        ==========================================
                AUTO LOGIN
        ==========================================
        */

        const loginResponse = await fetch(AUTH_API.LOGIN,{

            method:"POST",

            headers:{

                "Content-Type":"application/json"

            },

            body:JSON.stringify({

                email:request.email,

                password:request.password

            })

        });

        const loginData = await loginResponse.json();

        if(loginResponse.ok){

            saveToken(loginData.token);

            showSuccessToast(
                    "Registration Successful"
            );

            setTimeout(()=>{

                window.location.href="dashboard.html";

            },1000);

        }else{

            showWarningToast(
                    "Registration completed. Please login."
            );

            setTimeout(()=>{

                window.location.href="login.html";

            },1200);

        }

    }catch(error){

        console.error(error);

        showErrorToast(
                "Unable to connect to server."
        );

    }finally{

        signupButton.disabled=false;

        signupSpinner.style.display="none";

        signupText.style.display="inline";

    }

});