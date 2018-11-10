// https://leetcode.com/problems/longest-valid-parentheses/

class Solution {
  public int longestValidParentheses(String s) {
      int n = s.length();

      int max = 0; // will store the length of the longest valid substring
      
      // We are simulating a stack using an array since the maximum size of the stack is already known (n)
      // this is a tiny bit faster, we will use the stack to match paranthesis, but also 
      // keep track of lengths of valid expressions.
      int[] efficientStack = new int[n];
      int lastIndex = -1; // Points to the element added to the stack last
      
      // Go through the string from left to right
      for(int i = 0; i < n; i++) {
          char c = s.charAt(i);
          
          if(c == '(') {
            // This *could* be the start of a valid expression,
            // or part of an already valid expression,
            // push index to the stack.
            lastIndex++;
            efficientStack[lastIndex] = i;
          } else if(lastIndex >= 0) {
              // c == ')' and stack is none-empty,
              // this means we already have at least one free '(', so this constitutes a valid expression
              // the length of this expression is our current index - the index of the matching open '('
              // which is on the stack, plus 1.
              int lengthOfCurrentValid = i - efficientStack[lastIndex] + 1;
              if(max < lengthOfCurrentValid) {
                  max = lengthOfCurrentValid;
              }
              
              // now comes the interesting trick!
              // if we remove the element we read last from the stack, we loose
              // all info about the current valid expression, this will underestimate the 
              // length of the valid expression, since two adjecent valid expressions are valid
              // even when they do not share paranthesis.
              // Think of the case of ()(), by the time we see the first ), if we consume the element 
              // on the stack, we will be led to believe the max length is 2.
              // Instead, we look ahead, if the next character is (, then there is a *potential*
              // to having our current valid expression extended, we will ignore the next (, and keep
              // the start of this expression on the stack. If this next ( is matched with a ), then
              // the new length will be this expression's length + the new expression length.
              // Inductively, this would capture any number of repetitions of (..)(..)(..)..
              if(i + 1 < n && s.charAt(i+1) == '(') {
                i++;
              } else {
                // look ahead failed, remove element from stack, to allow even older ( to be matched next.
                lastIndex--;
              }
          }
      }
      
      return max;
  }
}
