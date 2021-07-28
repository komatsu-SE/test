package server;

import javax.swing.JButton;

public class Judge {

	public void reverseJudge(int theBnum, JButton[] buttonArray) {

		if (!(theBnum % 8 == 0)) {
			//			buttonArray[theBnum - 1].getIcon();
			System.out.println(buttonArray[theBnum - 1].getIcon());
		}
		if (!(theBnum % 8 == 7)) {
			//			buttonArray[theBnum + 1].getIcon();
			System.out.println(buttonArray[theBnum + 1].getIcon());
		}
		if (!(theBnum < 8)) {
			//			buttonArray[theBnum - 10].getIcon();
			System.out.println(buttonArray[theBnum - 10].getIcon());
		}
		if (!(theBnum > 55)) {
			//			buttonArray[theBnum + 10].getIcon();
			System.out.println(buttonArray[theBnum + 10].getIcon());
		}
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

}
