package com.example.projet_cctp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PlayfairActivity extends AppCompatActivity implements View.OnClickListener{

    EditText textclair, textcrypté, textclé;
    Button btncrypter, btndécrypter, btnretour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playfair);

        btncrypter = (Button) findViewById(R.id.crypter);
        btndécrypter = (Button) findViewById(R.id.décrypter);
        btnretour = (Button) findViewById(R.id.retour);
        textclair = (EditText) findViewById(R.id.textclair);
        textcrypté = (EditText) findViewById(R.id.textcrypté);
        textclé = (EditText) findViewById(R.id.clé);

        btncrypter.setOnClickListener(this);
        btndécrypter.setOnClickListener(this);
        btnretour.setOnClickListener(this);
    }

    public void onClick(View v) {
        String clair = "", crypte = "";
        String clé = "";
        if (v.getId() == R.id.crypter) {
            clair = textclair.getText().toString();
            clair = clair.toLowerCase();
            textclair.setText(clair);
            clé = textclé.getText().toString();
            PrintTab2(PlayfairCarré(clé), "clé"); // affichage du tableau clé dans la console
            crypte = PlayFair(clair, clé);
            textcrypté.setText(crypte);
            //System.out.println(crypte);
        }

        if (v.getId() == R.id.décrypter) {
            crypte = textcrypté.getText().toString();
            crypte = crypte.toLowerCase();
            textcrypté.setText(crypte);
            clé = textclé.getText().toString();
            PrintTab2(PlayfairCarré(clé), "clé"); // affichage du tableau clé dans la console
            clair = DécryptagePlayfair(crypte,clé);
            textclair.setText(clair);
            //System.out.println(clair);
        }

        if (v.getId() == R.id.retour) {
            this.finish();
        }
    }

    //La première partie de la construction du carré de Playfair est la même que celle du carré de Polybe
    // on utilisera donc la fonction qui permet de construire le carré de Polybe pour construire celui de Playfair

    public static char[][] PlayfairCarré(String clé){
        char [][] tabPolybe = StringToCharTab(clé);
        char[][] clétab = new char[6][6];
        // ensuite on doit remplir le tableau de playfair en y insérant les caractère(de haut en bas) du carré de Polybe
        // dans le carré de Playfair(de droite à gauche)
        for(int j = 0 ; j < 6 ; j ++) {
            for(int i = 0; i < 6; i ++) {
                clétab[j][i] = tabPolybe[i][j];
            }
        }
        return clétab;
    }

    public static String DécryptagePlayfair(String codé, String clé) {
        String clair = "";
        char[][] clétab= PlayfairCarré(clé);
        int[] indiceSymbole1 = null;
        int[] indiceSymbole2 = null;
        int compteurCode = 0;

        for( int m = 0; m < codé.length() ; m++) {
            char caractère = codé.charAt(m);
            if (!((caractère >= 'a' && caractère <= 'z' ) || (caractère >= '1' && caractère <= '9'))) {
                clair += caractère;
            }else { //sinon si c'est une lettre ou un chiffre
                if(compteurCode%2 == 0) {//1st caractère faire
                    indiceSymbole1 = getIndice(clétab, caractère); // récupérer l'indice i et j du premier caractère à codé
                   // System.out.print(caractère);
                }else { // sinon 2nd caractère faire
                    //System.out.println(caractère);
                    indiceSymbole2 = getIndice(clétab,caractère); // récupérer l'indice i et j du second caractère à codé
                    if(indiceSymbole1[0] != indiceSymbole2[0] && indiceSymbole1[1] != indiceSymbole2[1]) {
                        // si les 2 caractère sont sur des lignes et colonnes différentes on fait l'opération inverse du codage
                        char CaractèreCodé1 = clétab[indiceSymbole1[0]][indiceSymbole2[1]];
                        char CaractèreCodé2 = clétab[indiceSymbole2[0]][indiceSymbole1[1]];
                        clair += CaractèreCodé1;
                        clair += CaractèreCodé2;
                    }else if(indiceSymbole1[0] == indiceSymbole2[0] && indiceSymbole1[1] != indiceSymbole2[1]) {
                        // si les 2 caractère sont sur la même ligne
                        char CaractèreCodé1 = clétab[indiceSymbole1[0]][((indiceSymbole1[1]-1)%6 + 6)%6];
                        char CaractèreCodé2 = clétab[indiceSymbole2[0]][((indiceSymbole2[1]-1)%6 + 6)%6];
                        clair += CaractèreCodé1;
                        clair += CaractèreCodé2;
                    }else if(indiceSymbole1[0] != indiceSymbole2[0] && indiceSymbole1[1] == indiceSymbole2[1]) {
                        // si les 2 caractères sont sur la même colonne
                        char CaractèreCodé1 = clétab[((indiceSymbole1[0]-1)%6 + 6)%6][indiceSymbole1[1]];
                        char CaractèreCodé2 = clétab[((indiceSymbole2[0]-1)%6 + 6)%6][indiceSymbole2[1]];
                        clair += CaractèreCodé1;
                        clair += CaractèreCodé2;

                    }
                }
                compteurCode ++;
            }
        }
        return clair;
    }

    public static String PlayFair(String clair, String clé) {
        String codé = "";
        char[][] clétab= PlayfairCarré(clé);
        int[] indiceSymbole1 = null;
        int[] indiceSymbole2 = null;
	   /*int[] indiceCodé1 = null;
	   int[] indiceCodé2 = null;*/
        int compteurCode = 0;

        for(int l = 0; l < clair.length() ; l++) {
            char caractère = clair.charAt(l);
            // si le caractère n'est pas une lettre ou un chiffre
            if (!((caractère >= 'a' && caractère <= 'z' ) || (caractère >= '1' && caractère <= '9'))) {
                if(compteurCode%2 == 1) {
                    if (clétab[indiceSymbole1[0]][indiceSymbole1[1]] != 'q') {
                        clair = rajout(clair,'q',l);
                    }else {
                        clair = rajout(clair,'z',l);
                    }
                    //System.out.println(clair);
                    compteurCode++;
                    l -= 2 ;

                }else {
                    codé += caractère;
                }
            }else { //sinon si c'est une lettre ou un chiffre
                if(compteurCode%2 == 0) {//1st caractère faire
                    indiceSymbole1 = getIndice(clétab, caractère); // récupérer l'indice i et j du premier caractère à codé

                    compteurCode++;
                    // si ce caractère est le dernier caractère du mot clair et que la longueur du mot est impair
                    // on rajoute une lettre rare pour pouvoir coder le dernier caractère
                    if(l == clair.length()-1) {
                        if (caractère != 'q'){
                            clair += 'q';
                        }else {
                            clair += 'z';
                        }
                       // System.out.println(clair);
                    }

                }else { // sinon 2nd caractère faire
                    indiceSymbole2 = getIndice(clétab,caractère); // récupérer l'indice i et j du second caractère à codé
                    if(indiceSymbole1[0] != indiceSymbole2[0] && indiceSymbole1[1] != indiceSymbole2[1]) {
                        // si les 2 caractère sont sur des lignes et colonnes différentes on code chaque caractère
                        // par le caractère situé sur la même ligne que elle mais sur la colonne de l'autre caractère
                        char CaractèreCodé1 = clétab[indiceSymbole1[0]][indiceSymbole2[1]];
                        char CaractèreCodé2 = clétab[indiceSymbole2[0]][indiceSymbole1[1]];
                        codé += CaractèreCodé1;
                        codé += CaractèreCodé2;
                    }else if(indiceSymbole1[0] == indiceSymbole2[0] && indiceSymbole1[1] != indiceSymbole2[1]) {
                        // si les 2 caractère sont sur la même ligne
                        char CaractèreCodé1 = clétab[indiceSymbole1[0]][(indiceSymbole1[1]+1)%6];
                        char CaractèreCodé2 = clétab[indiceSymbole2[0]][(indiceSymbole2[1]+1)%6];
                        codé += CaractèreCodé1;
                        codé += CaractèreCodé2;
                    }else if(indiceSymbole1[0] != indiceSymbole2[0] && indiceSymbole1[1] == indiceSymbole2[1]) {
                        // si les 2 caractères sont sur la même colonne
                        char CaractèreCodé1 = clétab[(indiceSymbole1[0]+1)%6][indiceSymbole1[1]];
                        char CaractèreCodé2 = clétab[(indiceSymbole2[0]+1)%6][indiceSymbole2[1]];
                        codé += CaractèreCodé1;
                        codé += CaractèreCodé2;
                    }else if(indiceSymbole1[0] == indiceSymbole2[0] && indiceSymbole1[1] == indiceSymbole2[1]) {
                        // si les 2 lettres sont identiques

                        //rajouter une lettre rare entre le premier caractère et le deuxieme caractère
                        if (clétab[indiceSymbole1[0]][indiceSymbole1[1]] != 'q') {
                            clair = rajout(clair,'q',l);
                        }else {
                            clair = rajout(clair,'z',l);
                        }

                        //System.out.println(clair);
                        // décrémenter l de 2 pour reprendre au premier caractère à l'itération suivante
                        l -= 2;
                    }

                    compteurCode++;
                }
            }

        }
        return codé;
    }

    public static int[] getIndice(char[][] clétab, char a) {
        int[] indice = new int[2];
        for (int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                if(a == clétab[i][j]) {
                    indice[0] = i;
                    indice[1] = j;
                    return indice;
                }
            }
        }
        return indice;
    }

    public static char[][] StringToCharTab(String clé) {
        char[][] clétab = new char[6][6];
        int AlphabAscii = (int) 'a';
        int ChiffreAscii = (int) '0';
        int compteurMot = 0;
        int compteurAlphabet = 0;


        for (int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                if(compteurMot != clé.length()) {
                    char cléChar = clé.charAt(compteurMot);
                    if(!contain(clétab,cléChar)) { // si il ne contient pas le caracètre déja dans le tableau
                        clétab[i][j] = cléChar; // on place notre caractère dans la cellule du tableau
                    }else {
                        j --; //on décrémente l'indice j du tableau pour qu'on s'y replace au prochain mot
                    }

                    compteurMot ++; //on incrémente l'indice de notre Mot c-a-d compteurMot++;
                }
                else {
                    if(compteurAlphabet != 26) {
                        if(!contain(clétab, (char)AlphabAscii)) { // si il ne contient pas le caracètre déja dans le tableau
                            clétab[i][j] = (char)AlphabAscii; // on place notre caractère dans la cellule du tableau
                        }else {
                            j --; //on décrémente l'indice j du tableau pour qu'on s'y replace au prochain mot
                        }

                        AlphabAscii ++;
                        compteurAlphabet ++;
                    }else {
                        if(!contain(clétab, (char)ChiffreAscii)) { // si il ne contient pas le caracètre déja dans le tableau
                            clétab[i][j] = (char)ChiffreAscii; // on place notre caractère dans la cellule du tableau
                        }else {
                            j --; //on décrémente l'indice j du tableau pour qu'on s'y replace au prochain mot
                        }

                        ChiffreAscii ++;
                    }
                }

            }
        }
        return clétab;
    }

    // fonction qui vérifie qu'un caractère est contenu dans un tableau et renvoie true ou false
    public static boolean contain(char[][] clétab, char a) {
        for (int i = 0; i < 6; i++) {
            for(int j = 0; j < 6; j++) {
                if(a == clétab[i][j])
                    return true;
            }
        }
        return false;
    }

    public static String rajout(String clair, char lettreRare, int indice) {
        String newClair = "";
        int i = 0;
        while(clair.length() != 0) {
            if(i != indice) {
                newClair += clair.charAt(0);
                clair = clair.substring(1);
            }else {
                newClair += lettreRare;
            }
            i++;
        }
        return newClair;
    }

    public static void PrintTab2(char[][] tab2, String nomTab) {
        System.out.println(nomTab + " : ");
        for (int i = 0; i < tab2.length; i++) {
            for (int j = 0; j < tab2[i].length; j++) {
                System.out.print(tab2[i][j]);
            }
            System.out.println("");
        }
    }

}
