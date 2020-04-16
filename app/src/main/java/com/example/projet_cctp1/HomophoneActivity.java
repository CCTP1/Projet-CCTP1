package com.example.projet_cctp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomophoneActivity extends AppCompatActivity implements View.OnClickListener {

    EditText textclair, textcrypté, textclé;
    Button btncrypter, btndécrypter, btnretour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homophone);


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
        String clair = "",crypte = "";
        String clé = "";
        if(v.getId() == R.id.crypter ){
            clair = textclair.getText().toString();
            clé = textclé.getText().toString();
            PrintTab2(StringToCharTab(clé), "clé"); // affichage du tableau clé dans la console
            crypte = Polybe(clair, clé);
            textcrypté.setText(crypte);
            //System.out.println(crypte);
        }

        if(v.getId() == R.id.décrypter){
            crypte = textcrypté.getText().toString();
            clé = textclé.getText().toString();
            PrintTab2(StringToCharTab(clé), "clé"); // affichage du tableau clé dans la console
            clair = DécryptagePolybe(crypte, clé);
            textclair.setText(clair);
            //System.out.println(clair);
        }

        if (v.getId() == R.id.retour) {
            this.finish();
        }


    }

    public static String DécryptagePolybe(String codé, String clé) {
        String clair = "";
        //char caractèreClair;
        int compteurCode = 0;
        int caractèreLigne = 0;
        int caractèreColonne = 0;

        char[][] clétab = StringToCharTab(clé); // construction du tableau de polybe à partir du mot clé
        for(int k = 0 ; k < codé.length(); k++) { // parcours du code

            char caractère = codé.charAt(k);
            //décodage 2 cas :
            if(!(((int) caractère >= (int)'a' && (int) caractère <= (int) 'z') || ((int) caractère >= (int)'0' && (int) caractère <= (int) '9'))) {
                // si le caractère codé n'est pas une lettre ou un chiffre, on ne récupère pas le caractère
                clair += caractère;
            }else {
                //sinon on doit décoder les caractère deux à deux pour obtenir le message clair
                int[] indiceCaractère = getIndice(clétab,caractère); // récupérer l'indice du caractère dans le tableau
                if(compteurCode%2 == 0) {
                    // récupérer la ligne du premier caractère codé
                    caractèreLigne = indiceCaractère[0];
                    compteurCode ++;

                }else {
                    // récupérer la colonne du deuxieme caractère codé
                    caractèreColonne = indiceCaractère[1];
                    compteurCode ++;

                    //récupérer le symbole à l'intersection de la ligne du premier caractère codé
                    //et de la colonne du deuxieme caractère codé
                    clair += clétab[caractèreLigne][caractèreColonne];

                }
            }

        }

        return clair;
    }
    public static String Polybe(String clair, String clé) {
        String codé = "";
        //transformé le String en tableau(notre clé)
        char[][] clétab = StringToCharTab(clé);
        for(int i = 0; i < clair.length(); i++) {
            char caractère = clair.charAt(i);
            //convertir en code Ascii
            //int ctoascii = (int)caractère;
            //System.out.println(ctoascii + " "+ clétoascii);

            //opération cryptographique
            if(!(((int) caractère >= (int)'a' && (int) caractère <= (int) 'z') || ((int) caractère >= (int)'0' && (int) caractère <= (int) '9'))) {
                // si le caractère à codé n'est pas une lettre ou un chiffre, on ne modifie pas le caractère
                codé += caractère;

            }else { //sinon on code le caractère
                //recherche des indices(i et j) du caractère dans la tab à codé
                int[] indice = getIndice(clétab,caractère);
                // récupérer 2 symboles, un sur la meme ligne i et l'autre sur la meme colonne j que le caractère à codé
                int randChiffre =(int) (Math.random() * 6);//chiffre aléatoire entre 0 et 5
                char symbole1 = clétab[indice[0]][randChiffre]; // symbole sur la même ligne
                char symbole2 = clétab[randChiffre][indice[1]]; // symbole sur la même colonne
                codé += symbole1;
                codé += symbole2;
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

    public static void PrintTab2(char[][] tab2, String nomTab) {
        System.out.println(nomTab + " : ");
        for (int i = 0; i < tab2.length; i++) {
            for (int j = 0; j < tab2[i].length; j++) {
                System.out.print(tab2[i][j]);
            }
            System.out.println("");
        }
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

}
