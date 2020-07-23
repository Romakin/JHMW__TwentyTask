package Task3;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        // Macbook pro 15 2016 Intel Mobile Core i7 "Skylake" (I7-6820HQ) 2.7

        List<Integer> benchNums = Arrays.asList(100, 1_000, 10_000, 100_000, 1_000_000, 10_000_000, 15_000_000);

        for (int benchNum : benchNums) {
            System.out.println("----" +
                    "Старт бенчмарка для " + benchNum + " ед.-----\n");
            Collection<People> peoples = getPeople(benchNum);
            startBenchSimpleStream(peoples);
            startBenchParallelStream(peoples);
            System.out.println("-----------------------------\n");
        }
    }

    public static void startBenchSimpleStream(Collection<People> peoples) {
        long totalBench = System.nanoTime();
        System.out.println("startBenchSimpleStream");

        startBSArmyMen(peoples.stream());
        startBSAveMenAge(peoples.stream());
        startBSCanWorkCount(peoples.stream());

        System.out.println("Процесс всего занял: ");
        showBench(totalBench);
        System.out.println("**********************");
    }

    public static void startBenchParallelStream(Collection<People> peoples) {
        long totalBench = System.nanoTime();
        System.out.println("startBenchParallelStream");

        startBSArmyMen(peoples.parallelStream());
        startBSAveMenAge(peoples.parallelStream());
        startBSCanWorkCount(peoples.parallelStream());

        System.out.println("Процесс всего занял: ");
        showBench(totalBench);
    }

    public static void startBSArmyMen(Stream<People> stream) {
        long benchTime = System.nanoTime();
        List<People> armyMen = stream.filter(p -> p.getAge() > 17 && p.getSex().equals(Sex.MAN))
                .collect(Collectors.toList());
        showBench(benchTime);
        System.out.println("Военнообязанные (кол-во): " + armyMen.size());
    }

    public static void startBSAveMenAge(Stream<People> stream) {
        long benchTime = System.nanoTime();
        Double aveAge = stream.filter(p -> p.getSex().equals(Sex.MAN))
                .mapToInt(People::getAge).average().orElse(0);
        showBench(benchTime);
        System.out.println("Средний возраст среди мужчин: " + aveAge);
    }

    public static void startBSCanWorkCount(Stream<People> stream) {
        long benchTime = System.nanoTime();
        Long canWorkCount = stream.filter(
                p -> p.getAge() > 17 && (
                        (p.getSex().equals(Task2.Sex.MAN) && p.getAge() < 65) ||
                                (p.getSex().equals(Task2.Sex.WOMEN) && p.getAge() < 60)
                )
        ).count();
        showBench(benchTime);
        System.out.println("Кол-во работоспособных людей: " + canWorkCount);
    }

    public static Collection<People> getPeople(int num) {
        if (num == 0) num = 12_000_000;
        List<String> names = Arrays.asList(
                "Иванов", "Петров", "Сидоров",
                "Васечкин", "Попов", "Кузнецов",
                "Васильев", "Соколов", "Михайлов"
            );
        List<People> peoples = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            peoples.add(new People(names.get(
                    new Random().nextInt(names.size())),
                    new Random().nextInt(100),
                    Sex.randomSex()));
        }
        return peoples;
    }

    public static void showBench(long benchTime) {
        benchTime = System.nanoTime() - benchTime;
        double processTime = (double) benchTime / 1_000_000_000.0;
        System.out.println("Process time: " + processTime + " s");
    }
}
