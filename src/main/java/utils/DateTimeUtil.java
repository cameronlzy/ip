package utils;

import java.time.*;
import java.time.format.*;
import java.util.List;

public final class DateTimeUtil {
    private DateTimeUtil() {}

    private static final List<DateTimeFormatter> ACCEPT =
            List.of(
                    DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
                    DateTimeFormatter.ofPattern("d/M/yyyy H:mm"),
                    DateTimeFormatter.ofPattern("d-M-yyyy HHmm"),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                    DateTimeFormatter.ISO_LOCAL_DATE // date only
            );

    private static final DateTimeFormatter PRETTY =
            DateTimeFormatter.ofPattern("MMM d yyyy, h:mma");

    public static LocalDateTime parseFlexible(String s) {
        String in = s.trim().replaceAll("\\s+", " ");
        for (DateTimeFormatter f : ACCEPT) {
            try {
                if (f == DateTimeFormatter.ISO_LOCAL_DATE) {
                    LocalDate d = LocalDate.parse(in, f);
                    return d.atStartOfDay();
                }
                return LocalDateTime.parse(in, f);
            } catch (DateTimeParseException ignore) {}
        }
        throw new DateTimeParseException("Unrecognized date format", in, 0);
    }

    public static String readable(LocalDateTime dt) {
        return dt.format(PRETTY);
    }
}
