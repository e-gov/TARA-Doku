---
permalink: 2JARK
---

# 2. arendusjärk (töös)

Eesmärk: lisada TARA-le ID-kaardiga autentimise funktsionaalsus

Tööd:<br>
1 täiendada liitumisjuhendit ja tehnilist kirjeldust. (uue autentimismeetodi kood - "idcard").<br>
2 täiendada autentimise kasutajaliidest - autentimismeetodi valik<br>
3 ID-kaardiga autentimise komponent (serdi võtmine F5-st)<br>
TARA-Server (CAS) muudatused päringut x509 sertifikaadi lugemiseks ning töötlemiseks (info lugemine sertifikaadilt ja atribuutide lisamine ID Tokenisse)<br>
4 kehtivuskinnituse päring (OCSP)<br>
5 testistrateegia, -plaani ja -testide vastav täiendamine<br>
6 täiendada nii, et saaks tagastada eIDAS autentimistaset. Igale autentimismeetodile peab konfiguratsioonis saama omistada autentimistaseme. Esialgu on nii m-ID kui ka ID-kaardi autentimistase määratlemata. Autentimistase esitatakse identsustõendis (v.a kui autentimistase on määratlemata).<br>
7 makettrakenduse täiendamine nii, et testimine oleks hõlpsam (makettrakendus kuvab sirvijas identsustõendi)<br>
8 _hardening_-tegevused - mitmesugused läbivaatused, ülekontrollid ja vajadusel tarkvara täiendused, suunaga teenuse turvalisuse tagamisele.<br>