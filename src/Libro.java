public class Libro extends Material {

    // atributos
    private String autor;
    private String isbn;

    // constructor
    public Libro(String codigo, String titulo, int año, boolean disponible, String autor, String isbn) {
        super(codigo, titulo, año, disponible);
        this.autor = autor;
        this.isbn = isbn;
    }

    @Override
    public String getTipo() {
        return "Libro";
    }

    public String getAutor() {
        return autor;
    }

    public String getIsbn() {
        return isbn;
    }
}
