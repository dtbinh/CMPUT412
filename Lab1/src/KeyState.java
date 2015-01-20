
public class KeyState {

	private boolean keyPressed;
	private boolean keyChanged;
	private char key;
	private int code;
	private long initCheck=-1;
	private long lastCheck=-1;
	private long waitTime = 300;
	
	private boolean outPressed;

	
	public KeyState(char key){
		keyPressed=false;
		keyChanged=false;
		outPressed = false;
		code=-1;
		timeChecker();
		this.key=key;
	}
	
	public void check(boolean pressed, char key){
		if(this.key==key){
			outPressed = pressed;
			
			if(outPressed){
				if(!keyPressed){
					keyChanged=true;
					keyPressed=true;
					initCheck=System.currentTimeMillis();
					lastCheck=initCheck;
					try {
						Thread.sleep(waitTime+100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					timeChecker();
					
				}else{
					lastCheck=System.currentTimeMillis();
				}
			}
		}
	}
	
	public void timeChecker() {
		Thread thread = new Thread() {
			public void run() {
				while (keyPressed) {
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
					}
					
					if (lastCheck - initCheck > waitTime) {
						if (!outPressed) {
							keyPressed = false;
							keyChanged = true;
						}
						
					} else if(lastCheck - initCheck == 0){
						keyPressed = false;
						keyChanged = true;
					}else {
						initCheck = lastCheck;
					}
				}
			}
		};
		thread.start();
	}
	
	
	public boolean isChanged(){
		return keyChanged;
	}
	
	public boolean isPressed(){
		keyChanged = false;
		return keyPressed;
	}
	
	public void printChangeState(){
		if(keyChanged){
			if (isPressed()){
				System.out.println("Pressed "+key);
			}else{
				System.out.println("Released "+key);
			}
			
		}
	}
	
	
}
