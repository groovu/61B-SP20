package enigma;

import net.sf.saxon.style.XSLOutput;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Scanner;

import static enigma.TestUtils.*;

public class MachineTest {

//    public void printTest() {
//        System.out.println(NAVALA);
//        HashMap<String, Integer> happy = new HashMap<String, Integer>();
//        happy.put("a", 10);
//        happy.put("b", 3);
//        happy.put("c", 88);
//        System.out.println(happy);
//        System.out.println(NAVALA.get("II"));
//    }
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


}
