package run;

public class Start {

	private static volatile boolean running = true;
	
	public static void main(String[] args) {

		Runtime.getRuntime().addShutdownHook(new Thread(){
			@Override
			public void run() {
				System.out.println("app stoped");
			}
		});
		
		synchronized (Start.class) {
			while(running){
				try {
					Start.class.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
