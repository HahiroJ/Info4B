MOV #1 -100 : le mouchard qui indiquera l'arrivée du rouleau compresseur
ADD #19 14
MOV #0 @13
CMP #1 -103 : vérifie le mouchard
JMP -3 : revient au début
MOV @11 @12 : début du code permettant la duplication du programme
CMP #1 10 : si la copie est terminée...
JMP 4 : ... saut en fin de programme
ADD #1 8 : incrémentation des pointeurs
ADD #1 8
JMP -5 : retourne au MOV @11 @12
MOV #-16 -129 : ajoute un DAT -16 à la fin de la copie du programme
MOV #-16 -129
MOV #-150 -129
JMP -147 : saute au début de la copie du prog
DAT -16 : données utilisées par le programme
DAT -16
DAT -150