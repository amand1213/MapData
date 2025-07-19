public class Line {
    double latStart;
    double lonStart;
    
    double latEnd;
    double lonEnd;

    public Line() {

    }

    public Line (double latStart, double lonStart, double latEnd, double lonEnd) {
        this.latStart = latStart;
        this.lonStart = lonStart;

        this.latEnd = latEnd;
        this.lonEnd = lonEnd;
    }

    public void setStart(double latStart, double lonStart) {
        this.latStart = latStart;
        this.lonStart = lonStart;
    }

    public void setEnd(double latEnd, double lonEnd) {
        this.latEnd = latEnd;
        this.lonEnd = lonEnd;
    }

    public double[] getStart() {
        double[] point = new double[2];
        point[0] = latStart;
        point[1] = lonStart;

        return point;
    }

    public double[] getEnd() {
        double[] point = new double[2];
        point[0] = latEnd;
        point[1] = lonEnd;

        return point;
    }
}
