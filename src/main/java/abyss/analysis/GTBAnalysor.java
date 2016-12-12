package abyss.analysis;

import abyss.analysis.support.WordPosPair;
import abyss.semanticElementGraph.SEGManager;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.trees.Tree;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abyssss on 2016/12/11.
 *
 */
/*
this class is use to analysis definition based grammar tree.
 */
public class GTBAnalysor {

    private SEGManager segManage;
    private TokenizerFactory<CoreLabel> tokenizerFactory;
    private LexicalizedParser lp;
    private final static String parserModel =
            "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";
    private Tree currentParseResult;
    private WordPosPair currentWordPosPair;

    //i don't sure that if the to-do list should be put here.
    private ArrayList<WordPosPair> toDoList;

    public GTBAnalysor(SEGManager inputSegManage) {
        segManage = inputSegManage;
        tokenizerFactory = PTBTokenizer.factory(
                new CoreLabelTokenFactory(), "");
        lp = LexicalizedParser.loadModel(parserModel);
        currentParseResult = null;
        currentWordPosPair = null;
        toDoList = new ArrayList<>();
    }

    public void parse(String input){
        Tokenizer<CoreLabel> tok =
                tokenizerFactory.getTokenizer(new StringReader(input));
        List<CoreLabel> rawWords = tok.tokenize();
        setCurrentParseResult(lp.apply(rawWords));
    }

    /**
     * the function is use to get the pos tag of target word.
     * be careful!! if found more than one pos tags, the function only return the first.
     * @param word
     * @return wordPosPair
     */
    public WordPosPair getWordPosPairFromCurrentParseResult
            (String word){
        String result = "";
        String[] poses = {};
        if(null != currentParseResult) {
            result = getWordPos(currentParseResult,word);
            if(null == result || result.equals("")){
//                System.out.println("have not found the pos!!");
                return null;
            }
            else {
                poses = result.trim().split(" ");
                if (poses.length == 1) {
//                    System.out.println("the result is " + poses[0] + "!!");
                    return new WordPosPair(word, poses[0]);
                } else {
//                    System.out.println("multi results, and the first is " + poses[0] + "!!");
                    return new WordPosPair(word, poses[0]);
                }
            }
        }
        else {
//            System.out.println("have not found the pos!!");
            return null;
        }
    }

    /**
     * check the parse tree if contain the word.
     * @param root
     * @param word
     * @return
     */
    private boolean checkWord(Tree root, String word){
        Tree[] children = root.children();
        if(null != children){
            if(children.length == 0) {
                if(root.label().value().equals(word)){
                    System.out.println("I found the word!!");
                    return true;
                }
                else {
                    return false;
                }
            }
            else{
                for(Tree node:children){
                    if(checkWord(node,word))
                    {
                        return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    /**
     * the result should take a checking process for more than one time matching.
     * @param root
     * @param word
     * @return
     */
    private String getWordPos(Tree root, String word){
        String pos = "";
        Tree[] children = root.children();
        if(children.length == 1)//it means the word we want may in the children node array.
        {
            //children[0].label().value().equals(word) is to check if the node label is the word
            //Double.isNaN(children[0].score()) is to check if the node is a leave.
            if(children[0].label().value().equals(word)
                    && Double.isNaN(children[0].score())){
                return root.label().value() + " ";
            }
            return getWordPos(children[0],word);
        }
        else if(children.length == 0)
        {
            //if the function has not return up level and reach leave level,
            //it means the matching is failed
            return "";
        }
        else{
            for(Tree node:children){
                pos += getWordPos(node,word);
            }
            return pos;
        }
    }


    /**
     *
     * @param candidateWordList
     * @param root
     */
    public void getCandidateWordsFromParseResult(ArrayList<WordPosPair> candidateWordList,
             Tree root){
        Tree[] children = root.children();
        String pos = "";
        String candidateWord = "";
        if(children.length == 1)//it's similar to getWordPos
        {
            pos = root.label().value();
            candidateWord = children[0].label().value();
            if(pos.equals(currentWordPosPair.getPos()) && Double.isNaN(children[0].score())){
                candidateWordList.add(new WordPosPair(candidateWord,pos));
            }
            else{
                getCandidateWordsFromParseResult(candidateWordList,children[0]);
            }
        }
        else if(children.length == 0)
        {
            //reach the leave level, and failed.
            return ;
        }
        else{
            for(Tree node:children){
                getCandidateWordsFromParseResult(candidateWordList,node);
            }
        }
    }

    /**
     * working!!
     * @param candidateWordList
     */
    public void processCandidateWords(ArrayList<WordPosPair> candidateWordList){

    }

    /**
     * be careful !! the function is to check the "NOT" state!
     * if true, the word can be put into next stage.
     * if false, it means: the word is a closed word
     *                    or the process above is somewhere wrong!!
     * @param wpp
     * @return
     */
    public boolean checkIfNOTClosedWord(WordPosPair wpp){
        String pos = wpp.getPos();
        if(null != wpp && null != pos && pos.equals(""))
        {
            // || pos.equals("VBG")
            if(pos.equals("IN") || pos.equals("DT")) {
                return false;
            }
            else
            {
                return true;
            }
        }
        else {
            //it's misunderstanding on semantic.
            return false;
        }
    }

    public Tree getCurrentParseResult() {
        return currentParseResult;
    }

    public void setCurrentParseResult(Tree currentParseResult) {
        this.currentParseResult = currentParseResult;
    }

    public WordPosPair getCurrentWordPosPair() {
        return currentWordPosPair;
    }

    public void setCurrentWordPosPair(WordPosPair currentWordPosPair) {
        this.currentWordPosPair = currentWordPosPair;
    }
}
