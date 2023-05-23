import java.awt.Color;

public class Game {
	Color backgroundColor = new Color(0,100,0);


	public boolean checkSurrounding(int x, int y, Color currentPlayer, Color enemyPlayer, Color background, Case[][] board) {
	    boolean res = false;
	    int[][] directions = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};

	    for (int[] dir : directions) {
	        int row = x + dir[0];
	        int col = y + dir[1];

	        // On ne vérifie que les cases qui sont sur le plateau
	        if (row < 0 || col < 0 || row > 7 || col > 7) {
	            continue;
	        }

	        // On accède à la case du plateau en utilisant le tableau de Case de GameBoard
	        Case currentCase = board[row][col];
	        
	        if (row == x && col == y) {
	            continue;
	        }
	        // Si la case contient une pièce du joueur adverse, on continue de vérifier dans la même direction
	        if (currentCase.getCouleur() == enemyPlayer) {
	            int deltaRow = dir[0];
	            int deltaCol = dir[1];
	            int nextRow = row + deltaRow;
	            int nextCol = col + deltaCol;
	            while (nextRow >= 0 && nextCol >= 0 && nextRow <= 7 && nextCol <= 7) {
	                Case nextCase = board[nextRow][nextCol];
	                System.out.println("row " + row + " col "+ col + " deltaCol " + deltaCol + " deltaRow "+ deltaRow);
	                System.out.println("nextRow " + nextRow + " nextCol "+ nextCol);
	                System.out.println("next case couleur : "+nextCase.getCouleur() + "next case coordonnées : x : "+nextCase.getX()+" y : "+nextCase.getY());
	                System.out.println(currentCase.getCouleur());
	                if (nextCase.getCouleur() == currentPlayer) {
	                	System.out.println(deltaRow+" "+deltaCol);
	                	System.out.println(row + " " + col);
	                    res = true; 
	                    break;
	                } else if (nextCase.getCouleur() == background || nextCase.getCouleur() == enemyPlayer) {
	                	System.out.println("AAAA Case [" + row + "][" + col + "] has color " + currentCase.getCouleur());
	                }

	                nextRow += deltaRow;
	                nextCol += deltaCol;
	            }  
	        } else {
	            System.out.println("Case [" + row + "][" + col + "] has color " + currentCase.getCouleur());
	        }
	    } 
	    return res;
	}

	
	public void flipEnemyPieces(int x, int y, Color currentPlayer, Color enemyPlayer, Case[][] board) {
	    int[][] directions = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};

	    for (int[] dir : directions) {
	        int row = x + dir[0];
	        int col = y + dir[1];

	        // On ne vérifie que les cases qui sont sur le plateau
	        if (row < 0 || col < 0 || row > 7 || col > 7) {
	            continue;
	        }

	        // On accède à la case du plateau en utilisant le tableau de Case de GameBoard
	        Case currentCase = board[row][col];

	        // Si la case contient la couleur de l'ennemi, on regarde dans cette direction s'il y a une case de la couleur du joueur courant
	        if (currentCase.getCouleur() == enemyPlayer) {
	            int newRow = row;
	            int newCol = col;
	            while (true) {
	                newRow += dir[0];
	                newCol += dir[1];

	                // On ne vérifie que les cases qui sont sur le plateau
	                if (newRow < 0 || newCol < 0 || newRow > 7 || newCol > 7) {
	                    break;
	                }

	                Case newCase = board[newRow][newCol];

	                // Si la case contient la couleur du joueur courant, on peut commencer à changer la couleur des pièces ennemies
	                if (newCase.getCouleur() == currentPlayer) {
	                    int flipRow = row;
	                    int flipCol = col;
	                    while (flipRow != newRow || flipCol != newCol) {
	                        board[flipRow][flipCol].setCouleur(currentPlayer);
	                      
			                    
	                        flipRow += dir[0];
	                        flipCol += dir[1];

	                    }

	                }

	                // Si la case est vide, on ne peut pas changer la couleur des pièces ennemies dans cette direction
	                if (newCase.getCouleur() == backgroundColor) {
	                    break;
	                }
	            }
	        }
	    }
	}


}
