package libreria.servicios;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;

public class LibroServicio {

    static Scanner leer = new Scanner(System.in).useDelimiter("\n");

    public static void crearLibro() throws Exception {
        EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
        try {
            Libro libro = new Libro();
            System.out.println("Ingrese el nombre del titulo");
            libro.setTitulo(leer.next());
            System.out.println("Ingrese año");
            libro.setAnio(leer.nextInt());
            System.out.println("Cantidad de ejemplares?");
            libro.setEjemplares(leer.nextInt());
            libro.setEjemplaresRestantes(libro.getEjemplares());
            libro.setEjemplaresPrestados(0);

            System.out.println("Ingrese el ID del autor");
            Autor autor = em.find(Autor.class, leer.nextInt());

            if (autor != null) {
                System.out.println(autor);
                libro.setAutor(autor);
            } else {
                throw new Exception("NO EXISTE UNA ID PARA ESE AUTOR");
            }

            System.out.println("Ingrese el ID de la editorial");
            Editorial e = em.find(Editorial.class, leer.nextInt());
            
            if (e != null) {
                libro.setEditorial(e);
            } else {
                throw new Exception("NO EXISTE UNA ID DE ESA EDITORIAL");
            }

            System.out.println("LIBRO GUARDADO CORRECTAMENTE");

            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    public static void modificarLibroPorId() throws Exception {
        EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();

        try {

            System.out.println("Ingrese el ID del libro que desea modificar");
            Libro libro = em.find(Libro.class, leer.nextInt());

            if (libro != null) {
                System.out.println("LIBRO " + libro.getTitulo() + " ENCONTADO");
            } else {
                System.out.println("NO HAY LIBROS CON ESE ID");
            }

            System.out.println("Ingrese el nombre del titulo");
            libro.setTitulo(leer.next());
            System.out.println("Ingrese año");
            libro.setAnio(leer.nextInt());
            System.out.println("Cantidad de ejemplares?");
            libro.setEjemplares(leer.nextInt());
            libro.setEjemplaresRestantes(libro.getEjemplares());
            libro.setEjemplaresPrestados(0);

            System.out.println("Ingrese el ID del autor");
            Autor autor = em.find(Autor.class, leer.nextInt());
            
            if (autor != null) {
                System.out.println(autor);
                libro.setAutor(autor);
            } else {
                System.out.println(autor);
                throw new Exception("NO EXISTE UNA ID PARA ESE AUTOR");
            }
            System.out.println("Ingrese el ID de la editorial");
            Editorial e = em.find(Editorial.class, leer.nextInt());
            if (e != null) {
                libro.setEditorial(e);
            } else {
                throw new Exception("NO EXISTE UNA ID DE ESA EDITORIAL");
            }

            em.getTransaction().begin();
            em.merge(libro);
            em.getTransaction().commit();
            System.out.println("LIBRO MODIFICADO CORRECTAMENTE");
            
        } catch (Exception e) {
            throw e;
        }
    }

    public static void borrarLibroPorId() {
        EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();

        try {
            System.out.println("Ingrese el ID del libro que desea borrar");
            Libro libro = em.find(Libro.class, leer.nextInt());

            if (libro != null) {
                System.out.println("LIBRO " + libro.getTitulo() + " BORRADO EXITOSAMENTE");
            } else {
                System.out.println("NO HAY LIBROS CON ESE ID");
            }

            em.getTransaction().begin();
            em.remove(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        }
    }

    //9) Búsqueda de un libro por ISBN.
    public static void busquedaPorISBN() {
        EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
        System.out.println("INGRESE EL ISBN DEL LIBRO A BUSCAR");
        Long isbn = leer.nextLong();

        List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE l.isbn = :isbn").setParameter("isbn", isbn).getResultList();

        if (libros.isEmpty()) {
            System.out.println("NO HAY LIBROS CON ESE ISBN");
        }

        for (Libro i : libros) {
            System.out.println(i);
        }
    }

    //10) Búsqueda de un libro por Título
    public static void busquedaPorTitulo() {
        EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
        try {
            System.out.println("INGRESE EL TITULO DEL LIBRO A BUSCAR");
            String titulo = leer.next();

            List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE l.titulo LIKE :titulo").setParameter("titulo", titulo).getResultList();
            if (libros.isEmpty()) {
                System.out.println("LISTA DE LIBROS VACIA");
            }
            for (Libro i : libros) {
                System.out.println(i);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    //11)Búsqueda de un libro/s por nombre de Autor
    public static void busquedaPorNombreAutor() {
        EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
        System.out.println("INGRESE EL NOMBRE DEL AUTOR DEL LIBRO A BUSCAR");
        String nombreAutor = leer.next();

        List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE l.autor.nombre LIKE :nombreAutor")
                .setParameter("nombreAutor", nombreAutor)
                .getResultList();
        if (libros.isEmpty()) {
            System.out.println("NO HAY LIBROS CON ESE AUTOR");
        }
        for (Libro i : libros) {
            System.out.println(i);
        }
    }

    //12) Búsqueda de un libro/s por nombre de Editorial.
    public static void busquedaPorNombreEditorial() {
        EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
        System.out.println("INGRESE EL NOMBRE DE LA EDITORIAL DEL LIBRO A BUSCAR");
        String nombreEditorial = leer.next();

        List<Libro> libros = em.createQuery("SELECT l FROM Libro l WHERE l.editorial.nombre :nombreEditorial").setParameter("nombreEditorial", nombreEditorial).getResultList();
        if (libros.isEmpty()) {
            System.out.println("NO HAY LIBROS CON ESA EDITORIAL");
        }
        for (Libro i : libros) {
            System.out.println(i);
        }
    }

    public static void verTodosLosLibros() {
        EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
       
        List<Libro> libros = em.createQuery("SELECT l FROM Libro l").getResultList();
        if (libros.isEmpty()) {
            System.out.println("NO HAY LIBROS");
        }
        for (Libro i : libros) {
            System.out.println(i);
        }
    }
}
