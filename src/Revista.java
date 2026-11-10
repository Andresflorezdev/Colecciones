public class Revista extends Material{

    // atributos
    private int edicion;
    private String tematica;

    // constructor
    public Revista(String codigo, String titulo, int año, boolean disponible, int edicion, String tematica) {
        super(codigo, titulo, año, disponible);
        this.edicion = edicion;
        this.tematica = tematica;
    }

    @Override
    public String getTipo() {
        return "Revista";
    }

    public int getEdicion() {
        return edicion;
    }

    public String getTematica() {
        return tematica;
    }
}
