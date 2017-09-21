import javax.swing.*;

/**
 * This class acts as a controller, tying the whole game together
 * Created by Pierre Lejdbring on 9/11/17.
 */
public class Othello {

    private boolean isRunning;
    GameState gameState;
    GUI gui;
    AI ai;
    private int currentPlayer;

    public Othello() {
        gameState = new GameState();
        ai = new AI();
        currentPlayer = Utils.HUMAN;
        isRunning = true;
        gui = new GUI("Othello", gameState, this);
        System.out.println(currentPlayer);
    }

    /**
     * This is run when ever the human player press a coordinate in the GUI
     * It sets the current player, calls for a redraw of the GUI and trigger the AI's next move
     * @param coord
     */
    public void buttonPressed(Coordinate coord) {
        //System.out.println(currentPlayer > 0 ? "HUMAN" : "AI");
        //Utils.print(Engine.getValidMoves(gameState, currentPlayer));
        //System.out.println("Valid move count: " + Engine.validMoveCount(gameState, currentPlayer));
        //System.out.println(Engine.calculateScore(gameState));

        if(Engine.isValidMove(coord, gameState, currentPlayer)) {
            gameState = Engine.updateBoard(gameState, coord, currentPlayer);
            SwingUtilities.invokeLater(() -> {
                gui.draw(gameState);
                gui.disableButtons();
            });
            currentPlayer = Utils.AI;
        } else {
            return;
        }
        ai.aiMakeMove(gameState);
        ai.printChildren(ai.n);

        //Debuggin
        //System.out.println(currentPlayer > 0 ? "HUMAN" : "AI");
        //System.out.println(Engine.calculateScore(gameState));
        //Utils.print(Engine.getValidMoves(gameState, currentPlayer));
        //System.out.println();
        //------------------------------------------------------------

        while(Engine.hasValidMoves(gameState, Utils.AI) /*&& !Engine.hasValidMoves(gameState, Utils.HUMAN)*/) {
            //ai make move
            SwingUtilities.invokeLater(() -> {
                gui.draw(gameState);
                gui.enableButtons();
            });
            //This break is here only for debugging purposes
            break;
        }
        //debugging
        /*System.out.println("Current state");
        Utils.printState(gameState);
        System.out.println("Possible moves");
        Utils.print(Engine.getValidMoves(gameState, currentPlayer));*/
        //---------------------------------------------------
        currentPlayer = Utils.HUMAN;
    }

    public static void main(String[] args) {
        new Othello();

    }

}
