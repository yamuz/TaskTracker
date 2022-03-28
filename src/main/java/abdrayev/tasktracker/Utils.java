package abdrayev.tasktracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

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
