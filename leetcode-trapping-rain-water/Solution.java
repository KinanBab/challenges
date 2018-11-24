// https://leetcode.com/problems/trapping-rain-water/

class Solution {
    public int trap(int[] height) {
        int maxSeen = 0;
        int maxIndex = -1;
        int trap = 0;
        
        int current, min, stop, currentMax, j;
        for(int i = 0; i < height.length; i++) {
            current = height[i];
            min = current;
            stop = maxIndex;
            if(maxSeen <= current) {
                min = maxSeen;
                maxSeen = current;
                maxIndex = i;
            }
            
            currentMax = 0;
            for(j = i-1; j > stop && min >= height[j]; j--) {
                if(currentMax < height[j]) currentMax = height[j];
                trap += min - currentMax;
            }
            
            
        }
        
        return trap;
    }
}
