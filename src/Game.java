/**
 * Created by allan on 30/08/17.
 */

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.jogamp.opengl.GL.GL_COLOR_BUFFER_BIT;
import static com.jogamp.opengl.GL.GL_DEPTH_BUFFER_BIT;

public class Game extends MouseAdapter implements GLEventListener, KeyListener {
    private static final long serialVersionUID = 1L;
    final private int width = 500;
    final private int height = 500;
    private GLUT glut;
    private GLU glu;
    private GL2 gl;
    private GLAutoDrawable glDrawable;
    private double angle, fAspect;
    
    private int x, y;

    public Game() {
        // Especifica o �ngulo da proje��o perspectiva
        angle=44;
        // Inicializa o valor para corre��o de aspecto
        fAspect = 1;
    }
    
    @Override
    public void init(GLAutoDrawable drawable) {
//    	GL2 gl = drawable.getGL().getGL2();
//        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

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

        gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
        gl.glLoadIdentity();

        especificaParametrosVisualizacao();

        gl.glColor3f(0.0f, 0.0f, 1.0f);

        glut.glutWireTeapot(35);

    }

    public void especificaParametrosVisualizacao()
    {
        // Especifica sistema de coordenadas de proje��o
        gl.glMatrixMode(GL2.GL_PROJECTION);
        // Inicializa sistema de coordenadas de proje��o
        gl.glLoadIdentity();

        // Especifica a proje��o perspectiva(angulo,aspecto,zMin,zMax)
        glu.gluPerspective(angle, fAspect, 0.5, 500);

        posicionaObservador();
    }

    public void posicionaObservador()
    {
        // Especifica sistema de coordenadas do modelo
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        // Inicializa sistema de coordenadas do modelo
        gl.glLoadIdentity();
        // Especifica posi��o do observador e do alvo
        glu.gluLookAt(0.0,80.0,200.0, 0.0,0.0,0.0, 0.0,1.0,0.0);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl.glViewport(0, 0, width, height);
        fAspect = (float)width/(float)height;
    }

    public void mouseClicked(MouseEvent e)
    {
        if (e.getButton() == MouseEvent.BUTTON1) // Zoom in
            if (angle >= 4) angle -= 4;
        if (e.getButton() == MouseEvent.BUTTON3) // Zoom out
            if (angle <= 72) angle += 4;
        glDrawable.display();
    }

    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_ESCAPE:	System.exit(0);
                break;
        }
        glDrawable.display();
    }

    /**
     * M�todo definido na interface KeyListener.
     */
    public void keyTyped(KeyEvent e) { }

    /**
     * M�todo definido na interface KeyListener.
     */
    public void keyReleased(KeyEvent e) { }

    public void play() {

    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
    
    
}