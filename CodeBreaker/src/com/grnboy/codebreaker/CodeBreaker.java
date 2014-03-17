package com.grnboy.codebreaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class CodeBreaker {
	public static void main(String[] args) {
		// 変数の初期化。
		/*
		 * 必要な変数 タイトル、ルール文、答えの格納される配列、 入力された数字の格納される配列。 ヒット数、ブロー数、チャレンジの回数
		 */

		// タイトル文
		String title = "Code Breaker!!";

		// ルール説明文
		String rules = "▼ゲームの遊び方▼\n"
		// +"\n"
		// +"\n"
		// +"\n"
		// +"\n"
		// +"\n"
		// +"\n"
				+ "\n";

		// 初期数字の格納される配列
		int[] answerNum = new int[3];

		// 推理数字の格納される配列
		int[] inputNum = new int[3];

		// 推理数字と解答数字を比較してのヒットカウンタ
		int countHit = 0;
		int countBlow = 0;
		int countChallenge = 0;

		// タイトルとルールの表示
		System.out.println(title);
		System.out.println(rules);

		// ランダムな答えを作成。
		// ただし、仕様通り、同じ数字がないようにする。
		Random random = new Random();
		for (int i = 0; i <= 2; i++) {
			int num = random.nextInt(6) + 1;
			for (int j=i-1; j>=0; j--){
				while (answerNum[j]==num) {
					num = random.nextInt(6) + 1;
				}
			}
			
			
			
			answerNum[i] = num;
		}
		
		
		
//		for (int i = 0; i <= 2; i++) {
//			System.out.println(answerNum[i]);
//		}

		// 入力させる。数値のチェックを行う。
		countChallenge++;
		System.out.println("*******" + countChallenge + "回目の挑戦！*******");
		while (true) {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					System.in));
			for (int i = 0; i <= answerNum.length - 1; i++) {
				try {
					inputNum[i] = Integer.parseInt(br.readLine());
				} catch (NumberFormatException e) {
					System.err.print(i + 1 + "文字目の数値を入力してください:");
					i--;
				} catch (IOException e) {// 文字inputってこの辺りが必要？
					System.err.println("もう一度入力してください");
					i--;
				}
			}
			
			
//			for (int i = 0; i <= 2; i++) {
//				System.out.println(inputNum[i]);
//			}
			
			// 答え判断			
			countHit = 0;
			countBlow = 0;

			for (int i = 0; i <= answerNum.length - 1; i++) {
				if (answerNum[i] == inputNum[i]) {
					countHit++;
				} else {
					for (int j = 0; j
							<= answerNum.length - 1; j++) {
						if (answerNum[i] == inputNum[j]) {
							countBlow++;
							System.out.println(i);
						}
					}
				}
			}

			// 終了判断　ヒットが3つになったら終了
			if (countHit == 3) {
				System.out.println("３つのカード番号を当てました！おめでとうございます！");
				System.out.println(countChallenge + "回目のチャレンジでした。");
			} else {
				System.out.println("残念！あなたのHIT数は" + countHit + "で、BLOW数は"
						+ countBlow + "でした。");
				System.out.println(countChallenge + "回目のチャレンジでした。");
			}
		}
	}
}
