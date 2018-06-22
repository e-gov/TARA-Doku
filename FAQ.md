---
permalink: FAQ
---

# FAQ

**Autentimisdialoog avaneb, teen autentimise läbi, aga siis tuleb veateade `Teenusele ligipääs suletud`.**

Kontrolli, et klientrakendus kasutab TARA poole pöördumisel õiget `client_id`-d ja tagasuunamis-URL-i (`redirect_uri`). `client_id` tuleb anda autentimispäringus. Tagasisuunamis-URL tuleb anda nii autentimis- kui ka identsustõendi küsimise päringus. Need väärtused peavad täpselt vastama RIA-s registreeritutele. Kas oled neid väärtusi muutnud? Kas sul on mitu klientrakendust? Võib-olla nende väärtused on segamini läinud? Ülekontrollimiseks, milline `client_id` ja `redirect_uri` on registreeritud, võib pöörduda RIA teenusehalduri poole.
