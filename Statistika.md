---
permalink: Statistika
---

# TARA kasutusstatistika
{: .no_toc}

- TOC
{:toc}

Vt ka:
- [TARA-107](https://jira.ria.ee/browse/TARA-107) (JIRA)
- [logimise käsitlus TARA-Server repo vikis](https://github.com/e-gov/TARA-Server/wiki/Logimine). 

## TARA statistikalogi. Spetsifikatsioon

20\.11.2017, täiendatud 8.12.2017, 15.12.2017 Priit Parmakson

1\.	Statistikalogi eesmärk on koguda andmeid RIHA kasutusstatistika koostamiseks.<br>
2\.	Statistikalogi salvestatakse eraldi logifailide kogumina.<br>
3\.	Statistikalogi hõlmab nii siseriiklikku kui ka piiriülest autentimist.<br>
4\.	Logisse ei salvestata isikuandmeid.<br>
5\.	Logi ei toimetata RIA taristust välja.<br>
6\.	Statistika tootmise kõrval võib logi kasutada ka teenuse tõrgete põhjuste otsimisel ja turvajuhtumite uurimisel.<br>
7\.	Logi säilitatakse 1 aasta.<br>
8\.	Logifaili näide:

````
08.12.2017 12:03:42;openIdDemo;MOBILE_ID;START_AUTH;
08.12.2017 12:03:42;openIdDemo;MOBILE_ID;ERROR;NOT_ACTIVATED
08.12.2017 12:05:19;openIdDemo;ID_CARD;START_AUTH;
08.12.2017 12:05:19;openIdDemo;ID_CARD;SUCCESSFUL_AUTH;
15.12.2017 11:37:15;openIdDemo;MobileID;START_AUTH;
15.12.2017 11:37:15;openIdDemo;MobileID;ERROR;USER_CANCEL
15.12.2017 11:37:16;openIdDemo;MobileID;START_AUTH;
15.12.2017 11:37:16;openIdDemo;MobileID;ERROR;SENDING_ERROR
15.12.2017 11:37:17;openIdDemo;MobileID;START_AUTH;
15.12.2017 11:37:17;openIdDemo;MobileID;ERROR;MID_NOT_READY
15.12.2017 11:37:17;openIdDemo;MobileID;START_AUTH;
15.12.2017 11:37:18;openIdDemo;MobileID;ERROR;PHONE_ABSENT
15.12.2017 11:37:18;openIdDemo;MobileID;START_AUTH;
15.12.2017 11:37:19;openIdDemo;MobileID;ERROR;SIM_ERROR
15.12.2017 11:37:19;openIdDemo;MobileID;START_AUTH;
15.12.2017 11:37:20;openIdDemo;MobileID;ERROR;INTERNAL_ERROR
15.12.2017 11:37:20;openIdDemo;MobileID;START_AUTH;
15.12.2017 11:37:20;openIdDemo;MobileID;ERROR;CERTIFICATE_REVOKED: Certificate is revoked
15.12.2017 11:46:12;openIdDemo;MobileID;START_AUTH;
15.12.2017 11:46:28;openIdDemo;MobileID;SUCCESSFUL_AUTH;
15.12.2017 11:48:22;openIdDemo;MobileID;START_AUTH;
15.12.2017 11:48:38;openIdDemo;MobileID;SUCCESSFUL_AUTH;
15.12.2017 11:59:10;openIdDemo;MobileID;START_AUTH;
````

9\.	Logikirje formaat. Logikirjeid on kahte tüüpi:

- autentimistoimingu alustamise kirje

| nr	| väli |	selgitus	| formaat	|
|-----|------|------------|---------|
| 1.	| toimingu aeg	| autentimistoimingu alustamise aeg.	| ISO 8601, `YYYY-MM-DD hh:mm:ssTZD` |
| 2.	| klientrakendus	| klientrakenduse nimi, `client id` OpenID Connecti mõistes.	| sõne	|
| 4.	| autentimismeetod	| `MobileID`, `ID_CARD` jne	| sõne	|
| 5.  | toimingu alustamise kood |   | `START_AUTH` |

- autentimistoimingu ebaõnnestumise kirje;

| nr	| väli |	selgitus	| formaat	|
|-----|------|------------|---------|
| 1.	| toimingu aeg	| autentimistoimingu alustamise aeg.	| ISO 8601, `YYYY-MM-DD hh:mm:ss` |
| 2.	| klientrakendus	| klientrakenduse nimi, `client id` OpenID Connecti mõistes.	| sõne	|
| 4.	| autentimismeetod	| `MobileID`, `ID_CARD` jne	| sõne	|
| 5.  | toimingu tulemus | õnnestumise puhul `SUCCESSFUL_AUTH`, ebaedu puhul `ERROR` |  |
| 6.  | veateade | |  |
 
Logikirjed on ajalises järgnevuses, kuid mitte paariti - s.t autentimistoimingu tulemuse kirje ei tarvitse vahetult järgneda autentimistoimingu alustamise kirjele.

10\.	Autentimistoiming<br>
10\.1\.	Autentimistoimingu alguseks loeme hetke, kus TARA teenusesse saabunud kasutaja valib autentimismeetodi - s.t avab meetodi kastikese ja vajutab nupule `Sisenen` (nupu nimi võib vastavalt autentimismeetodile erineda).<br>
10\.2\.	Autentimistoiming lõpeb kas edukalt või ebaedukalt.<br>
10\.3\.	Autentimistoimingu loeme lõppenuks edukalt siis, kui klientrakendusele väljastatakse volituskood (_authorization code_).<br>
10\.4\.	Kõigil muudel juhtudel loetakse autentimistoiming lõppenuks ebaedukalt.<br>

11\.Kasutusstatistika logifailid asuvad kaustas `/etc/cas/logs/statistics`.

Kasutusstatistika aruande moodustamisel tuleb arvestada, et teenuse paigaldamisel mitmes instantsis (koormusjaoturi taga), moodustab kumbki oma logifailid.

12\.Kasutatakse log4j appenderit. Reeglid ja konfiguratsioon:

````
<RollingFile name="statisticsAppender"
  fileName="${sys:cas.log.dir}/statistics/stats.log" append="true"
  filePattern="${sys:cas.log.dir}/statistics/stats-%d{yyyy-MM-dd-HH}-%i.log">
<PatternLayout pattern="%m%n"/>
<Policies>
<OnStartupTriggeringPolicy/>
<TimeBasedTriggeringPolicy interval="1" modulate="true"/>
<SizeBasedTriggeringPolicy size="10 MB"/>
</Policies>
</RollingFile>
````

## TARA teenuse statistiline aruanne. Spetsifikatsioon

1\.	TARA teenuse statistiline aruanne esitab teenuse kaudu teostatud autentimistoimingute arvu ajaperioodil:<br>
1\.1\.	klientrakenduste lõikes<br>
1\.2\.	piiriülene/siseriiklik lõikes<br>
1\.3\.	välisriikide lõikes<br>
1\.4\.	autentimismeetodite lõikes<br>
1\.5\.	autentimistoimingu õnnestumise/ebaõnnestumise lõikes<br>
1\.6\.	ajalises lõikes.<br>
2\.	Statistikaperioodiks on 1 kuu või 1 nädal, aga võib olla ka muu.<br>
3\.	Statistiline aruanne koostatakse perioodi statistikalogi faili(de) põhjal, spetsiaalse skriptiga.<br>
4\.	Statistiline aruanne vormistatakse inimloetava, UTF-8 vormingus tekstifailina. Eriline kujundus ei ole vajalik.<br>

