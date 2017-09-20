---
permalink: Juhend
---

# Tehnilised tingimused ja liidestamise juhend

Vt ka:
- [Ärikirjeldus](Arikirjeldus)
- [Sonastik](Sonastik)

Märkus. Punase joonega on ümbritsetud omadused, mis teostatakse teenuse teises arendusjärgus.
{: .note}

## Autentimisprotsess

Autentimisprotsessi aluseks on OpenID Connect 1.0 protokoll, täpsemalt volituskoodi  (_authorization code_) kasutusvoog.

1 ***Autentimispäringu saatmine***

Kasutaja vajutab nupule "Logi sisse" vms. Rakendus võib ka ise algatada autentimise.

Rakendus moodustab OpenID Connect autentimispäringu ja saadab sirvijale korralduse kasutaja suunamiseks autentimisteenusesse (HTTP _redirect_). Autentimispäringu näide:

````
HTTP GET https://auth.ria.ee/login?
redirect_uri=https%3A%2F%2eteenindus.asutus.ee%2FCallback&
scope=user&
state=hkMVY7vjuN7xyLl5&
response_type=code&
client_id=58e7ba35aab5b4f1671a
````

Ümbersuunamis-URL-is on kuus autentimiseks vajalikku teabeelementi:

| URL-i element          | näide                       |  selgitus     |
|------------------------|-----------------------------|---------------|
| protokoll, host ja tee | `https://tara.eesti.ee/login` |               |
| tagasipöördumis-URL    | `https://eteenindus.asutus.ee/Callback` | tagasipöördumis-URL-i valib asutus ise |
| autentimise skoop      | `scope=openid`               |        |
| turvakood              | `state=hkMVY7vjuN7xyLl5`     | rakenduse serveripool genereerib turvakoodi |
| autentimise tulemuse serverile edastamise viis; toetatud on volituskoodi viis `code` | `response_type=code` |   |
| rakenduse identifikaator `client_id` | `client_id=58e7ba35aab5b4f1671a` | rakenduse identifikaatori annab RIA asutusele e-teenuse registreerimisel autentimisteenuse kasutajaks |

2 ***Autentimismeetodi valik***

3 Autentimisteenus esitab kasutajale lehe "TARA isikutuvastusteenus" vm samasisuline tekst. Lehel on esitatud toetatavad autentimismeetodid (esialgu üks - mobiil-ID).

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

## Minimaalne autentimistase

Rakendus võib autentimispäringus esitada minimaalse autentimistaseme, mille korral kasutaja e-teenusesse lubatakse. Minimaalne autentimistase esitatakse parameetriga `loamin=väärtus`, kus väärtus on autentimistaseme eIDAS-kood. Näiteks:
{: .note}

`loamin=`http://eidas.europa.eu/LoA/high`

Kasutajale esitatakse autentimisteenuses ainult need autentimismeetodid, mille autentimistase on võrdne või suurem päringus esitatud minimaalsest autentimistasemest.
{: .note}


