package model;

import controller.AudioManager;
import view.AudioTree;
import view.PopUpMenu;

import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by rares on 30.03.2016.
 */
public class PopClickListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e) {
        PopUpMenu menu = new PopUpMenu();
        TreePath treePath = AudioTree.tree.getPathForLocation(e.getX(), e.getY());
        if (treePath != null) {
            String path = AudioManager.extractPath(treePath);
            if (AudioManager.isAudio(path)) {
                AudioManager.setMenuPath(path);
                menu.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }


}
