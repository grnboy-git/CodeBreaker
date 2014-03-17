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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

// GUIを作ろうのやーつ。JFrameとか聞いたことあるけど。。ど。。。
@SuppressWarnings("serial")
public class CodeBreaker extends JFrame implements ActionListener{
	//作成したクラスの初期化
	CBEngine cbe = new CBEngine();
	
//	初期化祭り。こんな書き方でいいの？不安！超不安！！１１１
	private JPanel panel; //最終的に作って変数名調整しよう。スーパー暫定ver.
    private JPanel centerPanel; //content的な
    private JPanel southPanel; //footerらへん
    private JScrollPane sc; //Pane"l"じゃないの？わかめ。
    private DefaultTableModel dtm; //DTM!なんかかっこいい。
    private JTable resultTable; //そのまま結果表示のView
    @SuppressWarnings("unchecked")
	private JComboBox<String>[] inputBox = new JComboBox[3]; //ComboBoxのプルダウンリスト
    private String[] selector = { "1", "2", "3", "4", "5", "6" }; //pulldownリストのやつ
    private JButton judge; //実行ボタン的な
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public CodeBreaker() {
        //GUIの初期化
        //Panelの初期化
        panel = new JPanel(new BorderLayout()); //newした中でnewする違和感
        centerPanel = new JPanel(); 
        southPanel = new JPanel();
        //テーブル部。結果表示。今回はJTableを使用。
        dtm = new DefaultTableModel();
        dtm.addColumn("1"); //暫定データ載っけてカラム追加しておく。[FIXME]!!!
        dtm.addColumn("2"); 
        dtm.addColumn("3");
        dtm.addColumn("HIT");
        dtm.addColumn("BLOW");

        resultTable = new JTable(dtm);
        resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS); //勝手に幅調整のやつ
        sc = new JScrollPane(resultTable, //スクロールバーはどっちも有効化。neededだから必要なときだけ表示ね。なんか思い出した。
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        centerPanel.add(sc); //centerPanelにスクロールバーaddする。脳みそパンクする

        //入力部分の製作
        for (int i=0; i<3; i++) {
            inputBox[i] = new JComboBox(selector); //pulldownリストを選択
            inputBox[i].setActionCommand("" + i); //
            inputBox[i].addActionListener(this); //
            southPanel.add(inputBox[i]); //footerに入力部品を追加のやつ
        }
        judge = new JButton("JUDGE!!");
        judge.setActionCommand("JUDGE");
        judge.addActionListener(this);
        southPanel.add(judge); //実行ボタンもfooterに追加

        //テーブル部、入力部のセット。
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(southPanel, BorderLayout.SOUTH);
        this.getContentPane().add(panel); //最終的にメインに載せるときはContentPaneに載せるやーつですよ

        //表示させてるところ。
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //表示されるフレームの罰点押したときの処理の選択をするやーつ
        this.setBounds(100, 100, 400, 500); //margin left, upでwidth400のheight500ってこと。だっけ。 
        this.pack(); //ウィンドウのバーの領域は上記指定に含まない的な
        this.setVisible(true); //上の指定ありきでーの、フレームを表示！のやーつ
        
      //ルールの表示
        JOptionPane.showMessageDialog(this, //ダイアログでtitleとruleを表示させてみる
                                      cbe.getRule(), 
                                      cbe.getTitle(),
                                      JOptionPane.INFORMATION_MESSAGE);
        
        // ゲームの初期化
        try {
        	int[] input = {1,1,1};
        	cbe.setInput(input);
        }
        catch(InputException e) {
        	JOptionPane.showMessageDialog(this,//エラー表示もポップアップで。やっつけじゃないから。
        			e.getMessage(),
        			cbe.getTitle(),
        			JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // こっちで処理をどんどんしてく。
    public void actionPerformed(ActionEvent arg0) {
    	System.out.println(arg0.getActionCommand()); //FIXME
        if (arg0.getActionCommand().equalsIgnoreCase("judge")) {
            //Judge
            boolean judge = cbe.judgeAnswer(); //ゲーム結果を格納

            
            //コンソールに表示させる。確認用 FIXME
            int[] ans = cbe.getAnswer();
            System.out.println("答え");
            for (int i = 0; i < ans.length; i++) {
                System.out.print(ans[i]);
            }
            System.out.println();
            System.out.println("入力");
            int[] input = cbe.getInput();
            for (int i = 0; i < input.length; i++) {
                System.out.print(input[i]);
            }
            System.out.println();
            System.out.println("hit:" + cbe.getHit()
                             + " blow:" + cbe.getBlow());

            //結果をresultTableに表示させる
            Object[] row = new Object[5];
            for (int i = 0; i < 3; i++) { //ここはループで
                row[i] = Integer.valueOf(input[i]);
            }
            row[3] = Integer.valueOf(cbe.getHit()); //ここはメソッドから
            row[4] = Integer.valueOf(cbe.getBlow()); //ここはメソッドから
            dtm.addRow(row); //dtmに行の情報を追加する
            if (judge) {
                //全問正解のときの処理
                int res = JOptionPane.showConfirmDialog(this,
                                           "まだ続けますか？",
                                           "おめでとう",
                                           JOptionPane.YES_NO_OPTION); //続けるかDialogで聞く
                if (res == JOptionPane.YES_OPTION) { //Yesが選ばれたら
                    //resultTableの初期化
                    int count = dtm.getRowCount(); //チャレンジ数のカウンタがここに
                    for (int i = 0; i < count; i++) {
                        dtm.removeRow(0);
                    }
                    //GameEngineの初期化
                    cbe.setAnswerNum();
                    try {
                        cbe.setInput(new int[] { 1, 1, 1 });
                    } catch (InputException e) {
                        JOptionPane.showMessageDialog(this,
                                           e.getMessage(),
                                           cbe.getTitle(),
                                           JOptionPane.ERROR_MESSAGE);
                    }
                    cbe.judgeAnswer();
                    //GUIの初期化
                    for (int i = 0; i < inputBox.length; i++) {
                        inputBox[i].setSelectedIndex(0);
                    }
                } else {
                    System.exit(0);
                }

            }

        } else {//Judge以外なので入力部分の処理となる
            //入力部の特定
            int input = Integer.parseInt(arg0.getActionCommand());
            try {
                cbe.inputCardNum(input, (String) inputBox[input]
                        .getSelectedItem());
            } catch (InputException e) {
                JOptionPane.showMessageDialog(this,
                                           e.getMessage(),
                                           cbe.getTitle(),
                                           JOptionPane.ERROR_MESSAGE);
            }
        }
    }
        
	public static void main(String[] args) {
		new CodeBreaker();
	}
}
