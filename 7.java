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
