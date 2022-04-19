import java.util.stream.*;

class QuadraticSum {
    public static long rangeQuadraticSum(int fromIncl, int toExcl) {
        return Stream.iterate(fromIncl, e -> e + 1).limit(10).filter(e -> e < toExcl).distinct().mapToInt(e -> e * e).sum();
    }

    public static void main(String[] args) {
        System.out.println(rangeQuadraticSum(5, 6));
    }
}