import java.util.*;
import java.lang.*;
import java.io.*;


class DavinciAi{
	int hand[] = new int[4]; // 초기 핸드 필드
	static int correct = 0; // 선언성공 횟수
	int turn = 0; // 선언의 횟수
	String initial = new String(); // txt 파일에 저장 할 이니셜 필드 
	
	boolean setInitial(){ // 이니셜을 설정을 돕는 메소드
		if(initial.equals("")){
			return false;
		}
		else{
			return true;
		}
	}
	
	void sumHand(int[]a){ // 힌트를 주기 위해서 핸드값의 합을 출력하는 메소드
		int result = 0;
		int maxmin = 0;
		for(int i = 0 ; i<a.length ; i++){
			result +=a[i];
		}
		maxmin = a[a.length-1] - a[0];
		
		
		System.out.println("모든 카드의 합은 "+result+" 이고, 가장 큰 수와 작은 수의 차는 "+maxmin+" 입니다.");
	}
	
	boolean isSame(int[]a, int b){ // 초기패에 겹치는 것이 없도록 하는 메소드
		boolean isS = false;
		for(int i = 0 ; i<a.length ; i++){
			if(a[i] ==b){
				isS=true;
				break;
			}
		}
		return isS;
		
	}
	
	void makeHand(){ // isSame을 이용하여 초기 패에 1~11까지의 랜덤하고 겹치지 않는 4장의 패를 생성하는 메소드
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
	
	void printHand(){ // 관리자실행용 상대 패 프린트 메소드
		System.out.println(hand[0]+","+hand[1]+","+hand[2]+","+hand[3]);
	}
	
	boolean matchHand(int a[], int b, int c){ // 자신이 선언한 숫자와 computer의 패를 대조하고, 승리 및 패배 그리고 진행을 판별하고, txt에 저장을 해주는 메소드
			boolean match;
			String strstr = "";
			if(a[b-1]==c){
				match = true;
				++correct;
			}
			else{
				match = false;
			}
			if(correct == 4){ //선언 중 4번이 성공하면 승리
				String won = "";
				System.out.printf("당신의 승리입니다! %d번만에 승리하셧습니다. %d등급입니다.\n패는 ",turn, (turn-3));
				for(int i = 0; i < hand.length; i++){
					if(i == hand.length-1){
						won += Integer.toString(hand[i]) + "이었습니다.";
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
					str += "\r\n"; // txt 파일에 띄어쓰기는 \r\n...
					strstr = str + initial + "의 등급은 "+ Integer.toString(turn-3)+" 이고, 패는 "+won;
					FileWriter in = new FileWriter("Hand.txt");
					in.write(strstr);
					in.close();
				}catch(Exception e){
					System.out.println("저장 중 문제가 발생하였습니다 !");
				}
				System.out.println(won);
				System.exit(0);
			}
			if(hand.length == 9){ // 선언의 실패로 상대의 패가 9장이 되면 실패
				String lose = "";
				System.out.println("당신의 패배입니다! \n패는 ");
				for(int i = 0 ; i<hand.length;i++ ){
					if(i == hand.length-1){
						lose += Integer.toString(hand[i]) + "이었습니다...";
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
					strstr = str + initial + "은(는) 패배 했고, 패는 "+lose;
					FileWriter in = new FileWriter("Hand.txt");
					in.write(strstr);
					in.close();
				}catch(Exception e){
					System.out.println("저장 중 문제가 발생하였습니다 !");
				}
				System.out.println(lose);
				System.exit(0);
			}
			return match;
	}
	
	void addHandAdmin(int a[]){ // 관리자실행용 선언실패패널티로 상대 패에 각 조건에 따라 난이도에 맞게 패를 추가하는 메소드
		int length = a.length; 
		int newHand [] = new int[a.length+1];
		int ran = (int)((Math.random()*11)+1);
		int addcount = 0;
		boolean same = false;
		String resultStr = "";
		System.out.printf("상대의 패가 한장 늘어나 총 %d장이 되었습니다.",length+1);
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
		for(int i =0; i<newHand.length; i++){ // 관리자용 메소드
			if(i == newHand.length-1){
				resultStr += Integer.toString(newHand[i]);
			}
			else{
			resultStr += Integer.toString(newHand[i])+",";
			}
		}
		System.out.println(resultStr);
	}
	
	void addHand(int a[]){ // 도전자용 선언실패패널티로 상대 패에 각 조건에 따라 난이도에 맞게 패를 추가하는 메소드
		int length = a.length; 
		int newHand [] = new int[a.length+1];
		int ran = (int)(Math.random()*11)+1;
		int addcount = 0;
		String resultStr = "";
		
		System.out.printf("상대의 패가 한장 늘어나 총 %d장이 되었습니다.",length+1);
		
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
		
		int rule; // 룰 설명 여부 확인 매개체
		int mode; // 모드 설정 매개체
		Scanner scan = new Scanner(System.in);
		
		System.out.println("______DAVINCI_CODE_GAME______");
		System.out.println("규칙을 배우시겠습니까? 규칙을 열람하려면 0을 입력하세요,");
		rule = scan.nextInt();
		if(rule == 0){
			System.out.println("1. 상대는 0~11이 적혀있는 카드중 4장을 임의로 받습니다.");
			System.out.println("2. 그리고 그 카드는 서로 다른 카드가 크기순으로 왼쪽에서 오른쪽으로 배치됩니다.(ex.1,2,3,4)");
			System.out.println("3. 배치 된 다음에 카드패정보가 (카드들의 합과 가장 큰수-작은수의 결과물) 표시됩니다.");
			System.out.println("4. 준비가 끝난 후, 자신은 선언을 할 수 있습니다. 선언은 왼쪽에서 몇 번째 카드인지와 무슨 숫자인지를 선언합니다.");
			System.out.println("5. 만약 선언이 성공했다면 correct를 1개 얻습니다. 그리고 이어서 선언을 계속 할 수 있습니다.");
			System.out.println("6. 만약 선언이 실패했다면 상대 패에 중복 될 수도 있는 카드가 하나 더 섞이게 되고, 새롭게 카드패정보를 표시합니다. 그리고 이어서 선언을 계속 할 수 있습니다.");
			System.out.println("7. correct 카운트를 4개 얻으면 게임에서 승리하며, 자신의 점수가 표시되고, Hand.txt에 보관됩니다.");
			System.out.println("8. 상대의 카드가 9개가 된다면, 게임에서 패배하며, 패배기록이 Hand.txt에 보관됩니다.");
		}
		
		System.out.println("당신의 이니셜을 적어주세요.");
		Scanner scaninit = new Scanner(System.in);
		String initials = scaninit.nextLine();
		if(initials.equals("")){
			System.out.println("당신은 이니셜을 입력하지 않았으므로 당신의 이니셜은 admin으로 간주합니다.");
			ai.initial = "admin";
		}
		else{
			System.out.println("당신의 이니셜은 "+initials+" 입니다.");
			ai.initial = initials;
		}
		
		System.out.println("1p 모드와 관리자모드중 어떤 모드를 진행합니까?(1p mode = 1, admin = any key)");
			mode = scan.nextInt();
			int order;
			int declare;
			if(mode == 1){ // 모드 설정이 1일경우 도전자용실행으로 간주
				ai.sumHand(ai.hand);
				do{
					boolean check;
					System.out.println("선언 할 순번을 고르세요.(왼쪽부터 오른쪽순입니다.)");
					order = scan.nextInt();
					System.out.println("선언 할 숫자를 고르세요.(1~11)");
					declare = scan.nextInt();
					++ai.turn;
				
					check = ai.matchHand(ai.hand, order, declare);
					if(check == true){
						System.out.printf("당신의 선언은 맞았습니다. %d번째 숫자는  %d였습니다.",order,declare);
					}
					else{
						System.out.println("틀렸습니다.");
						ai.addHand(ai.hand);
					}
				}while(true);
			}
			else{ // 모드 설정이 그 외의 경우 도전자용실행으로 간주
				ai.printHand();
				ai.sumHand(ai.hand);
				do{
					boolean check;
					System.out.println("선언 할 순번을 고르세요.(왼쪽부터 오른쪽순입니다.)");
					order = scan.nextInt();
					System.out.println("선언 할 숫자를 고르세요.(1~11)");
					declare = scan.nextInt();
					++ai.turn;
			
					check = ai.matchHand(ai.hand, order, declare);
					if(check == true){
						System.out.printf("당신의 선언은 맞았습니다. %d번째 숫자는  %d였습니다.",order,declare);
					}
					else{
						System.out.println("틀렸습니다.");
						ai.addHandAdmin(ai.hand);
					}
				}while(true);
			}

	}
}