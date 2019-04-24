package Utilities;
import java.text.DecimalFormat;
/**
 * Created by Darius on 3/5/19.
 */

public class Utils {

    // Format numbers
    public static  String formatNumber(int value){
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format((value));
    }


}
