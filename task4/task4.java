import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class task4 {

    public static void main(String[] args) {
        // Путь к файлу передается как аргумент
        if (args.length != 1) {
            System.out.println("Укажите путь к файлу с числами в качестве аргумента.");
            return;
        }

        String filePath = args[0];

        List<Integer> nums = new ArrayList<>();

        // Чтение чисел из файла
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextInt()) {
                nums.add(scanner.nextInt());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            return;
        }

        // Проверка, что массив не пустой
        if (nums.isEmpty()) {
            System.out.println("Файл не содержит чисел.");
            return;
        }

        // Найдем медиану
        Collections.sort(nums);
        int median = nums.get(nums.size() / 2);  // Если размер массива четный, это одно из двух средних чисел

        // Считаем количество шагов для приведения всех элементов к медиане
        int moves = 0;
        for (int num : nums) {
            moves += Math.abs(num - median);
        }

        // Выводим результат
        System.out.println("Минимальное количество ходов: " + moves);
    }
}