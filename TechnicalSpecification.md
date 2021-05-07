---
permalink: TechnicalSpecification
---


# Technical specification
{: .no_toc}
v 1.8, 19.03.2021

- TOC
{:toc}

## 1 Overview

This document describes the technical characteristics of the TARA authentication service and includes advice for interfacing the client application with e-services.

The TARA authentication service is a service provided by the Information System Authority of the Republic of Estonia which can be used by institutions to add the support of various different authentication methods to its e-service:

- Mobiil-ID
- ID card
- cross-border authentication (via eIDAS-Node) support
- Smart-ID

This technical specification is targeted for mainly software developers. The reader should be familiar with the HTTP protocol. Experiences with OpenID Connect or OAuth 2.0 would be beneficial but are not necessary. The reader should be prepared to obtain further information from the original text of the OpenID Connect protocol, if necessary.

We have attempted to use uniform terminology throughout the technical specification. Definitions can be found from the vocabulary and references. It should be kept in mind that the systems of concepts of OpenID Connect, OAuth 2.0, etc. have not been perfectly homogenised. For example, the information system of the institution offering an e-service which is interfaced with TARA is referred to as ‘the client application’ in this document. In OAuth and some other contexts, however, the client application is referred to as ‘the service provider’.

### 1.1 OpenID Connect

The TARA authentication service is based on the OpenID Connect protocol, which is in turn based on the OAuth 2.0 protocol. OpenID Connect and OAuth 2.0 are extensive standards with numerous possibilities.

For TARA, the application flows and features necessary for the applications of TARA were chosen and some adjustments were made. The main selections and adjustments compared to the full OpenID Connect protocol are the following:

- The service supports the authorisation code flow. The authorisation code flow is deemed the most secure option and is thus appropriate for public services.
- All information about an authenticated user is transferred to the application in an ID token.
- The eIDAS assurance level is also transferred to the application if it is known (in the `acr` statement).
- The authentication method is selected by the user in the authentication service.
- Cross-border authentication based on the technical specification of eIDAS.
- Dynamic registration of the client application is not supported. The client application is registered in RIA by a separate procedure.
- Single sign-on (SSO) and _session management_ are currently not supported.

It is not ruled out that the scope of possibilities will be expanded as TARA is being developed further – based on the needs of the users of TARA and the opportunities for satisfying the needs at a high-quality level, while keeping the service simple and focussed.

### 1.2 National and cross-border authentication

TARA enables national as well as cross-border authentication. This means that Estonians (users of the Estonian e-identification system – ID card, Mobiil-ID, etc.) as well as foreigners (users of the e-identification systems of other EU member states) can be authenticated.

In the context of eIDAS, TARA is providing the ‘Authentication of an Estonian in an Estonian e-service’ and the ‘Authentication of a foreigner using an Estonian e-service’ application flows (Figure 1).

<p style='text-align:center;'><img src='img/YLEVAADE.PNG' style='width:600px'></p>

- Klientrakendus – customer’s application
- välismaa e-identimissüsteemi kasutaja – user of a foreign e-identification service
- Eesti e-identimissüsteemi kasutaja – user of the Estonian e-identification service
- eIDAS piiriülene autentimine – eIDAS-Node cross-border authentication
- ID-kaardiga autentimine – authentication by ID card
- Mobiil-ID-ga autentimine – authentication by Mobiil-ID
- Smart-ID – Smart-ID.

#### Figure 1. National and cross-border authentication

## 2 Authentication process from the user’s perspective

1 The user uses a client application providing an e-service

- the user may be Estonian or a foreigner;
- a screen with a ‘Log in’ etc. button is displayed to the user;
- the user clicks ‘Log in’.

2 The client application redirects the user to the TARA service (by browser redirection)

- authentication request in the redirection URL.

3 The authentication method selection screen is displayed to the user. Here, the user can:

- choose authentication by Mobiil-ID (step 4)
-	choose authentication by ID card (step 5)
-	choose cross-border (eIDAS-Node) authentication (step 6)
  - incl. the country the eID of which they use (select the correct ‘flag’)
-	choose authentication by smart ID (step 8)
-	return to the client application.

4 Mobiil-ID authentication

- the user enters their mobile phone number and personal identification code
-	a verification code is displayed on the user’s mobile device
-	waiting for confirmation
-	in the case of successful authentication, moving on to step 9; in the case of an error, to step 10.

5 ID card authentication

-	first, information about the authentication certificate is displayed to the user
-	the user confirms selection of the certificate
-	the user enters PIN1
-	in the case of successful authentication, moving on to step 9; in the case of an error, to step 10.

6 Cross-border (eIDAS-) authentication

-	the user selects the target country
-	the user is redirected to the authentication service of the foreign country through the eIDAS infrastructure
-	the user authenticates themselves by using the means of authentication of the foreign country
-	in the case of successful authentication (and if the level of the means of authentication of the foreign country is sufficient), moving on to step 9
-	in the case of an error, to step 10.

8 Smart-ID authentication

-	the user enters an Estonian personal identification code
-	a verification code is displayed on the user’s mobile device
-	waiting for confirmation
-	in the case of successful authentication, moving on to step 9; in the case of an error, to step 10.

9 Authenticated user

-	is redirected back to the client application
-	the client application requests the identity token from the TARA server
-	the _identity token_ is a signed certificate confirming successful authentication
-	the identity token includes the user’s data (attributes) which were identified in the course of authentication
-	the client application notifies the user of successful log in in an appropriate manner.

10 From the error message screen:

-	the user can return to the selection of the authentication method
-	and try again, by using a different authentication method, if desired
-	or terminate the authentication process and return to the client application.

11 The user can also:

- obtain further information about the service of TARA.

## 3 Authentication flow from the technical perspective

Detailed description of the communication between the browser, the server component of the client application, and the server component of TARA.

These three parties communicate by HTTP requests and responses.

The main requests and the responses thereto are discussed (Figure 2).

<p style='text-align:center;'><img src='img/VOOG-01.PNG' style='width:400px'></p>

Figure 2. Authentication request

The flow begins from the browser. A page is loaded from the client application to the browser, where the user choose click ‘Log in’ or start the authentication process in another manner.

After the user selects ‘Log in’, the browser sends a HTTP request **1a** to the client application (to the server component of the client application). The client application may also launch the authentication process autonomously, as a result of some other operation performed by the user.

The client application creates an authentication request. The composition of the authentication request is described in a separate section below. The client application sends a response body **1b** to the browser. The response body includes the HTTP redirection and the authentication request.

The browser completes the redirection by excerpting the authentication request from response body **1b** and sending it to the server component of TARA as **2a** request.

Having received an authentication request **2a**, the server component of TARA generates the page of authentication methods and transfers it to the browser as response body **2b**.

The page of authentication methods is displayed to the user. The flow is described further in Figure 3.

<p style='text-align:center;'><img src='img/VOOG-02.PNG' style='width:400px'></p>

Figure 3. Redirect request

The user chooses an authentication method. The selection is transferred to the server component of TARA by a HTTP request **3a**.

This is followed by an authentication dialogue based on the authentication method selected by the user. In the authentication dialogue, several messages may be exchanged between the browser and the server component of TARA and several redirections may be completed. For example, in the case of cross-border authentication, the user is redirected several times to reach an authentication service of a foreign country. These requests and responses are referred to as “…” in the Figure.

The authentication dialogue terminates and the user must be redirected back to the client application.

The server component of TARA sends a HTTP response body **3b** to the browser which includes a redirection order for redirecting the user to the client application.

The browser completes the redirection order **3b** by sending a HTTP request **4a** to the server component of the client application (redirect request).

The redirect request includes the result of the authentication process (the person was or was not identified). The redirect request is described in detail in a separate section below.

The role of TARA should be ending here. In the case of the OpenID Connect implicit flow, it does. However, TARA uses the authorisation code flow which is deemed somewhat more secure than the implicit flow. In the case of the authorisation code flow, the authorisation service does not transfer the entire authentication in the redirect request, but only the authorisation token.

An authorisation code is issued against the personal identification code, name, and other personal data of the person identified by sending a separate request to the server component of TARA (Figure 4).

<p style='text-align:center;'><img src='img/VOOG-03.PNG' style='width:400px'></p>

Figure 4. Identity token request

The server component of the client application sends an identity token request **5a** to the server component of TARA. In the identity token request, the client application provides the authorisation code received in the redirect request. The client application proves its authenticity by adding a client secret code to the request. The identity token request is a backend request that is not sent through the browser.

Having received an identity token **5a**, the server component of TARA verifies the client secret code and issues the identity token **5b** in the response. The identity token includes information about the fact of authentication (time of authentication, authentication method) and about the person identified (personal identification code, first name and surname; in the case of cross-border authentication, also date of birth and other data). The server component of TARA signs the identity token. The identity token is described in detail in a separate section below.

The client application receives the identity token **5b**. In order to prevent attacks, the client application must verify whether the identity token was actually issued by TARA, intended for the client application, and not expired.

Thereby, the authentication is completed. The user has now been identified in the client application.

In most cases, the client application then launches a session with the user. Launching a session is not included in the scope of TARA.

The client application sends a HTTP response **4b** to the browser, such as the ‘Logged in’ page.

## 4 Requests

### 4.1 Authentication request

An authentication request is a HTTP GET request by which the user is redirected from the client application to TARA for authentication.

An example of an authentication request (for better readability, the query component of the URL was divided over several lines):

````
GET https://tara.ria.ee/oidc/authorize?

redirect_uri=https%3A%2F%2Feteenindus.asutus.ee%2FCallback&
scope=openid&
state=hkMVY7vjuN7xyLl5&|
response_type=code&
client_id=58e7ba35aab5b4f1671a
````

Elements of an authentication request:

| URL element | compulsory | example | explanation |
| ----------- | ---------- | ------- | ----------- |
| protocol, host, and patch | yes | `https://tara.ria.ee/oidc/authorize` | `/authorize` is the OpenID Connect-based authentication endpoint of the TARA service (the concept of ‘authorisation’ originates from the OAuth 2.0 standard protocol). 
| `redirect_uri` | yes | `redirect_uri=https%3A%2F%2F eteenus.asutus.ee%2Ftagasi` | Redirect URL. The redirect URL is selected by the institution. The redirect URL may include the query component. <br><br> [URL encoding](https://en.wikipedia.org/wiki/Percent-encoding) should be used, if necessary.<br><br>It is [not permitted](https://tools.ietf.org/html/rfc6749#section-3.1.2) to use the URI [fragment component](https://tools.ietf.org/html/rfc3986#section-3.5) (`#` and the following component). |
| `scope` | yes | `scope=openid`<br><br>`scope=openid%20eidas`<br><br>`scope=openid%20idcard%20mid` | The authentication scope.<br><br>`openid` is compulsory (required by the OpenID Connect protocol).<br><br> The scopes of `idcard`, `mid`, `smartid`, `eidas` (and `eidasonly`) can be used to request that only the desired method of authentication is displayed to the user. See 4.1.3 Selective use of authentication methods.<br><br>The `email` scope can be used to request that the user’s e-mail address is issued in the identity token. See 4.1.2 Requesting e-mail address.<br><br>In the case of cross-border authentication, further scopes can be used to request additional personal data (see below).<br><br>When using several scopes, the scopes must be separated by spaces. Thereat, the space is presented in the URL encoding (`%20`) ([RFC 3986](https://www.ietf.org/rfc/rfc3986.txt)). Scope values are case sensitive. Unknown values are ignored. |
| `state` | yes | `state=hkMVY7vjuN7xyLl5` | Security code against false request attacks (_cross-site request forgery_, CSRF). Read more about formation and verification of `state` under ‘Protection against false request attacks. |
| `response_type` | yes | `response_type=code` | Determines the manner of communication of the authentication result to the server. The method of authorisation code is supported (_authorization flow_ of the OpenID Connect protocol) and it is referred to the `code` value. |
| `client_id` | yes | `client_id=58e7...` | Application identifier. The application identifier is issued to the institution by RIA upon registration of the client application as a user of the authentication service. |
| `ui_locales` | no | `ui_locales=et` | Selection of the user interface language. The following languages are supported: `et`, `en`, `ru`. |
| `nonce` | no | `fsdsfwrerhtry3qeewq` | A unique parameter which helps to prevent replay attacks based on the protocol ([References](References), [Core], subsection 3.1.2.1. Authentication Request). The `nonce` parameter is not compulsory. |
| `acr_values` | no | `acr_values=substantial` | The minimum required level of authentication based on the eIDAS LoA (level of assurance). The parameter is only applied in the case of cross-border authentication. The parameter is ignored in the case of other methods of authentication. It is permitted to apply one value of the following: `low`, `substantial`, `high` . `substantial` is used by default if the value has not been selected. |

#### 4.1.1 Requesting attributes about foreigners

In the case of authentication of a foreigner, the foreigner is redirected by TARA to the authentication service of their country.
By a regulation of the European Committee, members states have agreed that another country’s authentication service must always issue four attributes of a natural person: 1) first name; 2) surname; 3) date of birth; 4) personal identification code or another identifier.
Two attributes are always issued of the legal persons: 1) legal person's identifier; 2) legal person’s name.

#### 4.1.2 Requesting e-mail address

The `email` scope can be used to request the user’s e-mail address in the identity token. This option is targeted for the client applications that require verification of an e-mail address in authentication of a user. The `email` scope must be added to the main scope `openid`. The claims `email` and `email_verified` are issued in the identity token. For example:

```
"sub": "EE60001019906",
"email": "60001019906@eesti.ee",
"email_verified": false
```

The `email` value is read from an extension of the user’s authentication certificate (from the RFC822 type Subject Alternative Name field). The e-mail address is only issued if the user is authenticated by an Estonian ID card. The client application must take into consideration that the user may not have redirected their e-mail, i.e. a e-mail sent to this address may not reach the user.

The `email_verified` is always `false`. It means that TARA does not verify or issue information on whether or not the user has redirected their eesti.ee e-mail address. (The respective functionality may be added in the future).

#### 4.1.3 Selective use of means of authentication

By default, all supported authentication methods are displayed to the user. If necessary, the authentication options displayed can be managed by using the `scope` parameter's value. parameetri väärtuste abil. Preferred authentication methods can be combined to draw up a list of the authentication methods (the list of permitted values is provided in Table 1).

When using the selective means of authentication the validation of `amr` claim must be performed in the identity token as an additional security method.

Table 1 – displaying the authentication methods

| Value of the scope parameter | Explanation |
|--------------------------|--------------------------------|
| `idcard` | Allowing Estonian ID card authentication |
| `mid` | Allowing Mobile-ID authentication |
| `smartid` | Allowing Smart-ID authentication |
| `eidas` | Allowing cross-border (eIDAS) authentication |
| `eidasonly` | Allowing ONLY cross-border (eIDAS) authentication <br><br>NB! When `eidasonly` is used, all other preferred authentication methods will be always excluded. |

Example 1: All means of authentication
`scope=openid`

Example 2: Only ID card and mobile ID
`scope=openid%20idcard%20mid`

Example 3: Only cross-border authentication
`scope=openid%20eidas`

### 4.2 Redirect request

The redirect request is a HTTP GET request which is used to redirect the user back from TARA to the client application.

The user is redirected to the return address included in the authentication request sent by the client application. In the redirect request, an authorization code is sent to the client application by TARA, based on which the client application will (by a separate request) request from TARA the personal identification code, name, and other attributes of the authenticated person. Technically, a HTTP redirect request is used for redirecting.

An example of a redirect request:

````
HTTP GET https://eteenus.asutus.ee/tagasi?
code=71ed5797c3d957817d31&
state=OFfVLKu0kNbJ2EZk
````

Elements of a redirect request:

| URL element | example | selgitus |
|-------------|---------|----------|
| protocol, host, and path | `https://eteenus.asutus.ee/tagasi` | Matches the `redirect_uri` value sent in the authentication request. |
| `code` | `code=71ed579...`  | The authorisation code is a single ‘permission note’ to receive the identity token. |
| `state` | `state=OFfVLKu0kNbJ2EZk` | Security code against false request attacks. The security code received in the authentication request is mirrored back. Read more about forming and verifying `state` from ‘Protection against false request attacks’. |

**Error message in the redirect request.** If TARA is unable to process an authentication request – there is an error in the request, or another error has occurred – TARA transfers an error message (URL parameter `error`) and the description of the error (URL parameter `error_description`) in the redirect request. 

TARA relies on the OpenID Connect standard on error messages (more information regarding the error messages can be found from https://openid.net/specs/openid-connect-core-1_0.html#AuthError and https://tools.ietf.org/html/rfc6749#section-4.1.2.1). The error messages are always displayed in English.

`state` is also redirected but no authorisation code (`code`) is sent. E.g.:

````
HTTP GET https://eteenus.asutus.ee/tagasi?
error=invalid_scope&
error_description=Required+scope+%3Copenid%3E+not+provided.+
TARA+do+not+allow+this+request+to+be+processed&
state=qnYY56Ra8QF7IUzqvw+PPLzMKoHtQkuUWbV/wcrkvdU=
````

The redirect request errors are normally resulted by a misconfiguration; therefore the error description in parameter `error_description` is not needed to be displayed for the user directly. The client application should check whether or not an error message has been sent.

**Termination of the authentication process.** The user may also return to the e-service without choosing an authentication method and completing the authentication process (via ‘Back to the service provider’ link). This option is provided for the cases in which the user has clicked ‘Log in’ in the client application but does not actually wish to log in. In the application for subscribing to the service, the institution must notify RIA of the URL to which the user should be redirected in the case of clicking ‘Back to the service provider’. NB! The OpenID Connect protocol-based redirect URL and the URL described here have different meanings.

### 4.3 Identity token request

The identity token request is a HTTP POST request which is used by the client application to request the identity token from the server of TARA.

An example of an identity token request (for better readability, the body of the HTTP POST request is divided over several lines):

````
POST /token HTTP/1.1
Host: tara.ria.ee/oidc/token
Content-Type: application/x-www-form-urlencoded
Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW

grant_type=authorization_code&
code=SplxlOBeZQQYbYS6WxSbIA&
redirect_uri=https%3A%2F%2eteenus.asutus.ee%2Ftagasi
````

The client secret code must be provided in the identity token request. For this purpose, the request must include the `Authorization` request header with the value formed of the word `Basic`, a space, and a string `<client_id>:<client_secret>` encoded in the Base64 format (see RFC 2617 HTTP Authentication: Basic and Digest Access Authentication, Section 2 Basic Authentication Scheme).

The body of the HTTP POST request must be presented in a serialised [format](https://openid.net/specs/openid-connect-core-1_0.html#FormSerialization) based on the OpenID Connect protocol.

The body of the request must include the following parameters:

| POST request body element | example | explanation |
|---------------------------|---------|-------------|
| protocol, host, and path | `https://tara.ria.ee/oidc/token` |   |
| `grant_type`  | `grant_type=authorization_code` | The `authorization_code` value required based on the protocol. |
| `code` | `code=Splx...` | The authorization code received from the authentication service. | 
| `redirect_uri` | `redirect_uri=https%3A%2F` | The redirect URL sent in the authorisation request. |

#### 4.3.1 Identity token

TARA server verifies that the identity token is requested by the right application and issues the identity token included in the response body (HTTP response body).

The response body uses JSON format consisting four elements (see the following table).

| element | explanation |
|:-------:|-------------|
| `access_token` | OAuth 2.0 access certificate. With identity token the client application can issue authenticated user's data from `userinfo` endpoint.<br><br>Even though TARA issues the access token, we only advise to use it (to receive the authenticated user’s data from the `userinfo` endpoint) in case it is not possible to use the identity token (e.g when interfacing out-of-the-box products). All the data of authenticated user are already issued within the identity token. Using the identity is recommended and, in theory, is considered to be more secure (as the identity token is signed, while the `userinfo` endpoint is not). |
| `token_type` | OAuth 2.0 access token type with `bearer` value. Not used in TARA |
| `expires_in` | The validity period of the OAuth 2.0 access token. Not used in TARA. |
| `id_token` | Identity token, in Base64 format. | 

The identity token is a certificate of the fact of authentication issued by TARA.

The identity token is issued in [JSON Web Token](https://jwt.io/), JWT format.

An example:

````json
{
  "jti": "0c597356-3771-4315-a129-c7bc1f02a1b2",
  "iss": "https://tara-test.ria.ee",
  "aud": "TARA-Demo",
  "exp": 1530295852,
  "iat": 1530267052,
  "nbf": 1530266752,
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
````

The following claims are presented in the identity token.

| identity token element (_claim_) | example of a value, explanation |
|:---------------------------------|---------------------------------|
| `jti` (_JSON Token Identifier_) | `0c597356... ` - identity token identifier |
| `iss` (_Issuer_)       | `https://tara.ria.ee` - issuer of the certificate (TARA); in the case of test services `https://tara-test.ria.ee` |
| `aud` (_Audience_)     | `TARA-Demo` - the ID of a client application that requested authentication (the value of `client_id` field is specified upon directing the user to the authentication process). |
| `exp` (_Expires_) | `1530295852` - the expiration time of the certificate (in Unix _epoch_ format). |
| `iat` (_Issued At_) | `1530267052` - the time of issue of the certificate (in Unix _epoch_ format). |
| `nbf` (_Not Before_)   | `1530266752` - the validity start time of the certificate (in Unix _epoch_ format). |
| `sub` (_Subject_)      | `EE60001019906` - the identifier of the authenticated user (personal identification code or eIDAS identifier) with the prefix of the country code of the citizen (country codes based on the ISO 3166-1 alpha-2 standard). |
| `profile_attributes`   | the data of the authenticated user, including the eIDAS attributes |
| `profile_attributes`<br>`.date_of_birth` | `2000-01-01` - the date of birth of the authenticated user in the ISO_8601 format. Only sent in the case of persons with Estonian personal identification code and in the case of eIDAS authentication. |
| `profile_attributes`<br>`.given_name` | `MARY ÄNN` - the first name of the authenticated user (the test name was chosen because it consists special characters). |
| `profile_attributes`<br>`.family_name` | `O’CONNEŽ-ŠUSLIK` - the surname of the authenticated user (the test name was selected because it includes special characters). |
| `profile_attributes`<br>`_translit` | Includes a JSON object consisting of profile attributes in the Latin alphabet (see the section on transliteration below). The value is present only in the case of eIDAS authentication. |
| `amr` (_Authentication Method Reference_) | `mID` - the authentication method used for user authentication. Possible values: `mID` - Mobile-ID, `idcard` - Estonian ID card, `eIDAS` - cross-border, `smartid` - Smart-ID  |
| `state` | `abcdefghijklmnop` - security element. The authentication request’s `state` parameter value.  |
| `nonce` | `qrstuvwxyzabcdef` - security element. The authentication request’s `nonce` parameter value. Value is present only in case the `nonce` parameter was sent in the authentication request. |
| `acr` (_Authentication Context Class Reference_) | `high` - level of authentication based on the eIDAS LoA (level of assurance). Possible values: `low`, `substantial`, `high`. The element is not used if the level of authentication is not applicable or is unknown. |
| `at_hash` | `X0MVjwrmMQs/IBzfU2osvw==` - the access token hash. Not used in TARA. |
| `email` | `60001019906@eesti.ee` - the user’s e-mail address. Only issued if an Estonian ID card is used for authenticating the user. Is only read from the SAN extension of the user’s authentication certificate (from the RFC822 type `Subject Alternative Name` field) |
| `email_verified` | `false` - the e-mail address of the user has been verified. TARA always issues a value `false`. It means that TARA does not verify or issue information on whether or not the user has redirected his/her eesti.ee e-mail address. |

Identitiy token might consist other OpenID Connect protocol based fields that are not supported in TARA.

The client application must use the identity token immediately or within `5...10` minutes (before the expiry time of the identity token).

### 4.4 User info request

User info request enables requesting information about an authenticated user based on a valid `OAuth 2.0` access token. The request must be done by using the HTTP GET method.

The access token must be presented to the user info endpoint in the HTTP header or by using the [Bearer Token method](https://tools.ietf.org/html/rfc6750#section-2.1) or as a [URLi parameter](https://tools.ietf.org/html/rfc6750#section-2.3).

Example 1 – transferring an access certificate in the `Authorization` header:

````
GET /oidc/profile HTTP/1.1
Host: tara.ria.ee
Authorization: Bearer AT-20-qWuioSEtFhYVdW89JJ4yWvtI5SaNWep0
````

Example 2 – transferring of access certificate as an `access_token` parameter:

````
GET /oidc/profile?access_token=AT-20-qWuioSEtFhYVdW89JJ4yWvtI5SaNWep0 HTTP/1.1
Host: tara.ria.ee
````

The valid access token response is provided in the JSON format. Example:

````json
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
```` 

The claims included in the response are issued based on the identity token.

| json element (_claim_) | disclosure is compulsory | explanation | 
|:-----------------------|--------------------------|-------------|
| `auth_time` | yes | The time of successful authentication of the user. In the Unix epoch format. |
| `sub` (_Subject_) | yes | The same format and meaning as `sub` in identity token. |
| `given_name` | yes | The same format and meaning as `profile_attributes.given_name` in identity token. |
| `family_name` | yes | The same format and meaning as `profile_attributes.family_name` in identity token. |
| `amr` | yes | The same format and meaning as `amr` in identity token. |
| `date_of_birth` |  no <sup>1</sup> | The same format and meaning as `profile_attributes.date_of_birth` in identity token. |
| `email` | no  <sup>1</sup> | The same format and meaning as `email` in identity token. |
| `email_verified` | no  <sup>1</sup> | The same format and meaning as `email_verified` in identity token. |
| `acr` | no  <sup>1</sup> | The same format and meaning as `acr` in identity token. |

 <sup>1</sup> Only issued if the claim is also included in the identity token.

**Error handling**

In case the access token presented to the user information endpoint is missing or is expired, an error code and a brief description about the error are returned in the `WWW-Authenticate` header according to the [OpenID Connect Core 1.0 spetsifikatsioonile](https://openid.net/specs/openid-connect-core-1_0.html#UserInfoError).

An example:

````
HTTP/1.1 401 Unauthorized
WWW-Authenticate: Bearer error="invalid_token",error_description="The access token has expired"
````

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

For the signature verification the public signature key of TARA must be used. The public signature key is published at the public signature key endpoint (see chapter 6 "Endpoints").

The public signature key is stable - the public signature key will be changed  according to security recommendations. However, the key can be changed without prior notice for security reasons. Key exchange is carried out based on [OpenID Connect](https://openid.net/specs/openid-connect-core-1_0.html#RotateSigKeys) standard.

The public signature key has an identifier (`kid`). The key identifier is aligned with the best practices of [OpenID Connect](https://openid.net/specs/openid-connect-core-1_0.html#Signing) and OAuth 2.0 that enables the key exchange without the service interruption.

In case of the signing key change, two keys will be published at the public signatue key endpoint, both will have unique `kid` identifiers. 

TARA issues `kid` field in the response of the request of identity token (JWT header element `kid`). `kid` refers to the key that the client application has to use for the verification of a signature.

We recommend to buffer the signature key (together with `kid` value) in order to decrease the amount of requests made to TARA server. However, it is allowed to request the key on each validation.

For signature validation following checks needs to be performed on client application:

1 - Read the `kid` value from the JWT header.

2.1 - If the client application does not buffer the public key, make request to public signature key endpoint and select key corresponding to `kid` value received from JWT header.

2.2 - If client application buffers the public key (it needs to be buffered together with `kid` value), it needs to compare the `kid` value from JWT header with buffered `kid` value. If they match, buffered key can be used. If not client application needs to make request to public signature key endpoint and select key corresponding to `kid` value received from JWT header and buffer it.

3 - Validate the signature using the key corresponding to `kid` value from the JWT header.

NB! "Hardcoding" the key to client application configuration must be avoided. The key change will be typically communicated (except of urgent security reasons), but manual key handling will result downtime in client application for the period when TARA is already using the new key until the new key is taken use in client application.

#### 5.1.2 Trusting of the public signature key endpoint

The client application makes HTTPS requests to TARA server towards to the identity token and public signature key endpoints. The client application must verify TARA server’s certificate (domains `tara.ria.ee` and `tara-test.ria.ee`). As the certificates of afore-mentioned domains are issued by DigiCert, the client application must use DigiCert’s root certificate or TARA certificate as a trust anchor.

#### 5.1.3 Verifying the issuer of the certificate

The `iss` value of the identity token element must be `https://tara-test.ria.ee` (for TARA test environment) or `https://tara.ria.ee` (for TARA production environment).

#### 5.1.4 Verifying the addressee of the certificate

The client application must verify whether the certificate received was issued for them. For this purpose, it must be made sure that the `aud` value of the identity token element matches the client ID issued upon registration of the client application.

#### 5.1.5 Verifying the validity of the certificate

The verification is done using three elements in the identity token: `iat`, `nbf`, `exp`. The client application uses its own clock to verify the validity. The following details should be verified:

1) that "not before" time has reached:

`nbf <= jooksev_aeg + kellade_lubatud_erinevus` (current time + permitted difference between clocks).

2) that the "expired" time has not been reached:

`exp > jooksev_aeg - kellade_lubatud_erinevus` (current time - permitted difference between clocks).

The application must choose the `kellade_lubatud_erinevus` value. These checks are required for preventing attacks and confusion.

The identity token must be used immediately, within 5 minutes. When the time limit is exceeded, the identity token will be not issued.

#### 5.1.6 Verifying the authentication method used in authentication

When using the selective means of authentication (see section 4.1.3) the identity token must verify that the authentication method provided by the authentication method reference, `amr` ,is allowed. Otherwise, the risk of intermediary attacks is taken by allowing the user to authenticate through the method that is not acceptable in the interface (eg Smart-ID is used instead of authentication with an ID card) through manipulation of the authentication request `scope` parameter.

For example, when in the authentication request the `scope` parameter is defined to use only ID-card authentication method, it must be verified that the `amr` claim also contains an `idcard` code (the full list of all codes is described under section 4.3.1).

#### 5.1.7 Verifying the eIDAS level of assurance

In order to prevent access to cross-border authentication tools with a lower security level, it must be verified that the authentication level in the `acr` claim of identity token is not lower than the minimum level of assurance allowed.

For example, if the client application wants to use only authentication methods with eIDAS level of assurance `high` and has specified the value in the `acr_values` parameter, then only the identity tokens with `acr_values` parameter with value `high` can be accepted.

In case the level of assurance in the authentication request using `acr_values` parameter is not specified, the identity token must be equal to a level of assurance `substantial` or `high`.

#### 5.1.8 Creating a session

After a successful verification of the identity token, the client application will create a session with the user (‘log in the user’). The client application is responsible for creating and holding the sessions. The methods for doing this are not included in the scope of the TARA authentication service.

### 5.2 Protection against false request attacks

The client application must implement protective measures against false request attacks (_cross-site request forgery_, CSRF). This can be achieved by using `state` and `nonce` security codes. Using `state` is compulsory; using `nonce` is optional. The procedure of using `state` is described below.

The `state` security code is used to combat falsification of the redirect request following the authentication request. The client application must perform the following steps:

1 Generate a nonce word, for example of the length of 16 characters: `XoD2LIie4KZRgmyc` (referred to as `R`).

2 Calculate from the `R` nonce word the `H = hash(R)`hash, for example by using the SHA256 hash algorithm and by converting the result to the Base64 format: `vCg0HahTdjiYZsI+yxsuhm/0BJNDgvVkT6BAFNU394A=`.

3 Add an order to set a cookie to the authentication request, for example:

`Set-Cookie ETEENUS=XoD2LIie4KZRgmyc; HttpOnly`,

where `ETEENUS` is a freely selected cookie name. The `HttpOnly` attribute must be applied to the cookie.

4 Set the following value for the `state` parameter calculated based on section 2:

`GET ... state=vCg0HahTdjiYZsI+yxsuhm/0BJNDgvVkT6BAFNU394A=`

Thus, two elements are sent in an authentication request: a nonce word for including in the cookie and the hash value calculated from the nonce word in the `state` parameter. The client application is not required to remember the nonce word or the hash value.

In the course of processing the redirect request, the client application must:

5 Take the `ETEENUS` value of the cookie received with the request.

6 Calculate the hash based on the cookie value.

7 Verify that the hash matches the `state` value mirrored back in the redirect request.

The redirect request may only be accepted if the checks described above is successful.

The key element of the process described above is connection of the `state` value with the session. This is achieved by using a cookie. (This is a temporary authentication session. The work session will be created by the client application after the successful completion of the authentication).

Further information: unfortunately, this topic is not presented clearly in the OpenID Connect protocol. Some information can be found from an unofficial document, [OpenID Connect Basic Client Implementer's Guide 1.0](https://openid.net/specs/openid-connect-basic-1_0.html), section ‘2.1.1.1 Request Parameters’.

### 5.3 Logging

Logging must enable the reconstruction of the course of the communication between TARA and the client application for each occasion of using TARA. For this purpose, all requests and responses must be logged by TARA as well as by the client application: [autentimispäring](#41-autentimisp%C3%A4ring), [tagasisuunamispäring](#42-tagasisuunamisp%C3%A4ring) and [identsustõendipäring](#43-identsust%C3%B5endip%C3%A4ring). As the volumes of data transferred are not large, the URL as well as the identity token must be logged in full. The retention periods of the logs should be determined based on the importance of the client application. We advise using 1 … 7 years. In case of any issue, please submit an excerpt from the log (Which requests were sent to TARA? Which responses were received?).

## 6 Endpoints and timeouts

6.1 Test service

| endpoint      |                        URL      |
|---------------|---------------------------------|
| server discovery |  [https://tara-test.ria.ee/oidc/.well-known/openid-configuration](https://tara-test.ria.ee/oidc/.well-known/openid-configuration) |
| public signature key of the service | [https://tara-test.ria.ee/oidc/jwks](https://tara-test.ria.ee/oidc/jwks) |
| registration of the client application | dynamic registration is not supported, static registration via `help@ria.ee`. |
| authorization | [https://tara-test.ria.ee/oidc/authorize](https://tara-test.ria.ee/oidc/authorize) | 
| token | [https://tara-test.ria.ee/oidc/token](https://tara-test.ria.ee/oidc/token) | 

6.2 Production service

| endpoint      |                        URL      |
|---------------|---------------------------------|
| server discovery | [https://tara.ria.ee/oidc/.well-known/openid-configuration](https://tara.ria.ee/oidc/.well-known/openid-configuration) |
| public signature key of the service | [https://tara.ria.ee/oidc/jwks](https://tara.ria.ee/oidc/jwks) |
| registration of the client application | dynamic registration is not supported, static registration via `help@ria.ee`. |
| authorization | [https://tara.ria.ee/oidc/authorize](https://tara.ria.ee/oidc/authorize) | 
| token | [https://tara.ria.ee/oidc/token](https://tara.ria.ee/oidc/token) | 

6.3 Timeouts

| timeout      |   value    | remark                |
|--------------|------------| --------------------- |
| TARA session | 30 min | TARA server creates a session with the user identified. If the user doesn't perform any activity at TARA page, the session will expire in after 30 minutes. Note: TARA session must be distinguished from the session between the client application and the user. |
| SSL/TLS handshake | 25 s | In case of ID-card authentication. The user must enter PIN1 within 25 seconds. After the timeout, the authentication will be terminated for security reasons. |
| OAuth authorization code | 30 s | The client application must issue the ID tokend using authorization code within 30 seconds. |
| ID token (and OAuth access token) | 10 min | The ID token includes the token expiry time. For security reasons, the validity period of the token is set to 10 minutes. The client application must not use the expired token. Note that ID token is generally is generally not a proof of a session between the client application and the user. |

## 7 Reccommendations for interfacing with TARA

Interfacing with TARA is easy. Yet, the operations should be carefully planned and executed.

While performing the interfacing, special attention must be paid to ensure that all checks required based on the protocol are performed – checks of the `state` security element, as well as `nonce` security element, if used, the identity token verification, etc. See [ID token validation](http://openid.net/specs/openid-connect-core-1_0.html#ImplicitIDTValidation) [Core].

The process of interfacing is completed as follows:

The institution must determine whether and which of their e-services they would like to use TARA.

Then, the development required for using the service should be planned and executed – complementation of the client application with the OpenID Connect protocol-based client component, including testing. The estimated volume of the development: approx. two days for an experienced developer; if the developer has no previous experience with OpenID Connect, two weeks. The development should be planned based on the current [technical specification](TechnicalSpecification)

When the development is completed, the interface must be tested with the TARA test service. The representative of the institution must submit an application for subscribing to the test service. The application may be submitted before launching the development. In the application, the institution must state:

- the service to which they wish to subscribe to (test or production service)
- confirmation that the subscriber has developed their interface and tested it against the TARA test service (when subscribing to the production service)
- name(s) of the e-service(s)/client application(s) that will be integrated with TARA
- the estimated number of users of the e-service
- the obligation to only use the service for the intended purpose, including to only use the test service for testing, not for authentication
-	consent with the service level agreement (SLA)
- the proposal of the client application identifier – `client_id`, based on the OpenID Connect protocol
-	the redirect-URL of the test version of the client application, based on the OpenID Connect protocol
- the redirect-URL of the test version of the client application for the situations in which the user would like to terminate the authentication process
- the authentication method or methods which the institution intends to use
-	the contact details of the person responsible of the client application (e-mail address, telephone, personal identification code).

The application is submitted and any further communication in the course of administration of the service is conducted via the user support of RIA, `help@ria.ee`. Read more from the [website of RIA authentication services](https://www.ria.ee/en/state-information-system/eid/partners.html#tara).

RIA, having satisfied the application:

- issues a client application password `client_secret` for the institution. The client secret code is used for signing identity token requests
- provides an access to the TARA test service.

As a next step, the institution performs tests of it's client applicaton interface. See the testing [guidelines](https://e-gov.github.io/TARA-Doku/Testimine).

**Subscribing to the TARA production service.** After successful testing, the institution submits an application for integration with the production version for the client application. The application must include the redirect-URL of the production version of the client application (`redirect_uri`) based on the OpenID Connect protocol and other information.

RIA, having satisfied the application, issues a password for the production version of the client application, `client_secret` and grants access to the production service for the production version of the client application.
