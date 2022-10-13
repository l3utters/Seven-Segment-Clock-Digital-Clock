import java.awt.BasicStroke;
import java.awt.Color;
import static java.awt.Color.BLACK;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import static java.awt.Color.lightGray;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class DigitalClock extends javax.swing.JFrame {

    public Run taskClock;
    Calendar Time = Calendar.getInstance();
    boolean stop=false;
    Color Red = RED;
    Color Black = BLACK;
    String hour,min,sec;
    
    public DigitalClock() {
        initComponents();
        taskClock = new Run();
        taskClock.execute();
        
    }
    public class Clock extends JPanel
    {
        
        public void paint(Graphics g)
        {
            Calendar now = Calendar.getInstance();
            double sec = now.get(Calendar.SECOND);
            double min = now.get(Calendar.MINUTE);
            double hour = now.get(Calendar.HOUR);
            boolean stop = true;
            double hrAngle = 0.5*(60*hour+min);
            double minAngle = 6*min;
            double secAngle = 6*sec;
            
            Graphics2D g2 = (Graphics2D) g;
            
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            
            g2.drawString(hour+":"+min+":"+sec ,230,50);
            
            //CLOCK
            g2.setStroke(new BasicStroke(5.0f));
            g2.setPaint(BLACK);
            g2.draw(new Arc2D.Double(250-163, 250-163, 326, 326, 90, 360, Arc2D.OPEN));
            g2.setPaint(BLACK);
            g2.fill(new Arc2D.Double(250-163, 250-163, 326, 326, 90, 360, Arc2D.OPEN));

            //SECONDS
            g2.setStroke(new BasicStroke(6.0f));
            Color color3 = new Color(68,162,255);
            g2.setColor(color3);
            g2.draw(new Arc2D.Double(250-156, 250-156, 312, 312, 90, -secAngle, Arc2D.OPEN));
            //g2.fill(new Arc2D.Double(250-156, 250-156, 312, 312, 90, -secAngle, Arc2D.PIE));;            

            //MINUTES
            g2.setStroke(new BasicStroke(6.0f));
            Color color2 = new Color(247,255,0);
            g2.setColor(color2);
            g2.draw(new Arc2D.Double(250-142, 250-142, 284, 284, 90, -minAngle, Arc2D.OPEN));
            //g2.fill(new Arc2D.Double(250-137, 250-137, 275, 275, 90, -minAngle, Arc2D.PIE));
            
            //HOURS
            g2.setStroke(new BasicStroke(6.0f));
            Color color1 = new Color(0,255,102);
            g2.setColor(color1);
            g2.draw(new Arc2D.Double(250-125, 250-125, 250, 250, 90, -hrAngle, Arc2D.OPEN));
            //g2.fill(new Arc2D.Double(250-125, 250-125, 250, 250, 90, -hrAngle, Arc2D.PIE));
            
            //HOUR HAND
            g2.setStroke(new BasicStroke(6.0f));
            g2.setPaint(lightGray);
            g2.draw(new Line2D.Double(250, 250 , 250+Math.cos(((Math.PI/180)*hrAngle)-1.565)*125, 250+Math.sin(((Math.PI/180)*hrAngle)-1.565)*125));
            
            //MINUTE HAND
            g2.setStroke(new BasicStroke(5.0f));
            g2.setPaint(lightGray);
            g2.draw(new Line2D.Double(250, 250 , 250+Math.cos(((Math.PI/180)*minAngle)-1.565)*142, 250+Math.sin(((Math.PI/180)*minAngle)-1.565)*142));
            
            //SECOND HAND
            g2.setStroke(new BasicStroke(5.0f));
            g2.setPaint(lightGray);
            g2.draw(new Line2D.Double(250, 250 , 250+Math.cos(((Math.PI/180)*secAngle)-1.565)*156, 250+Math.sin(((Math.PI/180)*secAngle)-1.565)*156));
            
            //CENTRE DOT
            g2.setStroke(new BasicStroke(5.0f));
            g2.setPaint(WHITE);
            g2.draw(new Ellipse2D.Double(250-5, 250-5, 10, 10));
            g2.fill(new Ellipse2D.Double(250-5, 250-5, 10, 10));
            
            //CLOCK LINES
            g2.setStroke(new BasicStroke(5.0f));
            g2.setPaint(WHITE);
            double angle = Math.PI/2;
            
            for(int i=0;i<12;i++)
            {
                g2.draw(new Line2D.Double(160*Math.cos(angle)+250,160*Math.sin(angle)+250,121*Math.cos(angle)+250,121*Math.sin(angle)+250));
                angle+=Math.PI/6;
            }
            repaint();
        }
    }
    
    class Run extends SwingWorker<Void, Void> {
        
        @Override
        public Void doInBackground()
        {  
            Clock test = new Clock();
            JFrame ts = new JFrame();
            ts.setSize(500,500);
            ts.setVisible(true);
            ts.getContentPane().setBackground(BLACK);
            ts.add(test);
            ts.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            while(stop!=true) {
                updateSecondSecond();
                updateSecondFirst();
                updateMinuteSecond();
                updateMinuteFirst();
                updateHourSecond();
                updateHourFirst();
                repaint();
            }
            return null;
        }
        
        @Override
        public void done()
        {
            
        }
    }
    //HOURS
    public void updateHourFirst() {
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("HH");  
        String strDate = dateFormat.format(date);
        char hr1 = strDate.charAt(0);
        hour1Set(Character.getNumericValue(hr1));
    }
    public void updateHourSecond() {
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("HH");  
        String strDate = dateFormat.format(date);
        char hr2 = strDate.charAt(1);
        hour2Set(Character.getNumericValue(hr2));
    }
    public void hour1Set(int i) {
        if(i==0) {                                  
            hour1.setBackground(Red);
            hour2.setBackground(Red);
            hour3.setBackground(Red);
            hour4.setBackground(Black);
            hour5.setBackground(Red);
            hour6.setBackground(Red);
            hour7.setBackground(Red);
        }
        if(i==1) {
            hour1.setBackground(Black);
            hour2.setBackground(Black);
            hour3.setBackground(Red);
            hour4.setBackground(Black);
            hour5.setBackground(Black);
            hour6.setBackground(Red);
            hour7.setBackground(Black);
        }
        if(i==2) {
            hour1.setBackground(Red);
            hour2.setBackground(Black);
            hour3.setBackground(Red);
            hour4.setBackground(Red);
            hour5.setBackground(Red);
            hour6.setBackground(Black);
            hour7.setBackground(Red);
        }
        if(i==3) {
            hour1.setBackground(Red);
            hour2.setBackground(Black);
            hour3.setBackground(Red);
            hour4.setBackground(Red);
            hour5.setBackground(Black);
            hour6.setBackground(Red);
            hour7.setBackground(Red);
        }
        if(i==4) {
            hour1.setBackground(Black);
            hour2.setBackground(Red);
            hour3.setBackground(Red);
            hour4.setBackground(Red);
            hour5.setBackground(Black);
            hour6.setBackground(Red);
            hour7.setBackground(Black);
        }
        if(i==5) {
            hour1.setBackground(Red);
            hour2.setBackground(Red);
            hour3.setBackground(Black);
            hour4.setBackground(Red);
            hour5.setBackground(Black);
            hour6.setBackground(Red);
            hour7.setBackground(Red);
        }
        if(i==6) {
            hour1.setBackground(Black);
            hour2.setBackground(Red);
            hour3.setBackground(Black);
            hour4.setBackground(Red);
            hour5.setBackground(Red);
            hour6.setBackground(Red);
            hour7.setBackground(Red);
        }
        if(i==7) {
            hour1.setBackground(Red);
            hour2.setBackground(Black);
            hour3.setBackground(Red);
            hour4.setBackground(Black);
            hour5.setBackground(Black);
            hour6.setBackground(Red);
            hour7.setBackground(Black);
        }
        if(i==8) {
            hour1.setBackground(Red);
            hour2.setBackground(Red);
            hour3.setBackground(Red);
            hour4.setBackground(Red);
            hour5.setBackground(Red);
            hour6.setBackground(Red);
            hour7.setBackground(Red);
        }
        if(i==9) {
            hour1.setBackground(Red);
            hour2.setBackground(Red);
            hour3.setBackground(Red);
            hour4.setBackground(Red);
            hour5.setBackground(Black);
            hour6.setBackground(Red);
            hour7.setBackground(Black);
        }
    }
    public void hour2Set(int i) {
        
        if(i==0) {                                  
            hour8.setBackground(Red);
            hour9.setBackground(Red);
            hour10.setBackground(Red);
            hour11.setBackground(Black);
            hour12.setBackground(Red);
            hour13.setBackground(Red);
            hour14.setBackground(Red);
        }
        if(i==1) {
            hour8.setBackground(Black);
            hour9.setBackground(Black);
            hour10.setBackground(Red);
            hour11.setBackground(Black);
            hour12.setBackground(Black);
            hour13.setBackground(Red);
            hour14.setBackground(Black);
        }
        if(i==2) {
            hour8.setBackground(Red);
            hour9.setBackground(Black);
            hour10.setBackground(Red);
            hour11.setBackground(Red);
            hour12.setBackground(Red);
            hour13.setBackground(Black);
            hour14.setBackground(Red);
        }
        if(i==3) {
            hour8.setBackground(Red);
            hour9.setBackground(Black);
            hour10.setBackground(Red);
            hour11.setBackground(Red);
            hour12.setBackground(Black);
            hour13.setBackground(Red);
            hour14.setBackground(Red);
        }
        if(i==4) {
            hour8.setBackground(Black);
            hour9.setBackground(Red);
            hour10.setBackground(Red);
            hour11.setBackground(Red);
            hour12.setBackground(Black);
            hour13.setBackground(Red);
            hour14.setBackground(Black);
        }
        if(i==5) {
            hour8.setBackground(Red);
            hour9.setBackground(Red);
            hour10.setBackground(Black);
            hour11.setBackground(Red);
            hour12.setBackground(Black);
            hour13.setBackground(Red);
            hour14.setBackground(Red);
        }
        if(i==6) {
            hour8.setBackground(Black);
            hour9.setBackground(Red);
            hour10.setBackground(Black);
            hour11.setBackground(Red);
            hour12.setBackground(Red);
            hour13.setBackground(Red);
            hour14.setBackground(Red);
        }
        if(i==7) {
            hour8.setBackground(Red);
            hour9.setBackground(Black);
            hour10.setBackground(Red);
            hour11.setBackground(Black);
            hour12.setBackground(Black);
            hour13.setBackground(Red);
            hour14.setBackground(Black);
        }
        if(i==8) {
            hour8.setBackground(Red);
            hour9.setBackground(Red);
            hour10.setBackground(Red);
            hour11.setBackground(Red);
            hour12.setBackground(Red);
            hour13.setBackground(Red);
            hour14.setBackground(Red);
        }
        if(i==9) {
            hour8.setBackground(Red);
            hour9.setBackground(Red);
            hour10.setBackground(Red);
            hour11.setBackground(Red);
            hour12.setBackground(Black);
            hour13.setBackground(Red);
            hour14.setBackground(Black);
        }
    }
    //MINUTES
    public void updateMinuteFirst() {
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("mm");  
        String strDate = dateFormat.format(date);
        char mn1=strDate.charAt(0);
        min1Set(Character.getNumericValue(mn1));
    }
    public void updateMinuteSecond() {
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("mm");  
        String strDate = dateFormat.format(date);
        char mn2 = strDate.charAt(1);
        min2Set(Character.getNumericValue(mn2));
    }
    public void min1Set(int i){
        if(i==0) {                                  
            min1.setBackground(Red);
            min2.setBackground(Red);
            min3.setBackground(Red);
            min4.setBackground(Black);
            min5.setBackground(Red);
            min6.setBackground(Red);
            min7.setBackground(Red);
        }
        if(i==1) {
            min1.setBackground(Black);
            min2.setBackground(Black);
            min3.setBackground(Red);
            min4.setBackground(Black);
            min5.setBackground(Black);
            min6.setBackground(Red);
            min7.setBackground(Black);
        }
        if(i==2) {
            min1.setBackground(Red);
            min2.setBackground(Black);
            min3.setBackground(Red);
            min4.setBackground(Red);
            min5.setBackground(Red);
            min6.setBackground(Black);
            min7.setBackground(Red);
        }
        if(i==3) {
            min1.setBackground(Red);
            min2.setBackground(Black);
            min3.setBackground(Red);
            min4.setBackground(Red);
            min5.setBackground(Black);
            min6.setBackground(Red);
            min7.setBackground(Red);
        }
        if(i==4) {
            min1.setBackground(Black);
            min2.setBackground(Red);
            min3.setBackground(Red);
            min4.setBackground(Red);
            min5.setBackground(Black);
            min6.setBackground(Red);
            min7.setBackground(Black);
        }
        if(i==5) {
            min1.setBackground(Red);
            min2.setBackground(Red);
            min3.setBackground(Black);
            min4.setBackground(Red);
            min5.setBackground(Black);
            min6.setBackground(Red);
            min7.setBackground(Red);
        }
        if(i==6) {
            min1.setBackground(Black);
            min2.setBackground(Red);
            min3.setBackground(Black);
            min4.setBackground(Red);
            min5.setBackground(Red);
            min6.setBackground(Red);
            min7.setBackground(Red);
        }
        if(i==7) {
            min1.setBackground(Red);
            min2.setBackground(Black);
            min3.setBackground(Red);
            min4.setBackground(Black);
            min5.setBackground(Black);
            min6.setBackground(Red);
            min7.setBackground(Black);
        }
        if(i==8) {
            min1.setBackground(Red);
            min2.setBackground(Red);
            min3.setBackground(Red);
            min4.setBackground(Red);
            min5.setBackground(Red);
            min6.setBackground(Red);
            min7.setBackground(Red);
        }
        if(i==9) {
            min1.setBackground(Red);
            min2.setBackground(Red);
            min3.setBackground(Red);
            min4.setBackground(Red);
            min5.setBackground(Black);
            min6.setBackground(Red);
            min7.setBackground(Black);
        }
    }
    public void min2Set(int i){
        if(i==0) {                                  
            min8.setBackground(Red);
            min9.setBackground(Red);
            min10.setBackground(Red);
            min11.setBackground(Black);
            min12.setBackground(Red);
            min13.setBackground(Red);
            min14.setBackground(Red);
        }
        if(i==1) {
            min8.setBackground(Black);
            min9.setBackground(Black);
            min10.setBackground(Red);
            min11.setBackground(Black);
            min12.setBackground(Black);
            min13.setBackground(Red);
            min14.setBackground(Black);
        }
        if(i==2) {
            min8.setBackground(Red);
            min9.setBackground(Black);
            min10.setBackground(Red);
            min11.setBackground(Red);
            min12.setBackground(Red);
            min13.setBackground(Black);
            min14.setBackground(Red);
        }
        if(i==3) {
            min8.setBackground(Red);
            min9.setBackground(Black);
            min10.setBackground(Red);
            min11.setBackground(Red);
            min12.setBackground(Black);
            min13.setBackground(Red);
            min14.setBackground(Red);
        }
        if(i==4) {
            min8.setBackground(Black);
            min9.setBackground(Red);
            min10.setBackground(Red);
            min11.setBackground(Red);
            min12.setBackground(Black);
            min13.setBackground(Red);
            min14.setBackground(Black);
        }
        if(i==5) {
            min8.setBackground(Red);
            min9.setBackground(Red);
            min10.setBackground(Black);
            min11.setBackground(Red);
            min12.setBackground(Black);
            min13.setBackground(Red);
            min14.setBackground(Red);
        }
        if(i==6) {
            min8.setBackground(Black);
            min9.setBackground(Red);
            min10.setBackground(Black);
            min11.setBackground(Red);
            min12.setBackground(Red);
            min13.setBackground(Red);
            min14.setBackground(Red);
        }
        if(i==7) {
            min8.setBackground(Red);
            min9.setBackground(Black);
            min10.setBackground(Red);
            min11.setBackground(Black);
            min12.setBackground(Black);
            min13.setBackground(Red);
            min14.setBackground(Black);
        }
        if(i==8) {
            min8.setBackground(Red);
            min9.setBackground(Red);
            min10.setBackground(Red);
            min11.setBackground(Red);
            min12.setBackground(Red);
            min13.setBackground(Red);
            min14.setBackground(Red);
        }
        if(i==9) {
            min8.setBackground(Red);
            min9.setBackground(Red);
            min10.setBackground(Red);
            min11.setBackground(Red);
            min12.setBackground(Black);
            min13.setBackground(Red);
            min14.setBackground(Black);
        }
    }
    //SECONDS
    public void updateSecondFirst() {
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("ss");  
        String strDate = dateFormat.format(date);
        char sc1=strDate.charAt(0);
        sec1Set(Character.getNumericValue(sc1));
    }
    public void updateSecondSecond() {
        Date date = Calendar.getInstance().getTime();  
        DateFormat dateFormat = new SimpleDateFormat("ss");  
        String strDate = dateFormat.format(date);
        char sc2=strDate.charAt(1);
        sec2Set(Character.getNumericValue(sc2));
    }
    public void sec1Set(int i){
        if(i==0) {                                  
            sec1.setBackground(Red);
            sec2.setBackground(Red);
            sec3.setBackground(Red);
            sec4.setBackground(Black);
            sec5.setBackground(Red);
            sec6.setBackground(Red);
            sec7.setBackground(Red);
        }
        if(i==1) {
            sec1.setBackground(Black);
            sec2.setBackground(Black);
            sec3.setBackground(Red);
            sec4.setBackground(Black);
            sec5.setBackground(Black);
            sec6.setBackground(Red);
            sec7.setBackground(Black);
        }
        if(i==2) {
            sec1.setBackground(Red);
            sec2.setBackground(Black);
            sec3.setBackground(Red);
            sec4.setBackground(Red);
            sec5.setBackground(Red);
            sec6.setBackground(Black);
            sec7.setBackground(Red);
        }
        if(i==3) {
            sec1.setBackground(Red);
            sec2.setBackground(Black);
            sec3.setBackground(Red);
            sec4.setBackground(Red);
            sec5.setBackground(Black);
            sec6.setBackground(Red);
            sec7.setBackground(Red);
        }
        if(i==4) {
            sec1.setBackground(Black);
            sec2.setBackground(Red);
            sec3.setBackground(Red);
            sec4.setBackground(Red);
            sec5.setBackground(Black);
            sec6.setBackground(Red);
            sec7.setBackground(Black);
        }
        if(i==5) {
            sec1.setBackground(Red);
            sec2.setBackground(Red);
            sec3.setBackground(Black);
            sec4.setBackground(Red);
            sec5.setBackground(Black);
            sec6.setBackground(Red);
            sec7.setBackground(Red);
        }
        if(i==6) {
            sec1.setBackground(Black);
            sec2.setBackground(Red);
            sec3.setBackground(Black);
            sec4.setBackground(Red);
            sec5.setBackground(Red);
            sec6.setBackground(Red);
            sec7.setBackground(Red);
        }
        if(i==7) {
            sec1.setBackground(Red);
            sec2.setBackground(Black);
            sec3.setBackground(Red);
            sec4.setBackground(Black);
            sec5.setBackground(Black);
            sec6.setBackground(Red);
            sec7.setBackground(Black);
        }
        if(i==8) {
            sec1.setBackground(Red);
            sec2.setBackground(Red);
            sec3.setBackground(Red);
            sec4.setBackground(Red);
            sec5.setBackground(Red);
            sec6.setBackground(Red);
            sec7.setBackground(Red);
        }
        if(i==9) {
            sec1.setBackground(Red);
            sec2.setBackground(Red);
            sec3.setBackground(Red);
            sec4.setBackground(Red);
            sec5.setBackground(Black);
            sec6.setBackground(Red);
            sec7.setBackground(Black);
        }
    }
    public void sec2Set(int i){
        if(i==0) {                                  
            sec8.setBackground(Red);
            sec9.setBackground(Red);
            sec10.setBackground(Red);
            sec11.setBackground(Black);
            sec12.setBackground(Red);
            sec13.setBackground(Red);
            sec14.setBackground(Red);
        }
        if(i==1) {
            sec8.setBackground(Black);
            sec9.setBackground(Black);
            sec10.setBackground(Red);
            sec11.setBackground(Black);
            sec12.setBackground(Black);
            sec13.setBackground(Red);
            sec14.setBackground(Black);
        }
        if(i==2) {
            sec8.setBackground(Red);
            sec9.setBackground(Black);
            sec10.setBackground(Red);
            sec11.setBackground(Red);
            sec12.setBackground(Red);
            sec13.setBackground(Black);
            sec14.setBackground(Red);
        }
        if(i==3) {
            sec8.setBackground(Red);
            sec9.setBackground(Black);
            sec10.setBackground(Red);
            sec11.setBackground(Red);
            sec12.setBackground(Black);
            sec13.setBackground(Red);
            sec14.setBackground(Red);
        }
        if(i==4) {
            sec8.setBackground(Black);
            sec9.setBackground(Red);
            sec10.setBackground(Red);
            sec11.setBackground(Red);
            sec12.setBackground(Black);
            sec13.setBackground(Red);
            sec14.setBackground(Black);
        }
        if(i==5) {
            sec8.setBackground(Red);
            sec9.setBackground(Red);
            sec10.setBackground(Black);
            sec11.setBackground(Red);
            sec12.setBackground(Black);
            sec13.setBackground(Red);
            sec14.setBackground(Red);
        }
        if(i==6) {
            sec8.setBackground(Black);
            sec9.setBackground(Red);
            sec10.setBackground(Black);
            sec11.setBackground(Red);
            sec12.setBackground(Red);
            sec13.setBackground(Red);
            sec14.setBackground(Red);
        }
        if(i==7) {
            sec8.setBackground(Red);
            sec9.setBackground(Black);
            sec10.setBackground(Red);
            sec11.setBackground(Black);
            sec12.setBackground(Black);
            sec13.setBackground(Red);
            sec14.setBackground(Black);
        }
        if(i==8) {
            sec8.setBackground(Red);
            sec9.setBackground(Red);
            sec10.setBackground(Red);
            sec11.setBackground(Red);
            sec12.setBackground(Red);
            sec13.setBackground(Red);
            sec14.setBackground(Red);
        }
        if(i==9) {
            sec8.setBackground(Red);
            sec9.setBackground(Red);
            sec10.setBackground(Red);
            sec11.setBackground(Red);
            sec12.setBackground(Black);
            sec13.setBackground(Red);
            sec14.setBackground(Black);
        }
    }
    public void setBlack() {
        hour1.setBackground(Black);
        hour2.setBackground(Black);
        hour3.setBackground(Black);
        hour4.setBackground(Black);
        hour5.setBackground(Black);
        hour6.setBackground(Black);
        hour7.setBackground(Black);
        hour8.setBackground(Black);
        hour9.setBackground(Black);
        hour10.setBackground(Black);
        hour11.setBackground(Black);
        hour12.setBackground(Black);
        hour13.setBackground(Black);
        hour14.setBackground(Black);
        
        min1.setBackground(Black);
        min2.setBackground(Black);
        min3.setBackground(Black);
        min4.setBackground(Black);
        min5.setBackground(Black);
        min6.setBackground(Black);
        min7.setBackground(Black);
        min8.setBackground(Black);
        min9.setBackground(Black);
        min10.setBackground(Black);
        min11.setBackground(Black);
        min12.setBackground(Black);
        min13.setBackground(Black);
        min14.setBackground(Black);
        
        sec1.setBackground(Black);
        sec2.setBackground(Black);
        sec3.setBackground(Black);
        sec4.setBackground(Black);
        sec5.setBackground(Black);
        sec6.setBackground(Black);
        sec7.setBackground(Black);
        sec8.setBackground(Black);
        sec9.setBackground(Black);
        sec10.setBackground(Black);
        sec11.setBackground(Black);
        sec12.setBackground(Black);
        sec13.setBackground(Black);
        sec14.setBackground(Black);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        hour14 = new javax.swing.JButton();
        hour7 = new javax.swing.JButton();
        hour5 = new javax.swing.JButton();
        hour4 = new javax.swing.JButton();
        hour2 = new javax.swing.JButton();
        hour1 = new javax.swing.JButton();
        hour3 = new javax.swing.JButton();
        hour6 = new javax.swing.JButton();
        hour12 = new javax.swing.JButton();
        hour9 = new javax.swing.JButton();
        hour8 = new javax.swing.JButton();
        hour10 = new javax.swing.JButton();
        hour11 = new javax.swing.JButton();
        min5 = new javax.swing.JButton();
        min2 = new javax.swing.JButton();
        min1 = new javax.swing.JButton();
        min4 = new javax.swing.JButton();
        min6 = new javax.swing.JButton();
        min7 = new javax.swing.JButton();
        min12 = new javax.swing.JButton();
        min9 = new javax.swing.JButton();
        min8 = new javax.swing.JButton();
        min3 = new javax.swing.JButton();
        min10 = new javax.swing.JButton();
        min11 = new javax.swing.JButton();
        min13 = new javax.swing.JButton();
        min14 = new javax.swing.JButton();
        sec5 = new javax.swing.JButton();
        sec2 = new javax.swing.JButton();
        sec1 = new javax.swing.JButton();
        sec3 = new javax.swing.JButton();
        sec4 = new javax.swing.JButton();
        sec6 = new javax.swing.JButton();
        sec7 = new javax.swing.JButton();
        sec12 = new javax.swing.JButton();
        sec9 = new javax.swing.JButton();
        sec8 = new javax.swing.JButton();
        sec10 = new javax.swing.JButton();
        sec11 = new javax.swing.JButton();
        sec13 = new javax.swing.JButton();
        sec14 = new javax.swing.JButton();
        hour13 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jButton2.setBackground(new java.awt.Color(255, 0, 0));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DIGITAL CLOCK");
        setBackground(new java.awt.Color(0, 0, 0));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        hour14.setBackground(new java.awt.Color(0, 0, 0));
        hour14.setBorder(null);

        hour7.setBackground(new java.awt.Color(0, 0, 0));
        hour7.setBorder(null);

        hour5.setBackground(new java.awt.Color(0, 0, 0));
        hour5.setBorder(null);

        hour4.setBackground(new java.awt.Color(0, 0, 0));
        hour4.setBorder(null);

        hour2.setBackground(new java.awt.Color(0, 0, 0));
        hour2.setBorder(null);

        hour1.setBackground(new java.awt.Color(0, 0, 0));
        hour1.setBorder(null);

        hour3.setBackground(new java.awt.Color(0, 0, 0));
        hour3.setBorder(null);

        hour6.setBackground(new java.awt.Color(0, 0, 0));
        hour6.setBorder(null);

        hour12.setBackground(new java.awt.Color(0, 0, 0));
        hour12.setBorder(null);

        hour9.setBackground(new java.awt.Color(0, 0, 0));
        hour9.setBorder(null);

        hour8.setBackground(new java.awt.Color(0, 0, 0));
        hour8.setBorder(null);

        hour10.setBackground(new java.awt.Color(0, 0, 0));
        hour10.setBorder(null);

        hour11.setBackground(new java.awt.Color(0, 0, 0));
        hour11.setBorder(null);

        min5.setBackground(new java.awt.Color(0, 0, 0));
        min5.setBorder(null);

        min2.setBackground(new java.awt.Color(0, 0, 0));
        min2.setBorder(null);

        min1.setBackground(new java.awt.Color(0, 0, 0));
        min1.setBorder(null);

        min4.setBackground(new java.awt.Color(0, 0, 0));
        min4.setBorder(null);

        min6.setBackground(new java.awt.Color(0, 0, 0));
        min6.setBorder(null);

        min7.setBackground(new java.awt.Color(0, 0, 0));
        min7.setBorder(null);

        min12.setBackground(new java.awt.Color(0, 0, 0));
        min12.setBorder(null);

        min9.setBackground(new java.awt.Color(0, 0, 0));
        min9.setBorder(null);

        min8.setBackground(new java.awt.Color(0, 0, 0));
        min8.setBorder(null);

        min3.setBackground(new java.awt.Color(0, 0, 0));
        min3.setBorder(null);

        min10.setBackground(new java.awt.Color(0, 0, 0));
        min10.setBorder(null);

        min11.setBackground(new java.awt.Color(0, 0, 0));
        min11.setBorder(null);

        min13.setBackground(new java.awt.Color(0, 0, 0));
        min13.setBorder(null);

        min14.setBackground(new java.awt.Color(0, 0, 0));
        min14.setBorder(null);

        sec5.setBackground(new java.awt.Color(0, 0, 0));
        sec5.setBorder(null);

        sec2.setBackground(new java.awt.Color(0, 0, 0));
        sec2.setBorder(null);

        sec1.setBackground(new java.awt.Color(0, 0, 0));
        sec1.setBorder(null);

        sec3.setBackground(new java.awt.Color(0, 0, 0));
        sec3.setBorder(null);

        sec4.setBackground(new java.awt.Color(0, 0, 0));
        sec4.setBorder(null);

        sec6.setBackground(new java.awt.Color(0, 0, 0));
        sec6.setBorder(null);

        sec7.setBackground(new java.awt.Color(0, 0, 0));
        sec7.setBorder(null);

        sec12.setBackground(new java.awt.Color(0, 0, 0));
        sec12.setBorder(null);

        sec9.setBackground(new java.awt.Color(0, 0, 0));
        sec9.setBorder(null);

        sec8.setBackground(new java.awt.Color(0, 0, 0));
        sec8.setBorder(null);

        sec10.setBackground(new java.awt.Color(0, 0, 0));
        sec10.setBorder(null);

        sec11.setBackground(new java.awt.Color(0, 0, 0));
        sec11.setBorder(null);

        sec13.setBackground(new java.awt.Color(0, 0, 0));
        sec13.setBorder(null);

        sec14.setBackground(new java.awt.Color(0, 0, 0));
        sec14.setBorder(null);

        hour13.setBackground(new java.awt.Color(0, 0, 0));
        hour13.setBorder(null);

        jButton1.setBackground(new java.awt.Color(255, 0, 0));
        jButton1.setBorder(null);

        jButton6.setBackground(new java.awt.Color(255, 0, 0));
        jButton6.setBorder(null);

        jButton7.setBackground(new java.awt.Color(255, 0, 0));
        jButton7.setBorder(null);

        jButton8.setBackground(new java.awt.Color(255, 0, 0));
        jButton8.setBorder(null);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(hour2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(hour3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(hour4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(hour1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(hour5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(hour6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(hour7, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(hour9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(hour10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(hour11, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(hour8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(hour12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(hour13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(hour14, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(min2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(86, 86, 86)
                                .addComponent(min3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(min1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(min5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(86, 86, 86)
                                .addComponent(min6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(min7, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(min4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(min8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(min14, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(min9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(86, 86, 86)
                                .addComponent(min10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(min11, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(min12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(86, 86, 86)
                                .addComponent(min13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(sec2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(sec3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(sec4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(sec1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(sec5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(sec6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(sec7, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(sec9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(sec10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(sec11, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(sec8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(sec12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(86, 86, 86)
                        .addComponent(sec13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(sec14, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(min1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(min3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(min2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(min4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(min6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(min5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(min7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(min8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(min10, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(min9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(min11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(min13, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(min12, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(min14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(sec8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sec10, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sec9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sec11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sec13, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sec12, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sec14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(sec1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(sec3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sec2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sec4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(sec6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(sec5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sec7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(hour8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hour10, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hour9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hour11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hour12, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hour14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(hour13, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(hour1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hour3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hour2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hour4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hour6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(hour5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hour7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {hour10, hour12, hour13, hour2, hour3, hour5, hour6, hour9, min10, min12, min13, min2, min3, min5, min6, min9, sec10, sec12, sec13, sec2, sec3, sec5, sec6, sec9});

        jButton3.setText("Pause");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton3)
                        .addGap(511, 511, 511))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(stop==false)
            stop=true;
        else if(stop==true)
        {
            stop=false;
            taskClock = new Run();
            taskClock.execute();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DigitalClock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DigitalClock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DigitalClock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DigitalClock.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DigitalClock().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton hour1;
    private javax.swing.JButton hour10;
    private javax.swing.JButton hour11;
    private javax.swing.JButton hour12;
    private javax.swing.JButton hour13;
    private javax.swing.JButton hour14;
    private javax.swing.JButton hour2;
    private javax.swing.JButton hour3;
    private javax.swing.JButton hour4;
    private javax.swing.JButton hour5;
    private javax.swing.JButton hour6;
    private javax.swing.JButton hour7;
    private javax.swing.JButton hour8;
    private javax.swing.JButton hour9;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton min1;
    private javax.swing.JButton min10;
    private javax.swing.JButton min11;
    private javax.swing.JButton min12;
    private javax.swing.JButton min13;
    private javax.swing.JButton min14;
    private javax.swing.JButton min2;
    private javax.swing.JButton min3;
    private javax.swing.JButton min4;
    private javax.swing.JButton min5;
    private javax.swing.JButton min6;
    private javax.swing.JButton min7;
    private javax.swing.JButton min8;
    private javax.swing.JButton min9;
    private javax.swing.JButton sec1;
    private javax.swing.JButton sec10;
    private javax.swing.JButton sec11;
    private javax.swing.JButton sec12;
    private javax.swing.JButton sec13;
    private javax.swing.JButton sec14;
    private javax.swing.JButton sec2;
    private javax.swing.JButton sec3;
    private javax.swing.JButton sec4;
    private javax.swing.JButton sec5;
    private javax.swing.JButton sec6;
    private javax.swing.JButton sec7;
    private javax.swing.JButton sec8;
    private javax.swing.JButton sec9;
    // End of variables declaration//GEN-END:variables
}
