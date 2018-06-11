---
permalink: Legacy
---

# TARA + "Legacy"
{: .no_toc}

- TOC
{:toc}

## Ülevaade

Kas TARA kasutuselevõtmisega eesti.ee-s saab jätkata nn "legacy" autentimise kasutamist?

("Legacy" nime all tuntakse autentimismustrit, millega "sessioon antakse üle" ühelt infosüsteemilt teisele X-tee vahendusel. "Legacy" on kasutusele eesti.ee-s.) 

Jah, see on võimalik. eesti.ee senise autentimislahenduse saab TARA vastu välja vahetada nii, et "legacy"-t kasutava infosüsteemi jaoks ei muutu midagi.

Kasutusvoog hakkaks toimima nii, et kasutaja autendib end TARA-s (1. etapp) ja seejärel suunatakse infosüsteemi (2. etapp) (vt joonis 1). 

Joonis 1. Autentimisteenuse TARA ja "Legacy" autentimise ühitamine eesti.ee-s.

<img src='img/LEGACY.PNG' width='600'>

## Detailne kasutusvoog

ETAPP 1. Autentimine TARA abil

Lähteseis. __Kasutaja__ on saabunud eesti.ee-sse.

__Kasutaja__ vajutab nupule "Logi sisse" vms.

__eesti.ee__ (serveripool) suunab __kasutaja__ __TARA__-sse (autentimispäring).

__TARA__ autendib __kasutaja__ ja suunab __kasutaja__ __eesti.ee__-sse tagasi.

__eesti.ee__ pärib __TARA__-st identsustõendi.

Sellega on __kasutaja__ autenditud.

ETAPP 2. "Sessiooni üleandmine"

Lähteseis. __Kasutaja__ on __eesti.ee__-sse sisse loginud (s.t autenditud).

1a __Kasutaja__ vajutab nupule "Mine infosüsteemi" vms. Veebisirvijast läheb päring __eesti.ee__ serverikomponendile.

2a __eesti.ee__ serverikomponent saadab X-tee päringu __infosüsteemi__ serverikomponendile. Päringus saadetakse kasutaja isikukood ja küsitakse luba __infosüsteemi__ siseneda. 

Infosüsteemi saadetav isikukood on pärit TARA-st saadud identsustõendist. Identsustõendis on veel muid andmeid - isiku ees- ja perekonnanimi, kasutatud autentimismeetod jm. Nende edasisaatmine infosüsteemi ei oma mõtet - kui eesmärk on hoida voog infosüsteemile senisega võrreldes muutumatu.
{: .note}

2b __Infosüsteem__ kontrollib, kas juurdepääsu andmiseks on alust, genereerib juhusliku sõne, lisab selle __infosüsteemi__ URL-le ja saadab moodustatud URL-i ("turva-URL-i") __eesti.ee__-le.

1b __eesti.ee__ saadab päringu 1a vastusega 1b veebisirvijasse ümbersuunamiskorralduse (_redirect_), mille toimel kasutaja suunatakse __infosüsteemist__ saadud URL-le.

3 __Infosüsteem__ kontrollib, kas saabunud URL on see, mida ta on väljastanud, loob __kasutajale__ sessiooni ja saadab veebisirvijasse rakenduse avalehe. __Kasutaja__ alustab (sisselogitult) tööd __infosüsteemis__.

## Turvahinnang

__eesti.ee__ ja __infosüsteem__ suhtlevad X-teel, seega omavad usaldusväärseid identiteete. Vahetatavad sõnumid (2a ja 2b) on allkirjastatud. URL-is sisalduv juhuslik URL, sisuliselt nonss, toimib taasesitus- ja päringuvõltsimisrünnete tõkkena. Skeem on turvaline.