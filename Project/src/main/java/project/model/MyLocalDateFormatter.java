package project.model;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import project.controller.GlobalConstants;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@Component
public class MyLocalDateFormatter {
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM);
    public DateTimeFormatter formatDateForLocale() {
//        if(LocaleContextHolder.getLocale().equals(GlobalConstants.UA_LOCALE)){
//            return formatter.withLocale(Locale.forLanguageTag("uk-UA"));
//        }else {
//            return formatter.withLocale(Locale.US);
//        }
        return formatter.withLocale(LocaleContextHolder.getLocale());
    }
}
