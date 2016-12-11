package abyss.demo;

import abyss.extractor.MWXmlExtractor;
import abyss.misc.ConfigProvider;
import abyss.webService.MWWebService;

import java.io.FileNotFoundException;
import java.io.IOException;


public class SimpleExtractorDemo
{

    public static void main(String[] args) {

        ConfigProvider configProvider = null;
        try {
            configProvider = new ConfigProvider();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String word = "spring";
        MWWebService myMWWebService = new MWWebService(configProvider);
        myMWWebService.setWord(word);
        try {
            myMWWebService.getWordDefXmlByWebService();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MWXmlExtractor myExtractor = new MWXmlExtractor();
        try {
            myExtractor.setWord(word);
            myExtractor.initExtractorByFile(myMWWebService.getFilestr());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        myExtractor.extract();

    }


}
