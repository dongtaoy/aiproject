package dongtaoy.squatter;

import aiproj.squatter.Move;
import aiproj.squatter.Piece;
import aiproj.squatter.Player;
import com.sun.deploy.uitoolkit.impl.fx.ui.MixedCodeInSwing;

import java.awt.image.AreaAveragingScaleFilter;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by dongtao on 3/25/2015.
 */


public class Board {

    private int dimension;
    private Cell[][] cells;

    private boolean DEBUG = false;

    /**
     * Create Board object
     *
     * @param contents board layout in char[][] format
     */
    public Board(char[][] contents) {
        this.dimension = contents.length;
        this.cells = new Cell[this.dimension][this.dimension];
        // Store cell in cell object;
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                if (contents[i][j] == 'B')
                    cells[i][j] = new Cell(Piece.BLACK, i, j);
                else if (contents[i][j] == 'W')
                    cells[i][j] = new Cell(Piece.WHITE, i, j);
                else if (contents[i][j] == '+')
                    cells[i][j] = new Cell(Piece.EMPTY, i, j);
                else if (contents[i][j] == '-')
                    cells[i][j] = new Cell(Piece.DEAD, i, j);
    }


    public Board(int[][] contents) {
        this.dimension = contents.length;
        this.cells = new Cell[this.dimension][this.dimension];
        // Store cell in cell object;
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                cells[i][j] = new Cell(contents[i][j], i, j);

    }

    public Board(int dimension) {
        this.dimension = dimension;
        this.cells = new Cell[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                cells[i][j] = new Cell(Piece.EMPTY, i, j);
    }

    public Board(Board board, Cell cell, int piece) {
        this.dimension = board.getDimension();
        this.cells = new Cell[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                if (board.getCells()[i][j].equals(cell))
                    this.cells[i][j] = new Cell(piece, i, j);
                else
                    this.cells[i][j] = new Cell(board.getCells()[i][j].getPiece(), i, j);
            }
        }
    }

    /**
     * Check win for this board
     */
//    public void checkWin() {
//        int whiteCaptured = 0;
//        int blackCaptured = 0;
//        boolean isFinished = true;
//
//        for (int i = 0; i < this.dimension; i++) {
//            for (int j = 0; j < this.dimension; j++) {
//                // if cell is captured
//                if (cells[i][j].getPiece() == '-') {
//                    // if the previous cell is captured
//                    if (cells[i][j - 1].getPiece() != '-')
//                        cells[i][j].setCapturedBy(cells[i][j - 1].getPiece());
//                    else
//                        cells[i][j].setCapturedBy(cells[i][j - 1].getCapturedBy());
//
//                    // increment counter
//                    if (cells[i][j].getCapturedBy() == 'W')
//                        whiteCaptured++;
//                    else
//                        blackCaptured++;
//                } else if (cells[i][j].getPiece() == '+') {
//                    // if there is any '+' in cell, game is not finished
//                    isFinished = false;
//                }
//            }
//        }
//        // if finished print who wins or a draw
//        if (isFinished)
//            //if(DEBUG){System.out.println(blackCaptured > whiteCaptured ? "Black" : (blackCaptured == whiteCaptured ? "Draw" : "White"));
//        else
//            //if(DEBUG){System.out.println("None");
//        //if(DEBUG){System.out.println(whiteCaptured);
//        //if(DEBUG){System.out.println(blackCaptured);
//
//    }

    /**
     * toString function for Board
     *
     * @return String representation of a board
     */
    public String boardToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Cell[] row : this.cells) {
            for (Cell c : row) {
                stringBuilder.append(c);
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public String statusToString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Cell[] row : this.cells) {
            for (Cell c : row) {
                stringBuilder.append(c.statusToString());
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    public int placeCell(Move move) {
        cells[move.Row][move.Col] = new Cell(move.P, move.Row, move.Col);
        return 1;
    }

    public void findCycle() {
        for (int i = 1; i < this.dimension - 1; i++) {
            for (int j = 1; j < this.dimension - 1; j++) {

                Cell currentCell = this.cells[i][j];
                if (currentCell.getStatus() != Piece.DEAD) {
                    Integer[] array = new Integer[]{Piece.WHITE, Piece.BLACK, Piece.DEAD, Piece.EMPTY};
                    HashSet<Integer> validList = new HashSet<>(Arrays.asList(array));
                    HashSet<Cell> visited = new HashSet<>();

                    if (DEBUG) {
                        System.out.printf("\n\ncurrent: (%d, %d)\n", currentCell.getRow(), currentCell.getCol());
                    }

                    if (currentCell.getPiece() == Piece.EMPTY) {
                        ArrayList<Cell> surroundedCells = getSurroundedCell(this.cells[i][j], new HashSet<Cell>(), validList);
                        boolean isBlack = false, isWhite = false;
                        if (DEBUG) {
                            System.out.println("Empty cell surrounded by: " + surroundedCells);
                        }
                        for (Cell cell : surroundedCells) {
                            if (cell.getPiece() == Piece.BLACK)
                                isBlack = true;
                            if (cell.getPiece() == Piece.WHITE)
                                isWhite = true;
                        }
                        if (!(isBlack && isWhite)) {
                            if (isBlack) {
                                validList.remove(Piece.BLACK);
                            }
                            if (isWhite) {
                                validList.remove(Piece.WHITE);
                            }
                            if (!findPathToBorder(visited, currentCell, validList)) {
                                for (Cell cell : visited) {
                                    cell.setStatus(Piece.DEAD);
                                }
                            }

                        }

                    } else {
                        switch (this.cells[i][j].getPiece()) {
                            case Piece.BLACK:
                                validList.remove(Piece.WHITE);
                                break;
                            case Piece.WHITE:
                                validList.remove(Piece.BLACK);
                                break;
                        }
                        if (!findPathToBorder(visited, currentCell, validList)) {
                            for (Cell cell : visited) {
                                cell.setStatus(Piece.DEAD);
                            }
                        }

                    }
                }
            }
        }

    }

    private boolean findPathToBorder(HashSet<Cell> visited, Cell current, HashSet<Integer> validList) {
        if (isOnBorder(current))
            return true;
        visited.add(current);
        ArrayList<Cell> surroundedCells = this.getSurroundedCell(current, visited, validList);
        if (DEBUG) {
            System.out.println("in findPathToBorder");
            System.out.println("\tcurrent" + current.getCoordinates() + ": " + current);
        }

        for (Cell cell : surroundedCells) {
            if (findPathToBorder(visited, cell, validList))
                return true;
        }
        return false;
    }

    private boolean isOnBorder(Collection<Cell> cells) {
        for (Cell cell : cells) {
            if (isOnBorder(cell))
                return true;
        }
        return false;
    }

    private boolean isOnBorder(Cell cell) {
        if (cell.getRow() == 0 ||
                cell.getRow() == (this.dimension - 1) ||
                cell.getCol() == 0 ||
                cell.getCol() == (this.dimension - 1))
            return true;
        return false;
    }


    private ArrayList<Cell> getSurroundedCell(Cell cell, HashSet<Cell> visited, HashSet<Integer> validList) {
        ArrayList<Cell> cells = new ArrayList<>();
        int row = cell.getRow();
        int col = cell.getCol();

        //TOP
        if ((row - 1) >= 0)
            if (validList.contains(this.cells[row - 1][col].getPiece()))
                if (!visited.contains(this.cells[row - 1][col]))
                    cells.add(this.cells[row - 1][col]);
        //BOTTOM
        if ((row + 1) < this.dimension)
            if (validList.contains(this.cells[row + 1][col].getPiece()))
                if (!visited.contains(this.cells[row + 1][col]))
                    cells.add(this.cells[row + 1][col]);
        //LEFT
        if ((col - 1) >= 0)
            if (validList.contains(this.cells[row][col - 1].getPiece()))
                if (!visited.contains(this.cells[row][col - 1]))
                    cells.add(this.cells[row][col - 1]);

        //RIGHT
        if ((col + 1) < this.dimension)
            if (validList.contains(this.cells[row][col + 1].getPiece()))
                if (!visited.contains(this.cells[row][col + 1]))
                    cells.add(this.cells[row][col + 1]);


        return cells;
    }


    public ArrayList<Cell> getEmptyCell() {
        ArrayList<Cell> value = new ArrayList<>();
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                if (cells[i][j].isEmpty())
                    value.add(cells[i][j]);
        return value;
    }

    public Board verticalFlip() {
        int[][] contents = new int[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                contents[i][j] = this.cells[i][this.dimension - j - 1].getPiece();
        return new Board(contents);
    }

    public Board horizontalFlip() {
        int[][] contents = new int[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                contents[i][j] = this.cells[this.dimension - i - 1][j].getPiece();
        return new Board(contents);
    }

    public Board transposeDown() {
        int[][] contents = new int[this.dimension][this.dimension];
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                contents[i][j] = this.cells[j][i].getPiece();
        return new Board(contents);
    }

    public Board transposeUp() {
        return this.transposeDown().horizontalFlip().verticalFlip();
    }

    public ArrayList<Cell> getAvaliableCells() {
        ArrayList<Cell> values = this.getEmptyCell();
        ArrayList<Cell> temp = new ArrayList<>();
        int x;
        if (this.dimension % 2 == 0) {
            x = this.dimension / 2;
        } else {
            x = this.dimension / 2 + 1;
        }
        if (this.equals(this.horizontalFlip())) {
            temp.clear();
            for (int i = 0; i < (x); i++)
                for (int j = 0; j < this.dimension; j++)
                    temp.add(this.cells[i][j]);
            values.retainAll(temp);
        }
        if (this.equals(this.verticalFlip())) {
            temp.clear();
            for (int i = 0; i < (this.dimension); i++)
                for (int j = 0; j < (x); j++)
                    temp.add(this.cells[i][j]);
            values.retainAll(temp);
        }
        if (this.equals(this.transposeDown())) {
            temp.clear();
            for (int i = 0; i < (this.dimension); i++)
                for (int j = 0; j <= i; j++)
                    temp.add(this.cells[i][j]);
            values.retainAll(temp);
        }
        if (this.equals(this.transposeUp())) {
            temp.clear();
            for (int i = 0; i < (this.dimension); i++)
                for (int j = 0; j <= (this.dimension - i - 1); j++)
                    temp.add(this.cells[i][j]);
            values.retainAll(temp);
        }
        return values;
    }


    public boolean equals(Object object) {
        Board board = (Board) object;
        for (int i = 0; i < this.dimension; i++)
            for (int j = 0; j < this.dimension; j++)
                if (this.cells[i][j].getPiece() != board.getCells()[i][j].getPiece())
                    return false;
        return true;
    }

    public int evaluate(Dongtaoy player) {
        int ownsideConnect = 0;
        int opponentConnect = 0;
        int opposite = player.getOpponentPiece();
        int border = 0;
        for (int i = 0; i < this.dimension; i++) {
            for (int j = 0; j < this.dimension; j++) {
                Cell currentCell = this.cells[i][j];

                //Find empty border cell
                if( (i == 0) || (i == this.dimension - 1) || (j == 0) || (j == this.dimension))
                    if (currentCell.getPiece() == Piece.EMPTY)
                        border++;


                //Find the connectivity of each cell
                HashMap<String, Integer> cellSet = this.getConnectedCell(currentCell);
                if(player.getPiece() == currentCell.getPiece()){
                    for(int value: cellSet.values())
                        if (value == Piece.EMPTY)
                            ownsideConnect++;
                }
//                    System.out.println();
            }
        }

        return 1;
    }

    private HashMap<String, Integer> getConnectedCell(Cell currentCell) {
        HashMap<String, Integer> cellSet = new HashMap<>();
        int row = currentCell.getRow();
        int col = currentCell.getCol();

        // TopLeft
        if (row - 1 >= 0 && col - 1 >= 0) {
            cellSet.put("TopLeft", this.cells[row - 1][col - 1].getPiece());
        }
        //TopMiddle
        if (row - 1 >= 0) {
            cellSet.put("TopMiddle", this.cells[row - 1][col].getPiece());
        }
        //TopRight
        if (row - 1 >= 0 && col + 1 < this.dimension) {
            cellSet.put("TopRight", this.cells[row - 1][col + 1].getPiece());
        }
        //MiddleLeft
        if (col - 1 >= 0) {
            cellSet.put("MiddleLeft", this.cells[row][col - 1].getPiece());
        }
        //MiddleRight
        if (col + 1 < this.dimension) {
            cellSet.put("MiddleRight", this.cells[row][col + 1].getPiece());
        }
        //BottomLeft
        if (row + 1 < this.dimension && col - 1 >= 0) {
            cellSet.put("BottomLeft", this.cells[row + 1][col - 1].getPiece());
        }
        //BottomMiddle
        if (row + 1 < this.dimension) {
            cellSet.put("BottomMiddle", this.cells[row + 1][col].getPiece());
        }
        //BottomRight
        if (row + 1 < this.dimension && col + 1 < this.dimension) {
            cellSet.put("BottomRight", this.cells[row + 1][col + 1].getPiece());
        }
        return cellSet;
    }


//        for (int i=0; i < this.dimension;i++){
//            for (int j=0; j<this.dimension;j++){
//                if(this.getCells()[i][j].getPiece() == Piece.EMPTY){
//                    if(DEBUG){System.out.print(this.getCells()[i][j]);
//                }else{
//                    if(DEBUG){System.out.print(" ");
//                }
//            }
//            if(DEBUG){System.out.print("\n");
//        }
    // Number of Piece with your player that connected with empty spot -> Increase Evaluation Value
    // Number of Opponent piece associated with the empty spot -> Decrease Evaluation Value
    // Boarder with less possible connection for next move -> Decrease Evaluation Value
    // Occupy a piece on the empty piece after check with findLoop(), find the available "-" opportunity ->Increase Evaluation Value
    //
//        return 1;
//    }


    public int getDimension() {
        return dimension;
    }

    public Cell[][] getCells() {
        return cells;
    }
}
