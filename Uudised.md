---
permalink: Uudised
---

# Uudised



05.05.2021 Riigi autentimisteenuse üleminek uuele tarkvara platvormile TARA2. Uuele TARA2 versioonile migreerime kõik kliendid demokeskkonnas ajavahemikus 05.-06.05.2021. Uus TARA2 platvorm on tagasiühilduv ning senine TARA dokumentatsiooni tehniline kirjeldus on endiselt kehtiv.
Liidestunud klientide jaoks ei tohiks probleeme tekkida, kui liidestamisel on järgitud tehnilises dokumentatsioonis antud soovitusi, kuid eriti karbitoodete puhul tuleks üle kontrollida järgnevad täiendused/muutused:

- Toetatakse ainult TARA tehnilises dokumentatsioonis toodud skoope ning tundmatute skoopide korral tagastatakse viga. NB! banklink skoobi kasutus ei ole TARA2 teenuses toetatud!
- state parameetril on miinimum pikkus 8 tähemärki (TARA dokumentatsioonis on soovitatud kasutada 16 tähemärgi pikkust väärtust).
- Tagasisuunamispäringus tagastatakse ka kasutatud scope parameeter koos kasutatud väärtustega.
- oidc/jwks otspunkti väljundisse lisandub parameeter use.
{: .note}

Palume kindlasti kontrollida oma teenuse tööd demokeskkonnas ja kui esineb mingisuguseid probleeme, siis palun võtke kindlasti meiega ühendust help@ria.ee.
TARA2 toodangukeskkonda migreerime kliendid (eeldatavalt) 31.05.2021. TARA2 platvormi koodi avalikustame GitHubi repodes lähimal ajal.
{: .note}

14.12.2020 Alates 09.12.2020 ei ole võimalik TARA test keskkonnas kasutada Mobiil-ID testimiseks isiklikku Mobiil-ID numbrit. Testida saab ainult kasutades testnumbreid. Rohkem infot [siit.](https://www.skidsolutions.eu/uudised/mobiil-id-demo-keskkond-ei-ole-tehnilistel-pohjustel-kattesaadav/)
{: .note}

23.03.2020 Anname teada, et Smart-ID tagatistase on (üle)hindamisel, sest SK ID Solutioni Smart-ID väljastusprotsessile lisandus biomeetriline lahendus. Hinnangu valmimisel avaldab RIA Smart-ID kinnitatud taseme.
{: .note}

19.03.2020 Alates 30.04.2020 muutub TARA-s kasutajale vaikimisi kuvatavate autentimisvalikute nimekiri. Kui seni kuvati kasutajale kõik autentimisvahendid (ID-kaart, Mobiil-ID, pangalink, Smart-ID ja eIDAS), siis alates muudatuse rakendamise hetkest kuvatakse vaikimisi ainult kõrge tagatistasemega autentimisvahendeid (ID-kaart ja Mobiil-ID).
Kõiki autentimisvahendeid on endiselt võimalik kasutada, kuid liitunud infosüsteem peab soovitud autentimisvalikute kuvamist sellisel juhul ise juhtima, kasutades TARA skoope (loe rohkem siit - https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#413-autentimismeetodite-valikuline-kasutus)
Midagi ei muutu klientide jaoks, kes juba kasutavad autentimismeetodeid valikuliselt.
{: .note}

25.12.2019 Protokollitäiendus. Lisandus võimalus piiriülese (eIDAS) autentimise valiku korral kasutada TARA ilma kasutajaliidest kuvamata.
Otse eIDAS võrgustikku suunamiseks ilma kasutajaliidese kuvamiseta peab skoobi URL parameeter välja nägema taoline:
`.../authorize?scope=openeid eidasonly eidas:country:fi&...`.
Tundmatuid, valesti kirjeldatud ja mitte väärtustatud attribuute ignoreeritakse. Kõik skoobi atribuudid peavad olema kirjeldatud väikeste tähtetega, kaasaarvatud 'eidas:country:' väärtus, mis ISO 3166-1 alpha-2 standardi kohaselt enamasti suurte tähtedega kirjeldatud. Seega ka suurte tähtedega kirjeldatud korrektset riigikoodi ignoreeritakse.
Otsene eIDAS võrgustikku suunatakse alati kui esinevad eelnevas näites kirjeldatud skoobi atribuudid, isegi kui esineb teisi valiidseid atribuute.
TARA-server metateabe kaudu kätte saadud 'scopes_supported' väärtus koostatakse järgnevalt:
konfiparameetri `cas.authn.oidc.scopes` väärtused
konfiparameetri `eidas.available-countries` iga riigikoodi väärtuse x kohta dünaamiliselt koostatud `eidas:country:x`.
{: .note}

11.09.2019 Alates 11. septembrist 2019 lisandub TARA toodangukeskkonda autentimisvahendina Smart-ID.
Toodangukeskkonna kliendid, kes rakendavad autentimismeetodite valikulist kasutust ja soovivad smart-id, soovitame üle vaadata parameetri `scope` väärtus ja veenduda, et skoop `smartid` oleks lubatud.
{: .note}

05.09.2019 Alates 10. septembrist 2019 peavad EL-i liikmesriigid lubama autentida Itaalia eID vahendiga, mis on hinnatud tasemele märkimisväärne. See tähendab, et sellest kuupäevast peavad kõik avaliku sektori avalikud e-teenused, kes lubavad oma kodanikul kasutada sisse logimiseks märkimisväärse või madala tasemega eID vahendeid, lubama autentida ka Itaalia märkimisväärse autentimistasemega eID vahendiga. eIDAS autentimistasemele kõrge vastavad näiteks Eesti ID-kaart ja mobiil-ID, tasemele madal vastavad aga kõik ülejäänud hindamata eID skeemid, sh panga PIN-kalkulaator.
Välistamaks ligipääsu soovitust madalama turvalisusastmega ülepiirilistele autentimisvahenditele, peab ülepiirilise autentimise korral kontrollima, et identsustõendi acr väites esitatud autentimistase ei oleks väiksem minimaalsest lubatud autentimistasemest (loe autentimistasemete kohta [siit](https://www.ria.ee/sites/default/files/content-editors/EID/autentimislahendustele-kehtivad-nouded.pdf).
Näiteks, kui liidestuja soovib kasutada vaid kõrge eIDAS autentimistasemega autentimisvahendeid ja täpsustab `acr_values` parameetris `high` väärtuse, tohib aktsepteerida ainult identsustõendeid, mille `acr` väite väärtus on `high`.
Juhul kui autentimispäringus eIDAS autentimistaset `acr_values` parameetri abil ei täpsustatud, peab identsustõendis olev väärtus olema `substantial` või `high`.
[Täpsemalt](https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#5-turvatoimingud).
{: .note}

18.02.2019 Kliendi terava tähelepaneku põhjal täpsustasime identsustõendi kehtivuse arvutust (seda ei teosta TARA, vaid peab teostama klientrakendus: ). Tõend kehtib, kuid on täidetud tingimused: 
`nbf <= jooksev_aeg + kellade_lubatud_erinevus` ja `exp > jooksev_aeg - kellade_lubatud_erinevus`. Vt [veebitõendi standard](https://tools.ietf.org/html/rfc7519), jaot 4.1.4-5.
{: .note}
