/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeBehind.Logs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Eliott
 */
public class FichierLog {
    
    public final static String nomFichierLog = "fichierlogTI.txt";
    
    public void addLog(String ligneLog)
    {
        try {
            Date mtn = new Date();
            String maDate = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM,Locale.FRANCE).format(mtn);
            ligneLog = maDate.concat(" > " + ligneLog);
            FileWriter fw = new FileWriter(System.getProperty("user.dir") + System.getProperty("file.separator") + nomFichierLog, true);
            fw.write(ligneLog);
            fw.write(System.getProperty("line.separator"));
            System.out.println("Ecriture d'un log ("+ligneLog+")");
            fw.close();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(FichierLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }
    
    public String getAllLog()
    {
        try{
            String fichierDeLog = new String();
            FileReader fr = new FileReader(System.getProperty("user.dir") + System.getProperty("file.separator") + nomFichierLog);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line=br.readLine())!=null)
            {
                System.out.println("Lecture d'un log");
                fichierDeLog=fichierDeLog + line + System.getProperty("line.separator");
            }
            return fichierDeLog;
        } 
        catch (FileNotFoundException ex) 
        {
            java.util.logging.Logger.getLogger(FichierLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return "Fichier pas trouvé";
        } 
        catch (IOException ex) 
        {
            java.util.logging.Logger.getLogger(FichierLog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            return "Erreur d'entrée/sortie";
        } 
    }
    
    public void resetLog()
    {
        File fichier = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + nomFichierLog);
        fichier.delete();
        this.addLog("Suppressions de tous les logs");
    }
}
