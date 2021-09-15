package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RedesController {

	public RedesController() {
		super();
	}
	
	private String os() {
		String os = System.getProperty("os.name"); 
		return os;
	}
	
	public void ip() {
		String os = os();
		if (os.contains("Windows")) {
			ipWin();
		}
		
		if (os.contains("Linux")) {
		ipLinux();	
		}
	}
	
	public void ping() {
		pingOp();
	}
	
	public void ipWin() {
		String cmd = "ipconfig";
		try {
		Process p = Runtime.getRuntime().exec(cmd);
		InputStream is = p.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader buffer = new BufferedReader (isr);
		String linha = buffer.readLine();
		int contLinhaVazia = 0;
		int contAdapter = 0;
		StringBuffer stringBuffer = new StringBuffer();
		while (linha != null) {
			if (linha.contains("adapter")) {
				stringBuffer.append(linha);
				stringBuffer.append("\n");
				contAdapter++;
			}
			if (contAdapter == 1 && linha.trim().equals("")) {
				contLinhaVazia++;
			}
			if (contLinhaVazia == 1) {
				stringBuffer.append(linha);
				stringBuffer.append("\n");
			}
			if (contLinhaVazia == 2) {
				if (stringBuffer.toString().contains("IPv4")) {
					System.out.println(validaIPv4Win(stringBuffer.toString()));
				}
				contLinhaVazia = 0;
				contAdapter = 0;
				stringBuffer = new StringBuffer();
			}
			linha = buffer.readLine();
			if (linha == null) {
				System.out.println(validaIPv4Win(stringBuffer.toString()));
			}
		}
			buffer.close();
			isr.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void ipLinux() {
		String cmd = "ifconfig";
		try {
		Process p = Runtime.getRuntime().exec(cmd);
		InputStream is = p.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader buffer = new BufferedReader (isr);
		String linha = buffer.readLine();
		int contLinhaVazia = 0;
		int contAdapter = 0;
		StringBuffer stringBuffer = new StringBuffer();
		while (linha != null) {
			if (linha.contains("flags")) {
				stringBuffer.append(linha);
				stringBuffer.append("\n");
				contAdapter++;
			}
			if (contAdapter == 1 && linha.trim().equals("")) {
				contLinhaVazia++;
			}
			if (contLinhaVazia == 1) {
				if (stringBuffer.toString().contains("netmask")) {
					System.out.println(validainetLin(stringBuffer.toString()));
				}
				contLinhaVazia = 0;
				contAdapter = 0;
				stringBuffer = new StringBuffer();
			}
			linha = buffer.readLine();
			if (linha == null) {
				System.out.println(validainetLin(stringBuffer.toString()));
			}
		}
			buffer.close();
			isr.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String validaIPv4Win (String adapter) {
		StringBuffer buffer = new StringBuffer();
		String[] vetadapter = adapter.split("\n");
		for (String s: vetadapter) {
			if (s.contains("IPv4")) {
				buffer.append(vetadapter[0]);
				String[] vetLinha = s.split(":");
				buffer.append(" " +vetLinha[1]);
			}
			
		}
		return buffer.toString();	
	}
	
	private String validainetLin (String adapter) {
		StringBuffer buffer = new StringBuffer();
		String[] vetadapter = adapter.split("\n");
		for (String s: vetadapter) {
			if (s.contains("flags")) {
				String[] vetLinha1 = s.split(":");
				buffer.append(" " +vetLinha1[0]);
			}
			if (s.contains("netmask")) {
				String[] vetLinha2 = s.split(" ");
				buffer.append(" " +vetLinha2[1]);
			}
			
		}
		return buffer.toString();	
	}
	
	public void pingOp() {
		String os = os();
		String cmd = " ping -4 ";
		StringBuffer bufferString = new StringBuffer();
		bufferString.append(cmd);
		if (os.contains("Windows")) {
			bufferString.append("-n ");
		}
		if (os.contains("Linux")) {
			bufferString.append("-c ");
		}
		bufferString.append("10 www.google.com.br");
		try {
			Process p = Runtime.getRuntime().exec(bufferString.toString());
			InputStream is = p.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader buffer = new BufferedReader (isr);
			String linha = buffer.readLine();
			while (linha != null) {
				if (os.contains("Windows")) {
					if (linha.contains("Average")) {
						String[] vetUltimaLinha = linha.split(",");
						String[] vetMedia = vetUltimaLinha[2].split("=");
						System.out.println(bufferString.toString()+" Media = " +vetMedia[1]+ "ms");
					}
				}
				if (os.contains("Linux")) {
					if (linha.contains("avg")) {
						String[] vetUltimaLinha = linha.split(",");
						String[] vetMedia = vetUltimaLinha[2].split("/");
						System.out.println(bufferString.toString()+" Media = " +vetMedia[1]+" ms");
					}
				}
				linha = buffer.readLine();
			}
			buffer.close();
			isr.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}
}