---
permalink: SSO tehniline spetsifikatsioon
---

# Riikliku SSO tehniline spetsifikatsioon ja protokolli kirjeldus

Analüüsi käigus koostati TARA SSO OpenID Connect profiili tehniline spetsifikatsioon. Spetsifikatsioon kirjeldab päringute struktuuri ja kohustuslikud päringu kontrolli reeglid.
Ühtlasi tuuakse välja TARA SSO spetsiifilised protokolli täiendused.

<p style='text-align:left;'><img src='img/EL_logo.jpg' style='width:250px'></p>

- TOC
{:toc}

## Overview

This document describes the technical characteristics of the TARA single-sign-on (SSO) service protocol and includes advice for implementing client application interfaces. TARA SSO protocol was designed following the best practices of OpenID Connect protocol and is heavily influenced of the current TARA service protocol (References: TARA). As a result, a large part of this document is identical to TARA service technical specification.

TARA SSO service will provide the same authentication means and similar flow as provided by TARA service. The actual authentication of users is still done in TARA service. TARA SSO will add an authentication session management functionality on top of the existing service. For the client application perspective TARA SSO will be a separate OIDC provider next to the existing TARA service.

The terminology in this document follows the terminology used in TARA technical specification (References: TARA).

## OpenID Connect compliance

TARA SSO protocol has been designed to follow the OpenID Connect protocol standard as closely as possible. The intent is to create a protocol that is compatible with a wide range of OpenID Foundation certified OIDC client applications and standard libraries. OIDC is by itself a very diverse protocol, providing multiple different workflows for a wide array of use-cases. To limit the scope of the development required to implement TARA SSO service and client applications, the full scope of OIDC protocol will not be supported. This chapter describes the main customizations that are done to TARA SSO as compared to the full OIDC protocol:

- The service supports only the authorization code flow of OIDC. The authorization code flow is deemed the most secure option and is thus appropriate for public services.
- All information about an authenticated user is transferred to the application in an ID token.
- The eIDAS assurance level is also transferred to the application (in the `acr` claim, using custom claim values `high`, `substantial`, `low`).
- The requested minimum authentication level of assurance is selected by the client application in the beginning of TARA SSO session (on initial authentication).
- Available authentication methods are provided based on the requested minimum authentication level of assurance.
- TARA SSO supports only a single default scope that will return person authentication data: given name, family name, date_of_birth, email, person identifier. The - scope remains the same during the entire TARA SSO authentication session.
- Single-sign-on (SSO) is supported. Client applications are expected to perform session status checks to keep the authentication session alive.
- Central logout is supported according to OIDC Back-Channel logout specification (References, OIDC-BACK).
- Client applications must always prove knowledge of previous identity token to check session status or end session in TARA SSO. Different from OIDC standard protocol, the `id_token_hint` parameter is usually described as a mandatory parameter in TARA SSO requests.

## SSO session

TARA SSO will create an SSO session for each successful user authentication and stores it in its internal session storage. The SSO session is used to store user personal information and various parameters about the authentication request (for example authentication time, authentication level of assurance, authentication method used). The authentication information and parameters are not allowed to change during the authentication session.

The SSO session is linked to the user agent via a session cookie. The user is allowed to create a single SSO session per user agent but may create concurrent sessions across multiple user agents.

SSO session has a limited validity period of 15 minutes. During the SSO session validity period the user is not required to re-authenticate themselves while logging into different client applications. Instead they are given the option of using the authentication information stored in SSO session. TARA SSO issues identity tokens as proof of an active SSO session. The identity token contains the user personal information as well as the session information. All issued identity tokens are linked to an SSO session via a session id (`sid`) claim and have the same validity period as the SSO session.

The SSO session validity period is extended every time a new client application authentication request is received, or an existing authenticated client application performs an SSO session update request. Every time an SSO session update request occurs, the session validity period is updated to `currentTime + 15 minutes`. This means that all client applications are contributing to keep the SSO session valid for the duration the user is still using the client applications.

SSO session expires when no new authentication requests or session update requests have been received in the last 15 minutes. This is a safety feature assuring that user SSO session is not kept alive too long after the user has finished using the client applications.

The SSO session may also be terminated before the end of its expiry time by the user. When the user initiates a logout from one client application the client application must inform TARA SSO of the logout event. The user is then given an option to terminate the SSO session and log out of all client applications related to the same SSO session.

## TARA SSO process flows

### Authentication process

In order to log a user into a client application, the client application must acquire an identity token from TARA SSO. The identity token will contain relevant user personal information as well as information of the SSO session. If the SSO session does not exist prior to the authentication request, a new session is created. If the session already exists but its level of assurance is lower than requested in the new request, then the previous SSO session will be terminated and a new SSO session will be created.

<p style='text-align:left;'><img src='img/tara_sso_tech_auth_flow.png' style='width:1000px'></p>


