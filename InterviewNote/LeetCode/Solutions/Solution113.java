package leetcode;
import sun.reflect.generics.tree.Tree;

import java.util.*;
/**
 * @ClassName: Solution113
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/8/17 22:22
 * @Version: TODO
 **/
public class Solution113 {
    List<List<Integer>> res = new ArrayList<List<Integer>>();
    List<Integer> curPath = new ArrayList<Integer>();
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        if(root == null)
            return res;
        curPath.add(root.val);
        findPath(root, sum);
        return res;
    }
    public void findPath(TreeNode root, int sum){
        // reach the bottom, return
        if(root == null)
            return;
        // remain to substract
        int remain = sum - root.val;
        // if remain is 0 while the current node should be leafnode
        if(root.left == null && root.right == null){
            if(remain == 0)
                res.add(curPath);
            return;
        }
        // pass the ramain to child node
        if(root.left != null){
            curPath.add(root.left.val);
            findPath(root.left, remain);
            // recover the state of curPath
        }

        if(root.right != null){
            curPath.add(root.right.val);
            findPath(root.right, remain);
        }
        curPath.remove(curPath.size()-1);
        return;
    }

    public static void main(String args[]){
        BuildTree newTree = new BuildTree();
        TreeNode root = newTree.buildTree();
        newTree.printTree();
        Solution113 test = new Solution113();
        List<List<Integer>> res = test.pathSum(root, 11);
        for(List<Integer> list: res){
            for(Integer i: list){
                System.out.print(i);
                System.out.println(" ");
            }
            System.out.println(" ");
        }
    }
}
