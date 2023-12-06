package y2015;

import all.Utils;

record Present(int length, int width, int height){
    static Present fromLine(String line){
        var array = line.split("x");

        return new Present(Integer.parseInt(array[0]),Integer.parseInt(array[1]), Integer.parseInt(array[2]));
    }

    int feetOfWrappingPaper(){
        var s1 = length*width;
        var s2 = width*height;
        var s3 = height*length;
        var smallestSide = Math.min(Math.min(s1, s2), s3);

        return 2*s1 + 2*s2 + 2*s3 + smallestSide;
    }

    int feetOfRibbon(){
        var p1 = 2*length+2*width;
        var p2 = 2*width+2*height;
        var p3 = 2*height+2*length;
        
        return Math.min(Math.min(p1, p2), p3) + length * width * height;
    }
}
public class Day2 {
    public static void main(String[] args) {
        //var lines = Utils.listFromFile("inputs/demo.txt");
        var lines = Utils.listFromFile("inputs/y2015/day2_input.txt");
        var result_p1 = lines.stream()
                .map(Present::fromLine)
                .mapToInt(Present::feetOfWrappingPaper)
                .sum();
        
        var result_p2 = lines.stream()
                .map(Present::fromLine)
                .mapToInt(Present::feetOfRibbon)
                .sum();

        System.out.println("result_p1 = " + result_p1);
        System.out.println("result_p2 = " + result_p2);
    }
}
