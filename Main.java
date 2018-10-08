import java.lang.reflect.Array;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        //////////////////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////////////////
        /////////////Starting 3rd solution//////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////
        ArrayList<Integer> inner;
        //test-case 1
        /*ArrayList<ArrayList<Integer>> outer = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> inner = new ArrayList<Integer>();
        inner.add(3);
        inner.add(5);
        outer.add(inner);
        inner=new ArrayList<Integer>();
        inner.add(1);
        inner.add(2);
        inner.add(4);
        outer.add(inner);
        inner=new ArrayList<Integer>();
        inner.add(3);
        inner.add(6);
        outer.add(inner);
        inner=new ArrayList<Integer>();
        inner.add(4);
        inner.add(7);
        outer.add(inner);
        inner=new ArrayList<Integer>();
        inner.add(5);
        inner.add(8);
        outer.add(inner);
        inner=new ArrayList<Integer>();
        inner.add(8);
        inner.add(2);
        outer.add(inner);
        ArrayList<Integer> test1_element=new ArrayList<>();
        for(int i=1; i<8;i++) test1_element.add(i);
        BBDF start_run = new BBDF(test1_element, outer);
        ArrayList<ArrayList<Integer>> disjoint_sets=start_run.NextLevel();*/
        /////////////////test case 2///////////////////////////

        ArrayList<Integer> test2_element = new ArrayList<>();
        for(int i=1; i<7; i++) test2_element.add(i);
        ArrayList<ArrayList<Integer>> test2_graph = new ArrayList<ArrayList<Integer>>();
        inner=new ArrayList<Integer>();
        inner.add(2);
        inner.add(4);
        test2_graph.add(inner);
        inner=new ArrayList<Integer>();
        inner.add(1);
        inner.add(3);
        test2_graph.add(inner);
        inner=new ArrayList<Integer>();
        inner.add(3);
        inner.add(5);
        test2_graph.add(inner);
        inner=new ArrayList<Integer>();
        inner.add(1);
        inner.add(2);
        inner.add(4);
        test2_graph.add(inner);
        inner=new ArrayList<Integer>();
        inner.add(5);
        inner.add(6);
        test2_graph.add(inner);
        //BBDF test2 = new BBDF(test2_element, test2_graph);
        //ArrayList<ArrayList<Integer>> test2_soln=test2.NextLevel();

        /////////////////////////test3 to test exclusion//////////////////////////////
        inner=new ArrayList<Integer>();
        inner.add(5);
        inner.add(6);
        test2_graph.add(inner);
        //BBDF test2 = new BBDF(test2_element, test2_graph);
        //ArrayList<ArrayList<Integer>> test2_soln=test2.NextLevel();
/////////////////////////Test case group 3: 50 elements, 100 set, each set size bounded by 5/////////////
        ArrayList<ArrayList<Integer>>[] test3_soln = new ArrayList[100];
        ArrayList<ArrayList<Integer>>[] test3_soln_bounded = new ArrayList[100];
        Random rand = new Random();
        BBDF[] test3 = new BBDF[100];
        BoundedBBDF[] test3_bounded = new BoundedBBDF[100];
        int[][] total_node = new int[100][1];
        int[][] total_node_bounded = new int[100][1];
        for(int i=0; i<100; i++){   //number of test cases
            ArrayList<ArrayList<Integer>> graph_list=new ArrayList<ArrayList<Integer>>();
            for(int j=0; j<100; j++){ //number of sets in the test case
                ArrayList<Integer> set = new ArrayList<>();
                int size=rand.nextInt(5)+1;
                for(int x=0; x<size; x++){  //set size
                    int val=rand.nextInt(50)+1;
                    if(!set.contains(val)) set.add(val);
                }
                graph_list.add(set);
            }
            test3[i] = new BBDF(graph_list);
            test3_bounded[i] = new BoundedBBDF(graph_list);
            test3_soln[i] = test3[i].NextLevel(total_node[i]);
            test3_soln_bounded[i]=test3_bounded[i].NextLevel(total_node_bounded[i]);
        }
        int test3_nodes=0, test3_nodes_bounded=0;
        for(int i=0; i<total_node.length; i++){
            test3_nodes += total_node[i][0];
            test3_nodes_bounded += total_node_bounded[i][0];
        }
        test3_nodes =test3_nodes/total_node.length;
        test3_nodes_bounded=test3_nodes_bounded/total_node_bounded.length;
        System.out.println(test3_nodes);
        System.out.println(test3_nodes_bounded);




        ///////////////Test case group 4: 50 elements, 100 sets, each set size bounded by 10/////////////

    }



}
