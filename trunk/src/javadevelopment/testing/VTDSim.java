/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package javadevelopment.testing;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author agustian
 */
public class VTDSim {

    public static void main(String[] args) {
        VTDSim m = new VTDSim();
        m.test();
    }

    public void test2() {
        Calendar cal = Calendar.getInstance();

        Date date = cal.getTime();
        System.out.println("date: " + date);

        Format formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        String s = formatter.format(date);
        System.out.println("s: " + s);
        Format filenameFormatter = new SimpleDateFormat("_ddMMyy_hhmmss");
        String f = filenameFormatter.format(date);

        System.out.println("f: " + f);

    }

    public void test() {
        try {


            Format formatter = new SimpleDateFormat("dd/MM/yy hh:mm:ss");
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -1);
            Calendar fileCal = Calendar.getInstance();



            for (int o = 0; o < 1000; o++) {
                fileCal.add(Calendar.SECOND, 30);
                Format filenameFormatter = new SimpleDateFormat("_ddMMyy_hhmmss");
                String f = filenameFormatter.format(fileCal.getTime());
                BufferedWriter out = new BufferedWriter(new FileWriter("LOGS/LOG" + f + ".txt"));

                for (int i = 0; i < 10; i++) {
                    fileCal.add(Calendar.SECOND, 30);
                    Date date = fileCal.getTime();
                    String s = formatter.format(date);

                    String tmp = s + " -- 87,15:05:46,-5.976956,106.003544,23,005.56,30,1,12,1,0,0,11298,24261,16,43,29,1044\n";
                    out.write(tmp);

                }

                out.close();
            }


        } catch (IOException e) {
        }

    }
}
