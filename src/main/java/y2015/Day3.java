package y2015;

import all.Utils;

import java.awt.*;
import java.util.ArrayList;


public class Day3 {
    private static void computeIfAbsent(ArrayList<Point> list, Point point) {
        if (!list.contains(point)) {
            list.add(point);
        }
    }

    public static void main(String[] args) {
        //var input = Utils.listFromDemoFile().get(0);
        var input = Utils.listForDay(2015, 3).get(0);

        var coordinateSolo = new ArrayList<Point>();
        var coordinateTeam = new ArrayList<Point>();
        int xSolo = 0;
        int ySolo = 0;
        int xSanta = 0;
        int ySanta = 0;
        int xRobot = 0;
        int yRobot = 0;

        int counter = 0;

        coordinateSolo.add(new Point(xSolo, ySolo));
        coordinateTeam.add(new Point(xSanta, ySanta));

        for (var direction : input.toCharArray()) {
            switch (direction) {
                case '>':
                    xSolo++;
                    if (counter % 2 == 0) {
                        xSanta++;
                    } else xRobot++;
                    break;
                case '<':
                    xSolo--;
                    if (counter % 2 == 0) {
                        xSanta--;
                    } else xRobot--;
                    break;
                case '^':
                    ySolo++;
                    if (counter % 2 == 0) {
                        ySanta++;
                    } else yRobot++;
                    break;
                case 'v':
                    ySolo--;
                    if (counter % 2 == 0) {
                        ySanta--;
                    } else yRobot--;
                    break;
                default:
                    throw new IllegalArgumentException("Cannot parse " + direction);
            }

            var point = new Point(xSolo, ySolo);
            computeIfAbsent(coordinateSolo, point);

            if (counter % 2 == 0) {
                point = new Point(xSanta, ySanta);
            } else {
                point = new Point(xRobot, yRobot);
            }

            computeIfAbsent(coordinateTeam, point);
            counter++;
        }

        System.out.println("part1 = " + coordinateSolo.size());
        System.out.println("part2 = " + (coordinateTeam.size()));
    }
}
