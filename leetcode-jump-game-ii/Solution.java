// https://leetcode.com/problems/jump-game-ii/

class Solution {
  public int jump(int[] nums) {
    int n = nums.length;
    if(n < 2) {
      return 0;
    }

    // mins[k] Will store the minimum number of jumps needed to get
    // to the end, given that we are initially places somewhere between
    // our current index (i+1) and (k) inclusively. 
    // our current index is initially n-1, and is represented by i.
    // when the loop terminates, i = -1 => mins[0] will be the answer.
    int[] mins = new int[n];
    // not needed: by default initializaed to 0
    // mins[n-1] = 0; // we can get to the end in zero jumps if we are at the end!

    // traverse backwards from destination
    for(int i = n-2; i >= 0; i--) {
      // this is the max jump we can make
      int num = nums[i];
      if(num == 0) { // stuck!
        mins[i] = n + 1; // an impossible min, any actual path will be at most n!
        continue;
      }

      // index of max element within jump
      int maxTarget = num + i;
      if(maxTarget >= n) { // make sure we do not go out of bounds
        maxTarget = n-1;
      }

      // the best we can do is make the *optimal jump* somewhere within the target, and
      // follow the optimal path from there.
      int min = mins[maxTarget] + 1;
      mins[i] = min;

      // since we only look at mins[maxTarget], we must update following entries in min
      // so that previous jumps will get the right result (imagine a case where we can jump
      // far away, but the previous place only allows a smaller jump, it could be optimal
      // to jump to the current place, and not to one of few the consequent ones).
      for(int u = i+1; mins[u] > min; u++) {
        // we do not need to check u < mins.length before access,
        // i <= n-2 -> i+1 <= n-1, mins[n-1] = 0, since mins contains non-negatives
        // the loop will break at mins[n-1] the latest.
        mins[u] = min;
      }
    }
    return mins[0];
  }
}
