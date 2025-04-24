package by.it.group410972.popova.lesson04;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

/*
Рассчитать число инверсий одномерного массива.
Сложность алгоритма должна быть не хуже, чем O(n log n)

Первая строка содержит число 1<=n<=10000,
вторая - массив A[1…n], содержащий натуральные числа, не превосходящие 10E9.
Необходимо посчитать число пар индексов 1<=i<j<n, для которых A[i]>A[j].

    (Такая пара элементов называется инверсией массива.
    Количество инверсий в массиве является в некотором смысле
    его мерой неупорядоченности: например, в упорядоченном по неубыванию
    массиве инверсий нет вообще, а в массиве, упорядоченном по убыванию,
    инверсию образуют каждые (т.е. любые) два элемента.
    )

Sample Input:
5
2 3 9 2 9
Sample Output:
2

Головоломка (т.е. не обязательно).
Попробуйте обеспечить скорость лучше, чем O(n log n) за счет многопоточности.
Докажите рост производительности замерами времени.
Большой тестовый массив можно прочитать свой или сгенерировать его программно.
*/


public class C_GetInversions {

    public static void main(String[] args) throws FileNotFoundException {
        InputStream stream = C_GetInversions.class.getResourceAsStream("dataC.txt");
        C_GetInversions instance = new C_GetInversions();
        //long startTime = System.currentTimeMillis();
        int result = instance.calc(stream);
        //long finishTime = System.currentTimeMillis();
        System.out.print(result);
    }

    int calc(InputStream stream) throws FileNotFoundException {
        //подготовка к чтению данных
        Scanner scanner = new Scanner(stream);
        //!!!!!!!!!!!!!!!!!!!!!!!!!     НАЧАЛО ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!
        //размер массива
        int n = scanner.nextInt();
        //сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextInt();
        }
        //!!!!!!!!!!!!!!!!!!!!!!!!     тут ваше решение   !!!!!!!!!!!!!!!!!!!!!!!!

        int[] temp = new int[n];
        return mergeSortAndCount(a, temp, 0, n - 1);
    }

    // Метод для рекурсивной сортировки слиянием и подсчета инверсий
    private int mergeSortAndCount(int[] array, int[] temp, int left, int right) {
        int count = 0;

        if (left < right) {
            int middle = left + (right - left) / 2;

            // Подсчитываем инверсии в левой половине массива
            count += mergeSortAndCount(array, temp, left, middle);

            // Подсчитываем инверсии в правой половине массива
            count += mergeSortAndCount(array, temp, middle + 1, right);

            // Подсчитываем инверсии при слиянии двух половин
            count += mergeAndCount(array, temp, left, middle, right);
        }

        return count;
    }

    // Метод для слияния двух отсортированных частей массива и подсчета инверсий
    private int mergeAndCount(int[] array, int[] temp, int left, int middle, int right) {
        int i = left;       // Начало левой части
        int j = middle + 1; // Начало правой части
        int k = left;       // Индекс для временного массива
        int count = 0;

        // Слияние двух частей
        while (i <= middle && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
                count += (middle - i + 1); // Подсчитываем инверсии
            }
        }

        // Копируем оставшиеся элементы левой части
        while (i <= middle) {
            temp[k++] = array[i++];
        }

        // Копируем оставшиеся элементы правой части
        while (j <= right) {
            temp[k++] = array[j++];
        }

        // Копируем временный массив обратно в основной массив
        for (i = left; i <= right; i++) {
            array[i] = temp[i];
        }

        return count;
        //!!!!!!!!!!!!!!!!!!!!!!!!!     КОНЕЦ ЗАДАЧИ     !!!!!!!!!!!!!!!!!!!!!!!!!
    }
}
