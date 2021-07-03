import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
@Entity
public class Libro {
    @Id
    private int id;
    @Column
    private String nombre;
    @Column
    private int anio;
    @ManyToMany
    private List<Persona> editores = new ArrayList<Persona>();
    @OneToMany(mappedBy = "libro")
    private List<Capitulo> capitulos = new ArrayList<Capitulo>();

    public Libro(){}
    public Libro(int id,String nombre, int anio, List<Persona> editores) {
        this.id=id;
    	this.nombre = nombre;
        this.anio = anio;
        this.editores = editores;
        
    }
    public Libro(int id,String nombre, int anio, List<Persona> editores, List<Capitulo> capitulos) {
        this.id=id;
    	this.nombre = nombre;
        this.anio = anio;
        this.editores = editores;
        this.capitulos = capitulos;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public List<Persona> getEditores() {
        return editores;
    }

    public void setEditores(List<Persona> editores) {
        this.editores = editores;
    }

    public List<Capitulo> getCapitulos() {
        return capitulos;
    }

    public void setCapitulos(List<Capitulo> capitulos) {
        this.capitulos = capitulos;
    }
}
