/**
   Copyright (c) 2012, the JIGSAW AUTHORS.

   All rights reserved.

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions are
   met:

 * Redistributions of source code must retain the above copyright
   notice, this list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above
   copyright notice, this list of conditions and the following
   disclaimer in the documentation and/or other materials provided
   with the distribution.

 * Neither the name of the University of Bari nor the names
   of its contributors may be used to endorse or promote products
   derived from this software without specific prior written
   permission.

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
   A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
   PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
   LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
   NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 **/
package jigsaw.data;
import java.util.ArrayList;
import java.util.List;

/**
 * This class stores information about a token group.
 * A token group is a group of tokens belong to the same pos-tag.
 * @author Basile Pierpaolo
 */
public class TokenGroup {
    
    /**
     * Holds value of property posTag.
     */
    private String posTag;
    private List<Token> tokens=new ArrayList<Token>();
    
    /**
     * Creates a new instance
     */
    public TokenGroup() {
    }
    
    /**
     * Creates a new instance
     * @param posTag Pos-tag assigned to the token group
     */    
    public TokenGroup(String posTag) {
        this.posTag=posTag;
    }
    
   
    
    /**
     * Get the token at a specified position
     * @param index Index
     * @return Token
     */    
    public Token get(int index) {
        return getTokens().get(index);
    }
    
    /**
     * Add a token
     * @param token Token
     */    
    public void add(Token token) {
        getTokens().add(token);
    }
    
    /**
     * Return the number of tokens
     * @return Number of token
     */    
    public int size() {
        return getTokens().size();
    }
    
    /**
     * Delete all tokens
     */    
    public void clear() {
        getTokens().clear();
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
     * @return the tokens
     */
    public List<Token> getTokens() {
        return tokens;
    }

    /**
     * @param tokens the tokens to set
     */
    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
    
}
