import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameBoard extends JPanel implements ActionListener{

    Color currentPlayerColor;
    Color enemyPlayerColor;
    Color noir = Color.black; // joueur noir
    Color blanc = Color.white; // joueur blanc
    Color backgroundColor = new Color(0,100,0); // case vide
    private static final long serialVersionUID = 1L;
    private Case board[][]= new Case[8][8];
    private int tour;
    private JButton[][] buttonGrid = new JButton[8][8];
    

    // Donne la taille de la Frame

    public void setBoard() {

        JFrame othelloGlobal = new JFrame("Othello");
        othelloGlobal.setSize(850,950);
        othelloGlobal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        this.afficheEcran();
        othelloGlobal.add(this);

        othelloGlobal.setVisible(true);

    }


    //Setup le jeu de base

    private void setGame(Case[][] board) {

        // Change the background color of the buttons at coordinates (3,3), (3,4), (4,3), (4,4)
        JButton button;
        for (int i = 3; i < 5; i++) {
            for (int j = 3; j < 5; j++) {
                button = (JButton) this.getComponent(i * 8 +j);
                if(i == 3 && j == 3 || i == 4 && j == 4) {
                    button.setBackground(noir);
                    board[i][j] = new Case(j, j,noir);
                }else {
                    button.setBackground(blanc);
                    board[i][j] = new Case(j, j,blanc);
                }
            }
        }
    }
    
    //garde en mémoire le tableau de Case
    private Case[][] getBoard(){
        return board;
    }

    //Affiche la grille de jeu
    public void afficheEcran() {

        this.setLayout(new GridLayout(9,8));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton bouton = new JButton();
                bouton.setBorder(BorderFactory.createLineBorder(Color.black,2));
                board[i][j] = new Case(j, j,backgroundColor);
                buttonGrid[i][j] = new JButton();
                bouton.setName(i +","+ j);
                bouton.setBackground(backgroundColor);
                bouton.addActionListener(this);
                this.add(bouton);
            }
        } 
        
        Case[][] board = getBoard();
        setGame(board);
        currentPlayerColor = noir;
        enemyPlayerColor = blanc;
        JLabel joueurActuel = new JLabel("P: Noir");
        this.add(joueurActuel);
        JLabel tourActuel = new JLabel("tour: "+tour);
        this.add(tourActuel);
        
        }
    
    // Synchronise les valeurs des Cases dans le tableau de JButton
    public JButton getButton(int x, int y, JButton[][] buttonGrid) {
        JButton button = null;
        if (x >= 0 && x < buttonGrid.length && y >= 0 && y < buttonGrid[x].length) {
            button = buttonGrid[x][y];
        }
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        String[] coord = clickedButton.getName().split(",");
        int x = Integer.parseInt(coord[0]);
        int y = Integer.parseInt(coord[1]);
        System.out.println("Coordonnées: " + x + "," + y);
        JLabel joueurActuel = (JLabel) this.getComponent(64);
        JLabel tourActuel = (JLabel) this.getComponent(65);
        ArrayList<Integer> emptyCase = new ArrayList<Integer>();
        if (clickedButton.getBackground().equals(backgroundColor)) {
        	
            // Vérifier si le coup est valide
        	
        	 Game daRules = new Game();
        	 
            if (daRules.checkSurrounding(x, y, currentPlayerColor, enemyPlayerColor, backgroundColor, board)) {
                tour = tour + 1;
                tourActuel.setText("Tour: " + tour);

                // Mettre à jour la couleur du bouton cliqué
                clickedButton.setBackground(currentPlayerColor);
                board[x][y] = new Case(x, y,currentPlayerColor);
                
             // Flip les pièces ennemies
                daRules.flipEnemyPieces(x, y, currentPlayerColor, enemyPlayerColor, board);
                
                // Mise a jour des couleurs physique
                for (int i = 0; i < 64; i++) {
                    JButton physicalView = (JButton) this.getComponent(i);
                    String[] coordView = physicalView.getName().split(",");
                    int xView = Integer.parseInt(coordView[0]);
                    int yView = Integer.parseInt(coordView[1]);
                    Color caseColor = board[xView][yView].getCouleur();
                    physicalView.setBackground(caseColor);
                }
                
             // Changer le joueur courant et le joueur en face
                Color temp = currentPlayerColor;
                currentPlayerColor = enemyPlayerColor;
                enemyPlayerColor = temp;

             // Mettre à jour le JLabel du joueur courant
                if (currentPlayerColor.equals(noir)) {
                    joueurActuel.setText("P: Noir");
                } else {
                    joueurActuel.setText("P: Blanc");
                }
            } else {
                System.out.println("Coup invalide");
            }
            
            //	Met dans une liste toute les cases vides et vérifie si c'est encore jouable (pas encore fini)
            	for (int i = 0; i < 64; i++) {
            		JButton physicalEnd = (JButton) this.getComponent(i);
            		Color ColorEnd = physicalEnd.getBackground();
            		if (ColorEnd.equals(backgroundColor)) {
            			emptyCase.add(i);
            		}
            		
            		List<JButton> emptyEnd = new ArrayList<JButton>(); // liste pour stocker les boutons vides
            		for (Integer index : emptyCase) {
            		    JButton button = (JButton) this.getComponent(index); // récupérer le bouton correspondant à l'indice
            		    emptyEnd.add(button); // ajouter le bouton à la liste emptyEnd
            		}
            		//for (JButton button : emptyEnd) {
            		   // String[] coord1 = button.getName().split(","); // récupérer les coordonnées sous forme de chaîne
            		    //int x1 = Integer.parseInt(coord1[0]); // convertir la coordonnée x en entier
            		    //int y1 = Integer.parseInt(coord1[1]); // convertir la coordonnée y en entier
            		//}
            	}
            
        } else {
        	System.out.println("vous avez cliquez sur une case déjà jouée");
        }
    }
    


}