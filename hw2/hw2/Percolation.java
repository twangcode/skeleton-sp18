package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private class Site {
        private int row;
        private int col;
        private boolean blocked;

        private Site(int row, int col, boolean blocked) {
            this.row = row;
            this.col = col;
            this.blocked = blocked;
        }

        private void open() {
            blocked = false;
        }

        private boolean isOpen() {
            return (!blocked);
        }
    }

    private Site[][] sites;
    private int N;
    private WeightedQuickUnionUF ds;
    private WeightedQuickUnionUF backwash;
    private int numOpenSites;

    private int position(int row, int col) {
        return row * N + col + 1;
    }

    private Site[] getNeighbors(Site site) {
        Site[] neighbors = new Site[4];
        neighbors[0] = site.row > 0 ? sites[site.row - 1][site.col] : null;
        neighbors[1] = site.row < N - 1 ? sites[site.row + 1][site.col] : null;
        neighbors[2] = site.col > 0 ? sites[site.row][site.col - 1] : null;
        neighbors[3] = site.col < N - 1 ? sites[site.row][site.col + 1] : null;
        return neighbors;
    }

    public Percolation(int N) {
        // Throw a java.lang.IllegalArgumentException if N ≤ 0 as HW2 required.
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException("Non-positive number of sites");
        }
        this.N = N;
        // set all sits as blocked
        sites = new Site[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sites[i][j] = new Site(i, j, true);
            }
        }
        ds = new WeightedQuickUnionUF(N * N + 2);
        backwash = new WeightedQuickUnionUF(N * N + 2);
        numOpenSites = 0;
    }

    public void open(int row, int col) {
        if (row < 0 || col < 0 || row > N - 1 || col > N - 1) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }

        if (!isOpen(row, col)) {
            sites[row][col].open();
            numOpenSites++;
            if (row == 0) {
                ds.union(position(row, col), 0);
                backwash.union(position(row, col), 0);
            }
            if (row == N - 1) {
                backwash.union(position(row, col), N * N + 1);
            }
            Site[] neighbors = getNeighbors(sites[row][col]);
            for (int i = 0; i < neighbors.length; i++) {
                if (neighbors[i] != null && neighbors[i].isOpen()) {
                    ds.union(position(row, col), position(neighbors[i].row, neighbors[i].col));
                    backwash.union(position(row, col), position(neighbors[i].row, neighbors[i].col));
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row > N - 1 || col > N - 1) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
        return (sites[row][col].isOpen());
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || col < 0 || row > N - 1 || col > N - 1) {
            throw new IndexOutOfBoundsException("Index out of bounds!");
        }
        return (ds.connected(position(row, col), 0));
    }

    public int numberOfOpenSites() {
        return numOpenSites;
    }

    public boolean percolates() {
        return (backwash.connected(0, N * N + 1));
    }

    public static void main(String[] args) {
        Percolation test = new Percolation(100);
    }
}
