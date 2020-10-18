package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {

    private double mean;
    private double stddev;
    private double confidenceLow;
    private double confidenceHigh;
    private static final double LEVEL = 1.96;

    private double runExperiment(Percolation experiment, int N) {
        while (!experiment.percolates()) {
            int row = StdRandom.uniform(N);
            int col = StdRandom.uniform(N);
            experiment.open(row, col);
        }
        return (double) experiment.numberOfOpenSites() / (N * N);
    }

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException("Non-positive input");
        }
        double[] results = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation newExperiment = pf.make(N);
            results[i] = runExperiment(newExperiment, N);
            System.out.println("Run experment #" + (i + 1));
        }

        mean = StdStats.mean(results);
        stddev = results.length > 1 ? StdStats.stddev(results) : Double.NaN;
        confidenceLow = mean - LEVEL * stddev / Math.sqrt(T);
        confidenceHigh = mean + LEVEL * stddev / Math.sqrt(T);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLow() {
        return confidenceLow;
    }

    public double confidenceHigh() {
        return confidenceHigh;
    }
}
