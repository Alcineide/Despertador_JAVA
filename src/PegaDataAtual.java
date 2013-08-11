import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PegaDataAtual extends FrmAgenda implements Runnable{
	public PegaDataAtual() throws ParseException {
		
		super();
			}

	private String som;
	public String data;


	// metodo para retornar a data e hora atual do pc
	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = new Date();
		return dateFormat.format(date);
	}

	// metodo para selecionar o som so alarme
	public void selecionaSom() {
		JFileChooser jf = new JFileChooser();
		try {
			jf.setFileFilter(new FileNameExtensionFilter("files", "mp3"));
			jf.setAcceptAllFileFilterUsed(false);
			jf.setMultiSelectionEnabled(false);
			int escolha = jf.showOpenDialog(null);
			if (escolha == JFileChooser.APPROVE_OPTION) {
				som = jf.getSelectedFile().getPath();
				gravaSom();
			} else {

			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Erro na selecao do arquivo!");
		}
	}
	// metodo para gravar o som do alarme
	public void gravaSom() {
		try {
			FileWriter arqSom = new FileWriter("SomAlarme.txt");
			PrintWriter gravarArqSom = new PrintWriter(arqSom);
			gravarArqSom.print(som);
			arqSom.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
	}

	// metodo para gravar data e hora do alarme
	public void gravarData(String dta, String hora, String lembrete) throws ParseException, IOException {
		try{
			if (dta.equals("  /  /    ")
					|| hora.equals("  :  ")
					|| dta.equals(null) || hora.equals(null)) {
				JOptionPane.showMessageDialog(null,
						"Data ou Hora n�o pode ser vazio!!!");
			} else {
		
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			dateFormat.setLenient(false);
			Date dateEntrada = dateFormat.parse(dta + " "
					+ hora);
			String data = dateFormat.format(dateEntrada);
			FileWriter arq = new FileWriter("DataAlarme.txt");
			PrintWriter gravarArq = new PrintWriter(arq);
			gravarArq.print(data);
			arq.close();
			gravarLembrete(lembrete);
			JOptionPane.showMessageDialog(null,
					"Agendamento realizado!");
			}
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}

	}
	//metodo para gravar lembrete
	public void gravarLembrete(String lembrete){
		
		try {
			FileWriter arqLembrete = new FileWriter("Lembrete.txt");
			PrintWriter gravarArqLemb = new PrintWriter(arqLembrete);
			gravarArqLemb.print(lembrete);
			arqLembrete.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,e.getMessage());
		}
		
	}
	//metodo obrigatorio da implementacao Runnable
	public void run(){
		
	}
}
