---
permalink: FAQ
---

# FAQ

**1 Autentimisdialoog avaneb, teen autentimise läbi, aga siis tuleb veateade `Teenusele ligipääs suletud`.**

Kontrolli, et klientrakendus kasutab TARA poole pöördumisel õiget `client_id`-d ja tagasuunamis-URL-i (`redirect_uri`). `client_id` tuleb anda autentimispäringus. Tagasisuunamis-URL tuleb anda nii autentimis- kui ka identsustõendi küsimise päringus. Need väärtused peavad täpselt vastama RIA-s registreeritutele. Kas oled neid väärtusi muutnud? Kas sul on mitu klientrakendust? Võib-olla nende väärtused on segamini läinud? Ülekontrollimiseks, milline `client_id` ja `redirect_uri` on registreeritud, võib pöörduda RIA teenusehalduri poole.

**2 OAuth2 teek üritab jwks otspunktist võtme võtmeidentifikaatori abil kätte saada, kuid see ei õnnestu.**

Teenuse avaliku allkirjastamisvõtme otspunkt ([https://tara.ria.ee/oidc/jwks](https://tara.ria.ee/oidc/jwks)) pakub praegu ühtainust võtit. See tuleb võtta ja kasutada. TARA edasiarendamisel (2018 lõpp - 2019 algus) lisame dünaamilise võtmevahetuse (_key rollover_). Siis saab võtit pärida võtmeidentifikaatoriga.

**3 Mida tähendab sõna "TARA"?

Vt: Joh. V. Veski, [Sõna "tara" tähenduse asjus](https://dea.digar.ee/cgi-bin/dea?a=d&d=uuseesti19361028.2.57), "Uus Eesti", 1936 (Rahvusarhiivi Digar-kogu)