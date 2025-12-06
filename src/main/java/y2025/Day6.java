package y2025;

import all.Utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class Day6 {
    static class MathProblem {
        final List<BigInteger> values = new ArrayList<>();
        Operator operator;

        public BigInteger process(){
            BigInteger result = operator == Operator.ADD ? BigInteger.ZERO : BigInteger.ONE;

            for (var component : values){
                result = operator.apply(result, component);
            }

            return result;
        }

        public static List<MathProblem> getHomework(List<String> input){
            List<MathProblem> homework = new ArrayList<>();

            for (var line :input){
                var sections = line.strip().split("\\s+");
                System.out.println(Arrays.deepToString(sections));

                for (int i = 0; i < sections.length; i++){
                    if(homework.size() <= i){
                        homework.add(new MathProblem());
                    }

                    var problem = homework.get(i);

                    if(!Utils.isParsable(sections[i])){
                        problem.operator = Operator.fromSign(sections[i]);
                        System.out.println(problem);
                    } else {
                        problem.values.add(new BigInteger(sections[i]));
                    }
                }

            }

            return homework;
        }

        public static List<MathProblem> getHomeworkButRightThisTime(List<String> input){
            List<MathProblem> result = new ArrayList<>();

            final int MAX_LINE = input.size();
            final int MAX_COLUMN = input.stream().mapToInt(String::length).max().getAsInt();
            System.out.println("MAX_COLUMN = " + MAX_COLUMN);
            System.out.println("MAX_LINE = " + MAX_LINE);
            
            boolean newProblem = true;
            int homeworkInList = 0;
            List<BigInteger> values = new ArrayList<>();
            String sign = "";

            for (int i = 0; i <= MAX_COLUMN; i++ ){
                StringBuilder construct = new StringBuilder();
                for (int j = 0; j < MAX_LINE; j++){
                    char tmp;
                    try {
                        tmp = input.get(j).charAt(i);
                    } catch (Exception e){
                        tmp = ' ';
                    }
                    if (tmp == ' ') continue;

                    newProblem = false;
                    if (tmp == '+' || tmp == '*'){
                        sign = String.valueOf(Character.valueOf(tmp));
                        continue;
                    }

                    construct.append(tmp);

                }

                if(!construct.toString().isEmpty()){
                    values.add(new BigInteger(construct.toString()));
                }

                if (newProblem){
                    var math = new MathProblem();
                    math.values.addAll(values);
                    math.operator = Operator.fromSign(sign);
                    result.add(math);
                    System.out.println(math);
                    values = new ArrayList<>();
                    sign = "";
                    homeworkInList++;
                }

                newProblem = true;
            }

            return result;


        }

        @Override
        public String toString() {
            return "" +operator + values + " = " + process();
        }

        enum Operator {
            ADD('+', BigInteger::add),
            MULTIPLY('*', BigInteger::multiply);

            private final char sign;
            private final BiFunction<BigInteger, BigInteger, BigInteger> applyOperator;

            Operator(char sign, BiFunction<BigInteger, BigInteger, BigInteger> biFunction){
                this.sign = sign;
                this.applyOperator = biFunction;
            }

            public static Operator fromSign(String sign){
                return Arrays.stream(Operator.values())
                        .filter(o -> o.sign == sign.charAt(0))
                        .findFirst()
                        .get();
            }

            public BigInteger apply(BigInteger a, BigInteger b){
                return applyOperator.apply(a, b);
            }
        }

    }

    public static void main(String[] args) {
        //var input = Utils.listFromDemoFile();
        var input = Utils.listForDay(2025, 6);

        var result1 =
                MathProblem
                .getHomework(input)
                .stream()
                .map(MathProblem::process)
                .reduce(BigInteger.ZERO, BigInteger::add);

        var result2 =
                MathProblem
                .getHomeworkButRightThisTime(input)
                .stream()
                .map(MathProblem::process)
                .reduce(BigInteger.ZERO, BigInteger::add);

        System.out.println(result1);
        System.out.println(result2);
    }
}
