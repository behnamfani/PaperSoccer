import static java.lang.Math.max;
import static java.lang.Math.min;

public class Bot {

    double jGoal = (PaperSoccer.m1Goal + PaperSoccer.m2Goal)/2;
    int iGoal = 0;
    int iToop = PaperSoccer.itoop;
    int jToop = PaperSoccer.jtoop;
    Dot map[][] = PaperSoccer.map;
    public static  int action = 0;
    public static int minMaxCount = 0;
    
    static double power(double a){
        return a*a;
    }

    public double distance(int iBall, int jBall){
        double distance = power(iBall - iGoal) + power(jBall - jGoal);
        return distance;
    }

    // B is bot(Max) and A is player(Min)
    public double miniMax(Dot location, String player, int iBall, int jBall){
    	
        minMaxCount++;
        
        // Adhoc
        if(minMaxCount > 30){
        	return distance(iBall, jBall);
        }
        if( iBall == 0 || iBall == map.length - 1) {
        	return 0;
        }
        int[] forbidenNeighbors = new int[8];
//        forbidenNeighbors = location.forbidenNeighbors();
        int check = 0, checkA = 0;
//        for(int w = 0; w < 8; w++) {
//        	if(forbidenNeighbors[w] == 1) {
//        		check++;
//        		checkA = w;
//        	}
//        }
//        if(check == 1) {
//        	action = checkA;
//        	return distance(iBall, jBall);
//        }
//        if(minMaxCount == 1) {
        	for(int w = 0; w < 8; w++) {
            	forbidenNeighbors[w] = 0;
            }
            for(int w = 0; w < TextArea.available.size(); w++) {
            	String teeeemp = TextArea.available.get(w);
            	if(teeeemp.equals("1")) {
            		forbidenNeighbors[0] = 1;
            	}
            	if(teeeemp.equals("2")) {
            		forbidenNeighbors[1] = 1;
            	}
            	if(teeeemp.equals("3")) {
            		forbidenNeighbors[2] = 1;
           	}
            	if(teeeemp.equals("4")) {
            		forbidenNeighbors[3] = 1;
            	}
            	if(teeeemp.equals("5")) {
            		forbidenNeighbors[4] = 1;
            	}
            	if(teeeemp.equals("6")) {
            		forbidenNeighbors[5] = 1;
            	}
            	if(teeeemp.equals("7")) {
            		forbidenNeighbors[6] = 1;
            	}
            	if(teeeemp.equals("8")) {
            		forbidenNeighbors[7] = 1;
            	}
            }
            check = 0;
            checkA = 0;
            for(int w = 0; w < 8; w++) {
            	if(forbidenNeighbors[w] == 1) {
            		check++;
            		checkA = w;
            	}
            }
            if(check == 1) {
            	action = checkA;
            	return distance(iBall, jBall);
            }
//        }
        
        if( jBall == 0 || jBall == map[0].length - 1) {
        	for(int w = 0; w < 8; w++) {
        		if(forbidenNeighbors[w] == 1) {
        			action = w;
        			break;
        		}
        	}
        	return distance(iBall, jBall);
        }
        
        
        
        double Dists[] = new double[8];
        for(int l=0; l<8 ; l++){
        	Dists[l] = (int) 1e9;
        }
        
        boolean visiteds[] = new boolean[8];
        for(int i =0; i < 8; i++) {
        	visiteds[i] = false;
        }
        
        int visitedCount = 0;
        double v = 0;
        int j=0;
        
        // MAX
        if(player.equals("B")){
        	
            boolean meetOthers = true;
            
            // Check up nodes:
            while (j < 3) {
            	
                if(forbidenNeighbors[j] == 1){
                	
                	meetOthers = false;
                    visiteds[j] = map[iBall-1][jBall + j - 1].visited;
                    if(visiteds[j]){
                    	visitedCount++;
                    }
                    v = miniMax(PaperSoccer.map[iBall-1][jBall + j - 1], "A", iBall-1, jBall + j - 1);
                    Dists[j] = min(Dists[j], v);
                    
                }
                j++;
                
            }
            // Check which value for upper nodes must return:
            if( !meetOthers ){

                if(visitedCount == 3 || visitedCount==0){
                	double minimum = (double)1e9;
                	for(int k = 0; k < 3; k++) {
                		if(Dists[k] < minimum) {
                			minimum = Dists[k];
                			action = k;
                		}
                	}
                	return minimum;
                }
                else if( visitedCount == 1 ){
                    for(int k=0 ; k<=2 ; k++){
                        if( visiteds[k] ){
                        	action = k;
                        	return Dists[k];
                        }
                    }
                }
                else if( visitedCount == 2 ){
                    if( visiteds[0] && visiteds[1] && !visiteds[2] ){
                        if(Dists[0] < Dists[1]){
                        	action = 0;
                        	return Dists[0];
                        }
                        else{
                        	action = 1;
                        	return Dists[1];
                        }
                    }
                    if(!visiteds[0] && visiteds[1] && visiteds[2]){
                        if(Dists[1]<Dists[2]){
                        	action=1;
                        	return Dists[1];
                        }
                        else{
                        	action = 2;
                        	return  Dists[2];
                        }
                    }
                    if(visiteds[0] && !visiteds[1] && visiteds[2]){
                        if(Dists[0] < Dists[2]){
                        	action = 0;
                        	return Dists[0];
                        }
                        else {
                        	action = 2;
                        	return Dists[2];
                        }
                    }
                }
            }
            // Upper nodes aren't available
            // Check for side nodes
            else{
                meetOthers = true;
                if(forbidenNeighbors[3] == 1 || forbidenNeighbors[4] == 1) {
                    meetOthers = false;
                    if (forbidenNeighbors[3] == 1 && forbidenNeighbors[4] == 1) {
                        if (visiteds[3] && visiteds[4] || !visiteds[3] && !visiteds[4]) {
                            double v1 = miniMax(PaperSoccer.map[iBall][jBall + 1], "A", iBall, jBall + 1);
                            double v2 = miniMax(PaperSoccer.map[iBall][jBall - 1], "A", iBall, jBall - 1);
                            if(v1<v2){
                            	action = 4;
                            	return v1;
                            }
                            else{ 
                            	action= 3;
                            	return v2;
                            }
                        }
                        if (visiteds[3] && !visiteds[4]) {
                            action = 3;
                            return miniMax(PaperSoccer.map[iBall][jBall - 1], "A", iBall, jBall - 1);
                        }
                        if (!visiteds[3] && visiteds[4]) {
                            action = 4;
                            return miniMax(PaperSoccer.map[iBall][jBall + 1], "A", iBall, jBall + 1);
                        }
                    }
                }
                // Left and Right nodes aren't available so we must check down nodes
                if (meetOthers){
                    if(forbidenNeighbors[5] == 0 && forbidenNeighbors[6] == 0 && forbidenNeighbors[7] == 0){
                    	// Equal:
                    	action = 5;
                        return distance(iBall - 1, jBall - 1);
                    }
                    else{
                        for(int k=5 ; k<8 ; k++){
                            if(forbidenNeighbors[k] == 1 && visiteds[k]){
                                action = k;
                                return distance(iBall + 1, jBall + 1);
                            }
                        }
                        for(int k=5 ; k<8 ; k++){
                            if(forbidenNeighbors[k] == 1){
                                action = k;
                                return distance(iBall + 1, jBall + 1);
                            }
                        }
                    }

                }

            }


        }
        // MIN
        else if(player.equals("A")){
        	for(int l=0; l<8 ; l++){
            	Dists[l] = (int) -1e9;
            }
        	
            boolean meetOthers = true;
            
            // Check up nodes:
            while (j < 3) {
            	
                if(forbidenNeighbors[j] == 1){
                	
                	meetOthers = false;
                    visiteds[j] = map[iBall-1][jBall + j - 1].visited;
                    if(visiteds[j]){
                    	visitedCount++;
                    }
                    v = miniMax(PaperSoccer.map[iBall-1][jBall + j - 1], "B", iBall-1, jBall + j - 1);
                    Dists[j] = max(Dists[j], v);
                    
                }
                j++;
                
            }
            // Check which value for upper nodes must return:
            if( !meetOthers ){

                if(visitedCount == 3 || visitedCount==0){
                	double maximum = (double)-1e9;
                	for(int k = 0; k < 3; k++) {
                		if(Dists[k] > maximum) {
                			maximum = Dists[k];
                			action = k;
                		}
                	}
                	return maximum;
                }
                else if( visitedCount == 1 ){
                    for(int k=0 ; k<=2 ; k++){
                        if( visiteds[k] ){
                        	action = k;
                        	return Dists[k];
                        }
                    }
                }
                else if( visitedCount == 2 ){
                    if( visiteds[0] && visiteds[1] && !visiteds[2] ){
                        if(Dists[0] > Dists[1]){
                        	action = 0;
                        	return Dists[0];
                        }
                        else{
                        	action = 1;
                        	return Dists[1];
                        }
                    }
                    if(!visiteds[0] && visiteds[1] && visiteds[2]){
                        if(Dists[1] > Dists[2]){
                        	action=1;
                        	return Dists[1];
                        }
                        else{
                        	action = 2;
                        	return  Dists[2];
                        }
                    }
                    if(visiteds[0] && !visiteds[1] && visiteds[2]){
                        if(Dists[0] > Dists[2]){
                        	action = 0;
                        	return Dists[0];
                        }
                        else {
                        	action = 2;
                        	return Dists[2];
                        }
                    }
                }
            }
            // Upper nodes aren't available
            // Check for side nodes
            else{
                meetOthers = true;
                if(forbidenNeighbors[3] == 1 || forbidenNeighbors[4] == 1) {
                    meetOthers = false;
                    if (forbidenNeighbors[3] == 1 && forbidenNeighbors[4] == 1) {
                        if (visiteds[3] && visiteds[4] || !visiteds[3] && !visiteds[4]) {
                            double v1 = miniMax(PaperSoccer.map[iBall][jBall + 1], "B", iBall, jBall + 1);
                            double v2 = miniMax(PaperSoccer.map[iBall][jBall - 1], "B", iBall, jBall - 1);
                            if(v1 > v2){
                            	action = 4;
                            	return v1;
                            }
                            else{ 
                            	action= 3;
                            	return v2;
                            }
                        }
                        if (visiteds[3] && !visiteds[4]) {
                            action = 3;
                            return miniMax(PaperSoccer.map[iBall][jBall - 1], "B", iBall, jBall - 1);
                        }
                        if (!visiteds[3] && visiteds[4]) {
                            action = 4;
                            return miniMax(PaperSoccer.map[iBall][jBall + 1], "B", iBall, jBall + 1);
                        }
                    }
                }
                // Left and Right nodes aren't available so we must check down nodes
                if (meetOthers){
                    if(forbidenNeighbors[5] == 0 && forbidenNeighbors[6] == 0 && forbidenNeighbors[7] == 0){
                    	// Equal:
                    	action = 5;
                        return distance(iBall - 1, jBall - 1);
                    }
                    else{
                        for(int k=5 ; k<8 ; k++){
                            if(forbidenNeighbors[k] == 1 && visiteds[k]){
                                action = k;
                                return distance(iBall + 1, jBall + 1);
                            }
                        }
                        for(int k=5 ; k<8 ; k++){
                            if(forbidenNeighbors[k] == 1){
                                action = k;
                                return distance(iBall + 1, jBall + 1);
                            }
                        }
                    }

                }

            }


        }
        
        return distance(iBall, jBall);
    }
}
