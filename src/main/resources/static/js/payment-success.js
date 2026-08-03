/*
==========================================
            ELEMENTS
==========================================
*/

const amountElement =
    document.getElementById("amount");

const messageElement =
    document.getElementById("message");

const secondsElement =
    document.getElementById("seconds");

const progressBar =
    document.getElementById("progressBar");

/*
==========================================
        GET URL PARAMETERS
==========================================
*/

const params =
    new URLSearchParams(window.location.search);

const amount =
    params.get("amount");

const patient =
    params.get("patient");

/*
==========================================
    FORMAT INDIAN CURRENCY
==========================================
*/

function formatCurrency(amount){

    return Number(amount)
            .toLocaleString("en-IN");

}

/*
==========================================
        DISPLAY DETAILS
==========================================
*/

amountElement.textContent =
    `₹${formatCurrency(amount)} Donated Successfully`;

messageElement.textContent =
    `Thank you for supporting ${patient} ❤️`;

/*
==========================================
        COUNTDOWN
==========================================
*/

let seconds = 7;

const interval = setInterval(()=>{

    seconds--;

    secondsElement.textContent =
        seconds;

    progressBar.style.width =
        (seconds / 7) * 100 + "%";

    if(seconds <= 0){

        clearInterval(interval);

        window.location.href =
            "dashboard.html";

    }

},1000);