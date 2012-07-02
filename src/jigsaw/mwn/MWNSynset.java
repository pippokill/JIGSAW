/**
 * Copyright (c) 2012, the JIGSAW AUTHORS.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the University of Pittsburgh nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */
package jigsaw.mwn;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * MWN synset
 *
 * @author Pierpaolo
 */
public class MWNSynset {

    /**
     * MWN Synset
     */
    public MWNSynset() {
    }
    private List<MWNPointer> list = new ArrayList<MWNPointer>();
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        if (id.startsWith("n#")) {
            posTag = MWNType.NOUN;
        } else if (id.startsWith("v#")) {
            posTag = MWNType.VERB;
        } else if (id.startsWith("a#")) {
            posTag = MWNType.ADJ;
        } else if (id.startsWith("r#")) {
            posTag = MWNType.ADV;
        }
        this.id = id;
    }
    private String[] word;

    public String[] getWord() {
        return this.word;
    }

    public void setWord(String[] word) {
        this.word = word;
    }
    private String[] phrase;

    public String[] getPhrase() {
        return this.phrase;
    }

    public void setPhrase(String[] phrase) {
        this.phrase = phrase;
    }
    private String gloss;

    public String getGloss() {
        return this.gloss;
    }

    public void setGloss(String gloss) {
        this.gloss = gloss;
    }

    public boolean addPointer(MWNPointer p) {
        return list.add(p);
    }
    private int posTag;

    public int getPosTag() {
        return this.posTag;
    }

    public void setPosTag(int pos) {
        this.posTag = pos;
    }

    @Override
    public String toString() {
        StringBuilder description = new StringBuilder();
        description.append("[").append(this.getId()).append("] [");
        for (int i = 0; i < domain.size(); i++) {
            description.append(domain.get(i));
            if (i < domain.size() - 1) {
                description.append(", ");
            }
        }
        description.append("] ");
        String[] wordS = this.getWord();
        for (int i = 0; wordS != null && i < wordS.length; i++) {
            description.append(wordS[i]).append(" ");
        }
        description.append("; ").append(this.getGloss());
        return description.toString();
    }

    public MWNPointer[] getPointer(int type) {
        return list.toArray(new MWNPointer[list.size()]);
    }
    private List<MWNDomain> domain = new ArrayList();

    /**
     * Getter for property domain.
     *
     * @return Value of property domain.
     */
    public List<MWNDomain> getDomain() {
        return this.domain;
    }

    /**
     * Setter for property domain.
     *
     * @param domain New value of property domain.
     */
    public void setDomain(List<MWNDomain> domain) {
        this.domain = domain;
    }

    /**
     * Add new domain to synset
     *
     * @param d Domain
     */
    public void addDomain(MWNDomain d) {
        domain.add(d);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MWNSynset other = (MWNSynset) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
}
