import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.time.Duration;
import java.time.Instant;

@SuppressWarnings("serial")
class Main extends JFrame {
	public static final int WIDTH      = 768;
    public static final int HEIGHT     = 512;
    public static final int GRAPH_HEIGHT = 100;
    public static final int SIMS       = 850;
    public static final int SIM_SIZE   = 9;
    public static final int MAX_MOVE   = 1000;
    public static final int CONT_AFTER = 240; // 4 days
    public static final int DEATH_RATE = 10;   
    public static final int ILL_FOR    = 840; // 14 days
    public static final int TRANS_RATE = 50;  
    
    public static final Color SICK    = new Color(255, 153, 90);
    public static final Color DEAD    = new Color(255, 0, 0);
    public static final Color IMMUNE  = new Color(3, 11, 252);
    public static final Color UNKNOWN = new Color(141, 141, 141);

	class App extends JPanel implements MouseListener, KeyListener {
        Population p;
        Simulant selected;
        Color[][] graph;
        int graphX;
        private long lastUpdateTime;
        public App() {
            setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT+Main.GRAPH_HEIGHT));
            this.setFocusable(true);
            this.requestFocus();
            this.addMouseListener(this);
            this.addKeyListener(this);
            p = new Population();
            selected = null;
            graphX = 0;
            graph = new Color[Main.WIDTH][Main.GRAPH_HEIGHT];
            for(int x = 0; x < graph.length; x++){
                for(int y = 0; y < graph[x].length; y++){
                    graph[x][y]= new Color(255, 255, 255);
                }
            }
        }

        @Override
        public void paint(Graphics g) {
            RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
            ((Graphics2D)g).setRenderingHints(rh);

            // Calculate frame rate
        long currentTime = System.currentTimeMillis();
        long howLong = currentTime - lastUpdateTime;
        lastUpdateTime = currentTime;

        // Draw additional information
        g.setColor(Color.BLACK);
        g.drawString("Simulator Speed: " + (int)(1000.0 / howLong) + " fps", 10, Main.HEIGHT + 20);
        g.drawString("Population Size: " + p.getPopulationSize(), 10, Main.HEIGHT + 40);
        g.drawString("Sick People: " + p.getNumberSick(), 10, Main.HEIGHT + 60);
        g.drawString("Immune People: " + p.getNumberImmune(), 10, Main.HEIGHT + 80);

            p.update();

            g.setColor(new Color(255,255,255));
            g.fillRect(0,0,Main.WIDTH,Main.HEIGHT);

            if (selected != null){
                g.setColor(new Color(94,86,90));
                g.drawOval((int)selected.homeLoc.getX() - (int)selected.mobility, (int)selected.homeLoc.getY() - (int)selected.mobility, (int)selected.mobility*2, (int)selected.mobility*2);
            }

            for(Simulant s: p.sims){
                g.setColor(chooseColour(s));
                g.fillOval((int)s.loc.getX() - Main.SIM_SIZE/2,(int)s.loc.getY() - Main.SIM_SIZE/2, Main.SIM_SIZE, Main.SIM_SIZE);
            }
            if (p.numberSick() > 0){
                g.setColor(new Color(255, 253, 90, 20));
                g.fillOval((int)p.averageXOfSick() - 20, (int)p.averageYOfSick() - 20, 40, 40);
            }

            g.setColor(new Color(255,255,255));
            ArrayList<Simulant> sorted = p.sort();
            for(int y = 0; y < Main.GRAPH_HEIGHT; y++){
                graph[graphX][y] = chooseColour(sorted.get(y*Main.SIMS/Main.GRAPH_HEIGHT));
            }
            graphX = (graphX + 1) % Main.WIDTH;

            // draw graph
            for(int x = 0; x < graph.length; x++){
                for(int y = 0; y < graph[x].length; y++){
                    g.setColor(graph[x][y]);
                    g.drawLine(x, Main.HEIGHT + Main.GRAPH_HEIGHT - y, x, Main.HEIGHT + Main.GRAPH_HEIGHT -y);
                }
            }
        }

        private Color chooseColour(Simulant s){
            if(s.sick > 0){
                    return Main.SICK;
            } else if (s.sick < 0){
                    return Main.DEAD;
            } else if (s.immune){
                    return Main.IMMUNE;
            } else {
                    return Main.UNKNOWN;
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            selected = p.simAtLocation(e.getPoint());
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}

        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_RIGHT){
                selected = p.nextAfter(selected);
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT){
                selected = p.prevBefore(selected);
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {}
    }

    public static void main(String[] args) throws Exception {
        Main window = new Main();
        window.run();
    }

    private Main() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        App canvas = new App();
        this.setContentPane(canvas);
        this.pack();
        this.setVisible(true);
    }

    public void run() {
        while (true) {
            Instant startTime = Instant.now();
            this.repaint();
            Instant endTime = Instant.now();
            long howLong = Duration.between(startTime, endTime).toMillis();
            try{
                Thread.sleep(16l - howLong);
            } catch (InterruptedException e){
                System.out.println("thread was interrupted, but who cares?");
            } catch (IllegalArgumentException e){
                System.out.println("application can't keep up with framerate");
            }
        }
    }
}