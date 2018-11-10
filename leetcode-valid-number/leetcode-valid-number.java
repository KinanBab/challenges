https://leetcode.com/problems/valid-number/

import java.util.regex.Pattern;

class Solution {
  // why re-invent the wheel, just regex it!
  // ofcourse, using a loop with some smart boolean flags will probably be faster.
  // but both options are as uninteresting as each other, and I want to practice regexs.
  
  
  static Pattern p = Pattern.compile("[-+]?([0-9]+\\.?|[0-9]*\\.[0-9]+)(e[-+]?[0-9]+)?");
  // sign := [-+]?
  // nat := [0-9]+\\.?    allows 1. representing 1.0
  // flt := [0-9]*\\.[0-9]+  allows .1 rep. 0.1
  // part := sign (nat | flt)  signed numbers
  // regex = part (e part)?   a number is either a signed number, or two signed numbers seperated by e

  public boolean isNumber(String s) {
    return p.matcher(s.trim()).matches();        
  }
}
