/**********************************************************
* Program	:  Entity
* Author	:  Michael Waldron
* Due Date	:  April 10th
* Description	:  Farm Simulator object super class
***********************************************************/
import java.util.Random;

public class Entity {
	// Hunger Value if it = 0 the Entity will die excluding grass
	private int Hunger;
	//Age if the Entity is 30 it will die excluding grass
	private int Age;
	// int on how far the entity can move each turn
	private int Speed;
	// variable that helps with speed and the entity's current X location
	private int dX;
	// 	// variable that helps with speed and the entity's current Y location
	private int dY;
	//Variable of the entity's next Y location
	private int Y;
	//Variable of the entity's next X location
	private int X;
	// Boolean that decides whether that animal can reproduce or not
	private boolean Fertility;
	Random rnd = new Random();
	//Constructor for an object of type Entity
	public Entity(int Hunger, int Age,int Speed,Boolean Fertility,int X, int Y){
		this.Hunger= Hunger;
		this.Age=Speed;
		this.Speed=Speed;
		this.X=X;
		this.Y=Y;
		this.Fertility=Fertility;
	}
	// allows you to set the hunger level of the entity
	public void setHunger(int Hunger){
		this.Hunger=Hunger; 
	}
	// allows you to set the Speed level of the entity
	public int getSpeed(){
		return Speed;
	}
	// allows you to set the Age of the entity
	public int getAge(){
		return Age;
	}
	// returns the entity's current hunger
	public int getHunger(){
		return Hunger;
	}
	//returns the enitiy's x value
	public int getX(){
		if (X<0) return 0;
		if (X>78){
			return 79;
		}else{
		return X;
		}
	}
	//returns the enitiy's y value
	public int getY(){
		if (Y<0) return 0;
		if (Y>58)return 59;
		else{
		return Y;
		}
	}
	// this method calculates the entit's change in position
	public void move(){
		
		dX = rnd.nextInt(Speed) - 1;
		dY = rnd.nextInt(Speed) - 1;
		this.X=dX+X;
		this.Y=dY+Y;
	}
	//setter for the field Age
	public void setAge(int Age){
		this.Age=Age;
	}
	// Getter for the boolean Fertility
	public boolean getFertility(){
	return Fertility;
}
// if the Animal is age 12< the animal is not able to reproduce
	public void setFertility(){
		if (getAge()>=12){
		Fertility=true;
		}
		else{
			Fertility=false;
		}
	}
}

