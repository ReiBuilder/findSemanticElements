package abyss.webService;

import abyss.misc.ConfigProvider;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Abyssss on 2016/12/3.
 */
public class MWWebService {

    private String urlstr;
    private String filestr;
    private URL url;
    private ConfigProvider configProvider;
    private URLConnection urlconnection;
    private InputStream inputStream;

    public MWWebService(ConfigProvider configProvider) {
        this.configProvider = configProvider;
    }

    public void setWord(String word){
        urlstr = configProvider.makeUrlStr(word);
        filestr = configProvider.makeFileStr(word);
    }

    public String getFilestr(){
        return filestr;
    }

    public void getWordDefXmlByWebService() throws IOException {
        url = new URL(urlstr);
        urlconnection = url.openConnection();
        urlconnection.connect();
        inputStream = urlconnection.getInputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(
                urlconnection.getInputStream()));

        String result = "";
        String line = "";
        while ((line = in.readLine()) != null) {
            result += line + "\n";
        }

        File xmlFile = new File(filestr);//
        if(!xmlFile.exists())
        {
            xmlFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(xmlFile);
        Writer os = new OutputStreamWriter(fos, "UTF-8");//
        os.write(result);
        os.flush();
        fos.close();
//        System.out.println("content:\n" + result);
    }

    public void close()
    {

    }

    public InputStream getInputStream() {
        return inputStream;
    }
}
