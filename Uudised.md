---
permalink: Uudised
---

# Uudised

18.02.2019 Kliendi terava tähelepaneku põhjal täpsustasime identsustõendi kehtivuse arvutust (seda ei teosta TARA, vaid peab teostama klientrakendus: ). Tõend kehtib, kuid on täidetud tingimused: 
`nbf <= jooksev_aeg + kellade_lubatud_erinevus` ja `exp > jooksev_aeg - kellade_lubatud_erinevus`. Vt [veebitõendi standard](https://tools.ietf.org/html/rfc7519), jaot 4.1.4-5.
{: .note}

12.12.2018 Protokollimuudatus. TARA kasutajaliideses keele määramiseks tuleb praegu autentimispäringus kasutada parameetrit `locale` (vt [Tehniline kirjeldus](TehnilineKirjeldus#41-autentimisp%C3%A4ring)). OpenID Connect protokolliga parema vastavuse tagamiseks teeme muudatuse: keelt hakkab määrama parameeter `ui_locales` (vt [OpenID Connect spetsifikatsioon](https://openid.net/specs/openid-connect-core-1_0.html#AuthRequest)). Muudatuse üleminekuperiood on kuus kuud: `ui_locales` saab kasutatavaks jaanuari 2019 lõpus;  `locale` kasutuse lülitame välja juuli 2019 lõpus. Üleminekuperioodil on paralleelselt kasutatavad mõlemad parameetrid.
{: .note}