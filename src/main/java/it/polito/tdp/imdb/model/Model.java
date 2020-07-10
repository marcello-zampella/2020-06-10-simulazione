package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	public Model() {
		dao= new ImdbDAO();
	}
	
	private ArrayList<String> generi;
	private ImdbDAO dao;
	private SimpleWeightedGraph<Actor, DefaultWeightedEdge> grafo;

	public ArrayList<String> getAllGeneri() {
		
		generi=dao.getAllGeneri();
		return generi;
	}
	

	public Set<Actor> creaGrafo(String s) {
		grafo= new SimpleWeightedGraph<Actor, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		for(Collegamento c:dao.getAllCollegamenti(s)) {
			if(!grafo.containsVertex(c.getA1()))
				grafo.addVertex(c.getA1());
			if(!grafo.containsVertex(c.getA2()))
				grafo.addVertex(c.getA2());
			Graphs.addEdge(grafo, c.getA1(), c.getA2(), c.getPeso());
		}
		System.out.println("numero vertici "+grafo.vertexSet().size()+" e archi "+grafo.edgeSet().size());
		
		
		return grafo.vertexSet();
		
	}
	
	Map<Actor, Actor> backVisit;


	public List<Actor> cercaAttoriVicini(Actor source) {
		
		List<Actor> result = new ArrayList<Actor>();
		backVisit = new HashMap<>();
		GraphIterator<Actor,DefaultWeightedEdge> it=new BreadthFirstIterator<>(this.grafo, source); // crea un nuovo iteratore e lo associa a questo grafo
	// per scegliere quale sia il nodo in cui deve iniziare glielo devo specificare nel secondo parametro
		
		//GraphIterator<Fermata,DefaultEdge> it=new DepthFirstIterator<>(this.grafo, source); //modo diverso di visitare il grafico
		
		it.addTraversalListener(new EdgeTraverseGraphListener(grafo, backVisit));
				
		backVisit.put(source, null); // devo dargli un nodo da cui partire
		while(it.hasNext()) {
			result.add(it.next());
		}
		return result;
	}


	private Simulatore sim;
	
	public void simula(int giorni) {
		sim=new Simulatore();
		sim.init(giorni,grafo);
		sim.run();
		
	}


	public ArrayList<Actor> getInseriti() {
		return this.sim.getInseriti();
	}


	public int getGiorniDiPausa() {
		// TODO Auto-generated method stub
		return this.sim.getGiorniDiPausa();
	}

}
