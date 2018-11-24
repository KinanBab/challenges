// https://leetcode.com/problems/first-missing-positive/

class Solution {
    public int firstMissingPositive(int[] nums) {
        for(int i = 0; i < nums.length; i++) {
            int n = nums[i];
            if(n <= 0 || n > nums.length) {
                nums[i] = 0;
                continue;
            }
            
            if(n-1 != i) {
                int m = nums[n-1];
                if(m == n) nums[i] = 0;
                else {
                    nums[n-1] = n;
                    nums[i] = m;
                    i--;
                }
            }
        }
        
        for(int i = 0; i < nums.length; i++) {
            if(nums[i] == 0) {
                return i+1;
            }
        }
        
        return nums.length+1;
    }
}