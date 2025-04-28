package officeManagement.MVC;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.sql.SQLException;

import javax.swing.*;

public class View extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private JLabel titolo = new JLabel("Homepage gestionale");
	private JButton btnOrdini = new JButton("Ordini");
	private JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	private JButton btnProdotti = new JButton("Prodotti");
	
	
	public View() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		setSize(screenSize.width, screenSize.height);
		setLocation(0, 0);
		
	}
	
	public void LoadInterface() throws SQLException {
		panelBtn.add(btnOrdini);
		panelBtn.add(btnProdotti);
		btnOrdini.addActionListener(e -> {
			try {
				controller.openOrdiniView();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		btnProdotti.addActionListener(e -> {
			try {
				controller.openProdottiView();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		add(panelBtn);
		
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

}
