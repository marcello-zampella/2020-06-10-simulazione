package it.polito.tdp.imdb.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Collegamento;
import it.polito.tdp.imdb.model.Director;
import it.polito.tdp.imdb.model.Movie;

public class ImdbDAO {
	
	public List<Actor> listAllActors(){
		String sql = "SELECT * FROM actors";
		List<Actor> result = new ArrayList<Actor>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Actor actor = new Actor(res.getInt("id"), res.getString("first_name"), res.getString("last_name"),
						res.getString("gender"));
				
				result.add(actor);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Movie> listAllMovies(){
		String sql = "SELECT * FROM movies";
		List<Movie> result = new ArrayList<Movie>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Movie movie = new Movie(res.getInt("id"), res.getString("name"), 
						res.getInt("year"), res.getDouble("rank"));
				
				result.add(movie);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public List<Director> listAllDirectors(){
		String sql = "SELECT * FROM directors";
		List<Director> result = new ArrayList<Director>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Director director = new Director(res.getInt("id"), res.getString("first_name"), res.getString("last_name"));
				
				result.add(director);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<String> getAllGeneri() {
		String sql = "SELECT DISTINCT m.genre " + 
				"FROM movies_genres m " + 
				"ORDER BY m.genre ";
		ArrayList<String> result = new ArrayList<String>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				String genere = res.getString("genre");
				
				result.add(genere);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<Collegamento> getAllCollegamenti(String genere) {
		String sql = "SELECT a1.id AS idprimo, a1.first_name AS nomeprimo, a1.last_name AS cognomeprimo, a1.gender AS sesso1, a2.id AS idsecondo, a2.first_name AS nomesecondo, a2.last_name AS cognomesecondo, a2.gender AS sesso2,  COUNT(*) AS conto " + 
				"FROM movies_genres mg,roles r1,roles r2,actors a1 ,actors a2 " + 
				"WHERE mg.genre=? AND r1.movie_id=mg.movie_id AND r2.movie_id=mg.movie_id " + 
				"AND r1.actor_id>r2.actor_id AND r1.actor_id=a1.id AND r2.actor_id=a2.id " + 
				"GROUP BY a1.id, a2.id ";
		ArrayList<Collegamento> result = new ArrayList<Collegamento>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, genere);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Collegamento c =new Collegamento(new Actor(res.getInt("idprimo"),res.getString("nomeprimo"),res.getString("cognomeprimo"),res.getString("sesso1")),
						new Actor(res.getInt("idsecondo"),res.getString("nomesecondo"),res.getString("cognomesecondo"),res.getString("sesso2")),
						res.getInt("conto")						
						);
				result.add(c);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
	
	
	
}
