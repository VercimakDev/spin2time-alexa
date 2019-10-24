package at.spin2time.handlers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class TimeManagmentClass {
    public String getNow() {
        LocalDateTime myDateObj = LocalDateTime.now(ZoneId.of("Europe/Vienna"));
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        return formattedDate;
    }
}
