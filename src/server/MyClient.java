package server;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MyClient extends JFrame implements MouseListener, MouseMotionListener {
	private JButton buttonArray[][];//ボタン用の配列
	private Container c;
	private ImageIcon blackIcon, whiteIcon, boardIcon;
	private String myNumber;
	private int mycolor = 0;
	int turn = 0;
	PrintWriter out;//出力用のライター
	JLabel l = new JLabel("黒ターン");

	public MyClient() {
		//名前の入力ダイアログを開く
		String myName = JOptionPane.showInputDialog(null, "名前を入力してください", "名前の入力", JOptionPane.QUESTION_MESSAGE);
		if (myName.equals("")) {
			myName = "No name";//名前がないときは，"No name"とする
		}

		//ウィンドウを作成する
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ウィンドウを閉じるときに，正しく閉じるように設定する
		setSize(500, 500);//ウィンドウのサイズを設定する
		c = getContentPane();//フレームのペインを取得する

		//アイコンの設定
		whiteIcon = new ImageIcon("./src/server/White.jpg");
		blackIcon = new ImageIcon("./src/server/Black.jpg");
		boardIcon = new ImageIcon("./src/server/GreenFrame.jpg");

		c.setLayout(null);//自動レイアウトの設定を行わない
		//ボタンの生成
		buttonArray = new JButton[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {

				buttonArray[j][i] = new JButton(boardIcon);//ボタンにアイコンを設定する
				c.add(buttonArray[j][i]);//ペインに貼り付ける
				buttonArray[j][i].setBounds(i * 45, j * 45 + 1, 45, 45);//ボタンの大きさと位置を設定する．(x座標，y座標,xの幅,yの幅）
				buttonArray[j][i].addMouseListener(this);//ボタンをマウスでさわったときに反応するようにする
				buttonArray[j][i].setActionCommand(Integer.toString(i));//ボタンに配列の情報を付加する（ネットワークを介してオブジェクトを識別するため）
			}
		}
		buttonArray[3][4].setIcon(blackIcon);
		buttonArray[3][3].setIcon(whiteIcon);
		buttonArray[4][4].setIcon(whiteIcon);
		buttonArray[4][3].setIcon(blackIcon);

		l.setOpaque(true);
		l.setBackground(Color.green);
		l.setForeground(Color.black);
		l.setBounds(10, 400, 300, 50);
		getContentPane().add(l);
		//		c.add(l);
		//サーバに接続する
		Socket socket = null;
		try {
			//"localhost"は，自分内部への接続．localhostを接続先のIP Address（"133.42.155.201"形式）に設定すると他のPCのサーバと通信できる
			//10000はポート番号．IP Addressで接続するPCを決めて，ポート番号でそのPC上動作するプログラムを特定する
			socket = new Socket("localhost", 10000);
		} catch (UnknownHostException e) {
			System.err.println("ホストの IP アドレスが判定できません: " + e);
		} catch (IOException e) {
			System.err.println("エラーが発生しました: " + e);
		}

		MesgRecvThread mrt = new MesgRecvThread(socket, myName);//受信用のスレッドを作成する
		mrt.start();//スレッドを動かす（Runが動く）
	}

	//メッセージ受信のためのスレッド
	public class MesgRecvThread extends Thread {
		Socket socket;
		String myName;

		public MesgRecvThread(Socket s, String n) {
			socket = s;
			myName = n;

		}

		//通信状況を監視し，受信データによって動作する
		public void run() {
			try {
				InputStreamReader sisr = new InputStreamReader(socket.getInputStream());
				BufferedReader br = new BufferedReader(sisr);
				String yourString;

				out = new PrintWriter(socket.getOutputStream(), true);
				myNumber = br.readLine();

				if (Integer.valueOf(myNumber) % 2 == 0) {
					yourString = "黒です。";
					mycolor = 0;
				} else {
					yourString = "白です。";
					mycolor = 1;
				}
				setTitle("あなたは" + yourString);
				while (true) {
					String inputLine = br.readLine();//データを一行分だけ読み込んでみる
					if (inputLine != null) {//読み込んだときにデータが読み込まれたかどうかをチェックする

						System.out.println(inputLine);//デバッグ（動作確認用）にコンソールに出力する
						String[] inputTokens = inputLine.split(" "); //入力データを解析するために、スペースで切り分ける
						String cmd = inputTokens[0];//コマンドの取り出し．１つ目の要素を取り出す

						if (cmd.equals("PLACE")) {
							int theBnum = Integer.parseInt(inputTokens[1]);
							int xInt = theBnum / 8;
							int yInt = theBnum % 8;

							String setcolor = inputTokens[2];

							Judge.reverseJudge(theBnum, buttonArray, setcolor);
							if (turn == 0) {
								buttonArray[xInt][yInt].setIcon(blackIcon);
								l.setForeground(Color.white);
								l.setText("白のターン");
							} else {
								buttonArray[xInt][yInt].setIcon(whiteIcon);
								l.setForeground(Color.black);
								l.setText("黒のターン");
							}
							turn = 1 - turn;
						}
					} else {
						break;
					}

				}
				socket.close();
			} catch (IOException e) {
				System.err.println("エラーが発生しました: " + e);
			}
		}
	}

	public static void main(String[] args) {
		MyClient net = new MyClient();
		net.setVisible(true);
	}

	public void mouseClicked(MouseEvent e) {//ボタンをクリックしたときの処理
		JButton theButton = (JButton) e.getComponent();//クリックしたオブジェクトを得る．型が違うのでキャストする
		String theArrayIndex = theButton.getActionCommand();//ボタンの配列の番号を取り出す
		Icon thisIcon = theButton.getIcon();

		if (mycolor == turn) {

			if (thisIcon.equals(boardIcon)) {
				String msg = "PLACE" + " "
						+ theArrayIndex + " "
						+ mycolor + " "
						+ myNumber;
				//サーバに情報を送る
				System.out.println(theArrayIndex);
				out.println(msg);//送信データをバッファに書き出す
				out.flush();//送信データをフラッシュ（ネットワーク上にはき出す）する
			}
		}
		repaint();//画面のオブジェクトを描画し直す
	}

	public void mouseEntered(MouseEvent e) {//マウスがオブジェクトに入ったときの処理
	}

	public void mouseExited(MouseEvent e) {//マウスがオブジェクトから出たときの処理
	}

	public void mousePressed(MouseEvent e) {//マウスでオブジェクトを押したときの処理（クリックとの違いに注意）
	}

	public void mouseReleased(MouseEvent e) {//マウスで押していたオブジェクトを離したときの処理
	}

	public void mouseDragged(MouseEvent e) {//マウスでオブジェクトとをドラッグしているときの処理
	}

	public void mouseMoved(MouseEvent e) {//マウスがオブジェクト上で移動したときの処理
	}
}