import javax.persistence.*;
import java.*;
public class Prueba {
    public static void main(String[] args) {
//Crea el Entity manager factory con la configuración
//llamada editorial
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Editorial");
        EntityManager em = emf.createEntityManager();
//Inicia la transacción con la DBs
//Persiste una persona
//Hace el commit
  
//Inicio otra session
        em = emf.createEntityManager();
        em.getTransaction().begin();
//Pregunto por todas las Personas. Persona en este caso
//es la clase Persona, ya que la query es sobre JPQL
        TypedQuery<Persona> qp = (TypedQuery<Persona>) em.

                createQuery("SELECT p FROM Persona p",
                        Persona.class);
        for (Persona p: qp.getResultList()) {
            System.out.println(p);
        }
        em.getTransaction().commit();
        em.close();
        emf.close();
    }
}
