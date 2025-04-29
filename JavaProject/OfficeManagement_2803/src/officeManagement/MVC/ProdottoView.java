package officeManagement.MVC;
import java.awt.BorderLayout;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
	private boolean filtriVisibili = false;
	private JPanel panelClienti = new JPanel();
	private JPanel panelProdotti = new JPanel();
	private JPanel panelTipologia = new JPanel();

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
        panelFiltri.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelFiltri.setVisible(false);
        JScrollPane scrollPanelFiltri = new JScrollPane(panelFiltri);
        scrollPanelFiltri.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 
        JPanel panelF = new JPanel();
        panelF.setLayout(new BoxLayout(panelF, BoxLayout.Y_AXIS));
        
        JToggleButton jtbClienti = new JToggleButton("<html><font face = 'Segoe UI' size = 4>Clienti</font> <font face = 'Segoe UI' size = 2>▼</font></html>");
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
            	jtbClienti.setText("<html><font face = 'Segoe UI' size = 4>Clienti</font> <font face = 'Segoe UI' size = 2>▲</font></html>" );
            else
            	jtbClienti.setText("<html><font face = 'Segoe UI' size = 4>Clienti</font> <font face = 'Segoe UI' size = 2>▼</font></html>");
            panelClienti.revalidate();
            panelClienti.repaint();
        });
        
        JToggleButton jtbProdotti = new JToggleButton("<html><font face = 'Segoe UI' size = 4>Prodotti</font> <font face = 'Segoe UI' size = 2>▼</font></html>");
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
            	jtbProdotti.setText("<html><font face = 'Segoe UI' size = 4>Prodotti</font> <font face = 'Segoe UI' size = 2>▲</font></html>" );
            else
            	jtbProdotti.setText("<html><font face = 'Segoe UI' size = 4>Prodotti</font> <font face = 'Segoe UI' size = 2>▼</font></html>");
            panelProdotti.revalidate();
            panelProdotti.repaint();
        });
        
        JToggleButton jtbTipologia = new JToggleButton("<html><font face = 'Segoe UI' size = 4>Tipologia prodotti</font> <font face = 'Segoe UI' size = 2>▼</font></html>");
        jtbTipologia.setFocusPainted(false);
        jtbTipologia.setBorder(null);
        jtbTipologia.setBackground(this.getBackground());
        
        panelTipologia.setLayout(new BoxLayout(panelTipologia, BoxLayout.Y_AXIS));
        panelTipologia.setVisible(false);
        caricaTipologie();
        
        panelF.add(jtbTipologia);
        panelF.add(panelTipologia);
        jtbTipologia.addActionListener(e -> {
            boolean aperto = jtbTipologia.isSelected();
            panelTipologia.setVisible(aperto);
            if (aperto == true)
            	jtbTipologia.setText("<html><font face = 'Segoe UI' size = 4>Tipologia prodotti</font> <font face = 'Segoe UI' size = 2>▲</font></html>" );
            else
            	jtbTipologia.setText("<html><font face = 'Segoe UI' size = 4>Tipologia prodotti</font> <font face = 'Segoe UI' size = 2>▼</font></html>");
            panelTipologia.revalidate();
            panelTipologia.repaint();
        });
        
        JButton btnResetF = new JButton("Azzera filtri");
        btnResetF.setVisible(false);
        JButton btnApplica = new JButton("Applica filtri");
        btnApplica.addActionListener(e -> {
        	List<String> prodottiFiltri = new ArrayList<String>();
        	boolean almenoUnSelezionato = false;
        	boolean cliProdSelezionato = false;
        	String tipologiaSelezionata = "";
        	for (Component c: panelTipologia.getComponents())
        		if (c instanceof JCheckBox)
        			if (((JCheckBox) c).isSelected()) {
        				almenoUnSelezionato = true;
        				tipologiaSelezionata += ((JCheckBox) c).getText();
        			}
        	       	
        	for (Component c: panelClienti.getComponents()) 
        		if (c instanceof JCheckBox) 
        			if (((JCheckBox) c).isSelected()) {
        				almenoUnSelezionato = true;
        				cliProdSelezionato = true;
        				prodottiFiltri = controller.ricercaProdottiFiltri(prodottiFiltri, ((JCheckBox) c).getText(), tipologiaSelezionata);
        			}
        	
        	for (Component c: panelProdotti.getComponents())
        		if (c instanceof JCheckBox)
        			if (((JCheckBox) c).isSelected()) {
        				almenoUnSelezionato = true;
        				cliProdSelezionato = true;
        				prodottiFiltri = controller.ricercaProdottiFiltriP(prodottiFiltri, ((JCheckBox) c).getText(), tipologiaSelezionata);
        				}
        	
        	if (!cliProdSelezionato ) {
        		prodottiFiltri = controller.ricercaProdottiFiltriT(prodottiFiltri, tipologiaSelezionata);
        	}
        	if (almenoUnSelezionato == true) {
	        	prodotti.setVisible(false);
	        	prodottiRic.setVisible(false);
	        	prodottiFil.setText(controller.setTxtRiepilogoProdottiConFiltri(prodottiFiltri));
	        	prodottiFil.setVisible(true);
	        	panelTXT.add(prodottiFil);
	        	btnResetF.setVisible(true);
				
				revalidate();
				repaint();
        	}
        });
        
        btnResetF.addActionListener(e1 -> {
			prodottiFil.setVisible(false);
			prodotti.setVisible(true);
			for (Component c: panelProdotti.getComponents()) {
				if (c instanceof JCheckBox) 
					((JCheckBox) c).setSelected(false);
				
			}
			
			for (Component c: panelTipologia.getComponents()) 
				if (c instanceof JCheckBox)
					((JCheckBox) c).setSelected(false);
			
			for (Component c: panelClienti.getComponents()) {
				if (c instanceof JCheckBox) 
					((JCheckBox) c).setSelected(false);
				
			}
			btnResetF.setVisible(false);
			jtbClienti.setText("<html><font face = 'Segoe UI' size = 4>Clienti</font> <font face = 'Segoe UI' size = 2>▼</font></html>");
			jtbClienti.setSelected(false);
			jtbProdotti.setText("<html><font face = 'Segoe UI' size = 4>Prodotti</font> <font face = 'Segoe UI' size = 2>▼</font></html>");
			jtbProdotti.setSelected(false);
			jtbTipologia.setText("<html><font face = 'Segoe UI' size = 4>Tipologia prodotti</font> <font face = 'Segoe UI' size = 2>▼</font></html>");
			jtbTipologia.setSelected(false);
			panelClienti.setVisible(false);
			panelProdotti.setVisible(false);
			panelTipologia.setVisible(false);
			
		    panelFiltri.revalidate(); 
		    panelFiltri.repaint();
		});
        
        panelFiltri.add(panelF);
        panelFiltri.add(btnApplica);
        panelFiltri.add(btnResetF);
        panelFiltri.setPreferredSize(new Dimension(300, 800)); 
        panelFiltri.setMinimumSize(new Dimension(300, 800));
        
        panelAzioni.add(panelSearch, BorderLayout.WEST);
        panelAzioni.add(panelBtn, BorderLayout.EAST);
	    add(panelAzioni, BorderLayout.NORTH);  
	    JScrollPane scrollPane = new JScrollPane(panelTXT);
        add(scrollPane, BorderLayout.CENTER);
        add(panelFiltri, BorderLayout.WEST);
        
        JButton btnReset = new JButton("Azzera ricerca");
        btnSearch.addActionListener(e -> {
			 String prodottoCercato = txtSearch.getText();
			 prodotti.setVisible(false);
			 try {
				prodottiRic.setText(controller.setTxtRiepilogoProdottiConRicerca(prodottoCercato));
				panelTXT.add(prodottiRic);
				panelSearch.add(btnReset);
				prodottiRic.setVisible(true);
				
				btnReset.addActionListener(e1 -> {
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
	
	public void caricaTipologie() {
		JCheckBox checkBoxL = new JCheckBox("Conto lavoro");
		JCheckBox checkBoxV = new JCheckBox("Conto vendita");
		ingrandisciFont(checkBoxV);
		ingrandisciFont(checkBoxL);
		panelTipologia.add(checkBoxV);
		panelTipologia.add(checkBoxL);
	}
	
	private void aggiornaDimensioniPannello() {
		panelFiltri.revalidate(); // Ricalcola le dimensioni del pannello
	    panelFiltri.repaint();    // Rende visibile il cambiamento
	    this.revalidate();        
	    this.repaint(); 
	}
	
	private void ingrandisciFont(Component com) {
		Font fontIng = new Font("Segoe UI", Font.PLAIN, 14);
		com.setFont(fontIng);	
	}
	
}
