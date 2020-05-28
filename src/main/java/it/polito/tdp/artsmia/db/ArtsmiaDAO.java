package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {
	
public List<String> ruoli() {
		
		String sql = "select distinct role from authorship";
		List<String> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getString("role"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Integer> artisti(String ruolo) {
		
		String sql = "select distinct artist_id from authorship where role = ?";
		List<Integer> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, ruolo);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getInt("artist_id"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Arco> adiacenze(String ruolo) {
		
		String sql = "select a1.artist_id as ar1, a2.artist_id as ar2, count(distinct eo1.exhibition_id) as peso " + 
				"from authorship as a1, authorship as a2, exhibition_objects as eo1, exhibition_objects as eo2 " + 
				"where a1.artist_id < a2.artist_id " + 
				"and eo1.exhibition_id = eo2.exhibition_id " + 
				"and a1.object_id = eo1.object_id " + 
				"and a2.object_id = eo2.object_id " + 
				"and a1.role = ? " + 
				"and a2.role = ? " + 
				"group by ar1,ar2 order by peso desc";
		List<Arco> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, ruolo);
			st.setString(2, ruolo);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Arco a = new Arco(res.getInt("ar1"), res.getInt("ar2"), res.getInt("peso"));
				
				result.add(a);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
