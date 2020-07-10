package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Random;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Simulatore {
	
	ArrayList<Actor> tutti;
	ArrayList<Actor> inseriti;
	ArrayList<Actor> possibili;
	int giorni;
	int livello;
	Actor prossimo;
	Random rand;
	Actor intervistato;
	SimpleWeightedGraph<Actor, DefaultWeightedEdge> grafo;
	Actor vecchio;
	int scelta;
	int pausa;
	int giorniDiPausa;

	public void init(int giorni, SimpleWeightedGraph<Actor, DefaultWeightedEdge> grafo) {
		this.grafo=grafo;
		tutti=new ArrayList<Actor>(grafo.vertexSet());
		possibili=new ArrayList<Actor>(tutti);
		inseriti=new ArrayList<Actor>();
		rand=new Random();
		this.giorni=giorni;
		livello=0;
		intervistato=tutti.get(rand.nextInt(tutti.size()));
		inseriti.add(intervistato);
		possibili.remove(intervistato);
		livello++;
		giorniDiPausa=0;
	}

	public void run() {
		
		
		
		while(livello<giorni) {
		livello++;
		vecchio=intervistato;
			scelta=rand.nextInt(10);
			if(scelta<=5) {
				//random
				intervistato=possibili.get(rand.nextInt(possibili.size()));
				inseriti.add(intervistato);
				possibili.remove(intervistato);
			} else {
				intervistato=this.trovaPesoMaggiore(vecchio);
				if(intervistato==null) {
					intervistato=possibili.get(rand.nextInt(possibili.size()));
				}
				inseriti.add(intervistato);
				possibili.remove(intervistato);
			}
			if(vecchio.getGender().equals(intervistato.getGender())) {
				pausa=rand.nextInt(10);
				if(pausa>0) {
					livello++;
					this.giorniDiPausa++;
				}
			}
			
		}
		
	}

	private Actor trovaPesoMaggiore(Actor inter) {
		int max=0;
		int peso;
		Actor migliore=null;
		for(DefaultWeightedEdge e:grafo.incomingEdgesOf(inter)) {
			peso=(int) grafo.getEdgeWeight(e);
			if(max<peso) {
				max=peso;
				if(!inter.equals(grafo.getEdgeTarget(e))) {
					migliore=grafo.getEdgeTarget(e);
				} else {
					migliore=grafo.getEdgeSource(e);
				}
			}
		}
		return migliore;
	}

	public ArrayList<Actor> getInseriti() {
		return this.inseriti;
	}

	public int getGiorniDiPausa() {
		// TODO Auto-generated method stub
		return this.giorniDiPausa;
	}

}
