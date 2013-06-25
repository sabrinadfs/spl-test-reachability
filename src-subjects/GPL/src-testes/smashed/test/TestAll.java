package smashed.test;

import smashed.Main;



public class TestAll {
	
  public static void main(String[] args) {
    runTest(Integer.parseInt(args[0]));
  }

	
	/**
	 * Run each test according to the number.
	 */
	public static void runTest(int test) {
		switch(test){
			case 1:
				test1();
				break;
			case 2:
				test2();
				break;
			case 3:
				test3();
				break;
			case 4:
				test4();
				break;
			case 5:
				test5();
				break;
			case 6:
				test6();
				break;
			case 7:
				test7();
				break;
			case 8:
				test8();
				break;
			case 9:
				test9();
				break;
			default:
				throw new RuntimeException("This test does not exist");
		}
	}
	
	public static void test1() {
		Main.main(new String[]{"../GPL/gpl-20-network-benchmark.txt", "v0"});
	}
	
	public static void test2() {
		Main.main(new String[]{"../GPL/gpl-4-network-benchmark.txt", "v0"});
	}
	
	public static void test3() {
		Main.main(new String[]{"../GPL/gpl-tree-3-benchmark.txt", "v0"});		
	}
	
	public static void test4() {
		Main.main(new String[]{"../GPL/gpl-tree-4-benchmark.txt", "v0"});
	}
	
	public static void test5() {
		Main.main(new String[]{"../GPL/random1-gpl-benchmark.txt", "v0"});
	}
	
	public static void test6() {
		Main.main(new String[]{"../GPL/random2-gpl-benchmark.txt", "v0"});
	}
	
	public static void test7() {
		Main.main(new String[]{"../GPL/random3-gpl-benchmark.txt", "v0"});
	}
	
	public static void test8() {
		Main.main(new String[]{"../GPL/random4-gpl-benchmark.txt", "v0"});
	}
	
	public static void test9() {
		Main.main(new String[]{"../GPL/random5-gpl-benchmark.txt", "v0"});
	}
	
	
}
			
			