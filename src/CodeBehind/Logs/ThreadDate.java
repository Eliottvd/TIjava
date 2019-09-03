/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CodeBehind.Logs;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JLabel;

/**
 *
 * @author Eliott
 */
public class ThreadDate extends Thread{

    private JLabel _jLabelDate;
    private int _formatHeure;
    private int _formatDate;
    private Locale _pays;
    private boolean _flagdate;
    
    
    public ThreadDate(){
        _formatDate = DateFormat.SHORT;
        _formatHeure = DateFormat.MEDIUM;
        _pays = Locale.FRANCE;
        _flagdate = false;
    }
    
    /*public ThreadDate(JFrameAfficheMeteo am) {
        _jLabelDate = am.jLabelHeure;
        _formatDate = DateFormat.SHORT;
        _formatHeure = DateFormat.MEDIUM;
        _pays = Locale.FRANCE;
        _flagdate = false;
    }*/
    
    
    public void setFormatHeure(int format)
    {
        _formatHeure = format;
    }
    
    public void setFormatDate(int format)
    {
        _formatDate = format;
    }
    
    public void setPays(Locale p)
    {
        _pays = p;
    }
    
    public void run()
    {
        boolean test = true;
        while(test == true)
        {
            try{
                Date maintenant = new Date();
                String maDate;
                if(_flagdate)
                    maDate = DateFormat.getDateTimeInstance(_formatDate, _formatHeure, _pays).format(maintenant);
                else
                    maDate = DateFormat.getTimeInstance().format(maintenant);
                _jLabelDate.setText(maDate);
                sleep(1000);
            }
            catch(InterruptedException e)
            {
                return;
            }
                
        }
    }
    
}
