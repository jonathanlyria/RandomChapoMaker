import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel{
    private Timer timer = new Timer(1000, e -> repaint());
    public MainPanel(){
        timer.start();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(RandomChapatiMaker.getChapatiBurn(), 0, 0 , this);
    }
}
