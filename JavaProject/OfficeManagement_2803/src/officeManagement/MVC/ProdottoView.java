package officeManagement.MVC;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import com.formdev.flatlaf.FlatLightLaf;

import officeManagement.model.*;



public class ProdottoView extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller controller;
	private boolean aperta = false;
	private JPanel panelAzioni = new JPanel(new BorderLayout());
	private JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
	private JPanel panelBtn = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	private JPanel panelTXT = new JPanel();
	private JPanel panelFiltri = new JPanel(new BorderLayout());
	private JLabel prodotti = new JLabel();
	private JLabel prodottiRic = new JLabel();
	private JLabel prodottiFil = new JLabel();
	private JButton btnReset = new JButton("Azzera ricerca");
	private boolean filtriVisibili = false;
	private JPanel panelClienti = new JPanel();
	private JPanel panelProdotti = new JPanel();

	public ProdottoView() {
		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (Exception ex) {
			System.err.println("Impossibile caricare FlatLaf");
		}
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();

		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setLocation(0, 0);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public boolean isAperta() {
		return aperta;
	}
	
	public void LoadInterface() throws SQLException {
		aperta = true;
		panelAzioni.setLayout(new BorderLayout());
		panelAzioni.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		    
		 	// Barra di ricerca prodotti
		JButton btnSearch = new JButton("🔍");
		btnSearch.setPreferredSize(new Dimension(20, 20)); 
		btnSearch.setMargin(new Insets(0, 0, 0, 0));
		JTextField txtSearch = new JTextField("", 20);
		txtSearch.setPreferredSize(new Dimension(250, 20));
		panelSearch.add(txtSearch);
		panelSearch.add(btnSearch);
			// Filtri
		JButton btnFiltri = new JButton("Filtri");
		btnFiltri.setPreferredSize(new Dimension(80,20));
		btnFiltri.setFocusable(false);
		btnFiltri.addActionListener(e -> panelFiltri.setVisible(!panelFiltri.isVisible()));
		panelSearch.add(btnFiltri);
		 
		 	// Bottone per modificare un prodotto
		JButton btnModifica = new JButton("Modifica");
		btnModifica.addActionListener(e -> {
			try {
				controller.apriModificaProdottoView(null);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		panelBtn.add(btnModifica);
	
		    // Bottone per aggiungere
	    JButton btnAggiungi = new JButton("Aggiungi");
	    btnAggiungi.addActionListener(e -> {
			try {
				controller.apriAggiungiProdottoView();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
	    panelBtn.add(btnAggiungi); 
	    
	    	// Bottone per eliminare 
	    JButton btnElimina = new JButton("Elimina");
	    btnElimina.addActionListener(e -> controller.eliminaProdotto());
	    panelBtn.add(btnElimina);

		    // Bottone per chiudere la pagina
	    JButton btnIndietro = new JButton("Chiudi");
	    btnIndietro.addActionListener(e -> dispose());
	    panelBtn.add(btnIndietro);
	    
		    // Etichetta con il riepilogo dei prodotti
	    prodotti.setText(controller.setTxtRiepilogoProdotti());
	    panelTXT.setLayout(new FlowLayout(FlowLayout.LEFT));
	    panelTXT.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelTXT.add(prodotti);
        panelTXT.add(prodottiRic);

        	// 	Pannello per i filtri
        panelFiltri.setLayout(new BoxLayout(panelFiltri, BoxLayout.Y_AXIS));
        panelFiltri.setVisible(false);
        JPanel panelF = new JPanel();
        panelF.setLayout(new BoxLayout(panelF, BoxLayout.Y_AXIS));
        
        JToggleButton jtbClienti = new JToggleButton("Clienti ⌄");
        ingrandisciFont(jtbClienti);
        jtbClienti.setFocusPainted(false);
        jtbClienti.setBorder(null); 
        jtbClienti.setBackground(this.getBackground());
        
        panelClienti.setLayout(new BoxLayout(panelClienti, BoxLayout.Y_AXIS));
        panelClienti.setVisible(false);
        caricaClienti();
        
        panelF.add(jtbClienti);
        panelF.add(panelClienti);
        jtbClienti.addActionListener(e -> {
            boolean aperto = jtbClienti.isSelected();
            panelClienti.setVisible(aperto);
            if (aperto == true)
            	jtbClienti.setText("Categorie ⌃" );
            else
            	jtbClienti.setText("Categorie ⌄");
            panelClienti.revalidate();
            panelClienti.repaint();
        });
        
        JToggleButton jtbProdotti = new JToggleButton("Prodotti ⌄");
        ingrandisciFont(jtbProdotti);
        jtbProdotti.setFocusPainted(false);
        jtbProdotti.setBorder(null); 
        jtbProdotti.setBackground(this.getBackground());
        
        panelProdotti.setLayout(new BoxLayout(panelProdotti, BoxLayout.Y_AXIS));
        panelProdotti.setVisible(false);
        caricaProdotti();
        
        panelF.add(jtbProdotti);
        panelF.add(panelProdotti);
        jtbProdotti.addActionListener(e -> {
            boolean aperto = jtbProdotti.isSelected();
            panelProdotti.setVisible(aperto);
            if (aperto == true)
            	jtbProdotti.setText("Prodotti ⌃" );
            else
            	jtbProdotti.setText("Prodotti ⌄");
            panelProdotti.revalidate();
            panelProdotti.repaint();
        });
        
        JButton btnApplica = new JButton("Applica filtri");
        btnApplica.addActionListener(e -> {
        	List<String> prodottiFiltri = new ArrayList<String>();
        	for (Component c: panelClienti.getComponents()) 
        		if (c instanceof JCheckBox) 
        			if (((JCheckBox) c).isSelected())
        				prodottiFiltri = controller.ricercaProdottiFiltri(prodottiFiltri, ((JCheckBox) c).getText());
        	
        	for (Component c: panelProdotti.getComponents())
        		if (c instanceof JCheckBox)
        			if (((JCheckBox) c).isSelected())
        				prodottiFiltri = controller.ricercaProdottiFiltriP(prodottiFiltri, ((JCheckBox) c).getText());
        	
        	prodotti.setVisible(false);
        	prodottiRic.setVisible(false);
        	prodottiFil.setText(controller.setTxtRiepilogoProdottiConFiltri(prodottiFiltri));
        	prodottiFil.setVisible(true);
        	panelTXT.add(prodottiFil);
        	panelFiltri.add(btnReset);
        	
        	btnReset.addActionListener(e1 -> {
				prodottiFil.setVisible(false);
				prodotti.setVisible(true);
				txtSearch.setText("");
				panelSearch.remove(btnReset); 
			    revalidate(); 
			    repaint();
			});
			
			revalidate();
			repaint();
        	
        });
      
        panelFiltri.add(panelF);
        panelFiltri.add(btnApplica);
        panelFiltri.setPreferredSize(new Dimension(500, 800)); 
        panelFiltri.setMinimumSize(new Dimension(500, 800));
        
        panelAzioni.add(panelSearch, BorderLayout.WEST);
        panelAzioni.add(panelBtn, BorderLayout.EAST);
	    add(panelAzioni, BorderLayout.NORTH);  
	    JScrollPane scrollPane = new JScrollPane(panelTXT);
        add(scrollPane, BorderLayout.CENTER);
        add(panelFiltri, BorderLayout.WEST);

        btnSearch.addActionListener(e -> {
			 String prodottoCercato = txtSearch.getText();
			 prodotti.setVisible(false);
			 try {
				prodottiRic.setText(controller.setTxtRiepilogoProdottiConRicerca(prodottoCercato));
				panelTXT.add(prodottiRic);
				panelSearch.add(btnReset);
				prodottiRic.setVisible(true);
				
				btnReset.addActionListener(e1 -> {
					for (Component c: panelFiltri.getComponents()) {
						if (c instanceof JCheckBox)
							((JCheckBox) c).setSelected(false);
					}
					prodottiRic.setVisible(false);
					prodotti.setVisible(true);
					txtSearch.setText("");
					panelSearch.remove(btnReset); 
				    revalidate(); 
				    repaint();
				});
				
				revalidate();
				repaint();
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 });
        
	    revalidate();
	    repaint();
	}

	public void updateProdotti() throws SQLException { //PER AGGIORNAMENTO DATI NEL LISTINO
		prodotti.setText(controller.setTxtRiepilogoProdotti());
	}
	
	public void caricaClienti() throws SQLException {
		for (Cliente c: controller.getAllClienti()) {
			JCheckBox checkBox = new JCheckBox(c.getId());
			ingrandisciFont(checkBox);
			panelClienti.add(checkBox);
		}
	}
	
	public void caricaProdotti() throws SQLException {
		for(Prodotto p: controller.getAllProdotti()) {
			JCheckBox checkBox = new JCheckBox(p.getId());
			ingrandisciFont(checkBox);
			panelProdotti.add(checkBox);
		}
	}
	
	private void aggiornaDimensioniPannello() {
		panelFiltri.revalidate(); // Ricalcola le dimensioni del pannello
	    panelFiltri.repaint();    // Rende visibile il cambiamento
	    this.revalidate();        
	    this.repaint(); 
	}
	
	private void ingrandisciFont(Component com) {
		Font fontO = com.getFont();
		Font fontIng = fontO.deriveFont(fontO.getStyle(), 16f); 
		com.setFont(fontIng);
	}
	
}
