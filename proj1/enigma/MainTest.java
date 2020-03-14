package enigma;

import org.junit.Test;


import java.util.Scanner;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void readConfigTest() {
        String abc1 = _config1.next();
        assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ", abc1);
    }
    @Test
    public void readRotorTest() {
        String read = _config1.next();
        System.out.println(read);
    }
    @Test
    public void scannerTest() {
        String settings = "* B Beta I II";
        Scanner scanset = new Scanner(settings);
        String read = scanset.next();
        System.out.println(read);
        read = scanset.next();
        System.out.println(read);
    }
    @Test
    public void whiteSpace() {
        String str =
                "   AND HINGES\n";
        Scanner scan = new Scanner(str);
        String test = scan.next();
        System.out.println(test);
    }

    private Scanner _config = new Scanner(""
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "\n 5 3"
            + "\n I MQ (ABC)");

    private Scanner _config1 = new Scanner(""
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "\n 5 3"
            + "\n I MQ      (AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)"
            + "\n II ME     (FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)"
            + "\n III MV    (ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)"
            + "\n IV MJ     (AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)"
            + "\n V MZ      (AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)"
            + "\n  VI MZM    (AJQDVLEOZWIYTS) (CGMNHFUX) (BPRK)"
            + "\n  VII MZM   (ANOUPFRIMBZTLWKSVEGCJYDHXQ)"
            + "\n VIII MZM  (AFLSETWUNDHOZVICQ) (BKJ) (GXY) (MPR)"
            + "\n Beta N    (ALBEVFCYODJWUGNMQTZSKPR) (HIX)"
            + "\n Gamma N   (AFNIRLBSQWVXGUZDKMTPCOYJHE)"
            + "\n B R       (AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) "
            + "(LO) (MP) (RX) (SZ) (TV)"
            + "\n C R       (AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) "
            + "(LM) (PW) (QZ) (SX) (UY)");

    private Scanner _config1Weird = new Scanner(""
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "\n 5 3"
            + "\n I MQ      )AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)"
            + "\n II ME     (FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)"
            + "\n III MV    (ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)"
            + "\n IV MJ     (AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)"
            + "\n V MZ      (AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)"
            + "\n  VI MZM    (AJQDVLEOZWIYTS) (CGMNHFUX) (BPRK)"
            + "\n  VII MZM   (ANOUPFRIMBZTLWKSVEGCJYDHXQ)"
            + "\n VIII MZM  (AFLSETWUNDHOZVICQ) (BKJ) (GXY) (MPR)"
            + "\n Beta N    (ALBEVFCYODJWUGNMQTZSKPR) (HIX)"
            + "\n Gamma N   (AFNIRLBSQWVXGUZDKMTPCOYJHE)"
            + "\n B R       (AE) (BN) (CK) (DQ) (FU) (GY) "
            + "(HW) (IJ) (LO) (MP) (RX) (SZ) (TV)"
            + "\n C R       (AR) (BD) (CO) (EJ) (FN) (GT) "
            + "(HK) (IV) (LM) (PW) (QZ) (SX) (UY)");
}
