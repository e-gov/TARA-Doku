---
permalink: Testing
---

# Testing
{: .no_toc}

- TOC
{:toc}

## 1 Overview

This page holds guidelines for TARA clients to ensure a secure and working integration with TARA.

The information contained here focuses on testing integration with TARA from the point of view of the client application.

If you are a private sector client, then additionally please consider [private sector client limitations and specifics](TechnicalSpecification#9-private-sector-client-specifications).

If you need further advice than present in TARA documentation or wish to report a possible bug, please send an e-mail to [help@ria.ee](mailto:help@ria.ee).

## 2 Prerequisites

In order to perform testing, the integrating party has to first [submit an application to join the TARA test environment](Liitumine).

The integrator should beforehand acquaint with TARA [technical specifications](TechnicalSpecification).

It is also important to be aware of different authentication methods and test accounts for [ID-card](https://www.id.ee/en/article/service-testing-general-information/), [Mobile-ID](https://www.id.ee/en/article/mobile-id-testing-2/), [Smart-ID](https://github.com/SK-EID/smart-id-documentation/wiki/Environment-technical-parameters#test-accounts-for-automated-testing) and [eIDAS](#33-adjusting-level-of-assurance).

### 2.1 Quick reference of testing accounts

The table below holds information on test accounts for quick access.

| Authentication method | Login info                                                                                                                                                                                         |
|-----------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Mobile-ID             | **ID code:** 60001017869, **Number:** 68000769                                                                                                                                                     |
| Smart-ID              | **ID code:** 30303039914                                                                                                                                                                           |
| EU eID (eIDAS)        | Option 1: Choose **Czech Republic** from the drop-down menu. Press `Continue`. You will be redirected to the Czech Republic demo authentication service. Select **Testovací profily** and press `Přihlásit`.
|                       | Option 2: Choose **Sweden** from the drop-down menu. Press `Continue`. You will be redirected to the Swedish demo authentication service. Select **Sweden Connect Reference IdP**. Select person to authenticate as **Ulla Alm** and press `Authenticate` and then `Approve`.
|                       | Option 3: Choose **Malta** from the drop-down menu. Press `Continue`. You will be redirected to the Maltese demo authentication service. Sign in using 2FA test account with ID number **0367882M** and password **6Kucerkrq** and press `Continue`. For OTP code use this [link](https://stgdemosp.gov.mt/totp/index.html?secret=g43diqkcgi3eknjyhfatmnseguyuimzr).

## 3 Testing

Once your client application has been integrated with test TARA and you have familiarized yourself with other prerequisites, you are ready to start the testing process.

Please note that the processes described below include both workflows through the user interface and backend processes.

The backend processes mainly include processes dealing with ID tokens and security checks.

For backend processes the integrator should ensure conformity through static testing, code reviews and unit tests.

NB! test and production TARA environments **must not** be used for performance or load testing.

### 3.1 Authentication

The authentication process begins by making an [authentication request to TARA](TechnicalSpecification#41-authentication-request).

The integrator should ensure that a user:

- can begin authentication (i.e. user is taken to TARA UI)
- is directed back to client application after authentication

In order to allow a user to log in to the client application, it is mandatory for the client to first verify the ID token.

Compulsory verification includes verifying:

- signature
- issuer
- addressee
- validity
- eIDAS level of assurance

For a more detailed overview of security operations please refer to the [technical specifications page](TechnicalSpecification#5-security-operations).

Please note that the client application is allowed to use the ID token only if **all** security checks pass.

If all security checks pass, only then can the client application consider the ID token valid.

The integrator should then test that the user can indeed access the client application.

### 3.2 Limiting means of authentication

If an integrator wishes to limit or specify the means of authentication, then this can be done with the `scope` parameter in the [authentication request](TechnicalSpecification#41-authentication-request).

If this possibility is used, the integrator should test that the authentication request is formed correctly and the user is indeed shown only the specified authentication means.

When using selective means of authentication, [`amr` validation](TechnicalSpecification#516-verifying-the-authentication-method-used-in-authentication) must be included in the ID token verification step.

### 3.3 Adjusting level of assurance

eIDAS level of assurance (LoA) refers to [the level of reliability assigned to a means of authentication](TechnicalSpecification#6-eidas-levels-of-assurance).

In an authentication request to TARA this value is by default `substantial`. If the integrator wishes to increase or decrease the LoA, then the authentication request should include the `acr_values` parameter with the corresponding value.

During ID token validation, [the `acr` claim must be compared against the requested LoA](TechnicalSpecification#517-verifying-the-eidas-level-of-assurance). If the value is lower than requested, ID token validation must fail.

If the integrator specifies `high` as the allowed LoA and wishes to try authentication by means with a lower LoA, then this can be done through eIDAS cross-border authentication:

1) In TARA, choose `EU eID` as the authentication method and select `Czech Republic` from the dropdown menu;
2) Press `Continue` and you will be redirected to the Czech Republic authentication test environment;
3) Choose `Test Profile` under the `Testing Profiles` section (this profile has `substantial` LoA) and `Log in`;
4) The authentication will not succeed due to a lower LoA than requested, and you will encounter an error in TARA;

### 3.4 Redirecting to specified eIDAS country authentication service

If the client application allows eIDAS cross-border authentication, the integrator can make use of the [`eidas:country:xx` scope to skip TARA UI](TechnicalSpecification#9-private-sector-client-specifications) and redirect the user straight to the specified country's authentication service.

If this possibility is used, the integrator should test that the authentication request is well-formed and the user is indeed redirected to the specified country's service.

### 3.5 State and nonce parameters

TARA uses the compulsory `state` and optional `nonce` parameters to protect against [false request attacks](TechnicalSpecification#52-protection-against-false-request-attacks).

The integrator must ensure the following:
- `state` and `nonce` parameters are created according to the standard described in technical specification. The minimum length requirement of the `state` parameter is 8 characters. However, we advise to generate at least a 16 byte random string for the `state` and `nonce` parameters;
- The client application conducts a `state` parameter check on callback request from GovSSO. If the validation fails, the client side authentication must also fail.
- If the optional `nonce` parameter is used, then `nonce` validation is incorporated into ID token validation.

### 3.6 Error response handling

Errors can occur for various reasons, and it is important for the integrator to handle these cases with care.

Please refer to [redirect request subsection in technical specifications](TechnicalSpecification#42-redirect-request) for a more detailed overview of error causes for different requests.

The integrator has to ensure that if an error is returned and user is redirected back to the client application, then the application displays the user proper information.

### 3.7 Public signature key identifier usage

The public signature key (`kid`) is used for JWT [signature verification](TechnicalSpecification#511-verifying-the-signature) and can be obtained from the public signature key [endpoint](TechnicalSpecification#7-endpoints-and-timeouts).

The integrator should ensure that the `kid` value is not hardcoded on the client application side. If the key is hardcoded and should change, then client application users will be unable to log in.

It is recommended to buffer the key on the client side. If JWT validation fails due to a key change, then the client application should check the public signature key endpoint. If there is a new `kid`, the client application should buffer the new value and revalidate the JWT.

### 3.8 User language preference

TARA supports Estonian, English and Russian. For better user experience, it is advised to add the `ui_locales` parameter with the [authentication request](TechnicalSpecification#41-authentication-request).

When using the parameter, the integrator should test that TARA is displayed in the language submitted with the authentication request.

### 3.9 Browsers and devices

The integrator should test whether the client application works with TARA with a combination of browsers and devices supported by the client.
