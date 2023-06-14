package Basic;

import java.util.*;

public class Neuron implements Cloneable{
    double[] weightsAndT;
    int size;
    public String name;
    double a;

    public Neuron(int size, String name, double a) {
        this.size = size;
        this.name=name;
        this.a=a;
        Random random = new Random();
        weightsAndT =new double[size+1];
        for (int i = 0; i < weightsAndT.length; i++) {
            weightsAndT[i]= random.nextDouble(-0.1,0.1);
        }
        normalizeMe();
    }
    public int classify(Double[] data) throws IllegalArgumentException {
        double net = calcNet(data);
        if (net>=0) return 1;
        return 0;
    }
    public int isClassificationCorrect(Row row) throws IllegalArgumentException {
        Double[] data = row.data;
        int guess = classify(data);
        if (name.equalsIgnoreCase(row.className) && guess==1 || !name.equalsIgnoreCase(row.className) && guess==0) {
            return 1;
        }
        return 0;
    }
    public void adjustWeights(Row row){
        int guess= classify(row.data);
        Double[] data = new Double[row.data.length+1];
        System.arraycopy(row.data, 0, data, 0, data.length - 1);
        data[data.length-1]=-1d;
        int sign;
        if (name.equals(row.className) && guess==1) sign=0;
        else if (name.equals(row.className) && guess==0) sign=1;
        else if (!name.equals(row.className) && guess==0) sign=0;
        else sign=-1;

        for (int i = 0; i < this.weightsAndT.length; i++) {
            this.weightsAndT[i]+=a*sign*data[i];
        }
    }

    public void adjustWeights(Collection<Row> rows){
        if (rows.size()==0) return;
        for (Row row : rows) {
            this.adjustWeights(row);
        }
    }
    public double classify(Collection<Row> rows, boolean verbose){
        if (rows.size()==0) return 1;
        double correct=0;
        for (Row row : rows) {
            int wasCorrect= isClassificationCorrect(row);
            correct+=wasCorrect;
            if (verbose && wasCorrect==0){
                System.out.println(row);
            }
        }
        return correct/rows.size();
    }


    @Override
    public Neuron clone() {
        Neuron p = new Neuron(this.size,this.name,this.a);

        p.weightsAndT =this.weightsAndT.clone();

        return p;
    }
    public static Neuron train(ArrayList<Row> training, String className, double a, int epochs){
        Neuron neuron = new Neuron(training.get(0).data.length,className,a);
        Neuron mostAccurate= neuron.clone();
        double bestAccuracy=0;
        for (int i = 0; i < epochs; i++) {
            neuron.adjustWeights(training);
            double epochAccuracy= neuron.classify(training,false);
            if (epochAccuracy>bestAccuracy){
                mostAccurate= neuron.clone();
                bestAccuracy=epochAccuracy;
            }
        }
        return mostAccurate;
    }
    public double calcNet(Row row){
        Double[] data = row.data;
        return calcNet(data);
    }
    public double calcNet(Double[] data){
        if (data.length!=size) throw new IllegalArgumentException();
        double sum=0;
        for (int i = 0; i < size; i++) {
            sum+=data[i]* weightsAndT[i];
        }
        sum-= weightsAndT[weightsAndT.length-1];
        return sum;
    }
    public void normalizeMe(){
        double lenSquared = 0;
        for (int i = 0; i < weightsAndT.length-1; i++) {
            lenSquared+=Math.pow(weightsAndT[i],2);
        }
        double len = Math.sqrt(lenSquared);
        for (int i = 0; i < weightsAndT.length-1; i++) {
            weightsAndT[i]/=len;
        }

    }



    public Double calcActivation(Double[] data) {
        return (double) classify(data);
    }
}
