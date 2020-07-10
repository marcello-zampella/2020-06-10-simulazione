package it.polito.tdp.imdb;

import java.util.Comparator;

import it.polito.tdp.imdb.model.Actor;

public class ComparatoreCognomi implements Comparator {
	public int compare (Object o1, Object o2) {
		Actor a1=(Actor) o1;
		Actor a2 =(Actor) o2;
		return a1.getLastName().compareTo(a2.getLastName());
		
	}

}
