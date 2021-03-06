package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.AdminSearch;

public class AdminSearch_Person {
	String user = "root";
	String password = "password";

//	private Connection conn;
//	private ResultSet rs;
//
//	public void doAdminSearch(String departs, String arrives) {
//		String query = "SELECT departs.city as 'departs', arrives.city as 'arrives', departs.time as 'time1', arrives.time as 'time2', "
//				+ "departs.flight_id, flight.airline, flight.passenger_capacity, flight.status\r\n"
//				+ "FROM departs join arrives on departs.flight_id = arrives.flight_id join flight on flight.flight_id = departs.flight_id\r\n"
//				+ "WHERE departs.flight_id = arrives.flight_id AND departs.city LIKE ? AND arrives.city LIKE ?";
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/flightcatch?useSSL=false", user, password);
//
//			PreparedStatement ps = conn.prepareStatement(query);
//			ps.setString(1, "%" + departs + "%");
//			ps.setString(2, "%" + arrives + "%");
//			System.out.println(ps);
//			rs = ps.executeQuery();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (ClassNotFoundException error) {
//			error.printStackTrace();
//		}
//
//	}
//
//	public String getAdminHTMLTable() {
//		String table = "";
//		table += "<table style='width:100%'>";
//		table += "<tr>";
//		table += "<th>";
//		table += "Departs";
//		table += "</th>";
//
//		table += "<th>";
//		table += "Departure Time";
//		table += "</th>";
//
//		table += "<th>";
//		table += "Arrives";
//		table += "</th>";
//
//		table += "<th>";
//		table += "Arrival time";
//		table += "</th>";
//
//		table += "<th>";
//		table += "Airline";
//		table += "</th>";
//
//		table += "</tr>";
//
//		try {
//			while (this.rs.next()) {
//				AdminSearch as = new AdminSearch();
//				as.setFlight_id(this.rs.getInt("flight_id"));
//				as.setPassenger_capacity(this.rs.getInt("passenger_capacity"));
//				as.setStatus(this.rs.getString("status"));
//				as.setAirline(this.rs.getString("airline"));
//				as.setDeparts(this.rs.getString("departs"));
//				as.setArrives(this.rs.getString("arrives"));
//				as.setDepartureTime(this.rs.getTime("time1").toString());
//				as.setArrivalTime(this.rs.getTime("time2").toString());
//
//				table += "<tr>";
//				table += "<td>";
//				table += as.getDeparts();
//				table += "</td>";
//
//				table += "<td>";
//				table += as.getDepartureTime();
//				table += "</td>";
//
//				table += "<td>";
//				table += as.getArrives();
//				table += "</td>";
//
//				table += "<td>";
//				table += as.getArrivalTime();
//				table += "</td>";
//
//				table += "<td>";
//				table += as.getAirline();
//				table += "</td>";
//
//				table += "<td>";
//				table += "<a href=Reserve?flight_id=" + as.getFlight_id() + "> Reserve </a>";
//				table += "</td>";
//
//				table += "</tr>";
//			}
//		} catch (SQLException ex) {
//			ex.printStackTrace();
//		}
//		table += "</table>";
//		return table;
//	}

	public int adminInsertDepartureTimeAndCity(AdminSearch adminSearch) throws ClassNotFoundException {
		String INSERT_DEPARTURES_SQL = "INSERT INTO FlightCatch.departs" + "(city, flight_id, time) VALUES "
				+ " (?, ?, ?);";

		int res = 0;
		Class.forName("com.mysql.jdbc.Driver");
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FlightCatch?useSSL=false",
				user, password); PreparedStatement ps = connection.prepareStatement(INSERT_DEPARTURES_SQL)) {
			ps.setString(1, adminSearch.getCityD());
			ps.setInt(2, adminSearch.getFlight_id());
			ps.setString(3, adminSearch.getDepartureTime());

			System.out.println(ps);

			res = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	public int adminInsertArrivalTimeAndCity(AdminSearch adminSearch) throws ClassNotFoundException {
		String INSERT_ARRIVALS_SQL = "INSERT INTO FlightCatch.Arrives" + " (city, time, flight_id) VALUES "
				+ " (?, ?, ?);";

		int res = 0;
		Class.forName("com.mysql.jdbc.Driver");
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FlightCatch?useSSL=false",
				user, password); PreparedStatement ps = connection.prepareStatement(INSERT_ARRIVALS_SQL)) {
			ps.setString(1, adminSearch.getCityA());
			ps.setString(2, adminSearch.getArrivalTime());
			ps.setInt(3, adminSearch.getFlight_id());

			System.out.println(ps);

			res = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	public int adminInsertFlightInformation(AdminSearch adminSearch) throws ClassNotFoundException {
		String INSERT_FLIGHT_SQL = "INSERT INTO FlightCatch.Flight"
				+ " (flight_id, passenger_capacity, status, airline, current_capacity) VALUES " + " (?, ?, ?, ?, ?);";

		int res = 0;
		Class.forName("com.mysql.jdbc.Driver");
		try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FlightCatch?useSSL=false",
				user, password); PreparedStatement ps = connection.prepareStatement(INSERT_FLIGHT_SQL)) {
			ps.setInt(1, adminSearch.getFlight_id());
			ps.setInt(2, adminSearch.getPassenger_capacity());
			ps.setString(3, adminSearch.getStatus());
			ps.setString(4, adminSearch.getAirline());
			ps.setInt(5, 0);

			System.out.println(ps);

			res = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}
}
