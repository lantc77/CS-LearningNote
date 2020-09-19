package leetcode;
import java.util.*;
/**
 * @ClassName: Solution006
 * @Description: TODO
 * @Author: Eric Lan
 * @Date: 2020/4/22 22:02
 * @Version: TODO
 **/
public class Solution006 {
    public String convert(String s, int numRows) {
        if(numRows == 1 || numRows >= s.length())
            return s;

        // this list stores 'numRows' StringBuilder
        ArrayList<StringBuilder> zig = new ArrayList<StringBuilder>();
        // initial the StringBuilder
        for(int i = 0; i < numRows; i++)
            zig.add(new StringBuilder(""));

        // direct means the direction of moving, 0 is down, 1 is up.
        int curRow = -1, direct = 0;
        for(int i = 0;i < s.length(); i ++){
            // step down
            if(direct == 0) {
                curRow ++;
                if (curRow < numRows)
                    zig.get(curRow).append(s.charAt(i));
                else{
                    direct = 1; // turn the direction
                    curRow = curRow - 2 ;
                    zig.get(curRow).append(s.charAt(i));
                }
            }

            else if(direct == 1){
                curRow --;
                if(curRow >= 0)
                    zig.get(curRow).append(s.charAt(i));
                else{
                    direct = 0; // turn the direction
                    curRow = curRow + 2;
                    zig.get(curRow).append(s.charAt(i));
                }
            }
        }
        StringBuilder ans = new StringBuilder("");
        for(StringBuilder slice: zig){
            System.out.println(slice.toString());
            ans.append((slice));
        }

        return ans.toString();
    }

    public static void main(String args[]){
        String s = "PAYPALISHIRING";
        int numRows = 3;
        Solution006 test = new Solution006();
        System.out.println(s);
        System.out.println(test.convert(s,3));
    }


}
