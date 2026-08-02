let toastContainer=document.querySelector(".toast-container");

if(!toastContainer){

    toastContainer=document.createElement("div");

    toastContainer.className="toast-container";

    document.body.appendChild(toastContainer);

}

function showToast(message,type="info"){

    const toast=document.createElement("div");

    toast.className=`toast toast-${type}`;

    toast.innerHTML=`
        <span>${message}</span>
        <span class="toast-close">&times;</span>
    `;

    toast.querySelector(".toast-close")
        .addEventListener("click",()=>{

            toast.remove();

        });

    toastContainer.appendChild(toast);

    setTimeout(()=>{

        toast.style.animation="fadeOut .3s forwards";

        setTimeout(()=>{

            toast.remove();

        },300);

    },3000);

}

function showSuccessToast(message){

    showToast(message,"success");

}

function showErrorToast(message){

    showToast(message,"error");

}

function showWarningToast(message){

    showToast(message,"warning");

}

function showInfoToast(message){

    showToast(message,"info");

}