// https://leetcode.com/problems/median-of-two-sorted-arrays/

class Solution {
    public int myBinarySearch(int[] nums1, int[] nums2, int start, int end) { 
        while(!(start == end || start >= nums1.length)) {
            int mid = (start + end) / 2; // bug if start+end overflow!
            int dir = checkIfMedian(nums1, nums2, mid);
            if(dir == 0) {
                return mid;
            } else if(dir < 0) {
                end = mid;
            } else {
                start = mid+1;
            }
        }
        return -1;
    }
    
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if(nums1.length < nums2.length) {
            int[] tmp = nums2;
            nums2 = nums1;
            nums1 = tmp;
        }

        if(nums2.length == 0) {
            int k = (nums1.length - 1)/2;
            double median = nums1[k];
            if(nums1.length % 2 == 0) {
                median += nums1[k+1];
                return median / 2;
            }
            return median;
        }

        boolean left = true;
        int k = myBinarySearch(nums1, nums2, 0, nums1.length);
        if (k == -1) {
            k = myBinarySearch(nums2, nums1, 0, nums2.length);
            left = false;
        }

        int median = left ? nums1[k] : nums2[k];
        if((nums1.length + nums2.length) % 2 == 1) {
            return median;
        }
        
        // must get next largest element, sum, and divide by 2
        int check = (nums1.length + nums2.length - 1) / 2 - k - 1;
        int candidate1 = Integer.MAX_VALUE;
        int candidate2 = Integer.MAX_VALUE;
        if(left) {
            if (k+1 < nums1.length) candidate1 = nums1[k+1];
            if (check+1 < nums2.length) candidate2 = nums2[check+1];
        } else {
            if (k+1 < nums2.length) candidate1 = nums2[k+1];
            if (check+1 < nums1.length) candidate2 = nums1[check+1];
        }

        median = median + Math.min(candidate1, candidate2);
        return median / 2.0;
    }
    
    public int checkIfMedian(int[] nums1, int[] nums2, int k) {
        int candidate = nums1[k];

        // indices
        int n = nums1.length;
        int m = nums2.length;
        int l = n + m;
        int i = (l - 1) / 2; // alright even if floored

        // we know there are exactly k elements less than nums1[k] in nums1 alone,
        // therefore nums1[k] is the element we are looking for if
        // there are exactly i - k in nums2.
        int check = i - k;
        if(check < 0) {
            return -1;
        }
        if(check > nums2.length) {
            return 1;
        }

        if(check == 0) {
            if(candidate <= nums2[0]) {
                return 0;
            }
            return -1;
        }

        if(check == nums2.length) {
            if(nums2[nums2.length-1] <= candidate) {
                return 0;
            }
            return 1;
        }
        
        // check > 0, check < nums2.length
        if(candidate > nums2[check]) {
            return -1;
        }
        if(nums2[check-1] > candidate) {
            return 1;
        }
        
        return 0;
    }
}
