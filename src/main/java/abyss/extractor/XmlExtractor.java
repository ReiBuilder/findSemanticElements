package abyss.extractor;

import abyss.extractor.definition.WordDef;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * Created by Abyssss on 2016/11/28.
 *
 * the class is to extract the definition of words from xml files.
 */

public class XmlExtractor {

    protected SAXReader reader;
    protected InputStream in;
    protected Document doc;
    protected Element root;

    private WordDef[] wordDefArray;

    public XmlExtractor() {
        reader = new SAXReader();
        in = null;
        doc = null;
        root = null;
        wordDefArray = null;
    }

    private void clearExtractor()
    {
        reader = new SAXReader();
        in = null;
        doc = null;
        root = null;
    }

    public void initExtractorByFile(String inputFileName){

        clearExtractor();
        in = XmlExtractor.class.getClassLoader().getResourceAsStream(inputFileName);
        Document doc = null;
        try {
            doc = reader.read(in);
        } catch (DocumentException e) {
            e.printStackTrace();
            System.out.println("exception when read xml file: " + e.toString());
        }
        root = doc.getRootElement();
    }

    public void addToWordDefList(WordDef wordDef){}

    public WordDef[] getWordDefList() {
        return wordDefArray;
    }
}
