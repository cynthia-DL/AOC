package y2023;

import all.Utils;

import java.util.*;

enum Card{
    ACE( 'A'), KING('K'),
    QUEEN('Q'), JACK('J'),
    TEN('T'), NINE('9'),
    EIGHT('8'), SEVEN('7'),
    SIX('6'), FIVE('5'),
    FOUR('4'), THREE('3'),
    TWO ('2');


    private final char symbol;

    Card(char symbol){
        this.symbol = symbol;
    }

    static Card fromSymbol(char symbol){
        var opt = Arrays.stream(Card.values()).filter(card -> card.symbol == symbol).findFirst();

        return opt.orElse(null);
    }
}

enum HandType {
    FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, THREE_OF_A_KIND, TWO_PAIRS, ONE_PAIR, HIGH_CARD
}

record Player(List<Card> hand, long bid) implements Comparable<Player>{
    static Player fromLine(String line){
        var array = line.split(" ");
        ArrayList<Card> hand = new ArrayList<>(5);

        for (int i = 0; i < 5; i++){
            hand.add(Card.fromSymbol(array[0].charAt(i)));
        }

        //hand.sort(Comparator.naturalOrder());

        return new Player(hand, Long.parseLong(array[1]));
    }

    @Override
    public int compareTo(Player o) {
        var myHandType = this.handType();
        var theirHandType = o.handType();
        if (myHandType.equals(theirHandType)){
            for (int i = 0; i<hand.size(); i++){
                if(!this.hand.get(i).equals(o.hand.get(i))) return hand.get(i).compareTo(o.hand.get(i));
            }
            return 0;
        } else return myHandType.compareTo(theirHandType);
    }

    private record Frequency(Card card, int occurences){
        static List<Frequency> fromHand(List<Card> cards){
            ArrayList<Frequency> toReturn = new ArrayList<>();
        
            for (int i = 0; i < cards.size(); i++){
                boolean toBreak = false;
                for (var freq : toReturn){
                    if(freq.card.equals(cards.get(i))){
                        toBreak = true;
                        break;
                    }


                }
                if (!toBreak) {
                    toReturn.add(new Frequency(cards.get(i), Collections.frequency(cards, cards.get(i))));
                }
            }

            toReturn.sort(((o1, o2) -> o2.occurences - o1.occurences));
            
            return toReturn;
        }
    }

    HandType handType(){
        var freqs = Frequency.fromHand(hand);
        
        if (freqs.size() == 1){
            return HandType.FIVE_OF_A_KIND;
        } else if (freqs.size() == 2 && freqs.get(1).occurences == 1){
            return HandType.FOUR_OF_A_KIND;
        } else if (freqs.size() == 2 && freqs.get(0).occurences == 3 && freqs.get(1).occurences == 2) {
                return HandType.FULL_HOUSE;
        } else if (freqs.size() == 3 && freqs.get(0).occurences == 3){
            return HandType.THREE_OF_A_KIND;
        }else if (freqs.size() == 3 && freqs.get(0).occurences == 2 && freqs.get(1).occurences == 2){
            return HandType.TWO_PAIRS;
        } else if (freqs.size() == 4) {
            return HandType.ONE_PAIR;
        } else return HandType.HIGH_CARD;
    }
}

public class Day7 {
    public static void main(String[] args) {
        //var lines = Utils.listFromFile("inputs/demo.txt");
        var lines = Utils.listFromFile("inputs/y2023/day7_input.txt");

        var players = new ArrayList<>(lines.stream()
                .map(Player::fromLine)
                .peek(System.out::println)
                .toList());

        players.sort(Comparator.naturalOrder());

        long result_p1 = 0;

        for (int i = 0, rank = players.size(); i < players.size(); i++, rank--){
            result_p1 += rank*players.get(i).bid();
        }

        System.out.println("result_p1 = " + result_p1);
    }
}
