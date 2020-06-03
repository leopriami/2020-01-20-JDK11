package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;

public class Cammino {
	
	private List<Vertex> cammino;
	private double weight;
	
	public Cammino(List<Vertex> cammino, double weight) {
		this.cammino = new ArrayList<>(cammino);
		this.weight = weight;
	}

	public List<Vertex> getCammino() {
		return cammino;
	}

	public void setCammino(List<Vertex> cammino) {
		this.cammino = cammino;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
