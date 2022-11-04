package com.ispan.model1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class ExchangeRatesDao {
	
	private Connection conn;

	public ExchangeRatesDao(Connection conn) {
		super();
		this.conn = conn;
	}
	//新增表格
	public void createTable() throws SQLException {
		String sql = "create table ExchangeRates(yyyymmdd date primary key not null, usd2Ntd decimal(5,3), cny2Ntd decimal(7,6), usd2Jpy decimal(6,3), usd2Hkd decimal(6,5), usd2Cny decimal(6,5));";
		Statement state = conn.createStatement();
		state.execute(sql);
		System.out.println("新增表格OK");
	}
	
	//刪除表格
	public void deleteTable() throws SQLException {
		String sql = "drop table ExchangeRates";
		Statement state = conn.createStatement();
		state.executeUpdate(sql);
		System.out.println("刪除表格OK");
	}
	
	//美金usd人民幣cny歐元eur英鎊gbp日幣jpy澳幣aud港幣hkd南非幣zar紐幣nzd
	//新增全部資料
	public void addExchangeRates() throws SQLException {
		try {
			BufferedReader bis = new BufferedReader(new InputStreamReader(
					new FileInputStream("C:\\Users\\User\\Downloads\\DailyForeignExchangeRates.csv"), "big5"));
			bis.readLine(); // skip header line
			Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("日期", "美元／新台幣", "人民幣／新台幣", "歐元∕美元", "美元∕日幣",
					"英鎊∕美元","澳幣∕美元", "美元∕港幣", "美元∕人民幣", "美元∕南非幣" ,"紐幣∕美元").parse(bis);
			String sql = "insert into ExchangeRates values(?,?,?,?,?,?)";
			PreparedStatement preState = conn.prepareStatement(sql);
			for (CSVRecord record : records) {
				preState.setString(1, record.get("日期"));
				preState.setString(2, record.get("美元／新台幣"));
				preState.setString(3, record.get("人民幣／新台幣"));
				preState.setString(4, record.get("美元∕日幣"));
				preState.setString(5, record.get("美元∕港幣"));
				preState.setString(6, record.get("美元∕人民幣"));
				preState.addBatch();//隱含的list	
			}
			preState.executeBatch();//真正處理批次
			System.out.println("資料加入完成");
			preState.close();
			bis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//新增資料
	public void addExchangeRates(ExchangeRates er) throws SQLException {
		String sql = "insert into ExchangeRates values(?,?,?,?,?,?)";
		PreparedStatement preState;
		preState = conn.prepareStatement(sql);
		preState.setString(1, er.getYmd());
		preState.setString(2, er.getUsd2Ntd());
		preState.setString(3, er.getCny2Ntd());
		preState.setString(4, er.getUsd2Jpy());
		preState.setString(5, er.getUsd2Hkd());
		preState.setString(6, er.getUsd2Cny());
		int row = preState.executeUpdate();
		System.out.println("新增了 " + row + "筆");
		preState.close();
	}
	//透過日期拿資料
	public ExchangeRates findByDate(String ymd) throws SQLException {
		String sql = "select * from ExchangeRates where yyyymmdd = ?";
		PreparedStatement preState = conn.prepareStatement(sql);
		preState.setString(1, ymd);
		ResultSet rs = preState.executeQuery();
		rs.next();
		ExchangeRates er = new ExchangeRates();
		er.setYmd(rs.getString("yyyymmdd"));
		er.setUsd2Ntd(rs.getString("Usd2Ntd"));
		er.setCny2Ntd(rs.getString("Cny2Ntd"));
		er.setUsd2Jpy(rs.getString("Usd2Jpy"));
		er.setUsd2Hkd(rs.getString("Usd2Hkd"));
		er.setUsd2Cny(rs.getString("Usd2Cny"));
		rs.close();
		preState.close();
		return er;
	}
	//拿全部資料
	public List<ExchangeRates> getAllExchangeRates() throws SQLException {
		String sql = "select * from ExchangeRates";
		PreparedStatement preState = conn.prepareStatement(sql);
		ResultSet rs = preState.executeQuery();
		List<ExchangeRates> list = new ArrayList<>();
		while(rs.next()) {
			ExchangeRates er = new ExchangeRates();
			er.setYmd(rs.getString("yyyymmdd"));
			er.setUsd2Ntd(rs.getString("Usd2Ntd"));
			er.setCny2Ntd(rs.getString("Cny2Ntd"));
			er.setUsd2Jpy(rs.getString("Usd2Jpy"));
			er.setUsd2Hkd(rs.getString("Usd2Hkd"));
			er.setUsd2Cny(rs.getString("Usd2Cny"));
			list.add(er);
		}
		rs.close();
		preState.close();
		return list;
	}
	//模糊搜尋
	public List<ExchangeRates> findByDataLike(String data) throws SQLException{
		String sql = "select * from ExchangeRates where usd2Ntd like ?";
		PreparedStatement preState = conn.prepareStatement(sql);
		preState.setString(1, "%" + data + "%");
		ResultSet rs = preState.executeQuery();
		List<ExchangeRates> list = new ArrayList<>();
		while(rs.next()) {
			ExchangeRates er = new ExchangeRates();
			er.setYmd(rs.getString("yyyymmdd"));
			er.setUsd2Ntd(rs.getString("Usd2Ntd"));
			er.setCny2Ntd(rs.getString("Cny2Ntd"));
			er.setUsd2Jpy(rs.getString("Usd2Jpy"));
			er.setUsd2Hkd(rs.getString("Usd2Hkd"));
			er.setUsd2Cny(rs.getString("Usd2Cny"));
			list.add(er);
		}
		rs.close();
		preState.close();
		return list;
	}
	//給日期改資料
	public void updateDataByDate(String data, String ymd) throws SQLException {
		String sql = "update ExchangeRates set usd2Ntd = ? where yyyymmdd = ?";
		PreparedStatement preState = conn.prepareStatement(sql);
		preState.setString(1, data);
		preState.setString(2, ymd);
		int row = preState.executeUpdate();
		System.out.println("修改了 " + row + "筆");
		preState.close();
	}
	//給日期刪資料
	public void deleteByDate(String ymd) throws SQLException {
		String sql = "delete ExchangeRates where yyyymmdd = ?";
		PreparedStatement preState = conn.prepareStatement(sql);
		preState.setString(1, ymd);
		int row = preState.executeUpdate();
		System.out.println("刪除了 " + row + "筆");
		preState.close();
	}
	//輸出.csv
	public void outputFile() throws SQLException {
		String sql = "select * from ExchangeRates";
		PreparedStatement preState = conn.prepareStatement(sql);
		ResultSet rs = preState.executeQuery();
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
					(new FileOutputStream("C:\\Users\\User\\Desktop\\outputFile.csv"), "MS950"));
			CSVPrinter csvPrinter = new CSVPrinter(bw, CSVFormat.DEFAULT
					.withHeader("日期", "美元／新台幣", "人民幣／新台幣", "美元∕日幣", "美元∕港幣", "美元∕人民幣"));
			while(rs.next()) {
				csvPrinter.printRecord((rs.getString("yyyymmdd")),(rs.getString("Usd2Ntd")),(rs.getString("Cny2Ntd")),
						(rs.getString("Usd2Jpy")),(rs.getString("Usd2Hkd")),(rs.getString("Usd2Cny")));
			}
			bw.flush();
			System.out.println(".csv Output OK!!");
			csvPrinter.close();
			bw.close();
			rs.close();
			preState.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//輸出.json
	public void outputFileJson() throws SQLException {
		String sql = "select * from ExchangeRates";
		PreparedStatement preState = conn.prepareStatement(sql);
		ResultSet rs = preState.executeQuery();
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter
					(new FileOutputStream("C:\\Users\\User\\Desktop\\outputFile.json"), "MS950"));
			JsonArray ja = new JsonArray();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			ResultSetMetaData rsmd = rs.getMetaData();
			String columnName, columnValue = null;
			while(rs.next()) {
				JsonObject element = new JsonObject();
				for(int i = 0; i < rsmd.getColumnCount(); i++) {
					columnName = rsmd.getColumnName(i + 1);
					columnValue = rs.getString(columnName);
					element.addProperty(columnName, columnValue);
				}
				ja.add(element);
			}
			String json = gson.toJson(ja);
			bw.write(json);
			bw.flush();
			bw.close();
			rs.close();
			preState.close();
			System.out.println(".json Output OK");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
