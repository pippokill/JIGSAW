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
 * MWN lemma
 *
 * @author Pierpaolo
 */
public class MWNLemma {

    /**
     * Creates a new instance
     */
    public MWNLemma() {
    }
    private List<MWNPointer> list = new ArrayList();
    /**
     * Holds value of property lemma.
     */
    private String lemma;

    public String getLemma() {
        return this.lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }
    private String[] noun;

    public String[] getNoun() {
        return this.noun;
    }

    public void setNoun(String[] noun) {
        this.noun = noun;
    }
    private String[] verb;

    public String[] getVerb() {
        return this.verb;
    }

    public void setVerb(String[] verb) {
        this.verb = verb;
    }
    private String[] adj;

    public String[] getAdj() {
        return this.adj;
    }

    public void setAdj(String[] adj) {
        this.adj = adj;
    }
    private String[] adv;

    public String[] getAdv() {
        return this.adv;
    }

    public void setAdv(String[] adv) {
        this.adv = adv;
    }

    public boolean addPointer(MWNPointer p) {
        return list.add(p);
    }
}
