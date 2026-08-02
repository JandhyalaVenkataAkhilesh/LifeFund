/*
==========================================
            REQUIRE LOGIN
==========================================
*/

requireLogin();

/*
==========================================
            ELEMENTS
==========================================
*/

const userName =
    document.getElementById("userName");

const logoutButton =
    document.getElementById("logoutButton");

const campaignForm =
    document.getElementById("campaignForm");

const formSteps =
    document.querySelectorAll(".form-step");

const steps =
    document.querySelectorAll(".step");

const progressFill =
    document.getElementById("progressFill");

/*
==========================================
        STEP BUTTONS
==========================================
*/

const next1 =
    document.getElementById("next1");

const next2 =
    document.getElementById("next2");

const next3 =
    document.getElementById("next3");

const next4 =
    document.getElementById("next4");

const previous2 =
    document.getElementById("previous2");

const previous3 =
    document.getElementById("previous3");

const previous4 =
    document.getElementById("previous4");

const previous5 =
    document.getElementById("previous5");

/*
==========================================
            VARIABLES
==========================================
*/

let currentStep = 1;

/*
==========================================
            LOAD USER
==========================================
*/

async function loadUser(){

    try{

        const response =
            await fetch(

                DASHBOARD_API.GET_DASHBOARD,

                {

                    headers:
                        getAuthorizationHeader()

                }

            );

        if(!response.ok){

            throw new Error();

        }

        const data =
            await response.json();

        userName.innerHTML =
            `<i class="fa-solid fa-user"></i> ${data.userName}`;

    }

    catch(error){

        console.error(error);

    }

}

/*
==========================================
        UPDATE STEPPER
==========================================
*/

function updateStepper(){

    formSteps.forEach(

        step=>step.classList.remove("active")

    );

    document
        .getElementById(

            "step"+currentStep

        )
        .classList
        .add(

            "active"

        );

    steps.forEach(

        (

            step,

            index

        )=>{

            step.classList.remove(

                "active",

                "completed"

            );

            if(index+1<currentStep){

                step.classList.add(

                    "completed"

                );

            }

            else if(index+1===currentStep){

                step.classList.add(

                    "active"

                );

            }

        }

    );

    progressFill.style.width =

        (

            (

                currentStep-1

            )

            /

            (

                steps.length-1

            )

        )*100

        +

        "%";

}

/*
==========================================
        NEXT STEP
==========================================
*/

function nextStep(){

    if(currentStep<5){

        currentStep++;

        updateStepper();

        window.scrollTo(

            {

                top:0,

                behavior:"smooth"

            }

        );

    }

}

/*
==========================================
        PREVIOUS STEP
==========================================
*/

function previousStep(){

    if(currentStep>1){

        currentStep--;

        updateStepper();

        window.scrollTo(

            {

                top:0,

                behavior:"smooth"

            }

        );

    }

}

/*
==========================================
        BUTTON EVENTS
==========================================
*/

next1.addEventListener(

    "click",

    nextStep

);

next2.addEventListener(

    "click",

    nextStep

);

next3.addEventListener(

    "click",

    nextStep

);

next4.addEventListener(

    "click",

    nextStep

);

previous2.addEventListener(

    "click",

    previousStep

);

previous3.addEventListener(

    "click",

    previousStep

);

previous4.addEventListener(

    "click",

    previousStep

);

previous5.addEventListener(

    "click",

    previousStep

);

/*
==========================================
            LOGOUT
==========================================
*/

logoutButton.addEventListener(

    "click",

    logout

);

/*
==========================================
            PAGE LOAD
==========================================
*/

loadUser();

updateStepper();

/*
==========================================
        FORM ELEMENTS
==========================================
*/

const patientName =
    document.getElementById("patientName");

const age =
    document.getElementById("age");

const gender =
    document.getElementById("gender");

const bloodGroup =
    document.getElementById("bloodGroup");

const diseaseName =
    document.getElementById("diseaseName");

const hospitalName =
    document.getElementById("hospitalName");

const doctorName =
    document.getElementById("doctorName");

const treatmentRequired =
    document.getElementById("treatmentRequired");

const campaignTitle =
    document.getElementById("campaignTitle");

const targetAmount =
    document.getElementById("targetAmount");

const campaignDescription =
    document.getElementById("campaignDescription");

const description =
    document.getElementById("description");

const patientPhoto =
    document.getElementById("patientPhoto");

const medicalReport =
    document.getElementById("medicalReport");

const hospitalEstimate =
    document.getElementById("hospitalEstimate");

/*
==========================================
        PREVIEW ELEMENTS
==========================================
*/

const patientPreview =
    document.getElementById("patientPreview");

const medicalReportName =
    document.getElementById("medicalReportName");

const hospitalEstimateName =
    document.getElementById("hospitalEstimateName");

const previewPatientPhoto =
    document.getElementById("previewPatientPhoto");

const previewPatientName =
    document.getElementById("previewPatientName");

const previewDisease =
    document.getElementById("previewDisease");

const previewHospital =
    document.getElementById("previewHospital");

const previewDoctor =
    document.getElementById("previewDoctor");

const previewGoal =
    document.getElementById("previewGoal");

const previewTitle =
    document.getElementById("previewTitle");

const previewCampaignDescription =
    document.getElementById("previewCampaignDescription");

const previewStory =
    document.getElementById("previewStory");

const previewMedicalReport =
    document.getElementById("previewMedicalReport");

const previewHospitalEstimate =
    document.getElementById("previewHospitalEstimate");

/*
==========================================
        PATIENT PHOTO PREVIEW
==========================================
*/

patientPhoto.addEventListener(

    "change",

    function(){

        const file = patientPhoto.files[0];

        if(!file){

            return;

        }

        const reader =
            new FileReader();

        reader.onload =
            function(event){

                patientPreview.src =
                    event.target.result;

                patientPreview.style.display =
                    "block";

            };

        reader.readAsDataURL(file);

    }

);

/*
==========================================
        PDF FILE NAME
==========================================
*/

medicalReport.addEventListener(

    "change",

    function(){

        if(medicalReport.files.length){

            medicalReportName.textContent =

                "✔ "

                +

                medicalReport.files[0].name;

        }

    }

);

hospitalEstimate.addEventListener(

    "change",

    function(){

        if(hospitalEstimate.files.length){

            hospitalEstimateName.textContent =

                "✔ "

                +

                hospitalEstimate.files[0].name;

        }

    }

);

/*
==========================================
        LIVE PREVIEW
==========================================
*/

next4.addEventListener(

    "click",

    function(){

        previewPatientName.textContent =

            patientName.value;

        previewDisease.textContent =

            diseaseName.value;

        previewHospital.textContent =

            hospitalName.value;

        previewDoctor.textContent =

            doctorName.value;

        previewGoal.textContent =

            Number(

                targetAmount.value

            ).toLocaleString(

                "en-IN"

            );

        previewTitle.textContent =

            campaignTitle.value;

        previewCampaignDescription.textContent =

            campaignDescription.value;

        previewStory.textContent =

            description.value;

        if(

            medicalReport.files.length

        ){

            previewMedicalReport.textContent =

                medicalReport.files[0].name;

        }

        if(

            hospitalEstimate.files.length

        ){

            previewHospitalEstimate.textContent =

                hospitalEstimate.files[0].name;

        }

        if(

            patientPhoto.files.length

        ){

            const reader =
                new FileReader();

            reader.onload =
                function(event){

                    previewPatientPhoto.src =
                        event.target.result;

                };

            reader.readAsDataURL(

                patientPhoto.files[0]

            );

        }

    }

);

/*
==========================================
        SUCCESS MODAL
==========================================
*/

const successModal =
    document.getElementById("successModal");

const dashboardButton =
    document.getElementById("dashboardButton");

/*
==========================================
        CREATE CAMPAIGN
==========================================
*/

campaignForm.addEventListener(

    "submit",

    async function(event){

        event.preventDefault();

        /*
        ==========================================
                    VALIDATION
        ==========================================
        */

        if(patientName.value.trim() === ""){

            showWarningToast(
                "Enter patient name."
            );

            return;

        }

        if(age.value === ""){

            showWarningToast(
                "Enter age."
            );

            return;

        }

        if(gender.value === ""){

            showWarningToast(
                "Select gender."
            );

            return;

        }

        if(bloodGroup.value === ""){

            showWarningToast(
                "Select blood group."
            );

            return;

        }

        if(diseaseName.value.trim() === ""){

            showWarningToast(
                "Enter disease name."
            );

            return;

        }

        if(hospitalName.value.trim() === ""){

            showWarningToast(
                "Enter hospital name."
            );

            return;

        }

        if(doctorName.value.trim() === ""){

            showWarningToast(
                "Enter doctor name."
            );

            return;

        }

        if(campaignTitle.value.trim() === ""){

            showWarningToast(
                "Enter campaign title."
            );

            return;

        }

        if(targetAmount.value === ""){

            showWarningToast(
                "Enter target amount."
            );

            return;

        }

        if(patientPhoto.files.length === 0){

            showWarningToast(
                "Upload patient photo."
            );

            return;

        }

        if(medicalReport.files.length === 0){

            showWarningToast(
                "Upload medical report."
            );

            return;

        }

        if(hospitalEstimate.files.length === 0){

            showWarningToast(
                "Upload hospital estimate."
            );

            return;

        }

        try{

            const formData = new FormData();

            formData.append(
                "patientName",
                patientName.value.trim()
            );

            formData.append(
                "age",
                age.value
            );

            formData.append(
                "gender",
                gender.value
            );

            formData.append(
                "bloodGroup",
                bloodGroup.value
            );

            formData.append(
                "diseaseName",
                diseaseName.value.trim()
            );

            formData.append(
                "hospitalName",
                hospitalName.value.trim()
            );

            formData.append(
                "doctorName",
                doctorName.value.trim()
            );

            formData.append(
                "treatmentRequired",
                treatmentRequired.value.trim()
            );

            formData.append(
                "campaignTitle",
                campaignTitle.value.trim()
            );

            formData.append(
                "campaignDescription",
                campaignDescription.value.trim()
            );

            formData.append(
                "description",
                description.value.trim()
            );

            formData.append(
                "targetAmount",
                targetAmount.value
            );

            formData.append(
                "patientPhoto",
                patientPhoto.files[0]
            );

            formData.append(
                "medicalReport",
                medicalReport.files[0]
            );

            formData.append(
                "hospitalEstimate",
                hospitalEstimate.files[0]
            );

            const response = await fetch(

                CAMPAIGN_API.CREATE,

                {

                    method:"POST",

                    headers:
                        getMultipartAuthorizationHeader(),

                    body:formData

                }

            );

            if(!response.ok){

                throw new Error(
                    await response.text()
                );

            }

            successModal.classList.add(
                "show"
            );

        }

        catch(error){

            console.error(error);

            showErrorToast(
                "Unable to create campaign."
            );

        }

    }

);

/*
==========================================
        DASHBOARD BUTTON
==========================================
*/

dashboardButton.addEventListener(

    "click",

    function(){

        window.location.href =
            "dashboard.html";

    }

);
