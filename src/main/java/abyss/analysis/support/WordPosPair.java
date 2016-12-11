package abyss.analysis.support;

/**
 * Created by Abyssss on 2016/12/11.
 */
public class WordPosPair {

    private String word;
    private String pos;

    public WordPosPair(String word, String pos) {
        this.word = word;
        this.pos = pos;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }
}
