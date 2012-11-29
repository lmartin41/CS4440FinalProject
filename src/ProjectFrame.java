
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;


public class ProjectFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private DAO dao;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProjectFrame frame = new ProjectFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProjectFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width/2;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height/2;		
		setBounds(screenWidth-400, screenHeight-300, 800, 600);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[][grow][grow][][grow][][grow][][]", "[][][][][][][][grow][][][][][]"));

		final JButton btnSelectAFile = new JButton("Select a File");
		btnSelectAFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fc = new JFileChooser(DAO.ACCESS_PATH);
				int retVal = fc.showOpenDialog(btnSelectAFile);

				if (retVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					textField_3.setText(file.toString());
				}
			}
		});
		contentPane.add(btnSelectAFile, "cell 1 0");

		textField_3 = new JTextField();
		contentPane.add(textField_3, "cell 2 0 5 1,growx");
		textField_3.setColumns(10);

		JButton btnConvert = new JButton("Convert");
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				File file = new File(textField_3.getText());
				String titleRaw = file.getName();
				String title = titleRaw.substring(0, titleRaw.indexOf("."));
				String extraction = Convert.extract(file);
				Convert.convertToXML(title, extraction);
			}
		});
		contentPane.add(btnConvert, "cell 7 0");

		final JButton btnBrowse = new JButton("View Current Directory");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JFileChooser fc = new JFileChooser(DAO.ACCESS_PATH);
				fc.showOpenDialog(btnBrowse);
			}
		});

		contentPane.add(btnBrowse, "cell 2 2");

		JLabel label = new JLabel("");
		contentPane.add(label, "cell 2 3");

		JLabel lblElement = new JLabel("Element:");
		contentPane.add(lblElement, "cell 1 4,alignx trailing");

		textField = new JTextField();
		contentPane.add(textField, "cell 2 4,growx");
		textField.setColumns(10);

		JLabel lblRestrictBy = new JLabel("Restrict by:");
		contentPane.add(lblRestrictBy, "cell 3 4,alignx trailing");

		textField_1 = new JTextField();
		contentPane.add(textField_1, "cell 4 4,growx");
		textField_1.setColumns(10);

		JLabel lblReturn = new JLabel("Return:");
		contentPane.add(lblReturn, "cell 5 4,alignx trailing");

		textField_2 = new JTextField();
		contentPane.add(textField_2, "cell 6 4,growx");
		textField_2.setColumns(10);

		JButton btnQuery = new JButton("Query");

		btnQuery.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent event) {
				String element = textField.getText();
				String restrict = textField_1.getText();
				String returnVal = textField_2.getText();

				dao = new DAO();
				dao.openConnection();

				String query = giveMeQuery(element, restrict, returnVal);
				dao.executeQuery(query);

				dao.closeConnection();
			}
		});

		contentPane.add(btnQuery, "cell 7 4");

		JLabel lblOutput = new JLabel("Output:");
		contentPane.add(lblOutput, "cell 1 5");

		JTextArea textArea = new JTextArea();
		contentPane.add(textArea, "cell 1 6 7 2,grow");
	}

	private String giveMeQuery(String element, String restrict,
			String returnVal) {

		String query = "for $x in //" + element + " where $x//" + restrict + " return $x//" + returnVal;
		return query;
	}

}
