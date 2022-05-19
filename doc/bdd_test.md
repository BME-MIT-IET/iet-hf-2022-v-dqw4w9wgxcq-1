# BDD teszt dokumentáció

## Elvégezett munka
A leklónozott repository-ban található unit tesztek alapul véve **Behaviour Driven Development** tesztek írása **Cucumber** segítségével.

Tesztelt osztályok (src\main\java\com\atomgraph\processor):
- model\impl\TemplateCallImpl
- model\impl\TemplateImpl
- util\Skolemizer
- util\TemplateMatcher

## Eredmények és tapasztalatok

Nagyon látványosan elválnak a teszt készítésének egyes fázisai, amit egyszerű unit tesztek esetében csak kommentek segítségével lehet tagolni. Ennek valamint annak köszönhetően, hogy ezeket a fázisokat el lehet nevezni hétköznapi részmondatokkal jelentősen átláthatóbbak és egyszerűbben értelmezhetőek lesznek tesztjeink a fejlesztők és a megrendelők számára is.