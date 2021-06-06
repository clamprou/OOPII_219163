package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import com.sun.jersey.api.client.UniformInterfaceException;
import exception.WikipediaNoArcticleException;
import exercise.City;
import exercise.Traveller;
import exercise.Traveller.IllegalTravellerAge;
import exercise.Traveller.IllegalTravellerRate;
import exercise.Undefined;
import streams.CollaborativeFiltering;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;
import javax.swing.ScrollPaneConstants;

public class FrameUserInfo {
	private JFrame frmExcersize;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JList<String> list;
	private JTextField textField_4;
	private JTextPane txtpnAsd;
	private JTextPane txtpnAsd_1;
	private JTextPane textPane_1_1;
	private JTextPane textPane_1_2;
	private JTextPane textPane_1_2_1;
	private JSpinner spinner;
	private JSpinner spinner_1;
	private JSpinner spinner_1_1;
	private JSpinner spinner_1_2;
	private JSpinner spinner_1_3;
	private JSpinner spinner_1_4;
	private JSpinner spinner_2;
	private JSpinner spinner_3;
	private JSpinner spinner_4;
	private JSpinner spinner_5;
	public Boolean error;
	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameUserInfo window = new FrameUserInfo();
					window.frmExcersize.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrameUserInfo() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmExcersize = new JFrame();
		frmExcersize.getContentPane().setBackground(new Color(105, 105, 105));
		frmExcersize.setTitle("Excersize");
		frmExcersize.setBounds(100, 100, 862, 619);
		frmExcersize.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(169, 169, 169));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(169, 169, 169));
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(169, 169, 169));
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JButton btnNext = new JButton("Find best");
		btnNext.setToolTipText("finds best City");
		btnNext.setForeground(Color.BLACK);
		btnNext.setBackground(Color.WHITE);
		final FrameUserInfo f = this;
		btnNext.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				error = false;
				textPane_1_2_1.setText("");
				textPane_1_1.setText("");
				textPane_1_2.setText("");
				textPane_1_2_1.setText("");
				txtpnAsd.setText("");
				try {
					int[] selected = list.getSelectedIndices();
					City[] allCities = City.getArrayOfCities();
					City[] selectedCities = new City[selected.length];
					for(int i=0;i<selectedCities.length;i++) {
						selectedCities[i] = allCities[selected[i]];
					}
					
					Traveller traveller = Traveller.createTravellerGui(textField.getText(), textField_1.getText(), textField_2.getText(), Integer.parseInt(textField_3.getText()), new int[] {Integer.parseInt(spinner.getModel().getValue().toString()),Integer.parseInt(spinner_1.getModel().getValue().toString()),Integer.parseInt(spinner_1_1.getModel().getValue().toString()),Integer.parseInt(spinner_1_2.getModel().getValue().toString()),Integer.parseInt(spinner_1_3.getModel().getValue().toString()),Integer.parseInt(spinner_1_4.getModel().getValue().toString()),Integer.parseInt(spinner_2.getModel().getValue().toString()),Integer.parseInt(spinner_3.getModel().getValue().toString()),Integer.parseInt(spinner_4.getModel().getValue().toString()),Integer.parseInt(spinner_5.getModel().getValue().toString())},textPane_1_1,textPane_1_2,f);
					if(traveller == null) textPane_1_2_1.setText("Unexcpected Age, age should be(age>=16 and age<=115)");
					else {
						@SuppressWarnings("rawtypes")
						DefaultListModel listModel = new DefaultListModel();
						City[] allCitiess = City.getArrayOfCities();
						for(int i=0;i<allCitiess.length;i++) {listModel.addElement(allCitiess[i]);}
						list.setModel(listModel);
						if(error) {
							textPane_1_1.setText("Unexcpected City name");
							textPane_1_2.setText("Unexcpected Country name");
							return;
						}
/*an den exei epileksei*/if(selectedCities.length == 0) JOptionPane.showMessageDialog(null, traveller.compareCities(allCitiess).getName(), "Best City for you !!!",JOptionPane.PLAIN_MESSAGE);
						else JOptionPane.showMessageDialog(null, traveller.compareCities(selectedCities).getName(), "Best City for you !!!",JOptionPane.PLAIN_MESSAGE);
						Traveller.storeTravellers();
					}
				} catch (NumberFormatException e1) {
					textPane_1_2_1.setText("Unexcpected Age, should be numeric");
				} catch (UniformInterfaceException e1) {
					textPane_1_1.setText("Unexcpected City name");
					textPane_1_2.setText("Unexcpected Country name");
				} catch (IOException e1) {
					System.exit(0);
				} catch (SQLException e1) {
					System.exit(0);
				} catch (IllegalTravellerRate e1) {
					//wont happen
				} catch (IllegalTravellerAge e1) {
					textPane_1_2_1.setText("Unexcpected Age, should be(>=16 and <=115)");
				} catch (WikipediaNoArcticleException e1) {
					textPane_1_1.setText("Unexcpected City name");
					textPane_1_2.setText("Unexcpected Country name");
				}
			}
		});
		
		JButton btnClear = new JButton("Clear");
		btnClear.setToolTipText("deselect");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				list.setSelectedIndices(new int[0]);
			}
		});
		btnClear.setBackground(new Color(255, 255, 255));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				error = false;
				@SuppressWarnings("rawtypes")
				DefaultListModel listModel = new DefaultListModel();
				int[] selected = list.getSelectedIndices();
				txtpnAsd.setText("");
				
				try {
					@SuppressWarnings("unused")
					City city = new City(textField_4.getText(),"",txtpnAsd,null,f);
					if(!error) {
						City[] dbCities = City.getArrayOfCities();
						for(int i=0;i<dbCities.length;i++) {listModel.addElement(dbCities[i]);}
						list.setModel(listModel);
					}
				} catch (UniformInterfaceException e1) {
					txtpnAsd.setText("Unexcpected City name");
				} catch (IllegalArgumentException e1) {
					txtpnAsd.setText("Unexcpected City name");
				} catch (IOException e1) {
					txtpnAsd.setText("Unexcpected Error");
				} catch (SQLException e1) {
					txtpnAsd.setText("Unexcpected Error");
				} catch (WikipediaNoArcticleException e1) {
					txtpnAsd.setText("Couldnt find Article");
				} catch (Exception e1) {
					txtpnAsd.setText("Unexcpected Error");
				}
				list.setSelectedIndices(selected);
			}
		});
		btnNewButton.setToolTipText("Add a City that doesnt exist on the list");
		
		JLabel lblNewLabel_3_1 = new JLabel("Select with ctrl the cities");
		lblNewLabel_3_1.setForeground(Color.DARK_GRAY);
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		textField_4 = new JTextField();
		textField_4.setToolTipText("Write a city name");
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_3_1_1 = new JLabel("City");
		lblNewLabel_3_1_1.setForeground(Color.DARK_GRAY);
		lblNewLabel_3_1_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		
		txtpnAsd = new JTextPane();
		txtpnAsd.setForeground(new Color(255, 99, 71));
		txtpnAsd.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtpnAsd.setEditable(false);
		txtpnAsd.setBackground(new Color(105, 105, 105));
		
		JButton btnSelectAll = new JButton("Select All");
		btnSelectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] select = new int[list.getModel().getSize()];
				for(int i=0;i<select.length;i++) {
					select[i] = i;
				}
				list.setSelectedIndices(select);
				
			}
		});
		btnSelectAll.setToolTipText("Select All");
		btnSelectAll.setBackground(Color.WHITE);
		
		JLabel lblNewLabel_3 = new JLabel("you are intersted in");
		lblNewLabel_3.setForeground(Color.DARK_GRAY);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 20));
		
		JButton btnNewButton_1 = new JButton("Find best (Statistics)");
		btnNewButton_1.setToolTipText("finds best City from stats");
		btnNewButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				error = false;
				textPane_1_2_1.setText("");
				textPane_1_1.setText("");
				textPane_1_2.setText("");
				textPane_1_2_1.setText("");
				txtpnAsd.setText("");
				JOptionPane.showMessageDialog(null, CollaborativeFiltering.findBestCity(new int[] {Integer.parseInt(spinner.getModel().getValue().toString()),Integer.parseInt(spinner_1.getModel().getValue().toString()),Integer.parseInt(spinner_1_1.getModel().getValue().toString()),Integer.parseInt(spinner_1_2.getModel().getValue().toString()),Integer.parseInt(spinner_1_3.getModel().getValue().toString()),Integer.parseInt(spinner_1_4.getModel().getValue().toString()),Integer.parseInt(spinner_2.getModel().getValue().toString()),Integer.parseInt(spinner_3.getModel().getValue().toString()),Integer.parseInt(spinner_4.getModel().getValue().toString()),Integer.parseInt(spinner_5.getModel().getValue().toString())}), "Best City for you !!!",JOptionPane.PLAIN_MESSAGE);
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frmExcersize.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(textField_4, 99, 99, 99)
									.addGap(18)
									.addComponent(btnNewButton)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(txtpnAsd, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE))))
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnClear)
							.addGap(18)
							.addComponent(btnSelectAll)
							.addPreferredGap(ComponentPlacement.RELATED, 450, Short.MAX_VALUE)
							.addComponent(btnNewButton_1)
							.addGap(18)
							.addComponent(btnNext)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel_3_1, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
							.addGap(68)
							.addComponent(lblNewLabel_3_1_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(10)
									.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
								.addGroup(groupLayout.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtpnAsd, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE))))
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnSelectAll, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnNext)
							.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		City[] cities = City.getArrayOfCities();
		String[] city = new String[cities.length];
		for(int i=0;i<city.length;i++) {
			city[i] = cities[i].getName();
		}
		list = new JList<String>(city);
		list.setToolTipText("Select the Cities to be compaired");
		list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		list.setFont(new Font("Tahoma", Font.PLAIN, 14));
		list.setBackground(new Color(169, 169, 169));
		list.addAncestorListener(null);
		scrollPane.setViewportView(list);
		
		spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		((DefaultEditor) spinner_1.getEditor()).getTextField().setEditable(false);
		
		spinner_1_1 = new JSpinner();
		spinner_1_1.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		((DefaultEditor) spinner_1_1.getEditor()).getTextField().setEditable(false);
		
		spinner_1_2 = new JSpinner();
		spinner_1_2.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		((DefaultEditor) spinner_1_2.getEditor()).getTextField().setEditable(false);
		
		spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		((DefaultEditor) spinner.getEditor()).getTextField().setEditable(false);
		
		JLabel lblNewLabel_2 = new JLabel("Sea");
		lblNewLabel_2.setFont(lblNewLabel_2.getFont().deriveFont(11f));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_2_1 = new JLabel("Mountain");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Museum");
		lblNewLabel_2_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_2_1_1_1 = new JLabel("Cafe");
		lblNewLabel_2_1_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_2_1_1_2 = new JLabel("Restaurant");
		lblNewLabel_2_1_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_2_1_1_3 = new JLabel("Park");
		lblNewLabel_2_1_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_2_1_1_4 = new JLabel("Lake");
		lblNewLabel_2_1_1_4.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_2_1_1_5 = new JLabel("Train");
		lblNewLabel_2_1_1_5.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_2_1_1_6 = new JLabel("Metro");
		lblNewLabel_2_1_1_6.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_2_1_1_7 = new JLabel("Forest");
		lblNewLabel_2_1_1_7.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel label = new JLabel("");
		
		spinner_1_3 = new JSpinner();
		spinner_1_3.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		((DefaultEditor) spinner_1_3.getEditor()).getTextField().setEditable(false);
		
		spinner_1_4 = new JSpinner();
		spinner_1_4.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		((DefaultEditor) spinner_1_4.getEditor()).getTextField().setEditable(false);
		
		spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		((DefaultEditor) spinner_2.getEditor()).getTextField().setEditable(false);
		
		spinner_3 = new JSpinner();
		spinner_3.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		((DefaultEditor) spinner_3.getEditor()).getTextField().setEditable(false);
		
		spinner_4 = new JSpinner();
		spinner_4.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		((DefaultEditor) spinner_4.getEditor()).getTextField().setEditable(false);
		
		spinner_5 = new JSpinner();
		spinner_5.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		((DefaultEditor) spinner_5.getEditor()).getTextField().setEditable(false);
		
		JLabel label_1 = new JLabel("");
		panel_2.setLayout(new GridLayout(0, 11, 0, 0));
		panel_2.add(lblNewLabel_2);
		panel_2.add(lblNewLabel_2_1);
		panel_2.add(lblNewLabel_2_1_1);
		panel_2.add(lblNewLabel_2_1_1_1);
		panel_2.add(lblNewLabel_2_1_1_2);
		panel_2.add(lblNewLabel_2_1_1_3);
		panel_2.add(lblNewLabel_2_1_1_4);
		panel_2.add(lblNewLabel_2_1_1_5);
		panel_2.add(lblNewLabel_2_1_1_6);
		panel_2.add(lblNewLabel_2_1_1_7);
		panel_2.add(label);
		panel_2.add(spinner);
		panel_2.add(spinner_1);
		panel_2.add(spinner_1_1);
		panel_2.add(spinner_1_2);
		panel_2.add(spinner_1_3);
		panel_2.add(spinner_1_4);
		panel_2.add(spinner_2);
		panel_2.add(spinner_3);
		panel_2.add(spinner_4);
		panel_2.add(spinner_5);
		panel_2.add(label_1);
		
		JLabel lblNewLabel_1 = new JLabel("Name");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel_1_1 = new JLabel("City");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Country");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Age");
		lblNewLabel_1_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_3.setColumns(10);
		
		txtpnAsd_1 = new JTextPane();
		txtpnAsd_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtpnAsd_1.setForeground(new Color(255, 99, 71));
		txtpnAsd_1.setEditable(false);
		txtpnAsd_1.setBackground(new Color(169, 169, 169));
		
		textPane_1_1 = new JTextPane();
		textPane_1_1.setForeground(new Color(255, 99, 71));
		textPane_1_1.setEditable(false);
		textPane_1_1.setBackground(new Color(169, 169, 169));
		
		textPane_1_2 = new JTextPane();
		textPane_1_2.setForeground(new Color(255, 99, 71));
		textPane_1_2.setEditable(false);
		textPane_1_2.setBackground(new Color(169, 169, 169));
		
		textPane_1_2_1 = new JTextPane();
		textPane_1_2_1.setForeground(new Color(255, 99, 71));
		textPane_1_2_1.setEditable(false);
		textPane_1_2_1.setBackground(new Color(169, 169, 169));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblNewLabel_1_1_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNewLabel_1_1_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(txtpnAsd_1, GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(textPane_1_1, GroupLayout.PREFERRED_SIZE, 570, GroupLayout.PREFERRED_SIZE)
								.addComponent(textPane_1_2, GroupLayout.PREFERRED_SIZE, 570, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textPane_1_2_1, GroupLayout.PREFERRED_SIZE, 570, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(16)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtpnAsd_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(textPane_1_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(textPane_1_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1_1_1, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_1_1_2, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(textPane_1_2_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(14, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel lblNewLabel = new JLabel("Give your information");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 785, GroupLayout.PREFERRED_SIZE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
		);
		panel.setLayout(gl_panel);
		frmExcersize.getContentPane().setLayout(groupLayout);
	}
}

