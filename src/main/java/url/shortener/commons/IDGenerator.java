package url.shortener.commons;

import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import url.shortener.data.URLInfo;

import java.nio.charset.StandardCharsets;

public class IDGenerator {

    public static void generateHashCode(URLInfo urlInfo) {
        byte[] hashCode = hashCode(urlInfo.getUrl());
        urlInfo.setHashCode(encode(hashCode));
    }

    private static byte[] hashCode(String input) {
        return Hashing.sha256().hashString(input, StandardCharsets.UTF_8).asBytes();
    }

    private static String encode(byte[] input) {
        return BaseEncoding.base64Url().encode(input).replaceAll("[^A-Za-z0-9]", "");
    }
}
