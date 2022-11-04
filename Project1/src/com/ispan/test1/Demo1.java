package com.ispan.test1;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.ispan.model1.ExchangeRates;

//import org.apache.commons.csv.CSVFormat;
//import org.apache.commons.csv.CSVRecord;

import com.ispan.model1.ExchangeRatesDao;
import com.ispan.util1.ConnectionFactory;

public class Demo1 {
	
	public static void main(String[] args) {
		Connection conn = ConnectionFactory.createMSSQLConnection();
		ExchangeRatesDao eDao = new ExchangeRatesDao(conn);
		boolean b = true;
		try(Scanner s = new Scanner(System.in);) {
			while(b) {
				System.out.println("請輸入 0(新增表格&加入資料) 1(新增) 2(查詢) 3(修改) 4(刪除) 5(輸出) 6(模糊搜尋) -1(結束)");
				int input = s.nextInt();
				switch (input) {
				case -1:
					b = false;
					break;
				case 0:
//					//新增表格
					eDao.createTable();
//					//新增全部資料
					eDao.addExchangeRates();
					break;
				case 1:
					//新增資料
					System.out.println("請輸入:日期(yyyymmdd) 美元/新台幣(xx.xxx) 人民幣／新台幣(x.xxxxxx) 美元∕日幣(xxx.xxx) 美元∕港幣(x.xxxxx) 美元∕人民幣(x.xxxxx)");
					String yyyymmdd = s.next();
					String usd2Ntd = s.next();
					String cny2Ntd = s.next();
					String usd2Jpy = s.next();
					String usd2Hkd = s.next();
					String usd2Cny = s.next();
					//20221030 32.1 4.4 146 7.8444 7.222
					ExchangeRates er1 = new ExchangeRates(yyyymmdd, usd2Ntd, cny2Ntd, usd2Jpy, usd2Hkd, usd2Cny);
					eDao.addExchangeRates(er1);
					break;
				case 2:
					System.out.println("請輸入:1(透過日期拿資料) 2(拿全部資料)");
					int inputQuery = s.nextInt();
					if(inputQuery == 1) {
						System.out.println("請輸入:日期(yyyymmdd)");
						String yyyymmdd2 = s.next();
						//透過日期拿資料
						ExchangeRates find = eDao.findByDate(yyyymmdd2);
						System.out.println(find);						
					}else if(inputQuery == 2) {
						//拿全部資料
						List<ExchangeRates> results = eDao.getAllExchangeRates();
						for (ExchangeRates result : results) {
							System.out.println(result);						
						}
					}else {
						System.out.println("輸入錯誤，請重新輸入!");
					}
					break;
				case 3:
					System.out.println("請輸入: 美元/新台幣(xx.x) 日期(yyyymmdd)");
					String usd2Ntd2 = s.next();
					String yyyymmdd3 = s.next();
					//透過日期改資料
					eDao.updateDataByDate(usd2Ntd2, yyyymmdd3);
					break;
				case 4:
					System.out.println("請輸入:1(透過日期刪資料) 2(刪除表格)");
					int deleteQuery = s.nextInt();
					if(deleteQuery == 1) {
						System.out.println("請輸入: 日期(yyyymmdd)");
						String yyyymmdd4 = s.next();
						//給日期刪資料
						eDao.deleteByDate(yyyymmdd4);						
					}else if(deleteQuery == 2) {
						eDao.deleteTable();
					}else {
						System.out.println("輸入錯誤，請重新輸入!");
					}
					break;
				case 5:
					System.out.println("請輸入:1(輸出.csv) 2(輸出.json)");
					int outputCsvOrJson = s.nextInt();
					if(outputCsvOrJson == 1) {
						//輸出.csv
						eDao.outputFile();						
					}else if(outputCsvOrJson == 2) {
						//輸出.json
						eDao.outputFileJson();						
					}else {
						System.out.println("輸入錯誤，請重新輸入!");
					}
					break;
				case 6:
					System.out.println("以美金換台幣模糊搜尋\n請輸入數字:");
					String findByLike = s.next();
					//模糊搜尋 美金換台幣
					List<ExchangeRates> lists = eDao.findByDataLike(findByLike);
					if(lists.size() > 0) {				
						for (ExchangeRates list : lists) {
							System.out.println(list);
						}
					}else {
						System.out.println("沒有相符的資料");
					}
					break;
				default:
					System.out.println("輸入錯誤，請重新輸入!!!");
					break;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
