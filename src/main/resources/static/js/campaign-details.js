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

const userName = document.getElementById("userName");

const logoutButton = document.getElementById("logoutButton");

const patientName = document.getElementById("patientName");

const donationPatientName = document.getElementById("donationPatientName");

const campaignStatus = document.getElementById("campaignStatus");

const patientPhoto = document.getElementById("patientPhoto");

const diseaseName = document.getElementById("diseaseName");

const age = document.getElementById("age");

const gender = document.getElementById("gender");

const bloodGroup = document.getElementById("bloodGroup");

const hospitalName = document.getElementById("hospitalName");

const doctorName = document.getElementById("doctorName");

const raisedAmount = document.getElementById("raisedAmount");

const targetAmount = document.getElementById("targetAmount");

const donorCount = document.getElementById("donorCount");

const progressBar = document.getElementById("progressBar");

const progressPercentage = document.getElementById("progressPercentage");

const description = document.getElementById("description");

const treatmentRequired = document.getElementById("treatmentRequired");

const medicalReportBtn = document.getElementById("medicalReportBtn");

const hospitalEstimateBtn = document.getElementById("hospitalEstimateBtn");

const donorName =
    document.getElementById("donorName");

const donorEmail =
    document.getElementById("donorEmail");

const donorPhoneNumber =
    document.getElementById("donorPhoneNumber");

const donationAmount = document.getElementById("donationAmount");

const donateButton = document.getElementById("donateButton");

/*
==========================================
            VARIABLES
==========================================
*/

const params = new URLSearchParams(window.location.search);

const campaignId = params.get("id");

let campaign = null;

/*
==========================================
        FORMAT CURRENCY
==========================================
*/

function formatCurrency(amount){

    return Number(amount).toLocaleString("en-IN");

}

/*
==========================================
        LOAD USER
==========================================
*/

async function loadUser(){

    try{

        const response = await fetch(

            DASHBOARD_API.GET_DASHBOARD,

            {

                headers:getAuthorizationHeader()

            }

        );

        if(!response.ok){

            throw new Error();

        }

        const data = await response.json();

        userName.innerHTML =
            `<i class="fa-solid fa-user"></i> ${data.userName}`;

    }catch(error){

        console.error(error);

    }

}

/*
==========================================
        LOAD CAMPAIGN
==========================================
*/

async function loadCampaign(){

    try{

        const response = await fetch(

            CAMPAIGN_API.GET_BY_ID(campaignId)

        );

        if(!response.ok){

            throw new Error(await response.text());

        }

        campaign = await response.json();

        populateCampaign();

    }catch(error){

        console.error(error);

        showErrorToast("Unable to load campaign.");

    }

}

/*
==========================================
        POPULATE DATA
==========================================
*/

function populateCampaign(){

    patientName.textContent =
        campaign.patientName;

    donationPatientName.textContent =
        campaign.patientName;

    campaignStatus.textContent =
        campaign.campaignStatus;

    diseaseName.textContent =
        campaign.diseaseName;

    age.textContent =
        campaign.age + " Years";

    gender.textContent =
        campaign.gender;

    bloodGroup.textContent =
        campaign.bloodGroup;

    hospitalName.textContent =
        campaign.hospitalName;

    doctorName.textContent =
        campaign.doctorName;

    description.textContent =
        campaign.description;

    treatmentRequired.textContent =
        campaign.treatmentRequired;

    raisedAmount.textContent =
        formatCurrency(campaign.raisedAmount);

    targetAmount.textContent =
        formatCurrency(campaign.targetAmount);

    donorCount.textContent =
        campaign.donorCount;

    patientPhoto.src =
        FILE_API.GET_FILE +
        "/patient-photos/" +
        campaign.patientPhoto;

    const percentage = Math.min(

        (

            campaign.raisedAmount /
            campaign.targetAmount

        ) * 100,

        100

    ).toFixed(0);

    progressBar.style.width =
        percentage + "%";

    progressPercentage.textContent =
        percentage + "%";

}

/*
==========================================
        MEDICAL REPORT
==========================================
*/

medicalReportBtn.addEventListener(

    "click",

    function(){

        window.open(

            FILE_API.GET_FILE +
            "/medical-reports/" +
            campaign.medicalReport,

            "_blank"

        );

    }

);

/*
==========================================
        HOSPITAL ESTIMATE
==========================================
*/

hospitalEstimateBtn.addEventListener(

    "click",

    function(){

        window.open(

            FILE_API.GET_FILE +
            "/hospital-estimates/" +
            campaign.hospitalEstimate,

            "_blank"

        );

    }

);

/*
==========================================
        DONATE
==========================================
*/

donateButton.addEventListener(

    "click",

    async function(){

        const name = donorName.value.trim();

        const email = donorEmail.value.trim();

        const phone = donorPhoneNumber.value.trim();

        const amount = Number(donationAmount.value);

        if(name === ""){

            showWarningToast("Enter donor name.");

            return;

        }

        if(email === ""){

            showWarningToast("Enter email.");

            return;

        }

        if(phone === ""){

            showWarningToast("Enter phone number.");

            return;

        }

        if(amount <= 0){

            showWarningToast("Enter donation amount.");

            return;

        }

        try{

            const response = await fetch(

                DONATION_API.CREATE_ORDER(campaignId),

                {

                    method:"POST",

                    headers:getAuthorizationHeader(),

                    body:JSON.stringify({

                        donorName:name,

                        donorEmail:email,

                        donorPhoneNumber:phone,

                        amount:amount

                    })

                }

            );

            if(!response.ok){

                throw new Error(await response.text());

            }

            const order = await response.json();

            console.log(order);

            const options = {

                key:order.keyId,

                amount:order.amount,

                currency:order.currency,

                name:"LifeFund",

                description:"Donation",

                order_id:order.orderId,

                prefill:{

                    name:name,

                    email:email,

                    contact:phone

                },

                theme:{

                    color:"#E63946"

                },

                handler: async function(payment){

                    try{

                        const verifyResponse = await fetch(

                            DONATION_API.VERIFY_PAYMENT,

                            {

                                method:"POST",

                                headers:getAuthorizationHeader(),

                                body:JSON.stringify({

                                    campaignId:campaignId,

                                    donorName:name,

                                    donorEmail:email,

                                    donorPhoneNumber:phone,

                                    amount:amount,

                                    razorpayOrderId:payment.razorpay_order_id,

                                    razorpayPaymentId:payment.razorpay_payment_id,

                                    razorpaySignature:payment.razorpay_signature

                                })

                            }

                        );

                        if(!verifyResponse.ok){

                            throw new Error(await verifyResponse.text());

                        }

                        window.location.href =

                            `payment-success.html?patient=${encodeURIComponent(campaign.patientName)}&amount=${amount}`;

                    }catch(error){

                        console.error(error);

                        showErrorToast(

                            "Payment verification failed."

                        );

                    }

                }

            };

            const razorpay = new Razorpay(options);

            razorpay.open();

        }catch(error){

            console.error(error);

            showErrorToast(

                "Unable to create payment order."

            );

        }

    }

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

loadCampaign();