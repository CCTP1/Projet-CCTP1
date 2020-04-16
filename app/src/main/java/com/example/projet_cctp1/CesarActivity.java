package com.example.projet_cctp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CesarActivity extends AppCompatActivity implements View.OnClickListener{

    EditText textclair, textcrypté, textclé;
    Button btncrypter, btndécrypter,btnretour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cesar);

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

    @Override
    public void onClick(View v) {
        String clair = "",crypte = "";
        int clé = 3;
        if(v.getId() == R.id.crypter ){
            clair = textclair.getText().toString();
            try {
                clé = Integer.parseInt(textclé.getText().toString());
            } catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + textclé);
            }
            crypte = CésarCodage(clair, clé);
            textcrypté.setText(crypte);
            //System.out.println(crypte);
        }

        if(v.getId() == R.id.décrypter){
            crypte = textcrypté.getText().toString();
            try {
                clé = Integer.parseInt(textclé.getText().toString());
            } catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + textclé);
            }
            clair = CésarDécodage(crypte, clé);
            textclair.setText(clair);
           // System.out.println(clair);
        }

        if (v.getId() == R.id.retour) {
            this.finish();
        }


    }

    public static String CésarCodage(String clair, int clé) {
        String codé = "";
        int ctoascii = 0;
        for(int i = 0; i< clair.length(); i++) {
            char caractère = clair.charAt(i);
            //System.out.println(caractère);

            // récupérer l'asci d'un caractère non affichable qui est codé en hexa pour effectuer l'opération
            // de décodage plus tard
            if (clair.length() - i >= 4 && clair.charAt(i) == '\\' && clair.charAt(i+1) == 'x' &&
                    (clair.charAt(i+2) >= 'a' && clair.charAt(i+2) <= 'f' ||
                            clair.charAt(i+2) >= '0' && clair.charAt(i+2) <= '9')
                    && (clair.charAt(i+3) >= 'a' && clair.charAt(i+3) <= 'f' ||
                    clair.charAt(i+3) >= '0' && clair.charAt(i+3) <= '9')) /*si on récupère un hexa*/{
                //System.out.println(i +" : "+ clair.charAt(i) +" "+ (i+1) +" : "+ clair.charAt(i+1) +" "+ (i+2) +" : "+ codé.charAt(i+2)  +" "+ (i+3) +" : "+ codé.charAt(i+3));
                String hex = "" + clair.charAt(i+2) + clair.charAt(i+3);
                int asciiHex = Integer.parseInt(hex, 16);
                //System.out.println(asciiHex);
                ctoascii = asciiHex; //assignation de la valeur de l'ascii du caractère à décodé à ctoascii
                i += 3;
            }else { // cas où l'on récupère un caractère affichable
                ctoascii = charToAscii(caractère); // converti le caractère en code ascii;
            }

            int asciiCodage = CésarAsciiCodage(ctoascii, clé); // applique l'algorithme de décodage sur l'ascii pour otenir l'ascii
            // du caractère en clair

            //transforme l'asciiDécodé en caractère ou en String pour l'afficher
            if(!(asciiCodage < 32 || asciiCodage == 127 || asciiCodage == 255)) {
                char asciiToChar = AsciiToChar(asciiCodage); // j'utilise la fonction AsciiToChar
                //rajouter au mot codé
                codé += asciiToChar;
                //System.out.println(" char : " + asciiToChar);
            } else {
                if(asciiCodage < 16) {
                    String hex = Integer.toHexString(asciiCodage);
                    String asciiToHex = "\\x0" + hex;
                    codé += asciiToHex;
                    //System.out.println(" Hex1 : " + asciiToHex);
                }else {
                    String hex = Integer.toHexString(asciiCodage);
                    String asciiToHex = "\\x" + hex;
                    codé += asciiToHex;
                    //System.out.println(" Hex2 : " + asciiToHex);
                }
            }
        }
        return codé;
    }

    public static String CésarDécodage(String codé, int clé) {
        String clair = "";
        int ctoascii = 0;
        for( int i = 0; i < codé.length(); i++) {
            char caractère = codé.charAt(i);
            //System.out.println(caractère);

            // récupérer l'asci d'un caractère non affichable qui est codé en hexa pour effectuer l'opération
            // de décodage plus tard
            if (codé.length() - i >= 4 && codé.charAt(i) == '\\' && codé.charAt(i+1) == 'x' &&
                    (codé.charAt(i+2) >= 'a' && codé.charAt(i+2) <= 'f' ||
                            codé.charAt(i+2) >= '0' && codé.charAt(i+2) <= '9')
                    && (codé.charAt(i+3) >= 'a' && codé.charAt(i+3) <= 'f' ||
                    codé.charAt(i+3) >= '0' && codé.charAt(i+3) <= '9')) /*si on récupère un hexa*/{
                //System.out.println(i +" : "+ codé.charAt(i) +" "+ (i+1) +" : "+ codé.charAt(i+1) +" "+ (i+2) +" : "+ codé.charAt(i+2)  +" "+ (i+3) +" : "+ codé.charAt(i+3));
                String hex = "" + codé.charAt(i+2) + codé.charAt(i+3);
                int asciiHex = Integer.parseInt(hex, 16);
                //System.out.println(asciiHex);
                ctoascii = asciiHex; //assignation de la valeur de l'ascii du caractère à décodé à ctoascii
                i += 3;
            }else { // cas où l'on récupère un caractère affichable
                ctoascii = charToAscii(caractère); // converti le caractère en code ascii;
            }

            int asciiDecodage = CésarAsciiDécodage(ctoascii, clé); // applique l'algorithme de décodage sur l'ascii pour otenir l'ascii
            // du caractère en clair

            //transforme l'asciiDécodé en caractère ou en String pour l'afficher
            if(!(asciiDecodage < 32 || asciiDecodage == 127 || asciiDecodage == 255)) {
                char asciiToChar = AsciiToChar(asciiDecodage); // j'utilise la fonction AsciiToChar
                //rajouter au mot codé
                clair += asciiToChar;
                //System.out.println(" char : " + asciiToChar);
            } else {
                if(asciiDecodage < 16) {
                    String hex = Integer.toHexString(asciiDecodage);
                    String asciiToHex = "\\x0" + hex;
                    clair += asciiToHex;
                    //System.out.println(" Hex1 : " + asciiToHex);
                }else {
                    String hex = Integer.toHexString(asciiDecodage);
                    String asciiToHex = "\\x" + hex;
                    clair += asciiToHex;
                    //System.out.println(" Hex2 : " + asciiToHex);
                }
            }
        }
        return clair;
    }
    public static int CésarAsciiDécodage(int ascii, int clé) {
        //int début = 'a';
        //int fin = (int)'z';
        int asciiobtenu = ((ascii - clé ) % 256 + 256)%256;
        return asciiobtenu;
    }

    public static int CésarAsciiCodage(int ascii, int clé) {
        //int début = 'a';
        //int fin = (int)'z';
        int asciiobtenu = (ascii + clé ) % 256;
        return asciiobtenu;
    }

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

    public static int charToAscii(char caractère) {
        int ascii = 0;
        int codecaractère = (int)caractère;
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


    public static char AsciiToChar(int ascii) {
        char caractère = '5';
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

}
