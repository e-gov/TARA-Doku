---
permalink: Autentimisprotokoll
---

# Autentimisprotokolli turvauuring

__Autentimisprotokoll__ on tehniline ja organisatsiooniline reeglistik, mis võimaldab riigi e-teenustes kasutaja autentida:
- keskse autentimisteenuse abil (hoides kokku arendus- ja käitluskulusid)
- erinevate autentimismeetoditega (nende koosseis muutub dünaamiliselt)
- sh piiriülest autentimist
- pakkudes kodanikule ühtset kasutajakogemust
- ja hoides kokku kodaniku aega ühekordse sisselogimise näol.

Autentimisprotokoll reguleerib suhtlust e-teenuse, kasutaja ja keskse autentimisteenuse vahel.

Autentimisprotokolli tähtsamad omadused:
- erinevate autentimismeetodite toetus (ID-kaart, m-ID, pangalingid, Smart-ID)
- EL piiriülese autentimise (eIDAS) toetus
- ühekordne sisselogimine (SSO)
- ühekordne väljalogimine (Single Sign-Off)

__Kasutuslood__. Autentimisprotokoll toetab järgmisi kasutuslugusid:<br>
K0 ("lihtne autentimine") - kasutaja logib sisse e-teenusesse, kasutab seda mõningase aja ja lahkub, kas välja logides või sirvikut sulgedes (kasutaja võidakse välja logida ka serveri algatusel, seansi aegumise tõttu).<br>
K1a ("ühekordne sisselogimine") - kasutaja logib sisse e-teenuses E1. Mõne aja pärast, E1-st väljumata, soovib avada ja siseneda sama sirviku teises sirvimiskontekstis (teises sakis või aknas) e-teenuse E2. Kasutaja logitakse E2-te automaatselt sisse.<br>
K1b ("sessiooni üleandmine") - kasutaja logib sisse e-teenuses E1. Mõne aja järel soovib (samas sirvimiskontekstis) liikuda e-teenusesse E2. Kasutaja logitakse E2-te automaatselt sisse.<br>
K2a ("ühekordne väljalogimine") - kasutaja on sisse logitud mitmesse e-teenusesse. Kasutaja logib välja e-teenusest E1. Ühtlasi logitakse ta välja kõigist e-teenustest.<br>
K2b ("seansi aegumine") - keskne seanss aegub. Kasutaja logitakse välja.<br>
K3a ("pidev kasutaja") - asutuse töötaja kasutab e-teenust kogu tööpäeva vältel. Ta ei pea ikka ja jälle sisse logima.<br>
K3b ("mitme teenuse pidev kasutaja") - asutuse töötaja kasutab erinevaid e-teenuseid kogu tööpäeva vältel. Ta ei pea mitmeid kordi sisse logima.

__Tehniline protokoll__. Autentimisprotokoll on rajatud OpenID Connect 1.0 protokollile (OIDC) [1]. Keskset autentimisteenust ("TARA") osutab RIA. 2017. a teostati lihtne autentimine (ilma keskse seansihalduseta ja ühekordse sisselogimiseta) [2]. Alanud on tööd piiriülese autentimise (eIDAS) lisamiseks. 

__Uurimisküsimused__.
OpenID Connect pakub erinevaid võimalusi ja jätab mitmete valikute tegemise rakendajale. Vajame kindlust ja kinnitust kolmes küsimuses:

1) kas tehtud valikud on turvalised ja kokkuvõttes teostamist väärt?
- volituskoodi voog (authorization flow)
- klientrakenduste autentimine sümmeetrilise võtme (client secret) abil? (kas PKI oleks turvalisem?)
- keskse e SSO seansi kehtivuse kontrollimiseks on klientrakendustele jäetud vabad käed? Kas see on turvalisuse seisukohast mõistlik valik? (alternatiiv on SSO seansi kehtivuse päring igal pöördumisel kasutaja sirvikust)

2) kas OpenID Connect tervikuna on asutuseülese SSO tegemiseks sobiv?

3) kas protokoll on piisavalt detailiseeritud, et saaks alustada SSO-lahenduse arendusega?
- ei ole käsitletud, kas SSO-ga liitumine peaks olema kohustuslik (vrdl Soomes on keskse autentimisteenuse kasutamine keskvalitsuse asutustele kohustuslik, kuid SSO-ga liitumine mitte)
- kas oleks võimalik määratleda kriteeriume, mille abil piiritleda SSO autentimisprotokolli rakendamiseks sobivate e-teenuste ringi?
- kas seansihalduseks tuleks kasutada postMessage API põhist lahendust, vt [3]?
- kas ühekordne väljalogimine tuleks teostada OpenID Connect vastavate standardikavandite [4] järgi?
- kas protseduurilised ja _policy_ aspektid on piisavalt detailiseeritud, et lahendus oleks turvaline? Nt kas back-channel logout-le tuleks kehtestada ühtsed, kohustuslikud sessiooniaegumisajad?

Turvalisus hindamisel tuleks lisaks ründevõimalustele käsitleda ka riske veidi laiemalt: "keskne nuripunkt" ja kas kasutaja saab aru.

[1] RIA SSO autentimisteenuse kavand, https://github.com/ria-eidas/RIA-autentimisteenus/wiki/Teenuse-kontseptsioon.<br>
[2] TARA autentimisteenus, https://e-gov.github.io/TARA-Doku/.<br>
[3] OpenID Connect Session Management, http://openid.net/specs/openid-connect-session-1_0.html.<br>
[4] OpenID Connect Front-Channel Logout, http://openid.net/specs/openid-connect-frontchannel-1_0.html; OpenID Connect Back-Channel Logout, http://openid.net/specs/openid-connect-backchannel-1_0.html.<br>

