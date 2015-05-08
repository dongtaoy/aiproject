package dongtaoy.squatter;

import aiproj.squatter.Piece;

/**
 * Created by dongtao on 3/25/2015.
 */
public class Cell {
    private char content;
    private char capturedBy;
    private int row;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    private int col;

    public Cell(char content, int row, int col){
        this.content = content;
        this.row = row;
        this.col = col;
    }

    public char getContent() {
        return content;
    }

    public void setContent(char content) {
        this.content = content;
    }

    public char getCapturedBy() {
        return capturedBy;
    }

    public void setCapturedBy(char capturedBy) {
        this.capturedBy = capturedBy;
    }

    public boolean isEmpty(){
        return content == Character.forDigit(Piece.EMPTY, 10);
    }

    public String toString() {
        return row + ", " + col + ": " + content;
    }
}
