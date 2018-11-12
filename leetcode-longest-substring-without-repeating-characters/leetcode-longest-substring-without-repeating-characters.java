// https://leetcode.com/problems/longest-substring-without-repeating-characters/

class Solution {    
  public int lengthOfLongestSubstring(String s) {
    // assuming only ascii characters
    int[] lastOcc = new int[256]; // will contain (index + 1) of last occurence

    // store max for output
    int max = 0;
    
    // mark the start index of the current non-repeating substring
    int currentStart = 0;
    for(int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      
      // get the last occurence of this character
      int lo = lastOcc[(int) c];
      if (lo <= currentStart) {
        // last occurence is before the start of this substring (or zero, 
        // which means we did not see the character yet), must extened
        // the current string by 1
        int tmp = i - currentStart + 1;
        if(tmp > max) max = tmp;
      } else {
        // repeating character, forget about this string, use the next largest non-repeating string.
        // Must start at the index of the repeating character plus one!
        currentStart = lo; // lo = index + 1
      }

      lastOcc[(int) c] = i + 1;
    }
    
    return max;
  }
}
