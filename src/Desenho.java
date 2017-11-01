/**
 * Created by allan on 30/08/17.
 */

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import javax.swing.JFrame;

import java.util.ArrayList;

public class Desenho extends JFrame implements GLEventListener {
    private static final long serialVersionUID = 1L;
    final private int largura;
    final private int altura;
    private ArrayList<Pessoa> listaPessoas;
    private ArrayList<Coordenada> listaCoordenadasFrame;
    private ArrayList<ArrayList<Coordenada>> matrizPessoasPorFrame;
    private int proporcao = 7;
    private int qtdesFrames;
    private int pixelsPorMetro;
    private int tempoAtual = 1;
    private static final int FPS = 30;

    private float R, G, B;

    private int x, y;

    public Desenho(int largura, int altura, ArrayList<Pessoa> listaPessoas, int qtdesFrames, int pixelsPorMetro) {
        super("Minimal OpenGL");
        GLProfile profile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(this);

        R = 0.0f;
        G = 0.0f;
        B = 0.0f;
        
        final FPSAnimator animator = new FPSAnimator(canvas, FPS, true);
        GLWindow window = GLWindow.create(capabilities);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent arg0) {
                // Use a dedicate thread to run the stop() to ensure that the
                // animator stops before program exits.
                new Thread() {
                    @Override
                    public void run() {
                        animator.stop(); // stop the animator loop
                        System.exit(0);
                    }
                }.start();
            };
        });

        this.listaPessoas = listaPessoas;
        this.qtdesFrames = qtdesFrames;
        this.pixelsPorMetro = pixelsPorMetro;
        matrizPessoasPorFrame = new ArrayList<>();

        this.setName("Minimal OpenGL");
        this.getContentPane().add(canvas);

        this.largura = largura;
        this.altura = altura;

        this.setSize(this.largura, this.altura);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        animator.start();
        this.setResizable(false);
        canvas.requestFocusInWindow();
    }
    
    @Override
    public void init(GLAutoDrawable drawable) {
    	GL2 gl = drawable.getGL().getGL2();
        gl.glMatrixMode( GL2.GL_PROJECTION );
        gl.glLoadIdentity();

        GLU glu = new GLU();
        glu.gluOrtho2D( 0.0f, largura, 0.0f, altura);

        gl.glMatrixMode( GL2.GL_MODELVIEW );
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    @Override
    public void display(GLAutoDrawable drawable) {
    	GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
//        gl.glClearColor(0,0,0,0);
        gl.glColor3f(R, G, B);
//        gl.glColor3f(1.0f, 0.0f, 1.0f);
        gl.glLineWidth(3);

        listaCoordenadasFrame = new ArrayList<>();
            
        for (int i = 0; i < listaPessoas.size(); i++) {
            Pessoa pessoa = listaPessoas.get(i);
            if(pessoa.getFilaCoordenadas().isEmpty()) {
                continue;
             }
            
            if(tempoAtual == pessoa.getFilaCoordenadas().peek().getTempo()){
                Coordenada coordenada = pessoa.getFilaCoordenadas().poll();
                listaCoordenadasFrame.add(coordenada);

                gl.glBegin( GL.GL_LINE_LOOP );
//                gl.glColor3f(1.0f, 0.7f, 0.0f);
                gl.glVertex2f(coordenada.getX()-proporcao-1, altura -(coordenada.getY()-proporcao));
                gl.glVertex2f(coordenada.getX()-proporcao-1, altura -(coordenada.getY()+proporcao));
                gl.glVertex2f(coordenada.getX()+proporcao-1, altura -(coordenada.getY()+proporcao));
                gl.glVertex2f(coordenada.getX()+proporcao-1, altura -(coordenada.getY()-proporcao));
                gl.glEnd();
            }
        }

        R = R + 0.005f;
//        G =  G + 0.005f;
//        B = B + 0.005f;
        matrizPessoasPorFrame.add(listaCoordenadasFrame);
//        System.out.println("Qtde loops: " + contLoopDisplay++);
        tempoAtual++;
        if(tempoAtual-1 == qtdesFrames) {
            new Eventos(matrizPessoasPorFrame, pixelsPorMetro);
            this.dispose();
        }


    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }

    public void play() {}

    
    
}