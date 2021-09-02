package projectServlet.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class currentTimeTag extends SimpleTagSupport {
    private static final DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM);
    public void doTag() throws IOException {

        JspWriter out = getJspContext().getOut();
        out.println(LocalDateTime.now().format(formatter.withLocale(Locale.getDefault())));
    }
}

