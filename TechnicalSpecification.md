---
permalink: TechnicalSpecification
---

# Technical specification
{: .no_toc}
v 1.20, 17.02.2025

- TOC
{:toc}

## 1 Overview

This document describes the technical characteristics of the TARA authentication service and includes advice for interfacing the client application with e-services.

The TARA authentication service is a service provided by the Information System Authority of the Republic of Estonia which can be used by institutions to add the support of various different authentication methods to its e-service:

- ID-card
- Smart-ID
- Mobile-ID
- EU eID (cross-border authentication through eIDAS infrastructure)


This technical specification is targeted for mainly software developers. The reader should be familiar with the HTTP protocol. Experiences with OpenID Connect or OAuth 2.0 would be beneficial but are not necessary. The reader should be prepared to obtain further information from the original text of the OpenID Connect protocol, if necessary.

We have attempted to use uniform terminology throughout the technical specification. Definitions can be found from the vocabulary and references. It should be kept in mind that the systems of concepts of OpenID Connect, OAuth 2.0, etc. have not been perfectly homogenised. For example, the information system of the institution offering an e-service which is interfaced with TARA is referred to as ‘the client application’ in this document. In OAuth and some other contexts, however, the client application is referred to as ‘the service provider’.

### 1.1 OpenID Connect

The TARA authentication service is based on the OpenID Connect protocol ([References](Viited), [Core]), which is in turn based on the OAuth 2.0 protocol. OpenID Connect and OAuth 2.0 are extensive standards with numerous possibilities.

For TARA, limited subset from standards was chosen and some adjustments were made. The main selections and adjustments compared to the full OpenID Connect protocol are the following:

- The service supports the authorisation code flow. The authorisation code flow is deemed the most secure option and is thus appropriate for public services.
- All information about an authenticated user is transferred to the application in an ID token.
- The eIDAS assurance level is also transferred to the application if it is known (in the `acr` statement).
- The service supports using user's language preference by including `ui_locales` parameter in the authentication request.
- The authentication method is selected by the user in the authentication service or by the interfaced client with the `scope` parameter.
- Cross-border authentication based on the technical specification of eIDAS.
- Dynamic registration of the client application is not supported. The client application is registered in RIA by a separate procedure.
- Single sign-on (SSO) and _session management_ are not supported. Use State Single Sign-On (GovSSO) service for single sign-on functionality, find the documentation [here](https://e-gov.github.io/GOVSSO/).

Based on feedback of users the TARA functionality may expand.

### 1.2 National and cross-border authentication

TARA enables national as well as cross-border authentication. This means that users of the Estonian e-identification system (ID-card, Mobile-ID, etc.) as well as users of the e-identification systems of other EU member states can be authenticated.

In the context of eIDAS, TARA is providing the ‘Authentication of an Estonian in an Estonian e-service’ and the ‘Authentication of a foreigner using an Estonian e-service’ application flows (Figure 1). When using TARA to authenticate foreigners, it is possible to [skip TARA‘s user interface and direct the client straight to the foreign state‘s authentication system](https://e-gov.github.io/TARA-Doku/TechnicalSpecification#414-selective-use-of-means-of-authentication).

<p style='text-align:left;'><img src='img/YLEVAADE_EN.png' style='width:600px'></p>

#### Figure 1. National and cross-border authentication

## 2 Authentication process from the user’s perspective

1 The user uses a client application providing an e-service

- the user may be Estonian or a foreigner;
- a screen with a ‘Log in’ or an equivalent button is displayed to the user;
- the user clicks ‘Log in’.

2 The client application redirects the user to the TARA service (by browser redirection)

- authentication request in the redirection URL.

3 The authentication method selection screen is displayed to the user. Here, the user can:

- choose authentication by ID-card (step 4)
- choose authentication by Mobile-ID (step 5)
- choose authentication by Smart-ID (step 6)
- choose authentication by EU eID (cross-border) (step 7)
- return to the client application.

4 ID-card authentication

- information about the authentication certificate is displayed to the user (it may be required to select correct certificate)
- the user enters PIN1
- in case of a successful authentication, move to step 8; in case of an error, to step 9.

5 Mobile-ID authentication

- the user enters their mobile phone number and personal identification code
- a verification code is displayed on the user’s mobile device
- waiting for confirmation
- in case of a successful authentication, move to step 8; in case of an error, to step 9.

6 Smart-ID authentication

- the user enters an Estonian personal identification code
- a verification code is displayed on the user’s mobile device
- waiting for confirmation
- in case of a successful authentication, move to step 8; in case of an error, to step 9.

7 EU eID (cross-border) authentication

- the user selects the target country
- the user is redirected to the authentication service of the foreign country through the eIDAS infrastructure
- the user authenticates themselves by using the means of authentication of the foreign country
- in case of a successful authentication (and if the level of the means of authentication of the foreign country is sufficient), move to step 8
- in case of an error, move to step 9.

NB! For EU eID authentication, it is possible to choose the target country in the interfaced client and specify it in the authentication request. This will allow the user to skip TARA user interface and be directed to the specified country's authentication service. If the authentication was successful and the means of authentication has a sufficient level of assurance, move to step 8.

8 Authenticated user

- is redirected back to the client application
- the client application requests the identity token from the TARA server
- the _identity token_ is a signed certificate confirming successful authentication
- the identity token includes the user’s data (attributes) which were identified in the course of authentication
- the client application notifies the user of successful login in an appropriate manner.

9 From the error message screen:

- the user can return to the selection of the authentication method
- and try again, by using a different authentication method, if desired
- or terminate the authentication process and return to the client application.

10 The user can also:

- obtain further information about the service of TARA.

## 3 Authentication flow from the technical perspective

Detailed description of the communication between the browser, the server component of the client application, and the server component of TARA.

These three parties communicate by HTTP requests and responses.

The main requests and the responses thereto are discussed (Figure 2).

<p style='text-align:center;'><img src='img/VOOG-01.PNG' style='width:400px'></p>

Figure 2. Authentication request

The flow begins from the browser. A page is loaded from the client application to the browser, where the user choose click ‘Log in’ or start the authentication process in another manner.

After the user selects ‘Log in’, the browser sends an HTTP request **1a** to the client application (to the server component of the client application). The client application may also launch the authentication process autonomously, as a result of some other operation performed by the user.

The client application creates an authentication request. The composition of the authentication request is described in a separate section below. The client application sends a response body **1b** to the browser. The response body includes the HTTP redirection and the authentication request.

The browser completes the redirection by excerpting the authentication request from response body **1b** and sending it to the server component of TARA as **2a** request.

Having received an authentication request **2a**, the server component of TARA generates the page of authentication methods and transfers it to the browser as response body **2b**.

The page of authentication methods is displayed to the user. The flow is described further in Figure 3.

<p style='text-align:center;'><img src='img/VOOG-02.PNG' style='width:400px'></p>

Figure 3. Redirect request

The user chooses an authentication method. The selection is transferred to the server component of TARA by an HTTP request **3a**.

This is followed by an authentication dialogue based on the authentication method selected by the user. In the authentication dialogue, several messages may be exchanged between the browser and the server component of TARA and several redirections may be completed. For example, in the case of cross-border authentication, the user is redirected several times to reach an authentication service of a foreign country. These requests and responses are referred to as “…” in the Figure.

The authentication dialogue terminates and the user must be redirected back to the client application.

The server component of TARA sends an HTTP response body **3b** to the browser which includes a redirection order for redirecting the user to the client application.

The browser completes the redirection order **3b** by sending an HTTP request **4a** to the server component of the client application (redirect request).

If the authentication was successful authorization code is returned with the request. The redirect request is described in detail in a separate section below.

An authorisation code is used to retrieve the identification code, name and other personal data by sending a separate request to the server component of TARA (Figure 4).

<p style='text-align:center;'><img src='img/VOOG-03.PNG' style='width:400px'></p>

Figure 4. Identity token request

The server component of the client application sends an identity token request **5a** to the server component of TARA. In the identity token request, the client application provides the authorisation code received in the redirect request. The client application proves its authenticity by adding a client secret code to the request. The identity token request is a backend request that is not sent through the browser.

Having received an identity token **5a**, the server component of TARA verifies the client secret code and issues the identity token **5b** in the response. The identity token includes information about the fact of authentication (time of authentication, authentication method) and about the person identified (personal identification code, first name and surname; in the case of cross-border authentication, also date of birth and other data). The server component of TARA signs the identity token. The identity token is described in detail in a separate section below.

The client application receives the identity token **5b**. In order to prevent attacks, the client application must verify whether the identity token was actually issued by TARA, intended for the client application, and not expired.

Thereby, the authentication is completed. The user has now been identified in the client application.

In most cases, the client application then launches a session with the user. Launching a session is not included in the scope of TARA.

The client application sends an HTTP response **4b** to the browser, such as the ‘Logged in’ page.

## 4 Requests

### 4.1 Authentication request

An authentication request is an HTTP GET request by which the user is redirected from the client application to TARA for authentication.

An example of an authentication request (for better readability, the query component of the URL was divided over several lines):

```
GET https://tara.ria.ee/oidc/authorize?

redirect_uri=https%3A%2F%2Feteenindus.asutus.ee%2FCallback&
scope=openid&
state=hkMVY7vjuN7xyLl5&
response_type=code&
client_id=58e7ba35aab5b4f1671a
```

Elements of an authentication request:

| URL element               | compulsory | example                                                                           | explanation                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
|---------------------------|------------|-----------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| protocol, host, and patch | yes        | `https://tara.ria.ee/oidc/authorize`                                              | `/authorize` is the OpenID Connect-based authentication endpoint of the TARA service (the concept of ‘authorisation’ originates from the OAuth 2.0 standard protocol).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| `redirect_uri`            | yes        | `redirect_uri=https%3A%2F%2F eteenus.asutus.ee%2Ftagasi`                          | Redirect URL. The redirect URL is selected by the institution. The redirect URL may include the query component. <br><br> [URL encoding](https://en.wikipedia.org/wiki/Percent-encoding) should be used, if necessary.<br><br>It is [not permitted](https://tools.ietf.org/html/rfc6749#section-3.1.2) to use the URI [fragment component](https://tools.ietf.org/html/rfc3986#section-3.5) (`#` and the following component).                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| `scope`                   | yes        | `scope=openid`<br><br>`scope=openid%20eidas`<br><br>`scope=openid%20idcard%20mid` | The authentication scope.<br><br>`openid` is compulsory (required by the OpenID Connect protocol).<br><br> The scopes of `idcard`, `mid`, `smartid`, `eidas` (and `eidasonly`) can be used to request that only the desired method of authentication is displayed to the user. See 4.1.4 Selective use of authentication methods.<br><br>The `email` scope can be used to request that the user’s e-mail address is issued in the identity token. See 4.1.2 Requesting e-mail address.<br><br>In case of cross-border authentication, further scopes can be used to either specify the foreign country and direct the user to its authentication service or to request additional personal data (see 4.1.4 and 4.1.1).<br><br>When using several scopes, the scopes must be separated by spaces. Thereat, the space is presented in the URL encoding (`%20`) ([RFC 3986](https://www.ietf.org/rfc/rfc3986.txt)). Scope values are case sensitive. Only scope values described in current document are allowed, other values cause an error with code `invalid_scope` to be returned. |
| `state`                   | yes        | `state=hkMVY7vjuN7xyLl5`                                                          | Security code against false request attacks (_cross-site request forgery_, CSRF). Read more about formation and verification of `state` under ‘Protection against false request attacks.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| `response_type`           | yes        | `response_type=code`                                                              | Determines the manner of communication of the authentication result to the server. The method of authorisation code is supported (_authorization flow_ of the OpenID Connect protocol) and it is referred to the `code` value.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| `client_id`               | yes        | `client_id=58e7...`                                                               | Application identifier. The application identifier is issued to the institution by RIA upon registration of the client application as a user of the authentication service.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| `ui_locales`              | no         | `ui_locales=et`                                                                   | Selection of the user interface language. The following languages are supported: `et`, `en`, `ru`.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| `nonce`                   | no         | `fsdsfwrerhtry3qeewq`                                                             | A unique parameter which helps to prevent replay attacks based on the protocol ([References](Viited), [Core], subsection 3.1.2.1. Authentication Request). The `nonce` parameter is not compulsory.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| `acr_values`              | no         | `acr_values=high`                                                                 | The minimum required level of authentication based on the eIDAS LoA ([read more about eIDAS level-of-assurance](TechnicalSpecification#8-eidas-levels-of-assurance)). It is permitted to apply only one of the following values: `low`, `substantial`, `high` . `substantial` is used by default if the value has not been specified. <br><br>TARA will only display authentication methods which have an equal or higher LoA compared to the value of `acr_values` in the authentication request. For cross-border authentication, the value is forwarded to the foreign country's eIDAS authentication service. <br><br> On the interfaced client side, [it must be verified](https://e-gov.github.io/TARA-Doku/TechnicalSpecification#517-verifying-the-eidas-level-of-assurance) that the authentication level in the `acr` claim of an identity token is not lower than the minimum allowed level-of-assurance.                                                                                                                                |

#### 4.1.1 Requesting attributes about foreigners

In case of authentication of a foreigner, the foreigner is redirected by TARA to the authentication service of their country.

By a regulation of the European Commission, member states have agreed that another country’s authentication service must always issue four attributes of a natural person: 1) first name; 2) surname; 3) date of birth; 4) personal identification code or another identifier.

In case of natural person authentication, TARA always returns **the compulsory** attributes: first name, surname, date of birth and personal identifier. 

At the moment, TARA does not support requesting additional attributes nor authenticating legal persons.

#### 4.1.2 Requesting e-mail address

The `email` scope can be used to request the user’s e-mail address in the identity token. This option is targeted for the client applications that require verification of an e-mail address in authentication of a user. The `email` scope must be added to the main scope `openid`. The claims `email` and `email_verified` are issued in the identity token. For example:

```
"sub": "EE60001019906",
"email": "60001019906@eesti.ee",
"email_verified": false
```

The `email` value is read from an extension of the user’s authentication certificate (from the RFC822 type Subject Alternative Name field). The e-mail address is only issued if the user is authenticated by an Estonian ID-card. The client application must take into consideration that the user may not have redirected their e-mail, i.e. an e-mail sent to this address may not reach the user.

The `email_verified` is always `false`. It means that TARA does not verify or issue information on whether the user has redirected their eesti.ee e-mail address. (The respective functionality may be added in the future).

#### 4.1.3 Requesting phone number

The `phone` scope can be used to request the user’s phone number in the identity token. This option is targeted for client applications that use Mobile-ID, which requires user's phone number for input, as a means for giving digital signatures. The `phone` scope must be added to the main scope `openid`. The claims `phone` and `phone_number_verified` are issued in the identity token. For example:

```
"sub": "EE60001019906",
"phone_number": "+37200000766",
"phone_number_verified": true
```

The value of `phone_number` claim is in E.164 format and prefixed by a country code.

The value of `phone_number_verified` claim is always `true`. This means that the ownership of the phone number has been verified during the authentication process, i.e. the phone number belongs to the authenticating user.

The claims `phone_number` and `phone_number_verified` can only be issued if Mobile-ID was used for authentication.

#### 4.1.4 Selective use of means of authentication

By default, all supported authentication methods are displayed to the user. If necessary, the authentication options displayed can be managed by using the `scope` parameter's value. Preferred authentication methods can be combined to draw up a list of the authentication methods (the list of permitted values is provided in Table 1).

When using the selective means of authentication the validation of `amr` claim must be performed in the identity token as an additional security method.

Table 1 – displaying the authentication methods

| Value of the scope parameter | Explanation                                                                                                                                                 |
|------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `idcard`                     | Allowing Estonian ID-card authentication                                                                                                                    |
| `mid`                        | Allowing Mobile-ID authentication                                                                                                                           |
| `smartid`                    | Allowing Smart-ID authentication                                                                                                                            |
| `eidas`                      | Allowing cross-border (eIDAS) authentication                                                                                                                |
| `eidasonly`                  | Allowing ONLY cross-border (eIDAS) authentication <br><br>NB! When `eidasonly` is used, all other preferred authentication methods will be always excluded. |
| `eidas:country:xx`           | Selects defined country for cross-border (eIDAS) authentication (skips the TARA country selection page). Can only be used together with `eidasonly` scope. Supported country codes can be retrieved from discovery endpoint. |

Example 1: All means of authentication
`scope=openid`

Example 2: Only ID-card and Mobile-ID
`scope=openid%20idcard%20mid`

Example 3: Only cross-border authentication
`scope=openid%20eidas`

Example 4: Cross-border (eIDAS) authentication without TARA country selection page. The country is selected by client application.
`scope=openid%20eidasonly%20eidas%3Acountry%3Abe`

### 4.2 Redirect request

The redirect request is an HTTP GET request which is used to redirect the user back from TARA to the client application.

The user is redirected to the return address included in the authentication request sent by the client application. In the redirect request, an authorization code is sent to the client application by TARA, based on which the client application will (by a separate request) request from TARA the personal identification code, name, and other attributes of the authenticated person. Technically, an HTTP redirect request is used for redirecting.

An example of a redirect request:

```
HTTP GET https://eteenus.asutus.ee/tagasi?
code=71ed5797c3d957817d31&
state=OFfVLKu0kNbJ2EZk
```

Elements of a redirect request:

| URL element              | example                            | description                                                                                                                                                                                                            |
|--------------------------|------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| protocol, host, and path | `https://eteenus.asutus.ee/tagasi` | Matches the `redirect_uri` value sent in the authentication request.                                                                                                                                                   |
| `code`                   | `code=71ed579...`                  | The authorisation code is a single ‘permission note’ to receive the identity token.                                                                                                                                    |
| `state`                  | `state=OFfVLKu0kNbJ2EZk`           | Security code against false request attacks. The security code received in the authentication request is mirrored back. Read more about forming and verifying `state` from ‘Protection against false request attacks’. |

Request might contain other URL parameters, that client application must ignore.

**Error message in the redirect request.** If TARA is unable to process an authentication request – there is an error in the request, another error has occurred or user canceled the process – TARA transfers an error message (URL parameter `error`) and the description of the error (URL parameter `error_description`) in the redirect request. 

If a user cancels authentication in TARA (’Return to service provider’ link on TARA's front page), a `user_cancel` error message is returned.

For other cases, TARA relies on the OpenID Connect standard on error messages (more information regarding the error messages can be found from https://openid.net/specs/openid-connect-core-1_0.html#AuthError and https://tools.ietf.org/html/rfc6749#section-4.1.2.1). The error messages are always displayed in English.

`state` is also redirected but no authorisation code (`code`) is sent. E.g.:

```
HTTP GET https://eteenus.asutus.ee/tagasi?
error=invalid_scope&
error_description=Required+scope+%3Copenid%3E+not+provided.+
TARA+do+not+allow+this+request+to+be+processed&
state=qnYY56Ra8QF7IUzqvw+PPLzMKoHtQkuUWbV/wcrkvdU=
```

The redirect request errors are normally resulted by a misconfiguration; therefore the error description in parameter `error_description` is not needed to be displayed for the user directly. The client application should check whether an error message was sent.

**Termination of the authentication process.** The user may also return to the e-service without choosing an authentication method and completing the authentication process (via ‘Return to service provider’ link). This option is provided for the cases in which the user has clicked ‘Log in’ in the client application but does not actually wish to log in. 

Terminating the authentication process will redirect the user back to the client with error message `user_cancel`<sup>1</sup>.

<sup>1</sup> Beginning from August 2020, Information System Authority does not require a separate redirect URL for terminated authentications (a URL for redirects after user has chosen ‘Return to service provider’ option). NB! The OpenID Connect protocol-based redirect URL and the URL described here have different meanings.

### 4.3 Identity token request

The identity token request is an HTTP POST request which is used by the client application to request an identity token from the TARA server.

By default, client applications must use the `client_secret_basic` client authentication method to acquire identity tokens. A client application may use the `client_secret_post` client authentication method instead, but this must be specified in client application registration. A client application must use only one authentication method - the methods cannot be used concurrently.

#### 4.3.1 Using client_secret_basic client authentication method

When using `client_secret_basic`, the request must include the `Authorization` request header with the value formed of the word `Basic`, a space, and a string `<form_urlencoded_client_id>:<form_urlencoded_client_secret>` encoded in the Base64 format. `form_urlencoded_client_id` represents `client_id` value encoded in the "application/x-www-form-urlencoded" format and `form_urlencoded_client_secret` represents `client_secret` value encoded in the "application/x-www-form-urlencoded" format (see [RFC 6749](https://www.rfc-editor.org/rfc/rfc6749.html#section-2.3.1)).

The body of the HTTP POST request must be presented in a serialised [format](https://openid.net/specs/openid-connect-core-1_0.html#FormSerialization) based on the OpenID Connect protocol.

An example of an identity token request using `client_secret_basic`:

```
POST /oidc/token HTTP/1.1
Host: tara.ria.ee
Content-Type: application/x-www-form-urlencoded
Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW

grant_type=authorization_code&
code=SplxlOBeZQQYbYS6WxSbIA&
redirect_uri=https%3A%2F%2eteenus.asutus.ee%2Ftagasi
```

The body of the request must include the following parameters:

| POST request body element | example | explanation |
|---------------------------|---------|-------------|
| `grant_type` | `grant_type=authorization_code` | The `authorization_code` value required based on the protocol. |
| `code` | `code=SplxlOBeZQQYbYS6WxSbIA` | The authorization code received from the authentication service. | 
| `redirect_uri` | `redirect_uri=https%3A%2F%2eteenus.asutus.ee%2Ftagasi` | The redirect URL sent in the authentication request. |

#### 4.3.2 Using client_secret_post client authentication method

When using `client_secret_post`, the client credentials are included directly in the request body as parameters instead of the `Authorization` header.

The body of the HTTP POST request must be presented in a serialised [format](https://openid.net/specs/openid-connect-core-1_0.html#FormSerialization) based on the OpenID Connect protocol.

An example of an identity token request using `client_secret_post`:

```
POST /oidc/token HTTP/1.1
Host: tara.ria.ee
Content-Type: application/x-www-form-urlencoded

grant_type=authorization_code&
code=SplxlOBeZQQYbYS6WxSbIA&
redirect_uri=https%3A%2F%2eteenus.asutus.ee%2Ftagasi&
client_id=<client_id>&
client_secret=<client_secret>
```

The body of the request must include the following parameters:

| POST request body element | example | explanation |
|---------------------------|---------|-------------|
| `grant_type` | `grant_type=authorization_code` | The `authorization_code` value required based on the protocol. |
| `code` | `code=SplxlOBeZQQYbYS6WxSbIA` | The authorization code received from the authentication service. | 
| `redirect_uri` | `redirect_uri=https%3A%2F%2eteenus.asutus.ee%2Ftagasi` | The redirect URL sent in the authentication request. |
| `client_id` | `client_id=de39d9dc-3c1b-4105-81ec-54a449c1ae36` | Client application's ID. |
| `client_secret` | `client_secret=t3fx3ehWoL9z6yzw` | Client application's secret. |

#### 4.3.3 Identity token

TARA server verifies that the identity token is requested by the right application and issues the identity token included in the response body (HTTP response body).

The response body uses JSON format consisting four elements (see the following table).

|    element     | explanation                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
|:--------------:|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `access_token` | OAuth 2.0 access token. With identity token the client application can issue authenticated user's data from user info endpoint.<br><br>Even though TARA issues the access token, we only advise to use it in case it is not possible to use the identity token (e.g when interfacing out-of-the-box products). All the data of authenticated user are already issued within the identity token. Using the identity token is recommended and, in theory, is considered to be more secure (as the identity token is signed, while the user info endpoint output is not). |
|  `token_type`  | OAuth 2.0 access token type with `bearer` value. Not used in TARA                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
|  `expires_in`  | The validity period of the OAuth 2.0 access token. Not used in TARA.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
|   `id_token`   | Identity token. Presented in [JWS Compact Serialization](https://tools.ietf.org/html/rfc7515#section-3.1) form.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           | 

Response body might contain other fields, that client application must ignore.

The identity token is a certificate of the fact of authentication issued by TARA.

The identity token is issued in [JSON Web Token](https://jwt.io/), JWT format.

The identity token is always [signed](https://tools.ietf.org/html/rfc7515#section-5.2).

An example:

```json
{
  "jti": "0c597356-3771-4315-a129-c7bc1f02a1b2",
  "iss": "https://tara-test.ria.ee",
  "aud": "TARA-Demo",
  "exp": 1530295852,
  "iat": 1530267052,
  "nbf": 1530267052,
  "sub": "EE60001019906",
  "profile_attributes": {
    "date_of_birth": "2000-01-01",
    "family_name": "O’CONNEŽ-ŠUSLIK TESTNUMBER",
    "given_name": "MARY ÄNN"
  },
  "amr": [
    "mID"
  ],
  "state": "1OnH3qwltWy81fKqcmjYTqnco9yVQ2gGZXws/DBLNvQ=",
  "nonce": "",
  "at_hash": "X0MVjwrmMQs/IBzfU2osvw=="
}
```

The following claims are presented in the identity token:

| identity token element (_claim_)                 | example of a value, explanation                                                                                                                                                                                                                                                            |
|--------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `jti` (_JSON Token Identifier_)                  | `0c597356... ` - identity token identifier                                                                                                                                                                                                                                                 |
| `iss` (_Issuer_)                                 | `https://tara.ria.ee` - issuer of the certificate (TARA); in the case of demo services `https://tara-test.ria.ee`                                                                                                                                                                          |
| `aud` (_Audience_)                               | `TARA-Demo` - the ID of a client application that requested authentication (the value of `client_id` field is specified upon directing the user to the authentication process).                                                                                                            |
| `exp` (_Expires_)                                | `1530295852` - the expiration time of the certificate (in Unix _epoch_ format).                                                                                                                                                                                                            |
| `iat` (_Issued At_)                              | `1530267052` - the time of issuance of the certificate (in Unix _epoch_ format).                                                                                                                                                                                                           |
| `nbf` (_Not Before_)                             | `1530267052` - same as `iat` value. This claim is returned to maintain backward compatibility with older TARA versions (it is not a standard OpenID Connect claim). It is recommended to use `iat` value instead. |
| `sub` (_Subject_)                                | `EE60001019906` - the identifier of the authenticated user (personal identification code or eIDAS identifier) with the prefix of the country code of the citizen (country codes based on the ISO 3166-1 alpha-2 standard). NB! eIDAS identifier length may be a maximum of 256 characters. |
| `profile_attributes`                             | the data of the authenticated user, including the eIDAS attributes                                                                                                                                                                                                                         |
| `profile_attributes`<br>`.date_of_birth`         | `2000-01-01` - the date of birth of the authenticated user in the ISO_8601 format. Only sent in the case of persons with Estonian personal identification code and in the case of eIDAS authentication.                                                                                    |
| `profile_attributes`<br>`.given_name`            | `MARY ÄNN` - the first name of the authenticated user (the test name was chosen because it consists special characters).                                                                                                                                                                   |
| `profile_attributes`<br>`.family_name`           | `O’CONNEŽ-ŠUSLIK` - the surname of the authenticated user (the test name was selected because it includes special characters).                                                                                                                                                             |
| `profile_attributes`<br>`_translit`              | Includes a JSON object consisting of profile attributes in the Latin alphabet (see the section on transliteration below). The value is present only in the case of eIDAS authentication.                                                                                                   |
| `amr` (_Authentication Method Reference_)        | `mID` - the authentication method used for user authentication. Possible values: `mID` - Mobile-ID, `idcard` - Estonian ID-card, `eIDAS` - cross-border, `smartid` - Smart-ID                                                                                                              |
| `state`                                          | `abcdefghijklmnop` - security element. The authentication request’s `state` parameter value. This claim is returned to maintain backward compatibility with older TARA versions (it is not a standard OpenID Connect claim). It is recommended to use `state` parameter of callback request instead. |
| `nonce`                                          | `qrstuvwxyzabcdef` - security element. The authentication request’s `nonce` parameter value. Value is present only in case the `nonce` parameter was sent in the authentication request.                                                                                                   |
| `acr` (_Authentication Context Class Reference_) | `high` - level of authentication based on the eIDAS LoA (level of assurance). Possible values: `low`, `substantial`, `high`. The element is not used if the level of authentication is not applicable or is unknown.                                                                       |
| `at_hash`                                        | `X0MVjwrmMQs/IBzfU2osvw==` - the access token hash. Even though TARA issues the access token, we only advise to use it (to receive the authenticated user’s data from the user info endpoint) in case it is not possible to use the identity token (e.g when interfacing out-of-the-box products). All the data of authenticated user are already issued within the identity token. Hash value is Base64 Standard encoded (standard specifies Base64 URL encoded). The non-standard encoding is used to ensure backward compatibility with older TARA versions. |
| `email`                                          | `60001019906@eesti.ee` - the user’s e-mail address. Only issued if an Estonian ID-card is used for authenticating the user. Is only read from the SAN extension of the user’s authentication certificate (from the RFC822 type `Subject Alternative Name` field)                           |
| `email_verified`                                 | `false` - the e-mail address of the user has been verified. TARA always issues a value `false`. It means that TARA does not verify or issue information on whether or not the user has redirected his/her eesti.ee e-mail address.                                                         |
| `phone_number`                                   | `+37200000766` - the user’s phone number. Issued only when authenticating with Estonian Mobile-ID service. The phone number is presented in E.164 format and prefixed by a country code.                                                                                                   |
| `phone_number_verified`                          | `true` - the ownership of the phone number to the authenticating user has been confirmed. If the claim is present, then it is always `true`                                                                                                                                                |

Identity token might consist of other OpenID Connect protocol based fields that are not supported in TARA.

The client application must obtain the identity token immediately or within `30` seconds (before the expiry time of the identity token).

### 4.4 User info request

User info request enables requesting information about an authenticated user based on a valid `OAuth 2.0` access token. Even though TARA issues the access token, we only advise to use it in case it is not possible to use the identity token (e.g when interfacing out-of-the-box products). All the data of authenticated user are already issued within the identity token. Using the identity token is recommended and, in theory, is considered to be more secure (as the identity token is signed, while the user info endpoint output is not).

The request must be done by using the HTTP GET method. The access token must be presented to the user info endpoint in the HTTP header or by using the [Bearer Token method](https://tools.ietf.org/html/rfc6750#section-2.1) or as a [URLi parameter](https://tools.ietf.org/html/rfc6750#section-2.3).

Example 1 – transferring an access token in the `Authorization` header:

```
GET /oidc/profile HTTP/1.1
Host: tara.ria.ee
Authorization: Bearer AT-20-qWuioSEtFhYVdW89JJ4yWvtI5SaNWep0
```

Example 2 – transferring of access token as an `access_token` parameter:

```
GET /oidc/profile?access_token=AT-20-qWuioSEtFhYVdW89JJ4yWvtI5SaNWep0 HTTP/1.1
Host: tara.ria.ee
```

The valid access token response is provided in the JSON format. Example:

```json
{
  "amr": [
    "mID"
  ],
  "date_of_birth": "2000-01-01",
  "family_name": "O’CONNEŽ-ŠUSLIK TESTNUMBER",
  "given_name": "MARY ÄNN",
  "sub": "EE60001019906",
  "auth_time": 1550735597
}
```

The claims included in the response are issued based on the identity token.

| json element (_claim_)  | disclosure is compulsory | explanation                                                                          | 
|:------------------------|--------------------------|--------------------------------------------------------------------------------------|
| `auth_time`             | yes                      | The same format and meaning as `iat` in identity token.                              |
| `sub` (_Subject_)       | yes                      | The same format and meaning as `sub` in identity token.                              |
| `given_name`            | yes                      | The same format and meaning as `profile_attributes.given_name` in identity token.    |
| `family_name`           | yes                      | The same format and meaning as `profile_attributes.family_name` in identity token.   |
| `amr`                   | yes                      | The same format and meaning as `amr` in identity token.                              |
| `date_of_birth`         | no <sup>1</sup>          | The same format and meaning as `profile_attributes.date_of_birth` in identity token. |
| `email`                 | no  <sup>1</sup>         | The same format and meaning as `email` in identity token.                            |
| `email_verified`        | no  <sup>1</sup>         | The same format and meaning as `email_verified` in identity token.                   |
| `phone_number`          | no  <sup>1</sup>         | The same format and meaning as `phone_number` in identity token.                     |
| `phone_number_verified` | no  <sup>1</sup>         | The same format and meaning as `phone_number_verified` in identity token.            |
| `acr`                   | no  <sup>1</sup>         | The same format and meaning as `acr` in identity token.                              |

 <sup>1</sup> Only issued if the claim is also included in the identity token.

Response body might contain other fields, that client application must ignore.

**Error handling**

In case the access token presented to the user information endpoint is missing or is expired, an error code and a brief description about the error are returned as `WWW-Authenticate` header according to the [OpenID Connect Core 1.0 specification](https://openid.net/specs/openid-connect-core-1_0.html#UserInfoError).

An example:

```
HTTP/1.1 401 Unauthorized
WWW-Authenticate: Bearer error="invalid_token",error_description="The access token has expired"
```

## 5 Security operations

### 5.1 Verification of the identity token

The client application must always verify the identity token. The client application must implement all the verifications based on OpenID Connect and OAuth 2.0 protocols.
{: .adv}

The verification must verify identity token’s:

- signature
- issuer
- addressee
- validity
- authentication method
- eIDAS level of assurance

For more detailed information about the identity token verifications can be found from OpenID Connect and OAuth 2.0 protocol specifications.

#### 5.1.1 Verifying the signature

The identity token is signed by the TARA authentication service. The signature meets the JWT standard.

TARA uses the `RS256` signature algorithm. The client application must, at least, be able to verify the signature given by using this algorithm. It would be reasonable to use a standard JWT library which supports all JWT algorithms. The change of algorithm is considered unlikely, but possible in case a security vulnerability is detected in the `RS256`.

For the signature verification, the public signature key of TARA must be used. The public signature key is published at the public signature key [endpoint](#6-endpoints-and-timeouts).

The public signature key is stable - the public signature key will be changed according to security recommendations. However, the key can be changed without prior notice for security reasons. The key change process is described in a separate [chapter](#54-id-token-signing-key-change).

The public signature key has an identifier (`kid`). The key identifier is aligned with the best practices of [OpenID Connect](https://openid.net/specs/openid-connect-core-1_0.html#Signing) and OAuth 2.0 that enables the key exchange without the service interruption.

TARA issues `kid` field in the response of the request of identity token (JWT header element `kid`). `kid` refers to the key that the client application has to use for the verification of a signature.

We recommend to buffer the signature key (together with `kid` value) in order to decrease the amount of requests made to TARA server. However, it is allowed to request the key on each validation.

**For signature validation the following checks need to be performed on the client application:**

1 - Read the `kid` value from the JWT header.

2.1 - If the client application does not buffer the public key, make a request to the public signature key endpoint and select the key corresponding to the `kid` value received from the JWT header.

2.2 - If the client application buffers the public key (it needs to be buffered together with the `kid` value), it needs to compare the `kid` value from the JWT header with the buffered `kid` value. If they match, the buffered key can be used. If not, the client application needs to make a request to the public signature key endpoint and select the key corresponding to the `kid` value received from the JWT header and buffer it together with the corresponding `kid` value. Alternatively, the client application can buffer all the published keys from the public signature key endpoint together with `kid` values and during validation select the matching key from the buffer. However, in that case, the buffer should have an expiration time. Recommended buffer expiration should be between 5 minutes to 24 hours.

3 - Validate the signature using the key corresponding to the `kid` value from the JWT header.

NB! "Hardcoding" the key to client application configuration must be avoided. The key change will be typically communicated (except of urgent security reasons), but manual key handling will result downtime in client application for the period when TARA is already using the new key until the new key is taken use in client application.

#### 5.1.2 Verifying the TLS connection to endpoints

When making requests from the client application to TARA (to all endpoints, i.e., server discovery endpoint, public signature key endpoint, token endpoint), all necessary checks must be correctly performed during the initiation of the TLS connection. It is recommended not to implement these checks yourself, but to use a library with HTTPS or TLS connection functionality.

The trust anchor for the TLS connection in the client application must be set to the following root certificates:
* [DigiCert Global Root G2](https://cacerts.digicert.com/DigiCertGlobalRootG2.crt.pem);
* [GlobalSign R4](https://pki.goog/repo/certs/gsr4.pem);
* [GTS Root R1](https://pki.goog/repo/certs/gtsr1.pem);
* [GTS Root R2](https://pki.goog/repo/certs/gtsr2.pem);
* [GTS Root R3](https://pki.goog/repo/certs/gtsr3.pem);
* [GTS Root R4](https://pki.goog/repo/certs/gtsr4.pem);
* [ISRG Root X1](https://letsencrypt.org/certs/isrgrootx1.pem);
* [ISRG Root X2](https://letsencrypt.org/certs/isrg-root-x2.pem).

It is not advisable to trust certificates from other CAs or the entire operating system's CA certificate store. Web browsers trust many CA certificates, but they use the Certificate Transparency mechanism to mitigate the unauthorized certificate issuance problem. TARA authentication flow is a combination of web browser requests and client application requests, therefore the union of checks from both applies for the authentication flow to completely succeed.

The HTTPS or TLS connection functionality library must perform the following checks for each connection initiation:
* check whether a certificate chain with valid signatures is formed, ending with any of the root certificates listed above;
* check whether the hostname in the request (`tara.ria.ee` or `tara-test.ria.ee`) matches the CN field in the server's presented certificate or is included in the SAN field;
* check the start and end validity values for all certificates in the chain;
* check the constraints defined in the certificates (basic, name, key usage, critical extensions).

Additionally, revocation of all certificates in the chain must be observed in any of the following ways:
* RIA notifies contact persons of integrators by e-mail about certificate revocation.
* Client application may check CA's validity confirmation service (OCSP) or revocation list (CRL), but must take into account that RIA is not responsible for the availability of CA's services as an external party. `tara.ria.ee` ja `tara-test.ria.ee` endpoints may, but are not obligated to provide OCSP stapling, or it may change without notice.

#### 5.1.2.1 TLS connection parameters

The client application must support the following for establishing TLS connections:

* TLS protocol version 1.2 and/or 1.3;
* Server Name Indication (SNI) extension;
* RSA and EC type certificates;
* the following cipher suites:
    * for TLS 1.2:
        * `TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256`;
        * `TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256`;
        * `TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384`;
        * `TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384`;
        * `TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256`;
        * `TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256`;
    * for TLS 1.3:
        * `TLS_AES_128_GCM_SHA256`;
        * `TLS_AES_256_GCM_SHA384`;
        * `TLS_CHACHA20_POLY1305_SHA256`.

#### 5.1.3 Verifying the issuer of the certificate

The `iss` value of the identity token element must be `https://tara-test.ria.ee` (for TARA demo environment) or `https://tara.ria.ee` (for TARA production environment).

#### 5.1.4 Verifying the addressee of the certificate

The client application must verify whether the certificate received was issued for them. For this purpose, it must be made sure that the `aud` value of the identity token element matches the client ID issued upon registration of the client application.

#### 5.1.5 Verifying the validity of the certificate

The verification is done using two elements in the identity token: `iat` and `exp`. The client application uses its own clock to verify the validity. The following details should be verified:

1) that "issued at" time has been reached:

`iat <= current_time + permitted_difference_between_clocks`

2) that the "expired" time has not been reached:

`exp > current_time - permitted_difference_between_clocks`

The identity token also includes the `nbf` ('not before') claim (which value is equal to `iat` claim). This element is present for backwards compatibility with older versions of TARA. The ID token validity check should be done based on `iat` claim and `nbf` can be ignored.

The application must choose the `permitted_difference_between_clocks` value, taking into consideration network latency.

The identity token must be obtained as soon as possible within 30 seconds. When the time limit is exceeded, the authorization code expires and identity token will not be issued.

#### 5.1.6 Verifying the authentication method used in authentication

When using the selective means of authentication (see section 4.1.4) the identity token must verify that the authentication method provided by the authentication method reference, `amr` ,is allowed. Otherwise, the risk of intermediary attacks is taken by allowing the user to authenticate through the method that is not acceptable in the interface (e.g. Smart-ID is used instead of authentication with an ID-card) through manipulation of the authentication request `scope` parameter.

For example, when in the authentication request the `scope` parameter is defined to use only ID-card authentication method, it must be verified that the `amr` claim also contains an `idcard` code (the full list of all codes is described under section 4.3.3).

#### 5.1.7 Verifying the eIDAS level of assurance

In order to prevent access with authentication tools with a lower security level, it must be verified that the authentication level in the `acr` claim of identity token is not lower than the minimum level of assurance allowed.

For example, if the client application wants to use only authentication methods with eIDAS level of assurance `high` and has specified the value in the `acr_values` parameter, then only the identity tokens with `acr_values` parameter with value `high` can be accepted.

In case the level of assurance in the authentication request using `acr_values` parameter is not specified, the identity token must be equal to a level of assurance `substantial` or `high`.

#### 5.1.8 Creating a session

After a successful verification of the identity token, the client application will create a session with the user (‘log in the user’). The client application is responsible for creating and holding the sessions. The methods for doing this are not included in the scope of the TARA authentication service.

### 5.2 Protection against false request attacks

The client application must implement protective measures against false request attacks (_cross-site request forgery_, CSRF). This can be achieved by using `state` and `nonce` security codes. Using `state` is compulsory; using `nonce` is optional. Using `state` with a cookie set on the client application side means that the client application itself does not have to remember the state parameter value.  The process is described below.

The `state` security code is used to combat falsification of the redirect request following the authentication request. The client application must perform the following steps:

1 Generate a nonce word, for example of the length of 16 characters: `XoD2LIie4KZRgmyc` (referred to as `R`).

2 Calculate from the `R` nonce word the `H = hash(R)`hash, for example by using the SHA256 hash algorithm and by converting the result to the Base64 format: `vCg0HahTdjiYZsI+yxsuhm/0BJNDgvVkT6BAFNU394A=`.

3 Add an order to set a cookie for the client application domain with a value of R immediately before making the authentication request, for example:

`Set-Cookie ETEENUS=XoD2LIie4KZRgmyc; HttpOnly`,

where `ETEENUS` is a freely selected cookie name. The `HttpOnly` attribute must be applied to the cookie.

4 Set the following value, in the authentication request to TARA, for the `state` parameter calculated based on section 2:

`GET ... state=vCg0HahTdjiYZsI+yxsuhm/0BJNDgvVkT6BAFNU394A=`

Length of `state` parameter must be minimally 8 characters.

In the course of processing the redirect request, the client application must:

5 Take the `ETEENUS` value of the cookie received with the request (two user specific elements are sent with the redirect request: a nonce word as a cookie and the hash value calculated from the nonce word in the `state` parameter).

6 Calculate the hash based on the cookie value and encode it with base64.

7 Verify that the hash matches the `state` value mirrored back in the redirect request.

The redirect request may only be accepted if the checks described above is successful.

The key element of the process described above is connection of the `state` value with the session. This is achieved by using a cookie. (This is a temporary authentication session. The work session will be created by the client application after the successful completion of the authentication).

Further information: unfortunately, this topic is not presented clearly in the OpenID Connect protocol. Some information can be found from an unofficial document, [OpenID Connect Basic Client Implementer's Guide 1.0](https://openid.net/specs/openid-connect-basic-1_0.html), section ‘2.1.1.1 Request Parameters’.

### 5.3 Logging

Logging must enable the reconstruction of the course of the communication between TARA and the client application for each occasion of using TARA. For this purpose, all requests and responses must be logged by TARA as well as by the client application: [authentication request](#41-authentication-request), [redirect request](#42-redirect-request) and [identity token request](#43-identity-token-request). As the volumes of data transferred are not large, the URL as well as the identity token must be logged in full. The retention periods of the logs should be determined based on the importance of the client application. We advise using 1 … 7 years. In case of any issue, please submit an excerpt from the log (Which requests were sent to TARA? Which responses were received?).

### 5.4 ID token signing key change

The key change is carried out based on [OpenID Connect](https://openid.net/specs/openid-connect-core-1_0.html#RotateSigKeys) standard.

RIA notifies contact persons of integrators by e-mail about identity token signing key change.

Until the notified date, TARA signs identity tokens with the key that is being used until then. From the notified date, TARA will start signing identity tokens with the new key, which will be added to [public signature key endpoint](#6-endpoints-and-timeouts) by the time the first identity token is signed with it. Therefore, for some time, two public keys are visible at the public signature key endpoint at the same time, each with its own unique `kid` identifier. Example of publishing two keys:
```json
{
   "keys": [
      {
         "use": "sig",
         "kty": "RSA",
         "kid": "8b2c7561-fdf8-41d4-b466-ab323c3865c6",
         "alg": "RS256",
         "n": "sqEw...",
         "e": "AQAB"
      },
      {
         "use": "sig",
         "kty": "RSA",
         "kid": "1c5b7961-41d4-b468-a768-db523c3617f4",
         "alg": "RS256",
         "n": "y7XY...",
         "e": "AQAB"
      }
   ]
}
```

The old public key will be removed from the public signature key endpoint after the last identity tokens signed with it have expired.

See also [Verifying the signature](#511-verifying-the-signature) chapter for information on how the public key must be used in signature validation.

## 6 Endpoints and timeouts

6.1 Endpoints

| description | endpoint  |
|----------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| server discovery | `/.well-known/openid-configuration`<br>`/oidc/.well-known/openid-configuration` |
| public signature key of the service | `/oidc/jwks` |
| registration of the client application | dynamic registration is not supported, static registration via [klient@ria.ee](mailto:klient@ria.ee). |
| authorization | `/oidc/authorize` | 
| token | `/oidc/token` | 
| userinfo | `/oidc/profile` - Even though TARA issues the access token, we only advise to use it for user info endpoint request in case it is not possible to use the identity token (e.g when interfacing out-of-the-box products). All the data of authenticated user are already issued within the identity token. Using the identity token is recommended and, in theory, is considered to be more secure (as the identity token is signed, while the user info endpoint output is not). |

6.2 Timeouts

| timeout                           | value  | remark                                                                                                                                                                                                                                                                       |
|-----------------------------------|--------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| TARA session                      | 30 min | TARA server creates a session with the user identified. If the user doesn't perform any activity at TARA page, the session will expire in after 30 minutes. Note: TARA session must be distinguished from the session between the client application and the user.           |
| OAuth authorization code          | 30 s   | The client application must obtain the ID token using authorization code within 30 seconds.                                                                                                                                                                                  |
| ID token (and OAuth access token) | 40 s   | The ID token includes the token expiry time. For security reasons, the validity period of the token is set to 40 seconds. The client application must not use the expired token. Note that ID token is not a proof of a session between the client application and the user. |

## 7 Recommendations for interfacing with TARA

Interfacing with TARA is easy. Yet, the operations should be carefully planned and executed.

While performing the interfacing, special attention must be paid to ensure that all checks required based on the protocol are performed – checks of the `state` security element, as well as `nonce` security element, if used, the identity token verification, etc. See [ID token validation](http://openid.net/specs/openid-connect-core-1_0.html#ImplicitIDTValidation) [Core].

The process of interfacing is completed as follows:

The institution must determine whether and which of their e-services they would like to use TARA.

Then, the development required for using the service should be planned and executed – complementation of the client application with the OpenID Connect protocol-based client component, including testing. The estimated volume of the development: approx. two days for an experienced developer; if the developer has no previous experience with OpenID Connect, two weeks. The development should be planned based on the current [technical specification](TechnicalSpecification)

When the development is completed, the interface must be tested with the TARA demo service. The representative of the institution must submit an application for subscribing to the demo service. The application may be submitted before launching the development. In the application, the institution must state:

- the service to which they wish to subscribe to (demo or production service)
- confirmation that the subscriber has developed their interface and tested it against the TARA demo service (when subscribing to the production service)
- name(s) of the e-service(s)/client application(s) that will be integrated with TARA
- the estimated number of users of the e-service
- the obligation to only use the service for the intended purpose, including to only use the demo service for testing, not for authentication
- consent with the service level agreement (SLA)
- the redirect-URL of the demo version of the client application, based on the OpenID Connect protocol. Only HTTPS protocol is allowed. URL fragment part (`#` character and characters following it) is not allowed.
- the authentication method or methods which the institution intends to use
- the contact details of the person responsible for the client application (e-mail address, telephone, personal identification code).

The application is submitted and any further communication in the course of administration of the service is conducted via the user support of RIA, `klient@ria.ee`. Read more from the [website of RIA authentication services](https://www.ria.ee/en/state-information-system/eid/partners.html#tara).

RIA, having satisfied the application:

- issues a client application password `client_secret` for the institution. The client secret code is used for signing the identity token requests.
- provides access to the TARA demo service.

As a next step, the institution performs tests of its client application interface. See the testing [guidelines](https://e-gov.github.io/TARA-Doku/Testing).

**Subscribing to the TARA production service.** After successful testing, the institution submits an application for integration with the production version for the client application. The application must include the redirect-URL of the production version of the client application (`redirect_uri`) based on the OpenID Connect protocol and other information.

RIA, having satisfied the application, issues a password for the production version of the client application, `client_secret` and grants access to the production service for the production version of the client application.

## 8 eIDAS levels of assurance

As stated by [eIDAS regulation](https://eur-lex.europa.eu/legal-content/EN/TXT/HTML/?uri=CELEX:32014R0910&from=EN), eIDAS level of assurance (LoA) is the level of reliability (high, substantial, low) assigned to a means of authentication under the implementing regulation [(EL) 2015/1502](https://eur-lex.europa.eu/legal-content/EN/TXT/HTML/?uri=CELEX:32015R1502&from=EN). The LoA of a means of authentication is determined by several aspects: the basis of identity issuance, organizational processes, the technical solution and management of the solution.

Levels of assurance for domestic authentication methods used in TARA are approved by the Information System Authority according to eIDAS implementing regulation.

Means of authentication used in TARA have been assigned the following levels of assurance:

| Means of authentication | Level of assurance                                                                                                         |
|-------------------------|----------------------------------------------------------------------------------------------------------------------------|
| ID-Card                 | [high](https://ec.europa.eu/digital-building-blocks/wikis/display/EIDCOMMUNITY/Estonia)                                    |
| Mobile-ID               | [high](https://ec.europa.eu/digital-building-blocks/wikis/display/EIDCOMMUNITY/Estonia)                                    |
| Smart-ID                | [high](https://www.ria.ee/media/586/download) <sup>1</sup> |

<sup>1</sup>NB! The LoA applies only for persons with an Estonian identity code and a Smart-ID account. Interfacing party has to consider that non-residents (Estonian e-residents) are not distinguishable from residents.

Every member state assigns LoA's to their own domestic means of authentication and notifies other member states of them according to [eIDAS regulation](https://eur-lex.europa.eu/legal-content/EN/TXT/HTML/?uri=CELEX:32015R1502&from=EN). Please refer [here](https://ec.europa.eu/cefdigital/wiki/display/EIDCOMMUNITY/Overview+of+pre-notified+and+notified+eID+schemes+under+eIDAS) for a list of all authentication methods that have been notified by member states.

## 9 Private sector client specifications

The interfacing process and authentication flows for private sector clients are identical with public sector clients.

Compared to public sector, private sector clients can explicitly only use the compulsory `openid` scope with cross-border eIDAS authentication scopes `eidas`, `eidasonly`, `eidas:country:xx`. Read also [about the `scope` parameter in authentication request.](https://e-gov.github.io/TARA-Doku/TechnicalSpecification#41-authentication-request)

`eidas:country:xx` scope works only with `eidasonly` scope.

`eidas:country:xx` scope allows for interfaced clients to skip TARA's user interface and direct the authentication flow straight to specified country's authentication service.

In the demo environment, more countries are displayed to private sector clients than in the production environment. Not all countries support private sector authentication.

Currently, the following countries are supported in the production environment for private sector client authentication:

| Country  | Country code |
|----------|--------------|
| Belgium  | BE           |
| Portugal | PT           |
| Sweden   | SE           |

NB! Information System Authority does not guarantee the workability of other eIDAS member states' authentication services. Private sector eIDAS authentication depends on other member states and may be subject to change.

## 10 Environments

| Environment | OpenID Connect Issuer URL | IP addresses in use | All possible IP addresses to allow outgoing connections from client application |
|-------------|---------------------------|---------------------|---------------------------------------------------------------------------------|
| Demo | `https://tara-test.ria.ee` | client application must resolve via DNS | 141.101.90.16<br>141.101.90.17<br>2a06:98c1:3200::6<br>2a06:98c1:3200::7<br>195.80.123.174 |
| Production | `https://tara.ria.ee` | client application must resolve via DNS | 141.101.90.16<br>141.101.90.17<br>2a06:98c1:3200::6<br>2a06:98c1:3200::7<br>195.80.123.203 |

If outgoing connections to TARA are restricted by IP addresses on the client application side, the client application must allow outgoing connections to all IP addresses listed in the last column. The client application must resolve the connection to any of the IP addresses returned by the DNS record. The DNS record is updated as needed to include the functioning IP addresses serving the TARA service (a subset of the IP addresses listed in the last column).
