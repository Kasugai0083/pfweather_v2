import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * OpenWeatherAPIに接続し、フィールド変数に入力するクラス
 * 
 * 主なクラス:
 * - {@link Const}: 定数を管理し、プログラムの設定値を保持する。
 * 
 * @author kasugai
 * @since 1.0
 * @version 2.0
 */
public class WeatherData implements Const{
		
	/**
	 * APIから取得できる情報から icon_id を保管
	 */
	private Vector<String> icon_ids = new Vector<>();
	
	/**
	 * APIから取得できる情報から 気温 を保管
	 */
	private Vector<Double> temps = new Vector<>();
	
	/**
	 * APIから取得できる情報から 湿度 を保管
	 */
	private Vector<Double> humidities = new Vector<>();
	
	/**
	 * APIから取得できる情報から 風速 を保管
	 */
	private Vector<Double> winds = new Vector<>();
	
	
	/**
	 * 指定された都市の情報を5日分取得する
	 * 
	 * @param requestUrl　APIurl + 都市コード を入力
	 */
	public WeatherData(String requestUrl){

		JsonNode forecastList = null;
		
		try {
			URL url = new URL(requestUrl);
			
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			
			int responseCode = connection.getResponseCode();
			
			if(responseCode == HttpURLConnection.HTTP_OK) {
				
				InputStreamReader isr = new InputStreamReader(connection.getInputStream());
				
				BufferedReader br = new BufferedReader(isr);
				
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode node = mapper.readTree(br);

		        forecastList = node.get("list");

			}else {
				System.out.println("取得失敗");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        for(JsonNode forecast : forecastList) {
        	String forecast_time = forecast.get("dt_txt").asText();		        	
        				        
	        if (forecast_time.endsWith(TIME_OF_WEATHER)) {
		        
		        
		        /*	コンソールで出力
		         * 
		         *  String icon_id = forecast.get("weather").get(0).get("icon").asText();
				 *
		         *　Map<String, Double> weather = new HashMap<>(){{
		         *		put("temp",roundUpToFirstDecimalPlace(forecast.get("main").get("temp").asDouble() + TEMP_K));
		         *		put("humidity",roundUpToFirstDecimalPlace(forecast.get("main").get("humidity").asDouble()));
		         *		put("wind",roundUpToFirstDecimalPlace(forecast.get("wind").get("speed").asDouble()));
	        	 *　}};
	        	 * 
		         *	System.out.println("時間：" + forecast_time);
		         *	System.out.println("天気id：" + icon_id);
		         *	System.out.println("気温：" + weather.get("temp"));
		         *	System.out.println("湿度：" + weather.get("humidity") + "%");
		         *	System.out.println("風速(m/s)：" + weather.get("wind"));
		         *	System.out.println("-------------------------------------");
		         *
		         */
		        
		        icon_ids.add(forecast.get("weather").get(0).get("icon").asText());
		        temps.add(roundUpToFirstDecimalPlace(forecast.get("main").get("temp").asDouble() + TEMP_K));
		        humidities.add(roundUpToFirstDecimalPlace(forecast.get("main").get("humidity").asDouble()));
		        winds.add(roundUpToFirstDecimalPlace(forecast.get("wind").get("speed").asDouble()));
	        }
        } 
	}
	
	/**
	 * 実数値の小数点切り上げ関数
	 * 
	 * @param value　実数値を指定
	 * @return　指定された実数値の小数点第一までを戻す
	 */
	public static double roundUpToFirstDecimalPlace(double value) {
	    return Math.ceil(value * 10) / 10.0;
	}

	
	/*
	 * 各ゲッターはi日後の情報を戻す
	 */
	public String getIconID(int i){
		return icon_ids.get(i);
	}
	public Double getTemp(int i){
		return temps.get(i);
	}
	public Double getHumidity(int i){
		return humidities.get(i);
	}
	public Double getWind(int i){
		return winds.get(i);
	}
}
