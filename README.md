W repozytorium znajduja sie czesciowe pliki projektu aplikacji CityMiners zgloszonej do konkursu Dane po waszawsku: https://konkurs.danepowarszawsku.pl/pl/projekt/backsideart

**Aplikacja CityMiners wykorzystuje jedynie technologie w peni otwarte, korzysta z otwartych danych miejskich ( m.in. do sledzenie dostepnosci wifi oraz analizy tras na podstawie sieci przystankow komunikacji miejskiej ). Jest takze projekt w pelni otwarty, ltorego kompletny kod zrodlowy zostanie udostepniony na zasadach Creative Commons ( https://creativecommons.org/licenses/by-nc-sa/3.0/pl/legalcode )**

Aplikacja oparta jest o nastpujacy stos technologiczny:

- Java SE ( CodeNameOne - https://www.codenameone.com )
- JBoss ( Wildfly )
- Java EE7
- Java 8
- Neo4j ( http://neo4j.com )

Jako platforme wykonania frontendu wybrano otwart technologi Codename1 umożliwiajca realne zaprogramowanie aplikacji natywnej dla wszystkich czterech platform ( iOS, Android, Windows Phone i Blackberry )

Aplikacja CityMiners stanowic ma w zalozeniach wydajna platforme do realizacji systemow opartych na geolokalizowanym grafir zaleznosci, w którym wierzcholkami sa m.in.

- artefakty czyli zdjecia, filmy, opisy, nagrania dzwiekowe i inne liki
- zdarzenia np. planowanie w przestrzeni miejskiej wydarzenia,
- elementy strukturalne - np. pozycje pojazdow i przystankow komunikacji, trasy rowerowe czy pozycje hotspotow wifi
- uzytkownicy aplikacji
 
wszystkie wierzcholki tworza oczywiscie siec relacji zas zaawansowany algorytm analizy grafu w czasie rzeczywistym umozliwia uzytkownikom latwa interakcje z duza iloscia danych ( np. podpowiadanie znalezisk na podstawie dotychczasowej historii uzytkownika, jego znajomych oraz osob ktore weszly w tozsame interakcje z podobnymi artefaktami )
