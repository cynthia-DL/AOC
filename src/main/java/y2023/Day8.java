package y2023;

import all.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

record Node(String name, String left, String right) {
    static Node fromLine(String line){
        var array = line
                .replace("(", "")
                .replace(")", "")
                .replace(",", "")
                .replace(" = ", " ")
                .split(" ");

        return new Node(array[0], array[1], array[2]);
    }

}

record Tree(Node head, HashMap<String, Node> tree, String instructions){
    static Tree fromLines(List<String> lines){
        String instructions;
        Node head = null;
        HashMap<String, Node> tree = new HashMap<>();

        var list = new java.util.ArrayList<>(List.copyOf(lines));
        instructions = list.remove(0);
        list.remove(0);

        for (var line : list){
            var node = Node.fromLine(line);

            if (node.name().equals("AAA"))
                head = node;

            tree.put(node.name(), node);
        }



        return new Tree(head, tree, instructions);
    }

    int stepToTravel(){
        int steps = 0;
        Node currentNode = head;

        for (; !currentNode.name().equals("ZZZ"); steps++){
            if (instructions.charAt(steps%instructions.length()) == 'L'){
                currentNode = tree.get(currentNode.left());
            } else {
                currentNode = tree.get(currentNode.right());
            }
        }

        return steps;
    }

}

public class Day8 {
    public static void main(String[] args) {
        //var lines = Utils.listFromFile("inputs/demo.txt");
        var lines = Utils.listFromFile("inputs/y2023/day8_input.txt");

        var result_p1 = Tree.fromLines(lines).stepToTravel();

        System.out.println("result_p1 = " + result_p1);
    }
}
