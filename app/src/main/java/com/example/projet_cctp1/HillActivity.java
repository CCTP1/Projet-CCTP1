package com.example.projet_cctp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.*;
import java.io.*;


public class HillActivity extends AppCompatActivity implements View.OnClickListener{

    EditText textclair, textcrypte, cleA, cleB, cleC, cleD;
    Button btncrypter, btndecrypter, btnretour;

    int valueA;
    int valueB;
    int valueC;
    int valueD;

    static List convertChain = new ArrayList<Integer>();

    public static final char[] EXTENDED = { 0x00C7, 0x00FC, 0x00E9, 0x00E2,
            0x00E4, 0x00E0, 0x00E5, 0x00E7, 0x00EA, 0x00EB, 0x00E8, 0x00EF,
            0x00EE, 0x00EC, 0x00C4, 0x00C5, 0x00C9, 0x00E6, 0x00C6, 0x00F4,
            0x00F6, 0x00F2, 0x00FB, 0x00F9, 0x00FF, 0x00D6, 0x00DC, 0x00A2,
            0x00A3, 0x00A5, 0x20A7, 0x0192, 0x00E1, 0x00ED, 0x00F3, 0x00FA,
            0x00F1, 0x00D1, 0x00AA, 0x00BA, 0x00BF, 0x2310, 0x00AC, 0x00BD,
            0x00BC, 0x00A1, 0x00AB, 0x00BB, 0x2591, 0x2592, 0x2593, 0x2502,
            0x2524, 0x2561, 0x2562, 0x2556, 0x2555, 0x2563, 0x2551, 0x2557,
            0x255D, 0x255C, 0x255B, 0x2510, 0x2514, 0x2534, 0x252C, 0x251C,
            0x2500, 0x253C, 0x255E, 0x255F, 0x255A, 0x2554, 0x2569, 0x2566,
            0x2560, 0x2550, 0x256C, 0x2567, 0x2568, 0x2564, 0x2565, 0x2559,
            0x2558, 0x2552, 0x2553, 0x256B, 0x256A, 0x2518, 0x250C, 0x2588,
            0x2584, 0x258C, 0x2590, 0x2580, 0x03B1, 0x00DF, 0x0393, 0x03C0,
            0x03A3, 0x03C3, 0x00B5, 0x03C4, 0x03A6, 0x0398, 0x03A9, 0x03B4,
            0x221E, 0x03C6, 0x03B5, 0x2229, 0x2261, 0x00B1, 0x2265, 0x2264,
            0x2320, 0x2321, 0x00F7, 0x2248, 0x00B0, 0x2219, 0x00B7, 0x221A,
            0x207F, 0x00B2, 0x25A0, 0x00A0 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hill);

        btncrypter = (Button) findViewById(R.id.crypter);
        btndecrypter = (Button) findViewById(R.id.décrypter);
        btnretour = (Button) findViewById(R.id.retour);
        textclair = (EditText) findViewById(R.id.textclair);
        textcrypte = (EditText) findViewById(R.id.textcrypté);


        cleA = (EditText) findViewById(R.id.a);
        cleB = (EditText) findViewById(R.id.b);
        cleC = (EditText) findViewById(R.id.c);
        cleD = (EditText) findViewById(R.id.d);



        btncrypter.setOnClickListener(this);
        btndecrypter.setOnClickListener(this);
        btnretour.setOnClickListener(this);
    }

    public void onClick(View v) {
        String clair = "", crypte ="";

        String keyA = cleA.getText().toString();
        if(keyA.matches("")){
            Toast.makeText(getApplicationContext(), "erreur", Toast.LENGTH_LONG).show();
        }else{
            valueA = Integer.parseInt(keyA);
            System.out.println();
        }

        String keyB = cleB.getText().toString();
        if(keyB.matches("")){
            Toast.makeText(getApplicationContext(), "erreur", Toast.LENGTH_LONG).show();
        }else{
            valueB = Integer.parseInt(keyB);
        }

        String keyC = cleC.getText().toString();
        if(keyC.matches("")){
            Toast.makeText(getApplicationContext(), "erreur", Toast.LENGTH_LONG).show();
        }else{
            valueC = Integer.parseInt(keyC);
        }

        String keyD = cleD.getText().toString();
        if(keyD.matches("")){
            Toast.makeText(getApplicationContext(), "erreur", Toast.LENGTH_LONG).show();
        }else{
            valueD = Integer.parseInt(keyD);
        };

        if(v.getId() == R.id.crypter ) {

            getKey();
            clair = textclair.getText().toString();

            textcrypte.setText(crypter(clair));
            textclair.setText("");
        }
        if(v.getId() == R.id.décrypter) {

            getKey();
            crypte = textcrypte.getText().toString();
            textclair.setText(decrypter(crypte));
            textcrypte.setText("");
        }
        if (v.getId() == R.id.retour) {
            this.finish();
        }
    }

    private int[][] getKey() {
        final int[][] key = new int[2][2];

        key[0][0] = valueA;
        key[0][1] = valueB;
        key[1][0] = valueC;
        key[1][1] = valueD;

        return key;
    }

    private void Validation(final int[][] key) {
        final int det = key[0][0] * key[1][1] - key[0][1] * key[1][0];


        if(det == 0) {
            throw new java.lang.Error("Syntaxe matrice invalide");
        }
    }

    public int charToAscii(final char caractère) {
        int ascii = 0;
        final int codecaractère = (int)caractère;
        if(codecaractère > 127) { // le int renvoyé par le caractère est > 127 c'est un ascii étendu
            //on va donc récupérer son ascii oem
            for (int i = 0; i < EXTENDED.length; i++) {
                if(EXTENDED[i] == caractère) { // on compare le caractère avec son code unicode dans le tableau ascii étendu
                    //ensuite on récupère son indice auquel on ajoute 128 pour obtenir son code ascii oem
                    ascii = i + 128;
                }
            }
            //tous les codes ascii étendues sont affichable du coup ici on est pas obligé de faire un code
            //particulier pour récupérer le code ascii des caractère ascii étendu, on le fera pour certains
            // caractère ascii non étendu.
        }else {
            ascii = codecaractère;
        }
        return ascii;
    }

    public char AsciiToChar(final int ascii) {
        char caractère = '5';
        final byte hexa;
        if(ascii > 127) {
            for(int i = 0; i < EXTENDED.length ; i++) { // caractère étendu
                //System.out.println(EXTENDED[i]);
                if(ascii - 128 == i) {
                    //System.out.println(EXTENDED[i]);
                    caractère = EXTENDED[i];
                    return caractère;
                }else {}
            }
        }else {
            caractère = (char) ascii;
        }
        return caractère;
    }

    public int length_word(String mot) {
        // fonction qui calcule la longueur d'un mot qui contient du code hexa sous forme \xff qui compte pour
        // un seul caractère
        int longueur = 0;
        for(int i = 0; i < mot.length();i++) {
            if (mot.length() - i >= 4 && mot.charAt(i) == '\\' && mot.charAt(i+1) == 'x' &&
                    (mot.charAt(i+2) >= 'a' && mot.charAt(i+2) <= 'f'  ||  mot.charAt(i+2) >= '0' && mot.charAt(i+2) <= '9')
                    && (mot.charAt(i+3) >= 'a' && mot.charAt(i+3) <= 'f'  || mot.charAt(i+3) >= '0' && mot.charAt(i+3) <= '9')) {
                i += 3;
            } else {}
            longueur++;
        }
        return longueur;
    }

    public String crypter(String phrase)
    {
        int i;
        int[][] keyMatrix;
        final ArrayList<Integer> phraseCalcul = new ArrayList<>();
        final ArrayList<Integer> phraseCrypter = new ArrayList<>();

        //phrase = phrase.replaceAll("[^a-zA-Z]","").toUpperCase();

        // Caractere rare
        if(length_word(phrase) % 2 == 1) {
            phrase += "X";
        }
        keyMatrix = getKey();

        // det != 0
        Validation(keyMatrix);
        for(i=0; i < phrase.length(); i++) {

            //phraseCalcul.add(charToAscii(phrase.charAt(i)));
            if (phrase.length() - i >= 4 && phrase.charAt(i) == '\\' && phrase.charAt(i+1) == 'x' &&
                    (phrase.charAt(i+2) >= 'a' && phrase.charAt(i+2) <= 'f' ||
                            phrase.charAt(i+2) >= '0' && phrase.charAt(i+2) <= '9')
                    && (phrase.charAt(i+3) >= 'a' && phrase.charAt(i+3) <= 'f'  ||
                    phrase.charAt(i+3) >= '0' && phrase.charAt(i+3) <= '9')) {

                String hex = "" + phrase.charAt(i+2) + phrase.charAt(i+3);
                int asciiHex = Integer.parseInt(hex, 16);
                //System.out.println(asciiHex);
                phraseCalcul.add(asciiHex); //assignation de la valeur de l'ascii du caractère à décodé à ctoascii
                i += 3;
            }else { // cas où l'on récupère un caractère affichable

                phraseCalcul.add(charToAscii(phrase.charAt(i))); // converti le caractère en code ascii;

            }
        }
        System.out.println("Phrase calcul : "+ phraseCalcul);

        for(i=0; i < phraseCalcul.size(); i += 2) {
            final int x = (keyMatrix[0][0] * phraseCalcul.get(i) + keyMatrix[0][1] * phraseCalcul.get(i+1)) % 256;
            final int y = (keyMatrix[1][0] * phraseCalcul.get(i) + keyMatrix[1][1] * phraseCalcul.get(i+1)) % 256;
            phraseCrypter.add(x);
            phraseCrypter.add(y);
        }
        System.out.println("Before result : "+phraseCrypter);
        final StringBuilder result = new StringBuilder();
        for(int j=0; j < phraseCrypter.size();j++) {

            char e = AsciiToChar(phraseCrypter.get(j));

            if (((int) e < 32 && (int) e >= 0) || (int) e == 127 || (int) e == 255){
                //result.append(Integer.toHexString((int) e));
                if((int) e < 16){
                    String hex = Integer.toHexString(e);
                    String asciiToHex = "\\x0" + hex;
                    result.append(asciiToHex);
                } else {
                    result.append("\\x"+Integer.toHexString((int)e));
                }

                System.out.println("result hexa : "+result);
            }else {
                result.append(e);
            }


        }
        System.out.println(result.toString());
        //Result("Message codé: ", phraseCrypter);
        String code = result.toString();
        return code;
    }

    private int[][] InverseMatrix(final int[][] key) {
        final int detmod26 = (key[0][0] * key[1][1] - key[0][1] * key[1][0]) % 256;
        int factor;
        final int[][] inverse = new int[2][2];

        // factor*det = 1 mod 256
        for(factor=1; factor < 256; factor++)
        {
            if((detmod26 * factor) % 256 == 1)
            {
                break;
            }
        }

        inverse[0][0] = key[1][1] * factor % 256;
        inverse[0][1] = (256 - key[0][1]) * factor % 256;
        inverse[1][0] = (256 - key[1][0]) * factor % 256;
        inverse[1][1] = key[0][0] * factor % 256;

        return inverse;
    }

    public String decrypter(final String phrase)
    {
        int i;
        int[][] key, inverseKey;
        final ArrayList<Integer> phraseCalcul = new ArrayList<>();
        final ArrayList<Integer> phraseDecoder = new ArrayList<>();

        //phrase = phrase.replaceAll("[^a-zA-Z]","").toUpperCase();
        key = getKey();

        //det != 0
        Validation(key);

        for(i=0; i < phrase.length(); i++) {
            //phraseCalcul.add(phrase.codePointAt(i));
            if (phrase.length() - i >= 4 && phrase.charAt(i) == '\\' && phrase.charAt(i+1) == 'x' &&
                    (phrase.charAt(i+2) >= 'a' && phrase.charAt(i+2) <= 'f' ||
                            phrase.charAt(i+2) >= '0' && phrase.charAt(i+2) <= '9')
                    && (phrase.charAt(i+3) >= 'a' && phrase.charAt(i+3) <= 'f'  ||
                    phrase.charAt(i+3) >= '0' && phrase.charAt(i+3) <= '9')) {

                String hex = "" + phrase.charAt(i+2) + phrase.charAt(i+3);
                int asciiHex = Integer.parseInt(hex, 16);
                //System.out.println(asciiHex);
                phraseCalcul.add(asciiHex); //assignation de la valeur de l'ascii du caractère à décodé à ctoascii
                i += 3;
            }else { // cas où l'on récupère un caractère affichable

                phraseCalcul.add(charToAscii(phrase.charAt(i))); // converti le caractère en code ascii;

            }

        }

        inverseKey = InverseMatrix(key);

        for(i=0; i < phraseCalcul.size(); i += 2) {
            phraseDecoder.add((inverseKey[0][0] * phraseCalcul.get(i) + inverseKey[0][1] * phraseCalcul.get(i+1)) % 256);
            phraseDecoder.add((inverseKey[1][0] * phraseCalcul.get(i) + inverseKey[1][1] * phraseCalcul.get(i+1)) % 256);
        }


        final StringBuilder result = new StringBuilder();
        for(int j=0; j < phraseDecoder.size();j++) {
            //result.append( AsciiToChar(phraseDecoder.get(j)) );
            char e = AsciiToChar(phraseDecoder.get(j));

            if (((int) e < 32 && (int) e >= 0) || (int) e == 127 || (int) e == 255){
                //result.append(Integer.toHexString((int) e));
                if((int) e < 16){
                    String hex = Integer.toHexString(e);
                    String asciiToHex = "\\x0" + hex;
                    result.append(asciiToHex);
                } else {
                    result.append("\\x"+Integer.toHexString((int)e));
                }

                System.out.println("result hexa : "+result);
            }else {
                result.append(e);
            }
        }
        System.out.println(result.toString());
        //Result("Message décodé : ", phraseDecoder);
        String code2 = result.toString();
        return code2;
    }

}
