import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * DayLabel
 * 1日分の天気情報を取得、配置するクラス
 * 
 * 主なクラス:
 * - {@link Const}: 定数を管理し、プログラムの設定値を保持する。
 * 
 * @author kasugai
 * @since 1.0
 * @version 2.0
 */

public class DayLabel implements Const{
	
	/**
	 * no_imageやAPIから取得した画像を管理
	 */
	private ImageIcon icon;
	
	/**
	 * icon画像の描画用ラベル
	 */
	private JLabel img_l;
	
	/**
	 * 日付、気温、湿度、風速など天気情報の注釈用ラベル
	 */
	private Map<String,JLabel> ttls;
	
	/**
	 * APIから情報を取得、表示する用のラベル
	 */
	private Map<String,JLabel> vals;
	
	/**
	 * 1日分のUIを配置するパネル
	 */
	private JPanel panel;
	
	
	/**
	 * 1日分の天気情報を配置
	 * 
	 * @param i　当日からi日後の情報を取得
	 */
	public DayLabel(int i) {
		
		panel = new JPanel();
		
		// パネルを透過
		panel.setOpaque(false);
		
		// 日付を初期化
        Date date = new Date();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, i);
        
        Date new_date = calendar.getTime();
        
        SimpleDateFormat date_f = new SimpleDateFormat("MM/dd");
		
		ttls = new HashMap<>() {{
			put("day_l", new JLabel(date_f.format(new_date)));
			put("temp_l", new JLabel("気温(℃)"));
			put("humidity_l", new JLabel("湿度(%)"));
			put("wind_l", new JLabel("風速(m/s)"));
		}};
		
		
		vals = new HashMap<>() {{
			put("temp_v", new JLabel("-"));
			put("humidity_v", new JLabel("-"));
			put("wind_v", new JLabel("-"));
		}};
				
		// 画像の初期化
	    icon = new ImageIcon(NO_IMG_ADRESS);
	    icon = this.resizeImage(icon);

		img_l = new JLabel(icon);
		
		JLabel[] components = {
			ttls.get("day_l"),
			img_l,
			ttls.get("temp_l"),
			vals.get("temp_v"),
			ttls.get("humidity_l"),
			vals.get("humidity_v"),
			ttls.get("wind_l"),
			vals.get("wind_v")
		};
		
		for(JLabel tmp_l: ttls.values()) { 
			tmp_l.setBorder(SMALL_MG_BTM);
			if(tmp_l.equals(ttls.get("day_l"))) {
				tmp_l.setFont(MEDIUM_FONT);
			}			
		}
		for(JLabel tmp_l: vals.values()) {
			tmp_l.setBorder(LARGE_MG_BTM);
			tmp_l.setFont(MEDIUM_FONT);
		}
		img_l.setBorder(LARGE_MG_BTM);
		
		for(JLabel temp_c: components) {
			// オブジェクトを中央ぞろえ
			temp_c.setAlignmentX(Component.CENTER_ALIGNMENT);

			// 配色を白に変更
			temp_c.setForeground(Color.WHITE);
			
			// パネルに配置
			panel.add(temp_c);
		}
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
	}

	/**
	 * 1日分の天気情報を配置したパネルを取得
	 * 
	 * @return　panel
	 */
	public JPanel getDayPanel() {
		return panel;
	}
	
	/**
	 * 天気情報を入力
	 * 
	 * @param kind　入力するオブジェクトを指定
	 * @param data　入力する文字列を指定
	 */
	public void setWeatherData(String kind, String data) {
		vals.get(kind).setText(data);
	}

	/**
	 * 画像idを指定してリサイズ、上書きする
	 * 
	 * @param id　APIから取得したiconIdを指定
	 */
	public void setImage(String id) {
		try {
			URL url = new URL("https://openweathermap.org/img/wn/" + id + "@2x.png");
			
			icon = new ImageIcon(url);
	        
	        // 画像のリサイズ処理			
			icon = resizeImage(icon);
			
			img_l.setIcon(icon);

		}catch(MalformedURLException e){
	        icon = new ImageIcon(NO_IMG_ADRESS);
	        panel.add(img_l);
		}
	}
	
	/**
	 * ImageIcon型にセットされた画像をリサイズして返還する
	 * 
	 * @param icn　画像がセットされたオブジェクトを指定
	 * @return　ImageがリサイズされたImageIcon
	 */
	private ImageIcon resizeImage(ImageIcon icn) {
        // 画像のリサイズ処理
        Image img = icn.getImage().getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        
        icn = new ImageIcon(img);
        
        return icn;
	}
}
