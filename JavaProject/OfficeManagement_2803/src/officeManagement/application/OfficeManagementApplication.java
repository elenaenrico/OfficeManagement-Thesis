package officeManagement.application;

import java.sql.SQLException;

import javax.swing.SwingUtilities;
import officeManagement.DAO.*;
import officeManagement.MVC.*;
import officeManagement.model.*;

// INSERIRE LA VIEW DELLA FORNITURA 

public class OfficeManagementApplication {

	public static void main(String[] args) throws SQLException {
		
		
	    Model model = new Model();
	    View view = new View();
	    OrdiniView ordiniView = new OrdiniView();
	    ProdottoView prodottoView = new ProdottoView();
	    CompletaOrdineView completaOrdineView = new CompletaOrdineView();
	    AggiungiOrdineView aggiungiOrdineView = new AggiungiOrdineView();
	    AggiungiProdottoView aggiungiProdottoView = new AggiungiProdottoView();
	    AggiungiClienteView aggiungiClienteView = new AggiungiClienteView();
	    AggiungiMateriaPrimaView aggiungiMateriaPrimaView = new AggiungiMateriaPrimaView();
	    AggiungiFornitoreView aggiungiFornitoreView = new AggiungiFornitoreView();
	    ModificaProdottoView modificaProdottoView = new ModificaProdottoView();
	    OrdineFornituraView ordineFornituraView = new OrdineFornituraView();
	    Controller controller = new Controller(model, view, ordiniView, completaOrdineView, aggiungiOrdineView, 
	    		aggiungiProdottoView, prodottoView, aggiungiClienteView, aggiungiMateriaPrimaView, aggiungiFornitoreView, 
	    		modificaProdottoView, ordineFornituraView);
	    
	    view.setController(controller);
	    ordiniView.setController(controller);
	    prodottoView.setController(controller);
	    completaOrdineView.setController(controller);
	    aggiungiOrdineView.setController(controller);
	    aggiungiProdottoView.setController(controller); 
	    aggiungiClienteView.setController(controller);
	    aggiungiMateriaPrimaView.setController(controller);
	    aggiungiFornitoreView.setController(controller);
	    modificaProdottoView.setController(controller);
	    ordineFornituraView.setController(controller);
	    
	    ordiniView.setVisible(false);
	    completaOrdineView.setVisible(false);
	    aggiungiOrdineView.setVisible(false);
	    aggiungiProdottoView.setVisible(false);
	    aggiungiClienteView.setVisible(false);
	    aggiungiMateriaPrimaView.setVisible(false);
	    aggiungiFornitoreView.setVisible(false);
	    modificaProdottoView.setVisible(false);
	    ordineFornituraView.setVisible(false);
	    
	    view.LoadInterface();
	    //ordiniView.LoadInterface();
	    //completaOrdineView.LoadInterface();
	    //aggiungiOrdineView.LoadInterface();
	    
	    view.setVisible(true);
	    controller.startPeriodicUpdates();
	    	    
	}

}
