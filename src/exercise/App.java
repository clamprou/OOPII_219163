package exercise;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import gui.FrameUserInfo;
import oracle.sql.OracleDBServiceCRUD;

public class App {
	public static void main(String[] args) throws Exception {//Otan o xrhsths dosei gia City kapia pou den uparxei eidh sthn vash dedomenwn kolaei to frame mexri na thn anaktisei
		OracleDBServiceCRUD.makeJDBCConnection();
		Thread recoverCities = new Thread() {
			public void run() {
				try {
					City.recoverCities();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		recoverCities.start();
		Thread recoverTravellers = new Thread() {
			public void run() {
				try {
					Traveller.recoverTravellers("Put your key");
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		recoverTravellers.start();
		recoverCities.join();
		recoverTravellers.join();
		FrameUserInfo.main();
	}
}