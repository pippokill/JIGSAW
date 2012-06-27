/*
 * TokenArrayList.java
 *
 * Created on 3 maggio 2005, 10.34
 */

package jigsaw.data;
import java.util.ArrayList;
import java.util.List;

/**
 * This class holds all information on a token group. This informations are used by word sense disambiguation. A token group is a group that contains all token which have the same pos-tag.
 * @author Basile Pierpaolo and Grieco Franco
 */
public class TokenGroup {
    
    /**
     * Holds value of property posTag.
     */
    private String posTag;
    private List<Token> tokens=new ArrayList<Token>();
    
    /**
     * Creates a new instance of TokenGroup
     */
    public TokenGroup() {
    }
    
    /**
     * Creates a new instance of TokenGroup
     * @param posTag Pos-tag of this task group
     */    
    public TokenGroup(String posTag) {
        this.posTag=posTag;
    }
    
    /**
     * Getter for property posTag.
     * @return Value of property posTag.
     */
    public String getPosTag() {
        return this.posTag;
    }
    
    /**
     * Setter for property posTag.
     * @param posTag New value of property posTag.
     */
    public void setPosTag(String posTag) {
        this.posTag = posTag;
    }
    
    /**
     * Get the token at a specified position
     * @param index Index
     * @return Token
     */    
    public Token get(int index) {
        return tokens.get(index);
    }
    
    /**
     * Add a token to token group
     * @param token Token
     */    
    public void add(Token token) {
        tokens.add(token);
    }
    
    /**
     * Return the number of token
     * @return Number of token
     */    
    public int size() {
        return tokens.size();
    }
    
    /**
     * Delete all tokens
     */    
    public void clear() {
        tokens.clear();
    }
    
    /**
     * Getter for property tokens.
     * @return Value of property tokens.
     */
    public List<Token> getTokens() {
        return tokens;
    }    
    
    /**
     * Setter for property tokens.
     * @param tokens New value of property tokens.
     */
    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
    
}
