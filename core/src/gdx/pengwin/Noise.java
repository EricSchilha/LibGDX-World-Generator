package gdx.pengwin;

import java.util.Random;


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
    /*double[] ardValues;
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
    }*/


    //From Processing
    static final int PERLIN_YWRAPB = 4;
    static final int PERLIN_YWRAP = 16;
    static final int PERLIN_ZWRAPB = 8;
    static final int PERLIN_ZWRAP = 256;
    static final int PERLIN_SIZE = 4095;
    int perlin_octaves = 4;
    float perlin_amp_falloff = 0.5F;
    int perlin_TWOPI;
    int perlin_PI;
    float[] perlin_cosTable;
    float[] perlin;
    Random perlinRandom;
    protected static final float[] cosLUT = new float[720];

    static {
        for (int i = 0; i < 720; ++i) {
            cosLUT[i] = (float) Math.cos((double) ((float) i * 0.017453292F * 0.5F));
        }

    }

    public float noise(float x) {
        return this.noise(x, 0.0F, 0.0F);
    }

    public float noise(float x, float y) {
        return this.noise(x, y, 0.0F);
    }

    public float noise(float x, float y, float z) {
        int xi;
        if (this.perlin == null) {
            if (this.perlinRandom == null) {
                this.perlinRandom = new Random();
            }

            this.perlin = new float[4096];

            for (xi = 0; xi < 4096; ++xi) {
                this.perlin[xi] = this.perlinRandom.nextFloat();
            }

            this.perlin_cosTable = this.cosLUT;
            this.perlin_TWOPI = this.perlin_PI = 720;
            this.perlin_PI >>= 1;
        }

        if (x < 0.0F) {
            x = -x;
        }

        if (y < 0.0F) {
            y = -y;
        }

        if (z < 0.0F) {
            z = -z;
        }

        xi = (int) x;
        int yi = (int) y;
        int zi = (int) z;
        float xf = x - (float) xi;
        float yf = y - (float) yi;
        float zf = z - (float) zi;
        float r = 0.0F;
        float ampl = 0.5F;

        for (int i = 0; i < this.perlin_octaves; ++i) {
            int of = xi + (yi << 4) + (zi << 8);
            float rxf = this.noise_fsc(xf);
            float ryf = this.noise_fsc(yf);
            float n1 = this.perlin[of & 4095];
            n1 += rxf * (this.perlin[of + 1 & 4095] - n1);
            float n2 = this.perlin[of + 16 & 4095];
            n2 += rxf * (this.perlin[of + 16 + 1 & 4095] - n2);
            n1 += ryf * (n2 - n1);
            of += 256;
            n2 = this.perlin[of & 4095];
            n2 += rxf * (this.perlin[of + 1 & 4095] - n2);
            float n3 = this.perlin[of + 16 & 4095];
            n3 += rxf * (this.perlin[of + 16 + 1 & 4095] - n3);
            n2 += ryf * (n3 - n2);
            n1 += this.noise_fsc(zf) * (n2 - n1);
            r += n1 * ampl;
            ampl *= this.perlin_amp_falloff;
            xi <<= 1;
            xf *= 2.0F;
            yi <<= 1;
            yf *= 2.0F;
            zi <<= 1;
            zf *= 2.0F;
            if (xf >= 1.0F) {
                ++xi;
                --xf;
            }

            if (yf >= 1.0F) {
                ++yi;
                --yf;
            }

            if (zf >= 1.0F) {
                ++zi;
                --zf;
            }
        }

        return r;
    }

    private float noise_fsc(float i) {
        return 0.5F * (1.0F - this.perlin_cosTable[(int) (i * (float) this.perlin_PI) % this.perlin_TWOPI]);
    }
}
