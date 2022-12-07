import java.util.ArrayList;
import java.util.Objects;


public class Day7 {
    public String getExampleOutput(boolean fullOutput) {
        Helper helper = new Helper();

        return fullOutput ? helper.getExampleOutput("day7Input") : """
                $ cd /
                $ ls
                dir a
                14848514 b.txt
                8504156 c.dat
                dir d
                $ cd a
                $ ls
                dir e
                29116 f
                2557 g
                62596 h.lst
                $ cd e
                $ ls
                584 i
                $ cd ..
                $ cd ..
                $ cd d
                $ ls
                4060174 j
                8033020 d.log
                5626152 d.ext
                7214296 k
                """;
    }

    public void addToDirectory(Directory arbo, ArrayList<String> path, String content)
    {
        Directory localArbo = arbo;
        for (int i = 0; i < path.size(); i++) {
            int finalI = i;
            localArbo = localArbo.directories.stream()
                    .filter(d -> d.name.equals(path.get(finalI)))
                    .findFirst().orElse(null);
        }

        if (content.startsWith("dir")) {
            Directory dir = new Directory();
            dir.name = content.substring(4);
            dir.parent = localArbo;
            localArbo.directories.add(dir);
        } else {
            String[] info = content.split(" ");
            File f = new File();
            f.name = info[1];
            f.size = Long.parseLong(info[0]);
            localArbo.files.add(f);
            localArbo.addSize(f.size);
        }
    }

    public void changeDirectory(ArrayList<String> path, String content) {
        if (content.equals("$ cd ..")) {
            path.remove(path.size()-1);
        } else {
            path.add(content.split(" ")[2]);
        }
    }

    public Long getTotalInferiorTo(Directory baseRepo, Long maxValue) {
        Long total = 0L;
        for (int i = 0; i < baseRepo.directories.size(); i++) {
            if (baseRepo.directories.get(i).size < maxValue) {
                total += baseRepo.directories.get(i).size;
            }
            total += getTotalInferiorTo(baseRepo.directories.get(i), maxValue);
        }

        return total;
    }

    public Directory getTotalSuperiorTo(Directory directory, Long minValue) {
        Directory minDirectory = directory;
        for (int i = 0; i < directory.directories.size(); i++) {
            if (directory.directories.get(i).size > minValue && directory.directories.get(i).size < minDirectory.size) {
                minDirectory = directory.directories.get(i);
            }
            Directory other = getTotalSuperiorTo(directory.directories.get(i), minValue);
            if (other.size > minValue && other.size < minDirectory.size) {
                minDirectory = other;
            }
        }

        return minDirectory;
    }

    public void initFolders(String[] input, Directory baseRepo, ArrayList<String> path) {
        for (int i = 1; i < input.length; i++) {
            if (input[i].equals("$ ls")) {
                i++;
                while (i < input.length && !input[i].startsWith("$") ) {
                    addToDirectory(baseRepo, path, input[i]);
                    i++;
                }
                i--;
            } else {
                changeDirectory(path, input[i]);
            }
        }
    }
    public void executePart2()
    {
        String[] input = getExampleOutput(true).split("\\n");
        Directory baseRepo = new Directory();
        ArrayList<String> path = new ArrayList<>();
        initFolders(input, baseRepo, path);
        System.out.println(getTotalSuperiorTo(baseRepo, baseRepo.size - 40000000L).size);
        System.out.println("end");
    }

    public void executePart1()
    {
        String[] input = getExampleOutput(true).split("\\n");
        Directory baseRepo = new Directory();
        ArrayList<String> path = new ArrayList<>();
        initFolders(input, baseRepo, path);

        System.out.println(getTotalInferiorTo(baseRepo, 100000L));
        System.out.println("end");
    }
}

class File {
    public String name;
    public Long size = 0L;
}

class Directory extends File {
    public Directory parent = null;
    public ArrayList<File> files = new ArrayList<>();
    public ArrayList<Directory> directories = new ArrayList<>();

    public void addSize(Long size) {
        this.size += size;
        if (Objects.nonNull(this.parent)) {
            parent.addSize(size);
        }
    }
}