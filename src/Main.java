/**
 * プロジェクトのエントリーポイントとなるクラス。
 * 
 * このクラスはアプリケーションのメインウィンドウを作成し、ユーザーインターフェースを起動する。
 *
 * 主なクラス:
 * - {@link WindowGUI}: ユーザーインターフェースの作成と管理を行う。
 * - {@link Const}: 定数を管理し、プログラムの設定値を保持する。
 * 
 * @author kasugai
 * @version 1.0
 * @since 1.0
 */

public class Main implements Const{
	
	public static void main(String[] args) {
	
		new WindowGUI(WINDOW_TITLE,WINDOW_WIDTH,WINDOW_HEIGHT);
		
	}

}
