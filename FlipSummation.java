package Euler566;

import java.util.List;
import java.util.ArrayList;

public class FlipSummation {
    private List<int[]> termParameters = new ArrayList<>();
    public FlipSummation(int n) {
        calculateParams(n);
    }

    private void calculateParams(int n) {
        for(int i = 9; i <= n-2; i++) {
            for(int j = i+1; j <= n-1; j++) {
                for(int k = j+1; k <= n; k++) {
                    termParameters.add(new int[]{i,j,k});
                }
            }
        }
    }

    public Long addUpFlips() {
        Long total = new Long(0);
        for(int[] params : termParameters) {
            CakeSliceFlipping cakeFlipper = new CakeSliceFlipping();
            total += cakeFlipper.countFlips(params[0],params[1],params[2]);
        }
        return total;
    }

    public List<int[]> getTermParameters() {
        return termParameters;
    }
}
