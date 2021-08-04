package server;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Judge {
	private ImageIcon blackIcon, whiteIcon, boardIcon;

	public static void reverseJudge(int theBnum, JButton[] buttonArray, String setcolor) {
		boolean flag = false;

		int mycolor = Integer.valueOf(setcolor);

		//		if (mycolor == 0) {
		if (theBnum > 8)
			System.out.println(buttonArray[theBnum - 7].getIcon());

		//			if (!(theBnum % 8 == 0)) buttonArray[theBnum-8].getIcon();
		/*		} else {
		
				}*/
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
