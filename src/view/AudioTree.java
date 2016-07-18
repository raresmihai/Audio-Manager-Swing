package view;

/**
 * Created by rares on 27.03.2016.
 */

import model.FavoritesList;
import model.MetadataExtractor;
import model.PopClickListener;
import model.Song;
import org.apache.tika.metadata.Metadata;
import view.AudioManager;

import javax.swing.JPopupMenu;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.util.List;

public class AudioTree extends JTree {
    public static JTree tree;
    DefaultMutableTreeNode root;
    JPanel centerPanel = AudioManager.centerPanel;
    static DefaultMutableTreeNode favoritesRoot;

    public AudioTree() {
        root = new DefaultMutableTreeNode("root", true);
        DefaultMutableTreeNode filesRoot = new DefaultMutableTreeNode("Files");
        favoritesRoot = new DefaultMutableTreeNode("Favorites");
        root.add(filesRoot);
        root.add(favoritesRoot);
        getInitialChildren(filesRoot, "/home/rares");
        getFavorites();
        setLayout(new BorderLayout());
        tree = new JTree(root);
        initializeTree();
        tree.setRootVisible(false);
    }

    private void initializeTree() {
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);
        addExpansionEvent();
        addTreeSelectionListener();
        tree.addMouseListener(new PopClickListener());
    }

    void addTreeSelectionListener() {
        tree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        tree.getLastSelectedPathComponent();
                if (node == null) return;
                Object nodeInfo = node.getUserObject();
                String nodeName = nodeInfo.toString();
                File fileSelected = new File(nodeName);
                CardLayout cardLayout = (CardLayout) centerPanel.getLayout();
                if (!fileSelected.isDirectory() && !fileSelected.getName().equals("Files")) {
                    updateList(fileSelected);
                    cardLayout.show(centerPanel, "list");
                } else {
                    updateTable(fileSelected);
                    cardLayout.show(centerPanel, "table");
                }
            }
        });
    }

    private void updateList(File audioFile) {
        JScrollPane scrollPane = (JScrollPane) centerPanel.getComponent(1);
        JViewport viewport = (JViewport) scrollPane.getComponent(0);
        JList list = (JList) viewport.getComponent(0);

        MetadataExtractor metadataExtractor = new MetadataExtractor();
        Metadata metadata = metadataExtractor.extract(audioFile.toString());

        String[] metadataNames = metadata.names();
        DefaultListModel model = new DefaultListModel();

        for (String name : metadataNames) {
            if (metadata.get(name).length() > 0) {
                model.addElement(name + ":" + metadata.get(name));
            }
        }
        list.setModel(model);
    }

    private void updateTable(File directory) {
        JScrollPane scrollPane = (JScrollPane) centerPanel.getComponent(0);
        JViewport viewport = (JViewport) scrollPane.getComponent(0);
        JTable table = (JTable) viewport.getComponent(0);
        String columnHeadings[] = {"File name", "Song name", "Artist"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columnHeadings);

        MetadataExtractor metadataExtractor = new MetadataExtractor();
        Metadata metadata;

        File fList[] = directory.listFiles();
        if (fList != null) {
            for (File file : fList) {
                if (isAudioFile(file)) {
                    metadata = metadataExtractor.extract(directory.toString() + "/" + file.getName());
                    String data[] = {file.getName(), metadata.get("title"), metadata.get("Author")};
                    model.addRow(data);
                }
            }
        }
        table.setModel(model);
    }

    public JTree getTree() {
        return tree;
    }

    public Dimension getPreferredSize() {
        return new Dimension(200, 120);
    }

    private void getInitialChildren(DefaultMutableTreeNode root, String home) {
        File homeFile = new File(home);
        File fList[] = homeFile.listFiles();
        if (fList != null) {
            for (File file : fList) {
                if (isDirectory(file) || isAudioFile(file)) {
                    DefaultMutableTreeNode child = new DefaultMutableTreeNode(file);
                    root.add(child);
                }
            }
        }
    }

    public void getFavorites() {
        File file = new File("favorites.xml");
        if (file.exists()) {
            try {
                favoritesRoot.removeAllChildren();
                JAXBContext context = JAXBContext.newInstance(FavoritesList.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                FavoritesList favoritesList = (FavoritesList) unmarshaller.unmarshal(new File("favorites.xml"));
                List<Song> favs = favoritesList.getFavorites();
                for (Song song : favs) {
                    File songFile = new File(song.getFileName());
                    DefaultMutableTreeNode child = new DefaultMutableTreeNode(songFile);
                    favoritesRoot.add(child);
                }
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }

    void addExpansionEvent() {
        TreeWillExpandListener treeWillExpandListener = new TreeWillExpandListener() {
            public void treeWillCollapse(TreeExpansionEvent treeExpansionEvent)
                    throws ExpandVetoException {
            }

            public void treeWillExpand(TreeExpansionEvent treeExpansionEvent) throws ExpandVetoException {
                TreePath path = treeExpansionEvent.getPath();
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                getNephews(node);
            }
        };

        this.tree.addTreeWillExpandListener(treeWillExpandListener);
    }

    private void getNephews(DefaultMutableTreeNode grandParent) {
        for (int i = 0; i < grandParent.getChildCount(); ++i) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) grandParent.getChildAt(i);
            File childFile = new File(child.toString());
            File fList[] = childFile.listFiles();
            if (fList != null) {
                for (File file : fList) {
                    if (isDirectory(file) || isAudioFile(file)) {
                        child.add(new DefaultMutableTreeNode(file));
                    }
                }
            }
        }
    }

    private boolean isAudioFile(File file) {
        return file.getName().matches("(.*)(mp3|mp4|wav)");
    }

    private boolean isDirectory(File file) {
        return file.isDirectory() &&
                file.getName().charAt(0) != '.' &&
                file.listFiles().length > 0;
    }

    public static void updateFavorites(String newChild) {
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        favoritesRoot.add(new DefaultMutableTreeNode(newChild));
        model.reload(favoritesRoot);
    }

}

