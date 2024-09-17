import java.awt.image.BufferedImage;
import java.util.Random;

public class PerlinNoise2D {

    // algorithm from fataho, with some variation to make it more random
    protected static double time = 0;
    protected static BufferedImage image = new BufferedImage(MainWindow.WIDTH, MainWindow.HEIGHT, BufferedImage.TYPE_INT_RGB);
    public static BufferedImage getNoiseImage(){
        time += 0.01;
        for(int y = 0; y < MainWindow.HEIGHT; y++){
            for(int x = 0; x < MainWindow.WIDTH; x++){
                double dx = (double) x / MainWindow.HEIGHT;
                double dy = (double) y / MainWindow.HEIGHT;
                int frequency = 6;
                double noise = noise((dx * frequency) + time, (dy * frequency) + time);
                noise = (noise - 1) / 2;
                int b = (int)(noise * 0xFF);
                int g = b * 0x100;
                int r = b * 0x10000;
                int finalValue = r;
                image.setRGB(x, y, finalValue);
            }
        }
        return image;
    }

    protected static double noise(double x, double y){
        int xi = (int) Math.floor(x) & 255;
        int yi = (int) Math.floor(y) & 255;
        int g1 = p[p[xi] + yi];
        int g2 = p[p[xi + 1] + yi];
        int g3 = p[p[xi] + yi + 1];
        int g4 = p[p[xi + 1] + yi + 1];

        double xf = x - Math.floor(x);
        double yf = y - Math.floor(y);

        double d1 = grad(g1, xf, yf);
        double d2 = grad(g2, xf - 1, yf);
        double d3 = grad(g3, xf, yf - 1);
        double d4 = grad(g4, xf - 1, yf - 1);

        double u = fade(xf);
        double v = fade(yf);

        double x1Inter = lerp(u, d1, d2);
        double x2Inter = lerp(u, d3, d4);
        double yInter = lerp(v, x1Inter, x2Inter);

        return yInter;

    }

    protected static double lerp(double amount, double left, double right) {
        return ((1 - amount) * left + amount * right);
    }

    protected static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }

    protected static double grad(int hash, double x, double y){
        switch(hash & 3){
            case 0: return x + y;
            case 1: return -x + y;
            case 2: return x - y;
            case 3: return -x - y;
            default: return 0;
        }
    }
    static final int p[] = new int[512];

    static {
        int[] permutation = new int[256];
        for (int i = 0; i < 256; i++) {
            permutation[i] = i;
        }
        Random random = new Random();
        for (int i = 255; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int temp = permutation[i];
            permutation[i] = permutation[index];
            permutation[index] = temp;
        }
        for (int i = 0; i < 256; i++) {
            p[i] = permutation[i];
            p[256 + i] = permutation[i];
        }
    }

}