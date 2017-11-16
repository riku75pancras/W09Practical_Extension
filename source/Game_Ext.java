public class Game_Ext {
    // The following five constants were defined in the starter code (kt54)
    private static String FOXPLAYS_MSG = "Fox plays. Enter move:";
    private static String GEESEPLAY_MSG = "Geese play. Enter move:";
    private static String ILLEGALMOVE_MSG = "Illegal move!";
    private static String FOXWINS_MSG = "Fox wins!";
    private static String GEESEWIN_MSG = "Geese win!";
    private Board_Ext gameBoard;
    private int [] coordinateArray;
    // Constructor which creates the object gameboard
    public Game_Ext() {
        gameBoard = new Board_Ext();
    }
    // method which operates the game
    public void play() {
        EasyIn2 reader = new EasyIn2();
        boolean done = false;
        while (!done) {
            gameBoard.printBoard();
            //boolean used to change turns
            boolean geeseToPlay = true;
            //method to check whether the movement is valid
            if (geeseToPlay == true)   {
                if(gameBoard.checkFoxWin() != true) {
                    System.out.println(GEESEPLAY_MSG);
                    int x1 = reader.getInt();
                    int y1 = reader.getInt();
                    int x2 = reader.getInt();
                    int y2 = reader.getInt();
                    //when the player makes an invalid move
                    while (gameBoard.checkValidGeese(x1, y1, x2, y2) == false) {
                        System.out.println(ILLEGALMOVE_MSG);
                        gameBoard.printBoard();
                        System.out.println(GEESEPLAY_MSG);
                        x1 = reader.getInt();
                        y1 = reader.getInt();
                        x2 = reader.getInt();
                        y2 = reader.getInt();
                    }
                    gameBoard.makeGeeseMove(x1, y1, x2, y2);
                    gameBoard.printBoard();
                    geeseToPlay = !geeseToPlay;
                }
                else {
                    System.out.println(FOXWINS_MSG);
                    done = true;
                }
            }
            if (geeseToPlay != true){
                gameBoard.getFoxPosition();
                //xFox and yFox is used to check whether the fox can make
                //a move
                int xFox = gameBoard.getFoxPosition()[0];
                int yFox = gameBoard.getFoxPosition()[1];
                if (gameBoard.checkGeeseWin(xFox, yFox) != true) {
                    System.out.println(FOXPLAYS_MSG);
                    int x1 = reader.getInt();
                    int y1 = reader.getInt();
                    int x2 = reader.getInt();
                    int y2 = reader.getInt();
                    //when the player makes an invalid move
                    while (gameBoard.checkValidFox(x1, y1, x2, y2) == false) {
                        System.out.println(FOXPLAYS_MSG);
                        System.out.println(ILLEGALMOVE_MSG);
                        gameBoard.printBoard();
                        x1 = reader.getInt();
                        y1 = reader.getInt();
                        x2 = reader.getInt();
                        y2 = reader.getInt();
                    }
                    gameBoard.makeFoxMove(x1, y1, x2, y2);
                    geeseToPlay = !geeseToPlay;
                }
                else {
                    System.out.println(GEESEWIN_MSG);
                    done = true;
                }
            }
        }
    }
}
