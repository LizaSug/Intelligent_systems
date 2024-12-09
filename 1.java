import java.util.Arrays;

public class Perceptron {
    private double[] weights;
    private double bias;
    private double learningRate;

    public Perceptron(int inputSize, double learningRate) {
        this.weights = new double[inputSize];
        this.bias = 0.0;
        this.learningRate = learningRate;
        // Инициализация весов случайными значениями
        for (int i = 0; i < inputSize; i++) {
            weights[i] = Math.random();
        }
    }

    public int predict(double[] inputs) {
        double sum = bias;
        for (int i = 0; i < inputs.length; i++) {
            sum += weights[i] * inputs[i];
        }
        return activationFunction(sum);
    }

    public void train(double[][] trainingInputs, int[] labels, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int i = 0; i < trainingInputs.length; i++) {
                int prediction = predict(trainingInputs[i]);
                int error = labels[i] - prediction;

                // Обновление весов и смещения
                for (int j = 0; j < weights.length; j++) {
                    weights[j] += learningRate * error * trainingInputs[i][j];
                }
                bias += learningRate * error;
            }
        }
    }

    private int activationFunction(double sum) {
        return sum >= 0 ? 1 : 0;
    }

    public static void main(String[] args) {
        // Обучение функции "И"
        Perceptron andPerceptron = new Perceptron(2, 0.1);
        double[][] andInputs = { {0, 0}, {0, 1}, {1, 0}, {1, 1} };
        int[] andLabels = { 0, 0, 0, 1 }; // Логическая функция "И"
        andPerceptron.train(andInputs, andLabels, 10);

        System.out.println("AND Gate:");
        for (double[] input : andInputs) {
            System.out.println(Arrays.toString(input) + " => " + andPerceptron.predict(input));
        }

        // Обучение функции "ИЛИ"
        Perceptron orPerceptron = new Perceptron(2, 0.1);
        double[][] orInputs = { {0, 0}, {0, 1}, {1, 0}, {1, 1} };
        int[] orLabels = { 0, 1, 1, 1 }; // Логическая функция "ИЛИ"
        orPerceptron.train(orInputs, orLabels, 10);

        System.out.println("OR Gate:");
        for (double[] input : orInputs) {
            System.out.println(Arrays.toString(input) + " => " + orPerceptron.predict(input));
        }

        // Обучение функции "НЕ"
        Perceptron notPerceptron = new Perceptron(1, 0.1);
        double[][] notInputs = { {0}, {1} };
        int[] notLabels = { 1, 0 }; // Логическая функция "НЕ"
        notPerceptron.train(notInputs, notLabels, 10);

        System.out.println("NOT Gate:");
        for (double[] input : notInputs) {
            System.out.println(Arrays.toString(input) + " => " + notPerceptron.predict(input));
        }
    }
}
