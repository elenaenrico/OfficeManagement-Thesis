package officeManagement.model;

public class Fornitore {
	private String id;
	private String nome;
	private String email;
	private String telefono;
	
	public Fornitore(String id, String nome, String email, String telefono) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.telefono = telefono;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
