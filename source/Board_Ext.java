public class Board_Ext {
    // The following five constants were defined in the starter code
    private static final char FREE    = '.';
    private static final char INVALID = ' ';
    private static final char FOX     = '*';
    private static final char GOOSE   = 'o';
    //easyIn2 to receive the input from the reader
    EasyIn2 reader = new EasyIn2();
    //declaring variable used in the board class
    private int boardsize;

    private char[][] board;
    private int xFox;
    private int yFox;
    private int [] coordinateArray;
    // Default constructor and created 2d array which acts as a board
    public Board_Ext() {
        System.out.println("Please enter the size of the board you want");
        boardsize = reader.getInt();
        int thirdOfBoard = boardsize / 3;

        board = new char[boardsize][boardsize];

        // Clear all playable fields and assign FREE first
        for(int x = 0; x< boardsize; x++)
            for(int y = 0; y< boardsize; y++)
                board[x][y] = FREE;

        // Assign the invalid board area although the iteration does partially cover
        // the area which is meant to have geese or meant to be free
        for(int x = 0; x < boardsize; x++)
            for (int y = 0; y < thirdOfBoard; y++)
                board[x][y] = INVALID;

        for(int x = 0; x < boardsize; x++)
            for(int y = boardsize - thirdOfBoard; y < boardsize; y++)
                board[x][y] = INVALID;

        //retrieve the invalid area which are supposed to be free
        for(int x = thirdOfBoard; x < (boardsize - thirdOfBoard); x++)
            for(int y = boardsize - thirdOfBoard; y < boardsize; y++)
                board[x][y] = FREE;

        //Put geese on the board
        //First fill the geese on the top 6 boxes
        for(int x = thirdOfBoard; x < boardsize - thirdOfBoard; x++)
            for(int y = 0; y < thirdOfBoard; y++)
                board[x][y] = GOOSE;

        //Then fill the whole of 3rd and 4th row (2nd and 3rd as the index
        //starts from 0) although this will cover some areas which are meant
        //to be free
        for(int y = thirdOfBoard; y < thirdOfBoard + 2; y++)
            for(int x = 0; x < boardsize; x++)
                board[x][y] = GOOSE;

        //Then retrieve the goose area which are supposed to be free
        for(int y = thirdOfBoard + 1; y < boardsize - thirdOfBoard - 1; y++)
            for(int x = (thirdOfBoard); x < boardsize - thirdOfBoard; x++)
                board[x][y] = FREE;

        // Put a single fox in the middle
        board[boardsize / 2][(boardsize - thirdOfBoard)] = FOX;
    }
    //conditional to check the move for the goose is valid
    public boolean checkValidGeese(int x1, int y1, int x2, int y2){
        //if the starting set of coordinate is out of bounds
        if (x1 < 0 || x1 >= boardsize || y1 < 0 || y1 >= boardsize){
            return false;
        }
        //if the set of coordinate the goose moves to is out of bounds
        if (x2 < 0 || x2 >= boardsize || y2 < 0 || y2 >= boardsize){
            return false;
        }
        //if the the goose moves more than one coordinate in each direction
        if ((x1 - x2 > 1 || x1 - x2 < -1) || (y1 - y2 > 1 || y1 - y2 < -1)){
            return false;
        }
        //if the starting set of coordinate is not a goose or the
        //set of the coordinate the goose moves to is not free
        if (board[x1][y1] != GOOSE || board[x2][y2] != FREE){
            return false;
        }
        return true;
    }
    //conditional to check the move for the fox is valid
    public boolean checkValidFox(int x1, int y1, int x2, int y2){
        //if the starting set of coordinate is out of bounds
        if (x1 < 0 || x1 >= boardsize || y1 < 0 || y1 >= boardsize){
            return false;
        }
        //if the set of coordinate the fox moves to is out of bounds
        if (x2 < 0 || x2 >= boardsize || y2 < 0 || y2 >= boardsize){
            return false;
        }
        //if the starting set of coordinates is not a fox
        if (board[x1][y1] != FOX){
            return false;
        }
        //if the fox moves more than 2 coordinates in each direction
        if ((x1 - x2 > 2 || x1 - x2 < -2) || (y1 - y2 > 2 || y1 - y2 < -2)){
            return false;
        }
        //check if the fox can move to the adjacent empty box
        if ((x1 - x2 <= 1 && x1 - x2 >= -1) && (y1 - y2 <= 1 && y1 - y2 >= -1)){
            if (board[x2][y2] != FREE){
                return false;
            }
        }
        //check if the fox can make a jump over the geese
        if ((x1 - x2 == 2 || x1 - x2 == -2) || (y1 - y2 == 2 || y1 - y2 == -2 )){
            //the fox can't make a jump with 2 in either direction
            //and 1 in the other direction
            if ((x1 - x2 == 1 || x1 - x2 == -1) || (y1 - y2 == 1 || y1 - y2 == -1)){
                return false;
            }
            //if the box the fox is jumping over is not a goose
            if (board[(x1 + x2) / 2][(y1 + y2) / 2] != GOOSE){
                return false;
            }
            //if the set of coordinates the fox is moving to is not free
            if (board[x2][y2] != FREE){
                return false;
            }
        }
        return true;
    }
    //method to make move of the goose
    public void makeGeeseMove(int x1, int y1, int x2, int y2){
        board[x1][y1] = FREE;
        board[x2][y2] = GOOSE;
    }
    //method to make move of the fox
    public void makeFoxMove(int x1, int y1, int x2, int y2){
        //if the fox is jumping over a geese, it assigns the box with the geese
        // to free
        if ((x1 - x2 != 1 && x1 - x2 != -1) && (y1 - y2 != 1 && y1 -y2 != -1)) {
            if (board[(x1 + x2) / 2][(y1 + y2) / 2] == GOOSE) {
                board[(x1 + x2) / 2][(y1 + y2) / 2] = FREE;
            }
        }
        board[x1][y1] = FREE;
        board[x2][y2] = FOX;
    }
    //method to return the array which shows the position of the fox
    public int[] getFoxPosition() {
        for(int x = 0; x < boardsize; x++) {
            for (int y = 0; y < boardsize; y++) {
                if (board[x][y] == FOX){
                    coordinateArray = new int[]{x, y};
                }
            }
        }
        return coordinateArray;
    }
    //method to check whether the goose wins
    //if the fox can't move within 2 coordinates away in each direction
    //then geese win
    public boolean checkGeeseWin(int xFox, int yFox){
        for (int x = xFox - 2; x <= xFox + 2; x++) {
            for (int y = yFox - 2; y <= yFox + 2; y++) {
                if (checkValidFox(xFox, yFox, x, y) == true){
                    return false;
                }
            }
        }
        return true;
    }
    //method to check whether the fox wins
    //if there is no geese left on the board
    public boolean checkFoxWin(){
        for(int y = 0; y< boardsize; y++) {
            for (int x = 0; x < boardsize; x++) {
                if (board[x][y] == GOOSE)
                    return false;
            }
        }
        return true;
    }
    // Prints the board
    public void printBoard() {
        for(int y = 0; y< boardsize; y++)
        {
            for(int x = 0; x< boardsize; x++) {
                System.out.print(" ");
                switch(board[x][y]) {
                    case FREE:
                        System.out.print(".");
                        break;
                    case FOX:
                        System.out.print("*");
                        break;
                    case GOOSE:
                        System.out.print("o");
                        break;
                    default:
                        System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
