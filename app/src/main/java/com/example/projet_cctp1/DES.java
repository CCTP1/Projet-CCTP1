package com.example.projet_cctp1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DES extends AppCompatActivity implements View.OnClickListener{

    public static final char[] EXTENDED = {0x00C7, 0x00FC, 0x00E9, 0x00E2,
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
            0x207F, 0x00B2, 0x25A0, 0x00A0};

    // Initial Permutation table
    private static final byte[] IP = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    // Permuted Choice 1 table
    private static final byte[] PC1 = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };

    // Permuted Choice 2 table
    private static final byte[] PC2 = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32
    };

    // Array to store the number of rotations that are to be done on each round
    private static final byte[] rotations = {
            1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1
    };

    // Expansion (aka P-box) table
    private static final byte[] E = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };

    // S-boxes (i.e. Substitution boxes)
    private static final byte[][][] S = {{
            {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
            {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
            {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
            {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
    }, {
            {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
            {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
            {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
            {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
    }, {
            {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
            {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
            {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
            {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
    }, {
            {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
            {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
            {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
            {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
    }, {
            {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
            {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
            {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
            {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
    }, {
            {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
            {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
            {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
            {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
    }, {
            {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
            {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
            {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
            {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
    }, {
            {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
            {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
            {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
            {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
    }};

    // Permutation table
    private static final byte[] P = {
            16, 7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9,
            19, 13, 30, 6,
            22, 11, 4, 25
    };

    // Final permutation (aka Inverse permutation) table
    private static final byte[] FP = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    EditText textclair, textcrypté, textclé;
    Button btncrypter, btndécrypter, btnretour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_e_s);

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
        String clé = "";
        if(v.getId() == R.id.crypter ){
            clair = textclair.getText().toString();
            try {
                clé = textclé.getText().toString();
            } catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + textclé);
            }
            crypte = DEScodage(clair, clé);
            textcrypté.setText(crypte);
            //System.out.println(crypte);
        }

        if(v.getId() == R.id.décrypter){
            crypte = textcrypté.getText().toString();
            clé = textclé.getText().toString();
            clair = DESdécodage(crypte, clé);
            textclair.setText(clair);
            //System.out.println(clair);
        }

        if (v.getId() == R.id.retour) {
            this.finish();
        }

    }

    ///////////////////// fonction utile /////////////////////

    // permet de permuté les bit en prenant comme base de permutation un des tableaux de permutation.
    public static int charToAscii(char caractère) {
        int ascii = 0;
        int codecaractère = (int) caractère;
        if (codecaractère > 127) { // le int renvoyé par le caractère est > 127 c'est un ascii étendu
            //on va donc récupérer son ascii oem
            for (int i = 0; i < EXTENDED.length; i++) {
                if (EXTENDED[i] == caractère) { // on compare le caractère avec son code unicode dans le tableau ascii étendu
                    //ensuite on récupère son indice auquel on ajoute 128 pour obtenir son code ascii oem
                    ascii = i + 128;
                }
            }
            //tous les codes ascii étendues sont affichable du coup ici on est pas obligé de faire un code
            //particulier pour récupérer le code ascii des caractère ascii étendu, on le fera pour certains
            // caractère ascii non étendu.
        } else {
            ascii = codecaractère;
        }
        return ascii;
    }


    public static char AsciiToChar(int ascii) {
        char caractère = '5';
        if (ascii > 127) {
            for (int i = 0; i < EXTENDED.length; i++) { // caractère étendu
                //System.out.println(EXTENDED[i]);
                if (ascii - 128 == i) {
                    //System.out.println(EXTENDED[i]);
                    caractère = EXTENDED[i];
                    return caractère;
                } else {
                }
            }
        } else {
            caractère = (char) ascii;
        }
        return caractère;
    }

    public static int[] BitPermutation(int[] bitTab, byte[] baseTab) {
        int[] tabPermutation = new int[baseTab.length];
        for (int i = 0; i < baseTab.length; i++) {
            tabPermutation[i] = bitTab[baseTab[i] - 1];
            //System.out.print(tabPermutation[i]);
        }
        return tabPermutation;
    }

    public static int[][] BitPermutationTab2Tab(int[][] bitTab, byte[] baseTab) {
        int[][] tabPermutation = new int[bitTab.length][baseTab.length];
        for (int i = 0; i < tabPermutation.length; i++) {
            tabPermutation[i] = BitPermutation(bitTab[i], baseTab);
            //System.out.println("");
        }

        return tabPermutation;
    }


    //à faire au début change un String de bit en Tab de bit
    public static int[] StringtoInTab(String bitString) {
        int[] bitTab = new int[bitString.length()];
        for (int i = 0; i < bitTab.length; i++) {
            bitTab[i] = Integer.parseInt("" + bitString.charAt(i));
        }
        return bitTab;
    }

    // Permet de séparé les tab en 2 si tab de longueur pair.
    public static int[][] SéparationTabs(int bitTab[]) {
        int[][] tabSep = new int[2][bitTab.length / 2];
        for (int i = 0; i < bitTab.length / 2; i++) {
            tabSep[0][i] = bitTab[i];
        }
        for (int j = bitTab.length / 2, i = 0; j < bitTab.length; j++, i++) {
            tabSep[1][i] = bitTab[j];
        }
        return tabSep;
    }

    public static int[][] SéparationTabs_v2(int bitTab[], int nbséparation) {
        int[][] tabSep = new int[nbséparation][bitTab.length / nbséparation];
        int i = 0;
        while (i < nbséparation) {
            for (int j = 0; j < tabSep[i].length; j++) {
                tabSep[i][j] = bitTab[i * tabSep[i].length + j];
                //System.out.print(tabSep[i][j]);
            }
            //System.out.println("");
            i++;
        }
        return tabSep;
    }

    // coller les bouts de 2 tableaux.
    public static int[] RassemblementTableau(int[] tab1, int[] tab2) {
        int[] tabRassb = new int[tab1.length + tab2.length];
        for (int i = 0; i < tab1.length; i++) {
            tabRassb[i] = tab1[i];
        }
        for (int i = tab1.length, j = 0; i < tabRassb.length; i++, j++) {
            tabRassb[i] = tab2[j];
        }
        return tabRassb;
    }

    // rassembler le bout des tableaux pairs dans un tableau de tableau.
    public static int[][] RassemblementTabDeTab(int[][] C, int[][] D) {
        int[][] tabRassb = new int[C.length][C[0].length + D[0].length];
        for (int i = 0; i < C.length; i++) {
            for (int k = 0; k < C[i].length; k++) {
                tabRassb[i][k] = C[i][k];
            }
            for (int l = 0, k = C[i].length; l < D[i].length; l++, k++) {
                tabRassb[i][k] = D[i][l];
            }
        }
        return tabRassb;
    }

    // affichage de 2 tab de tab bout à bout
    public static void Print2Tab2(int[][] C, int[][] D, String nomTab) {
        System.out.println(nomTab + " : ");
        for (int i = 0; i < C.length; i++) {
            for (int j = 0; j < C[i].length; j++) {
                System.out.print(C[i][j]);
            }
            for (int j = 0; j < D[i].length; j++) {
                System.out.print(D[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    // affichage de tableau de tableau;
    public static void PrintTab2(int[][] tab2, String nomTab) {
        System.out.println(nomTab + " : ");
        for (int i = 0; i < tab2.length; i++) {
            for (int j = 0; j < tab2[i].length; j++) {
                System.out.print(tab2[i][j]);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    public static void PrintTab1(int[] tab1, String nomTab) {
        System.out.print(nomTab + " = ");
        for (int i = 0; i < tab1.length; i++) {
            System.out.print(tab1[i]);
        }
        System.out.println("");
        //System.out.println("");
    }

    public static void PrintTab1(String[] tab1, String nomTab) {
        System.out.print(nomTab + " = ");
        for (int i = 0; i < tab1.length; i++) {
            System.out.print(tab1[i]);
            System.out.println("");
        }
        //System.out.println("");

    }

    //opération pour le décalage itéré des clé. retourne un tableau à 2 dimension chaque ligne représente une
    //clé pour chaque itération effectué.
    public static int[][] Décalage1(int[] Ci) {

        int i = 1;
        int[][] C = new int[17][Ci.length];
        int[] Cj = new int[Ci.length];

        //System.out.println("itération : C0" );
        for (int j = 0; j < Ci.length; j++) {
            //System.out.print(Ci[j]);
            C[0][j] = Ci[j];
        }
        //System.out.println("");

        while (i <= 16) {

            if (i == 1 || i == 2 || i == 9 || i == 16) {
                for (int j = 0; j < Ci.length; j++) {
                    Cj[j] = Ci[(j + 1) % Ci.length];
                }
            } else {
                for (int j = 0; j < Ci.length; j++) {
                    Cj[j] = Ci[(j + 2) % Ci.length];
                }
            }

            for (int j = 0; j < Ci.length; j++) {
                Ci[j] = Cj[j];
            }
            //System.out.println("itération : " + i);
            for (int j = 0; j < Ci.length; j++) {
                //System.out.print(Cj[j]);
                C[i][j] = Cj[j];
            }
            //System.out.println("");


            i++;
        }
        return C;
    }


    public static int[][] diversificationClé(String clé) {
        int[] cleDep = StringtoInTab(clé);
        int[] CléPermuté = BitPermutation(cleDep, PC1);
        //PrintTab1(CléPermuté, "CléPermuté");
        int[][] CléSéparé = SéparationTabs(CléPermuté);
        //PrintTab2(CléSéparé, "CléSéparé");
        int[][] C = Décalage1(CléSéparé[0]);
        int[][] D = Décalage1(CléSéparé[1]);
        //PrintTab2(C, "CléDécaléG");
        //PrintTab2(D, "CléDécaléD");
        int[][] E = RassemblementTabDeTab(C, D);
        int[][] F = BitPermutationTab2Tab(E, PC2);


        return F;
    }

    public static int[][] permInit(String mot) {

        int[] motTab = StringtoInTab(mot);
        int[] motPermuté = BitPermutation(motTab, IP);
        //System.out.println("");
        int[][] motSPerm = SéparationTabs(motPermuté);
        return motSPerm;

    }

    public static int[][][] étape3(int[][] G0D0, int[][] Key) {
        int[][][] GiDi = new int[2][17][G0D0[0].length];
        GiDi[0][0] = G0D0[0];
        GiDi[1][0] = G0D0[1];
        for (int i = 1; i < 17; i++) {
            for (int j = 0; j < G0D0[0].length; j++) {
                GiDi[0][i][j] = GiDi[1][i - 1][j];
            }
            GiDi[1][i] = Xor(GiDi[0][i - 1], feistel(GiDi[1][i - 1], Key[i]));// Gi-1 + fonction de feistel

            //PrintTab1(GiDi[0][i], "L1 :");
            //PrintTab1(GiDi[1][i], "R1 :");
        }

        return GiDi;
    }

    public static int[] permutationFin(int[] DiGi) {
        return BitPermutation(DiGi, FP);
    }

    public static int[] feistel(int[] D, int[] Key) {
        int[] feisteltab;

        int[] D_étendu = BitPermutation(D, E);// expansion D de 32 bits à 48 bits avec le tableau E
        //System.out.println("");
        int[] XorTab = Xor(D_étendu, Key);//ajout de la clé K puis découpage en 8 chaine de 6 bits int B [8][6]
        //PrintTab1(XorTab , "Détendu + Key");
        //System.out.println("");
        int[][] B = SéparationTabs_v2(XorTab, 8);
        // transformation de chaque chaine de B de 6 bits à 4 bits avec les tableau S0 a S7
        // réassemblage des 8 blocs de 4 bits en bloc de 32 bits
        feisteltab = feistel3(B);


        return feisteltab; // retourner le tableau.
    }

    public static int[] feistel3(int[][] B) {
        int extrémité;
        int intérieur;
        String fin = "";
        for (int i = 0; i < B.length; i++) {
            extrémité = Integer.parseInt("" + B[i][0] + B[i][5], 2);
            intérieur = Integer.parseInt("" + B[i][1] + B[i][2] + B[i][3] + B[i][4], 2);
            int f = S[i][extrémité][intérieur];
            // transformation en binaire de 4 bit
            String fString = Integer.toBinaryString(f);
            while (fString.length() != 4) {
                fString = "0" + fString;
            }
            fin += fString;
        }

        int[] fintab = StringtoInTab(fin);
        int[] finTabPerm = BitPermutation(fintab, P);
        //System.out.println("");
        return finTabPerm;
    }

    public static int[] Xor(int[] A, int[] B) {

        int[] XorTab = new int[A.length];
        for (int i = 0; i < A.length; i++) {
            int temp = A[i] + B[i];
            switch (temp) {
                case 0:
                    XorTab[i] = 0;
                    break;
                case 1:
                    XorTab[i] = 1;
                    break;
                case 2:
                    XorTab[i] = 0;
                    break;
            }
        }

        return XorTab;
    }

    public static String DEScodage(String w, String k) {
        //String clétest = "0000000100100011010001010110011110001001101010111100110111101111";
        //String[] mottest = {"0010001101000101011001111000100110101011110011011110111100000001"};

        String clé = keyToBinaire(k);
        String[] mot = motToBinaire(w);
        //PrintTab1(mot, "mot");
        String[] binaire = new String[mot.length];

        //boucle ici pour chaque mot du tableau
        for (int i = 0; i < mot.length; i++) {
            int[][] cléI = diversificationClé(clé);

            //PrintTab2(cléI, "cléI");
            int[][] motLR = permInit(mot[i]);
            //PrintTab2(motLR, "L et R :");

            int[][][] GiDi = étape3(motLR, cléI);
            //PrintTab1(GiDi[0][16], "G16");
            //PrintTab1(GiDi[1][16], "D16");
            int[] D16G16 = RassemblementTableau(GiDi[1][16], GiDi[0][16]);

            //PrintTab1(D16G16, "D16G16");
            int[] C = permutationFin(D16G16);

            //System.out.println("itération : "+i);
            //PrintTab1(C , "C :");
            binaire[i] = TabToString(C);

            System.out.println("motclair = " + w + " et clé = " + k + "\n");
            System.out.println("bloc " + i);
            PrintTab1(motLR[0], "L0");
            PrintTab1(motLR[1], "R0");
            System.out.println("---------------------------------------");
            for (int j = 1; j < 17; j++) {
                System.out.println("iteration " + j);
                PrintTab1(cléI[i], "K" + j);
                PrintTab1(GiDi[0][i], "L" + j);
                PrintTab1(GiDi[1][i], "R" + j);
                System.out.println("---------------------------------------");
            }
            PrintTab1(C, "C" + i);
            System.out.println("---------------------------------------\n");

        }

        String codé = binaireToString(binaire);
        return codé;

    }

    public static String keyToBinaire(String key) {
        String keyBinaire = "";
        int ikey = 0;
        for (int i = 0; i < 8; i++) {
            if (ikey < key.length()) {
                if (key.length() - ikey >= 4 && key.charAt(ikey) == '\\' && key.charAt(ikey + 1) == 'x' &&
                        (key.charAt(ikey + 2) >= 'a' && key.charAt(ikey + 2) <= 'f' ||
                                key.charAt(ikey + 2) >= '0' && key.charAt(ikey + 2) <= '9')
                        && (key.charAt(ikey + 3) >= 'a' && key.charAt(ikey + 3) <= 'f' ||
                        key.charAt(ikey + 3) >= '0' && key.charAt(ikey + 3) <= '9')) /*si on récupère un hexa*/ {
                    //System.out.println(i +" : "+ clair.charAt(i) +" "+ (i+1) +" : "+ clair.charAt(i+1) +" "+ (i+2) +" : "+ codé.charAt(i+2)  +" "+ (i+3) +" : "+ codé.charAt(i+3));
                    String hex = "" + key.charAt(ikey + 2) + key.charAt(ikey + 3);
                    int asciiHex = Integer.parseInt(hex, 16);
                    //System.out.println(asciiHex);
                    String binaire = Integer.toBinaryString(asciiHex);
                    while (binaire.length() != 8) {// chaque caractère est codé sur 8 bit en ascii étendu pour DES
                        binaire = "0" + binaire;
                    }
                    keyBinaire += binaire;
                    ikey += 3;
                } else {
                    keyBinaire += charToBinaire(key.charAt(ikey));
                }
                ikey++;
            } else {
                keyBinaire += "00000000";
            }
        }
        return keyBinaire;
    }


    public static String[] motToBinaire(String mot) {
        String[] tabMotBinaire = new String[(int) Math.ceil((double) longueurmothexa(mot) / 8)];
        int imot = 0;
        for (int i = 0; i < tabMotBinaire.length; i++) { // parcours le n fois ,le nb de bloc de 64 bits.
            String binaire = "";
            for (int j = 0; j < 8; j++) { // parcours 8 fois car un bloc de 64 bits est composé de 8 octets
                if (mot.length() > imot) { // si la longueur du mot est inférieur à i*8 + j ça signifie
                    // que le dernier bloc de 64 bit du mot n'est pas complet
                    // on ajoute alors des octets nul en plus pour finir le mot.
                    if (mot.length() - imot >= 4 && mot.charAt(imot) == '\\' && mot.charAt(imot + 1) == 'x' &&
                            (mot.charAt(imot + 2) >= 'a' && mot.charAt(imot + 2) <= 'f' ||
                                    mot.charAt(imot + 2) >= '0' && mot.charAt(imot + 2) <= '9')
                            && (mot.charAt(imot + 3) >= 'a' && mot.charAt(imot + 3) <= 'f' ||
                            mot.charAt(imot + 3) >= '0' && mot.charAt(imot + 3) <= '9')) /*si on récupère un hexa*/ {
                        //System.out.println(i +" : "+ clair.charAt(i) +" "+ (i+1) +" : "+ clair.charAt(i+1) +" "+ (i+2) +" : "+ codé.charAt(i+2)  +" "+ (i+3) +" : "+ codé.charAt(i+3));
                        String hex = "" + mot.charAt(imot + 2) + mot.charAt(imot + 3);
                        int asciiHex = Integer.parseInt(hex, 16);
                        //System.out.println(asciiHex);
                        String asciibinaire = Integer.toBinaryString(asciiHex);
                        while (asciibinaire.length() != 8) {// chaque caractère est codé sur 8 bit en ascii étendu pour DES
                            asciibinaire = "0" + asciibinaire;
                            //System.out.println("ici");
                        }
                        binaire += asciibinaire;
                        imot += 3;
                    } else {
                        binaire += charToBinaire(mot.charAt(imot));
                    }
                    imot++;
                } else {
                    binaire += "00000000";
                }
            }
            tabMotBinaire[i] = binaire;
        }
        return tabMotBinaire;
    }

    public static char BinaireToChar(String binaire) {
        char caractère = ' ';
        int ascii = Integer.parseInt(binaire, 2);
        caractère = AsciiToChar(ascii);
        return caractère;
    }

    public static String binaireToString(String[] binaire) {
        String mot = "";
        int ascii;
        for (int i = 0; i < binaire.length; i++) {
            String abinaire = binaire[i]; // récupère le bloc de 64 bit dans un string abinaire
            for (int j = 0; j < 8; j++) {
                String cbinaire = "";
                for (int k = 0; k < 8; k++) {
                    cbinaire += abinaire.charAt(j * 8 + k); // récupère les bloc de 8 bit, de abinaire dans c binaire, qui compose un caractère
                }
                ascii = Integer.parseInt(cbinaire, 2); //transforme les bloc de 8 bit en ascii

                if (!(ascii < 32 || ascii == 127 || ascii == 255)) {
                    mot += AsciiToChar(ascii); // transforme ascii en char
                    //rajouter au mot codé
                    //System.out.println(" char : " + asciiToChar);
                } else {
                    if (ascii < 16) {
                        String hex = Integer.toHexString(ascii);
                        String asciiToHex = "\\x0" + hex;
                        mot += asciiToHex;
                        //System.out.println(" Hex1 : " + asciiToHex);
                    } else {
                        String hex = Integer.toHexString(ascii);
                        String asciiToHex = "\\x" + hex;
                        mot += asciiToHex;
                        //System.out.println(" Hex2 : " + asciiToHex);
                    }
                }

            }
        }
        return mot;
    }

    public static String charToBinaire(char caractère) {
        String binaire = "";
        int ascii = charToAscii(caractère);
        binaire = Integer.toBinaryString(ascii);
        while (binaire.length() != 8) {// chaque caractère est codé sur 8 bit en ascii étendu pour DES
            binaire = "0" + binaire;
        }
        return binaire;
    }

    public static String TabToString(int[] binaire) {
        String motBinaire = "";
        for (int i = 0; i < binaire.length; i++) {
            motBinaire += "" + binaire[i];
        }
        return motBinaire;
    }

    //////////////////////////////////////Décodage //////////////////////////////////////////////

    public static int[][] CléDécryptage(int[][] clé) {
        int[][] cléDécryptage = new int[clé.length][clé[0].length];
        cléDécryptage[0] = clé[0];
        for (int i = 1; i < clé.length; i++) {
            cléDécryptage[i] = clé[clé.length - i];
        }
        return cléDécryptage;
    }

    public static int[][] diversificationCléDécryptage(String clé) {
        int[] cleDep = StringtoInTab(clé);
        int[] CléPermuté = BitPermutation(cleDep, PC1);
        //PrintTab1(CléPermuté, "CléPermuté");
        int[][] CléSéparé = SéparationTabs(CléPermuté);
        //PrintTab2(CléSéparé, "CléSéparé");
        int[][] C = Décalage1(CléSéparé[0]);
        int[][] D = Décalage1(CléSéparé[1]);
        //PrintTab2(C, "CléDécaléGDécryptage");
        //PrintTab2(D, "CléDécaléDDécryptage");
        int[][] E = RassemblementTabDeTab(C, D);
        int[][] F = BitPermutationTab2Tab(E, PC2);
        F = CléDécryptage(F);


        return F;
    }

    public static String DESdécodage(String w, String k) {
        //String clétest = "0000000100100011010001010110011110001001101010111100110111101111";
        //String[] mottest = {"0010001101000101011001111000100110101011110011011110111100000001"};

        String clé = keyToBinaire(k);
        String[] mot = motToBinaire(w);
        //PrintTab1(mot, "mot");
        String[] binaire = new String[mot.length];

        //boucle ici pour chaque mot du tableau
        for (int i = 0; i < mot.length; i++) {
            int[][] cléI = diversificationCléDécryptage(clé);

            //PrintTab2(cléI, "cléI");
            int[][] motLR = permInit(mot[i]);
            //PrintTab2(motLR, "L et R :");

            int[][][] GiDi = étape3(motLR, cléI);
            //PrintTab1(GiDi[0][16], "G16");
            //PrintTab1(GiDi[1][16], "D16");
            int[] D16G16 = RassemblementTableau(GiDi[1][16], GiDi[0][16]);

            //PrintTab1(D16G16, "D16G16");
            int[] C = permutationFin(D16G16);

            //System.out.println("itération : "+i);

            binaire[i] = TabToString(C);

            System.out.println("motcodé = " + w + " et clé = " + k + "\n");
            System.out.println("bloc " + i);
            PrintTab1(motLR[0], "L0");
            PrintTab1(motLR[1], "R0");
            System.out.println("---------------------------------------");
            for (int j = 1; j < 17; j++) {
                System.out.println("iteration " + j);
                PrintTab1(cléI[i], "K" + j);
                PrintTab1(GiDi[0][i], "L" + j);
                PrintTab1(GiDi[1][i], "R" + j);
                System.out.println("---------------------------------------");
            }
            PrintTab1(C, "C" + i);
            System.out.println("---------------------------------------\n");
        }

        String codé = binaireToString(binaire);
        return codé;

    }

    public static int longueurmothexa(String mot) {
        // fonction qui calcule la longueur d'un mot qui contient du code hexa sous forme \xff qui compte pour
        // un seul caractère
        int longueur = 0;
        for (int i = 0; i < mot.length(); i++) {
            if (mot.length() - i >= 4 && mot.charAt(i) == '\\' && mot.charAt(i + 1) == 'x' &&
                    (mot.charAt(i + 2) >= 'a' && mot.charAt(i + 2) <= 'f' ||
                            mot.charAt(i + 2) >= '0' && mot.charAt(i + 2) <= '9')
                    && (mot.charAt(i + 3) >= 'a' && mot.charAt(i + 3) <= 'f' ||
                    mot.charAt(i + 3) >= '0' && mot.charAt(i + 3) <= '9')) {
                i += 3;
            } else {
            }
            longueur++;
        }
        return longueur;
    }
}
