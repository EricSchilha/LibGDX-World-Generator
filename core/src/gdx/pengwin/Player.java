package gdx.pengwin;

public class Player {
    private int nX, nY;
    private int nMinDivisor = 4096;

    public Player() {
        this.nX = (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2);
        this.nY = (int) (Math.random() * Integer.MAX_VALUE / nMinDivisor + Integer.MAX_VALUE / nMinDivisor / 2);
    }

    public int getX() {
        return nX;
    }

    public int getY() {
        return nY;
    }

    public void setX(int nX) {
        this.nX = nX;
    }

    public void setY(int nY) {
        this.nY = nY;
    }
}
