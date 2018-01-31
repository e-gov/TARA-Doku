---
permalink: Sisearhitektuur
---

# TARA serverrakenduse sisearhitektuur

Käesolev dokument kirjeldab TARA serverrakenduse [TARA-Server](https://github.com/e-gov/TARA-Server) sisearhitektuuri. Peaeesmärk on abistada TARA (edasi)arendajat, eriti järgmistes küsimustes: 

- kuidas (millise põhimõtte alusel) on CAS-i täiendatud? Muuhulgas ülevaade või väike käsitlus sellest, mida ja kuidas CAS-is asju on „kinni keeratud“ (nt atribuutide osa).
- kus on täiendused? (väike juhatus lisatud või muudetud pakettide või klasside juurde; tutvustus, kuidas täiendused on organiseeritud)
- mida arvestada eIDAS-kliendi ehitamisel, et tagada hilisem lõimimine TARA serverrakendusse? (nt teekide valik: kas Open SAML 3.0 on ok?)
- muu kasulik teave, mida TARA edasiarendaja võiks vajada.

__CAS Overlay__. TARA-Server rakendus on [Apero CAS](https://github.com/apereo/cas) toote peale ehitatud modifikatsioon, kus on kasutatud WAR Overlay meetodit.

WAR Overlay meetodist saab lähemalt lugeda siit: [https://maven.apache.org/plugins/maven-war-plugin/overlays.html](https://maven.apache.org/plugins/maven-war-plugin/overlays.html).

Kuidas CASi enda tootes „overlay“d kasutusele võtta ja konfigureerida, saab lugeda siit: [https://apereo.github.io/cas/5.1.x/installation/Maven-Overlay-Installation.html](https://apereo.github.io/cas/5.1.x/installation/Maven-Overlay-Installation.html).

__Klasterdamine__. Klasterdamise seisukohalt omab tähtsust, et TARA-Server töötab vaikimisi „standalone“ reziimis, mille kohane kirjeldus on leitav siit: https://apereo.github.io/cas/5.1.x/installation/Configuration-Server-Management.html.

__CAS-i versioon__. Aluseks on võetud TARA arenduse alguse seisuga viimane stabiilne CAS-i versioon - 5.1.4.

__Muudatused CAS-is__. Kõikidest originaal-CASi muudatustest saab ülevaate, kui võrrelda CAS ja „overlay“ vahelist deltat. Seda saab lihtsasti vaadata GitHubi repositooriumist, mis asub: [https://github.com/e-gov/TARA-Server](https://github.com/e-gov/TARA-Server). Kõik `org.apereo.cas` või `org.pac4j` paketis asuvad klassid on modifitseeritud.

__CAS Täiendused__. Lisaks „overlay“ meetodile on TARA-Server projektis loodud `ee.ria` pakett, kuhu on lisatud kõik ülejäänud muudatused vastavalt tellija soovidele, ID-kaardi ning mobiil-IDga autentimised kaasa arvatud. Kuna CAS toode kasutab laialdaselt Spring Boot raamistikku [https://projects.spring.io/spring-boot/](https://projects.spring.io/spring-boot/), siis kõik muudatused järgivad Spring raamistiku standardeid ja tavasi.

Tähtsamad paketid:  
- `ee.ria.sso.authentication` – autentimisega seotud klassid 
- `ee.ria.sso.config` – sisaldab kõiki konfiguratsiooniga seotud klasse, mis kasutavad @Configuration annotatsiooni
- `ee.ria.sso.flow` – Spring Web Flow’ga seotud klassid
- `ee.ria.sso.logging` – audentimiskordade logimine
- `ee.ria.sso.service` – teenusekiht. Erinevate autentimiskanalite implementatsioonid
- `ee.ria.sso.statistics` – statistika kogumisega seotud klassid
- `ee.ria.sso.validators` – validaatorklassid

__eIDAS kliendi Lõimimine__. Praegune CAS 5.1.4 versioon toetab SAML2 versiooni. Lähemalt saab lugeda siit: [https://apereo.github.io/cas/5.1.x/installation/Configuring-SAML2-Authentication.html](https://apereo.github.io/cas/5.1.x/installation/Configuring-SAML2-Authentication.html). Ka kõige hilisem CAS 5.3 versioon toetab SAML2 versiooni. Ilma eIDAS dokumentatsiooniga tutvumata on keeruline hinnata, millised takistused ja probleemid võivad tekkida kliendi lõimimisel TARA-Serveriga. Nii kaua, kui kasutatakse SAML2 protokolli ei tohiks lisakeerukusi lisanduda. Probleem võib suure tõenäosusega olla see, et CAS ei toeta üks-ühele kogu SAML2 standardit, mis tuli välja Open-ID Connect (OIDC) puhul. Tõenäoline on ka, et eIDAS-e funktsionaalsuse saab lisada ilma CAS-i enda SAML-i kasutamata, sest me ei vaja SAML autentimisprotokolli tervikuna, vaid SAML-sõnumivahetuse teostust TARA ja eIDAS konnektorteenuse vahel.

__Muu kasulik teave__. OpenID Connect on CAS-i peale ehitatud ümbersuunamisi (redirect) kasutades.
