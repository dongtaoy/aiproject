package dongtaoy.squatter;

import aiproj.squatter.Piece;

import java.util.Map;

/**
 * Created by dongtao on 3/25/2015.
 */
public class Cell {


    private int row;
    private int col;
    private int piece;

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }


    public Cell(int piece, int row, int col){
        this.piece = piece;
        this.row = row;
        this.col = col;
    }


    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public boolean isEmpty(){
        return this.piece == Piece.EMPTY;
    }

    public String toString() {
        switch (this.piece){
            case Piece.BLACK :
                return "B";

            case Piece.WHITE :
                return "W";

            case Piece.EMPTY :
                return "+";

            case Piece.DEAD :
                return "-";

            case Piece.INVALID:
                return "/";
            default:
                return "ERROR";
        }
    }
}
