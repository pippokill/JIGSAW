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
 * Neither the name of the University of Bari nor the names of its
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
 * MWN domain
 *
 * @author Pierpaolo
 */
public class MWNDomain {

    /**
     * Creates a new instance of Domain
     */
    public MWNDomain() {
    }
    private String domain;

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
    private List<MWNDomain> hype = new ArrayList<MWNDomain>();

    public List<MWNDomain> getHype() {
        return this.hype;
    }

    public void setHype(List<MWNDomain> hype) {
        this.hype = hype;
    }
    private List<MWNDomain> hypo = new ArrayList<MWNDomain>();

    public List<MWNDomain> getHypo() {
        return this.hypo;
    }

    public void setHypo(List<MWNDomain> hypo) {
        this.hypo = hypo;
    }

    public void addHype(MWNDomain d) {
        hype.add(d);
    }

    public void addHypo(MWNDomain d) {
        hypo.add(d);
    }

    @Override
    public String toString() {
        if (domain == null) {
            return "";
        } else {
            return domain;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MWNDomain other = (MWNDomain) obj;
        if ((this.domain == null) ? (other.domain != null) : !this.domain.equals(other.domain)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.domain != null ? this.domain.hashCode() : 0);
        return hash;
    }
}
