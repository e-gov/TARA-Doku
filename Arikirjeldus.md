---
permalink: Arikirjeldus
---

# Autentimisteenus TARA

Autentimisteenus TARA on Riigi Infosüsteemi Ameti poolt pakutav teenus, millega asutus saab oma e-teenusesse lisada järgmiste autentimismeetodite toe:

- mobiil-ID
- ID-kaart


- välismaalase autentimine, eIDAS piiriülese autentimise skeemiga
{:.test}

- Smart-ID
- Coop pank
- Danske pank
- LHV pank
- Luminor pank
- SEB pank
- Swedbank
{:.future}

## Kellele?

 Avaliku sektori asutustele, kes soovivad:
- oma e-teenustes pakkuda kasutajatele laia valikut autentimismeetodeid, ise neid meetodeid teostamata.
- lisada oma e-teenusele piiriülese autentimise toe.

## Tehnilised tingimused?

- E-teenus liidestatakse autentimisteenusega OpenID Connect protokolli kohaselt.

## Kuidas liituda?

Asutusel tuleb:

1 välja selgitada, kas ja millistes e-teenustes RIA autentimisteenust tahab kasutada<br>

2 kavandada ja tellida liidestamistöö

- autentimiskomponendi täiendamine OpenID Connect-ga või väljavahetamine
- hinnanguline töömaht: kogenud arendajal u 2 päeva, kui OpenID Connect-i pole varem teinud, siis 2 nädalat.

3 teostada arendus<br>

4 esitada RIA-le taotlus teenusega liitumiseks<br>

- seejuures esitada kasutajate arvu prognoos
- RIA registreerib teie rakenduse teenuse kliendiks ja avab teile testteenuse.

5 testida liidest RIA testteenuse vastu

- RIA abistab võimalike probleemide lahendamisel

6 eduka testimise järel taodelda ühendamist toodanguteenusega

- RIA avab toodanguteenuse.

## Millal?

Testteenus on avatud 2017. a sügisest.

Tootmisteenus on avatud 2018. a märtsist.

## Kuidas teenus välja näeb?

<img src='img/KUVA-04.png' width='500'>

## Rohkem teavet?

Kontakt: `help@ria.ee`.

[Tehniline kirjeldus](TehnilineKirjeldus) (liidese arendajale).

