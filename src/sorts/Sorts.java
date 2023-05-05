package sorts;

import java.util.Random;
import java.util.Scanner;

public class Sorts {
    public static void main(String[] args) {
        // Show menus
        System.out.print("""
            Selamat Datang di Program Simulasi
            Menu
            1. Random Data
            2. Simulasi Bubble Sort - Ascending
            3. Simulasi Selection Sort - Ascending
            4. Simulasi Bubble Sort - Descending
            5. Simulasi Selection Sort - Descending
            6. Keluar
            """);

        // Choose menu
        int[] array = new int[5];
        while (true) {
            int choice = inputNumber("\nMasukkan pilihan Anda");
            switch (choice) {
                case 1 -> array = menuGenerateNumbers();
                case 2 -> bubbleSort(array.clone());
                case 3 -> selectionSort(array.clone());
                case 4 -> bubbleSort(array.clone(), false);
                case 5 -> selectionSort(array.clone(), false);
                case 6 -> System.exit(0);
            }
        }
    }

    public static int inputNumber(String label) {
        int number = 0;
        Scanner scanner = new Scanner(System.in);
        boolean expectInput = true;
        System.out.printf("%s: ", label);
        while (expectInput) {
            try {
                number = Integer.parseInt(String.valueOf(scanner.nextLine()));
                expectInput = false;
            } catch (NumberFormatException e) {
                System.out.print("Harap input dengan angka bulat: ");
            }
        }
        return number;
    }
    public static int[] menuGenerateNumbers() {
        System.out.println("GENERATE RANDOM ARRAY");
        int min = inputNumber("Batas bawah");
        int max = inputNumber("Batas atas");
        boolean invalidMax = max < min;
        while (invalidMax) {
            max = inputNumber("Harap masukkan batas atas lebih dari batas bawah");
            invalidMax = max < min;
        }
        int[] array = generateRandomNumbers(min, max+1);
        printArray(array);
        return array;
    }
    public static int[] generateRandomNumbers(int min, int max) {
        int[] numbers = new int[5];
        Random rand = new Random();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = rand.nextInt(min, max);
        }
        return numbers;
    }
    public static void printArray(int[] array) {
        for (int num: array) {
            System.out.printf("%8d", num);
        }
        System.out.println();
    }

    public static void bubbleSort(int[] arr) {
        bubbleSort(arr, true);
    }
    public static void bubbleSort(int[] arr, boolean ascending) {
        String order = ascending ? "ASCENDING" : "DESCENDING";
        System.out.printf("BUBBLE SORT - %s%n", order);

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            System.out.printf("Pass %d%n", i+1);
            for (int j = 0; j < n - i - 1; j++) {
                boolean conditionMet;
                if (ascending) {
                    conditionMet = arr[j] > arr[j + 1];
                } else {
                    conditionMet = arr[j] < arr[j + 1];
                }

                if (conditionMet) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
                printArray(arr);
            }
            System.out.printf("Result of pass %d%n", i+1);
            printArray(arr);
        }
    }

    public static void selectionSort(int[] arr) {
        selectionSort(arr, true);
    }
    public static void selectionSort(int[] arr, boolean ascending) {
        String order = ascending ? "ASCENDING" : "DESCENDING";
        System.out.printf("SELECTION SORT - %s%n", order);

        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                boolean conditionMet;
                if (ascending) {
                    conditionMet = arr[j] < arr[minIndex];
                } else {
                    conditionMet = arr[j] > arr[minIndex];
                }

                if (conditionMet) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
                printArray(arr);
            }
        }
        System.out.println("Result:");
        printArray(arr);
    }
}
