public class BTree {
    private BTreeNode root;
    private int maxKeys;

    public BTree(int maxKeys) {
        this.root = new BTreeNode(maxKeys, true);
        this.maxKeys = maxKeys;
    }

    public void insert(ImageFeature key) {
        BTreeNode r = root;
        if (r.numKeys == maxKeys) {
            BTreeNode s = new BTreeNode(maxKeys, false);
            s.children[0] = r;
            splitChild(s, 0);
            root = s;
            insertNonFull(s, key);
        } else {
            insertNonFull(r, key);
        }
    }

    private void insertNonFull(BTreeNode node, ImageFeature key) {
        int i = node.numKeys - 1;

        if (node.isLeaf) {
            // Shift keys to make space
            while (i >= 0 && key.name.compareTo(node.keys[i].name)<0) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.numKeys++;
        } else {
            while (i >= 0 && key.name.compareTo(node.keys[i].name)<0) {
                i--;
            }
            i++;
            if (node.children[i].numKeys == maxKeys) {
                splitChild(node, i);
                if (key.name.compareTo(node.keys[i].name)>0) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }

    private void splitChild(BTreeNode parent, int index) {
        BTreeNode fullChild = parent.children[index];
        BTreeNode newNode = new BTreeNode(maxKeys, fullChild.isLeaf);
        int mid = maxKeys / 2;

        // Move keys to new node
        for (int j = 0; j < maxKeys - mid - 1; j++) {
            newNode.keys[j] = fullChild.keys[j + mid + 1];
        }
        newNode.numKeys = maxKeys - mid - 1;

        // Move children if not leaf
        if (!fullChild.isLeaf) {
            for (int j = 0; j < maxKeys - mid; j++) {
                newNode.children[j] = fullChild.children[j + mid + 1];
            }
        }

        // Reduce number of keys in fullChild
        fullChild.numKeys = mid;

        // Shift parent's children to make space for new node
        for (int j = parent.numKeys; j >= index + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[index + 1] = newNode;

        // Shift parent's keys
        for (int j = parent.numKeys - 1; j >= index; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }
        parent.keys[index] = fullChild.keys[mid];
        parent.numKeys++;
    }

    public boolean search(ImageFeature key) {
        return searchRecursive(root, key);
    }

    private boolean searchRecursive(BTreeNode node, ImageFeature key) {
        int i = 0;
        while (i < node.numKeys && key.name.compareTo(node.keys[i].name)>0) {
            i++;
        }

        if (i < node.numKeys && key.name.equals(node.keys[i].name)) {
            return true;
        } else if (node.isLeaf) {
            return false;
        } else {
            return searchRecursive(node.children[i], key);
        }
    }

    public ImageFeature returnFeature(String name) {//using the search code return the feature instead
        return returnFeatureRecursive(root, name);
    }

    private ImageFeature returnFeatureRecursive(BTreeNode node, String name) {//using the search code return the feature instead
        int i = 0;
        // find first key ≥ name
        while (i < node.numKeys && name.compareTo(node.keys[i].name) > 0) {
            i++;
        }
        if (i < node.numKeys && name.equals(node.keys[i].name)) {
            return node.keys[i];
        }

        if (node.isLeaf) {
            return null;
        }

        return returnFeatureRecursive(node.children[i], name);
    }
}