package cs2410.model;

/**
 * This is a game button that is not a mine
 * @version 1.0
 * @author Garrett Jennings A01491831
 */
public class NotMine extends GameBtn {
    /**
     * count of mines that are neighbors
     */
    int mineCount;

    /**
     * constructor nothing special
     */
    NotMine(){

    }

    /**
     * returns the mine count
     * @return
     */
    public int getMineCount() {
        return mineCount;
    }


    /**
     * sets minecount
     * @param mineCount
     */
    public void setMineCount(int mineCount) {
        this.mineCount = mineCount;
    }


}
