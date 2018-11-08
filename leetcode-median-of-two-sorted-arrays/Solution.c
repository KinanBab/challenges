// https://leetcode.com/problems/median-of-two-sorted-arrays/

#include <limits.h>

static int n;
static int m;
static int l;
static int i;

int myBinarySearch(int* nums1, int* nums2, int start, int end) { 
    while(!(start == end || start >= n)) {
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

double findMedianSortedArrays(int* nums1, int nums1Size, int* nums2, int nums2Size) {
    if(nums1Size < nums2Size) {
        nums1Size += nums2Size;
        nums2Size = nums1Size - nums2Size;
        nums1Size = nums1Size - nums2Size;
        int* tmp = nums1;
        nums1 = nums2;
        nums2 = tmp;
    }
    
    n = nums1Size;
    m = nums2Size;
    l = n + m;
    i = (l - 1) / 2;

    if(m == 0) {
        double median = nums1[i];
        if(n % 2 == 0) {
            median = (median + nums1[i+1]) / 2.0;
        }
        return median;
    }
    
    // main code
    int left = 1;
    int k = myBinarySearch(nums1, nums2, 0, n);
    if (k == -1) {
        n = nums2Size;
        m = nums1Size;
        k = myBinarySearch(nums2, nums1, 0, n);
        left = 0;
        n = nums1Size;
        m = nums2Size;
    }

    int median = left == 1 ? nums1[k] : nums2[k];
    if(l % 2 == 1) {
        return median;
    }

    // must get next largest element, sum, and divide by 2
    int check = i - k - 1;
    int candidate1 = INT_MAX;
    int candidate2 = INT_MAX;
    if(left) {
        if (k+1 < n) candidate1 = nums1[k+1];
        if (check+1 < m) candidate2 = nums2[check+1];
    } else {
        if (k+1 < m) candidate1 = nums2[k+1];
        if (check+1 < n) candidate2 = nums1[check+1];
    }

    median = median + (candidate1 < candidate2 ? candidate1 : candidate2);
    return median / 2.0;
}

int checkIfMedian(int* nums1, int* nums2, int k) {
    int candidate = nums1[k];

    // we know there are exactly k elements less than nums1[k] in nums1 alone,
    // therefore nums1[k] is the element we are looking for if
    // there are exactly i - k in nums2.
    int check = i - k;
    if(check < 0) {
        return -1;
    }
    if(check > m) {
        return 1;
    }

    if(check == 0) {
        if(candidate <= nums2[0]) {
            return 0;
        }
        return -1;
    }

    if(check == m) {
        if(nums2[m-1] <= candidate) {
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
