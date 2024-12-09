import java.util.Arrays;
import java.util.Random;

public class HopfieldNetwork {
    private int size;
    private double[][] weights;
    
    public HopfieldNetwork(int size) {
        this.size = size;
        this.weights = new double[size][size];
        
        // Инициализация весов нулями
        for (int i = 0; i < size; i++) {
            Arrays.fill(weights[i], 0);
        }
    }

    // Метод для обучения сети на паттерне
    public void train(double[] pattern) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i != j) {
                    weights[i][j] += pattern[i] * pattern[j];
                }
            }
        }
    }

    // Метод для восстановления паттерна
    public double[] recall(double[] input) {
        double[] output = Arrays.copyOf(input, size);
        boolean changed;

        do {
            changed = false;
            for (int i = 0; i < size; i++) {
                double netInput = 0;
                for (int j = 0; j < size; j++) {
                    netInput += weights[i][j] * output[j];
                }
                // Применение активационной функции (здесь - знак)
                double newValue = netInput >= 0 ? 1 : -1;
                if (newValue != output[i]) {
                    output[i] = newValue;
                    changed = true;
                }
            }
        } while (changed);

        return output;
    }

    public static void main(String[] args) {
        // Пример: кодирование цифр от 0 до 9 в виде 10x10 матриц
        double[][] digits = {
            {1, 1, 1, 1, 1, 0, 1, 1, 1, 1, // 0
             1, 0, 0, 0, 0, 1, 0, 0, 0, 1,
             1, 0, 0, 0, 0, 1, 0, 0, 0, 1,
             1, 0, 0, 0, 0, 1, 0, 0, 0, 1,
             1, 1, 1, 1, 1, 0, 1, 1, 1, 1},

            {0, 0, 0, 1, 1, 0, // ... и т.д. для других цифр
             // ...
            },
            // Добавьте остальные цифры от 1 до 9 аналогично
        };

        int size = digits[0].length; // Размерность сети
        HopfieldNetwork hopfieldNetwork = new HopfieldNetwork(size);

        // Обучение сети на всех цифрах
        for (double[] digit : digits) {
            hopfieldNetwork.train(digit);
        }

        // Пример поврежденного изображения (например цифра "0" с повреждениями)
        double[] damagedDigit = {
            -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1,
        };
        
        // Восстановление поврежденного изображения
        double[] restoredDigit = hopfieldNetwork.recall(damagedDigit);

        // Вывод восстановленного изображения
        System.out.println("Восстановленная цифра:");
        for (int i = 0; i < Math.sqrt(size); i++) {
            for (int j = 0; j < Math.sqrt(size); j++) {
                System.out.print((restoredDigit[i * (int)Math.sqrt(size) + j] == -1 ? " " : "*") + " ");
            }
            System.out.println();
        }
    }
}
