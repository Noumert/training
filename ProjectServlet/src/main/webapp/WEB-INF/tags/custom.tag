<%@ tag import="java.time.*"%>
<%@ tag import="java.time.format.DateTimeFormatter" %>
<%@ tag import="java.time.format.FormatStyle" %>
<%@ tag import="java.util.Locale" %>

<%
    DateTimeFormatter formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.MEDIUM);
    out.println(LocalDateTime.now().format(formatter.withLocale(Locale.getDefault())));
%>