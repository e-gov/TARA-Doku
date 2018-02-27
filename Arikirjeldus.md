---
permalink: Arikirjeldus
---

# TARA autentimisteenus

TARA autentimisteenus on Riigi Infosüsteemi Ameti uus pakutav teenus, millega asutus saab oma e-teenusesse lisada mobiil-ID ja ID-kaardiga autentimise toe.

Juba 2018. a esimesel poolel lisandub eIDAS autentimine.

E-teenus liidestatakse autentimisteenusega OpenID Connect protokolli kohaselt. 

***Kellele?*** Avaliku sektori asutustele, kes soovivad:
- oma e-teenustes mobiil-ID ja ID-kaardi kasutajaid autentida, ise seda tegemata.
- lisada oma e-teenusele piiriülese autentimise toe.

***Tehnilised tingimused?***
- E-teenus liidestatakse autentimisteenusega OpenID Connect protokolli kohaselt.
- Autentimismeetodina toetame mobiil-ID-d ja ID-kaarti.
- Teenuse esimese järgu valmides on kavas lisada teisi autentimismeetodeid, sh eIDAS e välismaa eID kasutaja autentimine.

***Kuidas liituda?*** Asutusel tuleb:<br>
1. välja selgitada, kas ja millistes e-teenustes RIA autentimisteenust tahab kasutada<br>
2. kavandada ja tellida liidestamistöö
  - autentimiskomponendi täiendamine OpenID Connect-ga või väljavahetamine
  - hinnanguline töömaht: kogenud arendajal u 2 päeva, kui OpenID Connect-i pole varem teinud, siis 2 nädalat.
3. teostada arendus<br>
4. esitada RIA-le taotlus teenusega liitumiseks<br>
  - seejuures esitada kasutajate arvu prognoos
  - RIA registreerib teie rakenduse teenuse kliendiks ja avab teile testteenuse.
5. testida liidest RIA testteenuse vastu
  - RIA abistab võimalike probleemide lahendamisel
6. eduka testimise järel taodelda ühendamist toodanguteenusega<br>
  - RIA avab toodanguteenuse.

***Millal?***
- Testteenus on avatud.
- Tootmisteenuse avame kokkuleppel esimeste kasutajatega.

Ootame asutusi, kes sooviksid olla teenuse pilootkasutajad. Kontakt: `help@ria.ee`.

[Tehniline kirjeldus](TehnilineKirjeldus)

<img src='img/KUVA-04.png' width='500'>

