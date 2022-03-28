package abdrayev.tasktracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**autor -Almaz
 date- 2022-03-28*/
public class Utils {

    /** Converts String date into Date type */
    public static Date fromStringToDate(String dateString){
        if (dateString == null || dateString.isEmpty())
            return new Date();

        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Date <" + dateString + "> could not be parsed!");
        }
    }
}
