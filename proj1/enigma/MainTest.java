package enigma;

import net.sf.saxon.style.XSLOutput;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

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
                "   AND HINGES\n" +
                        "TILL IT LOOKED ALL SQUARES \n" +
                        "   AND OBLONGS\n" +
                        "LIKE A COMPLICATED FIGURE\n" +
                        "IN THE SECOND BOOK OF EUCLID\n" +
                        "\n" +
                        "* B Beta III IV I AXLE\n" +
                        "HYIHL BKOML IUYDC MPPSF SZW\n" +
                        "SQCNJ EXNUO JYRZE KTCNB DGU\n" +
                        "FLIIE GEPGR SJUJT CALGX SNCTM KUF\n" +
                        "WMFCK WIPRY SODJC VCFYQ LV\n" +
                        "QLMBY UQRIR XEPOV EUHFI RIF\n" +
                        "KCGVS FPBGP KDRFY RTVMW GFU\n" +
                        "NMXEH FHVPQ IDOAC GUIWG TNM\n" +
                        "KVCKC FDZIO PYEVX NTBXY AHAO\n" +
                        "BMQOP GTZX\n" +
                        "VXQXO LEDRW YCMMW AONVU KQ\n" +
                        "OUFAS RHACK \n" +
                        "KXOMZ TDALH UNVXK PXBHA VQ\n" +
                        "XVXEP UNUXT XYNIF FMDYJ VKH";
        Scanner scan = new Scanner(str);
        String test = scan.next();
        System.out.println(test);
    }

    private Scanner _config = new Scanner(""
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "\n 5 3"
            + "\n I MQ (ABC)");

    private Scanner _config1 = new Scanner(""+
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            +"\n 5 3"
            +"\n I MQ      (AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)"
            +"\n II ME     (FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)"
            +"\n III MV    (ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)"
            +"\n IV MJ     (AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)"
            +"\n V MZ      (AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)"
            +"\n  VI MZM    (AJQDVLEOZWIYTS) (CGMNHFUX) (BPRK)"
            +"\n  VII MZM   (ANOUPFRIMBZTLWKSVEGCJYDHXQ)"
            +"\n VIII MZM  (AFLSETWUNDHOZVICQ) (BKJ) (GXY) (MPR)"
            +"\n Beta N    (ALBEVFCYODJWUGNMQTZSKPR) (HIX)"
            +"\n Gamma N   (AFNIRLBSQWVXGUZDKMTPCOYJHE)"
            +"\n B R       (AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)"
            +"\n C R       (AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) (LM) (PW) (QZ) (SX) (UY)");

    private Scanner _config1Weird = new Scanner(""+
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            +"\n 5 3"
            +"\n I MQ      )AELTPHQXRU) (BKNW) (CMOY) (DFG) (IV) (JZ) (S)"
            +"\n II ME     (FIXVYOMW) (CDKLHUP) (ESZ) (BJ) (GR) (NT) (A) (Q)"
            +"\n III MV    (ABDHPEJT) (CFLVMZOYQIRWUKXSG) (N)"
            +"\n IV MJ     (AEPLIYWCOXMRFZBSTGJQNH) (DV) (KU)"
            +"\n V MZ      (AVOLDRWFIUQ)(BZKSMNHYC) (EGTJPX)"
            +"\n  VI MZM    (AJQDVLEOZWIYTS) (CGMNHFUX) (BPRK)"
            +"\n  VII MZM   (ANOUPFRIMBZTLWKSVEGCJYDHXQ)"
            +"\n VIII MZM  (AFLSETWUNDHOZVICQ) (BKJ) (GXY) (MPR)"
            +"\n Beta N    (ALBEVFCYODJWUGNMQTZSKPR) (HIX)"
            +"\n Gamma N   (AFNIRLBSQWVXGUZDKMTPCOYJHE)"
            +"\n B R       (AE) (BN) (CK) (DQ) (FU) (GY) (HW) (IJ) (LO) (MP) (RX) (SZ) (TV)"
            +"\n C R       (AR) (BD) (CO) (EJ) (FN) (GT) (HK) (IV) (LM) (PW) (QZ) (SX) (UY)");
}
