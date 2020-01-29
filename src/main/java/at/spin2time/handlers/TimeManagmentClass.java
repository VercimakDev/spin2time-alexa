package at.spin2time.handlers;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeManagmentClass {
    public String getNow() {
        LocalDateTime myDateObj = LocalDateTime.now(ZoneId.of("Europe/Vienna"));
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }
    public String getHourAgo() {
        LocalDateTime myDateObj = LocalDateTime.now(ZoneId.of("Europe/Vienna")).minusHours(1);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }
}
