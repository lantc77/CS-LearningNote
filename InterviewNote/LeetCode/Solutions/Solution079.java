package leetcode;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * @ClassName: Solution079
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/8/8 16:25
 * @Version: TODO
 **/
public class Solution079 {
    boolean flag = false;
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        int[][] visited = new int[m][n];
        for(int i = 0; i < m; i++)
            for(int j = 0; j < n; j++)
                if(board[i][j] == word.charAt(0)){
                    if(word.length() == 1)
                        return true;
                    else
                        dfs(board, visited, word, 0, i, j);
                }
        return flag;
    }

    public void dfs(char[][] board, int[][] visited, String word, int loc, int x, int y){
        char c = word.charAt(loc);
        if(visited[x][y] == 1)
            return;

        if(board[x][y] != word.charAt(loc)){
            return;
        }
        if(loc == word.length() - 1){
            flag = true;
            return;
        }

        visited[x][y] = 1;
        if(x-1>=0) dfs(board, visited, word, loc+1, x-1, y);
        if(y-1>=0) dfs(board, visited, word, loc+1, x, y-1);
        if(y+1 < board[0].length) dfs(board, visited, word, loc+1, x, y+1);
        if(x+1 < board.length) dfs(board, visited, word, loc+1, x+1, y);
        visited[x][y] = 0;
    }

    public static void main(String args[]){
        char[][] board = {{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}};
        boolean YES = new Solution079().exist(board, "ABCB");
        System.out.println(YES);
    }
}
