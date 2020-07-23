package Task1;

import java.util.*;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        List<Integer> intList = Arrays.asList(
                1, 2, 5, 16, -1, -2, 0, 32, 3,
                5, 8, 23, 4, 1024, -400, 256,
                128, -128, -256, 334, 4546, 888
        );

        long benchTime = System.currentTimeMillis();
        Stream<Integer> stream = intList.stream();
        stream.filter(x -> x > 0)
                .filter(x -> x % 2 == 0)
                .sorted(Comparator.naturalOrder())
                .forEach(System.out::println);
        benchTime = System.currentTimeMillis() - benchTime;
        System.out.println("Working time: " +  benchTime );

        benchTime = System.currentTimeMillis();
        List<Integer> resList = new ArrayList<>();
        for (int i = 0; i < intList.size(); i++) {
            Integer v = intList.get(i);
            if (v > 0 && v % 2 == 0) {
                resList.add(v);
            }
        }
        Collections.sort(resList, Comparator.naturalOrder());
        for (int v : resList) {
            System.out.println(v);
        }
        benchTime = System.currentTimeMillis() - benchTime;
        System.out.println("Working time: " +  benchTime );
    }
}
