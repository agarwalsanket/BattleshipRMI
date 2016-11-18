/* 
 * BattleshipClient.java 
 * 
 * Version: 
 *     $Id$
 * 
 * Revisions: Initial Version
 *    
 */
/**
 * BattleshipClient class handling the client logic for the game.
 *
 * @author Sanket Agarwal
 */
import java.rmi.Naming;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BattleshipClient {
	BattleshipInterface obj= null;
	Scanner sc = null;
	int playerIdentity = 0;
	String name = "";
	boolean isFillingDone = false;
	Player p1 = null;
	Player p2= null;
	boolean oponents_chance = false;

	public BattleshipClient(){
		sc =  new Scanner(System.in);
		try{
			obj = (BattleshipInterface)Naming.lookup("//localhost:12345/BattleshipImplementationobj");
		}
		catch(Exception e){
			System.out.println("Exception in lookup"+e.getMessage());
			e.printStackTrace();
		}
		try{
			playerIdentity=obj.track();
			if(playerIdentity == 999){
				System.out.println("Only two players allowed to play!");
				System.exit(1);
			}
		}
		catch(Exception e){
			System.out.println("Exception in track"+e.getMessage());
			e.printStackTrace();
		}
	}

	public int intInput(){
		int value = 0;
		try{
			value = sc.nextInt();
		}
		catch(InputMismatchException e){
			System.out.println("Exception in int input taken: "+ e.getMessage());
			e.printStackTrace();

		}

		return value;
	}
	public String strInput(){
		String value = "";
		try{
			value = sc.next();
		}
		catch(InputMismatchException e){
			System.out.println("Exception in str input taken: "+ e.getMessage());
			e.printStackTrace();

		}

		return value;
	}

	public void nameInput(){
		System.out.println("-------------------The BAttleship Game-------------------");
		System.out.println("Enter your name : ");
		name  = strInput();
		try{
			obj.initializePlayer(playerIdentity, name);
		}

		catch(Exception e){
			System.out.println("Exception in initializing players :"+ e.getMessage());
			e.printStackTrace();
		}


	}

	public void fleetArrangementInput(){
		String shipStr = "";
		int posRow = 0;
		int posCol = 0;
		String orientation = "";
		int k = 4;
		while(k > 0){


			if(k == 4){
				shipStr = "carrier";
				System.out.println("Enter the location for Carrier");
			}
			else if(k == 3){
				shipStr = "battleship";
				System.out.println("Enter the location for Battleship");
			}
			else if(k == 2){
				shipStr = "cruiser";
				System.out.println("Enter the location for Cruiser");
			}
			else if(k == 1){
				shipStr = "destroyer";
				System.out.println("Enter the location for Destroyer");
			}
			else
			{

				System.out.println("This is not a ship");
				continue;

			}

			System.out.println("Enter the row you want to place your ship(0-9) ");
			try {

				posRow  = sc.nextInt();

			}
			catch(InputMismatchException ex){
				System.out.println("Error"+ ex);
				System.out.println("Enter a number");


			}
			System.out.println("Enter the column you want to place your ship(0-9) ");
			try {

				posCol  = sc.nextInt();

			}
			catch(InputMismatchException ex){
				System.out.println("Error"+ ex);
				System.out.println("Enter a number");

			}
			System.out.println("Enter the orientation of the ship(v/h)");

			try {

				orientation = sc.next();

			}
			catch(InputMismatchException ex){
				System.out.println("Error"+ ex);
				System.out.println("Enter a character");

			}

			while(!orientation.contentEquals("h") && !orientation.contentEquals("v") ){
				System.out.println("Enter either h or v");
				try {

					orientation = sc.next();

				}
				catch(InputMismatchException ex){
					System.out.println("Error"+ ex);
					System.out.println("Enter a character");

				}
			}
			try{
				isFillingDone =  obj.fleetArrangement(shipStr, posRow,posCol,orientation,playerIdentity );
			}
			catch(Exception e){
				System.out.println("Exception in fleeArrangement call "+e.getMessage());
				e.printStackTrace();
			}


			if(isFillingDone==false){
				try{
					String errMessage = obj.errMessage();
					System.out.println(errMessage);
				}
				catch(Exception e){
					System.out.println("Exception in error message print call "+e.getMessage());
					e.printStackTrace();
				}

				continue;
			}
			//p1.sumSize = f.counterSize;
			k--;
		}
	}



	public void playingInputs(){
		int rowTemp = 0;
		int colTemp = 0;
		int p1Counter = 0;
		int p2Counter = 0;
		while(p1Counter < 14 && p2Counter < 14){

			System.out.println(name+": enter your guess for your oponent's ocean"); 
			System.out.println("feed row # then press enter and then feed column # :");


			try {

				rowTemp = sc.nextInt();

			}
			catch(InputMismatchException ex){
				System.out.println("Error"+ ex);
				System.out.println("Enter a number");

			}

			while(rowTemp > 9){
				System.out.println("Enter a row number more than 0 and less than or equal to 9");
				try {

					rowTemp = sc.nextInt();

				}
				catch(InputMismatchException ex){
					System.out.println("Error"+ ex);
					System.out.println("Enter a number");

				}
			}

			try {

				colTemp = sc.nextInt();

			}
			catch(InputMismatchException ex){
				System.out.println("Error"+ ex);
				System.out.println("Enter a number");

			}
			while(colTemp > 9){
				System.out.println("Enter a column number more than 0 and less than or equal to 9");
				try {

					colTemp = sc.nextInt();

				}
				catch(InputMismatchException ex){
					System.out.println("Error"+ ex);
					System.out.println("Enter a number");

				}
			}
			try{
				oponents_chance = obj.oponentsChance();
			}
			catch(Exception e){
				System.out.println("Exception in oponent_chance "+e.getMessage());
				e.printStackTrace();
			}
			if(playerIdentity==1){
				try {

					p1Counter = obj.PlayGAme(playerIdentity,rowTemp,colTemp);
					if(p1Counter>= 999){
						System.out.println("Sorry "+name+", You Lost! Better luck next time!");
						System.exit(1);
					}
					oponents_chance = obj.oponentsChance();
					System.out.println(obj.errMessage());
					//hit = obj.hitOrMiss();
					/* if(hit == true){
					 System.out.println("This was a hit!");

				 }
				 else{
					 System.out.println("This was a miss!");
				 }*/
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(oponents_chance == true){
					System.out.println("This is your oponent's chance! Please wait!");
				}
				while(oponents_chance){

					try{
						oponents_chance = obj.oponentsChance();
					}
					catch(Exception e){
						System.out.println("Exception in oponents_chance "+e.getMessage());
						e.printStackTrace();
					}

				}
			}
			else{
				try {

					p2Counter = obj.PlayGAme(playerIdentity,rowTemp,colTemp);
					if(p2Counter>= 999){
						System.out.println("Sorry "+name+", You Lost! Better luck next time!");
						System.exit(1);
					}
					System.out.println(obj.errMessage());
					oponents_chance = obj.oponentsChance();
					/*hit = obj.hitOrMiss();
				if(hit==true){
					 System.out.println("This was a hit!");
				}
				else{
					 System.out.println("This was a miss!");
				 }*/
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println("This is you oponent's chance! Please wait!");
				while(!oponents_chance){
					try{
						oponents_chance = obj.oponentsChance();
					}
					catch(Exception e){
						System.out.println("Exception in oponents_chance "+e.getMessage());
						e.printStackTrace();
					}

					//System.out.println("This is your oponent's chance! Please wait!");
				}
			}
		}
		if(p1Counter > 13){
			System.out.println("Congrats "+name+"! you won" );
		}
		else if(p2Counter > 13){
			System.out.println("Congrats "+name+"! you won" );
		}
	}


	public void printOcean(){
		try{
			obj.printOcean(playerIdentity);
		}
		catch(Exception e){
			System.out.println("Exception while printing "+e.getMessage());
			e.printStackTrace();
		}

	}

	public static void main(String[]  args){
		BattleshipClient b = new BattleshipClient();
		b.nameInput();
		b.fleetArrangementInput();
		b.printOcean();
		b.playingInputs();

	}
}
