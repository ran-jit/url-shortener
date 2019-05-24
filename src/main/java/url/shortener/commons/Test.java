package url.shortener.commons;

import url.shortener.data.URLInfo;

public class Test {

    public static void main(String[] args) {
        URLInfo urlInfo = URLInfo.builder().url("http://abc.com").build();

        IDGenerator.generateHashCode(urlInfo);
        System.out.println(urlInfo.getHashCode());
    }
}
