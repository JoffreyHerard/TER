#!/usr/bin/env python
# -*- coding: utf-8 -*-
import copy
from StringIO import StringIO

result = 0

global resolution

def resolution(taille, eta):
    etat = eta.split()
    global result
    if len(etat) == taille :
        result+=1
        print(etat)
    else :
        for i in range(0,taille) :
            liste = []
            for j in range(0,taille) :
                liste.append(True)
            where = 0
            for j in etat :
                j = int(j)
                liste[j]=False
                if (j+len(etat)-where) < taille and j+(len(etat)-where) >= 0:
                    liste[j+(len(etat)-where)] = False
                if (j-len(etat)-where) < taille and j-(len(etat)-where) >= 0:
                    liste[j-(len(etat)-where)] = False
                where+=1
            if liste[i]:
                resolution(taille, eta+" "+str(i))
    mon_fichier = open("resultat.txt", "w") # Argh j'ai tout écrasé !
    mon_fichier.write(str(result))
    mon_fichier.close()

   
resolution(9, "")
