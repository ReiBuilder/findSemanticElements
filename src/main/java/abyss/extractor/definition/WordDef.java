package abyss.extractor.definition;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Abyssss on 2016/11/28.
 *
 * WordDef class is to record definition of word.
 * Although many words have different means and
 * the structure as which means belongs to one word often be described as a tree,
 * I use UUID (as parallel model) to discriminate these means to instead.
 *
 * Also, WordDef need to implement serializable as I want to store these word on
 * disk files.
 *
 */
public class WordDef implements Serializable{

    private UUID uuid;
    private String word;
    private FunctionLabel fl;
    private String def;

    public WordDef(UUID uuid, String word, FunctionLabel fl, String def) {
        this.uuid = uuid;
        this.word = word;
        this.fl = fl;
        this.def = def;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getWord() {
        return word;
    }

    public FunctionLabel getFl() {
        return fl;
    }

    public String getDef() {
        return def;
    }

    enum FunctionLabel
    {
        NOUN,
        VI,
        VT,
        ADJ,
        ADV
    }
}
