---
permalink: Feder
---

# Seansi üleandmine
{: .no_toc}

kavand

Märkus. Kavandis esitatud seisukohad on mõeldud diskussiooniks. Enne kavandi kinnitamist ei saa neile (ilma omapoolse kriitilise analüüsita) toodangulahendustes tugineda. 
{: .note}

06.02.2019  v0.1

- TOC
{:toc}

## Ülevaade

Käesolev dokument annab soovitusi seansi üleandmise koosvõimeliseks ja turvaliseks tehniliseks teostamiseks.

## Mõisted

Seanss (ingl _session_) on kasutaja autentimise järel loodav, ajas piiritletud suhe veebirakenduse kasutaja, sirviku ja veebirakenduse vahel.

Seansi üleandmine on autenditud, veebirakenduses sisselogitud kasutaja suunamine teise veebirakendusse ilma autentimist uuesti läbi tegemata.

## Vajadus

Seansi üleandmise vajadus ei ole universaalne. Paljude e-teenuste puhul on turvalisuse, aga ka arusaadavuse kaalutlustel hea, kui kasutaja logib igassse rakendusse eraldi sisse.

Selle kõrval on siiski kasutusjuhte, kus seansi üleandmist soovitakse. Näiteks eesti.ee-s sisselogitud kasutaja võiks autenditult liikuda rahvastikuregistri portaali.

Seansi üleandmise lahendaks keskne ühekordne sisselogimine (_single sign-on, SSO_). Kuid keskne autentimisteenus TARA praegu ei paku SSO-d. SSO lisamine on TARA teekaardil, kuid võib eeldada, et ka kesk-SSO valmides paljud asutused, eriti suuremad, soovivad säilitada oma autentimislahendused. Samuti vajab veel eraldi väljaselgitamist üleüldise SSO kasutajale arusaadavus ja turvalisus.

Seansi üleandmisel on oluline turvalisus ja jätkusuutlikkus. Järgnevas analüüsime võimalusi seansi üleandmise tehniliseks lahendamiseks.

## Seansi üleandmine OpenID Connect meetodiga

Voog on järgmine:

1  Kasutaja logib eesti.ee-s (edaspidi EE) sisse.

2  Kasutaja vajutab rahvastikuregistri kodanikuportaali (edaspidi RRKP) viivale lingile, nt `https://rrkp.smit.ee/sisene`.

3  RRKP serveripool, saades päringu, vastab ümbersuunamiskorraldusega (re-direct) eesti.ee autentimise jagamise otspunkti (nt `https://eesti.ee/jaga`).
Redirect-URL moodustatakse OpenID Connect eeskujul. Olulise elemendina on URL-is nonss (parameeter `state`). _Redirect_-i saates annab server ka korralduse nonssi salvestamiseks sirviku turvaküpsisesse.

4  `eesti.ee/jaga` (EE), saades autentimise  üleandmise päringu, kontrollib, kas kasutajal on eesti.ee-s kehtiv seanss. Seda tehakse turvaküpsisesse salvestatud eesti.ee seansitõendi (_JWT token_) kehtivuse kontrollimisega.

5  Kui kasutajal on kehtiv seanss, siis EE genereerib volituskoodi (_authorization code_) ja identsustõendi (_identity token_). Identsustõendis on kasutaja isikukood, nimi, vajadusel kontekstiteavet (mida kasutaja EE-s tegi). Identsustõendis on OAuth 2.0/OpenID Connect kohased turvaelemendid (väljaandja, adressaat, kehtivusaeg, allkiri). Identsustõendid jäävad lunastamist ootama (vt allpool).

6  EE vastab päringule redirect-ga RRKP tagasisuunamisaadressile, nt `https://rrkp.smit.ee/tagasi`. _Redirect_-s paneb EE kaasa volituskoodi ja peegeldab tagasi nonssi (parameeter `state`).

7 RRKP, saades tagasipöördumise, kontrollib, kas tagasitulnud nonss (parameeter `state`) ühtib turvaküpsisest tulevad väärtusega. Mitteühtimine loetakse päringuvõltsimise katseks.

8  RRKP võtab tagasipöördumispäringust volituskoodi ja teeb sellega backend-päringu EE tõendiväljastusotspunkti vastu (nt `https://eesti.ee/valjasta`).

9  EE tõendiväljastuspunkt kontrollib RRKP serveri identiteeti.
Selle lahendamiseks on mitu võimalust:
a) sümmeetrilise salasõnaga (TARA eeskujul)
b) PKI sertidega (_Client Certificate Authentication_)
c) teha päring X-tee kaudu.

10 EE tõendiväljastuspunkt leiab volituskoodile vastava identsustõendi ja saadab selle päringu vastuses RRKP-le.

11 RRKP. saades identsustõendi, kontrollib seda (EE antud allkirja õigsus, tõendi kehtivusaeg). Sellega on kasutaja RRKP-s autenditud.

12 RRKP loob kasutajaga seansi. RRKP seansi ja EE seansi vahel seost ei ole.

Skeem on OpenID Connect volituskoodi voo (_authorization flow_) kohandus.
Olulised momendid:
- peab olema päringuvõltsimise kaitse. See luuakse nonssi (parameeter `state`) läbiva kasutuse ja turvaküpsise sisuga võrdlemise teel
- oluline on rakendada ka kõik teised OAuth 2.0/OpenID Connect ettenähtud kontrollid (nende kohta eeskujuna: vt nt https://e-gov.github.io/TARA-Doku/TehnilineKirjeldus#5-turvatoimingud)
- märgime, et nn "legacy" mustrit ei saa pidada turvaliseks (vt https://e-gov.github.io/TARA-Doku/Legacy)

_Backend_-päringu tegemiseks on, nagu ülal nimetatud, kolm võimalust. Sümmeetrilise salasõna puuduseks loeme seda, et RRKP autentimiseks salasõna teaks ka EE.

Soovitame kaitsta EE-RRKP _backend_-ühendus vähemalt omavalmistatud sertidega.

Mõlemad osapooled peavad kindlasti kõik toimingud logima.

X-tee meie hinnangul ei ole tingimata vajalik.

## Seansi üleandmine "pangalingi" meetodiga

Veel üks võimalus "seansi üleandmiseks" on pangalingi laadne protokoll.

Peame silmas mitte koodikaarte (pangalinki kui autentimismeetodit) ega maksekorralduste edastamist, vaid panga poolt ettevõttele osutatava autentimisteenuse meetodit. Vt https://partners.lhv.ee/et/banklink/.

"Pangalingi"  kui autentimismeetodi olulised jooned:
- kogu suhtlus käib läbi sirviku (_redirect_-dega). _Back-end_ päringut ei tehta. On inimesi, kes peavad seda puuduseks. Samas, eIDAS piiriülene autentimine käib samamoodi, ainult sirvikus. Pangalingi autentimismeetod on sarnane SAML Web flow protokolliga. Ka SAML Web flow-s liiguvad kõik sõnumid läbi sirviku ja kasutatakse PKI allkirjastamist. SAML Web flow protokolli kohta on tehtud põhjalikke turvaanalüüse ja leitud, et protokoll on turvaline. Siiski ei saa ka kõige põhjalikumat uuringut pidada lõplikuks tõeks. Ohumudeleid on erinevaid. Asutuse otsust valida _backend_-meetod tuleb austada.
- pangalink ei ole täieliku protokolliga vormistatud. Muu hulgas ei ole pangalingi tehnilises spetsifikatsioonis nimetatud, et kaupmees peab rakendama CSFR kaitset (ega ole selgitatud, kuidas seda teha).
- pangalingi meetodil puudub turvamudel (_security considerations_) vms käsitlus. Turvamudelit peetakse turvaprotokolli puhul elementaarseks.
- suhtlus kaitstakse sertidega.
- X-tee kasutamine pole vajalik. Seda võib võtta positiivsena. Kuid sertide haldamise vajadus ikka jääb.

Kokkuvõttes: "pangalingi" meetodit saaks aluseks võtta seansi edasiandmise mustrile. Kui seda teha, siis ma soovitame fikseeritud pikkusega väljadega sõnumivorming välja vahetada tänapäevase JWT vastu. Meetod tuleks korralikult kirjeldada, muuhulgas lisada turvasoovitused.

## Seansi üleandmine ID-kaardi PIN1 puhverdamise kaudu

Pole mõtet seanssi üle tuua, sest ID-kaardiga saab lihtsamalt. Kui kasutaja on juba läbi TARA ID-kaardiga eesti.ee-sse sisse loginud ja teha _redirect_ RRKP, siis sirvikul on meeles PIN1 ning
kui siis RRKP-s klikkida TARA logimisele, siis on ID kaart meeles ja on paar klikki kokku hoitud.

ID-kaardi autentimise puhver (_cache_) tõesti on sellise käitumisega. Seda käitumist ei saa lugeda kõigis kontekstides positiivseks. Vastupidi, puhverdamine, millest kasutaja ega rakendust seadistav süsteemiadministraator aru ei saa, on kasutatavuse ja ka potentsiaalne turvaprobleem. Kasutatavuse probleem seepärast, et inimene sageli ei saa aru, kuhu on ta või ei ole sisse logitud.

## Juriidiline vormistamine

Kindlasti peab kõigile osapooltele olema selge, mis on seanssi üleandva ja seanssi vastuvõtva asutuse kohustused. Need tuleb kirja panna. Kas piisab tehnilisest protokollist või on vaja juhtide allkirjadega juriidilist lepingut, on kompetentsed otsustama asutuste ärijuhid.

## Isikuandmete kaitse

GDPR vaatest on oluline, et kasutajale antakse teada, kuhu ta sisse logitakse. Sisselogimine ei tohi olla automaatne, vaid peab toimuma inimese nõusolekul. Kasutajaliidestes tuleb see selgelt välja tuua. 