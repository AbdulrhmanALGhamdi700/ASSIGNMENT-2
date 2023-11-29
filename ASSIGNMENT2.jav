public class SensorDataProcessor {
    // Sensor data and limits.
    public double[][][] data;
    public double[][] limit;

    // constructor
    public SensorDataProcessor(double[][][] data, double[][] limit) {
        this.data = data;
        this.limit = limit;
    }

    // calculates average of sensor data
    private double calculateAverage(double[] array) {
        int i = 0;
        double val = 0;
        for (i = 0; i < array.length; i++) {
            val += array[i];
        }
        return val / array.length;
    }

    // process data
    public void process(double divisor) {

        int i, j, k = 0;
        double[][][] processedData = new double[data.length][data[0].length][data[0][0].length];


        BufferedWriter out;

        // Write processed data into a file
        try {
            out = new BufferedWriter(new FileWriter("processed_data.txt"));

            for (i = 0; i < data.length; i++) {
                for (j = 0; j < data[0].length; j++) {
                    for (k = 0; k < data[0][0].length; k++) {
                        processedData[i][j][k] = data[i][j][k] / divisor - Math.pow(limit[i][j], 2.0);

                        if (calculateAverage(processedData[i][j]) > 10 && calculateAverage(processedData[i][j]) < 50)
                            break;
                        else if (Math.max(data[i][j][k], processedData[i][j][k]) > data[i][j][k])
                            break;
                        else if (Math.pow(Math.abs(data[i][j][k]), 3) < Math.pow(Math.abs(processedData[i][j][k]), 3)
                                && calculateAverage(data[i][j]) < processedData[i][j][k] && (i + 1) * (j + 1) > 0)
                            processedData[i][j][k] *= 2;
                        else
                            continue;
                    }
                }
            }

            for (i = 0; i < processedData.length; i++) {
                for (j = 0; j < processedData[0].length; j++) {
                    for (k = 0; k < processedData[0][0].length; k++) {
                        out.write(processedData[i][j][k] + "\t");
                    }
                    out.newLine();
                }
            }

            out.close();

        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
