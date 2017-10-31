---
permalink: Sonastik
---

{% include Issue.html %}

# Sõnastik

Käesolev dokument määratleb TARA-teenuse kasutamise, haldamise ja arendamise seisukohalt olulisemad mõisted.

- _asutus_, autentimisteenust kasutav Eesti avaliku sektori asutus
- _autentimismeetod_, TARA-teenuse toetatav autentimismeetod, nt m-ID, ID-kaart, välisriigi eIDga autentimine eIDASe mõistes
- _autentimisteenus_, RIA pakutav autentimisteenus TARA
- _eestlane_ - Eesti eID kasutaja.
- _e-teenus_, asutuse poolt kasutajale, sh välismaalasele pakutav e-teenus
- _identsustõend_, (_ID token_), autentimisteenuse poolt rakendusele väljastatav teave kasutaja tuvastatud identiteedi kohta (isikukood ja tehnilised andmed)
- _ID-kaart_, hõlmab ka elamisloakaarte ja digi-ID-kaarte
- _kasutaja_, e-teenust kasutav füüsiline isik
- _klientrakendus_, lühidalt _rakendus_, TARA autentimisteenust kasutav süsteem.
- _pääsutalong_, (_access token_), autentimisteenuse poolt rakendusele väljastatav allkirjastatud teave
- _rakendus_, asutuse veebirakendus, mis pakub e-teenust; koosneb kahest osast: 1) kasutaja sirvijasse laetav osa; 2) serveripoolne osa
- _RIA_, Riigi Infosüsteemi Amet, osutab TARA-teenust.
- _registreerimine_, klientrakenduse registreerimine TARA-teenuse kasutajaks, vastavalt OpenID Connect protokolli nõuetele.
- _tagatistase_, Euroopa Liidu eIDAS määruse kohane eID ehk autentimislahenduse tagatistase [tasemed].
- _testteenus_, TARA-teenuse instants, mida kasutatakse teenusega liidestumiste testimiseks.
- _toodanguteenus_, TARA-teenuse instants, kus toimub reaalsete andmetega, toodangutähenduses autentimine.
- _TARA autentimisteenus_, lühidalt TARA-teenus, Riigi Infosüsteemi Ameti  osutatav teenus, millega asutus saab oma e-teenusesse lisada mobiil-ID kasutaja autentimise toe. Teenuse II arendusjärgus lisatakse eIDAS jt autentimismeetodite tugi, hiljem võimalik, et ka ühekordse sisselogimise (SSO) tugi.
- _volituskood_, (_authorization code_), juhuarv, mille esitamisel rakendus saab autentimisteenuselt identsustõendi ja pääsutalongi.
- _välismaalane_, Euroopa Liidu teise riigi eID kasutaja.
