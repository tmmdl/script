package tmmdl.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {

    public static String convert(String input){

        String datum;

        String dateList = "Heute Gerade veröffentlich NEU HOT Online Now";

        if (dateList.contains(input)) {

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();

            datum = sdf.format(date).toString();

            return datum;
        }

        else {

            Pattern pattern = Pattern.compile("vor (\\d+)\\s+(.*?)(n?|(en)?)");

            HashMap<String, Integer> map = new HashMap<String, Integer>();
            map.put("Sekund", Calendar.SECOND);
            map.put("Minut", Calendar.MINUTE);
            map.put("Stund", Calendar.HOUR);
            map.put("Tag", Calendar.DATE);
            map.put("Woch", Calendar.WEEK_OF_YEAR);
            map.put("Monat", Calendar.MONTH);
            map.put("Jahr", Calendar.YEAR);

            Calendar cal = Calendar.getInstance();
            Matcher matcher = pattern.matcher(input);
            if ((matcher.matches())) {

                int figure = Integer.parseInt(matcher.group(1));
                String word = matcher.group(2);
                cal.add(map.get(word), -figure);
            }

            return String.format("%tF%n", cal);
        }
    }

    public static java.sql.Date toDate(String input) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(input);
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        return sqlDate;
    }
}