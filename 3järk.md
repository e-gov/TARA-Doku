---
permalink: 3JARK
---

# 3. arendusjärk ("eIDAS")

Ülesanne

Märkus. Kasutame [sõnastiku](Sonastik) mõisteid.

Joonis 1. Piiriülese võimekusega TARA autentimisteenus

<img src='img/3JARK.PNG' style='width: 600px;'>

3\. arendusjärgu eesmärk on lisada TARA-teenusele eIDAS piiriülese autentimise võimekus. Tööde tulemusena peab  Eesti e-teenusel olema võimalik saata välismaalane TARA ja eIDAS piiriülese autentimise taristu kaudu autentimiseks välisriigi autentimisteenusesse ja saata kätte autentimist kinnitav eIDAS autentimistõend.

Üldistatud komponentmudel on esitatud joonisel 1. 3. arendusjärgus teostatakse kasutusvoog 3a ("Eesti e-teenust kasutava välismaalase autentimine TARA kaudu"). 

## Kasutusvoo 3a tehniline teostus

Samm-sammult näeb kasutusvoog välja järgmine:

1\. Välismaalane avaldab TARA-ga liidestatud Eesti-e teenuses soovi sisse logida.

2\. e-teenus (TARA suhtes klientrakendus) suunab välismaalase TARA teenusesse.<br>
\- suunamine tehakse veebisirvija ümbersuunamiskorralduse abil, vastavalt OpenID Connect protokollile. Vt [Tehniline kirjeldus](TehnilineKirjeldus).

3\. Välismaalane saabub TARA-teenusesse, autentimismeetodi valiku lehele.

4\. Välismaalane valib välisriigi.<br>
\- kui välisriigist osaleb eIDAS-skeemis mitu identiteedisüsteemi, siis valib ka identiteedisüsteemi.

5\. TARA-teenus koostab SAML autentimispäringu.<br>
\- TARA teenus allkirjastab ja sõltuvalt seadistusest ka krüpteerib autentimispäringu.

6\. TARA-teenus suunab välismaalase RIA eIDAS konnektorteenusesse.<br>
\- ümbersuunamiskorralduses pannakse kaasa SAML autentimispäring.

7\. RIA eIDAS konnektorteenus korraldab välismaalase suunamise välisriiki, autentima.

8\. RIA eIDAS konnektorteenus suunab välisriigist tagasi saabunud välismaalase tagasi TARA-teenusesse.<br>
- tagasisuunamisega koos edastab RIA eIDAS konnektorteenus allkirjastatud - ja vastavalt seadistusele - krüpteeritud eIDAS autentimisvastuse.

9\. TARA-teenus võtab vastu tagasisuunatud välismaalase, dekrüpteerib autentimisvastuse ja kontrollib allkirja.

10\. TARA-teenus, vastavalt OpenID Connect protokollile (volituskoodi voog) edastab tulemuse klientrakendusele.

11\. Klientrakendus, eduka autentimise korral pärib, vastavalt OpenID Connect protokollile TARA-teenuselt identsustõendi.

12\. TARA-teenus väljastab identsustõendi.

Kasutusvoogu on kujutatud ka [RIA SSO autentimisteenuses kavandis](Viited#4-3) oleval järgnevusdiagrammil.



| teetähis | tööd | tulemus | orienteeruv, minimaalne ajakava |
|----------|------|---------|--------------|


