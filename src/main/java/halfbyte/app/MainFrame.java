package halfbyte.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainFrame {
    // variables
    private JFrame m_frame;
    private HexPanel m_hexPanel;

    // methods
    public static void main(String[] args) throws IOException {
        new MainFrame();
    }

    private MainFrame() throws IOException {
        // main frame
        this.m_frame = new JFrame("JexEdit");
        this.m_frame.setSize(1280, 720);
        this.m_frame.setLocation(200, 200);
        this.m_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        this.m_frame.setContentPane(mainPanel);

        // put the hex panel in the main panel
        this.m_hexPanel = new HexPanel();
        mainPanel.add(this.m_hexPanel);

        // build menu
        JMenuBar menu = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        menu.add(menuFile);

        JMenuItem menuFileOpen = new JMenuItem("Open");
        menuFileOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainFrame.this.onMenuFileOpen();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        menuFile.add(menuFileOpen);

        JMenuItem menuFileClose = new JMenuItem("Close");
        menuFileClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.onMenuFileClose();
            }
        });
        menuFile.add(menuFileClose);

        JMenuItem menuFileExit = new JMenuItem("Exit");
        menuFileExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.onMenuFileExit();
            }
        });
        menuFile.add(menuFileExit);
        this.m_frame.setJMenuBar(menu);

        JMenu menuHelp = new JMenu("Help");
        menu.add(menuHelp);

        JMenuItem menuHelpAbout = new JMenuItem("About");
        menuHelpAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.this.onMenuHelpAbout();
            }
        });
        menuHelp.add(menuHelpAbout);

        // show
        this.m_frame.setVisible(true);
    }

    private void onMenuFileOpen() throws IOException {
        JFileChooser jfc = new JFileChooser();
        jfc.setCurrentDirectory(new File(System.getProperty("user.home")));
        if (jfc.showOpenDialog(this.m_frame) == JFileChooser.APPROVE_OPTION){
            this.m_hexPanel.loadFile(jfc.getSelectedFile().getAbsolutePath());
        }
    }

    private void onMenuFileClose(){
        this.m_hexPanel.closeFile();
    }

    private void onMenuFileExit(){
        // todo confirm if file is changed
        this.m_frame.dispose();
    }

    private void onMenuHelpAbout(){
        JDialog dialog = new AboutDialog(this.m_frame);
        dialog.setVisible(true);
    }
}
