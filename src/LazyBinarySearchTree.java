public class LazyBinarySearchTree {
    private TreeNode root;
    private final int MIN_VALUE = 1;
    private final int MAX_VALUE = 99;

    // Constructors
    LazyBinarySearchTree() { root = null; };

    /**
     * inserts key in tree
     * @param key integer to insert
     * @return  true if key inserted, false otherwise (integer must already exist)
     * @throws IllegalArgumentException if number range out of bounds
     */
    public boolean insert(int key) throws IllegalArgumentException {
        if (key < MIN_VALUE || key > MAX_VALUE)
            throw new IllegalArgumentException("IllegalArgumentException raised");
        TreeNode node = new TreeNode(key);
        // if root is null, make key the root
        if(isEmpty()) {
            root = node;
            return true;
        }            
        
        // traverse through node until current is null
        // parent will be the parent of key to insert
        TreeNode current = root;
        TreeNode parent = null;
        while(current != null){
            // set parent to current when insertion needs to be done
            parent = current;
            // if current key is key, mark deleted 'false' and return true
            if(current.key == key) {
                current.deleted = true;
                return true;
            }
            // go left if current key > key
            else if(current.key > key)
                current = current.leftChild;
            // go right if current key < key
            else
                current = current.rightChild;
        }
        // once current is null, insert key using parent 
        if(parent.key < key) {
            parent.rightChild = new TreeNode(key);
            return true;
        }
        else if(parent.key > key) {
            parent.leftChild = new TreeNode(key);
            return true;
        }
        // no insertion occured, return false
        else
            return false;
    }

    /**
     * deletes key from tree.
     * @param key integer value to delete
     * @return true if key marked deleted, false otherwise
     * @throws IllegalArgumentException if key is out of range
     */
    public boolean delete(int key) throws IllegalArgumentException {
        if (key < MIN_VALUE || key > MAX_VALUE)
            throw new IllegalArgumentException("IllegalArgumentException raised");
        // check if tree is empty
        if(isEmpty())
            return false;
        // traverse through the tree to find key node, mark deleted 'true' and return true
        // return false if not found
        TreeNode current = root;
        while(current != null) {
            // if current key == key, mark deleted an return true
            if(current.key == key) {
                current.deleted = true;
                return true;
            // go right if current key < key
            } else if (current.key < key)
                current = current.rightChild;
            // go left if current key > key
            else
                current = current.leftChild;
        }
        // key does not exist in tree, return false
        return false;
    }

    /**
     * finds minimum or smallest integer in tree.
     * @return smallest element in tree
     */
    public int findMin() {
        // call findMin(TreeNode) 
        return findMin(root);
    }

    /**
     * find maximum or largest element in tree
     * @return largest element in tree
     */
    public int findMax() {
        return findMax(root);
    }

    /**
     * checks if key is in tree, calls contains wrapper method
     * @param key the key to check for
     * @return true if key is found and not marked deleted, false otherwise
     * @throws IllegalArgumentException
     */
    public boolean contains(int key) throws IllegalArgumentException {
        if (key < MIN_VALUE || key > MAX_VALUE)
            throw new IllegalArgumentException("IllegalArgumentException raised");
        // returns false if tree is empty
        if(isEmpty())
            return false;
        
        // iterate through tree until key is found, return false when no element matches key
        TreeNode current = root;
        while(current != null) {
            if(current.key == key) {
                if(!current.deleted)
                    return true;
                else
                    return false;
            // go right if current key < key
            } else if(current.key < key)
                current = current.rightChild;
            // go left if current key > key
            else
                current = current.leftChild;
        }
        // return false when key is not found and traversed the tree completely
        return false;
    }

    /**
     * returns a string tree contents in pre-order
     */
    public String toString() {
        return preorder(root);
    }

    /**
     * Uses helper method height(TreeNode root) to find height of tree
     * @return  height of tree
     */
    public int height() {
        return height(root);
    }

    /**
     * finds the size of tree using helper method
     * @return  size of tree
     */
    public int size() {
        return size(root);
    }

    /* Helper methods */
    /**
     * Checks if tree is empty
     * @return true if empty, false otherwise
     */
    private boolean isEmpty() {
        return root == null;
    }

    /**
     * helper method for findMin(). Recursively goes left of tree
     * @param rootNode current key in tree
     * @return smallest key
     */
    private int findMin(TreeNode rootNode) {
        // if tree is empty or only root in tree, return -1
        if(isEmpty() || size() == 1)
            return -1;
        // traverse left through tree until left subtree does not exist
        TreeNode previous = rootNode;  // stores previous node in case min node is marked deleted
        TreeNode node = rootNode;
        while(node.leftChild != null) {
            if(!node.deleted)
                previous = node;
            node = node.leftChild;            
        }
        // if minimum node marked deleted, return most left node that is not marked deleted
        if(node.deleted)
            return previous.key;
        // else return current key
        else
            return node.key;
    }

    /**
     * helper method for findMax(). Recursively goes right of tree
     * @param rootNode current key in tree
     * @return largest key
     */
    private int findMax(TreeNode rootNode) {
        // traverse right through tree until right subtree does not exist
        TreeNode node = rootNode;
        // stores previous node in case min node is marked deleted
        TreeNode previous = rootNode;  
        while(node.rightChild != null) {
            if(!node.deleted)
                previous = node;
            node = node.rightChild;
        }

        // if max node marked deleted, return most right node that is not marked deleted
        if(node.deleted)
            return previous.key;
        // else return current key
        else   
            return node.key;
    }

    private String preorder(TreeNode node) {
        // if node is null, return an empty string; base case
        if(node == null) 
            return "";
        // used for deleted node case
        String s = "";
        // used for left subtrees
        String sLeft = "";
        // used for right subtrees 
        String sRight = "";

        if(!node.deleted) 
            s = s + node.key + " ";
        else
            s = s + "*" + node.key + " ";
        // go to left subtree
        sLeft = preorder(node.leftChild);
        // go to right subtre
        sRight = preorder(node.rightChild);
        // return all strings after traversing
        return s + sLeft + sRight;
    }

    /**
     * Calculates the height of tree
     * @param node the root of subtree
     * @return the height of each node (recursively) then return the largest height 
     * of the left and right subtrees
     */
    private int height(TreeNode node) {
        // if current node is null or tree is empty, return -1; height does not exist
        if(node == null || isEmpty())
            return -1;

        // traverse the tree left and right to find largest height 
        int lHeight = height(node.leftChild);
        int rHeight = height(node.rightChild);
        // if left subtree has larger height than right, return left, otheriwise, return right
        if(lHeight > rHeight)
            return lHeight + 1;
        else
            return rHeight + 1;

    }
    
    /**
     * helper method that recursively finds the number of nodes in tre
     * @param node root of subtree
     * @return number of nodes counted
     */
    private int size(TreeNode node) {
        if(node == null || isEmpty())
            return 0;
        else 
            return size(node.leftChild) + size(node.rightChild) + 1;        
    }
    private static class TreeNode {
       public int key;
       public TreeNode leftChild;
       public TreeNode rightChild;
       public boolean deleted;

       // constructor that will assign initial properties of newly added node
       TreeNode(int key) {
           this.key = key;
           leftChild = null;
           rightChild = null;
           deleted = false;
       }
   }
}
