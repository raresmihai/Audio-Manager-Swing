package view;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;


/**
 * Created by rares on 27.03.2016.
 */
public class AudioManager {

    public JFrame frame;
    public static JPanel centerPanel;


    public static void runApp() {
        try {
            AudioManager window = new AudioManager();
            window.frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AudioManager() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(280, 100, 450, 300);
        frame.setSize(700,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JSplitPane mainSplitPane = new JSplitPane();

        //right component
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout(0, 0));

        //center-right Component
        centerPanel = new JPanel();
        centerPanel.setLayout(new CardLayout());

        String columnHeadings[] = {"File name","Song name","Artist"};


        DefaultTableModel tableModel = new DefaultTableModel(0,3);
        tableModel.setColumnIdentifiers(columnHeadings);
        JTable audioTable = new JTable(tableModel);
        audioTable.setEnabled(false);

        JList infoList = new JList();
        infoList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        infoList.setLayoutOrientation(JList.VERTICAL);
        centerPanel.add(new JScrollPane(audioTable),"table");
        centerPanel.add(new JScrollPane(infoList),"list");

        rightPanel.add(centerPanel,BorderLayout.CENTER);


        //left component
        JTree audioTree = (new AudioTree()).getTree();
        JScrollPane scrollPane = new JScrollPane(audioTree);

        //add components
        mainSplitPane.setLeftComponent(scrollPane);
        mainSplitPane.setRightComponent(rightPanel);
        mainSplitPane.setDividerLocation(250);

        frame.getContentPane().add(mainSplitPane, BorderLayout.CENTER);
    }

}
