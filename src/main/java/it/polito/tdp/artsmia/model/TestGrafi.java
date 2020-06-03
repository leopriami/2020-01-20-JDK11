package it.polito.tdp.artsmia.model;

public class TestGrafi {

	public static void main(String[] args) {
		Model model = new Model();
		
		Vertex v1 = new Vertex(1, "primo");
		Vertex v2 = new Vertex(2, "secondo");
		Vertex v3 = new Vertex(3, "terzo");
		Vertex v4 = new Vertex(4, "quarto");
		Vertex v5 = new Vertex(5, "quinto");
		Vertex v6 = new Vertex(6, "sesto");
		Vertex v7 = new Vertex(7, "settimo");
		Vertex v8 = new Vertex(8, "ottavo");
		Vertex v9 = new Vertex(9, "nono");
		
		Edge e1 = new Edge(v1, v2, 3.21);
		Edge e2 = new Edge(v2, v3, 2.18);
		Edge e3 = new Edge(v1, v3, 1.0);
		Edge e4 = new Edge(v3, v4, 4.12); 
		Edge e5 = new Edge(v2, v4, 10.96);
		Edge e6 = new Edge(v1, v5, 4.12);
		Edge e7 = new Edge(v5, v4, 1.0);
		
		Edge e8 = new Edge(v7, v6, 4.12);
		Edge e9 = new Edge(v6, v8, 3.21);
		Edge e10 = new Edge(v8, v7, 2.18);
		
		
		model.getNodi().add(v1);
		model.getNodi().add(v2);
		model.getNodi().add(v3);
		model.getNodi().add(v4);
		model.getNodi().add(v5);
		model.getNodi().add(v6);
		model.getNodi().add(v7);
		model.getNodi().add(v8);
		model.getNodi().add(v9);
		
		model.getArchi().add(e1);
		model.getArchi().add(e2);
		model.getArchi().add(e3);
		model.getArchi().add(e4);
		model.getArchi().add(e5);
		model.getArchi().add(e6);
		model.getArchi().add(e7);
		model.getArchi().add(e8);
		model.getArchi().add(e9);
		model.getArchi().add(e10);
		

		model.creaGrafo();
		model.cercaOttimo(v5, null);
		if (model.getCamminoOttimo().size() == 0) {
			System.out.println("Nessun cammino");
		} else {
			for(Vertex v : model.getCamminoOttimo()) {
				System.out.println(v.getName()+" --> ");
			}
		}
		
		System.out.println("DA QUI SECONDO METODO");
		
		model.cercaCamminiOrdinati(v1, v3);
		for(Cammino c : model.getCammini()) {
			for(Vertex v : c.getCammino()) {
				System.out.println(v.getName()+" --> ");
			}
			System.out.println(c.getWeight());
		}
		

	}

}
