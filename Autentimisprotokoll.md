# Autentimisprotokolli turvauuring

__Autentimisprotokoll__ on tehniline ja organisatsiooniline reeglistik, mis võimaldab riigi e-teenustes kasutaja autentida:
- keskse autentimisteenuse abil (hoides kokku arendus- ja käitluskulusid)
- erinevate autentimismeetoditega (nende koosseis muutub dünaamiliselt)
- sh piiriülest autentimist
- pakkudes kodanikule ühtset kasutajakogemust
- ja hoides kokku kodaniku aega ühekordse sisselogimise näol.

<img src='img/3-1.PNG' style='width: 400px;'>

Autentimisprotokoll reguleerib suhtlust e-teenuse, kasutaja ja keskse autentimisteenuse vahel.

Autentimisprotokolli tähtsamad omadused:
- erinevate autentimismeetodite toetus (ID-kaart, m-ID, pangalingid, Smart-ID)
- EL piiriülese autentimise (eIDAS) toetus
- ühekordne sisselogimine (SSO)
- ühekordne väljalogimine (Single Sign-Off)

__Kasutuslood__. Autentimisprotokoll toetab järgmisi kasutuslugusid:

`K0` ("lihtne autentimine") - kasutaja logib sisse e-teenusesse, kasutab seda mõningase aja ja lahkub, kas välja logides või sirvikut sulgedes (kasutaja võidakse välja logida ka serveri algatusel, seansi aegumise tõttu).

`K1a` ("ühekordne sisselogimine") - kasutaja logib sisse e-teenuses E1. Mõne aja pärast, E1-st väljumata, soovib avada ja siseneda sama sirviku teises sirvimiskontekstis (teises sakis või aknas) e-teenuse E2. Kasutaja logitakse E2-te automaatselt sisse.

`K1b` ("sessiooni üleandmine") - kasutaja logib sisse e-teenuses E1. Mõne aja järel soovib (samas sirvimiskontekstis) liikuda e-teenusesse E2. Kasutaja logitakse E2-te automaatselt sisse.

`K2a` ("ühekordne väljalogimine") - kasutaja on sisse logitud mitmesse e-teenusesse. Kasutaja logib välja e-teenusest E1. Ühtlasi logitakse ta välja kõigist e-teenustest.

`K2b` ("seansi aegumine") - keskne seanss aegub. Kasutaja logitakse välja.

`K3a` ("pidev kasutaja") - asutuse töötaja kasutab e-teenust kogu tööpäeva vältel. Ta ei pea ikka ja jälle sisse logima.

`K3b` ("mitme teenuse pidev kasutaja") - asutuse töötaja kasutab erinevaid e-teenuseid kogu tööpäeva vältel. Ta ei pea mitmeid kordi sisse logima.

__Tehniline protokoll__. Autentimisprotokoll on rajatud OpenID Connect 1.0 protokollile (OIDC) [1]. Keskset autentimisteenust ("TARA") osutab RIA. 2017. a teostati lihtne autentimine (ilma keskse seansihalduseta ja ühekordse sisselogimiseta) [2]. Alanud on tööd piiriülese autentimise (eIDAS) lisamiseks. 

__Uurimisküsimused__.
OpenID Connect pakub erinevaid võimalusi ja jätab mitmete valikute tegemise rakendajale. Vajame kindlust ja kinnitust kolmes küsimuses:

1\. kas tehtud valikud on turvalised ja kokkuvõttes teostamist väärt?
1.1. volituskoodi voog (authorization flow)
1.2. klientrakenduste autentimine sümmeetrilise võtme (client secret) abil? (kas PKI oleks turvalisem?)
1.3 keskse e SSO seansi kehtivuse kontrollimiseks on klientrakendustele jäetud vabad käed? Kas see on turvalisuse seisukohast mõistlik valik? (alternatiiv on SSO seansi kehtivuse päring igal pöördumisel kasutaja sirvikust)

2\. kas protokoll tervikuna on asutuseülese SSO tegemiseks sobiv?
2.1. hinnang "keskse nuripunkti" probleemile?
2.2. kas kasutaja kokkuvõttes saab aru, millal ja kuhu ta on sisse või välja loginud? 

3\. kas protokoll on piisavalt detailiseeritud, et saaks alustada SSO-lahenduse arendusega?
3.1. ei ole käsitletud, kas SSO-ga liitumine peaks olema kohustuslik (vrdl Soomes on keskse autentimisteenuse kasutamine keskvalitsuse asutustele kohustuslik, kuid SSO-ga liitumine mitte)
3.2. kas oleks võimalik määratleda kriteeriume, mille abil piiritleda SSO autentimisprotokolli rakendamiseks sobivate e-teenuste ringi?
3.3. kas seansihalduseks tuleks kasutada postMessage API põhist lahendust, vt [3]?
3.4. kas ühekordne väljalogimine tuleks teostada OpenID Connect vastavate standardikavandite [4] järgi?
3.5. kas protseduurilised ja _policy_ aspektid on piisavalt detailiseeritud, et lahendus oleks turvaline? Nt kas back-channel logout-le tuleks kehtestada ühtsed, kohustuslikud sessiooniaegumisajad?

__Viited__<br>
[1] RIA SSO autentimisteenuse kavand, https://github.com/ria-eidas/RIA-autentimisteenus/wiki/Teenuse-kontseptsioon.<br>
[2] TARA autentimisteenus, https://e-gov.github.io/TARA-Doku/.<br>
[3] OpenID Connect Session Management, http://openid.net/specs/openid-connect-session-1_0.html.<br>
[4] OpenID Connect Front-Channel Logout, http://openid.net/specs/openid-connect-frontchannel-1_0.html; OpenID Connect Back-Channel Logout, http://openid.net/specs/openid-connect-backchannel-1_0.html.<br>

