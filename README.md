# blackjack_GL
Création d'un Blackjack (MVC) avec une IA - projet L3 Info

- Alexis Prevost-Maynen - 21511372
- Guillaume Drouart - 21009341
- Lucas Gouédard - 21506676
- Nicolas Vatel - 21410740

Ant - build.xml

Le projet fonctionne via Ant. Ce dernier est basé sur le build.xml utilisé en cours.
Voici les target à lancer pour pouvoir tester le projet :
- ant compile
- ant run-dist
Pour finir voici les targets à lancer pour effectuer les tests et générer les rapports :
- ant compile-test
- ant junit
- ant junitreport


Comment fonctionnent les bots ?

Vous pouvez choisir entre le bot attaquant & défensif.
Le bot attaquant va miser la moitié de son argent puis va piocher jusqu'à ce qu'il ait une main de valeur 18 ou supérieur.
Le bot defensif va miser le dixième de son argent puis va piocher jusqu'à ce qu'il ait une main de valeur 11 ou supérieur.


Sujet du projet de Génie Logiciel :

Vision synthétique de l’application : On souhaite une application permettant de jouer
au black-jack, un jeu habituellement pratiqué dans les casinos. Le jeu sera un jeu monoposte,
ou le joueur se mesure à des « robots », c’est-à-dire des joueurs jouant automatiquement.
L’application devra posséder une interface graphique intuitive, et permettre de gérer les règles
de base du black-jack, les mises de chaque joueur et, optionnellement, des règles avancées telles
que les splits, assurances, etc.

Code et conception : L’application devra être codée en java. La conception devra isoler
deux packages réutilisables pour d’autres jeux de cartes, l’un permettant de représenter et
manipuler des cartes, paquets de cartes, mains de joueurs, etc., et l’autre permettant de
gérer la représentation graphique de ces objets (par exemple, la représentation d’une main en
éventail, d’un paquet de cartes faces cachées, etc.).
