package Main;

//  1. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив. Метод должен
//  вернуть новый массив, который получен путем вытаскивания из исходного массива элементов, идущих после последней
//  четверки. Входной массив должен содержать хотя бы одну четверку, иначе в методе необходимо
//  выбросить RuntimeException.
//
//  Написать набор тестов для этого метода (по 3-4 варианта входных данных).
//  Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
//
//  2. Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы,
//  то метод вернет false; Если есть число отличное от 1 или 4 то вернуть false; Написать набор тестов для этого метода
//  (по 3-4 варианта входных данных).
//
//  [ 1 1 1 4 4 1 4 4 ] -> true
//  [ 1 1 1 1 1 1 ] -> false
//  [ 4 4 4 4 ] -> false
//  [ 1 4 4 1 1 4 3 ] -> false
//
//  3. * Добавить на серверную сторону сетевого чата логирование событий (сервер запущен, произошла ошибка, клиент
//  подключился, клиент прислал сообщение/команду).


import java.io.IOException;
import java.util.Arrays;

public class Main {

    public void main(String[] args) throws IOException {

//        System.out.println ("Задание №1");
//        firstTask (new int[]{2, 3, 4, 1, 3, 7, 9, 11, 25, 4, 31, 59, 2, 0, 13});
//        System.out.println (" ");
//
//        System.out.println ("Задание №2 (true)");
//        System.out.println (secondTask (new int[]{1, 4, 4, 1, 4, 1, 1, 1, 4}));
//        System.out.println (" ");

//        System.out.println ("Задание №2 (false)");
//        System.out.println (secondTask (new int[]{1, 4, 7, 1, 4, 8, 1, 2, 4}));

    }

    public int add(int a, int b) {
        return a + b;
    }

    public int mul(int a, int b) {
        return a * b;
    }

    public int div(int a, int b) {
        return a / b;
    }

    public int[] firstTask (int[] arr) {
        System.out.print ("Первоначальный массив: ");
        System.out.println (Arrays.toString (arr));
        int a = 0;
        int [] newArr = null;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 4) {
                a = arr[i];
                int l = i + 1;
                newArr = new int[arr.length - l];
                for (int j = i + 1, k = 0; j < arr.length; j++, k++) {
                    if (arr[j] == 4) {
                        continue;
                    }
                    newArr[k] = arr[j];
                }
            }
        }
        System.out.print ("Новый массив: ");
        System.out.println (Arrays.toString (newArr));
        try{
            a = 1 / a;
        } catch(ArithmeticException e){
            throw new RuntimeException("В массива нет \"4\"");
        }
        return newArr;
    }

    public boolean secondTask (int[] arr2) {
        System.out.println (Arrays.toString (arr2));
        boolean one = false;
        boolean four = false;
        for (int i = 0; i < arr2.length; i++) {
            if (arr2[i] != 1 && arr2[i] != 4) {
                return false;
            }
            if (arr2[i] == 1) {
                one = true;
            }
            if (arr2[i] == 4) {
                four = true;
            }
        }
        return one && four;
    }
}
