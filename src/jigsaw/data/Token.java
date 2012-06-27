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

 * Neither the name of the University of Pittsburgh nor the names
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

/**
 * This class stores information about a token.
 * @author Basile Pierpaolo
 */
public class Token {
    
    /**
     * Token value
     */
    private String token;
    
    /**
     * Token position in the text
     */
    private int position;
    
    /**
     * Token synsets
     */
    private String[] syns;
    
    /**
     * Token synset assigned by WSD algorithm
     */
    private String syn=null;
    
    /**
     * Token position in the TokenGroup
     */
    private int groupPosition;
    
    /**
     * Token pos-tag
     */
    private String posTag;
    
   /**
     * Token stem
     */
    private String stem;
    
    /**
     * Token lemma
     */
    private String lemma;
    
    /** Creates a new instance*/
    public Token() {
    }
    
 
    
    

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * @return the syns
     */
    public String[] getSyns() {
        return syns;
    }

    /**
     * @param syns the syns to set
     */
    public void setSyns(String[] syns) {
        this.syns = syns;
    }

    /**
     * @return the syn
     */
    public String getSyn() {
        return syn;
    }

    /**
     * @param syn the syn to set
     */
    public void setSyn(String syn) {
        this.syn = syn;
    }

    /**
     * @return the groupPosition
     */
    public int getGroupPosition() {
        return groupPosition;
    }

    /**
     * @param groupPosition the groupPosition to set
     */
    public void setGroupPosition(int groupPosition) {
        this.groupPosition = groupPosition;
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
     * @return the stem
     */
    public String getStem() {
        return stem;
    }

    /**
     * @param stem the stem to set
     */
    public void setStem(String stem) {
        this.stem = stem;
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
