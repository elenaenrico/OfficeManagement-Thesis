package officeManagement.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

import javax.swing.JOptionPane;

import java.util.*;
import officeManagement.model.*;

public class DAO {
	
	//METODI PER PRENDERE DATI DAL DATABASE
	
	public static List<Ordine> allOrdini() {
		try(Connection cnx = DatabaseConnection.getConnection()) {
					
			if (cnx != null) {
				Statement st = cnx.createStatement();
				ResultSet rs = st.executeQuery("select * from ordine");	
				
				List<Ordine> ordini = new ArrayList<Ordine>();
				while (rs.next()) {
					String id = rs.getString("id");
					String id_cliente = rs.getString("id_cliente");
					String id_prodotto = rs.getString("id_prodotto");
					int quantita = rs.getInt("quantità");
					Date data_ordine = rs.getDate("data_ordine");
					Date data_scadenza = rs.getDate("data_scadenza");
					Date data_completamento = rs.getDate("data_completamento");
					
					Ordine o = new Ordine(id, id_cliente, id_prodotto, quantita, data_ordine, data_scadenza, data_completamento);
					ordini.add(o);
				}
				
				
				return ordini;
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static List<Prodotto> allProdotti() throws SQLException {
	    List<Prodotto> prodotti = new ArrayList<>();
	    
	    try (Connection cnx = DatabaseConnection.getConnection()) {
	        if (cnx != null) {
	            Statement st = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	            Statement st2 = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	            Statement st3 = cnx.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	            ResultSet rsp = st.executeQuery("select * from prodotto");
	            ResultSet rspl = st2.executeQuery("select * from prodotto_conto_lavoro");
	            ResultSet rspv = st3.executeQuery("select * from prodotto_conto_vendita");

	            Set<String> idsContoLavoro = new HashSet<>();
	            Map<String, ProdottoContoVendita> contoVenditaMap = new HashMap<>();

	            while (rspl.next()) {
	                idsContoLavoro.add(rspl.getString("id"));
	            }

	            while (rspv.next()) {
	                String id = rspv.getString("id");
	                String idMateriaPrima = rspv.getString("id_materia_prima");
	                double quantita = rspv.getDouble("quantita_materia_prima");
	                contoVenditaMap.put(id, new ProdottoContoVendita(id, null, null, 0,  null, idMateriaPrima, quantita));
	            }
	            
	            while (rsp.next()) {
	                String id = rsp.getString("id");
	                String nome = rsp.getString("nome");
	                String descrizione = rsp.getString("descrizione");
	                String idCliente = rsp.getString("id_cliente");
	                double prezzo = rsp.getDouble("prezzo");

	                if (idsContoLavoro.contains(id)) {
	                    prodotti.add(new ProdottoContoLavoro(id, nome, descrizione, prezzo, idCliente));
	                } else if (contoVenditaMap.containsKey(id)) {
	                    ProdottoContoVendita base = contoVenditaMap.get(id);
	                    prodotti.add(new ProdottoContoVendita(id, nome, descrizione, prezzo, idCliente,
	                            base.getIdMateriaPrima(), base.getQuantitaMateriaPrima()));
	                } else {
	                   
	                    prodotti.add(new Prodotto(id, nome, descrizione, prezzo, idCliente));
	                }
	            }
	        }
	    }
	    return prodotti.isEmpty() ? null : prodotti;
	}
	
	
	public static List<Cliente> allClienti() {
		Connection cnx = DatabaseConnection.getConnection();
		try {
			Statement st = cnx.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM cliente");
			
			List<Cliente> clienti = new ArrayList<Cliente>();
			while(rs.next()) {
				String id = rs.getString("id");
				String nome = rs.getString("nome");
				String email = rs.getString("email");
				String telefono = rs.getString("telefono");
				
				Cliente c = new Cliente(id, nome, email, telefono);
				clienti.add(c);
			}
			return clienti;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<Fornitore> allFornitori() {
		try (Connection cnx = DatabaseConnection.getConnection()) {
			List<Fornitore> fornitori = new ArrayList<Fornitore>();
			Statement st = cnx.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM fornitore");
			
			while(rs.next()) {
				String id = rs.getString("id");
				String nome = rs.getString("nome");
				String email = rs.getString("email");
				String telefono = rs.getString("telefono");
				
				Fornitore f = new Fornitore(id, nome, email, telefono);
				fornitori.add(f);
			}
			
			return fornitori;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<MateriaPrima> allMateriePrime() {
		try (Connection cnx = DatabaseConnection.getConnection()) {
			List<MateriaPrima> materieP = new ArrayList<MateriaPrima>();
			Statement st = cnx.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM materia_prima");
			
			while (rs.next()) {
				String id = rs.getString("id");
				String idFornitore = rs.getString("id_fornitore");
				double prezzo = rs.getDouble("prezzo");
				String nome = rs.getString("nome");
				
				MateriaPrima mp = new MateriaPrima(id, idFornitore, nome, prezzo);
				materieP.add(mp);
			}
			return materieP;
		}
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//METODI PER INSERIRE NUOVI DATI NEL DATABASE
	
	public static boolean inserisciOrdine(Ordine o) throws SQLException{
		Connection cnx = DatabaseConnection.getConnection();
		try {
		String sql = "INSERT INTO ordine (id, id_cliente, id_prodotto, quantità, data_scadenza, data_ordine) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement statement = cnx.prepareStatement(sql);
		statement.setString(1, o.getId()); 
        statement.setString(2, o.getId_cliente()); 
        statement.setString(3, o.getId_prodotto());
        statement.setInt(4, o.getQuantita()); 
        statement.setDate(5,  new java.sql.Date(o.getData_scadenza().getTime()));
        statement.setDate(6,  new java.sql.Date(o.getData_ordine().getTime()));
        statement.executeUpdate();
        return true;
        }
		catch (SQLException e) {
			// JOptionPane.showMessageDialog(null, "Errore durante l'inserimento dell'ordine: " + e.getMessage());
			return false;
		}
        
	}


	public static boolean inserisciProdotto(Prodotto p) {
		Connection cnx = DatabaseConnection.getConnection();
		try {
			if (p instanceof ProdottoContoLavoro) {
				String sql1 = "INSERT INTO prodotto (id, nome, descrizione, prezzo, id_cliente) VALUES(?, ?, ?, ?, ?)";
				String sql2 = "INSERT INTO prodotto_conto_lavoro (id) VALUES (?)";
				PreparedStatement st1 = cnx.prepareStatement(sql1);
				PreparedStatement st2 = cnx.prepareStatement(sql2);
				
				st1.setString(1, p.getId());
				st1.setString(2, p.getNome());
				st1.setString(3, p.getDescrizione());
				st1.setDouble(4, p.getPrezzo());
				st1.setString(5, p.getIdCliente());
				st1.executeUpdate();
				
				st2.setString(1, p.getId());
				st2.executeUpdate();
			
			}
			else if (p instanceof ProdottoContoVendita) {
				String sql1 = "INSERT INTO prodotto (id, nome, descrizione, prezzo, id_cliente) VALUES(?, ?, ?, ?, ?)";
				String sql2 = "INSERT INTO prodotto_conto_vendita (id, id_materia_prima, quantita_materia_prima) VALUES (?, ?, ?)";
				PreparedStatement st1 = cnx.prepareStatement(sql1);
				PreparedStatement st2 = cnx.prepareStatement(sql2);
				
				st1.setString(1, p.getId());
				st1.setString(2, p.getNome());
				st1.setString(3, p.getDescrizione());
				st1.setDouble(4, p.getPrezzo());
				st1.setString(5, p.getIdCliente());
				st1.executeUpdate();
				
				st2.setString(1, p.getId());
				st2.setString(2, ((ProdottoContoVendita) p).getIdMateriaPrima());
				st2.setDouble(3, ((ProdottoContoVendita) p).getQuantitaMateriaPrima());
				st2.executeUpdate();
				
			}
			return true;
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Errore durante l'inserimento del prodotto: " + e.getMessage());
			return false;
		}
		
	}

	public static boolean inserisciCliente(String idC, String nomeC, String emailC, String telC, String indirizzoC) {
		try (Connection cnx = DatabaseConnection.getConnection()) {
			String sql = "INSERT INTO cliente (id, nome, email, telefono, indirizzo) VALUES (?, ?, ?, ?, ?)";
			PreparedStatement st = cnx.prepareStatement(sql);
			
			st.setString(1, idC);
			st.setString(2, nomeC);
			st.setString(3, emailC);
			st.setString(4, telC);
			st.setString(5, indirizzoC);
			st.executeUpdate();
			
			return true;
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Errore durante l'inserimento del cliente: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean inserisciMateriaPrima(String id, String idF, String nome, double prezzo) {
		try(Connection cnx = DatabaseConnection.getConnection()) {
			String sql = "INSERT INTO materia_prima(id, id_fornitore, nome, prezzo) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = cnx.prepareStatement(sql);
			
			ps.setString(1, id);
			ps.setString(2, idF);
			ps.setString(3, nome);
			ps.setDouble(4, prezzo);
			ps.executeUpdate();
			
			return true;
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Errore durante l'inserimento della materia prima: " + e.getMessage());
			return false;
		}
	}
	
	public static boolean inserisciFornitore(String id, String nome, String email, String tel) {
		try(Connection cnx = DatabaseConnection.getConnection()) {
			String sql = "INSERT INTO fornitore(id, nome, email, telefono) VALUES (?, ?, ?, ?)";
			PreparedStatement ps = cnx.prepareStatement(sql);
			
			ps.setString(1, id);
			ps.setString(2, nome);
			ps.setString(3, email);
			ps.setString(4, tel);
			ps.executeUpdate();
			
			return true;
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Errore durante l'inserimento del fornitore: " + e.getMessage());
			return false;
		}
	}
	
	//METODI PER AGGIORNARE DATI PRESENTI NEL DATABASE: 
	
	public static boolean aggiornaOrdine(String idOrdine, String idCliente, String idProdotto, Date dataOSQL,
			Date dataSSQL, Date dataC, int quantita) {
		Connection cnx = DatabaseConnection.getConnection();
		try {
			String sql = "UPDATE ordine SET id_cliente = ?, id_prodotto = ?, data_ordine = ?, data_scadenza = ?, data_completamento = ?, quantità = ? WHERE id = ?";
			PreparedStatement st = cnx.prepareStatement(sql);
			
			st.setString(1, idCliente);
			st.setString(2, idProdotto);
			st.setDate(3, dataOSQL);
			st.setDate(4, dataSSQL);
			st.setDate(5, dataC);
			st.setInt(6, quantita);
			st.setString(7, idOrdine);
			st.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Errore durante l'aggiornamento dell'ordine: " + e.getMessage());
			return false;
		}
		
	}
	
	public static boolean modificaProdottoCL(String idP, String nomeP, String descrizioneP, double prezzoP, String idCliente) {
		try (Connection cnx = DatabaseConnection.getConnection()) {
			String sql = "UPDATE prodotto SET nome = ?, descrizione = ?, prezzo = ?, id_cliente = ? WHERE id = ?";
			PreparedStatement st = cnx.prepareStatement(sql);
			
			st.setString(1, nomeP);
			st.setString(2, descrizioneP);
			st.setDouble(3, prezzoP);
			st.setString(4, idCliente);
			st.setString(5, idP);
			st.executeUpdate();
			
			return true;
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Errore durante l'aggiornamento del prodotto");
			return false;
		}
	}
	
	public static boolean modificaProdottoCV(String idP, String nomeP, String descrizioneP, double prezzoP, String materiaPrima, double quantita, String idCliente) {
		try (Connection cnx = DatabaseConnection.getConnection()) {
			String sql = "UPDATE prodotto SET nome = ?, descrizione = ?, prezzo = ?, id_cliente = ? WHERE id = ?";
			PreparedStatement st = cnx.prepareStatement(sql);
			
			st.setString(1, nomeP);
			st.setString(2, descrizioneP);
			st.setDouble(3, prezzoP);
			st.setString(4, idCliente);
			st.setString(5, idP);
			st.executeUpdate();
			
			String sql1 = "UPDATE prodotto_conto_vendita SET id_materia_prima = ?, quantita_materia_prima = ? WHERE id = ?";
			PreparedStatement st1 = cnx.prepareStatement(sql1);
			
			st1.setString(1, materiaPrima);
			st1.setDouble(2, quantita);
			st1.setString(3, idP);
			st1.executeUpdate();
			
			return true;
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Errore durante l'aggiornamento del prodotto");
			return false;
		}
	}
	
	public static boolean eliminaProdotto(String id, String tipo) {
		try (Connection cnx = DatabaseConnection.getConnection()) {
			
			String sql = "DELETE FROM prodotto WHERE id = ?";
			PreparedStatement st = cnx.prepareStatement(sql);
			
			st.setString(1, id);
			st.executeUpdate();
			
			String sql1 = "DELETE FROM " + tipo + " WHERE id = ?";
			PreparedStatement st1 = cnx.prepareStatement(sql1);
			
			st1.setString(1, id);
			st1.executeUpdate();
		
			return true;
		}
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Errore durante l'eliminazione del prodotto");
			return false;
		}
	}
	
	//METODI DI RICERCA DATI
	
	public static List<Prodotto> ricercaProdotti(String inputString) {
		try (Connection cnx = DatabaseConnection.getConnection()) {
	        List<Prodotto> prodottiTrovati = new ArrayList<Prodotto>();
	        
	        boolean isNumeric = false;
	        double prezzo = 0;
	       
	        try {
	            prezzo = Double.parseDouble(inputString);
	            isNumeric = true; 
	        } catch (NumberFormatException e) {
	        }

	        String query;
	        if (isNumeric) {
	            query = "SELECT * FROM prodotto WHERE nome LIKE ? OR id LIKE ? OR descrizione LIKE ? OR prezzo = ? OR id_cliente LIKE ?";
	        } else {
	            query = "SELECT * FROM prodotto WHERE nome LIKE ? OR id LIKE ? OR descrizione LIKE ? OR id_cliente LIKE ?";
	        }
	        
	        try (PreparedStatement ps = cnx.prepareStatement(query)) {
	            ps.setString(1, "%" + inputString + "%");
	            ps.setString(2, "%" + inputString + "%");
	            ps.setString(3, "%" + inputString + "%");
	            ps.setString(5, "%" + inputString + "%");

	            if (isNumeric) {
	                ps.setDouble(4, prezzo);  
	            }

	            ResultSet rs = ps.executeQuery();
	            
	            while (rs.next()) {
	                String id = rs.getString("id");
	                String nome = rs.getString("nome");
	                String descrizione = rs.getString("descrizione");
	                double prezzoProdotto = rs.getDouble("prezzo");
	                String idCliente = rs.getString("id_cliente");
	                
	                Prodotto p = new Prodotto(id, nome, descrizione, prezzoProdotto, idCliente);
	                prodottiTrovati.add(p);
	            }
	        }
	        
	        return prodottiTrovati;
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "È stato riscontrato un problema nell'esecuzione del comando.");
	        return null;
	    }
	}


	


}