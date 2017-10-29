/**********************************************************
* Program	:  World
* Author	:  Michael Waldron
* Due Date	:  April 10th
* Description	:  Farm Simulator main class
***********************************************************/
// Note:
// Wolves = Red
// Sheep = Yellow/Blue
// Grass = Grass
// Note: I completed the program everything is working but the I wasn't able to successfully be able to fill previous spots in the array i made an attempt in the nill method
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class World extends JPanel implements ActionListener {
	Timer time;
	Random rnd = new Random();
	//the array that stores the main elements
	static Entity[] entities = new Entity[5000];
	//Define colours
	Color BROWN = new Color(0,0,0);
	// set sheepnum this to the amount of starting elements-1
	// sheepnum keeps track of how full the array is
	private int sheepnum=2; 
	public World() {
		// create starting Sheep, Wolves and grass entities in a loop 
		entities[2] = new Sheep(100, 20,6,true,0,0);
		entities[1] = new Grass(0, 0,6,true,10,10);
		entities[0]=new Wolf(100,20,6,true,2,3);
		time = new Timer(500, this);
		time.start();
	}
	// checks the collision if two things are on top of each other.
	// I just used this for a criteria for the grass spawn so i don't end up with grass spawning on top of each other
	public boolean collision(int x,int y){
		for(int a= 0 ; a<entities.length;a++){
			if(entities[a]!=null){
			if(entities[a].getX()==x&&entities[a].getY()==y){
				return true;
			}
			}
		}return false;
	}
	// big double for loop that checks each pair of beings in the array and sends them to the- 
	// chompers method which checks if they are on top of each other and if they are it will send them to the - 
	// nill method which makes the element being 'eaten' dissapear
	public void eat(){
		for(int x=0;x<entities.length;x++){
			for(int y=0;y<entities.length;y++){
				if(entities[x] instanceof Sheep && entities[y] instanceof Grass){
					chompers(x,y);
					if(entities[x].getHunger()>100-6){
						entities[x].setHunger(100);
					}else{
					entities[x].setHunger(entities[x].getHunger()+6);
					}
				}
				if(entities[x] instanceof Wolf && entities[y] instanceof Sheep){
					chompers(x,y);
					if(entities[x].getHunger()>100-6){
						entities[x].setHunger(100);
					}else{
					entities[x].setHunger(entities[x].getHunger()+6);
					}
				}
			}
		}
	
	}
	//this method actually makes the arrray element null
	// when it becomes null it shifts everything past that element in the array down one to the left
	// this makes the array so that the null elements are always at the end
	// the sheepnum -- is to make sure basically let the reproduce method know that there is space for more elements
	
	public void nill(int x){
		entities[x]=null;
		for(int y= x;y<4951;y++){
			entities[y]=entities[y+1];
			
		}
		sheepnum--;
	}
	// look at eat for explanation
	public void chompers(int predator, int prey){
		if(entities[predator].getX()==entities[prey].getX()&&entities[predator].getY()==entities[prey].getY()){
			nill(prey);
		}
	}
	//for loop to check all entities and roll the dice of life whether they reproduce or not
	public void reproduce(Entity...a){
		for(int x =0;x<a.length;x++){
			if(entities[x]!=null&&4950>sheepnum){
			if(a[x] instanceof Grass){
				if(a[x].getFertility()==true){
					int b=rnd.nextInt(100)+1;
						if(b<27){
							// checks if any of the to be new grass would be on top of existing grass
							// and if thats the case doesnt grow the new grass
							if(collision(a[x].getX(),a[x].getY()+1)==false){
								entities[sheepnum+1]=new Grass(100,0,0,true,a[x].getX(),a[x].getY()+1);
								sheepnum++;
							}
							if(collision(a[x].getX(),a[x].getY()-1)==false){
								entities[sheepnum+1]=new Grass(100,0,0,true,a[x].getX(),a[x].getY()-1);
								sheepnum++;
							}
							if(collision(a[x].getX()+1,a[x].getY())==false){
								entities[sheepnum+1]=new Grass(100,0,0,true,a[x].getX()+1,a[x].getY());
								sheepnum++;
							}
							if(collision(a[x].getX()-1,a[x].getY())==false){
								entities[sheepnum+1]=new Grass(100,0,0,true,a[x].getX()-1,a[x].getY());
								sheepnum++;
							}
								
							
						}
				}
			}
			if(a[x] instanceof Sheep){
				if (a[x].getFertility()==true){
					// rnd is used for the odds of the entity reproducing taking in factors such as hunger
					int k=rnd.nextInt(120-a[x].getHunger())+1;
						if(k<20){
							entities[sheepnum+1]= new Sheep(100,0,rnd.nextInt(10 )+1,false,a[x].getX()+10,a[x].getY());
							sheepnum++;
					}
				}
			}
		if(a[x] instanceof Wolf){
			if(a[x].getFertility()==true){
				// k is used for the odds of the entity reproducing taking in factors such as hunger
				int k = rnd.nextInt(150-a[x].getHunger())+1;
				if(k<20){
					entities[sheepnum+1]=new Wolf(100,0,rnd.nextInt(10)+1,false,a[x].getX()+1,a[x].getY());
					sheepnum++;
						}
					}
				}
			}
		}
	}
	// the move method that calls the move methods in the respective entity subclasses
	public void move(){
		
		//loops through all the Entity that aren't null in the entities array
		for(int x =0;x<entities.length;x++)
			if(entities[x]!=null){
				if(entities[x]instanceof Wolf)entities[x].move();
				if(entities[x]instanceof Sheep)entities[x].move();
			}
			
	}
	
	public void actionPerformed(ActionEvent e) {
	// this loop is for the essisial things that must be checked each half second
		// including increasing and checking age, checking hunger, Fertility etc
		for(int x =0;x<entities.length;x++){
			if(entities[x]!=null){
			if(!(entities[x] instanceof Grass)){
				entities[x].setAge(entities[x].getAge()+1);
				entities[x].setHunger(entities[x].getHunger()-1);
				entities[x].setFertility();
					if(entities[x]!=null){
						if(entities[x].getAge()>30)nill(x);
					}
					if(entities[x]!=null){
						if(entities[x].getHunger()<0) nill(x);
					}
			}
		
		}
		}
		reproduce(entities);
		move();
		repaint();
		eat();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(BROWN);
		g.fillRect(0, 0, 800, 600);

		//Loop through and draw all entities
		
		for(int x =0;x<entities.length;x++){
		
		if(entities[x] instanceof Sheep){
			// if the sheep are babies it gives them a cool blue colour
			if(entities[x].getAge()<12){
				g.setColor(Color.blue);
				g.fillRect(entities[x].getX()*10,entities[x].getY()*10, 10, 10);
			}else{
			g.setColor(Color.yellow);
			g.fillRect(entities[x].getX()*10,entities[x].getY()*10, 10, 10);
			}
		}
		
		if(entities[x]!=null){
			if(entities[x] instanceof Grass){
				g.setColor(Color.green);
				g.fillRect(entities[x].getX()*10,entities[x].getY()*10, 10, 10);	
				}
			if(entities[x] instanceof Wolf){
				g.setColor(Color.RED);
				g.fillRect(entities[x].getX()*10,entities[x].getY()*10, 10, 10);
				
			}
		
		}
	}
		
	}

	public static void main(String[] args) {
		World w = new World();
		JFrame frame = new JFrame("Waldron's Breeding Grounds");
		frame.setVisible(true);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(w);
		
		
	}
}
