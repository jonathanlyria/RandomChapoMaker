import java.awt.image.BufferedImage;
import java.util.Random;

public final class RandomChapatiMaker extends PerlinNoise2D {

    private static double time = 0;

    private static BufferedImage image = new BufferedImage(MainWindow.WIDTH, MainWindow.HEIGHT, BufferedImage.TYPE_INT_RGB);

    public static BufferedImage getChapatiBurn() {

        time += 100;

        int baseColor = 0xEED39F;
        int transitionColor = 0xD9A65E;
        int superBurnSpotColor = 0x373630;
        int burnSpotColor = 0x7A4A22;
        int whiteSpots = 0xfbd89e;

        int centerX = MainWindow.WIDTH / 2;
        int centerY = MainWindow.HEIGHT / 2;
        double maxRadius = Math.min(MainWindow.WIDTH, MainWindow.HEIGHT) / 2.0;

        for (int y = 0; y < MainWindow.HEIGHT; y++) {
            for (int x = 0; x < MainWindow.WIDTH; x++) {

                double dx = (double) (x - centerX) / maxRadius;
                double dy = (double) (y - centerY) / maxRadius;
                double distance = Math.sqrt(dx * dx + dy * dy);

                int finalColor;

                if (distance > 1) {
                    finalColor = 0x00000000;
                } else {

                    double burnFrequency = 5.0;
                    double burnNoise = noise(burnFrequency * dx + time, burnFrequency * dy + time);
                    burnNoise = (burnNoise + 1) / 2;

                    if (burnNoise > 0.79) {
                        finalColor = superBurnSpotColor;
                    } else if (burnNoise > 0.75) {
                        finalColor = burnSpotColor;
                    } else if (burnNoise > 0.73) {
                        finalColor = transitionColor;
                    } else if (burnNoise > 0.65) {
                        finalColor = whiteSpots;
                    } else {
                        finalColor = baseColor;
                    }
                }
                image.setRGB(x, y, finalColor);
            }
        }
        return image;
    }
}