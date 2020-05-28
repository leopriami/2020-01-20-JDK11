package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola artisti connessi\n");
    	if(this.model.getGrafo()==null) {
    		txtResult.appendText("Prima il grafo");
    		return;
    	}
    	for(Arco a : this.model.getArchi()) {
    		txtResult.appendText(a.getA1()+" "+a.getA2()+" "+a.getPeso()+"\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso\n");
    	Integer artista = -1;
    	try {
    		artista = Integer.parseInt(txtArtista.getText());
    	}
    	catch(Exception e) {
    		txtResult.appendText("Inserisci codice artista");
    		return;
    	}
    	if(!this.model.getArtisti().contains(artista)) {
    		txtResult.appendText("Artista inesistente");
    		return;
    	}
    	this.model.calcolaPercorso(artista);
    	for(Integer a : this.model.getOttimo()) {
    		txtResult.appendText(Integer.toString(a)+"->");
    	}
    	txtResult.appendText("\npeso ottimo: "+Integer.toString(this.model.getPesoOttimo()));
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo\n");
    	String s = boxRuolo.getValue();
    	if(s==null) {
    		txtResult.appendText("Scegli un ruolo\n");
    		return;
    	}
    	this.model.creaGrafo(s);
    	txtResult.appendText("Grafo generato\n");
    }

    public void setModel(Model model) {
    	this.model = model;
    	boxRuolo.getItems().addAll(this.model.ruoli());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
