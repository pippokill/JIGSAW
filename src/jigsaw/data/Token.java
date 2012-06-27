/*
 * Token.java
 *
 * Created on 3 maggio 2005, 10.33
 */

package jigsaw.data;

/**
 * This class holds all information on a token. This informations are used by word sense disambiguation.
 * @author Basile Pierpaolo and Grieco Franco
 */
public class Token {
    
    /**
     * Holds value of property token.
     */
    private String token;
    
    /**
     * Holds value of property position.
     */
    private int position;
    
    /**
     * Holds value of property syns.
     */
    private String[] syns;
    
    /**
     * Holds value of property syn.
     */
    private String syn=null;
    
    /**
     * Holds value of property groupPosition.
     */
    private int groupPosition;
    
    private String posTag;
    
    /**
     * Holds value of property stem.
     */
    private String stem;
    
    private String lemma;
    
    /** Creates a new instance of Token */
    public Token() {
    }
    
    /**
     * Getter for property token.
     * @return Value of property token.
     */
    public String getToken() {
        return this.token;
    }
    
    /**
     * Setter for property token.
     * @param token New value of property token.
     */
    public void setToken(String token) {
        this.token = token;
    }
    
    /**
     * Getter for property position.
     * @return Value of property position.
     */
    public int getPosition() {
        return this.position;
    }
    
    /**
     * Setter for property position.
     * @param position New value of property position.
     */
    public void setPosition(int position) {
        this.position = position;
    }
    
    /**
     * Getter for property syns.
     * @return Value of property syns.
     */
    public String[] getSyns() {
        return this.syns;
    }
    
    /**
     * Setter for property syns.
     * @param syns New value of property syns.
     */
    public void setSyns(String[] syns) {
        this.syns = syns;
    }
    
    /**
     * Getter for property syn.
     * @return Value of property syn.
     */
    public String getSyn() {
        return this.syn;
    }
    
    /**
     * Setter for property syn.
     * @param syn New value of property syn.
     */
    public void setSyn(String syn) {
        this.syn = syn;
    }
    
    /**
     * Getter for property groupPosition.
     * @return Value of property groupPosition.
     */
    public int getGroupPosition() {
        return this.groupPosition;
    }
    
    /**
     * Setter for property groupPosition.
     * @param groupPosition New value of property groupPosition.
     */
    public void setGroupPosition(int groupPosition) {
        this.groupPosition = groupPosition;
    }
    
    /**
     * Getter for property stem.
     * @return Value of property stem.
     */
    public String getStem() {
        return this.stem;
    }
    
    /**
     * Setter for property stem.
     * @param stem New value of property stem.
     */
    public void setStem(String stem) {
        this.stem = stem;
    }

    /**
     * @return the posTag
     */
    public String getPosTag() {
        return posTag;
    }

    /**
     * @param posTag the posTag to set
     */
    public void setPosTag(String posTag) {
        this.posTag = posTag;
    }

    /**
     * @return the lemma
     */
    public String getLemma() {
        return lemma;
    }

    /**
     * @param lemma the lemma to set
     */
    public void setLemma(String lemma) {
        this.lemma = lemma;
    }
    
}
