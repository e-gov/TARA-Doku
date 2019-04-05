---
permalink: Technical specification
---

# Technical specification
v 1.6, 04.04.2019

## 1 Overview

This document describes the technical characteristics of the TARA authentication service and includes advice for interfacing the client application with e-services.

The TARA authentication service is a service provided by the Information System Authority of the Republic of Estonia which can be used by institutions to add the support of various different authentication methods to its e-service:

- mobile-ID
- ID card
- cross-border authentication (eIDAS) support
- Smart-ID
- Coop bank
- LHV bank
- Luminor bank
- SEB bank
- Swedbank

This technical specification is targeted for mainly software developers. The reader should be familiar with the HTTP protocol. Experiences with OpenID Connect or OAuth 2.0 would be beneficial but are not necessary. The reader should be prepared to obtain further information from the original text of the OpenID Connect protocol, if necessary.

We have attempted to use uniform terminology throughout the technical specification. Definitions can be found from the vocabulary and references. It should be kept in mind that the systems of concepts of OpenID Connect, OAuth 2.0, etc. have not been perfectly homogenised. For example, the information system of the institution offering an e-service which is interfaced with TARA is referred to as ‘the client application’ in this document. In OAuth and some other contexts, however, the client application is referred to as ‘the service provider’.

### 1.1 OpenID Connect

The TARA authentication service is based on the OpenID Connect protocol, which is in turn based on the OAuth 2.0 protocol. OpenID Connect and OAuth 2.0 are extensive standards with numerous possibilities.

For TARA, the application flows and features necessary for the applications of TARA were chosen and some adjustments were made. The main selections and adjustments compared to the full OpenID Connect protocol are the following:

- The service supports the authorisation code flow. The authorisation code flow is deemed the most secure option and is thus appropriate for public services.
- All information about an authenticated user is transferred to the application in an ID token.
- The eIDAS assurance level is also transferred to the application if it is known (in the `acr` statement).
- The service supports assigning a language preference to the user interface in the authentication request (by the `locale` parameter (until the end of July 2019) or `ui_locales` (from the end of January 2019)).
- The authentication method is selected by the user in the authentication service.
- Cross-border authentication based on the technical specification of eIDAS.
- Dynamic registration of the client application is not supported. The client application is registered in RIA by a separate procedure.
- Single sign-on (SSO) and _session management_ are currently not supported.

It is not ruled out that the scope of possibilities will be expanded as TARA is being developed further – based on the needs of the users of TARA and the opportunities for satisfying the needs at a high-quality level, while keeping the service simple and focussed.

### 1.2 National and cross-border authentication

TARA enables national as well as cross-border authentication. This means that Estonians (users of the Estonian e-identification system – ID card, mobile ID, etc.) as well as foreigners (users of the e-identification systems of other EU member states) can be authenticated.

In the context of eIDAS, TARA is providing the ‘Authentication of an Estonian in an Estonian e-service’ and the ‘Authentication of a foreigner using an Estonian e-service’ application flows (Figure 1).

<p style='text-align:center;'><img src='img/YLEVAADE.PNG' style='width:600px'></p>

- Klientrakendus – customer’s application
- välismaa e-identimissüsteemi kasutaja – user of a foreign e-identification service
- Eesti e-identimissüsteemi kasutaja – user of the Estonian e-identification service
- eIDAS piiriülene autentimine – eIDAS cross-border authentication
- ID-kaardiga autentimine – authentication by ID card
- Mobiil-ID-ga autentimine – authentication by mobile ID
- Pangalingid, Smart-ID jm – Bank links, Smart-ID, etc.
#### Figure 1. National and cross-border authentication

## 2 Authentication process from the user’s perspective

1 The user uses a client application providing an e-service

- the user may be Estonian or a foreigner;
- a screen with a ‘Log in’ etc. button is displayed to the user;
- the user clicks ‘Log in’.

2 The client application redirects the user to the TARA service (by browser redirection)

- authentication request in the redirection URL.

3 The authentication method selection screen is displayed to the user. Here, the user can:

- choose authentication by mobile ID (step 4)
-	choose authentication by ID card (step 5)
-	choose cross-border (eIDAS-) authentication (step 6)
  - incl. the country the eID of which they use (select the correct ‘flag’)
-	choose authentication by bank link (step 7)
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

7 Bank authentication

-	the user selects the bank
-	the user is redirected to the bank’s authentication service
-	the user authenticates themselves by using the selected means of authentication
-	in the case of successful authentication, moving on to step 9
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
| `scope` | yes | `scope=openid`<br><br>`scope=openid%20eidas`<br><br>`scope=openid%20idcard%20mid` | The authentication scope.<br><br>`openid` is compulsory (required by the OpenID Connect protocol).<br><br> The scopes of `idcard`, `mid`, `banklink`, `smartid`, `eidas` (and `eidasonly`) can be used to request that only the desired method of authentication is displayed to the user. See 4.1.3 Selective use of authentication methods.<br><br>The `email` scope can be used to request that the user’s e-mail address is issued in the identity token. See 4.1.2 Requesting e-mail address.<br><br>In the case of cross-border authentication, further scopes can be used to request additional personal data (see below).<br><br>When using several scopes, the scopes must be separated by spaces. Thereat, the space is presented in the URL encoding (`%20`) ([RFC 3986](https://www.ietf.org/rfc/rfc3986.txt)). Scope values are case sensitive. Unknown values are ignored. |
| `state` | yes | `state=hkMVY7vjuN7xyLl5` | Security code against false request attacks (_cross-site request forgery_, CSRF). Read more about formation and verification of `state` under ‘Protection against false request attacks. |
| `response_type` | yes | `response_type=code` | Determines the manner of communication of the authentication result to the server. The method of authorisation code is supported (_authorization flow_ of the OpenID Connect protocol) and it is referred to the `code` value. |
| `client_id` | yes | `client_id=58e7...` | Application identifier. The application identifier is issued to the institution by RIA upon registration of the client application as a user of the authentication service. |
| `locale` | no | `locale=et` | Selection of the user interface language. The following languages are supported: `et`, `en`, `ru`. By default, the user interface is in Estonian language. The user can select the desired language. Note: the parameter will be supported until the end of July 2019. |
| `ui_locales` | no | `ui_locales=et` | Selection of the user interface language. The following languages are supported: `et`, `en`, `ru`. By default, the user interface is in Estonian language. The user can select the desired language. Note: the parameter will be supported until the end of July 2019. |
| `nonce` | no | `fsdsfwrerhtry3qeewq` | A unique parameter which helps to prevent replay attacks based on the protocol ([References](References), [Core], subsection 3.1.2.1. Authentication Request). The `nonce` parameter is not compulsory. |
| `acr_values` | no | `acr_values=substantial` | The minimum required level of authentication based on the eIDAS LoA (level of assurance). The parameter is only applied in the case of cross-border authentication. The parameter is ignored in the case of other methods of authentication. It is permitted to apply one value of the following: `low`, `substantial`, `high` . `substantial` is used by default if the value has not been selected. |

#### 4.1.1 Requesting attributes about foreigners

In the case of authentication of a foreigner, the foreigner is redirected by TARA to the authentication service of their country.
By a regulation of the European Committee, members states have agreed that another country’s authentication service must always issue four attributes of a natural person: 1) first name; 2) surname; 3) date of birth; 4) personal identification code or another identifier.
Two attributes are always issued of the legal persons: 1) clegal person's identifier; 2) legal person’s name.

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

Table 1 – displaying the authentication methods

| Value of the scope parameter | Explanation |
|--------------------------|--------------------------------|
| `idcard` | Allowing Estonian ID card authentication |
| `mid` | Allowing Mobile-ID authentication |
| `banklink` | Allowing bank authentication |
| `smartid` | Allowing Smart-ID authentication |
| `eidas` | Allowing cross-border (eIDAS) authentication |
| `eidasonly` | Allowing ONLY cross-border (eIDAS) authenticationAinult <br><br>NB! When `eidasonly` is used, all other preferred authentication methods will be always excluded. |

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

Identsustõendipäring on HTTP POST päring, millega klientrakendus pärib TARA serverilt identsustõendi (_ID token_).

Identsustõendipäringu näide (HTTP POST päringu keha on loetavuse huvides jagatud mitmele reale):

````
POST /token HTTP/1.1
Host: tara.ria.ee/oidc/token
Content-Type: application/x-www-form-urlencoded
Authorization: Basic czZCaGRSa3F0MzpnWDFmQmF0M2JW

grant_type=authorization_code&
code=SplxlOBeZQQYbYS6WxSbIA&
redirect_uri=https%3A%2F%2eteenus.asutus.ee%2Ftagasi
````

Identsustõendipäringus tuleb esitada salasõna. Selleks tuleb päringusse lisada `Authorization` päis (_request header_), väärtusega, mis moodustatakse sõnast `Basic`, tühikust ja Base64 kodeeringus stringist `<client_id>:<client_secret>` (vt _RFC 2617 HTTP Authentication: Basic and Digest Access Authentication_, jaotis 2 _Basic Authentication Scheme_).

HTTP POST päringu keha peab olema esitatud OpenID Connect protokolli kohaselt serialiseeritud [kujul](https://openid.net/specs/openid-connect-core-1_0.html#FormSerialization).

Päringu kehas tuleb esitada järgnevad parameetrid:

| POST päringu keha element | näide                    |  selgitus     |
|------------------------|-----------------------------|---------------|
| protokoll, host ja tee | `https://tara.ria.ee/oidc/token` |   |
| `grant_type`  | `grant_type=authorization_code` | Protokollikohane nõutav väärtus `authorization_code`. |
| `code` | `code=Splx...` | Autentimisteenuselt saadud volituskood. | 
| `redirect_uri` | `redirect_uri=https%3A%2F` | Autentimispäringus saadetud ümbersuunamis-URI. |

#### 4.3.1 Identsustõend

TARA server kontrollib, et identsustõendit küsiv klientrakendus on TARAs registreeritud. Seejärel väljastab päringu vastuses (_HTTP response body_) identsustõendi.

Päringu vastus on JSON-struktuur, milles on neli elementi (vt järgnev tabel). 

| element | selgitus |
|:-------:|----------|
| `access_token` | OAuth 2.0 pääsutõend. Pääsutõendiga saab klientrakendus pärida `userinfo` otspunktist autenditud isikut kirjeldavad andmed.<br><br>TARA väljastab küll pääsutõendi, kuid soovitame pääsutõendit kasutada ainult siis, kui nn "karbitoote" liidestamisel ei ole võimalust kasutada identsustõendit (vt allpool). Kõik autenditud isikut kirjeldavad andmed väljastatakse juba identsustõendis. Identsustõendi kasutamine on eelistatud ja ka teoreetiliselt turvalisem, kuna identsustõend on allkirjastatud, `userinfo` otspunkti väljund aga mitte |
| `token_type` | Väärtusega `bearer`. OAuth 2.0 pääsutõendi tüüp |
| `expires_in` | OAuth 2.0 pääsutõendi aegumiskestus |
| `id_token` | identsustõend, Base64 vormingus | 

Identsustõend on TARA poolt väljastatav tõend autentimise fakti kohta.

Identsustõend väljastatakse [veebitõendi](https://jwt.io/) (JSON Web Token, JWT) vormingus.

Näide (identsustõendi sisu e _payload_):

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

Identsustõendis väljastatakse järgmised väited (_claims_).

| identsustõendi element (väide) | näiteväärtus, selgitus     |
|:-----------------------|---------------------|
| `jti` (_JSON Token Identifier_) | `0c597356... ` - identsustõendi identifikaator |
| `iss` (_Issuer_)       | `https://tara.ria.ee` - tõendi väljastaja (TARA-teenus); testteenuse puhul `https://tara-test.ria.ee` |
| `aud` (_Audience_)     | `TARA-Demo` - autentimist küsinud infosüsteemi ID (kasutaja autentimisele suunamisel määratud `client_id` välja väärtus)|
| `exp` (_Expires_) | `1530295852` - tõendi aegumisaeg, Unix _epoch_ vormingus |
| `iat` (_Issued At_) | `1530267052` - tõendi väljaandmisaeg, Unix _epoch_ vormingus |
| `nbf` (_Not Before_)   | `1530266752` - tõendi kehtivuse algusaeg, Unix _epoch_ vormingus |
| `sub` (_Subject_)      | `EE60001019906` - autenditud kasutaja identifikaator (isikukood või eIDAS identifikaator) koos kodaniku riigikoodi eesliitega (riigikoodid vastavalt ISO 3166-1 alpha-2 standardile) |
| `profile_attributes`   | autenditud isikut kirjeldavad andmed |
| `profile_attributes`<br>`.date_of_birth` | `2000-01-01` - autenditud kasutaja sünnikuupäev ISO_8601 formaadis. Tagastatakse ainult Eesti isikukoodiga isikute puhul ning eIDAS autentimisel |
| `profile_attributes`<br>`.given_name` | `MARY ÄNN` - autenditud kasutaja eesnimi (testnimi, valitud täpitähtede sisalduvuse pärast) |
| `profile_attributes`<br>`.family_name` | `O’CONNEŽ-ŠUSLIK` - autenditud kasutaja perekonnanimi (testnimi, valitud täpitähtede jm eritärkide sisalduvuse pärast) |
| `profile_attributes`<br>`_translit` | Sisaldab JSON objekti ladina tähestikus profiiliatribuutidest (vt allpool translitereerimine.). Väärtustatud ainult eIDAS autentimisel |
| `amr` (_Authentication Method Reference_) | `mID` - kasutaja autentimiseks kasutatud autentimismeetod. Võimalikud väärtused: `mID` - mobiil-ID, `idcard` - Eesti ID-kaart, `eIDAS` - piiriülene, `banklink` - pangalink, `smartid` - Smart-ID  |
| `state` | `abcdefghijklmnop` - turvaelement. Autentimispäringu `state` parameetri väärtus.  |
| `nonce` | `qrstuvwxyzabcdef` - turvaelement. Autentimispäringu `nonce` parameetri väärtus. Väärtustatud ainult juhul kui autentimispäringus saadeti `nonce` parameeter. |
| `acr` (_Authentication Context Class Reference_) | `high` - autentimistase, vastavalt eIDAS tasemetele. Võimalikud väärtused: `low` (madal), `substantial` (märkimisväärne), `high` (kõrge). Elementi ei kasutata, kui autentimistase ei kohaldu või pole teada |
| `at_hash` | `X0MVjwrmMQs/IBzfU2osvw==` - pääsutõendi räsi. TARA-s ei kasutata |
| `email` | `60001019906@eesti.ee` - kasutaja e-postiaadress. Väljastatakse ainult  Eesti ID-kaardiga kasutaja autentimisel. Loetakse kasutaja autentimissertifikaadi SAN laiendist (RFC822 tüüpi `Subject Alternative Name` väljast) |
| `email_verified` | `false` - tähendab, et e-postiaadressi kuulumine kasutajale on tuvastatud. TARA väljastab alati väärtuse `false`. See tähendab, et TARA ei kontrolli ega väljasta teavet, kas kasutaja on oma eesti.ee e-postiaadressi suunanud või mitte. |


Identsustõend võib sisaldada muid OpenID Connect protokolli kohaseid välju, kuid neid teenuses ei kasutata. 

Klientrakendus peab identsustõendile järgi tulema kohe tagasisuunamispäringu saamisel. Kui identsustõendit ei pärita `5..10` minuti jooksul, siis identsustõend aegub ja autentimisprotsessi tuleb korrata.

### 4.4 Kasutajainfopäring

Kasutajainfopäring võimaldab kehtiva  `OAuth 2.0` pääsutõendi alusel küsida infot autenditud kasutaja kohta. Päring peab olema esitatud HTTP GET meetodil. Kehtiva pääsutõendi korral väljastatakse JSON vormingus vastus.

Pääsutõend tuleb esitada kasutajainfot väljastavale otspunktile [Bearer Token meetodil](https://tools.ietf.org/html/rfc6750#section-2.1) HTTP päises (soovituslik) või [URLi parameetrina](https://tools.ietf.org/html/rfc6750#section-2.3).

Näide 1 - pääsutõendi edastamine `Authorization` päises:
````
GET /oidc/profile HTTP/1.1
Host: tara.ria.ee
Authorization: Bearer AT-20-qWuioSEtFhYVdW89JJ4yWvtI5SaNWep0
````

Näide 2 - pääsutõendi edastamine `access_token` parameetrina :
````
GET /oidc/profile?access_token=AT-20-qWuioSEtFhYVdW89JJ4yWvtI5SaNWep0 HTTP/1.1
Host: tara.ria.ee
````

Kehtiva pääsutõendi korral väljastatakse JSON vormingus vastus.

Näide:

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

Vastuses esitatavad väited väljastatakse identsustõendi alusel. 

| json element (väide) | väljastamine kohustuslik | selgitus | 
|:-----------------------|---------------------|-------------------|
| `auth_time` | jah | Kasutaja eduka autentimise ajahetk. Unix *epoch* vormingus |
| `sub` (_Subject_) | jah | Vormingult ja tähenduselt sama, mis `sub` identsustõendis |
| `given_name` | jah | Vormingult ja tähenduselt sama, mis `profile_attributes.given_name` identsustõendis |
| `family_name` | jah | Vormingult ja tähenduselt sama, mis `profile_attributes.family_name` identsustõendis |
| `amr` | jah | Vormingult ja tähenduselt sama, mis `amr` identsustõendis |
| `date_of_birth` |  ei <sup>1</sup> | Vormingult ja tähenduselt sama, mis `profile_attributes.date_of_birth` identsustõendis |
| `email` | ei  <sup>1</sup> | Vormingult ja tähenduselt sama, mis `email` identsustõendis |
| `email_verified` | ei  <sup>1</sup> | Vormingult ja tähenduselt sama, mis `email_verified` identsustõendis |
| `acr` | ei  <sup>1</sup> | Vormingult ja tähenduselt sama, mis `acr` identsustõendis |

 <sup>1</sup> Väljastatakse ainult juhul, kui antud väide on esitatud ka identsustõendis.


**Vigade käsitlemine**

Juhul kui kasutajainfo otspunktile esitatav pääsutõend puudub või on aegunud tagastatakse veakood ja lühikirjeldus `WWW-Authenticate` päises vastavalt [OpenID Connect Core 1.0 spetsifikatsioonile](https://openid.net/specs/openid-connect-core-1_0.html#UserInfoError)

Näide:
````
HTTP/1.1 401 Unauthorized
WWW-Authenticate: Bearer error="invalid_token",error_description="The access token has expired"
````

## 5 Turvatoimingud

### 5.1 Identsustõendi kontrollimine

Klientrakendus peab identsustõendit kindlasti kontrollima. Teostada tuleb kõik protokollikohased (OpenID Connect ja selle alusprotokoll OAuth 2.0) kontrollid.
{: .adv}

Kontrollida tuleb:

- tõendi allkirja
- tõendi väljaandjat
- tõendi adressaati
- tõendi ajalist kehtivust.

Lähemalt nendest kontrollidest allpool. Vajadusel saate täpsemat teavet OpenID Connect ja OAuth 2.0 protokollikirjeldustest.

**Allkirja kontrollimine.** Identsustõend väljastatakse autentimisteenuse TARA poolt allkirjastatult. Allkiri vastab JWT standardile.

Allkirjaalgoritmina kasutab TARA `RS256`. Klientrakendus peab suutma vähemalt selle algoritmiga antud allkirja kontrollida. Allkirja kontrollimine on otstarbekas teostada standardse JWT teegiga, mis toetaks kõiki JWT algoritme. Seda vähetõenäoliseks, kuid siiski võimalikuks juhuks, kui `RS256`-s peaks avastatama turvanõrkus, mis tingib algoritmi vahetamise.

Allkirja kontrollimisel tuleb kasutada TARA avalikku allkirjavõtit (edaspidi "võti"). Võtme saate võtmeväljastuse otspunktist (vt allpool jaotis "Otspunktid"). 

Võti on stabiilne. Vahetame võtit vastavalt turvasoovitustele, mitte sagedamini kui 2-3 aasta tagant. Kuid ei ole välistatud erakorraline võtmevahetus võtme korrumpeerumise korral.

Võtmel on identifikaator (`kid`). Võtmeidentifikaatorite osas järgime OpenID Connect ja OAuth 2.0 soovitatud praktikat, mis teeb võimalikuks võtmevahetuse ilma teenuse katkestuseta. 

Võtmevahetusel (väga harv ja erakordne sündmus) on lühikese perioodi (5-10 min) jooksul võtmeväljastuse otspunktist nähtavad kaks võtit, kumbki oma identifikaatoriga. Muul ajal on otspunktis üksainus võti.

Näiteks praegune (märts 2019) võtme `kid`, kui vaatate võtmeväljastuse otspunktist [https://tara.ria.ee/oidc/jwks](https://tara.ria.ee/oidc/jwks), on `a8ff37`. Kui võtame kasutusele uue võtme, siis uuel võtmel saab olema teine `kid`.

Identsustõendi päringu vastuses väljastab TARA klientrakendusele ka `kid` (JWT päise (_header_) element `kid`). See `kid` osutab võtmele, mida peate väljastatud tõendi allkirja kontrollimisel kasutama.

Soovitame võtit puhverdada, kuna see vähendab TARA serveri poole tehtavate päringute arvu. Kuid aktsepteeritav on ka võtme pärimine igas autentimises.

Otstarbekas on puhverdada `kid` koos võtmega (võti ise esitatakse väärtuste `n` ja `e` paarina).

Identsustõendi saamisel peab klientrakendus kontrollima, kas saab kasutada puhverdatud võtit. Kui TARA saatis identsustõendis `kid` väärtuse, millele vastavat võtit puhvris ei ole, siis tuleb puhvrit uuendada, s.t pärida võtmeväljastuse otspunktist uus võti.

Ülalesitatu teostamist võib mõjutada, kas liidestate TARA-ga "karbitoodet", üritate hakkama saada mõne OpenID Connect teegi seadistamisega või programmeerite liidestuse ise. Teegid ja karbitooted ei tarvitse puhverdamist toetada.

**Võtmeväljastuse otspunkti usaldamine.** Klientrakendus teeb HTTPS päringuid TARA serverile, identsustõendi väljastamise ja võtmeväljastuse otspunktide vastu. Klientrakendus peab kontrollima TARA serveri sertifikaati (domeenid `tara.ria.ee` ja `tara-test.ria.ee`). Serdid nendele domeenidele on välja antud DigiCert poolt. Klientrakenduses tuleb seetõttu kas DigiCert juursert või TARA sert seada usaldusankruks. 

**Tõendi väljaandja kontrollimine.** Identsustõendi elemendi `iss` väärtus peab olema `https://tara-test.ria.ee` (TARA testkeskkonna puhul) või `https://tara.ria.ee` (TARA toodangukeskkonna puhul).

**Tõendi adressaadi kontrollimine.** Klientrakendus peab kontrollima, et saadud tõend on välja antud just temale. Selleks veenduda, et identsustõendi elemendi `aud` väärtus ühtib klientrakendusele registreerimisel väljaantud kliendinimega (_Client ID_).

**Tõendi ajalise kehtivuse kontrollimine.** Kontrollitakse kolme identsustõendis sisalduva elemendi abil - `iat`, `nbf`, `exp`. Klientrakendus kasutab kontrollimisel oma kellaaega. Kontrollida tuleks, et: 

1) "not before" ajamoment on kätte jõudnud:

`nbf <= jooksev_aeg + kellade_lubatud_erinevus` 

2) "expired" ajamoment ei ole kätte jõudnud:

`exp > jooksev_aeg - kellade_lubatud_erinevus`.

`kellade_lubatud_erinevus` väärtus valida ise. Need kontrollid on vajaliku rünnete ja sassiminekute vältimiseks.

TARA põhimõte on, et identsustõendile tuleb järgi tulla kohe, 5 minuti jooksul. Selle aja ületamisel identsustõendit ei väljastatagi.

**Seansi loomine.** Identsustõendi eduka kontrollimise järel loob klientrakendus kasutajaga seansi ("logib kasutaja sisse"). Seansi loomine ja pidamine on klientrakenduse kohustus. Kuidas seda teha, ei ole enam autentimisteenuse TARA skoobis.

Märkus. Tavaliselt peetakse veebirakendusega seanssi küpsises hoitava seansitõendi (_session token_) abil. Seansitõend võib olla juhusõneline (_opaque_) või veebitõend (JWT). Vt lähemalt [Seansihaldus](Seansihaldus). Identsustõend ei sobi otseselt seansitõendiks, sest identsustõendi kehtivusaeg väljendab tõendi väljastamise perioodi, mitte seansi kehtivusperioodi. Küll aga saab klientrakendus seansitõendi koostada identsustõendi põhjal, valides seansi sobiva kehtivusaja. 

### 5.2 Võltspäringuründe vastane kaitse

Klientrakenduses tuleb rakendada võltspäringuründe (_cross-site request forgery_, CSRF) vastaseid kaitsemeetmeid. Seda tehakse turvakoodide `state` ja `nonce` abil. `state` kasutamine on kohustuslik; `nonce` kasutamine on vabatahtlik. Kirjeldame `state` kasutamise protseduuri.

Turvakoodi `state` kasutatakse autentimispäringule järgneva tagasisuunamispäringu võltsimise vastu. Klientrakenduses tuleb teostada järgmised sammud:

1 Genereerida juhusõne, näiteks pikkusega 16 tärki: `XoD2LIie4KZRgmyc` (tähistame `R`).

2 Arvutada juhusõnest `R` räsi `H = hash(R)`, näiteks SHA256 räsialgoritmiga ja teisendades tulemuse Base64 vormingusse: `vCg0HahTdjiYZsI+yxsuhm/0BJNDgvVkT6BAFNU394A=`.

3 Lisada autentimispäringule küpsise panemise korraldus, näiteks:

`Set-Cookie ETEENUS=XoD2LIie4KZRgmyc; HttpOnly`,

kus `ETEENUS` on vabalt valitud küpsisenimi. Küpsisele tuleb rakendada atribuuti `HttpOnly`.

4 Seada p 2 arvutatud räsi parameetri `state` väärtuseks:

`GET ... state=vCg0HahTdjiYZsI+yxsuhm/0BJNDgvVkT6BAFNU394A=`

Niisiis, autentimispäringuga saadetakse kaks asja: juhusõne küpsisesse panemiseks ja juhusõnest arvutatud räsiväärtus `state` parameetris. Klientrakendus ei pea juhusõne ega räsiväärtust meeles pidama.

Tagasisuunamispäringu töötlemisel peab klientrakendus tegema järgmist:

5 Võtab päringuga tuleva küpsise `ETEENUS` väärtuse

6 Arvutab küpsise väärtusest räsi

7 Kontrollib, et räsi ühtib tagasisuunamispäringus tagasipeegeldatava `state` väärtusega.

Tagasisuunamispäringut tohib aktsepteerida ainult ülalkirjeldatud kontrolli õnnestumisel.

Kirjeldatud protseduuris on võtmetähtsusega väärtuse `state` sidumine sessiooniga. Seda tehakse küpsise abil. (See on autentimise ajutine sessioon.  Töösessiooni moodustab klientrakendus pärast autentimise edukat lõpuleviimist).

Täiendav teave: OpenID Connect protokollis kahjuks ei ole teema selgelt esitatud. Mõningast teavet saab soovi korral mitteametlikust dokumendist [OpenID Connect Basic Client Implementer's Guide 1.0](https://openid.net/specs/openid-connect-basic-1_0.html), jaotis "2.1.1.1 Request Parameters".

Soovi korral võite veel tutvuda ründe (ja kaitse) detailse seletusega: [Võltspäringurünne ja kaitse selle vastu](Volts).  

### 5.3 Logimine

Logimine peab võimaldama rekonstrueerida TARA ja klientrakenduse suhtluse käigu TARA iga kasutuse jaoks. Selleks tuleb nii TARA kui ka klientrakenduse poolel logida kõik päringud ja päringute vastused: [autentimispäring](#41-autentimisp%C3%A4ring), [tagasisuunamispäring](#42-tagasisuunamisp%C3%A4ring) ja [identsustõendipäring](#43-identsust%C3%B5endip%C3%A4ring). Kuna edastatavad andmemahud ei ole suured, siis tuleb logida nii URL kui ka identsustõend täielikul kujul. Logide säilitamistähtaja määramisel arvestada klientrakenduse olulisust. Orientiiriks pakume 1..7 aastat. Probleemide lahendamiseks pöördumisel palume esitada väljavõte logist (mis päringud TARA poole saadeti? mis saadi vastuseks?).

## 6 Otspunktid

Testteenus

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) |  [https://tara-test.ria.ee/oidc/.well-known/openid-configuration](https://tara-test.ria.ee/oidc/.well-known/openid-configuration) |
| teenuse avalik allkirjastamisvõti | [https://tara-test.ria.ee/oidc/jwks](https://tara-test.ria.ee/oidc/jwks) |
| klientrakenduse registreerimine | dünaamilist registreerimist ei toetata, registreerimine staatiliselt, `help@ria.ee` kaudu |
| autentimine (_authorization_) | [https://tara-test.ria.ee/oidc/authorize](https://tara-test.ria.ee/oidc/authorize) | 
| tõendiväljastus (_token_) | [https://tara-test.ria.ee/oidc/token](https://tara-test.ria.ee/oidc/token) | 

Toodanguteenus

| otspunkt      |                        URL      |
|---------------|---------------------------------|
| teenuseteave (_server discovery_) | [https://tara.ria.ee/oidc/.well-known/openid-configuration](https://tara.ria.ee/oidc/.well-known/openid-configuration) |
| teenuse avalik allkirjastamisvõti | [https://tara.ria.ee/oidc/jwks](https://tara.ria.ee/oidc/jwks) |
| klientrakenduse registreerimine | dünaamilist registreerimist ei toetata, registreerimine staatiliselt, `help@ria.ee` kaudu |
| autentimine (_authorization_) | [https://tara.ria.ee/oidc/authorize](https://tara.ria.ee/oidc/authorize) | 
| tõendiväljastus (_token_) | [https://tara.ria.ee/oidc/token](https://tara.ria.ee/oidc/token) | 

## 7 Soovitusi liidestajale

TARA-ga liidestamine on lihtne. Siiski on vaja töid kavandada ja hoolikalt teostada.

Liidestuja peab erilist tähelepanu pöörama, et kõik protokollikohased kontrollid saaksid tehtud - turvaelemendi `state` ja kui kasutatakse, siis ka `nonce` kontroll, identsustõendi kontroll jm. Vt [ID token validation](http://openid.net/specs/openid-connect-core-1_0.html#ImplicitIDTValidation) [Core].

Liidestamise protsess näeb välja järgmine.

Asutus peaks välja selgitama, kas ja millistes oma e-teenustes soovib TARA kasutada. Selleks tuleks tutvuda TARA [ärikirjeldusega](Arikirjeldus), teenustaseme leppega (SLA-ga), käesoleva [tehnilise kirjeldusega](TehnilineKirjeldus). Võib heita pilgu teenuse [teekaardile](Teekaart). Vajadusel pidada nõu RIA-ga, `help@ria.ee`.

Seejärel kavandada ja teostada teenuse kasutamiseks vajalik arendustöö - klientrakenduse täiendamine OpenID Connect protokolli kohase klientkomponendiga, sh testimine. Hinnanguline töömaht: kogenud arendajal u 2 päeva; kui OpenID Connect-i pole varem teinud, siis 2 nädalat. Aluseks käesolev [tehniline kirjeldus](TehnilineKirjeldus)

Arenduse valmides tuleb liidest testida. Selleks kasutatakse TARA testteenust. Asutus esitab taotluse testteenusega liitumiseks. Taotluse võib esitada juba enne arenduse algust. Taotluses teatab asutus:

- teenuse, millega soovitakse liituda (test- või toodanguteenus)
- kinnituse, et liituja on välja arendanud omapoolse liidese ja seda TARA testteenuse vastu testinud (toodanguteenusega liitumise puhul)
- e-teenuse või -teenused, mille kasutajaid soovitakse TARA abil autentida
- kasutajate arvu prognoosi
- kohustumuse kasutada teenust eesmärgipäraselt. Sh testteenust kasutada ainult testimiseks, mitte toodangus autentimiseks
- nõustumuse teenustasemega (SLA-ga)
- klientrakenduse identifikaatori ettepanek -`client_id`, OpenID Connect protokolli kohaselt
- klientrakenduse testversiooni tagasisuunamis-URL (_redirect-URL_), OpenID Connect protokolli kohaselt
- klientrakenduse testversiooni tagasisuunamis-URL juhuks, kui kasutaja soovib autentimist katkestada
- autentimismeetod või meetodid, mida soovitakse kasutada
- klientrakenduse haldaja kontaktandmed (e-post, telefon, isikukood).

Taotlus esitatakse ja edasine suhtlus teenuse haldamisel käib läbi RIA kasutajatoe, `help@ria.ee`. Vt lähemalt [RIA autentimisteenuste lehel](https://www.ria.ee/et/riigi-infosusteem/eid/partnerile.html#tara).

RIA, rahuldades taotluse:
- väljastab asutusele klientrakenduse salasõna `client_secret`. Salasõna on ette nähtud identsustõendi päringute allkirjastamiseks
- avab asutuse klientrakenduse testversioonile juurdepääsu testteenusele.

Järgneb liidestuse testimine. RIA abistab siin võimalike probleemide lahendamisel. Testimise kohta vt lähemalt: [Testimine](https://e-gov.github.io/TARA-Doku/Testimine).

Tasub pilk heita [eneseabile](Eneseabi).

Liitumine TARA toodanguteenusega. Eduka testimise järel asutus esitab taotluse toodanguteenuse avamiseks klientrakendusele. Taotluses näidatakse klientrakenduse toodanguversiooni tagasisuunamis-URL (`redirect_uri`), OpenID Connect protokolli kohaselt jm andmed

RIA, rahuldades taotluse, väljastab asutusele klientrakenduse toodanguversiooni salasõna `client_secret` ja avab asutuse klientrakenduse toodanguversioonile juurdepääsu toodanguteenusele.
