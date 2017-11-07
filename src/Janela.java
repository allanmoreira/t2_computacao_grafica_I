import com.jogamp.opengl.GLCapabilities;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;

public class Janela
{
	private Desenho desenho;
	private static ArrayList<Pessoa> listaPessoas;
	private static LeituraArquivo leituraArquivo;

	/**
	 * Construtor da classe ExemploJava que n�o recebe par�metros. Cria uma janela e insere  
	 * um componente canvas OpenGL.
	 */
	public Janela()
	{
		// Cria janela
		JFrame janela = new JFrame("Desenho de um teapot 3D");   
		janela.setSize(1366,768);
		janela.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		BorderLayout layout = new BorderLayout(); 
		Container caixa=janela.getContentPane();
		caixa.setLayout(layout); 

		// Cria um objeto GLCapabilities para especificar o n�mero de bits 
		// por pixel para RGBA
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		capabilities.setRedBits(8);
		capabilities.setBlueBits(8);
		capabilities.setGreenBits(8);
		capabilities.setAlphaBits(8);

		// Cria o objeto que ir� gerenciar os eventos
		desenho = new Desenho(
			listaPessoas,
			leituraArquivo.getQtdeFrames(),
			leituraArquivo.getPixelsPorMetro(),
			janela
		);

		// Cria um canvas, adiciona na janela, e especifica o objeto "ouvinte" 
		// para os eventos Gl, de mouse e teclado
		// GLCanvas canvas = GLDrawableFactory.getFactory().createGLCanvas(c);

	}

	/**
	 * M�todo main que apenas cria um objeto ExemploJava.
	 */
	public static void main(String args[]) {
		leituraArquivo = new LeituraArquivo(ArquivosDataSet.FRANCA_FR01);
//        LeituraArquivo leituraArquivo = new LeituraArquivo(ArquivosDataSet.TURQUIA_TR01);
		listaPessoas = leituraArquivo.ler();

		Janela ej = new Janela();
	}
}
