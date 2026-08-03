/*
==========================================
            LifeFund API Configuration
==========================================
*/

const BASE_URL = "";

/*
==========================================
            Authentication APIs
==========================================
*/

const AUTH_API = {

    LOGIN: BASE_URL + "/auth/login",

    SIGNUP: BASE_URL + "/auth/signup",

    GOOGLE_LOGIN: BASE_URL + "/oauth2/authorization/google"

};

/*
==========================================
            Dashboard APIs
==========================================
*/

const DASHBOARD_API = {

    GET_DASHBOARD: BASE_URL + "/dashboard"

};

/*
==========================================
            Campaign APIs
==========================================
*/

const CAMPAIGN_API = {

    GET_ALL: BASE_URL + "/campaigns",

    GET_BY_ID: (campaignId) =>
        BASE_URL + "/campaigns/" + campaignId,

    SEARCH: BASE_URL + "/campaigns",

    CREATE: BASE_URL + "/campaigns"

};

/*
==========================================
            Donation APIs
==========================================
*/

const DONATION_API = {

    CREATE_ORDER: (campaignId) =>
        BASE_URL + "/campaigns/" + campaignId + "/donate",

    VERIFY_PAYMENT:
        BASE_URL + "/campaigns/payment/verify"

};

/*
==========================================
            File APIs
==========================================
*/

const FILE_API = {

    GET_FILE:
        BASE_URL + "/files"

};