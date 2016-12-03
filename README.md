# FindSemEle

## Introduction
FindSemEle is a tool to find fine grain semantic element from (electronic) dictionary.

The "fine grain semantic element" is defined on a new semantic theory which different from all other theories existing now.
The theory I called SSAE inspired by Embodied cognition and a view that consider cognition system as a prediction-comparison system.
It's not the right place to introduce SSAE, and I only give some key notes of semantic element.
Anyone who read files in Extracted and have interests at SSAE, can contact me for further discussion.

Basically, semantic element has been divided into three categories.

1. **Attribute** these conception often link to information which be received by sensory perception of human. for example, conception like "red", "big", "loud" or "strong".
    Attribute element can be always represented by scale to compute, and are comparable.
2. **Relation** the relationship between two (or more) objects In a broad sense.
3. **Action** a process which causes the changing of relation of several objects and attributes of object.

## Content
1. get dic using web service, the formation of dic is often xml.
2. extract word def from xml file.
3. analysis word def using Stanford NLP core.
4. a random-walk like algorithm to find semantic elements on dic

## Use
It's still a unfinished work.
See demos for using.
Fill **config.properties** base your own base.
Within the expected time, the finding algorithm and found semantic elements will not be full open.

