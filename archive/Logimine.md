---
permalink: Logimine
---

# Logimine


## Logimise mõiste

**Logimine** on süsteemis tehtavate toimingute, aset leidvate sündmuste või tekkivate olekute kohta teabe kogumine ja salvestamine.

**Logi** salvestatakse sageli faili, aga võib ka andmebaasi. Ka rakenduse konsoolile e haldaja kasutajaliidessse väljastatavat jooksvat teavet nimetatakse sageli logiks.

**Logide liigid** on seotud logi eesmärkidega (nendest allpool).

## Logimine kui süsteemi funktsioon

Igal süsteemil peab olema fookus. Fookust on otstarbekas väljendada kasutuslugude kaudu. Süsteem ei tohiks teha kõike ega "mida iganes",vaid toetama piiritletud, väikest arvu kasutuslugusid.

Seda põhimõtet tuleks rakendada ka logimisele. Suhtumine "logime kõike" ei ole mõeldav, seda mitmel põhjusel: 1) kõige logimine on ressursimahukas; 2) isikuandmete kaitse, täpsemalt - isikuandmete töötlemise minimaalsuse põhimõte rakendub ka logidele.

Seetõttu tuleks logimise lahendamisse süsteemis suhtuda nagu muusse süsteemiarendusse. See tähendab eelkõige, et logimine ei teki "muuseas", arenduse kõrvalnähtusena, vaid logilahendus tuleb analüüsida, kavandada, teostada ja juurutada. Tuleb arvestada, et see nõuab ressursse.

Logimist on vaja praktiliselt igas süsteemis.

Tihti kasutatakse logide kokkukogumist kesksesse logiserverisse.

Tihti on süsteemil rohkem kui üks logi.

Logimine tavaliselt ei ole süsteemi (peamine) ärifunktsionaalsus. Logimine on süsteemi toetav aspekt (nn "mittefunktsionaalsus").

## Logide kasutajad ja kasutusjuhud

Logi on vaja süsteemiadministraatorile (või kellele iganes, kes süsteemi püstihoidmisega tegeleb). Kui midagi ei tööta või läks valesti, siis süsteemiadministraator uurib logist.

See on lihtsustatud vaade. Logide otseseid ja kaudseid kasutajaid on tegelikult rohkem.

1. Logi võib olla kasutusel e-teenuse kasutusstatistika tootmisel. E-teenuse kasutusstatistikat s.o teavet palju teenust on kasutatud – klientide, autentimismeetodite ja ajaperioodide lõikes - vajab teenuse ärijuht teenuse planeerimiseks. Põhimõtteliselt saaks vajaliku teabe toota ka logimiseta (counter-id rakenduses), kuid logi annab kõikse andmestiku, millest saab teha igasugust statistikat.

2. Logi võib olla kasutusel e-teenuse tõrgete põhjuste väljaselgitamisel. Tõrge on olukord, kus teenus
mingil põhjusel ei toimi. Põhjus võib olla teenuses endas, välisteenustes või hoopis klientrakenduse
poolel. Tuleb leida rivist väljalangenud komponent ja see uuesti tööle panna, asendada või lahendada
olukord muul viisil.

3. Logi võidakse pidada toimingute tõendamise eesmärgil. Näiteks, kasutaja võib vaidlustada süsteemis tehtud toimingu, väites, et ta ei kasutanudki e-teenust.

4. Turvauurija (forensics expert) võib logi kasutada rünnete tuvastamiseks ja uurimiseks.

5. Audiitor võib logi kasutada kontrollimiseks, et süsteemis tehtud toimingud on õiguspärased.

6. Andmeteadlane (data scientist) võib logi kasutada - 


