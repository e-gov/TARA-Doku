---
permalink: 3JARK
---

# 3. arendusjärk ("eIDAS"). Lähteülesanne
{: .no_toc}

- TOC
{:toc}

Märkus. Kasutame [sõnastiku](Sonastik) mõisteid.

## Eesmärk

3\. arendusjärgu eesmärk on lisada TARA-teenusele eIDAS piiriülese autentimise võimekus. Tööde tulemusena peab  Eesti e-teenusel olema võimalik saata välismaalane TARA ja eIDAS piiriülese autentimise taristu kaudu autentimiseks välisriigi autentimisteenusesse ja saata kätte autentimist kinnitav eIDAS autentimistõend.

Arendusjärk on jätkuks TARA-teenuse [1.](1JARK) ja [2.](2JARK) arendusjärgule.

## Arhitektuur

TARA-teenuse üldistatud komponentmudel on esitatud joonisel 1.

<img src='img/3JARK.PNG' style='width: 600px;'>

Joonis 1. Piiriülese võimekusega TARA autentimisteenus

## TARA serverrakendus

1\. ja 2. arendusjärguga on loodud TARA serverrakendus, teostab Eesti e-teenust kasutava eestlase autentimist (joonisel 1 kasutusvoog 1). Suhtlus klientrakendusega toimub [OpenID Connect](Viited#1-1) protokolli kohaselt. TARA serverrakendus on ehitatud [CAS platvormile](Viited#3-1).

## Teostatav kasutusvoog

3\. arendusjärgus teostatakse kasutusvoog 3a ("Eesti e-teenust kasutava välismaalase autentimine TARA kaudu") (Vt joonis 1). Kasutusvoog koosneb järgmistest sammudest:

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

## Tööde koosseis

Tuleb teostada:

| nr | töö |
|----|-----|
| 1  | autentimismeetodi valiku kuva täiendamine piiriülese autentimismeetodi valimisega |
| 2  | SAML autentimispäringu moodustamine |
| 3  | välismaalase suunamine RIA eIDAS konnektorteenusesse |
| 4  | välismaalase vastuvõtmine RIA eIDAS konnektorteenusest |
| 5  | SAML autentimisvastuse dekrüpteerimine ja valideerimine |
| 6  | välismaalase tagasisuunamine autentimisrakendusse |
| 7  | identsustõendi moodustamine |
| 8  | identsustõendi väljastamine klientrakendusele |
| 9  | eIDAS nõuete kohased logimised (vrdl [eIDAS-Node Error and Event Logging](Viited#2-4)) |
| 10 | SAML metaandmeotspunkti teostus |

Arendustööd hõlmavad:
- kavandamist, sh
  - tehnilist kavandamist
  - projektijuhtimist
- programmeerimist, sh
  - koodi läbivaatust
- testimist
- dokumenteerimist.

Ettevalmistavate ja kaasnevate töödena:
- tutvumine olemasoleva TARA-teenuse
  - dokumentatsiooniga
  - koodiga
- tutvumine eIDAS Node tarkvara
  - dokumentatsiooniga
  - koodiga.

## Riskid

| risk | kirjeldus | ohuhinnang |
|------|-----------|-------------|
| eIDAS Node tarkvara ebastabiilsus | eIDAS konnektorteenuse tarkvara ei ole kahjuks veel stabiilne. Sept 2017 avaldati eIDAS Node v1.4. See versioon on RIAs paigaldatud. Euroopa Komisjonil on töös v2.0, milles suur muutus saab olema SAML 2-lt üleminek SAML 3.0-le (OpenSAML). Komisjoni sõnul v2.0 tuleb jaan-veebr 2018. | võib oluliselt ohustada töid (vajadus ümber teha) |
| eIDAS "protokolli" muutumine | Praegu kehtib eIDAS tehniline spetsifikatsioon v1.1. Kavas on seda uuendada, kuid tähtaegu teada ei ole. | Ohtu hindame väikeseks. |
| eIDAS Node tarkvara dokumentatsiooni puudulikkus | Komisjon on täiendanud eIDAS Node-i tehnilist dokumentatsiooni. Seda tehti meie soovi peale. Palgati professionaalne _technical writer_, kes on teinud head tööd. Siiski ei saa dokumentatsiooni lugeda perfektseks. | Ohtu meie töödele hindame keskmiseks. |

## Tööde planeerimine 

Arendusjärgu eesmärk on suhteliselt konkreetne ja selge. Siiski on arendusjärgus mitu tegurit, mis raskendavad töömahtude ja edenemistempo ennustamist. Seetõttu jagame arendusetapi küll konkreetsete tulemitega ja konkreetsete tähtaegadega töödeks (vt ülal), kuid tegeliku edenemistempo alusel vajadusel korrigeerime nii mahtusid kui ka tähtaegu.

