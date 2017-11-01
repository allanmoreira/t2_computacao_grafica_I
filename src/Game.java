/**
 * Created by allan on 30/08/17.
 */

import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

import javax.swing.JFrame;

import static com.jogamp.opengl.GL.*;

public class Game extends JFrame implements GLEventListener {
    private static final long serialVersionUID = 1L;
    final private int width = 500;
    final private int height = 500;
    
    private int x, y;

    public Game(int largura, int altura) {
        super("Minimal OpenGL");
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(this);

        this.setName("Minimal OpenGL");
        this.getContentPane().add(canvas);

//        this.setSize(width, height);
        this.setSize(largura, altura);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        canvas.requestFocusInWindow();
    }
    
    @Override
    public void init(GLAutoDrawable drawable) {
    	GL2 gl = drawable.getGL().getGL2();
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    @Override
    public void display(GLAutoDrawable drawable) {
    	GL2 gl = drawable.getGL().getGL2();
    	
        gl.glLoadIdentity();
        // Limpa a janela de visualização com a cor preta
        gl.glClearColor(0,0,0,0);
        gl.glClear(GL_COLOR_BUFFER_BIT);

        // Define a cor de desenho: vermelha
        gl.glColor3f(1,0,0);

        gl.glLineWidth(2);

        // Desenha os eixos cartesianos
        gl.glBegin(GL_LINES);
        // linha horizontal
        gl.glVertex2f(0f, 1.0f);
        gl.glVertex2f(0.0f, -1.0f);
        // linha vertical
        gl.glVertex2f(1.0f, 0.0f);
        gl.glVertex2f(-1.0f, 0.0f);
        gl.glEnd();

//        gl.glTranslatef(translacaoX, translacaoY, 0);

        gl.glLineWidth(3);

        // Desenha o triângulo
        gl.glBegin(GL_TRIANGLES);
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex2f(-0.2f,0.1f);
        gl.glColor3f(1.0f, 0.0f, 0.0f);
        gl.glVertex2f(0.0f,0.22f);
        gl.glColor3f(0.0f, 0.0f, 1.0f);
        gl.glVertex2f(0.2f,0.1f);
        gl.glEnd();

        gl.glBegin(GL_LINE_LOOP);
        gl.glVertex3f(-0.2f,0.1f, 0.0f);
        gl.glVertex3f(-0.2f,-0.2f, 0.0f);
        gl.glVertex3f(0.2f,-0.2f, 0.0f);
        gl.glVertex3f(0.2f,0.1f, 0.0f);
        gl.glEnd();
        //Executa os comandos OpenGL

        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}

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