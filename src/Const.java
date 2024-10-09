import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

public interface Const {
	
	// アプリケーションのウィンドウ情報
	String WINDOW_TITLE = "天気予報ポートフォリオ";
	int WINDOW_WIDTH = 600;
	int WINDOW_HEIGHT = 480;
	
	// 画像のサイズ
	int IMAGE_WIDTH = 100;
	int IMAGE_HEIGHT = 100;
	
	// (フォントの種類,フォントのスタイル,フォントサイズ)
	Font LARGE_FONT = new Font("ＭＳ ゴシック", Font.BOLD, 24);
	Font MEDIUM_FONT = new Font("ＭＳ ゴシック", Font.BOLD, 16); 
	
	// マージン (上,右,下,左)
	Border LARGE_MG_BTM = BorderFactory.createEmptyBorder(0,0,10,0);
	Border SMALL_MG_BTM = BorderFactory.createEmptyBorder(0,0,5,0);
	Border TOP_PANEL_MG = BorderFactory.createEmptyBorder(10,20,10,20);
	
	// 摂氏返還 絶対零度
	double TEMP_K = -273.15;
	
	// APIの取得情報日数(無料版は5)
	int API_FORECAST_DAYS = 5;
	
	// 初期化用の画像パス
	String NO_IMG_ADRESS = "img/no_image_square.jpg";
	
	// IDを含むAPIのアドレス
	String API_URL = "https://api.openweathermap.org/data/2.5/forecast?APPID=02711d57554026067d424493a5a72fcb&q=";
	
	// 都市リスト
	String[] CHOSEN_CITIES = {
		"都市を選択してください",
		"東京",
		"ニューヨーク",
		"ロンドン",
		"パリ",
		"シドニー",
		"ベルリン",
		"モスクワ",
		"ソウル",
		"上海",
		"メキシコ"
	};
	
	// 天気情報を取得する時間
	String TIME_OF_WEATHER = "06:00:00";
}
