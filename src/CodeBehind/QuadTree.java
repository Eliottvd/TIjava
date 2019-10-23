/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeBehind;

/**
 *
 * @author Eliott
 */
public class QuadTree {

    private Image img;
    private Node root;
    private static String tree_data;


    public QuadTree(Image img) {
        this.img = img;

        // matrix index starts with 0, thus (get dimension - 1)
        int dimension = img.getDimension() - 1;

        // image will be given as square
        // so no need for height and width, just dimension instead
        root = new Node(0,0, dimension, dimension);
        root.parent = null;
    }

    public void divide(int threshold) {
        rec_divide(root, threshold);
    }
    private void rec_divide(Node n, int t) {
        // assist function for divide:
        // recursive divide method

        if (n != null) {
            if (n.i1 != n.i2 && n.j1 != n.j2) {
                if (n.isExternal()) {

                    // asks image class if this is necessary or not
                    if (img.decider_by_threshold(t, n.i1, n.j1, n.i2, n.j2))
                        n.divide_node();
                }
                rec_divide(n.nw, t);
                rec_divide(n.sw, t);
                rec_divide(n.ne, t);
                rec_divide(n.se, t);
            }
        }
    }


    public Image compress(int depth, String output_file_name) {
        return img.compress(tree_data_by_depth(depth), depth, output_file_name);
    }

    private String tree_data_by_depth(int depth) {
        tree_data = "";
        rec_tree_data_by_depth(root, depth);
        return tree_data;
    }
    private void rec_tree_data_by_depth(Node n, int depth) {
        // assist function for tree_data_by_depth:
        // recursive displaying index data of the tree by depth method

        if (n != null) {
            if (depth(n) <= depth)
                tree_data += n.i1 + " " + n.j1 + " " +
                             n.i2 + " " + n.j2 + "\n";
            rec_tree_data_by_depth(n.nw, depth);
            rec_tree_data_by_depth(n.ne, depth);
            rec_tree_data_by_depth(n.sw, depth);
            rec_tree_data_by_depth(n.se, depth);
        }

    }
    private int depth(Node n) {
        // actually a node method
        if (n == root)
            return 0;
        return 1 + depth(n.parent);
    }

    public int height() {
        // assumed depth(root) is 0
        // thus 4^(tree.height()) is #of Pixels
        Node n = root;
        int count = 0;
        while (n.isInternal()) {
            n = n.nw;
            count++;
        }
        return count;
    }

    public void display() {
        rec_display(root);
    }
    private void rec_display(Node n) {
        // assist function for display:
        // recursive display method

        if (n != null) {
            System.out.println(n);
            rec_display(n.nw);
            rec_display(n.sw);
            rec_display(n.ne);
            rec_display(n.se);
        }
    }


    private class Node {
        // holds basically four integers as (i1, j1), (i2, j2)
        // those represent northwest and southeast indexes
        // (in the matrix in Image class) of the sub image.

        private int i1, i2, j1, j2;
        private Node parent;
        private Node nw, ne, sw, se;

        public Node(int i1, int j1, int i2, int j2) {
            this.i1 = i1;
            this.i2 = i2;
            this.j1 = j1;
            this.j2 = j2;
        }

        void divide_node() {
            Node nw = new Node(i1, j1, (i1 + i2) / 2, (j1 + j2) / 2);
            Node ne = new Node(i1, (j1 + j2) / 2 + 1, (i1 + i2) / 2, j2);
            Node sw = new Node((i1 + i2) / 2 + 1, j1, i2, (j1 + j2) / 2);
            Node se = new Node((i1 + i2) / 2 + 1, (j1 + j2) / 2 + 1 , i2, j2);
            this.nw = nw;
            this.ne = ne;
            this.sw = sw;
            this.se = se;
            nw.parent = this;
            ne.parent = this;
            sw.parent = this;
            se.parent = this;
        }

        boolean isExternal() { return this.nw == null; }
        boolean isInternal() { return !this.isExternal(); }

        @Override
        public String toString() {
            return depth(this) +" (" + i1 + "," + j1 + "), "  + "(" + i2 + ", " + j2 +")";
        }
    }

}