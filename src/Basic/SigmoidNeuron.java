package Basic;

public class SigmoidNeuron extends Neuron {
    public SigmoidNeuron(int size, String name, double a) {
        super(size, name, a);
    }

    public static SigmoidNeuron loadSigmoidPerceptron(double[] weightsAndT, String name, double a){
        SigmoidNeuron sigmoidPerceptron = new SigmoidNeuron(weightsAndT.length, name,a);
        sigmoidPerceptron.weightsAndT=weightsAndT;
        return sigmoidPerceptron;
    }

@Override
    public Double calcActivation(Double[] data) throws IllegalArgumentException {
        return sigmoid(super.calcNet(data));
    }
    private static double sigmoid(double x)
    {
        return 1 / (1 + Math.exp(-x));
    }
}
