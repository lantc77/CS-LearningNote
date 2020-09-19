package leetcode;
import java.util.*;
/**
 * @ClassName: TreeNode
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/5/5 10:16
 * @Version: TODO
 **/
public class BuildTree {
    public TreeNode buildTree(){
        TreeNode root = new TreeNode(5);
        TreeNode left = new TreeNode(4);
        TreeNode right = new TreeNode(6);

        root.left = left;
        root.right = right;

        left = new TreeNode(2);
        right = new TreeNode(9);
        root.left.left = left;
        root.left.right = right;

        return root;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<List<Integer>> ();
        if(root == null) return res;
        List<Integer> nodeValueList = new ArrayList<Integer>();
        // save the nodes of current level
        List<TreeNode> nodeList = new ArrayList<TreeNode>();
        // save all the subnodes of next level
        List<TreeNode> subNodeList = new ArrayList<TreeNode>();

        nodeList.add(root);
        // if subNodeList is still empty, end the loop;
        while(!nodeList.isEmpty()){
            TreeNode curNode = nodeList.get(0);
            nodeValueList.add(curNode.val);

            if(curNode.left != null) subNodeList.add(curNode.left);
            if(curNode.right != null) subNodeList.add(curNode.right);
            nodeList.remove(0);
            // current level is done;
            if(nodeList.isEmpty()){
                res.add(nodeValueList);
                nodeValueList = new ArrayList<Integer>();
                nodeList = subNodeList;
                subNodeList = new ArrayList<TreeNode>();
            }
        }
        return res;
    }

    public void printTree(){
        TreeNode root = buildTree();
        List<List<Integer>> nodeLevel = levelOrder(root);
        for(List<Integer> level: nodeLevel){
            for(Integer val: level){
                System.out.print(val);
                System.out.print(" ");
            }
            System.out.println("");
        }
    }
}
