
public class Reporte1DTO {
	private int personaId;
	private long cant;
	public Reporte1DTO(int personaId, long cant) {
	super();
	this.personaId = personaId;
	this.cant = cant;
	}
	//Getters y Setters
	public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public long getCant() {
        return cant;
    }

    public void setCant(long cant) {
        this.cant = cant;
    }
	@Override
	public String toString() {
	return "Reporte1DTO [personaId=" + personaId +

	", cant=" + cant + "]";
	}
}
