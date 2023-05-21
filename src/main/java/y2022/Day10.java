package y2022;

import all.Utils;

import java.util.ArrayList;

record Instruction(int when, int addToX) implements Comparable<Instruction>{

    @Override
    public int compareTo(Instruction o) {
        return Integer.compare(when, o.when);
    }
}
class CRT{
    int position;
    String line;
    public CRT(){
        position = 0;
        line = "";
    }

    private boolean canDraw(int x){
        int X = x%40;
        int POSITION = position%40;
        return X == POSITION - 1 || X == POSITION || X == POSITION + 1;
    }

    public void step(int x){
        if(canDraw(x)){
            line+="#";
        } else {
            line+="`";
        }

        if (position%40 == 39){
            line+="\n";
        }
        position++;
    }

    public String toString(){
        return line;
    }
}
class CPU{
    int cycle;
    int x;
    ArrayList<Instruction> instructions;

    CRT crt;

    private CPU(ArrayList<Instruction> instructions){
        this.instructions = instructions;
        cycle = 0;
        x = 1;
        crt = new CRT();

    }

    public static CPU fromList(ArrayList<String> list){
        var instructions = new ArrayList<Instruction>();
        int step = 0;
        for (String str : list) {
            if (str.contains("addx")) {
                var arr = str.split(" ");
                step += 2;
                instructions.add(new Instruction(step, Integer.parseInt(arr[1])));
            } else {
                step++;
                instructions.add(new Instruction(step, 0));
            }
        }

        instructions.sort(Instruction::compareTo);
        return new CPU(instructions);
    }


    public boolean hasNext() {
        return instructions.size() != 0;
    }

    public ArrayList<Instruction> next() {
        ArrayList<Instruction> toReturn = new ArrayList<>();

        while (this.instructions.get(0).when() == cycle){
            toReturn.add(this.instructions.remove(0));
            if (!hasNext())
                return toReturn;
        }
        cycle++;
        return toReturn;
    }

    public void step(){
        var list = next();
        for (var instruction : list) {
            x += instruction.addToX();
        }
        crt.step(x);
    }
}

public class Day10 {
    public static void main(String[] args) {
        var list = Utils.listFromFile("./inputs/2022/day10_input.txt");
        var cpu = CPU.fromList(list);

        System.out.println(cpu.instructions);

        while (cpu.hasNext()){
            cpu.step();
        }

        System.out.println(cpu.crt);
    }
}
