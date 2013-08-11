import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import javax.swing.JOptionPane;

import javazoom.jl.decoder.JavaLayerException;

public class ThreadSom extends FrmAgenda implements Runnable{
	public ThreadSom() throws ParseException {
		super();
	}
	PegaDataAtual pda = new PegaDataAtual();
	//metodo obrigatorio da implementacao Runnable
        @Override
	public void run(){
		try {
			javazoom.jl.player.Player p = null;
			//abre a data
			FileReader arqData = new FileReader("DataAlarme.txt");
			BufferedReader lerArqData = new BufferedReader(arqData);
			String data = lerArqData.readLine();
			arqData.close();
			//compara a data armazenada com a data atual
			if(data.equals(pda.getDateTime()))
			{
				threadForm.setVisible(true);
				//abrir o arquivo do som e executa
				FileReader arqSom = new FileReader("SomAlarme.txt");
				BufferedReader lerArqSom = new BufferedReader(arqSom);
				String nome = lerArqSom.readLine();
				InputStream in = new FileInputStream(nome);
				p = new javazoom.jl.player.Player(in);
				p.play();
				
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		} catch (JavaLayerException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}	
	}
}