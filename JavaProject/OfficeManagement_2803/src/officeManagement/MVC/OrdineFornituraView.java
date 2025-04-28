package officeManagement.MVC;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.*;

import officeManagement.model.*;

// RAGIONARE COME GESTIRE LA FORNITURA

public class OrdineFornituraView extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private MateriaPrima materiaPrima;
	private double quantitaRichiesta;
	private boolean aperta;
	private JPanel panelTitolo = new JPanel(new FlowLayout(FlowLayout.CENTER));
	
	public OrdineFornituraView() {
		this.aperta = false;
		setSize(500,500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(8,1));
	}
	
	public void setQuantitaRichiesta(double quantitaRichiesta) {
		this.quantitaRichiesta = quantitaRichiesta;
	}
	
	public void setMateriaPrima(MateriaPrima materiaPrima) {
		this.materiaPrima = materiaPrima;
	}
	
	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public boolean isAperta() {
		return aperta;
	}
	
	public void LoadInterface() {
		
		JLabel titolo = new JLabel("<html><h1>Ordine per fornitura: </h1></html>");
		panelTitolo.add(titolo);
		
		
		add(panelTitolo);
	}
	
	public void pulisci() {
		
	}
}
