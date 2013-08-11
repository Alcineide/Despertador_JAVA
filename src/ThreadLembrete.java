import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;

import javax.swing.JOptionPane;

import javazoom.jl.decoder.JavaLayerException;


public class ThreadLembrete extends FrmAgenda implements Runnable {
	public ThreadLembrete() throws ParseException {
		super();
	}
	PegaDataAtual pda = new PegaDataAtual();
	public String abrirLembrete()
	{
		String lembrete = null;
		try {
			//abre o arquivo de data
			FileReader arqData = new FileReader("DataAlarme.txt");
			BufferedReader lerArqData = new BufferedReader(arqData);
			String data = lerArqData.readLine();
			arqData.close();
			//compara com a data atual
			if(data.equals(pda.getDateTime()))
			{
				try {
					//abrir lembrete para que seja mostrado na tela
					FileReader arqLembrete = new FileReader("Lembrete.txt");
					BufferedReader lerArqLembrete = new BufferedReader(arqLembrete);
					lembrete = lerArqLembrete.readLine();
					lerArqLembrete.close();
				} catch (FileNotFoundException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
		return lembrete;
	}
	@Override
	//metodo obrigatorio da implementacao Runnable
	public void run() {
	 }


}
