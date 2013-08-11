import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.ServerSocket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.SystemColor;

public class FrmAgenda extends JFrame implements Runnable{
	//metodo obrigatorio para o implementacao Runnable
        @Override
	public void run()
	{
		
	}
	//variaves
	private static ServerSocket s; 
	static FrmAgenda threadForm;
	private JPanel contentPane;
	private JTextArea txtArea;
	private final JFormattedTextField txtData;
	private final JFormattedTextField txtHora;
	//Formatando data para ser mostrada no form
	private static final DateFormat FORMATODATA = new SimpleDateFormat(  
            "dd/MM/yyyy HH:mm:ss"); 
	private static final DateFormat DATA = new SimpleDateFormat(  
            "dd/MM/yyyy");
	private static final DateFormat HORA = new SimpleDateFormat(  
            "HH:mm"); 
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
                        @Override
			public void run() {
				try {
					//thread que executa o form FrmAgenda
					s = new ServerSocket(9581);
					threadForm = new FrmAgenda();
					threadForm.setVisible(true);
					Thread thread = new Thread(threadForm);
					thread.start();
					//thread que executa a classe PegaDataAtual
					PegaDataAtual threadData = new PegaDataAtual();
					Thread thread1 = new Thread(threadData);
					thread1.start();
				} catch (IllegalThreadStateException e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Aplicativo ja esta aberto!");
                                }
			}
		});
	}
	//construtor da classe 
	public FrmAgenda() throws ParseException {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("images.jpg"));//icone do form FrmAgenda
		Timer t = new Timer(1000, new ClockAction());  
                t.setInitialDelay(0);  
                t.setRepeats(true);  
                t.start();  
   
        //setando asconfiguracoes para form FrmAgenda
		setResizable(false);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 153, 102));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		//criacao dos label
		JLabel lblNewLabel = new JLabel("Hora");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 42, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Data");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_1.setBounds(10, 17, 46, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Lembrete");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_2.setBounds(265, 30, 58, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Para fechar sem desligar o alarme use o X no canto superior direito");
		lblNewLabel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblNewLabel_3.setBackground(Color.WHITE);
		lblNewLabel_3.setForeground(SystemColor.desktop);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblNewLabel_3.setBounds(74, 0, 376, 14);
		contentPane.add(lblNewLabel_3);
		
		//criacao dos campos formatados para hora e data
		txtHora = new JFormattedTextField(new MaskFormatter("##:##"));
		txtHora.setBackground(new Color(255, 255, 153));
		txtHora.setBounds(58, 39, 46, 20);
		contentPane.add(txtHora);
		Date horaEntrada = new Date();
		txtHora.setText(HORA.format(horaEntrada));
	
		txtData = new JFormattedTextField(new MaskFormatter("##/##/####"));
		txtData.setBackground(new Color(255, 255, 153));
		txtData.setBounds(58, 15, 99, 20);
		txtData.setSelectionStart(0);  
	    txtData.setSelectionEnd(txtData.getText().length()); 
		contentPane.add(txtData);
		Date dateEntrada = new Date();
		txtData.setText(DATA.format(dateEntrada));
		
		//criacao dos botoes		
		JButton btSelecionar = new JButton("Selecionar Som");
		btSelecionar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btSelecionar.setBackground(new Color(255, 255, 255));
		btSelecionar.setBounds(10, 72, 147, 23);
		contentPane.add(btSelecionar);
		
		JButton btSair = new JButton("Sair");
		btSair.setFont(new Font("Tahoma", Font.BOLD, 12));
		btSair.setBackground(new Color(255, 255, 255));
		btSair.setBounds(58, 228, 58, 23);
		contentPane.add(btSair);
		
		JButton btAgendar = new JButton("Gravar alarme");
		btAgendar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btAgendar.setBackground(new Color(255, 255, 255));
		btAgendar.setBounds(10, 106, 147, 23);
		contentPane.add(btAgendar);
		
		//criacao da area de texto
		txtArea = new JTextArea();
		txtArea.setWrapStyleWord(true);
		txtArea.setFont(new Font("Monospaced", Font.BOLD, 13));
		txtArea.setBackground(new Color(255, 255, 153));
		txtArea.setLineWrap(true);
		txtArea.setBounds(184, 55, 240, 196);
		contentPane.add(txtArea);
		
		//separadores 
		JSeparator separator = new JSeparator();
		separator.setBounds(167, 27, 283, 2);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(166, 27, 7, 245);
		contentPane.add(separator_1);
		
		//Acao para o botao de seleciona som
		btSelecionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PegaDataAtual ca;
				try {
					ca = new PegaDataAtual();
					ca.selecionaSom();
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
				}
			}
		});
		//Acao para o botao sair
		btSair.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent arg0) {
				int op = JOptionPane.showConfirmDialog(null, "Se voce confirmar desligara o ALARME"," Atencao",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				if (op == JOptionPane.YES_OPTION ) { 
					try {
						s.close();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null,"Erro em executar o Socket");
					}
					System.exit(0); 
					} 
			}
		});
		//Acao para o botao agendar o alarme
		btAgendar.addActionListener(new ActionListener() {
                        @Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					PegaDataAtual ca = new PegaDataAtual();
					ca.gravarData(txtData.getText(),txtHora.getText(),txtArea.getText());
					ca.gravarLembrete(txtArea.getText());
					txtArea.setText("");
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,e.getMessage());
				}

			}
		});
		
	}
	//metodo para adicionar o titulo ao form com a data e hora
	private void setData(Date date){  

		setTitle("Lembrete - "+FORMATODATA.format(date)); 
		
        }
	//metodo que atualiza a data e hora e verifica se ha alarme agendado para aquele instante
	 private class ClockAction implements ActionListener {  
		  
                @Override
	        public void actionPerformed(ActionEvent e) { 
	        	
				setData(new Date()); 
	        	GregorianCalendar gc = new GregorianCalendar();
	        	int seg = gc.get(Calendar.SECOND);
	        	if(seg == 00){
	        	ThreadSom threadSom;
				try {
				//	execucao da thread q toca o som
					threadSom = new ThreadSom();
					Thread thread2 = new Thread(threadSom);
					thread2.start();
					Thread.sleep(5*200);//atrasa a thread
					
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (InterruptedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
	        }
				ThreadLembrete threadLembrete;
				if(seg == 01){
				try {
					//thread para mostrar o lembrete
					threadLembrete = new ThreadLembrete();
					txtArea.setText(threadLembrete.abrirLembrete());
					Thread thread3 = new Thread(threadLembrete);
					thread3.start();
		        
				} catch (ParseException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
	 }
	 
}

}