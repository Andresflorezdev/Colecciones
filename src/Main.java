public class Main {
    public static void main(String[] args) {
        SistemaBiblioteca biblioteca = new SistemaBiblioteca();

        System.out.println("\n         1. Prueba de catalogo         ");
        Libro libro1 = new Libro("l001", "pepito", 2008, true, "jose", "978-013422112");
        Libro libro2 = new Libro("l002", "fruto del arbol borracho", 2001, false, "avispol", "979-0193434563");
        Revista rev1 = new Revista("r001", "MTV", 2003, false, 522, "Musica");
        Revista rev2 = new Revista("r002", "MTV", 2002, false, 511, "Musica");

        biblioteca.registrarMaterial(libro1);
        biblioteca.registrarMaterial(libro2);
        biblioteca.registrarMaterial(rev1);
        biblioteca.registrarMaterial(rev2);

        // mostrar el catalogo
        System.out.println("listado general de materiales");
        biblioteca.mostrarCatalogo();

        // buscar un material existente
        System.out.println("\n-> buscando material existente (l001)");
        Material encontrado = biblioteca.buscaMaterial("l001");
        if (encontrado != null) {
            encontrado.mostrarInfo();
        }

        // busco uno no existente
        System.out.println("\n-> buscando un codigo que no existe (r005)");
        Material noEncontrado = biblioteca.buscaMaterial("r005");
        System.out.println(noEncontrado == null ? "este material no fue encontrado" : "");

        // pruebo lista de espera
        System.out.println("\n       2. Prueba lista de espera     ");
        biblioteca.agregarEspera("l002", "andres");
        biblioteca.agregarEspera("l002", "mariam");
        biblioteca.agregarEspera("l002", "raul");

        // se extrae en orden primero en llegar primero en salir
        System.out.println("material 'l002' prestado");
        System.out.println("siguiente en recibir l002 " + biblioteca.siguienteEnEspera("l002")); // este es andres
        System.out.println("siguiente en recibir l002 " + biblioteca.siguienteEnEspera("l002")); // esta es mariam

        // membresias
        System.out.println("\n       3. Prueba de membresias          ");
        System.out.println("registrando a andres: " + biblioteca.registrarMiembro("andres")); // ahora queda en true
        System.out.println("registrando a mariam: " + biblioteca.registrarMiembro("mariam")); // queda en true

        // intentar registrar un duplicado hashSet lo ignora y retorna false
        System.out.println("intentando registrar a andres de nuevo: " + biblioteca.registrarMiembro("andres")); // retorna false

        // verificando membresia en contains()
        System.out.println("\nVerificaciones de membresía:");
        System.out.println("andres tiene membresia: " + biblioteca.tieneMembresia("andres")); // da true
        System.out.println("pedro tiene membresia: " + biblioteca.tieneMembresia("pedro")); // da false
    }
}