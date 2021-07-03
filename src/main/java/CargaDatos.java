import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;



public class CargaDatos {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		//Crea el Entity manager factory con la configuración
		//llamada editorial
	     EntityManagerFactory emf = Persistence.createEntityManagerFactory("Editorial");
	        EntityManager em = emf.createEntityManager();
		cargarPersonas(em);
		cargarLibros(em);
		cargarCapitulos(em);
		 em.close();
		 em = emf.createEntityManager();
	     em.getTransaction().begin();
	     TypedQuery<Reporte1DTO> q = em.
	    		 createQuery("SELECT new "

	    		 + "Reporte1DTO(p.id, count(*))"
	    		 + " FROM Libro l JOIN l.editores p"
	    		 + " WHERE l.anio = :anio"
	    		 + " GROUP BY p.id ",
	    		 Reporte1DTO.class);

	    		 q.setParameter("anio", 2017);

	    		 List<Reporte1DTO> res = q.getResultList();
	    		 //Imprime los resultados
	     res.forEach(r -> System.out.println(r));
	     em.getTransaction().commit();
	     em.close();
	     System.out.println("-----------------------------------");
	     em = emf.createEntityManager();
	     em.getTransaction().begin();
	     TypedQuery<Reporte1DTO> q2 = em.
	    		 createQuery("SELECT new "

	    		 + "Reporte1DTO(c.revisor.id, sum(c.paginas))"
	    		 + " FROM Capitulo c JOIN c.libro l"
	    		 + " WHERE l.anio = :anio"
	    		 + " GROUP BY c.revisor.id ",
	    		 Reporte1DTO.class);

	    		 q2.setParameter("anio", 2017);

	    		 List<Reporte1DTO> res2 = q2.getResultList();
	    		 //Imprime los resultados
	     res2.forEach(r -> System.out.println(r));
	     em.getTransaction().commit();
	     em.close();
	     System.out.println("-----------------------------------");
	     em = emf.createEntityManager();
	     em.getTransaction().begin();
	     TypedQuery<Reporte1DTO> q3 = em.
	    		 createQuery("SELECT new "

 							+ "Reporte1DTO(c.autores.id, count(*))"
 							+ " FROM Capitulo c JOIN Libro l"
 							+ " ON l.capitulos.autores.id = c.autores.id"
 							+ " GROUP BY c.autores.id",
 							Reporte1DTO.class);

	   //  q3.setParameter("valor", 3);

	    		 List<Reporte1DTO> res3= q3.getResultList();
	    		 //Imprime los resultados
	     res3.forEach(r -> System.out.println(r));
	     em.getTransaction().commit();
	     em.close();
	     emf.close();
	}

	private static void cargarCapitulos(EntityManager em) throws FileNotFoundException, IOException {
		CSVParser parser = CSVFormat.DEFAULT.
				withHeader().parse(new FileReader("C:/Users/Micaela/Desktop/untitled/src/main/java/autor.csv"));
		Map<Integer, List<Integer>> autores = new HashMap<>();
		parser.forEach(r -> {
			int chapter = Integer.parseInt(r.get("Capitulo_id"));
			int autor = Integer.parseInt(r.get("autores_id"));
			List aut = autores.get(chapter);
			if (aut == null) {
				aut = new ArrayList<>();
				autores.put(chapter, aut);
			}
			aut.add(autor);
		});
		parser = CSVFormat.DEFAULT.
				withHeader().parse(new FileReader("C:/Users/Micaela/Desktop/untitled/src/main/java/capitulo.csv"));
		em.getTransaction().begin();
		for(CSVRecord row: parser) {
			int id = Integer.parseInt(row.get("id"));
			@SuppressWarnings("unchecked")
			List<Persona> authors = (List<Persona>) autores.getOrDefault(id, Collections.EMPTY_LIST).
					stream().
					map(pid -> em.find(Persona.class, pid)).
					collect(Collectors.toList());
			Persona revisor = em.find(Persona.class, Integer.parseInt(row.get("revisor_id")));
			Libro libro = em.find(Libro.class, Integer.parseInt(row.get("libro_id")));
			Capitulo c = new Capitulo(Integer.parseInt(row.get("id")), 
					row.get("titulo"), 
					Integer.parseInt(row.get("paginas")), 
					revisor, 
					authors, 
					libro);
			em.persist(c);
		}
		em.getTransaction().commit();
	}

	private static void cargarLibros(EntityManager em) throws FileNotFoundException, IOException {
		CSVParser parser = CSVFormat.DEFAULT.
				withHeader().parse(new FileReader("C:/Users/Micaela/Desktop/untitled/src/main/java/editor.csv"));
		Map<Integer, List<Integer>> editores = new HashMap<>();
		parser.forEach(r -> {
			int book = Integer.parseInt(r.get("Libro_id"));
			int editor = Integer.parseInt(r.get("editores_id"));
			List edi = editores.get(book);
			if (edi == null) {
				edi = new ArrayList<>();
				editores.put(book, edi);
			}
			edi.add(editor);
		});
		parser = CSVFormat.DEFAULT.
				withHeader().parse(new FileReader("C:/Users/Micaela/Desktop/untitled/src/main/java/libro.csv"));
		em.getTransaction().begin();
		for(CSVRecord row: parser) {
			int id = Integer.parseInt(row.get("id"));
			@SuppressWarnings("unchecked")
			List<Persona> editors = (List<Persona>) editores.getOrDefault(id, Collections.EMPTY_LIST).
					stream().
					map(pid -> em.find(Persona.class, pid)).
					collect(Collectors.toList());
			Libro l = new Libro(id, 
					row.get("nombre"),
					Integer.parseInt(row.get("anio")),
					editors);
			em.persist(l);
		}
		em.getTransaction().commit();
	}

	private static void cargarPersonas(EntityManager em) throws FileNotFoundException, IOException {
		CSVParser parser = CSVFormat.DEFAULT.
				withHeader().parse(new FileReader("C:/Users/Micaela/Desktop/untitled/src/main/java/persona.csv"));
		em.getTransaction().begin();
		for(CSVRecord row: parser) {
			Persona p = new Persona(Integer.parseInt(row.get("id")), 
					row.get("nombre"),
					row.get("apellido"),
					row.get("mail"));
			em.persist(p);
		}
		em.getTransaction().commit();
	}
}
