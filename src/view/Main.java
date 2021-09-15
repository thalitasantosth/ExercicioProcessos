package view;	

import javax.swing.JOptionPane;
import controller.RedesController;

public class Main {

	public static void main(String[] args) {
			RedesController RedeControl = new RedesController();
			
			String op = JOptionPane.showInputDialog(null, "Digite a opção:\n"
					+ "1 - Lista Adaptadores de rede com IPv4 disponíveis\n"
					+ "2 - Media do ping para o servidor GOOGLE");
			
			if (op.equals("1")) {
			RedeControl.ip();
			}
			if (op.equals("2")) {
			RedeControl.ping();
			}
			
			//RedeControl.ip();
			//RedeControl.ping();
			//String os = RedeControl.os();
			//System.out.println(os);

	}

}
