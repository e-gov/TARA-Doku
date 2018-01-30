---
permalink: Veakasitlus
---

# Vead ja tõrked. Ennetamine ja käsitlemine

Käesoleva dokumendi eesmärk on aidata kavandada ja rakendada tehnilised ja korralduslikud meetmed vigade ja tõrgete vältimiseks, sh kommunikatsiooniteed tõrke- või veaolukordade esinemisel. 

Vt [joonis 1 Ülevaade](https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#1-%C3%BClevaade).

Lahendada:
- Kuidas käsitletakse autentimise ebaõnnestumised, olukorrad, kus mõni osateenus on maas jm tõrked?
  - Kuidas saadakse teada?
  - Kuidas teavitatakse?
- Kelle poole kasutaja vajadusel pöördub?
  - Kuidas kasutaja pöördumine lahendatakse?
  - Kuidas selgitatakse välja vea põhjus? Kuidas osapooled suhtlevad?

Lähtekohtad:<br>
1) autentimine TARA-teenuses on tervikprotsess, mille õnnestumine sõltub mitmete komponentide ja osateenuste toimimisest;<br>
2) autentimise tulemusest (OK, NOK) antakse kasutajale teada e-teenuses;<br>
3) tõrkena käsitleme ka olukorda, kus kasutaja ei saa aru, mis ta valesti tegi (nt pani ID-kaardi valetpidi kaardilugejasse);<br>
4) kasutaja esmane pöördumispunkt on e-teenuse kasutajatugi;<br>
5) kaalume e-teenuse kasutajatoe kontaktteabe kogumist klientrakenduse registreerimisel ja kuvamist TARA kasutajadialoogis.

| viga v tõrge   | ennetavad meetmed, lahendamine  |
|----|-----|
| valesti implementeeritud protokoll (klientrakenduses) | Paar kõige tähtsamat nõuet lisatud liitumisjuhendisse (jaotis 2) |
| eIDAS konnektorteenus on maas | |
| SK DigiDocService on maas | |
| SK OCSP teenus on maas | |
| välisriigi eIDAS vahendusserver on maas | |
| välismaalasel ei õnnestu autentida | |
| eestlasel ei õnnestu autentida | |
| TARA autentimisteenus on maas | |
| kasutaja esitas valed kredentsiaalid (nt vale PIN1) | TARA-teenus saadab klientrakendusele veakoodi (vt [Core], jaotis [3.1.2.6 Authentication Error Response](http://openid.net/specs/openid-connect-core-1_0.html#AuthError))  |
