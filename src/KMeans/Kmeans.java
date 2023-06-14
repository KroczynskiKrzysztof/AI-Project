package KMeans;

import Basic.Row;

import java.util.*;
import java.util.stream.Collectors;

public class Kmeans {
    ArrayList<Double[]> centroids = new ArrayList<>();
    int k;
    ArrayList<Row> observations;
    HashMap<Row,Double[]> observationCentroidMap = new HashMap<>();
    public Kmeans( int k, ArrayList<Row> data) {
        this.k = k;
        this.observations = data;
        Collections.shuffle(data);
        for (int i = 0; i < k; i++) {
            centroids.add(data.get(i).data);
        }
        Collections.shuffle(data);
        for (Row datum : data) {
            observationCentroidMap.put(datum,new Double[datum.data.length]);
        }
    }
    public int assignNearestCentroids(){
        int changes = 0;
        for (int i = 0; i < observations.size(); i++) {
            Double[] newCentroid = getNearestCentroid(observations.get(i));

            if (!Arrays.equals(newCentroid, observationCentroidMap.get(observations.get(i)))) {
                observationCentroidMap.replace(observations.get(i), newCentroid);
                changes++;
            }
        }
        return changes;
    }
    public Double[] getNearestCentroid(Row row){
        Double[] distances = new Double[k];
        for (int i = 0; i < distances.length; i++) {
            distances[i]=calcDistance(row.data,centroids.get(i));
        }
        return centroids.get(Arrays.asList(distances).indexOf(Arrays.stream(distances).min(Double::compare).get()));
    }

    public static Double calcDistance(Double[] d1,Double[] d2){
        if (d1.length!=d2.length) throw new RuntimeException();
        Double distance = 0.;
        for (int i = 0; i < d1.length; i++) {
            distance+=Math.pow(d1[i]-d2[i],2);
        }
        return Math.sqrt(distance);
    }
    public void recalculateCentroids(){
        ArrayList<Map.Entry<Row,Double[]>> mapEntryList = new ArrayList<>(observationCentroidMap.entrySet()) ;
        for (int i = 0; i < centroids.size(); i++) {
            int finalI = i;
            ArrayList<Map.Entry<Row,Double[]>> obsevationsForCentroid =  mapEntryList.stream().filter(rowEntry -> Arrays.equals(rowEntry.getValue(), centroids.get(finalI))).collect(Collectors.toCollection(ArrayList::new));
            Double[] avg = new Double[obsevationsForCentroid.get(0).getKey().data.length];
            Arrays.fill(avg,0.);
            for (int i1 = 0; i1 < obsevationsForCentroid.size(); i1++) {
                for (int i2 = 0; i2 < obsevationsForCentroid.get(i).getValue().length; i2++) {
                    avg[i2]+=obsevationsForCentroid.get(i).getValue()[i2];
                }
            }
            for (int i1 = 0; i1 < avg.length; i1++) {
                avg[i1]/=obsevationsForCentroid.size();
            }
            centroids.set(i,avg);
        }
    }
    public HashMap<Double[],ArrayList<Row>> getResult(){
        HashMap<Double[],ArrayList<Row>> result = new HashMap<>();
        ArrayList<Map.Entry<Row,Double[]>> mapEntryList = new ArrayList<>(observationCentroidMap.entrySet()) ;
        for (int i = 0; i < centroids.size(); i++) {
            result.put(centroids.get(i),new ArrayList<>());
            int finalI = i;
            ArrayList<Map.Entry<Row, Double[]>> obsevationsForCentroid = mapEntryList.stream().filter(rowEntry -> Arrays.equals(rowEntry.getValue(), centroids.get(finalI))).collect(Collectors.toCollection(ArrayList::new));
            result.get(centroids.get(i)).addAll(obsevationsForCentroid.stream().map(Map.Entry::getKey).collect(Collectors.toCollection(ArrayList::new)));
        }
        return result;
        }

    public static void main(String[] args) {
        String pathToData = "Z:\\sem4\\nai\\Perceptron\\data\\iris.txt";


        ArrayList<Row> data = Row.importFromCsv(pathToData);
        Kmeans kmeans = new Kmeans(3,data);
        int changes = kmeans.assignNearestCentroids();
        while (changes!=0){
            System.out.print("|");
            kmeans.recalculateCentroids();
            changes=kmeans.assignNearestCentroids();
        }
        System.out.println();
        HashMap<Double[],ArrayList<Row>> result =kmeans.getResult();
        for (Map.Entry<Double[], ArrayList<Row>> arrayListEntry : result.entrySet()) {
            ArrayList<Row> observations = arrayListEntry.getValue();
            System.out.println(Arrays.toString(arrayListEntry.getKey()));
            for (Row observation : observations) {
                System.out.println("\t"+Arrays.toString(observation.data)+"\t"+observation.className+"\t\t"+calcDistance(observation.data,arrayListEntry.getKey()));
            }
        }
    }

}
