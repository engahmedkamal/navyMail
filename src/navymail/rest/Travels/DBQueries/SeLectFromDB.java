package navymail.rest.Travels.DBQueries;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import navymail.Helpers.Wared.DocUtils;
import navymail.models.Master.Units;
import navymail.models.Travels.Internal_Unit;
import navymail.models.Travels.Mission;
import navymail.models.Travels.Mission_type;
import navymail.models.Travels.Port;
import navymail.models.Travels.Rank_degree;
import navymail.models.Travels.Ship;
import navymail.models.Travels.Traveler;
import navymail.models.Travels.Traveler_mission;
import navymail.util.ConnectionHandler;

public class SeLectFromDB {

	public Connection con;
	public ResultSet res;

	public SeLectFromDB() throws SQLException {}

	public void openConnection(String q) throws SQLException {
		con = ConnectionHandler.getInstance().getDBConnection(
					ConnectionHandler.NAVYMAIL);

		PreparedStatement st = con.prepareStatement(q);
		res = st.executeQuery(q);
	}

	// TRAVEL

	public ArrayList<Traveler> getTravelers() throws SQLException {

		ArrayList<Traveler> list = new ArrayList<Traveler>();
		while (res.next()) {
			Traveler traveler = new Traveler();
			traveler.setMilitary_num(res.getLong(Traveler.MILITARY_NUM_COL));
			traveler.setRank_id(res.getInt(Traveler.RANK_ID_COL));
			traveler.setName(res.getString(Traveler.NAME_COL));
			traveler.setSeniority_num(res.getString(Traveler.SENIORITY_NUM_COL));
			traveler.setJob_description(res.getString(Traveler.JOB_DESCRIPTION_COL));
			traveler.setUnit_id(res.getInt(Traveler.UNIT_ID_COL));
			traveler.setNotes(res.getString(Traveler.NOTES_COL));
			traveler.setSecurity_status(res.getInt(Traveler.Security_COL));
			list.add(traveler);
		}
		return list;
	}

	public ArrayList<Mission> getMissions() throws SQLException {

		ArrayList<Mission> list = new ArrayList<Mission>();
		while (res.next()) {
			Mission mission = new Mission();
			mission.setId(res.getInt(Mission.ID_COL));
			mission.setName(res.getString(Mission.NAME_COL));
			mission.setType_id(res.getInt(Mission.TYPE_ID_COL));
			mission.setActivity_name(res.getString(mission.ACTIVITY_NAME_COL));
			mission.setDate_from(res.getString(Mission.DATE_FROM_COL));
			mission.setDate_to(res.getString(Mission.DATE_TO_COL));
			mission.setDocument_id(DocUtils.getLinks(res.getString(Mission.DOCUMNET_ID_COL)));
			mission.setConfirmation_no(res.getString(Mission.CONFIRMATION_NO_COL));
			mission.setConfirmation_date(res.getString(Mission.CONFIRMATION_DATE_COL));
			mission.setCommander_id(res.getLong(Mission.COMMANDER_ID_COL));
			mission.setSecond_commander_id(res.getLong(Mission.SECOND_COMMANDER_ID_COL));
			mission.setPorts(res.getString(Mission.PORTS_COL));
			mission.setShips(res.getString(Mission.SHIPS_COL));
			mission.setDirection(res.getInt(Mission.DIRECTION_COL));
			list.add(mission);
			
		}
		return list;
	}

	public ArrayList<Internal_Unit> getInternal_Units() throws SQLException {
		
		ArrayList<Internal_Unit> list = new ArrayList<Internal_Unit>();
		while (res.next()) {
			Internal_Unit Int_unit = new Internal_Unit();
			Int_unit.setId(res.getInt(Units.ID_COL));
			Int_unit.setName(res.getString(Units.NAME_COL));
			list.add(Int_unit);
		}
		return list;
	}
	
	public ArrayList<Mission_type> getMissions_type() throws SQLException {

		ArrayList<Mission_type> list = new ArrayList<Mission_type>();
		while (res.next()) {
			Mission_type missionType = new Mission_type();
			missionType.setId(res.getInt(Mission_type.ID_COL));
			missionType.setType(res.getInt(Mission_type.TYPE_COL));
			missionType.setName(res.getString(Mission_type.NAME_COL));
			list.add(missionType);
		}
		return list;
	}

	public ArrayList<Rank_degree> getRank_degree() throws SQLException {

		ArrayList<Rank_degree> list = new ArrayList<Rank_degree>();
		while (res.next()) {
			Rank_degree rank = new Rank_degree();
			rank.setId(res.getInt(Rank_degree.ID_COL));
			rank.setRank_name(res.getString(Rank_degree.RANK_NAME_COL));
			rank.setRank_type(res.getInt(Rank_degree.RANK_TYPE_COL));
			rank.setGroup_id(res.getInt(Rank_degree.GROUP_ID_COL));
			list.add(rank);
		}
		return list;
	}
	
	public ArrayList<Ship> getShips() throws SQLException {

		ArrayList<Ship> list = new ArrayList<Ship>();
		while (res.next()) {
			Ship ship = new Ship();
			ship.setId(res.getInt(Ship.ID_COL));
			ship.setName(res.getString(Ship.NAME_COL));
			ship.setShip_type(res.getString(Ship.SHIP_TYPE_COL));
			ship.setUnit_id(res.getInt(Ship.UNIT_ID_COL));
			list.add(ship);
		}
		return list;
	}
	
	public ArrayList<Traveler_mission> getTravelers_missions() throws SQLException {

		ArrayList<Traveler_mission> list = new ArrayList<Traveler_mission>();
		while (res.next()) {
			Traveler_mission traveler_mission = new Traveler_mission();
			traveler_mission.setTraveler_id(res.getLong(Traveler_mission.TRAVELER_ID_COL));
			traveler_mission.setMission_id(res.getInt(Traveler_mission.MISSION_ID_COL));
			traveler_mission.setShip_id(res.getInt(Traveler_mission.SHIP_ID_COL));
			traveler_mission.setPostion(res.getInt(Traveler_mission.POSTION_COL));
			traveler_mission.setNotes(res.getString(Traveler_mission.Note_COL));
			list.add(traveler_mission);
		}
		return list;
	}
	
	
	
	public ArrayList<Port> getPorts() throws SQLException {

		ArrayList<Port> list = new ArrayList<Port>();
		while (res.next()) {
			Port port = new Port();
			port.setId(res.getInt(Port.ID_COL));
			port.setName(res.getString(Port.NAME_COL));
			list.add(port);
		}
		return list;
	}
	
	public String getShipName() throws SQLException {
		String ship_name="";
		while(res.next())
			ship_name=res.getString("name");
		return ship_name;
	}
	
	public void closeConnection() throws SQLException {
		con.close();
		res.close();
	}
}