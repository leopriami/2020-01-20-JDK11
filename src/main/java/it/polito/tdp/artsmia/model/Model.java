package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Model {
	
	private SimpleWeightedGraph<Vertex, DefaultWeightedEdge> grafo;
	private List<Vertex> nodi = new ArrayList<>();
	private List<Edge> archi = new ArrayList<>();
	private List<Cammino> cammini;
	
	private List<Vertex> camminoOttimo;
	private double pesoOttimo = 0;
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		for(Vertex v : nodi) grafo.addVertex(v);
		for(Edge e : archi) {
			Graphs.addEdge(grafo, e.getV1(), e.getV2(), e.getWeight());
		}
	}
	
	public List<Vertex> cercaOttimo(Vertex v1, Vertex v2) {
		List<Vertex> parziale = new ArrayList<>();
		parziale.add(v1);
		camminoOttimo = new ArrayList<>();
		//cercaCamminoMassimoLunghezza(parziale, v2);
		//cercaCamminoMassimoPeso(parziale, v2, 0);
		//cercaCamminoMassimoLunghezza1Vertice(parziale);
		cercaCamminoMassimoPeso1Vertice(parziale, 0);
		//cercaCamminoMassimoLunghezzaPesiCrescenti(parziale, 0);
		return camminoOttimo;
	}
	
	
	
	public List<Cammino> cercaCamminiOrdinati(Vertex v1, Vertex v2){
		List<Vertex> parziale = new ArrayList<>();
		parziale.add(v1);
		cammini = new ArrayList<>();
		cercaCammini(parziale, v2, 0);
		Collections.sort(cammini, new Comparator<Cammino>() {
			public int compare(Cammino c1, Cammino c2) {
				if(c1.getWeight() > c2.getWeight()) {
					return 1;
				}
				else return -1;
			}
		});
		return cammini;
	}

	
	
	private void cercaCamminoMassimoLunghezza(List<Vertex> parziale, Vertex v2) {
		if(parziale.contains(v2)) {
			if(parziale.size() > camminoOttimo.size()) {
				camminoOttimo = new ArrayList<>(parziale);
			}
			return;
		}
		for(Vertex v : Graphs.neighborSetOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(v)) {
				parziale.add(v);
				cercaCamminoMassimoLunghezza(parziale, v2);
				parziale.remove(parziale.size()-1);
			}
		}
	}
	
	

	private void cercaCamminoMassimoPeso(List<Vertex> parziale, Vertex v2, double peso) {
		if(parziale.contains(v2)) {
			if(peso > pesoOttimo) {
				camminoOttimo = new ArrayList<>(parziale);
				pesoOttimo = peso;
			}
			return;
		}
		for(Vertex v : Graphs.neighborSetOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(v)) {
				double pesoUltimoAggiunto = grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), v));
				peso = peso + pesoUltimoAggiunto;
				parziale.add(v);
				cercaCamminoMassimoPeso(parziale, v2, peso);
				parziale.remove(parziale.size()-1);
				peso = peso - pesoUltimoAggiunto;
			}
		}
	}
	
	
	
	private void cercaCamminoMassimoLunghezza1Vertice(List<Vertex> parziale) {
		for(Vertex v : Graphs.neighborSetOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(v)) {
				parziale.add(v);
				cercaCamminoMassimoLunghezza1Vertice(parziale);
				parziale.remove(parziale.size()-1);
			}
		}
		if(parziale.size() > camminoOttimo.size()) {
			camminoOttimo = new ArrayList<>(parziale);
		}
	}
	
	
	
	private void cercaCamminoMassimoPeso1Vertice(List<Vertex> parziale, double peso) {
		for(Vertex v : Graphs.neighborSetOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(v)) {
				double pesoUltimoAggiunto = grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), v));
				peso = peso + pesoUltimoAggiunto;
				parziale.add(v);
				cercaCamminoMassimoPeso1Vertice(parziale, peso);
				parziale.remove(parziale.size()-1);
				peso = peso - pesoUltimoAggiunto;
			}
		}
		if(peso > pesoOttimo) {
			camminoOttimo = new ArrayList<>(parziale);
			pesoOttimo = peso;
		}
	}
	
	
	
	private void cercaCammini(List<Vertex> parziale, Vertex v2, double peso) {
		if(parziale.contains(v2)) {
			cammini.add(new Cammino(parziale, peso));
			return;
		}
		for(Vertex v : Graphs.neighborSetOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(v)) {
				double pesoUltimoAggiunto = grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), v));
				peso = peso + pesoUltimoAggiunto;
				parziale.add(v);
				cercaCammini(parziale, v2, peso);
				parziale.remove(parziale.size()-1);
				peso = peso - pesoUltimoAggiunto;
			}
		}
	}
	
	
	
	private void cercaCamminoMassimoLunghezzaPesiCrescenti(List<Vertex> parziale, double peso) {
		for(Vertex v : Graphs.neighborSetOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(v)) {
				double pesoUltimoAggiunto = grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), v));
				if(peso <= pesoUltimoAggiunto) {
					peso = pesoUltimoAggiunto;
					parziale.add(v);
					cercaCamminoMassimoLunghezzaPesiCrescenti(parziale, peso);
					parziale.remove(parziale.size()-1);
					peso = peso - pesoUltimoAggiunto;
				}
			}
		}
		if(parziale.size() > camminoOttimo.size()) {
			camminoOttimo = new ArrayList<>(parziale);
		}
	}
	
	
	
	public List<Vertex> componenteConnessa(Vertex v1){
		List<Vertex> componente = new ArrayList<>();
		cercaComponente(v1, componente);
		return componente;
	}
	
	
	
	private void cercaComponente(Vertex v1, List<Vertex> componente) {
		componente.add(v1);
		for(Vertex v : Graphs.neighborListOf(grafo, v1)) {
			if(!componente.contains(v)) {
				cercaComponente(v, componente);
			}
		}
	}
	
	
	
	public int numeroComponenti() {
		ConnectivityInspector<Vertex, DefaultWeightedEdge> ci = new ConnectivityInspector<Vertex, DefaultWeightedEdge>(grafo);
		return ci.connectedSets().size();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public SimpleWeightedGraph<Vertex, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public void setGrafo(SimpleWeightedGraph<Vertex, DefaultWeightedEdge> grafo) {
		this.grafo = grafo;
	}

	public List<Vertex> getNodi() {
		return nodi;
	}

	public void setNodi(List<Vertex> nodi) {
		this.nodi = nodi;
	}

	public List<Edge> getArchi() {
		return archi;
	}

	public void setArchi(List<Edge> archi) {
		this.archi = archi;
	}

	public List<Vertex> getCamminoOttimo() {
		return camminoOttimo;
	}

	public void setCamminoOttimo(List<Vertex> camminoOttimo) {
		this.camminoOttimo = camminoOttimo;
	}

	public double getPesoOttimo() {
		return pesoOttimo;
	}

	public void setPesoOttimo(double pesoOttimo) {
		this.pesoOttimo = pesoOttimo;
	}

	public List<Cammino> getCammini() {
		return cammini;
	}

	public void setCammini(List<Cammino> cammini) {
		this.cammini = cammini;
	}
	
	
	
	

}
