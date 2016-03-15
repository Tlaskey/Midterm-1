import java.util.Stack;

public class PostfixEvaluator {
	public static void main(String[] args){
		//input: 1 4 * 5 + 8 - SAME AS: ((4 * 1) + 5) - 8)
		//output: 1
		Character[] l1 = {'1', '4', '*', '5', '+', '8', '-'};
		System.out.println(postfixEval(l1));
	}
	public static int postfixEval(Character[] list){
		int total = 0;
		Stack <Character> s1 = new Stack<>();
		for(int i = 0; i < list.length; i++){
			if(Character.isDigit(list[i])){
				s1.push(list[i]);
			}
			else{
				int num2 = Character.getNumericValue(s1.pop());
				if(!s1.empty()){
					int num1 = Character.getNumericValue(s1.pop());
					if(list[i].equals('*')){
						total += num1 * num2;
					}
					else if(list[i].equals('+')){
						total += num1 + num2;
					}
					else if(list[i].equals('-')){
						total += num1 - num2;
					}
					else{
						total += num1 / num2;
					}
				}
				else{
					if(list[i].equals('*')){
						total *= num2;
					}
					else if(list[i].equals('+')){
						total += num2;
					}
					else if(list[i].equals('-')){
						total -= num2;
					}
					else{
						total /= num2;
					}
				}
			}
		}
		return total;
	}
}
