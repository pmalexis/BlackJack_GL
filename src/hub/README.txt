DISCUTION FB ENTRE ALEXIS & LUCAS

ALEXIS :
un server centralisé sur lequel tous les fichier resources tel que notre liste de joueur est stocké...
mais pas que...
sur lequel on stoke un fichier qui permet de savoir quels ports ont utiliseent actuellement
du coup...
si un client ce co au server et ce fait bouler il peut recup le dernier port créer et lincrementé pour en créer un nouveau...
eu coup un client récupére d'abord la liste des ports utilisé, essaye de ce co à tous les server
si il n'y en a aucun de libre il en créer un nouveau et l'ajoute à la liste
ainsi si un server ce retrouve à 0 joueur on le kill
imagine on pourra lancer autant de server qu'on veut....
autant de client qu'on veut
du coup le server central aura tjrs le même port fixe lui
et si ça liste est vide on le kill aussi
comme ça on bouffe pas les ports pour rien
Voilà ça veut dire deux server, server pour jouer, server pour save les différentes données
Ça y est tu me prends pour un fou ? ^^"

LUCAS
alors attend : il y aurait donc un serveur qui serait la uniquement pour pouvoir jouer (comme actuellement) 
+ un autre (le serveur principal) qui se chargera de synchroniser les données (données qui sont principalement 
le fichier contenant les listes de joueur)
et en plus le serveur principal donne au client le port et l'ip d'un serveur de jeu