import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
@Entity
public class Capitulo {
    @Id
    private int id;
    @Column
    private String titulo;
    @Column
    private int paginas;
    @ManyToOne
    private Persona revisor;
    @ManyToMany
    private List<Persona> autores = new ArrayList<>();
    @ManyToOne
    private Libro libro;
     public Capitulo(){}

    public Capitulo(int id,String titulo, int paginas, Persona revisor, List<Persona> autores, Libro libro) {
        this.id=id;
    	this.titulo = titulo;
        this.paginas = paginas;
        this.revisor = revisor;
        this.autores = autores;
        this.libro = libro;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public void setRevisor(Persona revisor) {
        this.revisor = revisor;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getPaginas() {
        return paginas;
    }

    public Persona getRevisor() {
        return revisor;
    }

    public List<Persona> getAutores() {
        return autores;
    }

    public Libro getLibro() {
        return libro;
    }
}
