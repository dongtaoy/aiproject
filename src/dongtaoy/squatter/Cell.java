package dongtaoy.squatter;

import aiproj.squatter.Piece;

import java.util.*;


/**
 * Created by dongtao on 3/25/2015.
 */
public class Cell {

    public enum Direction {
        TOPLEFT,
        TOPMIDDLE,
        TOPRIGHT,
        MIDDLELEFT,
        MIDDLERIGHT,
        BOTTOMLEFT,
        BOTTOMMIDDLE,
        BOTTOMRIGHT
    }

    public enum CaptureType {
        BLACK_CAPTURED_BY_WHITE(Piece.BLACK, Piece.WHITE),
        EMPTY_CAPTURED_BY_WHITE(Piece.EMPTY, Piece.WHITE),
        WHITE_CAPTURED_BY_BLACK(Piece.WHITE, Piece.BLACK),
        EMPTY_CAPTURED_BY_BLACK(Piece.EMPTY, Piece.BLACK),
        NOT_CAPTURED(Piece.INVALID, Piece.INVALID);
        private final int piece;
        private final int capturedBy;

        CaptureType(int piece, int capturedBy) {
            this.piece = piece;
            this.capturedBy = capturedBy;
        }
    }

    public static CaptureType createCaptureType(int piece, int capturedBy) {
        if (capturedBy == Piece.WHITE)
            if (piece == Piece.EMPTY)
                return CaptureType.EMPTY_CAPTURED_BY_WHITE;
            else
                return CaptureType.BLACK_CAPTURED_BY_WHITE;
        else {
            if (piece == Piece.EMPTY)
                return CaptureType.EMPTY_CAPTURED_BY_BLACK;
            else
                return CaptureType.WHITE_CAPTURED_BY_BLACK;
        }
    }

//    public boolean isCapturedByWhite()


    private int row;
    private int col;
    private int piece;
    private CaptureType capturedBy;
    private Board board;



    // THIS MIGHT NOT WORK ONLY FOR TEST PURPOSE
    public Cell(int piece, Board board, int row, int col) {
        this.piece = piece;
        this.row = row;
        this.col = col;
        this.board = board;
        this.capturedBy = CaptureType.NOT_CAPTURED;
    }

    public Cell(int piece, Board board, int row, int col, CaptureType capturedBy){
        this.piece = piece;
        this.row = row;
        this.col = col;
        this.board = board;
        this.capturedBy = capturedBy;
    }

    public HashSet<Cell> getCellsBy(HashSet<Cell> visited, HashSet<Integer> validStatus, HashSet<Direction> validDirection) {
        HashMap<Direction, Cell> eightCells = this.getEightConnectedCells();
        HashSet<Cell> values = new HashSet<>();
        for (Direction direction : validDirection) {
            if (eightCells.containsKey(direction)) {
                Cell cell = eightCells.get(direction);
                if (validStatus.contains(cell.getPiece())) {
                    if (!visited.contains(cell)) {
                        values.add(cell);
                    }
                }
            }
        }
        return values;
    }

    /**
     * get 8 connected Cell
     *
     * @return cellHashMap
     */
    public HashMap<Direction, Cell> getEightConnectedCells() {
        HashMap<Direction, Cell> cellHashMap = new HashMap<>();
        int row = this.getRow();
        int col = this.getCol();
        int dimension = this.board.getDimension();
        Cell[][] cells = this.board.getCells();
        // TopLeft
        if (row - 1 >= 0 && col - 1 >= 0) {
            cellHashMap.put(Direction.TOPLEFT, cells[row - 1][col - 1]);
        }
        //TopMiddle
        if (row - 1 >= 0) {
            cellHashMap.put(Direction.TOPMIDDLE, cells[row - 1][col]);
        }
        //TopRight
        if (row - 1 >= 0 && col + 1 < dimension) {
            cellHashMap.put(Direction.TOPRIGHT, cells[row - 1][col + 1]);
        }
        //MiddleLeft
        if (col - 1 >= 0) {
            cellHashMap.put(Direction.MIDDLELEFT, cells[row][col - 1]);
        }
        //MiddleRight
        if (col + 1 < dimension) {
            cellHashMap.put(Direction.MIDDLERIGHT, cells[row][col + 1]);
        }
        //BottomLeft
        if (row + 1 < dimension && col - 1 >= 0) {
            cellHashMap.put(Direction.BOTTOMLEFT, cells[row + 1][col - 1]);
        }
        //BottomMiddle
        if (row + 1 < dimension) {
            cellHashMap.put(Direction.BOTTOMMIDDLE, cells[row + 1][col]);
        }
        //BottomRight
        if (row + 1 < dimension && col + 1 < dimension) {
            cellHashMap.put(Direction.BOTTOMRIGHT, cells[row + 1][col + 1]);
        }
        return cellHashMap;
    }

    public void capturedBy(int piece) {
        if (piece == Piece.BLACK) {
            if (this.getCapturedBy() == Cell.CaptureType.BLACK_CAPTURED_BY_WHITE) {
                this.setPiece(Piece.BLACK);
                this.setCapturedBy(Cell.CaptureType.NOT_CAPTURED);
            } else if (this.getCapturedBy() == Cell.CaptureType.EMPTY_CAPTURED_BY_WHITE) {
                this.setCapturedBy(Cell.CaptureType.EMPTY_CAPTURED_BY_BLACK);
                this.setPiece(Piece.DEAD);
            } else {
                this.setCapturedBy(Cell.createCaptureType(this.getPiece(), Piece.BLACK));
                this.setPiece(Piece.DEAD);
            }
        } else {
            if (this.getCapturedBy() == Cell.CaptureType.WHITE_CAPTURED_BY_BLACK) {
                this.setPiece(Piece.WHITE);
                this.setCapturedBy(Cell.CaptureType.NOT_CAPTURED);
            } else if (this.getCapturedBy() == Cell.CaptureType.EMPTY_CAPTURED_BY_BLACK) {
                this.setCapturedBy(Cell.CaptureType.EMPTY_CAPTURED_BY_WHITE);
                this.setPiece(Piece.DEAD);
            } else {
                this.setCapturedBy(Cell.createCaptureType(this.getPiece(), Piece.WHITE));
                this.setPiece(Piece.DEAD);
            }
        }
    }


    public boolean isOnBorder() {
        if (this.getRow() == 0 ||
                this.getRow() == (this.board.getDimension() - 1) ||
                this.getCol() == 0 ||
                this.getCol() == (this.board.getDimension() - 1))
            return true;
        return false;
    }

    public boolean isCaptured(int piece) {
        if (piece == Piece.BLACK)
            return this.isCapturedByBlack();
        return this.isCapturedByWhite();
    }

    public boolean isCapturedByWhite() {
        if (this.getCapturedBy() == CaptureType.BLACK_CAPTURED_BY_WHITE
                || this.getCapturedBy() == CaptureType.EMPTY_CAPTURED_BY_WHITE)
            return true;
        return false;
    }

    public boolean isCapturedByBlack() {
        if (this.getCapturedBy() == CaptureType.EMPTY_CAPTURED_BY_BLACK ||
                this.getCapturedBy() == CaptureType.WHITE_CAPTURED_BY_BLACK)
            return true;
        return false;
    }

    public boolean isEmpty() {
        return this.piece == Piece.EMPTY;
    }

    public String toString() {
        switch (this.piece) {
            case Piece.BLACK:
                return "B";

            case Piece.WHITE:
                return "W";

            case Piece.EMPTY:
                return "+";

            case Piece.DEAD:
                return "-";

            case Piece.INVALID:

                return "/";
            default:
                return "ERROR";
        }
    }


    public String getCoordinates() {
        return new StringBuilder()
                .append('(')
                .append(this.row)
                .append(',')
                .append(this.col)
                .append(')').toString();
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getPiece() {
        return piece;
    }

    public void setPiece(int piece) {
        this.piece = piece;
    }

    public CaptureType getCapturedBy() {
        return capturedBy;
    }

    public void setCapturedBy(CaptureType capturedBy) {
        this.capturedBy = capturedBy;
    }
}
