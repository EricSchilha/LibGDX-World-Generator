package gdx.pengwin;

public class Player {
    private int nX, nY;

    public Player() {
        this.nX = 0;
        this.nY = 0;
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
