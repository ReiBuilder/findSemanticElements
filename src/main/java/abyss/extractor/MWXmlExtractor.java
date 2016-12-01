package abyss.extractor;


import abyss.extractor.definition.WordDef;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.dom4j.Node;

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

    private Pattern pattern = null;
    private Matcher matcher = null;

    @Override
    public void initExtractorByFile(String inputFileName) {
        super.initExtractorByFile(inputFileName);
        pattern = Pattern.compile("\\w+\\[\\d+\\]");

    }

    /**
     * extract : the main function of MWXmlExtractor
     * data : 2016-11-28
     * procedure:
     * 1.get <entry_list> element
     * 2.get all word definition elements -- the target <entry> element
     * 3.for each <entry>, extract the first <df> content.
     */
    public void extract() {
        ArrayList<Element> myEntryElementList = null;
        if (null != getEntryListElement()) {
            myEntryElementList = getAllEntryElements();
        }
        ArrayList<WordDef> myWordDefList = new ArrayList<>();
        if(null != myEntryElementList) {
            for (Element ele : myEntryElementList) {
                WordDef wd = getWordDefFromEntry(ele);
                if (null != wd) {
                    myWordDefList.add(wd);
                }
            }
            fillWordDefArray(myWordDefList);
        }
    }

    /**
     * actually, this function is used to check if the root is what we want.
     * so, maybe name it checkEntryListElement is better.
     *
     * @return Element
     */
    private Element getEntryListElement() {

        if (root == null) return null;
        List<Attribute> attrs = root.attributes();
        if (attrs != null && attrs.size() > 0) {
            for (Attribute attr : attrs) {
                if (attr.getName().equals("version")) {
                    return root;
                }
            }
        } else {
            return null;
        }
        return null;
    }

    private ArrayList<Element> getAllEntryElements() {
        ArrayList<Element> elementList = new ArrayList<>();
        List<Element> childNodes = root.elements();
        for (Element e : childNodes) {
            if (e.getName().equals("entry")) {
                List<Attribute> attrs = e.attributes();
                for (Attribute attr : attrs) {
                    if (attr.getName().equals("id")
                            && checkTheIDValue(attr.getValue())) {
                        elementList.add(e);
                        break;
                    }
                }
            }
        }
        return elementList;
    }

    private WordDef getWordDefFromEntry(Element wordEntry) {
        Element dt = (Element) wordEntry.selectSingleNode("def/dt");
        String stringValue = dt.getStringValue();
        String text = dt.getText();
        if (dt != null && stringValue != null && !stringValue.equals("")
                && text != null && !text.equals("")) {
            String wordDef = null;
            if (stringValue.equals(text)) {
                wordDef = extractDefFromElement(dt);
                return null;
            } else {
                wordDef = extractDefFromFuckingStructure(dt);
                System.out.println("the final string is " + wordDef);
                return null;
            }
        } else {
            //something is wrong here.
            //I need to make a error log, but not now.
            return null;
        }
    }

    private String extractDefFromElement(Element dt) {
//        System.out.println("yes, i finished the easy work!!");
        return null;
    }

    /**
     * if some one have a better measure, please tell me!!!
     *
     * @param dt
     * @return
     */
    private String extractDefFromFuckingStructure(Element dt) {

        String stringValue = dt.getStringValue();

        List<Element> childrenNodes = dt.elements();
        int listSize = childrenNodes.size();

        if (listSize > 0) {
            ArrayList<String> strArray = new ArrayList<>();
            for (Element e : childrenNodes) {
                strArray.add(e.getStringValue());
            }

            //split the stringValue String of element by java.util.regex
            String patternStr = makePattern(strArray);

            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(stringValue);
            matcher.matches();//hava nothing to say...

            //use recursion to handle children elements' stringValue String
            //and add normal parts and recursion parts together to a complete one
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" ");//add the space before head and after tail
            Boolean equalFlag = false;
            for (int j1 = 1, i = matcher.groupCount(); j1 < i + 1; j1++) {
                String part = matcher.group(j1);
                for (int inside = 0; inside < listSize; inside++) {
                    if (part.equals(strArray.get(inside))) {
                        stringBuilder.append(
                                extractDefFromFuckingStructure(childrenNodes.get(inside)));
                        equalFlag = true;
                        break;
                    }
                }
                if (!equalFlag) {
                    stringBuilder.append(part);
                } else {
                    equalFlag = false;//forget to change back...
                }
            }
            stringBuilder.append(" ");//add the space after tail

            //finally i get what i need.
            return stringBuilder.toString();

        } else if (listSize == 0) {
            return " " + stringValue + " ";
        } else {
            return "";//useless
        }

    }

    private String makePattern(ArrayList<String> strArray) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("^");
        for (String str : strArray) {
            stringBuilder.append("(.*)(");
            stringBuilder.append(str);
            stringBuilder.append("){1}");
        }
        stringBuilder.append("(.*?)");
        stringBuilder.append("$");
        return stringBuilder.toString();
    }

    private String composeTheDefString(Element e) {
        return null;
    }

    private String getDefinitionStringByRecursion(Node node) {
        return null;
    }

    private void fillWordDefArray(ArrayList<WordDef> myWordDefList) {

    }

    private Boolean checkTheIDValue(String value) {
        matcher = pattern.matcher(value);
        return matcher.matches();
    }

    @Override
    public void addToWordDefList(WordDef wordDef) {

    }
}

/*
public String getText() {
        List list = this.getInternalDeclarations();
        if(list != null && list.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            Iterator iter = list.iterator();
            if(iter.hasNext()) {
                Object decl = iter.next();
                buffer.append(decl.toString());

                while(iter.hasNext()) {
                    decl = iter.next();
                    buffer.append("\n");
                    buffer.append(decl.toString());
                }
            }

            return buffer.toString();
        } else {
            return "";
        }
    }
 */