package ex3.gui;

import java.lang.Runnable;

import java.util.Properties;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

import javax.mail.MessagingException;

import ex3.mx.IMAP;

public class MainWindow extends JFrame 
                        implements ListSelectionListener {

  private final IMAP          IMAP;
  private final MessageList   MSG_LIST    = new MessageList();
  private final JTextPane     MSG_PANE    = new JTextPane();
  private final JScrollPane   MSG_VIEW    = new JScrollPane(MSG_PANE);
  private final JSplitPane    SPLIT_PANE  = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                                                           MSG_LIST, MSG_VIEW);
 
  public MainWindow(Properties _p) {
    super();
    this.setTitle("Email Client");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    IMAP i;
    try {
      i = new IMAP(_p);
    } catch (Exception e) {
      i = null;
    }
    IMAP = i;

    SPLIT_PANE.setDividerLocation(300);
    SPLIT_PANE.setContinuousLayout(true);

    Dimension minimum = new Dimension(300, 500);

    MSG_LIST.setMinimumSize(minimum);
    MSG_VIEW.setMinimumSize(minimum);

    SPLIT_PANE.setPreferredSize(new Dimension(800, 500));

    this.getContentPane().add(SPLIT_PANE);
    this.pack();
    this.setVisible(true);

    MSG_LIST.addListSelectionListener(this);
 
    try {
      IMAP.getMessages(MSG_LIST);
    } catch (Exception e) {

    }

  }

  @Override
  public void valueChanged(final ListSelectionEvent e) {
    if (e.getValueIsAdjusting()) return;

    DefaultListModel list = (DefaultListModel) ((JList) e.getSource()).getModel();
    Properties msg = (Properties) list.getElementAt(e.getFirstIndex());

    MSG_PANE.setContentType("text/html");
    MSG_PANE.setText(msg.getProperty("email.content"));
  }

}
