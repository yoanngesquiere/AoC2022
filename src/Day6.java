import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class Day6 {
    public String getExampleOutput(boolean fullOutput) {
        Helper helper = new Helper();

        return fullOutput ? helper.getExampleOutput("day6Input") : "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw";
    }

    public int findDistinctFollowingChars(String input, int length) {
        char[] chars = input.toCharArray();

        for (int i = length-1; i< chars.length;i++) {
            ArrayList<Character> existing = new ArrayList<Character>();
            boolean unique = true;
            for (int j = i - length + 1; j <= i; j++) {
                if (existing.contains(chars[j])) {
                    unique = false;
                }
                existing.add(chars[j]);
            }
            if (!unique) {
                continue;
            }
            return (i+1);
        }
        return 0;
    }

    public void executePart1()
    {
        String input = getExampleOutput(true);
        System.out.println(findDistinctFollowingChars(input, 4));
    }

    public void executePart2()
    {
        String input = getExampleOutput(true);
        System.out.println(findDistinctFollowingChars(input, 14));
    }
}
