import org.ejml.simple.SimpleMatrix;

public class NeuralNetwork {
    private SimpleMatrix weightsInputHidden;
    private SimpleMatrix weightsHiddenOutput;
    private double learningRate;

    public NeuralNetwork(int inputSize, int hiddenSize, int outputSize, double learningRate) {
        this.weightsInputHidden = SimpleMatrix.random_DDRM(inputSize, hiddenSize, -1, 1, new java.util.Random());
        this.weightsHiddenOutput = SimpleMatrix.random_DDRM(hiddenSize, outputSize, -1, 1, new java.util.Random());
        this.learningRate = learningRate;
    }

    public SimpleMatrix sigmoid(SimpleMatrix x) {
        return x.elementPower(1.0 / (1.0 + x.elementExp().scale(-1)));
    }

    public SimpleMatrix sigmoidDerivative(SimpleMatrix x) {
        return x.elementMult(x.scale(-1).elementPlus(1));
    }

    public void train(SimpleMatrix input, SimpleMatrix target) {
        // Forward pass
        SimpleMatrix hidden = sigmoid(weightsInputHidden.transpose().mult(input));
        SimpleMatrix output = sigmoid(weightsHiddenOutput.transpose().mult(hidden));

        // Backward pass
        SimpleMatrix outputError = target.minus(output);
        SimpleMatrix outputDelta = outputError.elementMult(sigmoidDerivative(output));

        SimpleMatrix hiddenError = weightsHiddenOutput.mult(outputDelta);
        SimpleMatrix hiddenDelta = hiddenError.elementMult(sigmoidDerivative(hidden));

        // Update weights
        weightsHiddenOutput = weightsHiddenOutput.plus(hidden.mult(outputDelta.transpose()).scale(learningRate));
        weightsInputHidden = weightsInputHidden.plus(input.mult(hiddenDelta.transpose()).scale(learningRate));
    }

    public SimpleMatrix predict(SimpleMatrix input) {
        SimpleMatrix hidden = sigmoid(weightsInputHidden.transpose().mult(input));
        return sigmoid(weightsHiddenOutput.transpose().mult(hidden));
    }
}
