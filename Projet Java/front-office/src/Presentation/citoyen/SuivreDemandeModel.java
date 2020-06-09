package Presentation.citoyen;

import DAO.Controleur;
import Metier.Demande;
import Metier.Etat;
import Metier.Procedure;
import Presentation.Tools.ToolButton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SuivreDemandeModel extends AbstractTableModel {
    Controleur controleur;
    ArrayList<JPanel>   uploaders;
    ArrayList<Demande>  demandes;

    SuivreDemandeModel(ArrayList<Demande>  demandes, Controleur controleur) {
        super();
        this.controleur = controleur;
        this.demandes = demandes;
        findnbEtape();
        uploaders = new ArrayList<>();
        for (int i = 0; i < demandes.size(); i++) {
            ToolButton toolButton = new ToolButton("", "uploader document", 30, 30);
            try {
                if (demandes.get(i).getEtat() == Etat.REJETE) toolButton.setIcon("./resrc/rejete.png", 30, 30);
                else if (demandes.get(i).getEtat() == Etat.ACCEPTE) toolButton.setIcon("./resrc/valid.png", 30, 30);
                else if (demandes.get(i).getEtat() == Etat.ENCORE) toolButton.setIcon("./resrc/encore.png", 30, 30);
                else if (demandes.get(i).getEtat() == Etat.MISEAJOUR) toolButton.setIcon("./resrc/upload.png", 30, 30);
            } catch (IOException ex) {
                System.out.println(ex);
            }
            JPanel uploader = new JPanel();
            toolButton.button.setBorder(new EmptyBorder(5, 0, 0,0 ));
            uploader.add(toolButton.button, BorderLayout.CENTER);
            uploaders.add(uploader);
        }
    }

    public ArrayList<Demande> getDemandes() {
        return demandes;
    }

    @Override
    public int getRowCount() {
        return demandes.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch(columnIndex) {
            case 0 : return demandes.get(rowIndex).getJeton();
            case 1 : return demandes.get(rowIndex).getProcedure().getNom();
            case 2 : return demandes.get(rowIndex).getEtat();
            case 3 : return uploaders.get(rowIndex);
            case 4 : return getProgress(rowIndex) + " %";
            default : return null;
        }
    }

    @Override
    public String getColumnName(int columnIndex ) {
        switch(columnIndex) {
            case 0 : return "   Demandes(jeton)";
            case 1 : return "   Procédure";
            case 2 : return "   État";
            case 3 : return "";
            case 4 : return "";
            default : return null;
        }
    }
    @Override
    public Class getColumnClass(int columnIndex ) {
        switch(columnIndex) {
            case 0 : return String.class;
            case 1 : return String.class;
            case 2 : return String.class;
            case 3 : return JPanel.class;
            case 4 : return String.class;
            default : return Object.class;
        }
    }

    private String getProgress(int rowIndex) {
        Integer ret;

        if (demandes.get(rowIndex).getProcedure().getNbrEtapes() != null && demandes.get(rowIndex).getProcedure().getNbrEtapes() != 0)
            ret = (demandes.get(rowIndex).getNumEtape() - 1) * 100 / demandes.get(rowIndex).getProcedure().getNbrEtapes();
        else
            ret = 0;
        ret = ret == -100 ? 100 : ret;
        if (demandes.get(rowIndex).getEtat() == Etat.REJETE)
            ret = 100;
        return String.valueOf(ret);
    }

    public void findnbEtape()
    {
        ArrayList<Procedure> bProcedures = controleur.getAllProcedure();
        ArrayList<Demande> suivDemandes = demandes;
        for (int i = 0; i < suivDemandes.size(); i++) {
            for (int j = 0; j < bProcedures.size(); j++) {
                if(bProcedures.get(j).getNom().equals(suivDemandes.get(i).getProcedure().getNom())) {
                    suivDemandes.get(i).getProcedure().setNbrEtapes(bProcedures.get(j).getNbrEtapes());
                }
            }
        }
    }
}
