---
permalink: Uudised
---

# Uudised


25.12.2019 Protokollitäiendus. Lisandus võimalus piiriülese (eIDAS) autentimise valiku korral kasutada TARA ilma kasutajaliidest kuvamata.
Otse eIDAS võrgustikku suunamiseks ilma kasutajaliidese kuvamiseta peab skoobi URL parameeter välja nägema taoline:
`.../authorize?scope=openeid eidasonly eidas:country:fi&...`.
Tundmatuid, valesti kirjeldatud ja mitte väärtustatud attribuute ignoreeritakse. Kõik skoobi atribuudid peavad olema kirjeldatud väikeste tähtetega, kaasaarvatud 'eidas:country:' väärtus, mis ISO 3166-1 alpha-2 standardi kohaselt enamasti suurte tähtedega kirjeldatud. Seega ka suurte tähtedega kirjeldatud korrektset riigikoodi ignoreeritakse.
Otsene eIDAS võrgustikku suunatakse alati kui esinevad eelnevas näites kirjeldatud skoobi atribuudid, isegi kui esineb teisi valiidseid atribuute.
TARA-server metateabe kaudu kätte saadud 'scopes_supported' väärtus koostatakse järgnevalt:
konfiparameetri `cas.authn.oidc.scopes` väärtused
konfiparameetri `eidas.available-countries` iga riigikoodi väärtuse x kohta dünaamiliselt koostatud `eidas:country:x`.
{: .note}

18.02.2019 Kliendi terava tähelepaneku põhjal täpsustasime identsustõendi kehtivuse arvutust (seda ei teosta TARA, vaid peab teostama klientrakendus: ). Tõend kehtib, kuid on täidetud tingimused: 
`nbf <= jooksev_aeg + kellade_lubatud_erinevus` ja `exp > jooksev_aeg - kellade_lubatud_erinevus`. Vt [veebitõendi standard](https://tools.ietf.org/html/rfc7519), jaot 4.1.4-5.
{: .note}
