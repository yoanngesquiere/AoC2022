public class Day4 {
    public String getExampleOutput(boolean fullOutput) {
        Helper helper = new Helper();

        return fullOutput ? helper.getExampleOutput("day4Input") : """
                    2-4,6-8
                    2-3,4-5
                    5-7,7-9
                    2-8,3-7
                    6-6,4-6
                    2-6,4-8
                """;
    }

    public boolean isOverlap(String part1, String part2, boolean fullOverlap)
    {
        String[] partsPart1 = part1.split("-");
        String[] partsPart2 = part2.split("-");

        int start1 = Integer.parseInt(partsPart1[0]);
        int end1 = Integer.parseInt(partsPart1[1]);
        int start2 = Integer.parseInt(partsPart2[0]);
        int end2 = Integer.parseInt(partsPart2[1]);

        return fullOverlap ?
                (start1 <= start2 && end1 >= end2) || (start1 >= start2 && end1 <= end2) :
                (start1 <= start2 && end1 >= end2) ||
                        (start1 >= start2 && end1 <= end2) ||
                        (start1 <= end2 && start2 <= start1)  ||
                        (start2 <= end1 && start1 <= start2)
                ;
    }

    public int executePart1()
    {
        String input = getExampleOutput(true);
        String[] lines = input.split("\\n");
        int total = 0;
        for (String line : lines) {
            String[] parts = line.split(",");
            if (isOverlap(parts[0].trim(), parts[1].trim(), true)) {
                total++;
            }
        }

        return total;
    }

    public int executePart2()
    {
        String input = getExampleOutput(true);
        String[] lines = input.split("\\n");
        int total = 0;
        for (String line : lines) {
            String[] parts = line.split(",");
            if (isOverlap(parts[0].trim(), parts[1].trim(), false)) {
                total++;
            }
        }

        return total;
    }
}
