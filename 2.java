import java.util.Arrays;
import java.util.Random;

public class SimplePerceptron {
    private double[][] weights; // Веса для 10 выходов
    private double[] biases; // Смещения для каждого выхода
    private double learningRate;

    public SimplePerceptron(int inputSize, int outputSize, double learningRate) {
        this.weights = new double[outputSize][inputSize];
        this.biases = new double[outputSize];
        this.learningRate = learningRate;

        // Инициализация весов случайными значениями
        Random rand = new Random();
        for (int i = 0; i < outputSize; i++) {
            for (int j = 0; j < inputSize; j++) {
                weights[i][j] = rand.nextDouble() * 0.01; // Небольшие случайные значения
            }
            biases[i] = 0.0; // Инициализация смещений
        }
    }

    public double[] predict(double[] inputs) {
        double[] outputs = new double[biases.length];

        for (int i = 0; i < biases.length; i++) {
            double sum = biases[i];
            for (int j = 0; j < inputs.length; j++) {
                sum += weights[i][j] * inputs[j];
            }
            outputs[i] = activationFunction(sum);
        }
        return outputs;
    }

    public void train(double[][] trainingInputs, int[][] labels, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int i = 0; i < trainingInputs.length; i++) {
                double[] prediction = predict(trainingInputs[i]);
                // Обновление весов и смещений
                for (int j = 0; j < weights.length; j++) {
                    int error = labels[i][j] - (prediction[j] > 0.5 ? 1 : 0); // Бинарная классификация
                    for (int k = 0; k < weights[j].length; k++) {
                        weights[j][k] += learningRate * error * trainingInputs[i][k];
                    }
                    biases[j] += learningRate * error;
                }
            }
        }
    }

    private double activationFunction(double sum) {
        return sum >= 0 ? 1 : 0; // Пороговая функция активации
    }

    public static void main(String[] args) {
        // Пример использования
        int inputSize = 100; // 10x10 пикселей
        int outputSize = 10; // Цифры от 0 до 9
        SimplePerceptron perceptron = new SimplePerceptron(inputSize, outputSize, 0.1);

        // Здесь должны быть ваши данные для обучения
        double[][] trainingInputs = new double[][] {
            // Пример: вектор из 100 значений для каждой цифры
            // Эти значения должны быть нормализованы (например, от 0 до 1)
            // Например, для цифры "0":
            { /* пиксели для цифры "0" */ },
            { /* пиксели для цифры "1" */ },
            // ...
        };

        int[][] labels = new int[][] {
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, // Для цифры "0"
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0}, // Для цифры "1"
            // ...
        };

        // Обучение перцептрона
        perceptron.train(trainingInputs, labels, 100); // Количество эпох

        // Пример предсказания
        double[] testInput = new double[100]; // Входные данные для тестирования
        Arrays.fill(testInput, 1.0); // Заполните тестовые данные

        double[] prediction = perceptron.predict(testInput);
        System.out.println("Предсказание: " + Arrays.toString(prediction));
    }
}
