
public class trycatchtest {

	public static void main(String[] args) 
	{
		try
		{
			int[] nums = new int[4];
			nums[5] = 8;
			System.out.println("Shouldn't be here!");
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("Stuff!");
		}
		System.out.println("Other stuff!");
	}

}
