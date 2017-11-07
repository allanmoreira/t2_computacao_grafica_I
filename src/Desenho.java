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
import com.jogamp.opengl.util.gl2.GLUT;

import javax.swing.JFrame;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Desenho extends MouseAdapter implements GLEventListener, KeyListener {
    private static final long serialVersionUID = 1L;
    private ArrayList<Pessoa> listaPessoas;
    private ArrayList<Coordenada> listaCoordenadasFrame;
    private ArrayList<ArrayList<Coordenada>> matrizPessoasPorFrame;
    private int qtdesFrames;
    private int pixelsPorMetro;
    private int tempoAtual = 1;
    private static final int FPS = 30;
    private JFrame janela;

    private float R, G, B;

    private GLUT glut;
    private GLU glu;
    private GL2 gl;
    private GLAutoDrawable glDrawable;
    private double angle, fAspect;
    private float obsX = 400;
    private float obsY = 400;
    private float obsZ = 400;

    private float rotX = 1;
    private float rotY = 0;
    private float rotX_ini, rotY_ini, obsX_ini, obsY_ini, obsZ_ini;

    public Desenho(ArrayList<Pessoa> listaPessoas, int qtdesFrames, int pixelsPorMetro, JFrame janela) {
        this.janela = janela;

        GLProfile profile = GLProfile.getDefault();
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);
        janela.add(canvas, BorderLayout.CENTER);
        canvas.addGLEventListener(this);
        canvas.addMouseListener(this);
        canvas.addKeyListener(this);
        janela.setVisible(true);
        canvas.requestFocus();

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
        animator.start();

        // Especifica o �ngulo da proje��o perspectiva
        angle=88;
        // Inicializa o valor para corre��o de aspecto
        fAspect = 1;
    }
    
    @Override
    public void init(GLAutoDrawable drawable) {
        glDrawable = drawable;
        gl = drawable.getGL().getGL2();
        glu = new GLU();
        glut = new GLUT();
        drawable.setGL(new DebugGL2(gl));

        gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    @Override
    public void display(GLAutoDrawable drawable) {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        especificaParametrosVisualizacao();

        gl.glColor3f(0.0f, 0.0f, 1.0f);

        listaCoordenadasFrame = new ArrayList<>();
//
        for (int i = 0; i < listaPessoas.size(); i++) {
            Pessoa pessoa = listaPessoas.get(i);
            if(pessoa.getFilaCoordenadas().isEmpty()) {
                continue;
             }

            if(tempoAtual == pessoa.getFilaCoordenadas().peek().getTempo()){
                Coordenada coordenada = pessoa.getFilaCoordenadas().poll();
                listaCoordenadasFrame.add(coordenada);


                gl.glPushMatrix();
                gl.glTranslatef(coordenada.getX(),10,coordenada.getY());

                glut.glutWireTeapot(35);

                gl.glPopMatrix();

//                gl.glBegin( GL.GL_LINE_LOOP );
////                gl.glColor3f(1.0f, 0.7f, 0.0f);
//                gl.glVertex2f(coordenada.getX()-proporcao-1, altura -(coordenada.getY()-proporcao));
//                gl.glVertex2f(coordenada.getX()-proporcao-1, altura -(coordenada.getY()+proporcao));
//                gl.glVertex2f(coordenada.getX()+proporcao-1, altura -(coordenada.getY()+proporcao));
//                gl.glVertex2f(coordenada.getX()+proporcao-1, altura -(coordenada.getY()-proporcao));
//                gl.glEnd();

            }
        }

        desenhaChao();
        gl.glFlush();

        R = R + 0.005f;
//        G =  G + 0.005f;
//        B = B + 0.005f;
        matrizPessoasPorFrame.add(listaCoordenadasFrame);
//        System.out.println("Qtde loops: " + contLoopDisplay++);
        tempoAtual++;

        System.out.println(tempoAtual);
        System.out.println(qtdesFrames);
        System.out.println("-----------------");

        if(tempoAtual-1 == qtdesFrames) {
            new Eventos(matrizPessoasPorFrame, pixelsPorMetro);
            System.exit(0);
            janela.dispose();
        }


    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }

    public void especificaParametrosVisualizacao() {
        // Especifica sistema de coordenadas de proje��o
        gl.glMatrixMode(GL2.GL_PROJECTION);
        // Inicializa sistema de coordenadas de proje��o
        gl.glLoadIdentity();

        // Especifica a proje��o perspectiva(angulo,aspecto,zMin,zMax)
        glu.gluPerspective(angle, fAspect, 0.5, 1000);

        posicionaObservador();
    }

    public void posicionaObservador(){
        // Especifica sistema de coordenadas do modelo
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        // Inicializa sistema de coordenadas do modelo
        gl.glLoadIdentity();
//        gl.glTranslatef(-obsX,-obsY,-obsZ);
        // Especifica posi��o do observador e do alvo
        gl.glRotatef(rotX,1,0,0);
        gl.glRotatef(rotY,0,1,0);
        glu.gluLookAt(
                obsX, obsY, obsZ,
                50,1.0,0.0,
                0.0,100.0,0.0
        );
    }

    private void desenhaChao() {
        gl.glColor3f(1,0,1);
        gl.glLineWidth(3);
        gl.glBegin(GL.GL_LINES);
        for(float z=-1000; z<=1000; z+=10)
        {
            gl.glVertex3f(-1000,-0.1f,z);
            gl.glVertex3f( 1000,-0.1f,z);
        }
        for(float x=-1000; x<=1000; x+=10)
        {
            gl.glVertex3f(x,-0.1f,-1000);
            gl.glVertex3f(x,-0.1f,1000);
        }
        gl.glEnd();
        gl.glLineWidth(1);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) // Zoom in
            if (angle >= 4) angle -= 4;
        if (e.getButton() == MouseEvent.BUTTON3) // Zoom out
            if (angle <= 72) angle += 4;
        glDrawable.display();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:	System.exit(0);
                break;

            case KeyEvent.VK_LEFT: obsX -=10;
                break;

            case KeyEvent.VK_RIGHT: obsX +=10;
                break;

            case KeyEvent.VK_UP: obsY +=10;
                break;

            case KeyEvent.VK_DOWN: obsY -=10;
                break;

            case KeyEvent.VK_HOME: obsZ +=10;
                break;

            case KeyEvent.VK_END: obsZ -=1;
                break;
        }
        glDrawable.display();
    }

    public void keyReleased(KeyEvent e) { }

    public void keyTyped(KeyEvent e) { }

    public void play() {}

}