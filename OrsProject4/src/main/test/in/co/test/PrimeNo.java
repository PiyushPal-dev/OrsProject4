package in.co.test;

public class PrimeNo {
	public static void main(String[]args){
		System.out.println("heloo");
		for(int i=1;i<=100;i++){
			boolean flag=false;
			for(int j=2;j<=i/2;j++){
				if(i/j==0){
					flag=false;
					break;
				}
			}
			if(flag==true){
				System.out.println(i);
			}
		}
	}

}
