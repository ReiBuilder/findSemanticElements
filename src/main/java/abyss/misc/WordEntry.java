package abyss.misc;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Abyssss on 2016/12/11.
 */
/*
this class is to record word and its definition.
 */
public class WordEntry implements Serializable{

    private String word;
    private String pos;
    private String definition;
    private UUID uuid;

    public WordEntry(String word, String pos, String definition, UUID uuid) {
        this.word = word;
        this.pos = pos;
        this.definition = definition;
        this.uuid = uuid;
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

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
