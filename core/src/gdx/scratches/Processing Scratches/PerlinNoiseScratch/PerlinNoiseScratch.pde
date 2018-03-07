int H, W;
int nScale;
int nSeed;
int[] arnPerm_half = {151, 160, 137, 91, 90, 15, 
  131, 13, 201, 95, 96, 53, 194, 233, 7, 225, 140, 36, 103, 30, 69, 142, 8, 99, 37, 240, 21, 10, 23, 
  190, 6, 148, 247, 120, 234, 75, 0, 26, 197, 62, 94, 252, 219, 203, 117, 35, 11, 32, 57, 177, 33, 
  88, 237, 149, 56, 87, 174, 20, 125, 136, 171, 168, 68, 175, 74, 165, 71, 134, 139, 48, 27, 166, 
  77, 146, 158, 231, 83, 111, 229, 122, 60, 211, 133, 230, 220, 105, 92, 41, 55, 46, 245, 40, 244, 
  102, 143, 54, 65, 25, 63, 161, 1, 216, 80, 73, 209, 76, 132, 187, 208, 89, 18, 169, 200, 196, 
  135, 130, 116, 188, 159, 86, 164, 100, 109, 198, 173, 186, 3, 64, 52, 217, 226, 250, 124, 123, 
  5, 202, 38, 147, 118, 126, 255, 82, 85, 212, 207, 206, 59, 227, 47, 16, 58, 17, 182, 189, 28, 42, 
  223, 183, 170, 213, 119, 248, 152, 2, 44, 154, 163, 70, 221, 153, 101, 155, 167, 43, 172, 9, 
  129, 22, 39, 253, 19, 98, 108, 110, 79, 113, 224, 232, 178, 185, 112, 104, 218, 246, 97, 228, 
  251, 34, 242, 193, 238, 210, 144, 12, 191, 179, 162, 241, 81, 51, 145, 235, 249, 14, 239, 107, 
  49, 192, 214, 31, 181, 199, 106, 157, 184, 84, 204, 176, 115, 121, 50, 45, 127, 4, 150, 254, 
  138, 236, 205, 93, 222, 114, 67, 29, 24, 72, 243, 141, 128, 195, 78, 66, 215, 61, 156, 180
};

int[] arnPerm = new int[512];
double[][] ardNoise;// = new float[][];


void setup() {
  size(800, 600);
  nScale = (width/40);
  W = width / nScale;
  H = height / nScale;
  for (int i = 0; i < 512; i++) {
    arnPerm[i] = arnPerm_half[i%256];
  }
  nSeed = (int)random(2147483647);
  ardNoise = new double[H][W];
  for (int y = 0; y < H; y++) {
    for (int x = 0; x < W; x++) {
      ardNoise[y][x] = perlinNoise(x, y, nSeed);
    }
  }
  noLoop();
}

void draw() {
  for (int y = 0; y < H; y++) {
    for (int x = 0; x < W; x++) {
      rect(nScale*x, nScale*y, nScale, nScale);
    }
  }
  println(noise(0, 0));
}

double linearInterpolation(double dX1, double dX2, double dY1, double dY2, double dXTarget) {
  return dY1 + ((dXTarget - dX1) * (dY2 - dY1) / (dX2 - dX1));
}

double perlinNoise(int nX, int nY, int nSeed) {
  double dX = nX*0.985384, dY = nY*0.985384; //I just chose a random decimal
  PVector vCorner1, vCorner2, vCorner3, vCorner4; //(0,0), (0,1), (1,0), (1,1)
  PVector vGradient1, vGradient2, vGradient3, vGradient4;
  vCorner1 = new PVector((float)(dX-Math.floor(dX)), (float)(dY-Math.floor(dY)));
  vCorner2 = new PVector((float)(dX-Math.floor(dX)), (float)(dY-Math.ceil(dY)));
  vCorner3 = new PVector((float)(dX-Math.ceil(dX)), (float)(dY-Math.floor(dY)));
  vCorner4 = new PVector((float)(dX-Math.ceil(dX)), (float)(dY-Math.ceil(dY)));
  vGradient1 = new PVector(-1,0);
  vGradient1 = new PVector(0,1);
  vGradient1 = new PVector(1,0);
  vGradient1 = new PVector(0,-1);
  nX%=256;
  nY%=256;
  double dInfluence1, dInfluence2, dInfluence3, dInfluence4;
  dInfluence1 = vCorner1.mag() * vCorner2.mag() * cos(ANGLE);











  return 0;
}

double calcAngle(PVector v1, PVector v2){
  //TODO
  return ANGLE_BETWEEN(v1, v2);
}