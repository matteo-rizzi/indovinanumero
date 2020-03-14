package it.polito.tdp.indovinanumero;

import java.net.URL;
import java.security.InvalidParameterException;
import java.util.ResourceBundle;

import it.polito.tdp.indovinanumero.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FXMLController {
	
	private Model model;

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextArea txtRisultato;

	@FXML
	private Button btnNuova;

	@FXML
	private TextField txtRimasti;

	@FXML
	private HBox layoutTentativo;

	@FXML
	private TextField txtTentativi;

	@FXML
	private Button btnProva;

	@FXML
	void doNuova(ActionEvent event) {
		// comunico al modello che voglio iniziare una nuova partita
		this.model.nuovaPartita();
		// gestione dell'interfaccia
		layoutTentativo.setDisable(false);
		txtRisultato.clear();
		txtRimasti.setText(Integer.toString(this.model.getTMAX()));
	}

	@FXML
	void doTentativo(ActionEvent event) {
		// leggere l'input dell'utente
		String ts = txtTentativi.getText();
		txtTentativi.clear();
		int tentativo;
		try {
			tentativo = Integer.parseInt(ts);
		} catch (NumberFormatException nfe) {
			txtRisultato.appendText("Devi inserire un numero!\n");
			return;
		}
		
		int risultato = -1;
		try {
			risultato = this.model.tentativo(tentativo);
		} catch (IllegalStateException se) {
			txtRisultato.appendText(se.getMessage());
			return;
		} catch (InvalidParameterException pe) {
			txtRisultato.appendText(pe.getMessage());
			return;
		}
		
		if (risultato == 0) {
			txtRisultato.appendText("Hai vinto!!! Hai vinto con " + this.model.getTentativiFatti() + " tentativi");
		}
		else if (risultato == -1) {
			txtRisultato.appendText("Tentativo troppo BASSO\n");
		}
		else {
			txtRisultato.appendText("Tentativo troppo ALTO\n");
		}
		
		int tentativiRimasti = this.model.getTMAX() - this.model.getTentativiFatti();
		if (tentativiRimasti == 0) {
			txtRisultato.appendText("Hai perso!!! il numero da indovinare è " + this.model.getSegreto());
		}
		txtRimasti.setText(""+tentativiRimasti);
	}

	@FXML
	void initialize() {
		assert txtRisultato != null : "fx:id=\"txtRisultato\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnNuova != null : "fx:id=\"btnNuova\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtRimasti != null : "fx:id=\"txtRimasti\" was not injected: check your FXML file 'Scene.fxml'.";
		assert layoutTentativo != null : "fx:id=\"layoutTentativo\" was not injected: check your FXML file 'Scene.fxml'.";
		assert txtTentativi != null : "fx:id=\"txtTentativi\" was not injected: check your FXML file 'Scene.fxml'.";
		assert btnProva != null : "fx:id=\"btnProva\" was not injected: check your FXML file 'Scene.fxml'.";

	//	this.model = new Model();
	//  Non conveniente, vi è una fortissima correlazione tra Controller e Model
	}
	
	public void setModel(Model model) {
		this.model = model;
	}
}
