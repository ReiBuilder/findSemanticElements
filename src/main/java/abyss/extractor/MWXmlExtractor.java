package abyss.extractor;


import abyss.extractor.definition.WordDef;
import org.dom4j.Attribute;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Abyssss on 2016/11/28.
 * <p>
 * the class is to extract the definition of words from xml files which required by
 * using Merriam-Webster Dictionary API(a web service).
 * <p>
 * Specifically, the class have to handle tree structure in the xml files like
 * resource/example-animal.xml.
 * <p>
 * tags I need to process include(by the date 2016-11-28):
 * 1. <entry_list></entry_list>
 * 2. <entry></entry>
 * 3. <fl></fl>
 * 4. <def></def>
 * 5. <dt></dt>
 * 6. <sn></sn>
 * 7. <snp></snp>
 * 8. <vi></vi>
 * 9. <it></it>
 * 10. <sx></sx>
 * 11. <vt></vt>
 * 12. <un></un>
 */

public class MWXmlExtractor extends XmlExtractor {

    Pattern pattern = null;
    Matcher matcher = null;

    @Override
    public void initExtractorByFile(String inputFileName){
        super.initExtractorByFile(inputFileName);
        pattern = Pattern.compile("[a-z]+[[0-9]+]");

    }

    /**
     * extract : the main function of MWXmlExtractor
     * data : 2016-11-28
     * procedure:
     *  1.get <entry_list> element
     *  2.get all word definition elements -- the target <entry> element
     *  3.for each <entry>, extract the first <df> content.
     */
    public void extract() {

        ArrayList<Element> myEntryList = null;

        if(null != getEntryListElement()){
            myEntryList = getAllEntryElements();
        }

        ArrayList<WordDef> myWordDefList = new ArrayList<>();

        System.out.println("the size of entryList is " + myEntryList.size());//test

        for(Element ele:myEntryList){
            WordDef wd = getWordDefFromEntry(ele);
            System.out.println(wd.getDef());//test
            if(null != wd){
                myWordDefList.add(wd);
            }
        }

        fillWordDefArray(myWordDefList);

    }

    /**
     * actually, this function is used to check if the root is what we want.
     * so, maybe name it checkEntryListElement is better.
     * @return
     */
    private Element getEntryListElement(){

        if (root == null) return null;

        List<Attribute> attrs = root.attributes();
        if (attrs != null && attrs.size() > 0) {
            for (Attribute attr : attrs) {
                if(attr.getName().equals("version")){
                    return root;
                }
            }
        }
        else{
            return null;
        }
        return null;
    }

    private ArrayList<Element> getAllEntryElements(){
        ArrayList<Element> elementList = new ArrayList<>();

        List<Element> childNodes = root.elements();

        for (Element e : childNodes) {
            if(e.getName().equals("entry")) {
                List<Attribute> attrs = e.attributes();
                for(Attribute attr:attrs){
                    if(attr.getName().equals("id")
                            && checkTheIDPattern(attr.getValue())){
                        elementList.add(e);
                        break;
                    }
                }
            }
        }
        return elementList;
    }

    private WordDef getWordDefFromEntry(Element wordEntry){
        return null;
    }

    private void fillWordDefArray(ArrayList<WordDef> myWordDefList) {

    }

    private Boolean checkTheIDPattern(String value){
        Matcher matcher = pattern.matcher(value);
        boolean b= matcher.matches();
        return false;
    }

    @Override
    public void addToWordDefList(WordDef wordDef){

    }
}
