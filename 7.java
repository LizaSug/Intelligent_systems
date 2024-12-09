import org.ejml.simple.SimpleMatrix;

public class Perceptron {
    private SimpleMatrix weights;
    private double learningRate;

    public Perceptron(int inputSize, double learningRate) {
        this.weights = SimpleMatrix.random_DDRM(inputSize, 1, -1, 1, new java.util.Random());
        this.learningRate = learningRate;
    }

    public SimpleMatrix activationFunction(SimpleMatrix x) {
        return x.elementPower(1.0 / (1.0 + x.elementExp().scale(-1)));
    }

    public SimpleMatrix activationFunctionDerivative(SimpleMatrix x) {
        return x.elementMult(x.scale(-1).elementPlus(1));
    }

    public void train(SimpleMatrix input, SimpleMatrix target) {
        // Forward pass
        SimpleMatrix output = predict(input);
        
        // Calculate error
        SimpleMatrix error = target.minus(output);
        
        // Backpropagation
        SimpleMatrix delta = error.elementMult(activationFunctionDerivative(output));
        
        // Update weights
        weights = weights.plus(input.mult(delta.transpose()).scale(learningRate));
    }

    public SimpleMatrix predict(SimpleMatrix input) {
        return activationFunction(weights.transpose().mult(input));
    }
}
public class Main {
    public static void main(String[] args) {
        // Примерные параметры
        int inputSize = 784; // Размер входа (например, 28x28 изображение)
        double learningRate = 0.01;

        // Создание когнитрона
        Perceptron perceptron = new Perceptron(inputSize, learningRate);

        // Загрузка данных (здесь вам нужно реализовать загрузку ваших данных)
        // Для примера создадим случайные данные
        for (int epoch = 0; epoch < 1000; epoch++) {
            for (int i = 0; i < 60000; i++) { // Примерно 60000 изображений
                SimpleMatrix input = SimpleMatrix.random_DDRM(inputSize, 1, -1, 1, new java.util.Random());
                SimpleMatrix target = SimpleMatrix.random_DDRM(1, 1, -1, 1, new java.util.Random());

                // Обучение когнитрона
                perceptron.train(input, target);
            }
            System.out.println("Epoch: " + epoch);
        }

        // Тестирование производительности (здесь вам нужно реализовать тестирование на тестовом наборе)
        // Для примера создадим случайный ввод
        SimpleMatrix testInput = SimpleMatrix.random_DDRM(inputSize, 1, -1, 1, new java.util.Random());
        SimpleMatrix output = perceptron.predict(testInput);

        System.out.println("Output: " + output);
    }
}
