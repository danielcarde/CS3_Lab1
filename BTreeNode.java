public class BTreeNode {
    public ImageFeature[] keys;    
    public BTreeNode[] children;   
    public int numKeys;     
    public boolean isLeaf; 
    public int maxKeys; // Current number of keys 
    
    public BTreeNode(int maxKeys, boolean isLeaf) { 
        this.maxKeys = maxKeys; 
        this.isLeaf = isLeaf; 
        this.keys = new ImageFeature[maxKeys]; 
        this.children = new BTreeNode[maxKeys + 1]; 
        this.numKeys = 0; 
     } 
}
