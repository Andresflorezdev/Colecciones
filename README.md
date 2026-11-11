# Sistema de Gestión de Biblioteca Universitaria
> Taller práctico de Colecciones en Java (`ArrayList`, `HashSet`, `HashMap`) y Programación Orientada a Objetos.

---

## Descripción del Proyecto

Este proyecto simula el núcleo de un sistema de gestión de biblioteca universitaria en Java. Reemplaza el registro manual en papel mediante estructuras de datos eficientes que resuelven tres necesidades fundamentales del negocio:

1. **Catálogo de materiales:** Encontrar un libro o revista al instante mediante su código identificador.
2. **Listas de espera:** Gestionar una fila ordenada por material prestado (el primero en llegar es el primero en recibirlo).
3. **Control de membresías:** Validar rápidamente si un usuario está registrado y activo, sin permitir duplicados.

---

## Criterio de Selección de Colecciones

| Pregunta de Negocio | Colección Elegida | ¿Por qué esta y no otra? |
| :--- | :--- | :--- |
| **¿Dónde está el material con código X?** | `HashMap<String, Material>` | **Búsqueda directa:** Acceso instantáneo en tiempo constante usando el código como clave, sin recorrer toda la lista. |
| **¿Quién sigue en la fila de espera?** | `ArrayList<String>` | **Orden de llegada:** Mantiene el orden exacto de llegada y permite saber quién está en la posición 0. |
| **¿Este usuario tiene membresía activa?** | `HashSet<String>` | **Unicidad:** No permite duplicados y verifica existencia en con `contains()`. |

---

## Preguntas del Taller y Respuestas Clave

### 1. Base y Programación Orientada a Objetos
* **¿Por qué una interfaz (`Catalogable`) antes de la clase?**  
  Para fijar una regla básica: cualquier elemento que entre a la biblioteca debe tener obligatoriamente un código identificador (`getCodigo()`). Define el "qué" debe tener, no el "cómo".
* **¿Por qué `Material` es abstracta y no concreta?**  
  Porque en la vida real no vas a la biblioteca a pedir "un material genérico", sino un *Libro* o una *Revista*. Al ser abstracta, evitas que alguien cree un material sin definir qué tipo es.
* **¿Qué hace `super()` en el constructor hijo?**  
  Le pasa los datos comunes (código, título, año, disponible) al constructor de la clase padre (`Material`) para que los inicialice adecuadamente.
* **¿Por qué importa `@Override`?**  
  Es un aviso de seguridad para el compilador. Si te equivocas en una letra del nombre del método (por ejemplo `getTiopo` en vez de `getTipo`), Java te avisa de inmediato en vez de crear un método nuevo invisible.
* **¿Cómo funciona el polimorfismo en `mostrarInfo()`?**  
  Aunque `Material` tiene el método `getTipo()` como abstracto, en tiempo de ejecución Java sabe exactamente si el objeto es un `Libro` o una `Revista` y ejecuta la versión correspondiente sin necesidad de usar condiciones `if` ni `instanceof`.

### 2. Catálogo (`HashMap`)
* **¿Por qué `HashMap` y no `ArrayList`?**  
  Porque buscar en una lista obliga a revisar elemento por elemento hasta dar con el código. Con `HashMap`, vas directo a la posición exacta mediante su clave en un solo paso.
* **¿Por qué declarar `Map<String, Material>` en lugar de `HashMap`?**  
  Por flexibilidad. Si mañana necesitas que el catálogo se ordene alfabéticamente con un `TreeMap`, solo cambias la inicialización (`new TreeMap<>()`) y el resto del código no sufre ningún cambio.

### 3. Lista de Espera (`ArrayList`)
* **¿Por qué `ArrayList` y no `HashSet`?**  
  Porque en una fila importa el orden de llegada. `HashSet` revuelve los datos sin orden y descarta si alguien aparece dos veces. El `ArrayList` respeta los turnos tal cual llegaron.
* **¿Por qué se hace `remove(0)` y no `remove("nombreUsuario")`?**  
  Porque se atiende al primero que llegó (índice 0). Si usáramos el nombre, atenderíamos a una persona en específico saltándonos el orden de la fila.

### 4. Registro de Membresías (`HashSet`)
* **¿Por qué `HashSet` y no `ArrayList`?**  
  Porque una persona o tiene membresía o no la tiene (no existen membresías dobles). Además, saber si alguien es miembro con `contains()` es inmediato.
* **¿Qué pasa si intentas registrar dos veces al mismo usuario?**  
  `HashSet` simplemente no lo duplica; descarta el registro repetido en silencio y su método `add()` retorna `false`, lo que permite avisarle al usuario que ya estaba registrado.

### 5. Pregunta Reflexiva
* **Si guardas dos objetos `Libro` con el mismo código en un `HashSet`, ¿Java los detecta como duplicados?**  
  No, por defecto Java no los detecta como duplicados. Como nuestra clase Material y sus subclases no sobreescriben los métodos equals() y hashCode(), Java compara las referencias de memoria de los objetos y no sus atributos internos (como el código o el título). Por lo tanto, si se crearan dos instancias distintas con el mismo código, Java permitiría almacenar ambas en el HashSet.

---

## Errores Cometidos Durante el Proceso y Aprendizajes

Durante el desarrollo surgieron tropiezos comunes que al principio parecían errores graves, pero en realidad eran pequeños descuidos de sintaxis o concepto:

### 1. Olvidar la palabra clave `extends`
* **Qué pasó:** Al crear las clases `Libro` y `Revista`, no se colocó `extends Material`.
* **Consecuencia:** Al no heredar de `Material`, el compilador no reconocía la llamada a `super()` en el constructor, ni los atributos heredados, ni permitía agregar libros o revistas al catálogo de tipo `Material`.
* **Aprendizaje:** La herencia en Java no es automática; requiere declarar explícitamente `class Libro extends Material` para conectar la subclase con su padre.

### 2. Confusión de tipos en las colecciones
* **Qué pasó:** En la declaración del catálogo se definió erróneamente una lista de cadenas:
  ```java
  // Incorrecto: Guardaba una lista de textos
  private Map<String, List<String>> catalogo = new HashMap<>();
  ```
* **Solución aplicada:** Se ajustó para almacenar el código como clave y el objeto `Material` completo como valor:
  ```java
  // Correcto: Código -> Objeto Material (Libro o Revista)
  private Map<String, Material> catalogo = new HashMap<>();
  ```
* **Aprendizaje:** La lista de cadenas (`List<String>`) pertenecía a la estructura de la **lista de espera** (`listaEspera`), no al catálogo. En el catálogo queríamos acceder a los métodos y datos del objeto (`mostrarInfo()`, `getCodigo()`).

### 3. El clásico punto y coma (`;`) faltante
* **Qué pasó:** Olvidar un punto y coma al final de una instrucción generó una cascada de 10 o más errores en pantalla.
* **Consecuencia:** En Java, cuando falta un `;`, el analizador del compilador sigue leyendo la siguiente línea como si fuera parte de la anterior, arrojando errores engañosos como `"cannot find symbol"`, `"illegal start of expression"` o `"expected )"`.
* **Aprendizaje:** Antes de alarmarse y cambiar la lógica del programa cuando aparecen muchos errores de golpe, lo primero que se debe revisar es la sintaxis básica (puntos y coma y llaves cerradas). Un solo detalle simple puede desestabilizar la compilación completa.

---
