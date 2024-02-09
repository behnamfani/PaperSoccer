
public class Dot {
	boolean visited = false;
	boolean n1 = false;
	boolean n2 = false;
	boolean n3 = false;
	boolean n4 = false;
	boolean n5 = false;
	boolean n6 = false;
	boolean n7 = false;
	boolean n8 = false;
	
	public int[] forbidenNeighbors() {
		int a[] = new int[8];
		
		if(n1) {
			a[0] = 0;
		}else {
			a[0] = 1;
		}
		if(n2) {
			a[1] = 0;
		}else {
			a[1]= 1;
		}
		if(n3) {
			a[2] = 0;
		}else {
			a[2] = 1;
		}
		if(n4) {
			a[3] = 0;
		}else {
			a[3] = 1;
		}
		if(n5) {
			a[4] = 0;
		}else {
			a[4] = 1;
		}
		if(n6) {
			a[5] = 0;
		}else {
			a[5] = 1;
		}
		if(n7) {
			a[6] = 0;
		}else {
			a[6] = 1;
		}
		if(n8) {
			a[7] = 0;
		}else {
			a[7] = 1;
		}
		
		return a;
		
	}
	
	//Setters and Getters
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public boolean isN1() {
		return n1;
	}
	public void setN1(boolean n1) {
		this.n1 = n1;
	}
	public boolean isN2() {
		return n2;
	}
	public void setN2(boolean n2) {
		this.n2 = n2;
	}
	public boolean isN3() {
		return n3;
	}
	public void setN3(boolean n3) {
		this.n3 = n3;
	}
	public boolean isN4() {
		return n4;
	}
	public void setN4(boolean n4) {
		this.n4 = n4;
	}
	public boolean isN5() {
		return n5;
	}
	public void setN5(boolean n5) {
		this.n5 = n5;
	}
	public boolean isN6() {
		return n6;
	}
	public void setN6(boolean n6) {
		this.n6 = n6;
	}
	public boolean isN7() {
		return n7;
	}
	public void setN7(boolean n7) {
		this.n7 = n7;
	}
	public boolean isN8() {
		return n8;
	}
	public void setN8(boolean n8) {
		this.n8 = n8;
	}
	

}
