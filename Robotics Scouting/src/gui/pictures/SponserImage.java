package gui.pictures;

import java.awt.*;
import java.io.File;
import java.util.*;

import javax.imageio.ImageIO;
/**
 * 
 * @author DaneJensen
 *
 */
public class SponserImage extends Component{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int width,height;
	
	protected Image image;
	
	private String filePath = "Images/";
	
	private ArrayList<Image> images = new ArrayList<Image>();
	
	protected SponserImage(){
		
		filePath = "";
		
	}

	public SponserImage(String FileName){
		
		File temp = new File(filePath);
		
		if(!(temp.exists())){
		
			temp.mkdir();
	
		}
		
		File imageFile = new File(filePath + FileName);
		
		try{
		
			//images.add(ImageIO.read(imageFile));
			
			image = ImageIO.read(imageFile);
	
		}catch(Exception e){
			
			System.out.println(e.toString());
			
		}
		
		//changeImage(0);
		
	}
	@Override
	public void paint(Graphics g){
		
		setBackground(Color.white);
		
		g.drawImage(image, 0, 0, null);
		
	}
	
	
	
	public void setImageSize(int width, int height){
		
		this.width = width;
		
		this.height = height;
		
	}
	
	@Override
	public String toString(){
		
		String[] temp = new String[3];
		
		temp[0] = "Width: " + width;
		
		temp[1] = "Height: " + height;
		
		temp[2] = "File Path " + filePath;
		
		return Arrays.toString(temp);
		
	}
	
	public void addNewImage(String fileName){
		
		File imageFile = new File(filePath + fileName);
		
		try{
		
			images.add(ImageIO.read(imageFile));
	
		}catch(Exception e){
			
			System.out.println(e.toString());
			
		}
		
	}
	
	public int getImageWidth(){
		
		return image.getWidth(null);
		
	}
	
	public int getImageHeight(){
		
		return image.getHeight(null);
		
	}
	
	private void changeImage(int i){
		
		try{
			
			Thread.sleep(15000);
			
		}catch(Exception e){
			
			System.err.println(e.getStackTrace());
			
		}
		
		if(images.size() != 0 || images.size() == i){
			
			i = 0;
			
		}
		
		image = images.get(i);
		
		changeImage(i + 1);
		
		repaint();
		
	}
	
}
