# Manuális kód átvizsgálás

## Eredmények és tanulságok

# Statikus analízis eszköz futtatása
A SonarCloud beüzemelése után az elemzés megtalált 19 bugot és 93 code smellt, amely az alábbi ábrán látszik is:
![](sonar_issues.png)
A 19 bug mind ugyanazt a hibát jelezte:

![](bug.png)

Itt az a probléma, hogy az `if` feltételében ugyan ellenőrizve van, hogy a visszaadott objektum érvényes-e, de később újra a függvényhívás segítségével van lekérve az objektum, ami időközben más objektummal is visszatérhet.

## Eredmények és tanulságok
