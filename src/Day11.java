import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day11 {
    public String getExampleOutput(boolean fullOutput) {
        Helper helper = new Helper();

        return fullOutput ? helper.getExampleOutput("day11Input") : """
                Monkey 0:
                  Starting items: 79, 98
                  Operation: new = old * 19
                  Test: divisible by 23
                    If true: throw to monkey 2
                    If false: throw to monkey 3
                
                Monkey 1:
                  Starting items: 54, 65, 75, 74
                  Operation: new = old + 6
                  Test: divisible by 19
                    If true: throw to monkey 2
                    If false: throw to monkey 0
                
                Monkey 2:
                  Starting items: 79, 60, 97
                  Operation: new = old * old
                  Test: divisible by 13
                    If true: throw to monkey 1
                    If false: throw to monkey 3
                
                Monkey 3:
                  Starting items: 74
                  Operation: new = old + 3
                  Test: divisible by 17
                    If true: throw to monkey 0
                    If false: throw to monkey 1
                 """;
    }

    Monkey[] monkeys;

    int divider = 1;

    public void calculateDivider() {

        for (Monkey monkey: this.monkeys) {
            this.divider *= monkey.testDivisibleBy;
        }
    }

    public Long calculateMonkeyBusiness()
    {
        Long[] max = new Long[] {0L, 0L};
        for (Monkey monkey : this.monkeys) {
            if (monkey.inspectedItems > max[0]) {
                max[1] = max[0];
                max[0] = (long) monkey.inspectedItems;
            } else if (monkey.inspectedItems > max[1]){
                max[1] = (long) monkey.inspectedItems;
            }
        }
        return max[0] * max[1];
    }

    public void executeRound(boolean divideBy3) {
        for(Monkey monkey: this.monkeys) {
            for (BigInteger item: monkey.items) {
                monkey.inspectedItems++;
                BigInteger worryLevel = monkey.getCalculatedWorryLevel(item);
                if (divideBy3) {
                    worryLevel =  worryLevel.divide(BigInteger.valueOf(3));
                } else {
                    worryLevel = worryLevel.remainder(BigInteger.valueOf(this.divider));
                }
                if (worryLevel.remainder(BigInteger.valueOf(monkey.testDivisibleBy)) == BigInteger.valueOf(0)) {
                    this.monkeys[monkey.trueMonkey].addItem(worryLevel);
                } else {
                    this.monkeys[monkey.falseMonkey].addItem(worryLevel);
                }
            }
            monkey.items = new ArrayList<>();
        }
    }

    public void initMonkeys(String[] monkeys) {
        this.monkeys = new Monkey[monkeys.length];

        for (String s: monkeys) {
            String[] monkeyDef = s.split("\\n");
            Monkey monkey = new Monkey();
            monkey.number = Integer.parseInt(monkeyDef[0].substring(7, 8));
            String[] items = monkeyDef[1].substring(18).split(", ");
            for (String item: items) {
                monkey.addItem(BigInteger.valueOf(Integer.parseInt(item)));
            }
            monkey.operation = monkeyDef[2].charAt(23);
            monkey.operand = monkeyDef[2].substring(25);
            monkey.testDivisibleBy = Integer.parseInt(monkeyDef[3].substring(21));
            monkey.trueMonkey = Integer.parseInt(monkeyDef[4].substring(29));
            monkey.falseMonkey = Integer.parseInt(monkeyDef[5].substring(30));

            this.monkeys[monkey.number] = monkey;
        }
    }
    public void executePart1() {
        initMonkeys(getExampleOutput(true).split("\\n\\n"));
        for (int i = 0; i < 20; i++) {
            executeRound(true);
        }

        System.out.println(calculateMonkeyBusiness());
        System.out.println("end");
    }

    public void executePart2() {
        initMonkeys(getExampleOutput(true).split("\\n\\n"));

        calculateDivider();
        for (int i = 0; i < 10000; i++) {
            executeRound(false);
        }

        System.out.println(calculateMonkeyBusiness());
        System.out.println("end");
    }
}

class Monkey {
    public int number;
    public int inspectedItems = 0;
    public List<BigInteger> items = new ArrayList<>();
    public char operation;
    public String operand;
    public int testDivisibleBy;
    public int trueMonkey;
    public int falseMonkey;

    public void addItem(BigInteger item) {
        this.items.add(item);
    }

    public BigInteger getCalculatedWorryLevel(BigInteger item) {
        BigInteger operand;
        if (this.operand.equals("old")) {
            operand = item;
        } else {
            operand = BigInteger.valueOf(Integer.parseInt(this.operand));
        }

        if (this.operation == '+') {
            return item.add(operand);
        }
        return item.multiply(operand);
    }
}
