package y2022;

import all.Utils;

import java.util.*;

class Monkey {
    int name;
    ArrayList<Integer> items = new ArrayList<>();
    int test;
    int trueTo;
    int falseTo;
    char operator;
    String other;

    int inspected;

    static Monkey fromList(List<String> origin){
        var monkey = new Monkey();

        //treat first line
        var line1 = origin.get(0).split(" ");
        var nameAsString = line1[1].subSequence(0, line1[1].length()-1).toString();
        monkey.name = Integer.parseInt(nameAsString);

        //treat second line
        var line2 = origin.get(1).split(":");
        var itemsAsStings = line2[1].trim().split(",");
        for (var item : itemsAsStings){
            monkey.items.add(Integer.parseInt(item.trim()));
        }

        //treat 3rd line
        var line3 = origin.get(2).split("=");
        monkey.operator = '+';
        if(line3[1].contains("*")) {
            monkey.operator = '*';
        }
        monkey.other = line3[1].split(" ")[3].trim();

        var line4 = origin.get(3).split("by ");
        monkey.test = Integer.parseInt(line4[1]);
        var line5 = origin.get(4).split("monkey ");
        monkey.trueTo = Integer.parseInt(line5[1]);
        var line6 = origin.get(5).split("monkey ");
        monkey.falseTo = Integer.parseInt(line6[1]);

        return monkey;
    }

    void round(HashMap<Integer, Monkey> monkeys){
        while (!items.isEmpty()){
            var item = items.remove(0);
            inspected++;
            System.out.println("\tMonkey inspects an item with a worry level of "+item+".");

            if(operator == '+'){
                if (other.equals("old")) {
                    item += item;
                    System.out.println("\t\tWorry level increases by self to "+item);
                } else {
                    item += Integer.parseInt(other);
                    System.out.println("\t\tWorry level increases by "+other+" to "+item);
                }

            } else {
                if (other.equals("old")) {
                    item *= item;
                    System.out.println("\t\tWorry level is multiplied by  self to "+item);
                } else {
                    item *= Integer.parseInt(other);
                    System.out.println("\t\tWorry level is multiplied by "+other+" to "+item);
                }
            }

            item /= 3;
            System.out.println("\t\tMonkey gets bored with item. Worry level is divided by 3 to "+item+".");

            if (item % test == 0){
                System.out.println("\t\tCurrent worry level is divisible by "+test+".");
                monkeys.get(trueTo).items.add(item);
                System.out.println("\t\tItem with worry level "+item+" is thrown to monkey "+trueTo);
            } else {
                System.out.println("\t\tCurrent worry level is not divisible by "+test+".");
                monkeys.get(falseTo).items.add(item);
                System.out.println("\t\tItem with worry level "+item+" is thrown to monkey "+falseTo);
            }
            System.out.println("\n");
        }
    }

    @Override
    public String toString() {
        return "Monkey{" +
                "name=" + name +
                ", items=" + items +
                ", test=" + test +
                ", trueTo=" + trueTo +
                ", falseTo=" + falseTo +
                ", operator=" + operator +
                ", other='" + other + '\'' +
                '}';
    }
}

public class Day11 {
    public static void main(String[] args) {
        var lignes = Utils.listFromFile("./inputs/2022/day11_input.txt");
        var monkeys = new HashMap<Integer, Monkey>();
        for (int i = 0; i < lignes.size(); i+= 7){
            var array = new ArrayList<String>();
            for (int j = 0; j < 6; j++){
                array.add(lignes.get(i+j));
            }

            var monkey = Monkey.fromList(array);

            monkeys.put(monkey.name, monkey);
        }

        for (int i = 0; i < 20; i++){
            for (var monkey : monkeys.entrySet()){
                monkey.getValue().round(monkeys);
            }
        }

        var asSortedArray = monkeys.values()
                .stream()
                .mapToInt(value -> value.inspected)
                .sorted()
                .toArray();

        System.out.println();
        System.out.println(asSortedArray[asSortedArray.length-1]*asSortedArray[asSortedArray.length-2]);
    }

}
