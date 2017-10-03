---
permalink: Torkekasitlus
---

# Tõrgete käsitlemine

<img src='img/KuvaVoog.PNG' style='width: 600px;'>

_Tõrkena käsitame siin nii tehnilise komponendi mittetöötamist kui ka kasutaja viga, millest kasutaja ise aru ei saa (nt paneb ID-kaardi valetpidi lugejasse)._

Lahendada:
- Kuidas käsitletakse autentimise ebaõnnestumised, olukorrad, kus mõni osateenus on maas jm tõrked?
  - Kuidas saadakse teada?
  - Kuidas teavitatakse?
- Kelle poole kasutaja vajadusel pöördub?
  - Kuidas kasutaja pöördumine lahendatakse?
  - Kuidas selgitatakse välja vea põhjus? Kuidas osapooled suhtlevad?

Lähtekohti:
1) autentimine TARA-teenuses on tervikprotsess, mille õnnestumine sõltub mitmete komponentide ja osateenuste toimimisest;<br>
2) autentimise tulemusest (OK, NOK) antakse kasutajale teada e-teenuses;<br>
3) tõrkena käsitleme ka olukorda, kus kasutaja ei saa aru, mis ta valesti tegi;<br>
4) kasutaja esmane pöördumispunkt on e-teenuse kasutajatugi;<br>
5) kaalume e-teenuse kasutajatoe kontaktteabe kogumist klientrakenduse registreerimisel ja kuvamist TARA kasutajadialoogis.