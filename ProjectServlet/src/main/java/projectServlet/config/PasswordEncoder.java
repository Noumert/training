package projectServlet.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordEncoder {
    private static final String key0 = "awet32s";
    private static final Charset charset = StandardCharsets.UTF_8;
    private static final byte[] keyBytes = key0.getBytes(charset);

    public static String encode(String password) {
        byte[] b = password.getBytes(charset);
        for (int i = 0, size = b.length; i < size; i++) {
            for (byte keyBytes0 : keyBytes) {
                b[i] = (byte) (b[i] ^ keyBytes0);
            }
        }
        return Base64.getEncoder().encodeToString(b);
    }

    public static String decode(String encodedPassword) {
        byte[] b = Base64.getDecoder().decode(encodedPassword);
        for (int i = 0, size = b.length; i < size; i++) {
            for (byte keyBytes0 : keyBytes) {
                b[i] = (byte) (b[i] ^ keyBytes0);
            }
        }
        return new String(b);
    }
}
