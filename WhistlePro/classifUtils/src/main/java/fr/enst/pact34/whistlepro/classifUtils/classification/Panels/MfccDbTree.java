package fr.enst.pact34.whistlepro.classifUtils.classification.Panels;

import fr.enst.pact34.whistlepro.api.common.FileOperator;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Enumeration;
import java.util.Set;

/**
 * Created by mms on 02/04/16.
 */
public class MfccDbTree extends JTree implements KeyListener{

    DefaultMutableTreeNode root;
    MfccDb db = new MfccDb();

    public MfccDbTree()
    {
        super(new DefaultMutableTreeNode("classes"));
        root = (DefaultMutableTreeNode) this.getModel().getRoot();

        this.addKeyListener(this);

    }

    boolean modifiable = true;

    public void setModifiable(boolean b)
    {
        modifiable = false;
    }

    public boolean addClass(String classe)
    {
        if(modifiable==false) return false;
        if(db.addClass(classe))
        {
            DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(classe);
            root.add(newChild);

            updateUI();
            expandRow(0);//expand root
            return true;
        }

        return false;
    }

    public boolean addSample(String classe, String path) {
        if(modifiable==false) return false;
        if(db.contains(classe))
        {
            db.addSample(classe, path);

            Enumeration e = root.children();
            while(e.hasMoreElements())
            {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.nextElement();
                if(node.getUserObject().toString().equals(classe))
                {
                    String name ;
                    if(path.contains("/"))
                        name = path.substring(path.lastIndexOf("/")+1);
                    else
                        name = path;
                    DefaultMutableTreeNode newChild = new DefaultMutableTreeNode(name);
                    node.add(newChild);
                    newChild.add(new DefaultMutableTreeNode("mfcc to calculate"));

                }
            }

            updateTree();
            return true;
        }

        return false;
    }

    public void saveToFile(String e) {
        FileOperator.saveToFile(e,db.saveString());
        updateTree();
    }

    public void loadFromFile(String e)
    {
        String ret = FileOperator.getDataFromFile(e);
        if(ret.isEmpty()==false)
            db.fromString(ret);
        updateTree();
    }

    public String[] getClassesList() {
        return db.getClassesList();
    }

    private void updateTree()
    {
        root.removeAllChildren();
        for (String classe : db.getClassesList()
             ) {

            DefaultMutableTreeNode classNOde = new DefaultMutableTreeNode(classe);
            root.add(classNOde);

            for(MfccDb.ElementInterface e : db.getElementsList(classe))
            {
                DefaultMutableTreeNode elmtNode = new DefaultMutableTreeNode(e.getName());
                if(e.getMfccs()!=null) {
                    for (String mfcc : e.getMfccs()) {
                        elmtNode.add(new DefaultMutableTreeNode(mfcc));
                    }
                }
                else
                {
                    elmtNode.add(new DefaultMutableTreeNode("mfcc to calculate"));
                }
                classNOde.add(elmtNode);
            }

        }
        updateUI();
        expandAllNodes(this,0,this.getRowCount());
    }

    private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
        for(int i=startingIndex;i<rowCount;++i){
            if( ((DefaultMutableTreeNode)tree.getPathForRow(i).getLastPathComponent()).getLevel() < 2)
                tree.expandRow(i);
        }

        if(tree.getRowCount()!=rowCount){
            expandAllNodes(tree, rowCount, tree.getRowCount());
        }
    }
    public void calculateMfccs() {
        if(modifiable==false) return;
        db.calculateMfccs();
        updateTree();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if(modifiable==false) return;
        for (TreePath i : this.getSelectionPaths()
             ) {
            if(i==null) continue;
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) i.getLastPathComponent();

            if((node.getLevel() == 2) && e.getKeyChar() == KeyEvent.VK_DELETE)
            {
                if(JOptionPane.showConfirmDialog(null,"Are you sure you want to delete " + node.getUserObject().toString() + " ?")==JOptionPane.OK_OPTION)
                {
                    db.remove(((DefaultMutableTreeNode)node.getParent()).getUserObject().toString(), node.getUserObject().toString());
                }
            }
        }

        updateTree();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
