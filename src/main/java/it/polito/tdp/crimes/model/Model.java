package it.polito.tdp.crimes.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	private EventsDao dao;
	private  SimpleWeightedGraph<String,DefaultWeightedEdge> grafo;
	private List<String> percorsoMigliore;
	public Model() {
		dao= new EventsDao();
	}
	/*
	 * GRAFO SEMPILCE PESATO NON ORIENTATO
	 * PESO: numero di quartieri distinti in cui deu reati sono avvenuti se =0 non creo l'arco
	 * VERTICI: tipi di reato (offense_type_id) della categoria e mese indicati
	 *  
	 */
	public List<String> getCategorie(){
		return dao.getCategorie();
	}
	public void CreaGrafo(String categoria,int mese) {
		
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo,dao.getVertici(categoria,mese));
		for(Adiacenza a:dao.getAdiacenze(categoria,mese)) {
			if(this.grafo.getEdge(a.getV1(),a.getV2())==null){
				Graphs.addEdgeWithVertices(grafo,a.getV1(),a.getV2(),a.getPeso());
				
				
				 
			}
	
		}
		System.out.println("# Vertici : "+ this.grafo.vertexSet().size());
		System.out.println("# Archi : "+ this.grafo.edgeSet().size());
		
	}
	public List<Adiacenza> getArchi() {
		//calcolo il peso medio e poi filtro 
		//gli archi
		double pesoMedio=0.0;
		for(DefaultWeightedEdge e:this.grafo.edgeSet()) {
			pesoMedio+=this.grafo.getEdgeWeight(e);
		}
		pesoMedio=pesoMedio/this.grafo.edgeSet().size();
		List<Adiacenza> result=new ArrayList<Adiacenza>();
		for(DefaultWeightedEdge e:this.grafo.edgeSet()) {

			if(this.grafo.getEdgeWeight(e)>pesoMedio) {
				result.add(new Adiacenza(this.grafo.getEdgeSource(e),this.grafo.getEdgeTarget(e),this.grafo.getEdgeWeight(e)));
			}
			
		}
		return result;
	}
	/*
	 * 
	 * RICORSIONE
	 * cammino aciclico che tocchi il numero max di vertici 
	 * partendo da un vertice ed arrivando ad un altro vertice
	 * 
	 */
	public List<String> trovaPercorso(String sorgente,String destinazione){
		
		this.percorsoMigliore=new LinkedList<>();
		List<String>parziale=new LinkedList<>();
		parziale.add(sorgente);
		cerca(destinazione, parziale);
		return percorsoMigliore;
		
	}
	private void cerca(String destinazione, List<String> parziale) {
		//caso terminale
		if(parziale.get(parziale.size()-1).equals(destinazione)) {
			if(parziale.size()>this.percorsoMigliore.size()) {
				this.percorsoMigliore=new LinkedList<>(parziale);
			}
			return;
		}
		for(String vicino:Graphs.neighborListOf(grafo,parziale.get(parziale.size()-1))) {
			if(!parziale.contains(vicino)) {
				parziale.add(vicino);
				cerca(destinazione,parziale);
				parziale.remove(parziale.size()-1);
			}
		}
	}
}
