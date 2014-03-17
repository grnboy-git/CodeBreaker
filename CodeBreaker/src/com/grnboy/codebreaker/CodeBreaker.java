package com.grnboy.codebreaker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class CodeBreaker extends JFrame implements ActionListener{
	private JPanel panel;

    private JPanel centorPanel;

    private JPanel southPanel;

    private JScrollPane sc;

    private DefaultTableModel dtm;

    private JTable resultTable;

    private JComboBox[] inputBox = new JComboBox[3];

    private String[] selector = { "1", "2", "3", "4", "5", "6" };

    private JButton judge;

    public CodeBreaker() {
        //GUIの初期化
        //Panelの初期化
        panel = new JPanel(new BorderLayout());
        centorPanel = new JPanel();
        southPanel = new JPanel();
        //テーブル部。結果表示。今回はJTableを使用。
        dtm = new DefaultTableModel();
        dtm.addColumn("1");
        dtm.addColumn("2");
        dtm.addColumn("3");
        dtm.addColumn("HIT");
        dtm.addColumn("BLOW");

        resultTable = new JTable(dtm);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        sc = new JScrollPane(resultTable,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        centorPanel.add(sc);

        //入力部分の製作
        for (int i = 0; i < 3; i++) {
            inputBox[i] = new JComboBox(selector);
            inputBox[i].setActionCommand("" + i);
            inputBox[i].addActionListener(this);
            southPanel.add(inputBox[i]);
        }
        judge = new JButton("JUDGE!!");
        judge.setActionCommand("JUDGE");
        judge.addActionListener(this);
        southPanel.add(judge);

        //テーブル部、入力部のセット。
        panel.add(centorPanel, BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);
        this.getContentPane().add(panel);

        //表示
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100, 100, 400, 500);
        this.pack();
        this.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent arg0) {
    }
        
	public static void main(String[] args) {
		//作成したクラスの初期化
		CBEngine cbe = new CBEngine();
		
		// 変数の初期化。
		/*
		 * 必要な変数 タイトル、ルール文、答えの格納される配列、 入力された数字の格納される配列。 ヒット数、ブロー数、チャレンジの回数
		 */

		// タイトル文
		String title = cbe.getTitle();

		// ルール説明文
		String rules = cbe.getRule();

		// 初期数字の格納される配列
//		int[] answerNum = new int[3];

		// 推理数字の格納される配列
//		int[] inputNum = new int[3];

		// 推理数字と解答数字を比較してのヒットカウンタ
//		int countHit = 0;
//		int countBlow = 0;
		int countChallenge = 0;
		
		// ゲームの初期化はここで実施
		cbe.initGame();

		// タイトルとルールの表示
		System.out.println(title);
		System.out.println(rules);

		// ランダムな答えを作成。
		// ただし、仕様通り、同じ数字がないようにする。
		cbe.setAnswerNum();

		// 入力させる。数値のチェックを行う。
		while (true) {
		countChallenge++;
		System.out.println("*******" + countChallenge + "回目の挑戦！*******");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			for (int i = 0; i <= cbe.getDefaultCardNum() - 1; i++) {
				System.out.print( (i + 1) + "個目 : ");
				try {
					cbe.inputCardNum(i,br.readLine());
				} catch (InputException e) {
					System.err.print(e.getMessage());
					i--;
				} catch (IOException e) { // 文字inputってこの辺りが必要？
					System.err.println("もう一度入力してください");
					i--;
				}
			}
			
			// 終了判断　ヒットが3つになったら終了
			if (cbe.judgeAnswer()) { //ここでカードのマッチ判定のメソッドを実行してる
				System.out.println("３つのカード番号を当てました！おめでとうございます！");
				System.out.println(countChallenge + "回目のチャレンジでした。");
				break;
			} else {
				System.out.println("残念！あなたのHIT数は" + cbe.getHit() + "で、BLOW数は"
						+ cbe.getBlow() + "でした。");
				System.out.println(countChallenge + "回目のチャレンジでした。");
			}
		}
	}
}
