package y2023;

import all.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

record Node(String name, String left, String right) {
    static Node fromLine(String line) {
        var array = line
                .replace("(", "")
                .replace(")", "")
                .replace(",", "")
                .replace(" = ", " ")
                .split(" ");

        return new Node(array[0], array[1], array[2]);
    }

}

record Tree(List<Node> head, HashMap<String, Node> tree, String instructions) {
    static Tree fromLinesP1(List<String> lines) {
        String instructions;
        List<Node> head = new ArrayList<>();
        HashMap<String, Node> tree = new HashMap<>();

        var list = new java.util.ArrayList<>(List.copyOf(lines));
        instructions = list.remove(0);
        list.remove(0);

        for (var line : list) {
            var node = Node.fromLine(line);

            if (node.name().equals("AAA"))
                head.add(node);

            tree.put(node.name(), node);
        }


        return new Tree(head, tree, instructions);
    }

    static Tree fromLinesP2(List<String> lines) {
        String instructions;
        List<Node> head = new ArrayList<>();
        HashMap<String, Node> tree = new HashMap<>();

        var list = new java.util.ArrayList<>(List.copyOf(lines));
        instructions = list.remove(0);
        list.remove(0);

        for (var line : list) {
            var node = Node.fromLine(line);

            if (node.name().endsWith("A"))
                head.add(node);

            tree.put(node.name(), node);
        }

        return new Tree(head, tree, instructions);
    }

    long stepToTravelP1() {
        long steps = 0;
        Node currentNode = head.get(0);

        for (; !currentNode.name().equals("ZZZ"); steps++) {
            if (instructions.charAt((int) (steps % instructions.length())) == 'L') {
                currentNode = tree.get(currentNode.left());
            } else {
                currentNode = tree.get(currentNode.right());
            }
        }

        return steps;
    }

    private boolean isFull(long [] array){
        for (var item : array){
            if (item == -1) return false;
        } return true;
    }

    long stepToTravelP2() {
        long steps = 0;
        List<Node> currentNodes = List.copyOf(head);
        var directions = instructions.toCharArray();
        var loopAt = new long[head.size()];
        Arrays.fill(loopAt, -1);

        //get smallest loop for each
        for (; !isFull(loopAt); steps++) {
            var tmpCurrentNodes = List.copyOf(currentNodes);
            currentNodes = new ArrayList<>(tmpCurrentNodes.size());
            for (int i = 0; i < head.size(); i++) {
                var node = tmpCurrentNodes.get(i);
                if(loopAt[i] == -1 && node.name().endsWith("Z")){
                    loopAt[i] = steps;
                }

                if (directions[(int) (steps % instructions.length())] == 'L') {
                    currentNodes.add(tree.get(node.left()));
                } else {
                    currentNodes.add(tree.get(node.right()));
                }
            }
        }
        System.out.println("Arrays.toString(loopAt) = " + Arrays.toString(loopAt));

        // find commun multiplicator
        long biggest = Arrays.stream(loopAt).reduce(-1, Math::max);
        long current = biggest;
        boolean isValid = false;
        for (; !isValid; current+= biggest){
            isValid = true;

            for (var item : loopAt){
                if (current % item != 0) {
                    isValid = false;
                    break;
                }
            }
        }

        return current-biggest;
    }
}

public class Day8 {
    public static void main(String[] args) {
        //var lines = Utils.listFromFile("inputs/demo.txt");
        var lines = Utils.listFromFile("inputs/y2023/day8_input.txt");

        var result_p1 = Tree.fromLinesP1(lines).stepToTravelP1();
        var result_p2 = Tree.fromLinesP2(lines).stepToTravelP2();

        System.out.println("result_p1 = " + result_p1);
        System.out.println("result_p2 = " + result_p2);
    }
}