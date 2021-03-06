package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.reserve;

public class reserveView {
	String user = "root";
	String password = "password"; // put in your password to the MySQL Workbench database
	private ResultSet rs;
	private ResultSet rs1;
	private Connection connection;

	public void reserveTicket(int flightID) {

		String updateSQL = "UPDATE flight set current_capacity=current_capacity+1 WHERE flight_id = ?;";
		String currentCap = "SELECT current_capacity FROM flight where flight_id = ?;";
		String query = "SELECT * FROM FlightCatch.Tickets,FlightCatch.Flight,FlightCatch.Arrives , FlightCatch.Departs WHERE ticket_number IN ( SELECT current_capacity FROM FlightCatch.Flight WHERE flight_id = ?) AND Flight.flight_id = ? AND Departs.flight_id= ? AND Arrives.flight_id= ? ;";
		try {
			int cap = 0;
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/FlightCatch?useSSL=false", user,
					password);

			PreparedStatement ps = connection.prepareStatement(updateSQL);
			// PreparedStatement ps1 = connection.prepareStatement(ticketNumberSQL);
			PreparedStatement ps2 = connection.prepareStatement(query);
			PreparedStatement ps3 = connection.prepareStatement(currentCap);
			ps.setInt(1, flightID);

			ps2.setInt(1, flightID);
			ps2.setInt(2, flightID);
			ps2.setInt(3, flightID);
			ps2.setInt(4, flightID);
			System.out.println(ps);
			// int rs =
			ps.executeUpdate();
			ps3.setInt(1, flightID);
			rs1 = ps3.executeQuery();
			if (rs1.next())
				cap = this.rs1.getInt("current_capacity");
			rs1.close();

			// ps1.setInt(1, cap);
			// ps1.setInt(2, flightID);
			// ps1.executeUpdate();
			rs = ps2.executeQuery();

			// res = rs.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public String getHTMLTable() {
		String table = "";
		table += "<table style='width:100%'>";
		table += "<tr>";
		table += "<th>";
		table += "Ticket Number";
		table += "</th>";

		table += "<th>";
		table += "Departs";
		table += "</th>";

		table += "<th>";
		table += "Departure Time";
		table += "</th>";

		table += "<th>";
		table += "Arrives";
		table += "</th>";

		table += "<th>";
		table += "Arrival Time";
		table += "</th>";

		table += "<th>";
		table += "Seat Number";
		table += "</th>";

		table += "</tr>";

		try {
			while (this.rs.next()) {
				reserve reserve1 = new reserve();
				reserve1.setTicketNumber(this.rs.getInt("ticket_number"));
				reserve1.setDeparts(this.rs.getString("Departs.city"));
				reserve1.setArrives(this.rs.getString("Arrives.city"));
				reserve1.setDeparture_time(this.rs.getTime("Departs.time").toString());
				reserve1.setArrival_time(this.rs.getTime("Arrives.time").toString());
				reserve1.setSeatNumber(this.rs.getInt("seat_number"));

				table += "<tr>";
				table += "<td>";
				table += reserve1.getTicketNumber();
				table += "</td>";

				table += "<td>";
				table += reserve1.getDeparts();
				table += "</td>";

				table += "<td>";
				table += reserve1.getDeparture_time();
				table += "</td>";

				table += "<td>";
				table += reserve1.getArrives();
				table += "</td>";

				table += "<td>";
				table += reserve1.getArrival_time();
				table += "</td>";

				table += "<td>";
				table += reserve1.getSeatNumber();
				table += "</td>";

				table += "<td>";
				table += "</td>";
				// table += "<form action=\"reserve\" method=\"GET\">"
				// + " <input type=\"hidden\" name=\"flight_id\" value=" + 1 + " />"
				// + "<button type=\"submit\"> Reserve </button>" + "</form>";
				// "<a href=Reserve? flight_id=" + flight.getFlight_id() + "> Reserve </a>";
				// "<action="search" method="GET""> </form>";

				table += "</td>";

				table += "</tr>";
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		table += "</table>";
		return table;
	}

}
