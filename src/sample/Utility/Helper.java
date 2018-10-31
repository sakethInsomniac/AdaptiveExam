package sample.Utility;

import java.time.LocalTime;

public class Helper {

    /**
     * Converts seconds value to minutes and seconds
     *
     * @param nSecondTime input seconds value
     * @return minutes and seconds
     * reference: https://stackoverflow.com/a/24385265/3796452
     */
    public static String ConvertSecondToHHMMSSString(int nSecondTime) {
        return LocalTime.MIN.plusSeconds(nSecondTime).toString();
    }
}
