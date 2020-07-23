package Task2;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {

        Collection<People> peoples = Arrays.asList(
                new People("Вася", 16, Sex.MAN),
                new People("Петя", 23, Sex.MAN),
                new People("Елена", 42, Sex.WOMEN),
                new People("Иван Иванович", 69, Sex.MAN)
        );

        Stream<People> stream = peoples.stream();
        List<People> armyMen = stream.filter(p -> p.getAge() > 17 && p.getSex().equals(Sex.MAN))
                .collect(Collectors.toList());

        Double aveAge = getAveAge(peoples.stream());

        stream = peoples.stream();
        Long canWorkCount = stream.filter(
                p -> p.getAge() > 17 && (
                        (p.getSex().equals(Sex.MAN) && p.getAge() < 65) ||
                                (p.getSex().equals(Sex.WOMEN) && p.getAge() < 60)
                    )
            ).count();

        System.out.println("Военнообязанные: " + armyMen);
        System.out.println("Средний возраст среди мужчин: " + aveAge);
        System.out.println("Кол-во работоспособных людей: " + canWorkCount);

        /*
        * Дополнительное задание со (*)
        * В случае ниже мы получаем ошибку NoSuchElementException: No value present
        * Нужно использовать функцию orElse которая позволит нам поставить свое
        *   результирующее значение вместо возникновения ошибки при обработке OptionalDouble.
        */
        try {
            // Ошибка
            aveAge = getAveAge(peoples.stream().filter(p -> p.getSex().equals(Sex.WOMEN)));
            System.out.println(aveAge);
        } catch (Exception e) {
            System.out.println(e.getClass().getName() + ": " + e.getMessage());
        }
        // Работает и не задает вопросов
        aveAge = getAveAgeOpt(peoples.stream().filter(p -> p.getSex().equals(Sex.WOMEN)));
        System.out.println(aveAge);
    }

    public static Double getAveAge(Stream<People> stream) {
        return stream.filter(p -> p.getSex().equals(Sex.MAN))
                .mapToInt(People::getAge).average().getAsDouble();
    }

    public static Double getAveAgeOpt(Stream<People> stream) {
        return stream.filter(p -> p.getSex().equals(Sex.MAN))
                .mapToInt(People::getAge).average().orElse(0);
    }
}
