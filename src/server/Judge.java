package server;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Judge {
	private ImageIcon blackIcon, whiteIcon, boardIcon;

	public static void reverseJudge(int theBnum, JButton[][] buttonArray, String setcolor) {

		int mycolor = Integer.valueOf(setcolor);

		int xInt = Integer.valueOf(theBnum) / 8;
		int yInt = Integer.valueOf(theBnum) % 8;
		
		if (Judgebutton(xInt,yInt)) {
			
		}
	}

	private static boolean Judgebutton(int xInt, int yInt) {
		boolean flag = false;
		
		
		return false;
	}

	public void reverse(int theBnum) {
		System.out.println(theBnum);
		if (!(theBnum % 8 == 0)) {

		}

		if (!(theBnum % 8 == 7)) {

		}

		if (!(theBnum < 8)) {

		}

		if (!(theBnum > 55)) {

		}
	}

	public void setWhiteIcon(ImageIcon whiteIcon) {
		this.whiteIcon = whiteIcon;
	}

	public void setBlackIcon(ImageIcon blackIcon) {
		this.blackIcon = blackIcon;
	}

	public void setBoardIcon(ImageIcon boardIcon) {
		this.boardIcon = boardIcon;
	}


}
