package abyss.support;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Abysssss on 2016/12/3.
 */
public class ConfigProvider {

    private String preFile;
    private String preUrl;
    private String key;
    private String dicType;
    private String contentType;

    public ConfigProvider() throws IOException {

        Properties prop = new Properties();
        InputStream in = new BufferedInputStream(new FileInputStream("src/config/config.properties"));//for test
        prop.load(in);

        preUrl = prop.getProperty ("preUrl");
        dicType = prop.getProperty ("dicType");
        contentType = prop.getProperty ("contentType");
        key = prop.getProperty ("key");
        preFile = prop.getProperty ("preFile");

//        System.out.println("the preUrl :" + preUrl + "\n" +
//                           "the key    :" + key    + "\n" +
//                           "the preFile:" + preFile + "\n"+
//                           "the dicType:" + dicType + "\n"+
//                           "the contentType:" + contentType + "\n");

    }


    public String makeUrlStr(String word) {
        return preUrl + dicType + contentType + word + key;
    }

    public String makeFileStr(String word) {
        return preFile + word + ".xml";
    }
}
