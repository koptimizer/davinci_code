import java.util.*;
import java.lang.*;
import java.io.*;


class DavinciAi{
	int hand[] = new int[4]; // �ʱ� �ڵ� �ʵ�
	static int correct = 0; // ���𼺰� Ƚ��
	int turn = 0; // ������ Ƚ��
	String initial = new String(); // txt ���Ͽ� ���� �� �̴ϼ� �ʵ� 
	
	boolean setInitial(){ // �̴ϼ��� ������ ���� �޼ҵ�
		if(initial.equals("")){
			return false;
		}
		else{
			return true;
		}
	}
	
	void sumHand(int[]a){ // ��Ʈ�� �ֱ� ���ؼ� �ڵ尪�� ���� ����ϴ� �޼ҵ�
		int result = 0;
		int maxmin = 0;
		for(int i = 0 ; i<a.length ; i++){
			result +=a[i];
		}
		maxmin = a[a.length-1] - a[0];
		
		
		System.out.println("��� ī���� ���� "+result+" �̰�, ���� ū ���� ���� ���� ���� "+maxmin+" �Դϴ�.");
	}
	
	boolean isSame(int[]a, int b){ // �ʱ��п� ��ġ�� ���� ������ �ϴ� �޼ҵ�
		boolean isS = false;
		for(int i = 0 ; i<a.length ; i++){
			if(a[i] ==b){
				isS=true;
				break;
			}
		}
		return isS;
		
	}
	
	void makeHand(){ // isSame�� �̿��Ͽ� �ʱ� �п� 1~11������ �����ϰ� ��ġ�� �ʴ� 4���� �и� �����ϴ� �޼ҵ�
		int count = 0;
		while(true){
			int b =(int)(Math.random()*11)+1;
			if(! isSame(hand,b)){
				hand[count++]=b;
			}
			if(count==4){
				break;
			}
		}
		Arrays.sort(hand);
	}
	
	void printHand(){ // �����ڽ���� ��� �� ����Ʈ �޼ҵ�
		System.out.println(hand[0]+","+hand[1]+","+hand[2]+","+hand[3]);
	}
	
	boolean matchHand(int a[], int b, int c){ // �ڽ��� ������ ���ڿ� computer�� �и� �����ϰ�, �¸� �� �й� �׸��� ������ �Ǻ��ϰ�, txt�� ������ ���ִ� �޼ҵ�
			boolean match;
			String strstr = "";
			if(a[b-1]==c){
				match = true;
				++correct;
			}
			else{
				match = false;
			}
			if(correct == 4){ //���� �� 4���� �����ϸ� �¸�
				String won = "";
				System.out.printf("����� �¸��Դϴ�! %d������ �¸��ϼ˽��ϴ�. %d����Դϴ�.\n�д� ",turn, (turn-3));
				for(int i = 0; i < hand.length; i++){
					if(i == hand.length-1){
						won += Integer.toString(hand[i]) + "�̾����ϴ�.";
					}
					else{
						won += Integer.toString(hand[i]) + ",";
					}
				}
				try{
					FileReader old = new FileReader("Hand.txt");
					String str ="";
					int x;
					while( (x= old.read())!=-1){
						str += (char)x;
					}
					old.close();
					str += "\r\n"; // txt ���Ͽ� ����� \r\n...
					strstr = str + initial + "�� ����� "+ Integer.toString(turn-3)+" �̰�, �д� "+won;
					FileWriter in = new FileWriter("Hand.txt");
					in.write(strstr);
					in.close();
				}catch(Exception e){
					System.out.println("���� �� ������ �߻��Ͽ����ϴ� !");
				}
				System.out.println(won);
				System.exit(0);
			}
			if(hand.length == 9){ // ������ ���з� ����� �а� 9���� �Ǹ� ����
				String lose = "";
				System.out.println("����� �й��Դϴ�! \n�д� ");
				for(int i = 0 ; i<hand.length;i++ ){
					if(i == hand.length-1){
						lose += Integer.toString(hand[i]) + "�̾����ϴ�...";
					}
					else{
						lose += Integer.toString(hand[i]) + ",";
					}
				}
				try{
					FileReader old = new FileReader("Hand.txt");
					String str ="";
					int x;
					while( (x= old.read())!=-1){
						str += (char)x;
					}
					old.close();
					str += "\r\n";
					strstr = str + initial + "��(��) �й� �߰�, �д� "+lose;
					FileWriter in = new FileWriter("Hand.txt");
					in.write(strstr);
					in.close();
				}catch(Exception e){
					System.out.println("���� �� ������ �߻��Ͽ����ϴ� !");
				}
				System.out.println(lose);
				System.exit(0);
			}
			return match;
	}
	
	void addHandAdmin(int a[]){ // �����ڽ���� ��������г�Ƽ�� ��� �п� �� ���ǿ� ���� ���̵��� �°� �и� �߰��ϴ� �޼ҵ�
		int length = a.length; 
		int newHand [] = new int[a.length+1];
		int ran = (int)((Math.random()*11)+1);
		int addcount = 0;
		boolean same = false;
		String resultStr = "";
		System.out.printf("����� �а� ���� �þ �� %d���� �Ǿ����ϴ�.",length+1);
		for(int i = 0;i<length+1;i++){
			if(i == length){
				for(int j = 0; j<length;j++){
					if(newHand[j] == ran){
					ran = (int)((Math.random()*11)+1);
					}
					else{
						newHand[i] = ran;
					}
				}
			}
			else{
				newHand[i] = a[i];
			}
		}
		Arrays.sort(newHand);
		hand = newHand;
		sumHand(newHand);
		for(int i =0; i<newHand.length; i++){ // �����ڿ� �޼ҵ�
			if(i == newHand.length-1){
				resultStr += Integer.toString(newHand[i]);
			}
			else{
			resultStr += Integer.toString(newHand[i])+",";
			}
		}
		System.out.println(resultStr);
	}
	
	void addHand(int a[]){ // �����ڿ� ��������г�Ƽ�� ��� �п� �� ���ǿ� ���� ���̵��� �°� �и� �߰��ϴ� �޼ҵ�
		int length = a.length; 
		int newHand [] = new int[a.length+1];
		int ran = (int)(Math.random()*11)+1;
		int addcount = 0;
		String resultStr = "";
		
		System.out.printf("����� �а� ���� �þ �� %d���� �Ǿ����ϴ�.",length+1);
		
		for(int i = 0;i<length+1;i++){
			if(i == length){
				for(int j = 0; j<length;j++){
					if(newHand[j] == ran){
					ran = (int)((Math.random()*11)+1);
					}
					else{
						newHand[i] = ran;
					}
				}
			}
			else{
				newHand[i] = a[i];
			}
		}
		Arrays.sort(newHand);
		hand = newHand;
		sumHand(newHand);
	}
}

public class Davinci_Code_Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DavinciAi ai = new DavinciAi();
		ai.makeHand();
		
		int rule; // �� ���� ���� Ȯ�� �Ű�ü
		int mode; // ��� ���� �Ű�ü
		Scanner scan = new Scanner(System.in);
		
		System.out.println("______DAVINCI_CODE_GAME______");
		System.out.println("��Ģ�� ���ðڽ��ϱ�? ��Ģ�� �����Ϸ��� 0�� �Է��ϼ���,");
		rule = scan.nextInt();
		if(rule == 0){
			System.out.println("1. ���� 0~11�� �����ִ� ī���� 4���� ���Ƿ� �޽��ϴ�.");
			System.out.println("2. �׸��� �� ī��� ���� �ٸ� ī�尡 ũ������� ���ʿ��� ���������� ��ġ�˴ϴ�.(ex.1,2,3,4)");
			System.out.println("3. ��ġ �� ������ ī���������� (ī����� �հ� ���� ū��-�������� �����) ǥ�õ˴ϴ�.");
			System.out.println("4. �غ� ���� ��, �ڽ��� ������ �� �� �ֽ��ϴ�. ������ ���ʿ��� �� ��° ī�������� ���� ���������� �����մϴ�.");
			System.out.println("5. ���� ������ �����ߴٸ� correct�� 1�� ����ϴ�. �׸��� �̾ ������ ��� �� �� �ֽ��ϴ�.");
			System.out.println("6. ���� ������ �����ߴٸ� ��� �п� �ߺ� �� ���� �ִ� ī�尡 �ϳ� �� ���̰� �ǰ�, ���Ӱ� ī���������� ǥ���մϴ�. �׸��� �̾ ������ ��� �� �� �ֽ��ϴ�.");
			System.out.println("7. correct ī��Ʈ�� 4�� ������ ���ӿ��� �¸��ϸ�, �ڽ��� ������ ǥ�õǰ�, Hand.txt�� �����˴ϴ�.");
			System.out.println("8. ����� ī�尡 9���� �ȴٸ�, ���ӿ��� �й��ϸ�, �й����� Hand.txt�� �����˴ϴ�.");
		}
		
		System.out.println("����� �̴ϼ��� �����ּ���.");
		Scanner scaninit = new Scanner(System.in);
		String initials = scaninit.nextLine();
		if(initials.equals("")){
			System.out.println("����� �̴ϼ��� �Է����� �ʾ����Ƿ� ����� �̴ϼ��� admin���� �����մϴ�.");
			ai.initial = "admin";
		}
		else{
			System.out.println("����� �̴ϼ��� "+initials+" �Դϴ�.");
			ai.initial = initials;
		}
		
		System.out.println("1p ���� �����ڸ���� � ��带 �����մϱ�?(1p mode = 1, admin = any key)");
			mode = scan.nextInt();
			int order;
			int declare;
			if(mode == 1){ // ��� ������ 1�ϰ�� �����ڿ�������� ����
				ai.sumHand(ai.hand);
				do{
					boolean check;
					System.out.println("���� �� ������ ������.(���ʺ��� �����ʼ��Դϴ�.)");
					order = scan.nextInt();
					System.out.println("���� �� ���ڸ� ������.(1~11)");
					declare = scan.nextInt();
					++ai.turn;
				
					check = ai.matchHand(ai.hand, order, declare);
					if(check == true){
						System.out.printf("����� ������ �¾ҽ��ϴ�. %d��° ���ڴ�  %d�����ϴ�.",order,declare);
					}
					else{
						System.out.println("Ʋ�Ƚ��ϴ�.");
						ai.addHand(ai.hand);
					}
				}while(true);
			}
			else{ // ��� ������ �� ���� ��� �����ڿ�������� ����
				ai.printHand();
				ai.sumHand(ai.hand);
				do{
					boolean check;
					System.out.println("���� �� ������ ������.(���ʺ��� �����ʼ��Դϴ�.)");
					order = scan.nextInt();
					System.out.println("���� �� ���ڸ� ������.(1~11)");
					declare = scan.nextInt();
					++ai.turn;
			
					check = ai.matchHand(ai.hand, order, declare);
					if(check == true){
						System.out.printf("����� ������ �¾ҽ��ϴ�. %d��° ���ڴ�  %d�����ϴ�.",order,declare);
					}
					else{
						System.out.println("Ʋ�Ƚ��ϴ�.");
						ai.addHandAdmin(ai.hand);
					}
				}while(true);
			}

	}
}