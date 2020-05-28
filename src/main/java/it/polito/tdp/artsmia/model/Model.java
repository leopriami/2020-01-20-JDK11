package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private SimpleWeightedGraph<Integer, DefaultWeightedEdge> grafo;
	private ArtsmiaDAO dao;
	private List<Arco> archi;
	private List<Integer> artisti;
	private List<Integer> ottimo;
	private int pesoOttimo = -1;
	
	public Model() {
		this.dao = new ArtsmiaDAO();
	}
	
	public void creaGrafo(String ruolo) {
		archi = new ArrayList<>(dao.adiacenze(ruolo));
		artisti = new ArrayList<>(dao.artisti(ruolo));
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for(int a : artisti) grafo.addVertex(a);
		for(Arco a : archi) {
			
				Graphs.addEdge(grafo, a.getA1(), a.getA2(), a.getPeso());
			
		}
	}
	
	public List<String> ruoli(){
		return dao.ruoli();
	}

	public List<Arco> getArchi() {
		return archi;
	}

	public void setArchi(List<Arco> archi) {
		this.archi = archi;
	}

	public SimpleWeightedGraph<Integer, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(SimpleWeightedGraph<Integer, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}

	public List<Integer> getArtisti() {
		return artisti;
	}

	public void setArtisti(List<Integer> artisti) {
		this.artisti = artisti;
	}
	
	public List<Integer> calcolaPercorso(Integer artista){
		List<Integer> parziale = new ArrayList<Integer>();
		List<Integer> pesi = new ArrayList<Integer>();
		for(DefaultWeightedEdge e : grafo.edgesOf(artista)) {
			pesi.add((int)grafo.getEdgeWeight(e));
		}
		parziale.add(artista);
		ottimo = new ArrayList<Integer>(parziale);
		for(Integer p : pesi) {
			cerca(parziale, artista, p);
		}
		return ottimo;
	}

	// cammino aciclico con piu' nodi possibile
	private void cerca(List<Integer> parziale, Integer artista, Integer peso) {
		for(Integer a : Graphs.neighborSetOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(a) && peso==grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), a))) {
				parziale.add(a);
				cerca(parziale, artista, peso);
				parziale.remove(parziale.size()-1);
			}
		}
		if(parziale.size()>ottimo.size()) {
			ottimo = new ArrayList<Integer>(parziale);
			pesoOttimo = peso;
		}
	}

	public List<Integer> getOttimo() {
		return ottimo;
	}

	public void setOttimo(List<Integer> ottimo) {
		this.ottimo = ottimo;
	}

	public int getPesoOttimo() {
		return pesoOttimo;
	}

	public void setPesoOttimo(int pesoOttimo) {
		this.pesoOttimo = pesoOttimo;
	}

}
