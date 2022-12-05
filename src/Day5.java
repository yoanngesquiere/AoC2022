import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Stack;

class Crate {
    public char value;

    public Crate(char value) {
        this.value = value;
    }
}

public class Day5 {
    public String getExampleOutput(boolean fullOutput) {
        Helper helper = new Helper();

        return fullOutput ? helper.getExampleOutput("day5Input") : """
                    [D]     
                [N] [C]    
                [Z] [M] [P]
                 1   2   3
                
                move 1 from 2 to 1
                move 3 from 1 to 3
                move 2 from 2 to 1
                move 1 from 1 to 2
                """;
    }

    public void move(HashMap<Integer, Stack<Crate>> cargo, String instruction, boolean multiple) {
        instruction = instruction.replaceAll("[^\\d .]", "");

        String[] split = instruction.trim().split("  ");
        int nb = Integer.parseInt(split[0]);
        int from = Integer.parseInt(split[1]);
        int to = Integer.parseInt(split[2]);

        Stack<Crate> stack = new Stack<>();

        for (int j = 0; j < nb; j++) {
            if (multiple) {
                stack.push(cargo.get(from-1).pop());
            } else {
                cargo.get(to-1).push(cargo.get(from-1).pop());
            }
        }

        while (stack.size() > 0) {
            cargo.get(to-1).push(stack.pop());
        }
    }

    public int executePart1()
    {
        String input = getExampleOutput(true);
        String[] lines = input.split("\\n");

        int nbPiles = 0;
        int i = 0;
        while (lines[i].contains("[")) {
            i++;
        }
        nbPiles = lines[i].split("   ").length;

        //create cargo
        HashMap<Integer, Stack<Crate>> cargo = new HashMap();
        for (int j = 0; j < nbPiles; j++) {
            cargo.put(j, new Stack<>());
        }

        //add crates
        for (int j = i-1; j >= 0; j--) {
            for (int k = 0; k < nbPiles; k++) {
                if (lines[j].length() > k*4) {
                    String crate = lines[j].substring(k*4+1, k*4+2);
                    if (crate.trim().length()>0) {
                        cargo.get(k).push(new Crate(crate.charAt(0)));
                    }
                }
            }
        }

        for (int j = i+2; j< lines.length;j++) {
            move(cargo, lines[j], false);
        }

        String result = "";
        for (int j   = 0; j < nbPiles; j++) {
            char value = cargo.get(j).pop().value;
            result = result.concat(String.valueOf(value));
            System.out.println(value);
        }
        System.out.println(result);

        return 0;
    }
    public int executePart2()
    {
        String input = getExampleOutput(true);
        String[] lines = input.split("\\n");

        int nbPiles = 0;
        int i = 0;
        while (lines[i].contains("[")) {
            i++;
        }
        nbPiles = lines[i].split("   ").length;

        //create cargo
        HashMap<Integer, Stack<Crate>> cargo = new HashMap();
        for (int j = 0; j < nbPiles; j++) {
            cargo.put(j, new Stack<>());
        }

        //add crates
        for (int j = i-1; j >= 0; j--) {
            for (int k = 0; k < nbPiles; k++) {
                if (lines[j].length() > k*4) {
                    String crate = lines[j].substring(k*4+1, k*4+2);
                    if (crate.trim().length()>0) {
                        cargo.get(k).push(new Crate(crate.charAt(0)));
                    }
                }
            }
        }

        for (int j = i+2; j< lines.length;j++) {
            move(cargo, lines[j], true);
        }

        String result = "";
        for (int j   = 0; j < nbPiles; j++) {
            char value = cargo.get(j).pop().value;
            result = result.concat(String.valueOf(value));
            System.out.println(value);
        }
        System.out.println(result);

        return 0;
    }
}
