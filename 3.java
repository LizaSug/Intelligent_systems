import java.util.Random;

public class MultiLayerPerceptron {
    private int inputSize;
    private int hiddenSize;
    private int outputSize;
    private double[][] weightsInputHidden;
    private double[][] weightsHiddenOutput;
    private double[] hiddenBiases;
    private double[] outputBiases;
    private double learningRate;

    public MultiLayerPerceptron(int inputSize, int hiddenSize, int outputSize, double learningRate) {
        this.inputSize = inputSize;
        this.hiddenSize = hiddenSize;
        this.outputSize = outputSize;
        this.learningRate = learningRate;

        weightsInputHidden = new double[inputSize][hiddenSize];
        weightsHiddenOutput = new double[hiddenSize][outputSize];
        hiddenBiases = new double[hiddenSize];
        outputBiases = new double[outputSize];

        initializeWeights();
    }

    private void initializeWeights() {
        Random rand = new Random();
        for (int i = 0; i < inputSize; i++) {
            for (int j = 0; j < hiddenSize; j++) {
                weightsInputHidden[i][j] = rand.nextDouble() * 0.1 - 0.05; // Инициализация весов в диапазоне [-0.05, 0.05]
            }
        }
        for (int i = 0; i < hiddenSize; i++) {
            for (int j = 0; j < outputSize; j++) {
                weightsHiddenOutput[i][j] = rand.nextDouble() * 0.1 - 0.05;
            }
            hiddenBiases[i] = rand.nextDouble() * 0.1 - 0.05;
        }
        for (int i = 0; i < outputSize; i++) {
            outputBiases[i] = rand.nextDouble() * 0.1 - 0.05;
        }
    }

    public double[] predict(double[] inputs) {
        double[] hiddenLayerOutputs = new double[hiddenSize];
        double[] finalOutputs = new double[outputSize];

        // Прямое распространение
        for (int i = 0; i < hiddenSize; i++) {
            hiddenLayerOutputs[i] = activationFunction(dotProduct(inputs, weightsInputHidden, i) + hiddenBiases[i]);
        }

        for (int i = 0; i < outputSize; i++) {
            finalOutputs[i] = activationFunction(dotProduct(hiddenLayerOutputs, weightsHiddenOutput, i) + outputBiases[i]);
        }

        return finalOutputs;
    }

    private double dotProduct(double[] inputs, double[][] weights, int neuronIndex) {
        double sum = 0.0;
        for (int j = 0; j < inputs.length; j++) {
            sum += inputs[j] * weights[j][neuronIndex];
        }
        return sum;
    }

    private double activationFunction(double x) {
        return 1 / (1 + Math.exp(-x)); // Сигмоидная функция
    }

    public void train(double[][] trainingInputs, double[][] trainingOutputs, int epochs) {
        for (int epoch = 0; epoch < epochs; epoch++) {
            for (int i = 0; i < trainingInputs.length; i++) {
                // Прямое распространение
                double[] hiddenLayerOutputs = new double[hiddenSize];
                for (int j = 0; j < hiddenSize; j++) {
                    hiddenLayerOutputs[j] = activationFunction(dotProduct(trainingInputs[i], weightsInputHidden, j) + hiddenBiases[j]);
                }

                double[] finalOutputs = new double[outputSize];
                for (int j = 0; j < outputSize; j++) {
                    finalOutputs[j] = activationFunction(dotProduct(hiddenLayerOutputs, weightsHiddenOutput, j) + outputBiases[j]);
                }

                // Обратное распространение ошибки
                double[] outputErrors = new double[outputSize];
                for (int j = 0; j < outputSize; j++) {
                    outputErrors[j] = trainingOutputs[i][j] - finalOutputs[j];
                }

                // Обновление весов и смещений для выходного слоя
                for (int j = 0; j < outputSize; j++) {
                  for (int k = 0; k < hiddenSize; k++) {
                        weightsHiddenOutput[k][j] += learningRate * outputErrors[j] * finalOutputs[j] * (1 - finalOutputs[j]) * hiddenLayerOutputs[k];
                    }
                    outputBiases[j] += learningRate * outputErrors[j] * finalOutputs[j] * (1 - finalOutputs[j]);
                }

                // Обновление весов и смещений для скрытого слоя
                double[] hiddenErrors = new double[hiddenSize];
                for (int j = 0; j < hiddenSize; j++) {
                    for (int k = 0; k < outputSize; k++) {
                        hiddenErrors[j] += outputErrors[k] * weightsHiddenOutput[j][k];
                    }
                    for (int k = 0; k < inputSize; k++) {
                        weightsInputHidden[k][j] += learningRate * hiddenErrors[j] * hiddenLayerOutputs[j] * (1 - hiddenLayerOutputs[j]) * trainingInputs[i][k];
                    }
                    hiddenBiases[j] += learningRate * hiddenErrors[j] * hiddenLayerOutputs[j] * (1 - hiddenLayerOutputs[j]);
                }
            }
        }
    }

    public static void main(String[] args) {
        // Пример использования для функции XOR
        MultiLayerPerceptron mlp = new MultiLayerPerceptron(2, 2, 1, 0.1);

        // Входные данные для функции XOR
        double[][] trainingInputs = {
            {0, 0},
            {0, 1},
            {1, 0},
            {1, 1}
        };

        // Ожидаемые выходные данные для функции XOR
        double[][] trainingOutputs = {
            {0},
            {1},
            {1},
            {0}
        };

        // Обучение
        mlp.train(trainingInputs, trainingOutputs, 10000);

        // Тестирование
        System.out.println("Результаты предсказаний для функции XOR:");
        for (double[] input : trainingInputs) {
            double[] output = mlp.predict(input);
            System.out.printf("Вход: %s -> Выход: %.5f%n", java.util.Arrays.toString(input), output[0]);
        }
    }
}
