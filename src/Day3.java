public class Day3 {
    public String getExampleOutput() {
        Helper helper = new Helper();
        return helper.getExampleOutput("day3Input");
        /*return """
vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw
""";*/

    }

    public char getIdenticalCharacter(String part1, String part2) {
        for (int i = 0; i < part1.length(); i++) {
            if (-1 != part2.indexOf(part1.charAt(i))) {
                return part1.charAt(i);
            }
        }
        return '$';
    }

    public char getIdenticalCharacter(String part1, String part2, String part3) {
        for (int i = 0; i < part1.length(); i++) {
            if (-1 != part2.indexOf(part1.charAt(i)) && -1 != part3.indexOf(part1.charAt(i)) ) {
                return part1.charAt(i);
            }
        }
        return '$';
    }

    public int mapping(char input) {
        char c = 'a';
        int a = c-1;
        int val = 0;
        if (Character.isUpperCase(input)) {
            val += 26;
        }
        val += Character.toLowerCase(input) - a;

        return val;
    }

    // On coupe les lignes en 2
    public int executePart1() {
        String input = getExampleOutput();
        String[] lines = input.split("\\n");
        int total = 0;
        for (String line : lines) {
            String part1 = line.substring(0, line.length()/2);
            String part2 = line.substring(line.length()/2);
            char identical = getIdenticalCharacter(part1, part2);
            total += mapping(identical);
        }
        return total;
    }


    // on vérifie sur 3 lignes
    public int executePart2() {
        String input = getExampleOutput();
        String[] lines = input.split("\\n");
        int total = 0;
        for (int i = 0; i < lines.length; i+=3) {
            String line1 = lines[i];
            String line2 = lines[i+1];
            String line3 = lines[i+2];
            char identical = getIdenticalCharacter(line1, line2, line3);
            total += mapping(identical);
        }
        return total;
    }
}
