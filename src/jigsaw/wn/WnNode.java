/*
 * WnNode.java
 *
 * Created on 3 giugno 2005, 16.56
 */

package jigsaw.wn;

/**
 *
 * @author  Basile Pierpaolo and Grieco Franco
 */
public class WnNode {
    
    /**
     * Holds value of property offset.
     */
    private String offset;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WnNode other = (WnNode) obj;
        if ((this.offset == null) ? (other.offset != null) : !this.offset.equals(other.offset)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.offset != null ? this.offset.hashCode() : 0);
        return hash;
    }
    
    
    
    /**
     * Holds value of property depth.
     */
    private int depth;
    
    /** Creates a new instance of WnNode */
    public WnNode() {
    }
    
    /**
     * Getter for property depth.
     * @return Value of property depth.
     */
    public int getDepth() {
        return this.depth;
    }
    
    /**
     * Setter for property depth.
     * @param depth New value of property depth.
     */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return this.offset+" "+this.depth;
    }
    
    
}
