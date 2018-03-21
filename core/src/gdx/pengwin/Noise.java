package gdx.pengwin;

import static com.badlogic.gdx.math.MathUtils.random;


/* REFFERENCES
https://en.wikipedia.org/wiki/Perlin_noise
http://mrl.nyu.edu/~perlin/doc/oscar.html#noise
https://web.archive.org/web/20160201105053/http://freespace.virgin.net/hugo.elias/models/m_perlin.htm
https://mzucker.github.io/html/perlin-noise-math-faq.html
http://mrl.nyu.edu/~perlin/noise/
http://devmag.org.za/2009/04/25/perlin-noise/
Based off: https://github.com/theDazzler/Infinite-World/blob/master/src/com/devon/infiniteworld/NoiseMap.java
 */


public class Noise {
    double[] ardValues;
    int nWidth, nHeight;
    double[][] ardNoise;

    public Noise(int nWidth, int nHeight, int nStepSize) {
        this.nWidth = nWidth;
        this.nHeight = nHeight;
        this.ardNoise = new double[nHeight][nWidth];
        this.ardValues = new double[nWidth * nHeight];

        double dScale = 1.0 / nWidth;
        double dScaleMod = 1;
        for (int y = 0; y < nHeight; y += nStepSize) {
            for (int x = 0; x < nWidth; x += nStepSize) {
                ardValues[(x & (nWidth - 1)) + (y & (nHeight - 1))] = Math.random() * 2 - 1;
            }
        }

        do {
            int nHalfStep = nStepSize / 2;
            for (int y = 0; y < nHeight; y += nStepSize) {
                for (int x = 0; x < nWidth; x += nStepSize) {
                    double dA = sample(x, y);
                    double dB = sample(x + nStepSize, y);
                    double dC = sample(x, y + nStepSize);
                    double dD = sample(x + nStepSize, y + nStepSize);
                    double dE = (dA + dB + dC + dD) / 4.0 + (random.nextFloat() * 2 - 1) * nStepSize * dScale;
                    ardValues[((x + nHalfStep) & (nWidth - 1)) + ((y + nHalfStep) & (nHeight - 1))] = dE;
                }
            }
            for (int y = 0; y < nHeight; y += nStepSize) {
                for (int x = 0; x < nWidth; x += nStepSize) {
                    double dA = sample(x, y);
                    double dB = sample(x + nStepSize, y);
                    double dC = sample(x, y + nStepSize);
                    double dD = sample(x + nHalfStep, y + nHalfStep);
                    double dE = sample(x + nHalfStep, y - nHalfStep);
                    double dF = sample(x - nHalfStep, y + nHalfStep);
                    double dH = (dA + dB + dD + dE) / 4.0 + (Math.random() * 2 - 1) * nStepSize * dScale * 0.5;
                    double dG = (dA + dC + dD + dF) / 4.0 + (Math.random() * 2 - 1) * nStepSize * dScale * 0.5;
                    ardValues[((x + nHalfStep) & (nWidth - 1)) + (y & (nHeight - 1))] = dH;
                    ardValues[(x & (nWidth - 1)) + ((y + nHalfStep) & (nHeight - 1))] = dG;
                }
            }
            nStepSize /= 2;
            dScale *= (dScaleMod + 1);
            dScaleMod *= 0.3;
        } while (nStepSize > 1);
    }

    public static double[][] noiseMap(int nWidth, int nHeight) {
        double[][] ardMap = new double[nHeight][nWidth];

        Noise noise1 = new Noise(nWidth, nHeight, nWidth / 4);
        Noise noise2 = new Noise(nWidth, nHeight, nWidth / 8);

        for (int y = 0; y < nHeight; y++) {
            for (int x = 0; x < nWidth; x++) {
                double dVal = Math.abs(noise1.ardValues[x + (y * nWidth)] - noise2.ardValues[x + (y + nWidth)]) * 12 - 1.8;
                double dX = x / (nWidth - 1.0) * 2 - 1;
                double dY = y / (nHeight - 1.0) * 2 - 1;
                dX = (dX < 0) ? -dX : dX;
                dY = (dY < 0) ? -dY : dY;
                double dDist = (dX >= dY) ? dX : dY;
                dDist = Math.pow(Math.pow(dDist, 4), 3);
                dVal = dVal + 1 - (dDist * 20);
                ardMap[y][x] = dVal;
            }
        }

        return ardMap;
    }

    private double sample(int nX, int nY) {
        return ardValues[(nX & (nWidth - 1)) + (nY & (nHeight - 1))];
    }
}
