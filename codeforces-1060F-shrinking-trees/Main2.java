// https://codeforces.com/problemset/problem/1060/F

import java.io.*;
import java.util.*;
import java.lang.Math; 
import java.lang.StringBuilder;
 
public class Main2 {
   public static double combInvF(int n, int size, int count) {
      double ans = 1;
      
      while(count > 0) {
        ans = (ans * size) / n;
        size--;
        n--;
        count--;
      }
      
      return ans;
   }

   public static void main(String[] args) {
      MyScanner sc = new MyScanner();
      out = new PrintWriter(new BufferedOutputStream(System.out));

      // Read input size
      int n = sc.nextInt();
      
      int[] degrees = new int[n+1];
      // Read input edges
      for(int i = 1; i < n; i++) {
        int src = sc.nextInt();
        int dst = sc.nextInt();
        
        degrees[src]++;
        degrees[dst]++;
      }

      // precompute 0.5 to powers 0 to n
      double[] powers = new double[n+1];
      powers[0] = 1;
      for(int i = 1; i <= n; i++) {
        powers[i] = 0.5 * powers[i-1];
      }

      // probability based on degree
      double[] probs = new double[n];
      probs[0] = 1;      
      for(int d = 1; d < n; d++) {
        for(int b = 0; b < n-d; b++) { // size of before set, between 0 and all other edges
          probs[d] += combInvF(n-1, n-1-d, b) * combInvF(n-1-b, d, d) * powers[n-1-b];
        }
      }

      for(int i = 1; i <= n; i++) {
        out.printf("%.10f\n", probs[degrees[i]]);
      }

      out.close();
   }

     

   //-----------PrintWriter for faster output---------------------------------
   public static PrintWriter out;
      
   //-----------MyScanner class for faster input----------
   public static class MyScanner {
      BufferedReader br;
      StringTokenizer st;
 
      public MyScanner() {
         br = new BufferedReader(new InputStreamReader(System.in));
      }
 
      String next() {
          while (st == null || !st.hasMoreElements()) {
              try {
                  st = new StringTokenizer(br.readLine());
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
          return st.nextToken();
      }
 
      int nextInt() {
          return Integer.parseInt(next());
      }
 
      long nextLong() {
          return Long.parseLong(next());
      }
 
      double nextDouble() {
          return Double.parseDouble(next());
      }
 
      String nextLine(){
          String str = "";
	  try {
	     str = br.readLine();
	  } catch (IOException e) {
	     e.printStackTrace();
	  }
	  return str;
      }

   }
   //--------------------------------------------------------
}
