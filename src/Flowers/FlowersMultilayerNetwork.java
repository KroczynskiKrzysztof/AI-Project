package Flowers;

import Basic.MultiLayerNetwork;
import Basic.Row;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FlowersMultilayerNetwork {
    public static void main(String[] args) {
        ArrayList<Row> trainingSet = Row.importFromCsv("Z:\\sem4\\nai\\Perceptron\\data\\training.txt");
        ArrayList<String> classNames = trainingSet.stream().map(row -> row.className).distinct().collect(Collectors.toCollection(ArrayList<String>::new));

        MultiLayerNetwork multiLayerNetwork = new MultiLayerNetwork(new int[]{10,20},classNames,trainingSet.get(0).data.length,.1);
        multiLayerNetwork.learnEpoch(trainingSet,10000);

        int[][] matrix=(multiLayerNetwork.testNetworkMatrix(trainingSet));
//        for (String className : classNames) {
//            System.out.print(className);
//        }
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            System.out.println((classNames.get(i))+":\t");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]+"\t");
            }
            System.out.println();
        }



        matrix=multiLayerNetwork.testNetworkMatrix(Row.importFromCsv("Z:\\sem4\\nai\\Perceptron\\data\\iris.txt"));
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            System.out.println((classNames.get(i))+":\t");
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]+"\t");
            }
            System.out.println();
        }
    }
}
