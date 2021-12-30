package errors;

public class PlyFileException extends Exception {

    public PlyFileException() {
        super("Le fichier choisi n'est pas un fichier PLY");
    }
}
