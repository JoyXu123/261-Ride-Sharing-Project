import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.IntStream;

public class BoundedBBDF {
    ArrayList<Integer> element_set;
    ArrayList<ArrayList<Integer>> graph_set;
    HashMap<Integer, ArrayList<Integer>> e_set;
    int bound=10000;

    BoundedBBDF(ArrayList<Integer> a, ArrayList<ArrayList<Integer>> b){
        element_set=a;
        graph_set=b;
        //step 1.1: put all groups into number element
        e_set=new HashMap<>();
        for(int i=0; i<graph_set.size(); i++){
            ArrayList<Integer> e_numbers= graph_set.get(i);
            for(Integer j: e_numbers){
                if( !e_set.containsKey(j)){
                    ArrayList<Integer> c= new ArrayList<>();
                    c.add(i);
                    e_set.put(j, c);
                }else{
                    ArrayList<Integer> ab= e_set.get(j);
                    ab.add(i);
                    e_set.put(j, ab);
                }
            }
        }
        bound=Math.min(bound,element_set.size()+1);
    }
    BoundedBBDF(ArrayList<Integer> a, ArrayList<ArrayList<Integer>> b, int bound_val){
        element_set=a;
        graph_set=b;
        //step 1.1: put all groups into number element
        e_set=new HashMap<>();
        for(int i=0; i<graph_set.size(); i++){
            ArrayList<Integer> e_numbers= graph_set.get(i);
            for(Integer j: e_numbers){
                if( !e_set.containsKey(j)){
                    ArrayList<Integer> c= new ArrayList<>();
                    c.add(i);
                    e_set.put(j, c);
                }else{
                    ArrayList<Integer> ab= e_set.get(j);
                    ab.add(i);
                    e_set.put(j, ab);
                }
            }
        }
        bound=Math.min(bound,Math.min(bound_val,element_set.size()+1));
    }
    BoundedBBDF(ArrayList<ArrayList<Integer>> b){
        element_set=new ArrayList<Integer>();
        graph_set=b;
        //step 1.1: put all groups into number element
        e_set=new HashMap<>();
        for(int i=0; i<graph_set.size(); i++){
            ArrayList<Integer> e_numbers= graph_set.get(i);
            for(Integer j: e_numbers){
                if( !e_set.containsKey(j)){ //also means element_list does not contain it
                    element_set.add(j);
                    ArrayList<Integer> c= new ArrayList<>();
                    c.add(i);
                    e_set.put(j, c);
                }else{
                    ArrayList<Integer> ab= e_set.get(j);
                    ab.add(i);
                    e_set.put(j, ab);
                }
            }
        }
        bound=Math.min(bound,element_set.size()+1);
    }
    BoundedBBDF(ArrayList<ArrayList<Integer>> b, int bound_val){
        element_set=new ArrayList<Integer>();
        graph_set=b;
        //step 1.1: put all groups into number element
        e_set=new HashMap<>();
        for(int i=0; i<graph_set.size(); i++){
            ArrayList<Integer> e_numbers= graph_set.get(i);
            for(Integer j: e_numbers){
                if( !e_set.containsKey(j)){ //also means element_list does not contain it
                    element_set.add(j);
                    ArrayList<Integer> c= new ArrayList<>();
                    c.add(i);
                    e_set.put(j, c);
                }else{
                    ArrayList<Integer> ab= e_set.get(j);
                    ab.add(i);
                    e_set.put(j, ab);
                }
            }
        }
        bound=Math.min(bound,Math.min(bound_val,element_set.size()+1));
    }

    /*ArrayList<ArrayList<Integer>>  SoleCheck(int[][] adjacency ){
        ArrayList<ArrayList<Integer>> solution = new ArrayList<ArrayList<Integer>>();
        for(Integer e: element_set){
            if(e_set.containsKey(e) && e_set.get(e).size()==1){
                //put the set in the solution
                ArrayList<Integer> set = e_set.get(e);
                solution.add(set);
                //remove the element and set from the hash table
                e_set.remove(e);
                ArrayList<ArrayList<Integer>> tobe_deleted_set = new ArrayList<ArrayList<Integer>>();
                //add all the adjacent set into tobe_deleted_set if not already in it.
                for(Integer comp_e: set) {
                    ArrayList<Integer> tobe_deleted_s =e_set.get(comp_e);
                    tobe_deleted_set.add(e_set.get(comp_e));

                }
                //remove intersecting sets from the hash table entries
                int set_index = graph_set.indexOf(set);
                for(int j=0; j<adjacency[set_index].length; j++){
                    if(adjacency[set_index][j]==1 && e_set.containsKey(e)){
                        e_set.get(e).remove(graph_set.get(j));
                    }
                }

            }
        }

    }*/

    boolean check_sole(){
        for(Integer e: element_set){
            if(e_set.containsKey(e) && e_set.get(e).size()==1){
                return true;
            }
        }
        return false;
    }
    int sole_element(){
        for(Integer e: element_set){
            if(e_set.containsKey(e) && e_set.get(e).size()==1){
                return e;
            }
        }
        return -1;
    }

    boolean check_end(){
        for(Integer e: element_set){
            if(!e_set.containsKey(e))
                return true;
        }
        return false;
    }

    ArrayList<ArrayList<Integer>> NextLevel(int[] total_node){
        total_node[0]=total_node[0]+1;
        if(check_goal()) return graph_set;
        if(check_end()) return null;

        int[][] adjacency = new int[graph_set.size()][graph_set.size()];
        ArrayList<ArrayList<ArrayList<Integer>>> groups = union_find(adjacency);


        if(check_sole()==true){ // it means that Sole Sets exist, perform inclusion action
            int sole_e = sole_element();
            //perform Inclusion action
            int set_index = e_set.get(sole_e).get(0);
            //ArrayList<ArrayList<Integer>> sol = new ArrayList<>();
            ArrayList<Integer> s = graph_set.get(set_index);
            //sol.add(s);
            ArrayList<ArrayList<Integer>> graph_set_next = new ArrayList<ArrayList<Integer>>();
            for(int j=0; j<adjacency[set_index].length; j++){
                if(adjacency[set_index][j]==0 && set_index!=j){
                    graph_set_next.add(graph_set.get(j));  //if no intersection add to next level
                }
            }
            ArrayList<Integer> element_set_next = new ArrayList<Integer>(element_set);
            for(int i=0; i<s.size(); i++){
                int index = element_set_next.indexOf(s.get(i));
                if(index!=-1) element_set_next.remove(index);
            }
            BoundedBBDF inclusion = new BoundedBBDF(element_set_next, graph_set_next, bound);
            ArrayList<ArrayList<Integer>> solution_inclusion=inclusion.NextLevel(total_node); //inclusion solution has not added s yet.
            if(solution_inclusion != null){
                solution_inclusion.add(s);
                return solution_inclusion;
            }else{
                return null;
            }

        }




        ArrayList<ArrayList<Integer>> solution=new ArrayList<ArrayList<Integer>>();

        if(groups.size()>1){
            BoundedBBDF[] sub=new BoundedBBDF[groups.size()];
            for(int i=0; i<groups.size(); i++){
                sub[i]=new BoundedBBDF(groups.get(i), bound);
                ArrayList<ArrayList<Integer>> sub_sol=sub[i].NextLevel(total_node);
                if(sub_sol==null || solution.size()>bound){
                    return null;
                }else{
                    solution.addAll(sub_sol);
                }
            }
            return solution;
        }else{
            int set_index=largest_connectivity(adjacency);
            //now Inclusion and Exclusion step
            //Inclusion step
            ArrayList<ArrayList<Integer>> solution_1=new ArrayList<ArrayList<Integer>>();
            ArrayList<Integer> s = graph_set.get(set_index);
            solution_1.add(s);
            ArrayList<ArrayList<Integer>> graph_set_next = new ArrayList<ArrayList<Integer>>();
            for(int j=0; j<adjacency[set_index].length; j++){
                if(adjacency[set_index][j]==0 && set_index!=j){
                    graph_set_next.add(graph_set.get(j));  //if no intersection add to next level
                }
            }
            ArrayList<Integer> element_set_next = new ArrayList<Integer>(element_set);
            for(int i=0; i<s.size(); i++){
                int index = element_set_next.indexOf(s.get(i));
                if(index!=-1) element_set_next.remove(index);
            }
            BoundedBBDF inclusion = new BoundedBBDF(element_set_next, graph_set_next, bound);
            ArrayList<ArrayList<Integer>> solution_inclusion=inclusion.NextLevel(total_node); //inclusion solution has not added s yet.

            //Exclusion step
            //nothing needed to be done for element set
            if(solution_inclusion!=null){
                solution_inclusion.add(s);
                bound=Math.min(solution_inclusion.size(), bound);
            }
            ArrayList<ArrayList<Integer>> graph_set_exclusion = new ArrayList<ArrayList<Integer>>(graph_set);
            graph_set_exclusion.remove(set_index);
            ArrayList<Integer> element_set_exclusion = new ArrayList<Integer>(element_set);
            BoundedBBDF exclusion = new BoundedBBDF(element_set_exclusion, graph_set_exclusion, bound);
            ArrayList<ArrayList<Integer>> solution_exclusion=exclusion.NextLevel(total_node);


            if(solution_inclusion!=null && solution_exclusion!=null){
                return solution_inclusion.size()<solution_inclusion.size() ? solution_inclusion : solution_exclusion;
            }else{
                return solution_inclusion==null ? solution_exclusion : solution_inclusion;
            }

        }
    }
    int largest_connectivity(int[][] adjacency){  //returns the index of the set with highest connectivity
        int largest_sum=0, largest_sum_setIndex=0;
        for(int i=0; i<adjacency.length; i++){
            if(largest_sum< IntStream.of(adjacency[i]).sum()){
                largest_sum=IntStream.of(adjacency[i]).sum();
                largest_sum_setIndex=i;
            }
        }
        return largest_sum_setIndex;
    }
    boolean check_goal(){
        for(Integer e : element_set){
            if(!e_set.containsKey(e) || e_set.get(e).size()!=1)
                return false;
        }
        return true;
    }

    ArrayList<ArrayList<ArrayList<Integer>>> union_find(int[][] adjacency){
        //first establish adjacency matrix
        /*//step 1.1: put all groups into number element
        HashMap<Integer, ArrayList<Integer>> e_set=new HashMap<>();
        for(int i=0; i<graph_set.size(); i++){
            ArrayList<Integer> e_numbers= graph_set.get(i);
            for(Integer j: e_numbers){
                if( !e_set.containsKey(j)){
                    ArrayList<Integer> a= new ArrayList<>();
                    a.add(i);
                    e_set.put(j, a);
                }else{
                    ArrayList<Integer> ab= e_set.get(j);
                    ab.add(i);
                    e_set.put(j, ab);
                }
            }
        }*/
        //step 1.2: adjacency matrix

        //step 1.3: convert all the groups with the same element to true in adjacency matrix
        for(Integer b: e_set.keySet()){
            ArrayList<Integer> group = e_set.get(b);
            for(int i=0; i<group.size(); i++){
                for(int j=i+1; j<group.size(); j++){
                    adjacency[group.get(i)][group.get(j)]=1;
                    adjacency[group.get(j)][group.get(i)]=1;
                }
            }
        }

        //Next we need to break them into groups according to the union_find method
        int[] set_parent = new int[graph_set.size()];
        for(int i=0; i<set_parent.length; i++)   set_parent[i]=-1;    //initialize everything to -1
        for(int i=0;i<graph_set.size(); i++){
            int new_head= set_parent[i]==-1 ? i: set_parent[i];
            set_parent[i]=new_head;
            while(new_head!=set_parent[new_head]) new_head=set_parent[new_head];

            for(int j=i+1; j<graph_set.size(); j++){
                if(adjacency[i][j]==1 ){
                    if(set_parent[j]==-1) set_parent[j]=new_head;
                    else{
                        int old_head=set_parent[j];
                        while(old_head!=set_parent[old_head]) old_head=set_parent[old_head];  //make sure we find the top of the previous group
                        new_head = Math.min(old_head,new_head);
                        set_parent[old_head] = new_head<old_head ? new_head:old_head;
                    }
                    set_parent[j]=new_head;
                }
            }
            set_parent[i]=new_head;
        }

        ArrayList<ArrayList<ArrayList<Integer>>> res = new ArrayList<ArrayList<ArrayList<Integer>>>();
        HashMap<Integer, ArrayList<ArrayList<Integer>>> set_map=new HashMap<>(); //map set number to sets
        for(int i=0; i<set_parent.length; i++){
            int head=set_parent[i];
            int x=i;
            while(head!=x){
                x=head;
                head=set_parent[x];
                //find the smallest head
            }
            if(set_map.containsKey(head))  set_map.get(head).add(graph_set.get(i));
            else{
                ArrayList<ArrayList<Integer>> val = new ArrayList<ArrayList<Integer>>();
                val.add(graph_set.get(i));
                set_map.put(head, val);
            }
        }
        for(Integer i: set_map.keySet()){
            res.add(set_map.get(i));
        }
        return res;

    }

}

