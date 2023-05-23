import java.awt.Color;

public class Case {
    private int x;
    private int y;
	private Color couleur;

    public Case(int x, int y, Color couleur) {
        this.x = x;
        this.y = y;
        this.couleur = couleur;
    }
    
    public void setCase(int x, int y, Color couleur) {
        this.x = x;
        this.y = y;
        this.couleur = couleur;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Color getCouleur() {
        return this.couleur;
    }
    
    public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}

}