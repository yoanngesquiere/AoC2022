import java.util.ArrayList;
import java.util.Arrays;

public class Day10 {
    public String getExampleOutput(boolean fullOutput) {
        Helper helper = new Helper();

        return fullOutput ? helper.getExampleOutput("day10Input") : """
                addx 15
                addx -11
                addx 6
                addx -3
                addx 5
                addx -1
                addx -8
                addx 13
                addx 4
                noop
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx 5
                addx -1
                addx -35
                addx 1
                addx 24
                addx -19
                addx 1
                addx 16
                addx -11
                noop
                noop
                addx 21
                addx -15
                noop
                noop
                addx -3
                addx 9
                addx 1
                addx -3
                addx 8
                addx 1
                addx 5
                noop
                noop
                noop
                noop
                noop
                addx -36
                noop
                addx 1
                addx 7
                noop
                noop
                noop
                addx 2
                addx 6
                noop
                noop
                noop
                noop
                noop
                addx 1
                noop
                noop
                addx 7
                addx 1
                noop
                addx -13
                addx 13
                addx 7
                noop
                addx 1
                addx -33
                noop
                noop
                noop
                addx 2
                noop
                noop
                noop
                addx 8
                noop
                addx -1
                addx 2
                addx 1
                noop
                addx 17
                addx -9
                addx 1
                addx 1
                addx -3
                addx 11
                noop
                noop
                addx 1
                noop
                addx 1
                noop
                noop
                addx -13
                addx -19
                addx 1
                addx 3
                addx 26
                addx -30
                addx 12
                addx -1
                addx 3
                addx 1
                noop
                noop
                noop
                addx -9
                addx 18
                addx 1
                addx 2
                noop
                noop
                addx 9
                noop
                noop
                noop
                addx -1
                addx 2
                addx -37
                addx 1
                addx 3
                noop
                addx 15
                addx -21
                addx 22
                addx -6
                addx 1
                noop
                addx 2
                addx 1
                noop
                addx -10
                noop
                noop
                addx 20
                addx 1
                addx 2
                addx 2
                addx -6
                addx -11
                noop
                noop
                noop
                 """;
    }

    private int nbCycles = 0;
    private int x = 1;

    private int[] monitoredCycles = new int[] {
            20, 60, 100, 140, 180, 220
    };
    private int[] monitoredCycles2 = new int[] {
            40, 80, 120, 160, 200
    };

    String[] monitor = new String[] {
        "........................................",
        "........................................",
        "........................................",
        "........................................",
        "........................................",
        "........................................",
    };

    private int result;

    private void updateScreen() {
        int line = (nbCycles -1)  / 40;
        int positionOnLine = (nbCycles - 1) % 40;
        if (positionOnLine >= x-1 && positionOnLine <= x+1) {
            StringBuilder builder = new StringBuilder(monitor[line]);
            builder.setCharAt(positionOnLine, '#');
            monitor[line] = builder.toString();
        }

    }

    private void monitor(int part) {
        if (part == 1) {
            if (Arrays.stream(this.monitoredCycles).anyMatch(a -> a == this.nbCycles)) {
                result += this.nbCycles * this.x;
            }
        } else {
            updateScreen();
        }
    }

    private void noop(int part) {
        this.nbCycles++;
        this.monitor(part);
    }

    private void addX(int x, int part) {
        this.nbCycles++;
        this.monitor(part);
        this.x += x;
    }


    public void executePart1() {
        String[] input = getExampleOutput(true).split("\\n");

        for (String s: input) {
            String action = s.split(" ")[0];
            this.noop(1);
            if (!action.equals("noop")) {
                int value = Integer.parseInt(s.split(" ")[1]);
                this.addX(value, 1);
            }

        }
        System.out.println(this.result);
        System.out.println("end");
    }

    public void executePart2() {
        String[] input = getExampleOutput(true).split("\\n");

        for (String s: input) {
            String action = s.split(" ")[0];
            this.noop(2);
            if (!action.equals("noop")) {
                int value = Integer.parseInt(s.split(" ")[1]);
                this.addX(value, 2);
            }

        }
        for (String s : monitor) {
            System.out.println(s);
        }
        System.out.println("end");
    }
}