public class myLine {
	private int oldX, oldY, newX, newY;
	private int length;
	private boolean hasLength = false;
	private int connectedPoint1, connectedPoint2;
	public myLine(int x0,int y0,int x1,int y1){
		oldX = x0;
		oldY = y0;
		newX = x1;
		newY = y1;
	}
	
	public void setOldX(int x) {
		oldX = x;
	}
	public void setOldY(int y) {
		oldY = y;
	}
	public void setNewX(int x) {
		newX = x;
	}
	public void setNewY(int y) {
		newY = y;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public void setHaveLength(boolean newHasLength) {
		hasLength = newHasLength;
	}
	public void setPoint1(int numberOfPoint) {
		connectedPoint1 = numberOfPoint;
	}
	public void setPoint2(int numberOfPoint) {
		connectedPoint2 = numberOfPoint;
	}
	
	
	public int getCenterX() {
		if(oldX < newX)
			return oldX + Math.abs(oldX-newX)/2;
		else
			return newX + Math.abs(oldX-newX)/2;
	}
	public int getCenterY() {
		if (oldY < newY)
			return oldY + Math.abs(oldY-newY)/2;
		else
			return newY + Math.abs(oldY-newY)/2;
	}
	public int getOldX() {
		return oldX;
	}
	public int getOldY() {
		return oldY;
	}
	public int getNewX() {
		return newX;
	}
	public int getNewY() {
		return newY;
	}
	public int getLength() {
		return length;
	}
	public boolean haveLength() {
		return hasLength;
	}
	public int getPoint1() {
		return connectedPoint1;
	}
	public int getPoint2() {
		return connectedPoint2;
	}
}
