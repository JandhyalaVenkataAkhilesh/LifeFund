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

const greeting = document.getElementById("greeting");

const userName = document.getElementById("userName");

const campaignCount = document.getElementById("campaignCount");

const donorCount = document.getElementById("donorCount");

const raisedAmount = document.getElementById("raisedAmount");

const recentCampaigns = document.getElementById("recentCampaigns");

const logoutButton = document.getElementById("logoutButton");

/*
==========================================
        DYNAMIC GREETING
==========================================
*/

function getGreeting(){

    const hour = new Date().getHours();

    if(hour >= 5 && hour < 12){

        return "🌅 Good Morning";

    }

    if(hour >= 12 && hour < 17){

        return "☀️ Good Afternoon";

    }

    if(hour >= 17 && hour < 21){

        return "🌇 Good Evening";

    }

    return "🌙 Good Night";

}

/*
==========================================
    FORMAT INDIAN CURRENCY
==========================================
*/

function formatCurrency(amount){

    return Number(amount).toLocaleString("en-IN");

}

/*
==========================================
        LOAD DASHBOARD
==========================================
*/

async function loadDashboard(){

    try{

        const response = await fetch(
            DASHBOARD_API.GET_DASHBOARD,
            {
                headers:getAuthorizationHeader()
            }
        );

        if(!response.ok){

            throw new Error(await response.text());

        }

        const data = await response.json();

        greeting.innerHTML =
            `${getGreeting()}, ${data.userName} 👋`;

        userName.innerHTML =
            `<i class="fa-solid fa-user"></i> ${data.userName}`;

        campaignCount.textContent =
            data.totalCampaigns;

        donorCount.textContent =
            data.totalDonors;

        raisedAmount.textContent =
            "₹" + formatCurrency(data.totalAmountRaised);

    }catch(error){

        console.error(error);

        showErrorToast("Unable to load dashboard.");

    }

}

/*
==========================================
    LOAD RECENT CAMPAIGNS
==========================================
*/

async function loadRecentCampaigns(){

    try{

        const response = await fetch(

            CAMPAIGN_API.GET_ALL + "?page=0&size=3"

        );

        if(!response.ok){

            throw new Error(await response.text());

        }

        const data = await response.json();

        recentCampaigns.innerHTML = "";

        data.content.forEach(campaign=>{

            const percentage =
                Math.min(

                    (campaign.raisedAmount /
                    campaign.targetAmount) * 100,

                    100

                ).toFixed(0);

            recentCampaigns.innerHTML += `

<div class="campaign-card">

    <div class="campaign-image-section">

        <img
            src="${FILE_API.GET_FILE}/patient-photos/${campaign.patientPhoto}"
            class="campaign-image"
            alt="${campaign.patientName}">

    </div>

    <div class="campaign-details">

        <div class="campaign-header">

            <h3>

                ${campaign.patientName}

            </h3>

            <span class="campaign-status">

                🟢 Active

            </span>

        </div>

        <p class="info">

            🩺 <strong>Disease :</strong>
            ${campaign.diseaseName}

        </p>

        <p class="info">

            🏥 <strong>Hospital :</strong>
            ${campaign.hospitalName}

        </p>

        <p class="info">

            🎯 <strong>Goal :</strong>
            ₹${formatCurrency(campaign.targetAmount)}

        </p>

        <p class="info">

            💰 <strong>Raised :</strong>
            ₹${formatCurrency(campaign.raisedAmount)}

        </p>

        <div class="progress">

            <div
                class="progress-bar"
                style="width:${percentage}%">

            </div>

        </div>

        <div class="campaign-footer">

            <span class="donor-count">

                ❤️ ${campaign.donorCount} Donors

            </span>

            <button
                class="btn btn-primary view-btn"
                onclick="viewDetails(${campaign.campaignId})">

                View Details

            </button>

        </div>

    </div>

</div>

`;

        });

    }catch(error){

        console.error(error);

        showErrorToast("Unable to load campaigns.");

    }

}

/*
==========================================
        VIEW DETAILS
==========================================
*/

function viewDetails(id){

    window.location.href =
        `campaign-details.html?id=${id}`;

}

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

loadDashboard();

loadRecentCampaigns();