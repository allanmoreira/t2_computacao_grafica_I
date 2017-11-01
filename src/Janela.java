import com.jogamp.opengl.GLCapabilities;

import javax.swing.*;
import java.awt.*;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

public class Janela
{
	private Game game;

	/**
	 * Construtor da classe ExemploJava que n�o recebe par�metros. Cria uma janela e insere  
	 * um componente canvas OpenGL.
	 */
	public Janela()
	{
		// Cria janela
		JFrame janela = new JFrame("Desenho de um teapot 3D");   
		janela.setBounds(50,100,500,500); 
		janela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		BorderLayout layout = new BorderLayout(); 
		Container caixa=janela.getContentPane();
		caixa.setLayout(layout); 

		// Cria um objeto GLCapabilities para especificar o n�mero de bits 
		// por pixel para RGBA
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities c = new GLCapabilities(profile);
		c.setRedBits(8);
		c.setBlueBits(8);
		c.setGreenBits(8);
		c.setAlphaBits(8); 

		// Cria o objeto que ir� gerenciar os eventos
		game = new Game();

		// Cria um canvas, adiciona na janela, e especifica o objeto "ouvinte" 
		// para os eventos Gl, de mouse e teclado
		// GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(c);
		GLCanvas canvas = new GLCanvas(c);
		janela.add(canvas,BorderLayout.CENTER);
		canvas.addGLEventListener(game);
		canvas.addMouseListener(game);
		canvas.addKeyListener(game);
		janela.setVisible(true);
		canvas.requestFocus();
	}

	/**
	 * M�todo main que apenas cria um objeto ExemploJava.
	 */
	public static void main(String args[])
	{
		Janela ej = new Janela();
	}
}
