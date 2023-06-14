package Language;

import Basic.Layer;
import Basic.Neuron;
import Basic.Row;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class LanguageSingleLayerNetwork {
    public static void main(String[] args) throws IOException {
        String pathToTrainingData = "Z:\\sem4\\nai\\Basic.Perceptron\\Articles";
        double a = .1;
        int epochs = 10000;


        System.out.println("""
                                                                                                                                    \s
                           88                                                          88                                           \s
                           88                                                ,d        88                         ,d                \s
                           88                                                88        88                         88                \s
                ,adPPYba,  88   ,d8  8b       d8  8b,dPPYba,    ,adPPYba,  MM88MMM     88,dPPYba,    ,adPPYba,  MM88MMM  ,adPPYYba, \s
                I8[    ""  88 ,a8"   `8b     d8'  88P'   `"8a  a8P_____88    88        88P'    "8a  a8P_____88    88     ""     `Y8 \s
                 `"Y8ba,   8888[      `8b   d8'   88       88  8PP""\"""\""    88        88       d8  8PP""\"""\""    88     ,adPPPPP88 \s
                aa    ]8I  88`"Yba,    `8b,d8'    88       88  "8b,   ,aa    88,       88b,   ,a8"  "8b,   ,aa    88,    88,    ,88 \s
                `"YbbdP"'  88   `Y8a     Y88'     88       88   `"Ybbd8"'    "Y888     8Y"Ybbd8"'    `"Ybbd8"'    "Y888  `"8bbdP"Y8 \s
                                         d8'                                                                                        \s
                                        d8'                                                                                         \s
                for simpler tomorrow :)""");


        ArrayList<Row> trainingData = LanguageTools.readLanguageFiles(pathToTrainingData);
        ArrayList<Neuron> neuronArrayList = new ArrayList<>();
        trainingData.stream().map(row -> row.className).distinct().forEach(
                className -> {
                    Neuron p = Neuron.train(trainingData,className,a,epochs);
                    p.normalizeMe();
                    neuronArrayList.add(p);
                }
        );
        Layer layer = new Layer(neuronArrayList);
        boolean go = true;
        Scanner s = new Scanner(System.in);
        while (go){
            System.out.println("input text:");
            String inputString = s.nextLine();
            Double[] charCounts = LanguageTools.getCharCount(inputString);

            HashMap<String,Double> verdictsAndValues = layer.askSingleLayerNetwork(charCounts);
            System.out.println("Verdict \t value");
            verdictsAndValues.forEach(((s1, aDouble) -> System.out.printf("%s \t %f %n",s1,aDouble)));

            if (inputString.equals("")) go=false;
        }
    }
}
