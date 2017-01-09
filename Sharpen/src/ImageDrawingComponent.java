import java.io.*;
import java.net.*;
import java.awt.*;
import javax.imageio.*;
import java.awt.image.*;
 
@SuppressWarnings("serial")
class ImageDrawingComponent extends Component {
 
    static String descs[] = {
        "Original Image",
        "Sharpen", 
    };
    
    int w, h;
    int opIndex;
    BufferedImage img;
     
    public static final float[] SHARPEN3x3 = { // sharpening filter kernel
        0.f, -1.f,  0.f,
       -1.f,  5.f, -1.f,
        0.f, -1.f,  0.f
    };
 
    public ImageDrawingComponent(URL imageSrc) {
        try {
            img = ImageIO.read(imageSrc);
            w = img.getWidth(null);
            h = img.getHeight(null);
            if (img.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage bi2 = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                
                Graphics big = bi2.getGraphics();
                big.drawImage(img, 0, 0, null);
                img = bi2;
            }
        } catch (IOException e) {
            System.out.println("Image could not be read");
            System.exit(1);
        }
    }
 
	public Dimension getPreferredSize() {
		return new Dimension(img.getWidth(null), img.getHeight(null));
	}
 
    static String[] getDescriptions() {
        return descs;
    }
 
    void setOpIndex(int i) {
        opIndex = i;
    }
     
    public void paint(Graphics g) {
 
        Graphics2D g2 = (Graphics2D) g;
 
        switch (opIndex) {
        case 0 : /* copy */
            g.drawImage(img, 0, 0, null);
            break;
        case 1:  /* sharpen */
            float[] data = SHARPEN3x3;
            ConvolveOp cop = new ConvolveOp(new Kernel(3, 3, data), ConvolveOp.EDGE_NO_OP, null);
            g2.drawImage(img, cop, 0, 0);
            
            break;
 
        default :
        }
    }
}