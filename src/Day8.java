import java.util.ArrayList;
import java.util.List;

public class Day8 {
    public String getExampleOutput(boolean fullOutput) {
        Helper helper = new Helper();

        return fullOutput ? helper.getExampleOutput("day8Input") : """
 30373
 25512
 65332
 33549
 35390
 """;
    }

    public Tree[][] initMap(String[] input) {
        Tree[][] map = new Tree[input.length][input[0].length()];
        for (int i = 0; i < input.length; i++) {
            String line = input[i];
            for (int j = 0; j < line.length(); j++) {
                Tree t = new Tree();
                t.x = j;
                t.y = i;
                t.value = Integer.parseInt(String.valueOf(line.charAt(j)));
                map[i][j] = t;
            }
        }
        return map;
    }
    public void executePart1()
    {
        String[] input = getExampleOutput(true).split("\\n");
        Tree[][] map = initMap(input);
        System.out.println(getVisible(map));
    }

    public int getVisible(Tree[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                Tree t = map[i][j];
                if (isVIsibleFromLeft(t, map) ||isVIsibleFromUp(t, map) || isVIsibleFromDown(t, map) ||isVIsibleFromRight(t, map)) {
                    t.visible = true;
                }
            }
        }

        int total = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].visible) {
                    total++;
                }
            }
        }
        return total;
    }
    public boolean isVIsibleFromLeft(Tree tree, Tree[][] map) {
        for (int i = 0; i < tree.x; i++) {
            if (map[tree.y][i].value >= tree.value) {
                return false;
            }
        }
        return true;
    }

    public boolean isVIsibleFromRight(Tree tree, Tree[][] map) {
        for (int i = map[tree.y].length-1; i > tree.x; i--) {
            if (map[tree.y][i].value >= tree.value) {
                return false;
            }
        }
        return true;
    }

    public boolean isVIsibleFromUp(Tree tree, Tree[][] map) {
        for (int i = 0; i < tree.y; i++) {
            if (map[i][tree.x].value >= tree.value) {
                return false;
            }
        }
        return true;
    }

    public boolean isVIsibleFromDown(Tree tree, Tree[][] map) {
        for (int i = map.length -1; i > tree.y; i--) {
            if (map[i][tree.x].value >= tree.value) {
                return false;
            }
        }
        return true;
    }


    public void executePart2()
    {
        String[] input = getExampleOutput(true).split("\\n");
        Tree[][] map = initMap(input);
        System.out.println(getMaxScenicScore(map));
    }

    public int getNbVIsibleFromLeft(Tree tree, Tree[][] map) {
        int nb = 0;
        for (int i = tree.x-1; i >= 0; i--) {
            nb++;
            if (map[tree.y][i].value >= tree.value) {
                return nb;
            }

        }
        return nb;
    }
    public int getNbVIsibleFromRight(Tree tree, Tree[][] map) {
        int nb = 0;
        for (int i = tree.x+1; i < map[tree.y].length; i++) {
            nb++;
            if (map[tree.y][i].value >= tree.value) {
                return nb;
            }

        }
        return nb;
    }
    public int getNbVIsibleFromDown(Tree tree, Tree[][] map) {
        int nb = 0;
        for (int i = tree.y+1; i < map.length; i++) {
            nb++;
            if (map[i][tree.x].value >= tree.value) {
                return nb;
            }

        }
        return nb;
    }
    public int getNbVIsibleFromUp(Tree tree, Tree[][] map) {
        int nb = 0;
        for (int i = tree.y-1; i >= 0; i--) {
            nb++;
            if (map[i][tree.x].value >= tree.value) {
                return nb;
            }

        }
        return nb;
    }

    public void initScenicScore(Tree[][] map, Tree tree) {
        tree.scenicScore = getNbVIsibleFromUp(tree, map) *getNbVIsibleFromRight(tree, map) *getNbVIsibleFromLeft(tree, map) *getNbVIsibleFromDown(tree, map);
    }

    public int getMaxScenicScore(Tree[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                initScenicScore(map, map[i][j]);
            }
        }

        int max = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j].scenicScore > max) {
                    max = map[i][j].scenicScore;
                }
            }
        }
        return max;
    }

}
class Tree {
    public int x;
    public int y;
    public int value;
    public int scenicScore = 1;
    public boolean visible = false;
}