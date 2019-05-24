package url.shortener.commons;

import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import url.shortener.data.URLInfo;

import java.nio.charset.StandardCharsets;

/** author: Ranjith Manickam @ 25 May' 2019 */
public class IDGenerator {

    /**
     * To generate URL hash code.
     *
     * @param urlInfo - URL details
     */
    public static void generateHashCode(URLInfo urlInfo) {
        byte[] hashCode = hashCode(urlInfo.getUrl());
        urlInfo.setHashCode(encode(hashCode));
    }

    /**
     * To generate SHA-256 hashcode.
     *
     * @param input - hashcode generated input.
     * @return - returns generated hash code.
     */
    private static byte[] hashCode(String input) {
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).asBytes();
    }

    /**
     * Returns base62 encoded value.
     *
     * @param input - base62 encoded input.
     * @return - returns the base62 encoded value.
     */
    private static String encode(byte[] input) {
        return BaseEncoding.base64Url().encode(input).replaceAll("[^A-Za-z0-9]", "");
    }
}
