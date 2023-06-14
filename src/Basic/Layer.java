package Basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Layer {
    public ArrayList<Neuron> neurons = new ArrayList<>();

    public Layer(ArrayList<Neuron> neurons) {
        this.neurons = neurons;
    }
    public Layer(int amount,int size, double a){
        for (int i = 0; i < amount; i++) {
            neurons.add(new SigmoidNeuron(size, String.valueOf(i),a));
        }
    }
    public Double[] calcActivation(Double[] data){
        Double[] result = new Double[neurons.size()];
        for (int i = 0; i < neurons.size(); i++) {
            result[i]= neurons.get(i).calcActivation(data);
        }
        return result;
    }
    public Double[] calcNet(Double[] data){
        Double[] result = new Double[neurons.size()];
        for (int i = 0; i < neurons.size()-1; i++) {
            result[i]= neurons.get(i).calcActivation(data);
        }
        result[neurons.size()-1]= neurons.get(neurons.size()-1).calcNet(data);
        return result;
    }
    public HashMap<String,Double> askSingleLayerNetwork( Double[] data){
        LinkedHashMap<String,Double> result = new LinkedHashMap<>();
        for (Neuron neuron : neurons) {
            result.put(neuron.name, neuron.calcNet(data));
        }
        ArrayList<Map.Entry<String, Double>> listOfEntries = new ArrayList<>(result.entrySet());
        listOfEntries.sort((o1, o2) -> -Double.compare(o1.getValue(), o2.getValue()));
        result=new LinkedHashMap<>();
        for (Map.Entry<String, Double> listOfEntry : listOfEntries) {
            result.put(listOfEntry.getKey(),listOfEntry.getValue());
        }
        return result;
    }
}
