import java.util.ArrayList;
import static java.lang.Math.abs;
public class Day9 {
    public String getExampleOutput(boolean fullOutput) {
        Helper helper = new Helper();

        return fullOutput ? helper.getExampleOutput("day9Input") : """
R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20
 """;
    }

    public void moveNextRopeElement(Integer ropeElement, Position[] rope, ArrayList<Position> map) {

        int diffX = rope[ropeElement+1].x - rope[ropeElement].x; // 1 - 3 si on va à droite = -2
        int diffY = rope[ropeElement+1].y - rope[ropeElement].y;

        Position p2 = null;
        if (diffY == 0 && abs(diffX) == 2) { //same line
            Position p = new Position();
            p.x = rope[ropeElement+1].x + (diffX > 0 ? -1 : 1);
            p.y = rope[ropeElement+1].y;
            p2 = map.stream().filter(po -> (po.x == p.x) && (po.y == p.y)).findFirst().orElse(p);
        } else if (diffX == 0 && abs(diffY) == 2) { //same column
            Position p = new Position();
            p.x = rope[ropeElement+1].x;
            p.y = rope[ropeElement+1].y + (diffY > 0 ? -1 : 1) ;
            p2 = map.stream().filter(po -> (po.x == p.x) && (po.y == p.y)).findFirst().orElse(p);
        } else if (abs(diffY) == 2 || abs(diffX) == 2) {
            Position p = new Position();
            p.x = rope[ropeElement+1].x + (diffX > 0 ? -1 : 1);
            p.y = rope[ropeElement+1].y + (diffY > 0 ? -1 : 1);
            p2 = map.stream().filter(po -> (po.x == p.x) && (po.y == p.y)).findFirst().orElse(p);
        }
        if (null != p2) {
            Position p3 = p2;
            if (null == map.stream().filter(po -> (po.x == p3.x) && (po.y == p3.y)).findFirst().orElse(null)) {
                map.add(p2);
            }
            rope[ropeElement+1] = p2;
        }

        if (ropeElement + 2 == rope.length) {
            rope[ropeElement+1].visitedByTail = true;
        } else {
            moveNextRopeElement(ropeElement+1, rope, map);
        }
    }

    public void move(Position[] rope, ArrayList<Position> map, String action) {
        String[] move = action.split(" ");
        String direction = move[0];
        int steps = Integer.parseInt(move[1]);

        if (direction.equals("R")) {
            for (int i = 0; i < steps; i++) {
                rope[0].setRight(map);
                rope[0] = rope[0].right;
                moveNextRopeElement(0, rope, map);
            }
        } else if (direction.equals("L")) {
            for (int i = 0; i < steps; i++) {
                rope[0].setLeft(map);
                rope[0] = rope[0].left;
                moveNextRopeElement(0, rope, map);
            }
        } else if (direction.equals("U")) {
            for (int i = 0; i < steps; i++) {
                rope[0].setUp(map);
                rope[0] = rope[0].up;
                moveNextRopeElement(0, rope, map);
            }
        } else if (direction.equals("D")) {
            for (int i = 0; i < steps; i++) {
                rope[0].setDown(map);
                rope[0] = rope[0].down;
                moveNextRopeElement(0, rope, map);
            }
        }
    }

    public void executePart1()
    {
        String[] input = getExampleOutput(true).split("\\n");
        ArrayList<Position> map = new ArrayList<>();
        Position current = new Position();
        current.x = 0;
        current.y = 0;
        current.visitedByTail = true;
        map.add(current);
        Position[] rope = new Position[] {
                current, current
        };

        for (String s : input) {
            move(rope, map, s);
        }

        int total = 0;
        for (Position position : map) {
            if (position.visitedByTail) {
                total++;
            }
        }
        System.out.println(total);
    }
    public void executePart2() // does not complete the task
    {
        String[] input = getExampleOutput(true).split("\\n");
        ArrayList<Position> map = new ArrayList<>();
        Position current = new Position();
        current.x = 0;
        current.y = 0;
        current.visitedByTail = true;
        map.add(current);
        Position[] rope = new Position[] {
                current, current, current, current, current, current, current, current, current, current
        };

        for (String s : input) {
            move(rope, map, s);
        }

        int total = 0;
        for (Position position : map) {
            if (position.visitedByTail) {
                total++;
            }
        }
        System.out.println(total);
    }
}

class Position {
    public Position up = null;
    public Position down = null;
    public Position right = null;
    public Position left = null;
    public int x;
    public int y;
    public boolean visitedByTail = false;

    public void setUp(Position up) {
        if (null == this.up) {
            this.up = up;
            this.up.setDown(this);
        }
    }

    public void setDown(Position down) {
        if (null == this.down) {
            this.down = down;
            this.down.setUp(this);
        }
    }
    public void setDown(ArrayList<Position> map) {
        if (null == this.down) {
            Position p = new Position();
            p.x = this.x;
            p.y = this.y - 1;
            Position p2 = map.stream().filter(po -> (po.x == p.x) && (po.y == p.y)).findFirst().orElse(p);
            this.down = p2;
            if (p2 == p) {
                map.add(p2);
            }
            this.down.setUp(this);
        }
    }
    public void setUp(ArrayList<Position> map) {
        if (null == this.up) {
            Position p = new Position();
            p.x = this.x;
            p.y = this.y + 1;
            Position p2 = map.stream().filter(po -> (po.x == p.x) && (po.y == p.y)).findFirst().orElse(p);
            this.up = p2;
            if (p2 == p) {
                map.add(p2);
            }
            this.up.setDown(this);
        }
    }

    public void setRight(Position right) {
        if (null == this.right) {
            this.right = right;
            this.right.setLeft(this);
        }
    }

    public void setRight(ArrayList<Position> map) {
        if (null == this.right) {
            Position p = new Position();
            p.x = this.x+1;
            p.y = this.y;
            Position p2 = map.stream().filter(po -> (po.x == p.x) && (po.y == p.y)).findFirst().orElse(p);
            this.right = p2;
            if (p2 == p) {
                map.add(p2);
            }
            this.right.setLeft(this);
        }
    }

    public void setLeft(Position left) {
        if (null == this.left) {
            this.left = left;
            this.left.setRight(this);
        }
    }

    public void setLeft(ArrayList<Position> map) {
        if (null == this.left) {
            Position p = new Position();
            p.x = this.x-1;
            p.y = this.y;
            Position p2 = map.stream().filter(po -> (po.x == p.x) && (po.y == p.y)).findFirst().orElse(p);
            this.left = p2;
            if (p2 == p) {
                map.add(p2);
            }
            this.left.setRight(this);
        }
    }
}
