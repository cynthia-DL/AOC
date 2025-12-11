package y2025;

import all.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Day11 {
    record Node(String name, List<Node> output){
        public static Node fromInput(List<String> input){
            var allNodes = new HashMap<String, Node>();

            for (var line : input){
                var a1 = line.split(":");
                allNodes.put(a1[0], new Node(a1[0], new ArrayList<>()));
            }

            for (var line: input){
                var a1 = line.split(":");
                var a2 = a1[1].trim().split(" ");
                for (var leaf : a2){
                    var toAdd = allNodes.get(leaf);
                    allNodes.get(a1[0]).output().add(toAdd);
                }
            }

            return allNodes.get("you");
        }

        public long parkour() {
            List<Node> visited = new ArrayList<>();
            return this.parkour(visited);
        }

        private long parkour(List<Node> visited) {
            if ("out".equals(name) || visited.contains(this)) {
                return 0L;
            }

            long sum = 0;
            visited.add(this);

            for (var node : output){
                sum += node.parkour(List.copyOf(visited));
            }

            return sum + 1;
        }

    }

    public static void main(String[] args) {
        //var input = Utils.listFromDemoFile();
        var input = Utils.listForDay(2025, 9);
    }
}