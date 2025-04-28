package officeManagement.model;

public class MateriaPrima {
	
	private String id;
	private String id_fornitore;
	private String nome;
	private double prezzo;
	private double quantitaMagazzino;
	
	public MateriaPrima(String id, String id_fornitore, String nome, double prezzo) {
		super();
		this.id = id;
		this.id_fornitore = id_fornitore;
		this.nome = nome;
		this.prezzo = prezzo;
		this.quantitaMagazzino = 0;
	}
	
	public double getQuantitaMagazzino() {
		return quantitaMagazzino;
	}

	public void setQuantitaMagazzino(double quantitaMagazzino) {
		this.quantitaMagazzino = quantitaMagazzino;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId_fornitore() {
		return id_fornitore;
	}

	public void setId_fornitore(String id_fornitore) {
		this.id_fornitore = id_fornitore;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	
}
