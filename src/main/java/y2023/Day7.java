package y2023;

import all.Utils;

import java.util.*;

enum CardP1 {
    ACE('A'), KING('K'),
    QUEEN('Q'), JACK('J'),
    TEN('T'), NINE('9'),
    EIGHT('8'), SEVEN('7'),
    SIX('6'), FIVE('5'),
    FOUR('4'), THREE('3'),
    TWO('2');


    private final char symbol;

    CardP1(char symbol) {
        this.symbol = symbol;
    }

    static CardP1 fromSymbol(char symbol) {
        var opt = Arrays.stream(CardP1.values()).filter(card -> card.symbol == symbol).findFirst();

        return opt.orElse(null);
    }
}

enum HandType {
    FIVE_OF_A_KIND, FOUR_OF_A_KIND, FULL_HOUSE, THREE_OF_A_KIND, TWO_PAIRS, ONE_PAIR, HIGH_CARD
}

record PlayerP1(List<CardP1> hand, long bid) implements Comparable<PlayerP1> {
    static PlayerP1 fromLine(String line) {
        var array = line.split(" ");
        ArrayList<CardP1> hand = new ArrayList<>(5);

        for (int i = 0; i < 5; i++) {
            hand.add(CardP1.fromSymbol(array[0].charAt(i)));
        }

        //hand.sort(Comparator.naturalOrder());

        return new PlayerP1(hand, Long.parseLong(array[1]));
    }

    @Override
    public int compareTo(PlayerP1 o) {
        var myHandType = this.handType();
        var theirHandType = o.handType();
        if (myHandType.equals(theirHandType)) {
            for (int i = 0; i < hand.size(); i++) {
                if (!this.hand.get(i).equals(o.hand.get(i))) return hand.get(i).compareTo(o.hand.get(i));
            }
            return 0;
        } else return myHandType.compareTo(theirHandType);
    }

    private record Frequency(CardP1 card, int occurrences) {
        static List<Frequency> fromHand(List<CardP1> cards) {
            ArrayList<Frequency> toReturn = new ArrayList<>();

            for (int i = 0; i < cards.size(); i++) {
                boolean toBreak = false;
                for (var freq : toReturn) {
                    if (freq.card.equals(cards.get(i))) {
                        toBreak = true;
                        break;
                    }


                }
                if (!toBreak) {
                    toReturn.add(new Frequency(cards.get(i), Collections.frequency(cards, cards.get(i))));
                }
            }

            toReturn.sort(((o1, o2) -> o2.occurrences - o1.occurrences));

            return toReturn;
        }
    }

    HandType handType() {
        var freqs = Frequency.fromHand(hand);

        if (freqs.size() == 1) {
            return HandType.FIVE_OF_A_KIND;
        } else if (freqs.size() == 2 && freqs.get(1).occurrences == 1) {
            return HandType.FOUR_OF_A_KIND;
        } else if (freqs.size() == 2 && freqs.get(0).occurrences == 3 && freqs.get(1).occurrences == 2) {
            return HandType.FULL_HOUSE;
        } else if (freqs.size() == 3 && freqs.get(0).occurrences == 3) {
            return HandType.THREE_OF_A_KIND;
        } else if (freqs.size() == 3 && freqs.get(0).occurrences == 2 && freqs.get(1).occurrences == 2) {
            return HandType.TWO_PAIRS;
        } else if (freqs.size() == 4) {
            return HandType.ONE_PAIR;
        } else return HandType.HIGH_CARD;
    }
}

enum CardP2 {
    ACE('A'), KING('K'),
    QUEEN('Q'),
    TEN('T'), NINE('9'),
    EIGHT('8'), SEVEN('7'),
    SIX('6'), FIVE('5'),
    FOUR('4'), THREE('3'),
    TWO('2'), JOKER('J');


    private final char symbol;

    CardP2(char symbol) {
        this.symbol = symbol;
    }

    static CardP2 fromSymbol(char symbol) {
        var opt = Arrays.stream(CardP2.values()).filter(card -> card.symbol == symbol).findFirst();

        return opt.orElse(null);
    }
}

record PlayerP2(List<CardP2> hand, long bid) implements Comparable<PlayerP2> {
    static PlayerP2 fromLine(String line) {
        var array = line.split(" ");
        ArrayList<CardP2> hand = new ArrayList<>(5);

        for (int i = 0; i < 5; i++) {
            hand.add(CardP2.fromSymbol(array[0].charAt(i)));
        }

        return new PlayerP2(hand, Long.parseLong(array[1]));
    }

    @Override
    public int compareTo(PlayerP2 o) {
        var myHandType = this.handType();
        var theirHandType = o.handType();
        if (myHandType.equals(theirHandType)) {
            for (int i = 0; i < hand.size(); i++) {
                if (!this.hand.get(i).equals(o.hand.get(i))) return hand.get(i).compareTo(o.hand.get(i));
            }
            return 0;
        } else return myHandType.compareTo(theirHandType);
    }

    private record Frequency(CardP2 card, int occurrences) {
        static List<Frequency> fromHand(List<CardP2> cards) {
            ArrayList<Frequency> toReturn = new ArrayList<>();

            for (int i = 0; i < cards.size(); i++) {
                boolean toBreak = false;
                for (var freq : toReturn) {
                    if (freq.card.equals(cards.get(i))) {
                        toBreak = true;
                        break;
                    }


                }
                if (!toBreak) {
                    toReturn.add(new Frequency(cards.get(i), Collections.frequency(cards, cards.get(i))));
                }
            }

            toReturn.sort(((o1, o2) -> o2.occurrences - o1.occurrences));

            return toReturn;
        }
    }

    private static HandType handType(List<CardP2> hand, List<Frequency> freqs) {
        if (freqs.size() == 1) {
            return HandType.FIVE_OF_A_KIND;
        } else if (freqs.size() == 2 && freqs.get(1).occurrences == 1) {
            return HandType.FOUR_OF_A_KIND;
        } else if (freqs.size() == 2 && freqs.get(0).occurrences == 3 && freqs.get(1).occurrences == 2) {
            return HandType.FULL_HOUSE;
        } else if (freqs.size() == 3 && freqs.get(0).occurrences == 3) {
            return HandType.THREE_OF_A_KIND;
        } else if (freqs.size() == 3 && freqs.get(0).occurrences == 2 && freqs.get(1).occurrences == 2) {
            return HandType.TWO_PAIRS;
        } else if (freqs.size() == 4) {
            return HandType.ONE_PAIR;
        } else return HandType.HIGH_CARD;
    }

    HandType handType() {
        var freqs = Frequency.fromHand(hand);

        //System.out.println("freqs = " + freqs);

        if (hand.contains(CardP2.JOKER)) {
            var bestHandType = HandType.HIGH_CARD;

            //iter card 1 if needed
            List<CardP2> listCard1 = hand.get(0) == CardP2.JOKER ? List.of(CardP2.values()) : List.of(hand.get(0));
            for (var card1 : listCard1) {
                //iter card 2 if needed
                List<CardP2> listCard2 = hand.get(1) == CardP2.JOKER ? List.of(CardP2.values()) : List.of(hand.get(1));
                for (var card2 : listCard2) {
                    //iter card 3 if needed
                    List<CardP2> listCard3 = hand.get(2) == CardP2.JOKER ? List.of(CardP2.values()) : List.of(hand.get(2));
                    for (var card3 : listCard3) {
                        //iter card 4 if needed
                        List<CardP2> listCard4 = hand.get(3) == CardP2.JOKER ? List.of(CardP2.values()) : List.of(hand.get(3));
                        for (var card4 : listCard4) {
                            //iter card 3 if needed
                            List<CardP2> listCard5 = hand.get(4) == CardP2.JOKER ? List.of(CardP2.values()) : List.of(hand.get(4));
                            for (var card5 : listCard5) {
                                var tmpHand = List.of(card1, card2, card3, card4, card5);
                                // TODO Finir méthode
                                var currentHandType = handType(tmpHand, Frequency.fromHand(tmpHand));

                                if (currentHandType.compareTo(bestHandType) < 0) bestHandType = currentHandType;
                            }

                        }
                    }
                }
            }
        return bestHandType;
        } else return handType(hand, freqs);
    }

}

public class Day7 {
    public static void main(String[] args) {
        //var lines = Utils.listFromFile("inputs/demo.txt");
        var lines = Utils.listFromFile("inputs/y2023/day7_input.txt");

        var players_p1 = new ArrayList<>(lines.stream()
                .map(PlayerP1::fromLine)
                .toList());
        var players_p2 = new ArrayList<>(lines.stream()
                .map(PlayerP2::fromLine)
                //.peek(p -> System.out.println("p.handType() = " + p.handType()))
                .toList()

        );

        players_p1.sort(Comparator.naturalOrder());
        players_p2.sort(Comparator.naturalOrder());

        for (int i = 0; i < players_p2.size(); i++){
            System.out.println("players_p2.get("+i+") = " + players_p2.get(i)+", hand ="+players_p2.get(i).handType());
        }

        long result_p1 = 0;
        long result_p2 = 0;


        for (int i = 0, rank = players_p1.size(); i < players_p1.size(); i++, rank--) {
            result_p1 += rank * players_p1.get(i).bid();
            result_p2 += rank * players_p2.get(i).bid();
        }

        System.out.println("result_p1 = " + result_p1);
        System.out.println("result_p2 = " + result_p2);
    }
}
