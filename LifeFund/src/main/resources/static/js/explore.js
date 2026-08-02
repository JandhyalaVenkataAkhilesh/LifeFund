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

const searchInput = document.getElementById("searchInput");

const clearSearch = document.getElementById("clearSearch");

const campaignList = document.getElementById("campaignList");

const campaignCountText = document.getElementById("campaignCountText");

const pagination = document.getElementById("pagination");

/*
==========================================
            VARIABLES
==========================================
*/

let currentPage = 0;

const pageSize = 6;

let searchText = "";

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
        LOAD CAMPAIGNS
==========================================
*/

async function loadCampaigns(page = 0){

    try{

        currentPage = page;

        let url =
            CAMPAIGN_API.GET_ALL +
            `?page=${page}&size=${pageSize}`;

        if(searchText.trim() !== ""){

            url +=
                `&search=${encodeURIComponent(searchText)}`;

        }

        const response = await fetch(url);

        if(!response.ok){

            throw new Error(await response.text());

        }

        const data = await response.json();

        renderCampaigns(data);

        renderPagination(data);

    }catch(error){

        console.error(error);

        showErrorToast(
            "Unable to load campaigns."
        );

    }

}

/*
==========================================
        RENDER CAMPAIGNS
==========================================
*/

function renderCampaigns(data){

    campaignList.innerHTML = "";

    const start =
        data.number * pageSize + 1;

    const end =
        start + data.numberOfElements - 1;

    campaignCountText.innerHTML =

        `Showing ${start}-${end} of ${data.totalElements} campaigns`;

    data.content.forEach(campaign=>{

        const percentage = Math.min(

            (campaign.raisedAmount /
            campaign.targetAmount) * 100,

            100

        ).toFixed(0);

        campaignList.innerHTML += `

<div class="campaign-card">

<img
src="${FILE_API.GET_FILE}/patient-photos/${campaign.patientPhoto}"
class="campaign-image">

<div class="campaign-details">

<div class="campaign-header">

<h3>

${campaign.patientName}

</h3>

<span class="status">

🟢 Active

</span>

</div>

<p class="info">

🩺 ${campaign.diseaseName}

</p>

<p class="info">

🏥 ${campaign.hospitalName}

</p>

<p class="info">

🎯 Goal :
₹${formatCurrency(campaign.targetAmount)}

</p>

<p class="info">

💰 Raised :
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

<div class="action-buttons">

<button
class="btn view-btn"
onclick="viewDetails(${campaign.campaignId})">

View Details

</button>

<button
class="btn donate-btn"
onclick="donate(${campaign.campaignId})">

Donate

</button>

</div>

</div>

</div>

</div>

`;

    });

}

/*
==========================================
        PAGINATION
==========================================
*/

function renderPagination(data){

    pagination.innerHTML = "";

    const previous = document.createElement("button");

    previous.className = "nav-btn";

    previous.innerHTML = "◀";

    previous.disabled = data.first;

    previous.onclick = ()=>loadCampaigns(currentPage-1);

    pagination.appendChild(previous);

    for(let i=0;i<data.totalPages;i++){

        const button = document.createElement("button");

        button.className = "page-btn";

        if(i===currentPage){

            button.classList.add("active");

        }

        button.innerHTML = i+1;

        button.onclick = ()=>loadCampaigns(i);

        pagination.appendChild(button);

    }

    const next = document.createElement("button");

    next.className = "nav-btn";

    next.innerHTML = "▶";

    next.disabled = data.last;

    next.onclick = ()=>loadCampaigns(currentPage+1);

    pagination.appendChild(next);

}

/*
==========================================
        SEARCH
==========================================
*/

searchInput.addEventListener(

    "input",

    function(){

        searchText = this.value;

        clearSearch.style.display =
            searchText ? "block" : "none";

        loadCampaigns(0);

    }

);

/*
==========================================
        CLEAR SEARCH
==========================================
*/

clearSearch.addEventListener(

    "click",

    function(){

        searchInput.value = "";

        searchText = "";

        clearSearch.style.display = "none";

        loadCampaigns(0);

    }

);

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
        DONATE
==========================================
*/

function donate(id){

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

loadUser();

loadCampaigns();