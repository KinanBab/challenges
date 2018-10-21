// https://codeforces.com/problemset/problem/1060/F

import java.io.*;
import java.util.*;
import java.lang.Math; 
import java.lang.StringBuilder;
 
public class Main {
   public static String join(int[] e) {
      StringBuilder sb = new StringBuilder(e.length);
      for(int i = 0; i < e.length; i++)
        sb.append(e[i]);
      return sb.toString();
   }

   // DP table
   public static HashMap<String, double[]> table = new HashMap<String, double[]>();

   // Global state: unmodified, or modified in a smart way 
   public static HashMap<String, Integer> order = new HashMap<String, Integer>();
   public static int[] graph;

   // Computation function
   public static double[] computeProbs(ArrayList<int[]> edges, int[] translations, int M) {
      String graphStr = join(graph) + "-" + join(translations);
      double[] d = table.get(graphStr);
      if(d != null)
        return d;
            
      // Base case
      if(edges.size() == 1) {
        int e1 = edges.get(0)[0];
        int e2 = edges.get(0)[1];
      
        double[] probs = new double[M];  
        probs[translations[e1]-1] = 0.5;
        probs[translations[e2]-1] = 0.5;
        table.put(graphStr, probs);
        return probs;
      }

      // Recursive step
      double[] probs = new double[M];
      for(int ei = 0; ei < edges.size(); ei++) {
        int[] e = edges.get(ei);
        int e1 = translations[e[0]];
        int e2 = translations[e[1]];
        String edg = e[0]+":"+e[1];
        int o = order.get(edg);
        
        // make copy and update graph structures
        // e1 replaced e2
        graph[o] = 1;
        ArrayList<int[]> nEdges = new ArrayList<int[]>(edges.size() - 1);
        int[] nTranslation = new int[M+1];
        for(int i = 1; i <= M; i++) {
          int t = translations[i];
          t = t == e2 ? e1 : t;
          nTranslation[i] = t;
          
          if(i-1 != ei && i-1 < edges.size())
            nEdges.add(edges.get(i-1));
        }
        nTranslation[e2] = e1;
        
        // recursive call
        double[] tmpResult = computeProbs(nEdges, nTranslation, M);

        // undo inplace graph modification
        graph[o] = 0;
        
        // aggreate result of recursion into final result
        for(int i = 0; i < tmpResult.length; i++) {
          if(i == e1-1)
            probs[i] += tmpResult[i] * 0.5;
          else if(i == e2-1)
            probs[i] += tmpResult[e1-1] * 0.5;
          else
            probs[i] += tmpResult[i];
        }
      }

      // All of the probabilities are given that a specfic edge was picked: keep division till the end to reduce floating point errors.
      for(int i = 0; i < probs.length; i++)
        probs[i] /= edges.size();

      return probs;
   }

   public static void main(String[] args) {
      MyScanner sc = new MyScanner();
      out = new PrintWriter(new BufferedOutputStream(System.out));

      // Read input size
      int n = sc.nextInt();
      if(n == 1) {
        out.printf("%.10f\n", 1.0);
        out.close();
        return;
      }
      
      ArrayList<int[]> edges = new ArrayList<int[]>();
      ArrayList<String> sorted = new ArrayList<String>();
      int[] translations = new int[n+1];
      graph = new int[n-1];
      
      // Read input edges
      for(int i = 1; i < n; i++) {
        int src = sc.nextInt();
        int dst = sc.nextInt();
        
        int min = src < dst ? src : dst;
        int max = src < dst ? dst : src;
        
        // fill in data structures
        sorted.add(min + ":" + max);
        edges.add(new int[] { min, max });
        translations[i] = i;
      }

      // more representation helpers
      translations[n] = n;
      Collections.sort(sorted);
      for (int i = 0; i < sorted.size(); i++) {
        order.put(sorted.get(i), i);
      }
            
      // now the actual code
      double[] result = computeProbs(edges, translations, n);
      for(double d : result) {
        out.printf("%.10f\n", d);
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
