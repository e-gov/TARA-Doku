---
permalink: Juhend
---

# TARA autentimisteenus. Tehnilised tingimused ja liidestamise juhend

Vt ka [Ärikirjeldus](Arikirjeldus)

## Mõisted

- _asutus_, autentimisteenust kasutav Eesti avaliku sektori asutus
- _e-teenus_, asutuse poolt kasutajale pakutav e-teenus
- _autentimisteenus_, RIA pakutav autentimisteenus
- _kasutaja_, e-teenust kasutav füüsiline isik, omab Eesti mobiil-ID-d
- _rakendus_, asutuse veebirakendus, mis pakub e-teenust; koosneb kahest osast: 1) kasutaja sirvijasse laetav osa; 2) serveripoolne osa
- _volituskood_, (_authorization code_), juhuarv, mille esitamisel rakendus saab autentimisteenuselt identsustõendi ja pääsutalongi.
- _identsustõend_, (_ID token_), autentimisteenuse poolt rakendusele väljastatav teave kasutaja tuvastatud identiteedi kohta (isikukood ja tehnilised andmed)
- _pääsutalong_, (_access token_), autentimisteenuse poolt rakendusele väljastatav allkirjastatud teave

## Autentimisprotsess

Autentimisprotsess vastab OpenID Connect 1.0 protokollile (_authorization code_ kasutusvoog). 

1 Kasutaja vajutab nupule "Logi sisse".

2 Rakendus moodustab OpenID Connect autentimispäringu ja saadab sirvijale korralduse kasutaja suunamiseks autentimisteenusesse (HTTP _redirect_). Autentimispäringu näide:

````
HTTP GET https://auth.ria.ee/login?
redirect_uri=https%3A%2F%2eteenindus.asutus.ee%2FCallback&
scope=user&
state=hkMVY7vjuN7xyLl5&
response_type=code&
client_id=58e7ba35aab5b4f1671a
````
Ümbersuunamis-URL-is on kuus autentimiseks vajalikku teabeelementi:
- autentimisteenuse URL `https://auth.ria.ee/login`
- tagasipöördumis-URL `https://eteenindus.asutus.ee/Callback` (asutus valib selle ise)
- õigused, mida rakendus kasutajalt küsib (`scope` = `user` (kasutaja profiiliandmed)
- turvakaalutlusel nõutav autentimise unikaalne identifikaator (server genereeris selle juhuslikult) `state`
- autentimise tulemuse serverile edastamise viis; toetatud on viis  `code`
- rakenduse identifikaator `client_id`, mille RIA annab asutusele e-teenuse registreerimisel autentimisteenuse kasutajaks.

3 Autentimisteenus esitab kasutajale lehe "Elektrooniline isikutuvastus". Lehel on esitatud toetatavad autentimismeetodid (esialgu üks - mobiil-ID).

4 Kasutaja teeb läbi mobiil-ID-ga autentimise. 

5 Autentimisrakendus suunab kasutaja tagasi rakendusse (rakenduse poolt kaasa antud naasmisaadressile), andes kaasa volituskoodi (_authorization code_). Tagasisuunamispäringu näide:

````
HTTP GET https://eteenindus.asutus.ee/Callback?
code=71ed5797c3d957817d31&
state=OFfVLKu0kNbJ2EZk
````
Tagasisuunamispäringus paneb autentimisteenus kaasa turvakoodi (`code=71ed5797c3d957817d31`) ja rakenduse saadetud unikaalse identifikaatori (`state=OFfVLKu0kNbJ2EZk`). Turvakood on ühekordne “lubatäht” identsustõendi ja pääsutalongi saamiseks. Unikaalne identifikaator (`state`) aitab tagada, et erinevate kasutajate autentimised sassi ei lähe ja ründaja ei saa protsessi vahele sekkuda.

6 Rakendus küsib autentimisteenuselt, volituskoodi esitades,  identsustõendi (_ID token_) ja soovi korral ka pääsutalongi (_access token_).

7 Autentimisteenus kontrollib, et identsustõendit küsib õige rakendus, koostab identsustõendi ja pääsutalongi ning tagastab need rakendusele.

8 Rakendus loob saadud identsustõendi (ja soovi korral pääsutalongi) alusel kasutajaga seansi. Seansi loomine ja pidamine on rakenduse kohustus. Kuidas seda teha, ei ole autentimisteenuse skoobis.

## Viited

[1] OpenID Connect Core 1.0, 
[http://openid.net/specs/openid-connect-core-1_0.html](http://openid.net/specs/openid-connect-core-1_0.html)
