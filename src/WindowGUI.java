import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 * ユーザーインターフェースの作成と管理を行う。
 * 
 * 主なクラス:
 * - {@link DayLabel}: 1日分の天気情報を配置する。
 * - {@link WeatherData}: APIに接続し、天気情報を取得する。
 * - {@link Const}: 定数を管理し、プログラムの設定値を保持する。
 * 
 * @author kasugai
 * @since 1.0
 * @version 2.0
 */

public class WindowGUI extends JFrame implements ItemListener, Const{
	
	/**
	 * アプリケーション上部のリスト
	 */
	private JComboBox<String> box;
	
	/**
	 * リストの補完用ラベル
	 */
	private JLabel select_l;
	
	/**
	 * 上部、下部のコンテンツを格納するパネル
	 */
	private Map<String, JPanel> panel;
	
	/**
	 * 5日分の天気情報を取得、配置
	 */
	private DayLabel[] days = new DayLabel[API_FORECAST_DAYS];
	
	/**
	 * ウィンドウに配置するコンテナ
	 */
	private Container content;
	
	/**
	 * アプリケーション下部のプログレスバー
	 */
	private JProgressBar progress_bar = new JProgressBar(0, 100);
	
	/**
	 * ウィンドウの作成とUIコンポーネントの初期化と配置
	 * 
	 *  @param title　アプリのタイトルを指定
	 *  @param w_width　ウィンドウの幅を指定
	 *  @param w_height　ウィンドウの高さを指定
	 */
	WindowGUI(String title,int w_width, int w_height){
		// タイトルを指定		
		super(title);
		
		// ウィンドウサイズを指定		
		this.setSize(w_width, w_height);
		
		// ウィンドウのリサイズを無効化
		this.setResizable(false);
		
		//ウィンドウをモニターの中央に配置
		this.setLocationRelativeTo(null);
		
		//ウィンドウを閉じたらプログラムを終了
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// メインのJPanelを初期化
		panel = new HashMap<>(){{
			put("select_p", new JPanel());
			put("main_p", new JPanel());
		}};
		
		// パネルの透過
		for(JPanel tmp_p: panel.values()) {
			tmp_p.setOpaque(false);
		}
		
		// JComboBoxの初期化
		box = new JComboBox<String>(CHOSEN_CITIES);
		box.addItemListener(this);
		
		// セレクター用のラベルを初期化
		select_l = new JLabel("都市");
				
		select_l.setForeground(Color.WHITE);
		select_l.setFont(LARGE_FONT);
		select_l.setBorder(LARGE_MG_BTM);
		
		select_l.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		// 都市選択のGUIを配置
		panel.get("select_p").add(select_l);
		panel.get("select_p").add(box);
		panel.get("select_p").setLayout(new BoxLayout(panel.get("select_p"), BoxLayout.Y_AXIS));
		panel.get("select_p").setBorder(TOP_PANEL_MG);

		// 余白を設置
		panel.get("main_p").add(Box.createGlue());

		// 日付別のGUIを初期化
		for(int i = 0; i < days.length; i++) {
			// インスタンス化
			days[i] = new DayLabel(i);	
			
			// 日付別のGUIを配置
			panel.get("main_p").add(days[i].getDayPanel());
			// 余白を設置
			panel.get("main_p").add(Box.createGlue());
		}
		
		panel.get("main_p").setLayout(new BoxLayout(panel.get("main_p"), BoxLayout.X_AXIS));

		
		content = getContentPane();
		
		//色変更
		content.setBackground(Color.DARK_GRAY);
		
		content.add(panel.get("select_p"),BorderLayout.NORTH);
		content.add(panel.get("main_p"),BorderLayout.CENTER);
		
		// プログレスバーを初期化
		progress_bar.setValue(0);
		progress_bar.setStringPainted(true);
		progress_bar.setFont(LARGE_FONT);
		content.add(progress_bar, BorderLayout.SOUTH);

		
		setVisible(true);
	}

	/**
	 * ItemListenerの関数をオーバーライド
	 * JComboBoxの値が変更された時に発火するイベント
	 * 
	 * @param e　JComboBoxの値を想定
	 */
	@Override
	public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
        	switch(e.getItem().toString()) {
        		case "東京": // Tokyo,jp
        			createWeatherData("Tokyo,jp");
        			break;
        		case "ニューヨーク": // New York,us
        			createWeatherData("New%20York,us");
        			break;
        		case "ロンドン": // London,gb
        			createWeatherData("London,gb");	                	
        			break;
        		case "パリ": // Paris,fr
        			createWeatherData("Paris,fr");                	
        			break;
        		case "シドニー": // Sydney,au
        			createWeatherData("Sydney,au");	                	
        			break;
        		case "ベルリン": // Berlin,de
        			createWeatherData("Berlin,de");	                	
        			break;
        		case "モスクワ": // Moscow,ru
        			createWeatherData("Moscow,ru");	                	
        			break;
        		case "ソウル": // Seoul,kr
        			createWeatherData("Seoul,kr");	                	
        			break;
        		case "上海": // Shanghai,cn
        			createWeatherData("Shanghai,cn");	                	
        			break;
        		case "メキシコ": // Mexico City,mx
        			createWeatherData("Mexico%20City,mx");                	
        			break;
            	default:
            		System.out.println("テスト: " + e.getItem());	
            		break;
            }
        }
		
	}
	

	
	/**
	 * シティコードを入力して、新しく天気情報を生成、取得する
	 * 
	 * @param url　シティコードを入力
	 */
	public void createWeatherData(String url) {
		
		// スレッドの作成
		SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>(){
			
			// スレッド作成時の処理
			protected Void doInBackground() throws Exception{
				
				// 気象情報の初期化,インスタンス化
				WeatherData weather_data = new WeatherData(API_URL + url);
				
				for(int i = 0; i < days.length; i++) {
					// IDの入力,画像の再生成
					days[i].setImage(weather_data.getIconID(i));
					
					// 気温の入力
					days[i].setWeatherData("temp_v", weather_data.getTemp(i).toString());
					
					// 湿度の入力
					days[i].setWeatherData("humidity_v", weather_data.getHumidity(i).toString());
					
					// 風速の入力
					days[i].setWeatherData("wind_v", weather_data.getWind(i).toString());
					
					progress_bar.setValue((i + 1) * 20);
					
				}
								
				return null;
			}
			
			// 実行完了時の処理
			protected void done() {
				// ポップアップの表示
				JOptionPane.showMessageDialog(null,"通信が完了しました。");
			}
			
		};
		
		// スレッドの実行
		worker.execute();
		
	}
	
}

